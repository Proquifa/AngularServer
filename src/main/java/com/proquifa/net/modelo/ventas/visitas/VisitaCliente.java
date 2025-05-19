package com.proquifa.net.modelo.ventas.visitas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.DocumentoAdjunto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.ventas.Sprint;

public class VisitaCliente {
	
	private Long idVisitaCliente;
	private String nombreCliente;
	private long idContacto;
	private long idCliente;
	private long idEmpleado;
	private int numSolicitud;
	private int numCRM;
	private int calificacionEV;
	private String asunto;
	private String ruta;
	private int numDocumentos;
	private String nivelIngreso;
	private Date fechaEstimadaVisita;
	private Contacto contacto;
	private String tipo;
	private String estado;
	private Long idSprint;
	private List<SolicitudVisita> solicitudesVisita; 
	private Empleado empleado;
	private Sprint sprint;
	private float credito;
	private float valor;
	private String reporte;
	private List<DocumentoAdjunto> documentosReporte; 
	private List<HallazgosAcciones> acciones;
	private List<HallazgosAcciones> hallazgos;
	private List<String> motivos;
	
	private Date fechaVisita;
	private Date horaVisitaInicio;
	private Date horaVisitaFin;
	private Date fechaCheckIn;
	private Date fechaE;
	
	private String justificacionCancelacion;
	private boolean realizacionVisita;
	private String tipoCancelacion;
	private Cliente cliente;
	private Long idSprintAntiguo;
	private boolean visitaExtemporanea;
	private String tipoVisita;
	private String notas;
	private int noHallazgos;
	private int noHallazgosCompletados;
	
	private SolicitudVisita solicitud;
	
	public String getTipoVisita() {
		return tipoVisita;
	}
	public void setTipoVisita(String tipoVisita) {
		this.tipoVisita = tipoVisita;
	}
	public Date getFechaCheckIn() {
		return fechaCheckIn;
	}
	public void setFechaCheckIn(Date fechaCheckIn) {
		this.fechaCheckIn = fechaCheckIn;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public long getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(long idContacto) {
		this.idContacto = idContacto;
	}
	public long getIdEmpleado() {
		return idEmpleado;
	}
	public void setIdEmpleado(long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	public int getNumSolicitud() {
		return numSolicitud;
	}
	public void setNumSolicitud(int numSolicitud) {
		this.numSolicitud = numSolicitud;
	}
	public long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}
	public int getNumCRM() {
		return numCRM;
	}
	public void setNumCRM(int numCRM) {
		this.numCRM = numCRM;
	}
	public Long getIdVisitaCliente() {
		return idVisitaCliente;
	}
	public void setIdVisitaCliente(Long idVisitaCliente) {
		this.idVisitaCliente = idVisitaCliente;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public int getNumDocumentos() {
		return numDocumentos;
	}
	public void setNumDocumentos(int numDocumentos) {
		this.numDocumentos = numDocumentos;
	}
	public String getNivelIngreso() {
		return nivelIngreso;
	}
	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}
	public Date getFechaEstimadaVisita() {
		return fechaEstimadaVisita;
	}
	public void setFechaEstimadaVisita(Date fechaEstimadaVisita) {
		this.fechaEstimadaVisita = fechaEstimadaVisita;
	}
	public Contacto getContacto() {
		return contacto;
	}
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Long getIdSprint() {
		return idSprint;
	}
	public void setIdSprint(Long idSprint) {
		this.idSprint = idSprint;
	}
	public List<SolicitudVisita> getSolicitudesVisita() {
		return solicitudesVisita;
	}
	public void setSolicitudesVisita(List<SolicitudVisita> solicitudesVisita) {
		this.solicitudesVisita = solicitudesVisita;
	}
	public Empleado getEmpleado() {
		return empleado;
	}
	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public Sprint getSprint() {
		return sprint;
	}
	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}
	public float getCredito() {
		return credito;
	}
	public void setCredito(float credito) {
		this.credito = credito;
	}
	public float getValor() {
		return valor;
	}
	public void setValor(float valor) {
		this.valor = valor;
	}
	public Date getHoraVisitaInicio() {
		return horaVisitaInicio;
	}
	public void setHoraVisitaInicio(Date horaVisitaInicio) {
		this.horaVisitaInicio = horaVisitaInicio;
	}
	public Date getHoraVisitaFin() {
		return horaVisitaFin;
	}
	public void setHoraVisitaFin(Date horaVisitaFin) {
		this.horaVisitaFin = horaVisitaFin;
	}
	public Date getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public List<DocumentoAdjunto> getDocumentosReporte() {
		return documentosReporte;
	}
	public void setDocumentosReporte(List<DocumentoAdjunto> documentosReporte) {
		this.documentosReporte = documentosReporte;
	}
	public List<HallazgosAcciones> getAcciones() {
		return acciones;
	}
	public void setAcciones(List<HallazgosAcciones> acciones) {
		this.acciones = acciones;
	}
	public List<HallazgosAcciones> getHallazgos() {
		return hallazgos;
	}
	public void setHallazgos(List<HallazgosAcciones> hallazgos) {
		this.hallazgos = hallazgos;
	}
	public List<String> getMotivos() {
		return motivos;
	}
	public void setMotivos(List<String> motivos) {
		this.motivos = motivos;
	}
	public Date getFechaE() {
		return fechaE;
	}
	public void setFechaE(Date fechaE) {
		this.fechaE = fechaE;
	}
	public String getJustificacionCancelacion() {
		return justificacionCancelacion;
	}
	public void setJustificacionCancelacion(String justificacionCancelacion) {
		this.justificacionCancelacion = justificacionCancelacion;
	}
	public String getTipoCancelacion() {
		return tipoCancelacion;
	}
	public void setTipoCancelacion(String tipoCancelacion) {
		this.tipoCancelacion = tipoCancelacion;
	}
	public boolean isRealizacionVisita() {
		return realizacionVisita;
	}
	public void setRealizacionVisita(boolean realizacionVisita) {
		this.realizacionVisita = realizacionVisita;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Long getIdSprintAntiguo() {
		return idSprintAntiguo;
	}
	public void setIdSprintAntiguo(Long idSprintAntiguo) {
		this.idSprintAntiguo = idSprintAntiguo;
	}
	public boolean isVisitaExtemporanea() {
		return visitaExtemporanea;
	}
	public void setVisitaExtemporanea(boolean visitaExtemporanea) {
		this.visitaExtemporanea = visitaExtemporanea;
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
	public int getCalificacionEV() {
		return calificacionEV;
	}
	public void setCalificacionEV(int calificacionEV) {
		this.calificacionEV = calificacionEV;
	}
	public int getNoHallazgos() {
		return noHallazgos;
	}
	public void setNoHallazgos(int noHallazgos) {
		this.noHallazgos = noHallazgos;
	}
	public int getNoHallazgosCompletados() {
		return noHallazgosCompletados;
	}
	public void setNoHallazgosCompletados(int noHallazgosCompletados) {
		this.noHallazgosCompletados = noHallazgosCompletados;
	}
	public SolicitudVisita getSolicitud() {
		return solicitud;
	}
	public void setSolicitud(SolicitudVisita solicitud) {
		this.solicitud = solicitud;
	}
}
