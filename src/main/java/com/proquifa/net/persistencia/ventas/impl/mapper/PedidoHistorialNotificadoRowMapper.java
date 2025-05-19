package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.proquifa.net.modelo.ventas.PartidaPedido;

import org.springframework.jdbc.core.RowMapper;

public class PedidoHistorialNotificadoRowMapper implements RowMapper{
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		PartidaPedido ppedido = new PartidaPedido();
		ppedido.setFEnvio(rs.getDate("FEnvio"));
		ppedido.setResponsable(rs.getString("Responsable"));
		ppedido.setDecision(rs.getString("Decision"));
		ppedido.setRespuesta(rs.getString("idNotRespuesta"));
		return ppedido;
	}

}
