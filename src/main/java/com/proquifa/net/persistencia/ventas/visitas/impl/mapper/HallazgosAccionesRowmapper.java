package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.HallazgosAcciones;

import org.springframework.jdbc.core.RowMapper;

public class HallazgosAccionesRowmapper implements RowMapper  {
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		

	
	HallazgosAcciones result = new HallazgosAcciones();
	
	result.setTipo(rs.getString("Tipo"));
	result.setDescripcion(rs.getString("Descripcion"));
	return result;
	
	
	}
}

