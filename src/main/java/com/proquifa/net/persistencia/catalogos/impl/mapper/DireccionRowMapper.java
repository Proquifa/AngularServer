package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Direccion;

import org.springframework.jdbc.core.RowMapper;

public class DireccionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Direccion dir = new Direccion();
		
		//Hay otro archivo que tiene este mismo nombre, pero el otro es para HorarioyLugar, este es para la nueva forma de Direccion
		
		//PK_Direccion, Igual, Pais, CP, Estado, Municipio, Ciudad, CalleNum, Colonia, Zona, Ruta, Mapa, Altitud, Latitud, Longitud, FUA, FK01_Cliente
		dir.setIdDireccion(rs.getLong("PK_Direccion"));
		dir.setPais(rs.getString("Pais"));
		dir.setCodigoPostal(rs.getString("CP"));
		dir.setEstado(rs.getString("Estado"));
		dir.setCalle(rs.getString("CalleNum"));
		dir.setMunicipio(rs.getString("Municipio"));
		dir.setCiudad(rs.getString("Ciudad"));
		dir.setColonia(rs.getString("Colonia"));
		dir.setZonaMensajeria(rs.getString("Zona"));
		dir.setRuta(rs.getString("Ruta"));
		dir.setMapa(rs.getString("Mapa"));
		dir.setLatitud(rs.getDouble("Latitud"));
		dir.setLongitud(rs.getDouble("Longitud"));
		dir.setAltitud(rs.getDouble("Altitud"));
		dir.setFua(rs.getTimestamp("FUA"));
		dir.setIdCliente(rs.getInt("FK01_Cliente"));
		dir.setVisita(rs.getBoolean("visita"));
		
		return dir;
	}

}
