package com.proquifa.net.persistencia.staff.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.staff.Categoria;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.CategoriaDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.CategoriaRowMapper;

/**
 * Consulta la tabla de Categoria
 * @author Isaac Garcia
 *
 */

@Repository
public class CategoriaDAOImpl extends DataBaseDAO implements CategoriaDAO{

	final Logger log = LoggerFactory.getLogger(CategoriaDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Categoria> consultaCategoria(){
		try{
			String query = "SELECT Categoria, Nombre " +
					" FROM Checador.dbo.tblCategoria " +
					" ORDER BY Nombre";	
			
//			log.info(query.toString());
			return super.jdbcTemplate.query(query, new CategoriaRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	public List<Categoria> consultaCategoriaRyndem(){
		try{
			//BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
			
			String query = "SELECT Categoria, Nombre " +
					" FROM Checador.dbo.tblCategoria " +
					" ORDER BY Nombre";
			
			//Query queryH = baseDAOImpl.getEntityManagerRyndem().createNativeQuery(query);
			
			List<Categoria> categorias = null;
			
			try {
				//categorias = CategoriaHibernateRowMapper.mapearCategoia(queryH.getResultList());
			} catch (Exception e) {}
			
			return categorias;
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	public List<Categoria> consultaCategoriaGDL(){
		try{
			//BaseDAOImpl baseDAOImpl = new BaseDAOImpl();
			
			String query = "SELECT Categoria, Nombre " +
					" FROM ChecadorGDL.dbo.tblCategoria " +
					" ORDER BY Nombre";
			
		//	Query queryH = baseDAOImpl.getEntityManagerGDL().createNativeQuery(query);
			
			List<Categoria> categorias = null;
			
			try {
				//categorias = CategoriaHibernateRowMapper.mapearCategoia(queryH.getResultList());
			} catch (Exception e) {}
			
			return categorias;
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	
}
