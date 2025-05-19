/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Industria;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author misael.camanos
 *
 */
public class IndustriaCorporativoRowMapper implements RowMapper{
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Industria industria = new Industria();
		industria.setIdIndustriaCorporativo(rs.getLong("PK_Industria_Corporativo"));
		industria.setIdIndustria(rs.getLong("FK01_Industria"));
		industria.setIdCorporativo(rs.getLong("FK02_Corporativo"));
		industria.setActivo(true);
		return industria;
	}
}
