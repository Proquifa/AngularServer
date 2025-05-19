package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.visitas.ObtenerPrecios;

import org.springframework.jdbc.core.RowMapper;

public class ObtenerPreciosRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ObtenerPrecios precio = new ObtenerPrecios();
		precio.setGrafica(rs.getString("Grafica"));
		precio.setClave(rs.getString("Clave"));
		precio.setCant(rs.getInt("Cant"));
		precio.setEstado(rs.getString("Estado"));
		precio.setMes(rs.getInt("Mes"));
		precio.setPrecioDolares(rs.getDouble("PrecioDolares"));
		precio.setPrecioPesos(rs.getDouble("PrecioPesos"));
		precio.setPrecioEuros(rs.getDouble("PrecioEuros"));
		
		return precio;
	}
}
