/**
 * 
 */
package com.proquifa.net.persistencia.ventas;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.DoctoR;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;

/**
 * @author ernestogonzalezlozada
 *
 */
public interface CotizacionDAO {
	/**
	 * Recupera la cotizacion por folio
	 * @param folio
	 * @return
	 */
	List<Cotizacion> obtenerCotizacionPorFolio (String folio) throws ProquifaNetException;
	/**
	 * Recupera las cotizaciones en un rango de fechas
	 * @param folio
	 * @return
	 */
	List<Cotizacion> findCotizacionesParaReporte (String condiciones) throws ProquifaNetException;
	/***
	 * 
	 * @param folio
	 * @return
	 */
	List<Cotizacion> cotizacionAbiertaCerrada (String folio) throws ProquifaNetException;
	
	
	public DoctoR obtenerDatosContactoParaDoctoR(Integer contacto) throws ProquifaNetException;
	
	/**
	 * @param docto
	 * @return
	 * @throws ProquifaNetException
	 */
	public DoctoR saveDoctoR(DoctoR docto) throws ProquifaNetException;
	
	/**
	 * @param cotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	public Cotizacion saveCotizacion(Cotizacion cotizacion,boolean vieneDeRepotar) throws ProquifaNetException;
	
	/**
	 * @param partidas
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<PartidaCotizacion> savePartidasCotizacion(List<PartidaCotizacion> partidas) throws ProquifaNetException;
	
	/**
	 * @param segCotizas
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<SeguimientoCotizacion> saveSegCotiza(List<SeguimientoCotizacion> segCotizas) throws ProquifaNetException;
	/**
	 * @param List<Cotizacion>
	 * @return
	 * @throws ProquifaNetException
	 */
	
	boolean actualizarCotizacion(Cotizacion cotizacion) throws ProquifaNetException;
	
	/**
	 * @param idCotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean eliminarPartidasCotizacionPoridCotizacion(Long idCotizacion) throws ProquifaNetException;
	/**
	 * @param idVisitaCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean eliminarCotizacionPorIdVisita(Long idVisitaCliente) throws ProquifaNetException;
	/**
	 * @param idCotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarDoctosRidCotizacion(String folioCot, Integer folio) throws ProquifaNetException;
	/**
	 * @param idVisita
	 * @return
	 * @throws ProquifaNetException
	 */
	String obtenerFoliosDoctoR(Long idVisita) throws ProquifaNetException;
	
	boolean EliminarDoctors(String folios) throws ProquifaNetException;


    class FacturasFisicasDAOImpl {
    }
}
