package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.Subproceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface SubprocesoDAO {

	/**
	 * Metodo que obtiene una lista de subprocesos a partir del id de un proceso
	 * @param idProceso
	 * @return
	 */
	List<Subproceso> obtenerSubprocesosXIdProceso(Long idProceso) throws ProquifaNetException;
	
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Subproceso> obtenerSubprocesos() throws ProquifaNetException;
	/**
	 * Metodo que inserta en la base de datos un subproceso
	 * @param subproceso
	 * @return
	 */
	Long insertarSubProceso(Subproceso subproceso) throws ProquifaNetException;
	/**
	 * Metodo que actualiza un subproceso a partir de otro
	 * @param nuevo
	 * @return
	 */
	Boolean actualizarSubProceso(Subproceso nuevo) throws ProquifaNetException;
	/**
	 * Metodo que valida la existencia de un subproceso
	 * @param target
	 * @return
	 */
	Boolean existeSubProceso(Subproceso target) throws ProquifaNetException;
	/**
	 * Metodo para eliminar un subproceso
	 * @param subproceso
	 * @return
	 */
	Boolean borrarSubProceso(Subproceso subproceso) throws ProquifaNetException;
	/**
	 * Metodo que obtiene un subproceso a traves de un idSubproceso
	 * @param idSubproceso
	 * @return
	 * @throws ProquifaNetException
	 */
	Subproceso obtenerSubProcesoXId(Long idSubproceso) throws ProquifaNetException;
	/***
	 * Obtiene el id del Subproceso por medio del nombre
	 * @param nombreProceso
	 * @return
	 * @throws ProquifaNetException
	 */
	Long obtenerIdSubProceso(String nombreProceso) throws ProquifaNetException;

	/***
	 * 
	 * @param user
	 * @return
	 * @throws ProquifaNetException
	 */
	Long getIdSubprocesoXEmpleado (String user) throws ProquifaNetException;
}
