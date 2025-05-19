package com.proquifa.net.modelo.comun;

import java.util.List;
import java.util.Date;

/**
 * @author bryan.magana
 *
 */
public class Cartera {
	private String nombre;
	private String nivel;
	private String folio;
	private long industria;
	private long esac;
	private long ev;
	private long cobrador;
	private long usuario;
	private long evt;
	private long mensajero;

	private long idcartera;
	private long idCorporativo;

	private boolean publicada;
	private boolean cart_sistema;
	private boolean cart_updatePublicada;
	private boolean cart_updateResponsables;
	private boolean cart_updateESAC;
	private boolean cart_updateEVT;
	private boolean cart_updateMENSAJERO;

	private boolean cart_updateEV;
	private boolean cart_updateCOBRADOR;

	private double facturacionAct;
	private double facturacionAnt;
	private double objetivoFundamental;
	private double objetivoDeseado;
	private double promedioFacturacion;
	private double proyeccionVenta;
	private double debemos;
	private double deben;

	private String area;
	private String areaActual;
	private String justificacion;
	private String nombreEsac;

	private Date fua;
	private List<CarteraCliente> clientes;

	/**
	 * @return the cart_sistema
	 */
	public boolean isCart_sistema() {
		return cart_sistema;
	}

	/**
	 * @param cart_sistema
	 *            the cart_sistema to set
	 */
	public void setCart_sistema(boolean cart_sistema) {
		this.cart_sistema = cart_sistema;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre
	 *            the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the nivel
	 */
	public String getNivel() {
		return nivel;
	}

	/**
	 * @param nivel
	 *            the nivel to set
	 */
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return the industria
	 */
	public long getIndustria() {
		return industria;
	}

	/**
	 * @param industria
	 *            the industria to set
	 */
	public void setIndustria(long industria) {
		this.industria = industria;
	}

	/**
	 * @return the esac
	 */
	public long getEsac() {
		return esac;
	}

	/**
	 * @param esac
	 *            the esac to set
	 */
	public void setEsac(long esac) {
		this.esac = esac;
	}

	/**
	 * @return the ev
	 */
	public long getEv() {
		return ev;
	}

	/**
	 * @param ev
	 *            the ev to set
	 */
	public void setEv(long ev) {
		this.ev = ev;
	}

	/**
	 * @return the cobrador
	 */
	public long getCobrador() {
		return cobrador;
	}

	/**
	 * @param cobrador
	 *            the cobrador to set
	 */
	public void setCobrador(long cobrador) {
		this.cobrador = cobrador;
	}

	/**
	 * @return the idcartera
	 */
	public long getIdcartera() {
		return idcartera;
	}

	/**
	 * @param idcartera
	 *            the idcartera to set
	 */
	public void setIdcartera(long idcartera) {
		this.idcartera = idcartera;
	}

	/**
	 * @return the publicada
	 */
	public boolean isPublicada() {
		return publicada;
	}

	/**
	 * @param publicada
	 *            the publicada to set
	 */
	public void setPublicada(boolean publicada) {
		this.publicada = publicada;
	}

	/**
	 * @return the clientes
	 */
	public List<CarteraCliente> getClientes() {
		return clientes;
	}

	/**
	 * @param clientes
	 *            the clientes to set
	 */
	public void setClientes(List<CarteraCliente> clientes) {
		this.clientes = clientes;
	}

	/**
	 * @return the facturacionAct
	 */
	public double getFacturacionAct() {
		return facturacionAct;
	}

	/**
	 * @param facturacionAct
	 *            the facturacionAct to set
	 */
	public void setFacturacionAct(double facturacionAct) {
		this.facturacionAct = facturacionAct;
	}

	/**
	 * @return the facturacionAnt
	 */
	public double getFacturacionAnt() {
		return facturacionAnt;
	}

	/**
	 * @param facturacionAnt
	 *            the facturacionAnt to set
	 */
	public void setFacturacionAnt(double facturacionAnt) {
		this.facturacionAnt = facturacionAnt;
	}

	/**
	 * @return the objetivoFundamental
	 */
	public double getObjetivoFundamental() {
		return objetivoFundamental;
	}

	/**
	 * @param objetivoFundamental
	 *            the objetivoFundamental to set
	 */
	public void setObjetivoFundamental(double objetivoFundamental) {
		this.objetivoFundamental = objetivoFundamental;
	}

	/**
	 * @return the objetivoDeseado
	 */
	public double getObjetivoDeseado() {
		return objetivoDeseado;
	}

	/**
	 * @param objetivoDeseado
	 *            the objetivoDeseado to set
	 */
	public void setObjetivoDeseado(double objetivoDeseado) {
		this.objetivoDeseado = objetivoDeseado;
	}

	/**
	 * @return the promedioFacturacion
	 */
	public double getPromedioFacturacion() {
		return promedioFacturacion;
	}

	/**
	 * @param promedioFacturacion
	 *            the promedioFacturacion to set
	 */
	public void setPromedioFacturacion(double promedioFacturacion) {
		this.promedioFacturacion = promedioFacturacion;
	}

	/**
	 * @return the proyeccionVenta
	 */
	public double getProyeccionVenta() {
		return proyeccionVenta;
	}

	/**
	 * @param proyeccionVenta
	 *            the proyeccionVenta to set
	 */
	public void setProyeccionVenta(double proyeccionVenta) {
		this.proyeccionVenta = proyeccionVenta;
	}

	/**
	 * @return the debemos
	 */
	public double getDebemos() {
		return debemos;
	}

	/**
	 * @param debemos
	 *            the debemos to set
	 */
	public void setDebemos(double debemos) {
		this.debemos = debemos;
	}

	/**
	 * @return the deven
	 */
	public double getDeben() {
		return deben;
	}

	/**
	 * @param deven
	 *            the deven to set
	 */
	public void setDeben(double deben) {
		this.deben = deben;
	}

	/**
	 * @return the usuario
	 */
	public long getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(long usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}

	/**
	 * @param fua
	 *            the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}

	/**
	 * @return the cart_updatePublicada
	 */
	public boolean isCart_updatePublicada() {
		return cart_updatePublicada;
	}

	/**
	 * @param cart_updatePublicada
	 *            the cart_updatePublicada to set
	 */
	public void setCart_updatePublicada(boolean cart_updatePublicada) {
		this.cart_updatePublicada = cart_updatePublicada;
	}

	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the cart_updateResponsables
	 */
	public boolean isCart_updateResponsables() {
		return cart_updateResponsables;
	}

	/**
	 * @param cart_updateResponsables
	 *            the cart_updateResponsables to set
	 */
	public void setCart_updateResponsables(boolean cart_updateResponsables) {
		this.cart_updateResponsables = cart_updateResponsables;
	}

	/**
	 * @return the cart_updateESAC
	 */
	public boolean isCart_updateESAC() {
		return cart_updateESAC;
	}

	/**
	 * @param cart_updateESAC
	 *            the cart_updateESAC to set
	 */
	public void setCart_updateESAC(boolean cart_updateESAC) {
		this.cart_updateESAC = cart_updateESAC;
	}

	/**
	 * @return the idCorporativo
	 */
	public long getIdCorporativo() {
		return idCorporativo;
	}

	/**
	 * @param idCorporativo
	 *            the idCorporativo to set
	 */
	public void setIdCorporativo(long idCorporativo) {
		this.idCorporativo = idCorporativo;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public boolean isCart_updateEV() {
		return cart_updateEV;
	}

	public void setCart_updateEV(boolean cart_updateEV) {
		this.cart_updateEV = cart_updateEV;
	}

	public boolean isCart_updateCOBRADOR() {
		return cart_updateCOBRADOR;
	}

	public void setCart_updateCOBRADOR(boolean cart_updateCOBRADOR) {
		this.cart_updateCOBRADOR = cart_updateCOBRADOR;
	}

	public String getAreaActual() {
		return areaActual;
	}

	public void setAreaActual(String areaActual) {
		this.areaActual = areaActual;
	}

	public String getJustificacion() {
		return justificacion;
	}

	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}

	public String getNombreEsac() {
		return nombreEsac;
	}

	public void setNombreEsac(String nombreEsac) {
		this.nombreEsac = nombreEsac;
	}

	/**
	 * @return the evt
	 */
	public long getEvt() {
		return evt;
	}

	/**
	 * @param evt
	 *            the evt to set
	 */
	public void setEvt(long evt) {
		this.evt = evt;
	}

	/**
	 * @return the cart_updateEVT
	 */
	public boolean isCart_updateEVT() {
		return cart_updateEVT;
	}

	/**
	 * @param cart_updateEVT
	 *            the cart_updateEVT to set
	 */
	public void setCart_updateEVT(boolean cart_updateEVT) {
		this.cart_updateEVT = cart_updateEVT;
	}

	public long getMensajero() {
		return mensajero;
	}

	public void setMensajero(long mensajero) {
		this.mensajero = mensajero;
	}

	public boolean isCart_updateMENSAJERO() {
		return cart_updateMENSAJERO;
	}

	public void setCart_updateMENSAJERO(boolean cart_updateMENSAJERO) {
		this.cart_updateMENSAJERO = cart_updateMENSAJERO;
	}
	
	
}
