/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ConsultaFacturacionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Facturacion f = new Facturacion();
		Double montoIVADLL = 0.0;
		
		f.setFecha(rs.getDate("Fecha"));
		f.setFactura(rs.getString("Factura"));
		f.setFpor(rs.getString("FPor"));
		f.setTipo(rs.getString("Tipo"));
		f.setMedio(rs.getString("Medio"));
		f.setImporte(Math.rint((rs.getDouble("Importe")) * 1e2) / 1e2);
		f.setMoneda(rs.getString("Moneda"));
		f.setEstado(rs.getString("Estado"));
		f.setCpedido(rs.getString("CPedido"));
		f.setIva(rs.getDouble("IVA"));
		f.setNombre_cliente(rs.getString("Nombre"));
		f.setRefacturada(rs.getString("CUANTOS"));
		f.setTotal(Math.rint((rs.getDouble("MONTODLL")) * 1e2) / 1e2);
		f.setEsac(rs.getString("Vendedor"));
		f.setCobrador(rs.getString("Cobrador"));
		
		montoIVADLL = (f.getTotal() * f.getIva()) + f.getTotal();
		f.setTotaliva(montoIVADLL);
		
		return f;
	}

}
