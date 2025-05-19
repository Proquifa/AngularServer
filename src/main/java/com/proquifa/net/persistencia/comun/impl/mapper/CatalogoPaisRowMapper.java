/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.CatalogoItem;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author jhidalgo
 *
 */
public class CatalogoPaisRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	@Override
	public Object mapRow(ResultSet arg0, int arg1) throws SQLException {
		CatalogoItem catalogoItem = new CatalogoItem();
		catalogoItem.setLlave(arg0.getLong("PK_Folio"));
		catalogoItem.setValor(arg0.getString("Valor"));
		catalogoItem.setTipoEmpleado(arg0.getString("Tipo"));
		return catalogoItem;
	}

}
