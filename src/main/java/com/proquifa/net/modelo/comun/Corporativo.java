package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

public class Corporativo {

	private Long idCorporativo;
	private String nombre;
	private Date fua;
	private List<Cliente> clientes;
	
	private Cliente cliente;
	private List<Industria> industrias;
	private boolean cart_updateResponsables;
	private boolean cart_updateESAC;
	
	
	private String areaActual;
	private String areaCorporativo;
	
	private Long idEsac;
	private Long idcobrador;
	private Long idEV;
	private Long idUsuario;
	
	
	/**
	 * 
	 */
	public Corporativo() {
		super();
	}

	/**
	 * @param idCorporativo
	 * @param nombre
	 * @param fua
	 * @param clientes
	 */
	public Corporativo(Long idCorporativo, String nombre, Date fua,
			List<Cliente> clientes) {  
		super();
		this.idCorporativo = idCorporativo;
		this.nombre = nombre;
		this.fua = fua;
		this.clientes = clientes;
	}

	/**
	 * @return the idCorporativo
	 */
	public Long getIdCorporativo() {
		return idCorporativo;
	}

	/**
	 * @param idCorporativo the idCorporativo to set
	 */
	public void setIdCorporativo(Long idCorporativo) {
		this.idCorporativo = idCorporativo;
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
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}

	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}

	/**
	 * @return the clientes
	 */
	public List<Cliente> getClientes() {
		return clientes;
	}

	/**
	 * @param clientes the clientes to set
	 */
	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
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
	 * @return the industrias
	 */
	public List<Industria> getIndustrias() {
		return industrias;
	}

	/**
	 * @param industrias the industrias to set
	 */
	public void setIndustrias(List<Industria> industrias) {
		this.industrias = industrias;
	}

	/**
	 * @return the cart_updateResponsables
	 */
	public boolean isCart_updateResponsables() {
		return cart_updateResponsables;
	}

	/**
	 * @param cart_updateResponsables the cart_updateResponsables to set
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
	 * @param cart_updateESAC the cart_updateESAC to set
	 */
	public void setCart_updateESAC(boolean cart_updateESAC) {
		this.cart_updateESAC = cart_updateESAC;
	}

	/**
	 * @return the idEsac
	 */
	public Long getIdEsac() {
		return idEsac;
	}

	/**
	 * @param idEsac the idEsac to set
	 */
	public void setIdEsac(Long idEsac) {
		this.idEsac = idEsac;
	}

	/**
	 * @return the idcobrador
	 */
	public Long getIdcobrador() {
		return idcobrador;
	}

	/**
	 * @param idcobrador the idcobrador to set
	 */
	public void setIdcobrador(Long idcobrador) {
		this.idcobrador = idcobrador;
	}

	/**
	 * @return the idEV
	 */
	public Long getIdEV() {
		return idEV;
	}

	/**
	 * @param idEV the idEV to set
	 */
	public void setIdEV(Long idEV) {
		this.idEV = idEV;
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

	public String getAreaCorporativo() {
		return areaCorporativo;
	}

	public void setAreaCorporativo(String areaCorporativo) {
		this.areaCorporativo = areaCorporativo;
	}

	public String getAreaActual() {
		return areaActual;
	}

	public void setAreaActual(String areaActual) {
		this.areaActual = areaActual;
	}

	
}
