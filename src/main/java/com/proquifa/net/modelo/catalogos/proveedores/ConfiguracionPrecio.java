/**
 * 
 */
package com.proquifa.net.modelo.catalogos.proveedores;

import java.util.Date;

import com.proquifa.net.modelo.catalogos.agenteAduanal.AgenteAConfigPrecio;


/**
 * @author viviana.romero
 *
 */
public class ConfiguracionPrecio {
	private Long idConfiguracionPrecio;
	private String tipo;
	private String subtipo;
	private String control;
	private String industria;
	private Long idProveedor;
	private Long idCostoFactor;
	private Long idTiempoEntrega;
	private Date fechaUltimaActualizacion;
	private Integer noProductos;
	private AgenteAConfigPrecio aacp;
	private Date caducidad;
	private Boolean restringirStock;
	private Boolean restringirFExpress;
	private Boolean restringirDistribuidor;
	private Integer noClasificaciones;

	/**
	 * @return the caducidad
	 */
	public Date getCaducidad() {
		return caducidad;
	}
	/**
	 * @param caducidad the caducidad to set
	 */
	public void setCaducidad(Date caducidad) {
		this.caducidad = caducidad;
	}
	/**
	 * @return the restringirStock
	 */
	public Boolean getRestringirStock() {
		return restringirStock;
	}
	/**
	 * @param restringirStock the restringirStock to set
	 */
	public void setRestringirStock(Boolean restringirStock) {
		this.restringirStock = restringirStock;
	}
	/**
	 * @return the restringirFExpress
	 */
	public Boolean getRestringirFExpress() {
		return restringirFExpress;
	}
	/**
	 * @param restringirFExpress the restringirFExpress to set
	 */
	public void setRestringirFExpress(Boolean restringirFExpress) {
		this.restringirFExpress = restringirFExpress;
	}
	/**
	 * @return the idConfiguracionPrecio
	 */
	public Long getIdConfiguracionPrecio() {
		return idConfiguracionPrecio;
	}
	/**
	 * @param idConfiguracionPrecio the idConfiguracionPrecio to set
	 */
	public void setIdConfiguracionPrecio(Long idConfiguracionPrecio) {
		this.idConfiguracionPrecio = idConfiguracionPrecio;
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
	 * @return the subtipo
	 */
	public String getSubtipo() {
		return subtipo;
	}
	/**
	 * @param subtipo the subtipo to set
	 */
	public void setSubtipo(String subtipo) {
		this.subtipo = subtipo;
	}
	/**
	 * @return the control
	 */
	public String getControl() {
		return control;
	}
	/**
	 * @param control the control to set
	 */
	public void setControl(String control) {
		this.control = control;
	}
	/**
	 * @return the industria
	 */
	public String getIndustria() {
		return industria;
	}
	/**
	 * @param industria the industria to set
	 */
	public void setIndustria(String industria) {
		this.industria = industria;
	}
	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the idCostoFactor
	 */
	public Long getIdCostoFactor() {
		return idCostoFactor;
	}
	/**
	 * @param idCostoFactor the idCostoFactor to set
	 */
	public void setIdCostoFactor(Long idCostoFactor) {
		this.idCostoFactor = idCostoFactor;
	}
	/**
	 * @return the idTiempoEntrega
	 */
	public Long getIdTiempoEntrega() {
		return idTiempoEntrega;
	}
	/**
	 * @param idTiempoEntrega the idTiempoEntrega to set
	 */
	public void setIdTiempoEntrega(Long idTiempoEntrega) {
		this.idTiempoEntrega = idTiempoEntrega;
	}
	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}
	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	/**
	 * @return the noProductos
	 */
	public Integer getNoProductos() {
		return noProductos;
	}
	/**
	 * @param noProductos the noProductos to set
	 */
	public void setNoProductos(Integer noProductos) {
		this.noProductos = noProductos;
	}
	/**
	 * @return the aacp
	 */
	public AgenteAConfigPrecio getAacp() {
		return aacp;
	}
	/**
	 * @param aacp the aacp to set
	 */
	public void setAacp(AgenteAConfigPrecio aacp) {
		this.aacp = aacp;
	}
	/**
	 * @return the restringirDistribuidor
	 */
	public Boolean getRestringirDistribuidor() {
		return restringirDistribuidor;
	}
	/**
	 * @param restringirDistribuidor the restringirDistribuidor to set
	 */
	public void setRestringirDistribuidor(Boolean restringirDistribuidor) {
		this.restringirDistribuidor = restringirDistribuidor;
	}
	/**
	 * @return the noClasificaciones
	 */
	public Integer getNoClasificaciones() {
		return noClasificaciones;
	}
	/**
	 * @param noClasificaciones the noClasificaciones to set
	 */
	public void setNoClasificaciones(Integer noClasificaciones) {
		this.noClasificaciones = noClasificaciones;
	}
	
}
