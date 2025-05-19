package com.proquifa.net.modelo.catalogos.proveedores;

public class ClasificacionConfiguracionPrecio {
	
	private Long idClasificacion;
	private Long idConfigFamilia;
	private Long idConfigClasificacion;
	private String concepto;
	private Long totalProductos;
	private Boolean eliminar;
	private Boolean restablecerCliente;
	private Boolean configuracionTemporal;
	
	private String tipo;
	private String subtipo;
	private String control;
	/**
	 * @return the idClasificacion
	 */
	public Long getIdClasificacion() {
		return idClasificacion;
	}
	/**
	 * @param idClasificacion the idClasificacion to set
	 */
	public void setIdClasificacion(Long idClasificacion) {
		this.idClasificacion = idClasificacion;
	}
	/**
	 * @return the idConfigFamilia
	 */
	public Long getIdConfigFamilia() {
		return idConfigFamilia;
	}
	/**
	 * @param idConfigFamilia the idConfigFamilia to set
	 */
	public void setIdConfigFamilia(Long idConfigFamilia) {
		this.idConfigFamilia = idConfigFamilia;
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
	 * @return the idConfigClasificacion
	 */
	public Long getIdConfigClasificacion() {
		return idConfigClasificacion;
	}
	/**
	 * @param idConfigClasificacion the idConfigClasificacion to set
	 */
	public void setIdConfigClasificacion(Long idConfigClasificacion) {
		this.idConfigClasificacion = idConfigClasificacion;
	}
	/**
	 * @return the totalProductos
	 */
	public Long getTotalProductos() {
		return totalProductos;
	}
	/**
	 * @param totalProductos the totalProductos to set
	 */
	public void setTotalProductos(Long totalProductos) {
		this.totalProductos = totalProductos;
	}
	/**
	 * @return the eliminar
	 */
	public Boolean getEliminar() {
		return eliminar;
	}
	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(Boolean eliminar) {
		this.eliminar = eliminar;
	}
	/**
	 * @return the restablecerCliente
	 */
	public Boolean getRestablecerCliente() {
		return restablecerCliente;
	}
	/**
	 * @param restablecerCliente the restablecerCliente to set
	 */
	public void setRestablecerCliente(Boolean restablecerCliente) {
		this.restablecerCliente = restablecerCliente;
	}
	public Boolean getConfiguracionTemporal() {
		return configuracionTemporal;
	}
	public void setConfiguracionTemporal(Boolean configuracionTemporal) {
		this.configuracionTemporal = configuracionTemporal;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getSubtipo() {
		return subtipo;
	}
	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}
	public String getControl() {
		return control;
	}
	public void setControl(String control) {
		this.control = control;
	}

}
