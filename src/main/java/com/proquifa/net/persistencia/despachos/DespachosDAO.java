/**
 * 
 */
package com.proquifa.net.persistencia.despachos;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface DespachosDAO {

	/**
	 * @throws ProquifaNetException
	 */
	public void guardarHistorialPrioridades() throws ProquifaNetException;
}
