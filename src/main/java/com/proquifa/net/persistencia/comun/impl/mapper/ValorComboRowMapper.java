/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.ValorCombo;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ValorComboRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ValorCombo valorCombo = new ValorCombo();
		valorCombo.setIdValorCombo(rs.getLong("pk_folio"));
		valorCombo.setConcepto(rs.getString("concepto"));
		valorCombo.setValor(rs.getString("valor"));
		valorCombo.setTipo(rs.getString("tipo"));
		return valorCombo;
	}

}
