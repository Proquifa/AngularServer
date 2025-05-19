package com.proquifa.net.modelo.comun.facturaElectronica;

import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;

public class FacturaElectronica {
	private int idFactura;
	private String version;
	private String serie;
	private String folio;
	private String fecha;
	private String formaPago;
	private String noCertificado;
	private String condicionesPago;
	private String certificado;
	private String subtotal;
	private String moneda;
	private String tipoCambio;
	private String total;
	private String tipoComprobante;
	private String metodoPago;
	private String lugarExpedicion;
	private String sello;
	private String cadenaOriginal;
	private String idPPedidos;
	private boolean esControlada;

	private Empresa empresa;
//	private String emisorRFC;
//	private String emisorRazonSocial;
//	private String emisorRegimen;
	
	private Cliente cliente;
//	private String receptorRFC;
//	private String receptorRazonSocial;
//	private String receptorUsoCFDI;
	
	private String impuestosTotalTraslados;
	private String impuestosClave;
	private String impuestosTipoFactor;
	private String impuestosTasaOCuota;
	private String impuestosImporte;
	
	private String satVersion;
	private String satUUID;
	private String satFechaTimbrado;
	private String satRfcProvCertif;
	private String satSelloCFD;
	private String satNoCertificadoSAT;
	private String satSelloSAT;
	private String satCadenaOriginal;
	
	private String codeQR;
	private String folioPedidoCliente;
	private String totalTexto;
	
	private String xml;
	private String estado;
	
	private String pedido;
	private String cpedido;
	private String ppedido;
	
	private String notaFE;
	
	private String correoAddenda;
	
	private String ordenCompra;
	
	private String comentario;

	
	private List<PFacturaElectronica> lstConceptos;
	private List<PFacturaElectronica> lstComplementos;
	
	private List<CfdiRelacionado> lstCfdiRel;

	private List<ComplementoPago> lstComplementoPago;
	private String distRecorrida;
	private String responsable;
	private String idMensajero;
	private String idVehiculo;

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getIdMensajero() {
		return idMensajero;
	}

	public void setIdMensajero(String idMensajero) {
		this.idMensajero = idMensajero;
	}

	public String getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(String idVehiculo) {
		this.idVehiculo = idVehiculo;
	}

	public String getDistRecorrida() {
		return distRecorrida;
	}

	public void setDistRecorrida(String distRecorrida) {
		this.distRecorrida = distRecorrida;
	}

	public String getIdPPedidos() {
		return idPPedidos;
	}

	public void setIdPPedidos(String idPPedidos) {
		this.idPPedidos = idPPedidos;
	}

	public int getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getNoCertificado() {
		return noCertificado;
	}

	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}

	public String getCertificado() {
		return certificado;
	}

	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}

	public String getCondicionesPago() {
		return condicionesPago;
	}

	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}

	public String getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getMetodoPago() {
		return metodoPago;
	}

	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}

	public String getLugarExpedicion() {
		return lugarExpedicion;
	}

	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}

	public String getSello() {
		return sello;
	}

	public void setSello(String sello) {
		this.sello = sello;
	}

	public String getCadenaOriginal() {
		return cadenaOriginal;
	}

	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}

	public String getImpuestosTotalTraslados() {
		return impuestosTotalTraslados;
	}

	public void setImpuestosTotalTraslados(String impuestosTotalTraslados) {
		this.impuestosTotalTraslados = impuestosTotalTraslados;
	}

	public String getImpuestosClave() {
		return impuestosClave;
	}

	public void setImpuestosClave(String impuestosClave) {
		this.impuestosClave = impuestosClave;
	}

	public String getImpuestosTipoFactor() {
		return impuestosTipoFactor;
	}

	public void setImpuestosTipoFactor(String impuestosTipoFactor) {
		this.impuestosTipoFactor = impuestosTipoFactor;
	}

	public String getImpuestosTasaOCuota() {
		return impuestosTasaOCuota;
	}

	public void setImpuestosTasaOCuota(String impuestosTasaOCuota) {
		this.impuestosTasaOCuota = impuestosTasaOCuota;
	}

	public String getImpuestosImporte() {
		return impuestosImporte;
	}

	public void setImpuestosImporte(String impuestosImporte) {
		this.impuestosImporte = impuestosImporte;
	}

	public String getSatVersion() {
		return satVersion;
	}

	public void setSatVersion(String satVersion) {
		this.satVersion = satVersion;
	}

	public String getSatUUID() {
		return satUUID;
	}

	public void setSatUUID(String satUUID) {
		this.satUUID = satUUID;
	}

	public String getSatFechaTimbrado() {
		return satFechaTimbrado;
	}

	public void setSatFechaTimbrado(String satFechaTimbrado) {
		this.satFechaTimbrado = satFechaTimbrado;
	}

	public String getSatRfcProvCertif() {
		return satRfcProvCertif;
	}

	public void setSatRfcProvCertif(String satRfcProvCertif) {
		this.satRfcProvCertif = satRfcProvCertif;
	}

	public String getSatSelloCFD() {
		return satSelloCFD;
	}

	public void setSatSelloCFD(String satSelloCFD) {
		this.satSelloCFD = satSelloCFD;
	}

	public String getSatNoCertificadoSAT() {
		return satNoCertificadoSAT;
	}

	public void setSatNoCertificadoSAT(String satNoCertificadoSAT) {
		this.satNoCertificadoSAT = satNoCertificadoSAT;
	}

	public String getSatSelloSAT() {
		return satSelloSAT;
	}

	public void setSatSelloSAT(String satSelloSAT) {
		this.satSelloSAT = satSelloSAT;
	}

	public String getSatCadenaOriginal() {
		return satCadenaOriginal;
	}

	public void setSatCadenaOriginal(String satCadenaOriginal) {
		this.satCadenaOriginal = satCadenaOriginal;
	}

	public String getCodeQR() {
		return codeQR;
	}

	public void setCodeQR(String codeQR) {
		this.codeQR = codeQR;
	}

	public String getFolioPedidoCliente() {
		return folioPedidoCliente;
	}

	public void setFolioPedidoCliente(String folioPedidoCliente) {
		this.folioPedidoCliente = folioPedidoCliente;
	}

	public String getTotalTexto() {
		return totalTexto;
	}

	public void setTotalTexto(String totalTexto) {
		this.totalTexto = totalTexto;
	}

	public List<PFacturaElectronica> getLstConceptos() {
		return lstConceptos;
	}

	public void setLstConceptos(List<PFacturaElectronica> lstConceptos) {
		this.lstConceptos = lstConceptos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public String getTipoCambio() {
		return tipoCambio;
	}

	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public String getPedido() {
		return pedido;
	}

	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	public String getCpedido() {
		return cpedido;
	}

	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}

	public String getPpedido() {
		return ppedido;
	}

	public void setPpedido(String ppedido) {
		this.ppedido = ppedido;
	}

	public String getNotaFE() {
		return notaFE;
	}

	public void setNotaFE(String notaFE) {
		this.notaFE = notaFE;
	}

	public String getCorreoAddenda() {
		return correoAddenda;
	}

	public void setCorreoAddenda(String correoAddenda) {
		this.correoAddenda = correoAddenda;
	}

	/**
	 * @return the lstComplementos
	 */
	public List<PFacturaElectronica> getLstComplementos() {
		return lstComplementos;
	}

	/**
	 * @param lstComplementos the lstComplementos to set
	 */
	public void setLstComplementos(List<PFacturaElectronica> lstComplementos) {
		this.lstComplementos = lstComplementos;
	}

	/**
	 * @return the lstComplementoPago
	 */
	public List<ComplementoPago> getLstComplementoPago() {
		return lstComplementoPago;
	}

	/**
	 * @param lstComplementoPago the lstComplementoPago to set
	 */
	public void setLstComplementoPago(List<ComplementoPago> lstComplementoPago) {
		this.lstComplementoPago = lstComplementoPago;
	}

	/**
	 * @return the ordenCompra
	 */
	public String getOrdenCompra() {
		return ordenCompra;
	}

	/**
	 * @param ordenCompra the ordenCompra to set
	 */
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	/**
	 * @return the lstCfdiRel
	 */
	public List<CfdiRelacionado> getLstCfdiRel() {
		return lstCfdiRel;
	}

	/**
	 * @param lstCfdiRel the lstCfdiRel to set
	 */
	public void setLstCfdiRel(List<CfdiRelacionado> lstCfdiRel) {
		this.lstCfdiRel = lstCfdiRel;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	/**
	 * @param Si la factura contiene productos de tipo controlado (Mundiales, Nacionales) 
	 */
	public boolean isEsControlada() {
		return esControlada;
	}

	public void setEsControlada(boolean esControlada) {
		this.esControlada = esControlada;
	}
}
