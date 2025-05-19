/**
 * 
 */
package com.proquifa.net.modelo.despachos.asuntosregulatorios;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ymendez
 *
 */
public class PermisoAdquisicion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3959576146384358347L;

	private Integer idGestionPAP;
	private Integer idPPedido;
	private Integer idPedido;
	private String noPermisoAdquisicion;
	private String fechaPermiso;
	private String fechaVencimiento;
	private String actaLiberacion;
	private String tipoPermiso;
	
	/**
	 * 
	 */
	public PermisoAdquisicion() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idGestionPAP
	 */
	public Integer getIdGestionPAP() {
		return idGestionPAP;
	}

	/**
	 * @param idGestionPAP the idGestionPAP to set
	 */
	public void setIdGestionPAP(Integer idGestionPAP) {
		this.idGestionPAP = idGestionPAP;
	}

	/**
	 * @return the idPPedido
	 */
	public Integer getIdPPedido() {
		return idPPedido;
	}

	/**
	 * @param idPPedido the idPPedido to set
	 */
	public void setIdPPedido(Integer idPPedido) {
		this.idPPedido = idPPedido;
	}

	/**
	 * @return the noPermisoAdquisicion
	 */
	public String getNoPermisoAdquisicion() {
		return noPermisoAdquisicion;
	}

	/**
	 * @param noPermisoAdquisicion the noPermisoAdquisicion to set
	 */
	public void setNoPermisoAdquisicion(String noPermisoAdquisicion) {
		this.noPermisoAdquisicion = noPermisoAdquisicion;
	}

	/**
	 * @return the fechaPermiso
	 */
	public String getFechaPermiso() {
		return fechaPermiso;
	}

	/**
	 * @param fechaPermiso the fechaPermiso to set
	 */
	public void setFechaPermiso(String fechaPermiso) {
		this.fechaPermiso = fechaPermiso;
	}

	/**
	 * @return the fechaVencimiento
	 */
	public String getFechaVencimiento() {
		return fechaVencimiento;
	}

	/**
	 * @param fechaVencimiento the fechaVencimiento to set
	 */
	public void setFechaVencimiento(String fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	/**
	 * @return the actaLiberacion
	 */
	public String getActaLiberacion() {
		return actaLiberacion;
	}

	/**
	 * @param actaLiberacion the actaLiberacion to set
	 */
	public void setActaLiberacion(String actaLiberacion) {
		this.actaLiberacion = actaLiberacion;
	}

	/**
	 * @return the idPedido
	 */
	public Integer getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public String getTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}
	
	
	
}
