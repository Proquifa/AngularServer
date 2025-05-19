/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.Pedido;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class PedidoReporteRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Pedido pedido = new Pedido();
		pedido.setIdCliente(rs.getLong("idCliente"));
		pedido.setCantidad(rs.getLong("cantidad"));
		pedido.setNombreCliente(rs.getString("nombre"));
		return pedido;
	}

}
