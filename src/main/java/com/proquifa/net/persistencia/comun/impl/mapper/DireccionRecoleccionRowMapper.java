package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Direccion;

import org.springframework.jdbc.core.RowMapper;

public class DireccionRecoleccionRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Direccion d = new Direccion();
		
		d.setIdDireccion(rs.getLong("PK_Direccion"));
		d.setPais(rs.getString("Pais"));
		d.setCodigoPostal(rs.getString("CP"));
		d.setEstado(rs.getString("Estado"));
		d.setMunicipio(rs.getString("Municipio"));
		d.setCiudad(rs.getString("Ciudad"));
		d.setCalle(rs.getString("Calle"));
		d.setColonia(rs.getString("Colonia"));
		d.setZona(rs.getString("Zona"));
		d.setRuta(rs.getString("Ruta"));
		d.setMapa(rs.getString("Mapa"));
		d.setAltitud(rs.getDouble("Altitud"));
		d.setLatitud(rs.getDouble("Latitud"));
		d.setLongitud(rs.getDouble("Longitud"));
		d.setNumExt(rs.getString("No_Ext"));
		d.setNumInt(rs.getString("No_Int"));
		d.setRegion(rs.getString("region"));
		d.setTipoRegion(rs.getString("Tipo_Region"));
		
		if(rs.getDate("FUA") != null){
			d.setFua(rs.getTimestamp("FUA"));
		}	
		
		d.setIdCliente(rs.getInt("FK01_Cliente"));
		
		return d;
	}

}
