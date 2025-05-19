package com.proquifa.net.modelo.despachos;

import java.io.Serializable;

public class TotalEmbalar implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2282760175344628466L;
	private Integer idCliente;
	private String  nombreCliente;
	private Double  monto;
	private Integer piezas;
	private String  prioridad;
	private Integer puntosPrioridad;
	private Integer maximoVendido;
	private Integer totalAEmbalar;
	private Integer totalEmbalada;	
	private Integer minimoEmbalar;
	private Integer partidasHoy;
	private Integer partidasMañana;
	private Integer partidasPMañana;
	private Integer partidasFuturo;
	private String estado;
	
	// cambio de sintaxis el PartidasPMañana y   MAximoVendido 
	
	
	
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	public String getPrioridad() {
		return prioridad;
	}
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	public Integer getMaximoVendido() {
		return maximoVendido;
	}
	public void setMaximoVendido(Integer mAximoVendido) {
		maximoVendido = mAximoVendido;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public Integer getPuntosPrioridad() {
		return puntosPrioridad;
	}
	public void setPuntosPrioridad(Integer puntosPrioridad) {
		this.puntosPrioridad = puntosPrioridad;
	}
	public Integer getPiezas() {
		return piezas;
	}
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}

	public Integer getMinimoEmbalar() {
		return minimoEmbalar;
	}
	public void setMinimoEmbalar(Integer minimoEmbalar) {
		this.minimoEmbalar = minimoEmbalar;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the totalAEmbalar
	 */
	public Integer getTotalAEmbalar() {
		return totalAEmbalar;
	}
	/**
	 * @param totalAEmbalar the totalAEmbalar to set
	 */
	public void setTotalAEmbalar(Integer totalAEmbalar) {
		this.totalAEmbalar = totalAEmbalar;
	}
	/**
	 * @return the totalEmbalada
	 */
	public Integer getTotalEmbalada() {
		return totalEmbalada;
	}
	/**
	 * @param totalEmbalada the totalEmbalada to set
	 */
	public void setTotalEmbalada(Integer totalEmbalada) {
		this.totalEmbalada = totalEmbalada;
	}
	/**
	 * @return the partidasHoy
	 */
	public Integer getPartidasHoy() {
		return partidasHoy;
	}
	/**
	 * @param partidasHoy the partidasHoy to set
	 */
	public void setPartidasHoy(Integer partidasHoy) {
		this.partidasHoy = partidasHoy;
	}
	/**
	 * @return the partidasMañana
	 */
	public Integer getPartidasMañana() {
		return partidasMañana;
	}
	/**
	 * @param partidasMañana the partidasMañana to set
	 */
	public void setPartidasMañana(Integer partidasMañana) {
		this.partidasMañana = partidasMañana;
	}
	/**
	 * @return the partidasPMañana
	 */
	public Integer getPartidasPMañana() {
		return partidasPMañana;
	}
	/**
	 * @param partidasPMañana the partidasPMañana to set
	 */
	public void setPartidasPMañana(Integer partidasPMañana) {
		this.partidasPMañana = partidasPMañana;
	}
	/**
	 * @return the partidasFuturo
	 */
	public Integer getPartidasFuturo() {
		return partidasFuturo;
	}
	/**
	 * @param partidasFuturo the partidasFuturo to set
	 */
	public void setPartidasFuturo(Integer partidasFuturo) {
		this.partidasFuturo = partidasFuturo;
	}
	
	
	
}
