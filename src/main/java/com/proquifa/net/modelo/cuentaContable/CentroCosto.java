package com.proquifa.net.modelo.cuentaContable;

public class CentroCosto {
	private Integer idCentroCosto;
	private String tipo;
	private String descripcion;
	private boolean activo;
	
	public Integer getIdCentroCosto() {
		return idCentroCosto;
	}
	public void setIdCentroCosto(Integer idCentroCosto) {
		this.idCentroCosto = idCentroCosto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
}
