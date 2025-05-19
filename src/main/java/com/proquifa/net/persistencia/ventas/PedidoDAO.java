/**
 * 
 */
package com.proquifa.net.persistencia.ventas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.modelo.ventas.Pedido;
import com.proquifa.net.persistencia.ventas.impl.PDFConfirmacionPedido;
//import com.proquifa.net.modelo.ventas.PDFConfirmacionPedido;


/**
 * @author ernestogonzalezlozada
 *
 */
public interface PedidoDAO {
	/**
	 * Método que obtiene un registro tipo Pedido mediante un documento recibido
	 * @param documentoRecibido
	 * @return
	 */
	Pedido obtenerPedidoXDocumentoRecibido(Long documentoRecibido);
	/**
	 * Método que actualiza un pedido 
	 * @param nuevoPedido
	 */
	void actualizarPedido(Pedido nuevoPedido);
	/**
	 * Método que obtiene el numero de pedidos por documento recibido
	 * @param folioDocumento
	 * @return
	 */
	Integer obtenerNumeroPedidosXDoctoR(Long folioDocumento);
	/**
	 * Método que obtiene un objeto pedido por el numero de folio
	 * @param cpedido
	 * @return
	 */
	List<Pedido> obtenerPedidoXFolio(String cPedido);
	Pedido obtenerPedidoXCPedido(String cPedido);
	/**Método que obtiene una lista de pedidos por el cpedido
	 * @param cpedido
	 * @return
	 */
	List<Pedido> obtenerPedidosXCpedido(String cPedido, String tipo, Date finicio, Date ffin, String Cliente, String tramito, String Estado, String referencia);
	/**
	 * Método que obtiene el Estado de un pedido por su cPedido
	 * @param cpedido
	 * @return
	 */
	Long obtenerEstadoXCpedido(String cPedido);
	/***
	 * Método que obtiene la guia de pedio por su cPedido y partida
	 * @param cpedido , part, idevento
	 * @return
	 */
	PartidaPedido obtenerGuiaPedido(Long Factura, String fPor, String  idEvento);
	/**
	 * Método que obtiene la Feche estimada de realizacion de pedido
	 * @param cpedido,factura,fpor,part
	 * @return
	 */
	Date obtenerFechaEstimadaRealizacion(String cPedido, Long Factura, String fPor, Integer Part);
	/**
	 * Método que obtiene el historial de notificados de un pedido
	 * @param folioNT, docto, part
	 * @return
	 */
	List<PartidaPedido> obtenerHistorialNotificado(String folioNT, String compra, Integer part);

	/***
	 * Método que obtine la fecha origen de pedido
	 * @param folio
	 * @return 
	 */
	Long obtenerTiempoProceso(Date finicio, Date ffin);
	
	/**
	 * Método para obtener Doctor
	 * @param cPedido
	 * @return
	 */
	TiempoProceso obtenerDoctoPedido(String cPedido);
	/**
	 * Método que obtiene el monto total de un conjunto de pedidos del mismo cliente o proveedor
	 * @param folioDocumento
	 * @return
	 */
	Double obtenerMontoTotalPedidos(Long idCliente, Long idProveedor, String periodo);
	/**
	 * Método que obtiene una lista de pedidos por proveedor
	 * @param folioDocumento
	 * @return
	 */
	List<Pedido> obtenerCantidadXProveedor(Long idProveedor, String periodo, Boolean topTen);
	/**
	 * @param idPedido
	 * @return
	 */
	Pedido obtenerPedidoXIdPedido(Integer idPedido);
	/**
	 * @param cpedido
	 * @return
	 */
	List<PDFConfirmacionPedido> obtenerDatosPdfConfirmarPedido(String cpedido);
}
