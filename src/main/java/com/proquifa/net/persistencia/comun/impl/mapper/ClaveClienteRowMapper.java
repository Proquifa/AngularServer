package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;

import org.springframework.jdbc.core.RowMapper;

public class ClaveClienteRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("clave"));
		
		return cliente;
	}

}
