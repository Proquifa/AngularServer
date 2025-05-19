/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Referencia;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author jmcamanos
 *
 */
public class ReferenciaSolicitudRowMapper implements RowMapper{
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Referencia referencia=new Referencia();
		referencia.setIdReferencia(rs.getLong("PK_Referencia"));
		referencia.setNombreResonsable(rs.getString("Nombre"));
		return referencia;
	}
}
