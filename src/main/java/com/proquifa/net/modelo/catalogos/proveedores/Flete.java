package com.proquifa.net.modelo.catalogos.proveedores;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.RutaFlete;

public class Flete {

	private Long idFlete;
	private String concepto;
	private Double monto;
	private Date fua;
	private String leyenda;
	private String tiempoEntrega;
	private Boolean habilitado;
	private List<RutaFlete> ruta;
	private String concatenaRuta;
	

	/**
	 * @return the idFlete
	 */
	public Long getIdFlete() {
		return idFlete;
	}
	/**
	 * @param idFlete the idFlete to set
	 */
	public void setIdFlete(Long idFlete) {
		this.idFlete = idFlete;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}
	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}
	/**
	 * @return the leyenda
	 */
	public String getLeyenda() {
		return leyenda;
	}
	/**
	 * @param leyenda the leyenda to set
	 */
	public void setLeyenda(String leyenda) {
		this.leyenda = leyenda;
	}
	/**
	 * @return the tiempoEntrega
	 */
	public String getTiempoEntrega() {
		return tiempoEntrega;
	}
	/**
	 * @param tiempoEntrega the tiempoEntrega to set
	 */
	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}
	/**
	 * @return the habilitado
	 */
	public Boolean getHabilitado() {
		return habilitado;
	}
	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	/**
	 * @return the ruta
	 */
	public List<RutaFlete> getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(List<RutaFlete> ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the concatenaRuta
	 */
	public String getConcatenaRuta() {
		return concatenaRuta;
	}
	/**
	 * @param concatenaRuta the concatenaRuta to set
	 */
	public void setConcatenaRuta(String concatenaRuta) {
		this.concatenaRuta = concatenaRuta;
	}
	
	
}
