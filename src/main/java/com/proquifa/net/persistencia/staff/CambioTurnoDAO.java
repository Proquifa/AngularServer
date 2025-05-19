package com.proquifa.net.persistencia.staff;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.CambioTurno;

/**
 * @author isaac Garcia
 *
 */

public interface CambioTurnoDAO {

	/**
	 * Declaracion del DAO
	 * Conuslta la tabla de tblTrabTurno, donde especifica por dia si le hicieron un cambio o rotacion
	 * de turno, en base al tipo de turno que se tiene asignado
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 * @throws ProquifaNetException
	 */
	List<CambioTurno> consultaCambioTurno(Date fechaInicio, Date fechaFin) throws ProquifaNetException;
}
