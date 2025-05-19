/**
 * 
 */
package com.proquifa.net.negocio.ventas;

import java.util.Date;
import java.util.List;


import com.proquifa.net.modelo.comun.Producto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.DoctoR;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;


/**
 * @author fmartinez
 *
 */
public interface CotizacionService {
	/**
	 * Metodo que recupera la cotizacion por folio
	 * @param folio
	 * @return
	 * @throws 
	 */
	List<Cotizacion> buscarCotizacionPorFolio (String folio) throws ProquifaNetException;
	
	/**
	 *Metodo que recupera un listado de cotizaciones por ciertos parametros.
	 * @throws 
	 */
	List<Cotizacion> buscarCotizacionesParaReporte(Date finicio, Date ffin, Cotizacion cotizacion, Long idEmpleado) throws ProquifaNetException;
	
	/**
	 *
	 * */
	
	public boolean generarCotizacionPconnect(Integer idContacto, Integer idProducto) throws ProquifaNetException;
	
	/**
	 * @param docto
	 * @return
	 * @throws ProquifaNetException
	 */
	public DoctoR generarDoctoR(DoctoR docto) throws ProquifaNetException;
	
	/**
	 * @param cotizacion
	 * @return
	 * @throws ProquifaNetException
	 */
	public Cotizacion guardarCotizacion(Cotizacion cotizacion) throws ProquifaNetException;
	
	/**
	 * @param partidas
	 * @return
	 * @throws ProquifaNetException
	 */
	public List<PartidaCotizacion> guardarPartidasCotizacion(List<PartidaCotizacion> partidas) throws ProquifaNetException;
	
	/**
	 * 
	 * @param idContacto
	 * @param productos
	 * @param previsualizar
	 * @return
	 * @throws ProquifaNetException
	 */
	public boolean generarCotizacionReporte(Integer idContacto, List<Producto> productos,Boolean previsualizar,String empresa) throws ProquifaNetException;
	
}