package com.proquifa.net.modelo.comun;

import java.util.Date;



public class Fabricante {
	private Long idFabricante;
	private String nombre;
	private Long productosEstandares;
	private Long productosReactivos;
	private Long productosMedicamentos;
	private Long productosLabware;
	private Long productosPublicaciones;
	private Boolean asociado;
	private Boolean relacionado;
	private Date fechaUA;
	private byte [] bytes; 
	private String paisManufactura;
	private String paisCompra;
	private String logoExt;
	private Boolean habilitado;
	private String razonSocial;
	private String taxID;
	private String direccion;
	
	
	public Long getIdFabricante() {
		return idFabricante;
	}
	public void setIdFabricante(Long idFabricante) {
		this.idFabricante = idFabricante;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the productosEstandares
	 */
	public Long getProductosEstandares() {
		return productosEstandares;
	}
	/**
	 * @param productosEstandares the productosEstandares to set
	 */
	public void setProductosEstandares(Long productosEstandares) {
		this.productosEstandares = productosEstandares;
	}
	/**
	 * @return the productosReactivos
	 */
	public Long getProductosReactivos() {
		return productosReactivos;
	}
	/**
	 * @param productosReactivos the productosReactivos to set
	 */
	public void setProductosReactivos(Long productosReactivos) {
		this.productosReactivos = productosReactivos;
	}
	/**
	 * @return the productosMedicamentos
	 */
	public Long getProductosMedicamentos() {
		return productosMedicamentos;
	}
	/**
	 * @param productosMedicamentos the productosMedicamentos to set
	 */
	public void setProductosMedicamentos(Long productosMedicamentos) {
		this.productosMedicamentos = productosMedicamentos;
	}
	/**
	 * @return the productosLabware
	 */
	public Long getProductosLabware() {
		return productosLabware;
	}
	/**
	 * @param productosLabware the productosLabware to set
	 */
	public void setProductosLabware(Long productosLabware) {
		this.productosLabware = productosLabware;
	}
	/**
	 * @return the productosPublicaciones
	 */
	public Long getProductosPublicaciones() {
		return productosPublicaciones;
	}
	/**
	 * @param productosPublicaciones the productosPublicaciones to set
	 */
	public void setProductosPublicaciones(Long productosPublicaciones) {
		this.productosPublicaciones = productosPublicaciones;
	}
	/**
	 * @return the asociado
	 */
	public Boolean getAsociado() {
		return asociado;
	}
	/**
	 * @param asociado the asociado to set
	 */
	public void setAsociado(Boolean asociado) {
		this.asociado = asociado;
	}
	/**
	 * @return the relacionado
	 */
	public Boolean getRelacionado() {
		return relacionado;
	}
	/**
	 * @param relacionado the relacionado to set
	 */
	public void setRelacionado(Boolean relacionado) {
		this.relacionado = relacionado;
	}
	/**
	 * @return the fechaUA
	 */
	public Date getFechaUA() {
		return fechaUA;
	}
	/**
	 * @param fechaUA the fechaUA to set
	 */
	public void setFechaUA(Date fechaUA) {
		this.fechaUA = fechaUA;
	}
	/**
	 * @return the bytes
	 */
	public byte[] getBytes() {
		return bytes;
	}
	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}
	/**
	 * @return the paisManofactura
	 */
	public String getPaisManufactura() {
		return paisManufactura;
	}
	/**
	 * @param paisManofactura the paisManofactura to set
	 */
	public void setPaisManufactura(String paisManofactura) {
		this.paisManufactura = paisManofactura;
	}
	/**
	 * @return the paisCompra
	 */
	public String getPaisCompra() {
		return paisCompra;
	}
	/**
	 * @param paisCompra the paisCompra to set
	 */
	public void setPaisCompra(String paisCompra) {
		this.paisCompra = paisCompra;
	}
	/**
	 * @return the logoExt
	 */
	public String getLogoExt() {
		return logoExt;
	}
	/**
	 * @param logoExt the logoExt to set
	 */
	public void setLogoExt(String logoExt) {
		this.logoExt = logoExt;
	}
	/**
	 * @return the habilitado
	 */
	public Boolean getHabilitado() {
		return habilitado;
	}
	/**
	 * @param habilidado the habilitado to set
	 */
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getTaxID() {
		return taxID;
	}
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
}
