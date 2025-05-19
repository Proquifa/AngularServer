/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;
import com.proquifa.net.modelo.comun.Producto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class ConfiguracionPrecioArbolRowMapper implements RowMapper {
	
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConfiguracionPrecioProducto configCompleta = new ConfiguracionPrecioProducto();
		configCompleta.setIdConfiguracion(rs.getLong("PK_Configuracion_Precio"));
		
		Producto producto = new Producto();
		producto.setIndustria(rs.getString("Industria"));
		producto.setTipo(rs.getString("Tipo"));
		producto.setSubtipo(rs.getString("SubTipo"));
		producto.setControl(rs.getString("Control"));
		configCompleta.setProducto(producto);
		
		return configCompleta;
	}
}