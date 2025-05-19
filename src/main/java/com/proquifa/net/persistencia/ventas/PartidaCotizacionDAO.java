/**
 * 
 */
package com.proquifa.net.persistencia.ventas;

import java.util.List;


import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface PartidaCotizacionDAO {
	/**
	 * Metodo que inserta una partida de cotización en la base de datos
	 * @param partidaCotizacion
	 * @return
	 */
	Long registrarPartidaCotizacion(PartidaCotizacion partidaCotizacion);
	/**
	 * Metodo que actualiza una partida de cotización 
	 * @param partidaCotizacion
	 */
	void actualizarPartidaCotizacion(PartidaCotizacion partidaCotizacion);
	/**
	 * Metodo que borra una partida de cotizacion
	 * @param partidaCotizacion
	 */
	void borrarPartidaCotizacion(PartidaCotizacion partidaCotizacion);
	/**
	 * Obtiene una partida cotizacion a partir de su folio 0 o 99 y la clave de la cotización
	 * @param cotizacion y folio
	 */
	List<PartidaCotizacion> obtenPCotizasCeroONoventaYNueve(String cotizacion, Integer folio);
	/**
	  * Obtiene las partidas con el folio de cotizacion dado
	  * @param folioDeCotizaion
	  * @return devuelve una lista que representa las partidas con el folioDeCotizacion especificada
	  */
	List<PartidaCotizacion> obtenerPartidasCotizacion(String folioCotizacion, String tipo);
	/**
	  * Determinar la partida con folio 99 de una PCotPharma
	  * @param folioDeCotizaion, Partida
	  * @return
	  */
	String obtenerPCotizaClasificacionOrigen(String cotiza, String codigo, String fabrica);	
	/**
	  * Obtiene la lista del tiempo de proceso de la partida seleccionada.
	  * @param idPcotiza
	  * @return
	  */
	List<TiempoProceso> obtenTiempoProcesoPCotiza(PartidaCotizacion pcotiza, Boolean completo);
	/**
	  * Obtiene la partida de una cotizacion x su id.
	  * @param idPcotiza
	  * @return
	  */
	PartidaCotizacion obtenerPCotizaXid(Long idPCotiza);
	
	/***
	 * Obtiene las partidas para mostrarlas en la Consulta de cotizaciones.
	 * @param cotizacion
	 * @return
	 */
	List<PartidaCotizacion> finConsultaPCotizas(String cotizacion);
	
	List<TiempoProceso> findConsultaTiempoPedCancRecot(PartidaCotizacion pcotiza);
	/***
	 * Obtiene el resumen si la cotizacion es cancelada, recotizada o pedida
	 * @param cotizacion
	 * @return
	 */
	List<PartidaCotizacion> findConsultaPartidaResumenGrafica(String condiciones) throws ProquifaNetException;
	/***
	 * Obtiene el resumen si la cotizacion es cancelada, recotizada o pedida
	 * @param cotizacion
	 * @return
	 */
	/**
	 * La consulta si devulve regsitros indica que es por el Nuevo Flujo de Investigacion del Producto si no es por el Flujo antiguo
	 * @param idPCotiza
	 * @return
	 */
	int definirFlujoInvestiga(Long idPCotiza);
	/**
	 * Obtiene el Tiempo Proceso del Nuevo Flujo de Investigacion
	 * @param idPCotiza
	 * @return
	 */
	List<TiempoProceso> obtenerTiempoProcesoNuevo(Long idPCotiza);	
	/**
	 * //Obtiene el Folio de PCotizas 0, 1 ... 99, 0 no cuenta con Codigo ni fabrica, y lo de folio 1 ... 99 si los tiene
	 * @param idPCotiza
	 * @return
	 */
	int obtenerFolioPCotizas(Long idPCotiza);
}