package com.proquifa.net.modelo.tableros.cliente;



public class ProductosCliente {
	private Integer cant;
	private String descripcion;	
	private Long idProducto;
	private Double costo;
	private String fabrica;
	private String moneda;
	private String tipo;
	private String codigo;
	private Long totalClientes;
	private Long totalPartidas;
	private Long totalPedidos;
	private String esac;
	/**
	 * @return the cant
	 */
	public Integer getCant() {
		return cant;
	}
	/**
	 * @param cant the cant to set
	 */
	public void setCant(Integer cant) {
		this.cant = cant;
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
	 * @return the idProducto
	 */
	public Long getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	/**
	 * @return the costo
	 */
	public Double getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	/**
	 * @return the fabrica
	 */
	public String getFabrica() {
		return fabrica;
	}
	/**
	 * @param fabrica the fabrica to set
	 */
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public void setTotalClientes(Long totalClientes) {
		this.totalClientes = totalClientes;
	}
	public Long getTotalClientes() {
		return totalClientes;
	}
	public void setTotalPartidas(Long totalPartidas) {
		this.totalPartidas = totalPartidas;
	}
	public Long getTotalPartidas() {
		return totalPartidas;
	}
	public void setTotalPedidos(Long totalPedidos) {
		this.totalPedidos = totalPedidos;
	}
	public Long getTotalPedidos() {
		return totalPedidos;
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
