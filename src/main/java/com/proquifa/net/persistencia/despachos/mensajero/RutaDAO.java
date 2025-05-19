/**
 * 
 */
package com.proquifa.net.persistencia.despachos.mensajero;

import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.despachos.mensajero.AsignarMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.Ruta;
import com.proquifa.net.modelo.despachos.mensajero.TotalMensajero;

/**
 * @author ymendez
 *
 */
public interface RutaDAO {

	
	/**
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean saveRutaEvento(AsignarMensajero ruta) throws ProquifaNetException;
	
	/**
	 * @param folio
	 * @param tabla
	 * @param campo
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean actualizarEvento(String ruta, String folio, String tabla, String campo) throws ProquifaNetException;

	/**
	 * @param idRuta
	 * @param idRutaDP
	 * @param packingList
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean saveRutaDP(String idRuta, String idRutaDP, String packingList) throws ProquifaNetException;

	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<AsignarMensajero> obtenerDatosCerrarRuta(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param idUsuario
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, List<TotalMensajero>> obtenerDatosGraficaCerrarRuta(Integer idUsuario) throws ProquifaNetException;

	/**
	 * @param idMensajero
	 * @return
	 * @throws ProquifaNetException
	 */
	List<AsignarMensajero> obtenerComparacionRuta(Integer idMensajero) throws ProquifaNetException;

	/**
	 * @param folio
	 * @param justificacion
	 * @param calificacion
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer guardarRuta(Ruta ruta) throws ProquifaNetException;

	/**
	 * @param ruta
	 * @throws ProquifaNetException
	 */
	//public boolean estadoAGenerar(Ruta ruta, String idRuta) throws ProquifaNetException;
	
	/**
	 * 
	 * @param id
	 * @param tipo
	 * @param folio
	 * @return
	 * @throws ProquifaNetException
	 */
	public Boolean actualizarRutaDP(String id, String tipo, String folio ) throws ProquifaNetException ;

	/**
	 * @param idRuta
	 * @param idRutaDP
	 * @param packingList
	 * @param is
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean saveRutaDP(String idRuta, String idRutaDP, String packingList, boolean is) throws ProquifaNetException;

	/**
	 * 
	 * @param pendiente
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean estadoAGenerar(PendientesMensajero pendiente, String mensajero) throws ProquifaNetException;

	/**
	 * @param idRuta
	 * @return
	 * @throws ProquifaNetException
	 */
	List<String[]> reporteAsingarMensajero(String idRuta) throws ProquifaNetException;
}


