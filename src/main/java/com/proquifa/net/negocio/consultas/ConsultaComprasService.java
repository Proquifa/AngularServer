/**
 * 
 */
package com.proquifa.net.negocio.consultas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.comun.TiempoProceso;

/**
 * @author vromero
 *
 */
public interface ConsultaComprasService {
	/**
	 * Regresa una lista de compras se puede filtrar por fecha, proveedor, estado y quien la coloco 
	 * @param FechaInicio
	 * @param FechaFin
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	
	

	/**
	 * Regresa una lista de compras se puede filtrar por fecha, proveedor, estado y quien la coloco 
	 * @param FechaInicio
	 * @param FechaFin
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @return Lista de Compra
	 * @throws ProquifaNetException
	 */
	List<Compra> obtenerReporteCompra(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, Integer coloco, String ordenCompra) throws ProquifaNetException;
	

		 
	
	
	List<Compra> obtenerCompras(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, String ordenCompra, Long usuario, String empresaCompra) throws ProquifaNetException;
	/**
	 * Se utiliza en el reporte de Compras, para especificar los datos de una orden de compra
	 * @param ordenCompra
	 * @return Lista de Partidas Compra
	 */
	
	List<PartidaCompra> obtenerPatidasCompraParaCEspecifica(String ordenCompra) throws ProquifaNetException;
	/**
	 * Obtiene una lista de TiempoProceso con un idPCompra, se utiliza en el reporte de Compras.
	 * @param idpcompra
	 * @return
	 */
	List<TiempoProceso> obtenerTiempoProcesoPorPartida(Long idPCompra) throws ProquifaNetException;	
	/***
	 * Obtiene los totales de la consulta
	 * @param FechaInicio
	 * @param FechaFin
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	
	
	
	List<ResumenConsulta> obtenerResumenDeConsultaCompras(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, Integer coloco, String ordenCompra, Long usuario) throws ProquifaNetException;
	
	/***
	 * Obtiene los totales de la consulta como Monto, pedidos y piezas
	 * @param finicio
	 * @param ffin
	 * @param proveedor
	 * @param usuario
	 * @param estado
	 * @param ordenCompra
	 * @param coloco
	 * @return {@link ResumenConsulta}
	 * @throws ProquifaNetException
	 */
	List<ResumenConsulta> findTotalesCompraPorPeriodo(Date finicio, Date ffin, Long proveedor, Integer estado, Integer coloco, String ordenCompra, Long usuario,Boolean individual) throws ProquifaNetException;

	
	/**
	 * @param FechaInicio
	 * @param FechaFin
	 * @param proveedor
	 * @param estado
	 * @param coloco
	 * @param ordenCompra
	 * @param usuario
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Compra> obtenerGraficaXCompra(Date FechaInicio, Date FechaFin, Long proveedor, Integer estado, Integer coloco, String ordenCompra, Long usuario) throws ProquifaNetException;
}
