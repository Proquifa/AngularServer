package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.Pedido;

import org.springframework.jdbc.core.RowMapper;

public class PedidoConsultaRowMapper implements RowMapper{
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Pedido pedido = new Pedido();
		pedido.setDoctoR(rs.getLong("DoctoR"));
		pedido.setFecha(rs.getDate("FPedido"));
		pedido.setIdCliente(rs.getLong("idCliente"));
		pedido.setIdContacto(rs.getLong("idContacto"));
		pedido.setCpedido(rs.getString("CPedido"));
		pedido.setPedido(rs.getString("Pedido"));
		pedido.setFolio(rs.getLong("Folio"));
		pedido.setFechaDoctoR(rs.getDate("Fecha"));
		pedido.setFechaOrigen(rs.getDate("FHOrigen"));
		pedido.setTramito(rs.getString("Tramito"));
		pedido.setNombreCliente(rs.getString("Contacto"));
		pedido.setContacto(rs.getString("Nombre"));
		pedido.setEstado(rs.getString("cuantos"));
		return pedido;
		
	}

}
