/**
 * 
 */
package com.proquifa.net.modelo.despachos.asuntosregulatorios;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ymendez
 *
 */
public class GestionarPap implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3039265259384949413L;
	private Integer idCliente;
	private String cliente;
	private String cantidad;
	private String unidad;
	private Integer idProducto;
	private String concepto;
	private Double monto;
	private Integer piezas;
	private Integer idPPedido;
	private Integer idPedido;
	private Date ftramite;
	private Date fee;
	private Double precio;
	private String  cpedido;
	
	/**
	 * 
	 */
	public GestionarPap() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
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
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
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
	 * @return the piezas
	 */
	public Integer getPiezas() {
		return piezas;
	}

	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}

	/**
	 * @return the idPPedido
	 */
	public Integer getIdPPedido() {
		return idPPedido;
	}

	/**
	 * @param idPPedido the idPPedido to set
	 */
	public void setIdPPedido(Integer idPPedido) {
		this.idPPedido = idPPedido;
	}

	/**
	 * @return the ftramite
	 */
	public Date getFtramite() {
		return ftramite;
	}

	/**
	 * @param ftramite the ftramite to set
	 */
	public void setFtramite(Date ftramite) {
		this.ftramite = ftramite;
	}

	/**
	 * @return the fee
	 */
	public Date getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(Date fee) {
		this.fee = fee;
	}

	/**
	 * @return the precio
	 */
	public Double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	public String getCpedido() {
		return cpedido;
	}

	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}
	

}
