package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import  com.proquifa.net.modelo.catalogos.proveedores.MultiusosValores;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class MultiusosMixProveedorRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		MultiusosValores multi = new MultiusosValores();
		multi.setValorString1(rs.getString("valorString1"));
		multi.setValorString2(rs.getString("valorString2"));
		multi.setValorString3(rs.getString("valorString3"));
		multi.setValorLong4(rs.getLong("valorLong4"));
		multi.setValorLong5(rs.getLong("valorLong5"));
		multi.setValorLong6(rs.getLong("valorLong6"));
		multi.setValorDouble7(Math.rint((rs.getDouble("valorDouble7")) * 1e2) / 1e2);
		multi.setValorDouble8(Math.rint((rs.getDouble("valorDouble8")) * 1e2) / 1e2);
		return multi;
	}
	

}
