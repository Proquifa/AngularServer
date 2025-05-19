package com.proquifa.net.persistencia.staff.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.staff.Area;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.AreaDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.AreaRowMapper;

/**
 * Consulta la tabla de Departamento
 * @author Isaac Garcia
 *
 */
@Repository
public class AreaDAOImpl extends DataBaseDAO implements AreaDAO{
	
	//BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
	final Logger log = LoggerFactory.getLogger(AreaDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Area> consultaArea(){
		try{
			String query = "SELECT Area, Nombre " +
					" FROM Checador.dbo.tblArea " +
					" ORDER BY Nombre";
			return super.jdbcTemplate.query(query, new AreaRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	
	public List<Area> consultaAreaRyndem(){
		try{
			//BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
			
			String query = "SELECT Area, Nombre " +
					" FROM Checador.dbo.tblArea " +
					" ORDER BY Nombre";
			
			//Query queryH = baseDAOImpl.getEntityManagerRyndem().createNativeQuery(query);
			
			List<Area> areas = null;
			
			try {
			//	areas = AreaHibernateRowMapper.mapearArea(queryH.getResultList());
			} catch (Exception e) {}
			
			return areas;
			
		}catch (Exception e) {
			return null;
		}
	}
	
	public List<Area> consultaAreaGDL(){
		try{
		//	BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
			
			String query = "SELECT Area, Nombre " +
					" FROM ChecadorGDL.dbo.tblArea " +
					" ORDER BY Nombre";
			
		//	Query queryH = baseDAOImpl.getEntityManagerGDL().createNativeQuery(query);
			
			List<Area> areas = null;
			
			try {
			//	areas = AreaHibernateRowMapper.mapearArea(queryH.getResultList());
			} catch (Exception e) {}
			
			return areas;
			
		}catch (Exception e) {
			return null;
		}
	}
}
