/**
 * 
 */
package com.proquifa.net.modelo.catalogos.agenteAduanal;

import java.util.Date;

/**
 * @author viviana.romero
 *
 */
public class AgenteAConfigPrecio {
	
	private Long idAgenteAConfigPrecio;
	private Long idAgenteAduanal;
	private Long idConfigPrecio;
	private Double minimoOC;
	private Double maximoOC;
	private Date fua;
	private String moneda;
	private Boolean asociar;


	/**
	 * @return the idAgenteAConfigPrecio
	 */
	public Long getIdAgenteAConfigPrecio() {
		return idAgenteAConfigPrecio;
	}
	/**
	 * @param idAgenteAConfigPrecio the idAgenteAConfigPrecio to set
	 */
	public void setIdAgenteAConfigPrecio(Long idAgenteAConfigPrecio) {
		this.idAgenteAConfigPrecio = idAgenteAConfigPrecio;
	}
	/**
	 * @return the idAgenteAduanal
	 */
	public Long getIdAgenteAduanal() {
		return idAgenteAduanal;
	}
	/**
	 * @param idAgenteAduanal the idAgenteAduanal to set
	 */
	public void setIdAgenteAduanal(Long idAgenteAduanal) {
		this.idAgenteAduanal = idAgenteAduanal;
	}
	/**
	 * @return the idConfigPrecio
	 */
	public Long getIdConfigPrecio() {
		return idConfigPrecio;
	}
	/**
	 * @param idConfigPrecio the idConfigPrecio to set
	 */
	public void setIdConfigPrecio(Long idConfigPrecio) {
		this.idConfigPrecio = idConfigPrecio;
	}
	/**
	 * @return the minimoOC
	 */
	public Double getMinimoOC() {
		return minimoOC;
	}
	/**
	 * @param minimoOC the minimoOC to set
	 */
	public void setMinimoOC(Double minimoOC) {
		this.minimoOC = minimoOC;
	}
	/**
	 * @return the maximoOC
	 */
	public Double getMaximoOC() {
		return maximoOC;
	}
	/**
	 * @param maximoOC the maximoOC to set
	 */
	public void setMaximoOC(Double maximoOC) {
		this.maximoOC = maximoOC;
	}
	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}
	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
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
	 * @return the asociar
	 */
	public Boolean getAsociar() {
		return asociar;
	}
	/**
	 * @param asociar the asociar to set
	 */
	public void setAsociar(Boolean asociar) {
		this.asociar = asociar;
	}
	
	
	
	

}
