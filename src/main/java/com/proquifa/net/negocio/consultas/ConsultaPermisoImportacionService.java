package com.proquifa.net.negocio.consultas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.comun.ResumenPermisoImportacion;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;


public interface ConsultaPermisoImportacionService {
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
	 * @throws ProquifaNetException
	 */
	public List<PermisoImportacion> consultaPermisos(Date finicio, Date ffin, Long proveedor, String catalogo, String tipoPermiso, 
			String tipoProducto, String subTipo, String clasificacion, String control, String estadoFisico, String estado) throws ProquifaNetException;
	
	/**
	 * @param catalogo
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<PermisoImportacion> consultaPermisosBusquedaRapida(String catalogo) throws ProquifaNetException;
	
	/**
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<TiempoProceso> obtenerLineaTiempo(Long idProducto) throws ProquifaNetException;
	
	/**
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ResumenPermisoImportacion> resumenPrevioTramitar(Long idProducto) throws ProquifaNetException;
	
	/**
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ResumenPermisoImportacion> resumenTramitarPermiso(Long idProducto) throws ProquifaNetException;
	
	/**
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ResumenPermisoImportacion> resumenEnAutorizacion(Long idProducto) throws ProquifaNetException;
	
	/**
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ResumenPermisoImportacion> resumenMonitorearResumen(Long idProducto) throws ProquifaNetException;
	
	/**
	 * @param idProducto
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<ResumenPermisoImportacion> resumenPermiso(Long idProducto) throws ProquifaNetException;
	
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
	 * @param individual
	 * @return
	 * @throws ProquifaNetException
	 */
	
	public List<PermisoImportacion> obtenerDetallesGrafica
	(Date finicio, Date ffin, Long proveedor,String catalogo, 
			String tipoPermiso, 
			String tipoProducto, 
			String subTipo,
			String clasificacion, 
			String control, 
			String estadoFisico, 
			String estado,
			boolean individual) throws ProquifaNetException;
	

}
