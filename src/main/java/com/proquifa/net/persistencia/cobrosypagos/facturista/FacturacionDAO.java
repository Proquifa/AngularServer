package com.proquifa.net.persistencia.cobrosypagos.facturista;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ComplementoPago;
import com.proquifa.net.modelo.cobrosypagos.facturista.ComprobanteFiscal;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;

/**
 * @author sarivera
 *
 */

public interface FacturacionDAO {
	/**
	 * Recupera toda la informacion para Facturacion 
	 * @param facturam busqueda, finicio, ffin , cliente, medio , facturo, estado, tipo, refacturada
	 * @return 
	 */ 
	List<Facturacion> consultaFacturacion (String factura, String busqueda, Date finicio, Date ffin, String cliente, String medio, String facturo, String estado, String tipo, String refacturada);
	
	/**
	 * Metodo que recupera las facturas por cobrar de un cliente
	 * @param idCliente
	 * @return
	 */
	List<Factura> obtenerFacturasPorCobrar(Long idCliente);
	/**
	 * Metodo que recupera la factura de un pedido 
	 * @param part, cpedido 
	 * @return
	 */
	List<Factura> obtenerFacturaPedido(Integer part, String cPedido);
	/**
	 * Metodo que recupera la fecha de factura
	 * @param cPedido
	 * @return
	 */
	Date obtenerFechaFactura(String cPedido);
	/***
	 * Metodo que obtiene una factura por  folio y quie las facturo.
	 * @param factura
	 * @param empresaFactura
	 * @return
	 */
	List<Factura> findFacturasXFolio (Long factura, String empresaFactura);
	/***
	 * Busca un conjunto de facturas, en base a determinados parametros.
	 * @param factura
	 * @param finicio
	 * @param ffin
	 * @return
	 */
	List<Factura> findFacturas(Factura factura, Date finicio, Date ffin);
	/***
	 * Obtiene el historial de la factura correspondiente.
	 * @param factura
	 * @param empresaFactura
	 * @return
	 */
	List<HistorialFactura> findHistorialXFactura (Long idFactura);
	/***
	 * Obtiene el umero de folios Asignados, Total de usados, y los usados en el mes actual
	 * @return
	 */
	List<Empresa> consultaFolios();
	/***
	 * Obtiene la informacion de la empresa 
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Empresa> getInfoEmpresa();
	/***
	 * Obtiene los conceptos para la factura 
	 * @return List ConceptoFactura
	 * @throws ProquifaNetException
	 */
	List<ConceptoFactura> obtenerConceptosFactura(Integer idEmpresa);
	/***
	 * Obtiene todos los clientes habilitados
	 * @return List clientes
	 * @throws ProquifaNetException
	 */
	List<Cliente> obtenerClientesHabilitados();
	/***
	 * 
	 * @param cfdi
	 * @return
	 */
	Long insetarFactura(Factura fac);
	/***
	 * Obtiene El tipo de cambio requerido
	 * @param String moneda
	 * @return Float tipo
	 * @throws ProquifaNetException
	 */
	Float getTipoCambio(String tipo);
	/***
	 * Regresa una lista de todas las facturas por pagar, que se realizaron en ProquifaNet
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> findFacturasEmitidasPQNet();
	
	/***
	 * Se actualiza la factura
	 * @param factura
	 * @return
	 */
	Boolean actualizarFactura(Factura factura);
	
	/***
	 * Regresa el registro de una factura determinada
	 * @param factura
	 * @param fpor
	 * @return
	 */	
	Factura getFactura(String factura, String fpor);
	/***
	 * 
	 * @param periodo
	 * @param idCliente
	 * @param fpor
	 * @param estado
	 * @param dentroSistema
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	List<Factura> findFacturasEmitidas(String condiciones,boolean dentroSistema) throws ProquifaNetException;
	/***
	 * 
	 * @param condiciones
	 * @return
	 * @throws ProquifaNetException
	 */
	Double getTotalFacturadoClienteEmpresa(String condiciones) throws ProquifaNetException;
	/***
	 * Obtiene datos complementarios para la facturacion electronica.
	 * @param folio, fPor
	 * @return
	 */
	String getUuid(int folio, String fPor);
	/***
	 * Obtiene el monto total de la factura menos las partidas canceladas, en nota de credito
	 * @param factura
	 * @param empresa
	 * @return
	 * @throws ProquifaNetException
	 */
	Double getMontoFactura(Long factura, String empresa) throws ProquifaNetException;
	/**
	 * @param fact
	 * @return
	 */
	Long insertDocumentoFiscalTemporal(Factura fact) ;

	/**
	 * @param fact
	 * @return
	 */
	Boolean updateDocumentoFiscalTemporal(Factura fact);
	
	/**
	 * @param parametro
	 * @return
	 */
	List<Factura> findDocumentosFiscalTemporal (String parametro);
	
	/**
	 * @param idDocumentoFiscal
	 * @return
	 * @throws ProquifaNetException 
	 */
	Boolean deleteDocumentoFiscalTemporal ( Long idDocumentoFiscal) throws ProquifaNetException;
	
	/**
	 * obtiene el docto fiscal con sus partidas por el id de 
	 * @param idDoctoFiscalTemporal
	 * @return
	 */
	Factura obtenerDocumentoFiscalTemporalxidDocto ( Long idDoctoFiscalTemporal);

	/***
	 * Obtiene datos complementarios para la facturacion electronica.
	 * @param idcliente
	 * @return
	 */
	Factura obtenerComprobanteDePago(Long idFactura);
	/**
	 * Obtiene CFDI en base a un idFactura
	 * @param idFactura
	 * @return
	 */
	
	List<ComprobanteFiscal> obtenerInfoCFDIxidFactura(Long idFactura) throws ProquifaNetException;
	/**
	 * Metodo que obtiene el tipo de Uso de CFDI por medio del id
	 * @param idCFDI
	 * @return
	 * @throws ProquifaNetException
	 */
	CatalogoItem obtenerUsoCFDI (int idCFDI) throws ProquifaNetException;
	/**
	 * Método que obtiene el tipo de método de pago por medio del id
	 * @param metPago
	 * @return
	 * @throws ProquifaNetException
	 */
	CatalogoItem obtenerMetodoPago (int metPago) throws ProquifaNetException;
	/**
	 * Método que devuelve el nombre de la claveunidad por medio de un id 
	 * @param claveUnidad
	 * @return
	 * @throws ProquifaNetException
	 */
	CatalogoItem obtenerClaveUnidad (int claveUnidad) throws ProquifaNetException;
	/**
	 * Método que devuelve el nombre de la claveProdServ por medio de un id
	 * @param claveProdServ
	 * @return
	 * @throws ProquifaNetException
	 */
	CatalogoItem obtenerClaveProdServ (int claveProdServ) throws ProquifaNetException;
	
	Boolean actualizarPDFGenerado(Factura factura); 
	
	List<Factura> findFacturaPendiente(String folio);

	CatalogoItem obtenerUsoCFDI(String usoCfdi);
	
	CatalogoItem obtenerFormaPago (String formaPago);
	/**
	 * Metodo que obtiene los productos para el complemento de pago
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	Map<String, Object> obtenerListasComplemento(String factura) throws ProquifaNetException;

	Factura obtenerDatosCliente(String factura) throws ProquifaNetException;

	List<ComplementoPago> pagosComplemento(String idFactura) throws ProquifaNetException;

	/**
	 * @param idFacturaElectronica
	 * @return
	 * @throws ProquifaNetException
	 */
	Factura obtenerFacturaElectronica(Integer idFacturaElectronica) throws ProquifaNetException;

	/**
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	Integer insertarFactura(Factura factura) throws ProquifaNetException;
	
}