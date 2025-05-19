/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ymendez
 *
 */
public class Remision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1204353288664157978L;
	private Integer idRemision;
	private Integer factura;
	private Date fecha;
	private String cpago;
	private Integer idCliente;
	private String moneda;
	private String importe;
	private Double iva;
	private String estado;
	private String fpor;
	private String cotiza;
	private String pedido;
	private String tipoCambio;
	private String cpedido;
	private String direccion;
	private String rfc;
	private String descripcion;
	private String cliente;
	
	private List<PRemision> partidaRemisiones;
	
	private String idPPedidos;
	
	private String total;
	private String letraTotal;
	
	/**
	 * 
	 */
	public Remision() {
		super();
		// TODO Auto-generated constructor stub
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
	 * @return the cpago
	 */
	public String getCpago() {
		return cpago;
	}

	/**
	 * @param cpago the cpago to set
	 */
	public void setCpago(String cpago) {
		this.cpago = cpago;
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
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}

	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	/**
	 * @return the iva
	 */
	public Double getIva() {
		return iva;
	}

	/**
	 * @param iva the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
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
	 * @return the cotiza
	 */
	public String getCotiza() {
		return cotiza;
	}

	/**
	 * @param cotiza the cotiza to set
	 */
	public void setCotiza(String cotiza) {
		this.cotiza = cotiza;
	}

	/**
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}

	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
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
	 * @return the partidaRemisiones
	 */
	public List<PRemision> getPartidaRemisiones() {
		return partidaRemisiones;
	}

	/**
	 * @param partidaRemisiones the partidaRemisiones to set
	 */
	public void setPartidaRemisiones(List<PRemision> partidaRemisiones) {
		this.partidaRemisiones = partidaRemisiones;
	}

	/**
	 * @return the idPPedidos
	 */
	public String getIdPPedidos() {
		return idPPedidos;
	}

	/**
	 * @param idPPedidos the idPPedidos to set
	 */
	public void setIdPPedidos(String idPPedidos) {
		this.idPPedidos = idPPedidos;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the tipoCambio
	 */
	public String getTipoCambio() {
		return tipoCambio;
	}

	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(String tipoCambio) {
		this.tipoCambio = tipoCambio;
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
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

	/**
	 * @return the letraTotal
	 */
	public String getLetraTotal() {
		return letraTotal;
	}

	/**
	 * @param letraTotal the letraTotal to set
	 */
	public void setLetraTotal(String letraTotal) {
		this.letraTotal = letraTotal;
	}
	
	
	
}
