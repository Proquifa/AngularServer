package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.ObjetivoCrecimiento;

public class MontosxNIAnteriorRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ObjetivoCrecimiento oc = new ObjetivoCrecimiento();
				
		oc.setNivelIngreso(rs.getString("NivelIngreso"));
		//oc.setMontoAnual((Math.rint(rs.getDouble("VentasUSD")) * 1e2) / 1e2);
		oc.setMontoAnual(rs.getDouble("VentasUSD"));
		oc.setNoClientes(rs.getLong("NoClientes"));		
		return oc;
	}
}
