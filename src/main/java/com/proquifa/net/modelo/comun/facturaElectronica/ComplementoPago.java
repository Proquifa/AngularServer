package com.proquifa.net.modelo.comun.facturaElectronica;

import java.util.List;

public class ComplementoPago {

	private Long idComplementoPago;
	private String fechaPago;
	private String formaDePagoP;
	private String monedaP;
	private String tipoCambioP;
	private String monto;
	private String ctaOrdenante;
	private String nomBancoOrdExt;
	private String rfcEmisorCtaOrd;
	private int idFactura;
	private int idDeposito;
	private String numOperacion;
	
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

	private List<CPDocRelacionado> lstDoctRelacionados;
	
	/**
	 * @return the idComplementoPago
	 */
	public Long getIdComplementoPago() {
		return idComplementoPago;
	}
	
	/**
	 * @param idComplementoPago the idComplementoPago to set
	 */
	public void setIdComplementoPago(Long idComplementoPago) {
		this.idComplementoPago = idComplementoPago;
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
	 * @return the formaDePagoP
	 */
	public String getFormaDePagoP() {
		return formaDePagoP;
	}
	
	/**
	 * @param formaDePagoP the formaDePagoP to set
	 */
	public void setFormaDePagoP(String formaDePagoP) {
		this.formaDePagoP = formaDePagoP;
	}
	
	/**
	 * @return the monedaP
	 */
	public String getMonedaP() {
		return monedaP;
	}
	
	/**
	 * @param monedaP the monedaP to set
	 */
	public void setMonedaP(String monedaP) {
		this.monedaP = monedaP;
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
	 * @return the idFactura
	 */
	public int getIdFactura() {
		return idFactura;
	}
	
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	
	/**
	 * @return the idDeposito
	 */
	public int getIdDeposito() {
		return idDeposito;
	}
	
	/**
	 * @param idDeposito the idDeposito to set
	 */
	public void setIdDeposito(int idDeposito) {
		this.idDeposito = idDeposito;
	}

	/**
	 * @return the lstDoctRelacionados
	 */
	public List<CPDocRelacionado> getLstDoctRelacionados() {
		return lstDoctRelacionados;
	}

	/**
	 * @param lstDoctRelacionados the lstDoctRelacionados to set
	 */
	public void setLstDoctRelacionados(List<CPDocRelacionado> lstDoctRelacionados) {
		this.lstDoctRelacionados = lstDoctRelacionados;
	}

	/**
	 * @return the tipoCambioP
	 */
	public String getTipoCambioP() {
		return tipoCambioP;
	}

	/**
	 * @param tipoCambioP the tipoCambioP to set
	 */
	public void setTipoCambioP(String tipoCambioP) {
		this.tipoCambioP = tipoCambioP;
	}

	/**
	 * @return the numOperacion
	 */
	public String getNumOperacion() {
		return numOperacion;
	}

	/**
	 * @param numOperacion the numOperacion to set
	 */
	public void setNumOperacion(String numOperacion) {
		this.numOperacion = numOperacion;
	}
	
}
