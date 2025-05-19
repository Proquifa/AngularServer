package com.proquifa.net.modelo.comun.facturaElectronica;

public class PFacturaElectronica {
	private int idPFactura;
	private int factura;
	private String claveProdServ;
	private String noIdentificacion;
	private String cantidad;
	private String claveUnidad;
	private String unidad;
	private String descripcion;
	private String valorUnitario;
	private String importe;
	
	private String impuestoBase;
	private String impuestoClave;
	private String impuestoTipoFactor;
	private String impuestoTasaOCuota;
	private String impuestoImporte;
	
	private String adnNumeroPedimento;
	
	private Integer ppedido;
	private String cpedido;
	
	private String lineaDeOrden;
	
	private Integer part;
	private String notas;
	
	private String comentario;
	
	public Integer getPpedido() {
		return ppedido;
	}
	public void setPpedido(Integer ppedido) {
		this.ppedido = ppedido;
	}
	public String getCpedido() {
		return cpedido;
	}
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}	
	public int getIdPFactura() {
		return idPFactura;
	}
	public void setIdPFactura(int idPFactura) {
		this.idPFactura = idPFactura;
	}
	public int getFactura() {
		return factura;
	}
	public void setFactura(int factura) {
		this.factura = factura;
	}
	public String getClaveProdServ() {
		return claveProdServ;
	}
	public void setClaveProdServ(String claveProductoServicio) {
		this.claveProdServ = claveProductoServicio;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public String getImporte() {
		return importe;
	}
	public void setImporte(String importe) {
		this.importe = importe;
	}
	public String getImpuestoBase() {
		return impuestoBase;
	}
	public void setImpuestoBase(String impuestoBase) {
		this.impuestoBase = impuestoBase;
	}
	public String getImpuestoClave() {
		return impuestoClave;
	}
	public void setImpuestoClave(String impuestoClave) {
		this.impuestoClave = impuestoClave;
	}
	public String getImpuestoTipoFactor() {
		return impuestoTipoFactor;
	}
	public void setImpuestoTipoFactor(String impuestoTipoFactor) {
		this.impuestoTipoFactor = impuestoTipoFactor;
	}
	public String getImpuestoTasaOCuota() {
		return impuestoTasaOCuota;
	}
	public void setImpuestoTasaOCuota(String impuestoTasaOCuota) {
		this.impuestoTasaOCuota = impuestoTasaOCuota;
	}
	public String getImpuestoImporte() {
		return impuestoImporte;
	}
	public void setImpuestoImporte(String impuestoImporte) {
		this.impuestoImporte = impuestoImporte;
	}
	public String getNoIdentificacion() {
		return noIdentificacion;
	}
	public void setNoIdentificacion(String noIdentificacion) {
		this.noIdentificacion = noIdentificacion;
	}
	public String getClaveUnidad() {
		return claveUnidad;
	}
	public void setClaveUnidad(String claveUnidad) {
		this.claveUnidad = claveUnidad;
	}
	public String getAdnNumeroPedimento() {
		return adnNumeroPedimento;
	}
	public void setAdnNumeroPedimento(String adnNumeroPedimento) {
		this.adnNumeroPedimento = adnNumeroPedimento;
	}
	/**
	 * @return the part
	 */
	public Integer getPart() {
		return part;
	}
	/**
	 * @param part the part to set
	 */
	public void setPart(Integer part) {
		this.part = part;
	}
	public String getLineaDeOrden() {
		return lineaDeOrden;
	}
	public void setLineaDeOrden(String lineaDeOrden) {
		this.lineaDeOrden = lineaDeOrden;
	}
	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}
	/**
	 * @param comentario the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	/**
	 * @return the notas
	 */
	public String getNotas() {
		return notas;
	}
	/**
	 * @param notas the notas to set
	 */
	public void setNotas(String notas) {
		this.notas = notas;
	}
}
