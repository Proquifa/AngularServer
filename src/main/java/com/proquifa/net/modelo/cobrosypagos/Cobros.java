/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos;

import java.util.Date;

/**
 * @author vromero
 *
 */
public class Cobros {
	
	private String fpor;
	private String nombreCliente;
	private Long idCliente;
	private String cpedido;
	private Long idFactura;
	private Long factura;
	private String medioFactura;
	private String cpago;
	private Date fechaFacturacion;
	private String contrarecibo;
	private Date fechaRevision;
	private Date fechaEsperadaPago;
	private Integer diasRestantesCobro;
	private String medioPago;
	private Double montoEsperadoCobro;
	private Double montoRealCobro;
	private Date fechaPago;
	private String estado;
	private Double montoDolares;
	private String monedaEsperadaCobro;
	private String monedaRealCobro;
	private String folioPC;
	private String nivelIngreso;
	private String moroso;
	private Long piezas;
	private Long partidas;
	private String esac;
	private String ev;
	private Date fechaProgramacion;
	private Date fechaRequeridaRealizacion;
	private String referencia;
	private String cobrador;
	private Double tipoCambio;
	private Double ivaDlls;
	private Double subTotalDlls;
	private Double totalDlls;
	private Double subtotalPesos;
	private Double ivaPesos;
	private Double totalPesos;
	private String banco;
	private String noCuenta;
	private Date fechaUEntrega;
    private Date fechaPEntrega;
	private String ordenCompra;
	private String resultadoEntrega;
	private String uuid;
	
	/**
	 * @return the ivaDlls
	 */
	public Double getIvaDlls() {
		return ivaDlls;
	}
	/**
	 * @param ivaDlls the ivaDlls to set
	 */
	public void setIvaDlls(Double ivaDlls) {
		this.ivaDlls = ivaDlls;
	}
	/**
	 * @return the totalDlls
	 */
	public Double getTotalDlls() {
		return totalDlls;
	}
	/**
	 * @param totalDlls the totalDlls to set
	 */
	public void setTotalDlls(Double totalDlls) {
		this.totalDlls = totalDlls;
	}
	/**
	 * @return the ivaPesos
	 */
	public Double getIvaPesos() {
		return ivaPesos;
	}
	/**
	 * @param ivaPesos the ivaPesos to set
	 */
	public void setIvaPesos(Double ivaPesos) {
		this.ivaPesos = ivaPesos;
	}
	/**
	 * @return the totalPesos
	 */
	public Double getTotalPesos() {
		return totalPesos;
	}
	/**
	 * @param totalPesos the totalPesos to set
	 */
	public void setTotalPesos(Double totalPesos) {
		this.totalPesos = totalPesos;
	}
	/**
	 * @return the subTotalDlls
	 */
	public Double getSubTotalDlls() {
		return subTotalDlls;
	}
	/**
	 * @param subTotalDlls the subTotalDlls to set
	 */
	public void setSubTotalDlls(Double subTotalDlls) {
		this.subTotalDlls = subTotalDlls;
	}
	/**
	 * @return the subtotalPesos
	 */
	public Double getSubtotalPesos() {
		return subtotalPesos;
	}
	/**
	 * @param subtotalPesos the subtotalPesos to set
	 */
	public void setSubtotalPesos(Double subtotalPesos) {
		this.subtotalPesos = subtotalPesos;
	}
	/**
	 * @return the banco
	 */
	public String getBanco() {
		return banco;
	}
	/**
	 * @param banco the banco to set
	 */
	public void setBanco(String banco) {
		this.banco = banco;
	}
	/**
	 * @return the noCuenta
	 */
	public String getNoCuenta() {
		return noCuenta;
	}
	/**
	 * @param noCuenta the noCuenta to set
	 */
	public void setNoCuenta(String noCuenta) {
		this.noCuenta = noCuenta;
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
	 * @return the fechaFacturacion
	 */
	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}
	/**
	 * @param fechaFacturacion the fechaFacturacion to set
	 */
	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}
	/**
	 * @return the contrarecibo
	 */
	public String getContrarecibo() {
		return contrarecibo;
	}
	/**
	 * @param contrarecibo the contrarecibo to set
	 */
	public void setContrarecibo(String contrarecibo) {
		this.contrarecibo = contrarecibo;
	}
	/**
	 * @return the fechaRevision
	 */
	public Date getFechaRevision() {
		return fechaRevision;
	}
	/**
	 * @param fechaRevision the fechaRevision to set
	 */
	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}
	/**
	 * @return the fechaEsperadaPago
	 */
	public Date getFechaEsperadaPago() {
		return fechaEsperadaPago;
	}
	/**
	 * @param fechaEsperadaPago the fechaEsperadaPago to set
	 */
	public void setFechaEsperadaPago(Date fechaEsperadaPago) {
		this.fechaEsperadaPago = fechaEsperadaPago;
	}
	/**
	 * @return the diasRestantesCobro
	 */
	public Integer getDiasRestantesCobro() {
		return diasRestantesCobro;
	}
	/**
	 * @param diasRestantesCobro the diasRestantesCobro to set
	 */
	public void setDiasRestantesCobro(Integer diasRestantesCobro) {
		this.diasRestantesCobro = diasRestantesCobro;
	}
	/**
	 * @return the medioPago
	 */
	public String getMedioPago() {
		return medioPago;
	}
	/**
	 * @param medioPago the medioPago to set
	 */
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	/**
	 * @return the montoEsperadoCobro
	 */
	public Double getMontoEsperadoCobro() {
		return montoEsperadoCobro;
	}
	/**
	 * @param montoEsperadoCobro the montoEsperadoCobro to set
	 */
	public void setMontoEsperadoCobro(Double montoEsperadoCobro) {
		this.montoEsperadoCobro = montoEsperadoCobro;
	}
	/**
	 * @return the montoRealCobro
	 */
	public Double getMontoRealCobro() {
		return montoRealCobro;
	}
	/**
	 * @param montoRealCobro the montoRealCobro to set
	 */
	public void setMontoRealCobro(Double montoRealCobro) {
		this.montoRealCobro = montoRealCobro;
	}
	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}
	/**
	 * @param fechaPago the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
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
	 * @param montoDolares the montoDolares to set
	 */
	public void setMontoDolares(Double montoDolares) {
		this.montoDolares = montoDolares;
	}
	/**
	 * @return the montoDolares
	 */
	public Double getMontoDolares() {
		return montoDolares;
	}
	/**
	 * @return the medioFactura
	 */
	public String getMedioFactura() {
		return medioFactura;
	}
	/**
	 * @param medioFactura the medioFactura to set
	 */
	public void setMedioFactura(String medioFactura) {
		this.medioFactura = medioFactura;
	}
	/**
	 * @return the monedaEsperadaCobro
	 */
	public String getMonedaEsperadaCobro() {
		return monedaEsperadaCobro;
	}
	/**
	 * @param monedaEsperadaCobro the monedaEsperadaCobro to set
	 */
	public void setMonedaEsperadaCobro(String monedaEsperadaCobro) {
		this.monedaEsperadaCobro = monedaEsperadaCobro;
	}
	/**
	 * @return the monedaRealCobro
	 */
	public String getMonedaRealCobro() {
		return monedaRealCobro;
	}
	/**
	 * @param monedaRealCobro the monedaRealCobro to set
	 */
	public void setMonedaRealCobro(String monedaRealCobro) {
		this.monedaRealCobro = monedaRealCobro;
	}
	/**
	 * @param folioPC the folioPC to set
	 */
	public void setFolioPC(String folioPC) {
		this.folioPC = folioPC;
	}
	/**
	 * @return the folioPC
	 */
	public String getFolioPC() {
		return folioPC;
	}
	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}
	public String getNivelIngreso() {
		return nivelIngreso;
	}
	public void setMoroso(String moroso) {
		this.moroso = moroso;
	}
	public String getMoroso() {
		return moroso;
	}
	public void setPiezas(Long piezas) {
		this.piezas = piezas;
	}
	public Long getPiezas() {
		return piezas;
	}
	public void setPartidas(Long partidas) {
		this.partidas = partidas;
	}
	public Long getPartidas() {
		return partidas;
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
	 * @return the ev
	 */
	public String getEv() {
		return ev;
	}
	/**
	 * @param ev the ev to set
	 */
	public void setEv(String ev) {
		this.ev = ev;
	}
	/**
	 * @return the fechaProgramacion
	 */
	public Date getFechaProgramacion() {
		return fechaProgramacion;
	}
	/**
	 * @param fechaProgramacion the fechaProgramacion to set
	 */
	public void setFechaProgramacion(Date fechaProgramacion) {
		this.fechaProgramacion = fechaProgramacion;
	}
	/**
	 * @return the fechaRequeridaRealizacion
	 */
	public Date getFechaRequeridaRealizacion() {
		return fechaRequeridaRealizacion;
	}
	/**
	 * @param fechaRequeridaRealizacion the fechaRequeridaRealizacion to set
	 */
	public void setFechaRequeridaRealizacion(Date fechaRequeridaRealizacion) {
		this.fechaRequeridaRealizacion = fechaRequeridaRealizacion;
	}
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}
	/**
	 * @return the idFactura
	 */
	public Long getIdFactura() {
		return idFactura;
	}
	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
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
	public Date getFechaUEntrega() {
		return fechaUEntrega;
	}
	public void setFechaUEntrega(Date fechaUEntrega) {
		this.fechaUEntrega = fechaUEntrega;
	}

	public Date getFechaPEntrega() {
		return fechaPEntrega;
	}
	public void setFechaPEntrega(Date fechaPEntrega) {
		this.fechaPEntrega = fechaPEntrega;
	}

	public String getOrdenCompra() {
		return ordenCompra;
	}
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	public String getResultadoEntrega() {
		return resultadoEntrega;
	}
	public void setResultadoEntrega(String resultadoEntrega) {
		this.resultadoEntrega = resultadoEntrega;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	

}
