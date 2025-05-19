/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;

/**
 * @author misael.camanos
 *
 */
public class TiempoEntregaRutaProveedorRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		TiempoEntrega te = new TiempoEntrega();
		te.setIdTiempoEntrega(rs.getLong("PK_Tiempo_Entrega_Ruta"));
		te.setIdConfiguracionPrecio(rs.getLong("FK01_Configuracion_Precio"));
		te.setRuta(rs.getString("Ruta"));
		te.setRequierePExiste(rs.getString("RequierePermiso_Existe"));
		te.setRequierePNoExiste(rs.getString("RequierePermiso_NoExiste"));
		te.setRequierePNo(rs.getString("RequierePermiso_No"));
		te.setFactorFlexibilidad(rs.getInt("FactorFlexibilidad"));
		
		return te;
	}

}
