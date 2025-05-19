/**
 * 
 */
package com.proquifa.net.persistencia.comun;


import java.util.List;

import com.proquifa.net.modelo.comun.Modulo;
;

/**
 * @author amartinez
 *
 */
public interface ModuloDAO {
	/**
	 * Metodo que obtiene los modulos relacionados a este empleado por su Id
	 * @param modificacion
	 */
	List<Modulo> obtenerModulosXIdEmpleado(Long idEmpleado);
	/**
	 * Metodo que obtiene todos los modulos
	 * @param modificacion
	 */
	List<Modulo> obtenerModulos();
}
