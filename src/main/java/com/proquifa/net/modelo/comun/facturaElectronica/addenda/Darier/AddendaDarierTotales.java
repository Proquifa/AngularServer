package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier;

import java.util.List;

public class AddendaDarierTotales {
	private String moneda;
	private String subTotalBruto;
	private String subTotal;
	private AddendaDarierDescuentoRecargo descuentoORecargo;
	private AddendaDarierResDescuentosRecargos resDescuentosRecargos;
	private List<AddendaDarierImpuesto> impuestos;
	private AddendaDarierResImpuestos resumenImpuestos;
	private String importeEnLetras;
	private String total;
	private AddendaDarierResConceptos resumenConceptos;
	
	public String getMoneda() {
		return moneda;
	}
	
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	
	public String getSubTotalBruto() {
		return subTotalBruto;
	}
	
	public void setSubTotalBruto(String subTotalBruto) {
		this.subTotalBruto = subTotalBruto;
	}
	
	public String getSubTotal() {
		return subTotal;
	}
	
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	
	public AddendaDarierResDescuentosRecargos getResDescuentosRecargos() {
		return resDescuentosRecargos;
	}
	
	public void setResDescuentosRecargos(AddendaDarierResDescuentosRecargos resDescuentosRecargos) {
		this.resDescuentosRecargos = resDescuentosRecargos;
	}
	
	public List<AddendaDarierImpuesto> getImpuestos() {
		return impuestos;
	}
	
	public void setImpuestos(List<AddendaDarierImpuesto> impuestos) {
		this.impuestos = impuestos;
	}
	
	public AddendaDarierResImpuestos getResumenImpuestos() {
		return resumenImpuestos;
	}
	
	public void setResumenImpuestos(AddendaDarierResImpuestos resumenImpuestos) {
		this.resumenImpuestos = resumenImpuestos;
	}
	
	public String getImporteEnLetras() {
		return importeEnLetras;
	}
	
	public void setImporteEnLetras(String importeEnLetras) {
		this.importeEnLetras = importeEnLetras;
	}
	
	public String getTotal() {
		return total;
	}
	
	public void setTotal(String total) {
		this.total = total;
	}
	
	public AddendaDarierResConceptos getResumenConceptos() {
		return resumenConceptos;
	}
	
	public void setResumenConceptos(AddendaDarierResConceptos resumenConceptos) {
		this.resumenConceptos = resumenConceptos;
	}

	public AddendaDarierDescuentoRecargo getDescuentoORecargo() {
		return descuentoORecargo;
	}

	public void setDescuentoORecargo(AddendaDarierDescuentoRecargo descuentoORecargo) {
		this.descuentoORecargo = descuentoORecargo;
	}
	
}
