package com.proquifa.net.modelo.compras;

import java.util.Date;

public class PermisoImportacion {
	private String tipoProducto;
	private String fabrica;
	private String codigoProducto;
	private String nombreProducto;
	private float costo;
	private String moneda;
	private String tipoPermiso;
	private String documento;
	private Date fechaET;
	private Date fechaEL;
	private String fraccionArancelaria;
	private String tipoSolicitudPermiso;
	private String clasificacionProducto;
	private String clasificacionSolictud;
	private String diasFiltro;
	private String origen;
	private String origenFolio;
	private String estadoProducto;
	private String controlProducto;
	private Double precioUnitario;
	private String cas;
	private String estadoPermiso;
	private String datosToxicologicos;
	private String folioEntrada;
	private String noEntrada;
	private String estadoFisico;
	private String etiqueta;
	private String subTipo;
	private Date fecha;
	private String estado;
	private Long idProducto;
	private Double cantidad;
	private String idSolicitud;
	private String pedCot;
	private Date finicio;
	
	private String folioLote;
	private String fechaSometio;
	private String doctoEntrada;
	private String numEntrada;
	private String folioSolicitud;
	private String justificacion;

	private Date fechaLiberacion;
	private Integer noProductos;
	private Date fechaFin;
	private String proveedor;
	private String marca;
	private String reqPermiso;
	private String sistema;
	private String concepto;
	private String unidad;
	private String depositarioInternacional;
	private String pais;
	private Long piezas;
	private Double monto;
	private String folio;
	private String docto;
	private String partida;
	private String cantidadString;
	private String idpcotiza;
	
		/**
	 * @return the finicio
	 */
	public Date getFinicio() {
		return finicio;
	}
	/**
	 * @param finicio the finicio to set
	 */
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}
	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}
	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	/**
	 * @return the cantidad
	 */
	public Double getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the tipoProducto
	 */
	public String getTipoProducto() {
		return tipoProducto;
	}
	/**
	 * @param tipoProducto the tipoProducto to set
	 */
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	/**
	 * @return the fabrica
	 */
	public String getFabrica() {
		return fabrica;
	}
	/**
	 * @param fabrica the fabrica to set
	 */
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}
	/**
	 * @return the costo
	 */
	public float getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(float costo) {
		this.costo = costo;
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
	 * @return the tipoPermiso
	 */
	public String getTipoPermiso() {
		return tipoPermiso;
	}
	/**
	 * @param tipoPermiso the tipoPermiso to set
	 */
	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}
	/**
	 * @return the documento
	 */
	public String getDocumento() {
		return documento;
	}
	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	/**
	 * @return the fechaET
	 */
	public Date getFechaET() {
		return fechaET;
	}
	/**
	 * @param fechaET the fechaET to set
	 */
	public void setFechaET(Date fechaET) {
		this.fechaET = fechaET;
	}
	/**
	 * @return the fechaEL
	 */
	public Date getFechaEL() {
		return fechaEL;
	}
	/**
	 * @param fechaEL the fechaEL to set
	 */
	public void setFechaEL(Date fechaEL) {
		this.fechaEL = fechaEL;
	}
	/**
	 * @return the fraccionArancelaria
	 */
	public String getFraccionArancelaria() {
		return fraccionArancelaria;
	}
	/**
	 * @param fraccionArancelaria the fraccionArancelaria to set
	 */
	public void setFraccionArancelaria(String fraccionArancelaria) {
		this.fraccionArancelaria = fraccionArancelaria;
	}
	/**
	 * @return the clasificacionProducto
	 */
	public String getClasificacionProducto() {
		return clasificacionProducto;
	}
	/**
	 * @param clasificacionProducto the clasificacionProducto to set
	 */
	public void setClasificacionProducto(String clasificacionProducto) {
		this.clasificacionProducto = clasificacionProducto;
	}
	/**
	 * @return the tipoSolicitudPermiso
	 */
	public String getTipoSolicitudPermiso() {
		return tipoSolicitudPermiso;
	}
	/**
	 * @param tipoSolicitudPermiso the tipoSolicitudPermiso to set
	 */
	public void setTipoSolicitudPermiso(String tipoSolicitudPermiso) {
		this.tipoSolicitudPermiso = tipoSolicitudPermiso;
	}
	/**
	 * @return the diasFiltro
	 */
	public String getDiasFiltro() {
		return diasFiltro;
	}
	/**
	 * @param diasFiltro the diasFiltro to set
	 */
	public void setDiasFiltro(String diasFiltro) {
		this.diasFiltro = diasFiltro;
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
	 * @return the estadoProducto
	 */
	public String getEstadoProducto() {
		return estadoProducto;
	}
	/**
	 * @param estadoProducto the estadoProducto to set
	 */
	public void setEstadoProducto(String estadoProducto) {
		this.estadoProducto = estadoProducto;
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
	 * @return the cas
	 */
	public String getCas() {
		return cas;
	}
	/**
	 * @param cas the cas to set
	 */
	public void setCas(String cas) {
		this.cas = cas;
	}
	/**
	 * @return the estadoPermiso
	 */
	public String getEstadoPermiso() {
		return estadoPermiso;
	}
	/**
	 * @param estadoPermiso the estadoPermiso to set
	 */
	public void setEstadoPermiso(String estadoPermiso) {
		this.estadoPermiso = estadoPermiso;
	}
	/**
	 * @return the datosToxicologicos
	 */
	public String getDatosToxicologicos() {
		return datosToxicologicos;
	}
	/**
	 * @param datosToxicologicos the datosToxicologicos to set
	 */
	public void setDatosToxicologicos(String datosToxicologicos) {
		this.datosToxicologicos = datosToxicologicos;
	}
	/**
	 * @return the folioEntrada
	 */
	public String getFolioEntrada() {
		return folioEntrada;
	}
	/**
	 * @param folioEntrada the folioEntrada to set
	 */
	public void setFolioEntrada(String folioEntrada) {
		this.folioEntrada = folioEntrada;
	}
	/**
	 * @return the noEntrada
	 */
	public String getNoEntrada() {
		return noEntrada;
	}
	/**
	 * @param noEntrada the noEntrada to set
	 */
	public void setNoEntrada(String noEntrada) {
		this.noEntrada = noEntrada;
	}
	/**
	 * @return the codigoProducto
	 */
	public String getCodigoProducto() {
		return codigoProducto;
	}
	/**
	 * @param codigoProducto the codigoProducto to set
	 */
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	/**
	 * @return the nombreProducto
	 */
	public String getNombreProducto() {
		return nombreProducto;
	}
	/**
	 * @param nombreProducto the nombreProducto to set
	 */
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	/**
	 * @return the estadoFisico
	 */
	public String getEstadoFisico() {
		return estadoFisico;
	}
	/**
	 * @param estadoFisico the estadoFisico to set
	 */
	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
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
	/**
	 * @return the origenFolio
	 */
	public String getOrigenFolio() {
		return origenFolio;
	}
	/**
	 * @param origenFolio the origenFolio to set
	 */
	public void setOrigenFolio(String origenFolio) {
		this.origenFolio = origenFolio;
	}
	/**
	 * @return the clasificacionSolictud
	 */
	public String getClasificacionSolictud() {
		return clasificacionSolictud;
	}
	/**
	 * @param clasificacionSolictud the clasificacionSolictud to set
	 */
	public void setClasificacionSolictud(String clasificacionSolictud) {
		this.clasificacionSolictud = clasificacionSolictud;
	}
	/**
	 * @return the subTipo
	 */
	public String getSubTipo() {
		return subTipo;
	}
	/**
	 * @param subTipo the subTipo to set
	 */
	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}
	/**
	 * @return the idSolicitud
	 */
	public String getIdSolicitud() {
		return idSolicitud;
	}
	/**
	 * @param idSolicitud the idSolicitud to set
	 */
	public void setIdSolicitud(String idSolicitud) {
		this.idSolicitud = idSolicitud;
	}
	/**
	 * @return the pedCot
	 */
	public String getPedCot() {
		return pedCot;
	}
	/**
	 * @param pedCot the pedCot to set
	 */
	public void setPedCot(String pedCot) {
		this.pedCot = pedCot;
	}
	/**
	 * @return the folioLote
	 */
	public String getFolioLote() {
		return folioLote;
	}
	/**
	 * @param folioLote the folioLote to set
	 */
	public void setFolioLote(String folioLote) {
		this.folioLote = folioLote;
	}
	/**
	 * @return the fechaSometio
	 */
	public String getFechaSometio() {
		return fechaSometio;
	}
	/**
	 * @param fechaSometio the fechaSometio to set
	 */
	public void setFechaSometio(String fechaSometio) {
		this.fechaSometio = fechaSometio;
	}
	/**
	 * @return the doctoEntrada
	 */
	public String getDoctoEntrada() {
		return doctoEntrada;
	}
	/**
	 * @param doctoEntrada the doctoEntrada to set
	 */
	public void setDoctoEntrada(String doctoEntrada) {
		this.doctoEntrada = doctoEntrada;
	}
	/**
	 * @return the numEntrada
	 */
	public String getNumEntrada() {
		return numEntrada;
	}
	/**
	 * @param numEntrada the numEntrada to set
	 */
	public void setNumEntrada(String numEntrada) {
		this.numEntrada = numEntrada;
	}
	/**
	 * @return the folioSolicitud
	 */
	public String getFolioSolicitud() {
		return folioSolicitud;
	}
	/**
	 * @param folioSolicitud the folioSolicitud to set
	 */
	public void setFolioSolicitud(String folioSolicitud) {
		this.folioSolicitud = folioSolicitud;
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
	 * @return the idProducto
	 */
	public Long getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	/**
	 * @return the fechaLiberacion
	 */
	public Date getFechaLiberacion() {
		return fechaLiberacion;
	}
	/**
	 * @param fechaLiberacion the fechaLiberacion to set
	 */
	public void setFechaLiberacion(Date fechaLiberacion) {
		this.fechaLiberacion = fechaLiberacion;
	}
	/**
	 * @return the noProductos
	 */
	public Integer getNoProductos() {
		return noProductos;
	}
	/**
	 * @param noProductos the noProductos to set
	 */
	public void setNoProductos(Integer noProductos) {
		this.noProductos = noProductos;
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
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the reqPermiso
	 */
	public String getReqPermiso() {
		return reqPermiso;
	}
	/**
	 * @param reqPermiso the reqPermiso to set
	 */
	public void setReqPermiso(String reqPermiso) {
		this.reqPermiso = reqPermiso;
	}
	/**
	 * @return the sistema
	 */
	public String getSistema() {
		return sistema;
	}
	/**
	 * @param sistema the sistema to set
	 */
	public void setSistema(String sistema) {
		this.sistema = sistema;
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
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}
	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	/**
	 * @return the depositarioInternacional
	 */
	public String getDepositarioInternacional() {
		return depositarioInternacional;
	}
	/**
	 * @param depositarioInternacional the depositarioInternacional to set
	 */
	public void setDepositarioInternacional(String depositarioInternacional) {
		this.depositarioInternacional = depositarioInternacional;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the piezas
	 */
	public Long getPiezas() {
		return piezas;
	}
	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(Long piezas) {
		this.piezas = piezas;
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
	 * @return the docto
	 */
	public String getDocto() {
		return docto;
	}
	/**
	 * @param docto the docto to set
	 */
	public void setDocto(String docto) {
		this.docto = docto;
	}
	/**
	 * @return the partida
	 */
	public String getPartida() {
		return partida;
	}
	/**
	 * @param partida the partida to set
	 */
	public void setPartida(String partida) {
		this.partida = partida;
	}
	/**
	 * @return the cantidadString
	 */
	public String getCantidadString() {
		return cantidadString;
	}
	/**
	 * @param cantidadString the cantidadString to set
	 */
	public void setCantidadString(String cantidadString) {
		this.cantidadString = cantidadString;
	}
	/**
	 * @return the idpcotiza
	 */
	public String getIdpcotiza() {
		return idpcotiza;
	}
	/**
	 * @param idpcotiza the idpcotiza to set
	 */
	public void setIdpcotiza(String idpcotiza) {
		this.idpcotiza = idpcotiza;
	}

}


