/**
 * 
 */
package com.proquifa.net.modelo.catalogos.clientes;

import java.util.List;

import com.proquifa.net.modelo.catalogos.proveedores.TiempoEntrega;


/**
 * @author viviana.romero
 *
 */
public class ParametrosOfertaCliente {
	
	private Long idCliente;
	private String nivelConfigPrecio; 
	private Long idConfigPrecioProd;
	private String tipoNivelIngreso; 
	private Double factor; 
	private Long idAgente; 
	private Long idLugar;
	private Long idConcepto; 
	private Double costoFijo;
	private List<TiempoEntrega> tiemposEntregaRuta;
	private Boolean compuestaCostoF;
	private Boolean compuestaFactorU;
	private Boolean restablecer;
	private Boolean temporal;
	private Integer duracionTemp;
	private Boolean precioListaAnterior;
	private Long idProveedor;
	private Long idConfigFamilia;
	
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public String getNivelConfigPrecio() {
		return nivelConfigPrecio;
	}
	public void setNivelConfigPrecio(String nivelConfigPrecio) {
		this.nivelConfigPrecio = nivelConfigPrecio;
	}
	public Long getIdConfigPrecioProd() {
		return idConfigPrecioProd;
	}
	public void setIdConfigPrecioProd(Long idConfigPrecioProd) {
		this.idConfigPrecioProd = idConfigPrecioProd;
	}
	public String getTipoNivelIngreso() {
		return tipoNivelIngreso;
	}
	public void setTipoNivelIngreso(String tipoNivelIngreso) {
		this.tipoNivelIngreso = tipoNivelIngreso;
	}
	public Double getFactor() {
		return factor;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	public Long getIdAgente() {
		return idAgente;
	}
	public void setIdAgente(Long idAgente) {
		this.idAgente = idAgente;
	}
	public Long getIdLugar() {
		return idLugar;
	}
	public void setIdLugar(Long idLugar) {
		this.idLugar = idLugar;
	}
	public Long getIdConcepto() {
		return idConcepto;
	}
	public void setIdConcepto(Long idConcepto) {
		this.idConcepto = idConcepto;
	}
	
	public List<TiempoEntrega> getTiemposEntregaRuta() {
		return tiemposEntregaRuta;
	}
	public void setTiemposEntregaRuta(List<TiempoEntrega> tiemposEntregaRuta) {
		this.tiemposEntregaRuta = tiemposEntregaRuta;
	}
	public Boolean getCompuestaCostoF() {
		return compuestaCostoF;
	}
	public void setCompuestaCostoF(Boolean compuestaCostoF) {
		this.compuestaCostoF = compuestaCostoF;
	}
	public Boolean getCompuestaFactorU() {
		return compuestaFactorU;
	}
	public void setCompuestaFactorU(Boolean compuestaFactorU) {
		this.compuestaFactorU = compuestaFactorU;
	}
	public Boolean getRestablecer() {
		return restablecer;
	}
	public void setRestablecer(Boolean restablecer) {
		this.restablecer = restablecer;
	}
	public Double getCostoFijo() {
		return costoFijo;
	}
	public void setCostoFijo(Double costoFijo) {
		this.costoFijo = costoFijo;
	}
	public Boolean getTemporal() {
		return temporal;
	}
	public void setTemporal(Boolean temporal) {
		this.temporal = temporal;
	}
	public Integer getDuracionTemp() {
		return duracionTemp;
	}
	public void setDuracionTemp(Integer duracionTemp) {
		this.duracionTemp = duracionTemp;
	}
	public Boolean getPrecioListaAnterior() {
		return precioListaAnterior;
	}
	public void setPrecioListaAnterior(Boolean precioListaAnterior) {
		this.precioListaAnterior = precioListaAnterior;
	}
	public Long getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	public Long getIdConfigFamilia() {
		return idConfigFamilia;
	}
	public void setIdConfigFamilia(Long idConfigFamilia) {
		this.idConfigFamilia = idConfigFamilia;
	}
	
	
	

}
