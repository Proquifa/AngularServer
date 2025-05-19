/**
 * 
 */
package com.proquifa.net.persistencia;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * @author ymendez
 *
 */
public class DataBaseDAO {

	@Autowired
	@PersistenceContext(unitName = "Checador")
	private EntityManager sessionFactory;
	
	@Autowired
	@PersistenceContext(unitName = "ChecadorGDL")
	private EntityManager sessionFactoryGDL;

	@Autowired
	protected NamedParameterJdbcTemplate jdbcTemplate;

	/**
	 * @return the sessionFactory
	 */
	//	public EntityManager getSessionFactory() {
	//		return sessionFactory;
	//	}

	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	//	public void setSessionFactory(EntityManager sessionFactory) {
	//		this.sessionFactory = sessionFactory;
	//	}

	/**
	 * @return the jdbcTemplate
	 */
	
	public NamedParameterJdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	/**
	 * @param jdbcTemplate the jdbcTemplate to set
	 */

	public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public SqlRowSet queryForRowSet(String query, Map<String, ?> obj) throws DataAccessException {
		return jdbcTemplate.queryForRowSet(query, obj);
	}


	public SqlRowSet queryForRowSet(String query) throws DataAccessException {
		return jdbcTemplate.queryForRowSet(query, new HashMap<String, Object>());
	}
	
	public Long queryForLong(String query, Map<String, ?> obj) throws DataAccessException {
		return jdbcTemplate.queryForObject(query, obj, Long.class);
	}


	public Long queryForLong(String query) throws DataAccessException {
		return jdbcTemplate.queryForObject(query, new HashMap<String, Object>(), Long.class);
	}


	public int queryForInt(String query, Map<String, ?> obj) throws DataAccessException {
		return jdbcTemplate.queryForObject(query, obj, int.class);
	}


	public int queryForInt(String query) throws DataAccessException {
		return jdbcTemplate.queryForObject(query, new HashMap<String, Object>(), int.class);
	}


	public String queryForString(String query) throws DataAccessException {
		return jdbcTemplate.queryForObject(query, new HashMap<String, Object>(), String.class);
	}


	public int update(String query) throws DataAccessException {
		return jdbcTemplate.update (query, new HashMap<String, Object>());
	}


	public int update(String query, Map<String, ?> obj) throws DataAccessException {
		return jdbcTemplate.update(query, obj);
	}

	public EntityManager getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(EntityManager sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public EntityManager getSessionFactoryGDL() {
		return sessionFactoryGDL;
	}

	public void setSessionFactoryGDL(EntityManager sessionFactoryGDL) {
		this.sessionFactoryGDL = sessionFactoryGDL;
	}
	
}
