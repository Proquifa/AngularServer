/**
 * 
 */
package com.proquifa.net.persistencia.comun;


import com.proquifa.net.modelo.comun.Gestion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author amartinez
 *
 */
public interface GestionDAO {
	/**
	 * Metodo que registra la gestión en la base de datos
	 * @param gestion
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarGestion(Gestion gestion) throws ProquifaNetException;
	/**
	 * Metodo que actualiza un registro de gestión
	 * @param nueva
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarGestion(Gestion gestion) throws ProquifaNetException;
	/**
	 * Metodo que recupera un registro de gestión mediante el id
	 * @param idGestion
	 * @return
	 * @throws ProquifaNetException
	 */
	Gestion obtenerGestionXId(Long idGestion) throws ProquifaNetException;
	/**
	 * Metodo que recupera una gestion mediante un incidente
	 * @param idIncidente
	 * @return
	 * @throws ProquifaNetException
	 */
	Gestion obtenerGestionXIdIncidente(Long idIncidente) throws ProquifaNetException;
	/**
	 * Metodo que recupera una gestión para un incidente a traves de su folio
	 * @param Folio
	 * @return
	 * @throws ProquifaNetException
	 */
	Gestion obtenerGestionXFolioIncidente(String folio) throws ProquifaNetException;
}