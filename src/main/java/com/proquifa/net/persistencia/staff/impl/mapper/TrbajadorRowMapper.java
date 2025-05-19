package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.staff.Trabajador;

import org.springframework.jdbc.core.RowMapper;

/**
 * Setea los Campos de la tabla Trabajador
 * @author Isaac Garcia
 *
 */
public class TrbajadorRowMapper implements RowMapper{
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
	Trabajador trabajador = new Trabajador();
	
	trabajador.setIdTrabajador(Long.parseLong(rs.getString("Trabajador")));
	trabajador.setNombreCorto(rs.getString("NombreCorto"));
	return trabajador;
	}

}
