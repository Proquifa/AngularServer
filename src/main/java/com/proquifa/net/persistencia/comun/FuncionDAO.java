package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Funcion;

public interface FuncionDAO {
	/**
	 * Metodo que obtiene todas las funciones de la base de datos
	 * @return
	 */
	List<Funcion> obtenerFunciones();
	/**
	 * Metodo que actualiza un registro funcion en la base de datos
	 * @param nueva
	 * @return
	 */
	Boolean actualizarFuncion(Funcion nueva);
	/**
	 * Metodo que obtienen un registro Funcion mediante un id de funcion
	 * @param idFuncion
	 * @return
	 */
	Funcion obtenerFuncionPorId(Long idFuncion);
	/**
	 * Metodo que obtiene todas las funciones asociadas a un subproceso
	 * @param idSubproceso
	 * @return
	 */
	List<Funcion> obtenerFuncionesSubproceso(Long idSubproceso);
	/**
	 * Metodo que recupera los empleados de la base de datos con el id de la funcion
	 * @param idFuncion
	 * @return
	 */
	List<Empleado> obtenerEmpleadosXIdFuncion(Long idFuncion);
	/**
	 * Metodo que recupera las funciones a traves del tipo
	 * @param nivel
	 * @return
	 */
	List<Funcion> obtenerFuncionesXNivel(String nivel);
	
	/***
	 * Metodo que recupera el empleados de la base de datos con el id de la funcion
	 * @param idFuncion
	 * @return
	 */
	String getEmpleadoXIdFuncion(Long idFuncion);
	/***
	 * Metodo que recupera el empleado habilitado (con fase >0) de la base de datos con el id de la funcion
	 * @param idFuncion
	 * @return
	 */
	String getEmpleadoXIdFuncionHabilitado(Long idFuncion);
	/***
	 * 
	 * @param nombreFuncion
	 * @return
	 */
	Long getidFuncionXNombre(String nombreFuncion);
	/**
	 * Metodo que obtiene la funcion asociada a un empleado por medio de su Id
	 * @param idEmpleado
	 * @return Funcion funcion
	 * @throws ProquifaNetException
	 */
	Funcion getFuncionPorIdEmpleado(Long idEmpleado);
	/***
	 * Metodo que obtiene el nombre del esac junior responsable en base a su carga de clientes nuevos y si esta en linea al hacer dicha solicitud.
	 * @return
	 */
	String getEsacJRXCargaClientes();

}
