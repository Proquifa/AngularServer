/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author amartinez
 *
 */
public class Modulo {
	private Long idModulo;
	private String nombre;
	private String nombre_vista;
	private String tipo;	
	
	/**
	 * @return the idModulo
	 */
	public Long getIdModulo() {
		return idModulo;
	}
	/**
	 * @param idModulo the idModulo to set
	 */
	public void setIdModulo(Long idModulo) {
		this.idModulo = idModulo;
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
	 * @return the nombre_vista
	 */
	public String getNombre_vista() {
		return nombre_vista;
	}
	/**
	 * @param nombre_vista the nombre_vista to set
	 */
	public void setNombre_vista(String nombre_vista) {
		this.nombre_vista = nombre_vista;
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
}
