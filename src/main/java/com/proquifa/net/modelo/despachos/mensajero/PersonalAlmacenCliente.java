package com.proquifa.net.modelo.despachos.mensajero;

public class PersonalAlmacenCliente {
	
	private Long idPersonal;
	private Long idCliente;
	private String nombre;
	private String puesto;
	private Boolean borrar = false;
	/**
	 * @return the idPersonal
	 */
	public Long getIdPersonal() {
		return idPersonal;
	}
	/**
	 * @param idPersonal the idPersonal to set
	 */
	public void setIdPersonal(Long idPersonal) {
		this.idPersonal = idPersonal;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the puesto
	 */
	public String getPuesto() {
		return puesto;
	}
	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	/**
	 * @return the borrar
	 */
	public Boolean getBorrar() {
		return borrar;
	}
	/**
	 * @param borrar the borrar to set
	 */
	public void setBorrar(Boolean borrar) {
		this.borrar = borrar;
	}

}
