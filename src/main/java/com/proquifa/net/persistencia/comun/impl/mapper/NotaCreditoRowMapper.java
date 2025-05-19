/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.NotaCredito;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class NotaCreditoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		NotaCredito nota = new NotaCredito();
		nota.setIdNota(rs.getLong("PK_Nota"));
		nota.setFecha(rs.getDate("Fecha"));
		nota.setFolio(rs.getString("AK_Folio"));
		nota.setIdCliente(rs.getLong("FK01_Cliente"));
		nota.setIdFactura(rs.getLong("FK02_Factura"));
		nota.setImporte(rs.getDouble("IMPORTEUDS"));
		nota.setMoneda(rs.getString("Moneda"));
		nota.setIva(rs.getDouble("IVA"));
		nota.setTCambio(rs.getDouble("TCambio"));
		nota.setSerie(rs.getString("Serie"));
		nota.setMedio(rs.getString("Medio"));
		nota.setFactura(rs.getLong("NoFac"));
		nota.setFpor(rs.getString("Factura"));
		nota.setCpedido(rs.getString("CPedido"));
		nota.setCpago(rs.getString("CPago"));
		nota.setEstado(rs.getString("EstadoNC"));
		nota.setTipo(rs.getString("tipoNC").toUpperCase());
		
		return nota;
	}

}
