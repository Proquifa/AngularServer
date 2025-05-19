package com.proquifa.net.modelo.cuentaContable;

import java.util.List;

public class PCuentaAuxilar {
	private String codigo;
	private String descripcion;
	private String nombre;
	private String monto;
	private String fecha;
	private String tipo;
	private String num;
	private String ubicacion;
	private String saldoInicial;
	private String saldoFinal;
	private List<PCuentaAuxilar> lstCCN2;
	private List<PCuentaAuxilar> lstPP;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getMonto() {
		return monto;
	}
	public void setMonto(String monto) {
		this.monto = monto;
	}
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(String saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	public String getSaldoFinal() {
		return saldoFinal;
	}
	public void setSaldoFinal(String saldoFinal) {
		this.saldoFinal = saldoFinal;
	}
	public List<PCuentaAuxilar> getLstCCN2() {
		return lstCCN2;
	}
	public void setLstCCN2(List<PCuentaAuxilar> lstCCN2) {
		this.lstCCN2 = lstCCN2;
	}
	public List<PCuentaAuxilar> getLstPP() {
		return lstPP;
	}
	public void setLstPP(List<PCuentaAuxilar> lstPP) {
		this.lstPP = lstPP;
	}
}
