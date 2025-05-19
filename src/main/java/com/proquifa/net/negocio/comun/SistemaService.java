package com.proquifa.net.negocio.comun;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Sistema;

public interface SistemaService {

	/***
	 * 
	 * @param nombre
	 * @return
	 * @throws ProquifaNetException
	 */
	public Sistema obtenerVersionSistema(String nombre) throws ProquifaNetException;

}
