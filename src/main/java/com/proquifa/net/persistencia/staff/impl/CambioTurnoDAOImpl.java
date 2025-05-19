package com.proquifa.net.persistencia.staff.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.staff.CambioTurno;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.CambioTurnoDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.CambioTurnoRowMapper;

/**
 * @author isaac Garcia
 *
 */
@Repository
public class CambioTurnoDAOImpl extends DataBaseDAO implements CambioTurnoDAO{
	
	final Logger log = LoggerFactory.getLogger(CambioTurnoDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<CambioTurno> consultaCambioTurno(Date fechaInicio, Date fechaFin){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		
		try{
			String query = "SELECT Trabajador, Fecha, Turno FROM Checador.dbo.tblTrabTurno " +
					" WHERE Fecha >= '" + formatoFecha.format(fechaInicio) + "' " +
					" AND Fecha <= '" + formatoFecha.format(fechaFin) + "' " +
					" ORDER BY Fecha";
			
//			log.info(query.toString());
			
			return super.jdbcTemplate.query(query, new CambioTurnoRowMapper());
		}catch (Exception e) {
			
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFechaInicio: "+ fechaInicio,"\nFechaFin: "+ fechaFin);
			return new ArrayList<CambioTurno>();
		}
	}
}
