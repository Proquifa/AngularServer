package com.proquifa.net.modelo.ventas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.Producto;


public class PartidaPedido extends Producto{
	
	private Long idPPedido;	
	private Long idPedido;
	private String cpedido;
	private Integer part;
	private Long clave;
	private Integer cantidadPedida;
	private Double precio;
	private Double precioUnitarioDLS;
	private Long provee;
	private Date fFactura;
	private Long factura;
	private String fpor;
	private Date fRecibido;
	private String contacto;
	private String idGuia;
	private String guia;
	private Integer faltan;
	private Date fPEntrega;
	private String comenta;
	private String pedLote;
	private Boolean faltaPago;
	private Boolean stock;
	private Long idComplemento;	
	private String trafico;
	private String caducidad;
	private String mesCaducidad;
	private String anoCaducidad;
	private String lote;
	private String edicion;
	private String pedimento;
	private Boolean reasignada;
	private Boolean bloqueadaAC;
	private String ac;
	private String oc;
	private String dc;
	private String cotiza;
	private Integer noReubicaciones;
	private String idEvento;
	private String idEntrega;
	private String idRuta;
	private String mensajeria;
	private String responsable;
	private String decision;	
	private String tipoFlete;
	private Date fEntrega;	
	private Date fRealizacion;
	private Date fEnvio;
	private Date fer;
	private String conforme;
	private List<PartidaCompra> ordenesCompra;	
	private String ruta;
	private Date rutaFFin;
	private Date fechaOrigen;
	private String tiempoProceso;
	private String respuesta;
	private Pedido pedido;
	private Boolean entregaEnTiempo;
	private Integer diasEnAlmacen;	
	private String folioFD;
	private String destinos;
	
	private String nombreVendedor;
	private boolean compraLinea; 
	
	private String diasFiltro;
	private Long diferencial;
	
	private Date fea;
	private boolean avisoCambio; // Indica si tiene aviso de cambio
	
	private String nombreProveedor;
	private int cant_piezas;
	private float monto;
	private int cant_productos;
	private int cant_programados;
	private int cant_regular;
	private int cant_fleteExpress;
	private int id_clientes;
	private String nombreCliente;
	private int enStock;
	private int cantStockTransito;
	
	private double montoUnitario;
	private double flete;
	private String agenteAduanal;
	
	private Long ordenDespacho;
	private float VAD;
	private float TIC;
	private float PRV;
	private float CNT;
	private float VAL;
	private List<PartidaPedido> suplementos;	
	private long totalStock;
	
	//propiedades de partidas en Stock
	private List<PartidaPedido> productosEnStock;	
	private long idPCompra;
	private String inspector;
	private String disponibilidadPedimento;
	private int cantidadDisponibleSTOCK;
	
	private int remisionar;
	private Integer lineaOrden;
	private String unidadMedida;
	
	/**
	 * @return the ac
	 */
	public String getAc() {
		return ac;
	}
	/**
	 * @return the anoCaducidad
	 */
	public String getAnoCaducidad() {
		return anoCaducidad;
	}
	/**
	 * @return the avisoCambio
	 */
	public boolean getAvisoCambio() {
		return avisoCambio;
	}
	/**
	 * @return the bloqueadaAC
	 */
	public Boolean getBloqueadaAC() {
		return bloqueadaAC;
	}
	/**
	 * @return the caducidad
	 */
	public String getCaducidad() {
		return caducidad;
	}
	/**
	 * @return the cantidadPedida
	 */
	public Integer getCantidadPedida() {
		return cantidadPedida;
	}
	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}
	/**
	 * @return the comenta
	 */
	public String getComenta() {
		return comenta;
	}
	/**
	 * @return the conforme
	 */
	public String getConforme() {
		return conforme;
	}
	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	/**
	 * @return the cotiza
	 */
	public String getCotiza() {
		return cotiza;
	}
	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}
	/**
	 * @return the dc
	 */
	public String getDc() {
		return dc;
	}
	/**
	 * @return the decision
	 */
	public String getDecision() {
		return decision;
	}
	/**
	 * @return the destinos
	 */
	public String getDestinos() {
		return destinos;
	}
	/**
	 * @return the diasEnAlmacen
	 */
	public Integer getDiasEnAlmacen() {
		return diasEnAlmacen;
	}
	public String getDiasFiltro() {
		return diasFiltro;
	}
	public Long getDiferencial() {
		return diferencial;
	}
	/**
	 * @return the edicion
	 */
	public String getEdicion() {
		return edicion;
	}
	/**
	 * @return the entregaEnTiempo
	 */
	public Boolean getEntregaEnTiempo() {
		return entregaEnTiempo;
	}
	/**
	 * @return the factura
	 */
	public Long getFactura() {
		return factura;
	}
	/**
	 * @return the faltan
	 */
	public Integer getFaltan() {
		return faltan;
	}
	/**
	 * @return the faltaPago
	 */
	public Boolean getFaltaPago() {
		return faltaPago;
	}
	/**
	 * @return the fea
	 */
	public Date getFea() {
		return fea;
	}
	/**
	 * @return the fechaOrigen
	 */
	public Date getFechaOrigen() {
		return fechaOrigen;
	}
	/**
	 * @return the fEntrega
	 */
	public Date getFEntrega() {
		return fEntrega;
	}
	/**
	 * @return the fEnvio
	 */
	public Date getFEnvio() {
		return fEnvio;
	}
	/**
	 * @return the fer
	 */
	public Date getFer() {
		return fer;
	}
	/**
	 * @return the fFactura
	 */
	public Date getFFactura() {
		return fFactura;
	}
	/**
	 * @return the folioFD
	 */
	public String getFolioFD() {
		return folioFD;
	}
	/**
	 * @return the fPEntrega
	 */
	public Date getFPEntrega() {
		return fPEntrega;
	}
	/**
	 * @return the fpor
	 */
	public String getFpor() {
		return fpor;
	}
	/**
	 * @return the fRealizacion
	 */
	public Date getFRealizacion() {
		return fRealizacion;
	}
	/**
	 * @return the fRecibido
	 */
	public Date getFRecibido() {
		return fRecibido;
	}
	/**
	 * @return the guia
	 */
	public String getGuia() {
		return guia;
	}
	/**
	 * @return the idComplemento
	 */
	public Long getIdComplemento() {
		return idComplemento;
	}
	/**
	 * @return the idEntrega
	 */
	public String getIdEntrega() {
		return idEntrega;
	}
	/**
	 * @return the idEvento
	 */
	public String getIdEvento() {
		return idEvento;
	}
	/**
	 * @return the idGuia
	 */
	public String getIdGuia() {
		return idGuia;
	}
	/**
	 * @return the idPedido
	 */
	public Long getIdPedido() {
		return idPedido;
	}
	/**
	 * @return the idPPedido
	 */
	public Long getIdPPedido() {
		return idPPedido;
	}
	/**
	 * @return the idRuta
	 */
	public String getIdRuta() {
		return idRuta;
	}
	/**
	 * @return the lote
	 */
	public String getLote() {
		return lote;
	}
	/**
	 * @return the mensajeria
	 */
	public String getMensajeria() {
		return mensajeria;
	}
	/**
	 * @return the mesCaducidad
	 */
	public String getMesCaducidad() {
		return mesCaducidad;
	}
	/**
	 * @return the noReubicaciones
	 */
	public Integer getNoReubicaciones() {
		return noReubicaciones;
	}
	/**
	 * @return the oc
	 */
	public String getOc() {
		return oc;
	}
	/**
	 * @return the ordenesCompra
	 */
	public List<PartidaCompra> getOrdenesCompra() {
		return ordenesCompra;
	}
	/**
	 * @return the part
	 */
	public Integer getPart() {
		return part;
	}
	/**
	 * @return the pedido
	 */
	public Pedido getPedido() {
		return pedido;
	}
	/**
	 * @return the pedimento
	 */
	public String getPedimento() {
		return pedimento;
	}
	/**
	 * @return the pedLote
	 */
	public String getPedLote() {
		return pedLote;
	}
	/**
	 * @return the precio
	 */
	public Double getPrecio() {
		return precio;
	}
	/**
	 * @return the precioUnitarioDLS
	 */
	public Double getPrecioUnitarioDLS() {
		return precioUnitarioDLS;
	}
	
	/**
	 * @return the reasignada
	 */
	public Boolean getReasignada() {
		return reasignada;
	}
	/**
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}
	/**
	 * @return the respuesta
	 */
	public String getRespuesta() {
		return respuesta;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @return the rutaFFin
	 */
	public Date getRutaFFin() {
		return rutaFFin;
	}
	/**
	 * @return the stock
	 */
	public Boolean getStock() {
		return stock;
	}
	/**
	 * @return the tiempoProceso
	 */
	public String getTiempoProceso() {
		return tiempoProceso;
	}
	/**
	 * @return the tipoFlete
	 */
	public String getTipoFlete() {
		return tipoFlete;
	}
	/**
	 * @return the trafico
	 */
	public String getTrafico() {
		return trafico;
	}
	/**
	 * @param ac the ac to set
	 */
	public void setAc(String ac) {
		this.ac = ac;
	}
	/**
	 * @param anoCaducidad the anoCaducidad to set
	 */
	public void setAnoCaducidad(String anoCaducidad) {
		this.anoCaducidad = anoCaducidad;
	}
	/**
	 * @param avisoCambio the avisoCambio to set
	 */
	public void setAvisoCambio(boolean avisoCambio) {
		this.avisoCambio = avisoCambio;
	}
	/**
	 * @param bloqueadaAC the bloqueadaAC to set
	 */
	public void setBloqueadaAC(Boolean bloqueadaAC) {
		this.bloqueadaAC = bloqueadaAC;
	}
	/**
	 * @param caducidad the caducidad to set
	 */
	public void setCaducidad(String caducidad) {
		this.caducidad = caducidad;
	}
	/**
	 * @param cantidadPedida the cantidadPedida to set
	 */
	public void setCantidadPedida(Integer cantidadPedida) {
		this.cantidadPedida = cantidadPedida;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}
	/**
	 * @param comenta the comenta to set
	 */
	public void setComenta(String comenta) {
		this.comenta = comenta;
	}
	/**
	 * @param conforme the conforme to set
	 */
	public void setConforme(String conforme) {
		this.conforme = conforme;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	/**
	 * @param cotiza the cotiza to set
	 */
	public void setCotiza(String cotiza) {
		this.cotiza = cotiza;
	}
	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}
	/**
	 * @param dc the dc to set
	 */
	public void setDc(String dc) {
		this.dc = dc;
	}
	/**
	 * @param decision the decision to set
	 */
	public void setDecision(String decision) {
		this.decision = decision;
	}
	/**
	 * @param destinos the destinos to set
	 */
	public void setDestinos(String destinos) {
		this.destinos = destinos;
	}
	/**
	 * @param diasEnAlmacen the diasEnAlmacen to set
	 */
	public void setDiasEnAlmacen(Integer diasEnAlmacen) {
		this.diasEnAlmacen = diasEnAlmacen;
	}
	public void setDiasFiltro(String diasFiltro) {
		this.diasFiltro = diasFiltro;
	}
	public void setDiferencial(Long diferencial) {
		this.diferencial = diferencial;
	}
	/**
	 * @param edicion the edicion to set
	 */
	public void setEdicion(String edicion) {
		this.edicion = edicion;
	}
	/**
	 * @param entregaEnTiempo the entregaEnTiempo to set
	 */
	public void setEntregaEnTiempo(Boolean entregaEnTiempo) {
		this.entregaEnTiempo = entregaEnTiempo;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(Long factura) {
		this.factura = factura;
	}
	/**
	 * @param faltan the faltan to set
	 */
	public void setFaltan(Integer faltan) {
		this.faltan = faltan;
	}
	/**
	 * @param faltaPago the faltaPago to set
	 */
	public void setFaltaPago(Boolean faltaPago) {
		this.faltaPago = faltaPago;
	}
	/**
	 * @param fea the fea to set
	 */
	public void setFea(Date fea) {
		this.fea = fea;
	}
	/**
	 * @param fechaOrigen the fechaOrigen to set
	 */
	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}
	/**
	 * @param entrega the fEntrega to set
	 */
	public void setFEntrega(Date entrega) {
		fEntrega = entrega;
	}
	/**
	 * @param envio the fEnvio to set
	 */
	public void setFEnvio(Date envio) {
		fEnvio = envio;
	}
	/**
	 * @param fer the fer to set
	 */
	public void setFer(Date fer) {
		this.fer = fer;
	}
	/**
	 * @param factura the fFactura to set
	 */
	public void setFFactura(Date factura) {
		fFactura = factura;
	}
	/**
	 * @param folioFD the folioFD to set
	 */
	public void setFolioFD(String folioFD) {
		this.folioFD = folioFD;
	}
	/**
	 * @param entrega the fPEntrega to set
	 */
	public void setFPEntrega(Date entrega) {
		fPEntrega = entrega;
	}
	/**
	 * @param fpor the fpor to set
	 */
	public void setFpor(String fpor) {
		this.fpor = fpor;
	}
	/**
	 * @param realizacion the fRealizacion to set
	 */
	public void setFRealizacion(Date realizacion) {
		fRealizacion = realizacion;
	}
	/**
	 * @param recibido the fRecibido to set
	 */
	public void setFRecibido(Date recibido) {
		fRecibido = recibido;
	}
	/**
	 * @param guia the guia to set
	 */
	public void setGuia(String guia) {
		this.guia = guia;
	}
	/**
	 * @param idComplemento the idComplemento to set
	 */
	public void setIdComplemento(Long idComplemento) {
		this.idComplemento = idComplemento;
	}
	/**
	 * @param idEntrega the idEntrega to set
	 */
	public void setIdEntrega(String idEntrega) {
		this.idEntrega = idEntrega;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
	}
	/**
	 * @param idGuia the idGuia to set
	 */
	public void setIdGuia(String idGuia) {
		this.idGuia = idGuia;
	}
	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	/**
	 * @param idPPedido the idPPedido to set
	 */
	public void setIdPPedido(Long idPPedido) {
		this.idPPedido = idPPedido;
	}
	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	/**
	 * @param lote the lote to set
	 */
	public void setLote(String lote) {
		this.lote = lote;
	}
	/**
	 * @param mensajeria the mensajeria to set
	 */
	public void setMensajeria(String mensajeria) {
		this.mensajeria = mensajeria;
	}
	/**
	 * @param mesCaducidad the mesCaducidad to set
	 */
	public void setMesCaducidad(String mesCaducidad) {
		this.mesCaducidad = mesCaducidad;
	}
	/**
	 * @param noReubicaciones the noReubicaciones to set
	 */
	public void setNoReubicaciones(Integer noReubicaciones) {
		this.noReubicaciones = noReubicaciones;
	}
	/**
	 * @param oc the oc to set
	 */
	public void setOc(String oc) {
		this.oc = oc;
	}
	/**
	 * @param ordenesCompra the ordenesCompra to set
	 */
	public void setOrdenesCompra(List<PartidaCompra> ordenesCompra) {
		this.ordenesCompra = ordenesCompra;
	}
	/**
	 * @param part the part to set
	 */
	public void setPart(Integer part) {
		this.part = part;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}
	/**
	 * @param pedimento the pedimento to set
	 */
	public void setPedimento(String pedimento) {
		this.pedimento = pedimento;
	}
	/**
	 * @param pedLote the pedLote to set
	 */
	public void setPedLote(String pedLote) {
		this.pedLote = pedLote;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	/**
	 * @param precioUnitarioDLS the precioUnitarioDLS to set
	 */
	public void setPrecioUnitarioDLS(Double precioUnitarioDLS) {
		this.precioUnitarioDLS = precioUnitarioDLS;
	}
	
	/**
	 * @param reasignada the reasignada to set
	 */
	public void setReasignada(Boolean reasignada) {
		this.reasignada = reasignada;
	}
	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	/**
	 * @param respuesta the respuesta to set
	 */
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @param rutaFFin the rutaFFin to set
	 */
	public void setRutaFFin(Date rutaFFin) {
		this.rutaFFin = rutaFFin;
	}
	/**
	 * @param stock the stock to set
	 */
	public void setStock(Boolean stock) {
		this.stock = stock;
	}
	/**
	 * @param tiempoProceso the tiempoProceso to set
	 */
	public void setTiempoProceso(String tiempoProceso) {
		this.tiempoProceso = tiempoProceso;
	}
	/**
	 * @param tipoFlete the tipoFlete to set
	 */
	public void setTipoFlete(String tipoFlete) {
		this.tipoFlete = tipoFlete;
	}
	/**
	 * @param trafico the trafico to set
	 */
	public void setTrafico(String trafico) {
		this.trafico = trafico;
	}
	public Long getProvee() {
		return provee;
	}
	public void setProvee(Long provee) {
		this.provee = provee;
	}
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	public int getCant_piezas() {
		return cant_piezas;
	}
	public void setCant_piezas(int cant_piezas) {
		this.cant_piezas = cant_piezas;
	}
	public float getMonto() {
		return monto;
	}
	public void setMonto(float monto) {
		this.monto = monto;
	}
	public int getCant_productos() {
		return cant_productos;
	}
	public void setCant_productos(int cant_productos) {
		this.cant_productos = cant_productos;
	}
	public int getCant_programados() {
		return cant_programados;
	}
	public void setCant_programados(int cant_programados) {
		this.cant_programados = cant_programados;
	}
	public int getCant_regular() {
		return cant_regular;
	}
	public void setCant_regular(int cant_regular) {
		this.cant_regular = cant_regular;
	}
	public int getCant_fleteExpress() {
		return cant_fleteExpress;
	}
	public void setCant_fleteExpress(int cant_fleteExpress) {
		this.cant_fleteExpress = cant_fleteExpress;
	}
	public int getId_clientes() {
		return id_clientes;
	}
	public void setId_clientes(int id_clientes) {
		this.id_clientes = id_clientes;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public int getEnStock() {
		return enStock;
	}
	public void setEnStock(int enStock) {
		this.enStock = enStock;
	}
	public int getCantStockTransito() {
		return cantStockTransito;
	}
	public void setCantStockTransito(int cantStockTransito) {
		this.cantStockTransito = cantStockTransito;
	}
	public double getMontoUnitario() {
		return montoUnitario;
	}
	public void setMontoUnitario(double montoUnitario) {
		this.montoUnitario = montoUnitario;
	}
	public double getFlete() {
		return flete;
	}
	public void setFlete(double flete) {
		this.flete = flete;
	}
	public String getAgenteAduanal() {
		return agenteAduanal;
	}
	public void setAgenteAduanal(String agenteAduanal) {
		this.agenteAduanal = agenteAduanal;
	}
	public Long getOrdenDespacho() {
		return ordenDespacho;
	}
	public void setOrdenDespacho(Long ordenDespacho) {
		this.ordenDespacho = ordenDespacho;
	}
	public float getVAD() {
		return VAD;
	}
	public void setVAD(float vAD) {
		VAD = vAD;
	}
	public float getTIC() {
		return TIC;
	}
	public void setTIC(float tIC) {
		TIC = tIC;
	}
	public float getPRV() {
		return PRV;
	}
	public void setPRV(float pRV) {
		PRV = pRV;
	}
	public float getCNT() {
		return CNT;
	}
	public void setCNT(float cNT) {
		CNT = cNT;
	}
	public float getVAL() {
		return VAL;
	}
	public void setVAL(float vAL) {
		VAL = vAL;
	}
	/**
	 * @return the compraLinea
	 */
	public boolean isCompraLinea() {
		return compraLinea;
	}
	/**
	 * @param compraLinea the compraLinea to set
	 */
	public void setCompraLinea(boolean compraLinea) {
		this.compraLinea = compraLinea;
	}
	/**
	 * @return the nombreVendedor
	 */
	public String getNombreVendedor() {
		return nombreVendedor;
	}
	/**
	 * @param nombreVendedor the nombreVendedor to set
	 */
	public void setNombreVendedor(String nombreVendedor) {
		this.nombreVendedor = nombreVendedor;
	}
	
	public long getTotalStock() {
		return totalStock;
	}
	public void setTotalStock(long totalStock) {
		this.totalStock = totalStock;
	}
	

	public long getIdPCompra() {
		return idPCompra;
	}
	public void setIdPCompra(long idPCompra) {
		this.idPCompra = idPCompra;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public String getDisponibilidadPedimento() {
		return disponibilidadPedimento;
	}
	public void setDisponibilidadPedimento(String disponibilidadPedimento) {
		this.disponibilidadPedimento = disponibilidadPedimento;
	}
	public int getCantidadDisponibleSTOCK() {
		return cantidadDisponibleSTOCK;
	}
	public void setCantidadDisponibleSTOCK(int cantidadDisponibleSTOCK) {
		this.cantidadDisponibleSTOCK = cantidadDisponibleSTOCK;
	}
	public List<PartidaPedido> getSuplementos() {
		return suplementos;
	}
	public void setSuplementos(List<PartidaPedido> suplementos) {
		this.suplementos = suplementos;
	}
	public List<PartidaPedido> getProductosEnStock() {
		return productosEnStock;
	}
	public void setProductosEnStock(List<PartidaPedido> productosEnStock) {
		this.productosEnStock = productosEnStock;
	}
	/**
	 * @return the remisionar
	 */
	public int getRemisionar() {
		return remisionar;
	}
	/**
	 * @param remisionar the remisionar to set
	 */
	public void setRemisionar(Integer remisionar) {
		this.remisionar = remisionar;
	}
	public Integer getLineaOrden() {
		return lineaOrden;
	}
	public void setLineaOrden(int lineaOrden) {
		this.lineaOrden = lineaOrden;
	}
	public String getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(String unidadMedida) {
		this.unidadMedida = unidadMedida;
	}
}