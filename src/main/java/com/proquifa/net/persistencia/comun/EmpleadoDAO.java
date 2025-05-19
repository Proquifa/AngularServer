package com.proquifa.net.persistencia.comun;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Modificacion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

import java.util.List;

public interface EmpleadoDAO {
	/***
	 * Recupera el responsable del Subproceso
	 * @param idSubproceso
	 * @return
	 */	
	Empleado obtenerResponsableDeSubProceso(Long idSubproceso);
	/**
	 * Recupera los empleados de acuerdo al nivel de la funcion que desempenan
	 * @param nivel
	 * @return
	 */
	
	Empleado obtenerGerenteXIdEmpleado(Long idEmpleado);
	/**
	 * Metodo que obtiene un empleado mediante su nombre de usuario
	 * @param usuario
	 * @return
	 */
	Empleado obtenerEmpleadoPorUsuario(String usuario);
	/***
	 * Regresa la lista de los roles asignados al Empledo
	 * @param usuario
	 * @return
	 */
	List<String> getRolesEmpleado(String usuario);

	/**
	 * Metodo que obtiene todos los empleados habilitados
	 * @return
	 */
	List<Empleado> obtenerEmpleadosHabilitados();
	/**
	 * Metodo que obtiene empleados mediante su tpo
	 * @param tipo
	 * @return
	 */
	List<Empleado> obtenerEmpleadosPorTipo(String tipo);
	/**
	 * Metodo que obtiene empleados mediante su id
	 * @param idEmpleado
	 * @return
	 */
	Empleado obtenerEmpleadoPorId(Long idEmpleado);
	/**
	 * Metodo que asocia una funcion con un empleado
	 * @param idEmpleado
	 * @param idFuncion
	 * @return
	 */
	Boolean actualizarEmpleado(Long idEmpleado, Long idFuncion);

	/**
	 * Recupera los empleados de acuerdo al nivel de la funcion que desempenan
	 * @param nivel
	 * @return
	 */
	List<Empleado> obtenerEmpleadosPorNivel(String nivel, Long idEmpleado);
	
	/**
	 * Recupera los empleados de acuerdo al nivel de la funcion que desempenan
	 * @param nivel
	 * @return
	 */
	List<Empleado> obtenerMensajeros();
	/**
	 * Recupera el Gerente encargado del SubProceso
	 * @param idSubproceso
	 * @return
	 */
	Empleado obtenerGerenteDeSubProceso(Long idSubproceso);

	/***
	 * validar contrasena
	 * @param usuario
	 * @param pass
	 * @return
	 */
	Boolean validarPassUser(Empleado user);

	/****
	 * 
	 * @param user
	 * @return
	 */
	String getGerenteOResponsableSubProceso(Long idSubproceso);
	/***
	 * Regresa una lista con todos los esac asignados a un responsable de grupo.
	 * @param idEsacMaster
	 * @return
	 */
	List<String> finEquipoESAC(Long idEsacMaster);
	
	/***
	 * @param idEmpleado
	 * @return
	 */
	String getNivelEmpleado(Long idEmpleado);
	
	/**
	 * Obtiene un esac aleactorio
	 * Servicio temporal en cuanto se determine la asignacion de esac para nuevos clientes
	 * @return Empleado empleado
	 * @throws ProquifaNetException
	 */
	Empleado getEsacParaNuevoCliente();
	
	/***
	 * 
	 * @param idEmpleado
	 * @return
	 */
	String getFuncionEmpleado(Long idEmpleado);
	/**
	 *  guarda datos para realizar el seguimiento de una modificacion
	 * @param nuevaModificacion
	 * @return
	 */
	Boolean guardarModificacion (Modificacion nuevaModificacion );

	
	/**
	 * obtiene los id de los subordinados de un esac master 
	 * @param idEsac
	 * @return
	 */
	List <Empleado> getsubordinadosEsacMaster (Long idEsac);
	/**
	 * obtiene el usuario responsable del proveedor 
	 * @param idProveedor
	 * @return
	 */
	String getCompradorporProveedor (Long idProveedor);
	/**
	 * 
	 * @param funcionId
	 * @return
	 * @throws ProquifaNetException
	 */
	public Empleado getEmpleadoPorFuncion(long funcionId) throws ProquifaNetException; 
	/**
	 * 
	 * @param usuario
	 * @return
	 */
	public int getIdEmpleadoPorUsuario(String usuario) throws ProquifaNetException;
	/**
	 * 
	 * @param id
	 * @return
	 * @throws ProquifaNetException
	 */
	public Empleado getEmpleadoPorId(long id) throws ProquifaNetException;
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public Empleado getEmpleadoPorRol(String rol) throws ProquifaNetException;
	/**
	 * 
	 * @param funcion
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> getContraseniasPorFuncion(String funcion) throws ProquifaNetException;
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public String getContraseniaAdministrador() throws ProquifaNetException;
	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> getContraseniasTipoAutorizacionValidarCertificadosNoDisponibles() throws ProquifaNetException;
	public List<String> getRolesEmpleadoInt(String usuario); 
	
	public Boolean updateContraseniaUsuario(Long clave,String pass) throws ProquifaNetException; 
	
	
	
	public Boolean validaUsuario(Empleado user) throws Exception;
}
