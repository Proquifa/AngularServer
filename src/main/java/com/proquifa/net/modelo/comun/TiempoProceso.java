/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;

/**
 * @author fmartinez
 *
 */
public class TiempoProceso {
	
	//GENERAL
	private String proceso;
	private Date fechaRecepcion;
	private Date fechaInicio;
	private Date fechaFin;
	private String tiempoProceso;
	private String tiempoTotal;
	private Double tCambio;
	private String tipoFlujo;
	private int ordenProceso; //Se usa para que vista pueda Ordenar los procesos como se requiere.
    private boolean cambioActual;
	//EXTRAS
	private Long documentoRecicbido;
	private Date fechaPedido;
	private Date fechaInicioPedido;
	private String totalTiempoProceso;
	private String clasificacion;
	private Long totalProceso;
	private String folio;
	private Date fechaAvisoC;
	private Date proxFGestion;
	private Integer respuesta;
    private boolean ExistenAviso;
    
    

	
	//ConsultaPedido
	private String contacto;
	private String responsable;
	private String referencia;
	private String medio;
	private String proveedor;
	private String cliente;
	private String fpor;
	private String comentarios;
	private String etapa;
	private String conforme;
	private Integer nivel;
	private Integer id;
	private Integer padre;
	private String lote;
	private String pedimento;
	private String documento;
	private String tipo;
	private String caducidad;
	private String edicion;
	private String idioma;
	private String despachable;

	private String etapaPadre;
	private String revision;
	private String refacturacion;
	
	private Boolean recogeEnProquifa;
	private Date fechaDispobible;
	private Date fechaInicioPortal;
	private Date fechaFinPortal;

	//Consulta Compras
	private String etiquetas;
	private String manejo;
	private Date fechaEsperadaArribo;
	private Date fechaRealArribo;
	private String origen;
	private Date FPhsUSA;
	private Date FPhsUSAAnterio;
	private Date FMatriz;
	private Date FMatrizAnterior;
	private String trafico;
	private String gestor_agente;

	//ConsultaCotizaciones
	private PartidaCotizacion pcotiza;
	private String tipoProveedor;
	
	//Consulta Cobros
	private Date fechaEntrega;
	private Date fechaRevision;
	private Date fechaProgramacion;
	private Date fechaCobro;
	private Date fechaCancelacion;
	private String mensajero;
	private String commentRevision;
	private String docsCierre;
	private String doscResult1;
	private String doscResult2;
	private Double monto;
	private String cobro;
	private String estado;

	//Consulta entregas
	private Date fechaFacturacion;
	private Date fechaTramitacion;
	private Date fechaSurtido;
	private Date fechaEjecucion;
	private Date fechaCierre;
	private Boolean entregaRevision;
	private String rutaMensajeria;
	private String zonaMensajeria;
	private String entrega;
	private String estadoRuta;
	
	//consulta Pagos
	private String documentos;
	private Long dias;
	private boolean NC;
	
	//consulta Facturacion
	private Date fechaSolicitud;
	private Date fechaRequerida;
	private Date fechaRecoleccion;
	private String prioridad;
	private String factura;
	private boolean moroso;
	private String moneda;
	
	//consulta Importacion
	private Date fechaSalidaAduana;
	private Date fechaEstimadaArribo;
	private Integer idCompra;
	private String paisProveedor;
	private String fletera;
	private String ordenDespacho;
	private String aduna;
	private String agenteAduanal;
	private String guiaEmbarque;
	private Date fechaPlanificacion;
	private Date fechaPedimento;
	private Integer totalPiezas;
	private Integer piezasFaltantes;
	private Integer piezasSobrantes;
	private String entregarEn;
	private Date fRDespacho;
	
	
	//consulta Modificacion del pedido
	private Date fechaModificacionPedido;
	private String quienAutorizaModificacion;
	private String razonesModificacion;
	private String analisisModificacion;
	private String decisionModificacion;
	private String quienModifico;
	private String doctoOrigen;
	private String queModifico;
	
	
	

	
	/**
	 * @return the proceso
	 */
	public String getProceso() {
		return proceso;
	}
	/**
	 * @param proceso the proceso to set
	 */
	public void setProceso(String proceso) {
		this.proceso = proceso;
	}	
	/**
	 * @return the fechaRecepcion
	 */
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	/**
	 * @param fechaRecepcion the fechaRecepcion to set
	 */
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
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
	 * @return the tiempoProceso
	 */
	public String getTiempoProceso() {
		return tiempoProceso;
	}
	/**
	 * @param tiempoProceso the tiempoProceso to set
	 */
	public void setTiempoProceso(String tiempoProceso) {
		this.tiempoProceso = tiempoProceso;
	}
	/**
	 * @return the tiempoTotal
	 */
	public String getTiempoTotal() {
		return tiempoTotal;
	}
	/**
	 * @param tiempoTotal the tiempoTotal to set
	 */
	public void setTiempoTotal(String tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}
	/**
	 * @return the documentoRecicbido
	 */
	public Long getDocumentoRecicbido() {
		return documentoRecicbido;
	}
	/**
	 * @param documentoRecicbido the documentoRecicbido to set
	 */
	public void setDocumentoRecicbido(Long documentoRecicbido) {
		this.documentoRecicbido = documentoRecicbido;
	}
	/**
	 * @return the fechaPedido
	 */
	public Date getFechaPedido() {
		return fechaPedido;
	}
	/**
	 * @param fechaPedido the fechaPedido to set
	 */
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	/**
	 * @return the fechaInicioPedido
	 */
	public Date getFechaInicioPedido() {
		return fechaInicioPedido;
	}
	/**
	 * @param fechaInicioPedido the fechaInicioPedido to set
	 */
	public void setFechaInicioPedido(Date fechaInicioPedido) {
		this.fechaInicioPedido = fechaInicioPedido;
	}
	/**
	 * @return the totalTiempoProceso
	 */
	public String getTotalTiempoProceso() {
		return totalTiempoProceso;
	}
	/**
	 * @param totalTiempoProceso the totalTiempoProceso to set
	 */
	public void setTotalTiempoProceso(String totalTiempoProceso) {
		this.totalTiempoProceso = totalTiempoProceso;
	}
	/**
	 * @return the clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}
	/**
	 * @param clasificacion the clasificacion to set
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	/**
	 * @return the totalProceso
	 */
	public Long getTotalProceso() {
		return totalProceso;
	}
	/**
	 * @param totalProceso the totalProceso to set
	 */
	public void setTotalProceso(Long totalProceso) {
		this.totalProceso = totalProceso;
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
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
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
	 * @return the etapa
	 */
	public String getEtapa() {
		return etapa;
	}
	/**
	 * @param etapa the etapa to set
	 */
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	/**
	 * @return the conforme
	 */
	public String getConforme() {
		return conforme;
	}
	/**
	 * @param conforme the conforme to set
	 */
	public void setConforme(String conforme) {
		this.conforme = conforme;
	}
	/**
	 * @return the nivel
	 */
	public Integer getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the padre
	 */
	public Integer getPadre() {
		return padre;
	}
	/**
	 * @param padre the padre to set
	 */
	public void setPadre(Integer padre) {
		this.padre = padre;
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
	 * @return the edicion
	 */
	public String getEdicion() {
		return edicion;
	}
	/**
	 * @param edicion the edicion to set
	 */
	public void setEdicion(String edicion) {
		this.edicion = edicion;
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
	/**
	 * @return the despachable
	 */
	public String getDespachable() {
		return despachable;
	}
	/**
	 * @param despachable the despachable to set
	 */
	public void setDespachable(String despachable) {
		this.despachable = despachable;
	}
	/**
	 * @return the etapaPadre
	 */
	public String getEtapaPadre() {
		return etapaPadre;
	}
	/**
	 * @param etapaPadre the etapaPadre to set
	 */
	public void setEtapaPadre(String etapaPadre) {
		this.etapaPadre = etapaPadre;
	}
	/**
	 * @return the revision
	 */
	public String getRevision() {
		return revision;
	}
	/**
	 * @param revision the revision to set
	 */
	public void setRevision(String revision) {
		this.revision = revision;
	}
	/**
	 * @return the refacturacion
	 */
	public String getRefacturacion() {
		return refacturacion;
	}
	/**
	 * @param refacturacion the refacturacion to set
	 */
	public void setRefacturacion(String refacturacion) {
		this.refacturacion = refacturacion;
	}
	/**
	 * @return the recogeEnProquifa
	 */
	public Boolean getRecogeEnProquifa() {
		return recogeEnProquifa;
	}
	/**
	 * @param recogeEnProquifa the recogeEnProquifa to set
	 */
	public void setRecogeEnProquifa(Boolean recogeEnProquifa) {
		this.recogeEnProquifa = recogeEnProquifa;
	}
	/**
	 * @return the fechaDispobible
	 */
	public Date getFechaDispobible() {
		return fechaDispobible;
	}
	/**
	 * @param fechaDispobible the fechaDispobible to set
	 */
	public void setFechaDispobible(Date fechaDispobible) {
		this.fechaDispobible = fechaDispobible;
	}
	/**
	 * @return the etiquetas
	 */
	public String getEtiquetas() {
		return etiquetas;
	}
	/**
	 * @param etiquetas the etiquetas to set
	 */
	public void setEtiquetas(String etiquetas) {
		this.etiquetas = etiquetas;
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
	 * @return the fechaEsperadaArribo
	 */
	public Date getFechaEsperadaArribo() {
		return fechaEsperadaArribo;
	}
	/**
	 * @param fechaEsperadaArribo the fechaEsperadaArribo to set
	 */
	public void setFechaEsperadaArribo(Date fechaEsperadaArribo) {
		this.fechaEsperadaArribo = fechaEsperadaArribo;
	}
	/**
	 * @return the fechaRealArribo
	 */
	public Date getFechaRealArribo() {
		return fechaRealArribo;
	}
	/**
	 * @param fechaRealArribo the fechaRealArribo to set
	 */
	public void setFechaRealArribo(Date fechaRealArribo) {
		this.fechaRealArribo = fechaRealArribo;
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
	 * @return the fPhsUSA
	 */
	public Date getFPhsUSA() {
		return FPhsUSA;
	}
	/**
	 * @param phsUSA the fPhsUSA to set
	 */
	public void setFPhsUSA(Date phsUSA) {
		FPhsUSA = phsUSA;
	}
	/**
	 * @return the fPhsUSAAnterio
	 */
	public Date getFPhsUSAAnterio() {
		return FPhsUSAAnterio;
	}
	/**
	 * @param phsUSAAnterio the fPhsUSAAnterio to set
	 */
	public void setFPhsUSAAnterio(Date phsUSAAnterio) {
		FPhsUSAAnterio = phsUSAAnterio;
	}
	/**
	 * @return the fMatriz
	 */
	public Date getFMatriz() {
		return FMatriz;
	}
	/**
	 * @param matriz the fMatriz to set
	 */
	public void setFMatriz(Date matriz) {
		FMatriz = matriz;
	}
	/**
	 * @return the fMatrizAnterior
	 */
	public Date getFMatrizAnterior() {
		return FMatrizAnterior;
	}
	/**
	 * @param matrizAnterior the fMatrizAnterior to set
	 */
	public void setFMatrizAnterior(Date matrizAnterior) {
		FMatrizAnterior = matrizAnterior;
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
	 * @return the gestor_agente
	 */
	public String getGestor_agente() {
		return gestor_agente;
	}
	/**
	 * @param gestor_agente the gestor_agente to set
	 */
	public void setGestor_agente(String gestor_agente) {
		this.gestor_agente = gestor_agente;
	}
	/**
	 * @return the pcotiza
	 */
	public PartidaCotizacion getPcotiza() {
		return pcotiza;
	}
	/**
	 * @param pcotiza the pcotiza to set
	 */
	public void setPcotiza(PartidaCotizacion pcotiza) {
		this.pcotiza = pcotiza;
	}
	/**
	 * @return the tipoProveedor
	 */
	public String getTipoProveedor() {
		return tipoProveedor;
	}
	/**
	 * @param tipoProveedor the tipoProveedor to set
	 */
	public void setTipoProveedor(String tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}
	/**
	 * @return the fechaEntrega
	 */
	public Date getFechaEntrega() {
		return fechaEntrega;
	}
	/**
	 * @param fechaEntrega the fechaEntrega to set
	 */
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
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
	 * @return the fechaCobro
	 */
	public Date getFechaCobro() {
		return fechaCobro;
	}
	/**
	 * @param fechaCobro the fechaCobro to set
	 */
	public void setFechaCobro(Date fechaCobro) {
		this.fechaCobro = fechaCobro;
	}
	/**
	 * @return the mensajero
	 */
	public String getMensajero() {
		return mensajero;
	}
	/**
	 * @param mensajero the mensajero to set
	 */
	public void setMensajero(String mensajero) {
		this.mensajero = mensajero;
	}
	/**
	 * @return the commentRevision
	 */
	public String getCommentRevision() {
		return commentRevision;
	}
	/**
	 * @param commentRevision the commentRevision to set
	 */
	public void setCommentRevision(String commentRevision) {
		this.commentRevision = commentRevision;
	}
	/**
	 * @return the docsCierre
	 */
	public String getDocsCierre() {
		return docsCierre;
	}
	/**
	 * @param docsCierre the docsCierre to set
	 */
	public void setDocsCierre(String docsCierre) {
		this.docsCierre = docsCierre;
	}
	/**
	 * @return the doscResult1
	 */
	public String getDoscResult1() {
		return doscResult1;
	}
	/**
	 * @param doscResult1 the doscResult1 to set
	 */
	public void setDoscResult1(String doscResult1) {
		this.doscResult1 = doscResult1;
	}
	/**
	 * @return the doscResult2
	 */
	public String getDoscResult2() {
		return doscResult2;
	}
	/**
	 * @param doscResult2 the doscResult2 to set
	 */
	public void setDoscResult2(String doscResult2) {
		this.doscResult2 = doscResult2;
	}

	/**
	 * @return the cobro
	 */
	public String getCobro() {
		return cobro;
	}
	/**
	 * @param cobro the cobro to set
	 */
	public void setCobro(String cobro) {
		this.cobro = cobro;
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
	 * @return the fechaSurtido
	 */
	public Date getFechaSurtido() {
		return fechaSurtido;
	}
	/**
	 * @param fechaSurtido the fechaSurtido to set
	 */
	public void setFechaSurtido(Date fechaSurtido) {
		this.fechaSurtido = fechaSurtido;
	}
	/**
	 * @return the fechaEjecucion
	 */
	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}
	/**
	 * @param fechaEjecucion the fechaEjecucion to set
	 */
	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
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
	 * @return the entregaRevision
	 */
	public Boolean getEntregaRevision() {
		return entregaRevision;
	}
	/**
	 * @param entregaRevision the entregaRevision to set
	 */
	public void setEntregaRevision(Boolean entregaRevision) {
		this.entregaRevision = entregaRevision;
	}
	/**
	 * @return the rutaMensajeria
	 */
	public String getRutaMensajeria() {
		return rutaMensajeria;
	}
	/**
	 * @param rutaMensajeria the rutaMensajeria to set
	 */
	public void setRutaMensajeria(String rutaMensajeria) {
		this.rutaMensajeria = rutaMensajeria;
	}
	/**
	 * @return the zonaMensajeria
	 */
	public String getZonaMensajeria() {
		return zonaMensajeria;
	}
	/**
	 * @param zonaMensajeria the zonaMensajeria to set
	 */
	public void setZonaMensajeria(String zonaMensajeria) {
		this.zonaMensajeria = zonaMensajeria;
	}
	/**
	 * @return the entrega
	 */
	public String getEntrega() {
		return entrega;
	}
	/**
	 * @param entrega the entrega to set
	 */
	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @return the estadoRuta
	 */
	public String getEstadoRuta() {
		return estadoRuta;
	}
	/**
	 * @param estadoRuta the estadoRuta to set
	 */
	public void setEstadoRuta(String estadoRuta) {
		this.estadoRuta = estadoRuta;
	}
	public void setDocumentos(String documentos) {
		this.documentos = documentos;
	}
	public String getDocumentos() {
		return documentos;
	}
	public void setDias(Long dias) {
		this.dias = dias;
	}
	public Long getDias() {
		return dias;
	}
	public boolean isNC() {
		return NC;
	}
	public void setNC(boolean nc) {
		this.NC = nc;
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
	 * @return the fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	/**
	 * @return the fechaRequerida
	 */
	public Date getFechaRequerida() {
		return fechaRequerida;
	}
	/**
	 * @param fechaRequerida the fechaRequerida to set
	 */
	public void setFechaRequerida(Date fechaRequerida) {
		this.fechaRequerida = fechaRequerida;
	}
	/**
	 * @return the fechaRecoleccion
	 */
	public Date getFechaRecoleccion() {
		return fechaRecoleccion;
	}
	/**
	 * @param fechaRecoleccion the fechaRecoleccion to set
	 */
	public void setFechaRecoleccion(Date fechaRecoleccion) {
		this.fechaRecoleccion = fechaRecoleccion;
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
	 * @return the moroso
	 */
	public boolean isMoroso() {
		return moroso;
	}
	/**
	 * @param moroso the moroso to set
	 */
	public void setMoroso(boolean moroso) {
		this.moroso = moroso;
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
	 * @return the fechaInicioPortal
	 */
	public Date getFechaInicioPortal() {
		return fechaInicioPortal;
	}
	/**
	 * @param fechaInicioPortal the fechaInicioPortal to set
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
	 * @param fechaFinPortal the fechaFinPortal to set
	 */
	public void setFechaFinPortal(Date fechaFinPortal) {
		this.fechaFinPortal = fechaFinPortal;
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
	 * @return the fechaCancelacion
	 */
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}
	/**
	 * @param fechaCancelacion the fechaCancelacion to set
	 */
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}
	/**
	 * @return the tipoFlujo
	 */
	public String getTipoFlujo() {
		return tipoFlujo;
	}
	/**
	 * @param tipoFlujo the tipoFlujo to set
	 */
	public void setTipoFlujo(String tipoFlujo) {
		this.tipoFlujo = tipoFlujo;
	}
	/**
	 * @return the ordenProceso
	 */
	public int getOrdenProceso() {
		return ordenProceso;
	}
	/**
	 * @param ordenProceso the ordenProceso to set
	 */
	public void setOrdenProceso(int ordenProceso) {
		this.ordenProceso = ordenProceso;
	}
	
	public Date getFechaSalidaAduana() {
		return fechaSalidaAduana;
	}
	public void setFechaSalidaAduana(Date fechaSalidaAduana) {
		this.fechaSalidaAduana = fechaSalidaAduana;
	}
	public Date getFechaEstimadaArribo() {
		return fechaEstimadaArribo;
	}
	public void setFechaEstimadaArribo(Date fechaEstimadaArribo) {
		this.fechaEstimadaArribo = fechaEstimadaArribo;
	}
	public Integer getIdCompra() {
		return idCompra;
	}
	public void setIdCompra(Integer idCompra) {
		this.idCompra = idCompra;
	}
	public String getPaisProveedor() {
		return paisProveedor;
	}
	public void setPaisProveedor(String paisProveedor) {
		this.paisProveedor = paisProveedor;
	}
	public String getFletera() {
		return fletera;
	}
	public void setFletera(String fletera) {
		this.fletera = fletera;
	}
	public String getOrdenDespacho() {
		return ordenDespacho;
	}
	public void setOrdenDespacho(String ordenDespacho) {
		this.ordenDespacho = ordenDespacho;
	}
	public Date getFechaPlanificacion() {
		return fechaPlanificacion;
	}
	public void setFechaPlanificacion(Date fechaPlanificacion) {
		this.fechaPlanificacion = fechaPlanificacion;
	}
	public String getAduna() {
		return aduna;
	}
	public void setAduna(String aduna) {
		this.aduna = aduna;
	}
	public String getAgenteAduanal() {
		return agenteAduanal;
	}
	public void setAgenteAduanal(String agenteAduanal) {
		this.agenteAduanal = agenteAduanal;
	}
	public String getGuiaEmbarque() {
		return guiaEmbarque;
	}
	public void setGuiaEmbarque(String guiaEmbarque) {
		this.guiaEmbarque = guiaEmbarque;
	}
	public Date getFechaPedimento() {
		return fechaPedimento;
	}
	public void setFechaPedimento(Date fechaPedimento) {
		this.fechaPedimento = fechaPedimento;
	}
	public Integer getTotalPiezas() {
		return totalPiezas;
	}
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	public Integer getPiezasFaltantes() {
		return piezasFaltantes;
	}
	public void setPiezasFaltantes(Integer piezasFaltantes) {
		this.piezasFaltantes = piezasFaltantes;
	}
	public Integer getPiezasSobrantes() {
		return piezasSobrantes;
	}
	public void setPiezasSobrantes(Integer piezasSobrantes) {
		this.piezasSobrantes = piezasSobrantes;
	}
	public Date getFechaAvisoC() {
		return fechaAvisoC;
	}
	public void setFechaAvisoC(Date fechaAvisoC) {
		this.fechaAvisoC = fechaAvisoC;
	}
	public Date getProxFGestion() {
		return proxFGestion;
	}
	public void setProxFGestion(Date proxFGestion) {
		this.proxFGestion = proxFGestion;
	}
	public Integer getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(Integer respuesta) {
		this.respuesta = respuesta;
	}
	public boolean isExistenAviso() {
		return ExistenAviso;
	}
	public void setExistenAviso(boolean existenAviso) {
		ExistenAviso = existenAviso;
	}
	public boolean isCambioActual() {
		return cambioActual;
	}
	public void setCambioActual(boolean cambioActual) {
		this.cambioActual = cambioActual;
	}
	public String getEntregarEn() {
		return entregarEn;
	}
	public void setEntregarEn(String entregarEn) {
		this.entregarEn = entregarEn;
	}
	public Date getfRDespacho() {
		return fRDespacho;
	}
	public void setfRDespacho(Date fRDespacho) {
		this.fRDespacho = fRDespacho;
	}
	public Date getFechaModificacionPedido() {
		return fechaModificacionPedido;
	}
	public void setFechaModificacionPedido(Date fechaModificacionPedido) {
		this.fechaModificacionPedido = fechaModificacionPedido;
	}
	public String getQuienAutorizaModificacion() {
		return quienAutorizaModificacion;
	}
	public void setQuienAutorizaModificacion(String quienAutorizaModificacion) {
		this.quienAutorizaModificacion = quienAutorizaModificacion;
	}
	public String getRazonesModificacion() {
		return razonesModificacion;
	}
	public void setRazonesModificacion(String razonesModificacion) {
		this.razonesModificacion = razonesModificacion;
	}
	public String getAnalisisModificacion() {
		return analisisModificacion;
	}
	public void setAnalisisModificacion(String analisisModificacion) {
		this.analisisModificacion = analisisModificacion;
	}
	public String getDecisionModificacion() {
		return decisionModificacion;
	}
	public void setDecisionModificacion(String decisionModificacion) {
		this.decisionModificacion = decisionModificacion;
	}
	public String getQuienModifico() {
		return quienModifico;
	}
	public void setQuienModifico(String quienModifico) {
		this.quienModifico = quienModifico;
	}
	public String getDoctoOrigen() {
		return doctoOrigen;
	}
	public void setDoctoOrigen(String doctoOrigen) {
		this.doctoOrigen = doctoOrigen;
	}
	public String getQueModifico() {
		return queModifico;
	}
	public void setQueModifico(String queModifico) {
		this.queModifico = queModifico;
	}

	


}