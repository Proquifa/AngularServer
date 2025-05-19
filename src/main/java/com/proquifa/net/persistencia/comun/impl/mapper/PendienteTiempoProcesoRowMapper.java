package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Pendiente;

import org.springframework.jdbc.core.RowMapper;

public class PendienteTiempoProcesoRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Pendiente pendiente = new Pendiente();
		pendiente.setFechaInicio(rs.getTimestamp("FInicio"));
		pendiente.setFechaFin(rs.getTimestamp("FFin"));
		return pendiente;
	}

}
