package com.proquifa.net.modelo.consultas.comun;

import java.util.Date;

public class ParametrosBusquedaCobros {
	private Date fechaInicio;
	private Date fechaFin; 
	private Long idCliente;
	private String medioPago;
	private String fpor;
	private String estado;
	private String cpago;
	private int drc;
	private String factura;
	private Long idUsuarioLogueado;
	private Long cobrador;
	private String cuenta;
	private String banco;
	private String uuid;
	private Date fechaCobro;
	private Boolean busquedaCR;
	private Date fechaUEntrega;
	private Boolean individual;
	private String cpedido;
	private String SC;
	
	public String getSC() {
		return SC;
	}

	public void setSC(String sC) {
		SC = sC;
	}

	public String getCpedido() {
		return cpedido;
	}

	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getFpor() {
		return fpor;
	}
	public void setFpor(String fpor) {
		this.fpor = fpor;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCpago() {
		return cpago;
	}
	public void setCpago(String cpago) {
		this.cpago = cpago;
	}
	public int getDrc() {
		return drc;
	}
	public void setDrc(int drc) {
		this.drc = drc;
	}
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	public Long getIdUsuarioLogueado() {
		return idUsuarioLogueado;
	}
	public void setIdUsuarioLogueado(Long idUsuarioLogueado) {
		this.idUsuarioLogueado = idUsuarioLogueado;
	}
	public Long getCobrador() {
		return cobrador;
	}
	public void setCobrador(Long cobrador) {
		this.cobrador = cobrador;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getCuenta() {
		return cuenta;
	}
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	public Date getFechaCobro() {
		return fechaCobro;
	}
	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}
	public Boolean getBusquedaCR() {
		return busquedaCR;
	}
	public void setBusquedaCR(Boolean busquedaCR) {
		this.busquedaCR = busquedaCR;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Boolean getIndividual() {
		return individual;
	}

	public void setIndividual(Boolean individual) {
		this.individual = individual;
	}

	public Date getFechaUEntrega() {
		return fechaUEntrega;
	}

	public void setFechaUEntrega(Date fechaUEntrega) {
		this.fechaUEntrega = fechaUEntrega;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
