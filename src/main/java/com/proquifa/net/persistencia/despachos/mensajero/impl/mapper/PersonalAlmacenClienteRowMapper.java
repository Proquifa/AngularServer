package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;

public class PersonalAlmacenClienteRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PersonalAlmacenCliente p = new PersonalAlmacenCliente();
		
		p.setIdPersonal(rs.getLong("PK_PersonalAC"));
		p.setIdCliente(rs.getLong("FK01_Cliente"));
		p.setPuesto(rs.getString("Puesto"));
		p.setNombre(rs.getString("Nombre"));
		
		return p;
	}

}
