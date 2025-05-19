package com.proquifa.net.modelo.staff;

import java.util.Date;

public class Asistencia {
	
	private Long idTrabajador;
	private String nombreCorto;
	private Date fecha; //Fecha de Checada
	private String hora; //Hora de Checada 
	//(Entrada Turno, Salida a Comer, Entrada de Comer, Salida Turno, Entrada Imprevista, Salida Imprevista)
	private String tipoChecada;
	private String area;
	private String depto;
	private String categoria;
	private String incidencia; // Tipo de Incidencia (Falta, Reatardo, Fuera de Tiempo)
	private Date checada; // Es la fecha de checada que incluye la hora, se usa para determinar la inicidencia
	private String rotacion; //Perfil de rotacion de Turno (DIR,COOR, REC)
	private String localidad; //Donde esta laborando el Trabajador en DISTRITO FEDERAL o CUERNAVACA
	
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
	 * @return the hora
	 */
	public String getHora() {
		return hora;
	}
	/**
	 * @param hora the hora to set
	 */
	public void setHora(String hora) {
		this.hora = hora;
	}
	/**
	 * @return the tipoChecada
	 */
	public String getTipoChecada() {
		return tipoChecada;
	}
	/**
	 * @param tipoChecada the tipoChecada to set
	 */
	public void setTipoChecada(String tipoChecada) {
		this.tipoChecada = tipoChecada;
	}
	/**
	 * @return the area
	 */
	public String getArea() {
		return area;
	}
	/**
	 * @param area the area to set
	 */
	public void setArea(String area) {
		this.area = area;
	}
	/**
	 * @return the depto
	 */
	public String getDepto() {
		return depto;
	}
	/**
	 * @param depto the depto to set
	 */
	public void setDepto(String depto) {
		this.depto = depto;
	}
	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the incidencia
	 */
	public String getIncidencia() {
		return incidencia;
	}
	/**
	 * @param incidencia the incidencia to set
	 */
	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}
	/**
	 * @return the checada
	 */
	public Date getChecada() {
		return checada;
	}
	/**
	 * @param checada the checada to set
	 */
	public void setChecada(Date checada) {
		this.checada = checada;
	}
	/**
	 * @return the rotacion
	 */
	public String getRotacion() {
		return rotacion;
	}
	/**
	 * @param rotacion the rotacion to set
	 */
	public void setRotacion(String rotacion) {
		this.rotacion = rotacion;
	}
	/**
	 * @return the localidad
	 */
	public String getLocalidad() {
		return localidad;
	}
	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}
}
