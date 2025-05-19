
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;

import org.springframework.jdbc.core.RowMapper;

public class ListaConfiguracionPrecioProveedorCampanaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConfiguracionPrecio cp = new ConfiguracionPrecio();
		
			cp.setIdConfiguracionPrecio(rs.getLong("PK_Configuracion_Precio"));
			cp.setTipo(rs.getString("TIPO"));
			cp.setSubtipo(rs.getString("SUBTIPO"));
			cp.setControl(rs.getString("CONTR"));
			cp.setIndustria(rs.getString("INDUSTIAL"));
			cp.setNoProductos(rs.getInt("SUMA"));
		return cp;
	}
}