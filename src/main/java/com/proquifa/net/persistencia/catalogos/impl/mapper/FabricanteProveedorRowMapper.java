/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Fabricante;

/**
 * @author jmcamanos
 *
 */
public class FabricanteProveedorRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Fabricante fabricante = new Fabricante();
		fabricante.setIdFabricante(rs.getLong("FK02_Fabricante"));
		return fabricante;
	}
}
