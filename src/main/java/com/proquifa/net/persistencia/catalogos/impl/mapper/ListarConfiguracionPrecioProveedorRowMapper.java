/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */
public class ListarConfiguracionPrecioProveedorRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ConfiguracionPrecio cp = new ConfiguracionPrecio();
		
			cp.setIdConfiguracionPrecio(rs.getLong("PK_Configuracion_Precio"));
			cp.setTipo(rs.getString("TIPO"));
			cp.setSubtipo(rs.getString("SUBTIPO"));
			cp.setControl(rs.getString("CONTR"));
			cp.setIndustria(rs.getString("INDUSTIAL"));
			cp.setFechaUltimaActualizacion(rs.getTimestamp("FUA"));
			cp.setNoProductos(rs.getInt("SUMA"));
			cp.setCaducidad(rs.getDate("Caduca"));
			cp.setRestringirDistribuidor(rs.getBoolean("Restringir_Distribuidor"));
			cp.setRestringirFExpress(rs.getBoolean("Restringir_FExpress"));
			cp.setRestringirStock(rs.getBoolean("Restringir_Stock"));
			cp.setNoClasificaciones(rs.getInt("TCLASIFI"));
			

		return cp;
	}
}