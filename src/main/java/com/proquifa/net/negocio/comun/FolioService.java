package com.proquifa.net.negocio.comun;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

public interface FolioService {
	/**
	 * Metodo que recupera el folio a traves de su concepto
	 * @param concepto
	 * @return
	 * @throws ProquifaNetException
	 */
	Folio obtenerFolioPorConcepto(String concepto) throws ProquifaNetException;

	
}
