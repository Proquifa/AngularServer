package com.proquifa.net.persistencia.staff.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.staff.Departamento;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.DepartamentoDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.DepartamentoRowMapper;

/**
 * Consulta la tabla de Departamento
 * @author Isaac Garcia
 *
 */

@Repository
public class DepartamentoDAOImpl extends DataBaseDAO implements DepartamentoDAO{
	
	final Logger log = LoggerFactory.getLogger(DepartamentoDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Departamento> consultaDepartamento(){
		try{
			String query = "SELECT Depto, Nombre " +
					" FROM Checador.dbo.tblDepto " +
					" ORDER BY Nombre";
//			log.info(query.toString());
			return super.jdbcTemplate.query(query, new DepartamentoRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	public List<Departamento> consultaDepartamentoRyndem(){
		try{
			DataBaseDAO baseDAOImpl = new DataBaseDAO();
			
			String query = "SELECT Depto, Nombre " +
					" FROM Checador.dbo.tblDepto " +
					" ORDER BY Nombre";
			
		//	Query queryH = baseDAOImpl.getEntityManagerRyndem().createNativeQuery(query);
			
		//	return DepartamentoHibernateRowMapper.mapearDepartamento(queryH.getResultList());
			return null;
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	public List<Departamento> consultaDepartamentoGDL(){
		try{
			DataBaseDAO baseDAOImpl = new DataBaseDAO();
			
			String query = "SELECT Depto, Nombre " +
					" FROM ChecadorGDL.dbo.tblDepto " +
					" ORDER BY Nombre";
			
		//	Query queryH = baseDAOImpl.getEntityManagerGDL().createNativeQuery(query);
			
		//	return DepartamentoHibernateRowMapper.mapearDepartamento(queryH.getResultList());
			return null;
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

}
