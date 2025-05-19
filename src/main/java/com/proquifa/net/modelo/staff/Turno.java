package com.proquifa.net.modelo.staff;

import java.util.Date;
/**
 * Se guarda los horarios de entrada, salida y de comida
 * que tienen cada grupo asignado
 * (Grupo es la Rotacion puede ser Coordinador Director, Recepcionista)
 * @author isaac.garcia
 *
 */
public class Turno {
	
	private String turno; // Clava del Grupo al cual aplica el turno ejemplo: COOR, DIR, etc.
	private Date entradaHasta; // La hora hasta la que puede entrar
	private Date Salida; // Hora de Salida
	private Date comida; // Apartir de esta Hora se puede Salir a Comer
	private Date comidaRegreso; // Hasta que hora se puede Regresar a Comer
	private Date comidaTiempo; // El tiempo de comida que tiene asignado el turno de ese grupo
	
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
	/**
	 * @return the entradaHasta
	 */
	public Date getEntradaHasta() {
		return entradaHasta;
	}
	/**
	 * @param entradaHasta the entradaHasta to set
	 */
	public void setEntradaHasta(Date entradaHasta) {
		this.entradaHasta = entradaHasta;
	}
	/**
	 * @return the salida
	 */
	public Date getSalida() {
		return Salida;
	}
	/**
	 * @param salida the salida to set
	 */
	public void setSalida(Date salida) {
		Salida = salida;
	}
	/**
	 * @return the comida
	 */
	public Date getComida() {
		return comida;
	}
	/**
	 * @param comida the comida to set
	 */
	public void setComida(Date comida) {
		this.comida = comida;
	}
	/**
	 * @return the comidaRegreso
	 */
	public Date getComidaRegreso() {
		return comidaRegreso;
	}
	/**
	 * @param comidaRegreso the comidaRegreso to set
	 */
	public void setComidaRegreso(Date comidaRegreso) {
		this.comidaRegreso = comidaRegreso;
	}
	/**
	 * @return the comidaTiempo
	 */
	public Date getComidaTiempo() {
		return comidaTiempo;
	}
	/**
	 * @param comidaTiempo the comidaTiempo to set
	 */
	public void setComidaTiempo(Date comidaTiempo) {
		this.comidaTiempo = comidaTiempo;
	}
}
