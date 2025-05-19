package com.proquifa.net.negocio.despachos.trabajarRuta;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;


public interface TrEnvioService {

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Integer> getObjetivos() throws ProquifaNetException;

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Map<String, List<TrTotalGraficasAlmacen>>> getGraficas() throws ProquifaNetException;

	/**
	 * 
	 * @param envio
	 * @return
	 */
	public boolean finalizarEjecutarRutaEnvio(TrEnvio envio);

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Integer> getMontosTab() throws ProquifaNetException;

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Object> getPackingListClient() throws ProquifaNetException;

	
	public Map<String, List<TrEnvio>> obtenerEstadisticasUsuarioTR(Parametro parametro) throws ProquifaNetException;
	
	public boolean registrarEnvio(TrEnvio envio) throws ProquifaNetException;
	
	public boolean actualizarEnvio(TrEnvio envio) throws ProquifaNetException;
	
	public Map<String, List<TrEnvio>> obtenerTiempoTrabajoEnvio() throws ProquifaNetException;

	public List<TrAlmacen> obtenerCliente() throws ProquifaNetException;

	public Map<String, Object> packingListPorCliente(Integer idCliente) throws ProquifaNetException;

	public Map<String, Integer> obtenerTotalesObjetivos() throws ProquifaNetException;

	public Map<String, List<TrEnvio>> obtenerTiempoXCliente() throws ProquifaNetException;

	public Map<String, List<TrEnvio>> estadisticasPagoCliente(Integer IdUsuario) throws ProquifaNetException;

	
	
}
