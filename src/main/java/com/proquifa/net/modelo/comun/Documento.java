/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author vromero
 *
 */
public class Documento {
	private String folio;
	private String tipo;
	private String evento;
	private String destino;
	private Boolean verificado;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}
	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @param verificado the verificado to set
	 */
	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
	/**
	 * @return the verificado
	 */
	public Boolean getVerificado() {
		return verificado;
	}
}
