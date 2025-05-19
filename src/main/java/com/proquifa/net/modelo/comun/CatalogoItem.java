/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.io.Serializable;
import java.util.List;

/**
 * @author vaguirre
 * 
 */
@SuppressWarnings("serial")
public class CatalogoItem implements Serializable {
	private Long llave;
	private String valor;
	private String valor1;
	private String valor2;
	private Integer valor3;
	private String tipoEmpleado;
	private String nombre;
	private boolean selected;
	private boolean activo;
	//Para el llenado de Combos
	private List<CatalogoItem> usoDeCFDI;
	private List<CatalogoItem> metodoPago;
	private List<CatalogoItem> claveUnidad;
	private List<CatalogoItem> claveProdServ;
	private List<CatalogoItem> formaPago;
	
	private List<CatalogoItem> lstFoliosPoliza;
	
	

	public String getValor1() {
		return valor1;
	}

	public void setValor1(String valor1) {
		this.valor1 = valor1;
	}

	public String getValor2() {
		return valor2;
	}

	public void setValor2(String valor2) {
		this.valor2 = valor2;
	}

	public Integer getValor3() {
		return valor3;
	}

	public void setValor3(Integer valor3) {
		this.valor3 = valor3;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public List<CatalogoItem> getLstFoliosPoliza() {
		return lstFoliosPoliza;
	}

	public void setLstFoliosPoliza(List<CatalogoItem> lstFoliosPoliza) {
		this.lstFoliosPoliza = lstFoliosPoliza;
	}

	/**
	 * @return the formaPago
	 */
	public List<CatalogoItem> getFormaPago() {
		return formaPago;
	}

	/**
	 * @param formaPago the formaPago to set
	 */
	public void setFormaPago(List<CatalogoItem> formaPago) {
		this.formaPago = formaPago;
	}

	/**
	 * @return the llave
	 */
	public Long getLlave() {
		return llave;
	}

	/**
	 * @param llave the llave to set
	 */
	public void setLlave(Long llave) {
		this.llave = llave;
	}

	/**
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

	/**
	 * @param tipoEmpleado the tipoEmpleado to set
	 */
	public void setTipoEmpleado(String tipoEmpleado) {
		this.tipoEmpleado = tipoEmpleado;
	}

	/**
	 * @return the tipoEmpleado
	 */
	public String getTipoEmpleado() {
		return tipoEmpleado;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the usoDeCFDI
	 */
	public List<CatalogoItem> getUsoDeCFDI() {
		return usoDeCFDI;
	}

	/**
	 * @param usoDeCFDI the usoDeCFDI to set
	 */
	public void setUsoDeCFDI(List<CatalogoItem> usoDeCFDI) {
		this.usoDeCFDI = usoDeCFDI;
	}

	/**
	 * @return the metodoPago
	 */
	public List<CatalogoItem> getMetodoPago() {
		return metodoPago;
	}

	/**
	 * @param metodoPago the metodoPago to set
	 */
	public void setMetodoPago(List<CatalogoItem> metodoPago) {
		this.metodoPago = metodoPago;
	}

	/**
	 * @return the claveUnidad
	 */
	public List<CatalogoItem> getClaveUnidad() {
		return claveUnidad;
	}

	/**
	 * @param claveUnidad the claveUnidad to set
	 */
	public void setClaveUnidad(List<CatalogoItem> claveUnidad) {
		this.claveUnidad = claveUnidad;
	}

	/**
	 * @return the claveProdServ
	 */
	public List<CatalogoItem> getClaveProdServ() {
		return claveProdServ;
	}

	/**
	 * @param claveProdServ the claveProdServ to set
	 */
	public void setClaveProdServ(List<CatalogoItem> claveProdServ) {
		this.claveProdServ = claveProdServ;
	}
}
