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
public class ProductoDescripcionRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		String cantidad = "";
		String unidad = "";
		Long pureza = 0L;
		Producto producto = new Producto();
		producto.setTipo(rs.getString("tipo"));
		producto.setFabrica(rs.getString("fabrica"));
		producto.setCodigo(rs.getString("codigo"));
		producto.setConcepto(rs.getString("concepto"));
		cantidad = rs.getString("cantidad");
		unidad = rs.getString("unidad");
		if(cantidad == null){
			cantidad = "";
		}
		if(unidad == null){
			unidad = "";
		}
		producto.setCantidad(cantidad);
		producto.setUnidad(unidad);
		pureza = rs.getLong("pureza");
		if(rs.wasNull()){			
			producto.setPureza(null);
		}else{
			producto.setPureza(pureza);
		}
		
		producto.setTiempoEntrega(rs.getString("TEntrega"));
		producto.setProveedor(rs.getLong("Proveedor"));
		return producto;
	}

}
