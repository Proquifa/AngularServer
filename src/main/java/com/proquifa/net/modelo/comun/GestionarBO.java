/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;

/**
 * @author ymendez
 *
 */
public class GestionarBO {

	private Integer idProveedor;
	private String proveedor;
	private Integer totalProductos;
	private Integer totalControlados;
	private Integer totalNoControlados;
	
	private String control;
	private String etiqueta;
	
	private String codigo;
	private String descripcion;
	private String unidad;
	private String cantidad;
	private String presentacion;
	private String tipo;
	private String subTipo;
	private Date fechaInicio;
	private String finicio;
	
	private Integer idProducto;
	private Integer idProductoBO;
	
	/**
	 * 
	 */
	public GestionarBO() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idProveedor
	 */
	public Integer getIdProveedor() {
		return idProveedor;
	}

	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
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
	 * @return the totalControlados
	 */
	public Integer getTotalControlados() {
		return totalControlados;
	}

	/**
	 * @param totalControlados the totalControlados to set
	 */
	public void setTotalControlados(Integer totalControlados) {
		this.totalControlados = totalControlados;
	}

	/**
	 * @return the proveedor
	 */
	public String getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the totalNoControlados
	 */
	public Integer getTotalNoControlados() {
		return totalNoControlados;
	}

	/**
	 * @param totalNoControlados the totalNoControlados to set
	 */
	public void setTotalNoControlados(Integer totalNoControlados) {
		this.totalNoControlados = totalNoControlados;
	}

	/**
	 * @return the control
	 */
	public String getControl() {
		return control;
	}

	/**
	 * @param control the control to set
	 */
	public void setControl(String control) {
		this.control = control;
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
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}

	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	/**
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}

	/**
	 * @param presentacion the presentacion to set
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
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
	 * @return the subTipo
	 */
	public String getSubTipo() {
		return subTipo;
	}

	/**
	 * @param subTipo the subTipo to set
	 */
	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}

	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the finicio
	 */
	public String getFinicio() {
		return finicio;
	}

	/**
	 * @param finicio the finicio to set
	 */
	public void setFinicio(String finicio) {
		this.finicio = finicio;
	}

	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * @return the idProductoBO
	 */
	public Integer getIdProductoBO() {
		return idProductoBO;
	}

	/**
	 * @param idProductoBO the idProductoBO to set
	 */
	public void setIdProductoBO(Integer idProductoBO) {
		this.idProductoBO = idProductoBO;
	}
	
	
}
