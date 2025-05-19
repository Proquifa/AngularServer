/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;

import com.proquifa.net.modelo.comun.Referencia;

/**
 * @author amartinez
 *
 */
public interface ReferenciaDAO {
	/***
	 * obtiene las referencias cargadas para un incidente.
	 * @param idIncidente
	 * @return
	 */
	List<Referencia> obtenerReferenciaXIdIncidente(Long idIncidente);
	
	/***
	 * Inserta las referencias
	 * @param referencia
	 * @return
	 */
	Long insertarReferencia(Referencia referencia);
	
	/***
	 * Borra referencias por idReferencia
	 * @param referencia
	 * @return
	 */
	Boolean borrarReferencia(Referencia referencia);
	
	/***
	 * Revisa si la referencia existe.
	 * @param referencia
	 * @return
	 */
	Boolean existeReferencia(Referencia referencia);
	
	/***
	 * Actualizar datos de la referencia.
	 * @param nueva
	 * @return
	 */
	Boolean actualizarReferencia(Referencia nueva);
}