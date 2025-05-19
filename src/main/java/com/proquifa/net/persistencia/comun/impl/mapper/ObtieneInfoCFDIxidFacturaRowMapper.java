package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.ComprobanteFiscal;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;

public class ObtieneInfoCFDIxidFacturaRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Factura totales =  new Factura();
		totales.setIdFactura(rs.getLong("idFactura"));
		totales.setNumeroFactura(rs.getLong("Factura"));
		totales.setMoneda(rs.getString("Moneda"));
		totales.setTipoCambio(rs.getDouble("TCambio"));
		totales.setImporte(rs.getFloat("ImporteF"));
//		return totales;
		Empresa emisor = new Empresa();
		emisor.setRfcEmpresa(rs.getString("RFCEmisor"));
		emisor.setRazonSocial(rs.getString("RazonSocialEmisor"));
		emisor.setCp(rs.getString("LugarExpedicion"));
		
		Cliente receptor = new Cliente();
		receptor.setNumeroDeCuenta(rs.getString("NumCtaPago"));
		receptor.setPais(rs.getString("PaisReceptor"));
		receptor.setRfc(rs.getString("RFCReceptor"));
		receptor.setRazonSocial(rs.getString("RazonSocialReceptor"));
		
		ArrayList<ConceptoFactura> conceptos = new ArrayList<ConceptoFactura>();
		ConceptoFactura concepto = new ConceptoFactura();
		concepto.setCantidad(rs.getInt("Cantidad"));
		concepto.setCodigo(rs.getString("Codigo"));
		concepto.setDescripcion(rs.getString("Descripcion"));
		concepto.setImporte(rs.getFloat("ImportePF"));
		conceptos.add(concepto);
		
		ComprobanteFiscal CFDI = new ComprobanteFiscal();
		CFDI.setEmisor(emisor);
		CFDI.setReceptor(receptor);
		CFDI.setConceptosList(conceptos);
		CFDI.setTotales(totales);		
		return CFDI;	 
	}

}