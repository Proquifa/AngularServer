/**
 * 
 */
package com.proquifa.net.modelo.despachos;

/**
 * @author ymendez
 *
 */
public class ReceptorMaterial {

	private String etiqueta;
	private Integer totalGuias;
	private Integer totalClientes;
	private Integer totalFacturas;
	private Integer totalMensajeria;
	private String concepto;
	
	/**
	 * 
	 */
	public ReceptorMaterial() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the totalGuias
	 */
	public Integer getTotalGuias() {
		return totalGuias;
	}

	/**
	 * @param totalGuias the totalGuias to set
	 */
	public void setTotalGuias(Integer totalGuias) {
		this.totalGuias = totalGuias;
	}

	/**
	 * @return the totalClientes
	 */
	public Integer getTotalClientes() {
		return totalClientes;
	}

	/**
	 * @param totalClientes the totalClientes to set
	 */
	public void setTotalClientes(Integer totalClientes) {
		this.totalClientes = totalClientes;
	}

	/**
	 * @return the totalFacturas
	 */
	public Integer getTotalFacturas() {
		return totalFacturas;
	}

	/**
	 * @param totalFacturas the totalFacturas to set
	 */
	public void setTotalFacturas(Integer totalFacturas) {
		this.totalFacturas = totalFacturas;
	}

	/**
	 * @return the totalMensajeria
	 */
	public Integer getTotalMensajeria() {
		return totalMensajeria;
	}

	/**
	 * @param totalMensajeria the totalMensajeria to set
	 */
	public void setTotalMensajeria(Integer totalMensajeria) {
		this.totalMensajeria = totalMensajeria;
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
	
	
}
