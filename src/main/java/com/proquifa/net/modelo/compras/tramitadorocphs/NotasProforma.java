/**
 * 
 */
package com.proquifa.net.modelo.compras.tramitadorocphs;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class NotasProforma {
	private Long folio;
	private String nota;
	private Date fecha;
	private Boolean eliminar;
	/**
	 * @return the folio
	 */
	public Long getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}
	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
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
	 * @return the eliminar
	 */
	public Boolean getEliminar() {
		return eliminar;
	}
	/**
	 * @param eliminar the eliminar to set
	 */
	public void setEliminar(Boolean eliminar) {
		this.eliminar = eliminar;
	}
}
