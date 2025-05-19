/**
 * 
 */
package com.proquifa.net.modelo.despachos.packinglist;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ymendez
 *
 */

public class PackingList implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6635057839674294522L;
	private Integer idPackingList;
	private Date fecha;
	private String folio;
	private String  ruta;
	private String zona;
	private Integer idUsuario;
	private String video;
	
	private List<PartidaPackingList> partidaPackingList;
	
	/**
	 * 
	 */
	public PackingList() {
		super();
	}
	
	public PackingList(Integer idPackingList) {
		super();
		this.idPackingList = idPackingList;
	}

	/**
	 * @param idPackingList
	 * @param fecha
	 * @param folio
	 * @param embalar
	 * @param partidasEmbalar
	 */
	public PackingList(Integer idPackingList, Date fecha, String folio,List<PartidaPackingList> partidaPackingList) {
		super();
		this.idPackingList = idPackingList;
		this.fecha = fecha;
		this.folio = folio;
		this.partidaPackingList = partidaPackingList;
	}


	
	
	
	
	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the ruta
	 *
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 *
	 */

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the zona
	 *
	 */
	public String getZona() {
		return zona;
	}
	/**
	 * @param zona the zona to set
	 *
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}

	/**
	 * 
	 * @return the idPackingList
	 */
	public Integer getIdPackingList() {
		return idPackingList;
	}

	/**
	 * @param idPackingList the idPackingList to set
	 */
	public void setIdPackingList(Integer idPackingList) {
		this.idPackingList = idPackingList;
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
	 * @return the partidaPackingList
	 */
	public List<PartidaPackingList> getPartidaPackingList() {
		return partidaPackingList;
	}

	/**
	 * @param partidaPackingList the partidaPackingList to set
	 */
	public void setPartidaPackingList(List<PartidaPackingList> partidaPackingList) {
		this.partidaPackingList = partidaPackingList;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}
	
	
}
