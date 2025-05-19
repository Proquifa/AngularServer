package com.proquifa.net.persistencia.staff.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.staff.Trabajador;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.TrabajadorDAO2;
import com.proquifa.net.persistencia.staff.impl.mapper.TrabajadorRowMapper2;

@Repository
public class TrabajadorDAOImpl2 extends DataBaseDAO implements TrabajadorDAO2 {
	
	final Logger log = LoggerFactory.getLogger(TrabajadorDAOImpl2.class);
	
	@Autowired
	@PersistenceContext(unitName = "Checador")
	private EntityManager em;
	
	@Autowired
	@PersistenceContext(unitName = "ChecadorGDL")
	private EntityManager emGDL;
	
	@SuppressWarnings({"unchecked", "deprecation"})
	@Override
	public List<Trabajador>consultarTrabajador2(){
		try{
			em.clear();
			StringBuilder sbQuery = new StringBuilder("SELECT Trabajador, LTRIM(NombreCorto) NombreCorto " +
					" FROM tblTrabajador " +
					" WHERE Activo=1 " +
					" ORDER BY LTRIM(NombreCorto)");
			Query query = em.createNativeQuery(sbQuery.toString());
			
			List<Trabajador> list = null;
			
			try {
				list = TrabajadorRowMapper2.mapearTrabajador(query.getResultList());
			} catch (Exception e) {
				log.info("Error1 Ryndem");
				log.info(e.toString());
			}
			
			return list;
		}catch (Exception e) {
			log.info("Error2 Ryndem");
			log.info(e.toString());
			return null;
		}
	}
	
	@Override
	@SuppressWarnings({"unchecked", "deprecation"})
	public List<Trabajador>consultarTrabajadorGDL(){
		try{
			emGDL.clear();
			StringBuilder sbQuery = new StringBuilder("SELECT Trabajador, LTRIM(NombreCorto) NombreCorto " +
					" FROM tblTrabajador " +
					" WHERE Activo=1 " +
					" ORDER BY LTRIM(NombreCorto)");
			Query query = emGDL.createNativeQuery(sbQuery.toString());
			
			List<Trabajador> list = null;
			
			try {
				list = TrabajadorRowMapper2.mapearTrabajador(query.getResultList());
			} catch (Exception e) {
				log.info("Error1 GDL");
				log.info(e.toString());
			}
			
			return list;
		}catch (Exception e) {
			log.info("Error2 GDL");
			log.info(e.toString());
			return null;
		}
	}
	
}
