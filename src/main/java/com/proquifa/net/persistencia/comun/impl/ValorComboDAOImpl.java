/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.persistencia.DataBaseDAO;

import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.persistencia.comun.ValorComboDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ValorComboRowMapper;


/**
 * @author amartinez
 *
 */
@Repository
public class ValorComboDAOImpl extends DataBaseDAO implements ValorComboDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.negocio.comun.ValorComboDAO#obtenerValorCombo(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public ValorCombo obtenerValorCombo(String tipo, String concepto) {
		try{
			String condiciones = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo", tipo);
			map.put("concepto", concepto);
			map.put("condiciones", condiciones);
			
			if(tipo!=null && !tipo.equals("")){
				condiciones = "AND Tipo= :tipo";
			}
			String query = "SELECT * FROM valorcombo WHERE Concepto= :concepto "+ ":condiciones";
			return (ValorCombo)super.jdbcTemplate.queryForObject(query, map, new ValorComboRowMapper());
		}catch (RuntimeException e) {
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ValorCombo> obtenerValorCombo(String concepto) {
		try{
			StringBuilder sbQuery = new StringBuilder("SELECT PK_Folio idValorCombo, Valor  FROM ValorCombo WHERE Concepto = :concepto");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("concepto", concepto);
			return getJdbcTemplate().query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(ValorCombo.class));
		}catch (RuntimeException e) {
			e.printStackTrace();
			return null;
		}
	}
}