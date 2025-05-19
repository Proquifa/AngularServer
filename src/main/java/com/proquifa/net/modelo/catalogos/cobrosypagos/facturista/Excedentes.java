package com.proquifa.net.modelo.catalogos.cobrosypagos.facturista;

import java.io.Serializable;

public class Excedentes implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7547408266549104079L;
	private Integer idExcedente;
	private Double monto;
	private String moneda;
	private String fecha;
	private Integer idCuentaBanco;
	private Integer idCliente;
	private String banco;
	private Integer noCuenta;
	private String monedaBanco;
	private Integer referenciaBancaria;
	private Double  monedaPesos;
	private Double monedaUSD;
	private String empresa;
	private Double tCambio;
	private Double eTCambio;
	private Double total;
	private Boolean modificado;
	private String fPor;
	private Long  noFactura;
	
	
	
	
	
	
	
	public Long getNoFactura() {
		return noFactura;
	}
	public void setNoFactura(Long noFactura) {
		this.noFactura = noFactura;
	}
	public String getfPor() {
		return fPor;
	}
	public void setfPor(String fPor) {
		this.fPor = fPor;
	}
	public Boolean getModificado() {
		return modificado;
	}
	public void setModificado(Boolean modificado) {
		this.modificado = modificado;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Double getMonedaUSD() {
		return monedaUSD;
	}
	public void setMonedaUSD(Double monedaUSD) {
		this.monedaUSD = monedaUSD;
	}
	public Double gettCambio() {
		return tCambio;
	}
	public void settCambio(Double tCambio) {
		this.tCambio = tCambio;
	}
	public Double geteTCambio() {
		return eTCambio;
	}
	public void seteTCambio(Double eTCambio) {
		this.eTCambio = eTCambio;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public Integer getReferenciaBancaria() {
		return referenciaBancaria;
	}
	public void setReferenciaBancaria(Integer referenciaBancaria) {
		this.referenciaBancaria = referenciaBancaria;
	}
	public Double getMonedaPesos() {
		return monedaPesos;
	}
	public void setMonedaPesos(Double monedaPesos) {
		this.monedaPesos = monedaPesos;
	}

	public Integer getIdExcedente() {
		return idExcedente;
	}
	public void setIdExcedente(Integer idExcedente) {
		this.idExcedente = idExcedente;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public Integer getIdCuentaBanco() {
		return idCuentaBanco;
	}
	public void setIdCuentaBanco(Integer idCuentaBanco) {
		this.idCuentaBanco = idCuentaBanco;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public Integer getNoCuenta() {
		return noCuenta;
	}
	public void setNoCuenta(Integer noCuenta) {
		this.noCuenta = noCuenta;
	}
	public String getMonedaBanco() {
		return monedaBanco;
	}
	public void setMonedaBanco(String monedaBanco) {
		this.monedaBanco = monedaBanco;
	}
	
	
	
}
