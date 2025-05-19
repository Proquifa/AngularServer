package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.staff.Turno;

public class TurnoRowMapper implements RowMapper{
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Turno turno = new Turno();
		
		turno.setTurno(rs.getString("Turno"));
		turno.setEntradaHasta(rs.getTimestamp("EntradaHasta"));
		turno.setSalida(rs.getTimestamp("Salida"));
		turno.setComida(rs.getTimestamp("Comida"));
		turno.setComidaRegreso(rs.getTimestamp("ComidaRegreso"));
		turno.setComidaTiempo(rs.getTimestamp("ComidaTiempo"));
		
		return turno;
	}

}
