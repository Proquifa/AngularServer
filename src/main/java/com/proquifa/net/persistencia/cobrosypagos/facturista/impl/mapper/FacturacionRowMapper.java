package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;

import org.springframework.jdbc.core.RowMapper;

public class FacturacionRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		Facturacion fac = new Facturacion();
		fac.setRefacturada(rs.getString("Refacturada"));
		fac.setTotaliva(rs.getDouble("totalIVA"));
		fac.setTotal(rs.getDouble("Total"));
		fac.setFecha(rs.getDate("Fecha"));
		fac.setFactura(rs.getString("Factura"));
		fac.setCpedido(rs.getString("CPedido"));
		fac.setFpor(rs.getString("FPor"));
		fac.setTipo(rs.getString("Tipo"));
		fac.setMedio(rs.getString("Medio"));
		fac.setImporte(rs.getDouble("Importe"));
		fac.setMoneda(rs.getString("Moneda"));
		fac.setIva(rs.getDouble("Iva"));
		fac.setEstado(rs.getString("Estado"));
		fac.setNombre_cliente(rs.getString("Nombre"));
		fac.setFacturaantigua(rs.getString("FacturaAntigua"));
		fac.setFacturanueva(rs.getString("FacturaNueva"));
		fac.setRazones(rs.getString("Razones"));
		fac.setAutorizo(rs.getString("Autorizo"));
		fac.setRazonpop(rs.getString("razonPop"));
		fac.setPdolar(rs.getDouble("PDolar"));
		return fac;
	}
}