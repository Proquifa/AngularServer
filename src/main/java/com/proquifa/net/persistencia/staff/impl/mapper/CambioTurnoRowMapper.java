package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.staff.CambioTurno;

public class CambioTurnoRowMapper implements RowMapper{
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		CambioTurno cambioturno = new CambioTurno();
		
		cambioturno.setIdTrabajador(rs.getLong("Trabajador"));
		cambioturno.setFecha(rs.getTimestamp("Fecha"));
		cambioturno.setTurno(rs.getString("Turno"));
		
		return cambioturno;
	}
}
