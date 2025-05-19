/**
 * 
 */
package com.proquifa.net.modelo.despachos.mensajero;

import java.io.Serializable;

/**
 * @author admin
 *
 */
public class TotalMensajero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 592416505010459344L;
	private Integer totalClientes;
	private Integer totalEventos;
	private Integer totalMensajeros;
	private String mensajero;
	private String zona;
	private String prioridad;
	private String cliente;
	private String evento;
	
	private Integer totalPiezas;
	private Double totalMonto;
	
	private Integer totalProveedores;
	
	/**
	 * 
	 */
	public TotalMensajero() {
		super();
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
	 * @return the totalEventos
	 */
	public Integer getTotalEventos() {
		return totalEventos;
	}

	/**
	 * @param totalEventos the totalEventos to set
	 */
	public void setTotalEventos(Integer totalEventos) {
		this.totalEventos = totalEventos;
	}

	/**
	 * @return the totalMensajeros
	 */
	public Integer getTotalMensajeros() {
		return totalMensajeros;
	}

	/**
	 * @param totalMensajeros the totalMensajeros to set
	 */
	public void setTotalMensajeros(Integer totalMensajeros) {
		this.totalMensajeros = totalMensajeros;
	}

	/**
	 * @return the mensajero
	 */
	public String getMensajero() {
		return mensajero;
	}

	/**
	 * @param mensajero the mensajero to set
	 */
	public void setMensajero(String mensajero) {
		this.mensajero = mensajero;
	}

	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}

	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * @return the prioridad
	 */
	public String getPrioridad() {
		return prioridad;
	}

	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
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
	 * @return the totalMonto
	 */
	public Double getTotalMonto() {
		return totalMonto;
	}

	/**
	 * @param totalMonto the totalMonto to set
	 */
	public void setTotalMonto(Double totalMonto) {
		this.totalMonto = totalMonto;
	}

	/**
	 * @return the totalProveedores
	 */
	public Integer getTotalProveedores() {
		return totalProveedores;
	}

	/**
	 * @param totalProveedores the totalProveedores to set
	 */
	public void setTotalProveedores(Integer totalProveedores) {
		this.totalProveedores = totalProveedores;
	}
	
}
