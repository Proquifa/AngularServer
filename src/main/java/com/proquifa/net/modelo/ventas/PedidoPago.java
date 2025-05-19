/**
 * 
 */
package com.proquifa.net.modelo.ventas;

import java.util.Date;

/**
 * @author fmartinez
 *
 */
public class PedidoPago {
	private Long folio;
	private Long pedido;
	private Long pago;
	private String cpedido;
	private Date fecha;
	private Long moroso;
	private String comentarios;
	
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
	 * @return the pedido
	 */
	public Long getPedido() {
		return pedido;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(Long pedido) {
		this.pedido = pedido;
	}
	/**
	 * @return the pago
	 */
	public Long getPago() {
		return pago;
	}
	/**
	 * @param pago the pago to set
	 */
	public void setPago(Long pago) {
		this.pago = pago;
	}
	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}
	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
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
	 * @return the moroso
	 */
	public Long getMoroso() {
		return moroso;
	}
	/**
	 * @param moroso the moroso to set
	 */
	public void setMoroso(Long moroso) {
		this.moroso = moroso;
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
}
