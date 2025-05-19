package com.proquifa.net.modelo.despachos.mensajero;

import java.io.Serializable;

public class ComentaiosRutaDP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2734992849931008549L;
	private int idComentaiosRutaDP;
	private String razonesEntrega;
	private String tipoJustificacion;
	private String rutaDP;
	private String folioFactura;
	
	public int getIdComentaiosRutaDP() {
		return idComentaiosRutaDP;
	}
	public void setIdComentaiosRutaDP(int idComentaiosRutaDP) {
		this.idComentaiosRutaDP = idComentaiosRutaDP;
	}
	public String getRazonesEntrega() {
		return razonesEntrega;
	}
	public void setRazonesEntrega(String razonesEntrega) {
		this.razonesEntrega = razonesEntrega;
	}
	public String getTipoJustificacion() {
		return tipoJustificacion;
	}
	public void setTipoJustificacion(String tipoJustificacion) {
		this.tipoJustificacion = tipoJustificacion;
	}
	public String getRutaDP() {
		return rutaDP;
	}
	public void setRutaDP(String rutaDP) {
		this.rutaDP = rutaDP;
	}
	public String getFolioFactura() {
		return folioFactura;
	}
	public void setFolioFactura(String folioFactura) {
		this.folioFactura = folioFactura;
	}

}
