/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

/**
 * @author amartinez
 *
 */
public class Accion {
	private Long idAccion;
	private Long incidente;
	private Long responsable;
	private Date fecha;
	private Date fechaRealizacion;
	private Date fechaEstimadaRealizacion;
	private String tipo;
	private Double eficacia;
	private String descripcion;
	private String folio;
	private Long diasAtraso;
	private String nombreProgramo;
	private Long idPendiente;
	private String incidenteFolio;
	private Long programo;
	private String comentarios;
	private List<Referencia> referencias;
	private Integer horasInvertidas;
	private Double eficaciaVerificacion;
	private String descripcionVerificacion;
	private String nombreResponsable;
	private Double eficaciaReal;
	private String razonesPonderacion;
	private Date enEsperaDesde;
	
	/**
	 * @return the idAccion
	 */
	public Long getIdAccion() {
		return idAccion;
	}
	/**
	 * @param idAccion the idAccion to set
	 */
	public void setIdAccion(Long idAccion) {
		this.idAccion = idAccion;
	}
	/**
	 * @return the incidente
	 */
	public Long getIncidente() {
		return incidente;
	}
	/**
	 * @param incidente the incidente to set
	 */
	public void setIncidente(Long incidente) {
		this.incidente = incidente;
	}
	/**
	 * @return the responsable
	 */
	public Long getResponsable() {
		return responsable;
	}
	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(Long responsable) {
		this.responsable = responsable;
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
	 * @return the fechaRealizacion
	 */
	public Date getFechaRealizacion() {
		return fechaRealizacion;
	}
	/**
	 * @param fechaRealizacion the fechaRealizacion to set
	 */
	public void setFechaRealizacion(Date fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the eficacia
	 */
	public Double getEficacia() {
		return eficacia;
	}
	/**
	 * @param eficacia the eficacia to set
	 */
	public void setEficacia(Double eficacia) {
		this.eficacia = eficacia;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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
	 * @param diasAtraso the diasAtraso to set
	 */
	public void setDiasAtraso(Long diasAtraso) {
		this.diasAtraso = diasAtraso;
	}
	/**
	 * @return the diasAtraso
	 */
	public Long getDiasAtraso() {
		return diasAtraso;
	}
	/**
	 * @param nombreProgramo the nombreProgramo to set
	 */
	public void setNombreProgramo(String nombreProgramo) {
		this.nombreProgramo = nombreProgramo;
	}
	/**
	 * @return the nombreProgramo
	 */
	public String getNombreProgramo() {
		return nombreProgramo;
	}
	/**
	 * @param idPendiente the idPendiente to set
	 */
	public void setIdPendiente(Long idPendiente) {
		this.idPendiente = idPendiente;
	}
	/**
	 * @return the idPendiente
	 */
	public Long getIdPendiente() {
		return idPendiente;
	}
	/**
	 * @param incidenteFolio the incidenteFolio to set
	 */
	public void setIncidenteFolio(String incidenteFolio) {
		this.incidenteFolio = incidenteFolio;
	}
	/**
	 * @return the incidenteFolio
	 */
	public String getIncidenteFolio() {
		return incidenteFolio;
	}
	/**
	 * @param programo the programo to set
	 */
	public void setProgramo(Long programo) {
		this.programo = programo;
	}
	/**
	 * @return the programo
	 */
	public Long getProgramo() {
		return programo;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param fechaEstimadaRealizacion the fechaEstimadaRealizacion to set
	 */
	public void setFechaEstimadaRealizacion(Date fechaEstimadaRealizacion) {
		this.fechaEstimadaRealizacion = fechaEstimadaRealizacion;
	}
	/**
	 * @return the fechaEstimadaRealizacion
	 */
	public Date getFechaEstimadaRealizacion() {
		return fechaEstimadaRealizacion;
	}
	/**
	 * @param referencias the referencias to set
	 */
	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}
	/**
	 * @return the referencias
	 */
	public List<Referencia> getReferencias() {
		return referencias;
	}
	/**
	 * @param horasInvertidas the horasInvertidas to set
	 */
	public void setHorasInvertidas(Integer horasInvertidas) {
		this.horasInvertidas = horasInvertidas;
	}
	/**
	 * @return the horasInvertidas
	 */
	public Integer getHorasInvertidas() {
		return horasInvertidas;
	}
	/**
	 * @return the eficaciaVerificacion
	 */
	public Double getEficaciaVerificacion() {
		return eficaciaVerificacion;
	}
	/**
	 * @param eficaciaVerificacion the eficaciaVerificacion to set
	 */
	public void setEficaciaVerificacion(Double eficaciaVerificacion) {
		this.eficaciaVerificacion = eficaciaVerificacion;
	}
	/**
	 * @return the descripcionVerificacion
	 */
	public String getDescripcionVerificacion() {
		return descripcionVerificacion;
	}
	/**
	 * @param descripcionVerificacion the descripcionVerificacion to set
	 */
	public void setDescripcionVerificacion(String descripcionVerificacion) {
		this.descripcionVerificacion = descripcionVerificacion;
	}
	/**
	 * @param nombreResponsable the nombreResponsable to set
	 */
	public void setNombreResponsable(String nombreResponsable) {
		this.nombreResponsable = nombreResponsable;
	}
	/**
	 * @return the nombreResponsable
	 */
	public String getNombreResponsable() {
		return nombreResponsable;
	}
	/**
	 * @return the eficaciaReal
	 */
	public Double getEficaciaReal() {
		return eficaciaReal;
	}
	/**
	 * @param eficaciaReal the eficaciaReal to set
	 */
	public void setEficaciaReal(Double eficaciaReal) {
		this.eficaciaReal = eficaciaReal;
	}
	/**
	 * @return the razonesPonderacion
	 */
	public String getRazonesPonderacion() {
		return razonesPonderacion;
	}
	/**
	 * @param razonesPonderacion the razonesPonderacion to set
	 */
	public void setRazonesPonderacion(String razonesPonderacion) {
		this.razonesPonderacion = razonesPonderacion;
	}
	/**
	 * @param enEsperaDesde the enEsperaDesde to set
	 */
	public void setEnEsperaDesde(Date enEsperaDesde) {
		this.enEsperaDesde = enEsperaDesde;
	}
	/**
	 * @return the enEsperaDesde
	 */
	public Date getEnEsperaDesde() {
		return enEsperaDesde;
	}
}