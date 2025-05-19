package com.proquifa.net.modelo.cuentaContable;

import java.util.List;

public class BalanceGeneral {
	private String fecha;
	private String empresa;
	private String labelTotalA;
	private String labelTotalB;
	private String totalA;
	private String totalB;
	private List<PBalanceGeneral> lstCat;
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getLabelTotalA() {
		return labelTotalA;
	}
	public void setLabelTotalA(String labelTotalA) {
		this.labelTotalA = labelTotalA;
	}
	public String getLabelTotalB() {
		return labelTotalB;
	}
	public void setLabelTotalB(String labelTotalB) {
		this.labelTotalB = labelTotalB;
	}
	public String getTotalA() {
		return totalA;
	}
	public void setTotalA(String totalA) {
		this.totalA = totalA;
	}
	public String getTotalB() {
		return totalB;
	}
	public void setTotalB(String totalB) {
		this.totalB = totalB;
	}
	public List<PBalanceGeneral> getLstCat() {
		return lstCat;
	}
	public void setLstCat(List<PBalanceGeneral> lstCat) {
		this.lstCat = lstCat;
	}
}
