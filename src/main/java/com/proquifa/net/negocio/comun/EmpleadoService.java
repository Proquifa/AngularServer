package com.proquifa.net.negocio.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.ExepcionEnvio;
import com.proquifa.net.modelo.comun.Modificacion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


public interface EmpleadoService {
	
	public Boolean validarusuario(Empleado empleado);
	/**
	 * Determina el responsable de un subproceso
	 * @param idSubproceso
	 * @return
	 * @throws ProquifaNetException
	 */
	
	public Empleado obtenerResponsableDeSubProceso(Long idSubproceso) throws ProquifaNetException;
	/**
	 * Metodo que recupera un empleado mediante su usuario
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */	
	public Empleado obtenerEmpleadoXUsuario(String usuario) throws ProquifaNetException ;
	
	/**
	 * Metodo que recupera los empleados habilitados
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empleado> obtenerEmpleadosHabilitados() throws ProquifaNetException;
	/**
	 * Metodo que recupera a los empleados de acuerdo a su tipo
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empleado> obtenerEmpleadosPorTipo(String tipo) throws ProquifaNetException;
	/**
	 * Metodo que valida la conteasena de un empleado
	 * @param empleado
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean validarContrasena(Empleado empleado) throws ProquifaNetException;

	/**
	 * Metodo que recupera un empleado mediante su id
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Empleado obtenerEmpleadoXId(Long idUsuario) throws ProquifaNetException;
	/**
	 * Metodo que determina el gerente del empleado con el id correspondiente
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Empleado determinarGerenteXIdUsuario(Long idUsuario) throws ProquifaNetException;
	/**
	 * Metodo q recupera a los empleados del SGC
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empleado> obtenerEmpleadosSGC() throws ProquifaNetException;

	/**
	 * Obtiene un esac aleactorio
	 * Servicio temporal en cuanto se determine la asignacion de esac para nuevos clientes
	 * @return Empleado empleado
	 * @throws ProquifaNetException
	 */
	Empleado obtenerEsacParaNuevoCliente() throws ProquifaNetException;
	/**
	 * Obtiene un esac aleactorio
	 * Servicio temporal en cuanto se determine la asignacion de esac para nuevos clientes
	 * @return Empleado empleado
	 * @throws ProquifaNetException
	 */
	String obtenerFuncionEmpleado(long idEmpleado) throws ProquifaNetException;
	
	/**
	 * guarda datos para realizar el seguimiento de una modificacion
	 * @param nuevaModificacion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean  guardarModificacion(Modificacion nuevaModificacion )   throws ProquifaNetException;
	
	/**
	 * obtiene el comprador responsable para el proveedor.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	String obtenerCompradorporProveedor (Long idProveedor) throws ProquifaNetException;
	
	/**
	 * obtiene el comprador responsable para el proveedor.
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean enviarCorreoAvisoExepcionVista (ExepcionEnvio exepcionEnvio) throws ProquifaNetException;
	/**
	 * Obtiene un empleado dependiendo de la funcion
	 * @param funcionId
	 * @return
	 */
	public Empleado obtenerEmpleadoPorFuncion(long funcionId) throws ProquifaNetException; 
	/**
	 * 
	 * @param tipoAutorizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> obtenerContraseniasTipoAutorizacion(String tipoAutorizacion) throws ProquifaNetException;
	/**
	 * 
	 * @param tipoAutorizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> obtenerContraseniasTipoAutorizacionValidarCertificadosNoDisponibles(String tipoAutorizacion) throws ProquifaNetException;
	/**
	 * 
	 * @param tipoAutorizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> obtenerContraseniasTipoModificarEmpresa(String tipoAutorizacion) throws ProquifaNetException;
	
	public void cambiarContraseniasUsuarios() throws ProquifaNetException;
	
	/**
	 * 
	 * @param tipoAutorizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empleado> obtenerMensajeros() throws ProquifaNetException;
}
