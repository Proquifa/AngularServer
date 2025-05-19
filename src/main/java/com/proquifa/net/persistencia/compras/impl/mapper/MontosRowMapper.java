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
public class MontosRowMapper implements RowMapper {
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Compra compra = new Compra();
		compra.setMontoTotalDolares(rs.getDouble("totalDolares"));
		compra.setTipoCambio(rs.getDouble("tipoCambio"));
		return compra;
	}
}
