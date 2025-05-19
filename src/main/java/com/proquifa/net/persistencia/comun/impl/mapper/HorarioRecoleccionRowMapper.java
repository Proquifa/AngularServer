package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Horario;

import org.springframework.jdbc.core.RowMapper;

public class HorarioRecoleccionRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Horario h = new Horario();
		
		h.setIdHorario(rs.getLong("PK_Horario"));
		h.setIdDireccion(rs.getLong("FK01_Direccion"));
		h.setTipo(rs.getString("Tipo"));
		h.setLunes(rs.getBoolean("Lunes"));
		h.setMartes(rs.getBoolean("Martes"));
		h.setMiercoles(rs.getBoolean("Miercoles"));
		h.setJueves(rs.getBoolean("Jueves"));
		h.setViernes(rs.getBoolean("Viernes"));
		h.setLuDe1(rs.getString("LuDe1"));
		h.setLuA1(rs.getString("LuA1"));
		h.setLuDe2(rs.getString("LuDe2"));
		h.setLuA2(rs.getString("LuA2"));
		h.setMaDe1(rs.getString("MaDe1"));
		h.setMaA1(rs.getString("MaA1"));
		h.setMaDe2(rs.getString("MaDe2"));
		h.setMaA2(rs.getString("MaA2"));
		h.setMiDe1(rs.getString("MiDe1"));
		h.setMiA1(rs.getString("MiA1"));
		h.setMiDe2(rs.getString("MiDe2"));
		h.setMiA2(rs.getString("MiA2"));
		h.setJuDe1(rs.getString("JuDe1"));
		h.setJuA1(rs.getString("JuA1"));
		h.setJuDe2(rs.getString("JuDe2"));
		h.setJuA2(rs.getString("JuA2"));
		h.setViDe1(rs.getString("ViDe1"));
		h.setViA1(rs.getString("ViA1"));
		h.setViDe2(rs.getString("ViDe2"));
		h.setViA2(rs.getString("ViA2"));
		if (rs.getTimestamp("FUA") != null ){
			h.setFua(rs.getTimestamp("FUA"));
		}
		
		return h;
	}

}
