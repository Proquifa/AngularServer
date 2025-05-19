/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;

/**
 * @author bryan.magana
 *
 */
public class PDocumentoFiscalTemporalRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaFactura pfac = new PartidaFactura();	
		
		pfac.setFactura(rs.getString("Factura"));
		pfac.setFpor(rs.getString ("FPOR"));
		pfac.setCpedido(rs.getString("Cpedido"));
		pfac.setPpedido(rs.getInt("PPedido"));
		pfac.setImporte(rs.getDouble("Importe"));
		pfac.setCantidad(rs.getInt("Cant"));
		pfac.setFabrica(rs.getString("Fabrica"));
		pfac.setCodigo(rs.getString("Codigo"));
		pfac.setConceptoPartida(rs.getString("Concepto"));
		pfac.setCotiza(rs.getString("Cotiza"));
		pfac.setNota(rs.getString("Nota"));
		pfac.setDoctoFacturacionTemporal(rs.getLong("FK01_DocumentoFiscalTemporal"));
		pfac.setPartidaFactura(rs.getInt("Part"));
		pfac.setIdPFactura(rs.getLong("PK_PDocumentoFiscalTemporal"));
		
		return pfac;
		}
}
