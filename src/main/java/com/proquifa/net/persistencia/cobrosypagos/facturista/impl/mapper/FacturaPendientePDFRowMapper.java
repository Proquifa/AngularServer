package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

public class FacturaPendientePDFRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura fac = new Factura();
		fac.setIdFactura(rs.getLong("idFactura"));
		Long numFac = rs.getLong("Factura");
		fac.setNumeroFactura(numFac);
		fac.setFacturadoPor(rs.getString("Fpor"));
		
		return fac;
	}
}
 