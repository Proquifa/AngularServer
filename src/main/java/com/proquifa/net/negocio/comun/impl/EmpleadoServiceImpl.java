package com.proquifa.net.negocio.comun.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.ExepcionEnvio;
import com.proquifa.net.modelo.comun.Funcion;
import com.proquifa.net.modelo.comun.Modificacion;
import com.proquifa.net.modelo.comun.Proceso;
import com.proquifa.net.modelo.comun.Subproceso;
import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.negocio.comun.EmpleadoService;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.ModuloDAO;
import com.proquifa.net.persistencia.comun.ProcesoDAO;
import com.proquifa.net.persistencia.comun.SubprocesoDAO;
import com.proquifa.net.persistencia.comun.ValorComboDAO;

@Service("empleadoService")
public class EmpleadoServiceImpl implements EmpleadoService {
	@Autowired
	EmpleadoDAO empleadoDAO;

	@Autowired
	private ModuloDAO moduloDAO;

	@Autowired
	private FuncionDAO funcionDAO;
	@Autowired
	private SubprocesoDAO subprocesoDAO;
	@Autowired
	private ProcesoDAO procesoDAO;
	@Autowired
	private ValorComboDAO valorComboDAO;

	com.proquifa.net.modelo.comun.util.Funcion funcionUtil;

	@Override
	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public Boolean validarusuario(Empleado empleado) {

		try {
			return empleadoDAO.validaUsuario(empleado);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public Empleado obtenerResponsableDeSubProceso(Long idSubproceso) throws ProquifaNetException {
		try {
			return empleadoDAO.obtenerResponsableDeSubProceso(idSubproceso);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	@Transactional(readOnly = true, transactionManager = "ds1TransactionManager")
	public Empleado obtenerEmpleadoXUsuario(String usuario) throws ProquifaNetException {
		Empleado gerente = null;
		Empleado empleado = this.empleadoDAO.obtenerEmpleadoPorUsuario(usuario);
		if (empleado.getNivelGeneral().equals("Directivo")) {
			gerente = this.empleadoDAO.obtenerEmpleadoPorUsuario(usuario);
			empleado.setEsGerente(true);
		} else {
			gerente = this.empleadoDAO.obtenerGerenteXIdEmpleado(empleado.getClave());
			if (gerente != null) {
				Long a = gerente.getClave();
				Long b = empleado.getClave();
				if (a.equals(b)) {
					empleado.setEsGerente(true);
				} else {
					empleado.setEsGerente(false);
				}
			} else {
				empleado.setEsGerente(false);

			}
		}

		empleado.setModulos(this.moduloDAO.obtenerModulosXIdEmpleado(empleado.getClave()));
		empleado.setRoles(this.empleadoDAO.getRolesEmpleado(empleado.getUsuario()));
		return empleado;
	}

	public List<Empleado> obtenerEmpleadosHabilitados() throws ProquifaNetException {
		return this.empleadoDAO.obtenerEmpleadosHabilitados();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see mx.com.proquifa.proquifanet.negocio.comun.EmpleadoService#
	 * obtenerEmpleadosPorTipo(java.lang.String)
	 */

	public List<Empleado> obtenerEmpleadosPorTipo(String tipo) throws ProquifaNetException {
		return this.empleadoDAO.obtenerEmpleadosPorTipo(tipo);
	}

	public Boolean validarContrasena(Empleado empleado) throws ProquifaNetException {
		return this.empleadoDAO.validarPassUser(empleado);
	}

	public Empleado obtenerEmpleadoXId(Long idUsuario) throws ProquifaNetException {
		try {
			Empleado e = this.empleadoDAO.obtenerEmpleadoPorId(idUsuario);
			Empleado gerente = this.empleadoDAO.obtenerGerenteXIdEmpleado(e.getClave());
			if (gerente != null) {
				Long a = gerente.getClave();
				Long b = e.getClave();

				if (a.equals(b)) {
					e.setEsGerente(true);
				} else {
					e.setEsGerente(false);
				}
			} else {
				e.setEsGerente(false);
			}
			e.setModulos(this.moduloDAO.obtenerModulosXIdEmpleado(idUsuario));
			e.setRoles(this.empleadoDAO.getRolesEmpleado(idUsuario.toString()));
			return e;
		} catch (Exception e) {

			return new Empleado();
		}
	}

	public Empleado obtenerEmpleadoPorFuncion(long funcionId) throws ProquifaNetException {
		try {
			return this.empleadoDAO.obtenerEmpleadoPorId(funcionId);
		} catch (Exception e) {

			return new Empleado();
		}
	}

	@Transactional
	public Empleado determinarGerenteXIdUsuario(Long idUsuario) throws ProquifaNetException {
		return this.empleadoDAO.obtenerGerenteXIdEmpleado(idUsuario);
	}

	@Transactional
	public List<Empleado> obtenerEmpleadosSGC() throws ProquifaNetException {
		List<Empleado> empleados = this.empleadoDAO.obtenerEmpleadosHabilitados();

		Funcion funcion = new Funcion();
		Subproceso subproceso = new Subproceso();
		Proceso proceso = new Proceso();
		Empleado responsable = null;
		for (Empleado e : empleados) {
			if (e.getIdFuncion() != 0) {

				funcion = this.funcionDAO.obtenerFuncionPorId(e.getIdFuncion());
				// log.info("--"+e.getIdEmpleado());
				// log.info("--"+e.getIdFuncion());
				// log.info("--"+e.getNivelGeneral());
				e.setNombreFuncion(funcion.getNombre());
				e.setNivelFuncion(funcion.getNivel());
				if (!e.getNivelGeneral().equals("Directivo")) {
					subproceso = this.subprocesoDAO.obtenerSubProcesoXId(funcion.getSubproceso());
					e.setNombreSubproceso(subproceso.getNombre());
					if (e.getNivelFuncion().equals("Operativo")) {
						responsable = this.empleadoDAO.obtenerGerenteXIdEmpleado(e.getIdEmpleado());
						if (responsable != null) {
							e.setResponsable(responsable.getUsuario());
						} else {
							e.setResponsable("ND");
						}
					} else {
						// if(e.getNivelFuncion().equals("Gerente"))/

						proceso = this.procesoDAO.obtenerProcesoXId(subproceso.getIdProceso());
						responsable = this.empleadoDAO.obtenerEmpleadoPorId(proceso.getIdEmpleado());
						e.setResponsable(responsable.getUsuario());
					}
				}
				ValorCombo valorCombo = this.valorComboDAO.obtenerValorCombo(e.getNivelFuncion(), "HorasHombre");
				e.setCostoHoraHombre(Long.parseLong(valorCombo.getValor()));
			} else {
				e.setNombreFuncion("ND");
				e.setNivelFuncion("ND");
				e.setNombreSubproceso("ND");
				e.setResponsable("ND");
				e.setCostoHoraHombre(0L);
			}
			// log.info(e.getIdEmpleado());
		}

		return empleados;
	}

	public Empleado obtenerEsacParaNuevoCliente() throws ProquifaNetException {
		return this.empleadoDAO.getEsacParaNuevoCliente();
	}

	public String obtenerFuncionEmpleado(long idEmpleado) throws ProquifaNetException {
		return this.empleadoDAO.getFuncionEmpleado(idEmpleado);
	}

	public Boolean guardarModificacion(Modificacion nuevaModificacion) throws ProquifaNetException {
		String cadena = "ProquifaNet -Servicio: " + nuevaModificacion.getServicio() + ", Metodo: "
				+ nuevaModificacion.getMetodo();
		nuevaModificacion.setModificaciones(cadena);
		return this.empleadoDAO.guardarModificacion(nuevaModificacion);

	}

	@Override
	public String obtenerCompradorporProveedor(Long idProveedor) throws ProquifaNetException {
		return this.empleadoDAO.getCompradorporProveedor(idProveedor);
	}

	@Override
	public Boolean enviarCorreoAvisoExepcionVista(ExepcionEnvio exepcionEnvio) throws ProquifaNetException {
		funcionUtil = new com.proquifa.net.modelo.comun.util.Funcion();
		return funcionUtil.enviarCorreoAvisoExepcionVista(exepcionEnvio);
	}

	@Override
	public List<String> obtenerContraseniasTipoAutorizacion(String tipoAutorizacion) throws ProquifaNetException {
		try {
			List<String> contrasenas = new ArrayList<String>();
			if (tipoAutorizacion.equals("ModificarDoctoRercibido")) {
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Gerente de Ventas"));
				String contraseniaAdmin = empleadoDAO.getContraseniaAdministrador();
				contrasenas.add(contraseniaAdmin);
			} else if (tipoAutorizacion.equals("ModificarPrecioProducto")) {
				String contraseniaAdmin = empleadoDAO.getContraseniaAdministrador();
				contrasenas.add(contraseniaAdmin);
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Director General"));
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Ejecutivo de Servicio a Clientes Master"));
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Gerente de Sistemas"));
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Representante de Dirección"));
			}
			return contrasenas;
		} catch (Exception e) {
			e.printStackTrace();
			new com.proquifa.net.modelo.comun.util.Funcion().enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	@Override
	public List<String> obtenerContraseniasTipoAutorizacionValidarCertificadosNoDisponibles(String tipoAutorizacion)
			throws ProquifaNetException {
		try {
			List<String> contrasenas = new ArrayList<String>();
			if (tipoAutorizacion.equals("ValidarCertificadosNoDisponibles")) {
				String contraseniaAdmin = empleadoDAO.getContraseniaAdministrador();
				contrasenas.add(contraseniaAdmin);
				contrasenas.addAll(empleadoDAO.getContraseniasTipoAutorizacionValidarCertificadosNoDisponibles());
			}
			return contrasenas;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	public void cambiarContraseniasUsuarios() throws ProquifaNetException {
		List<Empleado> empleados = this.obtenerEmpleadosHabilitados();
		for (Empleado empleado : empleados) {
//			if(empleado.getUsuario().compareTo("OCardona")==0){
			String passNuevo = getCadenaAlfanumAleatoria(6);
			empleadoDAO.updateContraseniaUsuario(empleado.getClave(), passNuevo);

			com.proquifa.net.modelo.comun.util.Funcion funcion = new com.proquifa.net.modelo.comun.util.Funcion();
			Correo correo = new Correo();

			String result;
			SimpleDateFormat formatter;
			formatter = new SimpleDateFormat("dd-MMM-yy hh:mm");
			result = formatter.format(new Date());
			String cuerpoCorreo = "";
			correo.setOrigen("serviciosti");
			correo.setAsunto("Cambio de contraseña para SAP y ProquifaNet");
			cuerpoCorreo = "Atención " + empleado.getNombre()
					+ ", se informa que a partir de este momento su nueva contraseña para los sistemas SAP y ProquifaNet será la siguiente:\n";
			cuerpoCorreo += "\nUsuario: " + empleado.getUsuario() + "\nNueva contraseña: " + passNuevo
					+ "\nFecha y hora: " + result;
			cuerpoCorreo += "\n\nATTE. TI ";
			cuerpoCorreo += "\n\nSaludos, ";
			correo.setCuerpoCorreo(cuerpoCorreo);
			correo.setCorreo(empleado.getUsuario() + "@proquifa.com.mx");
			correo.setCocorreo("oscar.cardona@ryndem.mx");
			correo.setArchivoAdjunto("");

			funcion.enviarCorreo(correo);
//			}

		}
	}

	public List<String> obtenerContraseniasTipoModificarEmpresa(String tipoAutorizacion) throws ProquifaNetException {
		try {
			List<String> contrasenas = new ArrayList<String>();
			if (tipoAutorizacion.equals("ModificarEmpresaCliente")) {
				String contraseniaAdmin = empleadoDAO.getContraseniaAdministrador();
				contrasenas.add(contraseniaAdmin);
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Director General"));
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Ejecutivo de Servicio a Clientes Master"));
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Gerente de Sistemas"));
				contrasenas.addAll(empleadoDAO.getContraseniasPorFuncion("Representante de Dirección"));
			}

			return contrasenas;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	String getCadenaAlfanumAleatoria(int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < longitud) {
			char c = (char) r.nextInt(255);
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z')) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
	}

	@Override
	public List<Empleado> obtenerMensajeros() throws ProquifaNetException {
		return empleadoDAO.obtenerMensajeros();
	}
}
