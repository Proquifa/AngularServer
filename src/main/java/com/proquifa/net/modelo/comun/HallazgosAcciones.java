package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;

public class HallazgosAcciones {
	
	private Integer idHallazgoAccion;
	private String tipo;
	private String descripcion;
	private String descripcionAccion;
    private Long idVisita;
    
    private Date ferealizacion;
    private Date frealizacion;
    private String observaciones;
    private String tipoHallazgo;
    private String competencia;
    private List<CatalogoItem> marcas;
    private DocumentoSolicitudVisita documento;
    
    private Integer usuarioAsociado;
	private Boolean checkSeleccionado;
	private Boolean rectDescVisible;
	private String ferealizacion2;
	private String motivoDescartado;
	private Boolean descartado;
    
	/**
	 * 
	 */
	public HallazgosAcciones() {
		super();
	}
	
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Long getIdVisita() {
		return idVisita;
	}
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}
	/**
	 * @return the idHallazgoAccion
	 */
	public Integer getIdHallazgoAccion() {
		return idHallazgoAccion;
	}
	/**
	 * @param idHallazgoAccion the idHallazgoAccion to set
	 */
	public void setIdHallazgoAccion(Integer idHallazgoAccion) {
		this.idHallazgoAccion = idHallazgoAccion;
	}
	/**
	 * @return the ferealizacion
	 */
	public Date getFerealizacion() {
		return ferealizacion;
	}
	/**
	 * @param ferealizacion the ferealizacion to set
	 */
	public void setFerealizacion(Date ferealizacion) {
		this.ferealizacion = ferealizacion;
	}
	/**
	 * @return the frealizacion
	 */
	public Date getFrealizacion() {
		return frealizacion;
	}
	/**
	 * @param frealizacion the frealizacion to set
	 */
	public void setFrealizacion(Date frealizacion) {
		this.frealizacion = frealizacion;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the tipoHallazgo
	 */
	public String getTipoHallazgo() {
		return tipoHallazgo;
	}
	/**
	 * @param tipoHallazgo the tipoHallazgo to set
	 */
	public void setTipoHallazgo(String tipoHallazgo) {
		this.tipoHallazgo = tipoHallazgo;
	}
	/**
	 * @return the competencia
	 */
	public String getCompetencia() {
		return competencia;
	}
	/**
	 * @param competencia the competencia to set
	 */
	public void setCompetencia(String competencia) {
		this.competencia = competencia;
	}
	/**
	 * @return the marcas
	 */
	public List<CatalogoItem> getMarcas() {
		return marcas;
	}
	/**
	 * @param marcas the marcas to set
	 */
	public void setMarcas(List<CatalogoItem> marcas) {
		this.marcas = marcas;
	}


	/**
	 * @return the documento
	 */
	public DocumentoSolicitudVisita getDocumento() {
		return documento;
	}


	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(DocumentoSolicitudVisita documento) {
		this.documento = documento;
	}


	public Integer getUsuarioAsociado() {
		return usuarioAsociado;
	}


	public void setUsuarioAsociado(Integer usuarioAsociado) {
		this.usuarioAsociado = usuarioAsociado;
	}


	public Boolean getCheckSeleccionado() {
		return checkSeleccionado;
	}


	public void setCheckSeleccionado(Boolean checkSeleccionado) {
		this.checkSeleccionado = checkSeleccionado;
	}


	public Boolean getRectDescVisible() {
		return rectDescVisible;
	}


	public void setRectDescVisible(Boolean rectDescVisible) {
		this.rectDescVisible = rectDescVisible;
	}


	public String getFerealizacion2() {
		return ferealizacion2;
	}


	public void setFerealizacion2(String ferealizacion2) {
		this.ferealizacion2 = ferealizacion2;
	}


	public Boolean getDescartado() {
		return descartado;
	}


	public void setDescartado(Boolean descartado) {
		this.descartado = descartado;
	}


	public String getDescripcionAccion() {
		return descripcionAccion;
	}


	public void setDescripcionAccion(String descripcionAccion) {
		this.descripcionAccion = descripcionAccion;
	}


	public String getMotivoDescartado() {
		return motivoDescartado;
	}


	public void setMotivoDescartado(String motivoDescartado) {
		this.motivoDescartado = motivoDescartado;
	}
}
