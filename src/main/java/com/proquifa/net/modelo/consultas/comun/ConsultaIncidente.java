package com.proquifa.net.modelo.consultas.comun;

import java.util.Date;

import com.proquifa.net.modelo.comun.Incidente;

/**
 * @author fmartinez
 *
 */
public class ConsultaIncidente extends Incidente {
	
	private String asociado;
	private String nombre_Empleado;
	private String origen;
	private String decision;	
	private String tiempoProceso;
	private Long tiempoTotal;
	private String estado;
	
	//Extras
	private String responsable;
	private Date fechaInicio;
	private Date fechaFin;
	private Long idGestionRelacionada;	
	private Boolean aceptado;
	
	/**
	 * @return the asociado
	 */
	public String getAsociado() {
		return asociado;
	}
	/**
	 * @param asociado the asociado to set
	 */
	public void setAsociado(String asociado) {
		this.asociado = asociado;
	}
	/**
	 * @return the nombre_Empleado
	 */
	public String getNombre_Empleado() {
		return nombre_Empleado;
	}
	/**
	 * @param nombre_Empleado the nombre_Empleado to set
	 */
	public void setNombre_Empleado(String nombre_Empleado) {
		this.nombre_Empleado = nombre_Empleado;
	}
	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	/**
	 * @return the decision
	 */
	public String getDecision() {
		return decision;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(String decision) {
		this.decision = decision;
	}
	/**
	 * @return the tiempoProceso
	 */
	public String getTiempoProceso() {
		return tiempoProceso;
	}
	/**
	 * @param tiempoProceso the tiempoProceso to set
	 */
	public void setTiempoProceso(String tiempoProceso) {
		this.tiempoProceso = tiempoProceso;
	}
	/**
	 * @return the tiempoTotal
	 */
	public Long getTiempoTotal() {
		return tiempoTotal;
	}
	/**
	 * @param tiempoTotal the tiempoTotal to set
	 */
	public void setTiempoTotal(Long tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}
	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the idGestionRelacionada
	 */
	public Long getIdGestionRelacionada() {
		return idGestionRelacionada;
	}
	/**
	 * @param idGestionRelacionada the idGestionRelacionada to set
	 */
	public void setIdGestionRelacionada(Long idGestionRelacionada) {
		this.idGestionRelacionada = idGestionRelacionada;
	}
	/**
	 * @return the aceptado
	 */
	public Boolean getAceptado() {
		return aceptado;
	}
	/**
	 * @param aceptado the aceptado to set
	 */
	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
	}	
}