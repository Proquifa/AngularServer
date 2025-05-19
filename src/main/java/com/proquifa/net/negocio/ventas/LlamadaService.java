/**
 * 
 */
package com.proquifa.net.negocio.ventas;

import java.util.List;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Llamada;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface LlamadaService {
	/**
	 * Metodo con el cual se da de alta una llamada en el sistema
	 * @param llamada
	 */
	void registrarLlamada(Llamada llamada);
	
	/***
	 * Metodo con el cual se obtiene una lista de las llamadas registradas.
	 * @param llamada
	 * @return lista
	 */
	List<Llamada> obtenerLlamadas(Llamada llamada);
	
	/***
	 * Actulizar el reporte de llamdas
	 * @param llamada
	 * @param pcotizas
	 * @return
	 */
	String actualizarRegistroLlamada (Llamada llamada, List<PartidaCotizacion> pcotizas);
	/***
	 * Generar pendiente de Habilitar Cliente
	 * @param DoctoR
	 * @return
	 */
	Integer generarPendienteClienteDeshabilitado (String DoctoR);
}