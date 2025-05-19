/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author ernestogonzalezlozada
 *
 */
public class Folio {
	private Long idFolio;
	private String folioCompleto;
	private String valor;
	/**
	 * @return the idFolio
	 */
	public Long getIdFolio() {
		return idFolio;
	}
	/**
	 * @param idFolio the idFolio to set
	 */
	public void setIdFolio(Long idFolio) {
		this.idFolio = idFolio;
	}
	/**
	 * @return the folioCompleto
	 */
	public String getFolioCompleto() {
		return folioCompleto;
	}
	/**
	 * @param folioCompleto the folioCompleto to set
	 */
	public void setFolioCompleto(String folioCompleto) {
		this.folioCompleto = folioCompleto;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}
	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

}
