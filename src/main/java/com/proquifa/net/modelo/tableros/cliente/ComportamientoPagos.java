/**
 * 
 */
package com.proquifa.net.modelo.tableros.cliente;

/**
 * @author vromero
 *
 */
public class ComportamientoPagos {
	private String empresa;
	private Long factura;
	private Double monto;
	private Double tCambio;
	private Boolean aTiempo;
	private int diasRestantesDeCredito;
	private int mes;
	private String estado;
	private String esac;
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the factura
	 */
	public Long getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(Long factura) {
		this.factura = factura;
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
	 * @return the tCambio
	 */
	public Double getTCambio() {
		return tCambio;
	}
	/**
	 * @param cambio the tCambio to set
	 */
	public void setTCambio(Double cambio) {
		tCambio = cambio;
	}
	/**
	 * @return the aTiempo
	 */
	public Boolean getATiempo() {
		return aTiempo;
	}
	/**
	 * @param tiempo the aTiempo to set
	 */
	public void setATiempo(Boolean tiempo) {
		aTiempo = tiempo;
	}
	/**
	 * @return the diasRestantesDeCredito
	 */
	public int getDiasRestantesDeCredito() {
		return diasRestantesDeCredito;
	}
	/**
	 * @param diasRestantesDeCredito the diasRestantesDeCredito to set
	 */
	public void setDiasRestantesDeCredito(int diasRestantesDeCredito) {
		this.diasRestantesDeCredito = diasRestantesDeCredito;
	}
	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @return the esac
	 */
	public String getEsac() {
		return esac;
	}
	/**
	 * @param esac the esac to set
	 */
	public void setEsac(String esac) {
		this.esac = esac;
	}

}
