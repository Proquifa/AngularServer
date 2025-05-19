/**
 * 
 */
package com.proquifa.net.modelo.despachos;

/**
 * @author ymendez
 *
 */
public class Guias {

	private String guia;
	private String mensajeria;
	private Integer totalClientes;
	private Integer totalFacturas;
	private String fechaEnvio;
	private String hora;
	private Integer idPendiente;
	
	/**
	 * 
	 */
	public Guias() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the guia
	 */
	public String getGuia() {
		return guia;
	}

	/**
	 * @param guia the guia to set
	 */
	public void setGuia(String guia) {
		this.guia = guia;
	}

	/**
	 * @return the mensajeria
	 */
	public String getMensajeria() {
		return mensajeria;
	}

	/**
	 * @param mensajeria the mensajeria to set
	 */
	public void setMensajeria(String mensajeria) {
		this.mensajeria = mensajeria;
	}

	/**
	 * @return the totalClientes
	 */
	public Integer getTotalClientes() {
		return totalClientes;
	}

	/**
	 * @param totalClientes the totalClientes to set
	 */
	public void setTotalClientes(Integer totalClientes) {
		this.totalClientes = totalClientes;
	}

	/**
	 * @return the totalFacturas
	 */
	public Integer getTotalFacturas() {
		return totalFacturas;
	}

	/**
	 * @param totalFacturas the totalFacturas to set
	 */
	public void setTotalFacturas(Integer totalFacturas) {
		this.totalFacturas = totalFacturas;
	}

	/**
	 * @return the fechaEnvio
	 */
	public String getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(String fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	/**
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}

	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}

	/**
	 * @return the idPendiente
	 */
	public Integer getIdPendiente() {
		return idPendiente;
	}

	/**
	 * @param idPendiente the idPendiente to set
	 */
	public void setIdPendiente(Integer idPendiente) {
		this.idPendiente = idPendiente;
	}
	
	
}
