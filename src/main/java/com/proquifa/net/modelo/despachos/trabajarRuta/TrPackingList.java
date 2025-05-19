package com.proquifa.net.modelo.despachos.trabajarRuta;

import java.io.Serializable;

import com.proquifa.net.modelo.comun.ParametroEnvio;

public class TrPackingList implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5408040005980424845L;
	private int idClave;
	private String cliente;
	private int idPendiente;
	private String packingList;
	private int cant;
	private String folio;
	private String tipo;
	private String comentario;
	private int p1;
	private int p2;
	private int p3;
	
	private String mensajeria;
	private String pais;
	private String calle;
	private String delegacion;
	private String estado;
	private String cp;
	private String tel;
	private String mail;
	private String puesto;
	private String departamento;
	private String contacto;
	private String ruta;
	private String codPais;
	
	private String factura;
	
	private ParametroEnvio envio;
	
	private String guia;
	private String numero;
	
	
	
	//variables para la estadistica de ususario 
	   private Integer tiempo;
	   private Integer totalPL;
	   private Integer totalPiezas;
	   private String prioridad;
	   private Integer totalPrioridad;
	   
	   
	   
	
	
	public Integer getTiempo() {
		return tiempo;
	}

	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
	}

	public Integer getTotalPL() {
		return totalPL;
	}

	public void setTotalPL(Integer totalPL) {
		this.totalPL = totalPL;
	}

	public Integer getTotalPiezas() {
		return totalPiezas;
	}

	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public Integer getTotalPrioridad() {
		return totalPrioridad;
	}

	public void setTotalPrioridad(Integer totalPrioridad) {
		this.totalPrioridad = totalPrioridad;
	}

	public int getIdClave() {
		return idClave;
	}
	
	

	public void setIdClave(int idClave) {
		this.idClave = idClave;
	}

	public String getFolio() {
		return folio;
	}

	public void setFolio(String folio) {
		this.folio = folio;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getPackingList() {
		return packingList;
	}
	
	public void setPackingList(String packingList) {
		this.packingList = packingList;
	}
	
	public int getCant() {
		return cant;
	}
	
	public void setCant(int cant) {
		this.cant = cant;
	}
	
	public int getP1() {
		return p1;
	}
	
	public void setP1(int p1) {
		this.p1 = p1;
	}
	
	public int getP2() {
		return p2;
	}
	
	public void setP2(int p2) {
		this.p2 = p2;
	}
	
	public int getP3() {
		return p3;
	}
	
	public void setP3(int p3) {
		this.p3 = p3;
	}

	public int getIdPendiente() {
		return idPendiente;
	}

	public void setIdPendiente(int idPendiente) {
		this.idPendiente = idPendiente;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public String getMensajeria() {
		return mensajeria;
	}

	public void setMensajeria(String mensajeria) {
		this.mensajeria = mensajeria;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getDelegacion() {
		return delegacion;
	}

	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCp() {
		return cp;
	}

	public void setCp(String cp) {
		this.cp = cp;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPuesto() {
		return puesto;
	}

	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
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

	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public String getCodPais() {
		return codPais;
	}

	public void setCodPais(String codPais) {
		this.codPais = codPais;
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
	 * @return the guia
	 */
	public String getGuia() {
		return guia;
	}

	/**
	 * @param guia the guia to set
	 */
	public void setGuia(String guia) {
		this.guia = guia;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}	
	
}
