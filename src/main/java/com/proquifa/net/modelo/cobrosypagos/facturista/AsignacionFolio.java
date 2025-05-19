package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;


/**
 * @author Jhidalgo
 *
 */

public class AsignacionFolio {
	
	private Date fecha;
	private int folioInicio;
	private int folioFinal;
	private int idEmpresa;
	private int idEmpleado;
	private String nombreEmpleado;
	private boolean asignado;
	private int rangoFoliosAsignados;
	private int foliosConsumidos;
	private String tipo;
	
	
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param folioInicio the folioInicio to set
	 */
	public void setFolioInicio(int folioInicio) {
		this.folioInicio = folioInicio;
	}
	/**
	 * @return the folioInicio
	 */
	public int getFolioInicio() {
		return folioInicio;
	}
	/**
	 * @param folioFinal the folioFinal to set
	 */
	public void setFolioFinal(int folioFinal) {
		this.folioFinal = folioFinal;
	}
	/**
	 * @return the folioFinal
	 */
	public int getFolioFinal() {
		return folioFinal;
	}
	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(int idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	/**
	 * @return the idEmpresa
	 */
	public int getIdEmpresa() {
		return idEmpresa;
	}
	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	/**
	 * @return the idEmpleado
	 */
	public int getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @param asignado the asignado to set
	 */
	public void setAsignado(boolean asignado) {
		this.asignado = asignado;
	}
	/**
	 * @return the asignado
	 */
	public boolean isAsignado() {
		return asignado;
	}
	/**
	 * @param nombreEmpleado the nombreEmpleado to set
	 */
	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	/**
	 * @return the nombreEmpleado
	 */
	public String getNombreEmpleado() {
		return nombreEmpleado;
	}
	/**
	 * @param rangoFoliosAsignados the rangoFoliosAsignados to set
	 */
	public void setRangoFoliosAsignados(int rangoFoliosAsignados) {
		this.rangoFoliosAsignados = rangoFoliosAsignados;
	}
	/**
	 * @return the rangoFoliosAsignados
	 */
	public int getRangoFoliosAsignados() {
		return rangoFoliosAsignados;
	}
	/**
	 * @param foliosConsumidos the foliosConsumidos to set
	 */
	public void setFoliosConsumidos(int foliosConsumidos) {
		this.foliosConsumidos = foliosConsumidos;
	}
	/**
	 * @return the foliosConsumidos
	 */
	public int getFoliosConsumidos() {
		return foliosConsumidos;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}


}
