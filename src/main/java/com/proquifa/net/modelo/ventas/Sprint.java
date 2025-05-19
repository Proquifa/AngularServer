package com.proquifa.net.modelo.ventas;

import java.util.Date;

public class Sprint {
	
	private Long idSprint;
	private int anio;
	private Date fechaInicio;
	private Date fechaFin;
	private String estado;
	private int numeroSprint;
	
	
	public Long getIdSprint() {
		return idSprint;
	}
	public void setIdSprint(Long idSprint) {
		this.idSprint = idSprint;
	}
	public int getAnio() {
		return anio;
	}
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getNumeroSprint() {
		return numeroSprint;
	}
	public void setNumeroSprint(int numeroSprint) {
		this.numeroSprint = numeroSprint;
	}
	
	
}
