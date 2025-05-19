package com.proquifa.net.modelo.comun;

import java.util.Date;

public class ObjetivoCrecimiento {
	
	private Long idObjetivoCrecimiento;
	private String nivelIngreso;
	private String descripcion;
	private String tipoProveedor;
	private int mes;
	private Date fua;

	/*
	 * Atributos Generales
	 */
	private Double monto; //Deseado
	private Double montoFundamental;
	private Double porcentaje; //Deseado
	private Double porcentajeFundamental;
	
	/*
	 * Atributos Anuales
	 */
	private Double montoAnual; //Deseado
	private Double montoAnualFundamental;
	private Double porcentajeAnual; //Deseado
	private Double porcentajeAnualDeseado;
	private Double porcentajeAnualFundamental;
	

	private Long noClientes; 
	private Long noProveedor; 
	private Double valorBajo;
	
	
	
	/**
	 * @return the idObjetivoCrecimiento
	 */
	public Long getIdObjetivoCrecimiento() {
		return idObjetivoCrecimiento;
	}
	/**
	 * @param idObjetivoCrecimiento the idObjetivoCrecimiento to set
	 */
	public void setIdObjetivoCrecimiento(Long idObjetivoCrecimiento) {
		this.idObjetivoCrecimiento = idObjetivoCrecimiento;
	}
	/**
	 * @return the nivelIngreso
	 */
	public String getNivelIngreso() {
		return nivelIngreso;
	}
	/**
	 * @param nivelIngreso the nivelIngreso to set
	 */
	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the tipoProveedor
	 */
	public String getTipoProveedor() {
		return tipoProveedor;
	}
	/**
	 * @param tipoProveedor the tipoProveedor to set
	 */
	public void setTipoProveedor(String tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}
	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}
	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}
	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}
	/**
	 * @return the porcentajeAnual
	 */
	public Double getPorcentajeAnual() {
		return porcentajeAnual;
	}
	/**
	 * @param porcentajeAnual the porcentajeAnual to set
	 */
	public void setPorcentajeAnual(Double porcentajeAnual) {
		this.porcentajeAnual = porcentajeAnual;
	}
	/**
	 * @return the montoAnual
	 */
	public Double getMontoAnual() {
		return montoAnual;
	}
	/**
	 * @param montoAnual the montoAnual to set
	 */
	public void setMontoAnual(Double montoAnual) {
		this.montoAnual = montoAnual;
	}
	/**
	 * @return the porcentajeAnualFundamental
	 */
	public Double getPorcentajeAnualFundamental() {
		return porcentajeAnualFundamental;
	}
	/**
	 * @param porcentajeAnualFundamental the porcentajeAnualFundamental to set
	 */
	public void setPorcentajeAnualFundamental(Double porcentajeAnualFundamental) {
		this.porcentajeAnualFundamental = porcentajeAnualFundamental;
	}
	/**
	 * @return the porcentajeAnualDeseado
	 */
	public Double getPorcentajeAnualDeseado() {
		return porcentajeAnualDeseado;
	}
	/**
	 * @param porcentajeAnualDeseado the porcentajeAnualDeseado to set
	 */
	public void setPorcentajeAnualDeseado(Double porcentajeAnualDeseado) {
		this.porcentajeAnualDeseado = porcentajeAnualDeseado;
	}
	/**
	 * @return the montoAnualFundamental
	 */
	public Double getMontoAnualFundamental() {
		return montoAnualFundamental;
	}
	/**
	 * @param montoAnualFundamental the montoAnualFundamental to set
	 */
	public void setMontoAnualFundamental(Double montoAnualFundamental) {
		this.montoAnualFundamental = montoAnualFundamental;
	}
	/**
	 * @return the montoFundamental
	 */
	public Double getMontoFundamental() {
		return montoFundamental;
	}
	/**
	 * @param montoFundamental the montoFundamental to set
	 */
	public void setMontoFundamental(Double montoFundamental) {
		this.montoFundamental = montoFundamental;
	}
	/**
	 * @return the porcentajeFundamental
	 */
	public Double getPorcentajeFundamental() {
		return porcentajeFundamental;
	}
	/**
	 * @param porcentajeFundamental the porcentajeFundamental to set
	 */
	public void setPorcentajeFundamental(Double porcentajeFundamental) {
		this.porcentajeFundamental = porcentajeFundamental;
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
	 * @return the porcentaje
	 */
	public Double getPorcentaje() {
		return porcentaje;
	}
	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}
	/**
	 * @return the noClientes
	 */
	public Long getNoClientes() {
		return noClientes;
	}
	/**
	 * @param noClientes the noClientes to set
	 */
	public void setNoClientes(Long noClientes) {
		this.noClientes = noClientes;
	}
	/**
	 * @return the noProveedor
	 */
	public Long getNoProveedor() {
		return noProveedor;
	}
	/**
	 * @param noProveedor the noProveedor to set
	 */
	public void setNoProveedor(Long noProveedor) {
		this.noProveedor = noProveedor;
	}
	/**
	 * @return the valorBajo
	 */
	public Double getValorBajo() {
		return valorBajo;
	}
	/**
	 * @param valorBajo the valorBajo to set
	 */
	public void setValorBajo(Double valorBajo) {
		this.valorBajo = valorBajo;
	}

	
		
}
