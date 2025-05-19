package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.staff.Asistencia;

public class AsistenciaRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Asistencia asistencia= new Asistencia();
		
		asistencia.setIdTrabajador(rs.getLong("Trabajador"));
		asistencia.setNombreCorto(rs.getString("NombreCorto"));
		asistencia.setFecha(rs.getDate("Fecha"));
		asistencia.setHora(rs.getString("Hora"));
		asistencia.setTipoChecada(rs.getString("TipoChecada"));
		asistencia.setArea(rs.getString("Area"));
		asistencia.setDepto(rs.getString("Departamento"));
		asistencia.setCategoria(rs.getString("Categoria"));
		asistencia.setChecada(rs.getTimestamp("Checada"));
		asistencia.setRotacion(rs.getString("Rotacion"));
		asistencia.setIncidencia(rs.getString("Incidencia"));
		asistencia.setLocalidad("DISTRITO FEDERAL");
		
		return asistencia;
	}
}
