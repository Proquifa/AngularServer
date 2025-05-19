/**
 * 
 */
package com.proquifa.net.persistencia.compras;

import java.util.List;

import com.proquifa.net.modelo.compras.HistorialMonitoreo;

/**
 * @author vromero
 *
 */
public interface HistorialMonitoreoDAO {
	
	/**
	 * Obtiene una el hitorial de Monitoreo sobre una partida en una compra.
	 * @param ordenCompra
	 * @param partida
	 * @return List<HitorialMonitoreo>
	 */
	
	List<HistorialMonitoreo> obteberHistorialMonitoreo(String ordenCompra, Integer partida);

}
