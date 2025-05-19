/**
 * 
 */
package com.proquifa.net.negocio.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.Accion;
import com.proquifa.net.modelo.comun.Incidente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author amartinez
 *
 */
public interface AccionService {
	/**
	 * Metodo que actualiza las acciones en la lista recibida
	 * @param acciones
	 * @return true en caso de que la actualizacion sea exitosa, en caso contrario, devuelve false
	 * @throws ProquifaNetException
	 */
	Boolean actualizarAcciones(List<Accion> acciones) throws ProquifaNetException;
	/**
	 * Metodo que borra un registro de accion
	 * @param accion
	 * @return true en caso de que la operacion sea exitosa, en caso contrario, devuelve false
	 * @throws ProquifaNetException
	 */
	Boolean borrarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que recupera las acciones de un incidente
	 * @param idIncidente
	 * @return Lista de acciones correspondientes al id del incidente
	 * @throws ProquifaNetException
	 */
	List<Accion> obtenerAccionesXIdIncidente(Long idIncidente) throws ProquifaNetException;
	/**
	 * Metodo que obtiene las acciones a realizar de un empleado
	 * @param usuario
	 * @param tipoPendiente
	 * @return Lista de acciones correspondientes al usuario 
	 * @throws ProquifaNetException
	 */
	List<Accion> obtenerAccionesXUsuario(String usuario, String tipoPendiente) throws ProquifaNetException;
	/**
	 * Metodo que da de alta una accion en la base de datos 
	 * @param accion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean registrarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que recupera una accion a travos de su id
	 * @param idAccion
	 * @return
	 * @throws ProquifaNetException
	 */
	Accion obtenerAccionXId(Long idAccion) throws ProquifaNetException;
	/**
	 * Metodo que se encarga de la ejecucion de una accion
	 * @param accion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean ejecutarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que se encarga de la verificacion de una accion
	 * @param accion
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean verificarAccion(Accion accion) throws ProquifaNetException;
	/**
	 * Metodo que se utiliza al concluir la programacion de las acciones
	 * @param incidente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean finalizarProgramacionAccion(Incidente incidente) throws ProquifaNetException;
	/**
	 * Metodo que modifica una accion 
	 * @param nueva
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarAccion(Accion nueva) throws ProquifaNetException;
}
