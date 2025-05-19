/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import com.proquifa.net.modelo.comun.Folio;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface FolioDAO {
	/**
	 * Metodo que obtiene el folio mediante el concepto
	 * @param concepto
	 * @return
	 */
	Folio obtenerFolioPorConcepto(String concepto,boolean masUno);
	/***
	 * 
	 * @param concepto
	 * @return
	 */
	Long obtenerConsecutivoFolio(String concepto);
	/***
	 * 
	 * @param concepto
	 * @return
	 */
	Long getConsecutivoMasUnoXConcepto(String concepto);
	/**
	 * Medoto que obtiene el siguiente valor de la tabla consecutivos a traves del concepto
	 * @param concepto
	 * @param valor
	 */
	void actualizarValorConsecutivo(String concepto, Long valor);
	/***
	 * obtiene si el concepto esta bloqueado
	 * @param concepto
	 * @return
	 */
	long getEstaBloqueadoXConcepto(String concepto);
	/**
	 * Metodo que bloquea y desbloqueo los consecutivos
	 * @param concepto
	 * @param bloqueo
	 */
	int bloquear(String concepto,Boolean bloqueo);
	Long getConsecutivoMasUnoXConceptoFolio(String concepto);
}
