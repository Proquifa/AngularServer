package com.proquifa.net.modelo.comun;

public class NominaCatalogo {
	private Integer idNominaCatalogo;
	private String codigoSAT;
	private String descripcion;
	private boolean activo;
	private String tipo;
	
	public Integer getIdNominaCatalogo() {
		return idNominaCatalogo;
	}
	public void setIdNominaCatalogo(Integer idNominaCatalogo) {
		this.idNominaCatalogo = idNominaCatalogo;
	}
	public String getCodigoSAT() {
		return codigoSAT;
	}
	public void setCodigoSAT(String codigoSAT) {
		this.codigoSAT = codigoSAT;
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
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
