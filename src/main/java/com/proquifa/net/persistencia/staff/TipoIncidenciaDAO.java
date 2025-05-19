package com.proquifa.net.persistencia.staff;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.TipoIncidencia;

public interface TipoIncidenciaDAO {
	
	List<TipoIncidencia> consultaTipoIncidencia() throws ProquifaNetException;

}
