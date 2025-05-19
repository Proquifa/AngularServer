/**
 * 
 */
package com.proquifa.net.persistencia.ventas.admoncomunicacion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.admoncomunicacion.Llamada;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class LlamadaRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Llamada infoLlamada = new Llamada();
		String comentario="";
		infoLlamada.setFolio(rs.getLong("Folio"));
		infoLlamada.setFecha(rs.getTimestamp("Fecha"));
		infoLlamada.setRecibio(rs.getString("Recibio"));
		infoLlamada.setDestino(rs.getString("Destino"));
		infoLlamada.setEmpresa(rs.getString("Empresa"));
		infoLlamada.setContacto(rs.getString("Contacto"));
		infoLlamada.setAsunto(rs.getString("Asunto"));
		infoLlamada.setAtendio(rs.getString("Atendio"));
		infoLlamada.setRespuesta(rs.getString("Respuesta"));
		infoLlamada.setMensaje(rs.getBoolean("Mensaje"));
		infoLlamada.setOrigen(rs.getString("TOrigen"));
		infoLlamada.setCotiza(rs.getString("Cotiza"));
		infoLlamada.setEstado(rs.getString("Estado"));
		infoLlamada.setEnteradoNotificacion(rs.getBoolean("chkEstado"));
		infoLlamada.setEnTiempo(rs.getString("ENTIEMPO"));
		infoLlamada.setRequsicionCotiza(rs.getBoolean("Coti"));
		comentario = rs.getString("Comentarios");
		if(rs.wasNull()){
			infoLlamada.setComentarios("ND");
		}else{
			infoLlamada.setComentarios(comentario);
		}
			
		return infoLlamada;
	}
}