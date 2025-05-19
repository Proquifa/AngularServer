/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.cfdi;

/**
 * @author fmartinez
 *
 */
public class ReferenciasBancarias {
	
	private String moneda;
	private String banco;
	private String sucursal;
	private String cuenta;
	private String cuentaClabe;
	private String referenciaCliente;
	
	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
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
	 * @return the cuentaClabe
	 */
	public String getCuentaClabe() {
		return cuentaClabe;
	}
	/**
	 * @param cuentaClabe the cuentaClabe to set
	 */
	public void setCuentaClabe(String cuentaClabe) {
		this.cuentaClabe = cuentaClabe;
	}
	/**
	 * @return the referenciaCliente
	 */
	public String getReferenciaCliente() {
		return referenciaCliente;
	}
	/**
	 * @param referenciaCliente the referenciaCliente to set
	 */
	public void setReferenciaCliente(String referenciaCliente) {
		this.referenciaCliente = referenciaCliente;
	}
}