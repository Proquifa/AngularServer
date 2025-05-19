package com.proquifa.net.modelo.comun;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.consultas.comun.ArchivosFacturaCliente;
import com.proquifa.net.modelo.despachos.mensajero.ComentaiosRutaDP;
import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;
import com.proquifa.net.modelo.despachos.mensajero.PersonalAlmacenCliente;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;
public class Parametro {
	
	//ObtenerFechaDinamicamente
	private String tabla;
	private String campo;
	private String fecha;


	//EmbalarPedido
	private Long idPPedido;
	private Integer idPedido;
	private Long tiempo;
	private Integer idPackingList;
	private String destinoAlmacen;
	private String  zona;
	List <Integer> lista = new ArrayList<Integer>();

	private Integer idHorario;
	private boolean generGuia;
	
	private ParametroEnvio envio;


	//Generate QR Code And Barcode
	private String code;
	private String nombreProveedor;

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getDestinoAlmacen() {
		return destinoAlmacen;
	}

	public void setDestinoAlmacen(String destinoAlmacen) {
		this.destinoAlmacen = destinoAlmacen;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public List<Integer> getLista() {
		return lista;
	}

	public void setLista(List<Integer> lista) {
		this.lista = lista;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public void setTiempo(Long tiempo) {
		this.tiempo = tiempo;
	}

	public Long getIdPPedido() {
		return idPPedido;
	}

	public void setIdPPedido(Long idPPedido) {
		this.idPPedido = idPPedido;
	}

	private String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	/* Emppleado */
	private String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/* Factura */

	private Date finicio = new Date();
	private Date ffin = new Date();
	private long idCliente;
	private String fpor;
	private String estado;
	private Boolean dentroSistema;
	private Long factura;

	private Long idUsuarioLogueado;
	private Long cobrador;
	private String uuid;

	private String[] archivos;
	private String[] nombres;
	private ArchivosFacturaCliente[] archivosClientes;
	private String nombreArchivo;

	private String estadoPedido;
	private List<Integer> idsPPedidos;

	private Long idFactura;
	private Integer idUsuario2;

	//rutadp
	private String idRutaDP;
	
	/*atributos para promsy*/
	private String startDate;
	private String finalDate;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}

	public String getIdRutaDP() {
		return idRutaDP;
	}

	public void setIdRutaDP(String idRutaDP) {
		this.idRutaDP = idRutaDP;
	}

	public Date getFinicio() {
		return finicio;
	}

	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}

	public Date getFfin() {
		return ffin;
	}

	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public String getFpor() {
		return fpor;
	}

	public void setFpor(String fpor) {
		this.fpor = fpor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getDentroSistema() {
		return dentroSistema;
	}

	public void setDentroSistema(Boolean dentroSistema) {
		this.dentroSistema = dentroSistema;
	}

	public Long getFactura() {
		return factura;
	}

	public void setFactura(Long factura) {
		this.factura = factura;
	}

	public Long getIdUsuarioLogueado() {
		return idUsuarioLogueado;
	}

	public void setIdUsuarioLogueado(Long idUsuarioLogueado) {
		this.idUsuarioLogueado = idUsuarioLogueado;
	}

	public Long getCobrador() {
		return cobrador;
	}

	public void setCobrador(Long cobrador) {
		this.cobrador = cobrador;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String[] getArchivos() {
		return archivos;
	}

	public void setArchivos(String[] archivos) {
		this.archivos = archivos;
	}

	public String[] getNombres() {
		return nombres;
	}

	public void setNombres(String[] nombres) {
		this.nombres = nombres;
	}

	/* Facturación */
	private String cliente;
	private String refacturada;
	private String facturo;
	private String medio;
	private String cPago;
	private String facturaS;
	private String cPedido;
	private String SC;

	public String getSC() {
		return SC;
	}

	public void setSC(String sC) {
		SC = sC;
	}

	public String getcPedido() {
		return cPedido;
	}

	public void setcPedido(String cPedido) {
		this.cPedido = cPedido;
	}

	public String getFacturaS() {
		return facturaS;
	}

	public void setFacturaS(String facturaS) {
		this.facturaS = facturaS;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getRefacturada() {
		return refacturada;
	}

	public void setRefacturada(String refacturada) {
		this.refacturada = refacturada;
	}

	public String getFacturo() {
		return facturo;
	}

	public void setFacturo(String facturo) {
		this.facturo = facturo;
	}

	public String getMedio() {
		return medio;
	}

	public void setMedio(String medio) {
		this.medio = medio;
	}

	public String getcPago() {
		return cPago;
	}

	public void setcPago(String cPago) {
		this.cPago = cPago;
	}

	/* Asistencia */
	private Long idTrabajador;
	private String tipoChecada;
	private String area;
	private String depto;
	private String categoria;
	private String incidencia;
	private String localidad;
	private String nombreTrabajador;

	public Long getIdTrabajador() {
		return idTrabajador;
	}

	public void setIdTrabajador(Long idTrabajador) {
		this.idTrabajador = idTrabajador;
	}

	public String getTipoChecada() {
		return tipoChecada;
	}

	public void setTipoChecada(String tipoChecada) {
		this.tipoChecada = tipoChecada;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getIncidencia() {
		return incidencia;
	}

	public void setIncidencia(String incidencia) {
		this.incidencia = incidencia;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getNombreTrabajador() {
		return nombreTrabajador;
	}

	public void setNombreTrabajador(String nombreTrabajador) {
		this.nombreTrabajador = nombreTrabajador;
	}

	/*CatalogoClientes*/
	private Long idFuncion;
	private Long idResponsable;
	private Long idCartera;
	private Long idCarteraAnt;
	private Boolean borrarCarteraAnt;
	private Long idUsuario;
	private Cartera cartera;
	private Boolean sinCartera;
	private Empleado empleado;
	private Long habilitado;

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
	 * @return the idResponsable
	 */
	public Long getIdResponsable() {
		return idResponsable;
	}

	/**
	 * @param idResponsable the idResponsable to set
	 */
	public void setIdResponsable(Long idResponsable) {
		this.idResponsable = idResponsable;
	}

	/**
	 * @return the idCartera
	 */
	public Long getIdCartera() {
		return idCartera;
	}

	/**
	 * @param idCartera the idCartera to set
	 */
	public void setIdCartera(Long idCartera) {
		this.idCartera = idCartera;
	}

	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * @return the cartera
	 */
	public Cartera getCartera() {
		return cartera;
	}

	/**
	 * @param cartera the cartera to set
	 */
	public void setCartera(Cartera cartera) {
		this.cartera = cartera;
	}

	/**
	 * @return the sinCartera
	 */
	public Boolean getSinCartera() {
		return sinCartera;
	}

	/**
	 * @param sinCartera the sinCartera to set
	 */
	public void setSinCartera(Boolean sinCartera) {
		this.sinCartera = sinCartera;
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
	 * @return the habilitado
	 */
	public Long getHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(Long habilitado) {
		this.habilitado = habilitado;
	}

	public Long getIdCarteraAnt() {
		return idCarteraAnt;
	}

	public void setIdCarteraAnt(Long idCarteraAnt) {
		this.idCarteraAnt = idCarteraAnt;
	}

	public Boolean getBorrarCarteraAnt() {
		return borrarCarteraAnt;
	}

	public void setBorrarCarteraAnt(Boolean borrarCarteraAnt) {
		this.borrarCarteraAnt = borrarCarteraAnt;
	}

	///*Compras*////
	private Date FechaInicio;
	private Date FechaFin;
	private Long proveedor;
	private Integer Estado;
	private Integer coloco;
	private String ordenCompra;
	private Long idPCompra;
	private String valorAdicional;
	private Integer partida;
	private String empresaCompra;
	/**
	 * @param FechaInicio the FechaInicio to set
	 */
	public void setFechaInicio(Date FechaInicio) {
		this.FechaInicio = FechaInicio;
	}

	/**
	 * @return the FechaInicio
	 */
	public Date getFechaInicio() {
		return this.FechaInicio;
	}


	/**
	 * @param FechaFIN the FechaFin to set
	 */
	public void setFechaFin(Date FechaFin) {
		this.FechaFin = FechaFin;
	}

	/**
	 * @return the FechaFin
	 */
	public Date getFechaFin() {
		return FechaFin;
	}

	/**
	 * @param FechaFIN the FechaFin to set
	 */
	public void setproveedor(Long proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the FechaFin
	 */
	public Long getproveedor() {
		return proveedor;
	}

	/**
	 * @param coloco the coloco to set
	 */
	public void setcoloco(Integer coloco) {
		this.coloco = coloco;
	}

	/**
	 * @return the coloco
	 */
	public Integer getcoloco() {
		return coloco;
	}

	/**
	 * @param ordenCompra the ordenCompra to set
	 */
	public void setordenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	/**
	 * @return the ordenCompra
	 */
	public String getordenCompra() {
		return ordenCompra;
	}


	/**
	 * @param Estado the Estado to set
	 */

	public void setestadoInt(Integer Estado) {
		this.Estado = Estado;
	}

	/**
	 * @return the Estado
	 */
	public Integer getestadoInt() {
		return Estado;
	}

	/**
	 * @param idPCompra the idPCompra to set
	 */

	public void setidPCompra(Long idPCompra) {
		this.idPCompra = idPCompra;
	}

	/**
	 * @return the idPCompra
	 */
	public Long getidPCompra() {
		return idPCompra;
	}

	/**
	 * @return the valorAdicional
	 */
	public String getValorAdicional() {
		return valorAdicional;
	}
	/**
	 * @param valorAdicional the valorAdicional to set
	 */
	public void setValorAdicional(String valorAdicional) {
		this.valorAdicional = valorAdicional;
	}

	/**
	 * @return the partida
	 */
	public Integer getpartida() {
		return partida;
	}
	/**
	 * @param partida the partida to set
	 */
	public void setpartidal(Integer partida) {
		this.partida = partida;
	}


	/**
	 * @return the empresaCompra
	 */
	public String getempresaCompra() {
		return empresaCompra;
	}
	/**
	 * @param empresaCompra the empresaCompra to set
	 */
	public void setpartidal(String empresaCompra) {
		this.empresaCompra = empresaCompra;
	}

	/*Envío de documentos*/

	private int destino;
	private String origen;
	private String folioDocumento;


	public String getFolioDocumento() {
		return folioDocumento;
	}

	public void setFolioDocumento(String folioDocumento) {
		this.folioDocumento = folioDocumento;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public int getDestino() {
		return destino;
	}

	public void setDestino(int destino) {
		this.destino = destino;
	}

	public void setEstado(Integer estado) {
		Estado = estado;
	}


	public ArchivosFacturaCliente[] getArchivosClientes() {
		return archivosClientes;
	}

	public void setArchivosClientes(ArchivosFacturaCliente[] archivosClientes) {
		this.archivosClientes = archivosClientes;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public String getnombreArchivo() {
		return this.nombreArchivo;
	}
	/////****Cotizacion****/////
	private String folio;
	/**
	 * @return the folioCotizacion
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param empresaCompra the empresaCompra to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}


	///Consulta Cotización/////////
	private Cotizacion cotizacion;

	private Long idEmpleado;
	private Long requisicion;
	private Long idpcotiza;
	/**
	 * @return the cotizacion
	 */
	public Cotizacion getCotizacion() {
		return cotizacion;
	}
	/**
	 * @param set Cotizacion
	 */
	public void setFolio(Cotizacion cotizacion) {
		this.cotizacion = cotizacion;
	}

	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	/**
	 * @param set idEmpleado
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}


	/**
	 * @return the Requesicion
	 */
	public Long getRequesicion() {
		return requisicion;
	}
	/**
	 * @param set Requesicion
	 */
	public void setRequisicion(Long requisicion) {
		this.requisicion = requisicion;
	}


	/**
	 * @return the idPCotiza
	 */
	public Long getIdpcotiza() {
		return idpcotiza;
	}
	/**
	 * @param set idPCotiza
	 */
	public void setIdpcotiza(Long idPCotiza) {
		this.idpcotiza = idPCotiza;
	}



	//Seguimeinto////

	private int confirmacion;

	private String marca;
	private String control;
	private int master;
	private String empleadoString;
	public int getConfirmacion() {
		return confirmacion;
	}

	public void setConfirmacion(int confirmacion) {
		this.confirmacion = confirmacion;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getControl() {
		return control;
	}

	public void setControl(String control) {
		this.control = control;
	}

	public int getMaster() {
		return master;
	}

	public void setMaster(int master) {
		this.master = master;
	}

	public String getEmpleadoString() {
		return empleadoString;
	}

	public void setEmpleadoString(String empleadoString) {
		this.empleadoString = empleadoString;
	}

	/*Documentos recibidos*/
	private String referencia;
	private String destinatario;
	private String empresa;
	private String abiertoCerrado;

	private List<NivelIngreso> niveles;



	public List<NivelIngreso> getNiveles() {
		return niveles;
	}
	/**
	 * @param niveles the niveles to set
	 */
	public void setNiveles(List<NivelIngreso> niveles) {
		this.niveles = niveles;
	}


	public String getAbiertoCerrado() {
		return abiertoCerrado;
	}

	public void setAbiertoCerrado(String abiertoCerrado) {
		this.abiertoCerrado = abiertoCerrado;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/*Documentos recibidos*/
	private Boolean porFolio;

	public Boolean getPorFolio() {
		return porFolio;
	}

	public void setPorFolio(Boolean porFolio) {
		this.porFolio = porFolio;
	}
	/*Entregas*/
	private String mensajero;
	private String ruta;
	private String conforme;
	private String idPD; 
	private Boolean individual;
	public String getMensajero() {
		return mensajero;
	}

	public void setMensajero(String mensajero) {
		this.mensajero = mensajero;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getConforme() {
		return conforme;
	}

	public void setConforme(String confrome) {
		this.conforme = confrome;
	}

	public String getIdPD() {
		return idPD;
	}

	public void setIdPD(String idPD) {
		this.idPD = idPD;
	}

	public Boolean getIndividual() {
		return individual;
	}

	public void setIndividual(Boolean individual) {
		this.individual = individual;
	}
	
/*Para Consulta Permiso Importacion*/
	
//	private Date finicio;
//	private Date ffin;
//	private Long proveedor;
	private String catalogo; 
	private String tipoPermiso; 
	private String tipoProducto; 
	private String subTipo; 
	private String clasificacion;
//	private String control;
	private String estadoFisico;
//	private String estado;
	private Long idProducto;

	public String getCatalogo() {
		return catalogo;
	}

	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	
	public String getTipoPermiso() {
		return tipoPermiso;
	}

	public void setTipoPermiso(String tipoPermiso) {
		this.tipoPermiso = tipoPermiso;
	}
	
	public String getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	
	public String getSubTipo() {
		return subTipo;
	}

	public void setSubTipo(String subTipo) {
		this.subTipo = subTipo;
	}
	
	public String getClasificacion() {
		return clasificacion;
	}

	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	public String getEstadoFisico() {
		return estadoFisico;
	}

	public void setEstadoFisico(String estadoFisico) {
		this.estadoFisico = estadoFisico;
	}
	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	/* Mensajero */
	PendientesMensajero pendiente;
	List<PendientesMensajero> pendientes;
	List<PersonalAlmacenCliente> personal;
	List<ComentaiosRutaDP> comentaiosRutaDP;
	
	String evidencia;
	List<String> fotos;
	

	public String getEvidencia() {
		return evidencia;
	}

	public void setEvidencia(String evidencia) {
		this.evidencia = evidencia;
	}

	public List<String> getFotos() {
		return fotos;
	}

	public void setFotos(List<String> fotos) {
		this.fotos = fotos;
	}

	public PendientesMensajero getPendiente() {
		return pendiente;
	}

	public void setPendiente(PendientesMensajero pendiente) {
		this.pendiente = pendiente;
	}

	public List<PendientesMensajero> getPendientes() {
		return pendientes;
	}
	

	public void setPendientes(List<PendientesMensajero> pendientes) {
		this.pendientes = pendientes;
	}
	
	public List<ComentaiosRutaDP> getComentaiosRutaDP() {
		return comentaiosRutaDP;
	}
	

	public void setComentaiosRutaDP(List<ComentaiosRutaDP> comentaiosRutaDP) {
		this.comentaiosRutaDP =comentaiosRutaDP;
	}

	public List<PersonalAlmacenCliente> getPersonal() {
		return personal;
	}

	public void setPersonal(List<PersonalAlmacenCliente> personal) {
		this.personal = personal;
	}
	/* Nota de Credito */
	private Long folioNota;

	public Long getFolioNota() {
		return folioNota;
	}

	public void setNota(Long folioNota) {
		this.folioNota = folioNota;
	}


	/*Para App de Vendedor*/

	private VisitaCliente visitaCliente;
	private Direccion direccion;
	public VisitaCliente getVisitaCliente() {
		return visitaCliente;
	}

	public void setVisitaCliente(VisitaCliente visitaCliente) {
		this.visitaCliente = visitaCliente;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	/* Visitas */
	private Integer idVisita;
	private Integer generada;
	
	public Integer getIdVisita() {
		return idVisita;
	}
	
	public void setIdVisita(Integer idVisita) {
		this.idVisita = idVisita;
	}
	
	public Integer getGenerada() {
		return generada;
	}
	
	public void setGenerada(Integer generada) {
		this.generada = generada;
	}

	/*ReportarVisita */
	private String nivelCliente;
	
	public String getNivelCliente() {
		return nivelCliente;
	}

	public void setNivelCliente(String nivelCliente) {
		this.nivelCliente = nivelCliente;
	}

	/**
	 * @return the estadoPedido
	 */
	public String getEstadoPedido() {
		return estadoPedido;
	}

	/**
	 * @param estadoPedido the estadoPedido to set
	 */
	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	/**
	 * @return the idsPPedidos
	 */
	public List<Integer> getIdsPPedidos() {
		return idsPPedidos;
	}

	/**
	 * @param idsPPedidos the idsPPedidos to set
	 */
	public void setIdsPPedidos(List<Integer> idsPPedidos) {
		this.idsPPedidos = idsPPedidos;
	}

	/**
	 * @return the idFactura
	 */
	public Long getIdFactura() {
		return idFactura;
	}

	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}

	public Integer getIdUsuario2() {
		return idUsuario2;
	}

	public void setIdUsuario2(Integer idUsuario2) {
		this.idUsuario2 = idUsuario2;
	}

	/**
	 * @return the idPackingList
	 */
	public Integer getIdPackingList() {
		return idPackingList;
	}

	/**
	 * @param idPackingList the idPackingList to set
	 */
	public void setIdPackingList(Integer idPackingList) {
		this.idPackingList = idPackingList;
	}

	/**
	 * @return the idHorario
	 */
	public Integer getIdHorario() {
		return idHorario;
	}

	/**
	 * @param idHorario the idHorario to set
	 */
	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	/**
	 * @return the generGuia
	 */
	public boolean isGenerGuia() {
		return generGuia;
	}

	/**
	 * @param generGuia the generGuia to set
	 */
	public void setGenerGuia(boolean generGuia) {
		this.generGuia = generGuia;
	}

	/* Mensja*/
	
	/*---------------------------------------------------*/
	private Integer idEmpresa;
	private Integer idTrabajadorNomina;
	private Integer idPais;
	private Integer codigoPostal;
	private Integer idCuenta;
	private Integer idPoliza;
	private Integer tipoPDF;
	private Date fechaInicial;
	private Date fechaFinal;
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}

	public Integer getIdTrabajadorNomina() {
		return idTrabajadorNomina;
	}

	public void setIdTrabajadorNomina(Integer idTrabajadorNomina) {
		this.idTrabajadorNomina = idTrabajadorNomina;
	}

	public Integer getIdPais() {
		return idPais;
	}

	public void setIdPais(Integer idPais) {
		this.idPais = idPais;
	}

	public Integer getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(Integer codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public Integer getIdCuenta() {
		return idCuenta;
	}

	public void setIdCuenta(Integer idCuenta) {
		this.idCuenta = idCuenta;
	}

	public Integer getIdPoliza() {
		return idPoliza;
	}

	public void setIdPoliza(Integer idPoliza) {
		this.idPoliza = idPoliza;
	}

	public Integer getTipoPDF() {
		return tipoPDF;
	}

	public void setTipoPDF(Integer tipoPDF) {
		this.tipoPDF = tipoPDF;
	}

	public Date getFechaInicial() {
		return fechaInicial;
	}

	public void setFechaInicial(Date fechaInicial) {
		this.fechaInicial = fechaInicial;
	}

	public Date getFechaFinal() {
		return fechaFinal;
	}

	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}

	/**
	 * @return the envio
	 */
	public ParametroEnvio getEnvio() {
		return envio;
	}

	/**
	 * @param envio the envio to set
	 */
	public void setEnvio(ParametroEnvio envio) {
		this.envio = envio;
	}

	/**
	 * @return the idPedido
	 */
	public Integer getIdPedido() {
		return idPedido;
	}

	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}

	/**
	 * @return the tabla
	 */
	public String getTabla() {
		return tabla;
	}

	/**
	 * @param tabla the tabla to set
	 */
	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

	/**
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}



