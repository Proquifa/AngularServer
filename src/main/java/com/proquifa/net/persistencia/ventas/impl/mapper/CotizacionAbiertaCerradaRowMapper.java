/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.Cotizacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class CotizacionAbiertaCerradaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Cotizacion registro = new Cotizacion();
		registro.setFolioCotizacion(rs.getString("Clave"));
		registro.setPartidas0(rs.getInt("FolioCero"));
		registro.setPartidas99(rs.getInt("FolioNueve"));
		registro.setAbierto(rs.getBoolean("AbiertasCerradas"));
		
		return registro;
	}
}