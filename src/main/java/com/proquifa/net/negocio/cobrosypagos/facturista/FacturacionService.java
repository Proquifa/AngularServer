package com.proquifa.net.negocio.cobrosypagos.facturista;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;

/**
 * @author sarivera
 *
 */
public interface FacturacionService {
	/**
	 * Metodo que Recupera toda la informacion para Facturacion 
	 * @param facturam busqueda, finicio, ffin , cliente, medio , facturo, estado, tipo, refacturada
	 * @return 
	 */ 
	List<Facturacion> consultaFacturacion (String factura, String busqueda, Date finicio, Date ffin, String cliente, String medio, String facturo, String estado, String tipo, String refacturada) throws ProquifaNetException;
	/***
	 * 	Obtiene el historial de la factura correspondiente.
	 * @param idFactura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<HistorialFactura> listarHistorialXFactura(Long idFactura) throws ProquifaNetException;
	/***
	 * Obtiene el numero de folios Asignados, Total de usados, y los usados en el mes actual
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> obtenerFolios() throws ProquifaNetException;
	/***
	 * 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> infoEmpresa() throws ProquifaNetException;
	/***
	 * Obtiene los conceptos para la factura de una determinada empresa
	 * @return List ConceptoFactura
	 * @throws ProquifaNetException
	 */
	List<ConceptoFactura> obtenerConceptosFactura(Integer idEmpresa) throws ProquifaNetException;
	/***
	 * Obtiene todos los clientes habilitados
	 * @return List clientes
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesHabilitados() throws ProquifaNetException;
	/***
	 * Obtiene El tipo de cambio requerido
	 * @param String moneda
	 * @return Float tipo
	 * @throws ProquifaNetException
	 */
	Float getTipoCambio(String moneda) throws ProquifaNetException;
	/***
	 * Regresa una lista de todas las facturas por pagar, que se realizaron en ProquifaNet
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> consultarFacturasEmitidasPQNet() throws ProquifaNetException;
	/***
	 * Regresa una lista de todas las facturas emitidas en un periodo
	 * @param finicio
	 * @param ffin
	 * @param idCliente
	 * @param fpor
	 * @param estado
	 * @param dentroSistema
	 * @param factura
	 * @param idUsuarioLogueado
	 * @param cobrador
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> listarFacturasEmitidas(Date finicio, Date ffin, Long idCliente, String fpor, String estado, Boolean dentroSistema, Long factura, Long idUsuarioLogueado, Long cobrador, String uuid) throws ProquifaNetException;
	/***
	 * obtiene el total facturado a un cliente, por una empresa especifica en un periodo de tiempo
	 * @param idEmpresa
	 * @param idCliente
	 * @param ffinicio
	 * @param ffin
	 * @return
	 * @throws ProquifaNetException
	 */
	Factura obtenerTotalFacturadoClienteEmpresa(String empresa, Long idCliente, Date ffinicio, Date ffin, int folioFactura) throws ProquifaNetException;
	/***
	 * Obtener el monto total de facturacion - notas de credito, de un cliente, empresa; en un perido
	 * @param empresa
	 * @param idCliente
	 * @param ffinicio
	 * @param ffin
	 * @return
	 * @throws ProquifaNetException
	 */
	Double calcularMontoFacturacionNotaCredito(String empresa, Long idCliente, Date ffinicio, Date ffin) throws ProquifaNetException;
	/***
	 * Obtiene el monto total de la factura menos las partidas canceladas, en nota de credito
	 * @param factura
	 * @param empresa
	 * @return
	 * @throws ProquifaNetException
	 */
	Factura obtenerMontoFactura(Long factura, String empresa) throws ProquifaNetException;
	/**
	 * Inserta en base de datos la informacion de una factura para retomar en tiempo posterior , debuelve el id del registro 
	 * @param doctoFiscal
	 * @return
	 * @throws ProquifaNetException
	 */
	Long GuardarDocumentoFiscal(Factura doctoFiscal ) throws ProquifaNetException;
	
	/**
	 * Actualiza todos los datos del docto fiscal guardado en base a su id 
	 * @param doctoFiscal
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean actualizarDocumentoFiscal (Factura doctoFiscal )throws ProquifaNetException;
	
	/**
	 * elimina un registro en base a su id 
	 * @param idDoctoFiscal
	 * @return
	 * @throws ProquifaNetException
	 */
	boolean eliminarDocumentoFiscal(Long idDoctoFiscal) throws ProquifaNetException;
	
	/**
	 * se obtiene una lista de tipo comprabante fiscal con el filtrado por el parametro de entrada ejemplo Factura/Cliente o NotaCredito/Todas
	 * @param fpor
	 * @param idCliente
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> obtenerComprobantesFiscales(String fpor,Long idCliente) throws ProquifaNetException;

}
