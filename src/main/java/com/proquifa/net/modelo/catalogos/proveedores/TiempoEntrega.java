/**
 * 
 */
package com.proquifa.net.modelo.catalogos.proveedores;

/**
 * @author orosales
 *
 */
public class TiempoEntrega {
	private Long idTiempoEntrega;
	private String requierePExiste;
	private String requierePNoExiste;
	private String requierePNo;
	private String fleteExpress;
	private Long idConfiguracionPrecio;
	private String ruta;
	private Integer factorFlexibilidad;
	
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the idConfiguracionPrecio
	 */
	public Long getIdConfiguracionPrecio() {
		return idConfiguracionPrecio;
	}
	/**
	 * @param idConfiguracionPrecio the idConfiguracionPrecio to set
	 */
	public void setIdConfiguracionPrecio(Long idConfiguracionPrecio) {
		this.idConfiguracionPrecio = idConfiguracionPrecio;
	}
	/**
	 * @return the idTiempoEntrega
	 */
	public Long getIdTiempoEntrega() {
		return idTiempoEntrega;
	}
	/**
	 * @param idTiempoEntrega the idTiempoEntrega to set
	 */
	public void setIdTiempoEntrega(Long idTiempoEntrega) {
		this.idTiempoEntrega = idTiempoEntrega;
	}
	/**
	 * @return the requierePExiste
	 */
	public String getRequierePExiste() {
		return requierePExiste;
	}
	/**
	 * @param requierePExiste the requierePExiste to set
	 */
	public void setRequierePExiste(String requierePExiste) {
		this.requierePExiste = requierePExiste;
	}
	/**
	 * @return the requierePNoExiste
	 */
	public String getRequierePNoExiste() {
		return requierePNoExiste;
	}
	/**
	 * @param requierePNoExiste the requierePNoExiste to set
	 */
	public void setRequierePNoExiste(String requierePNoExiste) {
		this.requierePNoExiste = requierePNoExiste;
	}
	/**
	 * @return the requierePNo
	 */
	public String getRequierePNo() {
		return requierePNo;
	}
	/**
	 * @param requierePNo the requierePNo to set
	 */
	public void setRequierePNo(String requierePNo) {
		this.requierePNo = requierePNo;
	}
	/**
	 * @return the fleteExpress
	 */
	public String getFleteExpress() {
		return fleteExpress;
	}
	/**
	 * @param fleteExpress the fleteExpress to set
	 */
	public void setFleteExpress(String fleteExpress) {
		this.fleteExpress = fleteExpress;
	}
	/**
	 * @return the factorFlexibilidad
	 */
	public Integer getFactorFlexibilidad() {
		return factorFlexibilidad;
	}
	/**
	 * @param factorFlexibilidad the factorFlexibilidad to set
	 */
	public void setFactorFlexibilidad(Integer factorFlexibilidad) {
		this.factorFlexibilidad = factorFlexibilidad;
	}

}