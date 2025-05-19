package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.Compra;

import org.springframework.jdbc.core.RowMapper;

public class compraTiempoProcesoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Compra compra = new Compra();
		compra.setFecha(rs.getTimestamp("fecha"));
		compra.setIdPCompra(rs.getLong("idPCompra"));
		return compra;
	}
}
