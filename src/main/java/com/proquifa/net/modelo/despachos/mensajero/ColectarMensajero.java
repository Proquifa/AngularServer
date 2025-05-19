package com.proquifa.net.modelo.despachos.mensajero;

import java.io.Serializable;

public class ColectarMensajero  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8933478090885819029L;
	private Integer totalPL;
	private Integer totalPR;
	private Integer totalES;
	private Integer totalPC;
	private Integer totalRM;
	private String calle;
	private String delegacion;
	private String cp;
	
	
	
	public Integer getTotalPL() {
		return totalPL;
	}
	public void setTotalPL(Integer totalPL) {
		this.totalPL = totalPL;
	}
	public Integer getTotalPR() {
		return totalPR;
	}
	public void setTotalPR(Integer totalPR) {
		this.totalPR = totalPR;
	}
	public Integer getTotalES() {
		return totalES;
	}
	public void setTotalES(Integer totalES) {
		this.totalES = totalES;
	}
	public Integer getTotalPC() {
		return totalPC;
	}
	public void setTotalPC(Integer totalPC) {
		this.totalPC = totalPC;
	}
	public Integer getTotalRM() {
		return totalRM;
	}
	public void setTotalRM(Integer totalRM) {
		this.totalRM = totalRM;
	}
	public String getCalle() {
		return calle;
	}
	public void setCalle(String calle) {
		this.calle = calle;
	}
	public String getDelegacion() {
		return delegacion;
	}
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	public String getCp() {
		return cp;
	}
	public void setCp(String cp) {
		this.cp = cp;
	}
	
	
	
	
}
