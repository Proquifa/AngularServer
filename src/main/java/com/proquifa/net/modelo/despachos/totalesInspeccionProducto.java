package com.proquifa.net.modelo.despachos;

import java.util.List;

public class totalesInspeccionProducto {
	
	private Long total_A;
	private Long total_M;
	private Long total_Q;
	private Long total_D;
	private Long promXpieza;
	private Long num_hallazgos;
	private Long t_partidas;
	private List<PartidaInspeccion> list_Inspeccion; 
	private double total_piezas;
	private double totalTiempo_Ensegundos;
	private Long piezasProm;
	private Long maxPiezasInsp;
	private Long total_piezasInspeccionadas;
	private Long PzaAInspeccion;
	
	
	
	
	public Long getTotal_M() {
		return total_M;
	}
	public void setTotal_M(Long total_M) {
		this.total_M = total_M;
	}
	public Long getTotal_Q() {
		return total_Q;
	}
	public void setTotal_Q(Long total_Q) {
		this.total_Q = total_Q;
	}
	public Long getTotal_D() {
		return total_D;
	}
	public void setTotal_D(Long total_D) {
		this.total_D = total_D;
	}
	public Long getTotal_A() {
		return total_A;
	}
	public void setTotal_A(Long total_A) {
		this.total_A = total_A;
	}
	public Long getPromXpieza() {
		return promXpieza;
	}
	public void setPromXpieza(Long promXpieza) {
		this.promXpieza = promXpieza;
	}
	public List<PartidaInspeccion> getList_Inspeccion() {
		return list_Inspeccion;
	}
	public void setList_Inspeccion(List<PartidaInspeccion> list_Inspeccion) {
		this.list_Inspeccion = list_Inspeccion;
	}
	public Long getNum_hallazgos() {
		return num_hallazgos;
	}
	public void setNum_hallazgos(Long num_hallazgos) {
		this.num_hallazgos = num_hallazgos;
	}
	public Long getT_partidas() {
		return t_partidas;
	}
	public void setT_partidas(Long t_partidas) {
		this.t_partidas = t_partidas;
	}
	public double getTotal_piezas() {
		return total_piezas;
	}
	public void setTotal_piezas(double total_piezas) {
		this.total_piezas = total_piezas;
	}
	public double getTotalTiempo_Ensegundos() {
		return totalTiempo_Ensegundos;
	}
	public void setTotalTiempo_Ensegundos(double totalTiempo_Ensegundos) {
		this.totalTiempo_Ensegundos = totalTiempo_Ensegundos;
	}
	public Long getPiezasProm() {
		return piezasProm;
	}
	public void setPiezasProm(Long piezasProm) {
		this.piezasProm = piezasProm;
	}
	public Long getMaxPiezasInsp() {
		return maxPiezasInsp;
	}
	public void setMaxPiezasInsp(Long maxPiezasInsp) {
		this.maxPiezasInsp = maxPiezasInsp;
	}
	public Long getPzaAInspeccion() {
		return PzaAInspeccion;
	}
	public void setPzaAInspeccion(Long pzaAInspeccion) {
		PzaAInspeccion = pzaAInspeccion;
	}
	public Long getTotal_piezasInspeccionadas() {
		return total_piezasInspeccionadas;
	}
	public void setTotal_piezasInspeccionadas(Long total_piezasInspeccionadas) {
		this.total_piezasInspeccionadas = total_piezasInspeccionadas;
	}
	

}
