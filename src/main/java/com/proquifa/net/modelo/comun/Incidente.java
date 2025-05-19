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
public class Incidente {
	private Long idIncidente;
	private Long idEmpleado;
	private String descripcion;
	private String lugar;
	private String cuando;
	private Date fecha;
	private String comentarios;
	private String folio;
	private Long diasAtraso;
	private List<Referencia> referencias;
	private Long idPendiente;
	private String redacto;
	private Integer numeroAcciones;
	private String verifico;
	private Double eficaciaGlobal;
	private Double eficaciaGlobalV;
	private String tipo;
	private List<Accion> acciones;
	private String pondero;
	private Double costoCalidad;
	private String ubicacion;
	private String gestor;
	private Long incidenteRelacionado;
	private String incidenteRelacionadoS;
	private String folioIncidenteRelacionado;
	private Long pendientePonderacion;
	private Long idGestiono;
	private Date fechaCierre;
	private String justificacion;
	private String estado;
	private Double horasHombre;
	private Double costoHoraHombre;
	private Long idSubprocesoRelacionado;
	
	/**
	 * @return the idIncidente
	 */
	public Long getIdIncidente() {
		return idIncidente;
	}
	/**
	 * @param idIncidente the idIncidente to set
	 */
	public void setIdIncidente(Long idIncidente) {
		this.idIncidente = idIncidente;
	}
	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
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
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}
	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	/**
	 * @return the cuando
	 */
	public String getCuando() {
		return cuando;
	}
	/**
	 * @param cuando the cuando to set
	 */
	public void setCuando(String cuando) {
		this.cuando = cuando;
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
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
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
	 * @return the diasAtraso
	 */
	public Long getDiasAtraso() {
		return diasAtraso;
	}
	/**
	 * @param diasAtraso the diasAtraso to set
	 */
	public void setDiasAtraso(Long diasAtraso) {
		this.diasAtraso = diasAtraso;
	}
	/**
	 * @return the referencias
	 */
	public List<Referencia> getReferencias() {
		return referencias;
	}
	/**
	 * @param referencias the referencias to set
	 */
	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}
	/**
	 * @return the idPendiente
	 */
	public Long getIdPendiente() {
		return idPendiente;
	}
	/**
	 * @param idPendiente the idPendiente to set
	 */
	public void setIdPendiente(Long idPendiente) {
		this.idPendiente = idPendiente;
	}
	/**
	 * @return the redacto
	 */
	public String getRedacto() {
		return redacto;
	}
	/**
	 * @param redacto the redacto to set
	 */
	public void setRedacto(String redacto) {
		this.redacto = redacto;
	}
	/**
	 * @return the numeroAcciones
	 */
	public Integer getNumeroAcciones() {
		return numeroAcciones;
	}
	/**
	 * @param numeroAcciones the numeroAcciones to set
	 */
	public void setNumeroAcciones(Integer numeroAcciones) {
		this.numeroAcciones = numeroAcciones;
	}
	/**
	 * @return the verifico
	 */
	public String getVerifico() {
		return verifico;
	}
	/**
	 * @param verifico the verifico to set
	 */
	public void setVerifico(String verifico) {
		this.verifico = verifico;
	}
	/**
	 * @return the eficaciaGlobal
	 */
	public Double getEficaciaGlobal() {
		return eficaciaGlobal;
	}
	/**
	 * @param eficaciaGlobal the eficaciaGlobal to set
	 */
	public void setEficaciaGlobal(Double eficaciaGlobal) {
		this.eficaciaGlobal = eficaciaGlobal;
	}
	/**
	 * @return the eficaciaGlobalV
	 */
	public Double getEficaciaGlobalV() {
		return eficaciaGlobalV;
	}
	/**
	 * @param eficaciaGlobalV the eficaciaGlobalV to set
	 */
	public void setEficaciaGlobalV(Double eficaciaGlobalV) {
		this.eficaciaGlobalV = eficaciaGlobalV;
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
	 * @return the acciones
	 */
	public List<Accion> getAcciones() {
		return acciones;
	}
	/**
	 * @param acciones the acciones to set
	 */
	public void setAcciones(List<Accion> acciones) {
		this.acciones = acciones;
	}
	/**
	 * @return the pondero
	 */
	public String getPondero() {
		return pondero;
	}
	/**
	 * @param pondero the pondero to set
	 */
	public void setPondero(String pondero) {
		this.pondero = pondero;
	}
	/**
	 * @return the costoCalidad
	 */
	public Double getCostoCalidad() {
		return costoCalidad;
	}
	/**
	 * @param costoCalidad the costoCalidad to set
	 */
	public void setCostoCalidad(Double costoCalidad) {
		this.costoCalidad = costoCalidad;
	}
	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}
	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	/**
	 * @return the gestor
	 */
	public String getGestor() {
		return gestor;
	}
	/**
	 * @param gestor the gestor to set
	 */
	public void setGestor(String gestor) {
		this.gestor = gestor;
	}
	/**
	 * @return the incidenteRelacionado
	 */
	public Long getIncidenteRelacionado() {
		return incidenteRelacionado;
	}
	/**
	 * @param incidenteRelacionado the incidenteRelacionado to set
	 */
	public void setIncidenteRelacionado(Long incidenteRelacionado) {
		this.incidenteRelacionado = incidenteRelacionado;
	}
	/**
	 * @return the incidenteRelacionadoS
	 */
	public String getIncidenteRelacionadoS() {
		return incidenteRelacionadoS;
	}
	/**
	 * @param incidenteRelacionadoS the incidenteRelacionadoS to set
	 */
	public void setIncidenteRelacionadoS(String incidenteRelacionadoS) {
		this.incidenteRelacionadoS = incidenteRelacionadoS;
	}
	/**
	 * @return the folioIncidenteRelacionado
	 */
	public String getFolioIncidenteRelacionado() {
		return folioIncidenteRelacionado;
	}
	/**
	 * @param folioIncidenteRelacionado the folioIncidenteRelacionado to set
	 */
	public void setFolioIncidenteRelacionado(String folioIncidenteRelacionado) {
		this.folioIncidenteRelacionado = folioIncidenteRelacionado;
	}
	/**
	 * @return the pendientePonderacion
	 */
	public Long getPendientePonderacion() {
		return pendientePonderacion;
	}
	/**
	 * @param pendientePonderacion the pendientePonderacion to set
	 */
	public void setPendientePonderacion(Long pendientePonderacion) {
		this.pendientePonderacion = pendientePonderacion;
	}
	/**
	 * @return the idGestiono
	 */
	public Long getIdGestiono() {
		return idGestiono;
	}
	/**
	 * @param idGestiono the idGestiono to set
	 */
	public void setIdGestiono(Long idGestiono) {
		this.idGestiono = idGestiono;
	}
	/**
	 * @return the fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}
	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}
	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the horasHombre
	 */
	public Double getHorasHombre() {
		return horasHombre;
	}
	/**
	 * @param horasHombre the horasHombre to set
	 */
	public void setHorasHombre(Double horasHombre) {
		this.horasHombre = horasHombre;
	}
	/**
	 * @return the costoHoraHombre
	 */
	public Double getCostoHoraHombre() {
		return costoHoraHombre;
	}
	/**
	 * @param costoHoraHombre the costoHoraHombre to set
	 */
	public void setCostoHoraHombre(Double costoHoraHombre) {
		this.costoHoraHombre = costoHoraHombre;
	}
	/**
	 * @return the idSubprocesoRelacionado
	 */
	public Long getIdSubprocesoRelacionado() {
		return idSubprocesoRelacionado;
	}
	/**
	 * @param idSubprocesoRelacionado the idSubprocesoRelacionado to set
	 */
	public void setIdSubprocesoRelacionado(Long idSubprocesoRelacionado) {
		this.idSubprocesoRelacionado = idSubprocesoRelacionado;
	}

}