package com.proquifa.net.modelo.finanzas;

import java.io.Serializable;
import java.util.Date;

public class Deposito implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6367444093973349297L;
	private int idDeposito;
	private Date fecha;
	private String referenciaBancaria;
	private String cuentaOrigen;
	private Date fechaPago;
	private String formaPago;
	private Double monto;
	private Boolean activo;
	private Boolean complemento;
	private Boolean completa;
	private String correo;
	private String correoCC;
	private String comentarios;
	private int idCliente;
	private int idCuentaBanco;
	
	/**
	 * @return the idDeposito
	 */
	public int getIdDeposito() {
		return idDeposito;
	}
	
	/**
	 * @param idDeposito the idDeposito to set
	 */
	public void setIdDeposito(int idDeposito) {
		this.idDeposito = idDeposito;
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
	 * @return the referenciaBancaria
	 */
	public String getReferenciaBancaria() {
		return referenciaBancaria;
	}
	
	/**
	 * @param referenciaBancaria the referenciaBancaria to set
	 */
	public void setReferenciaBancaria(String referenciaBancaria) {
		this.referenciaBancaria = referenciaBancaria;
	}
	
	/**
	 * @return the cuentaOrigen
	 */
	public String getCuentaOrigen() {
		return cuentaOrigen;
	}
	
	/**
	 * @param cuentaOrigen the cuentaOrigen to set
	 */
	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}
	
	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}
	
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}
	
	/**
	 * @return the formaPago
	 */
	public String getFormaPago() {
		return formaPago;
	}
	
	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}
	
	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}
	
	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
	/**
	 * @return the complemento
	 */
	public Boolean getComplemento() {
		return complemento;
	}
	
	/**
	 * @param complemento the complemento to set
	 */
	public void setComplemento(Boolean complemento) {
		this.complemento = complemento;
	}
	
	/**
	 * @return the completa
	 */
	public Boolean getCompleta() {
		return completa;
	}
	
	/**
	 * @param completa the completa to set
	 */
	public void setCompleta(Boolean completa) {
		this.completa = completa;
	}
	
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	
	/**
	 * @return the correoCC
	 */
	public String getCorreoCC() {
		return correoCC;
	}
	
	/**
	 * @param correoCC the correoCC to set
	 */
	public void setCorreoCC(String correoCC) {
		this.correoCC = correoCC;
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
	 * @return the idCliente
	 */
	public int getIdCliente() {
		return idCliente;
	}
	
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	
	/**
	 * @return the idCuentaBanco
	 */
	public int getIdCuentaBanco() {
		return idCuentaBanco;
	}
	
	/**
	 * @param idCuentaBanco the idCuentaBanco to set
	 */
	public void setIdCuentaBanco(int idCuentaBanco) {
		this.idCuentaBanco = idCuentaBanco;
	}	

}
