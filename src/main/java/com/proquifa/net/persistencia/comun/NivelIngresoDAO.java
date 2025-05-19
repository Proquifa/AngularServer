package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface NivelIngresoDAO {
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<NivelIngreso> findLimitesNivelIngreso() throws ProquifaNetException;

	public boolean crearNivelIngreso(NivelIngreso nivelIngreso) throws ProquifaNetException;
	
	public boolean updateNivelIngreso(NivelIngreso nivelIngreso) throws ProquifaNetException;
}
