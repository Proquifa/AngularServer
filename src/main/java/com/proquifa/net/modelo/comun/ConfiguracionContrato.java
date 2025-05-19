package com.proquifa.net.modelo.comun;

import java.util.List;

public class ConfiguracionContrato {
	
	
	private String marca;
	private String tipo;
	private String subtipo;
	private String control;
	private String periodo;
	private List<Producto> listaProductos;
	private String tipoConfiguracion;
	private Long idConfProducto;
	private Long idMarca;
	private String industria;
	
	
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
	
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public String getTipoConfiguracion() {
		return tipoConfiguracion;
	}
	public void setTipoConfiguracion(String tipoConfiguracion) {
		this.tipoConfiguracion = tipoConfiguracion;
	}
	public Long getIdConfProducto() {
		return idConfProducto;
	}
	public void setIdConfProducto(Long idConfProducto) {
		this.idConfProducto = idConfProducto;
	}
	public Long getIdMarca() {
		return idMarca;
	}
	public void setIdMarca(Long idMarca) {
		this.idMarca = idMarca;
	}
	public List<Producto> getListaProductos() {
		return listaProductos;
	}
	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	public String getIndustria() {
		return industria;
	}
	public void setIndustria(String industria) {
		this.industria = industria;
	}


}
