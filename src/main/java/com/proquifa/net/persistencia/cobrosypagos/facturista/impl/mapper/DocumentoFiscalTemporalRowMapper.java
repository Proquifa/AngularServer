package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

import org.springframework.jdbc.core.RowMapper;

public class DocumentoFiscalTemporalRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura fac = new Factura();	
		
		fac.setMedio(rs.getString("Medio"));
		fac.setSerie(rs.getString ("Serie"));
		fac.setTipo(rs.getString ("Tipo"));
		fac.setTipoCambio(rs.getDouble("TCambio"));
		fac.setFacturadoPor(rs.getString ("FPor"));
		fac.setEstado(rs.getString ("Estado"));
		fac.setIva(rs.getDouble("IVA"));
		fac.setImporte(rs.getFloat("Importe"));
		fac.setMoneda(rs.getString ("Moneda"));
		fac.setIdCliente(rs.getLong ("Cliente"));
		fac.setCondicionesPago(rs.getString("CPago"));
		fac.setFecha(rs.getDate("Fecha"));
		fac.setIdDocumentoFiscal(rs.getLong("PK_DocumentoFiscalTemporal"));
		
		return fac;
		}
}
