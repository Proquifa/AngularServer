package com.proquifa.net.modelo.staff;

import java.util.Date;

public class CambioTurno {
	
	private Long idTrabajador;
	private Date fecha;
	private String turno;
	
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
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the turno
	 */
	public String getTurno() {
		return turno;
	}
	/**
	 * @param turno the turno to set
	 */
	public void setTurno(String turno) {
		this.turno = turno;
	}	
}
