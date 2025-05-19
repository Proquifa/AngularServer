/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Funcion;

/**
 * @author amartinez
 *
 */
public class FuncionRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Funcion funcion = new Funcion();
		funcion.setIdFuncion(rs.getLong("pk_funcion"));
		funcion.setSubproceso(rs.getLong("fk01_subproceso"));
		funcion.setIdentificador(rs.getString("identificador"));
		funcion.setNombre(rs.getString("nombre"));
		funcion.setNivel(rs.getString("nivel"));
		try {
			funcion.setUsuario(rs.getString("usuario"));
		} catch (Exception e) {
		}
		
		return funcion;
	}
}