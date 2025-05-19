package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier;

public class AddendaDarierIdentificacion {
	private String tipoDeOrdenDeCompra;
	private String numeroDeOrdenDeCompra;
	private String fechaDeOrdenDeCompra;
	private String numeroContrato;
	private AddendaDarierEmisor emisor;
	private AddendaDarierReceptor receptor;
	private String skind;
	
	public String getTipoDeOrdenDeCompra() {
		return tipoDeOrdenDeCompra;
	}
	
	public void setTipoDeOrdenDeCompra(String tipoDeOrdenDeCompra) {
		this.tipoDeOrdenDeCompra = tipoDeOrdenDeCompra;
	}
	
	public String getNumeroDeOrdenDeCompra() {
		return numeroDeOrdenDeCompra;
	}
	
	public void setNumeroDeOrdenDeCompra(String numeroDeOrdenDeCompra) {
		this.numeroDeOrdenDeCompra = numeroDeOrdenDeCompra;
	}
	
	public String getFechaDeOrdenDeCompra() {
		return fechaDeOrdenDeCompra;
	}
	
	public void setFechaDeOrdenDeCompra(String fechaDeOrdenDeCompra) {
		this.fechaDeOrdenDeCompra = fechaDeOrdenDeCompra;
	}
	
	public AddendaDarierEmisor getEmisor() {
		return emisor;
	}
	
	public void setEmisor(AddendaDarierEmisor emisor) {
		this.emisor = emisor;
	}
	
	public AddendaDarierReceptor getReceptor() {
		return receptor;
	}
	
	public void setReceptor(AddendaDarierReceptor receptor) {
		this.receptor = receptor;
	}
	
	public String getSkind() {
		return skind;
	}
	
	public void setSkind(String skind) {
		this.skind = skind;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}
	
	
	
}
