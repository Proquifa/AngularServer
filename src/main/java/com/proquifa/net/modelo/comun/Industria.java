/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author misael.camanos
 *
 */
public class Industria {
	
	private Long idIndustria;
	private Long idCorporativo;
	private Long idIndustriaCorporativo;
	private boolean activo;
	
	/**
	 * @return the idIndustria
	 */
	public Long getIdIndustria() {
		return idIndustria;
	}
	/**
	 * @param idIndustria the idIndustria to set
	 */
	public void setIdIndustria(Long idIndustria) {
		this.idIndustria = idIndustria;
	}
	/**
	 * @return the idCorporativo
	 */
	public Long getIdCorporativo() {
		return idCorporativo;
	}
	/**
	 * @param idCorporativo the idCorporativo to set
	 */
	public void setIdCorporativo(Long idCorporativo) {
		this.idCorporativo = idCorporativo;
	}
	/**
	 * @return the idIndustriaCorporativo
	 */
	public Long getIdIndustriaCorporativo() {
		return idIndustriaCorporativo;
	}
	/**
	 * @param idIndustriaCorporativo the idIndustriaCorporativo to set
	 */
	public void setIdIndustriaCorporativo(Long idIndustriaCorporativo) {
		this.idIndustriaCorporativo = idIndustriaCorporativo;
	}
	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
