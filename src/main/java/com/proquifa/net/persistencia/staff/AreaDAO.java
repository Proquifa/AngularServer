package com.proquifa.net.persistencia.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Area;

/**
 * Delcaracion del DAO de Area
 * @author Isaac Garcia
 *
 */
public interface AreaDAO {

	List<Area> consultaArea() throws ProquifaNetException;
	
	public List<Area> consultaAreaRyndem();
	
	public List<Area> consultaAreaGDL();
}
