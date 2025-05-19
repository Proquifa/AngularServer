package com.proquifa.net.negocio.consultas;
import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.consultas.comun.ArchivosFacturaCliente;
import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.comun.TiempoProceso;
/**
 * @author vromero
 *
 */
public interface ConsultaFacturacionService {
	/***
	 * Obtiene los datos de las facturas
	 * @param finicio
	 * @param ffin
	 * @param idCliente
	 * @param quienFactura
	 * @param estado
	 * @param tipo
	 * @param medio
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerFacturacion(Date finicio, Date ffin, Long idCliente, String quienFactura, String estado, String tipo, String medio, String factura) throws ProquifaNetException;



	/**
	 * Metodo que obtiene la informacion para la facturacion con el importe en dolar
	 * @param finicio
	 * @param ffin
	 * @param cliente
	 * @param estado
	 * @param refacturada
	 * @param facturo
	 * @param tipo
	 * @param medio
	 * @return {@link List}<{@link Facturacion}>
	 * @throws ProquifaNetException
	 */
	List<Facturacion> consultaAvanzadaFacturacion(Date finicio, Date ffin, String cliente, String estado,
			String refacturada, String facturo, String tipo, String medio, String cPago, Long idUsuarioLogueado, Long cobrador) throws ProquifaNetException;

	/** Metodo que obtiene la informacion para la factura con el importe en dolar
	 * @param factura
	 * @param cPedido
	 * @return {@link List}<{@link Facturacion}>
	 * @throws ProquifaNetException
	 */
	List<Facturacion> consultaRapidaFacturacion(String factura, String cPedido, String uuid, String fpor, Long idUsuarioLogueado) throws ProquifaNetException;

	/** Obtiene el Resumen de la linea del tiempo
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerResumen(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerResumenEntrega(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerResumenRevision(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerResumenCobro(String factura, String fpor, String SC) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerResumenFacturaRemision(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerResumenRefacturacion(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link Facturacion}>
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerResumenCancelacion(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link TiempoProceso}>
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerResumenFactura(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return {@link List}<{@link Facturacion}>
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerResumenFacturacionXAdelantado(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<TiempoProceso> obtenerLineaTiempoPrepago(String factura, String fpor) throws ProquifaNetException; 

	/**
	 * @param factura
	 * @param fpor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerResumenEnvioFactura(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Facturacion> obtenerResumenMonitoreoCobro(String factura, String fpor) throws ProquifaNetException;

	/**
	 * @param factura
	 * @param fpor
	 * @return
	 */
	List<Facturacion> obtenerResumenFacturaPrepago(String factura, String fpor);


	/**
	 * 
	 * @param lstFiles
	 * @param lstNombres
	 * @param rutaSave
	 * @return
	 * @throws Exception
	 */
	String generarZip(ArchivosFacturaCliente[] archivosFacturaCliente, String nombreFactura) throws Exception;
}
