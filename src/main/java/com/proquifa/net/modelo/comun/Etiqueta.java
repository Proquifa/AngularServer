package com.proquifa.net.modelo.comun;

public class Etiqueta {

	private String Descripcion;
	private String folio;
	private String extension;
	private String justificacion;
	private byte[] cadenaDeBytes;
	
	//SuperClass
	public Etiqueta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDescripcion() {
		return Descripcion;
	}

	public void setDescripcion(String descripcion) {
		Descripcion = descripcion;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public byte[] getCadenaDeBytes() {
		return cadenaDeBytes;
	}

	public void setCadenaDeBytes(byte[] cadenaDeBytes) {
		this.cadenaDeBytes = cadenaDeBytes;
	}
	
	


	
}
