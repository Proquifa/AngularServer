/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.CatalogoItem;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class CatalogoEmpleadoTipoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CatalogoItem catalogoItem = new CatalogoItem();
		catalogoItem.setLlave(rs.getLong("clave"));
		catalogoItem.setValor(rs.getString("usuario"));
		catalogoItem.setTipoEmpleado(rs.getString("Tipo"));
		catalogoItem.setNombre(rs.getString("Nombre"));
		return catalogoItem;
	}

}
