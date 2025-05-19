package com.proquifa.net.persistencia.comun.facturacion;

import java.util.List;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.ReferenciaBancaria;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.facturaElectronica.CPDocRelacionado;
import com.proquifa.net.modelo.comun.facturaElectronica.CfdiRelacionado;
import com.proquifa.net.modelo.comun.facturaElectronica.ComplementoPago;
import com.proquifa.net.modelo.comun.facturaElectronica.FacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.PFacturaElectronica;
import com.proquifa.net.modelo.comun.facturaElectronica.RespuestaSap;

public interface FacturacionElectronicaDAO {

	/***
	 *
	 * @param f
	 * @return
	 */
	public FacturaElectronica insertarFactura(FacturaElectronica f);

	/***
	 *
	 * @return
	 */
	public FacturaElectronica obtenerFacturaElectronica();

	/***
	 *
	 * @param f
	 * @return
	 */
	public FacturaElectronica modificarFactura(FacturaElectronica f);

	/***
	 *
	 * @param f
	 * @return
	 */
	public PFacturaElectronica insertarPFactura(PFacturaElectronica f);

	/***
	 *
	 * @param f
	 * @return
	 */
	public int insertarTablaFactura(Factura f);

	/***
	 *
	 * @param f
	 * @return
	 */
	public Boolean insertarTablaPFacturas(PartidaFactura f);

	/***
	 *
	 * @param idFactura
	 * @param idFEl
	 * @return
	 */
	public Boolean relacionarFactura_FElectronica(int idFactura, int idFEl);

	/**
	 *
	 * @param idFactura
	 * @return
	 */
	public FacturaElectronica getFElectronicaByFactura(int idFactura);

	/**
	 *
	 * @param idCliente
	 * @param empresa
	 * @return
	 */
	public List<ReferenciaBancaria> obtenerReferenciaBancaria(int idCliente, String empresa);

	/**
	 *
	 * @param idCliente
	 * @param empresa
	 * @return
	 */
	public String obtenerNumProveedor(int idCliente, String empresa);

	/**
	 *
	 * @return
	 */
	public String obtenerTipoCambio();

	/**
	 *
	 * @param idFactura
	 * @return
	 */
	public Boolean updateErrorFactura(int idFactura);

	/**
	 *
	 * @param idFactura
	 * @return
	 */
	public RespuestaSap facturaSap(int idFactura);

	/**
	 *
	 * @param cp
	 * @return
	 */
	public ComplementoPago insertarComplementoPago(ComplementoPago cp);

	/**
	 *
	 * @param cpdr
	 * @return
	 */
	public CPDocRelacionado insertarCPDocRelacionado(CPDocRelacionado cpdr);

	/**
	 *
	 * @param cfdiRelacionado
	 * @return
	 */
	public CfdiRelacionado insertarCfdiRelacionado(CfdiRelacionado cfdiRelacionado);

	/**
	 *
	 * @param error
	 * @param idFactura
	 * @return
	 */
	public Boolean insertarErrorFactura(String error, int idFactura);

	/**
	 * @param factura
	 * @return
	 * @throws ProquifaNetException
	 */
	String validarClienteInternacional(FacturaElectronica factura) throws ProquifaNetException;

	public double obtenerSubtotalFactura(String folio)throws ProquifaNetException;
	public double obtenertipoCambioDR(String folio)throws  ProquifaNetException;
	public String aplicaImpuestos(String folio)throws ProquifaNetException;
	public String obtnerUsoCFDICliente(String rfc)throws ProquifaNetException;


	String obtenerCPCliente(String rfc) throws ProquifaNetException;

	String getNameCliente(String rfc) throws ProquifaNetException;

	String getRegimenF(String rfc) throws ProquifaNetException;

	String getImpuestoClave (String idDocumento) throws ProquifaNetException;

	public List <String> impuestos(String UUID) throws ProquifaNetException;

}
