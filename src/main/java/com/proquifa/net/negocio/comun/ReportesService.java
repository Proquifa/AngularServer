/**
 * 
 */
package com.proquifa.net.negocio.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.Reporte;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface ReportesService {

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<?> getScoringInspeccionar() throws ProquifaNetException;
	
	public List<?> obtenerSeguimientos() throws ProquifaNetException;
	
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<?> getScoringDespachos() throws ProquifaNetException;
	
	/**
	 * @param ordenDespacho
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Reporte> getOrdenDespacho(String ordenDespacho) throws ProquifaNetException;


	/**
	 * @param compra
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Reporte> getConsultaCompra(String compra) throws ProquifaNetException;


	/**
	 * @param tipo
	 * @param folio
	 * @param carpet
	 * @return
	 */
	String obtenerDate(String tipo, String folio, String carpet);
	
	List<DocumentoRecibido> getMailInfoDoctor(long folioDoctor) throws ProquifaNetException;
}
