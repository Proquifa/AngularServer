/**
 * 
 */
package com.proquifa.net.modelo.tableros.cliente;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class EntregasCliente {
	private String cpedido;
	private String concepto;
	private Date fee;
	private Date fr;
	private String situacion;
	private String enTiempo;
	private int mes;
	private Long factura;
	private String fPor;
	private String estado;
	private Integer piezas;
	private Double montoTotal;
	private String esac;
	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}
	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the fee
	 */
	public Date getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(Date fee) {
		this.fee = fee;
	}
	/**
	 * @return the fr
	 */
	public Date getFr() {
		return fr;
	}
	/**
	 * @param fr the fr to set
	 */
	public void setFr(Date fr) {
		this.fr = fr;
	}
	/**
	 * @return the situacion
	 */
	public String getSituacion() {
		return situacion;
	}
	/**
	 * @param situacion the situacion to set
	 */
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	/**
	 * @return the enTiempo
	 */
	public String getEnTiempo() {
		return enTiempo;
	}
	/**
	 * @param enTiempo the enTiempo to set
	 */
	public void setEnTiempo(String enTiempo) {
		this.enTiempo = enTiempo;
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
	 * @param factura the factura to set
	 */
	public void setFactura(Long factura) {
		this.factura = factura;
	}
	/**
	 * @return the factura
	 */
	public Long getFactura() {
		return factura;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param fPor the fPor to set
	 */
	public void setFPor(String fPor) {
		this.fPor = fPor;
	}
	/**
	 * @return the fPor
	 */
	public String getFPor() {
		return fPor;
	}
	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}
	/**
	 * @return the piezas
	 */
	public Integer getPiezas() {
		return piezas;
	}
	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	/**
	 * @return the montoTotal
	 */
	public Double getMontoTotal() {
		return montoTotal;
	}
	/**
	 * @return the esac
	 */
	public String getEsac() {
		return esac;
	}
	/**
	 * @param esac the esac to set
	 */
	public void setEsac(String esac) {
		this.esac = esac;
	}
}
