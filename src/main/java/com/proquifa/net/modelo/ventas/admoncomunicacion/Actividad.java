/**
 * 
 */
package com.proquifa.net.modelo.ventas.admoncomunicacion;

import java.util.Date;

/**
 * @author ernestogonzalezlozada
 *
 */
public class Actividad {
	private Date fecha;
	private String usuario;
	private String tipoDocumento;
	private String documento;
	private String sujeto;
	private String observaciones;
	
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}
	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	/**
	 * @return the sujeto
	 */
	public String getSujeto() {
		return sujeto;
	}
	/**
	 * @param sujeto the sujeto to set
	 */
	public void setSujeto(String sujeto) {
		this.sujeto = sujeto;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	
	
}
