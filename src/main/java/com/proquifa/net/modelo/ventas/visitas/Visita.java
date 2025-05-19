/**
 * 
 */
package com.proquifa.net.modelo.ventas.visitas;

import java.util.Date;
import java.util.List;

/**
 * @author ymendez
 *
 */

public class Visita {

	private Long idVisita;
	private String folio;
	private Date fecha;
	private Date fechaUltimaActualizacion;
	private String objetivo;
	private String alcance;
	private Date fechaEstimadaRealizacion;
	private Date fechaEstimadaContacto;
	private List<Temas> temas;
	private List<SolicitudVisita> solicitudes;
	
	private Boolean tventa;
	private Boolean tgestion;
	private Boolean tcobranza;
	private Boolean tvisita;
	
	/**
	 * @return the idVisita
	 */
	public Long getIdVisita() {
		return idVisita;
	}
	/**
	 * @param idVisita the idVisita to set
	 */
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the fehaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}
	/**
	 * @param fehaUltimaActualizacion the fehaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fehaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fehaUltimaActualizacion;
	}
	/**
	 * @return the objetivo
	 */
	public String getObjetivo() {
		return objetivo;
	}
	/**
	 * @param objetivo the objetivo to set
	 */
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	/**
	 * @return the alcance
	 */
	public String getAlcance() {
		return alcance;
	}
	/**
	 * @param alcance the alcance to set
	 */
	public void setAlcance(String alcance) {
		this.alcance = alcance;
	}
	/**
	 * @return the fechaEstimadaRealizacion
	 */
	public Date getFechaEstimadaRealizacion() {
		return fechaEstimadaRealizacion;
	}
	/**
	 * @param fechaEstimadaRealizacion the fechaEstimadaRealizacion to set
	 */
	public void setFechaEstimadaRealizacion(Date fechaEstimadaRealizacion) {
		this.fechaEstimadaRealizacion = fechaEstimadaRealizacion;
	}
	/**
	 * @return the fechaEstimadaContacto
	 */
	public Date getFechaEstimadaContacto() {
		return fechaEstimadaContacto;
	}
	/**
	 * @param fechaEstimadaContacto the fechaEstimadaContacto to set
	 */
	public void setFechaEstimadaContacto(Date fechaEstimadaContacto) {
		this.fechaEstimadaContacto = fechaEstimadaContacto;
	}
	/**
	 * @return the solicitudesVisitas
	 */
//	public List<Long> getSolicitudesVisitas() {
//		return solicitudesVisitas;
//	}
//	/**
//	 * @param solicitudesVisitas the solicitudesVisitas to set
//	 */
//	public void setSolicitudesVisitas(List<Long> solicitudesVisitas) {
//		this.solicitudesVisitas = solicitudesVisitas;
//	}
	/**
	 * @return the temas
	 */
	public List<Temas> getTemas() {
		return temas;
	}
	/**
	 * @param temas the temas to set
	 */
	public void setTemas(List<Temas> temas) {
		this.temas = temas;
	}
	/**
	 * @param solicitudes the solicitudes to set
	 */
	public void setSolicitudes(List<SolicitudVisita> solicitudes) {
		this.solicitudes = solicitudes;
	}
	/**
	 * @return the solicitudes
	 */
	public List<SolicitudVisita> getSolicitudes() {
		return solicitudes;
	}
	/**
	 * @return the tventa
	 */
	public Boolean getTventa() {
		return tventa;
	}
	/**
	 * @param tventa the tventa to set
	 */
	public void setTventa(Boolean tventa) {
		this.tventa = tventa;
	}
	/**
	 * @return the tgestion
	 */
	public Boolean getTgestion() {
		return tgestion;
	}
	/**
	 * @param tgestion the tgestion to set
	 */
	public void setTgestion(Boolean tgestion) {
		this.tgestion = tgestion;
	}
	/**
	 * @return the tcobranza
	 */
	public Boolean getTcobranza() {
		return tcobranza;
	}
	/**
	 * @param tcobranza the tcobranza to set
	 */
	public void setTcobranza(Boolean tcobranza) {
		this.tcobranza = tcobranza;
	}
	/**
	 * @return the tvisita
	 */
	public Boolean getTvisita() {
		return tvisita;
	}
	/**
	 * @param tvisita the tvisita to set
	 */
	public void setTvisita(Boolean tvisita) {
		this.tvisita = tvisita;
	}
	
}
