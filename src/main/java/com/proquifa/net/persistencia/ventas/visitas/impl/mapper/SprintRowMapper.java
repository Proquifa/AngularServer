package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.Sprint;

import org.springframework.jdbc.core.RowMapper;

public class SprintRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Sprint sprint = new Sprint();
		sprint.setIdSprint(rs.getLong("PK_Sprint"));
		sprint.setAnio(rs.getInt("Anio"));
		sprint.setFechaInicio(rs.getDate("FInicio"));
		sprint.setFechaFin(rs.getDate("FFin"));
		sprint.setEstado(rs.getString("Estado"));
		sprint.setNumeroSprint(rs.getInt("NumeroSprint"));
		
		return sprint;
	}
	
	

}
