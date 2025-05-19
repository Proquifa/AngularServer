package com.proquifa.net.modelo.ventas;

import java.sql.Time;
import java.util.Date;

public class AccionesPendientes {
	private Integer pk_HallazgosAcciones;
	private String descripcion;
	private String descripcionAccion;
	private String tipoHallazgoAccion;
	private Integer fk01_Visita;
	private Date ferealizacion;
	private Integer fk02_Cotizacion;
	private Integer fk03_Pedido;
	private Integer fk04_VisitaVinculada;
	private Integer idContacto;
	private String contacto;
	private String tel1;
	private String extension1;
	private String email;
	private Integer idCliente;
	private String empresa;
	private String folio;
	private String estrategia;
	private String comentarios;
	private String clasificacion_Cliente;
	private String tipo_Visita;
	private Date fecha;
	private Date fecha_Visita;
	private Time hora_Visita;
	private Time hora_Llegada;
	private Integer fk03_Empleado;
	private String etapa;
	private String tipo;
	private Date fechaEstimada;
	private String asunto;
	private Integer documentos;
	private String estado;
	private Integer sprintAsignado;
	private Double creditos;
	private Double valor;
	private Time hora_Visita_Fin;
	private Date fecha_CheckIn;
	private String reporte;
	private Boolean visitaRealizada;
	private String justificacionCancelacion;
	private String tipoCancelacion;
	private Date fechaCierre;
	private Integer fk04_SprintAntiguo;
	private Integer fk05_CotizasMateriales;
	private Integer fk06_CotizasPublicaciones;
	private String colorFecha;
	private String filtroFecha;
	private Integer numSprint;
	private String fechaFormateada;
	
	public Integer getPk_HallazgosAcciones() {
		return pk_HallazgosAcciones;
	}
	public void setPk_HallazgosAcciones(Integer pk_HallazgosAcciones) {
		this.pk_HallazgosAcciones = pk_HallazgosAcciones;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getTipoHallazgoAccion() {
		return tipoHallazgoAccion;
	}
	public void setTipoHallazgoAccion(String tipoHallazgoAccion) {
		this.tipoHallazgoAccion = tipoHallazgoAccion;
	}
	public Integer getFk01_Visita() {
		return fk01_Visita;
	}
	public void setFk01_Visita(Integer fk01_Visita) {
		this.fk01_Visita = fk01_Visita;
	}
	public Date getFerealizacion() {
		return ferealizacion;
	}
	public void setFerealizacion(Date ferealizacion) {
		this.ferealizacion = ferealizacion;
	}
	public Integer getFk02_Cotizacion() {
		return fk02_Cotizacion;
	}
	public void setFk02_Cotizacion(Integer fk02_Cotizacion) {
		this.fk02_Cotizacion = fk02_Cotizacion;
	}
	public Integer getFk03_Pedido() {
		return fk03_Pedido;
	}
	public void setFk03_Pedido(Integer fk03_Pedido) {
		this.fk03_Pedido = fk03_Pedido;
	}
	public Integer getFk04_VisitaVinculada() {
		return fk04_VisitaVinculada;
	}
	public void setFk04_VisitaVinculada(Integer fk04_VisitaVinculada) {
		this.fk04_VisitaVinculada = fk04_VisitaVinculada;
	}
	public Integer getIdContacto() {
		return idContacto;
	}
	public void setIdContacto(Integer idContacto) {
		this.idContacto = idContacto;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getExtension1() {
		return extension1;
	}
	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getEmpresa() {
		return empresa;
	}
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getEstrategia() {
		return estrategia;
	}
	public void setEstrategia(String estrategia) {
		this.estrategia = estrategia;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public String getClasificacion_Cliente() {
		return clasificacion_Cliente;
	}
	public void setClasificacion_Cliente(String clasificacion_Cliente) {
		this.clasificacion_Cliente = clasificacion_Cliente;
	}
	public String getTipo_Visita() {
		return tipo_Visita;
	}
	public void setTipo_Visita(String tipo_Visita) {
		this.tipo_Visita = tipo_Visita;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Date getFecha_Visita() {
		return fecha_Visita;
	}
	public void setFecha_Visita(Date fecha_Visita) {
		this.fecha_Visita = fecha_Visita;
	}
	public Time getHora_Visita() {
		return hora_Visita;
	}
	public void setHora_Visita(Time hora_Visita) {
		this.hora_Visita = hora_Visita;
	}
	public Time getHora_Llegada() {
		return hora_Llegada;
	}
	public void setHora_Llegada(Time hora_Llegada) {
		this.hora_Llegada = hora_Llegada;
	}
	public Integer getFk03_Empleado() {
		return fk03_Empleado;
	}
	public void setFk03_Empleado(Integer fk03_Empleado) {
		this.fk03_Empleado = fk03_Empleado;
	}
	public String getEtapa() {
		return etapa;
	}
	public void setEtapa(String etapa) {
		this.etapa = etapa;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Date getFechaEstimada() {
		return fechaEstimada;
	}
	public void setFechaEstimada(Date fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}
	public String getAsunto() {
		return asunto;
	}
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	public Integer getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Integer documentos) {
		this.documentos = documentos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Integer getSprintAsignado() {
		return sprintAsignado;
	}
	public void setSprintAsignado(Integer sprintAsignado) {
		this.sprintAsignado = sprintAsignado;
	}
	public Double getCreditos() {
		return creditos;
	}
	public void setCreditos(Double creditos) {
		this.creditos = creditos;
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Time getHora_Visita_Fin() {
		return hora_Visita_Fin;
	}
	public void setHora_Visita_Fin(Time hora_Visita_Fin) {
		this.hora_Visita_Fin = hora_Visita_Fin;
	}
	public Date getFecha_CheckIn() {
		return fecha_CheckIn;
	}
	public void setFecha_CheckIn(Date fecha_CheckIn) {
		this.fecha_CheckIn = fecha_CheckIn;
	}
	public String getReporte() {
		return reporte;
	}
	public void setReporte(String reporte) {
		this.reporte = reporte;
	}
	public Boolean getVisitaRealizada() {
		return visitaRealizada;
	}
	public void setVisitaRealizada(Boolean visitaRealizada) {
		this.visitaRealizada = visitaRealizada;
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
	public Date getFechaCierre() {
		return fechaCierre;
	}
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	public Integer getFk04_SprintAntiguo() {
		return fk04_SprintAntiguo;
	}
	public void setFk04_SprintAntiguo(Integer fk04_SprintAntiguo) {
		this.fk04_SprintAntiguo = fk04_SprintAntiguo;
	}
	public Integer getFk05_CotizasMateriales() {
		return fk05_CotizasMateriales;
	}
	public void setFk05_CotizasMateriales(Integer fk05_CotizasMateriales) {
		this.fk05_CotizasMateriales = fk05_CotizasMateriales;
	}
	public Integer getFk06_CotizasPublicaciones() {
		return fk06_CotizasPublicaciones;
	}
	public void setFk06_CotizasPublicaciones(Integer fk06_CotizasPublicaciones) {
		this.fk06_CotizasPublicaciones = fk06_CotizasPublicaciones;
	}
	public String getColorFecha() {
		return colorFecha;
	}
	public void setColorFecha(String colorFecha) {
		this.colorFecha = colorFecha;
	}
	public String getFiltroFecha() {
		return filtroFecha;
	}
	public void setFiltroFecha(String filtroFecha) {
		this.filtroFecha = filtroFecha;
	}
	public String getFechaFormateada() {
		return fechaFormateada;
	}
	public void setFechaFormateada(String fechaFormateada) {
		this.fechaFormateada = fechaFormateada;
	}
	public Integer getNumSprint() {
		return numSprint;
	}
	public void setNumSprint(Integer numSprint) {
		this.numSprint = numSprint;
	}
	public String getDescripcionAccion() {
		return descripcionAccion;
	}
	public void setDescripcionAccion(String descripcionAccion) {
		this.descripcionAccion = descripcionAccion;
	}
		
}
