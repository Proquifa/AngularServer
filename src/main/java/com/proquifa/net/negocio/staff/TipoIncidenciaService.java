package com.proquifa.net.negocio.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.TipoIncidencia;

public interface TipoIncidenciaService {

	/**
	 * Declaracion del Servicio que Obtiene los Tipo de Incidencia
	 * @author Isaac Garcia
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TipoIncidencia>getTipoIncidencia() throws ProquifaNetException;
}
