package com.proquifa.net.negocio.reportes;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.HistorialPartidaEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;

public interface SeguimientoCotizacionesService {
	/***
	 * 
	 * @param Cliente
	 * @param Empleado
	 * @return
	 * @throws ProquifaNetException
	 */
	List<SeguimientoCotizacion> obtenerCotizacionesEnSeguimiento(String Cliente,String Empleado,int confirmacion) throws ProquifaNetException;
	/***
	 * 
	 * @param FolioCotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	List<PartidaCotizacionEnSeguimiento> obtenerPartidasPorCotizacionEnSeguimiento(String FolioCotizacion) throws ProquifaNetException;
	/***
	 * 
	 * @param idPCotiza
	 * @return
	 * @throws ProquifaNetException
	 */
	List<HistorialPartidaEnSeguimiento> obtenerHistorialSeguimientoXPartida(Long idPCotiza) throws ProquifaNetException;
	/***
	 * 
	 * @param confirmacion
	 * @param Cliente
	 * @param Empleado
	 * @param Tipo
	 * @param Marca
	 * @param Control
	 * @return
	 * @throws ProquifaNetException
	 */
	List<SeguimientoCotizacion> obtenerCotizacionesEnSeguimientoEnPartidas(int confirmacion,String Cliente, String Empleado,String Tipo,String Marca,String Control,int  Master) throws ProquifaNetException;
}