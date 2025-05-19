/**
 * 
 */
package com.proquifa.net.modelo.despachos.packinglist;

import java.util.Date;

import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.despachos.EmbalarPedido;

/**
 * @author ymendez
 *
 */
public class PartidaPackingList {

	
	private Integer idPartidaPackingList;
	private Date fecha;
	private String folio;
	private String tipo;
	
	private PackingList packingList;
	
	private EmbalarPedido embalar;
	
	private Parametro parametro;

	/**
	 * 
	 */
	public PartidaPackingList() {
		super();
	}
	
	public void setPartidaPackingList(Integer idPackingList) {
		this.packingList = new PackingList(idPackingList);
	}

	/**
	 * @param idPartidaPackingList
	 * @param fecha
	 * @param folio
	 * @param tipo
	 * @param packingList
	 */
	public PartidaPackingList(Integer idPartidaPackingList, Date fecha, String folio, String tipo,
			PackingList packingList) {
		super();
		this.idPartidaPackingList = idPartidaPackingList;
		this.fecha = fecha;
		this.folio = folio;
		this.tipo = tipo;
		this.packingList = packingList;
	}


	/**
	 * @return the idPartidaPackingList
	 */
	public Integer getIdPartidaPackingList() {
		return idPartidaPackingList;
	}

	/**
	 * @param idPartidaPackingList the idPartidaPackingList to set
	 */
	public void setIdPartidaPackingList(Integer idPartidaPackingList) {
		this.idPartidaPackingList = idPartidaPackingList;
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the packingList
	 */
	public PackingList getPackingList() {
		return packingList;
	}

	/**
	 * @param packingList the packingList to set
	 */
	public void setPackingList(PackingList packingList) {
		this.packingList = packingList;
	}

	/**
	 * @return the embalar
	 */
	public EmbalarPedido getEmbalar() {
		return embalar;
	}

	/**
	 * @param embalar the embalar to set
	 */
	public void setEmbalar(EmbalarPedido embalar) {
		this.embalar = embalar;
	}
	

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	
	
}
