package com.proquifa.net.persistencia.catalogos.impl.mapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.ConfiguracionContrato;

public class ConfiguracionContratoRowMapper implements RowMapper  {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfiguracionContrato conf = new ConfiguracionContrato();
		
		
		conf.setMarca(rs.getString("Nombre"));
		conf.setIdMarca(rs.getLong("clave"));
		return conf;
	}

}
