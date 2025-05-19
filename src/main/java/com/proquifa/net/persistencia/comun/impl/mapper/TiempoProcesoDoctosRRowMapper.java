package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.DocumentoRecibido;

import org.springframework.jdbc.core.RowMapper;

public class TiempoProcesoDoctosRRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		DocumentoRecibido dr = new DocumentoRecibido();
		dr.setFecha(rs.getTimestamp("fecha"));
		dr.setFechaOrigen(rs.getTimestamp("fhorigen"));
		return dr;
	}
}