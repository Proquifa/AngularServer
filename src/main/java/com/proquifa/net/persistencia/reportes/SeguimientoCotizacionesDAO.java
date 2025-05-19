package com.proquifa.net.persistencia.reportes;

import java.util.List;

import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.HistorialPartidaEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;

public interface SeguimientoCotizacionesDAO {
	/***
	 * 
	 * @param Cliente
	 * @param Empleado
	 * @return
	 */
	public List<SeguimientoCotizacion> findSeguimientoCotizacion(String Cliente,String Empleado,int confirmacion, List<NivelIngreso> niveles);
	/***
	 * 
	 * @param FolioCotizacion
	 * @return
	 */
	public List<PartidaCotizacionEnSeguimiento> findPartidasPorCotizacionEnSeguimiento(String FolioCotizacion);
	
	/***
	 * 
	 * @param idPCotiza
	 * @return
	 */
	public List<HistorialPartidaEnSeguimiento> finHistorialSeguimientoXPartida(Long idPCotiza);
	/***
	 * 
	 * @param confirmacion
	 * @param Cliente
	 * @param Empleado
	 * @param condiciones
	 * @return
	 */
	public List<SeguimientoCotizacion> findSeguimientoCotizacionEnPartida(int confirmacion,String Cliente, String Empleado, String condiciones,Integer esacMaster, List<NivelIngreso> niveles);
}