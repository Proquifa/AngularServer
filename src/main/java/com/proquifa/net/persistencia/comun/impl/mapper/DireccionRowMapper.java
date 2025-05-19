package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Direccion;

import org.springframework.jdbc.core.RowMapper;

public class DireccionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Direccion dir = new Direccion();
		
		dir.setIdLugar(rs.getInt("idHorario"));
		dir.setIdCliente(rs.getInt("idCliente"));
		dir.setTipo(rs.getString("Tipo"));
		dir.setDestino(rs.getString("Destino"));
		dir.setPais(rs.getString("Pais"));
		dir.setEstado(rs.getString("Estado"));
		dir.setCalle(rs.getString("Calle"));
		dir.setMunicipio(rs.getString("Municipio"));
		dir.setCodigoPostal(rs.getString("CP"));
		dir.setRuta(rs.getString("Ruta"));
		dir.setZonaMensajeria(rs.getString("ZonaMensajeria"));
		dir.setMapa(rs.getString("Mapa"));
		dir.setIdProveedor(rs.getInt("idProveedor"));
		dir.setLatitud(rs.getDouble("Latitud"));
		dir.setLongitud(rs.getDouble("Longitud"));
		dir.setAltitud(rs.getDouble("Altitud"));
		dir.setValidada(rs.getBoolean("Validada"));
		dir.setNumAsociados(rs.getInt("NumAsociados"));
		
		return dir;
	}

}
