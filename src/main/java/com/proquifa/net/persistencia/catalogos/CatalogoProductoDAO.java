package com.proquifa.net.persistencia.catalogos;

import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


public interface CatalogoProductoDAO {
	/***
	 * Actualiza el campo TransitoMexico  
	 * @return
	 */
	boolean actualizarTransitoMexico(Producto producto) throws ProquifaNetException;
}
