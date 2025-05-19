package com.proquifa.net.modelo.consultas.comun;

import java.sql.Timestamp;
import java.util.Date;


public class ResumenConsulta {
		
	private Double total;
	private Double flete;
	private Double porcentaje;
	private Double monto;
	
	private String conceptoResultado;
	private String usuario;
	private String etiqueta;
	private String etiquetaETyFT;
	private String nivelI;
	
	private Integer mes;
	
	private Integer visMes;
	private Integer facMes;
	
	private Long piezasTotal;
	private Long factura;
	private Long partidas;
	private Long clientes_proveedores;
	private Long totalFolios;
	private Long ocompras;
	private Long totalPedidos;
	private Long totalClientes;
	private Long totalTiempoProceso;
	private Long totalMarcas;
	
	private Date fechaInicio;
	private Date fechaFinal;
	private Double porcentaje2;
	private Double monto2;
	private Double montoAnterior;
	
	private String ev;
	private String cobrador; 
	
	private long idCartera;
	private String nombreCartera;
	private String folioCartera;
	
	private String tipoProveedor;
	private String pagador;
	private String ruta;
	private String industria;
	private int anio;
	
	private int totalProveedores;
	private int totalOrdenesDespacho;
	private int totalPiezas;
	private String nombreProveedor;
	
	private int totalVisitas;
	private String tipo;
	private boolean realizada;
	
//	VARIABLES PARA LLENADO DE TABLA DE VENTAS VS VISITAS
	private int idVisitaCliente;
	private int calificacion;
	private int reqRealizados;
	private int totalReq;
	private int noPendientes;
	private int noHallazgos;
	private int noRequisiciones;
	private int noCotizaciones;
	private int piezasCotizadas;
	private Double precioDolares;
	private String nombreUsuario;
	private String nombreCliente;
	private String contacto;
	private Date fechaVisita;
	private int idCliente;
	private String email;
	private String tel1;
	private String tel2;
	private String ext1;
	private String ext2;
	
//	VARIABLES PARA LLENADO DE TABLA DE VISITAS TIPO
	private int vtmes;
	private String vtTipoVisita;
	private Boolean vtVisitaRealizada;
	private String vtNombre;
	private String vtUsuario;
	private String vtContacto;
	private String vtPuesto;
	private Timestamp vtFecha;
	private Timestamp vtFechaCheckIn;
	private String vtAsunto;
	private String vtEstado;
	
	
	/**
	 * @return the total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Double total) {
		this.total = total;
	}
	/**
	 * @return the flete
	 */
	public Double getFlete() {
		return flete;
	}
	/**
	 * @param flete the flete to set
	 */
	public void setFlete(Double flete) {
		this.flete = flete;
	}
	/**
	 * @return the porcentaje
	 */
	public Double getPorcentaje() {
		return porcentaje;
	}
	/**
	 * @param porcentaje the porcentaje to set
	 */
	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
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
	 * @return the conceptoResultado
	 */
	public String getConceptoResultado() {
		return conceptoResultado;
	}
	/**
	 * @param conceptoResultado the conceptoResultado to set
	 */
	public void setConceptoResultado(String conceptoResultado) {
		this.conceptoResultado = conceptoResultado;
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
	 * @return the nivelI
	 */
	public String getNivelI() {
		return nivelI;
	}
	/**
	 * @param nivelI the nivelI to set
	 */
	public void setNivelI(String nivelI) {
		this.nivelI = nivelI;
	}
	/**
	 * @return the mes
	 */
	public Integer getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(Integer mes) {
		this.mes = mes;
	}
	/**
	 * @return the piezasTotal
	 */
	public Long getPiezasTotal() {
		return piezasTotal;
	}
	/**
	 * @param piezasTotal the piezasTotal to set
	 */
	public void setPiezasTotal(Long piezasTotal) {
		this.piezasTotal = piezasTotal;
	}
	/**
	 * @return the factura
	 */
	public Long getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(Long factura) {
		this.factura = factura;
	}
	/**
	 * @return the partidas
	 */
	public Long getPartidas() {
		return partidas;
	}
	/**
	 * @param partidas the partidas to set
	 */
	public void setPartidas(Long partidas) {
		this.partidas = partidas;
	}
	/**
	 * @return the clientes_proveedores
	 */
	public Long getClientes_proveedores() {
		return clientes_proveedores;
	}
	/**
	 * @param clientes_proveedores the clientes_proveedores to set
	 */
	public void setClientes_proveedores(Long clientes_proveedores) {
		this.clientes_proveedores = clientes_proveedores;
	}
	/**
	 * @return the totalFolios
	 */
	public Long getTotalFolios() {
		return totalFolios;
	}
	/**
	 * @param totalFolios the totalFolios to set
	 */
	public void setTotalFolios(Long totalFolios) {
		this.totalFolios = totalFolios;
	}
	/**
	 * @return the ocompras
	 */
	public Long getOcompras() {
		return ocompras;
	}
	/**
	 * @param ocompras the ocompras to set
	 */
	public void setOcompras(Long ocompras) {
		this.ocompras = ocompras;
	}
	/**
	 * @return the totalPedidos
	 */
	public Long getTotalPedidos() {
		return totalPedidos;
	}
	/**
	 * @param totalPedidos the totalPedidos to set
	 */
	public void setTotalPedidos(Long totalPedidos) {
		this.totalPedidos = totalPedidos;
	}
	/**
	 * @return the totalClientes
	 */
	public Long getTotalClientes() {
		return totalClientes;
	}
	/**
	 * @param totalClientes the totalClientes to set
	 */
	public void setTotalClientes(Long totalClientes) {
		this.totalClientes = totalClientes;
	}
	/**
	 * @return the totalTiempoProceso
	 */
	public Long getTotalTiempoProceso() {
		return totalTiempoProceso;
	}
	/**
	 * @param totalTiempoProceso the totalTiempoProceso to set
	 */
	public void setTotalTiempoProceso(Long totalTiempoProceso) {
		this.totalTiempoProceso = totalTiempoProceso;
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
	 * @return the fechaFinal
	 */
	public Date getFechaFinal() {
		return fechaFinal;
	}
	/**
	 * @param fechaFinal the fechaFinal to set
	 */
	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}
	/**
	 * @return the porcentaje2
	 */
	public Double getPorcentaje2() {
		return porcentaje2;
	}
	/**
	 * @param porcentaje2 the porcentaje2 to set
	 */
	public void setPorcentaje2(Double porcentaje2) {
		this.porcentaje2 = porcentaje2;
	}
	/**
	 * @return the monto2
	 */
	public Double getMonto2() {
		return monto2;
	}
	/**
	 * @param monto2 the monto2 to set
	 */
	public void setMonto2(Double monto2) {
		this.monto2 = monto2;
	}
	/**
	 * @return the montoAnterior
	 */
	public Double getMontoAnterior() {
		return montoAnterior;
	}
	/**
	 * @param montoAnterior the montoAnterior to set
	 */
	public void setMontoAnterior(Double montoAnterior) {
		this.montoAnterior = montoAnterior;
	}
	/**
	 * @return the ev
	 */
	public String getEv() {
		return ev;
	}
	/**
	 * @param ev the ev to set
	 */
	public void setEv(String ev) {
		this.ev = ev;
	}
	/**
	 * @return the cobrador
	 */
	public String getCobrador() {
		return cobrador;
	}
	/**
	 * @param cobrador the cobrador to set
	 */
	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}
	/**
	 * @return the totalMarcas
	 */
	public Long getTotalMarcas() {
		return totalMarcas;
	}
	/**
	 * @param totalMarcas the totalMarcas to set
	 */
	public void setTotalMarcas(Long totalMarcas) {
		this.totalMarcas = totalMarcas;
	}
	/**
	 * @return the idCartera
	 */
	public long getIdCartera() {
		return idCartera;
	}
	/**
	 * @param idCartera the idCartera to set
	 */
	public void setIdCartera(long idCartera) {
		this.idCartera = idCartera;
	}
	/**
	 * @return the nombreCartera
	 */
	public String getNombreCartera() {
		return nombreCartera;
	}
	/**
	 * @param nombreCartera the nombreCartera to set
	 */
	public void setNombreCartera(String nombreCartera) {
		this.nombreCartera = nombreCartera;
	}
	/**
	 * @return the folioCartera
	 */
	public String getFolioCartera() {
		return folioCartera;
	}
	/**
	 * @param folioCartera the folioCartera to set
	 */
	public void setFolioCartera(String folioCartera) {
		this.folioCartera = folioCartera;
	}
	public String getEtiquetaETyFT() {
		return etiquetaETyFT;
	}
	public void setEtiquetaETyFT(String etiquetaETyFT) {
		this.etiquetaETyFT = etiquetaETyFT;
	}
	public String getTipoProveedor() {
		return tipoProveedor;
	}
	public void setTipoProveedor(String tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}
	public String getPagador() {
		return pagador;
	}
	public void setPagador(String pagador) {
		this.pagador = pagador;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
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
	
	public int getAnio() {
		return anio;
	}
	
	public void setAnio(int anio) {
		this.anio = anio;
	}
	public int getTotalProveedores() {
		return totalProveedores;
	}
	public void setTotalProveedores(int totalProveedores) {
		this.totalProveedores = totalProveedores;
	}
	public int getTotalOrdenesDespacho() {
		return totalOrdenesDespacho;
	}
	public void setTotalOrdenesDespacho(int totalOrdenesDespacho) {
		this.totalOrdenesDespacho = totalOrdenesDespacho;
	}
	public int getTotalPiezas() {
		return totalPiezas;
	}
	public void setTotalPiezas(int totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	/**
	 * @return the totalVisitas
	 */
	public int getTotalVisitas() {
		return totalVisitas;
	}
	/**
	 * @param totalVisitas the totalVisitas to set
	 */
	public void setTotalVisitas(int totalVisitas) {
		this.totalVisitas = totalVisitas;
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
	 * @return the realizada
	 */
	public boolean isRealizada() {
		return realizada;
	}
	/**
	 * @param realizada the realizada to set
	 */
	public void setRealizada(boolean realizada) {
		this.realizada = realizada;
	}
	public int getIdVisitaCliente() {
		return idVisitaCliente;
	}
	public void setIdVisitaCliente(int idVisitaCliente) {
		this.idVisitaCliente = idVisitaCliente;
	}
	public int getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}
	public int getReqRealizados() {
		return reqRealizados;
	}
	public void setReqRealizados(int reqRealizados) {
		this.reqRealizados = reqRealizados;
	}
	public int getTotalReq() {
		return totalReq;
	}
	public void setTotalReq(int totalReq) {
		this.totalReq = totalReq;
	}
	public int getNoPendientes() {
		return noPendientes;
	}
	public void setNoPendientes(int noPendientes) {
		this.noPendientes = noPendientes;
	}
	public int getNoHallazgos() {
		return noHallazgos;
	}
	public void setNoHallazgos(int noHallazgos) {
		this.noHallazgos = noHallazgos;
	}
	public int getNoRequisiciones() {
		return noRequisiciones;
	}
	public void setNoRequisiciones(int noRequisiciones) {
		this.noRequisiciones = noRequisiciones;
	}
	public String getNombreUsuario() {
		return nombreUsuario;
	}
	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public Date getFechaVisita() {
		return fechaVisita;
	}
	public void setFechaVisita(Date fechaVisita) {
		this.fechaVisita = fechaVisita;
	}
	public int getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getExt1() {
		return ext1;
	}
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	public String getExt2() {
		return ext2;
	}
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	public int getNoCotizaciones() {
		return noCotizaciones;
	}
	public void setNoCotizaciones(int noCotizaciones) {
		this.noCotizaciones = noCotizaciones;
	}
	public int getPiezasCotizadas() {
		return piezasCotizadas;
	}
	public void setPiezasCotizadas(int piezasCotizadas) {
		this.piezasCotizadas = piezasCotizadas;
	}
	public Double getPrecioDolares() {
		return precioDolares;
	}
	public void setPrecioDolares(Double precioDolares) {
		this.precioDolares = precioDolares;
	}
	public int getVtmes() {
		return vtmes;
	}
	public void setVtmes(int vtmes) {
		this.vtmes = vtmes;
	}
	public String getVtTipoVisita() {
		return vtTipoVisita;
	}
	public void setVtTipoVisita(String vtTipoVisita) {
		this.vtTipoVisita = vtTipoVisita;
	}
	public String getVtNombre() {
		return vtNombre;
	}
	public void setVtNombre(String vtNombre) {
		this.vtNombre = vtNombre;
	}
	public String getVtUsuario() {
		return vtUsuario;
	}
	public void setVtUsuario(String vtUsuario) {
		this.vtUsuario = vtUsuario;
	}
	public String getVtContacto() {
		return vtContacto;
	}
	public void setVtContacto(String vtContacto) {
		this.vtContacto = vtContacto;
	}
	public String getVtPuesto() {
		return vtPuesto;
	}
	public void setVtPuesto(String vtPuesto) {
		this.vtPuesto = vtPuesto;
	}
	public String getVtAsunto() {
		return vtAsunto;
	}
	public void setVtAsunto(String vtAsunto) {
		this.vtAsunto = vtAsunto;
	}
	public String getVtEstado() {
		return vtEstado;
	}
	public void setVtEstado(String vtEstado) {
		this.vtEstado = vtEstado;
	}
	public Boolean getVtVisitaRealizada() {
		return vtVisitaRealizada;
	}
	public void setVtVisitaRealizada(Boolean vtVisitaRealizada) {
		this.vtVisitaRealizada = vtVisitaRealizada;
	}
	public Timestamp getVtFecha() {
		return vtFecha;
	}
	public void setVtFecha(java.sql.Timestamp timestamp) {
		this.vtFecha = timestamp;
	}
	public Timestamp getVtFechaCheckIn() {
		return vtFechaCheckIn;
	}
	public void setVtFechaCheckIn(Timestamp vtFechaCheckIn) {
		this.vtFechaCheckIn = vtFechaCheckIn;
	}
	public Integer getVisMes() {
		return visMes;
	}
	public void setVisMes(Integer visMes) {
		this.visMes = visMes;
	}
	public Integer getFacMes() {
		return facMes;
	}
	public void setFacMes(Integer facMes) {
		this.facMes = facMes;
	}
}
