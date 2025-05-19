/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.ReferenciasBancarias;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class ReferenciasBancariasClienteRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ReferenciasBancarias ref = new ReferenciasBancarias();
		ref.setBanco(rs.getString("Banco"));
		ref.setCuenta(rs.getString("NoCuenta"));
		ref.setCuentaClabe(rs.getString("Clabe"));
		ref.setMoneda(rs.getString("Moneda"));
		ref.setReferenciaCliente(rs.getString("refCliente"));
		ref.setSucursal(rs.getString("Sucursal"));
		
		return ref;
	}
}