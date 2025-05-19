package com.proquifa.net.modelo.cuentaContable;

import java.util.List;

public class EstadoResultado {
	private String nombre;
	private String subnombre;
	private String monto;
	private String totalMonto;
	private String porcentaje;
	private List<EstadoResultado> lstN2;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSubnombre() {
		return subnombre;
	}
	public void setSubnombre(String subnombre) {
		this.subnombre = subnombre;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getTotalMonto() {
		return totalMonto;
	}
	public void setTotalMonto(String totalMonto) {
		this.totalMonto = totalMonto;
	}
	public String getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(String porcentaje) {
		this.porcentaje = porcentaje;
	}
	public List<EstadoResultado> getLstN2() {
		return lstN2;
	}
	public void setLstN2(List<EstadoResultado> lstN2) {
		this.lstN2 = lstN2;
	}
	
}
