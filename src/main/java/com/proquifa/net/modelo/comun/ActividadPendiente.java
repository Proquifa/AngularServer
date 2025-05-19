/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author vromero
 *
 */
public class ActividadPendiente {
	private String nombre;
	private Long totalPendiente;
	private String rol;
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
	 * @return the totalPendiente
	 */
	public Long getTotalPendiente() {
		return totalPendiente;
	}
	/**
	 * @param totalPendiente the totalPendiente to set
	 */
	public void setTotalPendiente(Long totalPendiente) {
		this.totalPendiente = totalPendiente;
	}
	/***
	 * 
	 * @param rol
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	/***
	 * 
	 * @return
	 */
	public String getRol() {
		return rol;
	}

}
