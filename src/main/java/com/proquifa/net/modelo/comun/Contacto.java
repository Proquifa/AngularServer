package com.proquifa.net.modelo.comun;

import java.util.Date;

public class Contacto {

	//Para contacto
	private Long idContacto;
	private Long idEmpresa;
	private String nombre;
	private String titulo;
	private String puesto;
	private String departamento;
	private String telefono;
	private String telefonoN;
	private String fax;
	private String eMail;
	private String tipo;
	private String empresa;
	private Boolean habilitado;
	private String nacionalidadEmpresa;
	private Date fechaUltimaActualizacion;
	private Direccion direccion;
	private Integer totalContactos;
	private Integer totalDirecciones;
	private Integer totalZonas;
	private boolean agregadoExpo;
	private String extension1; //Extension del Tel1 (telefono)
	private String extension2; //Extension del Tel2 (telefonoN)

	//Para cliente
	private String industria;
	private String rol;
	private String sector;
	private String nivelIngresos;
	private Integer idEmpleadoEsac;
	private String nombreEsac;
	private Boolean estadoCliente;
	private String fk02_Cliente;
	private Long dificultad;
	private String mantenimiento;

	private String nivelDecision;

	private String nivelPuesto;

	private String origenRegistro;
	private Long idProveedor;
	private Long idAgenteAduanal;
	private boolean contactoNAFTA;

	private String esac;
	private String ev;

	private String apellidos;
	private String celular;
	private Integer antiguedadAnio;
	private Integer antiguedadMes;
	private Date fechaNacimiento;
	private int idFletera;

	private boolean usuarioPConnect;

	private byte [] foto;
	private String rutaFoto;

	private byte [] firma;
	private String rutaFirma;


	/**
	 * 
	 */
	public Contacto() {
		super();
	}


	/**
	 * @param idContacto
	 */
	public Contacto(Long idContacto) {
		super();
		this.idContacto = idContacto;
	}



	/**
	 * @return the idContacto
	 */
	public Long getIdContacto() {
		return idContacto;
	}

	/**
	 * @param idContacto the idContacto to set
	 */
	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}

	/**
	 * @return the idEmpresa
	 */
	public Long getIdEmpresa() {
		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
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
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
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
	 * @return the telefonoN
	 */
	public String getTelefonoN() {
		return telefonoN;
	}

	/**
	 * @param telefonoN the telefonoN to set
	 */
	public void setTelefonoN(String telefonoN) {
		this.telefonoN = telefonoN;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
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
	 * @return the habilitado
	 */
	public Boolean getHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}

	/**
	 * @return the nacionalidadEmpresa
	 */
	public String getNacionalidadEmpresa() {
		return nacionalidadEmpresa;
	}

	/**
	 * @param nacionalidadEmpresa the nacionalidadEmpresa to set
	 */
	public void setNacionalidadEmpresa(String nacionalidadEmpresa) {
		this.nacionalidadEmpresa = nacionalidadEmpresa;
	}

	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}

	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}

	/**
	 * @return the direccion
	 */
	public Direccion getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the totalContactos
	 */
	public Integer getTotalContactos() {
		return totalContactos;
	}

	/**
	 * @param totalContactos the totalContactos to set
	 */
	public void setTotalContactos(Integer totalContactos) {
		this.totalContactos = totalContactos;
	}

	/**
	 * @return the totalDirecciones
	 */
	public Integer getTotalDirecciones() {
		return totalDirecciones;
	}

	/**
	 * @param totalDirecciones the totalDirecciones to set
	 */
	public void setTotalDirecciones(Integer totalDirecciones) {
		this.totalDirecciones = totalDirecciones;
	}

	/**
	 * @return the totalZonas
	 */
	public Integer getTotalZonas() {
		return totalZonas;
	}

	/**
	 * @param totalZonas the totalZonas to set
	 */
	public void setTotalZonas(Integer totalZonas) {
		this.totalZonas = totalZonas;
	}

	/**
	 * @return the industria
	 */
	public String getIndustria() {
		return industria;
	}

	/**
	 * @param industria the industria to set
	 */
	public void setIndustria(String industria) {
		this.industria = industria;
	}

	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * @return the sector
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector the sector to set
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return the nivelIngresos
	 */
	public String getNivelIngresos() {
		return nivelIngresos;
	}

	/**
	 * @param nivelIngresos the nivelIngresos to set
	 */
	public void setNivelIngresos(String nivelIngresos) {
		this.nivelIngresos = nivelIngresos;
	}

	/**
	 * @return the idEmpleadoEsac
	 */
	public Integer getIdEmpleadoEsac() {
		return idEmpleadoEsac;
	}

	/**
	 * @param idEmpleadoEsac the idEmpleadoEsac to set
	 */
	public void setIdEmpleadoEsac(Integer idEmpleadoEsac) {
		this.idEmpleadoEsac = idEmpleadoEsac;
	}

	/**
	 * @return the nombreEsac
	 */
	public String getNombreEsac() {
		return nombreEsac;
	}

	/**
	 * @param nombreEsac the nombreEsac to set
	 */
	public void setNombreEsac(String nombreEsac) {
		this.nombreEsac = nombreEsac;
	}

	/**
	 * @return the nivelDecision
	 */
	public String getNivelDecision() {
		return nivelDecision;
	}

	/**
	 * @param nivelDecision the nivelDecision to set
	 */
	public void setNivelDecision(String nivelDecision) {
		this.nivelDecision = nivelDecision;
	}

	/**
	 * @return the nivelPuesto
	 */
	public String getNivelPuesto() {
		return nivelPuesto;
	}

	/**
	 * @param nivelPuesto the nivelPuesto to set
	 */
	public void setNivelPuesto(String nivelPuesto) {
		this.nivelPuesto = nivelPuesto;
	}

	/**
	 * @param agregadoExpo the agregadoExpo to set
	 */
	public void setAgregadoExpo(boolean agregadoExpo) {
		this.agregadoExpo = agregadoExpo;
	}

	/**
	 * @return the agregadoExpo
	 */
	public boolean isAgregadoExpo() {
		return agregadoExpo;
	}

	/**
	 * @return the estadoCliente
	 */
	public Boolean getEstadoCliente() {
		return estadoCliente;
	}

	/**
	 * @param estadoCliente the estadoCliente to set
	 */
	public void setEstadoCliente(Boolean estadoCliente) {
		this.estadoCliente = estadoCliente;
	}

	/**
	 * @return the fk02_Cliente
	 */
	public String getFk02_Cliente() {
		return fk02_Cliente;
	}

	/**
	 * @param fk02_Cliente the fk02_Cliente to set
	 */
	public void setFk02_Cliente(String fk02_Cliente) {
		this.fk02_Cliente = fk02_Cliente;
	}

	/**
	 * @return the origenRegistro
	 */
	public String getOrigenRegistro() {
		return origenRegistro;
	}

	/**
	 * @param origenRegistro the origenRegistro to set
	 */
	public void setOrigenRegistro(String origenRegistro) {
		this.origenRegistro = origenRegistro;
	}


	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}

	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * @return the eMail
	 */
	public String getEMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void setEMail(String eMail) {
		this.eMail = eMail;
	}

	/**
	 * @return the idAgenteAduanal
	 */
	public Long getIdAgenteAduanal() {
		return idAgenteAduanal;
	}

	/**
	 * @param idAgenteAduanal the idAgenteAduanal to set
	 */
	public void setIdAgenteAduanal(Long idAgenteAduanal) {
		this.idAgenteAduanal = idAgenteAduanal;
	}

	public Long getDificultad() {
		return dificultad;
	}

	public void setDificultad(Long dificultad) {
		this.dificultad = dificultad;
	}

	public String getMantenimiento() {
		return mantenimiento;
	}

	public void setMantenimiento(String mantenimiento) {
		this.mantenimiento = mantenimiento;
	}

	/**
	 * @return the extension1
	 */
	public String getExtension1() {
		return extension1;
	}

	/**
	 * @param extension1 the extension1 to set
	 */
	public void setExtension1(String extension1) {
		this.extension1 = extension1;
	}

	/**
	 * @return the extension2
	 */
	public String getExtension2() {
		return extension2;
	}

	/**
	 * @param extension2 the extension2 to set
	 */
	public void setExtension2(String extension2) {
		this.extension2 = extension2;
	}

	public String getEsac() {
		return esac;
	}

	public void setEsac(String esac) {
		this.esac = esac;
	}

	public String getEv() {
		return ev;
	}

	public void setEv(String ev) {
		this.ev = ev;
	}

	public boolean isUsuarioPConnect() {
		return usuarioPConnect;
	}

	public void setUsuarioPConnect(boolean usuarioPConnect) {
		this.usuarioPConnect = usuarioPConnect;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Integer getAntiguedadAnio() {
		return antiguedadAnio;
	}

	public void setAntiguedadAnio(Integer antiguedadAnio) {
		this.antiguedadAnio = antiguedadAnio;
	}

	public Integer getAntiguedadMes() {
		return antiguedadMes;
	}

	public void setAntiguedadMes(Integer antiguedadMes) {
		this.antiguedadMes = antiguedadMes;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public int getIdFletera() {
		return idFletera;
	}

	public void setIdFletera(int idFletera) {
		this.idFletera = idFletera;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getRutaFoto() {
		return rutaFoto;
	}

	public void setRutaFoto(String rutaFoto) {
		this.rutaFoto = rutaFoto;
	}

	public boolean isContactoNAFTA() {
		return contactoNAFTA;
	}

	public void setContactoNAFTA(boolean contactoNAFTA) {
		this.contactoNAFTA = contactoNAFTA;
	}

	public byte[] getFirma() {
		return firma;
	}

	public void setFirma(byte[] firma) {
		this.firma = firma;
	}

	public String getRutaFirma() {
		return rutaFirma;
	}

	public void setRutaFirma(String rutaFirma) {
		this.rutaFirma = rutaFirma;
	}
}
