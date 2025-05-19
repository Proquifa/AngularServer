package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

public class ConsultaInspectorCobrosRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		TiempoProceso tp = new TiempoProceso();
		
		tp.setEtapa(rs.getString("ETIQUETA"));
		
		tp.setComentarios(rs.getString("COMENTARIOS"));

		tp.setDocsCierre(rs.getString("DOC_CIERRE"));

		tp.setDoscResult1(rs.getString("DOCUMENT"));

		tp.setCobro(rs.getString("COBRO"));
		
		if ( rs.getString("ETIQUETA").trim().equals("COBRO_MONITOREO") || rs.getString("ETIQUETA").trim().equals("MONITOREO_CO_SC") ){
			try{
				tp.setFolio(rs.getString("idFactura"));
				tp.setDocumento(rs.getString("FolioComplementoPago"));
			}catch(Exception e){
				
			}
		}
		
		if (rs.getString("ETIQUETA").trim().equals("SOLICITUD_RECOLECCION") || rs.getString("ETIQUETA").trim().equals("RECOLECCION_CHEQUE")){
			if (rs.getString("FechaRequerida") != null){
				tp.setFechaRequerida(rs.getTimestamp("FechaRequerida"));
			}
			if (rs.getString("FechaRecoleccion") != null){
				tp.setFechaRecoleccion(rs.getTimestamp("FechaRecoleccion"));
			}
			if (rs.getString("FechaSolicitud") != null){
				tp.setFechaSolicitud(rs.getTimestamp("FechaSolicitud"));
			}
			tp.setPrioridad(rs.getString("Prioridad"));
			tp.setCommentRevision(rs.getString("ComentariosAdicionales"));
			tp.setRutaMensajeria(rs.getString("ruta"));
			tp.setZonaMensajeria(rs.getString("zona"));
			tp.setDocumento(rs.getString("Solicitud"));
		}
		else{

			if(rs.getDate("FENTREGA") != null){
				tp.setFechaEntrega(rs.getTimestamp("FENTREGA"));
			}
			if(rs.getDate("FPROGRAMACION") != null){
				tp.setFechaProgramacion(rs.getTimestamp("FPROGRAMACION"));
			}		
			if(rs.getDate("FREVISION") != null){
				tp.setFechaRevision(rs.getTimestamp("FREVISION"));
			}
			if(rs.getDate("FCOBRO") != null){
				tp.setFechaCobro(rs.getTimestamp("FCOBRO"));
			}
			tp.setResponsable(rs.getString("RESPONSABLE"));

			tp.setCommentRevision(rs.getString("COMENTARIOS_REV"));

			tp.setMensajero(rs.getString("MENSAJERO"));

			tp.setRevision(rs.getString("REVISION"));
			
			tp.setDoscResult2(rs.getString("DOCUMENT1"));

			tp.setMonto(Math.rint((rs.getDouble("MONTO")) * 1e2) / 1e2);
		}
		return tp;
	}

}
