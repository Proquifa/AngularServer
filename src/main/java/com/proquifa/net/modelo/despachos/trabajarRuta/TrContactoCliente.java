package com.proquifa.net.modelo.despachos.trabajarRuta;

import java.io.Serializable;

public class TrContactoCliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2359314224966420631L;
	private int idTrContactoCliente;
	private int idPendiente;
	private String contacto;
	private String tel;
	private String puesto;
	private String email;
	private String packingList;
	
	private Integer idUsuario;
	
	public int getIdTrContactoCliente() {
		return idTrContactoCliente;
	}
	
	public void setIdTrContactoCliente(int idTrContactoCliente) {
		this.idTrContactoCliente = idTrContactoCliente;
	}
	
	public int getIdPendiente() {
		return idPendiente;
	}
	
	public void setIdPendiente(int idPendiente) {
		this.idPendiente = idPendiente;
	}
	
	public String getContacto() {
		return contacto;
	}
	
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	
	public String getTel() {
		return tel;
	}
	
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public String getPuesto() {
		return puesto;
	}
	
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the idUsuario
	 */
	public Integer getIdUsuario() {
		return idUsuario;
	}

	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getPackingList() {
		return packingList;
	}

	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}

}
