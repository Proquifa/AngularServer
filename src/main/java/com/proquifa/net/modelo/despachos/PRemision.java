/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.io.Serializable;

/**
 * @author ymendez
 *
 */
public class PRemision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7967130491954837785L;
	private Integer idPRemision; 
	private Integer factura;
	private String fpor;
	private String importe;
	private String cpedido;
	private Integer ppedido;
	private Integer part;
	private Integer cant;
	private String subTotal;
	private String descripcion;
	
	private Integer idRemision;
	
	/**
	 * 
	 */
	public PRemision() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idPRemision
	 */
	public Integer getIdPRemision() {
		return idPRemision;
	}

	/**
	 * @param idPRemision the idPRemision to set
	 */
	public void setIdPRemision(Integer idPRemision) {
		this.idPRemision = idPRemision;
	}

	/**
	 * @return the factura
	 */
	public Integer getFactura() {
		return factura;
	}

	/**
	 * @param factura the factura to set
	 */
	public void setFactura(Integer factura) {
		this.factura = factura;
	}

	/**
	 * @return the fpor
	 */
	public String getFpor() {
		return fpor;
	}

	/**
	 * @param fpor the fpor to set
	 */
	public void setFpor(String fpor) {
		this.fpor = fpor;
	}

	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}

	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}

	/**
	 * @return the ppedido
	 */
	public Integer getPpedido() {
		return ppedido;
	}

	/**
	 * @param ppedido the ppedido to set
	 */
	public void setPpedido(Integer ppedido) {
		this.ppedido = ppedido;
	}

	/**
	 * @return the part
	 */
	public Integer getPart() {
		return part;
	}

	/**
	 * @param part the part to set
	 */
	public void setPart(Integer part) {
		this.part = part;
	}

	/**
	 * @return the idRemision
	 */
	public Integer getIdRemision() {
		return idRemision;
	}

	/**
	 * @param idRemision the idRemision to set
	 */
	public void setIdRemision(Integer idRemision) {
		this.idRemision = idRemision;
	}

	/**
	 * @return the cant
	 */
	public Integer getCant() {
		return cant;
	}

	/**
	 * @param cant the cant to set
	 */
	public void setCant(Integer cant) {
		this.cant = cant;
	}

	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}

	/**
	 * @return the subTotal
	 */
	public String getSubTotal() {
		return subTotal;
	}

	/**
	 * @param subTotal the subTotal to set
	 */
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
