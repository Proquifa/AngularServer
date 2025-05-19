/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Subproceso;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class SubprocesoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Subproceso subproceso = new Subproceso();
		subproceso.setIdSubproceso(rs.getLong("pk_subproceso"));
		subproceso.setNombre(rs.getString("nombre"));
		subproceso.setIdProceso(rs.getLong("fk01_proceso"));
		subproceso.setIdEmpleado(rs.getLong("FK02_Usuario"));
		return subproceso;
	}

}
