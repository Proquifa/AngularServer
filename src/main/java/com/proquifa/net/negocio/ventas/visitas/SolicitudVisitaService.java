/**
 * 
 */
package com.proquifa.net.negocio.ventas.visitas;

import java.util.List;


import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
/**
 * @author ymendez
 *
 */
public interface SolicitudVisitaService {
	/**Consulta todas las solicitudes pendientes
	 * @return
	 * @throws ProquifaNetException
	 */
	List<SolicitudVisita> consultarSolicitudesVisita(Long idEjecutivoVentas)throws ProquifaNetException;
	/**Obtiene una lista de referencias de una solicitud de visita
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Referencia> consultarReferenciaSolicitud(Long idSolicitudVisita)throws ProquifaNetException;
	/**
	 * Metodo para insertar una solicitud nueva
	 * @param solicitudVisita
	 * @return Long
	 * @throws ProquifaNetException
	 */
	Long insertarSolicitudVisita(SolicitudVisita solicitudVisita) throws ProquifaNetException;
	/**
	 * Metodo para descartar una solicitud de visita
	 * @param idSolicitudVisita
	 * @return boolean
	 * @throws ProquifaNetException
	 */
	Boolean descartarSolicitudVisita(Long idSolicitudVisita, String folio, String justificacion) throws ProquifaNetException;
	/**
	 * @param idSolicitudVisita
	 * @return
	 */
	String buscarFolioxId(Long idSolicitudVisita);
	/**
	 * @param solicitudVisita
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualiarSolicitudVisita(SolicitudVisita solicitudVisita) throws ProquifaNetException;
	/**
	 * 
	 * @param solicitudVisita
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean generarSolicitudVisita(SolicitudVisita solicitudVisita)throws ProquifaNetException;
	/**
	 * 
	 * @param solicitudVisita
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	public int generarSolicitudVisita(SolicitudVisita solicitudVisita,String tipo)throws ProquifaNetException;
}