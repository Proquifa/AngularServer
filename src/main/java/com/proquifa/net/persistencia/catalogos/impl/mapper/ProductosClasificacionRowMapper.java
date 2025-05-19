package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

public class ProductosClasificacionRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Producto p = new Producto();
		
		p.setIdProducto(rs.getLong("idProducto"));
		p.setCategoriaPrecioLista(rs.getLong("FK03_Categoria_PrecioLista"));
		p.setCosto(rs.getDouble("Costo"));
		
		return p;
	}

}
