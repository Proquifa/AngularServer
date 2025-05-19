package com.proquifa.net.modelo.comun;

public class DocumentoAdjunto {
	
	private String nombre;
	private String titulo;
	private String ruta;
	private byte[] archivo;
	private int idArchivoAdjunto;
	private int idServicio;
	private String extension;
	private String asunto;
	private String descripcion;
	
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
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public byte[] getArchivo() {
		return archivo;
	}
	public void setArchivo(byte[] archivo) {
		this.archivo = archivo;
	}
	public int getIdArchivoAdjunto() {
		return idArchivoAdjunto;
	}
	public void setIdArchivoAdjunto(int idArchivoAdjunto) {
		this.idArchivoAdjunto = idArchivoAdjunto;
	}
	public int getIdServicio() {
		return idServicio;
	}
	public void setIdServicio(int idServicio) {
		this.idServicio = idServicio;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
