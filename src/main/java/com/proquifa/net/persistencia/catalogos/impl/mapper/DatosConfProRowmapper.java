package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;

public class DatosConfProRowmapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConfiguracionPrecioProducto confPro = new ConfiguracionPrecioProducto();
		
		
		confPro.setNivel(rs.getString("NivelConfigPrecio"));
		confPro.setIdConfiguracion(rs.getLong("idConfiguracion"));
		
		if (rs.getLong("idProveedor") > 0) {
			confPro.setIdProveedor(rs.getLong("idProveedor"));
		}
		
		
		return confPro;
	}

}
