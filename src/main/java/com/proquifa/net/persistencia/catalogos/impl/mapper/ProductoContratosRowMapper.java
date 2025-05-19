package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Producto;

public class ProductoContratosRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Producto pro = new Producto();
		
		
		pro.setDescripcion(rs.getString("Concepto"));
		pro.setCosto(rs.getDouble("Costo"));
		pro.setCostoString((pro.getCosto()).toString());
		
		
		return pro;
	}

}
