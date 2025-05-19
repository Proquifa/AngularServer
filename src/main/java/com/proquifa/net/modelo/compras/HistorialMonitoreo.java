/**
 * 
 */
package com.proquifa.net.modelo.compras;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class HistorialMonitoreo {
	
	private String origenMonitoreo;
	private Date fecha;
	private String gestor;
	private String tipo;
	private String comentarios;
	/**
	 * @param origenMonitoreo the origenMonitoreo to set
	 */
	public void setOrigenMonitoreo(String origenMonitoreo) {
		this.origenMonitoreo = origenMonitoreo;
	}
	/**
	 * @return the origenMonitoreo
	 */
	public String getOrigenMonitoreo() {
		return origenMonitoreo;
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
	 * @param gestor the gestor to set
	 */
	public void setGestor(String gestor) {
		this.gestor = gestor;
	}
	/**
	 * @return the gestor
	 */
	public String getGestor() {
		return gestor;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

}
