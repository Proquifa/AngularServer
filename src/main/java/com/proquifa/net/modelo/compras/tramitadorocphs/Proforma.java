/**
 * 
 */
package com.proquifa.net.modelo.compras.tramitadorocphs;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class Proforma {
	
	private Long idProforma;
	private Long idProveedor;
	private String folio;
	private Boolean abierta;
	private Boolean enviable;
	private Date fechaEnvio;
	private String almacenEntrega;
	private String quienCompra;
	private Long idContacto;
	private String otroCorreo;
	private Double total;
	private String comentarios;
	private Boolean impuestos;
	/**
	 * @return the idProforma
	 */
	public Long getIdProforma() {
		return idProforma;
	}
	/**
	 * @param idProforma the idProforma to set
	 */
	public void setIdProforma(Long idProforma) {
		this.idProforma = idProforma;
	}
	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the abierta
	 */
	public Boolean getAbierta() {
		return abierta;
	}
	/**
	 * @param abierta the abierta to set
	 */
	public void setAbierta(Boolean abierta) {
		this.abierta = abierta;
	}
	/**
	 * @return the enviable
	 */
	public Boolean getEnviable() {
		return enviable;
	}
	/**
	 * @param enviable the enviable to set
	 */
	public void setEnviable(Boolean enviable) {
		this.enviable = enviable;
	}
	/**
	 * @return the fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	/**
	 * @return the almacenEntrega
	 */
	public String getAlmacenEntrega() {
		return almacenEntrega;
	}
	/**
	 * @param almacenEntrega the almacenEntrega to set
	 */
	public void setAlmacenEntrega(String almacenEntrega) {
		this.almacenEntrega = almacenEntrega;
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
	/**
	 * @return the otroCorreo
	 */
	public String getOtroCorreo() {
		return otroCorreo;
	}
	/**
	 * @param otroCorreo the otroCorreo to set
	 */
	public void setOtroCorreo(String otroCorreo) {
		this.otroCorreo = otroCorreo;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
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
	 * @return the impuestos
	 */
	public Boolean getImpuestos() {
		return impuestos;
	}
	/**
	 * @param impuestos the impuestos to set
	 */
	public void setImpuestos(Boolean impuestos) {
		this.impuestos = impuestos;
	}
	/**
	 * @param quienCompra the quienCompra to set
	 */
	public void setQuienCompra(String quienCompra) {
		this.quienCompra = quienCompra;
	}
	/**
	 * @return the quienCompra
	 */
	public String getQuienCompra() {
		return quienCompra;
	}
	

}
