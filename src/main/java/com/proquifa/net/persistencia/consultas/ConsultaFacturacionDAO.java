/**
 * 
 */
package com.proquifa.net.persistencia.consultas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.comun.TiempoProceso;

/**
 * @author vromero
 *
 */
public interface ConsultaFacturacionDAO {
	/***
	 * regresa una lista con la informacion de la facturacion, filtrada por las condiciones 
	 * @param condiciones
	 * @param periodo
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Facturacion> findConsultaFacturacion(String condiciones) throws ProquifaNetException;
	
	/**
	 * Metodo que obtiene la informacion para la facturacion con el importe en dolar
	 * @param factura
	 * @param cPedido
	 * @param finicio
	 * @param ffin
	 * @param cliente
	 * @param estado
	 * @param refacturada
	 * @param facturo
	 * @param tipo
	 * @param medio
	 * @param cPago
	 * @return {@link List}<{@link Facturacion}>
	 */
	List<Facturacion> consultaAvanzadaFacturacion(String factura, String cPedido, String uuid, Date finicio, Date ffin, 
			String cliente, String estado, String refacturada, String facturo, String tipo, String medio, String cPago, String consulta, String condiciones);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> findLineaTiempoResumen(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenFacturaRemision(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenRefacturacion(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenCancelacion(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> findInspectorSolicitudRecoleccionCheque(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> findInspectorRecoleccionCheque(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> obtenerResumenFactura(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> findInspectorCobroProgramacion(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> findInspectorCobroMonitoreo(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> findInspectorEntrega(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenFacturacionXAdelantado(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<TiempoProceso> obtenerLineaTiempoPrepago(String factura, String fpor, String profor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenEnvioFactura(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenMonitoreoCobro(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenFacturaPrepago(String factura, String fpor);
	
	/**
	 * @param factura
	 * @param cpedido
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> findInspectorCobroSC(String factura, String cpedido) throws ProquifaNetException;
}
