/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author ernestogonzalezlozada
 *
 */
@XmlRootElement(name="Empleado")
public class Empleado {
	
	private Long idEmpleado;
	private Long clave;
	private String usuario;
	private String password;
	private Long nivel;
	private String nombre;
	private String departamento;
	private Long fase;
	private String puesto;
	private Date fechaIngreso;
	private Date fechaContrato;
	private Date fechaNacimiento;
	private String telefono;
	private String ext1;
	private String telefono2;
	private String empresa;
	private String direccion;
	private String curp;
	private Long idFuncion;
	private Long costoHoraHombre;
	private String nombreFuncion;
	private String nombreSubproceso;
	private String nivelFuncion;
	private String responsable;
	private ArrayList<String> folioIncidentes;
	private Double costoTotalIncidentes;
	private Double montoAPagar;
	private Double porcentajeBono;
	private List<Modulo> modulos;
	private String nivelGeneral;
	private Long idSubproceso;
	private String subproceso;
	private Boolean esGerente;
	private ArrayList<String> incidentes;
	private Boolean administrador;
	private List<String> roles;
	private Integer numrol;
	
	private String usuarioESAC;
	private String nombreESAC;
	private String area;
	
	//////SCRUM
	private boolean asistencia;
	private String codigoAsistencia;
	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the nivel
	 */
	public Long getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(Long nivel) {
		this.nivel = nivel;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the departamento
	 */
	public String getDepartamento() {
		return departamento;
	}
	/**
	 * @param departamento the departamento to set
	 */
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	/**
	 * @return the fase
	 */
	public Long getFase() {
		return fase;
	}
	/**
	 * @param fase the fase to set
	 */
	public void setFase(Long fase) {
		this.fase = fase;
	}
	/**
	 * @return the puesto
	 */
	public String getPuesto() {
		return puesto;
	}
	/**
	 * @param puesto the puesto to set
	 */
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	/**
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	/**
	 * @return the fechaContrato
	 */
	public Date getFechaContrato() {
		return fechaContrato;
	}
	/**
	 * @param fechaContrato the fechaContrato to set
	 */
	public void setFechaContrato(Date fechaContrato) {
		this.fechaContrato = fechaContrato;
	}
	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}
	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	/**
	 * @return the telefono
	 */
	public String getTelefono() {
		return telefono;
	}
	/**
	 * @param telefono the telefono to set
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	/**
	 * @return the telefono2
	 */
	public String getTelefono2() {
		return telefono2;
	}
	/**
	 * @param telefono2 the telefono2 to set
	 */
	public void setTelefono2(String telefono2) {
		this.telefono2 = telefono2;
	}
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the curp
	 */
	public String getCurp() {
		return curp;
	}
	/**
	 * @param curp the curp to set
	 */
	public void setCurp(String curp) {
		this.curp = curp;
	}
	/**
	 * @return the idFuncion
	 */
	public Long getIdFuncion() {
		return idFuncion;
	}
	/**
	 * @param idFuncion the idFuncion to set
	 */
	public void setIdFuncion(Long idFuncion) {
		this.idFuncion = idFuncion;
	}
	/**
	 * @return the costoHoraHombre
	 */
	public Long getCostoHoraHombre() {
		return costoHoraHombre;
	}
	/**
	 * @param costoHoraHombre the costoHoraHombre to set
	 */
	public void setCostoHoraHombre(Long costoHoraHombre) {
		this.costoHoraHombre = costoHoraHombre;
	}
	/**
	 * @return the nombreFuncion
	 */
	public String getNombreFuncion() {
		return nombreFuncion;
	}
	/**
	 * @param nombreFuncion the nombreFuncion to set
	 */
	public void setNombreFuncion(String nombreFuncion) {
		this.nombreFuncion = nombreFuncion;
	}
	/**
	 * @return the nombreSubproceso
	 */
	public String getNombreSubproceso() {
		return nombreSubproceso;
	}
	/**
	 * @param nombreSubproceso the nombreSubproceso to set
	 */
	public void setNombreSubproceso(String nombreSubproceso) {
		this.nombreSubproceso = nombreSubproceso;
	}
	/**
	 * @return the nivelFuncion
	 */
	public String getNivelFuncion() {
		return nivelFuncion;
	}
	/**
	 * @param nivelFuncion the nivelFuncion to set
	 */
	public void setNivelFuncion(String nivelFuncion) {
		this.nivelFuncion = nivelFuncion;
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
	 * @return the folioIncidentes
	 */
	public ArrayList<String> getFolioIncidentes() {
		return folioIncidentes;
	}
	/**
	 * @param folioIncidentes the folioIncidentes to set
	 */
	public void setFolioIncidentes(ArrayList<String> folioIncidentes) {
		this.folioIncidentes = folioIncidentes;
	}
	/**
	 * @return the costoTotalIncidentes
	 */
	public Double getCostoTotalIncidentes() {
		return costoTotalIncidentes;
	}
	/**
	 * @param costoTotalIncidentes the costoTotalIncidentes to set
	 */
	public void setCostoTotalIncidentes(Double costoTotalIncidentes) {
		this.costoTotalIncidentes = costoTotalIncidentes;
	}
	/**
	 * @return the montoAPagar
	 */
	public Double getMontoAPagar() {
		return montoAPagar;
	}
	/**
	 * @param montoAPagar the montoAPagar to set
	 */
	public void setMontoAPagar(Double montoAPagar) {
		this.montoAPagar = montoAPagar;
	}
	/**
	 * @return the porcentajeBono
	 */
	public Double getPorcentajeBono() {
		return porcentajeBono;
	}
	/**
	 * @param porcentajeBono the porcentajeBono to set
	 */
	public void setPorcentajeBono(Double porcentajeBono) {
		this.porcentajeBono = porcentajeBono;
	}
	/**
	 * @return the modulos
	 */
	public List<Modulo> getModulos() {
		return modulos;
	}
	/**
	 * @param modulos the modulos to set
	 */
	public void setModulos(List<Modulo> modulos) {
		this.modulos = modulos;
	}
	/**
	 * @return the nivelGeneral
	 */
	public String getNivelGeneral() {
		return nivelGeneral;
	}
	/**
	 * @param nivelGeneral the nivelGeneral to set
	 */
	public void setNivelGeneral(String nivelGeneral) {
		this.nivelGeneral = nivelGeneral;
	}
	/**
	 * @return the idSubproceso
	 */
	public Long getIdSubproceso() {
		return idSubproceso;
	}
	/**
	 * @param idSubproceso the idSubproceso to set
	 */
	public void setIdSubproceso(Long idSubproceso) {
		this.idSubproceso = idSubproceso;
	}
	/**
	 * @return the subproceso
	 */
	public String getSubproceso() {
		return subproceso;
	}
	/**
	 * @param subproceso the subproceso to set
	 */
	public void setSubproceso(String subproceso) {
		this.subproceso = subproceso;
	}
	/**
	 * @return the esGerente
	 */
	public Boolean getEsGerente() {
		return esGerente;
	}
	/**
	 * @param esGerente the esGerente to set
	 */
	public void setEsGerente(Boolean esGerente) {
		this.esGerente = esGerente;
	}
	/**
	 * @return the incidentes
	 */
	public ArrayList<String> getIncidentes() {
		return incidentes;
	}
	/**
	 * @param incidentes the incidentes to set
	 */
	public void setIncidentes(ArrayList<String> incidentes) {
		this.incidentes = incidentes;
	}
	public void setAdministrador(Boolean administrador) {
		this.administrador = administrador;
	}
	public Boolean getAdministrador() {
		return administrador;
	}
	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}
	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public Integer getNumrol() {
		return numrol;
	}
	public void setNumrol(Integer numrol) {
		this.numrol = numrol;
	}
	public boolean isAsistencia() {
		return asistencia;
	}
	public void setAsistencia(boolean asistencia) {
		this.asistencia = asistencia;
	}
	public String getCodigoAsistencia() {
		return codigoAsistencia;
	}
	public void setCodigoAsistencia(String codigoAsistencia) {
		this.codigoAsistencia = codigoAsistencia;
	}
	public String getUsuarioESAC() {
		return usuarioESAC;
	}
	public void setUsuarioESAC(String usuarioESAC) {
		this.usuarioESAC = usuarioESAC;
	}
	public String getNombreESAC() {
		return nombreESAC;
	}
	public void setNombreESAC(String nombreESAC) {
		this.nombreESAC = nombreESAC;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
}