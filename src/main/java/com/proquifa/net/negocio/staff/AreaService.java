package com.proquifa.net.negocio.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Area;

/**
 * Declaracion del Servicio para obtener los datos del Area
 * @author Isaac Garcia
 * @return
 * @throws ProquifaNetException
 */

public interface AreaService {

	List<Area> getConsultaArea() throws ProquifaNetException;
}
