package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Horario;

public class DireccionHorarioDeClienteRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Direccion d = new Direccion();
		d.setPais(rs.getString("Pais"));
		d.setCodigoPostal(rs.getString("CP"));
		d.setEstado(rs.getString("Estado"));
		d.setMunicipio(rs.getString("Municipio"));
		d.setCiudad(rs.getString("Ciudad"));
		d.setCalle(rs.getString("Calle"));
		d.setColonia(rs.getString("Colonia"));
		Horario horario = new Horario();
		horario.setLunes(rs.getBoolean("Lunes"));
		horario.setLuDe1(rs.getString("LuDe1"));
		horario.setLuA1(rs.getString("LuA1"));
		horario.setLuDe2(rs.getString("LuDe2"));
		horario.setLuA2(rs.getString("LuA2"));
		horario.setMartes(rs.getBoolean("Martes"));
		horario.setMaDe1(rs.getString("MaDe1"));
		horario.setMaA1(rs.getString("MaA1"));
		horario.setMaDe2(rs.getString("MaDe2"));
		horario.setMaA2(rs.getString("MaA2"));
		horario.setMiercoles(rs.getBoolean("Miercoles"));
		horario.setMiDe1(rs.getString("MiDe1"));
		horario.setMiA1(rs.getString("MiA1"));
		horario.setMiDe2(rs.getString("MiDe2"));
		horario.setMiA2(rs.getString("MiA2"));
		horario.setJueves(rs.getBoolean("Jueves"));
		horario.setJuDe1(rs.getString("JuDe1"));
		horario.setJuA1(rs.getString("JuA1"));
		horario.setJuDe2(rs.getString("JuDe2"));
		horario.setJuA2(rs.getString("JuA2"));
		horario.setViernes(rs.getBoolean("Viernes"));
		horario.setViDe1(rs.getString("ViDe1"));
		horario.setViA1(rs.getString("ViA1"));
		horario.setViDe2(rs.getString("ViDe2"));
		horario.setViA2(rs.getString("ViA2"));
	
		
		d.setHorarioEntrega(horario);
		
		return d;
	}

}

