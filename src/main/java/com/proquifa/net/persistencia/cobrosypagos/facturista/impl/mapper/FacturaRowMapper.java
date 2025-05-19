/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class FacturaRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura factura = new Factura();
		String pedidoInterno="";
		factura.setIdFactura(rs.getLong("idFactura"));
		factura.setNumeroFactura(rs.getLong("factura"));
		factura.setFecha(rs.getDate("fecha"));
		factura.setCondicionesPago(rs.getString("cpago"));
		factura.setIdCliente(rs.getLong("cliente"));
		factura.setMoneda(rs.getString("moneda"));
		factura.setImporte(rs.getFloat("importe"));
		factura.setIva(rs.getDouble("iva"));
		factura.setEstado(rs.getString("estado"));
		factura.setFacturadoPor(rs.getString("fpor"));
		factura.setPedido(rs.getString("pedido"));
		factura.setTipoCambio(rs.getDouble("tcambio"));
		pedidoInterno = rs.getString("cpedido");
		if(rs.wasNull()){
			factura.setCpedido("ND");
		}else{
			factura.setCpedido(pedidoInterno);
		}
		factura.setImprimirTipoCambio(rs.getBoolean("imprimirTC"));
		factura.setFolioEntrega(rs.getString("folioEntrega"));
		factura.setRemision(rs.getBoolean("deremision"));
		factura.setOrdenCompra(rs.getString("ordencompra"));
		factura.setTipo(rs.getString("tipo"));
		factura.setSerie(rs.getString("serie"));
		factura.setMedio(rs.getString("medio"));
		factura.setDocumentoRecibido(rs.getLong("doctoR"));
		factura.setNombre_Cliente(rs.getString("Nombre"));
		factura.setContraRecibo(rs.getString("ContraRecibo"));
		factura.setFrevision(rs.getDate("FRevision"));
		factura.setMedioPago(rs.getString("MedioPago"));
		factura.setMontoAPagar(rs.getDouble("Monto"));
		factura.setMonedaPago(rs.getString("MonedaPago"));
		factura.setFEPago(rs.getDate("FEP"));
		factura.setFPago(rs.getDate("FechaPago"));
		factura.setFolioPC(rs.getString("FolioPC"));
		factura.setMontoRealPagado(rs.getDouble("MontoPagado"));
		
		return factura;
	}
}