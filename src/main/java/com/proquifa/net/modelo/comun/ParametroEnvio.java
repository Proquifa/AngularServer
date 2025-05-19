package com.proquifa.net.modelo.comun;

import java.util.List;

import com.proquifa.net.negocio.envio.ship.stub.Address;
import com.proquifa.net.negocio.envio.ship.stub.Contact;

public class ParametroEnvio {
	private Double peso;
	private Integer length;
	private Integer height;
	private Integer width;
	private String customerReferenceClient;
	private String invoceNumber;
	private String poNumber;
	private String emisor;
	private String idPendiente;
	private String tipo;
	private List<Integer> pendientes;
	
	
	

	public String getIdPendiente() {
		return idPendiente;
	}
	public void setIdPendiente(String idPendiente) {
		this.idPendiente = idPendiente;
	}
	private int reintentos;
	
	public Double getPeso() {
		return peso;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public String getCustomerReferenceClient() {
		return customerReferenceClient;
	}
	public void setCustomerReferenceClient(String customerReferenceClient) {
		this.customerReferenceClient = customerReferenceClient;
	}
	public String getInvoceNumber() {
		return invoceNumber;
	}
	public void setInvoceNumber(String invoceNumber) {
		this.invoceNumber = invoceNumber;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	private Address address;
	private Contact contact;
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	/**
	 * @return the reintentos
	 */
	public int getReintentos() {
		return reintentos;
	}
	/**
	 * @param reintentos the reintentos to set
	 */
	public void setReintentos(int reintentos) {
		this.reintentos = reintentos;
	}
	public String getEmisor() {
		return emisor;
	}
	public void setEmisor(String emisor) {
		this.emisor = emisor;
	}
	/**
	 * @return the pendientes
	 */
	public List<Integer> getPendientes() {
		return pendientes;
	}
	/**
	 * @param pendientes the pendientes to set
	 */
	public void setPendientes(List<Integer> pendientes) {
		this.pendientes = pendientes;
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
	

}
