/**
 * 
 */
package com.proquifa.net.modelo.despachos.mensajero;

import java.util.Date;

/**
 * @author ymendez
 *
 */
public class Ruta {

	private Integer idRuta;
	private String folio;
	private Date fecha;
	private String justificacion;
	private Integer calificacion;
	
	private Integer idResponsable;
	
	/**
	 * 
	 */
	public Ruta() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idRuta
	 */
	public Integer getIdRuta() {
		return idRuta;
	}

	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(Integer idRuta) {
		this.idRuta = idRuta;
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
	 * @return the calificacion
	 */
	public Integer getCalificacion() {
		return calificacion;
	}

	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}

	/**
	 * @return the idResponsable
	 */
	public Integer getIdResponsable() {
		return idResponsable;
	}

	/**
	 * @param idResponsable the idResponsable to set
	 */
	public void setIdResponsable(Integer idResponsable) {
		this.idResponsable = idResponsable;
	}
	
	
}
