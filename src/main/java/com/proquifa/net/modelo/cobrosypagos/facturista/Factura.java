/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.despachos.Ruta;

/**
 * @author sarivera
 *
 */
public class Factura {	
	private Long idFactura;
	private Long numeroFactura;
	private String serie;
	private String medio;
	private String tipo;
	private String estado;
	private Long idEmpresaFactura;
	private String facturadoPor;
	private String razonSocialFPor;
	private String rfc_FPor;
	private Date fecha;
	private String moneda;
	private Float importe;
	private Double iva;
	private Double tipoCambio;
	private Boolean imprimirTipoCambio;
	private Double montoRealPagado;
	private Double montoAPagar;
	private String montoConLetra;
	private Long idCliente;
	private String nombre_Cliente;
	private String rfc_Cliente;
	private String contraRecibo;
	private Date fPago;
	private String condicionesPago;
	private String medioPago;
	private Long diasRestantesPago;
	private String monedaPago;
	private Date frevision;
	private Date fEPago;
	private Long documentoRecibido;
	private String cpedido;
	private String pedido;
	private String ordenCompra;
	private String folioPC;
	private String folioEntrega;
	private Boolean remision;
	private String observaciones;
	private String monedaPedido;
	private String nivelIngresocliente;
	private Double montoTotalPedido;
	private Integer numeroPiezasPedidoRelacionado;
	private Integer numeroPiezasFactura;
	private Integer remOfact;
	private Ruta rutaRelacionada;
	private Double montoFacturaDLS;
	private Double ivaDLS;
	
	private String esac;
	private String cobrador;
	private String cuentaBanco;
	private String uuid;
	
	private List<PartidaFactura> partidas;
	private Long idDocumentoFiscal;
	
	// Se usan para generar el PDF
	private String direccionFPor;
	private Date fEnvio;
	private String medioEnvio;
	private String puntoEntrega;
	private String facturarA;
	private String entregarEn;
	private String idioma; // Idioma para imprimir el PDF
	private float flete;
	
	//Prueba para obtener idDocumentoFiscal
	private String idDocFiscal;
	private String tipoPago;
	private String metodoPago;
	private String tipoRelacion;
	private String relacionUUID;
	private String tipoCambioF;
	//Traslados
	private String totalTraslados;
	private String tipoImpuesto;
	private String tasa;
	private String tipoFactor;
	
	private Integer idFacturaElectronica;
	private String CP;
	private String regimenFiscal;

	public String getRegimenFiscal() {
		return regimenFiscal;
	}

	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}

	public String getCP() {
		return CP;
	}

	public void setCP(String CP) {
		this.CP = CP;
	}

	/**
	 * @return the cuentaBanco
	 */
	public String getCuentaBanco() {
		return cuentaBanco;
	}
	/**
	 * @param cuentaBanco the cuentaBanco to set
	 */
	public void setCuentaBanco(String cuentaBanco) {
		this.cuentaBanco = cuentaBanco;
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
	 * @return the numeroFactura
	 */
	public Long getNumeroFactura() {
		return numeroFactura;
	}
	/**
	 * @param numeroFactura the numeroFactura to set
	 */
	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
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
	 * @return the idEmpresaFactura
	 */
	public Long getIdEmpresaFactura() {
		return idEmpresaFactura;
	}
	/**
	 * @param idEmpresaFactura the idEmpresaFactura to set
	 */
	public void setIdEmpresaFactura(Long idEmpresaFactura) {
		this.idEmpresaFactura = idEmpresaFactura;
	}
	/**
	 * @return the facturadoPor
	 */
	public String getFacturadoPor() {
		return facturadoPor;
	}
	/**
	 * @param facturadoPor the facturadoPor to set
	 */
	public void setFacturadoPor(String facturadoPor) {
		this.facturadoPor = facturadoPor;
	}
	/**
	 * @return the razonSocialFPor
	 */
	public String getRazonSocialFPor() {
		return razonSocialFPor;
	}
	/**
	 * @param razonSocialFPor the razonSocialFPor to set
	 */
	public void setRazonSocialFPor(String razonSocialFPor) {
		this.razonSocialFPor = razonSocialFPor;
	}
	/**
	 * @return the rfc_FPor
	 */
	public String getRfc_FPor() {
		return rfc_FPor;
	}
	/**
	 * @param rfc_FPor the rfc_FPor to set
	 */
	public void setRfc_FPor(String rfc_FPor) {
		this.rfc_FPor = rfc_FPor;
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
	 * @return the imprimirTipoCambio
	 */
	public Boolean getImprimirTipoCambio() {
		return imprimirTipoCambio;
	}
	/**
	 * @param imprimirTipoCambio the imprimirTipoCambio to set
	 */
	public void setImprimirTipoCambio(Boolean imprimirTipoCambio) {
		this.imprimirTipoCambio = imprimirTipoCambio;
	}
	/**
	 * @return the montoRealPagado
	 */
	public Double getMontoRealPagado() {
		return montoRealPagado;
	}
	/**
	 * @param montoRealPagado the montoRealPagado to set
	 */
	public void setMontoRealPagado(Double montoRealPagado) {
		this.montoRealPagado = montoRealPagado;
	}
	/**
	 * @return the montoAPagar
	 */
	public Double getMontoAPagar() {
		return montoAPagar;
	}
	/**
	 * @param montoAPagar the montoAPagar to set
	 */
	public void setMontoAPagar(Double montoAPagar) {
		this.montoAPagar = montoAPagar;
	}
	/**
	 * @return the montoConLetra
	 */
	public String getMontoConLetra() {
		return montoConLetra;
	}
	/**
	 * @param montoConLetra the montoConLetra to set
	 */
	public void setMontoConLetra(String montoConLetra) {
		this.montoConLetra = montoConLetra;
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
	 * @return the nombre_Cliente
	 */
	public String getNombre_Cliente() {
		return nombre_Cliente;
	}
	/**
	 * @param nombre_Cliente the nombre_Cliente to set
	 */
	public void setNombre_Cliente(String nombre_Cliente) {
		this.nombre_Cliente = nombre_Cliente;
	}
	/**
	 * @return the rfc_Cliente
	 */
	public String getRfc_Cliente() {
		return rfc_Cliente;
	}
	/**
	 * @param rfc_Cliente the rfc_Cliente to set
	 */
	public void setRfc_Cliente(String rfc_Cliente) {
		this.rfc_Cliente = rfc_Cliente;
	}
	/**
	 * @return the contraRecibo
	 */
	public String getContraRecibo() {
		return contraRecibo;
	}
	/**
	 * @param contraRecibo the contraRecibo to set
	 */
	public void setContraRecibo(String contraRecibo) {
		this.contraRecibo = contraRecibo;
	}
	/**
	 * @return the fPago
	 */
	public Date getFPago() {
		return fPago;
	}
	/**
	 * @param pago the fPago to set
	 */
	public void setFPago(Date pago) {
		fPago = pago;
	}
	/**
	 * @return the condicionesPago
	 */
	public String getCondicionesPago() {
		return condicionesPago;
	}
	/**
	 * @param condicionesPago the condicionesPago to set
	 */
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
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
	 * @return the diasRestantesPago
	 */
	public Long getDiasRestantesPago() {
		return diasRestantesPago;
	}
	/**
	 * @param diasRestantesPago the diasRestantesPago to set
	 */
	public void setDiasRestantesPago(Long diasRestantesPago) {
		this.diasRestantesPago = diasRestantesPago;
	}
	/**
	 * @return the monedaPago
	 */
	public String getMonedaPago() {
		return monedaPago;
	}
	/**
	 * @param monedaPago the monedaPago to set
	 */
	public void setMonedaPago(String monedaPago) {
		this.monedaPago = monedaPago;
	}
	/**
	 * @return the frevision
	 */
	public Date getFrevision() {
		return frevision;
	}
	/**
	 * @param frevision the frevision to set
	 */
	public void setFrevision(Date frevision) {
		this.frevision = frevision;
	}
	/**
	 * @return the fEPago
	 */
	public Date getFEPago() {
		return fEPago;
	}
	/**
	 * @param pago the fEPago to set
	 */
	public void setFEPago(Date pago) {
		fEPago = pago;
	}
	/**
	 * @return the documentoRecibido
	 */
	public Long getDocumentoRecibido() {
		return documentoRecibido;
	}
	/**
	 * @param documentoRecibido the documentoRecibido to set
	 */
	public void setDocumentoRecibido(Long documentoRecibido) {
		this.documentoRecibido = documentoRecibido;
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
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	/**
	 * @return the ordenCompra
	 */
	public String getOrdenCompra() {
		return ordenCompra;
	}
	/**
	 * @param ordenCompra the ordenCompra to set
	 */
	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}
	/**
	 * @return the folioPC
	 */
	public String getFolioPC() {
		return folioPC;
	}
	/**
	 * @param folioPC the folioPC to set
	 */
	public void setFolioPC(String folioPC) {
		this.folioPC = folioPC;
	}
	/**
	 * @return the folioEntrega
	 */
	public String getFolioEntrega() {
		return folioEntrega;
	}
	/**
	 * @param folioEntrega the folioEntrega to set
	 */
	public void setFolioEntrega(String folioEntrega) {
		this.folioEntrega = folioEntrega;
	}
	/**
	 * @return the remision
	 */
	public Boolean getRemision() {
		return remision;
	}
	/**
	 * @param remision the remision to set
	 */
	public void setRemision(Boolean remision) {
		this.remision = remision;
	}
	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}
	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	/**
	 * @return the monedaPedido
	 */
	public String getMonedaPedido() {
		return monedaPedido;
	}
	/**
	 * @param monedaPedido the monedaPedido to set
	 */
	public void setMonedaPedido(String monedaPedido) {
		this.monedaPedido = monedaPedido;
	}
	/**
	 * @return the nivelIngresocliente
	 */
	public String getNivelIngresocliente() {
		return nivelIngresocliente;
	}
	/**
	 * @param nivelIngresocliente the nivelIngresocliente to set
	 */
	public void setNivelIngresocliente(String nivelIngresocliente) {
		this.nivelIngresocliente = nivelIngresocliente;
	}
	/**
	 * @return the montoTotalPedido
	 */
	public Double getMontoTotalPedido() {
		return montoTotalPedido;
	}
	/**
	 * @param montoTotalPedido the montoTotalPedido to set
	 */
	public void setMontoTotalPedido(Double montoTotalPedido) {
		this.montoTotalPedido = montoTotalPedido;
	}
	/**
	 * @return the numeroPiezasPedidoRelacionado
	 */
	public Integer getNumeroPiezasPedidoRelacionado() {
		return numeroPiezasPedidoRelacionado;
	}
	/**
	 * @param numeroPiezasPedidoRelacionado the numeroPiezasPedidoRelacionado to set
	 */
	public void setNumeroPiezasPedidoRelacionado(
			Integer numeroPiezasPedidoRelacionado) {
		this.numeroPiezasPedidoRelacionado = numeroPiezasPedidoRelacionado;
	}
	/**
	 * @return the numeroPiezasFactura
	 */
	public Integer getNumeroPiezasFactura() {
		return numeroPiezasFactura;
	}
	/**
	 * @param numeroPiezasFactura the numeroPiezasFactura to set
	 */
	public void setNumeroPiezasFactura(Integer numeroPiezasFactura) {
		this.numeroPiezasFactura = numeroPiezasFactura;
	}
	/**
	 * @return the rutaRelacionada
	 */
	public Ruta getRutaRelacionada() {
		return rutaRelacionada;
	}
	/**
	 * @param rutaRelacionada the rutaRelacionada to set
	 */
	public void setRutaRelacionada(Ruta rutaRelacionada) {
		this.rutaRelacionada = rutaRelacionada;
	}
	/**
	 * @return the montoFacturaDLS
	 */
	public Double getMontoFacturaDLS() {
		return montoFacturaDLS;
	}
	/**
	 * @param montoFacturaDLS the montoFacturaDLS to set
	 */
	public void setMontoFacturaDLS(Double montoFacturaDLS) {
		this.montoFacturaDLS = montoFacturaDLS;
	}
	/**
	 * @return the ivaDLS
	 */
	public Double getIvaDLS() {
		return ivaDLS;
	}
	/**
	 * @param ivaDLS the ivaDLS to set
	 */
	public void setIvaDLS(Double ivaDLS) {
		this.ivaDLS = ivaDLS;
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
	public Integer getRemOfact() {
		return remOfact;
	}
	public void setRemOfact(Integer remOfact) {
		this.remOfact = remOfact;
	}
	/**
	 * @return the partidas
	 */
	public List<PartidaFactura> getPartidas() {
		return partidas;
	}
	/**
	 * @param partidas the partidas to set
	 */
	public void setPartidas(List<PartidaFactura> partidas) {
		this.partidas = partidas;
	}
	/**
	 * @return the idDocumentoFiscal
	 */
	public Long getIdDocumentoFiscal() {
		return idDocumentoFiscal;
	}
	/**
	 * @param idDocumentoFiscal the idDocumentoFiscal to set
	 */
	public void setIdDocumentoFiscal(Long idDocumentoFiscal) {
		this.idDocumentoFiscal = idDocumentoFiscal;
	}
	/**
	 * @return the direccionFPor
	 */
	public String getDireccionFPor() {
		return direccionFPor;
	}
	/**
	 * @param direccionFPor the direccionFPor to set
	 */
	public void setDireccionFPor(String direccionFPor) {
		this.direccionFPor = direccionFPor;
	}
	/**
	 * @return the fEnvio
	 */
	public Date getfEnvio() {
		return fEnvio;
	}
	/**
	 * @param fEnvio the fEnvio to set
	 */
	public void setfEnvio(Date fEnvio) {
		this.fEnvio = fEnvio;
	}
	/**
	 * @return the medioEnvio
	 */
	public String getMedioEnvio() {
		return medioEnvio;
	}
	/**
	 * @param medioEnvio the medioEnvio to set
	 */
	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
	}
	/**
	 * @return the puntoEntrega
	 */
	public String getPuntoEntrega() {
		return puntoEntrega;
	}
	/**
	 * @param puntoEntrega the puntoEntrega to set
	 */
	public void setPuntoEntrega(String puntoEntrega) {
		this.puntoEntrega = puntoEntrega;
	}
	/**
	 * @return the facturarA
	 */
	public String getFacturarA() {
		return facturarA;
	}
	/**
	 * @param facturarA the facturarA to set
	 */
	public void setFacturarA(String facturarA) {
		this.facturarA = facturarA;
	}
	/**
	 * @return the entregarEn
	 */
	public String getEntregarEn() {
		return entregarEn;
	}
	/**
	 * @param entregarEn the entregarEn to set
	 */
	public void setEntregarEn(String entregarEn) {
		this.entregarEn = entregarEn;
	}
	/**
	 * @return the idioma
	 */
	public String getIdioma() {
		return idioma;
	}
	/**
	 * @param idioma the idioma to set
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public float getFlete() {
		return flete;
	}
	public void setFlete(float flete) {
		this.flete = flete;
	}
	/**
	 * @return the idDocFiscal
	 */
	public String getIdDocFiscal() {
		return idDocFiscal;
	}
	/**
	 * @param idDocFiscal the idDocFiscal to set
	 */
	public void setIdDocFiscal(String idDocFiscal) {
		this.idDocFiscal = idDocFiscal;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTipoPago() {
		return tipoPago;
	}
	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}
	public String getMetodoPago() {
		return metodoPago;
	}
	public void setMetodoPago(String metodoPago) {
		this.metodoPago = metodoPago;
	}
	public String getTipoRelacion() {
		return tipoRelacion;
	}
	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
	public String getRelacionUUID() {
		return relacionUUID;
	}
	public void setRelacionUUID(String relacionUUID) {
		this.relacionUUID = relacionUUID;
	}
	public String getTipoCambioF() {
		return tipoCambioF;
	}
	public void setTipoCambioF(String tipoCambioF) {
		this.tipoCambioF = tipoCambioF;
	}
	public String getTotalTraslados() {
		return totalTraslados;
	}
	public void setTotalTraslados(String totalTraslados) {
		this.totalTraslados = totalTraslados;
	}
	public String getTipoImpuesto() {
		return tipoImpuesto;
	}
	public void setTipoImpuesto(String tipoImpuesto) {
		this.tipoImpuesto = tipoImpuesto;
	}
	public String getTasa() {
		return tasa;
	}
	public void setTasa(String tasa) {
		this.tasa = tasa;
	}
	public String getTipoFactor() {
		return tipoFactor;
	}
	public void setTipoFactor(String tipoFactor) {
		this.tipoFactor = tipoFactor;
	}
	/**
	 * @return the idFacturaElectronica
	 */
	public Integer getIdFacturaElectronica() {
		return idFacturaElectronica;
	}
	/**
	 * @param idFacturaElectronica the idFacturaElectronica to set
	 */
	public void setIdFacturaElectronica(Integer idFacturaElectronica) {
		this.idFacturaElectronica = idFacturaElectronica;
	}
	
	
}
