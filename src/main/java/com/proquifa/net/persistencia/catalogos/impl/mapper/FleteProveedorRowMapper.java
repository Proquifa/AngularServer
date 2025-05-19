package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.Flete;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */

public class FleteProveedorRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		Flete f = new Flete();
		
		f.setIdFlete(rs.getLong("PK_Flete"));
		f.setConcepto(rs.getString("Codigo"));
		f.setMonto(Math.rint((rs.getDouble("Monto")) * 1e2) / 1e2);
		f.setFua(rs.getTimestamp("FUA_M"));
		f.setTiempoEntrega(rs.getString("TEntrega"));
		f.setLeyenda(rs.getString("Concepto"));
		f.setHabilitado(rs.getBoolean("Vigente"));
		f.setConcatenaRuta(rs.getString("Nota"));
		
		return f;

	}
}
