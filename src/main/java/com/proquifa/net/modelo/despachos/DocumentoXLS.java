package com.proquifa.net.modelo.despachos;

import java.io.Serializable;

public class DocumentoXLS implements Serializable{
 
	
	
/**
	 * 
	 */
 private static final long serialVersionUID = -5053468942011771899L;
// documento Pedido
 private Integer idPPedido;
 private Integer idCliente;
 private String Cpedido;

 // documento certificado y hojas de seguridad 
 private Integer idProducto;
 private String lote;
 private String codigo;
 private String fabrica;
 private Integer idFabricante;
 
 // documento packingList 
 private Integer idPackingList;
 private String folio;
 private String  numeroFactura;
 private String  facturadoPor;
 
 private Integer idFactura;
 
 
 
 
 
public String getNumeroFactura() {
	return numeroFactura;
}
public void setNumeroFactura(String numeroFactura) {
	this.numeroFactura = numeroFactura;
}
public String getFacturadoPor() {
	return facturadoPor;
}
public void setFacturadoPor(String facturadoPor) {
	this.facturadoPor = facturadoPor;
}
public Integer getIdPPedido() {
	return idPPedido;
}
public void setIdPPedido(Integer idPPedido) {
	this.idPPedido = idPPedido;
}
public Integer getIdCliente() {
	return idCliente;
}
public void setIdCliente(Integer idCliente) {
	this.idCliente = idCliente;
}
public Integer getIdPackingList() {
	return idPackingList;
}
public void setIdPackingList(Integer idPackingList) {
	this.idPackingList = idPackingList;
}
public String getFolio() {
	return folio;
}
public void setFolio(String folio) {
	this.folio = folio;
}

public String getCpedido() {
	return Cpedido;
}
public void setCpedido(String cpedido) {
	Cpedido = cpedido;
}
public Integer getIdProducto() {
	return idProducto;
}
public void setIdProducto(Integer idProducto) {
	this.idProducto = idProducto;
}
public String getLote() {
	return lote;
}
public void setLote(String lote) {
	this.lote = lote;
}
public String getCodigo() {
	return codigo;
}
public void setCodigo(String codigo) {
	this.codigo = codigo;
}
public String getFabrica() {
	return fabrica;
}
public void setFabrica(String fabrica) {
	this.fabrica = fabrica;
}
public Integer getIdFabricante() {
	return idFabricante;
}
public void setIdFabricante(Integer idFabricante) {
	this.idFabricante = idFabricante;
}
/**
 * @return the idFactura
 */
public Integer getIdFactura() {
	return idFactura;
}
/**
 * @param idFactura the idFactura to set
 */
public void setIdFactura(Integer idFactura) {
	this.idFactura = idFactura;
}
 
 
	
 
 
	
}
