package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;

public class FacturaElectronicaRowMapper implements RowMapper {

	public FacturaElectronicaRowMapper() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Factura mapRow(ResultSet rs, int arg1) throws SQLException {
		Factura factura = new Factura();
		
		factura.setNumeroFactura(rs.getLong("Folio"));
		factura.setCondicionesPago(rs.getString("CondicionesPago"));
		factura.setIdCliente(rs.getLong("Cliente"));
		factura.setMoneda(rs.getString("Moneda"));
		factura.setImporte(rs.getFloat("Total"));
		factura.setIva(rs.getDouble("ImpuestosTasaOCuota"));
		factura.setEstado(rs.getString("Estado"));
		factura.setFacturadoPor(rs.getString("Alias"));
		factura.setTipoCambio(rs.getDouble("TipoCambio"));
		factura.setTipo(rs.getString("Tipo"));
		factura.setSerie(rs.getString("Serie"));
		factura.setMedio(rs.getString("Medio"));
		factura.setUuid(rs.getString("SAtUUID"));

		factura.setIdEmpresaFactura(rs.getLong("EmpresaEmisor"));
		
		return factura;
	}

}
