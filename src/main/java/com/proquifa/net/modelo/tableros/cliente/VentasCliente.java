/**
 * 
 */
package com.proquifa.net.modelo.tableros.cliente;

/**
 * @author vromero
 *
 */
public class VentasCliente {
	private int mes;
	private String empresaFactura;
	private String folio;
	private Double monto;
	private String moneda;
	private Double tipoCambio;
	private Double conversionUSD;
	private String tipoDocumento;
	private String esac;
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
	 * @return the empresaFactura
	 */
	public String getEmpresaFactura() {
		return empresaFactura;
	}
	/**
	 * @param empresaFactura the empresaFactura to set
	 */
	public void setEmpresaFactura(String empresaFactura) {
		this.empresaFactura = empresaFactura;
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
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	/**
	 * @return the tipoCambio
	 */
	public Double getTipoCambio() {
		return tipoCambio;
	}
	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	/**
	 * @return the conversionUSD
	 */
	public Double getConversionUSD() {
		return conversionUSD;
	}
	/**
	 * @param conversionUSD the conversionUSD to set
	 */
	public void setConversionUSD(Double conversionUSD) {
		this.conversionUSD = conversionUSD;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
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
