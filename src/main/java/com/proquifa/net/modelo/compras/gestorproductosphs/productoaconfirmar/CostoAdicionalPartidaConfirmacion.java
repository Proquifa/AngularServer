/**
 * 
 */
package com.proquifa.net.modelo.compras.gestorproductosphs.productoaconfirmar;

/**
 * @author vromero
 *
 */
public class CostoAdicionalPartidaConfirmacion {
	
	private int idCostoAdicional;
	private String concepto;
	private String nota;
	private Double costo;
	private int idPartida;
	/**
	 * @param idCostoAdicional the idCostoAdicional to set
	 */
	public void setIdCostoAdicional(int idCostoAdicional) {
		this.idCostoAdicional = idCostoAdicional;
	}
	/**
	 * @return the idCostoAdicional
	 */
	public int getIdCostoAdicional() {
		return idCostoAdicional;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
	}
	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	/**
	 * @return the costo
	 */
	public Double getCosto() {
		return costo;
	}
	/**
	 * @param idPartida the idPartida to set
	 */
	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}
	/**
	 * @return the idPartida
	 */
	public int getIdPartida() {
		return idPartida;
	}
	

}
