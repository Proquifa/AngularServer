/**
 * 
 */
package com.proquifa.net.negocio.ventas;

import java.util.Date; 
import java.util.List;


import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;

/**
 * @author eakoji
 *
 */
public interface PartidaCotizacionService {
	
	/**
	 * 
	 * @param folioDeCotizacion: el folio para las partidas de interes
	 * @return Devuelve una lista de las partidas de cotizacion que pertenece al folio de 
	 * cotizacion definido
	 * @throws ProquifaNetException
	 */
	List<PartidaCotizacion> obtenerPartidaCotizacion(String folioCotizacion, String tipo) throws ProquifaNetException;
	
	/**
	 * Obtiene una partidaCotizacion apartir de su folio y id 
	 * @param folioDeCotizacion
	 * @param idPCotiza
	 * @return
	 * @throws ProquifaNetException
	 */
	PartidaCotizacion obtenerPartidaPorId(String folioDeCotizacion, String idPCotiza) throws ProquifaNetException;
	
	/**
	 * Obtiene una partida cotizacion a partir de su folio 0 o 99 y la clave de la cotizacion
	 * @param folioDeCotizacion
	 * @param Folio
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PartidaCotizacion> obtenerPCotizasParaReporteCotizacion(String cotizacion) throws ProquifaNetException;
	
	/**
	 * Determinar el tiempo de proceso de una partida de cotizacion
	 * @param Partida Cotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> tiempoProcesoPCotiza(Long idPartida) throws ProquifaNetException;
	
	
	List<PartidaCotizacion> obtenerPartidasCotizaGraficaETyFT(Date finicio,Date ffin, String Estatus,String Estado,String Cotizo,String Cliente,String MEnvio,String cotizacion,Long idEmpleado) throws ProquifaNetException;
	/**
	 * Obtener el Resumen por partida de cotizaciones, para la generacion de las graficas de ET y FT (Marca,Cliente,Partida,Estado,Piezas,Status,ETyFT,Monto)
	 * @param Finicio
	 * @param Ffin
	 * @param Estatus
	 * @param Estado
	 * @param Cotizo
	 * @param Cliente
	 * @param MEnvio
	 * @param Cotizacion
	 * @param IdEmpleado
	 * @return
	 * @throws ProquifaNetException
	 */
}