package com.proquifa.net.modelo.tableros.cliente;

import com.proquifa.net.modelo.comun.Direccion;

public class EntregaCatClientes {
	
   private boolean facturaOriginal;
   private boolean pedidoOriginal;
   private boolean copiaFactura;
   private int num_copiasFactura;
   private boolean copiaPedido;
   private int num_copiasPedidos;
   private boolean certificado;
   private boolean hojaSeguridad;
   private String comentarios;
   private Long idCliente;
   private Long idEntregaCliente;
   private boolean aceptaParcial;
   private boolean entregayRevicion;
   private String destino;
   
   
   private Direccion direccionEntrega;

public boolean isFacturaOriginal() {
	return facturaOriginal;
}

public void setFacturaOriginal(boolean facturaOriginal) {
	this.facturaOriginal = facturaOriginal;
}

public boolean isPedidoOriginal() {
	return pedidoOriginal;
}

public void setPedidoOriginal(boolean pedidoOriginal) {
	this.pedidoOriginal = pedidoOriginal;
}


public boolean isCopiaFactura() {
	return copiaFactura;
}

public void setCopiaFactura(boolean copiaFactura) {
	this.copiaFactura = copiaFactura;
}

public int getNum_copiasFactura() {
	return num_copiasFactura;
}

public void setNum_copiasFactura(int num_copiasFactura) {
	this.num_copiasFactura = num_copiasFactura;
}

public boolean isCopiaPedido() {
	return copiaPedido;
}

public void setCopiaPedido(boolean copiaPedido) {
	this.copiaPedido = copiaPedido;
}

public int getNum_copiasPedidos() {
	return num_copiasPedidos;
}

public void setNum_copiasPedidos(int num_copiasPedidos) {
	this.num_copiasPedidos = num_copiasPedidos;
}

public boolean isHojaSeguridad() {
	return hojaSeguridad;
}

public void setHojaSeguridad(boolean hojaSeguridad) {
	this.hojaSeguridad = hojaSeguridad;
}

public boolean isCertificado() {
	return certificado;
}

public void setCertificado(boolean certificado) {
	this.certificado = certificado;
}

public String getComentarios() {
	return comentarios;
}

public void setComentarios(String comentarios) {
	this.comentarios = comentarios;
}

public boolean isAceptaParcial() {
	return aceptaParcial;
}

public void setAceptaParcial(boolean aceptaParcial) {
	this.aceptaParcial = aceptaParcial;
}

public boolean isEntregayRevicion() {
	return entregayRevicion;
}

public void setEntregayRevicion(boolean entregayRevicion) {
	this.entregayRevicion = entregayRevicion;
}

public Long getIdEntregaCliente() {
	return idEntregaCliente;
}

public void setIdEntregaCliente(Long idEntregaCliente) {
	this.idEntregaCliente = idEntregaCliente;
}

public Long getIdCliente() {
	return idCliente;
}

public void setIdCliente(Long idCliente) {
	this.idCliente = idCliente;
}

public Direccion getDireccionEntrega() {
	return direccionEntrega;
}

public void setDireccionEntrega(Direccion direccionEntrega) {
	this.direccionEntrega = direccionEntrega;
}

public String getDestino() {
	return destino;
}

public void setDestino(String destino) {
	this.destino = destino;
}

}
