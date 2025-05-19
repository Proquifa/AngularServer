package com.proquifa.net.modelo.catalogos.proveedores;

import java.util.Date;
import java.util.List;

public class Logistica {
	
	private Long idProveedor;
//	private List<ProveedorTiempoEntrega> tiempoEntrega;
	private List<Flete> flete;
	private Flete fleteExpress;
	private Date fua;
	
	
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
	 * @return the tiempoEntrega
	 */
//	public List<ProveedorTiempoEntrega> getTiempoEntrega() {
//		return tiempoEntrega;
//	}
	/**
	 * @param tiempoEntrega the tiempoEntrega to set
	 */
//	public void setTiempoEntrega(List<ProveedorTiempoEntrega> tiempoEntrega) {
//		this.tiempoEntrega = tiempoEntrega;
//	}
	/**
	 * @return the flete
	 */
	public List<Flete> getFlete() {
		return flete;
	}
	/**
	 * @param flete the flete to set
	 */
	public void setFlete(List<Flete> flete) {
		this.flete = flete;
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
	 * @return the fleteExpress
	 */
	public Flete getFleteExpress() {
		return fleteExpress;
	}
	/**
	 * @param fleteExpress the fleteExpress to set
	 */
	public void setFleteExpress(Flete fleteExpress) {
		this.fleteExpress = fleteExpress;
	}
	

}
