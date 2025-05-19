package com.proquifa.net.modelo.comun.facturaElectronica;

public class RespuestaSap {
	private String folio;
	private String serie;
	private String uuid;
	private String error;
	private int idFacturaE;
	
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
	 * @return the idFacturaE
	 */
	public int getIdFacturaE() {
		return idFacturaE;
	}

	/**
	 * @param idFacturaE the idFacturaE to set
	 */
	public void setIdFacturaE(int idFacturaE) {
		this.idFacturaE = idFacturaE;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	
}
