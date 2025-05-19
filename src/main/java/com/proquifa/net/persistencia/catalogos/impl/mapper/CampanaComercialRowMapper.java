package com.proquifa.net.persistencia.catalogos.impl.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Campana;

import org.springframework.jdbc.core.RowMapper;

public class CampanaComercialRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Campana cam = new Campana();
		
		cam.setId_Camapana(rs.getLong("PK_Campana"));
		cam.setFechaInicio(rs.getDate("FechaInicio"));
		cam.setFechaFin(rs.getDate("FechaFin"));
		cam.setNombre(rs.getString("Nombre"));
		cam.setTipo(rs.getString("Tipo"));
		cam.setObjetivo(rs.getString("Objetivo"));
		cam.setComision(rs.getDouble("Comision"));
		cam.setIdProvee(rs.getLong("FK01_idProveedor"));
		cam.setTipo_Comision(rs.getString("Tipo_Comision"));
		cam.setFuaCampana(rs.getDate("UltimaActualizacion"));
		return cam;
	}


}
