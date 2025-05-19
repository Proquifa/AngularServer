package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.ObjetivoCrecimiento;

public class ObjetivoCrecimientoNIRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ObjetivoCrecimiento oc = new ObjetivoCrecimiento();
		//, , ,  
			oc.setIdObjetivoCrecimiento(rs.getLong("PK_ObjCrecimientoNI"));
			oc.setNivelIngreso(rs.getString("NivelIngreso"));
			oc.setPorcentajeAnual(rs.getDouble("PorcentajeAnual"));
			oc.setFua(rs.getTimestamp("FUA"));
			oc.setPorcentajeAnualFundamental(rs.getDouble("Fundamental"));
		
		return oc;
	}

}