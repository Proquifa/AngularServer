package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.staff.Area;

import org.springframework.jdbc.core.RowMapper;

/**
 * Setea los Campos de la tabla Area
 * @author Isaac Garcia
 *
 */

public class AreaRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Area area = new Area();
		
		area.setClaveArea(rs.getString("Area"));
		area.setNombreArea(rs.getString("Nombre"));
		return area;
	}
}
