package com.proquifa.net.modelo.ventas.visitas;

public class DocumentoSolicitudVisita {

	private Integer idDocumentoSolicitudVisita;
	private Integer idSolicitudVisita;
	private String nombre;
	private String titulo;
	private String descripcion;
	
	private byte[] archivo;
	
	/**
	 * 
	 */
	public DocumentoSolicitudVisita() {
		super();
	}
	
	public DocumentoSolicitudVisita(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Integer getIdDocumentoSolicitudVisita() {
		return idDocumentoSolicitudVisita;
	}
	public void setIdDocumentoSolicitudVisita(Integer idDocumentoSolicitudVisita) {
		this.idDocumentoSolicitudVisita = idDocumentoSolicitudVisita;
	}
	public Integer getIdSolicitudVisita() {
		return idSolicitudVisita;
	}
	public void setIdSolicitudVisita(Integer idSolicitudVisita) {
		this.idSolicitudVisita = idSolicitudVisita;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
}
