package com.proquifa.net.modelo.despachos.mensajero;

import java.io.Serializable;
import java.util.Date;

public class Recorrido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2016192636742537249L;
	
	private Integer idRecorrido;
	private Double latitud;
	private Double longitud;
	private Date fechaHora;
	private String idRuta;
	private Integer idCliente;
	private Integer direccion;
	private String tipo;
	
	public Integer getIdRecorrido() {
		return idRecorrido;
	}
	
	public void setIdRecorrido(Integer idRecorrido) {
		this.idRecorrido = idRecorrido;
	}
	
	public Double getLatitud() {
		return latitud;
	}
	
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	
	public Double getLongitud() {
		return longitud;
	}
	
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	
	public Date getFechaHora() {
		return fechaHora;
	}
	
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	
	public String getIdRuta() {
		return idRuta;
	}
	
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	
	public Integer getIdCliente() {
		return idCliente;
	}
	
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public Integer getDireccion() {
		return direccion;
	}
	
	public void setDireccion(Integer direccion) {
		this.direccion = direccion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
