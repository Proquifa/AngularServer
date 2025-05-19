package com.proquifa.net.modelo.comun;

import java.util.List;



public class FacturaPQF {

	//Variables
	private String version;
	private String fechaFactura;
	private String sello;
	private String formaPago;
	private String noCertificado;
	private String certificado;
	private String condicionesPago;
	private Double subTotal;
	private String moneda;
	private String tipoCambio;
	private String total;
	private String tipoComprobante;
	private String metodoPago;
	private String lugarExpedicion;
	private String serie;
	private String folio;
	private String rfcEmisor;
	private String nombreEmisor;
	private String regimenFiscalEmisor;
	private String rfcReceptor;
	private String nombreReceptor;
	private String usoCfdi;
	
	private Double importe;
	private Double importeTrasladoConcepto;
	
	private String impuesto;
	private String tipoFactor;
	private String tasaOCuota;
	private String numeroPedimento;
	private String noCertificadoSat;
	private String selloSat;
	private String selloCFD;
	private String cadenaOriginal;
	private String claveProdServ;
	private String codIdentificacion;
	private String cantidad;
	private String claveUnidad;
	private String unidadConcepto;
	private String descripcionConcepto;
	private Double valorUnitario;
	private Double importeConcepto;
	private String codigoTerminoPago;
	private Double totalFactura;
	private String subTotalFactura;
	private String totalLetra;
	private String folioFiscal;
	private String pedido;
	private String datosDigitales;
	private String noCertificadoSAT;
	private String codigoQR;
	private Double totalImpuestos;
	
	private String numeroProveedor;
	private String ordenCompra;
	
	private String oC;
	private String cPedido;

	private List<ReferenciaBancaria> refBancaria;
	
	
	
	public FacturaPQF() {
		super();
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}



	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}



	/**
	 * @return the fechaFactura
	 */
	public String getFechaFactura() {
		return fechaFactura;
	}



	/**
	 * @param fechaFactura the fechaFactura to set
	 */
	public void setFechaFactura(String fechaFactura) {
		this.fechaFactura = fechaFactura;
	}



	/**
	 * @return the sello
	 */
	public String getSello() {
		return sello;
	}



	/**
	 * @param sello the sello to set
	 */
	public void setSello(String sello) {
		this.sello = sello;
	}



	/**
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}



	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}



	/**
	 * @return the noCertificado
	 */
	public String getNoCertificado() {
		return noCertificado;
	}



	/**
	 * @param noCertificado the noCertificado to set
	 */
	public void setNoCertificado(String noCertificado) {
		this.noCertificado = noCertificado;
	}



	/**
	 * @return the certificado
	 */
	public String getCertificado() {
		return certificado;
	}



	/**
	 * @param certificado the certificado to set
	 */
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}



	/**
	 * @return the condicionesPago
	 */
	public String getCondicionesPago() {
		return condicionesPago;
	}



	/**
	 * @param condicionesPago the condicionesPago to set
	 */
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}



	/**
	 * @return the subTotal
	 */
	public Double getSubTotal() {
		return subTotal;
	}



	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}



	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}



	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}



	/**
	 * @return the tipoCambio
	 */
	public String getTipoCambio() {
		return tipoCambio;
	}



	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
	}



	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}



	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}



	/**
	 * @return the tipoComprobante
	 */
	public String getTipoComprobante() {
		return tipoComprobante;
	}



	/**
	 * @param tipoComprobante the tipoComprobante to set
	 */
	public void setTipoComprobante(String tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}



	/**
	 * @return the metodoPago
	 */
	public String getMetodoPago() {
		return metodoPago;
	}



	/**
	 * @param metodoPago the metodoPago to set
	 */
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}



	/**
	 * @return the lugarExpedicion
	 */
	public String getLugarExpedicion() {
		return lugarExpedicion;
	}



	/**
	 * @param lugarExpedicion the lugarExpedicion to set
	 */
	public void setLugarExpedicion(String lugarExpedicion) {
		this.lugarExpedicion = lugarExpedicion;
	}



	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}



	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}



	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}



	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}



	/**
	 * @return the rfcEmisor
	 */
	public String getRfcEmisor() {
		return rfcEmisor;
	}



	/**
	 * @param rfcEmisor the rfcEmisor to set
	 */
	public void setRfcEmisor(String rfcEmisor) {
		this.rfcEmisor = rfcEmisor;
	}



	/**
	 * @return the nombreEmisor
	 */
	public String getNombreEmisor() {
		return nombreEmisor;
	}



	/**
	 * @param nombreEmisor the nombreEmisor to set
	 */
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}



	/**
	 * @return the regimenFiscalEmisor
	 */
	public String getRegimenFiscalEmisor() {
		return regimenFiscalEmisor;
	}



	/**
	 * @param regimenFiscalEmisor the regimenFiscalEmisor to set
	 */
	public void setRegimenFiscalEmisor(String regimenFiscalEmisor) {
		this.regimenFiscalEmisor = regimenFiscalEmisor;
	}



	/**
	 * @return the rfcReceptor
	 */
	public String getRfcReceptor() {
		return rfcReceptor;
	}



	/**
	 * @param rfcReceptor the rfcReceptor to set
	 */
	public void setRfcReceptor(String rfcReceptor) {
		this.rfcReceptor = rfcReceptor;
	}



	/**
	 * @return the nombreReceptor
	 */
	public String getNombreReceptor() {
		return nombreReceptor;
	}



	/**
	 * @param nombreReceptor the nombreReceptor to set
	 */
	public void setNombreReceptor(String nombreReceptor) {
		this.nombreReceptor = nombreReceptor;
	}



	/**
	 * @return the usoCfdi
	 */
	public String getUsoCfdi() {
		return usoCfdi;
	}



	/**
	 * @param usoCfdi the usoCfdi to set
	 */
	public void setUsoCfdi(String usoCfdi) {
		this.usoCfdi = usoCfdi;
	}



	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}



	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}



	/**
	 * @return the importeTrasladoConcepto
	 */
	public Double getImporteTrasladoConcepto() {
		return importeTrasladoConcepto;
	}



	/**
	 * @param importeTrasladoConcepto the importeTrasladoConcepto to set
	 */
	public void setImporteTrasladoConcepto(Double importeTrasladoConcepto) {
		this.importeTrasladoConcepto = importeTrasladoConcepto;
	}



	/**
	 * @return the impuesto
	 */
	public String getImpuesto() {
		return impuesto;
	}



	/**
	 * @param impuesto the impuesto to set
	 */
	public void setImpuesto(String impuesto) {
		this.impuesto = impuesto;
	}



	/**
	 * @return the tipoFactor
	 */
	public String getTipoFactor() {
		return tipoFactor;
	}



	/**
	 * @param tipoFactor the tipoFactor to set
	 */
	public void setTipoFactor(String tipoFactor) {
		this.tipoFactor = tipoFactor;
	}



	/**
	 * @return the tasaOCuota
	 */
	public String getTasaOCuota() {
		return tasaOCuota;
	}



	/**
	 * @param tasaOCuota the tasaOCuota to set
	 */
	public void setTasaOCuota(String tasaOCuota) {
		this.tasaOCuota = tasaOCuota;
	}



	/**
	 * @return the numeroPedimento
	 */
	public String getNumeroPedimento() {
		return numeroPedimento;
	}



	/**
	 * @param numeroPedimento the numeroPedimento to set
	 */
	public void setNumeroPedimento(String numeroPedimento) {
		this.numeroPedimento = numeroPedimento;
	}



	/**
	 * @return the noCertificadoSat
	 */
	public String getNoCertificadoSat() {
		return noCertificadoSat;
	}



	/**
	 * @param noCertificadoSat the noCertificadoSat to set
	 */
	public void setNoCertificadoSat(String noCertificadoSat) {
		this.noCertificadoSat = noCertificadoSat;
	}



	/**
	 * @return the selloSat
	 */
	public String getSelloSat() {
		return selloSat;
	}



	/**
	 * @param selloSat the selloSat to set
	 */
	public void setSelloSat(String selloSat) {
		this.selloSat = selloSat;
	}



	/**
	 * @return the selloCFD
	 */
	public String getSelloCFD() {
		return selloCFD;
	}



	/**
	 * @param selloCFD the selloCFD to set
	 */
	public void setSelloCFD(String selloCFD) {
		this.selloCFD = selloCFD;
	}



	/**
	 * @return the cadenaOriginal
	 */
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}



	/**
	 * @param cadenaOriginal the cadenaOriginal to set
	 */
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}



	/**
	 * @return the claveProdServ
	 */
	public String getClaveProdServ() {
		return claveProdServ;
	}



	/**
	 * @param claveProdServ the claveProdServ to set
	 */
	public void setClaveProdServ(String claveProdServ) {
		this.claveProdServ = claveProdServ;
	}



	/**
	 * @return the codIdentificacion
	 */
	public String getCodIdentificacion() {
		return codIdentificacion;
	}



	/**
	 * @param codIdentificacion the codIdentificacion to set
	 */
	public void setCodIdentificacion(String codIdentificacion) {
		this.codIdentificacion = codIdentificacion;
	}



	/**
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}



	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}



	/**
	 * @return the claveUnidad
	 */
	public String getClaveUnidad() {
		return claveUnidad;
	}



	/**
	 * @param claveUnidad the claveUnidad to set
	 */
	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}



	/**
	 * @return the unidadConcepto
	 */
	public String getUnidadConcepto() {
		return unidadConcepto;
	}



	/**
	 * @param unidadConcepto the unidadConcepto to set
	 */
	public void setUnidadConcepto(String unidadConcepto) {
		this.unidadConcepto = unidadConcepto;
	}



	/**
	 * @return the descripcionConcepto
	 */
	public String getDescripcionConcepto() {
		return descripcionConcepto;
	}



	/**
	 * @param descripcionConcepto the descripcionConcepto to set
	 */
	public void setDescripcionConcepto(String descripcionConcepto) {
		this.descripcionConcepto = descripcionConcepto;
	}



	/**
	 * @return the valorUnitario
	 */
	public Double getValorUnitario() {
		return valorUnitario;
	}



	/**
	 * @param valorUnitario the valorUnitario to set
	 */
	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}



	/**
	 * @return the importeConcepto
	 */
	public Double getImporteConcepto() {
		return importeConcepto;
	}



	/**
	 * @param importeConcepto the importeConcepto to set
	 */
	public void setImporteConcepto(Double importeConcepto) {
		this.importeConcepto = importeConcepto;
	}



	/**
	 * @return the codigoTerminoPago
	 */
	public String getCodigoTerminoPago() {
		return codigoTerminoPago;
	}



	/**
	 * @param codigoTerminoPago the codigoTerminoPago to set
	 */
	public void setCodigoTerminoPago(String codigoTerminoPago) {
		this.codigoTerminoPago = codigoTerminoPago;
	}



	/**
	 * @return the totalFactura
	 */
	public Double getTotalFactura() {
		return totalFactura;
	}



	/**
	 * @param totalFactura the totalFactura to set
	 */
	public void setTotalFactura(Double totalFactura) {
		this.totalFactura = totalFactura;
	}



	/**
	 * @return the subTotalFactura
	 */
	public String getSubTotalFactura() {
		return subTotalFactura;
	}



	/**
	 * @param subTotalFactura the subTotalFactura to set
	 */
	public void setSubTotalFactura(String subTotalFactura) {
		this.subTotalFactura = subTotalFactura;
	}



	/**
	 * @return the totalLetra
	 */
	public String getTotalLetra() {
		return totalLetra;
	}



	/**
	 * @param totalLetra the totalLetra to set
	 */
	public void setTotalLetra(String totalLetra) {
		this.totalLetra = totalLetra;
	}



	/**
	 * @return the folioFiscal
	 */
	public String getFolioFiscal() {
		return folioFiscal;
	}



	/**
	 * @param folioFiscal the folioFiscal to set
	 */
	public void setFolioFiscal(String folioFiscal) {
		this.folioFiscal = folioFiscal;
	}



	/**
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}



	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}



	/**
	 * @return the datosDigitales
	 */
	public String getDatosDigitales() {
		return datosDigitales;
	}



	/**
	 * @param datosDigitales the datosDigitales to set
	 */
	public void setDatosDigitales(String datosDigitales) {
		this.datosDigitales = datosDigitales;
	}



	/**
	 * @return the noCertificadoSAT
	 */
	public String getNoCertificadoSAT() {
		return noCertificadoSAT;
	}



	/**
	 * @param noCertificadoSAT the noCertificadoSAT to set
	 */
	public void setNoCertificadoSAT(String noCertificadoSAT) {
		this.noCertificadoSAT = noCertificadoSAT;
	}



	/**
	 * @return the codigoQR
	 */
	public String getCodigoQR() {
		return codigoQR;
	}



	/**
	 * @param codigoQR the codigoQR to set
	 */
	public void setCodigoQR(String codigoQR) {
		this.codigoQR = codigoQR;
	}



	/**
	 * @return the totalImpuestos
	 */
	public Double getTotalImpuestos() {
		return totalImpuestos;
	}



	/**
	 * @param totalImpuestos the totalImpuestos to set
	 */
	public void setTotalImpuestos(Double totalImpuestos) {
		this.totalImpuestos = totalImpuestos;
	}



	/**
	 * @return the numeroProveedor
	 */
	public String getNumeroProveedor() {
		return numeroProveedor;
	}



	/**
	 * @param numeroProveedor the numeroProveedor to set
	 */
	public void setNumeroProveedor(String numeroProveedor) {
		this.numeroProveedor = numeroProveedor;
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
	 * @return the refBancaria
	 */
	public List<ReferenciaBancaria> getRefBancaria() {
		return refBancaria;
	}



	/**
	 * @param refBancaria the refBancaria to set
	 */
	public void setRefBancaria(List<ReferenciaBancaria> refBancaria) {
		this.refBancaria = refBancaria;
	}



	/**
	 * @return the oC
	 */
	public String getoC() {
		return oC;
	}



	/**
	 * @param oC the oC to set
	 */
	public void setoC(String oC) {
		this.oC = oC;
	}



	/**
	 * @return the cPedido
	 */
	public String getcPedido() {
		return cPedido;
	}



	/**
	 * @param cPedido the cPedido to set
	 */
	public void setcPedido(String cPedido) {
		this.cPedido = cPedido;
	}
	
	
	
	
}

