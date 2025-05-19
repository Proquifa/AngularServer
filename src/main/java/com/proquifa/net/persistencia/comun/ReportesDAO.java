/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Reporte;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
/**
 * @author ymendez
 *
 */
public interface ReportesDAO {

	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<?> getScoringInspeccionar() throws ProquifaNetException;
	
		
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<?> getScoringDespachos() throws ProquifaNetException;
	
	public List<?> getSeguimientos() throws ProquifaNetException;


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
	 * @param parametro
	 * @return
	 */
	String obtenerDate(Parametro parametro);
	
	List<DocumentoRecibido> getMailInfoDoctor(long folioDoctor) throws ProquifaNetException;
}
