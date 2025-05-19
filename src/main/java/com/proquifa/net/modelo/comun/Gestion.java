/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

/**
 * @author amartinez
 *
 */
public class Gestion {
	private Long idGestion;
	private Long empleado;
	private Long incidente;
	private Long cliente;
	private Long proveedor;
	private Long subProceso;
	private Long producto;
	private Long contacto;
	private String tipo;
	private Boolean impactoCliente;
	private Boolean impactoProducto;
	private Boolean impactoSistema;
	private String descripcion;
	private String quien;
	private String lugar;
	private String cuando;
	private String como;
	private String causa;
	private String solucion;
	private Boolean aceptado;
	private String justificacion;
	private List<Referencia> referencias;
	private List<Procedimiento> procedimientos;
	private Date fecha;
	private String nombreContacto;
	private String correoContacto;
	private Boolean analisis;
	private Boolean decision;
	private String problema;
	private Boolean seleccionado;
	private String aceptadoString;
	private Long idPendiente;
	private String enviarCorreo;
	private String nombreEmpleado;
	private Boolean acuerdo;
	private Boolean NAProcedimiento;
	private String procedimientosAsociados;
	private String nombre_Proveedor;
	private String nombre_Producto;
	private String nombre_Cliente;
	private String pilotoProcesoOrigen;	
	private Long subProcesoIncidente;
	
	/**
	 * @return the idGestion
	 */
	public Long getIdGestion() {
		return idGestion;
	}
	/**
	 * @param idGestion the idGestion to set
	 */
	public void setIdGestion(Long idGestion) {
		this.idGestion = idGestion;
	}
	/**
	 * @return the empleado
	 */
	public Long getEmpleado() {
		return empleado;
	}
	/**
	 * @param empleado the empleado to set
	 */
	public void setEmpleado(Long empleado) {
		this.empleado = empleado;
	}
	/**
	 * @return the incidente
	 */
	public Long getIncidente() {
		return incidente;
	}
	/**
	 * @param incidente the incidente to set
	 */
	public void setIncidente(Long incidente) {
		this.incidente = incidente;
	}
	/**
	 * @return the cliente
	 */
	public Long getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the proveedor
	 */
	public Long getProveedor() {
		return proveedor;
	}
	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Long proveedor) {
		this.proveedor = proveedor;
	}
	/**
	 * @return the subProceso
	 */
	public Long getSubProceso() {
		return subProceso;
	}
	/**
	 * @param subProceso the subProceso to set
	 */
	public void setSubProceso(Long subProceso) {
		this.subProceso = subProceso;
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
	 * @return the impactoCliente
	 */
	public Boolean getImpactoCliente() {
		return impactoCliente;
	}
	/**
	 * @param impactoCliente the impactoCliente to set
	 */
	public void setImpactoCliente(Boolean impactoCliente) {
		this.impactoCliente = impactoCliente;
	}
	/**
	 * @return the impactoProducto
	 */
	public Boolean getImpactoProducto() {
		return impactoProducto;
	}
	/**
	 * @param impactoProducto the impactoProducto to set
	 */
	public void setImpactoProducto(Boolean impactoProducto) {
		this.impactoProducto = impactoProducto;
	}
	/**
	 * @return the impactoSistema
	 */
	public Boolean getImpactoSistema() {
		return impactoSistema;
	}
	/**
	 * @param impactoSistema the impactoSistema to set
	 */
	public void setImpactoSistema(Boolean impactoSistema) {
		this.impactoSistema = impactoSistema;
	}
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the quien
	 */
	public String getQuien() {
		return quien;
	}
	/**
	 * @param quien the quien to set
	 */
	public void setQuien(String quien) {
		this.quien = quien;
	}
	/**
	 * @return the lugar
	 */
	public String getLugar() {
		return lugar;
	}
	/**
	 * @param lugar the lugar to set
	 */
	public void setLugar(String lugar) {
		this.lugar = lugar;
	}
	/**
	 * @return the cuando
	 */
	public String getCuando() {
		return cuando;
	}
	/**
	 * @param cuando the cuando to set
	 */
	public void setCuando(String cuando) {
		this.cuando = cuando;
	}
	/**
	 * @return the como
	 */
	public String getComo() {
		return como;
	}
	/**
	 * @param como the como to set
	 */
	public void setComo(String como) {
		this.como = como;
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
	 * @return the solucion
	 */
	public String getSolucion() {
		return solucion;
	}
	/**
	 * @param solucion the solucion to set
	 */
	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}
	/**
	 * @return the aceptado
	 */
	public Boolean getAceptado() {
		return aceptado;
	}
	/**
	 * @param aceptado the aceptado to set
	 */
	public void setAceptado(Boolean aceptado) {
		this.aceptado = aceptado;
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
	 * @param referencias the referencias to set
	 */
	public void setReferencias(List<Referencia> referencias) {
		this.referencias = referencias;
	}
	/**
	 * @return the referencias
	 */
	public List<Referencia> getReferencias() {
		return referencias;
	}
	/**
	 * @param procedimientos the procedimientos to set
	 */
	public void setProcedimientos(List<Procedimiento> procedimientos) {
		this.procedimientos = procedimientos;
	}
	/**
	 * @return the procedimientos
	 */
	public List<Procedimiento> getProcedimientos() {
		return procedimientos;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Long producto) {
		this.producto = producto;
	}
	/**
	 * @return the producto
	 */
	public Long getProducto() {
		return producto;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param nombreContacto the nombreContacto to set
	 */
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}
	/**
	 * @return the nombreContacto
	 */
	public String getNombreContacto() {
		return nombreContacto;
	}
	/**
	 * @param analisis the analisis to set
	 */
	public void setAnalisis(Boolean analisis) {
		this.analisis = analisis;
	}
	/**
	 * @return the analisis
	 */
	public Boolean getAnalisis() {
		return analisis;
	}
	/**
	 * @param desicion the desicion to set
	 */
	public void setDecision(Boolean decision) {
		this.decision = decision;
	}
	/**
	 * @return the desicion
	 */
	public Boolean getDecision() {
		return decision;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(Long contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the contacto
	 */
	public Long getContacto() {
		return contacto;
	}
	/**
	 * @param correoContacto the correoContacto to set
	 */
	public void setCorreoContacto(String correoContacto) {
		this.correoContacto = correoContacto;
	}
	/**
	 * @return the correoContacto
	 */
	public String getCorreoContacto() {
		return correoContacto;
	}
	/**
	 * @param problema the problema to set
	 */
	public void setProblema(String problema) {
		this.problema = problema;
	}
	/**
	 * @return the problema
	 */
	public String getProblema() {
		return problema;
	}
	/**
	 * @param seleccionado the seleccionado to set
	 */
	public void setSeleccionado(Boolean seleccionado) {
		this.seleccionado = seleccionado;
	}
	/**
	 * @return the seleccionado
	 */
	public Boolean getSeleccionado() {
		return seleccionado;
	}
	/**
	 * @param aceptadoString the aceptadoString to set
	 */
	public void setAceptadoString(String aceptadoString) {
		this.aceptadoString = aceptadoString;
	}
	/**
	 * @return the aceptadoString
	 */
	public String getAceptadoString() {
		return aceptadoString;
	}
	/**
	 * @param idPendiente the idPendiente to set
	 */
	public void setIdPendiente(Long idPendiente) {
		this.idPendiente = idPendiente;
	}
	/**
	 * @return the idPendiente
	 */
	public Long getIdPendiente() {
		return idPendiente;
	}
	/**
	 * @param enviarCorreo the enviarCorreo to set
	 */
	public void setEnviarCorreo(String enviarCorreo) {
		this.enviarCorreo = enviarCorreo;
	}
	/**
	 * @return the enviarCorreo
	 */
	public String getEnviarCorreo() {
		return enviarCorreo;
	}
	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}
	public String getNombreEmpleado() {
		return nombreEmpleado;
	}
	/**
	 * @param acuerdo the acuerdo to set
	 */
	public void setAcuerdo(Boolean acuerdo) {
		this.acuerdo = acuerdo;
	}
	/**
	 * @return the acuerdo
	 */
	public Boolean getAcuerdo() {
		return acuerdo;
	}
	/**
	 * @param nAProcedimiento the nAProcedimiento to set
	 */
	public void setNAProcedimiento(Boolean nAProcedimiento) {
		NAProcedimiento = nAProcedimiento;
	}
	/**
	 * @return the nAProcedimiento
	 */
	public Boolean getNAProcedimiento() {
		return NAProcedimiento;
	}
	/**
	 * @param procedimientosAsociados the procedimientosAsociados to set
	 */
	public void setProcedimientosAsociados(String procedimientosAsociados) {
		this.procedimientosAsociados = procedimientosAsociados;
	}
	/**
	 * @return the procedimientosAsociados
	 */
	public String getProcedimientosAsociados() {
		return procedimientosAsociados;
	}
	/**
	 * @return the nombre_Proveedor
	 */
	public String getNombre_Proveedor() {
		return nombre_Proveedor;
	}
	/**
	 * @param nombre_Proveedor the nombre_Proveedor to set
	 */
	public void setNombre_Proveedor(String nombre_Proveedor) {
		this.nombre_Proveedor = nombre_Proveedor;
	}
	/**
	 * @return the nombre_Producto
	 */
	public String getNombre_Producto() {
		return nombre_Producto;
	}
	/**
	 * @param nombre_Producto the nombre_Producto to set
	 */
	public void setNombre_Producto(String nombre_Producto) {
		this.nombre_Producto = nombre_Producto;
	}
	/**
	 * @return the nombre_Empleado
	 */
	public String getNombre_Cliente() {
		return nombre_Cliente;
	}
	/**
	 * @param nombre_Empleado the nombre_Empleado to set
	 */
	public void setNombre_Cliente(String nombre_Cliente) {
		this.nombre_Cliente = nombre_Cliente;
	}
	/**
	 * @param pilotoProcesoOrigen the pilotoProcesoOrigen to set
	 */
	public void setPilotoProcesoOrigen(String pilotoProcesoOrigen) {
		this.pilotoProcesoOrigen = pilotoProcesoOrigen;
	}
	/**
	 * @return the pilotoProcesoOrigen
	 */
	public String getPilotoProcesoOrigen() {
		return pilotoProcesoOrigen;
	}
	/**
	 * @return the subProcesoIncidente
	 */
	public Long getSubProcesoIncidente() {
		return subProcesoIncidente;
	}
	/**
	 * @param subProcesoIncidente the subProcesoIncidente to set
	 */
	public void setSubProcesoIncidente(Long subProcesoIncidente) {
		this.subProcesoIncidente = subProcesoIncidente;
	}
}
