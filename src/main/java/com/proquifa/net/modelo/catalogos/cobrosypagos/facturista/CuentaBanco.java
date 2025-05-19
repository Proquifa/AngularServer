package com.proquifa.net.modelo.catalogos.cobrosypagos.facturista;

import java.io.Serializable;

public class CuentaBanco implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 939242937903803415L;
	private Long idCuenta;
	private String banco;
	private String moneda;
	private String noCuenta;
	private String clabe;
	private Integer sucursal;
	private String beneficiario;
	private Integer Activo;
	private Long idEmpresa;
	private Long rfcBanco;
	
	
	public Long getIdCuenta() {
		return idCuenta;
	}
	public void setIdCuenta(Long idCuenta) {
		this.idCuenta = idCuenta;
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
	public String getNoCuenta() {
		return noCuenta;
	}
	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
	}
	public String getClabe() {
		return clabe;
	}
	public void setClabe(String clabe) {
		this.clabe = clabe;
	}
	public Integer getSucursal() {
		return sucursal;
	}
	public void setSucursal(Integer sucursal) {
		this.sucursal = sucursal;
	}
	public String getBeneficiario() {
		return beneficiario;
	}
	public void setBeneficiario(String beneficiario) {
		this.beneficiario = beneficiario;
	}
	public Integer getActivo() {
		return Activo;
	}
	public void setActivo(Integer activo) {
		Activo = activo;
	}
	public Long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Long getRfcBanco() {
		return rfcBanco;
	}
	public void setRfcBanco(Long rfcBanco) {
		this.rfcBanco = rfcBanco;
	}
	
	
	
	
	
	
}
