package com.proquifa.net.persistencia.staff.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.staff.TipoIncidencia;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.TipoIncidenciaDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.TipoIncidenciaRowMapper;

@Repository
public class TipoIncidenciaDAOImpl extends DataBaseDAO implements TipoIncidenciaDAO{
	
	final Logger log = LoggerFactory.getLogger(TipoIncidenciaDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<TipoIncidencia> consultaTipoIncidencia(){
		
		try{
			String query = "SELECT Concepto, Nombre FROM Checador.dbo.tblConcepto " + 
					" UNION " + 
					" SELECT 'FALTA' Concepto, 'FALTA' Nombre " + 
					" UNION " +
					" SELECT 'RETARDO' Concepto, 'RETARDO' Nombre " +
					" UNION " +
					" SELECT 'FUERA DE TIEMPO' Concepto, 'FUERA DE TIEMPO' Nombre " +
					" UNION " +
					" SELECT 'NINGUNA' Concepto, 'NINGUNA' Nombre " +
					" ORDER BY 2";
//			log.info(query.toString());
			
			return super.jdbcTemplate.query(query, new TipoIncidenciaRowMapper());
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

}
