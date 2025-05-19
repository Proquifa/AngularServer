/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;

/**
 * @author fmartinez
 *
 */
public class HistorialFactura {
	
	private String Comentarios;
	private Date fecha;
	private String usuario;
	private Long factura;
	private String facturadoPor;
	private String nombreCliente;
	private String nombreContacto;
	private String Puesto;
	private Date FEP;
	
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return Comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		Comentarios = comentarios;
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
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the factura
	 */
	public Long getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(Long factura) {
		this.factura = factura;
	}
	/**
	 * @return the facturadoPor
	 */
	public String getFacturadoPor() {
		return facturadoPor;
	}
	/**
	 * @param facturadoPor the facturadoPor to set
	 */
	public void setFacturadoPor(String facturadoPor) {
		this.facturadoPor = facturadoPor;
	}
	/**
	 * @return the nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}
	/**
	 * @param nombreCliente the nombreCliente to set
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	/**
	 * @return the nombreContacto
	 */
	public String getNombreContacto() {
		return nombreContacto;
	}
	/**
	 * @param nombreContacto the nombreContacto to set
	 */
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	/**
	 * @return the puesto
	 */
	public String getPuesto() {
		return Puesto;
	}
	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(String puesto) {
		Puesto = puesto;
	}
	/**
	 * @return the fEP
	 */
	public Date getFEP() {
		return FEP;
	}
	/**
	 * @param fep the fEP to set
	 */
	public void setFEP(Date fep) {
		FEP = fep;
	}
}