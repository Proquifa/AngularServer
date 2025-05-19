package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;

public class ClientesCotizacionesRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("Clave"));
		cliente.setNombre(rs.getString("Nombre"));
		cliente.setMonto(rs.getDouble("Monto"));
		
		return cliente;
	}

}
