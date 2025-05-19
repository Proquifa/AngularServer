package com.proquifa.net.modelo.comun.facturaElectronica;

public class CPDocRelacionado {
	private Long idCPDocRelacionado;
	private String idDocumento;
	private String serie;
	private String folio;
	private String monedaDR;
	private String metodoPagoDR;
	private Integer numParcialidad;
	private Double impSaldoAnt;
	private Double impPagado;
	private Double impSaldoInsoluto;
	private String monedaBanco;
	private String tipoCambio;
	private String tipoCambioDR;
	private Long idComplementoPago;
	private String tipoCambioP;

	public String getTipoCambioP() {
		return tipoCambioP;
	}

	public void setTipoCambioP(String tipoCambioP) {
		this.tipoCambioP = tipoCambioP;
	}

	/**
	 * @return the idCPDocRelacionado
	 */
	public Long getIdCPDocRelacionado() {
		return idCPDocRelacionado;
	}

	/**
	 * @param idCPDocRelacionado the idCPDocRelacionado to set
	 */
	public void setIdCPDocRelacionado(Long idCPDocRelacionado) {
		this.idCPDocRelacionado = idCPDocRelacionado;
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
	 * @return the metodoPagoDR
	 */
	public String getMetodoPagoDR() {
		return metodoPagoDR;
	}

	/**
	 * @param metodoPagoDR the metodoPagoDR to set
	 */
	public void setMetodoPagoDR(String metodoPagoDR) {
		this.metodoPagoDR = metodoPagoDR;
	}

	/**
	 * @return the numParcialidad
	 */
	public Integer getNumParcialidad() {
		return numParcialidad;
	}

	/**
	 * @param numParcialidad the numParcialidad to set
	 */
	public void setNumParcialidad(Integer numParcialidad) {
		this.numParcialidad = numParcialidad;
	}

	/**
	 * @return the impSaldoAnt
	 */
	public Double getImpSaldoAnt() {
		return impSaldoAnt;
	}

	/**
	 * @param impSaldoAnt the impSaldoAnt to set
	 */
	public void setImpSaldoAnt(Double impSaldoAnt) {
		this.impSaldoAnt = impSaldoAnt;
	}

	/**
	 * @return the impPagado
	 */
	public Double getImpPagado() {
		return impPagado;
	}

	/**
	 * @param impPagado the impPagado to set
	 */
	public void setImpPagado(Double impPagado) {
		this.impPagado = impPagado;
	}

	/**
	 * @return the impSaldoInsoluto
	 */
	public Double getImpSaldoInsoluto() {
		return impSaldoInsoluto;
	}

	/**
	 * @param impSaldoInsoluto the impSaldoInsoluto to set
	 */
	public void setImpSaldoInsoluto(Double impSaldoInsoluto) {
		this.impSaldoInsoluto = impSaldoInsoluto;
	}

	/**
	 * @return the monedaBanco
	 */
	public String getMonedaBanco() {
		return monedaBanco;
	}

	/**
	 * @param monedaBanco the monedaBanco to set
	 */
	public void setMonedaBanco(String monedaBanco) {
		this.monedaBanco = monedaBanco;
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

}
