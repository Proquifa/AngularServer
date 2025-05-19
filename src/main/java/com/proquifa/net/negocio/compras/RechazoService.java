/**
 * 
 */
package com.proquifa.net.negocio.compras;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.compras.rechazos.Rechazos;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface RechazoService {
	
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
	public List<Rechazos> documentoFaltantePorProveedor(Integer idProveedor, String responsable) throws ProquifaNetException;
	
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
	public List<Rechazos> obtenerPiezasRechazadas(Rechazos datos) throws ProquifaNetException;

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
	 * @param data
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean finalizarCuarentena(Map<String, Object> data) throws ProquifaNetException;

	boolean finalizarReclamo(Map<String, Object> data) throws ProquifaNetException;

	void generarReclamo(String idPCompra, String idPieza, Map<String, Object> data);

	Map<String, Object> obtenerTotalesSeccion(String idUsuario) throws ProquifaNetException;

}
