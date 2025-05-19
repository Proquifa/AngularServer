/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;

/**
 * @author ernestogonzalezlozada
 *
 */
public class ModificacionDocumentoRecibido {
	private Long idModificacion;
	private Long idDocumento;
	private Date fecha;
	private String manejo;
	private String origen;
	private Long empresa;
	private String recibio;
	private String medio;
	private String tipo;
	private String numero;
	private String observacion;
	private String realizo;
	private Date fechaOrigen;
	private Double montoDocto;
	private Boolean existeReferencia;
	private Long idContacto;
	
	/**
	 * @return the idModificacion
	 */
	public Long getIdModificacion() {
		return idModificacion;
	}
	/**
	 * @param idModificacion the idModificacion to set
	 */
	public void setIdModificacion(Long idModificacion) {
		this.idModificacion = idModificacion;
	}
	/**
	 * @return the idDocumento
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}
	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
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
	 * @return the manejo
	 */
	public String getManejo() {
		return manejo;
	}
	/**
	 * @param manejo the manejo to set
	 */
	public void setManejo(String manejo) {
		this.manejo = manejo;
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
	 * @return the empresa
	 */
	public Long getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
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
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}
	/**
	 * @param medio the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}
	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}
	/**
	 * @return the observacion
	 */
	public String getObservacion() {
		return observacion;
	}
	/**
	 * @param observacion the observacion to set
	 */
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	/**
	 * @return the realizo
	 */
	public String getRealizo() {
		return realizo;
	}
	/**
	 * @param realizo the realizo to set
	 */
	public void setRealizo(String realizo) {
		this.realizo = realizo;
	}
	/**
	 * @return the fechaOrigen
	 */
	public Date getFechaOrigen() {
		return fechaOrigen;
	}
	/**
	 * @param fechaOrigen the fechaOrigen to set
	 */
	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}
	/**
	 * @return the montoDocto
	 */
	public Double getMontoDocto() {
		return montoDocto;
	}
	/**
	 * @param montoDocto the montoDocto to set
	 */
	public void setMontoDocto(Double montoDocto) {
		this.montoDocto = montoDocto;
	}
	/**
	 * @return the existeReferencia
	 */
	public Boolean getExisteReferencia() {
		return existeReferencia;
	}
	/**
	 * @param existeReferencia the existeReferencia to set
	 */
	public void setExisteReferencia(Boolean existeReferencia) {
		this.existeReferencia = existeReferencia;
	}
	/**
	 * @return the idContacto
	 */
	public Long getIdContacto() {
		return idContacto;
	}
	/**
	 * @param idContacto the idContacto to set
	 */
	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}
}
