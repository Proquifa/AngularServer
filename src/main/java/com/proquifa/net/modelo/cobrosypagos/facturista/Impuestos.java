/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

/**
 * @author fmartinez
 *
 */
public class Impuestos {
	
	private String contexto = "FEDERAL";
	private String operacion = "TRASLADO";
	private String codigo = "IVA";
	private Float importe;
	private Double tasa = 0.160000;
	private Double monto;
	private String Impuesto = "002";
	private String tipoFactor = "Tasa";
	/**
	 * @return the contexto
	 */
	public String getContexto() {
		return contexto;
	}
	/**
	 * @param contexto the contexto to set
	 */
	public void setContexto(String contexto) {
		this.contexto = contexto;
	}
	/**
	 * @return the operacion
	 */
	public String getOperacion() {
		return operacion;
	}
	/**
	 * @param operacion the operacion to set
	 */
	public void setOperacion(String operacion) {
		this.operacion = operacion;
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
	 * @return the importe
	 */
	public Float getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Float importe) {
		this.importe = importe;
	}
	/**
	 * @return the tasa
	 */
	public Double getTasa() {
		return tasa;
	}
	/**
	 * @param tasa the tasa to set
	 */
	public void setTasa(Double tasa) {
		this.tasa = tasa;
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
	 * @return the impuesto
	 */
	public String getImpuesto() {
		return Impuesto;
	}
	/**
	 * @param impuesto the impuesto to set
	 */
	public void setImpuesto(String impuesto) {
		Impuesto = impuesto;
	}
	/**
	 * @return the tipoFactor
	 */
	public String getTipoFactor() {
		return tipoFactor;
	}
	/**
	 * @param tipoFactor the tipoFactor to set
	 */
	public void setTipoFactor(String tipoFactor) {
		this.tipoFactor = tipoFactor;
	}
}