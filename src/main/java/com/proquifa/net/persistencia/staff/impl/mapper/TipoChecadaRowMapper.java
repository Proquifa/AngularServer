package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.staff.TipoChecada;

/**
 * Setea los Campos de la tabla TipoChecada
 * @author Isaac Garcia
 *
 */

public class TipoChecadaRowMapper implements RowMapper {
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		TipoChecada tipoChecada = new TipoChecada();

		tipoChecada.setClaveChecada(rs.getString("TipoChecada"));
		tipoChecada.setDescripcionChecada(rs.getString("Descripcion"));
		return tipoChecada;
	}

}
