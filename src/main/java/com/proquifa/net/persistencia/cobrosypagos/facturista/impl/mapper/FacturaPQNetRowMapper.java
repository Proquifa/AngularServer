/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class FacturaPQNetRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura fac = new Factura();
		fac.setRazonSocialFPor(rs.getString("RazonSocial"));
		fac.setRfc_FPor(rs.getString("RFC"));
		fac.setNumeroFactura(rs.getLong("Factura"));
		fac.setRfc_Cliente(rs.getString("RFC_Cliente"));
		fac.setNombre_Cliente(rs.getString("NombreCliente"));
		fac.setFecha(rs.getTimestamp("Fecha"));
		fac.setImporte(rs.getFloat("Importe"));
		fac.setEstado(rs.getString("Estado"));
		fac.setFacturadoPor(rs.getString("Alias"));
		fac.setSerie(rs.getString("Serie"));
		fac.setIdEmpresaFactura(rs.getLong("idEmpresa"));
		
		return fac;
	}
}