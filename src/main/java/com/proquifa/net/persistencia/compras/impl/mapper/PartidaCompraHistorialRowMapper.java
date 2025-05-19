package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.PartidaCompra;

public class PartidaCompraHistorialRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		PartidaCompra pcompra = new PartidaCompra();
		pcompra.setFolioNT(rs.getString("folioNT"));
		pcompra.setFechaCierre(rs.getDate("fechaCierre"));
		pcompra.setPartida(rs.getInt("partida"));
		pcompra.setCompra(rs.getString("compra"));
		try {pcompra.setOrigen(rs.getString("origen"));} catch (Exception e) {}
		return pcompra;
	}
	
}
