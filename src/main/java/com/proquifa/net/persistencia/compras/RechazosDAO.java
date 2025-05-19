/**
 * 
 */
package com.proquifa.net.persistencia.compras;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.compras.rechazos.Rechazos;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface RechazosDAO {

	/**
	 * @param responsable
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<Rechazos> obtenerDocumentacionFaltante(String responsable) throws ProquifaNetException;

	/**
	 * @param responsable
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Object> obtenerGraficaDocumentacionFaltante(String responsable) throws ProquifaNetException;

	/**
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Contacto> obtenerProveedoresPorProveedor(Integer idProveedor) throws ProquifaNetException;

	/**
	 * @param idProveedor
	 * @param responsable
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Rechazos> documentoFaltantePorProveedor(Integer idProveedor, String responsable) throws ProquifaNetException;

	/**
	 * @param codigo
	 * @param lote
	 * @param hoja 
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarEstadoAInspeccion(String codigo, String lote, String hoja) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Rechazos> obtenerPiezasRechazadas(Rechazos datos) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Object> obtenerGraficaRechazoInspeccion(Rechazos datos) throws ProquifaNetException;

	/**
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Rechazos> obtenerPiezasRechazadasPorInspeccion(Integer idProveedor, String tipo) throws ProquifaNetException;

	/**
	 * @param idPieza
	 * @param instrucciones
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updatePieza(Integer idPieza, String instrucciones) throws ProquifaNetException;

	/**
	 * @param idPCompra
	 * @param idPPedido
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean updateInspeccion(String idPCompra, String idPPedido) throws ProquifaNetException;

	boolean insertarReclamo(Map<String, Object> param, String Folio) throws ProquifaNetException;

	Rechazos obtenerDatosJasper(String idPcompra, String idPieza);

	Map<String, Object> obtenerTotales(String idUsuario) throws ProquifaNetException;

	
}
