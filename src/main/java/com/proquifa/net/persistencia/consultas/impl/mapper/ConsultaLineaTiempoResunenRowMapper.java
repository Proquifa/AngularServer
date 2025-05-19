package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.TiempoProceso;

import org.springframework.jdbc.core.RowMapper;

public class ConsultaLineaTiempoResunenRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		TiempoProceso tp = new TiempoProceso();
		
		tp.setEtapa(rs.getString("ETIQUETA"));
		if (rs.getString("ETIQUETA").equals("Facturación por Adelantado") || rs.getString("ETIQUETA").equals("Envío de Factura")
				|| rs.getString("ETIQUETA").equals("Monitoreo de Cobro SC") || rs.getString("ETIQUETA").equals("Factura") ){
			tp.setFechaInicio(rs.getTimestamp("FINICIO"));
			tp.setFechaFin(rs.getTimestamp("FFIN"));
			tp.setTotalProceso(rs.getLong("TT"));
			tp.setResponsable(rs.getString("RESPONSABLE"));
		}
		else{
			if(rs.getDate("FINICIO") != null){
				tp.setFechaInicio(rs.getTimestamp("FINICIO"));
			}
			if(rs.getDate("FFIN") != null){
				tp.setFechaFin(rs.getTimestamp("FFIN"));
			}
			tp.setEtapa(rs.getString("ETIQUETA"));
			tp.setTotalProceso(rs.getLong("TT"));
			try{
				tp.setContacto(rs.getString("CONTACTO"));
				tp.setReferencia(rs.getString("REFERENCIA"));
				tp.setMonto(Math.rint((rs.getDouble("MONTO")) * 1e2) / 1e2);
				
				if(rs.getString("MEDIO") == null){
					tp.setMedio("NO DISPONIBLE");
				}else{
					tp.setMedio(rs.getString("MEDIO"));
				}

				if(rs.getString("COMENTARIOS") == null){
					tp.setComentarios("NO DISPONIBLE");
				}else{
					tp.setComentarios(rs.getString("COMENTARIOS"));
				}
				
				if(rs.getString("CONFORME") == null){
					tp.setConforme("NO DISPONIBLE");
				}else if(rs.getBoolean("CONFORME") == true){
					tp.setConforme("SI");
				}else{
					tp.setConforme("NO");
				}
				
				if(tp.getEtapa().equals("ENTREGA")){
					tp.setDocumento(rs.getString("DOCUMENT"));
				}else{
					tp.setPedimento(rs.getString("DOCUMENT"));

					tp.setDocumento(rs.getString("DOCUMENT"));
				}
				
				tp.setLote(rs.getString("LoteTxt"));
				tp.setTipo(rs.getString("TIPO"));

				if(tp.getEtapa().equals("COOBRADA")){
					tp.setFechaProgramacion(rs.getTimestamp("QUIENCOMPRA"));
					tp.setFechaDispobible(rs.getTimestamp("MEDIO"));
					tp.setEtapaPadre(rs.getString("CONFORME"));
					tp.setConforme(null);
					tp.setMedio(null);
					tp.setPedimento(null);
					tp.setConforme(null);
					tp.setFolio(rs.getString("FolioCompPago"));
				}else{
					tp.setFpor(rs.getString("QUIENCOMPRA"));
				}
				if(tp.getEtapa().equals("CERRAR RUTA")){
					if(tp.getComentarios().equals("NO DISPONIBLE")){
						tp.setComentarios(null);
					}
				}

				if(rs.getTimestamp("FECHACOBRO") == null){
					tp.setFechaCobro(null);
				}else{
					tp.setFechaCobro(rs.getTimestamp("FECHACOBRO"));
				}

				if(tp.getEtapa().equals("COBRO")){
					tp.setEtapaPadre(tp.getReferencia());
					tp.setReferencia(null);
				}
				if(tp.getEtapa().equals("COOBRADA_SC")){
					tp.setEtapaPadre(rs.getString("CONFORME"));
					tp.setConforme(null);
					tp.setFolio(rs.getString("FolioCompPago"));
				}
				
			}catch (Exception e) {
			}
			
			tp.setResponsable(rs.getString("RESPONSABLE"));
			
			
			

			
			
			
			if (rs.getString("ETIQUETA").equals("COBRO") || rs.getString("ETIQUETA").equals("REVISION")
					|| rs.getString("ETIQUETA").equals("ENTREGA") || rs.getString("ETIQUETA").equals("ENVIO") 
					|| rs.getString("ETIQUETA").equals("FACTURACION") || rs.getString("ETIQUETA").equals("COOBRADA")
					|| rs.getString("ETIQUETA").equals("REFACTURACION") || rs.getString("ETIQUETA").equals("COOBRADA_SC") ){
				
				try{
					tp.setEstado(rs.getString("Estado"));
					tp.setFechaCancelacion(rs.getTimestamp("FCancelacion"));
				}catch (Exception e) {
				}
				if(tp.getComentarios().equalsIgnoreCase("NO DISPONIBLE")){
					tp.setComentarios(null);
				}
			}

			

			

			

			try{
				tp.setTCambio(rs.getDouble("TCAMBIO"));
				tp.setFechaInicioPortal(rs.getTimestamp("FIPortal"));
				tp.setFechaFinPortal(rs.getTimestamp("FFPortal"));
			}catch (Exception e) {
			}

		}
		return tp;
	}

}
