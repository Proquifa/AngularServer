package com.proquifa.net.modelo.ventas;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;

public class Cotizacion {

	private Long idCotizacion;
	private String folioCotizacion;
	private Date fecha;
	private String nombreCliente;
	private String contacto;
	private String vendedor;
	private String vigencia;
	private String moneda;
	private String parciales;
	private String cpago;
	private String lugar;
	private String zona;
	private String estado;
	private Date fEnvio;
	private String observa;
	private String imoneda;
	private String cotizo;
	private String factura;
	private String hEntrada;
	private String mEntrada;
	private String hSalida;
	private String mSalida;
	private String confirmo;
	private String observaC;
	private Date fechaClasificacion;
	private Long idContacto;
	private String canceladaDesde;
	private Boolean infoFacturacion;
	private Date fechaCierre;
	private Boolean abierto;
	private Double montoCotiza;
	private Double montoDLSCotiza;
	
	//Extras
	private Long requisicion;
	private Date fechaOrigen;
	private Date fechaRegistro;
	private String pendienteOrigen;
	private Boolean enTiempoFueraDeTiempo;
	private Integer partidas99;
	private Integer partidas0;
	private String medio;
	private Integer fOrigen;
	private Long totalClientes;
	private Long totalPartidas;
	private Long totalMarcas;
	private Long totalPiezas;
	
	/***/
	private List<PartidaCotizacion> partidas;
	private Contacto contactos;
	private Cliente cliente;
	private Integer doctoR;
	private boolean fuersaSistema;
	private boolean gravaIva;
	
	
	private boolean generada;
	private Long idVisita;
	private String nombreCotizacion;
	private boolean deSistema;
	private Long numCotizacion;
	private String tipoCotizacion;
	private int numPartidas;
	
	public Cotizacion(Cliente cliente) {
		super();
		this.cliente = cliente;
		this.nombreCliente = cliente.getNombre();
		this.vendedor = cliente.getVendedor();
		this.parciales = cliente.getParciales();
		this.cpago = cliente.getCondicionesPago();
		this.zona = cliente.getZona();
		this.imoneda = cliente.getMonedaFactura();
		this.cotizo = cliente.getVendedor();
		this.factura = cliente.getEmpresaFactura();
		this.fecha = new Date();
		this.moneda = "Dolares";
		this.estado = "Cotizada";
		this.fEnvio = new Date();
		this.mEntrada = this.mSalida = "C";
		this.fechaClasificacion = new Date();
	}
	/**
	 * 
	 */
	public Cotizacion() {
		super();
	}
	/**
	 * @return the idCotizacion
	 */
	public Long getIdCotizacion() {
		return idCotizacion;
	}
	/**
	 * @param idCotizacion the idCotizacion to set
	 */
	public void setIdCotizacion(Long idCotizacion) {
		this.idCotizacion = idCotizacion;
	}
	/**
	 * @return the folioCotizacion
	 */
	public String getFolioCotizacion() {
		return folioCotizacion;
	}
	/**
	 * @param folioCotizacion the folioCotizacion to set
	 */
	public void setFolioCotizacion(String folioCotizacion) {
		this.folioCotizacion = folioCotizacion;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}
	/**
	 * @param nombreCliente the nombreCliente to set
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
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
	 * @return the vendedor
	 */
	public String getVendedor() {
		return vendedor;
	}
	/**
	 * @param vendedor the vendedor to set
	 */
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	/**
	 * @return the vigencia
	 */
	public String getVigencia() {
		return vigencia;
	}
	/**
	 * @param vigencia the vigencia to set
	 */
	public void setVigencia(String vigencia) {
		this.vigencia = vigencia;
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
	 * @return the parciales
	 */
	public String getParciales() {
		return parciales;
	}
	/**
	 * @param parciales the parciales to set
	 */
	public void setParciales(String parciales) {
		this.parciales = parciales;
	}
	/**
	 * @return the cpago
	 */
	public String getCpago() {
		return cpago;
	}
	/**
	 * @param cpago the cpago to set
	 */
	public void setCpago(String cpago) {
		this.cpago = cpago;
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
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}
	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
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
	 * @return the fEnvio
	 */
	public Date getFEnvio() {
		return fEnvio;
	}
	/**
	 * @param envio the fEnvio to set
	 */
	public void setFEnvio(Date envio) {
		fEnvio = envio;
	}
	/**
	 * @return the observa
	 */
	public String getObserva() {
		return observa;
	}
	/**
	 * @param observa the observa to set
	 */
	public void setObserva(String observa) {
		this.observa = observa;
	}

	/**
	 * @return the cotizo
	 */
	public String getCotizo() {
		return cotizo;
	}
	/**
	 * @param cotizo the cotizo to set
	 */
	public void setCotizo(String cotizo) {
		this.cotizo = cotizo;
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
	 * @return the hEntrada
	 */
	public String getHEntrada() {
		return hEntrada;
	}
	/**
	 * @param entrada the hEntrada to set
	 */
	public void setHEntrada(String entrada) {
		hEntrada = entrada;
	}
	/**
	 * @return the mEntrada
	 */
	public String getMEntrada() {
		return mEntrada;
	}
	/**
	 * @param entrada the mEntrada to set
	 */
	public void setMEntrada(String entrada) {
		mEntrada = entrada;
	}
	/**
	 * @return the hSalida
	 */
	public String getHSalida() {
		return hSalida;
	}
	/**
	 * @param salida the hSalida to set
	 */
	public void setHSalida(String salida) {
		hSalida = salida;
	}
	/**
	 * @return the mSalida
	 */
	public String getMSalida() {
		return mSalida;
	}
	/**
	 * @param salida the mSalida to set
	 */
	public void setMSalida(String salida) {
		mSalida = salida;
	}
	/**
	 * @return the confirmo
	 */
	public String getConfirmo() {
		return confirmo;
	}
	/**
	 * @param confirmo the confirmo to set
	 */
	public void setConfirmo(String confirmo) {
		this.confirmo = confirmo;
	}
	/**
	 * @return the observaC
	 */
	public String getObservaC() {
		return observaC;
	}
	/**
	 * @param observaC the observaC to set
	 */
	public void setObservaC(String observaC) {
		this.observaC = observaC;
	}
	/**
	 * @return the fechaClasificacion
	 */
	public Date getFechaClasificacion() {
		return fechaClasificacion;
	}
	/**
	 * @param fechaClasificacion the fechaClasificacion to set
	 */
	public void setFechaClasificacion(Date fechaClasificacion) {
		this.fechaClasificacion = fechaClasificacion;
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
	 * @return the canceladaDesde
	 */
	public String getCanceladaDesde() {
		return canceladaDesde;
	}
	/**
	 * @param canceladaDesde the canceladaDesde to set
	 */
	public void setCanceladaDesde(String canceladaDesde) {
		this.canceladaDesde = canceladaDesde;
	}
	/**
	 * @return the infoFacturacion
	 */
	public Boolean getInfoFacturacion() {
		return infoFacturacion;
	}
	/**
	 * @param infoFacturacion the infoFacturacion to set
	 */
	public void setInfoFacturacion(Boolean infoFacturacion) {
		this.infoFacturacion = infoFacturacion;
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
	 * @return the abierto
	 */
	public Boolean getAbierto() {
		return abierto;
	}
	/**
	 * @param abierto the abierto to set
	 */
	public void setAbierto(Boolean abierto) {
		this.abierto = abierto;
	}
	/**
	 * @return the montoCotiza
	 */
	public Double getMontoCotiza() {
		return montoCotiza;
	}
	/**
	 * @param montoCotiza the montoCotiza to set
	 */
	public void setMontoCotiza(Double montoCotiza) {
		this.montoCotiza = montoCotiza;
	}
	/**
	 * @return the montoDLSCotiza
	 */
	public Double getMontoDLSCotiza() {
		return montoDLSCotiza;
	}
	/**
	 * @param montoDLSCotiza the montoDLSCotiza to set
	 */
	public void setMontoDLSCotiza(Double montoDLSCotiza) {
		this.montoDLSCotiza = montoDLSCotiza;
	}
	/**
	 * @return the requisicion
	 */
	public Long getRequisicion() {
		return requisicion;
	}
	/**
	 * @param requisicion the requisicion to set
	 */
	public void setRequisicion(Long requisicion) {
		this.requisicion = requisicion;
	}
	/**
	 * @return the fechaOrigen
	 */
	public Date getFechaOrigen() {
		return fechaOrigen;
	}
	/**
	 * @param fechaOrigen the fechaOrigen to set
	 */
	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}
	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	/**
	 * @return the pendienteOrigen
	 */
	public String getPendienteOrigen() {
		return pendienteOrigen;
	}
	/**
	 * @param pendienteOrigen the pendienteOrigen to set
	 */
	public void setPendienteOrigen(String pendienteOrigen) {
		this.pendienteOrigen = pendienteOrigen;
	}
	/**
	 * @return the enTiempoFueraDeTiempo
	 */
	public Boolean getEnTiempoFueraDeTiempo() {
		return enTiempoFueraDeTiempo;
	}
	/**
	 * @param enTiempoFueraDeTiempo the enTiempoFueraDeTiempo to set
	 */
	public void setEnTiempoFueraDeTiempo(Boolean enTiempoFueraDeTiempo) {
		this.enTiempoFueraDeTiempo = enTiempoFueraDeTiempo;
	}
	/**
	 * @return the partidas99
	 */
	public Integer getPartidas99() {
		return partidas99;
	}
	/**
	 * @param partidas99 the partidas99 to set
	 */
	public void setPartidas99(Integer partidas99) {
		this.partidas99 = partidas99;
	}
	/**
	 * @return the partidas0
	 */
	public Integer getPartidas0() {
		return partidas0;
	}
	/**
	 * @param partidas0 the partidas0 to set
	 */
	public void setPartidas0(Integer partidas0) {
		this.partidas0 = partidas0;
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
	 * @return the fOrigen
	 */
	public Integer getFOrigen() {
		return fOrigen;
	}
	/**
	 * @param origen the fOrigen to set
	 */
	public void setFOrigen(Integer origen) {
		fOrigen = origen;
	}
	public void setTotalClientes(Long totalClientes) {
		this.totalClientes = totalClientes;
	}
	public Long getTotalClientes() {
		return totalClientes;
	}
	public void setTotalPartidas(Long totalPartidas) {
		this.totalPartidas = totalPartidas;
	}
	public Long getTotalPartidas() {
		return totalPartidas;
	}
	public void setTotalMarcas(Long totalMarcas) {
		this.totalMarcas = totalMarcas;
	}
	public Long getTotalMarcas() {
		return totalMarcas;
	}
	public void setTotalPiezas(Long totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	public Long getTotalPiezas() {
		return totalPiezas;
	}
	/**
	 * @return the partidas
	 */
	public List<PartidaCotizacion> getPartidas() {
		return partidas;
	}
	/**
	 * @param partidas the partidas to set
	 */
	public void setPartidas(List<PartidaCotizacion> partidas) {
		this.partidas = partidas;
	}
	/**
	 * @return the contactos
	 */
	public Contacto getContactos() {
		return contactos;
	}
	/**
	 * @param contactos the contactos to set
	 */
	public void setContactos(Contacto contactos) {
		this.contactos = contactos;
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
	 * @return the doctoR
	 */
	public Integer getDoctoR() {
		return doctoR;
	}
	/**
	 * @param doctoR the doctoR to set
	 */
	public void setDoctoR(Integer doctoR) {
		this.doctoR = doctoR;
	}
	/**
	 * @return the fuersaSistema
	 */
	public boolean isFuersaSistema() {
		return fuersaSistema;
	}
	/**
	 * @param fuersaSistema the fuersaSistema to set
	 */
	public void setFuersaSistema(boolean fuersaSistema) {
		this.fuersaSistema = fuersaSistema;
	}
	/**
	 * @return the gravaIva
	 */
	public boolean isGravaIva() {
		return gravaIva;
	}
	/**
	 * @param gravaIva the gravaIva to set
	 */
	public void setGravaIva(boolean gravaIva) {
		this.gravaIva = gravaIva;
	}
	public boolean isGenerada() {
		return generada;
	}
	public void setGenerada(boolean generada) {
		this.generada = generada;
	}
	public Long getIdVisita() {
		return idVisita;
	}
	public void setIdVisita(Long idVisita) {
		this.idVisita = idVisita;
	}
	public String getNombreCotizacion() {
		return nombreCotizacion;
	}
	public void setNombreCotizacion(String nombreCotizacion) {
		this.nombreCotizacion = nombreCotizacion;
	}
	public boolean isDeSistema() {
		return deSistema;
	}
	public void setDeSistema(boolean deSistema) {
		this.deSistema = deSistema;
	}
	public Long getNumCotizacion() {
		return numCotizacion;
	}
	public void setNumCotizacion(Long numCotizacion) {
		this.numCotizacion = numCotizacion;
	}
	public String getTipoCotizacion() {
		return tipoCotizacion;
	}
	public void setTipoCotizacion(String tipoCotizacion) {
		this.tipoCotizacion = tipoCotizacion;
	}
	public int getNumPartidas() {
		return numPartidas;
	}
	public void setNumPartidas(int numPartidas) {
		this.numPartidas = numPartidas;
	}
	public String getImoneda() {
		return imoneda;
	}
	public void setImoneda(String imoneda) {
		this.imoneda = imoneda;
	}


}