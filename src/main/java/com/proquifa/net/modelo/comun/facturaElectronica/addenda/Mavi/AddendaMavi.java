package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Mavi;

public class AddendaMavi {
	private String rfcProveedor;
	private String numProveedor;
	private String fechaFacturacion;
	private String numPedido;
	private String codMoneda;
	private String montoTotal;
	private String iva;
	private String PorcentajeIVA;
	private String NumFactura;
	private String Serie;
	private String Folio;
	
	public String getRfcProveedor() {
		return rfcProveedor;
	}
	
	public void setRfcProveedor(String rfcProveedor) {
		this.rfcProveedor = rfcProveedor;
	}
	
	public String getNumProveedor() {
		return numProveedor;
	}
	
	public void setNumProveedor(String numProveedor) {
		this.numProveedor = numProveedor;
	}
	
	public String getFechaFacturacion() {
		return fechaFacturacion;
	}
	
	public void setFechaFacturacion(String fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}
	
	public String getNumPedido() {
		return numPedido;
	}
	
	public void setNumPedido(String numPedido) {
		this.numPedido = numPedido;
	}
	
	public String getCodMoneda() {
		return codMoneda;
	}
	
	public void setCodMoneda(String codMoneda) {
		this.codMoneda = codMoneda;
	}
	
	public String getMontoTotal() {
		return montoTotal;
	}
	
	public void setMontoTotal(String montoTotal) {
		this.montoTotal = montoTotal;
	}
	
	public String getIva() {
		return iva;
	}
	
	public void setIva(String iva) {
		this.iva = iva;
	}
	
	public String getPorcentajeIVA() {
		return PorcentajeIVA;
	}
	
	public void setPorcentajeIVA(String porcentajeIVA) {
		PorcentajeIVA = porcentajeIVA;
	}
	
	public String getNumFactura() {
		return NumFactura;
	}
	
	public void setNumFactura(String numFactura) {
		NumFactura = numFactura;
	}
	
	public String getSerie() {
		return Serie;
	}
	
	public void setSerie(String serie) {
		Serie = serie;
	}
	
	public String getFolio() {
		return Folio;
	}
	
	public void setFolio(String folio) {
		Folio = folio;
	}

}
