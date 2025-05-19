/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.util.Date;

import com.proquifa.net.modelo.compras.PartidaCompra;



/**
 * @author fmartinez
 *
 */
public class PartidaInspeccion extends PartidaCompra {

	private Integer idProveedor;
	private Long idPPedido;
	private Long idProducto;
	private String paisProveedor;
	private String origen;
	private String manejo;
	private String control;
	private Boolean rechazoXDoc;
	private Boolean rechazoXInsp;
	private Date fechaInicioInspeccion;
	private Date fechaFinInspeccion;
	private String fechaEstadisticas;
	private String tipoFlete;
	private String disponibilidadPedimento;
	private String guia;
	private String mesCaduca;	
	private String anoCaduca;	
	private String caducidad;
	private String Parciales;
	private String folioPackingList;
	private String estadoRechazo;
	private Integer numPartidasProgramadas;
	private Integer numPartidasRegulares;
	private Integer numPartidasFEx;
	private String documento;
	private String tipoDocumento;
	private String presentacion;
	private String edoPrevioPP;
	private String edoPrevioPC;
	private String inspector;
	private Boolean editoPartida;
	private Boolean revisoNumPiezas;
	private Boolean revisoCaducidad;
	private Boolean revisoLote;
	private Boolean revisoDoc;
	private Boolean insertoPiezas;
	private String pedidoFacturadoPor;
	
	private String folioOrdenDespacho;
	private String folioListaArribo;
	private Integer diasRestantes;
	private String ruta;
	private String zonaMensajeria;
	private String prioridad;
	private String puntos;
	private String criterio;
	private Integer aceptaParciales;
	private String frecuencia;
	private String prioridadFEE;
	private String prioridadRDestino;
	private String prioridadRParciales;
	private String prioridadManejo;
	private String prioridadUDArribo;
	private String prioridadRuta;
	private Integer puntosFEE;
	private Integer puntosRDestino;
	private Integer puntosRParciales;
	private Integer puntosManejo;
	private Integer puntosUDArribo;
	private Integer puntosRuta;
	private String folioEtiqueta;
	private Integer idCliente;
	private Boolean esImportado;
	private String prioridadControl;
	private Integer puntosControl;
	
	private Boolean catCorrecto;
	private Boolean descripcionCorrecta;
	private Boolean presentacionCorrecta;
	private Boolean loteCorrecto;
	private Boolean caducidadCorrecta;
	private Boolean fisicamenteCorrecto;
	private Boolean documentacionCorrecta;
	private Boolean despachable;
	private Boolean idiomaCorecto;
	private Boolean edicionCorrecta;
	private String reporte;
	private Boolean esPublicacion;

	private String ubicacion;
	private Integer referenciaOC;
	private Integer referenciaFact;
	private String aduana;
	private Date fechaPedimento;
	private Long tiempoInspeccionEnSegundos;
	private Integer prioridad_p; 
	private String prioridadCalculada;
	private Integer cantPartidas;
	private String prioridad_Em;
	private Date fechaFinEmbalaje;
	private String folioPaquete;
	private Date fea;
	private String diasFiltro;
	private String loteOriginal;

	private String fFrente;
	private String fArriba;
	private String fAbajo ;
	private String videoPartida;
	private String documentoSDS;

	private Integer idPartidaInspeccion;
	
	private boolean aplicaDocumentacion;
	private String  imagenRechazo; // Se agrego para guardar la imagen rechazo
//	variables para embalar
	
	
	
	public PartidaInspeccion() {
		super();
		// TODO Auto-generated constructor stub
	}
	private Long tiempoEmbalajeEnSegundos;
	private Long idOrdenEntrega;
	private String tipoPartida;	
	
	
	public String getLoteOriginal() {
		return loteOriginal;
	}
	public void setLoteOriginal(String loteOriginal) {
		this.loteOriginal = loteOriginal;
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
	 * @return the idPPedido
	 */
	public Long getIdPPedido() {
		return idPPedido;
	}
	/**
	 * @param idPPedido the idPPedido to set
	 */
	public void setIdPPedido(Long idPPedido) {
		this.idPPedido = idPPedido;
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
	 * @return the paisProveedor
	 */
	public String getPaisProveedor() {
		return paisProveedor;
	}
	/**
	 * @param paisProveedor the paisProveedor to set
	 */
	public void setPaisProveedor(String paisProveedor) {
		this.paisProveedor = paisProveedor;
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
	 * @return the control
	 */
	public String getControl() {
		return control;
	}
	/**
	 * @param control the control to set
	 */
	public void setControl(String control) {
		this.control = control;
	}
	/**
	 * @return the rechazoXDoc
	 */
	public Boolean getRechazoXDoc() {
		return rechazoXDoc;
	}
	/**
	 * @param rechazoXDoc the rechazoXDoc to set
	 */
	public void setRechazoXDoc(Boolean rechazoXDoc) {
		this.rechazoXDoc = rechazoXDoc;
	}
	/**
	 * @return the rechazoXInsp
	 */
	public Boolean getRechazoXInsp() {
		return rechazoXInsp;
	}
	/**
	 * @param rechazoXInsp the rechazoXInsp to set
	 */
	public void setRechazoXInsp(Boolean rechazoXInsp) {
		this.rechazoXInsp = rechazoXInsp;
	}
	/**
	 * @return the fechaInicioInspeccion
	 */
	public Date getFechaInicioInspeccion() {
		return fechaInicioInspeccion;
	}
	/**
	 * @param fechaInicioInspeccion the fechaInicioInspeccion to set
	 */
	public void setFechaInicioInspeccion(Date fechaInicioInspeccion) {
		this.fechaInicioInspeccion = fechaInicioInspeccion;
	}
	/**
	 * @return the fechaFinInspeccion
	 */
	public Date getFechaFinInspeccion() {
		return fechaFinInspeccion;
	}
	/**
	 * @param fechaFinInspeccion the fechaFinInspeccion to set
	 */
	public void setFechaFinInspeccion(Date fechaFinInspeccion) {
		this.fechaFinInspeccion = fechaFinInspeccion;
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
	 * @return the disponibilidadPedimento
	 */
	public String getDisponibilidadPedimento() {
		return disponibilidadPedimento;
	}
	/**
	 * @param disponibilidadPedimento the disponibilidadPedimento to set
	 */
	public void setDisponibilidadPedimento(String disponibilidadPedimento) {
		this.disponibilidadPedimento = disponibilidadPedimento;
	}
	/**
	 * @return the guia
	 */
	public String getGuia() {
		return guia;
	}
	/**
	 * @param guia the guia to set
	 */
	public void setGuia(String guia) {
		this.guia = guia;
	}
	/**
	 * @return the mesCaduca
	 */
	public String getMesCaduca() {
		return mesCaduca;
	}
	/**
	 * @param mesCaduca the mesCaduca to set
	 */
	public void setMesCaduca(String mesCaduca) {
		this.mesCaduca = mesCaduca;
	}
	/**
	 * @return the anoCaduca
	 */
	public String getAnoCaduca() {
		return anoCaduca;
	}
	/**
	 * @param anoCaduca the anoCaduca to set
	 */
	public void setAnoCaduca(String anoCaduca) {
		this.anoCaduca = anoCaduca;
	}
	/**
	 * @return the caducidad
	 */
	public String getCaducidad() {
		return caducidad;
	}
	/**
	 * @param caducidad the caducidad to set
	 */
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	/**
	 * @return the parciales
	 */
	public String getParciales() {
		return Parciales;
	}
	/**
	 * @param parciales the parciales to set
	 */
	public void setParciales(String parciales) {
		Parciales = parciales;
	}
	/**
	 * @return the folioPackingList
	 */
	public String getFolioPackingList() {
		return folioPackingList;
	}
	/**
	 * @param folioPackingList the folioPackingList to set
	 */
	public void setFolioPackingList(String folioPackingList) {
		this.folioPackingList = folioPackingList;
	}
	/**
	 * @return the estadoRechazo
	 */
	public String getEstadoRechazo() {
		return estadoRechazo;
	}
	/**
	 * @param estadoRechazo the estadoRechazo to set
	 */
	public void setEstadoRechazo(String estadoRechazo) {
		this.estadoRechazo = estadoRechazo;
	}
	/**
	 * @return the numPartidasProgramadas
	 */
	public Integer getNumPartidasProgramadas() {
		return numPartidasProgramadas;
	}
	/**
	 * @param numPartidasProgramadas the numPartidasProgramadas to set
	 */
	public void setNumPartidasProgramadas(Integer numPartidasProgramadas) {
		this.numPartidasProgramadas = numPartidasProgramadas;
	}
	/**
	 * @return the numPartidasRegulares
	 */
	public Integer getNumPartidasRegulares() {
		return numPartidasRegulares;
	}
	/**
	 * @param numPartidasRegulares the numPartidasRegulares to set
	 */
	public void setNumPartidasRegulares(Integer numPartidasRegulares) {
		this.numPartidasRegulares = numPartidasRegulares;
	}
	/**
	 * @return the numPartidasFEx
	 */
	public Integer getNumPartidasFEx() {
		return numPartidasFEx;
	}
	/**
	 * @param numPartidasFEx the numPartidasFEx to set
	 */
	public void setNumPartidasFEx(Integer numPartidasFEx) {
		this.numPartidasFEx = numPartidasFEx;
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
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	/**
	 * @return the presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}
	/**
	 * @param presentacion the presentacion to set
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	/**
	 * @return the edoPrevioPP
	 */
	public String getEdoPrevioPP() {
		return edoPrevioPP;
	}
	/**
	 * @param edoPrevioPP the edoPrevioPP to set
	 */
	public void setEdoPrevioPP(String edoPrevioPP) {
		this.edoPrevioPP = edoPrevioPP;
	}
	/**
	 * @return the edoPrevioPC
	 */
	public String getEdoPrevioPC() {
		return edoPrevioPC;
	}
	/**
	 * @param edoPrevioPC the edoPrevioPC to set
	 */
	public void setEdoPrevioPC(String edoPrevioPC) {
		this.edoPrevioPC = edoPrevioPC;
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
	 * @return the editoPartida
	 */
	public Boolean getEditoPartida() {
		return editoPartida;
	}
	/**
	 * @param editoPartida the editoPartida to set
	 */
	public void setEditoPartida(Boolean editoPartida) {
		this.editoPartida = editoPartida;
	}
	/**
	 * @return the revisoNumPiezas
	 */
	public Boolean getRevisoNumPiezas() {
		return revisoNumPiezas;
	}
	/**
	 * @param revisoNumPiezas the revisoNumPiezas to set
	 */
	public void setRevisoNumPiezas(Boolean revisoNumPiezas) {
		this.revisoNumPiezas = revisoNumPiezas;
	}
	/**
	 * @return the revisoCaducidad
	 */
	public Boolean getRevisoCaducidad() {
		return revisoCaducidad;
	}
	/**
	 * @param revisoCaducidad the revisoCaducidad to set
	 */
	public void setRevisoCaducidad(Boolean revisoCaducidad) {
		this.revisoCaducidad = revisoCaducidad;
	}
	/**
	 * @return the revisoLote
	 */
	public Boolean getRevisoLote() {
		return revisoLote;
	}
	/**
	 * @param revisoLote the revisoLote to set
	 */
	public void setRevisoLote(Boolean revisoLote) {
		this.revisoLote = revisoLote;
	}
	/**
	 * @return the revisoDoc
	 */
	public Boolean getRevisoDoc() {
		return revisoDoc;
	}
	/**
	 * @param revisoDoc the revisoDoc to set
	 */
	public void setRevisoDoc(Boolean revisoDoc) {
		this.revisoDoc = revisoDoc;
	}
	/**
	 * @return the insertoPiezas
	 */
	public Boolean getInsertoPiezas() {
		return insertoPiezas;
	}
	/**
	 * @param insertoPiezas the insertoPiezas to set
	 */
	public void setInsertoPiezas(Boolean insertoPiezas) {
		this.insertoPiezas = insertoPiezas;
	}
	/**
	 * @return the pedidoFacturadoPor
	 */
	public String getPedidoFacturadoPor() {
		return pedidoFacturadoPor;
	}
	/**
	 * @param pedidoFacturadoPor the pedidoFacturadoPor to set
	 */
	public void setPedidoFacturadoPor(String pedidoFacturadoPor) {
		this.pedidoFacturadoPor = pedidoFacturadoPor;
	}
	/**
	 * @param folioOrdenDespacho the folioOrdenDespacho to set
	 */
	public String getFolioOrdenDespacho() {
		return folioOrdenDespacho;
	}
	public void setFolioOrdenDespacho(String folioOrdenDespacho) {
		this.folioOrdenDespacho = folioOrdenDespacho;
	}
	public String getFolioListaArribo() {
		return folioListaArribo;
	}
	public void setFolioListaArribo(String folioListaArribo) {
		this.folioListaArribo = folioListaArribo;
	}
	public Integer getDiasRestantes() {
		return diasRestantes;
	}
	public void setDiasRestantes(Integer diasRestantes) {
		this.diasRestantes = diasRestantes;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getZonaMensajeria() {
		return zonaMensajeria;
	}
	public void setZonaMensajeria(String zonaMensajeria) {
		this.zonaMensajeria = zonaMensajeria;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public String getPuntos() {
		return puntos;
	}
	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}
	public String getCriterio() {
		return criterio;
	}
	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}
	public String getPrioridadFEE() {
		return prioridadFEE;
	}
	public void setPrioridadFEE(String prioridadFEE) {
		this.prioridadFEE = prioridadFEE;
	}
	public String getPrioridadRDestino() {
		return prioridadRDestino;
	}
	public void setPrioridadRDestino(String prioridadRDestino) {
		this.prioridadRDestino = prioridadRDestino;
	}
	public String getPrioridadRParciales() {
		return prioridadRParciales;
	}
	public void setPrioridadRParciales(String prioridadRParciales) {
		this.prioridadRParciales = prioridadRParciales;
	}
	public String getPrioridadManejo() {
		return prioridadManejo;
	}
	public void setPrioridadManejo(String prioridadManejo) {
		this.prioridadManejo = prioridadManejo;
	}
	
	public String getPrioridadUDArribo() {
		return prioridadUDArribo;
	}
	public void setPrioridadUDArribo(String prioridadUDArribo) {
		this.prioridadUDArribo = prioridadUDArribo;
	}
	public Integer getPuntosFEE() {
		return puntosFEE;
	}
	public void setPuntosFEE(Integer puntosFEE) {
		this.puntosFEE = puntosFEE;
	}
	public Integer getPuntosRDestino() {
		return puntosRDestino;
	}
	public void setPuntosRDestino(Integer puntosRDestino) {
		this.puntosRDestino = puntosRDestino;
	}
	public Integer getPuntosRParciales() {
		return puntosRParciales;
	}
	public void setPuntosRParciales(Integer puntosRParciales) {
		this.puntosRParciales = puntosRParciales;
	}
	public Integer getPuntosManejo() {
		return puntosManejo;
	}
	public void setPuntosManejo(Integer puntosManejo) {
		this.puntosManejo = puntosManejo;
	}
	public Integer getPuntosUDArribo() {
		return puntosUDArribo;
	}
	public void setPuntosUDArribo(Integer puntosUDArribo) {
		this.puntosUDArribo = puntosUDArribo;
	}
	public String getFrecuencia() {
		return frecuencia;
	}
	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}
	public Integer getAceptaParciales() {
		return aceptaParciales;
	}
	public void setAceptaParciales(Integer aceptaParciales) {
		this.aceptaParciales = aceptaParciales;
	}
	/**
	 * @return the folioEtiqueta
	 */
	public String getFolioEtiqueta() {
		return folioEtiqueta;
	}
	/**
	 * @param folioEtiqueta the folioEtiqueta to set
	 */
	public void setFolioEtiqueta(String folioEtiqueta) {
		this.folioEtiqueta = folioEtiqueta;
	}
	/**
	 * @return the idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the esImportado
	 */
	public Boolean getEsImportado() {
		return esImportado;
	}
	/**
	 * @param esImportado the esImportado to set
	 */
	public void setEsImportado(Boolean esImportado) {
		this.esImportado = esImportado;
	}
	/**
	 * @return the prioridadControl
	 */
	public String getPrioridadControl() {
		return prioridadControl;
	}
	/**
	 * @param prioridadControl the prioridadControl to set
	 */
	public void setPrioridadControl(String prioridadControl) {
		this.prioridadControl = prioridadControl;
	}
	/**
	 * @return the puntosControl
	 */
	public Integer getPuntosControl() {
		return puntosControl;
	}
	/**
	 * @param puntosControl the puntosControl to set
	 */
	public void setPuntosControl(Integer puntosControl) {
		this.puntosControl = puntosControl;
	}
	public Boolean getCatCorrecto() {
		return catCorrecto;
	}
	public void setCatCorrecto(Boolean catCorrecto) {
		this.catCorrecto = catCorrecto;
	}
	public Boolean getPresentacionCorrecta() {
		return presentacionCorrecta;
	}
	public void setPresentacionCorrecta(Boolean presentacionCorrecta) {
		this.presentacionCorrecta = presentacionCorrecta;
	}
	public Boolean getDescripcionCorrecta() {
		return descripcionCorrecta;
	}
	public void setDescripcionCorrecta(Boolean descripcionCorrecta) {
		this.descripcionCorrecta = descripcionCorrecta;
	}
	public Boolean getLoteCorrecto() {
		return loteCorrecto;
	}
	public void setLoteCorrecto(Boolean loteCorrecto) {
		this.loteCorrecto = loteCorrecto;
	}
	public Boolean getCaducidadCorrecta() {
		return caducidadCorrecta;
	}
	public void setCaducidadCorrecta(Boolean caducidadCorrecta) {
		this.caducidadCorrecta = caducidadCorrecta;
	}
	public Boolean getFisicamenteCorrecto() {
		return fisicamenteCorrecto;
	}
	public void setFisicamenteCorrecto(Boolean fisicamenteCorrecto) {
		this.fisicamenteCorrecto = fisicamenteCorrecto;
	}
	public Boolean getDocumentacionCorrecta() {
		return documentacionCorrecta;
	}
	public void setDocumentacionCorrecta(Boolean documentacionCorrecta) {
		this.documentacionCorrecta = documentacionCorrecta;
	}
	public Boolean getDespachable() {
		return despachable;
	}
	public void setDespachable(Boolean despachable) {
		this.despachable = despachable;
	}
	public Boolean getIdiomaCorecto() {
		return idiomaCorecto;
	}
	public void setIdiomaCorecto(Boolean idiomaCorecto) {
		this.idiomaCorecto = idiomaCorecto;
	}
	public Boolean getEdicionCorrecta() {
		return edicionCorrecta;
	}
	public void setEdicionCorrecta(Boolean edicionCorrecta) {
		this.edicionCorrecta = edicionCorrecta;
	}
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public Boolean getEsPublicacion() {
		return esPublicacion;
	}
	public void setEsPublicacion(Boolean esPublicacion) {
		this.esPublicacion = esPublicacion;
	}
	public String getUbicacion() {
		return ubicacion;
	}
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	public Integer getReferenciaOC() {
		return referenciaOC;
	}
	public void setReferenciaOC(Integer referenciaOC) {
		this.referenciaOC = referenciaOC;
	}
	public Integer getReferenciaFact() {
		return referenciaFact;
	}
	public void setReferenciaFact(Integer referenciaFact) {
		this.referenciaFact = referenciaFact;
	}
	public String getAduana() {
		return aduana;
	}
	public void setAduana(String aduana) {
		this.aduana = aduana;
	}
	public Date getFechaPedimento() {
		return fechaPedimento;
	}
	public void setFechaPedimento(Date fechaPedimento) {
		this.fechaPedimento = fechaPedimento;
	}
	public Long getTiempoInspeccionEnSegundos() {
		return tiempoInspeccionEnSegundos;
	}
	public void setTiempoInspeccionEnSegundos(Long tiempoInspeccionEnSegundos) {
		this.tiempoInspeccionEnSegundos = tiempoInspeccionEnSegundos;
	}
	public Integer getPrioridad_p() {
		return prioridad_p;
	}
	public void setPrioridad_p(Integer prioridad_p) {
		this.prioridad_p = prioridad_p;
	}
	public String getPrioridadCalculada() {
		return prioridadCalculada;
	}
	public void setPrioridadCalculada(String prioridadCalculada) {
		this.prioridadCalculada = prioridadCalculada;
	}
	public Integer getCantPartidas() {
		return cantPartidas;
	}
	public void setCantPartidas(Integer cantPartidas) {
		this.cantPartidas = cantPartidas;
	}
	public Long getTiempoEmbalajeEnSegundos() {
		return tiempoEmbalajeEnSegundos;
	}
	public void setTiempoEmbalajeEnSegundos(Long tiempoEmbalajeEnSegundos) {
		this.tiempoEmbalajeEnSegundos = tiempoEmbalajeEnSegundos;
	}
	public Long getIdOrdenEntrega() {
		return idOrdenEntrega;
	}
	public void setIdOrdenEntrega(Long idOrdenEntrega) {
		this.idOrdenEntrega = idOrdenEntrega;
	}
	public Date getFechaFinEmbalaje() {
		return fechaFinEmbalaje;
	}
	public void setFechaFinEmbalaje(Date fechaFinEmbalaje) {
		this.fechaFinEmbalaje = fechaFinEmbalaje;
	}
	public String getPrioridad_Em() {
		return prioridad_Em;
	}
	public void setPrioridad_Em(String prioridad_Em) {
		this.prioridad_Em = prioridad_Em;
	}
	public String getFolioPaquete() {
		return folioPaquete;
	}
	public void setFolioPaquete(String folioPaquete) {
		this.folioPaquete = folioPaquete;
	}
	public Date getFea() {
		return fea;
	}
	public void setFea(Date fea) {
		this.fea = fea;
	}
	public String getDiasFiltro() {
		return diasFiltro;
	}
	public void setDiasFiltro(String diasFiltro) {
		this.diasFiltro = diasFiltro;
	}
	public String getfFrente() {
		return fFrente;
	}
	public void setfFrente(String fFrente) {
		this.fFrente = fFrente;
	}
	public String getfArriba() {
		return fArriba;
	}
	public void setfArriba(String fArriba) {
		this.fArriba = fArriba;
	}
	public String getfAbajo() {
		return fAbajo;
	}
	public void setfAbajo(String fAbajo) {
		this.fAbajo = fAbajo;
	}
	public String getVideoPartida() {
		return videoPartida;
	}
	public void setVideoPartida(String videoPartida) {
		this.videoPartida = videoPartida;
	}
	
	public String getDocumentoSDS() {
		return documentoSDS;
	}
	public void setDocumentoSDS(String documentoSDS) {
		this.documentoSDS = documentoSDS;
	}
	/**
	 * @return the fechaEstadisticas
	 */
	public String getFechaEstadisticas() {
		return fechaEstadisticas;
	}
	/**
	 * @param fechaEstadisticas the fechaEstadisticas to set
	 */
	public void setFechaEstadisticas(String fechaEstadisticas) {
		this.fechaEstadisticas = fechaEstadisticas;
	}
	/**
	 * @return the idPartidaInspeccion
	 */
	public Integer getIdPartidaInspeccion() {
		return idPartidaInspeccion;
	}
	/**
	 * @param idPartidaInspeccion the idPartidaInspeccion to set
	 */
	public void setIdPartidaInspeccion(Integer idPartidaInspeccion) {
		this.idPartidaInspeccion = idPartidaInspeccion;
	}
	/**
	 * @return the puntosRuta
	 */
	public Integer getPuntosRuta() {
		return puntosRuta;
	}
	/**
	 * @param puntosRuta the puntosRuta to set
	 */
	public void setPuntosRuta(Integer puntosRuta) {
		this.puntosRuta = puntosRuta;
	}
	/**
	 * @return the prioridadRuta
	 */
	public String getPrioridadRuta() {
		return prioridadRuta;
	}
	/**
	 * @param prioridadRuta the prioridadRuta to set
	 */
	public void setPrioridadRuta(String prioridadRuta) {
		this.prioridadRuta = prioridadRuta;
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
	public String getImagenRechazo() {
		return imagenRechazo;
	}
	public void setImagenRechazo(String imagenRechazo) {
		this.imagenRechazo = imagenRechazo;
	}
	public String getTipoPartida() {
		return tipoPartida;
	}
	public void setTipoPartida(String tipoPartida) {
		this.tipoPartida = tipoPartida;
	}
	

	
}