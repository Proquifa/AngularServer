/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;


/**
 * @author fmartinez
 *
 */
public class SolicitudCFDI {
	
	private String empresaFactura;
	private String serie;
	private String folio;
	private Date fecha;
	private String ano;
	private String mes;
	private String accion;
	private String esquemaNativoMysuite;
	private Boolean realizarPrueba;
	private String cfdi;
	
	
	/**
	 * @return the empresaFactura
	 */
	public String getEmpresaFactura() {
		return empresaFactura;
	}
	/**
	 * @param empresaFactura the empresaFactura to set
	 */
	public void setEmpresaFactura(String empresaFactura) {
		this.empresaFactura = empresaFactura;
	}
	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
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
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}
	/**
	 * @param ano the ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}
	/**
	 * @return the mes
	 */
	public String getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(String mes) {
		this.mes = mes;
	}
	/**
	 * @return the accion
	 */
	public String getAccion() {
		return accion;
	}
	/**
	 * @param accion the accion to set
	 */
	public void setAccion(String accion) {
		this.accion = accion;
	}
	/**
	 * @return the esquemaNativoMysuite
	 */
	public String getEsquemaNativoMysuite() {
		return esquemaNativoMysuite;
	}
	/**
	 * @param esquemaNativoMysuite the esquemaNativoMysuite to set
	 */
	public void setEsquemaNativoMysuite(String esquemaNativoMysuite) {
		this.esquemaNativoMysuite = esquemaNativoMysuite;
	}
	/**
	 * @return the realizarPrueba
	 */
	public Boolean getRealizarPrueba() {
		return realizarPrueba;
	}
	/**
	 * @param realizarPrueba the realizarPrueba to set
	 */
	public void setRealizarPrueba(Boolean realizarPrueba) {
		this.realizarPrueba = realizarPrueba;
	}
	
	public String getCfdi() {
		return cfdi;
	}
	
	public void setCfdi(String cfdi) {
		this.cfdi = cfdi;
	}	
}