package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Empleado;

import org.springframework.jdbc.core.RowMapper;

public class VendedorVisitaClienteRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Empleado empleado = new Empleado();
		empleado.setUsuario(rs.getString("Usuario"));
		empleado.setNombre(rs.getString("Nombre"));
		empleado.setIdEmpleado(rs.getLong("Clave"));
		
		return empleado;
	}
}
