/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.comun.Modulo;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ModuloRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Modulo modulo = new Modulo();
		modulo.setIdModulo(rs.getLong("PK_Modulo"));
		modulo.setNombre(rs.getString("Nombre"));
		modulo.setNombre_vista(rs.getString("Nombre_Vista"));
		modulo.setTipo(rs.getString("Tipo"));
		
		return modulo;
	}
}
