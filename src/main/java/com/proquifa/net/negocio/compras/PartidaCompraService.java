/**
 * 
 */
package com.proquifa.net.negocio.compras;

import java.util.List;


import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author amartinez
 *
 */
public interface PartidaCompraService {
	
	/***
	 * Obtener partidas de una compra por proveedor
	 * @param idProveedor
	 * @param anio
	 * @param periodo
	 * @param tipoPeriodo
	 * @param mes
	 * @return
	 * @throws ProquifaNetException
	 */
	//List<PartidaCompra> obtenerPartidasCompraProveedorTransito(Long idProveedor, Long anio, String periodo, Integer tipoPeriodo, Long mes) throws ProquifaNetException;
	
	/***
	 * Obtener partidas de una compra recibidas
	 * @param idProveedor
	 * @param anio
	 * @param periodo
	 * @param tipoPeriodo
	 * @param mes
	 * @return
	 * @throws ProquifaNetException
	 */
	//List<PartidaCompra> obtenerPartidasCompraProveedorRecibidas(Long idProveedor, Long anio, String periodo, Integer tipoPeriodo, Long mes) throws ProquifaNetException;
	
	/***
	 * obtener partidas de una compra en transito por mes.
	 * @param idProveedor
	 * @param anio
	 * @param periodo
	 * @param tipoPeriodo
	 * @param mes
	 * @return
	 * @throws ProquifaNetException
	 */
//	int [][] obtenerPartidasCompraTransitoXMes(Long idProveedor, Long anio, String periodo, Integer tipoPeriodo, Long mes) throws ProquifaNetException;
	
	/***
	 * Partidas de una compra recibidas por mes
	 * @param idProveedor
	 * @param anio
	 * @param periodo
	 * @param tipoPeriodo
	 * @param mes
	 * @return
	 * @throws ProquifaNetException
	 */
	//int [][] obtenerPartidasCompraRecibidasXMes(Long idProveedor, Long anio, String periodo, Integer tipoPeriodo, Long mes) throws ProquifaNetException;
	
	/***
	 * Partidas de una compra rechazadas.
	 * @param idProveedor
	 * @param anio
	 * @param periodo
	 * @param tipoPeriodo
	 * @param mes
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PartidaCompra> obtenerPartidasCompraRechazadas(Long idProveedor, Long anio, String periodo, Integer tipoPeriodo, Long mes) throws ProquifaNetException;
	
	/**
	 * Se utiliza en el reporte de Compras, para especificar los datos de una orden de compra
	 * @param ordenCompra
	 * @return Lista de Partidas Compra
	 */
	
	List<PartidaCompra> obtenerPatidasCompraParaCEspecifica(String ordenCompra) throws ProquifaNetException;
	/**
	 * Obtiene una lista de TiempoProceso con un idPCompra, se utiliza en el reporte de Compras.
	 * @param idpcompra
	 * @return
	 */
	List<TiempoProceso> obtenerTiempoProcesoPorPartida(Long idPCompra, String ordenCompra) throws ProquifaNetException;	
	/***
	 * 
	 * @param idPCompra
	 * @return
	 * @throws ProquifaNetException
	 */
	PartidaCompra obtenerPartidaCompraXId(Long idPCompra) throws ProquifaNetException;
}