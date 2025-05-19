package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class IdsSolicitudVisitaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Long idSolicitudVisita = rs.getLong("FK02_SolicitudVisita");
		return idSolicitudVisita;
	}

}
