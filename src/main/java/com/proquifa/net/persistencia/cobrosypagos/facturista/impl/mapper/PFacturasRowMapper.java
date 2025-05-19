/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class PFacturasRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaFactura pfac = new PartidaFactura();
		
		pfac.setIdPFactura(rs.getLong("idPFactura"));
		pfac.setFactura(rs.getString("Factura"));
		pfac.setCantidad(rs.getInt("Cant"));
		pfac.setFabrica(rs.getString("Fabrica"));
		pfac.setCodigo(rs.getString("Codigo"));
		pfac.setConceptoPartida(rs.getString("Concepto"));
		pfac.setImporte(rs.getDouble("Importe"));
		pfac.setPedido(rs.getString("Pedido"));
		pfac.setClave(rs.getInt("Clave"));
		pfac.setPartidaFactura(rs.getInt("Part"));
		pfac.setFpor(rs.getString("FPor"));
		pfac.setCotiza(rs.getString("Cotiza"));
		pfac.setPpedido(rs.getInt("PPedido"));
		pfac.setCpedido(rs.getString("CPedido"));
		pfac.setNota(rs.getString("Nota"));
		pfac.setIdPCompra(rs.getLong("idPCompra"));
		pfac.setEstado(rs.getString("Estado"));

		return pfac;
	}
}