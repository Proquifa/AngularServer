/**
 * 
 */
package com.proquifa.net.persistencia.ventas;

import com.proquifa.net.modelo.ventas.PedidoPago;

/**
 * @author fmartinez
 *
 */
public interface PedidoPagoDAO {
	/**
	 * Metodo que registra un DocumentoRecibido en la base de datos
	 * @param doctoRecibido
	 * @return
	 */
	PedidoPago obtenerPedidoPago(Long idPago);
}
