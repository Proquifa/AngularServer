/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.io.Serializable;
import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;

/**
 * @author admin
 *
 */
public class PackingListJasper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3009735426001605702L;
	private Integer idPackingList;
	private String folio;
	private String cliente;
	private String calle;
	private String destino;
	private String titulo;
	private String contacto;
	private Integer ambiente;
	private Integer refrigeracion;
	private Integer congelacion;
	
	private String descripcion;
	private String codigo;
	private String folioEmpaque;
	private Integer cant;
	private String pedido;
	
	private String factura;
	
	private Integer totalPiezas;
	private String pedidoI;
	private List<PackingListJasper> listaPartidas;
		
	private Empresa empresa;
	private Cliente cli;
	
	/**
	 * 
	 */
	public PackingListJasper() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the idPackingList
	 */
	public Integer getIdPackingList() {
		return idPackingList;
	}
	/**
	 * @param idPackingList the idPackingList to set
	 */
	public void setIdPackingList(Integer idPackingList) {
		this.idPackingList = idPackingList;
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
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the ambiente
	 */
	public Integer getAmbiente() {
		return ambiente;
	}
	/**
	 * @param ambiente the ambiente to set
	 */
	public void setAmbiente(Integer ambiente) {
		this.ambiente = ambiente;
	}
	/**
	 * @return the refrigeracion
	 */
	public Integer getRefrigeracion() {
		return refrigeracion;
	}
	/**
	 * @param refrigeracion the refrigeracion to set
	 */
	public void setRefrigeracion(Integer refrigeracion) {
		this.refrigeracion = refrigeracion;
	}
	/**
	 * @return the congelacion
	 */
	public Integer getCongelacion() {
		return congelacion;
	}
	/**
	 * @param congelacion the congelacion to set
	 */
	public void setCongelacion(Integer congelacion) {
		this.congelacion = congelacion;
	}
	/**
	 * @return the concepto
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setDescripcion(String concepto) {
		this.descripcion = concepto;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the empaque
	 */
	public String getFolioEmpaque() {
		return folioEmpaque;
	}
	/**
	 * @param empaque the empaque to set
	 */
	public void setFolioEmpaque(String empaque) {
		this.folioEmpaque = empaque;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCant() {
		return cant;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCant(Integer cantidad) {
		this.cant = cantidad;
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
	 * @return the factura
	 */
	public String getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(String factura) {
		this.factura = factura;
	}
	/**
	 * @return the listaPartidas
	 */
	public List<PackingListJasper> getListaPartidas() {
		return listaPartidas;
	}
	/**
	 * @param listaPartidas the listaPartidas to set
	 */
	public void setListaPartidas(List<PackingListJasper> listaPartidas) {
		this.listaPartidas = listaPartidas;
	}
	/**
	 * @return the totalPiezas
	 */
	public Integer getTotalPiezas() {
		return totalPiezas;
	}
	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	public String getPedidoI() {
		return pedidoI;
	}
	public void setPedidoI(String pedidoI) {
		this.pedidoI = pedidoI;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Cliente getCli() {
		return cli;
	}
	
	public void setCli(Cliente cli) {
		this.cli = cli;
	}
}
