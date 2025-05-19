package com.proquifa.net.modelo.comun;

import java.util.List;

public class Proceso {

	private Long idProceso;
	private String nombre;
	private Long idEmpleado;
	private List<Subproceso> subprocesos;
	
	/**
	 * @return the idProceso
	 */
	public Long getIdProceso() {
		return idProceso;
	}
	/**
	 * @param idProceso the idProceso to set
	 */
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
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
	 * @return the empleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @param empleado the empleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	/**
	 * @param subprocesos the subprocesos to set
	 */
	public void setSubprocesos(List<Subproceso> subprocesos) {
		this.subprocesos = subprocesos;
	}
	/**
	 * @return the subprocesos
	 */
	public List<Subproceso> getSubprocesos() {
		return subprocesos;
	}

}
