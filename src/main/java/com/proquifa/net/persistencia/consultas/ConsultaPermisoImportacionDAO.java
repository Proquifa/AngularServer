package com.proquifa.net.persistencia.consultas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.comun.ResumenPermisoImportacion;
import com.proquifa.net.modelo.comun.TiempoProceso;

public interface ConsultaPermisoImportacionDAO {
	
	/**
	 * @param finicio
	 * @param ffin
	 * @param proveedor
	 * @param catalogo
	 * @param tipoPermiso
	 * @param tipoProducto
	 * @param subTipo
	 * @param clasificacion
	 * @param control
	 * @param estado
	 */

	public List<PermisoImportacion> consultaPermisos(Date finicio, Date ffin, Long proveedor, String catalogo, String tipoPermiso, 
			String tipoProducto, String subTipo, String clasificacion, String control, String estadoFisico, String estado);
	
	/**
	 * @param idProducto
	 * @return
	 */
	public List<TiempoProceso> obtenerLineaTiempo(Long idProducto);
	
	/**
	 * @param idProducto
	 * @return
	 */
	public List<ResumenPermisoImportacion> resumenPrevioTramitacion(Long idProducto);
	
	/**
	 * @param idProducto
	 * @return
	 */
	public List<ResumenPermisoImportacion> resumenTramitarPermiso(Long idProducto);
	
	/**
	 * @param idProducto
	 * @return
	 */
	public List<ResumenPermisoImportacion> resumenEnAutorizacion(Long idProducto);
	
	/**
	 * @param idProducto
	 * @return
	 */
	public List<ResumenPermisoImportacion> resumenMonitorearResumen(Long idProducto);
	
	/**
	 * @param idProducto
	 * @return
	 */
	public List<ResumenPermisoImportacion> resumenPermiso(Long idProducto);
	
	/**
	 * @param finicio
	 * @param ffin
	 * @param proveedor
	 * @param catalogo
	 * @param tipoPermiso
	 * @param tipoProducto
	 * @param subTipo
	 * @param clasificacion
	 * @param control
	 * @param estadoFisico
	 * @param estado
	 * @return
	 */
	public PermisoImportacion obtenerDetallesGrafica(Date finicio, Date ffin, Long proveedor, String catalogo, String tipoPermiso, 
			String tipoProducto, String subTipo, String clasificacion, String control, String estadoFisico, String estado);
	
	
	
}
