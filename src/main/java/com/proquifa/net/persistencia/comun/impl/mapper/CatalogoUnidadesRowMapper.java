/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.CatalogoItem;
import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class CatalogoUnidadesRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CatalogoItem catalogoItem = new CatalogoItem();
		catalogoItem.setLlave(rs.getLong("PK_unidad"));
		catalogoItem.setValor(rs.getString("Unidades"));
		return catalogoItem;
	}

}
