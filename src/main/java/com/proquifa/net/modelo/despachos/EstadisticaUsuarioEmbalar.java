package com.proquifa.net.modelo.despachos;

import java.io.Serializable;

public class EstadisticaUsuarioEmbalar implements Serializable{
   /**
	 * 
	 */
   private static final long serialVersionUID = 5122450588903397464L;
   private Integer tiempo;
   private Integer totalPartidas;
   private Integer totalPiezas;
   private String prioridad;
   private Integer totalPrioridad;
   
   
   

public Integer getTotalPartidas() {
	return totalPartidas;
}
public void setTotalPartidas(Integer totalPartidas) {
	this.totalPartidas = totalPartidas;
}
public Integer getTotalPiezas() {
	return totalPiezas;
}
public void setTotalPiezas(Integer totalPiezas) {
	this.totalPiezas = totalPiezas;
}
public String getPrioridad() {
	return prioridad;
}
public void setPrioridad(String prioridad) {
	this.prioridad = prioridad;
}
public Integer getTotalPrioridad() {
	return totalPrioridad;
}
public void setTotalPrioridad(Integer totalPrioridad) {
	this.totalPrioridad = totalPrioridad;
}
public Integer getTiempo() {
	return tiempo;
}
public void setTiempo(Integer tiempo) {
	this.tiempo = tiempo;
}
   
   
}
