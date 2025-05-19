package com.proquifa.net.negocio.staff.impl;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.TipoIncidencia;
import com.proquifa.net.negocio.staff.TipoIncidenciaService;
import com.proquifa.net.persistencia.staff.TipoIncidenciaDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tipoIncidenciaService")
public class TipoIncidenciaServiceImpl implements TipoIncidenciaService {

	@Autowired
	TipoIncidenciaDAO tipoIncidenciaDAO;
	
	public List<TipoIncidencia> getTipoIncidencia() throws ProquifaNetException{
		return this.tipoIncidenciaDAO.consultaTipoIncidencia();
	}
}
