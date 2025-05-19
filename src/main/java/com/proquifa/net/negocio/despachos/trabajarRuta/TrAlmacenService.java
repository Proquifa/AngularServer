package com.proquifa.net.negocio.despachos.trabajarRuta;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrAlmacen;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrContactoCliente;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrPackingList;
import com.proquifa.net.modelo.despachos.trabajarRuta.TrTotalGraficasAlmacen;

public interface TrAlmacenService {

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
	public List<TrAlmacen> getClientes() throws ProquifaNetException;

	/**
	 * 
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<TrPackingList>> getPackingListClient(int idCliente) throws ProquifaNetException;

	/**
	 * 
	 * @param trContactoCliente
	 * @return
	 */
	public boolean finalizarEjecutarRutaAlmacen(TrContactoCliente trContactoCliente) throws ProquifaNetException;

	/**
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, Integer> getMontosTab() throws ProquifaNetException;

	
	public Map<String, List<TrPackingList>> obtenerEstadisticasUsuarioTR(Parametro parametro) throws ProquifaNetException;
}
