package com.proquifa.net.negocio.ventas.visitas.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.poi.hwpf.model.types.LSTFAbstractType;*/
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.DocumentoAdjunto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.DoctoR;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.modelo.ventas.visitas.AsistenciaDailyScrum;
import com.proquifa.net.modelo.ventas.visitas.DailyScrum;
import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.ObtenerPrecios;
import com.proquifa.net.modelo.ventas.visitas.ObtenerVisitas;
import com.proquifa.net.modelo.ventas.visitas.ReportarVisita;
import com.proquifa.net.modelo.ventas.visitas.ResumenSolicitudVisitasyReporteVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;
import com.proquifa.net.negocio.catalogos.CatalogoClientesService;
import com.proquifa.net.negocio.ventas.requisicion.RequisicionService;
import com.proquifa.net.negocio.ventas.visitas.SolicitudVisitaService;
import com.proquifa.net.negocio.ventas.visitas.VisitaClienteService;
import com.proquifa.net.persistencia.catalogos.CatalogoClientesDAO;
import com.proquifa.net.persistencia.comun.ClienteDAO;
import com.proquifa.net.persistencia.comun.ContactoDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.NivelIngresoDAO;
import com.proquifa.net.persistencia.ventas.CotizacionDAO;
import com.proquifa.net.persistencia.ventas.requisicion.RequisicionDAO;
import com.proquifa.net.persistencia.ventas.visitas.VisitaClienteDAO;

@Service("visitaClienteService")
public class VisitaClienteServiceImpl implements VisitaClienteService{

	@Autowired
	VisitaClienteDAO visitaClienteDAO;
	
	@Autowired
	CatalogoClientesService catalogoClientesService;
	
	//@Autowired
	SolicitudVisitaService solicitudVisitaService;
	
	@Autowired
	RequisicionDAO requisicionDAO;
	
	@Autowired
	NivelIngresoDAO nivelIngresoDAO;
	
	@Autowired
	CatalogoClientesDAO catalogoClienteDAO;
	
	@Autowired
	FolioDAO folioDAO;
	
	@Autowired
	CotizacionDAO cotizacionDAO;
	
	@Autowired
	ContactoDAO contactoDAO;
	
	@Autowired
	EmpleadoDAO empleadoDAO;
	
	//@Autowired
RequisicionService requisicionService;
	
	@Autowired
	ClienteDAO clienteDAO;
	
	final Logger log = LoggerFactory.getLogger(VisitaClienteServiceImpl.class);
	
	
	
	public VisitaClienteServiceImpl(VisitaClienteDAO visitaClienteDAO){
		this.visitaClienteDAO = visitaClienteDAO;
	}
	public VisitaClienteServiceImpl(){
		
	}
	
	@Override
	public List<String> pendientesVisitaCliente(Long usuario)
			throws ProquifaNetException {
		List<String> pendientes = new ArrayList<String>();	
		pendientes.add("atenderPoolVisita/" + visitaClienteDAO.consultaContadorPendientesPoolVisitas(usuario));
		pendientes.add("planificarSprint/" + visitaClienteDAO.consultaContadorPendientesPlanificarSprint(usuario));
		pendientes.add("asignarSprint/" + visitaClienteDAO.consultaContadorPendientesAsignarSprint());
		pendientes.add("agendarVisita/" + visitaClienteDAO.consultarPendientesAgendarVisita(usuario));
		pendientes.add("numerpoSprint/" + visitaClienteDAO.consultarNumeroSprintAbierto());
		pendientes.add("reportarVisita/" + visitaClienteDAO.consultarPendientesReportarVisita(usuario));
		pendientes.add("juntaCierre/" + visitaClienteDAO.consultarPendientesJuntaCierre());
		pendientes.add("controlarPendientes/" + visitaClienteDAO.consultarContadorControlarPendientes(usuario));
		pendientes.add("controlarAcciones/" + visitaClienteDAO.consultarContadorControlarAcciones(usuario));
		return pendientes;
	}
	
	@Override
	public List<SolicitudVisita> consultarPoolVisitas(Long usuario)
			throws ProquifaNetException {
		List<SolicitudVisita> result=visitaClienteDAO.obtenerPoolVisitas(usuario);
		for (SolicitudVisita solicitudVisita : result) {
			String ruta = solicitudVisita.getCliente().getRuta();
			
			Cliente cliente = catalogoClientesService.obtenerClienteXId(solicitudVisita.getIdCliente());
			
			System.out.print("idCliente: " + cliente.getIdCliente());
			
			if(cliente.getIdCliente() != null)
			{
				List<Direccion> direccionesC = new ArrayList<Direccion>();
				
				direccionesC = catalogoClientesService.obtenerDireccionesDeVisitaXidCliente(cliente.getIdCliente());	
				
				if(direccionesC != null && direccionesC.size() > 0)
				{
					cliente.setDireccionVisita(direccionesC.get(0));
				}
			}
			cliente.setRuta(ruta);
			solicitudVisita.setCliente(cliente);
		}
		
		return result;
	}

	@Override
	public Boolean agruparSolicitudesVisita(List<SolicitudVisita> solicitudes,Boolean visitaExtratemporal)
			throws ProquifaNetException {
		Long id;
		if(solicitudes.size() > 0 ){
			com.proquifa.net.modelo.comun.util.Funcion funcion = new com.proquifa.net.modelo.comun.util.Funcion();
			
			Long diferenciaMayor = 0L;
			int totalDocumentos = 0;
			int crm = 0, total = 0;
			for (SolicitudVisita solicitudVisita : solicitudes) {
				Long diferencia = funcion.calcularDiferenciaDeDias(solicitudVisita.getFechaDeseadaRealizacion(), new Date());
				if(diferencia < diferenciaMayor){
					diferenciaMayor = diferencia;
				}
				
				if(solicitudVisita.getTipoSolicitud().compareTo("crm")==0)
					crm++;
				
				totalDocumentos += solicitudVisita.getNumDocumentos();
				total++;
			}
			
			solicitudes.get(0).setNumDocumentos(totalDocumentos);
			
			if(crm > 0 && crm == total){
				//CRM
				if(visitaExtratemporal)
					solicitudes.get(0).setTipoVisita("Mixta");
				else if(!visitaExtratemporal)
					solicitudes.get(0).setTipoVisita("CRM");
				
			}else if(crm > 0 && crm != total){
				solicitudes.get(0).setTipoVisita("Mixta");
			}else{
				//Programada, No programada
				if(visitaExtratemporal)
					solicitudes.get(0).setTipoVisita("No Programada");
				else
					solicitudes.get(0).setTipoVisita("Programada");
			}
			
			if(!visitaExtratemporal)
				id = visitaClienteDAO.registrarAgrupacionSolicitudVisitas(solicitudes.get(0));
			else{
				Sprint sprint = visitaClienteDAO.getSprintEnCurso();
				id = visitaClienteDAO.registrarAgrupacionExtratemporal(solicitudes.get(0),sprint.getIdSprint());
			}
			
			
			if(id > 0){
				visitaClienteDAO.actualizarVisitaClienteASolicitud(solicitudes, id);
				return true;		
			}else
				return false;
			
		}else
			return false;
		
	}
	
	@SuppressWarnings("unused")
	@Override
	public Long agruparSolicitudesVisitaCopia(List<SolicitudVisita> solicitudes)
			throws ProquifaNetException {
		Long id;
		int contadorCRM = 0, contadorSolicitud = 0;
		String tipo = "";
		if(solicitudes.size() > 0 ){
			Funcion funcion = new Funcion();
			
			Long diferenciaMayor = 0L;
			int totalDocumentos = 0;
			for (SolicitudVisita solicitudVisita : solicitudes) {
				Long diferencia = funcion.calcularDiferenciaDeDias(solicitudVisita.getFechaDeseadaRealizacion(), new Date());
				if(diferencia < diferenciaMayor){
					diferenciaMayor = diferencia;
				}
				
				totalDocumentos += solicitudVisita.getNumDocumentos();
			}
			
			solicitudes.get(0).setNumDocumentos(totalDocumentos);
			
			id = visitaClienteDAO.registrarAgrupacionSolicitudVisitas(solicitudes.get(0));
			
			if(id > 0){
				visitaClienteDAO.actualizarVisitaClienteASolicitud(solicitudes, id);
				return id;		
			}else
				return 0L;
			
		}else
			return 0L;
		
	}

	@Override
	public List<VisitaCliente> obtenerTotalesVisitaPorCliente(Long usuario)
			throws ProquifaNetException {
		
		List<VisitaCliente> result = visitaClienteDAO.obtenerVisitasClienteAgrupadas(usuario);
		return result;
	}

	@Override
	public List<VisitaCliente> obtenerVisitasClientePorUsuario(Long usuario)
			throws ProquifaNetException {
		List<VisitaCliente> result = visitaClienteDAO.consultarVisitasClientePorUsuario(usuario);
		for (VisitaCliente visitaCliente : result) {

			visitaCliente.setSolicitudesVisita(visitaClienteDAO.consultarSolicitudesVisitaPorVisitaCliente(visitaCliente.getIdVisitaCliente()));
		}
		
		return result;
	}

	@Override
	public List<Sprint> obtenerUltimosSprintAbiertos()
			throws ProquifaNetException {
		
		List<Sprint> sprints = visitaClienteDAO.consultarUltimosSprintsAbiertos();
		return sprints;
	}

	@Override
	public Boolean actualizarVisitaClienteGuardadasPlanificar(Long usuario,List<VisitaCliente> visita1,List<VisitaCliente> visita2,List<VisitaCliente> visita3
			,List<VisitaCliente> visita4,List<VisitaCliente> visita5)
			throws ProquifaNetException {
		String condicion = "";
		Long idSprint = 0L;
		Boolean exito;
		
		visitaClienteDAO.limpiarVisitasGuardadasPlanificar(usuario);
		
		condicion = formarCondicionGuardar(visita1);
		if(visita1.size() > 0) 
			idSprint = visita1.get(0).getIdSprint();
		exito = visitaClienteDAO.updateVisitaClienteGuardadasPlanificar(usuario, idSprint, condicion);
		
		condicion = formarCondicionGuardar(visita2);
		if(visita2.size() > 0) 
			idSprint = visita2.get(0).getIdSprint();
		exito = visitaClienteDAO.updateVisitaClienteGuardadasPlanificar(usuario, idSprint, condicion);
		
		condicion = formarCondicionGuardar(visita3);
		if(visita3.size() > 0) 
			idSprint = visita3.get(0).getIdSprint();
		exito = visitaClienteDAO.updateVisitaClienteGuardadasPlanificar(usuario, idSprint, condicion);
		
		condicion = formarCondicionGuardar(visita4);
		if(visita4.size() > 0) 
			idSprint = visita4.get(0).getIdSprint();
		exito = visitaClienteDAO.updateVisitaClienteGuardadasPlanificar(usuario, idSprint, condicion);
		
		condicion = formarCondicionGuardar(visita5);
		if(visita5.size() > 0) 
			idSprint = visita5.get(0).getIdSprint();
		exito = visitaClienteDAO.updateVisitaClienteGuardadasPlanificar(usuario, idSprint, condicion);
		
		return exito;
	}
	private String formarCondicionGuardar(List<VisitaCliente> visita) {
		String condicion = "";
		for (int j = 0; j < visita.size(); j++) {
			if(j+1 == visita.size())
				condicion += " PK_Formulario = "+visita.get(j).getIdVisitaCliente();
			else
				condicion += " PK_Formulario = "+visita.get(j).getIdVisitaCliente()+" OR";
		}
		return condicion;
	}

	@Override
	public Boolean planificarSprintVisitaCliente(Long usuario, Long idSprint,
			List<VisitaCliente> visitas) throws ProquifaNetException {
		String condicion = "";
		Boolean exito;
		
		for (int j = 0; j < visitas.size(); j++) {
			if(j+1 == visitas.size())
				condicion += " PK_Formulario = "+visitas.get(j).getIdVisitaCliente();
			else
				condicion += " PK_Formulario = "+visitas.get(j).getIdVisitaCliente()+" OR";
		}
		
		exito = visitaClienteDAO.updatePlanificarSprintEV(usuario, idSprint, condicion);
		
		return exito;
	}

	@Override
	public List<VisitaCliente> consultarVisitasParaAsignarSprint()
			throws ProquifaNetException {
		List<VisitaCliente> visitaClientes = visitaClienteDAO.consultasVisitasClienteDeCoordinador();
		for (VisitaCliente visitaCliente : visitaClientes) {
//			visitaCliente.setTipo("solicitud");
			visitaCliente.setSolicitudesVisita(visitaClienteDAO.consultarSolicitudesVisitaPorVisitaCliente(visitaCliente.getIdVisitaCliente()));
		}
		return visitaClientes;
	}

	@Override
	public Boolean guardarAsignarSprint(List<VisitaCliente> visita,Boolean asignar,List<VisitaCliente> visitasEliminadas)
			throws ProquifaNetException {
		String condicionValor = "",condicionCredito = "",condicionLlaves = "",condicionLlavesEliminadas = "";
		String condicionAsignar = "Etapa = 'AgendarVisita', Estado = 'Asignado' ";
		boolean actualizarSprint;
		Long idSprint = visita.get(0).getIdSprint();
		Boolean exito;
		for (VisitaCliente visitaCliente : visita) {
			condicionValor += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then "+ visitaCliente.getValor() +" \n"; 
			condicionCredito += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then "+ visitaCliente.getCredito() +" \n";
			condicionLlaves += visitaCliente.getIdVisitaCliente()+",";
		}
		if(condicionLlaves.length() > 0)
			condicionLlaves = condicionLlaves.substring(0, condicionLlaves.length()-1);
		
		
		for (VisitaCliente visitaCliente : visitasEliminadas) {
			condicionLlavesEliminadas += visitaCliente.getIdVisitaCliente()+",";
		}
		if(condicionLlavesEliminadas.length() > 0){
			condicionLlavesEliminadas = condicionLlavesEliminadas.substring(0, condicionLlavesEliminadas.length()-1);
			visitaClienteDAO.limpiarVisitasSinPlanificar(condicionLlavesEliminadas);
		}
		
		
		if(asignar){
			exito = visitaClienteDAO.asignarVisitasSprintEV(condicionValor, condicionCredito, condicionLlaves, condicionAsignar);
			actualizarSprint = VerificarSprint(idSprint);
			if(actualizarSprint)
			{
				visitaClienteDAO.actualizarSprintEnCurso(idSprint);
			}
		}
		else{
			exito = visitaClienteDAO.asignarVisitasSprintEV(condicionValor, condicionCredito, condicionLlaves,"");
		}
		
		return exito;
	}
	
	@Override
	public boolean VerificarSprint(Long idSprint)
			throws ProquifaNetException {
		try{
			
			if(idSprint != 0)
			{
				 Long numVisitas = visitaClienteDAO.obtenerPartidasPorSprint(idSprint);
				 if (numVisitas > 0)
					 return true;
				 else
					 return false;
			}
			else{
				return false;
			}
			
			}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	

	@Override
	public List<VisitaCliente> obtenerVisitasAsignadasPorEV(Long usuario)
			throws ProquifaNetException {
		try{
			log.info("IDUSUARIO->"+ usuario);
			
			 List<VisitaCliente> visitaClientes = visitaClienteDAO.consultarVisitasAsignadasPorEV(usuario);
			for (VisitaCliente visitaCliente : visitaClientes) {
				Cliente cliente = visitaCliente.getCliente();
				visitaCliente.setCliente(catalogoClientesService.obtenerClienteXId(visitaCliente.getIdCliente()));
				if (cliente.getDireccion().getIdDireccion() != null && cliente.getDireccion().getIdDireccion() > 0)
					visitaCliente.getCliente().setDireccion(cliente.getDireccion());
				visitaCliente.setSolicitudesVisita(visitaClienteDAO.consultarSolicitudesVisitaPorVisitaCliente(visitaCliente.getIdVisitaCliente()));
			}
			return visitaClientes;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
		
	}

	@Override
	public Boolean guardarVisitasAgendadas(List<VisitaCliente> visita,
			Long usuarios) throws ProquifaNetException {
		
		String conidionesLlave = "",condicionFecha = "",condicionHoraInicio = "",condicionHoraFin = "";
		Boolean exito = false;
		for (VisitaCliente visitaCliente : visita) {
			SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
			String horaInicio = localDateFormat.format(visitaCliente.getHoraVisitaInicio());
			String horaFin = localDateFormat.format(visitaCliente.getHoraVisitaFin());
			
			SimpleDateFormat fechaFormato = new SimpleDateFormat("yyyy-MM-dd");
			String fechaVisita = fechaFormato.format(visitaCliente.getFechaVisita());

			condicionFecha += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then '"+ fechaVisita +"' \n"; 
			condicionHoraInicio += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then '"+ horaInicio  +"' \n";
			condicionHoraFin += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then '"+ horaFin  +"' \n";
			conidionesLlave += visitaCliente.getIdVisitaCliente()+",";
		}
		if(conidionesLlave.length() > 0)
			conidionesLlave = conidionesLlave.substring(0, conidionesLlave.length()-1);

		if(visita.size() > 0)
			exito = visitaClienteDAO.asignarHorarioVisitas(conidionesLlave, condicionFecha, condicionHoraInicio, condicionHoraFin);

		return exito;
	}

	@Override
	public String registrarFechaCheckIn(VisitaCliente visita, Long usuario)
			throws ProquifaNetException {
		return visitaClienteDAO.guardarChecadoVisita(visita.getIdVisitaCliente(), usuario).toString();
	}

	@Override
	public List<VisitaCliente> obtenerVisitasEjecutadas(Long usuario)
			throws ProquifaNetException {
		List<VisitaCliente> result = visitaClienteDAO.obtenerVisitasConCheckIn(usuario);
		for (VisitaCliente visitaCliente : result) {
			visitaCliente.setSolicitudesVisita(visitaClienteDAO.consultarSolicitudesVisitaPorVisitaCliente(visitaCliente.getIdVisitaCliente()));
		}
		return result;
//		return visitaClienteDAO.obtenerVisitasConCheckIn(usuario);
	}
	
	@Override
	public List<VisitaCliente> obtenerVisitasEjecutadasRealizadas(Long usuario)
			throws ProquifaNetException {
		return visitaClienteDAO.obtenerVisitasRealizadas(usuario);
//		return visitaClienteDAO.obtenerVisitasConCheckIn(usuario,"AND VC.VisitaRealizada = 1");
	}
	
	@SuppressWarnings("unused")
	public Boolean insertarDocumentacionDeVisita(List<DocumentoAdjunto> documentacion, Long idVisita, String Reporte, List<String> Hallazgos, List<String> Acciones)
			throws ProquifaNetException {
		try {
			Funcion funcion = new Funcion();
			String rutasDocumentos="";
			Boolean exito = false;

			for (DocumentoAdjunto documento : documentacion) {

				if (documento.getArchivo() != null && documento.getArchivo().length > 0) {

					String dirreccion = funcion.obtenerRutaServidor("reportesvisitaclientes", idVisita +""); 
					String direccionDocumento = dirreccion + documento.getNombre() +"."+ documento.getExtension();

					File direccionF = new File(dirreccion);

					if (!direccionF.exists()) {
						direccionF.mkdir();
					}
					rutasDocumentos += documento.getNombre() +"."+ documento.getExtension()+",";
					OutputStream out = new FileOutputStream(direccionDocumento);

					out.write(documento.getArchivo());
					out.close();

					exito = visitaClienteDAO.insertarDocumentacionVisita(documento, idVisita);

				}
			}

			if(Hallazgos != null && Hallazgos.size() > 0){

				for (String hallazgo : Hallazgos) {

					if (hallazgo != "") {

						exito = visitaClienteDAO.insertarHallazgosYAccionesReporteVisita(hallazgo,"Hallazgo", idVisita);

					}
				}

			}


			if(Acciones != null && Acciones.size() > 0){

				for (String accion : Acciones) {

					if (accion != "") {
						exito = visitaClienteDAO.insertarHallazgosYAccionesReporteVisita(accion,"Acción", idVisita);

					}
				}

			}
			
			

//			if(Reporte != "")
//			{
//				visitaClienteDAO.updateEstadoDeVisitaCliente(idVisita,Reporte);
//				exito = true; 
//			}
			return exito;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}
	
	
	

	@Override
	public List<VisitaCliente> obtenerTotalesReportarVisita(Long usuario)
			throws ProquifaNetException {

		List<VisitaCliente> result = visitaClienteDAO.obtenerVisitasClienteAgrupadasReportarVisita(usuario);
		return result;
	}

	@Override
	public List<VisitaCliente> obtenerTodasVisitasAsignadas()
			throws ProquifaNetException {
		List<VisitaCliente> visitaClientes = visitaClienteDAO.getVisitasAsignadas();
		for (VisitaCliente visitaCliente : visitaClientes) {
			visitaCliente.setSolicitudesVisita(visitaClienteDAO.consultarSolicitudesVisitaPorVisitaCliente(visitaCliente.getIdVisitaCliente()));
			visitaCliente.setDocumentosReporte(visitaClienteDAO.obtenerDocumentosReportes(visitaCliente.getIdVisitaCliente()));
		}
		return visitaClientes;
	}

	@Override
	public Boolean registrarDailyScrum(DailyScrum daily)
			throws ProquifaNetException {
		try{
			//SI YA EXISTEN REGISTROS
			List<AsistenciaDailyScrum> asistencia = visitaClienteDAO.getAsistenciaAactual();

			if(asistencia.size()>0){
				for (AsistenciaDailyScrum asistenciaDailyScrum : asistencia) {
					for (Empleado empleado : daily.getEmpleados()) {
						if(empleado.getIdEmpleado() == asistenciaDailyScrum.getIdEmpleado()){
							visitaClienteDAO.actualizarCodigoAsistencia(empleado.getCodigoAsistencia(), asistenciaDailyScrum.getIdAsistenciaDailyScrum());
						}
					}
				}
			}else{
				if(daily.getIdDailyScrum() > 0){
					String queryAsistenciaValues = "";
					for (Empleado empleado : daily.getEmpleados()) {
						queryAsistenciaValues += "("+empleado.getIdEmpleado()+","+daily.getIdDailyScrum()+","+empleado.getCodigoAsistencia()+"),";
					}
					if(queryAsistenciaValues.length() > 0)
						queryAsistenciaValues = queryAsistenciaValues.substring(0, queryAsistenciaValues.length()-1);
					
					visitaClienteDAO.insertarRegistroAsistencia(queryAsistenciaValues);
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public int revisarBloqueoDailyScrum() throws ProquifaNetException {
		return visitaClienteDAO.revisarDailyRealizado();
	}

	@Override
	public boolean actualizarContactoSolicitud(SolicitudVisita solicitud)
			throws ProquifaNetException {
		return visitaClienteDAO.updateContactoSolicitudVisita(solicitud);
	}

	
	@SuppressWarnings("unused")
	@Override
	public ResumenSolicitudVisitasyReporteVisita obtenerInformacionVisitaxidCliente(Long idCliente)
			throws ProquifaNetException {

		List<VisitaCliente> result =  new ArrayList<VisitaCliente>();	
		List<ReportarVisita> listaRepotar =  new ArrayList<ReportarVisita>();	
		List<VisitaCliente> listaVisitas =  new ArrayList<VisitaCliente>();
		
		ResumenSolicitudVisitasyReporteVisita resumenSolicitud = new ResumenSolicitudVisitasyReporteVisita();
		
		List<Integer> visitas = visitaClienteDAO.obtenerUltimasVisitasDeClientePoridCliente(idCliente);
		
		for (int i = 0; i < visitas.size(); i++) {
			VisitaCliente  vCliente = new VisitaCliente();
			Long idContacto = visitaClienteDAO.obtenerIdContactoDeVisita(visitas.get(i).longValue());
			listaRepotar.add(obtenerReportarVisita(visitas.get(i), 1 ));
			vCliente = visitaClienteDAO.obtenerReporteDeVisita(visitas.get(i));
			vCliente.setContacto(contactoDAO.obtenerPorId(idContacto)); 
			vCliente.setCliente(this.clienteDAO.obtenerClienteXId(idCliente));
			listaVisitas.add(vCliente);
		}
		
		
		resumenSolicitud.setReporteVisita(listaRepotar);
		resumenSolicitud.setVisitas(listaVisitas);
		


     return resumenSolicitud;
	}
	
	
	@Override
	public List<VisitaCliente> obtenerTotalesAgendarVisita(Long usuario)
			throws ProquifaNetException {
            List<VisitaCliente> result = visitaClienteDAO.obtenerVisitasAgrupadasAgendarVisita(usuario);
		return result;
	}
	
	
	@Override
	public boolean desagendarVisitaCliente(Long idVisita)
			throws ProquifaNetException {
		return visitaClienteDAO.reagendarVisita(idVisita);
	}

	@Override
	public Sprint obtenerSprintEnCurso() throws ProquifaNetException {
		return visitaClienteDAO.getSprintEnCurso();
	}

	@Override
	public int registrarRealizacionVisita(VisitaCliente visita)
			throws ProquifaNetException {
		return visitaClienteDAO.actualizarRealizacionVisita(visita);
	}

	@Override
	public List<VisitaCliente> obtenerTodasVisitasCierre()
			throws ProquifaNetException {
		
		List<VisitaCliente> result = visitaClienteDAO.obtenerTodaVisitasParaCierre();
		for (VisitaCliente visitaCliente : result) {
			visitaCliente.setSolicitudesVisita(visitaClienteDAO.consultarSolicitudesVisitaPorVisitaCliente(visitaCliente.getIdVisitaCliente()));
			visitaCliente.setDocumentosReporte(visitaClienteDAO.obtenerDocumentosReportes(visitaCliente.getIdVisitaCliente()));
			
			visitaCliente.setHallazgos(visitaClienteDAO.obtenerHallazgoAccionesDeVisitaCliente(visitaCliente.getIdVisitaCliente(), "Hallazgo"));
			visitaCliente.setAcciones(visitaClienteDAO.obtenerHallazgoAccionesDeVisitaCliente(visitaCliente.getIdVisitaCliente(), "Acción"));
		}
		
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void verificarSprintEnCurso() throws ProquifaNetException {
		Date fecha = new Date();

		List<Sprint> sprints = visitaClienteDAO.consultarUltimosSprintsAbiertos();

		System.out.print("Dia: "+fecha.getDate()+" Mes: "+fecha.getMonth());

		if(sprints.size() > 0){
			Sprint sprint = sprints.get(0);
			visitaClienteDAO.actualizarSprintEnCurso(sprint.getIdSprint());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void verificarCierreSprint() throws ProquifaNetException {
		Date fecha = new Date();
		Sprint sprint = visitaClienteDAO.obtenerSprintEnCurso();

		if(!sprint.equals(null)){
			System.out.print("Dia: "+sprint.getFechaFin().getDate()+" Mes: "+sprint.getFechaFin().getMonth());
			if(fecha.getMonth() == sprint.getFechaFin().getMonth() &&
					fecha.getDate() == sprint.getFechaFin().getDate()){
				
				int pendientes = visitaClienteDAO.obtenerNumeroVisitasPendientes();
				if(pendientes <= 0){
					log.info("Actualizo");
					visitaClienteDAO.cerrarSprintEnCurso(sprint.getIdSprint());
				}
			}
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public List<Empleado> iniciarDailyScrum() throws ProquifaNetException {
		List<Empleado> vendedores = visitaClienteDAO.getVendedoresConVisitas();
		
		Sprint sprint = visitaClienteDAO.getSprintEnCurso();
		DailyScrum daily = visitaClienteDAO.getDailylScrumActual();
		
		Funcion funcion = new Funcion();
		Correo correo = new Correo();
		
		if(daily.getNumeroDaily() == 0){
			//INSERTAR DAILY
			DailyScrum dailyNuevo = calcularNumeroDaily(sprint.getFechaInicio(), sprint.getFechaFin());
			dailyNuevo.setSprint(sprint);
			
			int idDailyScrum = visitaClienteDAO.insertarDailyScrum(dailyNuevo);
			
			dailyNuevo.setIdDailyScrum(idDailyScrum);
			daily = dailyNuevo;
		}
		
		for (Empleado empleado : vendedores) {
			
			int codigoGSeguridad = funcion.generarCodigo4Digitos();
			empleado.setCodigoAsistencia(Integer.toString(codigoGSeguridad));
			
			String result;
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("dd-MM-yy hh:mm:ss");
			result = formatter.format(new Date());
			
			correo.setOrigen("serviciosti");
			correo.setCuerpoCorreo("Vendedor:"+empleado.getUsuario()+"\n\nFolio para asistencia a Daily Scrum: "+codigoGSeguridad+
					"\n\nFecha y hora: "+result);
			correo.setAsunto("Folio Asistencia");
//			correo.setCorreo("samuel.diaz@ryndem.mx");
			correo.setCorreo(empleado.getUsuario()+"@proquifa.net");
			correo.setCcorreo("oscar.cardona@ryndem.mx");
			correo.setMedio("Correo electrónico");
			correo.setTipo("correonormal");
			correo.setArchivoAdjunto("");
			
			boolean exito = funcion.enviarCorreo(correo);
		}
		
		daily.setEmpleados(vendedores);
		this.registrarDailyScrum(daily);
		
		return vendedores;
	}
	
	@SuppressWarnings("deprecation")
	public DailyScrum calcularNumeroDaily(Date fechaInicio,Date fechaFin) {
		int numeroDaily = 0;
		int contador = 0;
		Date temp;
		Date fechaActual = new Date();
		int diaInicio = fechaInicio.getDate();
		int diaFin = fechaFin.getDate();
		
		if(fechaInicio.getMonth() == fechaFin.getMonth()){
			for (int i = diaInicio; i <= diaFin; i++) 
			{
				temp = new Date(fechaInicio.getYear(),fechaInicio.getMonth(),i);
				if(temp.getDay() == 1 || temp.getDay() == 2 || temp.getDay() == 3 || temp.getDay() == 4 || temp.getDay() == 5){
					contador++;
					if(fechaActual.getDate() == temp.getDate() && fechaActual.getMonth() == temp.getMonth()
					&& fechaActual.getYear() == temp.getYear()){
						numeroDaily = contador;
					}
				}
			}
		}else{
			temp = new Date(fechaInicio.getYear(),fechaInicio.getMonth(),0);
			log.info("FechaInicio.anio:"+fechaInicio.getYear()+" FechaInicio.mes:"+fechaInicio.getMonth());
			int finMes = getNumeroDias(fechaInicio.getYear(),fechaInicio.getMonth());
			
			for (int j = diaInicio; j <= finMes; j++) 
			{
				Date temp1 = new Date(fechaInicio.getYear(),fechaInicio.getMonth(),j);
				if(temp1.getDate() == 1 || temp1.getDate() == 2 || temp1.getDate() == 3 || temp1.getDate() == 4 || temp1.getDate() == 5){
					contador++;
					if(fechaActual.getDate() == temp1.getDate() && fechaActual.getMonth() == temp1.getMonth() 
							&& fechaActual.getYear() == temp1.getYear()){
						numeroDaily = contador;
					}
				}
			}
			for (int k = 1; k <= diaFin; k++) 
			{
				Date temp2 = new Date(fechaFin.getYear(),fechaFin.getMonth(),k);
				if(temp2.getDate() == 1 || temp2.getDate() == 2 || temp2.getDate() == 3 || temp2.getDate() == 4 || temp2.getDate() == 5){
					contador++;
					if(fechaActual.getDate() == temp2.getDate() && fechaActual.getMonth() == temp2.getMonth()
						&& fechaActual.getYear() == temp2.getYear()){
						numeroDaily = contador;
					}
				}
			}
		}
		DailyScrum obj = new DailyScrum();
		obj.setNumeroDaily(numeroDaily);
//		obj.totaDailys = contador;
		return obj;
	}
	
	@SuppressWarnings("deprecation")
	private int getNumeroDias(int year,int month){
		Date d=new Date(year, month, 0);
		log.info("Numero Dias:"+d.getDate());
		return d.getDate();
	}
	
	@Override
	public List<VisitaCliente> obtenerVisitasParaEjecucion(Long usuario)
			throws ProquifaNetException {
		return visitaClienteDAO.getVisitaConCheckIn(usuario);
	}
	
	@Override
	public Boolean acutalizarObservacionesDailyScrum(DailyScrum daily)
			throws ProquifaNetException {
		DailyScrum dailyAcutal = visitaClienteDAO.getDailylScrumActual();
		visitaClienteDAO.actualizarObservacionesDailyScrum(daily.getObservacion(), dailyAcutal.getIdDailyScrum());
		
		//PARA LA ASISTENCIA
		for (Empleado empleado : daily.getEmpleados()) {
			visitaClienteDAO.actualizarAsistenciaDailyScrum((empleado.isAsistencia()?1:0),empleado.getIdEmpleado(),dailyAcutal.getIdDailyScrum());
		}
		
		return true;
	}
	@Override
	public int cerrarSprintPorEV(Long idSprint,Long idEmpleado,String observaciones,List<VisitaCliente> visitas, Integer calificacionEV)
			throws ProquifaNetException {
		
		String ids = "";
		List<VisitaCliente> visitasPendientes = new ArrayList<VisitaCliente>();
		List<VisitaCliente> visitasExtemporaneas = new ArrayList<VisitaCliente>();
		
		for (VisitaCliente visitaCliente : visitas) {
			
			ids += visitaCliente.getIdVisitaCliente()+",";
			
			//SACAR LAS QUE TIENEN ESTADO PENDIENTE O NO FINALIZADO
			if(visitaCliente.getEstado().trim().compareTo("Pendiente")==0 ||
					visitaCliente.getEstado().trim().compareTo("No finalizada")==0){
				visitasPendientes.add(visitaCliente);
			}
			
			if(visitaCliente.isVisitaExtemporanea())
				visitasExtemporaneas.add(visitaCliente);
		}
		
		if(ids.length() > 0)
			ids = ids.substring(0, ids.length()-1);
		
		boolean exito1 = visitaClienteDAO.actualizarFechaCierreVisitaCliente(ids,idEmpleado);
		
		boolean exito2 = visitaClienteDAO.insertarCierreSprint(idSprint, idEmpleado, observaciones, calificacionEV);
		
		actualizarVisitasExtemporaneas(visitasExtemporaneas);
		
		copiarVisitasPendientes(visitasPendientes);
		
		if(exito1 && exito2)
			return 2;
		else
			return 1;
	}
	
	private void copiarVisitasPendientes(List<VisitaCliente> visitasPendientes) throws ProquifaNetException {
		Funcion funcion = new Funcion();
		for (VisitaCliente visitaCliente : visitasPendientes) {
			
			for (SolicitudVisita solicitudes : visitaCliente.getSolicitudesVisita()) {
				int totalDocumentos = 0;
				
				for (DocumentoSolicitudVisita documentos : solicitudes.getDocumentos()) {
					String rutaArchivo = funcion.obtenerRutaServidor("solicitudvisitadocumento", solicitudes.getIdSolicitudVisita().toString());
					File arcihvo = new File(rutaArchivo+documentos.getNombre()+".pdf");
					byte[] archivoByte = null;
					try {
						archivoByte = funcion.getBytesFromFile(arcihvo);
						documentos.setArchivo(archivoByte);
					} catch (IOException e) {
						e.printStackTrace();
					}
					totalDocumentos++;
				}
				solicitudes.setNumDocumentos(totalDocumentos);
				int idSolicitud = this.solicitudVisitaService.generarSolicitudVisita(solicitudes,solicitudes.getTipoSolicitud());
				solicitudes.setFechaDeseadaRealizacion(visitaCliente.getFechaEstimadaVisita());
				solicitudes.setContacto(visitaCliente.getContacto());
				solicitudes.setCliente(visitaCliente.getCliente());
				solicitudes.setIdSolicitudVisita( Long.parseLong(""+idSolicitud) );
			}
			
			Long nuevaVisita = this.agruparSolicitudesVisitaCopia(visitaCliente.getSolicitudesVisita());
			if(visitaCliente.getIdSprintAntiguo() == null || visitaCliente.getIdSprintAntiguo() == 0)
				visitaClienteDAO.actualizarSprintAntiguo(visitaCliente.getSprint().getIdSprint(),nuevaVisita);
			else
				visitaClienteDAO.actualizarSprintAntiguo(visitaCliente.getIdSprintAntiguo(),nuevaVisita);
		}
		
	}
	
	private Boolean actualizarVisitasExtemporaneas(List<VisitaCliente> visitasExtemporaneas) throws ProquifaNetException {
		
		String condicionValor = "",condicionCredito = "",condicionLlaves = "";
		Boolean exito;
		for (VisitaCliente visitaCliente : visitasExtemporaneas) {
			condicionValor += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then "+ visitaCliente.getValor() +" \n"; 
			condicionCredito += "WHEN PK_Formulario = "+ visitaCliente.getIdVisitaCliente() +" then "+ visitaCliente.getCredito() +" \n";
			condicionLlaves += visitaCliente.getIdVisitaCliente()+",";
		}
		if(condicionLlaves.length() > 0)
			condicionLlaves = condicionLlaves.substring(0, condicionLlaves.length()-1);
		
		exito = visitaClienteDAO.asignarVisitasSprintEV(condicionValor, condicionCredito, condicionLlaves, "");
		
		return exito;
	}
	
	@Override
	public List<Sprint> obtenerUltimosSprintAbiertosNoPlanificados(Long idUsuario)
			throws ProquifaNetException {
		
		List<Sprint> sprints = visitaClienteDAO.consultarUltimosSprintsAbiertosSinPlanificar(idUsuario);
		return sprints;
	}
	
	@Override
	public List<Sprint> obtenerSprintSinCerrar()
			throws ProquifaNetException {
		
		List<Sprint> sprints = visitaClienteDAO.ConsultaDeSprintSinCerrar();
		return sprints;
	}

	
	@Override
	public boolean actualizarLatitudyLongitud(Direccion direccion) throws ProquifaNetException {
		try {
			return visitaClienteDAO.updateLongitudyLatitud(direccion);
		} catch (Exception e) {
		}
		return false;
	}
	
	@Override
	public List <VisitaCliente> obtenerTodasVisitasPorSprint(Long usuario) throws ProquifaNetException {
		try {
			
			List<VisitaCliente> visitasCliente = new ArrayList<VisitaCliente>();
			
			visitasCliente = visitaClienteDAO.obtenerTodasVisitasPorSprint(usuario);
			
			if(visitasCliente.size()>0)
			{
				for(VisitaCliente visita:visitasCliente)
				{
					List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
					if (visita.getCliente().getIdCliente() != null && visita.getCliente().getIdCliente() > 0) {
						Cliente cliente = catalogoClienteDAO.obtenerClienteXId(visita.getCliente().getIdCliente(), ni);
						
						if(cliente.getIdCliente() > 0)
							visita.setCliente(cliente);
					}
				}
			}
		
			
			return visitasCliente;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List <VisitaCliente> obtenerDatosSeccionReporte(int visita) throws ProquifaNetException {
		try {
			return visitaClienteDAO.obtenerDatosSeccionReporte(visita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List <HallazgosAcciones> obtenerDatosSeccionHallazgos(int visita) throws ProquifaNetException {
		try {
			return visitaClienteDAO.obtenerDatosSeccionHallazgos(visita);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Boolean guardaObservaciones(List<HallazgosAcciones> listaHallazgos) throws ProquifaNetException {
		try {
			
			for (HallazgosAcciones hallazgosAcciones : listaHallazgos) {
				visitaClienteDAO.guardaObservaciones(hallazgosAcciones);
			}
			return true;
		} catch (Exception e) {
		}
		return false;
	}
	
	@Override
	public ReportarVisita obtenerReportarVisita(Integer idVisita, Integer generada) throws ProquifaNetException {
		try {
			ReportarVisita repVisita = new ReportarVisita();
			
			repVisita = visitaClienteDAO.obtenerReportarVisita(idVisita);
			repVisita.setNotas(visitaClienteDAO.obtenerNotasDeVisita(idVisita.longValue()));
			repVisita.setCalificacion(visitaClienteDAO.obtenerCalificacion(idVisita.longValue()));
			repVisita.setCotizacion(obtenerListaDeCotizacionesPoridVisitaCliente(idVisita.longValue(), generada));
			
			return repVisita;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<ObtenerVisitas> obtenerAsuntos(Integer idVisita, Integer idContacto) throws ProquifaNetException {
		try {
			return visitaClienteDAO.obtenerAsuntos(idVisita, idContacto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unused")
	@Override
	public List<ObtenerPrecios> obtenerPrecios(Integer idProducto, Integer idCliente, Integer idProveedor, String tipo, String subTipo, String control) throws ProquifaNetException {
		try {
			return visitaClienteDAO.obtenerPrecios(idProducto, idCliente, idProveedor, tipo, subTipo, control);				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	@Transactional
	public boolean guardarTodasSeccionesReportarVisita(ReportarVisita reporte, Integer idVisita, boolean finalizarVisita, boolean eliminarCotizaciones, VisitaCliente visitaCli) throws ProquifaNetException {
		try {
			String docto = "";
			String documentos = "";
			if (reporte.getRequerimientos() != null) {
				visitaClienteDAO.updateRequerimientos(reporte.getRequerimientos());
			}
			if (reporte.getPendientes() != null) {
				visitaClienteDAO.updatePendientes(reporte.getPendientes(), idVisita);
			}
			if (reporte.getHallazgos() != null) {
				visitaClienteDAO.eliminaHallazgos(idVisita);
				visitaClienteDAO.updateHallazgos(reporte.getHallazgos(), idVisita);
				for (HallazgosAcciones hallazgos : reporte.getHallazgos()) {
					if (hallazgos.getDocumento() != null && hallazgos.getDocumento().getArchivo() != null) {
						Funcion function = new Funcion();
						Funcion.subirArchivo(hallazgos.getDocumento().getArchivo(), hallazgos.getIdHallazgoAccion().toString() + ".pdf" , function.obtenerRutaServidor("hallazgos", ""));
					}
				}
			}			
			
			if (reporte.getRequisicion() != null){
				log.info("DoctoR Requisición:" + reporte.getRequisicion().getIdDoctoR());
				if(reporte.getRequisicion().getIdDoctoR() == null || reporte.getRequisicion().getIdDoctoR() <= 0){
					int idDoctoR = this.requisicionDAO.insertaDoctoR(reporte.getRequisicion());
					reporte.getRequisicion().setIdDoctoR(idDoctoR);
					requisicionDAO.insertarRequisicionMovil(reporte.getRequisicion());
				}
				visitaClienteDAO.eliminaRequisiciones(idVisita);
				visitaClienteDAO.ReinsertaRequisiciones(reporte.getRequisicion());
				
				
			}
			
			
			if(eliminarCotizaciones)
			{
				eliminarCotizacionesPoridVisita(idVisita.longValue());
			}
						
			
			if(reporte.getCotizacion() != null && reporte.getCotizacion().size() > 0)
			{
				guardarGenerarCotizacion(reporte.getCotizacion(),idVisita.longValue(),reporte.getCorreo(), visitaCli.getContacto().getIdContacto().intValue());
			}
			
			if(finalizarVisita)
			{
				String cadena = "";
				String doc = "";
				Map<String, String> mp =  new HashMap<String, String>();
			  if(reporte.getRequerimientos() != null && reporte.getRequerimientos().size() > 0)
			  {
				  for (SolicitudVisita reque : reporte.getRequerimientos()) {
					  
					  if(mp.get(reque.getUsuarioSolicitante()) != null){
						  cadena = "";
						  cadena = mp.get(reque.getUsuarioSolicitante());
						  cadena = cadena + "\n"+ "\n" + "Requerimiento: " + Funcion.quitaApostrofes(reque.getMotivo()) + "\n" + "Respuesta: " + Funcion.quitaApostrofes(reque.getRespuesta()); 
						  mp.put(reque.getUsuarioSolicitante(),cadena);
					  }
					  else {
						  cadena = "";
						  
						  cadena = "Requerimiento: " + Funcion.quitaApostrofes(reque.getMotivo()) + "\n" + "Respuesta: " + Funcion.quitaApostrofes(reque.getRespuesta()); 
							mp.put(reque.getUsuarioSolicitante(),cadena);
						}
					  
				}
				  
				  for (SolicitudVisita re : reporte.getRequerimientos()) {

					  if(re.getDocumento() != null)
					  {
						  if(doc.length() > 0)
						  {
							  doc = doc + "," + re.getIdSolicitudVisita();
						  }
						  else{
							  doc = re.getIdSolicitudVisita().toString();
						  }
					  }



				  }
				 
				  
					for (Entry<String, String> mapaR : mp.entrySet()) {
						String key = mapaR.getKey();
						String contacto = key + "@proquifa.net";
						String mensaje = mp.get(key);
						
						
						Funcion funcion = new Funcion();
						Correo correoA = new Correo();
						correoA.setAsunto("Respuesta a requerimiento");
						correoA.setArchivoAdjunto(doc);
						correoA.setRemitente(key);
						correoA.setCorreo(contacto);
						correoA.setTipo("requerimientos");
						correoA.setFacturadaPor("");
						correoA.setOrigen("ventas");
						correoA.setCuerpoCorreo(mensaje);
						funcion.enviarCorreo(correoA);
						
						
						log.info("==================================================");
					
					}
				  
			  }
			  
               if(reporte.getRequisicion() != null && reporte.getRequisicion().getPartidaRequisicion() != null && reporte.getRequisicion().getPartidaRequisicion().size() > 0 )
               {
            	   log.info("------------------------------------------");
            	   log.info("------------------------------------------");
            	   log.info("Partida" + reporte.getRequisicion().getPartidaRequisicion().size());
            	   log.info("------------------------------------------");
            	   log.info("------------------------------------------");
            	   docto = requisicionService.actualizaRequisicionMovil(reporte.getRequisicion(), reporte.getRequisicion().getPartidaRequisicion(), visitaCli.getContacto());
               }
			  
			  
			  
			  visitaClienteDAO.updateEstadoDeVisitaCliente(idVisita.longValue(),reporte.getReporte(),reporte.getCalificacion());
				
			}
			
			log.info("fin");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
	}
	
	
	@Override
	@Transactional
	public Long guardarGenerarCotizacion(List<Cotizacion> cotizaciones,Long idVisitaCliente,Correo correo, Integer idContacto) throws ProquifaNetException {
		try {
			SimpleDateFormat formatterTime = new SimpleDateFormat("hh:mm");
			Date date = new Date();
			double actIva = 0.16;
			double subtotal = 0;
			double ivaTotal = 0;
			double totalCotizacion = 0;
			float importePartida = 0;
			Long numCotizacion = 0L;
			Funcion funcion = new Funcion();
			List<String> listaDefolios = new ArrayList<String>();
			String cadenaDeFolios = "";
			String direccionCliente = "";
			int contador = 0;
			
//			log.info("Se eliminan todas las cotizacion y partidas de la visita :" + idVisitaCliente);
			eliminarCotizacionesPoridVisita(idVisitaCliente);
			
			if(cotizaciones.size() > 0)
			{
				for (Cotizacion cotizacion : cotizaciones) {
					
					subtotal = 0;
					ivaTotal = 0;
					totalCotizacion = 0;
					Empleado empleado = new Empleado();
					String esacCliente = "";
					
					esacCliente = clienteDAO.obtenerEsacDelCliente(cotizacion.getCliente().getIdCliente());
//					log.info("idVisitaCliente:" + cotizacion.getIdVisita());
					    numCotizacion = numCotizacion + 1 ;
						Cotizacion coti = new Cotizacion();
						//Crea el folio
						Folio folio = folioDAO.obtenerFolioPorConcepto("Cotizas",true);
						cotizacion.setFolioCotizacion(folio.getFolioCompleto());
						cotizacion.setHEntrada(formatterTime.format(date));
						cotizacion.setMEntrada("C");
						cotizacion.setMSalida("C");
						cotizacion.setDoctoR(null);
						cotizacion.setFuersaSistema(false);
						cotizacion.setNumCotizacion(cotizacion.getNumCotizacion());
						
						DoctoR docto = cotizacionDAO.obtenerDatosContactoParaDoctoR(idContacto);
						
						docto.setPart(0);
						docto.setFecha(date);
						docto.setRpor(esacCliente);
						docto.setCliente(cotizacion.getCliente());
						docto.setManejo("Entrante");
						docto.setOrigen("Cliente");
						docto.setMedio("Mail");
						docto.setDocto("Requisición");
						docto.setObserva("");
						docto.setFproceso(date);
						docto.setForigen(0);
						docto.setFhorigen(date);
						docto.setIngreso(cotizacion.getCotizo());
						docto.setHijo(false);

						docto = cotizacionDAO.saveDoctoR(docto);
						cotizacion.setDoctoR(docto.getFolio());
//						log.info("+++++++++++++++++++++++GUARDA COTICACION++++++++++++++++++++++++++++++++++++++");
						cotizacion = cotizacionDAO.saveCotizacion(cotizacion,true);
						cotizacionDAO.actualizarDoctosRidCotizacion(cotizacion.getFolioCotizacion(), docto.getFolio());
//						log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

						for (int i = 0; i < cotizacion.getPartidas().size(); i++) {
							
							

//							log.info("PRECIO PRODUCTO:" + cotizacion.getPartidas().get(i).getProducto().getCosto());
							cotizacion.getPartidas().get(i).setPrecio(cotizacion.getPartidas().get(i).getProducto().getCostoAux().floatValue());
							String indice = String.valueOf(i);
							cotizacion.getPartidas().get(i).setPartida(Long.valueOf(indice));
							cotizacion.getPartidas().get(i).setCotizacion(cotizacion.getFolioCotizacion());
							cotizacion.getPartidas().get(i).setClasificacion("A");
							cotizacion.getPartidas().get(i).setIdPCotizaOrigen(0);
							cotizacion.getPartidas().get(i).setPrecioInvestigacion(0F);
							log.info("idCOTIZACION:" + cotizacion.getIdCotizacion());
							cotizacion.getPartidas().get(i).setIdCotizacion(cotizacion.getIdCotizacion().intValue());
							
							double iva = 0;
//							log.info("Aplica iva:" + cotizacion.isGravaIva());
							
							if(cotizacion.isGravaIva())
							{
								iva = cotizacion.getPartidas().get(i).getProducto().getCostoAux() * actIva;

							}

							cotizacion.getPartidas().get(i).setIva(iva);
							cotizacion.getPartidas().get(i).setFolio(0L);

						}
						
//						log.info("GUARDAR PARTIDAS");
						cotizacionDAO.savePartidasCotizacion(cotizacion.getPartidas());
//						log.info("TERMINO");

					
				
						if(cotizacion.isGenerada())
						{
							cotizacion.setHSalida(formatterTime.format(date));
							cotizacion.setGenerada(true);
							cotizacion.setNumCotizacion(cotizacion.getNumCotizacion());
							
							for (int i = 0; i < cotizacion.getPartidas().size(); i++) {
								
								importePartida = 0;
								
//								log.info("PRECIO PRODUCTO:" + cotizacion.getPartidas().get(i).getProducto().getCostoAux());
//								log.info("Nota partida:" + cotizacion.getPartidas().get(i).getNota());
								cotizacion.getPartidas().get(i).setPrecio(cotizacion.getPartidas().get(i).getProducto().getCostoAux().floatValue());
								String indice = String.valueOf(i);
								cotizacion.getPartidas().get(i).setPartida(Long.valueOf(indice));
								cotizacion.getPartidas().get(i).setCotizacion(cotizacion.getFolioCotizacion());
								cotizacion.getPartidas().get(i).setClasificacion("A");
								cotizacion.getPartidas().get(i).setPrecioInvestigacion(0F);
								double iva = 0;
								if(cotizacion.isGravaIva())
								{
									iva = cotizacion.getPartidas().get(i).getProducto().getCostoAux() * actIva;

								}
                                subtotal = subtotal + (double) Math.round(cotizacion.getPartidas().get(i).getProducto().getCostoAux() * cotizacion.getPartidas().get(i).getProducto().getCantPiezas());
								cotizacion.getPartidas().get(i).setIva(iva);
								cotizacion.getPartidas().get(i).setFolio(99L);
								cotizacion.getPartidas().get(i).setFechaGeneracion(date);
								cotizacion.getPartidas().get(i).setDestino("Enviada"); 
								cotizacion.getPartidas().get(i).setHoraEnvio(date);
								cotizacion.getPartidas().get(i).setNum(i+1);
								cotizacion.getPartidas().get(i).setDescripcion(cotizacion.getPartidas().get(i).getProducto().getConcepto());
								cotizacion.getPartidas().get(i).setPrecioFormato(funcion.formatoMoneda(cotizacion.getPartidas().get(i).getProducto().getCostoAux()));
								cotizacion.getPartidas().get(i).setPrecio(cotizacion.getPartidas().get(i).getProducto().getCostoAux().floatValue());
								
								importePartida = cotizacion.getPartidas().get(i).getProducto().getCostoAux().floatValue() * cotizacion.getPartidas().get(i).getCantidad();
								cotizacion.getPartidas().get(i).setImporteFormato(funcion.formatoMoneda((double)importePartida));
								cotizacion.getPartidas().get(i).setImporte(importePartida);
								empleado = this.empleadoDAO.obtenerEmpleadoPorUsuario(cotizacion.getCotizo());

							}
							cotizacionDAO.actualizarCotizacion(cotizacion);
//							log.info("GUARDAR PARTIDAS");
							cotizacionDAO.savePartidasCotizacion(cotizacion.getPartidas());
//							log.info("TERMINO");
							
							
							String rutaPlantilla = funcion.obtenerRutaServidor("plantillaCotizacion", "");
							String rutaCotizacion = funcion.obtenerRutaServidor("cotizacion", "");
							String rutaDocto = funcion.obtenerRutaServidor("doctos", "");
							
							direccionCliente = cotizacion.getCliente().getCalleFiscal()+", "+cotizacion.getCliente().getDelegacionFiscal()+" " + cotizacion.getCliente().getEstado()+ ", " + cotizacion.getCliente().getPais() + " C.P. " + cotizacion.getCliente().getCodigoPostalFiscal();							
							InputStream inputStream = new FileInputStream(rutaPlantilla + "Cotizacion.jrxml");
							//JasperDesign jasperDesign = JRXmlLoader.load(inputStream);
						//	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
							Map<String, Object> parametros = new HashMap<String, Object>();
							parametros.put("nombre", cotizacion.getCliente().getNombre());
							parametros.put("cotizacion", cotizacion.getFolioCotizacion());
							parametros.put("nombreE", empleado.getNombre());
							parametros.put("contacto", cotizacion.getContactos().getNombre());
							if(cotizacion.getContactos().getFax() != null)
								parametros.put("fax", cotizacion.getContactos().getFax());
							else
								parametros.put("fax","N/D");	
							parametros.put("telefono", cotizacion.getContactos().getTelefono());
							parametros.put("email", cotizacion.getContactos().getEMail());
							parametros.put("cpago", cotizacion.getCliente().getCondicionesPago());
							if(direccionCliente != null)
							parametros.put("direccion", direccionCliente);
							parametros.put("moneda", funcion.cambiarTextoMoneda(cotizacion.getImoneda()));
							
							parametros.put("empresa", cotizacion.getFactura());
							parametros.put("firma", Funcion.RUTA_FIRMASDIGITALES + cotizacion.getCotizo() + ".jpg");
							parametros.put("subTotal", funcion.formatoMoneda(subtotal));
							if(cotizacion.isGravaIva())
							{
								ivaTotal = actIva * subtotal;
								totalCotizacion = ivaTotal + subtotal;
							}
							else {
								ivaTotal = 0;
								totalCotizacion = subtotal;
							}
							parametros.put("iva", funcion.formatoMoneda(ivaTotal));
							parametros.put("total", funcion.formatoMoneda(totalCotizacion));
							Calendar calendar = Calendar.getInstance(); 
							calendar.setTime(new Date()); 
							calendar.add(Calendar.MONTH, 1);
							parametros.put("vigencia", calendar.getTime());
							parametros.put("listData", cotizacion.getPartidas());
							parametros.put("ruta", rutaPlantilla);
							
							
							log.info("******************************" );
							log.info("******************************" );
							log.info("RUTA" + rutaPlantilla );
							log.info("******************************" );
							log.info("******************************" );

							List<PartidaCotizacion> list = new ArrayList<PartidaCotizacion>();
							list.add(new PartidaCotizacion());
						//	JRDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list, true);
							
							File file = new File(rutaCotizacion);
							if (!file.exists())
								file.mkdirs();
							
						//	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, beanCollectionDataSource);
						//	JasperExportManager.exportReportToPdfFile(jasperPrint, rutaCotizacion + cotizacion.getFolioCotizacion() + ".pdf");
							
						//	JasperExportManager.exportReportToPdfFile(jasperPrint, rutaDocto + docto.getFolio() + ".pdf");
							
							file = new File(rutaCotizacion + cotizacion.getFolioCotizacion() + ".pdf");
							listaDefolios.add(cotizacion.getFolioCotizacion());
							if(cadenaDeFolios.length() > 0)
							{
								cadenaDeFolios = cadenaDeFolios + ","+cotizacion.getFolioCotizacion();
							}
							else{
								cadenaDeFolios = cotizacion.getFolioCotizacion();
							}
					
//							levanta pendiente de confirmación
							visitaClienteDAO.insertarPendienteConfirmacion(cotizacion.getFolioCotizacion(), cotizacion.getCotizo(), cotizacion.getContacto(), cotizacion.getIdCotizacion(),"Mail");
							
						}
					
				}
				
				if(listaDefolios.size() > 0 && correo != null)
				{
					Correo correoA = new Correo();
					correoA.setAsunto("Cotizacion");
					correo.setRemitente("ventas");
					correoA.setCorreo(correo.getCorreo());
					log.info("cccorreo:" + correo.getCcorreo());
					correoA.setCcorreo(correo.getCcorreo());
					
				//	log.info("copia correo:" + correo.getCopiaCorreo());

					correoA.setArchivoAdjunto(cadenaDeFolios);
					
					correoA.setTipo("cotizaciones");
					correoA.setFacturadaPor("");
					correoA.setOrigen("ventas");
					correoA.setCuerpoCorreo(correo.getCuerpoCorreo());
					funcion.enviarCorreo(correoA);
				}


			}
		    
			return 1L;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1L;
		}
	}
	

	@Transactional
	public List<Cotizacion> obtenerListaDeCotizacionesPoridVisitaCliente(Long idVisitaCliente, Integer generada) throws ProquifaNetException {
		try {
			List<Cotizacion> listCO= new ArrayList<Cotizacion>();
			
			listCO = visitaClienteDAO.cotizacionesLista(idVisitaCliente, generada);
			
			if(listCO != null && listCO.size() > 0)
			{
				List<NivelIngreso> ni = nivelIngresoDAO.findLimitesNivelIngreso();
				
				for (Cotizacion cotizacion : listCO) {
					cotizacion.setCliente(catalogoClienteDAO.obtenerClienteXId(cotizacion.getCliente().getIdCliente(), ni));
					cotizacion.setContactos(contactoDAO.obtenerPorId(cotizacion.getIdContacto()));
					List<PartidaCotizacion> part= new ArrayList<PartidaCotizacion>();
					log.info("Folio: " + cotizacion.getFolioCotizacion());
					part = obtenerListaPartidasCotizacion(cotizacion.getIdCotizacion());
					cotizacion.setPartidas(part);
					cotizacion.setNumPartidas(cotizacion.getPartidas().size());
				}
			}
		    
			return listCO;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}
	
	
	@Transactional
	public List<PartidaCotizacion> obtenerListaPartidasCotizacion(Long idCotizacion) throws ProquifaNetException {
		try {
			List<PartidaCotizacion> listPart= new ArrayList<PartidaCotizacion>();
			
			listPart = visitaClienteDAO.obtenerListaDePartidasCO(idCotizacion);
		    
			return listPart;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return null;
		}
	}
	
	
	@Override
	@Transactional
	public boolean eliminarCotizacionesPoridVisita(Long idVisitaCliente) throws ProquifaNetException {
		try {
			String folios = "";
			List<Cotizacion> listCO= new ArrayList<Cotizacion>();
			 listCO = obtenerListaDeCotizacionesPoridVisitaCliente(idVisitaCliente, 0);
			 
			 if(listCO != null && listCO.size() > 0)
			 {
				 for (Cotizacion cotizacion : listCO) {
					 cotizacionDAO.eliminarPartidasCotizacionPoridCotizacion(cotizacion.getIdCotizacion());
					
				}
				 
				 folios = cotizacionDAO.obtenerFoliosDoctoR(idVisitaCliente);
				 cotizacionDAO.eliminarCotizacionPorIdVisita(idVisitaCliente);
				 cotizacionDAO.EliminarDoctors(folios);
				
			 }
			
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return false;
	}
	
	
	@Override
	@Transactional()
	public Long validarContrasena(Empleado empleado, String cliente, String razones, String tipo, String solicitante) throws ProquifaNetException {
		try{
			boolean respuesta;
			Long idAutorizacion = 0L;
			
			respuesta =  empleadoDAO.validarPassUser(empleado);
			if(respuesta){
				idAutorizacion = visitaClienteDAO.insertarAutorizacion(empleado.getUsuario(), solicitante, tipo, razones, cliente);
			}
			
		  return idAutorizacion;


		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return -1L;
		}
	}
	

		
}
