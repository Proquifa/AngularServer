/**
 * 
 */
package com.proquifa.net.modelo.ventas.admoncomunicacion;

import java.util.Date;

/**
 * @author ernestogonzalezlozada
 *
 */
public class Llamada {
	private Long folio;
	private Date fecha;
	private String recibio;
	private String destino;
	private String empresa;
	private String contacto;
	private String asunto;
	private String atendio;
	private String respuesta;
	private Boolean mensaje;
	private String origen;
	private String cotiza;
	private String estado;
	private String fechaHora;
	private String comentarios;
	private Boolean enteradoNotificacion;
	private Boolean requsicionTelefonica;
	private String enTiempo;
	private Boolean requsicionCotiza;
	private Date fechaInicio;
	private Date fechaFin;
	
	/**
	 * @return the folio
	 */
	public Long getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
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
	 * @return the recibio
	 */
	public String getRecibio() {
		return recibio;
	}
	/**
	 * @param recibio the recibio to set
	 */
	public void setRecibio(String recibio) {
		this.recibio = recibio;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 * @return the atendio
	 */
	public String getAtendio() {
		return atendio;
	}
	/**
	 * @param atendio the atendio to set
	 */
	public void setAtendio(String atendio) {
		this.atendio = atendio;
	}
	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}
	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	/**
	 * @return the mensaje
	 */
	public Boolean getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje the mensaje to set
	 */
	public void setMensaje(Boolean mensaje) {
		this.mensaje = mensaje;
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
	 * @return the cotiza
	 */
	public String getCotiza() {
		return cotiza;
	}
	/**
	 * @param cotiza the cotiza to set
	 */
	public void setCotiza(String cotiza) {
		this.cotiza = cotiza;
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
	 * @return the fechaHora
	 */
	public String getFechaHora() {
		return fechaHora;
	}
	/**
	 * @param fechaHora the fechaHora to set
	 */
	public void setFechaHora(String fechaHora) {
		this.fechaHora = fechaHora;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the enteradoNotificacion
	 */
	public Boolean getEnteradoNotificacion() {
		return enteradoNotificacion;
	}
	/**
	 * @param enteradoNotificacion the enteradoNotificacion to set
	 */
	public void setEnteradoNotificacion(Boolean enteradoNotificacion) {
		this.enteradoNotificacion = enteradoNotificacion;
	}
	/**
	 * @return the requsicionTelefonica
	 */
	public Boolean getRequsicionTelefonica() {
		return requsicionTelefonica;
	}
	/**
	 * @param requsicionTelefonica the requsicionTelefonica to set
	 */
	public void setRequsicionTelefonica(Boolean requsicionTelefonica) {
		this.requsicionTelefonica = requsicionTelefonica;
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
	public void setEnTiempo(String enTiempo) {
		this.enTiempo = enTiempo;
	}
	public String getEnTiempo() {
		return enTiempo;
	}
	public void setRequsicionCotiza(Boolean requsicionCotiza) {
		this.requsicionCotiza = requsicionCotiza;
	}
	public Boolean getRequsicionCotiza() {
		return requsicionCotiza;
	}
}