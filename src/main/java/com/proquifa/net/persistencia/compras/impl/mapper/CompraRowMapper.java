/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.Compra;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author sarivera
 * 
 */
public class CompraRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Compra compra = new Compra();
		compra.setClave(rs.getString("clave"));
		compra.setFecha(rs.getDate("fecha"));
		compra.setProveedor(rs.getLong("provee"));
		compra.setEstado(rs.getString("estado"));
		compra.setAlmacenUE(rs.getBoolean("almacenUE"));
		compra.setAlmacenUSA(rs.getBoolean("almacenUSA"));
		compra.setAlmacenMatriz(rs.getBoolean("almacenMatriz"));
		return compra;
	}
}
