package com.proquifa.net.modelo.cobrosypagos;

import java.util.Date;

import com.proquifa.net.modelo.comun.Direccion;

public class ProgramarCobros {
	
	//Este modelo se utiliza para programar de cobro y programar revision
	
	/*private String nombre;
	private String pais;
	private String estado;
	private String calle;
	private String municipio;
	private String ruta;
	private String zonaMensajeria;
	private String cp;
	private String mapa;
	private String cPedido;
	private Date FRR;*/
	
	private String cPedido;
	private String Nombre;
	private Direccion direccion;
	private Date frr;
	
	private Boolean diario;
	private String diaDe1;
	private String diaA1;
	private String diaDe2;
	private String diaA2;
	
	private Boolean lunes;
	private String luDe1;
	private String luA1;
	private String luDe2;
	private String luA2;
	
	private Boolean martes;
	private String maDe1;
	private String maA1;
	private String maDe2;
	private String maA2;
	
	private Boolean miercoles;
	private String miDe1;
	private String miA1;
	private String miDe2;
	private String miA2;
	
	private Boolean jueves;
	private String juDe1;
	private String juA1;
	private String juDe2;
	private String juA2;
	
	private Boolean viernes;
	private String viDe1;
	private String viA1;
	private String viDe2;
	private String viA2;
	
	
	public String getcPedido() {
		return cPedido;
	}
	public void setcPedido(String cPedido) {
		this.cPedido = cPedido;
	}
	public String getNombre() {
		return Nombre;
	}
	public void setNombre(String nombre) {
		Nombre = nombre;
	}
	public Direccion getDireccion() {
		return direccion;
	}
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	public Date getFrr() {
		return frr;
	}
	public void setFrr(Date frr) {
		this.frr = frr;
	}
	public Boolean getDiario() {
		return diario;
	}
	public void setDiario(Boolean diario) {
		this.diario = diario;
	}
	public String getDiaDe1() {
		return diaDe1;
	}
	public void setDiaDe1(String diaDe1) {
		this.diaDe1 = diaDe1;
	}
	public String getDiaA1() {
		return diaA1;
	}
	public void setDiaA1(String diaA1) {
		this.diaA1 = diaA1;
	}
	public String getDiaDe2() {
		return diaDe2;
	}
	public void setDiaDe2(String diaDe2) {
		this.diaDe2 = diaDe2;
	}
	public String getDiaA2() {
		return diaA2;
	}
	public void setDiaA2(String diaA2) {
		this.diaA2 = diaA2;
	}
	public Boolean getLunes() {
		return lunes;
	}
	public void setLunes(Boolean lunes) {
		this.lunes = lunes;
	}
	public String getLuDe1() {
		return luDe1;
	}
	public void setLuDe1(String luDe1) {
		this.luDe1 = luDe1;
	}
	public String getLuA1() {
		return luA1;
	}
	public void setLuA1(String luA1) {
		this.luA1 = luA1;
	}
	public String getLuDe2() {
		return luDe2;
	}
	public void setLuDe2(String luDe2) {
		this.luDe2 = luDe2;
	}
	public String getLuA2() {
		return luA2;
	}
	public void setLuA2(String luA2) {
		this.luA2 = luA2;
	}
	public Boolean getMartes() {
		return martes;
	}
	public void setMartes(Boolean martes) {
		this.martes = martes;
	}
	public String getMaDe1() {
		return maDe1;
	}
	public void setMaDe1(String maDe1) {
		this.maDe1 = maDe1;
	}
	public String getMaA1() {
		return maA1;
	}
	public void setMaA1(String maA1) {
		this.maA1 = maA1;
	}
	public String getMaDe2() {
		return maDe2;
	}
	public void setMaDe2(String maDe2) {
		this.maDe2 = maDe2;
	}
	public String getMaA2() {
		return maA2;
	}
	public void setMaA2(String maA2) {
		this.maA2 = maA2;
	}
	public Boolean getMiercoles() {
		return miercoles;
	}
	public void setMiercoles(Boolean miercoles) {
		this.miercoles = miercoles;
	}
	public String getMiDe1() {
		return miDe1;
	}
	public void setMiDe1(String miDe1) {
		this.miDe1 = miDe1;
	}
	public String getMiA1() {
		return miA1;
	}
	public void setMiA1(String miA1) {
		this.miA1 = miA1;
	}
	public String getMiDe2() {
		return miDe2;
	}
	public void setMiDe2(String miDe2) {
		this.miDe2 = miDe2;
	}
	public String getMiA2() {
		return miA2;
	}
	public void setMiA2(String miA2) {
		this.miA2 = miA2;
	}
	public Boolean getJueves() {
		return jueves;
	}
	public void setJueves(Boolean jueves) {
		this.jueves = jueves;
	}
	public String getJuDe1() {
		return juDe1;
	}
	public void setJuDe1(String juDe1) {
		this.juDe1 = juDe1;
	}
	public String getJuA1() {
		return juA1;
	}
	public void setJuA1(String juA1) {
		this.juA1 = juA1;
	}
	public String getJuDe2() {
		return juDe2;
	}
	public void setJuDe2(String juDe2) {
		this.juDe2 = juDe2;
	}
	public String getJuA2() {
		return juA2;
	}
	public void setJuA2(String juA2) {
		this.juA2 = juA2;
	}
	public Boolean getViernes() {
		return viernes;
	}
	public void setViernes(Boolean viernes) {
		this.viernes = viernes;
	}
	public String getViDe1() {
		return viDe1;
	}
	public void setViDe1(String viDe1) {
		this.viDe1 = viDe1;
	}
	public String getViA1() {
		return viA1;
	}
	public void setViA1(String viA1) {
		this.viA1 = viA1;
	}
	public String getViDe2() {
		return viDe2;
	}
	public void setViDe2(String viDe2) {
		this.viDe2 = viDe2;
	}
	public String getViA2() {
		return viA2;
	}
	public void setViA2(String viA2) {
		this.viA2 = viA2;
	}
	
	
}
