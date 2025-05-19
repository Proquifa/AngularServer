package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;

public class PendienteRutaPlanearRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PendientesMensajero pend = new PendientesMensajero();
		pend.setIdhorario(rs.getString("IdHorario"));
		pend.setClave(rs.getInt("Clave"));
		pend.setNombre(rs.getString("Nombre"));
		pend.setZona(rs.getString("ZonaMensajeria"));
		pend.setPrioridad(rs.getString("Prioridad"));
		pend.setDireccion(rs.getString("Direccion"));
		pend.setMapa(rs.getString("Mapa"));
		pend.setCiudad(rs.getString("Ciudad"));
		pend.setColonia(rs.getString("Colonia"));
		
		return pend;
	}

}
