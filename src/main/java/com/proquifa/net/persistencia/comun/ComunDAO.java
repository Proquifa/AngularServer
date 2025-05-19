/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Sistema;

/**
 * @author ymendez
 *
 */
/**
 * @author ymendez
 *
 */
public interface ComunDAO {

	
	public Integer obtenerUltimoRegistroInsertado(String tabla) throws ProquifaNetException;

	/***
	 * 
	 * @param nombre
	 * @return
	 * @throws ProquifaNetException
	 */
	public Sistema obtenerVersionSistema(String nombre) throws ProquifaNetException;
}
