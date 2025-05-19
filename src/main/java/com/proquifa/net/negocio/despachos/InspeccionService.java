package com.proquifa.net.negocio.despachos;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.ValorCombo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.ContadorPiezasXPrioridad;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import com.proquifa.net.modelo.despachos.parametrosInspeccion;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;

public interface InspeccionService {
	
	public Map<String, Object>  consultaPartidasInspeccion() throws ProquifaNetException;
	
	public Integer contadorSeccionArribados();
	
	public String obtenerConsecutivoDeLoteInspeccion(String concepto) throws ProquifaNetException;
	
	Object finalizarInspeccionDePartidas(parametrosInspeccion param) throws ProquifaNetException;

	totalesInspeccionProducto consultaDeTotalesPorInspector(String inspector) throws ProquifaNetException;
	
	public String obtenerModoInspeccion() throws ProquifaNetException;
	
	public  Boolean guardarTablaInspeccionTemporal(String inspector) throws ProquifaNetException;

	public PartidaInspeccion ordenarPartidaPorFolio(String folio) throws ProquifaNetException;

	public totalesInspeccionProducto obtenerTotalesDeinspecionPorUsuario(String inspector) throws ProquifaNetException;
	
	public Long piezasInspeccionadasHoy(String inspector) throws ProquifaNetException;
	
	public Long sumaPiezasInspeccionadasyPorInspeccion(String inspector) throws ProquifaNetException;
	
	public PartidaInspeccion ordenarPartidasInspeccion(String inspector) throws ProquifaNetException;
	
	public ContadorPiezasXPrioridad obtenerPiezasPorPrioridad() throws ProquifaNetException;

	List<PartidaInspeccion> obtenerPiezasXFecha() throws ProquifaNetException;
	
	public String convierteArr(Map<String, Object> documento) throws ProquifaNetException;

	public String enviaArchivo(InputStream archivo, String folioDocumentacion, String subCarpeta, String tipoArchivo)  throws ProquifaNetException;

	public String  obtenerUbicacionInspeccion(Map<String, Object> data) throws ProquifaNetException ;
	
	public Boolean guardarExistenciaUbicacion(Map<String, Object> data) throws ProquifaNetException;

	public Boolean guardarConsumible(Map<String, Object> data) throws ProquifaNetException;

	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public PartidaInspeccion obtenerPartida(Integer idUsuario) throws ProquifaNetException;

	public List<ValorCombo> obtenerComboUbicaciones(String tipo) throws ProquifaNetException;

	Object verificarApartado(parametrosInspeccion param) throws ProquifaNetException;


}
