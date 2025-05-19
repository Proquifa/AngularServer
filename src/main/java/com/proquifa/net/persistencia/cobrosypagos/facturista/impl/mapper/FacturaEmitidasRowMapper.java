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
public class FacturaEmitidasRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura fac = new Factura();
		fac.setRazonSocialFPor(rs.getString("RazonSocial"));
		fac.setRfc_FPor(rs.getString("RFC"));
		fac.setNumeroFactura(rs.getLong("Factura"));
		fac.setRfc_Cliente(rs.getString("RFC_Cliente"));
		fac.setNombre_Cliente(rs.getString("NombreCliente"));
		fac.setFecha(rs.getTimestamp("Fecha"));
		
		fac.setEstado(rs.getString("Estado"));
		fac.setFacturadoPor(rs.getString("Alias"));
		fac.setSerie(rs.getString("Serie"));
		fac.setIdEmpresaFactura(rs.getLong("idEmpresa"));
		
		fac.setImporte(rs.getFloat("IMPORTE_MN"));//Math.rint((rs.getDouble("")) * 1e2) / 1e2
		fac.setIva((Math.rint(rs.getDouble("IVA_MN")) * 1e2) / 1e2);
		fac.setMontoFacturaDLS((Math.rint(rs.getDouble("IMPORTE_USD")) * 1e2) / 1e2);
		fac.setIvaDLS((Math.rint(rs.getDouble("IVA_USD")) * 1e2) / 1e2);
		fac.setMoneda(rs.getString("Moneda"));
		fac.setTipoCambio((Math.rint(rs.getDouble("TCambio")) * 1e2) / 1e2);
		fac.setCobrador(rs.getString("Cobrador"));
		fac.setEsac(rs.getString("Vendedor"));
		fac.setUuid(rs.getString("UUID"));
		try {fac.setFolioPC(rs.getString("FolioPC"));} catch (Exception e) {}
		
		fac.setCuentaBanco(rs.getString("CuentaBanco"));
	
		
		return fac;
	}
}