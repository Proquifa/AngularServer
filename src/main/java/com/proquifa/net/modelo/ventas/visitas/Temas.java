/**
 * 
 */
package com.proquifa.net.modelo.ventas.visitas;

/**
 * @author ymendez
 *
 */

public class Temas {

	private Long idTema;
	private String asunto;
	private String argumentos;
	
	private Long idVisita;
	
	private Boolean eliminar;

	/**
	 * @return the idTema
	 */
	public Long getIdTema() {
		return idTema;
	}

	/**
	 * @param idTema the idTema to set
	 */
	public void setIdTema(Long idTema) {
		this.idTema = idTema;
	}

	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}

	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	/**
	 * @return the argumentos
	 */
	public String getArgumentos() {
		return argumentos;
	}

	/**
	 * @param argumentos the argumentos to set
	 */
	public void setArgumentos(String argumentos) {
		this.argumentos = argumentos;
	}

	/**
	 * @return the idVisita
	 */
	public Long getIdVisita() {
		return idVisita;
	}

	/**
	 * @param idVisita the idVisita to set
	 */
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}

	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(Boolean eliminar) {
		this.eliminar = eliminar;
	}

	/**
	 * @return the eliminar
	 */
	public Boolean getEliminar() {
		return eliminar;
	}
	
}
