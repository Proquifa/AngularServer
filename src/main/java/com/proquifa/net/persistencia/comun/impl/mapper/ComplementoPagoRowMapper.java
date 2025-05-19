package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

public class ComplementoPagoRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {		
		Factura totales = new Factura();
		totales.setIdCliente(rs.getLong("Cliente"));
		totales.setIdFactura(rs.getLong("idFactura"));
		totales.setNumeroFactura(rs.getLong("Factura"));
		totales.setFPago(rs.getDate("FPago"));
		totales.setMoneda(rs.getString("Moneda"));
		totales.setMonedaPago(rs.getString("MPago"));
		totales.setSerie(rs.getString("Serie"));
		totales.setIdDocFiscal(rs.getString("UUID"));
		totales.setMedioPago(rs.getString("MedioDePago"));
		return totales;

	}
	
}
