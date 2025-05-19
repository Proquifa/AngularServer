/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.List;

/**
 * @author ymendez
 *
 */
public class ComplementosPago {
	
	private Integer idComplementosPago;
	private Integer idDoctoRelacionado;

	//EMISOR
	private String rfcEmisor;
	private String nombreEmisor;
	private String regimenFiscal;
	
	//RECEPTOR
	private String rfcReceptor;
	private String nombreReceptor;
	private String usoCFDI;
	
	//Pago
	private String fechaPago;
	private String formaDePago;
	private String moneda;
	private String monto;
	private String ctaOrdenante;
	private String nomBancoOrdExt;
	private String rfcEmisorCtaOrd;
	
	//DoctoRelacionado
	private String idDocumento;
	private String serie;
	private String monedaDR;
	private String metodoDePagoDR;
	private String tipoCambioDR;
	private String impPagado;
	private String numParcialidad;
	private String impSaldoAnt;
	private String impSaldoInsoluto;
	private String folio;
	
	//TimbreFiscalDigital
	private String uuid;
	private String fechaTimbrado;
	private String rfcProvCertif;
	private String selloCFD;
	private String noCertificadoSAT;
	private String selloSAT;
	
	/**
	 * 
	 */
	public ComplementosPago() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	/**
	 * @param idComplementosPago
	 * @param idDoctoRelacionado
	 * @param rfcEmisor
	 * @param nombreEmisor
	 * @param regimenFiscal
	 * @param rfcReceptor
	 * @param nombreReceptor
	 * @param usoCFDI
	 * @param fechaPago
	 * @param formaDePago
	 * @param moneda
	 * @param monto
	 * @param ctaOrdenante
	 * @param nomBancoOrdExt
	 * @param rfcEmisorCtaOrd
	 * @param idDocumento
	 * @param serie
	 * @param monedaDR
	 * @param metodoDePagoDR
	 * @param tipoCambioDR
	 * @param impPagado
	 * @param numParcialidad
	 * @param impSaldoAnt
	 * @param impSaldoInsoluto
	 * @param uuid
	 * @param fechaTimbrado
	 * @param rfcProvCertif
	 * @param selloCFD
	 * @param noCertificadoSAT
	 * @param selloSAT
	 */
	public ComplementosPago(ComplementosPago pago) {
		super();
		this.idComplementosPago = pago.idComplementosPago;
		this.idDoctoRelacionado = pago.idDoctoRelacionado;
		this.rfcEmisor = pago.rfcEmisor;
		this.nombreEmisor = pago.nombreEmisor;
		this.regimenFiscal = pago.regimenFiscal;
		this.rfcReceptor = pago.rfcReceptor;
		this.nombreReceptor = pago.nombreReceptor;
		this.usoCFDI = pago.usoCFDI;
		this.fechaPago = pago.fechaPago;
		this.formaDePago = pago.formaDePago;
		this.moneda = pago.moneda;
		this.monto = pago.monto;
		this.ctaOrdenante = pago.ctaOrdenante;
		this.nomBancoOrdExt = pago.nomBancoOrdExt;
		this.rfcEmisorCtaOrd = pago.rfcEmisorCtaOrd;
		this.idDocumento = pago.idDocumento;
		this.serie = pago.serie;
		this.monedaDR = pago.monedaDR;
		this.metodoDePagoDR = pago.metodoDePagoDR;
		this.tipoCambioDR = pago.tipoCambioDR;
		this.impPagado = pago.impPagado;
		this.numParcialidad = pago.numParcialidad;
		this.impSaldoAnt = pago.impSaldoAnt;
		this.impSaldoInsoluto = pago.impSaldoInsoluto;
		this.uuid = pago.uuid;
		this.fechaTimbrado = pago.fechaTimbrado;
		this.rfcProvCertif = pago.rfcProvCertif;
		this.selloCFD = pago.selloCFD;
		this.noCertificadoSAT = pago.noCertificadoSAT;
		this.selloSAT = pago.selloSAT;
		this.folio = pago.folio;
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
	 * @return the regimenFiscal
	 */
	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	/**
	 * @param regimenFiscal the regimenFiscal to set
	 */
	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
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
	 * @return the usoCFDI
	 */
	public String getUsoCFDI() {
		return usoCFDI;
	}

	/**
	 * @param usoCFDI the usoCFDI to set
	 */
	public void setUsoCFDI(String usoCFDI) {
		this.usoCFDI = usoCFDI;
	}

	/**
	 * @return the fechaPago
	 */
	public String getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the formaDePago
	 */
	public String getFormaDePago() {
		return formaDePago;
	}

	/**
	 * @param formaDePago the formaDePago to set
	 */
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
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
	 * @return the monto
	 */
	public String getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(String monto) {
		this.monto = monto;
	}

	/**
	 * @return the ctaOrdenante
	 */
	public String getCtaOrdenante() {
		return ctaOrdenante;
	}

	/**
	 * @param ctaOrdenante the ctaOrdenante to set
	 */
	public void setCtaOrdenante(String ctaOrdenante) {
		this.ctaOrdenante = ctaOrdenante;
	}

	/**
	 * @return the nomBancoOrdExt
	 */
	public String getNomBancoOrdExt() {
		return nomBancoOrdExt;
	}

	/**
	 * @param nomBancoOrdExt the nomBancoOrdExt to set
	 */
	public void setNomBancoOrdExt(String nomBancoOrdExt) {
		this.nomBancoOrdExt = nomBancoOrdExt;
	}

	/**
	 * @return the rfcEmisorCtaOrd
	 */
	public String getRfcEmisorCtaOrd() {
		return rfcEmisorCtaOrd;
	}

	/**
	 * @param rfcEmisorCtaOrd the rfcEmisorCtaOrd to set
	 */
	public void setRfcEmisorCtaOrd(String rfcEmisorCtaOrd) {
		this.rfcEmisorCtaOrd = rfcEmisorCtaOrd;
	}

	/**
	 * @return the idDocumento
	 */
	public String getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
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
	 * @return the monedaDR
	 */
	public String getMonedaDR() {
		return monedaDR;
	}

	/**
	 * @param monedaDR the monedaDR to set
	 */
	public void setMonedaDR(String monedaDR) {
		this.monedaDR = monedaDR;
	}

	/**
	 * @return the metodoDePagoDR
	 */
	public String getMetodoDePagoDR() {
		return metodoDePagoDR;
	}

	/**
	 * @param metodoDePagoDR the metodoDePagoDR to set
	 */
	public void setMetodoDePagoDR(String metodoDePagoDR) {
		this.metodoDePagoDR = metodoDePagoDR;
	}

	/**
	 * @return the tipoCambioDR
	 */
	public String getTipoCambioDR() {
		return tipoCambioDR;
	}

	/**
	 * @param tipoCambioDR the tipoCambioDR to set
	 */
	public void setTipoCambioDR(String tipoCambioDR) {
		this.tipoCambioDR = tipoCambioDR;
	}

	/**
	 * @return the impPagado
	 */
	public String getImpPagado() {
		return impPagado;
	}

	/**
	 * @param impPagado the impPagado to set
	 */
	public void setImpPagado(String impPagado) {
		this.impPagado = impPagado;
	}

	/**
	 * @return the numParcialidad
	 */
	public String getNumParcialidad() {
		return numParcialidad;
	}

	/**
	 * @param numParcialidad the numParcialidad to set
	 */
	public void setNumParcialidad(String numParcialidad) {
		this.numParcialidad = numParcialidad;
	}

	/**
	 * @return the impSaldoAnt
	 */
	public String getImpSaldoAnt() {
		return impSaldoAnt;
	}

	/**
	 * @param impSaldoAnt the impSaldoAnt to set
	 */
	public void setImpSaldoAnt(String impSaldoAnt) {
		this.impSaldoAnt = impSaldoAnt;
	}

	/**
	 * @return the impSaldoInsoluto
	 */
	public String getImpSaldoInsoluto() {
		return impSaldoInsoluto;
	}

	/**
	 * @param impSaldoInsoluto the impSaldoInsoluto to set
	 */
	public void setImpSaldoInsoluto(String impSaldoInsoluto) {
		this.impSaldoInsoluto = impSaldoInsoluto;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the fechaTimbrado
	 */
	public String getFechaTimbrado() {
		return fechaTimbrado;
	}

	/**
	 * @param fechaTimbrado the fechaTimbrado to set
	 */
	public void setFechaTimbrado(String fechaTimbrado) {
		this.fechaTimbrado = fechaTimbrado;
	}

	/**
	 * @return the rfcProvCertif
	 */
	public String getRfcProvCertif() {
		return rfcProvCertif;
	}

	/**
	 * @param rfcProvCertif the rfcProvCertif to set
	 */
	public void setRfcProvCertif(String rfcProvCertif) {
		this.rfcProvCertif = rfcProvCertif;
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
	 * @return the selloSAT
	 */
	public String getSelloSAT() {
		return selloSAT;
	}

	/**
	 * @param selloSAT the selloSAT to set
	 */
	public void setSelloSAT(String selloSAT) {
		this.selloSAT = selloSAT;
	}

	/**
	 * @return the idComplementosPago
	 */
	public Integer getIdComplementosPago() {
		return idComplementosPago;
	}

	/**
	 * @param idComplementosPago the idComplementosPago to set
	 */
	public void setIdComplementosPago(Integer idComplementosPago) {
		this.idComplementosPago = idComplementosPago;
	}

	/**
	 * @return the idDoctoRelacionado
	 */
	public Integer getIdDoctoRelacionado() {
		return idDoctoRelacionado;
	}

	/**
	 * @param idDoctoRelacionado the idDoctoRelacionado to set
	 */
	public void setIdDoctoRelacionado(Integer idDoctoRelacionado) {
		this.idDoctoRelacionado = idDoctoRelacionado;
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
	
	
	
}
