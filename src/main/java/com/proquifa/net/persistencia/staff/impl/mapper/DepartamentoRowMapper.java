package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.staff.Departamento;

import org.springframework.jdbc.core.RowMapper;

/**
 * Setea los Campos de la tabla Departamento
 * @author Isaac Garcia
 *
 */
public class DepartamentoRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Departamento departamento = new Departamento();
		
		departamento.setClaveDepartamento(rs.getString("Depto"));
		departamento.setNombreDepartamento(rs.getString("Nombre"));
		return departamento;
	}
}
