package com.proquifa.net.modelo.cuentaContable;

import java.util.List;

public class CuentaAuxilar {
	private String fecha;
	private String empresa;
	private String logo;
	private String totalA;
	private String totalB;
	private List<PCuentaAuxilar> lstCC;
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public List<PCuentaAuxilar> getLstCC() {
		return lstCC;
	}
	public void setLstCC(List<PCuentaAuxilar> lstCC) {
		this.lstCC = lstCC;
	}
}
