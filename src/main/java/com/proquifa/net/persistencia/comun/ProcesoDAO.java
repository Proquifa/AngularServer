package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.Proceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface ProcesoDAO {
	

	/**
	 * Metodo que obtiene todos los registros de la tabla Proceso
	 * @return
	 */
	List<Proceso> obtenerProcesos();
	/**
	 * Metodo que obtiene todos los procesos por medio de un id de proceso
	 * @param idProceso
	 * @return
	 */
	Proceso obtenerProcesoXId(Long idProceso);
	/**
	 * Metodoque actualiza un registro en la tabla proceso
	 * @param proceso
	 * @return
	 */
	Boolean actualizarProceso(Proceso proceso);
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	String getResponsableDeProceso(Long idEmpleado) throws ProquifaNetException;

}
