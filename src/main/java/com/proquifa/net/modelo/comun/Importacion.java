package com.proquifa.net.modelo.comun;

public class Importacion {
	
	Integer idEmpresa;
	Boolean vende;
	Boolean compra;
	Boolean importa;
	Boolean exporta;
	//Boolean padroteImportador;
	Boolean padronImportacion;
	
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Boolean getImporta() {
		return importa;
	}
	public void setImporta(Boolean importa) {
		this.importa = importa;
	}
	public Boolean getExporta() {
		return exporta;
	}
	public void setExporta(Boolean exporta) {
		this.exporta = exporta;
	}
	public Boolean getPadronImportacion() {
		return padronImportacion;
	}
	public void setPadronImportacion(Boolean padronImportacion) {
		this.padronImportacion = padronImportacion;
	}
	public Boolean getVende() {
		return vende;
	}
	public void setVende(Boolean vende) {
		this.vende = vende;
	}
	public Boolean getCompra() {
		return compra;
	}
	public void setCompra(Boolean compra) {
		this.compra = compra;
	}
	
}
