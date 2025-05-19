package com.proquifa.net.modelo.despachos.mensajero;

import java.util.Date;

public class ConfirmacionEntrega {
	
	private String contacto;
	private String cliente;
	private String cpedido;
	private String factura;
	private Integer cantidad;
	private String producto;
	private Date fecha;
	private String email;
	private String vendedor;
	private String ev;
	private Date fechaEsperadaEntrega;
	private String personaRecibio;
	private String puestoRecibio;
	private String registroEntrega;
	private String pedidoCliente;
	private String estadoEntrega;
	private String tipoJustificacion;
	private String razonesEntrega;
	private String tiempoEntrega;
	private Integer totalPiezas;
	private Integer idFabricante;
	private String lote;
	private String catalogo;
	private Integer diasAtraso;
	/**
	 * @return the registroEntrega
	 */
	public String getRegistroEntrega() {
		return registroEntrega;
	}
	/**
	 * @param registroEntrega the registroEntrega to set
	 */
	public void setRegistroEntrega(String registroEntrega) {
		this.registroEntrega = registroEntrega;
	}
	/**
	 * @return the pedidoCliente
	 */
	public String getPedidoCliente() {
		return pedidoCliente;
	}
	/**
	 * @param pedidoCliente the pedidoCliente to set
	 */
	public void setPedidoCliente(String pedidoCliente) {
		this.pedidoCliente = pedidoCliente;
	}
	/**
	 * @return the estadoEntrega
	 */
	public String getEstadoEntrega() {
		return estadoEntrega;
	}
	/**
	 * @param estadoEntrega the estadoEntrega to set
	 */
	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}
	/**
	 * @return the tipoJustificacion
	 */
	public String getTipoJustificacion() {
		return tipoJustificacion;
	}
	/**
	 * @param tipoJustificacion the tipoJustificacion to set
	 */
	public void setTipoJustificacion(String tipoJustificacion) {
		this.tipoJustificacion = tipoJustificacion;
	}
	/**
	 * @return the razonesEntrega
	 */
	public String getRazonesEntrega() {
		return razonesEntrega;
	}
	/**
	 * @param razonesEntrega the razonesEntrega to set
	 */
	public void setRazonesEntrega(String razonesEntrega) {
		this.razonesEntrega = razonesEntrega;
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
	 * @return the factura
	 */
	public String getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(String factura) {
		this.factura = factura;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the producto
	 */
	public String getProducto() {
		return producto;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(String producto) {
		this.producto = producto;
	}
	/**
	 * @return the fechaEsperadaEntrega
	 */
	public Date getFechaEsperadaEntrega() {
		return fechaEsperadaEntrega;
	}
	/**
	 * @param fechaEsperadaEntrega the fechaEsperadaEntrega to set
	 */
	public void setFechaEsperadaEntrega(Date fechaEsperadaEntrega) {
		this.fechaEsperadaEntrega = fechaEsperadaEntrega;
	}
	/**
	 * @return the personaRecibio
	 */
	public String getPersonaRecibio() {
		return personaRecibio;
	}
	/**
	 * @param personaRecibio the personaRecibio to set
	 */
	public void setPersonaRecibio(String personaRecibio) {
		this.personaRecibio = personaRecibio;
	}
	/**
	 * @return the tiempoEntrega
	 */
	public String getTiempoEntrega() {
		return tiempoEntrega;
	}
	/**
	 * @param tiempoEntrega the tiempoEntrega to set
	 */
	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}
	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the totalPiezas
	 */
	public Integer getTotalPiezas() {
		return totalPiezas;
	}
	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	/**
	 * @return the vendedor
	 */
	public String getVendedor() {
		return vendedor;
	}
	/**
	 * @param vendedor the vendedor to set
	 */
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	/**
	 * @return the ev
	 */
	public String getEv() {
		return ev;
	}
	/**
	 * @param ev the ev to set
	 */
	public void setEv(String ev) {
		this.ev = ev;
	}
	public Integer getDiasAtraso() {
		return diasAtraso;
	}
	public void setDiasAtraso(Integer diasAtraso) {
		this.diasAtraso = diasAtraso;
	}
	/**
	 * @return the puestoRecibio
	 */
	public String getPuestoRecibio() {
		return puestoRecibio;
	}
	/**
	 * @param puestoRecibio the puestoRecibio to set
	 */
	public void setPuestoRecibio(String puestoRecibio) {
		this.puestoRecibio = puestoRecibio;
	}
	/**
	 * @return the idFabricante
	 */
	public Integer getIdFabricante() {
		return idFabricante;
	}
	/**
	 * @param idFabricante the idFabricante to set
	 */
	public void setIdFabricante(Integer idFabricante) {
		this.idFabricante = idFabricante;
	}
	/**
	 * @return the catalogo
	 */
	public String getCatalogo() {
		return catalogo;
	}
	/**
	 * @param catalogo the catalogo to set
	 */
	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	/**
	 * @return the lote
	 */
	public String getLote() {
		return lote;
	}
	/**
	 * @param lote the lote to set
	 */
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	

}
