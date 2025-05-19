package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

import org.springframework.jdbc.core.RowMapper;

public class PartidaFacturaRowMapper  implements RowMapper{
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Factura factura = new Factura();
		factura.setFacturadoPor(rs.getString("fpor"));
		factura.setIdFactura(rs.getLong("Factura"));
		return factura;
	}

}
