/**
 * 
 */
package com.proquifa.net.persistencia.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.Incidente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.consultas.comun.ConsultaIncidente;

/**
 * @author amartinez
 *
 */
public interface IncidenteDAO {
	/**
	 * Recupera una lista de incidentes
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Incidente> obtenerIncidentes();
	/**
	 * Inserta un nuevo registro de incidente
	 * @param incidente
	 * @return
	 * @throws ProquifaNetException
	 */
	Long insertarIncidente(Incidente incidente);
	/**
	 * Recupera un incidente mediante su id
	 * @param idIncidente
	 * @return
	 * @throws ProquifaNetException
	 */
	Incidente obtenerIncidenteXId(Long idIncidente);
	/**
	 * Recupera los incidentes con algun pendiente asignados a un usuario
	 * @param usuario
	 * @return Lista de incidentes asignados al usuario
	 * @throws ProquifaNetException
	 */
	List<Incidente> obtenerIncidentesXUsuario(String tipoPendiente, String usuario);

	/**
	 * Recupera los incidentes por folio IN
	 * @param folio
	 * @return Lista de incidentes
	 * @throws ProquifaNetException
	 */
	Incidente obtenerIncidenteXFolio (String folio);
	/**
	 * Obtener consulta de incidentes
	 * @param objeto consulta
	 * @return Lista de consultas
	 * @throws ProquifaNetException
	 */
	List<ConsultaIncidente> obtenerConsultaDeIncidentes (String folio, ConsultaIncidente paramConsulta);
	/**
	 * Obtener numero de incidentes Abiertos o Cerrados en un rango de fecha
	 * @param String con Abierto o Cerrado
	 * @return Long 
	 * @throws
	 */
	Long obtenerCantidadDeIncidentes(String tipo, String fecha);
	/**
	 *  Cerrar un incidente rechazado o ponderado
	 * 	@param Incidente
	 *  @return verdadero o falso
	 */
	Boolean cerrarIncidenteXidIncidente(Long idIncidente) throws ProquifaNetException;
	
	/***
	 * 
	 * @param indIncidente
	 * @param idSubproceso
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarSubprocesoIncidente(Long indIncidente, Long idSubproceso) throws ProquifaNetException;
	
	/***
	 * 
	 * @param idIncidente
	 * @return
	 * @throws ProquifaNetException
	 */
	Long getSubProcesoIncidente(Long idIncidente) throws ProquifaNetException;
}