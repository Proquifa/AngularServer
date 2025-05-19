package com.proquifa.net.modelo.despachos;

import java.util.Date;

public class OrdenEntrega {

	private String folio;
	private Long idOrdenEntrega;
	private Date fecha;
	private Long tiempoEmbalaje;
	private String responsableEmbalaje;
	private Long idCliente;
	private String folio_packingList;
	private String zona;
	private String ruta;
	
	
	//SuperClass
	public OrdenEntrega() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getFolio() {
		return folio;
	}


	public void setFolio(String folio) {
		this.folio = folio;
	}


	public Long getIdOrdenEntrega() {
		return idOrdenEntrega;
	}


	public void setIdOrdenEntrega(Long idOrdenEntrega) {
		this.idOrdenEntrega = idOrdenEntrega;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Long getTiempoEmbalaje() {
		return tiempoEmbalaje;
	}


	public void setTiempoEmbalaje(Long tiempoEmbalaje) {
		this.tiempoEmbalaje = tiempoEmbalaje;
	}


	public String getResponsableEmbalaje() {
		return responsableEmbalaje;
	}


	public void setResponsableEmbalaje(String responsableEmbalaje) {
		this.responsableEmbalaje = responsableEmbalaje;
	}


	public Long getIdCliente() {
		return idCliente;
	}


	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}


	public String getFolio_packingList() {
		return folio_packingList;
	}


	public void setFolio_packingList(String folio_packingList) {
		this.folio_packingList = folio_packingList;
	}


	public String getZona() {
		return zona;
	}


	public void setZona(String zona) {
		this.zona = zona;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	
	
	
	
	
	
	
}
