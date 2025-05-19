package com.proquifa.net.modelo.despachos.trabajarRuta;

import java.io.Serializable;
import java.util.List;

import org.hibernate.type.SerializableType;

public class TrEnvio  implements Serializable{

	private static final long serialVersionUID = 7419802263844005389L;
	private Integer idTREnvio;
	private Integer idPendiente;
	private Integer idUsuario;
	private String 	numGuia;
	private Integer fInicio;
	private Integer fFin;
	private String packingList;
	
	
	
	private String prioridad;
	private Integer Total;
	private Integer totalPiezas;
	private Integer tiempo;
	private Integer totalPl;
	private Integer totalPrioridad;

	private String mensajeria;
	
	private List<Integer> pendientes;
	private List<String> lstPackingList;
	
	
	
	public Integer getfInicio() {
		return fInicio;
	}
	public void setfInicio(Integer fInicio) {
		this.fInicio = fInicio;
	}
	public Integer getfFin() {
		return fFin;
	}
	public void setfFin(Integer fFin) {
		this.fFin = fFin;
	}
	public Integer getTotalPrioridad() {
		return totalPrioridad;
	}
	public void setTotalPrioridad(Integer totalPrioridad) {
		this.totalPrioridad = totalPrioridad;
	}
	public Integer getIdTREnvio() {
		return idTREnvio;
	}
	public void setIdTREnvio(Integer idTREnvio) {
		this.idTREnvio = idTREnvio;
	}
	public Integer getIdPendiente() {
		return idPendiente;
	}
	public void setIdPendiente(Integer idPendiente) {
		this.idPendiente = idPendiente;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public Integer getTotal() {
		return Total;
	}
	public void setTotal(Integer total) {
		Total = total;
	}
	public Integer getTotalPiezas() {
		return totalPiezas;
	}
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	public Integer getTiempo() {
		return tiempo;
	}
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}
	public Integer getTotalPl() {
		return totalPl;
	}
	public void setTotalPl(Integer totalPl) {
		this.totalPl = totalPl;
	}
	public String getNumGuia() {
		return numGuia;
	}
	public void setNumGuia(String numGuia) {
		this.numGuia = numGuia;
	}
	public String getPackingList() {
		return packingList;
	}
	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}
	/**
	 * @return the mensajeria
	 */
	public String getMensajeria() {
		return mensajeria;
	}
	/**
	 * @param mensajeria the mensajeria to set
	 */
	public void setMensajeria(String mensajeria) {
		this.mensajeria = mensajeria;
	}
	/**
	 * @return the pendientes
	 */
	public List<Integer> getPendientes() {
		return pendientes;
	}
	/**
	 * @param pendientes the pendientes to set
	 */
	public void setPendientes(List<Integer> pendientes) {
		this.pendientes = pendientes;
	}
	/**
	 * @return the lstPackingList
	 */
	public List<String> getLstPackingList() {
		return lstPackingList;
	}
	/**
	 * @param lstPackingList the lstPackingList to set
	 */
	public void setLstPackingList(List<String> lstPackingList) {
		this.lstPackingList = lstPackingList;
	}
	
	
}
