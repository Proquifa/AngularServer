/**
 * 
 */
package com.proquifa.net.modelo.compras.gestorproductosphs.productoaconfirmar;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class RechazoPartida {
	private int idRechazo;
	private String justificacionRechazo;
	private String comentariosRechazo;
	private Date fecha;
	private String fechaFormato;
	private String responsableRechazo;
	/**
	 * @param idRechazo the idRechazo to set
	 */
	public void setIdRechazo(int idRechazo) {
		this.idRechazo = idRechazo;
	}
	/**
	 * @return the idRechazo
	 */
	public int getIdRechazo() {
		return idRechazo;
	}
	
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fechaFormato the fechaFormato to set
	 */
	public void setFechaFormato(String fechaFormato) {
		this.fechaFormato = fechaFormato;
	}
	/**
	 * @return the fechaFormato
	 */
	public String getFechaFormato() {
		return fechaFormato;
	}
	/**
	 * @param responsableRechazo the responsableRechazo to set
	 */
	public void setResponsableRechazo(String responsableRechazo) {
		this.responsableRechazo = responsableRechazo;
	}
	/**
	 * @return the responsableRechazo
	 */
	public String getResponsableRechazo() {
		return responsableRechazo;
	}
	/**
	 * @param justificacionRechazo the justificacionRechazo to set
	 */
	public void setJustificacionRechazo(String justificacionRechazo) {
		this.justificacionRechazo = justificacionRechazo;
	}
	/**
	 * @return the justificacionRechazo
	 */
	public String getJustificacionRechazo() {
		return justificacionRechazo;
	}
	/**
	 * @param comentariosRechazo the comentariosRechazo to set
	 */
	public void setComentariosRechazo(String comentariosRechazo) {
		this.comentariosRechazo = comentariosRechazo;
	}
	/**
	 * @return the comentariosRechazo
	 */
	public String getComentariosRechazo() {
		return comentariosRechazo;
	}
	

}
