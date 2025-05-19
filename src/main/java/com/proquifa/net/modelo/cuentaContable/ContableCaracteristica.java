package com.proquifa.net.modelo.cuentaContable;

public class ContableCaracteristica {
	
	private Integer idContableCaracteristica;
	private String descripcion;
	private String tipo;
	private boolean activo;
	
	public Integer getIdContableCaracteristica() {
		return idContableCaracteristica;
	}
	public void setIdContableCaracteristica(Integer idContableCaracteristica) {
		this.idContableCaracteristica = idContableCaracteristica;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
