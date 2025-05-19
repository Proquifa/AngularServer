/**
 * 
 */
package com.proquifa.net.negocio.comun.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.Accion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Gestion;
import com.proquifa.net.modelo.comun.Incidente;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.comun.AccionService;
import com.proquifa.net.persistencia.comun.AccionDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.GestionDAO;
import com.proquifa.net.persistencia.comun.IncidenteDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.ProcesoDAO;
import com.proquifa.net.persistencia.comun.ReferenciaDAO;

/**
 * @author amartinez
 *
 */
@Service("accionService")
public class AccionServiceImpl implements AccionService {
	
	final Logger log = LoggerFactory.getLogger(AccionServiceImpl.class);
	
	@Autowired
	private AccionDAO accionDAO;
	@Autowired
	private PendienteDAO pendienteDAO;
	@Autowired
	private EmpleadoDAO empleadoDAO;
	@Autowired
	private ReferenciaDAO referenciaDAO;
	@Autowired
	private GestionDAO gestionDAO;
	@Autowired
	private IncidenteDAO incidenteDAO;
	@Autowired
	private ProcesoDAO procesoDAO;
	
	private Funcion funcion;
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.AccionService#actualizarAccion(mx.com.proquifa.proquifanet.modelo.comun.Accion)
	 */
	@Transactional
	public Boolean actualizarAcciones(List<Accion> acciones) throws ProquifaNetException {
		funcion = new Funcion();
		this.accionDAO.borrarAccionesXIdIncidente(acciones.get(0).getIncidente());
		for(Accion a: acciones){
			this.accionDAO.registrarAccion(a);
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.AccionService#borrarAccion(mx.com.proquifa.proquifanet.modelo.comun.Accion)
	 */
	public Boolean borrarAccion(Accion accion) throws ProquifaNetException {
		return this.accionDAO.eliminarAccion(accion);
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.AccionService#obtenerAccionesXIdIncidente(java.lang.Long)
	 */
	@Transactional
	public List<Accion> obtenerAccionesXIdIncidente(Long idIncidente)
			throws ProquifaNetException {
		List<Accion> acciones = this.accionDAO.obtenerAccionesIncidente(idIncidente);
		funcion = new Funcion();
		Long diasAtraso = 0L;
		for(Accion a: acciones){
			diasAtraso = funcion.calcularDiasAtraso(a.getFechaEstimadaRealizacion(), 0);
			a.setDiasAtraso(diasAtraso);
			a.setReferencias(this.referenciaDAO.obtenerReferenciaXIdIncidente(idIncidente));
			Empleado e = this.empleadoDAO.obtenerEmpleadoPorId(a.getResponsable());
			if(e!=null){
				a.setNombreResponsable(e.getUsuario());
			}
		}
		return acciones;
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.AccionService#registrarAccion(mx.com.proquifa.proquifanet.modelo.comun.Accion)
	 */
	@Transactional
	public Boolean registrarAccion(Accion accion) throws ProquifaNetException {
		Long idAccion = this.accionDAO.registrarAccion(accion);
		if(idAccion == -1L){
			throw new ProquifaNetException("Error al registrar la acción");
		}
		return true;
	}
	@Transactional
	public Accion obtenerAccionXId(Long idAccion) throws ProquifaNetException {
		Accion temp = this.accionDAO.obtenerAccionXId(idAccion);
		temp.setReferencias(this.referenciaDAO.obtenerReferenciaXIdIncidente(temp.getIncidente()));
		return temp;
	}
	@Transactional
	public List<Accion> obtenerAccionesXUsuario(String usuario, String tipoPendiente)
			throws ProquifaNetException {
		List<Accion> acciones = this.accionDAO.obtenerAccionXUsuario(usuario, tipoPendiente);
		funcion = new Funcion();
		Long diasAtraso = 0L;
		String usuarioEmpleado = "";
		String usuarioResponsable = "";
		List<Referencia> referencias = null;
		for(Accion a: acciones){
			if(tipoPendiente.equals("Verificar accion")){
				Pendiente pendiente = new Pendiente();
				pendiente = this.pendienteDAO.obtenerPendienteXTipoDoctoResponsable(a.getIdAccion().toString(), usuario, "Verificar accion");
				a.setEnEsperaDesde(pendiente.getFechaInicio());
				diasAtraso = funcion.calcularDiasDeAtraso(a.getFechaRealizacion(),a.getFechaEstimadaRealizacion());
			}else{
				diasAtraso = funcion.calcularDiasAtraso(a.getFechaEstimadaRealizacion(),0);
			}
			usuarioEmpleado = this.empleadoDAO.obtenerEmpleadoPorId(a.getProgramo()).getUsuario();
			referencias = this.referenciaDAO.obtenerReferenciaXIdIncidente(a.getIncidente());
			a.setReferencias(referencias);
			a.setNombreProgramo(usuarioEmpleado);
			a.setDiasAtraso(diasAtraso);
			usuarioResponsable = this.empleadoDAO.obtenerEmpleadoPorId(a.getResponsable()).getUsuario();
			a.setNombreResponsable(usuarioResponsable);	
		}
		return acciones;
	}
	
	@Transactional
	public Boolean ejecutarAccion(Accion accion) throws ProquifaNetException {
		funcion = new Funcion();
		Accion temp = this.accionDAO.obtenerAccionXId(accion.getIdAccion());
		Empleado realizoAccion = this.empleadoDAO.obtenerEmpleadoPorId(temp.getResponsable());
		temp.setComentarios(accion.getComentarios());
		temp.setIdPendiente(accion.getIdPendiente());
		temp.setProgramo(accion.getProgramo());
		temp.setFechaRealizacion(new Date());
		Boolean actualizada = this.accionDAO.actualizarAccion(temp);
		if(actualizada){
			List<Referencia> referencias = accion.getReferencias();
			if(referencias != null){
				for(Referencia r: referencias){
					if(r.getIdReferencia() == 0){
						//funcion.copiarArchivo(r.getBytes(), r.getFolio() + "." + r.getExtensionArchivo(), "Incidente");
						this.referenciaDAO.insertarReferencia(r);
					}
				}
			}
			Pendiente accionARealizar = new Pendiente();
			accionARealizar.setFolio(accion.getIdPendiente());
			this.pendienteDAO.actualizarPendiente(accionARealizar);
			Pendiente verificarAccion = new Pendiente();
			verificarAccion.setDocto(accion.getIdAccion().toString());
			verificarAccion.setFechaInicio(new Date());
			Empleado responsable = this.empleadoDAO.obtenerEmpleadoPorId(accion.getProgramo());
			verificarAccion.setResponsable(responsable.getUsuario());
			verificarAccion.setTipoPendiente("Verificar accion");
			this.pendienteDAO.guardarPendiente(verificarAccion);
			//logger.info("Creación de correo para verificar accion...");
			Correo correo = new Correo();
			correo.setOrigen(realizoAccion.getUsuario());
			//logger.info("De: " + correo.getOrigen() + " Para: " + responsable.getUsuario());
			correo.setAsunto("Verificar acción " + temp.getFolio());
			correo.setCorreo(responsable.getUsuario() + "@proquifa.com.mx");
			correo.setArchivoAdjunto("");
			correo.setCuerpoCorreo("Se generó un pendiente de tipo verificación acción con el folio: " + temp.getFolio());
			funcion.enviarCorreo(correo);
			//logger.info("Correo enviado...");
		}
		return true;
	}
	@Transactional
	public Boolean verificarAccion(Accion accion) throws ProquifaNetException {
		funcion = new Funcion();
		Accion temp = this.accionDAO.obtenerAccionXId(accion.getIdAccion());
		if(temp == null){
			//logger.info("Error obteniendo la acción por Id");
			throw new ProquifaNetException("Error obteniendo la acción por Id");
		}
		temp.setHorasInvertidas(accion.getHorasInvertidas());
		temp.setEficaciaVerificacion(accion.getEficaciaVerificacion());
		temp.setDescripcionVerificacion(accion.getDescripcionVerificacion());
		temp.setIdPendiente(accion.getIdPendiente());
		temp.setProgramo(accion.getProgramo());
		//logger.info("actualizar acción...");
		Boolean actualizo = this.accionDAO.actualizarAccion(temp);
		Long nuevaReferencia = 0L;
		if(!actualizo){
			throw new ProquifaNetException("Error al acutalizar el registro de acción");
		}else{
			if(accion.getReferencias() != null){
				List<Referencia> referencias = accion.getReferencias();
				for(Referencia r : referencias){
					if(r.getIdReferencia() == 0){						
						nuevaReferencia = this.referenciaDAO.insertarReferencia(r);
						if(nuevaReferencia == -1L){
							//logger.info("Error al insertar la referencia...");
							throw new ProquifaNetException("Error al insertar la referencia...");
						}else{							
							//logger.info("Referencia insertada...ok");
							funcion.copiarArchivo(r.getBytes(), nuevaReferencia + "." + r.getExtensionArchivo(), "Incidente");
							//logger.info("Archivo copiado..." + " Nombre: " + nuevaReferencia + "." + r.getExtensionArchivo() + " Tamaño: " + r.getBytes().length);
						}
					}
				}
			}
			//logger.info("Actualización exitosa...");
			Pendiente verificacion = new Pendiente();
			verificacion.setFolio(accion.getIdPendiente());
			this.pendienteDAO.actualizarPendiente(verificacion);
			//logger.info("Actualizar pendiente...");
			Empleado empleado = this.empleadoDAO.obtenerEmpleadoPorId(temp.getProgramo());

			Long pendientesCerrados;
			Boolean levantarPendientePonderaciopn = false;
			
			//logger.info("Obtener acciones por incidente........");
			List<Accion> accionesLevantadas = this.accionDAO.obtenerAccionesIncidente(temp.getIncidente());
			//logger.info("numero de acciones........" + accionesLevantadas.size());
			//logger.info("Cuantas de acciones estan cerradas........");
			pendientesCerrados = this.accionDAO.numeroPendientesAccionVerificacion(temp.getIncidente(), "Accion a realizar", false);
			//logger.info("numero de acciones cerradas........" + pendientesCerrados);
			//Si el numero de pendientes de acciones a realizar cerrado es el mismo numero de acciones verificar cuantos pendientes 
			//de verificar acción estan cerrados.
			if(accionesLevantadas.size() == pendientesCerrados){
				//logger.info("Cuantos pendientes de verificar accion estan cerrados.........");
				pendientesCerrados = this.accionDAO.numeroPendientesAccionVerificacion(temp.getIncidente(), "Verificar accion", false);
				if(accionesLevantadas.size() == pendientesCerrados){
					levantarPendientePonderaciopn = true;
				}
			}
			
			if(levantarPendientePonderaciopn){
				String responsableProceso = "";
				Incidente incidente = this.incidenteDAO.obtenerIncidenteXId(temp.getIncidente());
				responsableProceso = this.procesoDAO.getResponsableDeProceso(empleado.getClave());
				Pendiente ponderacion = new Pendiente();
				ponderacion.setDocto(temp.getIncidente().toString());
				ponderacion.setFechaInicio(new Date());
				ponderacion.setTipoPendiente("Ponderar incidente");
				ponderacion.setResponsable(responsableProceso);
				this.pendienteDAO.guardarPendiente(ponderacion);
				//logger.info("Pendiente ponderacion creado...");
				//logger.info("Enviando correo para la ponderación...");
				Correo correo = new Correo();
				correo.setOrigen(empleado.getUsuario());
				//logger.info("De: " + correo.getOrigen() + " Para: plozada@proquifa.com.mx");
				correo.setAsunto("Ponderar incidente " + incidente.getFolio());
				correo.setArchivoAdjunto("");
				correo.setCorreo(responsableProceso +"@proquifa.com.mx");
				correo.setCuerpoCorreo("Se generó un pendiente de tipo ponderar incidente con el folio: " + incidente.getFolio());
				funcion.enviarCorreo(correo);
				//logger.info("Correo enviado...");
			}
		}
		return true;
	}
	@Transactional
	public Boolean finalizarProgramacionAccion(Incidente incidente)
			throws ProquifaNetException {
		Pendiente pendiente = new Pendiente();
		funcion = new Funcion();
		List<Accion> acciones = this.accionDAO.obtenerAccionesIncidente(incidente.getIdIncidente());
		//logger.info("Obtener acciones...ok");
		for(Accion a : acciones){
			pendiente.setDocto(a.getIdAccion().toString());
			Empleado empleado = this.empleadoDAO.obtenerEmpleadoPorId(a.getResponsable());
			Gestion gestion = this.gestionDAO.obtenerGestionXIdIncidente(a.getIncidente());
			Empleado programo = this.empleadoDAO.obtenerEmpleadoPorId(gestion.getEmpleado());
			pendiente.setResponsable(empleado.getUsuario());
			pendiente.setTipoPendiente("Accion a realizar");
			this.pendienteDAO.guardarPendiente(pendiente);
			//logger.info("Enviando correo para la programación de acciones...");
			Correo correo = new Correo();
			correo.setOrigen(programo.getUsuario());
			//logger.info("De: " + correo.getOrigen().toLowerCase() + " Para: " + empleado.getUsuario().toLowerCase());
			correo.setAsunto("Acción a realizar " + a.getFolio());
			correo.setArchivoAdjunto("");
			correo.setCorreo(empleado.getUsuario() + "@proquifa.com.mx");
			correo.setCuerpoCorreo("Se generó un pendiente de acción a realizar con el folio: " + a.getFolio());
			//logger.info("Correo enviado..." +  funcion.enviarCorreo(correo));
		}
		Pendiente gestionPendiente = new Pendiente();
		gestionPendiente.setFolio(incidente.getIdPendiente());
		this.pendienteDAO.actualizarPendiente(gestionPendiente);
		//logger.info("Cerrar pendiente gestionar ok...folio: " + gestionPendiente.getFolio());
		return true;
	}
	@Transactional
	public Boolean actualizarAccion(Accion nueva) throws ProquifaNetException {
		Boolean actualizado = this.accionDAO.actualizarAccion(nueva);
		//logger.info("Acción actualizada..." + actualizado);
		funcion = new Funcion();
		Long nuevaReferencia = 0L;
		if(actualizado){
			List<Referencia> referencias = nueva.getReferencias();
			//logger.info("Obteniendo referencias..." + nueva.getReferencias().size());
			if(referencias != null){
				for(Referencia r : referencias){
					if(r.getIdReferencia() == 0){						
						nuevaReferencia = this.referenciaDAO.insertarReferencia(r);
						//logger.info("Nueva referencia agregada..." + nuevaReferencia);
						funcion.copiarArchivo(r.getBytes(), nuevaReferencia + "." + r.getExtensionArchivo(), "incidente");
						//logger.info("Archivo copiado..." + nuevaReferencia + "." + r.getExtensionArchivo() + " Tamaño: " + r.getBytes());
					}else{
						//logger.info("Referencia actualizada..."+ this.referenciaDAO.actualizarReferencia(r));
					}
				}
			}
		}else{
			log.info("Error en la transacción");
			throw new ProquifaNetException("Error en la actualización de la acción");
		}
		return actualizado;
	}
}
