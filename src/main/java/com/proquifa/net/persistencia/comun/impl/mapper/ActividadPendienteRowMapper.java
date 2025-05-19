/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.ActividadPendiente;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ActividadPendienteRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ActividadPendiente pendiente = new ActividadPendiente();
		pendiente.setNombre(rs.getString("NOMBRE"));
		pendiente.setRol(rs.getString("ROL"));
		pendiente.setTotalPendiente(rs.getLong("TOTAL"));
		return pendiente;
	}

}
