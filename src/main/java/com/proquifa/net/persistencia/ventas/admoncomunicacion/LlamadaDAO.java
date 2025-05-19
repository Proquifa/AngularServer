/**
 * 
 */
package com.proquifa.net.persistencia.ventas.admoncomunicacion;

import java.util.List;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Llamada;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface LlamadaDAO {
	/**
	 * Metodo que obtiene todas las llamadas de la base de datos
	 * @return
	 */
	List<Llamada> obtenerLlamadas(Llamada parametrosLlamada);
	/**
	 * Metodo que registra una llamada
	 * @param llamada
	 */
	void guardarLlamada(Llamada llamada);
	
	/***
	 * Actualizar las llamadas telefenicas.
	 * @param llamdas
	 * @param pcotiza
	 * @return
	 */
	Boolean actulizarLLamada (Llamada llamada);
	
	/***
	 * Insertar la partida en la tabla DoctoLLamada que fue registrada en una llamada.
	 * @param pcotiza
	 * @return
	 */
	void insertarPartidaLlamada (PartidaCotizacion pcotiza, Long folioDoctoR);
}