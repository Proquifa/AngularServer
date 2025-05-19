/**
 * 
 */
package com.proquifa.net.persistencia.compras;

import java.util.List;

import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


/**
 * @author sarivera
 *
 */
public interface PartidaCompraDAO {
	/**
	 * Recupera las partidas de compra del periodo deseado por proveedor y estado 
	 * @param idProveedor
	 * @param periodo - fechas construidas a partir de la funcion determinarPeriodo en la clase Funcion.java
	 * @return lista con las partidas de compra 
	 */
	List<PartidaCompra> obtenerPartidasCompraXPeriodo(Long idProveedor, String estado, String periodo); 
	
	/***
	 * 
	 * @param idProveedor
	 * @param periodo
	 * @return
	 */
	List<PartidaCompra> obtenerPartidasCompraRechazadas(Long idProveedor, String periodo);
	/**
	 * Recupera el numero de piezas total en las partidas de una orden de compra 
	 * @param compra
	 * @param estado
	 * @return
	 */
	Integer obtenerCantidadPiezas(String compra, String estado);
	/**
	 * Metodo que obtiene orden de compra y pedimento por cPedido , su partida y idPPedido
	 * @param cpedido , Part, idPPedido
	 * @return
	 */
	List <PartidaCompra> obtenerOrdendeCompraXCpedidoyPartida(String cPedido, Integer Part, Long idPPedido);
	/**
	 * Metodo que obtine FolioNt por su cPedido y partida
	 * @param idcompra , part
	 * @return
	 */ 
	List <PartidaCompra> obtenerFolioNT(String cPedido);
	
	/***
	 * Actuaizar registro de pcompra
	 * @param idPCompra
	 * @param Parametros
	 * @return
	 */
	Boolean actualizarPCompra(PartidaCompra pc);
	boolean actualizarCostoPCompra(long idPCompra, double costoNuevo);
	
	/****
	 * Obtiene la partida de la compra por id.
	 * @param idPCompra
	 * @return
	 */
	PartidaCompra getPCompraXid(Long idPCompra);
	
	/***
	 * Inserta una partida de compra.
	 * @param pcompra
	 * @return
	 */
	long insertarPCompra(PartidaCompra pcompra);
	
	/***
	 * Obtiene la partida maxima de la compra
	 * @param compra
	 * @return
	 */
	Integer getMaxPcompra(String compra);
	
	/***
	 * 
	 * @param pcompra
	 * @return
	 * @throws ProquifaNetException
	 */
	PartidaCompra copiarPCompra (PartidaCompra pcompra);
	
	/***
	 * 
	 * @param pcompra
	 * @return
	 * @throws ProquifaNetException
	 */
	long dividirPCompra(PartidaCompra pcompra, Long ppedidoRelacionado);

}