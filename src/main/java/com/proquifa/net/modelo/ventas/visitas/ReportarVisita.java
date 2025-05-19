/**
 * 
 */
package com.proquifa.net.modelo.ventas.visitas;

import java.util.List;

import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;

/**
 * @author yosimar.mendez
 *
 */
public class ReportarVisita {
	
	private List<SolicitudVisita> requerimientos;
	private List<HallazgosAcciones> pendientes;
	private List<HallazgosAcciones> hallazgos;
	private Requisicion requisicion;
	private List<Cotizacion> cotizacion;
	private Long calificacion;
	private String reporte;
	private Correo correo;
	private String notas;
	/**
	 * 
	 */
	public ReportarVisita() {
		super();
	}

	/**
	 * @return the requerimientos
	 */
	public List<SolicitudVisita> getRequerimientos() {
		return requerimientos;
	}

	/**
	 * @param requerimientos the requerimientos to set
	 */
	public void setRequerimientos(List<SolicitudVisita> requerimientos) {
		this.requerimientos = requerimientos;
	}

	/**
	 * @return the pendientes
	 */
	public List<HallazgosAcciones> getPendientes() {
		return pendientes;
	}

	/**
	 * @param pendientes the pendientes to set
	 */
	public void setPendientes(List<HallazgosAcciones> pendientes) {
		this.pendientes = pendientes;
	}

	/**
	 * @return the hallazgos
	 */
	public List<HallazgosAcciones> getHallazgos() {
		return hallazgos;
	}

	/**
	 * @param hallazgos the hallazgos to set
	 */
	public void setHallazgos(List<HallazgosAcciones> hallazgos) {
		this.hallazgos = hallazgos;
	}

	/**
	 * @return the requisicion
	 */
	public Requisicion getRequisicion() {
		return requisicion;
	}

	/**
	 * @param requisicion the requisicion to set
	 */
	public void setRequisicion(Requisicion requisicion) {
		this.requisicion = requisicion;
	}

	public List<Cotizacion> getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(List<Cotizacion> cotizacion) {
		this.cotizacion = cotizacion;
	}

	public String getReporte() {
		return reporte;
	}

	public void setReporte(String reporte) {
		this.reporte = reporte;
	}

	public Long getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Long calificacion) {
		this.calificacion = calificacion;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}	
	

}
