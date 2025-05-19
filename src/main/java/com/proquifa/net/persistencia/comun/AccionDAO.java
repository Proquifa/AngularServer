/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.Accion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author amartinez
 *
 */
public interface AccionDAO {
	/**
	 * 
	 */
	List<Accion> obtenerAccionesIncidente(Long idIncidente) throws ProquifaNetException;
	/**
	 * Metodo que registra una accion
	 * @param accion
	 * @return
	 */
	Long registrarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que actualiza un registro de accion
	 * @param nueva
	 * @return
	 */
	Boolean actualizarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que borra una Accion
	 * @param accion
	 * @return
	 */
	Boolean eliminarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que recupera una acciona traves de su id
	 * @param idAccion
	 * @return
	 */
	Accion obtenerAccionXId(Long idAccion) throws ProquifaNetException;
	/**
	 * Metodo que obtiene las acciones asignadas a un usuario
	 * @param usuario
	 * @return
	 */
	List<Accion> obtenerAccionXUsuario(String usuario, String tipoPendiente) throws ProquifaNetException;
	/**
	 * Metodo para borrar acciones por id de incidente
	 * @param usuario
	 * @return
	 */
	Boolean borrarAccionesXIdIncidente(Long idIncidente) throws ProquifaNetException;
	/**
	 *Metodo para determinar el numero de acciones abiertas  o cerradas del tipo Accion a Realizar
	 * @param docto, ffin
	 * @return
	 * @throws ProquifaNetException
	 */
	Long numeroPendientesAccionVerificacion (Long idIncidente, String tipo, Boolean fechaCerrada) throws ProquifaNetException;
	/**
	 *Metodo para determinar la eficacia global del incidente.
	 * @param idIncidente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerEficaciaGlobalXIdIncidente(Long idIncidente) throws ProquifaNetException;
}