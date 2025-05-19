package com.proquifa.net.modelo.cobrosypagos.estadocuenta;

import java.util.Date;

public class MovimientosECuenta {
	
	private Date fecha;
	private long pkmovimientoECuenta;
	private String descripcion;
	private Double deposito;
	private Double retiro;
	private long fkestadoCuenta;
	private String banco;
	private String cuenta;
	private String sucursal;
	private String tipoCuenta;
	private boolean estado;
	/**
	 * @return the pkmovimientoECuenta
	 */
	public long getPkmovimientoECuenta() {
		return pkmovimientoECuenta;
	}
	/**
	 * @param pkmovimientoECuenta the pkmovimientoECuenta to set
	 */
	public void setPkmovimientoECuenta(long pkmovimientoECuenta) {
		this.pkmovimientoECuenta = pkmovimientoECuenta;
	}
	/**
	 * @return the estado
	 */
	public boolean isEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	/**
	 * @return the banco
	 */
	public String getBanco() {
		return banco;
	}
	/**
	 * @param banco the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}
	/**
	 * @return the cuenta
	 */
	public String getCuenta() {
		return cuenta;
	}
	/**
	 * @param cuenta the cuenta to set
	 */
	public void setCuenta(String cuenta) {
		this.cuenta = cuenta;
	}
	/**
	 * @return the sucursal
	 */
	public String getSucursal() {
		return sucursal;
	}
	/**
	 * @param sucursal the sucursal to set
	 */
	public void setSucursal(String sucursal) {
		this.sucursal = sucursal;
	}
	/**
	 * @return the tipoCuenta
	 */
	public String getTipoCuenta() {
		return tipoCuenta;
	}
	/**
	 * @param tipoCuenta the tipoCuenta to set
	 */
	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}
	/**
	 * @return the fkestadoCuenta
	 */
	public long getFkestadoCuenta() {
		return fkestadoCuenta;
	}
	/**
	 * @param fkestadoCuenta the fkestadoCuenta to set
	 */
	public void setFkestadoCuenta(long fkestadoCuenta) {
		this.fkestadoCuenta = fkestadoCuenta;
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
	 * @return the deposito
	 */
	public Double getDeposito() {
		return deposito;
	}
	/**
	 * @param deposito the deposito to set
	 */
	public void setDeposito(Double deposito) {
		this.deposito = deposito;
	}
	/**
	 * @return the retiro
	 */
	public Double getRetiro() {
		return retiro;
	}
	/**
	 * @param retiro the retiro to set
	 */
	public void setRetiro(Double retiro) {
		this.retiro = retiro;
	}

}
