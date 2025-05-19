/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.PedidoPago;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class PedidoPagoRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PedidoPago pedidoPago = new PedidoPago();
		pedidoPago.setFolio(rs.getLong("Folio"));
		pedidoPago.setPedido(rs.getLong("Pedido"));
		pedidoPago.setPago(rs.getLong("Pago"));
		pedidoPago.setCpedido(rs.getString("CPedido"));
		pedidoPago.setFecha(rs.getDate("Fecha"));
		pedidoPago.setMoroso(rs.getLong("Moroso"));
		pedidoPago.setComentarios(rs.getString("ComentariosValidacion"));
		return pedidoPago;
	}

}
