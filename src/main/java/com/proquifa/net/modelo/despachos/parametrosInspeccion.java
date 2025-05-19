package com.proquifa.net.modelo.despachos;

import java.sql.Date;
import java.util.List;

/**
 * @author nbaron
 *
 */


public class parametrosInspeccion {
	
	private List<PartidaInspeccion> listaPzas;
	private Long idCompra;
	private Long idPPedido;
	private Integer numAnaquel;
	private String letraAnaquel;
	private String ubicacion;
	private Integer numRechazos;
	private String usuario;
	private String fechaInspeccion;
	private Date fechaInicio;
	private Date fechaFin;
	private boolean finalizarPendiente;
	private String videoPartida;
	private String documento;
	private String documentoSDS;
	private String ubicacionAsignada;
	private String codigoQrBolsa;
	private String qrBolsaIncidencia;
	private String prioridad;
	
	private boolean aplicaDocumentacion;
	private String cantidadProducto;
	private String unidadProducto;
	private Integer idProducto;
	private String manejo;
	private String manejoTransporte;
	private String lote;
	private String tipoPresentacion;
	private  List<String> nombreImagenesRechazo;
	private String idioma;
	private String edicion;
	private Integer CantDespachables;
	private String documentos;
	private String documentosSDS;
	
	private Integer piezas;
	private Integer tiempo;
	private String imagenRechazo;
	
	private Integer idProveedor;
	private List<String> comentarios;
	private boolean cargaDoc;
	private Long idUsuario;
	private String unidadCambio;
	private String cantidadCambio;
	private String codigoProd;
	private String presentacionProd;
	private String marca;
	private String tipo;
	private Integer idInspeccionOC;

	
	public List<PartidaInspeccion> getListaPzas() {
		return listaPzas;
	}
	public void setListaPzas(List<PartidaInspeccion> listaPzas) {
		this.listaPzas = listaPzas;
	}
	public Long getIdPPedido() {
		return idPPedido;
	}
	public void setIdPPedido(Long idPPedido) {
		this.idPPedido = idPPedido;
	}
	public Long getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Long idCompra) {
		this.idCompra = idCompra;
	}
	public Integer getNumAnaquel() {
		return numAnaquel;
	}
	public void setNumAnaquel(Integer numAnaquel) {
		this.numAnaquel = numAnaquel;
	}
	public String getLetraAnaquel() {
		return letraAnaquel;
	}
	public void setLetraAnaquel(String letraAnaquel) {
		this.letraAnaquel = letraAnaquel;
	}
	public Integer getNumRechazos() {
		return numRechazos;
	}
	public void setNumRechazos(Integer numRechazos) {
		this.numRechazos = numRechazos;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getFechaInspeccion() {
		return fechaInspeccion;
	}
	public void setFechaInspeccion(String fechaInspeccion) {
		this.fechaInspeccion = fechaInspeccion;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public boolean isFinalizarPendiente() {
		return finalizarPendiente;
	}
	public void setFinalizarPendiente(boolean finalizarPendiente) {
		this.finalizarPendiente = finalizarPendiente;
	}
	public String getVideoPartida() {
		return videoPartida;
	}
	public void setVideoPartida(String videoPartida) {
		this.videoPartida = videoPartida;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getDocumentoSDS() {
		return documentoSDS;
	}
	public void setDocumentoSDS(String documentoSDS) {
		this.documentoSDS = documentoSDS;
	}
	public String getUbicacionAsignada() {
		return ubicacionAsignada;
	}
	public void setUbicacionAsignada(String ubicacionAsignada) {
		this.ubicacionAsignada = ubicacionAsignada;
	}
	public String getCodigoQrBolsa() {
		return codigoQrBolsa;
	}
	public void setCodigoQrBolsa(String codigoQrBolsa) {
		this.codigoQrBolsa = codigoQrBolsa;
	}
	public String getQrBolsaIncidencia() {
		return qrBolsaIncidencia;
	}
	public void setQrBolsaIncidencia(String qrBolsaIncidencia) {
		this.qrBolsaIncidencia = qrBolsaIncidencia;
	}
	/**
	 * @return the prioridad
	 */
	public String getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	/**
	 * @return the aplicaDocumentacion
	 */
	public boolean isAplicaDocumentacion() {
		return aplicaDocumentacion;
	}
	/**
	 * @param aplicaDocumentacion the aplicaDocumentacion to set
	 */
	public void setAplicaDocumentacion(boolean aplicaDocumentacion) {
		this.aplicaDocumentacion = aplicaDocumentacion;
	}
	/**
	 * @return the cantidadProducto
	 */
	public String getCantidadProducto() {
		return cantidadProducto;
	}
	/**
	 * @param cantidadProducto the cantidadProducto to set
	 */
	public void setCantidadProducto(String cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}
	/**
	 * @return the unidadProducto
	 */
	public String getUnidadProducto() {
		return unidadProducto;
	}
	/**
	 * @param unidadProducto the unidadProducto to set
	 */
	public void setUnidadProducto(String unidadProducto) {
		this.unidadProducto = unidadProducto;
	}
	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public String getManejo() {
		return manejo;
	}
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}
	public String getLote() {
		return lote;
	}
	public void setLote(String lote) {
		this.lote = lote;
	}
	public String getTipoPresentacion() {
		return tipoPresentacion;
	}
	public void setTipoPresentacion(String tipoPresentacion) {
		this.tipoPresentacion = tipoPresentacion;
	}
	/**
	 * @return the manejoTransporte
	 */
	public String getManejoTransporte() {
		return manejoTransporte;
	}
	/**
	 * @param manejoTransporte the manejoTransporte to set
	 */
	public void setManejoTransporte(String manejoTransporte) {
		this.manejoTransporte = manejoTransporte;
	}
	public List<String> getNombreImagenesRechazo() {
		return nombreImagenesRechazo;
	}
	public void setNombreImagenesRechazo(List<String> nombreImagenesRechazo) {
		this.nombreImagenesRechazo = nombreImagenesRechazo;
	}
	public String getIdioma() {
		return idioma;
	}
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}
	public String getEdicion() {
		return edicion;
	}
	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}
	public Integer getCantDespachables() {
		return CantDespachables;
	}
	public void setCantDespachables(Integer CantDespachables) {
		this.CantDespachables = CantDespachables;
	}
	/**
	 * @return the piezas
	 */
	public Integer getPiezas() {
		return piezas;
	}
	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}
	/**
	 * @return the tiempo
	 */
	public Integer getTiempo() {
		return tiempo;
	}
	/**
	 * @param tiempo the tiempo to set
	 */
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
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
	public String getDocumentosSDS() {
		return documentosSDS;
	}
	public void setDocumentosSDS(String documentosSDS) {
		this.documentosSDS = documentosSDS;
	}
	public String getDocumentos() {
		return documentos;
	}
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}
	public boolean isCargaDoc() {
		return cargaDoc;
	}
	public void setCargaDoc(boolean cargaDoc) {
		this.cargaDoc = cargaDoc;
	}
	public List<String> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<String> comentarios) {
		this.comentarios = comentarios;
	}
	public Long getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getUnidadCambio() {
		return unidadCambio;
	}
	public void setUnidadCambio(String unidadCambio) {
		this.unidadCambio = unidadCambio;
	}
	public String getCantidadCambio() {
		return cantidadCambio;
	}
	public void setCantidadCambio(String cantidadCambio) {
		this.cantidadCambio = cantidadCambio;
	}
	public String getPresentacionProd() {
		return presentacionProd;
	}
	public void setPresentacionProd(String presentacionProd) {
		this.presentacionProd = presentacionProd;
	}
	public String getCodigoProd() {
		return codigoProd;
	}
	public void setCodigoProd(String codigoProd) {
		this.codigoProd = codigoProd;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Integer getIdInspeccionOC() {
		return idInspeccionOC;
	}
	public void setIdInspeccionOC(Integer idInspeccionOC) {
		this.idInspeccionOC = idInspeccionOC;
	}
	
}
