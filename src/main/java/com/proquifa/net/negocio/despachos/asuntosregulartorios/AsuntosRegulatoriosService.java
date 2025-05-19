/**
 * 
 */
package com.proquifa.net.negocio.despachos.asuntosregulartorios;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.GestionarPap;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.PermisoAdquisicion;
import com.proquifa.net.modelo.despachos.asuntosregulatorios.TotalesPAP;

/**
 * @author ymendez
 *
 */
public interface AsuntosRegulatoriosService {

	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<GestionarPap> obtenerPendientesPap(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	public Map<String, List<TotalesPAP>> obtenerDatosGrafica(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param permiso
	 * @return
	 * @throws ProquifaNetException
	 */
	public Integer saveGestionarPAP(PermisoAdquisicion permiso) throws ProquifaNetException;
}
