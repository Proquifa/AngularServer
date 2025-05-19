/**
 * 
 */
package com.proquifa.net.negocio.compras;

import java.util.List;


import com.proquifa.net.modelo.compras.HistorialMonitoreo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author vromero
 *
 */
public interface HistorialMonitoreoService {

	/**
	 * obtiene el historial de monitoreo de una partida de Compra
	 * @param ordenCompra
	 * @param partida
	 * @return List<HistorialMonitoreo>
	 * @throws ProquifaNetException
	 */
	List<HistorialMonitoreo> obtenerHistorialMonitoreo(String ordenCompra, Integer partida) throws ProquifaNetException;
}
