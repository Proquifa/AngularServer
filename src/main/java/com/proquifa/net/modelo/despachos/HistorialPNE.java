/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class HistorialPNE {
	private Date fecha;
	private String razones;
	private String folioRuta;
	private String mensajero;
	
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
	 * @return the razones
	 */
	public String getRazones() {
		return razones;
	}
	/**
	 * @param razones the razones to set
	 */
	public void setRazones(String razones) {
		this.razones = razones;
	}
	/**
	 * @return the folioRuta
	 */
	public String getFolioRuta() {
		return folioRuta;
	}
	/**
	 * @param folioRuta the folioRuta to set
	 */
	public void setFolioRuta(String folioRuta) {
		this.folioRuta = folioRuta;
	}
	/**
	 * @return the mensajero
	 */
	public String getMensajero() {
		return mensajero;
	}
	/**
	 * @param mensajero the mensajero to set
	 */
	public void setMensajero(String mensajero) {
		this.mensajero = mensajero;
	}
}