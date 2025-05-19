package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.staff.TipoIncidencia;

import org.springframework.jdbc.core.RowMapper;

public class TipoIncidenciaRowMapper implements RowMapper{
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		TipoIncidencia tipoIncidencia = new TipoIncidencia();
		
		tipoIncidencia.setCveIncidencia(rs.getString("Concepto"));
		tipoIncidencia.setNomIncidencia(rs.getString("Nombre"));
		
		return tipoIncidencia;
	}

}
