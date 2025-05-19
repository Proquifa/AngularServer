package com.proquifa.net.negocio.consultas.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.ResumenPermisoImportacion;
import com.proquifa.net.negocio.consultas.ConsultaPermisoImportacionService;
import com.proquifa.net.persistencia.consultas.ConsultaPermisoImportacionDAO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("consultaPermisoImportacionService")
public class ConsultaPermisoImportacionServiceImpl implements ConsultaPermisoImportacionService {
	

	@Autowired
	ConsultaPermisoImportacionDAO consultaPermisosDAO;
	
	private Funcion funcion = new Funcion();
	
	@Override
	public List<TiempoProceso> obtenerLineaTiempo(Long idProducto) throws ProquifaNetException {
		try{
		return consultaPermisosDAO.obtenerLineaTiempo(idProducto);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, idProducto);
		}return new ArrayList<TiempoProceso>();
	}

	@Override
	public List<ResumenPermisoImportacion> resumenMonitorearResumen(Long idProducto)
			throws ProquifaNetException {
		try{
		return consultaPermisosDAO.resumenMonitorearResumen(idProducto);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, idProducto);
		}return new ArrayList<ResumenPermisoImportacion>();
	}

	@Override
	public List<ResumenPermisoImportacion> resumenEnAutorizacion(Long idProducto) throws ProquifaNetException {
		try{
		return consultaPermisosDAO.resumenEnAutorizacion(idProducto);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, idProducto);
		}
		return new ArrayList<ResumenPermisoImportacion>();
	}

	@Override
	public List<ResumenPermisoImportacion> resumenPrevioTramitar(Long idProducto)
			throws ProquifaNetException {
		try{
		return consultaPermisosDAO.resumenPrevioTramitacion(idProducto);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, idProducto);
		}return new ArrayList<ResumenPermisoImportacion>();
	}

	@Override
	public List<ResumenPermisoImportacion> resumenTramitarPermiso(Long idProducto)
			throws ProquifaNetException {
		try{
		return consultaPermisosDAO.resumenTramitarPermiso(idProducto);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, idProducto);
		}
		return new ArrayList<ResumenPermisoImportacion>();
	}

	@Override
	public List<ResumenPermisoImportacion> resumenPermiso(Long idProducto) throws ProquifaNetException {
		try{
		return consultaPermisosDAO.resumenPermiso(idProducto);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, idProducto);
		}return new ArrayList<ResumenPermisoImportacion>();
	}

	@Override
	public List<PermisoImportacion> consultaPermisos(Date finicio, Date ffin, Long proveedor,
			String catalogo, String tipoPermiso, String tipoProducto,
			String subTipo, String clasificacion, String control, String estadoFisico, String estado)
			throws ProquifaNetException {
		try{
		List<PermisoImportacion> permisos = consultaPermisosDAO.consultaPermisos(finicio, ffin, proveedor, catalogo, tipoPermiso, tipoProducto, subTipo, 
				clasificacion, control, estadoFisico, estado);
		
		return permisos;
		}
		catch(Exception e){
		funcion.enviarCorreoAvisoExepcion(e, "finicio:"+finicio,"ffin:"+ffin, "proveedor:"+proveedor,tipoPermiso,tipoProducto,subTipo,clasificacion,control,estadoFisico,estado);	
		}
		return new ArrayList<PermisoImportacion>();
	}

	@Override
	public List<PermisoImportacion> consultaPermisosBusquedaRapida(
			String catalogo) throws ProquifaNetException {
		try{
		return consultaPermisos(null, null, null, catalogo, null, null, null, null, null, null, null);
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, catalogo);
		}
		return new ArrayList<PermisoImportacion>();
	}

	@Override
	public List<PermisoImportacion> obtenerDetallesGrafica(Date finicio, Date ffin, Long proveedor,
			String catalogo, String tipoPermiso, String tipoProducto,
			String subTipo, String clasificacion, String control, String estadoFisico, String estado,
			boolean individual ) throws ProquifaNetException {
		try{
		List<PermisoImportacion> lstPermiso = new ArrayList<PermisoImportacion>();
		
		if (individual){
			lstPermiso.add(consultaPermisosDAO.obtenerDetallesGrafica(finicio, ffin, proveedor, catalogo, tipoPermiso, tipoProducto, 
					subTipo, clasificacion, control, estadoFisico, estado));
		}else{
			if (finicio != null && ffin != null){
				Date finicio2, finicio3, ffin2, ffin3;
				Date[] rango = null;
				rango = funcion.calcularFechasPeriodoAnterior(finicio, ffin);
				finicio2 = rango[0];
				ffin2 = rango[1];
				rango = funcion.calcularFechasPeriodoAnterior(finicio2, ffin2);
				finicio3 = rango[0];
				ffin3 = rango[1];
				
				List<PermisoImportacion> arrTemp = new ArrayList<PermisoImportacion>();
				
				arrTemp.add(consultaPermisosDAO.obtenerDetallesGrafica(finicio, ffin, proveedor, catalogo, tipoPermiso, 
						tipoProducto, subTipo, clasificacion, control, estadoFisico, estado));
				
				for (PermisoImportacion permisoImportacion : arrTemp) {
					permisoImportacion.setFecha(finicio);
					permisoImportacion.setFechaFin(ffin);
					permisoImportacion.setEtiqueta("actual");
					lstPermiso.add(permisoImportacion);
				}
				arrTemp.clear();
				arrTemp.add(consultaPermisosDAO.obtenerDetallesGrafica(finicio2, ffin2, proveedor, catalogo, tipoPermiso, tipoProducto, 
						subTipo, clasificacion, control, estadoFisico, estado));
				for (PermisoImportacion permisoImportacion : arrTemp) {
					permisoImportacion.setFecha(finicio2);
					permisoImportacion.setFechaFin(ffin2);
					permisoImportacion.setEtiqueta("pasado");
					lstPermiso.add(permisoImportacion);
				}
				arrTemp.clear();
				arrTemp.add(consultaPermisosDAO.obtenerDetallesGrafica(finicio3, ffin3, proveedor, catalogo, tipoPermiso, tipoProducto, 
						subTipo, clasificacion, control, estadoFisico, estado));
				for (PermisoImportacion permisoImportacion : arrTemp) {
					permisoImportacion.setFecha(finicio3);
					permisoImportacion.setFechaFin(ffin3);
					permisoImportacion.setEtiqueta("postpasado");
					lstPermiso.add(permisoImportacion);
				}
//				log.info();
				
			}
		}	
		 return lstPermiso;
		}
		catch(Exception e){
			funcion.enviarCorreoAvisoExepcion(e, "finicio:"+finicio,"ffin:"+ffin,"proveedor:"+proveedor,catalogo,tipoPermiso,tipoProducto,subTipo,clasificacion,control,estadoFisico,estado);
			}
		return new ArrayList<PermisoImportacion>();
	}

}
