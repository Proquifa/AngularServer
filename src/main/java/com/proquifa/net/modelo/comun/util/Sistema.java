package com.proquifa.net.modelo.comun.util;

public class Sistema {
	
	private int idSistema;
	private String nombre;
	private String version;
	private String comentario;
	private String url;
	
	/**
	 * @return the idSistema
	 */
	public int getIdSistema() {
		return idSistema;
	}
	
	/**
	 * @param idSistema the idSistema to set
	 */
	public void setIdSistema(int idSistema) {
		this.idSistema = idSistema;
	}
	
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	/**
	 * @return the comentarios
	 */
	public String getComentario() {
		return comentario;
	}
	
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
		
}
