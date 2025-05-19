package com.proquifa.net.modelo.ventas.reportes.seguimientos;

import java.util.Date;
import java.util.List;


public class PartidaCotizacionEnSeguimiento {
	private Number cantidad;
	private String concepto;
	private String estado;
	private String folioCotizacion;
	private List<HistorialPartidaEnSeguimiento> historial;
	private Long idPCotiza;
	private Number monto;
	private Number partida;
	private Number precio;
	private String monedaCotiza;
	private Date fechaSiguiente;
	private String tipo;
	private String control;
	private String marca;
	private String situacion;
	
	/**
	 * @return the cantidad
	 */
	public Number getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Number cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
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
	 * @return the folioCotizacion
	 */
	public String getFolioCotizacion() {
		return folioCotizacion;
	}
	/**
	 * @param folioCotizacion the folioCotizacion to set
	 */
	public void setFolioCotizacion(String folioCotizacion) {
		this.folioCotizacion = folioCotizacion;
	}
	/**
	 * @return the historial
	 */
	public List<HistorialPartidaEnSeguimiento> getHistorial() {
		return historial;
	}
	/**
	 * @param historial the historial to set
	 */
	public void setHistorial(List<HistorialPartidaEnSeguimiento> historial) {
		this.historial = historial;
	}
	/**
	 * @return the idPCotiza
	 */
	public Long getIdPCotiza() {
		return idPCotiza;
	}
	/**
	 * @param idPCotiza the idPCotiza to set
	 */
	public void setIdPCotiza(Long idPCotiza) {
		this.idPCotiza = idPCotiza;
	}
	/**
	 * @return the monto
	 */
	public Number getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Number monto) {
		this.monto = monto;
	}
	/**
	 * @return the partida
	 */
	public Number getPartida() {
		return partida;
	}
	/**
	 * @param partida the partida to set
	 */
	public void setPartida(Number partida) {
		this.partida = partida;
	}
	/**
	 * @return the precio
	 */
	public Number getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Number precio) {
		this.precio = precio;
	}
	/**
	 * @return the monedaCotiza
	 */
	public String getMonedaCotiza() {
		return monedaCotiza;
	}
	/**
	 * @param monedaCotiza the monedaCotiza to set
	 */
	public void setMonedaCotiza(String monedaCotiza) {
		this.monedaCotiza = monedaCotiza;
	}
	/**
	 * @return the fechaSiguiente
	 */
	public Date getFechaSiguiente() {
		return fechaSiguiente;
	}
	/**
	 * @param fechaSiguiente the fechaSiguiente to set
	 */
	public void setFechaSiguiente(Date fechaSiguiente) {
		this.fechaSiguiente = fechaSiguiente;
	}
	public void setControl(String control) {
		this.control = control;
	}
	public String getControl() {
		return control;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getMarca() {
		return marca;
	}
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	public String getSituacion() {
		return situacion;
	}
}