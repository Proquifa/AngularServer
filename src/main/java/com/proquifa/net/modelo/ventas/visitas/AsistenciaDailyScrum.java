package com.proquifa.net.modelo.ventas.visitas;

public class AsistenciaDailyScrum {

	private int idAsistenciaDailyScrum;
	private boolean asistencia;
	private int idEmpleado; 
	private int idDailyScrum;
	private String codigoAsistencia;
	
	public int getIdAsistenciaDailyScrum() {
		return idAsistenciaDailyScrum;
	}
	public void setIdAsistenciaDailyScrum(int idAsistenciaDailyScrum) {
		this.idAsistenciaDailyScrum = idAsistenciaDailyScrum;
	}
	public int getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public int getIdDailyScrum() {
		return idDailyScrum;
	}
	public void setIdDailyScrum(int idDailyScrum) {
		this.idDailyScrum = idDailyScrum;
	}
	public boolean isAsistencia() {
		return asistencia;
	}
	public void setAsistencia(boolean asistencia) {
		this.asistencia = asistencia;
	}
	public String getCodigoAsistencia() {
		return codigoAsistencia;
	}
	public void setCodigoAsistencia(String codigoAsistencia) {
		this.codigoAsistencia = codigoAsistencia;
	}
	
}
