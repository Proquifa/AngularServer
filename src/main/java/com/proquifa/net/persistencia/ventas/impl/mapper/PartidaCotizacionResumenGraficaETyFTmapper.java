package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;

import org.springframework.jdbc.core.RowMapper;

public class PartidaCotizacionResumenGraficaETyFTmapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PartidaCotizacion pcotiza = new PartidaCotizacion();
		pcotiza.setIdPartidaCotizacion(rs.getLong("idPCotiza"));
		pcotiza.setFechaGeneracion(rs.getTimestamp("FechaFin"));
		pcotiza.setCotizacion(rs.getString("Clave"));
		pcotiza.setCantidad(rs.getInt("PIEZAS"));
		pcotiza.setPartida(rs.getLong("PARTIDAS"));
		pcotiza.setClasifOrigen(rs.getString("Clasif"));
		pcotiza.setClasificacion(rs.getString("Clasificacion"));
		
		if (rs.getString("Fabrica").length()>0){
			pcotiza.setFabrica(rs.getString("Fabrica"));
		}else{
			pcotiza.setFabrica("DESCONOCIDO");
		}
		
		
		pcotiza.setCliente(rs.getString("CLIENTE"));
		pcotiza.setSituacion(rs.getString("ESTATUS"));
		pcotiza.setEstado(rs.getString("ESTADO"));
		pcotiza.setCotizo(rs.getString("COTIZO"));
		pcotiza.setMedioEnvio(rs.getString("MSalida"));
		pcotiza.setPrecio(rs.getFloat("MONTO"));
		pcotiza.setCodigo(rs.getString("Codigo"));
		pcotiza.setConforme(rs.getBoolean("ET"));
		pcotiza.setTipo(rs.getString("TIPO"));
		
		return pcotiza;
	}

}
