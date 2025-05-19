/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.cfdi;

/**
 * @author fmartinez
 *
 */
public class Totales {
	
	private String monedaFacturaElectronica;
	private String tipoDeCambioVenta;
	private Double subTotal;
	private Double total;
	private String totalConLetra;
	private String formaDePago;
	
	/**
	 * @return the monedaFacturaElectronica
	 */
	public String getMonedaFacturaElectronica() {
		return monedaFacturaElectronica;
	}
	/**
	 * @param monedaFacturaElectronica the monedaFacturaElectronica to set
	 */
	public void setMonedaFacturaElectronica(String monedaFacturaElectronica) {
		this.monedaFacturaElectronica = monedaFacturaElectronica;
	}
	/**
	 * @return the tipoDeCambioVenta
	 */
	public String getTipoDeCambioVenta() {
		return tipoDeCambioVenta;
	}
	/**
	 * @param tipoDeCambioVenta the tipoDeCambioVenta to set
	 */
	public void setTipoDeCambioVenta(String tipoDeCambioVenta) {
		this.tipoDeCambioVenta = tipoDeCambioVenta;
	}
	/**
	 * @return the subTotal
	 */
	public Double getSubTotal() {
		return subTotal;
	}
	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(Double subTotal) {
		this.subTotal = subTotal;
	}
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/**
	 * @return the totalConLetra
	 */
	public String getTotalConLetra() {
		return totalConLetra;
	}
	/**
	 * @param totalConLetra the totalConLetra to set
	 */
	public void setTotalConLetra(String totalConLetra) {
		this.totalConLetra = totalConLetra;
	}
	/**
	 * @return the formaDePago
	 */
	public String getFormaDePago() {
		return formaDePago;
	}
	/**
	 * @param formaDePago the formaDePago to set
	 */
	public void setFormaDePago(String formaDePago) {
		this.formaDePago = formaDePago;
	}
}