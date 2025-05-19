package com.proquifa.net.modelo.despachos.trabajarRuta;

import java.io.Serializable;

public class TrAlmacen implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3181899059934170589L;
	private int idCliente;
	private String nombreCliente;
	private int cant;
	private int numPL;
	private int p1;
	private int p2;
	private int p3;
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
	public int getNumPL() {
		return numPL;
	}
	public void setNumPL(int numPL) {
		this.numPL = numPL;
	}
	public int getP1() {
		return p1;
	}
	public void setP1(int p1) {
		this.p1 = p1;
	}
	public int getP2() {
		return p2;
	}
	public void setP2(int p2) {
		this.p2 = p2;
	}
	public int getP3() {
		return p3;
	}
	public void setP3(int p3) {
		this.p3 = p3;
	}
	
	
	
}
