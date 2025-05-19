/**
 * 
 */
package com.proquifa.net.persistencia.ventas;


import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.PartidaPedido;


/**
 * @author fmartinez
 *
 */
public interface PartidaPedidoDAO {
	/****
	 * Actualizar la partida de pedido, con determinados parametros.
	 * @param idPPedido
	 * @param Parametros
	 * @return
	 */
	Boolean actualizarPPedido (PartidaPedido pp);
	/***
	 * Se crea un registro en el Historial de Partida Pedido, para la ultima modificacion al PPedido
	 * @param idPPedido
	 * @param tipo
	 * @param Concepto
	 * @param fecha
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertRegistroHistorialPartidaPedidos(Long idPPedido, String tipo, String Concepto, Date fecha ) ;
	/***
	 * Obtiene el ultimo paso de la partida
	 * @param idPPedido
	 * @return
	 */
	String getOrigen(Long idPPedido);
	
	/***
	 * Obtiene la partida del pedido por medio de su idPPedid
	 * @param idPPedido
	 * @return
	 */
	PartidaPedido getPPedidoXid (Long idPPedido, Boolean tipoFlete, Boolean compras);
	/***
	 * 
	 * @param cpedido
	 * @param idPPedido
	 * @param tipoFlete
	 * @return
	 */
	PartidaPedido getPPedidoXCPedidoIdPPedido (String cpedido, Long idPPedido, Boolean tipoFlete);
	
	/****
	 * Obtiene la partida maxima del pedido relacionado
	 * @param idPPedido
	 * @return
	 */
	Integer getMaxPPedido(String cpedido);
	
	/****
	 * Crear nuevo registro de partida de pedido
	 * @param ppedido
	 * @return
	 */
	long insertarPPedido (PartidaPedido ppedido);
	
	/***
	 * 
	 * @param ppedido
	 * @return
	 * @throws ProquifaNetException
	 */
	PartidaPedido copiarPPedido (PartidaPedido ppedido) throws ProquifaNetException;
	
	/***
	 * 
	 * @param ppedido
	 * @return
	 * @throws ProquifaNetException
	 */
	long dividirPPedido (PartidaPedido ppedido) throws ProquifaNetException;
	
	/***
	 * 
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PartidaPedido> findPartidasXCPedido(String cpedido, Boolean tipoFlete) throws ProquifaNetException;
	
	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateEstadoxEmbalarPedido(Parametro parametro) throws ProquifaNetException;
	boolean verificarFactura(PartidaPedido ppedido, Long idPcompra, Integer stock) throws ProquifaNetException;
	boolean actualizarPFactura(Long idPCompra, Integer cantidad) throws ProquifaNetException;
}