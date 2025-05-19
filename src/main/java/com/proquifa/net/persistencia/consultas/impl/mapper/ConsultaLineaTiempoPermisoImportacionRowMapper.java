package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


import com.proquifa.net.modelo.comun.TiempoProceso;

public class ConsultaLineaTiempoPermisoImportacionRowMapper implements RowMapper{
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TiempoProceso tiempo = new TiempoProceso();
		
		tiempo.setId(rs.getInt("ID"));
		tiempo.setEtiquetas(rs.getString("Etiqueta"));
		tiempo.setFechaInicio(rs.getTimestamp("FInicio"));
		tiempo.setFechaFin(rs.getTimestamp("FFin"));
		tiempo.setResponsable(rs.getString("Responsable"));
		tiempo.setDias(rs.getLong("TT"));
		
		return tiempo;
	}

}
