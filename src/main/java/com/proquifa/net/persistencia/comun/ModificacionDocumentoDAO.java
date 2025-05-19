/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import com.proquifa.net.modelo.comun.ModificacionDocumentoRecibido;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface ModificacionDocumentoDAO {
	/**
	 * Metodo que inserta una modificacion de documento en la base de datos
	 * @param modificacion
	 */
	void insertarModificacionDocumento(ModificacionDocumentoRecibido modificacion);
}
