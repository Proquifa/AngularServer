package com.proquifa.net.persistencia.staff.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.staff.Turno;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.TurnoDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.TurnoRowMapper;

@Repository
public class TurnoDAOImpl extends DataBaseDAO implements TurnoDAO{
	
	final Logger log = LoggerFactory.getLogger(TurnoDAOImpl.class);

	@SuppressWarnings("unchecked")
	public List<Turno>consultaTurno(){
		
		try{
			String query = "SELECT Turno, EntradaHasta, Salida, Comida, ComidaRegreso, ComidaTiempo " +
					" FROM Checador.dbo.tblTurno";
//			log.info(query.toString());
			
			return super.jdbcTemplate.query(query, new TurnoRowMapper());
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new ArrayList<Turno>();
		}
	}
}
