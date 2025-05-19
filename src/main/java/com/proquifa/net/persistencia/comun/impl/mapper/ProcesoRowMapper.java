/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Proceso;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ProcesoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Proceso proceso = new Proceso();
		proceso.setIdProceso(rs.getLong("PK_Proceso"));
		proceso.setNombre(rs.getString("nombre"));
		proceso.setIdEmpleado(rs.getLong("FK01_usuario"));
		return proceso;
	}

}
