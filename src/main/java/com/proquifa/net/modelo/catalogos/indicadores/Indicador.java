package com.proquifa.net.modelo.catalogos.indicadores;

import java.util.Date;

public class Indicador {

	private long idIndicador;
	private long idSubProceso;	
	private String concepto;
	private Double valor;
	private Date fua;
	private long idUsuarioActualizo;
	private boolean editar;	//Indica si fue editado
	private double segundosValorTiempo;
	private double segundosValorTiempo2;
	/**
	 * @return the idIndicador
	 */
	public long getIdIndicador() {
		return idIndicador;
	}
	/**
	 * @param idIndicador the idIndicador to set
	 */
	public void setIdIndicador(long idIndicador) {
		this.idIndicador = idIndicador;
	}
	/**
	 * @return the idSubProceso
	 */
	public long getIdSubProceso() {
		return idSubProceso;
	}
	/**
	 * @param idSubProceso the idSubProceso to set
	 */
	public void setIdSubProceso(long idSubProceso) {
		this.idSubProceso = idSubProceso;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the valor
	 */
	public Double getValor() {
		return valor;
	}
	/**
	 * @param valor the valor to set
	 */
	public void setValor(Double valor) {
		this.valor = valor;
	}
	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}
	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}
	/**
	 * @return the idUsuarioActualizo
	 */
	public long getIdUsuarioActualizo() {
		return idUsuarioActualizo;
	}
	/**
	 * @param idUsuarioActualizo the idUsuarioActualizo to set
	 */
	public void setIdUsuarioActualizo(long idUsuarioActualizo) {
		this.idUsuarioActualizo = idUsuarioActualizo;
	}
	/**
	 * @return the editar
	 */
	public boolean isEditar() {
		return editar;
	}
	/**
	 * @param editar the editar to set
	 */
	public void setEditar(boolean editar) {
		this.editar = editar;
	}
	/**
	 * @return the segundosValorTiempo
	 */
	public double getSegundosValorTiempo() {
		return segundosValorTiempo;
	}
	/**
	 * @param segundosValorTiempo the segundosValorTiempo to set
	 */
	public void setSegundosValorTiempo(double segundosValorTiempo) {
		this.segundosValorTiempo = segundosValorTiempo;
	}
	/**
	 * @return the segundosValorTiempo2
	 */
	public double getSegundosValorTiempo2() {
		return segundosValorTiempo2;
	}
	/**
	 * @param segundosValorTiempo2 the segundosValorTiempo2 to set
	 */
	public void setSegundosValorTiempo2(double segundosValorTiempo2) {
		this.segundosValorTiempo2 = segundosValorTiempo2;
	}

}
