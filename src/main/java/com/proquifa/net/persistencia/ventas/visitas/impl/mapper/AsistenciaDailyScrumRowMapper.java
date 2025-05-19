package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.visitas.AsistenciaDailyScrum;

import org.springframework.jdbc.core.RowMapper;

public class AsistenciaDailyScrumRowMapper implements RowMapper {
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		AsistenciaDailyScrum daily = new AsistenciaDailyScrum();
		daily.setIdDailyScrum(rs.getInt("FK02_DailyScrum"));
		daily.setAsistencia(rs.getBoolean("Asistencia"));
		daily.setIdAsistenciaDailyScrum(rs.getInt("PK_AsistenciaDailyScrum"));
		daily.setIdEmpleado(rs.getInt("FK01_Empleado"));
		daily.setCodigoAsistencia(rs.getString("CodigoAsistencia"));
		
		return daily;
	}
}
