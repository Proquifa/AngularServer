/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ReporteRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Producto producto = new Producto();
		producto.setCodigo(rs.getString("codigo"));
		producto.setFabrica(rs.getString("fabrica"));
		producto.setCant(rs.getInt("cantidad"));
		producto.setTotalVentas(rs.getDouble("total"));
		return producto;
	}

}
