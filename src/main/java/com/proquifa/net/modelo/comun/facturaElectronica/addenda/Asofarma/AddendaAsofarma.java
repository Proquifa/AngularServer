package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Asofarma;

import java.util.List;

public class AddendaAsofarma {
	private String tipoProveedor;
	private String noProveedor;
	private String serie;
	private String folio;
	private String ordenCompra;
	private List<AddendaAsofarmaPartida> partidas;
	
	public String getTipoProveedor() {
		return tipoProveedor;
	}
	
	public void setTipoProveedor(String tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}
	
	public String getNoProveedor() {
		return noProveedor;
	}
	
	public void setNoProveedor(String noProveedor) {
		this.noProveedor = noProveedor;
	}
	
	public String getSerie() {
		return serie;
	}
	
	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getOrdenCompra() {
		return ordenCompra;
	}
	
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public List<AddendaAsofarmaPartida> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<AddendaAsofarmaPartida> partidas) {
		this.partidas = partidas;
	}
	
}
