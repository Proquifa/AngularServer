package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;

import org.springframework.jdbc.core.RowMapper;

/**
 * Mapea la Consulta Avanzada para las notas de credito
 * @author isaac.garcia
 *
 */
public class NotaCreditoAvanzadaRowMapper implements RowMapper{
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		NotaCredito notaCredito = new NotaCredito();
		
		notaCredito.setFecha(rs.getDate("Fecha"));
		notaCredito.setNombreCliente(rs.getString("Cliente"));
		notaCredito.setAkFolio(rs.getLong("NotaCredito"));
		notaCredito.setFpor(rs.getString("Facturo"));
		notaCredito.setImporte(rs.getDouble("Importe"));
		notaCredito.setFactura(rs.getLong("Factura"));
		notaCredito.setCpedido(rs.getString("CPedido"));
		notaCredito.setEstado(rs.getString("Estado"));
		notaCredito.setEsac(rs.getString("Vendedor"));
		notaCredito.setCobrador(rs.getString("Cobrador"));
		//notaCredito.setTipoNota(rs.getString("Tipo"));
		notaCredito.setFacturaDestino(rs.getLong("FacturaDestino"));
		
		return notaCredito;
	}

}
