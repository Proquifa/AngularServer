package com.proquifa.net.persistencia.reportes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.proquifa.net.modelo.ventas.reportes.seguimientos.HistorialPartidaEnSeguimiento;

import org.springframework.jdbc.core.RowMapper;

public class HistorialPartidaEnSeguimientoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		HistorialPartidaEnSeguimiento hps = new HistorialPartidaEnSeguimiento();
		String contacto="",comentario="";
		Date frechaRealizacion= new Date();
		
		hps.setEstado(rs.getString("Origen"));
		hps.setVendedor(rs.getString("Realizo"));
		comentario=rs.getString("Comentarios");
		if(rs.wasNull()){
			hps.setComentarios("Pendiente");
		}else{
			hps.setComentarios(comentario);
		}
		contacto=rs.getString("Contacto");
		if(rs.wasNull()){
			hps.setContacto("Pendiente");
		}else{
			hps.setContacto(contacto);
		}
		hps.setFer(rs.getTimestamp("FER"));
		frechaRealizacion=rs.getTimestamp("FechaRealizacion");
		if(rs.wasNull()){
			hps.setFr(null);
		}else{
			hps.setFr(frechaRealizacion);
		}
		hps.setEstadoFinal(rs.getString("Estado"));
		hps.setRazonesESAC(rs.getString("RazonesESAC"));
		hps.setRazonesMonitor(rs.getString("RazonesMonitor"));
		hps.setCanceladaDesde(rs.getString("CanceladaDesde"));
		hps.setAceptado(rs.getBoolean("Aceptado"));
		hps.setCotizacionDestino(rs.getString("CotizacionHija"));
		
		return hps;
	}
}
