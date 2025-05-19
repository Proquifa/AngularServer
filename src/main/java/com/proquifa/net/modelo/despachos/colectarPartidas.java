package com.proquifa.net.modelo.despachos;

import java.io.Serializable;

public class colectarPartidas  implements Serializable{

	private static final long serialVersionUID = 7786241866890932713L;
	private Integer numPiezasP1;
	private Integer numPiezasp2;
	private Integer numPiezasP3;
	private Integer numPartidasCongelacion;
	private Integer numPartidasRefrigeracion;
	private Integer numPartidasAmbiente;
	private Integer idPedido;
	private String cliente;
	private String contacto;
	private String puesto;
	private String zonaMensajeria;
	private String ruta;
	private Integer tiempo;
	private String prioridad;
	private String destino;

	private Integer idHorario;
	private Integer idCliente;

	private Integer condicionAlmacenaje;
	private String cobrador;





	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public Integer getNumPiezasP1() {
		return numPiezasP1;
	}
	public void setNumPiezasP1(Integer numPiezasP1) {
		this.numPiezasP1 = numPiezasP1;
	}
	public Integer getNumPiezasp2() {
		return numPiezasp2;
	}
	public void setNumPiezasp2(Integer numPiezasp2) {
		this.numPiezasp2 = numPiezasp2;
	}
	public Integer getNumPiezasP3() {
		return numPiezasP3;
	}
	public void setNumPiezasP3(Integer numPiezasP3) {
		this.numPiezasP3 = numPiezasP3;
	}
	public Integer getNumPartidasCongelacion() {
		return numPartidasCongelacion;
	}
	public void setNumPartidasCongelacion(Integer numPartidasCongelacion) {
		this.numPartidasCongelacion = numPartidasCongelacion;
	}
	public Integer getNumPartidasRefrigeracion() {
		return numPartidasRefrigeracion;
	}
	public void setNumPartidasRefrigeracion(Integer numPartidasRefrigeracion) {
		this.numPartidasRefrigeracion = numPartidasRefrigeracion;
	}
	public Integer getNumPartidasAmbiente() {
		return numPartidasAmbiente;
	}
	public void setNumPartidasAmbiente(Integer numPartidasAmbiente) {
		this.numPartidasAmbiente = numPartidasAmbiente;
	}
	public Integer getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getPuesto() {
		return puesto;
	}
	public void setPuesto(String puesto) {
		this.puesto = puesto;
	}
	public String getZonaMensajeria() {
		return zonaMensajeria;
	}
	public void setZonaMensajeria(String zonaMensajeria) {
		this.zonaMensajeria = zonaMensajeria;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public Integer getTiempo() {
		return tiempo;
	}
	public void setTiempo(Integer tiempo) {
		this.tiempo = tiempo;
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
	 * @return the condicionAlmacenaje
	 */
	public Integer getCondicionAlmacenaje() {
		return condicionAlmacenaje;
	}
	/**
	 * @param condicionAlmacenaje the condicionAlmacenaje to set
	 */
	public void setCondicionAlmacenaje(Integer condicionAlmacenaje) {
		this.condicionAlmacenaje = condicionAlmacenaje;
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
	public String getCobrador() {
		return cobrador;
	}
	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}


}
