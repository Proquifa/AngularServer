package com.proquifa.net.persistencia.comun;

import java.util.List;


import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.ObjetivoCrecimiento;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.tableros.cliente.ResumenVentasESAC;

public interface ObjetivoCrecimientoDAO {
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ObjetivoCrecimiento> findObjetivosCrecimientoPorNivelIngreso() throws ProquifaNetException;
	/***
	 * inserta objetivos de crecimiento por nivel de ingreso
	 * @param objetivos
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertObjetivosCrecimientoPorNivelIngreso(List<ObjetivoCrecimiento> objetivos, String usuario) throws ProquifaNetException;
	/***
	 * modifica los objetivos de crecimiento a nivel de ingreso
	 * @param objetivos
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateObjetivosCrecimientoPorNivelIngreso(List<ObjetivoCrecimiento> objetivos, String usuario) throws ProquifaNetException;
	/***
	 * Actualiza el objetivo de crecimiento de todos los clientes, tomando de base los objetivos de crecimiento por nivel de ingreso
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateObjetivosCrecimientoCliente() throws ProquifaNetException;
	/***
	 * Encuentra los clientes que no tiene objetivos de crecimiento mensual del ano en curso e inserta los registros en cero
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertClientesNuevosEnObjetivosCrecimiento() throws ProquifaNetException;
	/***
	 * Obtiene el objetivo de crecimiento de un Cliente
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double getObjetivoCrecimientoCliente(Long idCliente) throws ProquifaNetException;
	/**
	 * Obtiene el Objetivo de Crecimiento del Proveedor
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	Double getObjetivoCrecimientoProveedor(Long idProveedor) throws ProquifaNetException;
	Double getObjetivoCrecimientoFundamentalProveedor(Long idProveedor) throws ProquifaNetException;
	/***
	 * inserta los registro de objetivos de crecrimiento para un cliente
	 * @param cliente
	 * @param objetivo
	 * @param objetivoFundamental
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertObjetivoCrecimientoPorCliente(Long idCliente, String objetivo , String objetivoFundamental)	throws ProquifaNetException;
	/***
	 * modifica los objetivos de crecimiento de un cliente en especifico
	 * @param idCliente
	 * @param objetivoCrecimiento
	 * @param montoAnualAnterior
	 * @param objetivoTranscurrido
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateObjetivoCrecimientoPorCliente(Long idCliente, Double objetivoCrecimiento, Double montoAnualAnterior, Double objetivoTranscurrido, Double objetivoMontoTranscurrido) throws ProquifaNetException;
	/***
	 * modifica los objetivos de crecimiento mensuales del ano en curso de todos los proveedores segun su tipo
	 * @param estrategicos
	 * @param otros
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateObjetivosCrecimientoPorProveedor(String condicion) throws ProquifaNetException;
	/***
	 * inserta los  objetivos de crecimiento mensuales del ano en curso, de todos los proveedores que no esten en la tabla de Objetivo_Crecimiento
	 * @param estrategicos
	 * @param otros
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertObjetivosCrecimientoPorProveedor() throws ProquifaNetException;
	/***
	 * actualiza el porcentaje de crecimiento(Tabla Proveedores) de todos los proveedores, segun el tipo
	 * @param estrategicos
	 * @param otros
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updatePorcentajeDeCrecimientoPorTipoProveedor(Double objetivoCrecimiento,Double objetivoCrecimientoFundametale, String condicion) throws ProquifaNetException;
	
	/**
	 * @param cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoTranscurrido(Long cliente) throws ProquifaNetException;
	
	/**
	 * @param cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoMontoTranscurrido(Long cliente) throws ProquifaNetException;
	
	/**
	 * @param cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoFundamentalTranscurrido(Long cliente) throws ProquifaNetException;
	
	/**
	 * @param cliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoMontoFundamentalTranscurrido(Long cliente) throws ProquifaNetException;
	
	/**
	 * @param idCliente
	 * @param objetivoCrecimiento
	 * @param montoAnualAnterior
	 * @param objetivoTranscurrido
	 * @param objetivoMontoTranscurrido
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateObjetivoCrecimientoFundamentalPorCliente(Long idCliente, Double objetivoCrecimiento, Double montoAnualAnterior, Double objetivoTranscurrido, Double objetivoMontoTranscurrido) throws ProquifaNetException;
	/**
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Double getObjetivoCrecimientoFundamentalCliente(Long idCliente)	throws ProquifaNetException;
	/***
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean existObjetivoCrecimientoActual(Long idCliente) throws ProquifaNetException;
	/**
	 * Obtiene los montos por NI del ano anterior
	 * @param niveles
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ObjetivoCrecimiento>getMontosxNIAnterior(List<NivelIngreso> niveles, String sCondiciones) throws ProquifaNetException;
	/**
	 * @param niveles
	 * @param sCondiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ObjetivoCrecimiento>getMontosxNIActual(List<NivelIngreso> niveles, String sCondiciones) throws ProquifaNetException;
	/**
	 * Obtiene objetivos de crecimiento, historial de venta del ano anterior por Esac y EsacMaster
	 * @param condiciones
	 * @param niveles
	 * @param anio
	 * @return
	 * @throws ProquifaNetException
	 */
	List <ResumenVentasESAC> findobjetivoCrecimientoporESAC (String Condiciones , List<NivelIngreso> niveles,Long anio,String condicionesPeriodoHistorialVenta)throws ProquifaNetException;
	/**
	 * Obtiene objetivos de crecimiento, historial de venta del ano anterior por EV
	 * @param condiciones
	 * @param niveles
	 * @param anio
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ResumenVentasESAC>findobjetivoCrecimientoporEV(String condiciones ,List<NivelIngreso> niveles,Long anio) throws ProquifaNetException;
	/**
	 * Obtiene objetivos de crecimiento, historial de venta del ano anterior por cartera
	 * @param condiciones
	 * @param niveles
	 * @param anio
	 * @return
	 * @throws ProquifaNetException
	 */
	List <ResumenVentasESAC> findobjetivoCrecimientoporCartera (String Condiciones , List<NivelIngreso> niveles,Long anio,String condicionesPeriodoHistorialVenta)throws ProquifaNetException;
	/**
	 * @param objetivoCrecimiento
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean insertObjetivoCrecimientoProveedores(ObjetivoCrecimiento objetivoCrecimiento)throws ProquifaNetException;
	/**
	 * @param objetivoCrecimiento
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean updateObjetivoCrecimientoProveedores(ObjetivoCrecimiento objetivoCrecimiento)throws ProquifaNetException;
	/**
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	Long existObjetivoCrecimientoProveedorXTipo(String tipo)throws ProquifaNetException;
	/**
	 * @param tipo
	 * @return
	 * @throws ProquifaNetException
	 */
	Double obtenerObjetivoCrecimientoProveedorXObjetivo(String tipoObjetivo, String tipoProveedor)throws ProquifaNetException;
	
	List<ObjetivoCrecimiento>getMontosPeriodoProveedor(String sCondiciones) throws ProquifaNetException;
}
