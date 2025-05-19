package com.proquifa.net.modelo.staff;

public class Trabajador {
	
	private Long idTrabajador;
	private Long NIP;
	//private String clave;
	private String nombre;
	private String nombreCorto;
	private String claveArea;
	private String claveDepto;
	private String claveCategoria;
	
	/**
	 * @return the idTrabajador
	 */
	public Long getIdTrabajador() {
		return idTrabajador;
	}
	/**
	 * @param idTrabajador the idTrabajador to set
	 */
	public void setIdTrabajador(Long idTrabajador) {
		this.idTrabajador = idTrabajador;
	}
	/**
	 * @return the nIP
	 */
	public Long getNIP() {
		return NIP;
	}
	/**
	 * @param nIP the nIP to set
	 */
	public void setNIP(Long nIP) {
		NIP = nIP;
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
	 * @return the nombreCorto
	 */
	public String getNombreCorto() {
		return nombreCorto;
	}
	/**
	 * @param nombreCorto the nombreCorto to set
	 */
	public void setNombreCorto(String nombreCorto) {
		this.nombreCorto = nombreCorto;
	}
	/**
	 * @return the claveArea
	 */
	public String getClaveArea() {
		return claveArea;
	}
	/**
	 * @param claveArea the claveArea to set
	 */
	public void setClaveArea(String claveArea) {
		this.claveArea = claveArea;
	}
	/**
	 * @return the claveDepto
	 */
	public String getClaveDepto() {
		return claveDepto;
	}
	/**
	 * @param claveDepto the claveDepto to set
	 */
	public void setClaveDepto(String claveDepto) {
		this.claveDepto = claveDepto;
	}
	/**
	 * @return the claveCategoria
	 */
	public String getClaveCategoria() {
		return claveCategoria;
	}
	/**
	 * @param claveCategoria the claveCategoria to set
	 */
	public void setClaveCategoria(String claveCategoria) {
		this.claveCategoria = claveCategoria;
	}
}
