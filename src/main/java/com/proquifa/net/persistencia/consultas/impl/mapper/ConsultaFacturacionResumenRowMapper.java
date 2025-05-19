package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;

public class ConsultaFacturacionResumenRowMapper implements RowMapper {


	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		Facturacion facturacion = new Facturacion();

		String etiqueta = rs.getString("Etiqueta");
		if (etiqueta.equals("Cancelacion") || etiqueta.equals("Refacturacion") || etiqueta.equals("FacturaRemision")){
			if (rs.getString("FechaR") != null){
				facturacion.setFechaR(rs.getTimestamp("FechaR"));
			}
			facturacion.setRemision(rs.getString("Remision"));
			facturacion.setFecha(rs.getTimestamp("Fecha"));
			facturacion.setFactura(rs.getString("Factura"));
			facturacion.setResponsable(rs.getString("Responsable"));
			facturacion.setTipo(rs.getString("Tipo"));
			facturacion.setMedio(rs.getString("Medio"));
			facturacion.setRazones(rs.getString("Razones"));
			facturacion.setComentarios(rs.getString("Comentarios"));
			facturacion.setAutorizo(rs.getString("Autorizo"));
			if (rs.getString("TCambio") != null){
				facturacion.setPdolar(rs.getDouble("TCambio"));
			}
			if (rs.getString("FInicio") != null){
				facturacion.setFinicio(rs.getTimestamp("FInicio"));
			}
			if (rs.getString("FFin") != null){
				facturacion.setFfin(rs.getTimestamp("FFin"));
			}
		}
		else if(etiqueta.equals("FPorAdelantado")){
			facturacion.setPedido(rs.getString("Pedido"));
			facturacion.setFactura(rs.getString("Factura"));
			facturacion.setResponsable(rs.getString("Facturo"));
			facturacion.setTcambio(rs.getDouble("TCambio"));
			facturacion.setTipo(rs.getString("Tipo"));
			facturacion.setMedio(rs.getString("Medio"));
			facturacion.setFechaFacturacion(rs.getTimestamp("FPorAdelantado"));
			facturacion.setFechaTramitacion(rs.getTimestamp("FTramitacionPSC"));
			facturacion.setDocumento(rs.getString("DoctoR"));
			facturacion.setContacto(rs.getString("Realizo"));
			facturacion.setFechaInicioPortal(rs.getTimestamp("FIPortal"));
			facturacion.setFechaFinPortal(rs.getTimestamp("FFPortal"));
			facturacion.setDocument(rs.getString("ComprobantePortal"));
			
		}
		else if(etiqueta.equals("EnvioFactura")){
			facturacion.setFechaFacturacion(rs.getTimestamp("FechaFacturacion"));
			facturacion.setFechaEnvio(rs.getTimestamp("FechaEnvio"));
			facturacion.setFactura(rs.getString("Factura"));
			facturacion.setResponsable(rs.getString("Responsable"));
			facturacion.setFechaSAP(rs.getTimestamp("FechaSAP"));
			facturacion.setFechaProquifa(rs.getTimestamp("FechaProquifaNet"));
			facturacion.setComentarios(rs.getString("CuerpoCorreo"));
			facturacion.setContacto(rs.getString("Contacto"));
		}
		else if(etiqueta.equals("MonitoreoSC")){
			facturacion.setFactura(rs.getString("factura"));
			facturacion.setFechaTramitacion(rs.getTimestamp("FechaTramitacionPSC"));
			facturacion.setImporte(rs.getDouble("MontoPagado"));
			facturacion.setMonedaPago(rs.getString("MonedaPagada"));
			facturacion.setTcambio(rs.getDouble("TCPagado"));
			facturacion.setDocumento(rs.getString("DocumentoAmpara"));
			facturacion.setTotal(rs.getDouble("Monto"));
			facturacion.setMedioPago(rs.getString("MedioPago"));
			facturacion.setComentarios(rs.getString("ComentariosValidacion"));
			facturacion.setMoneda(rs.getString("Moneda"));
			facturacion.setFep(rs.getTimestamp("FEP"));
			facturacion.setFechaPago(rs.getDate("FPago"));
			facturacion.setFechaCobro(rs.getTimestamp("FechaValidacionCobro"));
			facturacion.setFechaAsosiacion(rs.getTimestamp("FechaAsosiacionPago"));
			facturacion.setFolioNC(rs.getString("FolioNC"));
			facturacion.setClave(rs.getString("Clave"));
		}
		else if (etiqueta.equals("Factura") ){
			facturacion.setFechaFacturacion(rs.getTimestamp("FechaFacturacion"));
			facturacion.setFactura(rs.getString("Factura"));
			facturacion.setResponsable(rs.getString("Facturo"));
			facturacion.setTcambio(rs.getDouble("TCambio"));
			facturacion.setTipo(rs.getString("Tipo"));
			facturacion.setMedio(rs.getString("Medio"));
			facturacion.setFechaEnvio(rs.getTimestamp("FechaEnvio"));
			facturacion.setComentarios(rs.getString("CuerpoCorreo"));
			facturacion.setContacto(rs.getString("Contacto"));
			facturacion.setDocumento(rs.getString("DocumentoAmpara"));
			facturacion.setFechaProquifa(rs.getTimestamp("FechaProquifaNet"));
			facturacion.setMonedaPago(rs.getString("MonedaPagada"));
			facturacion.setImporte(rs.getDouble("MontoPagado"));
			facturacion.setFechaPago(rs.getDate("FPago"));
			facturacion.setTcambioCobro(rs.getDouble("TCPagado"));
			facturacion.setRazones(rs.getString("ComentariosValidacion"));
			facturacion.setFpor(rs.getString("Fpor"));
			facturacion.setFolioNC(rs.getString("FolioNC"));
			facturacion.setProforma(rs.getString("proforma"));
		}
		return facturacion;
	}
}
