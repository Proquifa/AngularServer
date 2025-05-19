package com.proquifa.net.modelo.comun;

import java.util.List;

import com.proquifa.net.modelo.catalogos.indicadores.Indicador;

public class Subproceso {

	private Long idSubproceso;
	private String nombre;
	private Long idProceso;
	private List<Funcion> funciones;
	private Long idEmpleado;
	private List<Indicador> indicadores;
	/**
	 * @return the idSubproceso
	 */
	public Long getIdSubproceso() {
		return idSubproceso;
	}
	/**
	 * @param idSubproceso the idSubproceso to set
	 */
	public void setIdSubproceso(Long idSubproceso) {
		this.idSubproceso = idSubproceso;
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
	 * @param idProceso the idProceso to set
	 */
	public void setIdProceso(Long idProceso) {
		this.idProceso = idProceso;
	}
	/**
	 * @return the idProceso
	 */
	public Long getIdProceso() {
		return idProceso;
	}
	/**
	 * @param funciones the funciones to set
	 */
	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}
	/**
	 * @return the funciones
	 */
	public List<Funcion> getFunciones() {
		return funciones;
	}
	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @return the indicadores
	 */
	public List<Indicador> getIndicadores() {
		return indicadores;
	}
	/**
	 * @param indicadores the indicadores to set
	 */
	public void setIndicadores(List<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

}
