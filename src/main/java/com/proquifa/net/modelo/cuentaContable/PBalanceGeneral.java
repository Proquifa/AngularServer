package com.proquifa.net.modelo.cuentaContable;

import java.util.List;

public class PBalanceGeneral {
	private String nombre;
	private List<PBalanceGeneral> lstSubcat;
	private String subTotalEtiqueta;
	private String subTotalMonto;
	private List<PBalanceGeneral> lstValores;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public List<PBalanceGeneral> getLstSubcat() {
		return lstSubcat;
	}
	public void setLstSubcat(List<PBalanceGeneral> lstSubcat) {
		this.lstSubcat = lstSubcat;
	}
	public String getSubTotalEtiqueta() {
		return subTotalEtiqueta;
	}
	public void setSubTotalEtiqueta(String subTotalEtiqueta) {
		this.subTotalEtiqueta = subTotalEtiqueta;
	}
	public String getSubTotalMonto() {
		return subTotalMonto;
	}
	public void setSubTotalMonto(String subTotalMonto) {
		this.subTotalMonto = subTotalMonto;
	}
	public List<PBalanceGeneral> getLstValores() {
		return lstValores;
	}
	public void setLstValores(List<PBalanceGeneral> lstValores) {
		this.lstValores = lstValores;
	}
	
}
