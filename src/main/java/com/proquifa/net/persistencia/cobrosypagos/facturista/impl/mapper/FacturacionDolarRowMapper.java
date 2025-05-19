
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;

import org.springframework.jdbc.core.RowMapper;

public class FacturacionDolarRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Facturacion facturacion = new Facturacion();

		facturacion.setFecha(rs.getDate("fecha"));
		facturacion.setFpor(rs.getString("FPor"));
		facturacion.setFactura(rs.getString("Factura"));
		facturacion.setTipo(rs.getString("Tipo"));
		facturacion.setMedio(rs.getString("Medio"));
		facturacion.setCpedido(rs.getString("CPedido"));
		facturacion.setNombre_cliente(rs.getString("Nombre"));
		facturacion.setImporte(rs.getDouble("Importe"));
		facturacion.setTotaliva(rs.getDouble("TotalIVA"));
		facturacion.setTotal(rs.getDouble("Total"));
		facturacion.setEstado(rs.getString("Estado"));
		facturacion.setRefacturada(rs.getString("Refacturada"));
		facturacion.setCPago(rs.getString("CPago"));
		facturacion.setFactura(rs.getString("Factura"));
		facturacion.setDrc(rs.getString("drc"));
		if (rs.getString("fep") != null){
			facturacion.setFep(rs.getDate("fep"));
		}
		facturacion.setPdolar(rs.getDouble("TCambio"));
		facturacion.setMedioPago(rs.getString("medioPago"));
		facturacion.setNumPiezas(rs.getInt("Part"));
		facturacion.setFechaPago(rs.getDate("FPago"));
		facturacion.setRfc(rs.getString("CURP"));
		facturacion.setRsocial(rs.getString("RSocial"));
		facturacion.setMoneda(rs.getString("Moneda"));

		facturacion.setTotalMN(rs.getDouble("TotalMN"));
		facturacion.setImporteMN(rs.getDouble("ImporteMN"));
		facturacion.setTotalivaMN(rs.getDouble("TotalIvaMN"));
		facturacion.setEsac(rs.getString("Vendedor"));
		facturacion.setCobrador(rs.getString("Cobrador"));
		facturacion.setFechaCancelacion(rs.getDate("FechaCancelacion"));
		facturacion.setCuentaBanco(rs.getString("CuentaBanco"));

		facturacion.setUuid(rs.getString("uuid"));
		
		try {facturacion.setReferencia(rs.getString("Referencia"));} catch (Exception e) {}
		try {facturacion.setDoctoR(rs.getString("DoctoR"));} catch (Exception e) {}
		
		return facturacion;
	}
}