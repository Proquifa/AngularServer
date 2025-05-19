package com.proquifa.net.negocio.consultas;

import java.util.List;

import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.consultas.comun.ParametrosBusquedaCobros;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;


public interface ConsultaCobrosService {
	/***
	 * Obtiene los cobros segun los datos que se mandan
	 * @param FechaInicio
	 * @param FechaFin
	 * @param idCliente
	 * @param medioPago
	 * @param fpor
	 * @param estado
	 * @param cpago
	 * @param drc
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cobros> obtenerCobros(ParametrosBusquedaCobros parametros) throws ProquifaNetException;
	
	/***
	 * 
	 * @param factura
	 * @param fpor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<HistorialFactura> obtenerHistorialFactura(String factura, String fpor) throws ProquifaNetException;
	
	/***
	 * 
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerLineaTiempoResumen(String factura, String cpedido)	throws ProquifaNetException;
	
	/***
	 * 
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerInspectorEntrega(String factura, String cpedido)	throws ProquifaNetException;
	
	/***
	 * 
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerInspectorRevision(String factura, String cpedido)	throws ProquifaNetException;
	/***
	 * 
	 * @param factura
	 * @param cpedido
	 * @param SC
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerInspectorCobro(String factura, String cpedido,String SC)	throws ProquifaNetException;
	/***
	 * 	
	 * @return
	 * @throws ProquifaNetException
	 */
	List<ResumenConsulta> obtenerComparativasDPeriodos(ParametrosBusquedaCobros parametros) throws ProquifaNetException;
}
