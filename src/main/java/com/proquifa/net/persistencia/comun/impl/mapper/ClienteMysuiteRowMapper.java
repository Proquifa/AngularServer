/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;

/**
 * @author fmartinez
 *
 */
public class ClienteMysuiteRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Cliente receptor = new Cliente();
		receptor.setMedioPago(rs.getString("MedioDePAgo"));
		receptor.setNumeroDeCuenta(rs.getString("NumeroDeCuenta"));
		receptor.setRfc(rs.getString("RFC"));
		receptor.setTaxID(rs.getString("TaxID"));
		receptor.setCorreoElectronico(rs.getString("MailFElectronica"));
		receptor.setNombre(rs.getString("Nombre"));
		receptor.setCodigoPostal(rs.getString("CP"));
		receptor.setPais(rs.getString("pais"));
		receptor.setEstado(rs.getString("estado"));
		receptor.setDelegacion(rs.getString("Delegacion"));
		receptor.setCalle(rs.getString("calle"));
		
		return receptor;
	}

}
