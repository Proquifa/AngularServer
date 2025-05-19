package com.proquifa.net.persistencia.contabilidad.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cuentaContable.PPoliza;

public class PPolizaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		PPoliza pp = new PPoliza();
		pp.setIdPPoliza(rs.getInt("PK_PPoliza"));
		pp.setIdPoliza(rs.getInt("FK01_Poliza"));
		pp.setIdCuentaContable(rs.getInt("FK02_CuentaContable"));
		pp.setIdCentroCosto(rs.getInt("FK03_CentroCosto"));
		pp.setDescripcion(rs.getString("Descripcion"));
		pp.setMonto(rs.getDouble("Monto"));
		pp.setMontoIVA(rs.getDouble("MontoIVA"));
		pp.setTipoIVA(rs.getBoolean("TipoIVA"));
		pp.setTipo(rs.getBoolean("Tipo"));
		return pp;
	}

}
