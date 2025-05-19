package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier;

public class AddendaDarierDocumento {
	private String version;
	private AddendaDarierIdentificacion identificacion;
	private AddendaDarierCondicionesDePago condicionesDePago;
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public AddendaDarierIdentificacion getIdentificacion() {
		return identificacion;
	}
	
	public void setIdentificacion(AddendaDarierIdentificacion identificacion) {
		this.identificacion = identificacion;
	}

	public AddendaDarierCondicionesDePago getCondicionesDePago() {
		return condicionesDePago;
	}

	public void setCondicionesDePago(AddendaDarierCondicionesDePago condicionesDePago) {
		this.condicionesDePago = condicionesDePago;
	}

}
