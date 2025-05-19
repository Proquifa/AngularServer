package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.visitas.DailyScrum;

import org.springframework.jdbc.core.RowMapper;

public class DailyScrumRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		DailyScrum daily = new DailyScrum();
		daily.setIdDailyScrum(rs.getInt("PK_DailyScrum"));
		daily.setNumeroDaily(rs.getInt("NumeroDaily"));
		daily.setIdSprint(rs.getInt("FK01_Sprint"));
		daily.setObservacion(rs.getString("Observaciones"));
		daily.setFechaDaily(rs.getDate("FechaDaily"));
		 
		return daily;
	}
}
