package com.proquifa.net.modelo.despachos.trabajarRuta;

import java.io.Serializable;

public class TrTotalGraficasAlmacen implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5088127547731818805L;
	private int piezas;
	private Double monto;
	private String titulo;
	
	/**
	 * 
	 */
	public TrTotalGraficasAlmacen() {
		super();
	}

	/**
	 * 
	 * @return
	 */
	public int getPiezas() {
		return piezas;
	}

	/**
	 * 
	 * @param piezas
	 */
	public void setPiezas(int piezas) {
		this.piezas = piezas;
	}

	/**
	 * 
	 * @return
	 */
	public Double getMonto() {
		return monto;
	}

	/**
	 * 
	 * @param monto
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}

	/**
	 * 
	 * @return
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
