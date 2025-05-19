/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class PendienteNotificacionRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 * Carta de fin de ano
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Pendiente pendiente = new Pendiente();
		Correo correo = new Correo();
		String tipoDPendiente = "";
		
		correo.setIdCorreo(rs.getLong("idCorreo"));
		correo.setFechaInicio(rs.getDate("fechaInicio"));
		correo.setOrigen(rs.getString("origen"));
		correo.setDestino(rs.getLong("destino"));
		correo.setMedio(rs.getString("medio"));
		correo.setFolioDocumento(rs.getString("docto"));
		correo.setCorreo(rs.getString("correo"));
		correo.setCcorreo(rs.getString("ccorreo"));
		correo.setCuerpoCorreo(rs.getString("cuerpoCorreo"));
		correo.setComentariosAdicionales(rs.getString("comentariosAdicionales"));
		correo.setNombreDestino(rs.getString("nombre"));
		correo.setFacturadaPor(rs.getString("facturadaPor"));
		
		correo.setIdContacto(rs.getLong("idContacto"));
		try{
			correo.setpaisDestino(rs.getString("pais"));	
		}
		catch(Exception ex){}
		try{
			correo.setnombreOrigen(rs.getString("Nombre"));	
		}
		catch(Exception ex){}
		tipoDPendiente = rs.getString("tipo");
		try{
			pendiente.setTipoPendiente(tipoDPendiente);	
		}
		catch(Exception ex){}
		correo.setArchivoAdjunto(rs.getString("folioDocumento"));
		
		StringTokenizer tokensDoctos = new StringTokenizer(correo.getFolioDocumento() , ",");
		String folioTemp = "";
		while(tokensDoctos.hasMoreTokens()){
			folioTemp = tokensDoctos.nextToken();
			break;
		}
		correo.setAsunto(rs.getString("asunto"));
		pendiente.setFolio(rs.getLong("folio"));
		pendiente.setDocto(folioTemp);
		pendiente.setPartida(correo.getIdCorreo().toString());
		pendiente.setFechaInicio(correo.getFechaInicio());
		pendiente.setResponsable("Recepción");
		
		pendiente.setCorreo(correo);
		return pendiente;
	}
}