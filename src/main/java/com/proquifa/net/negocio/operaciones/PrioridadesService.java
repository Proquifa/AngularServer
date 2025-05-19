/**
 * 
 */
package com.proquifa.net.negocio.operaciones;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.operaciones.Prioridades;

/**
 * @author ymendez
 *
 */
public interface PrioridadesService {

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<Prioridades> obtenerBotoneraPrioridades() throws ProquifaNetException;
	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<Prioridades>> obtenerListado() throws ProquifaNetException;
	
	/**
	 * @param idPedidos
	 * @param urgencia
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarUrgencia(String idPedidos, Integer urgencia) throws ProquifaNetException;

	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<Prioridades>> obtenerListadoEnvio() throws ProquifaNetException;

	/**
	 * @param idPedidos
	 * @param urgencia
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarUrgenciaEnvio(String idPedidos, Integer urgencia) throws ProquifaNetException;

	/**
	 * @param idPedidos
	 * @param urgencia
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarPausa(String idPedidos, Integer urgencia) throws ProquifaNetException;

	/**
	 * @param idPedidos
	 * @param facturaRemision
	 * @param remisionar
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarFacturaRemision(String idPedidos, Integer facturaRemision, Integer remisionar)
			throws ProquifaNetException;

	Map<String, List<Prioridades>> obtenerListadoStock() throws ProquifaNetException;

	List<Prioridades> obtenerMarcas() throws ProquifaNetException;

	Boolean updateStock(Prioridades producto);

	Map<String, Object> obtenerListadoDestruccion();

	String enviarDestruccion(List<Prioridades> productos) throws ProquifaNetException;
	
}
