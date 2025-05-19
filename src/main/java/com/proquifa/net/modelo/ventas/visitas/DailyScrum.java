package com.proquifa.net.modelo.ventas.visitas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.ventas.Sprint;

public class DailyScrum {

	private int idDailyScrum;
	private int idSprint;
	private int numeroDaily;
	private Sprint sprint;
	private List<Empleado> empleados;
	private String observacion;
	private Date fechaDaily;
	
	public int getIdDailyScrum() {
		return idDailyScrum;
	}
	public void setIdDailyScrum(int idDailyScrum) {
		this.idDailyScrum = idDailyScrum;
	}
	public int getNumeroDaily() {
		return numeroDaily;
	}
	public void setNumeroDaily(int numeroDaily) {
		this.numeroDaily = numeroDaily;
	}
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	public List<Empleado> getEmpleados() {
		return empleados;
	}
	public void setEmpleados(List<Empleado> empleados) {
		this.empleados = empleados;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public int getIdSprint() {
		return idSprint;
	}
	public void setIdSprint(int idSprint) {
		this.idSprint = idSprint;
	}
	public Date getFechaDaily() {
		return fechaDaily;
	}
	public void setFechaDaily(Date fechaDaily) {
		this.fechaDaily = fechaDaily;
	}
}
