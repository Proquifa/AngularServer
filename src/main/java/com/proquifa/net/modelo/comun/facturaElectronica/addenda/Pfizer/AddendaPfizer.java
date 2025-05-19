package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Pfizer;

import java.util.List;

public class AddendaPfizer {
	
	private String tipoAddenda;
	private String InstruccionesAdicionales;
	private List<AddendaPfizerLinea> lineas;
	
	public String getTipoAddenda() {
		return tipoAddenda;
	}
	
	public void setTipoAddenda(String tipoAddenda) {
		this.tipoAddenda = tipoAddenda;
	}
	
	public String getInstruccionesAdicionales() {
		return InstruccionesAdicionales;
	}
	
	public void setInstruccionesAdicionales(String instruccionesAdicionales) {
		InstruccionesAdicionales = instruccionesAdicionales;
	}
	
	public List<AddendaPfizerLinea> getLineas() {
		return lineas;
	}
	
	public void setLineas(List<AddendaPfizerLinea> lineas) {
		this.lineas = lineas;
	}
	
}
