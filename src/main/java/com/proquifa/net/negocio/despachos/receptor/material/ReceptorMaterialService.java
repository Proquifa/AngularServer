/**
 * 
 */
package com.proquifa.net.negocio.despachos.receptor.material;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.ReceptorMaterial;

/**
 * @author ymendez
 *
 */
public interface ReceptorMaterialService {

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
	 * @param object
	 * @return
	 * @throws ProquifaNetException
	 */
	public Object finalizarEnvio(String guia, Integer idUsuario, Integer idPendiente) throws ProquifaNetException;
}
