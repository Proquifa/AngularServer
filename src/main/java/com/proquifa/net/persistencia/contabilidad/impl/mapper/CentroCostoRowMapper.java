package com.proquifa.net.persistencia.contabilidad.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cuentaContable.CentroCosto;

public class CentroCostoRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CentroCosto centro = new CentroCosto();
		centro.setIdCentroCosto(rs.getInt("PK_CentroCosto"));
		centro.setTipo(rs.getString("Tipo"));
		centro.setDescripcion(rs.getString("Descripcion"));
		centro.setActivo(rs.getBoolean("Activo"));
		return centro;
	}

}
