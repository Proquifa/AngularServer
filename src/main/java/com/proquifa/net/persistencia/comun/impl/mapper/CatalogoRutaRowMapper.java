/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.CatalogoItem;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class CatalogoRutaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CatalogoItem catRuta = new CatalogoItem();
		catRuta.setLlave(rs.getLong("PK_Folio"));
		catRuta.setValor(rs.getString("Valor"));
		catRuta.setTipoEmpleado(rs.getString("Tipo"));
		
		return catRuta;
	}
}