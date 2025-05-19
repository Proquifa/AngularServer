/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;

/**
 * @author fmartinez
 *
 */
public class Facturacion {
	private String refacturada;
	private Double totaliva;
	private Double total;
	private Date fecha;
	private String factura;
	private String cpedido;
	private String fpor;
	private String tipo;
	private String medio;
	private Double importe;
	private String moneda;
	private Double iva;
	private String estado;
	private String nombre_cliente;
	private String facturaantigua;
	private String facturanueva;
	private String razones;
	private String autorizo;
	private String razonpop;
	private Double pdolar;

	private String cPago;
	private String drc;
	private Date fep;
	private String responsable;
	private Double tcambio;

	private String remision;
	private Date fechaR;
	private String comentarios;
	private String medioPago;
	private String pedido;
	private Date fechaTramitacion;
	private Date fechaFacturacion;

	private Date fechaEnvio;
	private Date fechaSAP;
	private Date fechaProquifa;
	private String contacto;
	private String documento;
	private String monedaPago;

	private Date fechaPago;
	private Date fechaCobro;
	private Date fechaAsosiacion;
	private Double tcambioCobro;

	private Integer numPiezas;

	private Date fechaInicioPortal;
	private Date fechaFinPortal;
	private String document;

	private Date finicio;
	private Date ffin;
	private String rsocial;
	private String rfc;

	private double totalMN;
	private double importeMN;
	private double totalivaMN;

	private String esac;
	private String cobrador;

	private Date fechaCancelacion;

	private String folioNC;
	private String cuentaBanco;
	private String clave;
	private String proforma;

	private String referencia;
	private String doctoR;
	private String uuid;

	/**
	 * @return the cuentaBanco
	 */
	public String getCuentaBanco() {
		return cuentaBanco;
	}

	/**
	 * @param cuentaBanco
	 *            the cuentaBanco to set
	 */
	public void setCuentaBanco(String cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
	}

	/**
	 * @return the fechaCancelacion
	 */
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}

	/**
	 * @param fechaCancelacion
	 *            the fechaCancelacion to set
	 */
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}

	/**
	 * @return the refacturada
	 */
	public String getRefacturada() {
		return refacturada;
	}

	/**
	 * @param refacturada
	 *            the refacturada to set
	 */
	public void setRefacturada(String refacturada) {
		this.refacturada = refacturada;
	}

	/**
	 * @return the totaliva
	 */
	public Double getTotaliva() {
		return totaliva;
	}

	/**
	 * @param totaliva
	 *            the totaliva to set
	 */
	public void setTotaliva(Double totaliva) {
		this.totaliva = totaliva;
	}

	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the factura
	 */
	public String getFactura() {
		return factura;
	}

	/**
	 * @param factura
	 *            the factura to set
	 */
	public void setFactura(String factura) {
		this.factura = factura;
	}

	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}

	/**
	 * @param cpedido
	 *            the cpedido to set
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
	 * @param fpor
	 *            the fpor to set
	 */
	public void setFpor(String fpor) {
		this.fpor = fpor;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}

	/**
	 * @param medio
	 *            the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}

	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}

	/**
	 * @param importe
	 *            the importe to set
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
	 * @param moneda
	 *            the moneda to set
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
	 * @param iva
	 *            the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the nombre_cliente
	 */
	public String getNombre_cliente() {
		return nombre_cliente;
	}

	/**
	 * @param nombre_cliente
	 *            the nombre_cliente to set
	 */
	public void setNombre_cliente(String nombre_cliente) {
		this.nombre_cliente = nombre_cliente;
	}

	/**
	 * @return the facturaantigua
	 */
	public String getFacturaantigua() {
		return facturaantigua;
	}

	/**
	 * @param facturaantigua
	 *            the facturaantigua to set
	 */
	public void setFacturaantigua(String facturaantigua) {
		this.facturaantigua = facturaantigua;
	}

	/**
	 * @return the facturanueva
	 */
	public String getFacturanueva() {
		return facturanueva;
	}

	/**
	 * @param facturanueva
	 *            the facturanueva to set
	 */
	public void setFacturanueva(String facturanueva) {
		this.facturanueva = facturanueva;
	}

	/**
	 * @return the razones
	 */
	public String getRazones() {
		return razones;
	}

	/**
	 * @param razones
	 *            the razones to set
	 */
	public void setRazones(String razones) {
		this.razones = razones;
	}

	/**
	 * @return the autorizo
	 */
	public String getAutorizo() {
		return autorizo;
	}

	/**
	 * @param autorizo
	 *            the autorizo to set
	 */
	public void setAutorizo(String autorizo) {
		this.autorizo = autorizo;
	}

	/**
	 * @return the razonpop
	 */
	public String getRazonpop() {
		return razonpop;
	}

	/**
	 * @param razonpop
	 *            the razonpop to set
	 */
	public void setRazonpop(String razonpop) {
		this.razonpop = razonpop;
	}

	/**
	 * @return the pdolar
	 */
	public Double getPdolar() {
		return pdolar;
	}

	/**
	 * @param pdolar
	 *            the pdolar to set
	 */
	public void setPdolar(Double pdolar) {
		this.pdolar = pdolar;
	}

	/**
	 * @return the cPago
	 */
	public String getCPago() {
		return cPago;
	}

	/**
	 * @param pago
	 *            the cPago to set
	 */
	public void setCPago(String pago) {
		cPago = pago;
	}

	/**
	 * @return the drc
	 */
	public String getDrc() {
		return drc;
	}

	/**
	 * @param drc
	 *            the drc to set
	 */
	public void setDrc(String drc) {
		this.drc = drc;
	}

	/**
	 * @return the fep
	 */
	public Date getFep() {
		return fep;
	}

	/**
	 * @param fep
	 *            the fep to set
	 */
	public void setFep(Date fep) {
		this.fep = fep;
	}

	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the remision
	 */
	public String getRemision() {
		return remision;
	}

	/**
	 * @param remision
	 *            the remision to set
	 */
	public void setRemision(String remision) {
		this.remision = remision;
	}

	/**
	 * @return the fechaR
	 */
	public Date getFechaR() {
		return fechaR;
	}

	/**
	 * @param fechaR
	 *            the fechaR to set
	 */
	public void setFechaR(Date fechaR) {
		this.fechaR = fechaR;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios
	 *            the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the medioPago
	 */
	public String getMedioPago() {
		return medioPago;
	}

	/**
	 * @param medioPago
	 *            the medioPago to set
	 */
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}

	/**
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}

	/**
	 * @param pedido
	 *            the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}

	/**
	 * @return the tcambio
	 */
	public Double getTcambio() {
		return tcambio;
	}

	/**
	 * @param tcambio
	 *            the tcambio to set
	 */
	public void setTcambio(Double tcambio) {
		this.tcambio = tcambio;
	}

	/**
	 * @return the fechaTramitacion
	 */
	public Date getFechaTramitacion() {
		return fechaTramitacion;
	}

	/**
	 * @param fechaTramitacion
	 *            the fechaTramitacion to set
	 */
	public void setFechaTramitacion(Date fechaTramitacion) {
		this.fechaTramitacion = fechaTramitacion;
	}

	/**
	 * @return the fechaFacturacion
	 */
	public Date getFechaFacturacion() {
		return fechaFacturacion;
	}

	/**
	 * @param fechaFacturacion
	 *            the fechaFacturacion to set
	 */
	public void setFechaFacturacion(Date fechaFacturacion) {
		this.fechaFacturacion = fechaFacturacion;
	}

	/**
	 * @return the fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	/**
	 * @param fechaEnvio
	 *            the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	/**
	 * @return the fechaSAP
	 */
	public Date getFechaSAP() {
		return fechaSAP;
	}

	/**
	 * @param fechaSAP
	 *            the fechaSAP to set
	 */
	public void setFechaSAP(Date fechaSAP) {
		this.fechaSAP = fechaSAP;
	}

	/**
	 * @return the fechaProquifa
	 */
	public Date getFechaProquifa() {
		return fechaProquifa;
	}

	public double getImporteMN() {
		return importeMN;
	}

	public void setImporteMN(double importeMN) {
		this.importeMN = importeMN;
	}

	/**
	 * @param fechaProquifa
	 *            the fechaProquifa to set
	 */
	public void setFechaProquifa(Date fechaProquifa) {
		this.fechaProquifa = fechaProquifa;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto
	 *            the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}

	/**
	 * @param documento
	 *            the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}

	/**
	 * @return the monedaPago
	 */
	public String getMonedaPago() {
		return monedaPago;
	}

	/**
	 * @param monedaPago
	 *            the monedaPago to set
	 */
	public void setMonedaPago(String monedaPago) {
		this.monedaPago = monedaPago;
	}

	/**
	 * @return the fechaPago
	 */
	public Date getFechaPago() {
		return fechaPago;
	}

	/**
	 * @param fechaPago
	 *            the fechaPago to set
	 */
	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	/**
	 * @return the fechaCobro
	 */
	public Date getFechaCobro() {
		return fechaCobro;
	}

	/**
	 * @param fechaCobro
	 *            the fechaCobro to set
	 */
	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}

	/**
	 * @return the fechaAsosiacion
	 */
	public Date getFechaAsosiacion() {
		return fechaAsosiacion;
	}

	/**
	 * @param fechaAsosiacion
	 *            the fechaAsosiacion to set
	 */
	public void setFechaAsosiacion(Date fechaAsosiacion) {
		this.fechaAsosiacion = fechaAsosiacion;
	}

	/**
	 * @return the tcambioCobro
	 */
	public Double getTcambioCobro() {
		return tcambioCobro;
	}

	/**
	 * @param tcambioCobro
	 *            the tcambioCobro to set
	 */
	public void setTcambioCobro(Double tcambioCobro) {
		this.tcambioCobro = tcambioCobro;
	}

	/**
	 * @return the numPiezas
	 */
	public Integer getNumPiezas() {
		return numPiezas;
	}

	/**
	 * @param numPiezas
	 *            the numPiezas to set
	 */
	public void setNumPiezas(Integer numPiezas) {
		this.numPiezas = numPiezas;
	}

	/**
	 * @return the fechaInicioPortal
	 */
	public Date getFechaInicioPortal() {
		return fechaInicioPortal;
	}

	/**
	 * @param fechaInicioPortal
	 *            the fechaInicioPortal to set
	 */
	public void setFechaInicioPortal(Date fechaInicioPortal) {
		this.fechaInicioPortal = fechaInicioPortal;
	}

	/**
	 * @return the fechaFinPortal
	 */
	public Date getFechaFinPortal() {
		return fechaFinPortal;
	}

	/**
	 * @param fechaFinPortal
	 *            the fechaFinPortal to set
	 */
	public void setFechaFinPortal(Date fechaFinPortal) {
		this.fechaFinPortal = fechaFinPortal;
	}

	/**
	 * @return the document
	 */
	public String getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(String document) {
		this.document = document;
	}

	/**
	 * @return the finicio
	 */
	public Date getFinicio() {
		return finicio;
	}

	/**
	 * @param finicio
	 *            the finicio to set
	 */
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	/**
	 * @return the ffin
	 */
	public Date getFfin() {
		return ffin;
	}

	/**
	 * @param ffin
	 *            the ffin to set
	 */
	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	/**
	 * @return the cPago
	 */
	public String getcPago() {
		return cPago;
	}

	/**
	 * @param cPago
	 *            the cPago to set
	 */
	public void setcPago(String cPago) {
		this.cPago = cPago;
	}

	/**
	 * @return the rsocial
	 */
	public String getRsocial() {
		return rsocial;
	}

	/**
	 * @param rsocial
	 *            the rsocial to set
	 */
	public void setRsocial(String rsocial) {
		this.rsocial = rsocial;
	}

	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}

	/**
	 * @param rfc
	 *            the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	/**
	 * @return the totalivaMN
	 */
	public double getTotalivaMN() {
		return totalivaMN;
	}

	/**
	 * @param totalivaMN
	 */
	public void setTotalivaMN(double totalivaMN) {
		this.totalivaMN = totalivaMN;
	}

	/**
	 * @return the totalMN
	 */
	public double getTotalMN() {
		return totalMN;
	}

	/**
	 * @param totalMN
	 *            the totalMN to set
	 */
	public void setTotalMN(double totalMN) {
		this.totalMN = totalMN;
	}

	/**
	 * @return the esac
	 */
	public String getEsac() {
		return esac;
	}

	/**
	 * @param esac
	 *            the esac to set
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
	 * @param cobrador
	 *            the cobrador to set
	 */
	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}

	/**
	 * @return the folioNC
	 */
	public String getFolioNC() {
		return folioNC;
	}

	/**
	 * @param folioNC
	 *            the folioNC to set
	 */
	public void setFolioNC(String folioNC) {
		this.folioNC = folioNC;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getProforma() {
		return proforma;
	}

	public void setProforma(String proforma) {
		this.proforma = proforma;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDoctoR() {
		return doctoR;
	}

	public void setDoctoR(String doctoR) {
		this.doctoR = doctoR;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
