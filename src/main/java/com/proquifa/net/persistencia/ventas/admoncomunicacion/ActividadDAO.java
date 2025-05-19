/**
 * 
 */
package com.proquifa.net.persistencia.ventas.admoncomunicacion;

import com.proquifa.net.modelo.ventas.admoncomunicacion.Actividad;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface ActividadDAO {
	/**
	 * Metodo que inserta una actividad 
	 * @param actividad
	 */
	void insertarActividades(Actividad actividad);
}
