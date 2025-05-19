package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.NivelIngreso;

public class NivelIngresoRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			
			NivelIngreso ni = new NivelIngreso();
			ni.setFua(rs.getTimestamp("FUA"));
			ni.setIdNivelIngreso(rs.getLong("PK_NivelIngreso"));
			ni.setNivel(rs.getString("Nivel"));
			ni.setMax(rs.getDouble("MAX"));
			ni.setMin(rs.getDouble("MIN"));
			
			return ni;
		}
}
