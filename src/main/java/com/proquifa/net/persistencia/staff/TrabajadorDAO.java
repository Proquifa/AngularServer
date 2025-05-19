package com.proquifa.net.persistencia.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Trabajador;

/**
 * Delcaracion del DAO de Trabajador
 * @author Isaac Garcia
 *
 */
public interface TrabajadorDAO {
	List<Trabajador> consultaTrabajador() throws ProquifaNetException;
}
