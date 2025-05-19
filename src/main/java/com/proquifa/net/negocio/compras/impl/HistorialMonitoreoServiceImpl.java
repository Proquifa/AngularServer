/**
 * 
 */
package com.proquifa.net.negocio.compras.impl;
import java.util.List;


import com.proquifa.net.modelo.compras.HistorialMonitoreo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.negocio.compras.HistorialMonitoreoService;
import com.proquifa.net.persistencia.compras.HistorialMonitoreoDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author vromero
 *
 */
@Service("historialMonitoreo")
public class HistorialMonitoreoServiceImpl implements HistorialMonitoreoService {
	
	@Autowired 
	HistorialMonitoreoDAO historialMonitoreoDAO;
	
	@Transactional
	public List<HistorialMonitoreo> obtenerHistorialMonitoreo(
			String ordenCompra, Integer partida) throws ProquifaNetException {

		List<HistorialMonitoreo> list = this.historialMonitoreoDAO.obteberHistorialMonitoreo(ordenCompra, partida);
		
		return list;
	}

}
