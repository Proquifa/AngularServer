/**
 * 
 */
package com.proquifa.net.modelo.ventas.visitas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Referencia;

/**
 * @author jmcamanos
 *
 */
public class SolicitudVisita {
	
	private Long idSolicitudVisita;
	
	private Cliente cliente;
	private Contacto contacto;
	private Date fechaDeseadaRealizacion;
	private String urgencia;
	private String nombreCliente;
	private String nombreSolicitante;
	private String justificacion;
	private Empleado empleado;
	private String folio;
	private String tiempoRealizacion;
	private String diasAtraso;
	private boolean existeReferencia;
	
	private String argumento;
	private String folioVisita;
	private String folioVisitaOrigen;
	private Date fechaUltimaActualizacionVisita;
	private Date fechaEstimadaRealizacionVisita;
	
	private List<Referencia> referencias;
	private List<DocumentoSolicitudVisita> documentos;
	
	private String asunto;
	private long idSolicitante;
	private String motivo;
	private Date fechaSolicitud;
	private long idContacto;
	private long idCliente;
	private long idEmpleado;
	private int numDocumentos;
	private String tipoSolicitud;
	private String solicito;
	private String tipoVisita;
	
	private Integer calificacion;
	private Integer reqRealizados;
	private Integer reqTotales;
	private Integer noPendientes;
	private Integer noHallazgos;
	private Integer noRequisiciones;
	private String respuesta;
	private String usuarioSolicitante;
	
	private DocumentoSolicitudVisita documento;
	
	/**
	 * @return the idSolicitudVisita
	 */
	public Long getIdSolicitudVisita() {
		return idSolicitudVisita;
	}
	/**
	 * @param idSolicitudVisita the idSolicitudVisita to set
	 */
	public void setIdSolicitudVisita(Long idSolicitudVisita) {
		this.idSolicitudVisita = idSolicitudVisita;
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
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the contacto
	 */
	public Contacto getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the fechaDeseadaRealizacion
	 */
	public Date getFechaDeseadaRealizacion() {
		return fechaDeseadaRealizacion;
	}
	/**
	 * @param fechaDeseadaRealizacion the fechaDeseadaRealizacion to set
	 */
	public void setFechaDeseadaRealizacion(Date fechaDeseadaRealizacion) {
		this.fechaDeseadaRealizacion = fechaDeseadaRealizacion;
	}
	/**
	 * @return the urgencia
	 */
	public String getUrgencia() {
		return urgencia;
	}
	/**
	 * @param urgencia the urgencia to set
	 */
	public void setUrgencia(String urgencia) {
		this.urgencia = urgencia;
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
	 * @return the empleado
	 */
	public Empleado getEmpleado() {
		return empleado;
	}
	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
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
	 * @return the tiempoRealizacion
	 */
	public String getTiempoRealizacion() {
		return tiempoRealizacion;
	}
	/**
	 * @param tiempoRealizacion the tiempoRealizacion to set
	 */
	public void setTiempoRealizacion(String tiempoRealizacion) {
		this.tiempoRealizacion = tiempoRealizacion;
	}
	/**
	 * @param diasAtraso the diasAtraso to set
	 */
	public void setDiasAtraso(String diasAtraso) {
		this.diasAtraso = diasAtraso;
	}
	/**
	 * @return the diasAtraso
	 */
	public String getDiasAtraso() {
		return diasAtraso;
	}
//	/**
//	 * @param visita the visita to set
//	 */
//	public void setVisita(Visita visita) {
//		this.visita = visita;
//	}
//	/**
//	 * @return the visita
//	 */
//	public Visita getVisita() {
//		return visita;
//	}
	/**
	 * @param existeReferencia the existeReferencia to set
	 */
	public void setExisteReferencia(boolean existeReferencia) {
		this.existeReferencia = existeReferencia;
	}
	/**
	 * @return the existeReferencia
	 */
	public boolean isExisteReferencia() {
		return existeReferencia;
	}
	/**
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 * @return the argumento
	 */
	public String getArgumento() {
		return argumento;
	}
	/**
	 * @param argumento the argumento to set
	 */
	public void setArgumento(String argumento) {
		this.argumento = argumento;
	}
	/**
	 * @return the folioVisita
	 */
	public String getFolioVisita() {
		return folioVisita;
	}
	/**
	 * @param folioVisita the folioVisita to set
	 */
	public void setFolioVisita(String folioVisita) {
		this.folioVisita = folioVisita;
	}
	/**
	 * @return the fechaUltimaActualizacionVisita
	 */
	public Date getFechaUltimaActualizacionVisita() {
		return fechaUltimaActualizacionVisita;
	}
	/**
	 * @param fechaUltimaActualizacionVisita the fechaUltimaActualizacionVisita to set
	 */
	public void setFechaUltimaActualizacionVisita(
			Date fechaUltimaActualizacionVisita) {
		this.fechaUltimaActualizacionVisita = fechaUltimaActualizacionVisita;
	}
	/**
	 * @return the fechaEstimadaRealizacionVisita
	 */
	public Date getFechaEstimadaRealizacionVisita() {
		return fechaEstimadaRealizacionVisita;
	}
	/**
	 * @param fechaEstimadaRealizacionVisita the fechaEstimadaRealizacionVisita to set
	 */
	public void setFechaEstimadaRealizacionVisita(
			Date fechaEstimadaRealizacionVisita) {
		this.fechaEstimadaRealizacionVisita = fechaEstimadaRealizacionVisita;
	}
	/**
	 * @param folioVisitaOrigen the folioVisitaOrigen to set
	 */
	public void setFolioVisitaOrigen(String folioVisitaOrigen) {
		this.folioVisitaOrigen = folioVisitaOrigen;
	}
	/**
	 * @return the folioVisitaOrigen
	 */
	public String getFolioVisitaOrigen() {
		return folioVisitaOrigen;
	}
	/**
	 * @return the referencias
	 */
	public List<Referencia> getReferencias() {
		return referencias;
	}
	/**
	 * @param referencias the referencias to set
	 */
	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}
	public long getIdSolicitante() {
		return idSolicitante;
	}
	public void setIdSolicitante(long idSolicitante) {
		this.idSolicitante = idSolicitante;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public long getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}
	public List<DocumentoSolicitudVisita> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<DocumentoSolicitudVisita> documentos) {
		this.documentos = documentos;
	}
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public int getNumDocumentos() {
		return numDocumentos;
	}
	public void setNumDocumentos(int numDocumentos) {
		this.numDocumentos = numDocumentos;
	}
	public String getTipoSolicitud() {
		return tipoSolicitud;
	}
	public void setTipoSolicitud(String tipoSolicitud) {
		this.tipoSolicitud = tipoSolicitud;
	}
	public String getSolicito() {
		return solicito;
	}
	public void setSolicito(String solicito) {
		this.solicito = solicito;
	}
	public String getTipoVisita() {
		return tipoVisita;
	}
	public void setTipoVisita(String tipoVisita) {
		this.tipoVisita = tipoVisita;
	}
	/**
	 * @return the calificacion
	 */
	public Integer getCalificacion() {
		return calificacion;
	}
	/**
	 * @param calificacion the calificacion to set
	 */
	public void setCalificacion(Integer calificacion) {
		this.calificacion = calificacion;
	}
	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}
	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	/**
	 * @return the documento
	 */
	public DocumentoSolicitudVisita getDocumento() {
		return documento;
	}
	/**
	 * @param documento the documento to set
	 */
	public void setDocumento(DocumentoSolicitudVisita documento) {
		this.documento = documento;
	}
	public Integer getReqRealizados() {
		return reqRealizados;
	}
	public void setReqRealizados(Integer reqRealizados) {
		this.reqRealizados = reqRealizados;
	}
	public Integer getReqTotales() {
		return reqTotales;
	}
	public void setReqTotales(Integer reqTotales) {
		this.reqTotales = reqTotales;
	}
	public Integer getNoPendientes() {
		return noPendientes;
	}
	public void setNoPendientes(Integer noPendientes) {
		this.noPendientes = noPendientes;
	}
	public Integer getNoHallazgos() {
		return noHallazgos;
	}
	public void setNoHallazgos(Integer noHallazgos) {
		this.noHallazgos = noHallazgos;
	}
	public Integer getNoRequisiciones() {
		return noRequisiciones;
	}
	public void setNoRequisiciones(Integer noRequisiciones) {
		this.noRequisiciones = noRequisiciones;
	}
	public String getNombreSolicitante() {
		return nombreSolicitante;
	}
	public void setNombreSolicitante(String nombreSolicitante) {
		this.nombreSolicitante = nombreSolicitante;
	}
	public String getUsuarioSolicitante() {
		return usuarioSolicitante;
	}
	public void setUsuarioSolicitante(String usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}
}