/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.Compra;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ReporteCompraRowMapper implements RowMapper {
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Compra compra = new Compra();
		compra.setClave(rs.getString("clave"));
		compra.setEmpresa(rs.getString("empresa"));
		compra.setMontoTotal(rs.getDouble("montoTotal"));
		compra.setMoneda(rs.getString("moneda"));
		compra.setFecha(rs.getDate("fecha"));
		return compra;
	}
}
