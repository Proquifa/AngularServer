package com.proquifa.net.persistencia.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Turno;

/**
 * Declaracion del DAO con respecto a los Turnos
 * @author isaac.garcia
 *
 */

public interface TurnoDAO {
	
	/**
	 * Consulta toda la tabla de tblTurno donde estan almacenados los turnos
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Turno>consultaTurno() throws ProquifaNetException;
}
