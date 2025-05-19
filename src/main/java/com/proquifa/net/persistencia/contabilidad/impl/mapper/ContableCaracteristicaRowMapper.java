package com.proquifa.net.persistencia.contabilidad.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cuentaContable.ContableCaracteristica;

@SuppressWarnings("rawtypes")
public class ContableCaracteristicaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ContableCaracteristica contable = new ContableCaracteristica();
		//contable.setActivo(rs.getBoolean("Activo"));
		contable.setActivo(true);
		contable.setDescripcion(rs.getString("Descripcion"));
		contable.setIdContableCaracteristica(rs.getInt("PK_ContablesC"));
		//contable.setTipo(rs.getString("Tipo"));
		contable.setTipo("Tipo");
		return contable;
	}

}
