/**
 * 
 */
package com.proquifa.net.persistencia.consultas;

import java.util.List;


import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;

/**
 * @author vromero
 *
 */
public interface ConsultaComprasDAO {
	/**
	 * Reporte de Compras, regresa una lista de todas las Compras filtradas por fecha, proveedor, estado y coloco
	 * */
	List<Compra> findCompras(String periodo, String proveedor, String estado, String ordenCompra, String usuario, String empresaCompra);
	/**
	 * obtener partidas de compra para una compra especifica; Reporte de Compras
	 * @param ordenCompra
	 * @return Lista de Partidas de la Compra
	 */
	List<PartidaCompra> obtenerPatidasCompraParaCEspecifica(String ordenCompra);
	/**
	 * obtiene una lista de Tiempo Proceso por una partida
	 * @param idPCompra
	 * @return
	 */
	List<TiempoProceso> obtenerTiempoProcesoPorPartida(Long idPCompra);
	/***
	 * 
	 * @param periodo
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 */
	List<ResumenConsulta> findResumenCompras(String periodo, String proveedor, String estado, String coloco, String ordenCompra, String usuario);
	/***
	 * 
	 * @param periodo
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 */
	List<ResumenConsulta> findTopProveedores(String periodo, String proveedor, String estado, String coloco, String ordenCompra, String usuario);
	/***
	 * 
	 * @param periodo
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 */
	List<ResumenConsulta> findTopProductos(String periodo, String proveedor, String estado, String coloco, String ordenCompra, String usuario);
	/***
	 * 
	 * @param periodo
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 */
	List<ResumenConsulta> findTotalesComprasPorPeriodo(String periodo, String proveedor, String estado, String coloco,String ordenCompra,String usuario);
	Integer obtenerEnTiempo(Long idPCompra);
	/***
	 * 
	 * @param Lista Tiempo Proceso
	 * @param Id PCompra
	 * @return Lista Tiempo Proceso
	 */
	List<TiempoProceso> obtenerHistorialPhsMatriz(Long idPCompra);
	
	
	/**
	 * @param periodo
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 */
	List<Compra> obtenerGraficaXCompra(String periodo, String proveedor, String estado, String coloco, String ordenCompra, String usuario) throws ProquifaNetException;
}