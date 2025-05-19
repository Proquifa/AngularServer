package com.proquifa.net.modelo.tableros.cliente;

public class MarcasCliente {
	private String nombre;
	private String etiqueta;
	private Long cantidad;		
	private Double montoCompra;
	private Double montoVenta;
	private Long totalPiezas;
	private Long totalClientes;
	private Integer totalPartidas;
	private Long totalPedidos;
	private String esac;
	/**
	 * @return the cantidad
	 */
	public Long getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the montoCompra
	 */
	public Double getMontoCompra() {
		return montoCompra;
	}
	/**
	 * @param montoCompra the montoCompra to set
	 */
	public void setMontoCompra(Double montoCompra) {
		this.montoCompra = montoCompra;
	}
	/**
	 * @return the montoVenta
	 */
	public Double getMontoVenta() {
		return montoVenta;
	}
	/**
	 * @param montoVenta the montoVenta to set
	 */
	public void setMontoVenta(Double montoVenta) {
		this.montoVenta = montoVenta;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getEtiqueta() {
		return etiqueta;
	}
	public void setTotalPiezas(Long totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	public Long getTotalPiezas() {
		return totalPiezas;
	}
	public void setTotalClientes(Long totalClientes) {
		this.totalClientes = totalClientes;
	}
	public Long getTotalClientes() {
		return totalClientes;
	}
	public void setTotalPedidos(Long totalPedidos) {
		this.totalPedidos = totalPedidos;
	}
	public Long getTotalPedidos() {
		return totalPedidos;
	}
	public void setTotalPartidas(Integer totalPartidas) {
		this.totalPartidas = totalPartidas;
	}
	public Integer getTotalPartidas() {
		return totalPartidas;
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
