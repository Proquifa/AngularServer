package com.proquifa.net.persistencia.despachos.trabajarRuta;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.colectarPartidas;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrEnvio;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;

public interface TrEnvioDAO {

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
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Integer> getMontosTab() throws ProquifaNetException;

	/**
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<TrPackingList>> getPackingListClient(int idCliente) throws ProquifaNetException;

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public int getClientePrioridad() throws ProquifaNetException;
	
	
	public Map<String, List<TrEnvio>> obtenerEstadisticaUsuario(Parametro  parametro) throws ProquifaNetException;
	
	public boolean registrarEnvio(TrEnvio envio) throws ProquifaNetException;
	
	
	public Boolean actualizarEnvio(TrEnvio envio) throws ProquifaNetException;

	public Map<String, List<TrEnvio>> obtenerTiempoTrabajoEnvio() throws ProquifaNetException;

	/**
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	public colectarPartidas obtenerClientePorEnvio(Integer idCliente) throws ProquifaNetException;

	/**
	 * @param envio
	 * @return
	 * @throws ProquifaNetException
	 */
	Boolean actualizarEnvioGDL(TrEnvio envio) throws ProquifaNetException;

	public List<TrAlmacen> obtenerClientes() throws ProquifaNetException;

	public Map<String, List<TrPackingList>> packingListPorCliente(int idCliente) throws ProquifaNetException;

	public Map<String, Integer> obtenerTotales() throws ProquifaNetException;

	public Map<String, List<TrEnvio>> obtenerTiempoXCliente() throws ProquifaNetException;

	public Map<String, List<TrEnvio>> PrioridadesPagoCliente(Integer IdUsuario) throws ProquifaNetException;

	public String obtenerComentarios(String packingList) throws ProquifaNetException;

	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarPesoPaquete(Parametro parametro) throws ProquifaNetException;

	/**
	 * @param parametro
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean guardarPackingList(Parametro parametro) throws ProquifaNetException;
	

}
