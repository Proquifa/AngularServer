package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Asofarma;

public class AddendaAsofarmaPartida {
	private String noPartida;
	private String ivaAcreditable;
	private String ivaDevengado;
	private String otros;
	
	public String getNoPartida() {
		return noPartida;
	}
	public void setNoPartida(String noPartida) {
		this.noPartida = noPartida;
	}
	
	public String getIvaAcreditable() {
		return ivaAcreditable;
	}
	
	public void setIvaAcreditable(String ivaAcreditable) {
		this.ivaAcreditable = ivaAcreditable;
	}
	
	public String getIvaDevengado() {
		return ivaDevengado;
	}
	
	public void setIvaDevengado(String ivaDevengado) {
		this.ivaDevengado = ivaDevengado;
	}
	
	public String getOtros() {
		return otros;
	}
	
	public void setOtros(String otros) {
		this.otros = otros;
	}

}
