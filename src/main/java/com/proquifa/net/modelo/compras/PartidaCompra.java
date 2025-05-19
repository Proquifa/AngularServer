/**
 * Llenar las variables de anaquelNumero y anaquelLetra antes de Ubicacion
 */
package com.proquifa.net.modelo.compras;

import java.util.Date;

import com.proquifa.net.modelo.comun.Producto;




/**
 * @author ernestogonzalezlozada
 *
 */
public class PartidaCompra extends Producto {
	
	private Long idPartidaCompra;
	private Date fechaTramitacion;
	private String responsable;
	private String compra;
	private Integer partida; 
	private String pedido;
	private Integer partidaPedido;
	private Long idComplemento;
	private Integer cantidadCompra;
	private Long totalPiezas;
	private String descripcionProducto;
	private String cotizacion;
	private String ubicacion;
	private Integer anaquelNumero;
	private String anaquelLetra;
	private String reasignado;
	private Double precioUnitario;
	private Double precioTotal;
	private Double montoTotal;
	private String pedimento;
	private String lote;
	private String lotePartida;
	private String tipoFlete;
	private String trafico;
	private Boolean confirmacion;
	private Boolean transito;
	private String destino;
	private String cliente;
	private String nombreCliente;
	private String nombreProveedor;
	private String comentarios;
	private String comentariosPHS;
	private String facturaProveedor;
	private String factura;
	private String fporCliente;
	private Boolean pagada;
	private Boolean porCancelar;
	private Boolean backOrder;
	private String bloqueadaAC;
	private Boolean aCambio;
	private Integer inTime;
	private String edoCliente;
	private Boolean porEnterarse;
	private Boolean abierto; 
	private String fcadPartida;
	private String decision;
	private String tiempoRespuesta;
	private Boolean almacenUE;
	private Boolean almacenUSA;
	private Boolean almacenMatriz;
	private Date fechaRealArriboUE;
	private Date fechaRealArriboUSA;
	private Date fechaRealArriboPQF;
	private Date fechaEsperadaArriboUE;
	private Date fechaEsperadaArriboUSA;
	private Date fechaEsperadaArriboPQF;
	private Date fechaEstimadaEntrega;
	private Date fechaCierre;
	private Date fechaEnvio;
	private String folioInspeccion;
	private String folioInspeccionPHS;
	private String folioInspeccionUE;
	private String folioPacking;
	private String folioNT;
	private String folioRN;
	private Integer diferenciaDias;
	private String etiqueta;

	private String cpedido;
	private String medioPago;
	private String condicionesPago;
	private Integer facturasTotal;
	private Boolean asignada;
	private String origenCompra;
	private String loteAnterior;
	
	private String origen;
	
	private byte[] certificadoPdf;
	
	private String rutaCertificadoActual;
	private String rutaCertificadoAnterior;
	
	private String noFactura; 
	private String folioFP;
	private String folioPG;
	
	private String caracteristicasFisicas;
	private String composicion;
	private String formulaQuimica;
	private String peligrosidad;
	private long idPpedido;
	private String controlProducto;
	private long idCompra;
	private Boolean certificadoModificado;
	
	private boolean certificadoNoDisponible;
	private String PaisEmpresaAFacturar;
	private String NombreEmpresaAFacturar;
	private long idEmpresaAFacturar;
	private String fechaString;
	
	private Integer idFactura;
	private String codBarras;
	private String codQrBolsa;
	

	
	public PartidaCompra() {
		super();
	}
	
	/**
	 * @return the idPartidaCompra
	 */
	public Long getIdPartidaCompra() {
		return idPartidaCompra;
	}
	/**
	 * @param idPartidaCompra the idPartidaCompra to set
	 */
	public void setIdPartidaCompra(Long idPartidaCompra) {
		this.idPartidaCompra = idPartidaCompra;
	}
	/**
	 * @return the fechaTramitacion
	 */
	public Date getFechaTramitacion() {
		return fechaTramitacion;
	}
	/**
	 * @param fechaTramitacion the fechaTramitacion to set
	 */
	public void setFechaTramitacion(Date fechaTramitacion) {
		this.fechaTramitacion = fechaTramitacion;
	}
	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}
	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
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
	 * @return the partida
	 */
	public Integer getPartida() {
		return partida;
	}
	/**
	 * @param partida the partida to set
	 */
	public void setPartida(Integer partida) {
		this.partida = partida;
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
	 * @return the partidaPedido
	 */
	public Integer getPartidaPedido() {
		return partidaPedido;
	}
	/**
	 * @param partidaPedido the partidaPedido to set
	 */
	public void setPartidaPedido(Integer partidaPedido) {
		this.partidaPedido = partidaPedido;
	}
	/**
	 * @return the idComplemento
	 */
	public Long getIdComplemento() {
		return idComplemento;
	}
	/**
	 * @param idComplemento the idComplemento to set
	 */
	public void setIdComplemento(Long idComplemento) {
		this.idComplemento = idComplemento;
	}
	/**
	 * @return the cantidadCompra
	 */
	public Integer getCantidadCompra() {
		return cantidadCompra;
	}
	/**
	 * @param cantidadCompra the cantidadCompra to set
	 */
	public void setCantidadCompra(Integer cantidadCompra) {
		this.cantidadCompra = cantidadCompra;
	}
	/**
	 * @return the totalPiezas
	 */
	public Long getTotalPiezas() {
		return totalPiezas;
	}
	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Long totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	/**
	 * @return the descripcionProducto
	 */
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	/**
	 * @param descripcionProducto the descripcionProducto to set
	 */
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	/**
	 * @return the cotizacion
	 */
	public String getCotizacion() {
		return cotizacion;
	}
	/**
	 * @param cotizacion the cotizacion to set
	 */
	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}
	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}
	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	/**
	 * @return the anaquelNumero
	 */
	public Integer getAnaquelNumero() {
		return anaquelNumero;
	}
	/**
	 * @param anaquelNumero the anaquelNumero to set
	 */
	public void setAnaquelNumero(Integer anaquelNumero) {
		this.anaquelNumero = anaquelNumero;
	}
	/**
	 * @return the anaquelLetra
	 */
	public String getAnaquelLetra() {
		return anaquelLetra;
	}
	/**
	 * @param anaquelLetra the anaquelLetra to set
	 */
	public void setAnaquelLetra(String anaquelLetra) {
		this.anaquelLetra = anaquelLetra;
	}
	/**
	 * @return the reasignado
	 */
	public String getReasignado() {
		return reasignado;
	}
	/**
	 * @param reasignado the reasignado to set
	 */
	public void setReasignado(String reasignado) {
		this.reasignado = reasignado;
	}
	/**
	 * @return the precioUnitario
	 */
	public Double getPrecioUnitario() {
		return precioUnitario;
	}
	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(Double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	/**
	 * @return the precioTotal
	 */
	public Double getPrecioTotal() {
		return precioTotal;
	}
	/**
	 * @param precioTotal the precioTotal to set
	 */
	public void setPrecioTotal(Double precioTotal) {
		this.precioTotal = precioTotal;
	}
	/**
	 * @return the montoTotal
	 */
	public Double getMontoTotal() {
		return montoTotal;
	}
	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	/**
	 * @return the pedimento
	 */
	public String getPedimento() {
		return pedimento;
	}
	/**
	 * @param pedimento the pedimento to set
	 */
	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
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
	 * @return the lotePartida
	 */
	public String getLotePartida() {
		return lotePartida;
	}
	/**
	 * @param lotePartida the lotePartida to set
	 */
	public void setLotePartida(String lotePartida) {
		this.lotePartida = lotePartida;
	}
	/**
	 * @return the tipoFlete
	 */
	public String getTipoFlete() {
		return tipoFlete;
	}
	/**
	 * @param tipoFlete the tipoFlete to set
	 */
	public void setTipoFlete(String tipoFlete) {
		this.tipoFlete = tipoFlete;
	}
	/**
	 * @return the trafico
	 */
	public String getTrafico() {
		return trafico;
	}
	/**
	 * @param trafico the trafico to set
	 */
	public void setTrafico(String trafico) {
		this.trafico = trafico;
	}
	/**
	 * @return the confirmacion
	 */
	public Boolean getConfirmacion() {
		return confirmacion;
	}
	/**
	 * @param confirmacion the confirmacion to set
	 */
	public void setConfirmacion(Boolean confirmacion) {
		this.confirmacion = confirmacion;
	}
	/**
	 * @return the transito
	 */
	public Boolean getTransito() {
		return transito;
	}
	/**
	 * @param transito the transito to set
	 */
	public void setTransito(Boolean transito) {
		this.transito = transito;
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
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
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
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the comentariosPHS
	 */
	public String getComentariosPHS() {
		return comentariosPHS;
	}
	/**
	 * @param comentariosPHS the comentariosPHS to set
	 */
	public void setComentariosPHS(String comentariosPHS) {
		this.comentariosPHS = comentariosPHS;
	}
	/**
	 * @return the facturaProveedor
	 */
	public String getFacturaProveedor() {
		return facturaProveedor;
	}
	/**
	 * @param facturaProveedor the facturaProveedor to set
	 */
	public void setFacturaProveedor(String facturaProveedor) {
		this.facturaProveedor = facturaProveedor;
	}
	/**
	 * @return the factura
	 */
	public String getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(String factura) {
		this.factura = factura;
	}
	/**
	 * @return the pagada
	 */
	public Boolean getPagada() {
		return pagada;
	}
	/**
	 * @param pagada the pagada to set
	 */
	public void setPagada(Boolean pagada) {
		this.pagada = pagada;
	}
	/**
	 * @return the porCancelar
	 */
	public Boolean getPorCancelar() {
		return porCancelar;
	}
	/**
	 * @param porCancelar the porCancelar to set
	 */
	public void setPorCancelar(Boolean porCancelar) {
		this.porCancelar = porCancelar;
	}
	/**
	 * @return the backOrder
	 */
	public Boolean getBackOrder() {
		return backOrder;
	}
	/**
	 * @param backOrder the backOrder to set
	 */
	public void setBackOrder(Boolean backOrder) {
		this.backOrder = backOrder;
	}
	/**
	 * @return the bloqueadaAC
	 */
	public String getBloqueadaAC() {
		return bloqueadaAC;
	}
	/**
	 * @param bloqueadaAC the bloqueadaAC to set
	 */
	public void setBloqueadaAC(String bloqueadaAC) {
		this.bloqueadaAC = bloqueadaAC;
	}
	/**
	 * @return the aCambio
	 */
	public Boolean getACambio() {
		return aCambio;
	}
	/**
	 * @param cambio the aCambio to set
	 */
	public void setACambio(Boolean cambio) {
		aCambio = cambio;
	}
	/**
	 * @return the inTime
	 */
	public Integer getInTime() {
		return inTime;
	}
	/**
	 * @param inTime the inTime to set
	 */
	public void setInTime(Integer inTime) {
		this.inTime = inTime;
	}
	/**
	 * @return the edoCliente
	 */
	public String getEdoCliente() {
		return edoCliente;
	}
	/**
	 * @param edoCliente the edoCliente to set
	 */
	public void setEdoCliente(String edoCliente) {
		this.edoCliente = edoCliente;
	}
	/**
	 * @return the porEnterarse
	 */
	public Boolean getPorEnterarse() {
		return porEnterarse;
	}
	/**
	 * @param porEnterarse the porEnterarse to set
	 */
	public void setPorEnterarse(Boolean porEnterarse) {
		this.porEnterarse = porEnterarse;
	}
	/**
	 * @return the abierto
	 */
	public Boolean getAbierto() {
		return abierto;
	}
	/**
	 * @param abierto the abierto to set
	 */
	public void setAbierto(Boolean abierto) {
		this.abierto = abierto;
	}
	
	/**
	 * @return the decision
	 */
	public String getDecision() {
		return decision;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(String decision) {
		this.decision = decision;
	}
	/**
	 * @return the tiempoRespuesta
	 */
	public String getTiempoRespuesta() {
		return tiempoRespuesta;
	}
	/**
	 * @param tiempoRespuesta the tiempoRespuesta to set
	 */
	public void setTiempoRespuesta(String tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}
	/**
	 * @return the almacenUE
	 */
	public Boolean getAlmacenUE() {
		return almacenUE;
	}
	/**
	 * @param almacenUE the almacenUE to set
	 */
	public void setAlmacenUE(Boolean almacenUE) {
		this.almacenUE = almacenUE;
	}
	/**
	 * @return the almacenUSA
	 */
	public Boolean getAlmacenUSA() {
		return almacenUSA;
	}
	/**
	 * @param almacenUSA the almacenUSA to set
	 */
	public void setAlmacenUSA(Boolean almacenUSA) {
		this.almacenUSA = almacenUSA;
	}
	/**
	 * @return the almacenMatriz
	 */
	public Boolean getAlmacenMatriz() {
		return almacenMatriz;
	}
	/**
	 * @param almacenMatriz the almacenMatriz to set
	 */
	public void setAlmacenMatriz(Boolean almacenMatriz) {
		this.almacenMatriz = almacenMatriz;
	}
	/**
	 * @return the fechaRealArriboUE
	 */
	public Date getFechaRealArriboUE() {
		return fechaRealArriboUE;
	}
	/**
	 * @param fechaRealArriboUE the fechaRealArriboUE to set
	 */
	public void setFechaRealArriboUE(Date fechaRealArriboUE) {
		this.fechaRealArriboUE = fechaRealArriboUE;
	}
	/**
	 * @return the fechaRealArriboUSA
	 */
	public Date getFechaRealArriboUSA() {
		return fechaRealArriboUSA;
	}
	/**
	 * @param fechaRealArriboUSA the fechaRealArriboUSA to set
	 */
	public void setFechaRealArriboUSA(Date fechaRealArriboUSA) {
		this.fechaRealArriboUSA = fechaRealArriboUSA;
	}
	/**
	 * @return the fechaRealArriboPQF
	 */
	public Date getFechaRealArriboPQF() {
		return fechaRealArriboPQF;
	}
	/**
	 * @param fechaRealArriboPQF the fechaRealArriboPQF to set
	 */
	public void setFechaRealArriboPQF(Date fechaRealArriboPQF) {
		this.fechaRealArriboPQF = fechaRealArriboPQF;
	}
	/**
	 * @return the fechaEsperadaArriboUE
	 */
	public Date getFechaEsperadaArriboUE() {
		return fechaEsperadaArriboUE;
	}
	/**
	 * @param fechaEsperadaArriboUE the fechaEsperadaArriboUE to set
	 */
	public void setFechaEsperadaArriboUE(Date fechaEsperadaArriboUE) {
		this.fechaEsperadaArriboUE = fechaEsperadaArriboUE;
	}
	/**
	 * @return the fechaEsperadaArriboUSA
	 */
	public Date getFechaEsperadaArriboUSA() {
		return fechaEsperadaArriboUSA;
	}
	/**
	 * @param fechaEsperadaArriboUSA the fechaEsperadaArriboUSA to set
	 */
	public void setFechaEsperadaArriboUSA(Date fechaEsperadaArriboUSA) {
		this.fechaEsperadaArriboUSA = fechaEsperadaArriboUSA;
	}
	/**
	 * @return the fechaEsperadaArriboPQF
	 */
	public Date getFechaEsperadaArriboPQF() {
		return fechaEsperadaArriboPQF;
	}
	/**
	 * @param fechaEsperadaArriboPQF the fechaEsperadaArriboPQF to set
	 */
	public void setFechaEsperadaArriboPQF(Date fechaEsperadaArriboPQF) {
		this.fechaEsperadaArriboPQF = fechaEsperadaArriboPQF;
	}
	/**
	 * @return the fechaEstimadaEntrega
	 */
	public Date getFechaEstimadaEntrega() {
		return fechaEstimadaEntrega;
	}
	/**
	 * @param fechaEstimadaEntrega the fechaEstimadaEntrega to set
	 */
	public void setFechaEstimadaEntrega(Date fechaEstimadaEntrega) {
		this.fechaEstimadaEntrega = fechaEstimadaEntrega;
	}
	/**
	 * @return the fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}
	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	/**
	 * @return the fechaEnvio
	 */
	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	/**
	 * @param fechaEnvio the fechaEnvio to set
	 */
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
	/**
	 * @return the folioInspeccion
	 */
	public String getFolioInspeccion() {
		return folioInspeccion;
	}
	/**
	 * @param folioInspeccion the folioInspeccion to set
	 */
	public void setFolioInspeccion(String folioInspeccion) {
		this.folioInspeccion = folioInspeccion;
	}
	/**
	 * @return the folioInspeccionPHS
	 */
	public String getFolioInspeccionPHS() {
		return folioInspeccionPHS;
	}
	/**
	 * @param folioInspeccionPHS the folioInspeccionPHS to set
	 */
	public void setFolioInspeccionPHS(String folioInspeccionPHS) {
		this.folioInspeccionPHS = folioInspeccionPHS;
	}
	/**
	 * @return the folioInspeccionUE
	 */
	public String getFolioInspeccionUE() {
		return folioInspeccionUE;
	}
	/**
	 * @param folioInspeccionUE the folioInspeccionUE to set
	 */
	public void setFolioInspeccionUE(String folioInspeccionUE) {
		this.folioInspeccionUE = folioInspeccionUE;
	}
	/**
	 * @return the folioPacking
	 */
	public String getFolioPacking() {
		return folioPacking;
	}
	/**
	 * @param folioPacking the folioPacking to set
	 */
	public void setFolioPacking(String folioPacking) {
		this.folioPacking = folioPacking;
	}
	/**
	 * @return the folioNT
	 */
	public String getFolioNT() {
		return folioNT;
	}
	/**
	 * @param folioNT the folioNT to set
	 */
	public void setFolioNT(String folioNT) {
		this.folioNT = folioNT;
	}
	/**
	 * @return the folioRN
	 */
	public String getFolioRN() {
		return folioRN;
	}
	/**
	 * @param folioRN the folioRN to set
	 */
	public void setFolioRN(String folioRN) {
		this.folioRN = folioRN;
	}
	/**
	 * @return the diferenciaDias
	 */
	public Integer getDiferenciaDias() {
		return diferenciaDias;
	}
	/**
	 * @param diferenciaDias the diferenciaDias to set
	 */
	public void setDiferenciaDias(Integer diferenciaDias) {
		this.diferenciaDias = diferenciaDias;
	}
	/**
	 * @return the fcadPartida
	 */
	public String getFcadPartida() {
		return fcadPartida;
	}
	/**
	 * @param fcadPartida the fcadPartida to set
	 */
	public void setFcadPartida(String fcadPartida) {
		this.fcadPartida = fcadPartida;
	}
	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}
	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}
	public String getFporCliente() {
		return fporCliente;
	}
	public void setFporCliente(String fporCliente) {
		this.fporCliente = fporCliente;
	}
	public String getCpedido() {
		return cpedido;
	}
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getCondicionesPago() {
		return condicionesPago;
	}
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}
	
	public Boolean getAsignada() {
		return asignada;
	}
	public void setAsignada(Boolean asignada) {
		this.asignada = asignada;
	}
	public Integer getFacturasTotal() {
		return facturasTotal;
	}
	public void setFacturasTotal(Integer facturasTotal) {
		this.facturasTotal = facturasTotal;
	}
	public String getOrigenCompra() {
		return origenCompra;
	}
	public void setOrigenCompra(String origenCompra) {
		this.origenCompra = origenCompra;
	}
	public String getLoteAnterior() {
		return loteAnterior;
	}
	public void setLoteAnterior(String loteAnterior) {
		this.loteAnterior = loteAnterior;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public byte[] getCertificadoPdf() {
		return certificadoPdf;
	}
	public void setCertificadoPdf(byte[] certificadoPdf) {
		this.certificadoPdf = certificadoPdf;
	}
	public String getRutaCertificadoActual() {
		return rutaCertificadoActual;
	}
	public void setRutaCertificadoActual(String rutaCertificadoActual) {
		this.rutaCertificadoActual = rutaCertificadoActual;
	}
	public String getRutaCertificadoAnterior() {
		return rutaCertificadoAnterior;
	}
	public void setRutaCertificadoAnterior(String rutaCertificadoAnterior) {
		this.rutaCertificadoAnterior = rutaCertificadoAnterior;
	}
	public String getNoFactura() {
		return noFactura;
	}
	public void setNoFactura(String noFactura) {
		this.noFactura = noFactura;
	}
	public String getCaracteristicasFisicas() {
		return caracteristicasFisicas;
	}
	public void setCaracteristicasFisicas(String caracteristicasFisicas) {
		this.caracteristicasFisicas = caracteristicasFisicas;
	}
	public String getComposicion() {
		return composicion;
	}
	public void setComposicion(String composicion) {
		this.composicion = composicion;
	}
	public String getFormulaQuimica() {
		return formulaQuimica;
	}
	public void setFormulaQuimica(String formulaQuimica) {
		this.formulaQuimica = formulaQuimica;
	}
	public String getPeligrosidad() {
		return peligrosidad;
	}
	public void setPeligrosidad(String peligrosidad) {
		this.peligrosidad = peligrosidad;
	}

	/**
	 * @return the folioFP
	 */
	public String getFolioFP() {
		return folioFP;
	}

	/**
	 * @param folioFP the folioFP to set
	 */
	public void setFolioFP(String folioFP) {
		this.folioFP = folioFP;
	}

	/**
	 * @return the folioPG
	 */
	public String getFolioPG() {
		return folioPG;
	}

	/**
	 * @param folioPG the folioPG to set
	 */
	public void setFolioPG(String folioPG) {
		this.folioPG = folioPG;
	}

	/**
	 * @return the idPpedido
	 */
	public long getIdPpedido() {
		return idPpedido;
	}

	/**
	 * @param idPpedido the idPpedido to set
	 */
	public void setIdPpedido(long idPpedido) {
		this.idPpedido = idPpedido;
	}

	/**
	 * @return the controlProducto
	 */
	public String getControlProducto() {
		return controlProducto;
	}

	/**
	 * @param controlProducto the controlProducto to set
	 */
	public void setControlProducto(String controlProducto) {
		this.controlProducto = controlProducto;
	}

	public long getIdCompra() {
		return idCompra;
	}

	public void setIdCompra(long idCompra) {
		this.idCompra = idCompra;
	}



	public Boolean getCertificadoModificado() {
		return certificadoModificado;
	}

	public void setCertificadoModificado(Boolean certificadoModificado) {
		this.certificadoModificado = certificadoModificado;
	}

	public Boolean getCertificadoNoDisponible() {
		return certificadoNoDisponible;
	}

	public void setCertificadoNoDisponible(Boolean certificadoNoDisponible) {
		this.certificadoNoDisponible = certificadoNoDisponible;
	}

	public String getPaisEmpresaAFacturar() {
		return PaisEmpresaAFacturar;
	}

	public void setPaisEmpresaAFacturar(String paisEmpresaAFacturar) {
		PaisEmpresaAFacturar = paisEmpresaAFacturar;
	}

	public String getNombreEmpresaAFacturar() {
		return NombreEmpresaAFacturar;
	}

	public void setNombreEmpresaAFacturar(String nombreEmpresaAFacturar) {
		NombreEmpresaAFacturar = nombreEmpresaAFacturar;
	}

	public long getIdEmpresaAFacturar() {
		return idEmpresaAFacturar;
	}

	public void setIdEmpresaAFacturar(long idEmpresaAFacturar) {
		this.idEmpresaAFacturar = idEmpresaAFacturar;
	}

	public String getFechaString() {
		return fechaString;
	}

	public void setFechaString(String fechaString) {
		this.fechaString = fechaString;
	}

	public Integer getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public String getCodBarras() {
		return codBarras;
	}

	public void setCodBarras(String codBarras) {
		this.codBarras = codBarras;
	}

	public String getCodQrBolsa() {
		return codQrBolsa;
	}

	public void setCodQrBolsa(String codQrBolsa) {
		this.codQrBolsa = codQrBolsa;
	}

	
}