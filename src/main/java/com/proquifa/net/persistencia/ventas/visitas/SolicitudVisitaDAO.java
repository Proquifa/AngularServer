/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas;

import java.util.List;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;


/**
 * @author ymendez
 *
 */
public interface SolicitudVisitaDAO {
	
	/**Regresa las solicitudes de visita
	 * @return
	 */
	List<SolicitudVisita> findSolicitudesVisita(String parametros, List<NivelIngreso> niveles);
	/**Listado de referencias de una Solicitud
	 * @return
	 */
	List<Referencia> findReferenciaSolicitud(Long idSolicitudVisita);
	/**
	 * Metodo que inserta una solicitud de visita
	 * @param solicitudVisita
	 * @return Long
	 */
	Long insertarSolicitudVisita(SolicitudVisita solicitudVisita, Folio folio);
	/**
	 * Metodo que inserta una solicitud de visita
	 * @param solicitudVisita
	 * @return Long
	 */
	Boolean descartarSolicitudVisita(Long idSolicitudVisita,String justificacion);
	/***
	 * 
	 * @param idSolicitudesVisitas
	 * @param idVisita
	 * @return
	 */
	Boolean agruparSolicitudes(List<Integer> idSolicitudesVisitas, Long idVisita);
	/***
	 * 
	 * @param idSolicitudVisita
	 * @return
	 */
	String buscarFolioxId(Long idSolicitudVisita);
	/***
	 * 
	 * @param idVisita
	 * @return
	 */
	List<Long> obtenerSolicitudesxVisita(Long idVisita);
	/**
	 * @param solicitudVisita
	 * @return
	 */
	Boolean actualiarSolicitudVisita(SolicitudVisita solicitudVisita);	
	/**
	 * 
	 * @param solicitudVisita
	 * @return
	 * @throws ProquifaNetException
	 */
	public int insertSolicitudVisita(SolicitudVisita solicitudVisita,String tipo) throws ProquifaNetException;
	/**
	 * 
	 * @param documeto
	 * @param idSolicitud
	 * @return
	 * @throws ProquifaNetException
	 */
	public Integer insertDocumentoSolicitudVisita(DocumentoSolicitudVisita documento,int idSolicitud) throws ProquifaNetException;
}