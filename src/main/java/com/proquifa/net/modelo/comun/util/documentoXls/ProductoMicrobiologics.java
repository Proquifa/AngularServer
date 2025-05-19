package com.proquifa.net.modelo.comun.util.documentoXls;

import com.proquifa.net.modelo.comun.Producto;

public class ProductoMicrobiologics extends Producto {
	private long pk_ProductoMicro;
	private String comentario;
	private double precioLista;
	private double precioListaR;
	private String codigoPrecio;
	
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public double getPrecioLista() {
		return precioLista;
	}
	public void setPrecioLista(double precioLista) {
		this.precioLista = precioLista;
	}
	public double getPrecioListaR() {
		return precioListaR;
	}
	public void setPrecioListaR(double precioListaR) {
		this.precioListaR = precioListaR;
	}
	public String getCodigoPrecio() {
		return codigoPrecio;
	}
	public void setCodigoPrecio(String codigoPrecio) {
		this.codigoPrecio = codigoPrecio;
	}
	public long getPK_ProductoMicro() {
		return pk_ProductoMicro;
	}
	public void setPK_ProductoMicro(long pk_ProductoMicro) {
		this.pk_ProductoMicro = pk_ProductoMicro;
	}
}
