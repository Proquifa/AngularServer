/**
 * 
 */
package com.proquifa.net.modelo.ventas.requisicion;

/**
 * @author jhidalgo
 *
 */
public class PRequisicion {
	
	private Integer idPartida;
	private Integer idRequisicion;
	private String marca;
	private String codigo;
	private String concepto;
	private Integer piezasACotizar;
	private Integer cantidad;
	private Integer cant;
	private String unidad;
	private Integer idDoctor;
	private Float precioU;
	/**
	 * @return the idPartida
	 */
	public Integer getIdPartida() {
		return idPartida;
	}
	/**
	 * @param idPartida the idPartida to set
	 */
	public void setIdPartida(Integer idPartida) {
		this.idPartida = idPartida;
	}
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
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
	 * @return the piezasACotizar
	 */
	public Integer getPiezasACotizar() {
		return piezasACotizar;
	}
	/**
	 * @param piezasACotizar the piezasACotizar to set
	 */
	public void setPiezasACotizar(Integer piezasACotizar) {
		this.piezasACotizar = piezasACotizar;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}
	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	/**
	 * @return the idDoctor
	 */
	public Integer getIdDoctor() {
		return idDoctor;
	}
	/**
	 * @param idDoctor the idDoctor to set
	 */
	public void setIdDoctor(Integer idDoctor) {
		this.idDoctor = idDoctor;
	}
	/**
	 * @return the precioU
	 */
	public Float getPrecioU() {
		return precioU;
	}
	/**
	 * @param precioU the precioU to set
	 */
	public void setPrecioU(Float precioU) {
		this.precioU = precioU;
	}
	public Integer getIdRequisicion() {
		return idRequisicion;
	}
	public void setIdRequisicion(Integer idRequisicion) {
		this.idRequisicion = idRequisicion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Integer getCant() {
		return cant;
	}
	public void setCant(Integer cant) {
		this.cant = cant;
	}
}
