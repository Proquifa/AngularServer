/**
 * 
 */
package com.proquifa.net.negocio.comun.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proquifa.net.modelo.comun.GestionarBO;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.comun.GestionarBOService;
import com.proquifa.net.persistencia.comun.GestionarBODAO;

/**
 * @author ymendez
 *
 */
@Service("gestionarBOService")
public class GestionarBOServiceImpl implements GestionarBOService {

	@Autowired
	GestionarBODAO gestionarBODAO;
	
	@Override
	public Map<String, Object> obtenerGraficaProveedor() throws ProquifaNetException {
		try {
			return gestionarBODAO.obtenerGraficaProveedor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<GestionarBO> obtenerProductosBOPorProveedor(Integer idProveedor) throws ProquifaNetException {
		try {
			return gestionarBODAO.obtenerProductosBOPorProveedor(idProveedor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean finalizarProductoBO(Map<String, Object> data) throws ProquifaNetException {
		try {
			return gestionarBODAO.finalizarProductoBO(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
