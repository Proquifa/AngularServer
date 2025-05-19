/**
 * 
 */
package com.proquifa.net.modelo.compras.rechazos;

import java.util.Date;
import java.util.List;

/**
 * @author ymendez
 *
 */
public class Rechazos {

	private Integer idProveedor;
	private String proveedor;
	
	private Integer totalOC;
	private Integer totalProducto;
	private Date fechaInspeccion;
	private String fechaInspeccionFormato;
	
	private Integer totalProveedores;
	
	private String codigo;
	private String lote;
	private String concepto;
	private String compra;
	private String cpedido;
	private String inspector;
	
	private Date fee;
	private String feeFormato;
	private String tipo;
	private Integer dre;
	
	private String pedido;
	private String pendiente;
	private Integer idFabrica;
	
	private String identificador;
	
	private List<Rechazos> lstRechazos;
	
	
	private String destino;
	private String manejo;
	private String origen;
	private String causa;
	private String rechazo;
	private String imagenRechazo;
	private Integer idPCompra;
	private Double costo;
	
	private Integer idPPedido;
	
	/**
	 * 
	 */
	public Rechazos() {
		super();
	}
	
	/**
	 * @param idProveedor
	 * @param proveedor
	 * @param totalOC
	 * @param totalProducto
	 * @param fechaInspeccion
	 * @param fechaInspeccionFormato
	 * @param totalProveedores
	 * @param codigo
	 * @param lote
	 * @param concepto
	 * @param compra
	 * @param cpedido
	 * @param inspector
	 * @param fee
	 * @param feeFormato
	 * @param tipo
	 * @param dre
	 * @param pedido
	 * @param pendiente
	 * @param idFabrica
	 * @param identificador
	 * @param lstRechazos
	 * @param destino
	 * @param manejo
	 * @param origen
	 * @param causa
	 * @param rechazo
	 * @param imagenRechazo
	 */
	public Rechazos(Rechazos rechazo) {
		super();
		this.idProveedor = rechazo.idProveedor;
		this.proveedor = rechazo.proveedor;
		this.totalOC = rechazo.totalOC;
		this.totalProducto = rechazo.totalProducto;
		this.fechaInspeccion = rechazo.fechaInspeccion;
		this.fechaInspeccionFormato = rechazo.fechaInspeccionFormato;
		this.totalProveedores = rechazo.totalProveedores;
		this.codigo = rechazo.codigo;
		this.lote = rechazo.lote;
		this.concepto = rechazo.concepto;
		this.compra = rechazo.compra;
		this.cpedido = rechazo.cpedido;
		this.inspector = rechazo.inspector;
		this.fee = rechazo.fee;
		this.feeFormato = rechazo.feeFormato;
		this.tipo = rechazo.tipo;
		this.dre = rechazo.dre;
		this.pedido = rechazo.pedido;
		this.pendiente = rechazo.pendiente;
		this.idFabrica = rechazo.idFabrica;
		this.identificador = rechazo.identificador;
		this.destino = rechazo.destino;
		this.manejo = rechazo.manejo;
		this.origen = rechazo.origen;
		this.causa = rechazo.causa;
		this.rechazo = rechazo.rechazo;
		this.imagenRechazo = rechazo.imagenRechazo;
		this.idPCompra = rechazo.idPCompra;
		this.idPPedido = rechazo.idPPedido;
	}



	/**
	 * @return the idProveedor
	 */
	public Integer getIdProveedor() {
		return idProveedor;
	}

	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * @return the proveedor
	 */
	public String getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the totalOC
	 */
	public Integer getTotalOC() {
		return totalOC;
	}

	/**
	 * @param totalOC the totalOC to set
	 */
	public void setTotalOC(Integer totalOC) {
		this.totalOC = totalOC;
	}

	/**
	 * @return the totalProducto
	 */
	public Integer getTotalProducto() {
		return totalProducto;
	}

	/**
	 * @param totalProducto the totalProducto to set
	 */
	public void setTotalProducto(Integer totalProducto) {
		this.totalProducto = totalProducto;
	}

	/**
	 * @return the fechaInspeccion
	 */
	public Date getFechaInspeccion() {
		return fechaInspeccion;
	}

	/**
	 * @param fechaInspeccion the fechaInspeccion to set
	 */
	public void setFechaInspeccion(Date fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}

	/**
	 * @return the fechaInspeccionFormato
	 */
	public String getFechaInspeccionFormato() {
		return fechaInspeccionFormato;
	}

	/**
	 * @param fechaInspeccionFormato the fechaInspeccionFormato to set
	 */
	public void setFechaInspeccionFormato(String fechaInspeccionFormato) {
		this.fechaInspeccionFormato = fechaInspeccionFormato;
	}

	/**
	 * @return the totalProveedores
	 */
	public Integer getTotalProveedores() {
		return totalProveedores;
	}

	/**
	 * @param totalProveedores the totalProveedores to set
	 */
	public void setTotalProveedores(Integer totalProveedores) {
		this.totalProveedores = totalProveedores;
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
	 * @return the lote
	 */
	public String getLote() {
		return lote;
	}

	/**
	 * @param lote the lote to set
	 */
	public void setLote(String lote) {
		this.lote = lote;
	}

	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}

	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	/**
	 * @return the compra
	 */
	public String getCompra() {
		return compra;
	}

	/**
	 * @param compra the compra to set
	 */
	public void setCompra(String compra) {
		this.compra = compra;
	}

	/**
	 * @return the inspector
	 */
	public String getInspector() {
		return inspector;
	}

	/**
	 * @param inspector the inspector to set
	 */
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}

	/**
	 * @return the fee
	 */
	public Date getFee() {
		return fee;
	}

	/**
	 * @param fee the fee to set
	 */
	public void setFee(Date fee) {
		this.fee = fee;
	}

	/**
	 * @return the feeFormato
	 */
	public String getFeeFormato() {
		return feeFormato;
	}

	/**
	 * @param feeFormato the feeFormato to set
	 */
	public void setFeeFormato(String feeFormato) {
		this.feeFormato = feeFormato;
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
	 * @return the dre
	 */
	public Integer getDre() {
		return dre;
	}

	/**
	 * @param dre the dre to set
	 */
	public void setDre(Integer dre) {
		this.dre = dre;
	}

	/**
	 * @return the lstRechazos
	 */
	public List<Rechazos> getLstRechazos() {
		return lstRechazos;
	}

	/**
	 * @param lstRechazos the lstRechazos to set
	 */
	public void setLstRechazos(List<Rechazos> lstRechazos) {
		this.lstRechazos = lstRechazos;
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
	 * @return the pendiente
	 */
	public String getPendiente() {
		return pendiente;
	}



	/**
	 * @param pendiente the pendiente to set
	 */
	public void setPendiente(String pendiente) {
		this.pendiente = pendiente;
	}



	/**
	 * @return the idFabrica
	 */
	public Integer getIdFabrica() {
		return idFabrica;
	}



	/**
	 * @param idFabrica the idFabrica to set
	 */
	public void setIdFabrica(Integer idFabrica) {
		this.idFabrica = idFabrica;
	}



	/**
	 * @return the identificador
	 */
	public String getIdentificador() {
		return identificador;
	}



	/**
	 * @param identificador the identificador to set
	 */
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}



	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}



	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}



	/**
	 * @return the manejo
	 */
	public String getManejo() {
		return manejo;
	}



	/**
	 * @param manejo the manejo to set
	 */
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}



	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}



	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}



	/**
	 * @return the causa
	 */
	public String getCausa() {
		return causa;
	}



	/**
	 * @param causa the causa to set
	 */
	public void setCausa(String causa) {
		this.causa = causa;
	}



	/**
	 * @return the rechazo
	 */
	public String getRechazo() {
		return rechazo;
	}



	/**
	 * @param rechazo the rechazo to set
	 */
	public void setRechazo(String rechazo) {
		this.rechazo = rechazo;
	}



	/**
	 * @return the imagenRechazo
	 */
	public String getImagenRechazo() {
		return imagenRechazo;
	}



	/**
	 * @param imagenRechazo the imagenRechazo to set
	 */
	public void setImagenRechazo(String imagenRechazo) {
		this.imagenRechazo = imagenRechazo;
	}

	/**
	 * @return the idPCompra
	 */
	public Integer getIdPCompra() {
		return idPCompra;
	}

	/**
	 * @param idPCompra the idPCompra to set
	 */
	public void setIdPCompra(Integer idPCompra) {
		this.idPCompra = idPCompra;
	}

	/**
	 * @return the costo
	 */
	public Double getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Double costo) {
		this.costo = costo;
	}

	/**
	 * @return the idPPedido
	 */
	public Integer getIdPPedido() {
		return idPPedido;
	}

	/**
	 * @param idPPedido the idPPedido to set
	 */
	public void setIdPPedido(Integer idPPedido) {
		this.idPPedido = idPPedido;
	}
	
	
}
