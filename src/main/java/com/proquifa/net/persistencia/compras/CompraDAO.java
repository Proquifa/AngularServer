/**
 * 
 */
package com.proquifa.net.persistencia.compras;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.compras.ResumenCompra;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


/**
 * @author sarivera
 *
 */
public interface CompraDAO {
	/***
	 * Obtener compra por folio
	 * @param folioCompra
	 * @return
	 */
	Compra obtenerCompraXFolio(String folioCompra);
	
	/***
	 * 
	 * @param idProveedor
	 * @param periodo
	 * @return
	 */
	List<Compra> obtenerComprasConfirmadasXIdProveedor(Long idProveedor, String periodo);
	
	/***
	 * obtener monto en dolares de una compra 
	 * @param ordenCompra
	 * @param monedaOrigen
	 * @return
	 */
	Compra obtenerMontoDolaresCompra(String ordenCompra, String monedaOrigen);
	
	/***
	 * obtener fecha de una compra
	 * @param cPedido
	 * @return
	 */
	Compra obtenerFechaCompra(String cPedido);
	
	/***
	 * obtener fecha de inspeccion de una compra
	 * @param cPedido
	 * @return	
	 */
	Date obtenerFechaInspeccionCompra(String cPedido);
	
	/**
	 * Reporte de Compras, regresa una lista de todas las Compras filtradas por fecha, proveedor, estado y coloco
	 * */
	List<Compra> obtenerReporteCompra(String periodo, String proveedor, String estado, String coloco, String ordenCompra);
	
	/****
	 * 
	 * @param folioOC
	 */
	Boolean cerrarPendienteDeMonitoreoOC (String folioOC);	
	
	/***
	 * obtener Permisos de Importacion para Reporte
	 * @param cPedido
	 * @return
	 */
	List<PermisoImportacion> obtenerReportePermisoImportacion (String condiciones);
	
	/***
	 * obtener el Historial de los Permisos de Importacion para Reporte
	 * @param cPedido
	 * @return
	 */
	List<PermisoImportacion> obtenerHistorialPerImportacion (String idSolicitud);
	
	/***
	 * obtener el previo de permisos de importacion
	 * @param cPedido
	 * @return
	 */
	List<PermisoImportacion> obtenerPrevioPermisoImportacion ();
	/***
	 * se cierra el pendiente previo de Permiso de Importacion
	 * @param fecha    con la que se va a cerrar el pendiente
	 * @param folio    de la tabla de pendiente
	 * @return
	 */
	Integer cerrarPendientePrevio(String fecha, String folio );
	/***
	 * se actualiza la tabla de producto, para cambiar su estado
	 * @param PermisoImportacion
	 * @return
	 */
	Integer actualizarProducto(PermisoImportacion data);
	/***
	 * se actualiza la tabla de Pharma, para cambiar su estado
	 * @param PermisoImportacion
	 * @return
	 */
	Integer actualizarPharma(PermisoImportacion data);
	/***
	 * se actualiza la tabla de Pharma, para cambiar su estado
	 * @param PermisoImportacion
	 * @param fecha
	 * @return
	 */
	Integer insertarSolictudPermisoImp(PermisoImportacion data, String fecha);
	/***
	 * Se crea el pendiente de "A Tramitar PermisoImp"
	 * @param PermisoImportacion
	 * @param fecha
	 * @return
	 */
	Integer crearPendientePartidaStr(PermisoImportacion data, String fecha);
	/***
	 * Se actualiza el estado del producto a "A tramitacion"
	 * @param idProducto
	 * @return
	 */
	Integer actualizarEdoProducto(Long idProducto);

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
	
	ResumenCompra getResumenCompra(String folioOC) throws ProquifaNetException;
}