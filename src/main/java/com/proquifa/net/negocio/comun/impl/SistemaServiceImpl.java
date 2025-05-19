package com.proquifa.net.negocio.comun.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Sistema;
import com.proquifa.net.negocio.comun.SistemaService;
import com.proquifa.net.persistencia.comun.ComunDAO;

@Service("SistemaServiceImpl")
public class SistemaServiceImpl implements SistemaService{
	@Autowired
	ComunDAO comunDAO;
	
	@Override
	public Sistema obtenerVersionSistema(String nombre) throws ProquifaNetException {
		try {
			return comunDAO.obtenerVersionSistema(nombre);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
