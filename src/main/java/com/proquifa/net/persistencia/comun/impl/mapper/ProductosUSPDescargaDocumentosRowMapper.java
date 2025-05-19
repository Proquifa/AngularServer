package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

public class ProductosUSPDescargaDocumentosRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Producto producto = new Producto();
		
		producto.setIdProducto(rs.getLong("idProducto"));
		producto.setCodigo(rs.getString("Codigo"));
		try {producto.setCodigoInterno(rs.getString("codigoInterno"));} catch (Exception e) {}
		producto.setLote(rs.getString("Lote"));
		producto.setDocumentacionVersion(rs.getDouble("DocumentacionVersion"));
		producto.setSdsVersion(rs.getDouble("sdsVersion"));		
		return producto;

	}

}
