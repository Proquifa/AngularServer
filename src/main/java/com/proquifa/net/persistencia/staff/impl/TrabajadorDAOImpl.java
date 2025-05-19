package com.proquifa.net.persistencia.staff.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.staff.Trabajador;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.TrabajadorDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.TrbajadorRowMapper;

@Repository
public class TrabajadorDAOImpl extends DataBaseDAO implements TrabajadorDAO{
	
	final Logger log = LoggerFactory.getLogger(TrabajadorDAOImpl.class);
	
	/**
	 * Consulta la Tabla de Trabajador solo los Activos
	 * Se usa para llenar el combo de Colaborador en la consulta de Asistencias
	 * Se apunta a la Base de Datos Checador
	 * @author Isaac Garcia
	 */
	@SuppressWarnings("unchecked")
	public List<Trabajador>consultaTrabajador(){
		try{
			String query ="SELECT Trabajador, LTRIM(NombreCorto) NombreCorto " +
					" FROM Checador.dbo.tblTrabajador " +
					" WHERE Activo=1 " +
					" ORDER BY LTRIM(NombreCorto) ";
			
//			log.info(query.toString());
			return super.jdbcTemplate.query(query, new TrbajadorRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

}
