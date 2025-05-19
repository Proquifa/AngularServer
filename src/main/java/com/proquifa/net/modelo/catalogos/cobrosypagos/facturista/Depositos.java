package com.proquifa.net.modelo.catalogos.cobrosypagos.facturista;

import java.io.Serializable;

public class Depositos implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2553498996722091812L;
	private Long idDeposito;
	private String fecha;
	private Integer referenciaBanco;
	private String cuentaOrigen;
	private String fechaPago;
	private String formaPago;
	private Double Monto;
	private Double montoActual;
	private Integer Activo;
	private Integer complemeneto;
	private String completa;
	private String correo;
	private String correoCC;
	private String comnetarios;
	private Long idCliente;
	private Long idCuentaBanco;
	
	private String banco;
	private String moneda;
	private String monedaBanco;
	private String fPor;
	private Long RFCBanco;
	private Double Tcambio;
	private Long  noCuenta;
	private String fechaIn;
	
	
	
	
	
	
	public String getFechaIn() {
		return fechaIn;
	}
	public void setFechaIn(String fechaIn) {
		this.fechaIn = fechaIn;
	}
	public Long getNoCuenta() {
		return noCuenta;
	}
	public void setNoCuenta(Long noCuenta) {
		this.noCuenta = noCuenta;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Long getIdCuentaBanco() {
		return idCuentaBanco;
	}
	public void setIdCuentaBanco(Long idCuentaBanco) {
		this.idCuentaBanco = idCuentaBanco;
	}
	public Long getIdDeposito() {
		return idDeposito;
	}
	public void setIdDeposito(Long idDeposito) {
		this.idDeposito = idDeposito;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Integer getReferenciaBanco() {
		return referenciaBanco;
	}
	public void setReferenciaBanco(Integer referenciaBanco) {
		this.referenciaBanco = referenciaBanco;
	}
	public String getCuentaOrigen() {
		return cuentaOrigen;
	}
	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	public String getFechaPago() {
		return fechaPago;
	}
	public void setFechaPago(String fechaPago) {
		this.fechaPago = fechaPago;
	}
	public String getFormaPago() {
		return formaPago;
	}
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	public Double getMonto() {
		return Monto;
	}
	public void setMonto(Double monto) {
		Monto = monto;
	}
	public Double getMontoActual() {
		return montoActual;
	}
	public void setMontoActual(Double montoActual) {
		this.montoActual = montoActual;
	}
	public Integer getActivo() {
		return Activo;
	}
	public void setActivo(Integer activo) {
		Activo = activo;
	}
	public Integer getComplemeneto() {
		return complemeneto;
	}
	public void setComplemeneto(Integer complemeneto) {
		this.complemeneto = complemeneto;
	}
	public String getCompleta() {
		return completa;
	}
	public void setCompleta(String completa) {
		this.completa = completa;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCorreoCC() {
		return correoCC;
	}
	public void setCorreoCC(String correoCC) {
		this.correoCC = correoCC;
	}
	public String getComnetarios() {
		return comnetarios;
	}
	public void setComnetarios(String comnetarios) {
		this.comnetarios = comnetarios;
	}

	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getMonedaBanco() {
		return monedaBanco;
	}
	public void setMonedaBanco(String monedaBanco) {
		this.monedaBanco = monedaBanco;
	}
	public String getfPor() {
		return fPor;
	}
	public void setfPor(String fPor) {
		this.fPor = fPor;
	}
	public Long getRFCBanco() {
		return RFCBanco;
	}
	public void setRFCBanco(Long rFCBanco) {
		RFCBanco = rFCBanco;
	}
	public Double getTcambio() {
		return Tcambio;
	}
	public void setTcambio(Double tcambio) {
		Tcambio = tcambio;
	}
	
	
	
	

}
