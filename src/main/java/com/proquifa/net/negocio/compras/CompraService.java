/**
 * 
 */
package com.proquifa.net.negocio.compras;


import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;



/**
 * @author amartinez
 *
 */
public interface CompraService {

	/**
	 * Regresa una lista de compras se puede filtrar por fecha, proveedor, estado y quien la coloco 
	 * @param FechaInicio
	 * @param FechaFin
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @return Lista de Compra
	 * Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, Integer coloco, String ordenCompra
	 * @throws ProquifaNetException
	 */
	List<Compra> obtenerReporteCompra(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, Integer coloco, String ordenCompra) throws ProquifaNetException;

	/**
	 * @param fecha
	 * @return
	 * @throws ProquifaNetException
	 */
	String validarHorarioCliente(String idPCompra) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String> obtenerAsuetos() throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	String obtenerDiaFinMes(String idCliente) throws ProquifaNetException;
	
	boolean generarDocumentoOC(String folioOC) throws ProquifaNetException;
		 
}