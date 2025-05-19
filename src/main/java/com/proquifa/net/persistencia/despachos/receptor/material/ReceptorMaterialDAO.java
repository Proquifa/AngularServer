/**
 * 
 */
package com.proquifa.net.persistencia.despachos.receptor.material;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.ReceptorMaterial;

/**
 * @author ymendez
 *
 */
public interface ReceptorMaterialDAO {
	
	
	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<ReceptorMaterial>> getDatosGrafica(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<?> getGuias(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param guia
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<String> obtenerFolio(String guia) throws ProquifaNetException;
	
}
