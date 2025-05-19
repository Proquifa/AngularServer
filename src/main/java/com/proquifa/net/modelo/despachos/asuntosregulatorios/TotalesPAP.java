/**
 * 
 */
package com.proquifa.net.modelo.despachos.asuntosregulatorios;

import java.io.Serializable;

/**
 * @author ymendez
 *
 */
public class TotalesPAP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6848984515766922753L;
	private String etiqueta;
	private String nombre;
	private Integer totalProductos;
	private Integer totalPiezas;
	private Double monto;
	
	/**
	 * 
	 */
	public TotalesPAP() {
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
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the totalProductos
	 */
	public Integer getTotalProductos() {
		return totalProductos;
	}

	/**
	 * @param totalProductos the totalProductos to set
	 */
	public void setTotalProductos(Integer totalProductos) {
		this.totalProductos = totalProductos;
	}

	/**
	 * @return the totalPiezas
	 */
	public Integer getTotalPiezas() {
		return totalPiezas;
	}

	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
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
	
	
}
