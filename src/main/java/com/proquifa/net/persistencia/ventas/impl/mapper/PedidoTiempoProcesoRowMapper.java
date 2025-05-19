package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

public class PedidoTiempoProcesoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		TiempoProceso tp = new TiempoProceso();
		tp.setDocumentoRecicbido(rs.getLong("doctor"));
		tp.setFechaPedido(rs.getTimestamp("fpedido"));
		tp.setFechaInicioPedido(rs.getTimestamp("finiciopedido"));
		return tp;
	}
	
}
