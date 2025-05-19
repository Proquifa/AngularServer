package com.proquifa.net.modelo.comun;

import java.util.List;

public class Funcion {

	private Long idFuncion;
	private String identificador;
	private String nombre;
	private String nivel;
	private Long subproceso;
	private String usuario;
	private List<Empleado> empleados;

	/**
	 * @return the idFuncion
	 */
	public Long getIdFuncion() {
		return idFuncion;
	}
	/**
	 * @param idFuncion the idFuncion to set
	 */
	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}
	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}
	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
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
	 * @return the nivel
	 */
	public String getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	/**
	 * @return the subproceso
	 */
	public Long getSubproceso() {
		return subproceso;
	}
	/**
	 * @param subproceso the subproceso to set
	 */
	public void setSubproceso(Long subproceso) {
		this.subproceso = subproceso;
	}
	/**
	 * @param empleados the empleados to set
	 */
	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	/**
	 * @return the empleados
	 */
	public List<Empleado> getEmpleados() {
		return empleados;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
