/**
 * 
 */
package com.proquifa.net.persistencia.consultas;

import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.TiempoProceso;

/**
 * @author vromero
 *
 */
public interface ConsultaCobrosDAO {
	/***
	 * obtiene una lista de cobros que estan dentro de un periodo determinado y con las restricciones que se indican
	 * @param periodo
	 * @param restricciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Cobros> findCobros(String periodo, String restricciones, List<NivelIngreso> niveles) throws ProquifaNetException;
	/***
	 * obtiene el historial de factura
	 * @param restricciones
	 * @return
	 * @throws ProquifaNetException
	 */
	List<HistorialFactura> findHistorial(String restricciones) throws ProquifaNetException;
	/***
	 * 
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findLineaTiempoResumen(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Linea de tiempo 
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorEntrega(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Entrega
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorRevision(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Revision
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroGral(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de CobroGral
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroProgramacion(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Programacion
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroEjecucion(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Ejecucion
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroMonitoreo(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Monitoreo
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroHistorial(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Historial
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroSolicitud(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * obtiene Inspector de Solicitud
	 * @param Factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroSC(String factura, String cpedido) throws ProquifaNetException;
	/***
	 * 
	 * @return
	 */
	List<ResumenConsulta> findComparativasDPeriodos(String condiciones) throws ProquifaNetException;
}