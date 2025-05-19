/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class NotaCredito {
	private Long idNota;
	private Date fecha;
	private String folio;
	private Long idCliente;
	private Long factura;
	private Long idFactura;
	private Boolean cancelada;
	private Double importe;
	private String moneda;
	private Double iva;
	private Double tCambio;
	private String serie;
	private String medio;
	private String cpedido;
	private String fpor;
	private String cpago;
	private String nombreCliente; //Nombre del Cliente
	private String estado; // Por Aplicar (Si es <> de Cancelada), Aplicada (Si es igual a Cancelada)
	private Long akFolio;
	private String esac;
	private String cobrador;
	private String tipo;
	private Long facturaDestino;
	private String pInterno;
	private String fechaNota;
	
	//variables del servicio obtener notas 
	private Double  mO;
	private Double mUSD;
	private Double mPesos;
	private Double totalUSD;
	private Double tCambioEuro;
	private Boolean modificado;
	private Double  importeTotal;

	

	
	
	public Double getImporteTotal() {
		return importeTotal;
	}
	public void setImporteTotal(Double importeTotal) {
		this.importeTotal = importeTotal;
	}
	public Double gettCambio() {
		return tCambio;
	}
	public void settCambio(Double tCambio) {
		this.tCambio = tCambio;
	}
	public Boolean getModificado() {
		return modificado;
	}
	public void setModificado(Boolean modificado) {
		this.modificado = modificado;
	}
	public Double getmPesos() {
		return mPesos;
	}
	public void setmPesos(Double mPesos) {
		this.mPesos = mPesos;
	}
	public Double gettCambioEuro() {
		return tCambioEuro;
	}
	public void settCambioEuro(Double tCambioEuro) {
		this.tCambioEuro = tCambioEuro;
	}
	public Double getmO() {
		return mO;
	}
	public void setmO(Double mO) {
		this.mO = mO;
	}
	public Double getmUSD() {
		return mUSD;
	}
	public void setmUSD(Double mUSD) {
		this.mUSD = mUSD;
	}
	public Double getTotalUSD() {
		return totalUSD;
	}
	public void setTotalUSD(Double totalUSD) {
		this.totalUSD = totalUSD;
	}
	public String getFechaNota() {
		return fechaNota;
	}
	public void setFechaNota(String fechaNota) {
		this.fechaNota = fechaNota;
	}
	public String getpInterno() {
		return pInterno;
	}
	public void setpInterno(String pInterno) {
		this.pInterno = pInterno;
	}
	/**
	 * @return the facturaDestino
	 */
	public Long getFacturaDestino() {
		return facturaDestino;
	}
	/**
	 * @param facturaDestino the facturaDestino to set
	 */
	public void setFacturaDestino(Long facturaDestino) {
		this.facturaDestino = facturaDestino;
	}
	/**
	 * @return the idNota
	 */
	public Long getIdNota() {
		return idNota;
	}
	/**
	 * @param idNota the idNota to set
	 */
	public void setIdNota(Long idNota) {
		this.idNota = idNota;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
	 * @return the idFactura
	 */
	public Long getIdFactura() {
		return idFactura;
	}
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}
	/**
	 * @return the cancelada
	 */
	public Boolean getCancelada() {
		return cancelada;
	}
	/**
	 * @param cancelada the cancelada to set
	 */
	public void setCancelada(Boolean cancelada) {
		this.cancelada = cancelada;
	}
	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
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
	 * @return the iva
	 */
	public Double getIva() {
		return iva;
	}
	/**
	 * @param iva the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
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
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}
	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}
	/**
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}
	/**
	 * @param medio the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}
	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}
	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}
	/**
	 * @return the fpor
	 */
	public String getFpor() {
		return fpor;
	}
	/**
	 * @param fpor the fpor to set
	 */
	public void setFpor(String fpor) {
		this.fpor = fpor;
	}
	/**
	 * @return the cpago
	 */
	public String getCpago() {
		return cpago;
	}
	/**
	 * @param cpago the cpago to set
	 */
	public void setCpago(String cpago) {
		this.cpago = cpago;
	}
	
	/**
	 * @return the nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}
	/**
	 * @param nombreCliente the nombreCliente to set
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the akFolio
	 */
	public Long getAkFolio() {
		return akFolio;
	}
	/**
	 * @param akFolio the akFolio to set
	 */
	public void setAkFolio(Long akFolio) {
		this.akFolio = akFolio;
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
	/**
	 * @return the cobrador
	 */
	public String getCobrador() {
		return cobrador;
	}
	/**
	 * @param cobrador the cobrador to set
	 */
	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
