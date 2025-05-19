/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Sistema;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ComunDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.SistemaRowMapper;

/**
 * @author ymendez
 *
 */
@Repository
public class ComunDAOImpl extends DataBaseDAO implements ComunDAO {

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.comun.ComunDAO#obtenerUltimoRegistroInsertado(java.lang.String)
	 */
	@Override
	public Integer obtenerUltimoRegistroInsertado(String tabla) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT IDENT_CURRENT ('").append(tabla).append("') \n");
			sbQuery.append(" \n");
			Map<String, Object> map = new HashMap<String, Object>();
			return jdbcTemplate.queryForObject(sbQuery.toString(), map, Integer.class);
			
		} catch (Exception e) {
			return -1;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Sistema obtenerVersionSistema(String nombre) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT * FROM Sistema WHERE Nombre = '" + nombre + "'");
			return (Sistema) super.jdbcTemplate.query(sbQuery.toString(), new SistemaRowMapper()).get(0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
