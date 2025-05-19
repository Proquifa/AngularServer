/**
 * 
 */
package com.proquifa.net.modelo.compras;

/**
 * @author yosimar.mendez
 *
 */
public class TipoPPedido {

	private int totalTipo;
	private int piezas;
	private double monto;
	
	/**
	 * 
	 */
	public TipoPPedido() {
		super();
	}

	/**
	 * @return the totalTipo
	 */
	public int getTotalTipo() {
		return totalTipo;
	}

	/**
	 * @param totalTipo the totalTipo to set
	 */
	public void setTotalTipo(int totalTipo) {
		this.totalTipo = totalTipo;
	}

	/**
	 * @return the piezas
	 */
	public int getPiezas() {
		return piezas;
	}

	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(int piezas) {
		this.piezas = piezas;
	}

	/**
	 * @return the monto
	 */
	public double getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
	
	
}
