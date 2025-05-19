/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import java.text.NumberFormat;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Folio;

/**
 * @author ernestogonzalezlozada
 *
 */
 
public class ConsecutivoMasUnoRowMapper implements RowMapper {
    
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMinimumIntegerDigits(4);
		nf.setGroupingUsed(false);
		Long valor = (rs.getLong("valor")); // + 1);		
		if (valor >= 9999){
			valor=1L;
		} else{
			valor ++;
		}
		Folio folio = new Folio();
		folio.setValor(nf.format(valor));
		return folio;
	}

}
