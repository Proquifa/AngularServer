/**
 * 
 */
package com.proquifa.net.modelo.despachos.mensajero;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author ymendez
 *
 */
public class AsignarMensajero implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4324853275939132993L;
	
	private Integer idAsignarMensajero;
	private Integer idCliente;
	private String cliente;
	private Integer idMensajero;
	private String mensajero;
	private String usuario;
	private String zona;
	private String folio;
	private String calle;
	private String estado;
	private String pais;
	private String dia;
	private Double altitud;
	private Double latitud;
	private Double longitud;
	private String horarioDe;
	private String horarioA;
	private String evento;
	
	private Integer orden;
	private Integer ordenReal;
	private Double monto;
	
	private String ruta;
	
	private String prioridad;
	private Integer totalClientes;
	private Integer totalCerrados;
	private String etiqueta;
	private Integer totalNoEntregado;
	private Integer totalEntregado;
	
	private Integer totalZonas;
	private Integer totalRutas;
	
	private List<String> folios;
	
	private String entrega;
	private String idDP;
	
	private boolean guadalajara;
	
	/*Cerrar Ruta*/
	private Integer idHorario;
	private String fInicio;
	private String fFin;
	private String fCierre;
	
	private String dias;
	private boolean activo;
	private String fee;
	
	/**
	 * 
	 */
	public AsignarMensajero() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idCliente
	 */
	public Integer getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the cliente
	 */
	public String getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the idMensajero
	 */
	public Integer getIdMensajero() {
		return idMensajero;
	}

	/**
	 * @param idMensajero the idMensajero to set
	 */
	public void setIdMensajero(Integer idMensajero) {
		this.idMensajero = idMensajero;
	}

	/**
	 * @return the mensajero
	 */
	public String getMensajero() {
		return mensajero;
	}

	/**
	 * @param mensajero the mensajero to set
	 */
	public void setMensajero(String mensajero) {
		this.mensajero = mensajero;
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
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
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
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}

	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}

	/**
	 * @return the altitud
	 */
	public Double getAltitud() {
		return altitud;
	}

	/**
	 * @param altitud the altitud to set
	 */
	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}

	/**
	 * @return the latitud
	 */
	public Double getLatitud() {
		return latitud;
	}

	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

	/**
	 * @return the longitud
	 */
	public Double getLongitud() {
		return longitud;
	}

	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	/**
	 * @return the horarioDe
	 */
	public String getHorarioDe() {
		return horarioDe;
	}

	/**
	 * @param horarioDe the horarioDe to set
	 */
	public void setHorarioDe(String horarioDe) {
		this.horarioDe = horarioDe;
	}

	/**
	 * @return the horarioA
	 */
	public String getHorarioA() {
		return horarioA;
	}

	/**
	 * @param horarioA the horarioA to set
	 */
	public void setHorarioA(String horarioA) {
		this.horarioA = horarioA;
	}

	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}

	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}

	/**
	 * @return the orden
	 */
	public Integer getOrden() {
		return orden;
	}

	/**
	 * @param orden the orden to set
	 */
	public void setOrden(Integer orden) {
		this.orden = orden;
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
	 * @return the idAsignarMensajero
	 */
	public Integer getIdAsignarMensajero() {
		return idAsignarMensajero;
	}

	/**
	 * @param idAsignarMensajero the idAsignarMensajero to set
	 */
	public void setIdAsignarMensajero(Integer idAsignarMensajero) {
		this.idAsignarMensajero = idAsignarMensajero;
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
	 * @return the totalClientes
	 */
	public Integer getTotalClientes() {
		return totalClientes;
	}

	/**
	 * @param totalClientes the totalClientes to set
	 */
	public void setTotalClientes(Integer totalClientes) {
		this.totalClientes = totalClientes;
	}

	/**
	 * @return the totalCerrados
	 */
	public Integer getTotalCerrados() {
		return totalCerrados;
	}

	/**
	 * @param totalCerrados the totalCerrados to set
	 */
	public void setTotalCerrados(Integer totalCerrados) {
		this.totalCerrados = totalCerrados;
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
	 * @return the prioridad
	 */
	public String getPrioridad() {
		return prioridad;
	}

	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	/**
	 * @return the folios
	 */
	public List<String> getFolios() {
		return folios;
	}

	/**
	 * @param folios the folios to set
	 */
	public void setFolios(List<String> folios) {
		this.folios = folios;
	}

	/**
	 * @return the entrega
	 */
	public String getEntrega() {
		return entrega;
	}

	/**
	 * @param entrega the entrega to set
	 */
	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	/**
	 * @return the totalNoEntregado
	 */
	public Integer getTotalNoEntregado() {
		return totalNoEntregado;
	}

	/**
	 * @param totalNoEntregado the totalNoEntregado to set
	 */
	public void setTotalNoEntregado(Integer totalNoEntregado) {
		this.totalNoEntregado = totalNoEntregado;
	}

	/**
	 * @return the totalEntregado
	 */
	public Integer getTotalEntregado() {
		return totalEntregado;
	}

	/**
	 * @param totalEntregado the totalEntregado to set
	 */
	public void setTotalEntregado(Integer totalEntregado) {
		this.totalEntregado = totalEntregado;
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
	 * @return the totalRutas
	 */
	public Integer getTotalRutas() {
		return totalRutas;
	}

	/**
	 * @param totalRutas the totalRutas to set
	 */
	public void setTotalRutas(Integer totalRutas) {
		this.totalRutas = totalRutas;
	}

	/**
	 * @return the idDP
	 */
	public String getIdDP() {
		return idDP;
	}

	/**
	 * @param idDP the idDP to set
	 */
	public void setIdDP(String idDP) {
		this.idDP = idDP;
	}

	/**
	 * @return the guadalajara
	 */
	public boolean isGuadalajara() {
		return guadalajara;
	}

	/**
	 * @param guadalajara the guadalajara to set
	 */
	public void setGuadalajara(boolean guadalajara) {
		this.guadalajara = guadalajara;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getOrdenReal() {
		return ordenReal;
	}

	/**
	 * 
	 * @param ordenReal
	 */
	public void setOrdenReal(Integer ordenReal) {
		this.ordenReal = ordenReal;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getIdHorario() {
		return idHorario;
	}

	/**
	 * 
	 * @param idHorario
	 */
	public void setIdHorario(Integer idHorario) {
		this.idHorario = idHorario;
	}

	/**
	 * 
	 * @return
	 */
	public String getfInicio() {
		return fInicio;
	}

	/**
	 * 
	 * @param fInicio
	 */
	public void setfInicio(String fInicio) {
		this.fInicio = fInicio;
	}

	/**
	 * 
	 * @return
	 */
	public String getfFin() {
		return fFin;
	}

	/**
	 * 
	 * @param fFin
	 */
	public void setfFin(String fFin) {
		this.fFin = fFin;
	}

	/**
	 * 
	 * @return
	 */
	public String getfCierre() {
		return fCierre;
	}

	/**
	 * 
	 * @param fCierre
	 */
	public void setfCierre(String fCierre) {
		this.fCierre = fCierre;
	}

	/**
	 * @return the activo
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * @return the dias
	 */
	public String getDias() {
		return dias;
	}

	/**
	 * @param dias the dias to set
	 */
	public void setDias(String dias) {
		this.dias = dias;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}
	
}
