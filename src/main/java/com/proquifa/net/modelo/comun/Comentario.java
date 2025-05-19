/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author misael.camanos
 *
 */
public class Comentario {
	
	private Long idComentario;
	private Long idCliente;
	private String seccion;
	private boolean eliminar;
	private String tema;
	private String contenido;
	
	/**
	 * @return the idComentario
	 */
	public Long getIdComentario() {
		return idComentario;
	}
	/**
	 * @param idComentario the idComentario to set
	 */
	public void setIdComentario(Long idComentario) {
		this.idComentario = idComentario;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the seccion
	 */
	public String getSeccion() {
		return seccion;
	}
	/**
	 * @param seccion the seccion to set
	 */
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	/**
	 * @return the eliminar
	 */
	public boolean isEliminar() {
		return eliminar;
	}
	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}
	/**
	 * @return the tema
	 */
	public String getTema() {
		return tema;
	}
	/**
	 * @param tema the tema to set
	 */
	public void setTema(String tema) {
		this.tema = tema;
	}
	/**
	 * @return the contenido
	 */
	public String getContenido() {
		return contenido;
	}
	/**
	 * @param contenido the contenido to set
	 */
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	
	
}
