/**
 * 
 */
package com.proquifa.net.negocio.comun;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.GestionarBO;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

/**
 * @author ymendez
 *
 */
public interface GestionarBOService {

	/**
	 * @param responsable
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Object> obtenerGraficaProveedor() throws ProquifaNetException;

	/**
	 * @param idProveedor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<GestionarBO> obtenerProductosBOPorProveedor(Integer idProveedor) throws ProquifaNetException;

	/**
	 * @param data
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean finalizarProductoBO(Map<String, Object> data) throws ProquifaNetException;
}
