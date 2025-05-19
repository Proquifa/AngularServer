/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.io.Serializable;

/**
 * @author ymendez
 *
 */
public class Reporte implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2782449980171898396L;
	private String cpedido;
	private String compra;
	private String codigo;
	private String descripcion;
	private String fabrica;
	private String manejo;
	private String cliente;
	private String fecha;
	private String ruta;
	private String zona;
	private String dias;
	private String prioridad;
	
	//Inspeccion
	private Integer puntosControl;
	private Integer puntosFEE;
	private Integer puntosRuta;
	private Integer puntosParciales;
	private Integer puntosManejo;
	private Integer totalPuntos;
	private Integer puntosEmbalar;
	
	//Embalar
	private Integer puntosPAviso;
	
	//Orden Despacho
	private String precioUnitario;
	private Integer piezas;
	private String importe;
	private String costo;
	private String fechaTramitacion;
	private String fechaCompra;
	private String fechaDeclararArribo;
	private String fechaArribo;
	
	//Compra
	private String cantidad;
	private String unidad;
	private String hscode;
	private String origen;
	private String usmca;
	
	public String getUsmca() {
		return usmca;
	}

	public void setUsmca(String usmca) {
		this.usmca = usmca;
	}

	/**
	 * 
	 */
	public Reporte() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the cpedido
	 */
	public String getCpedido() {
		return cpedido;
	}

	/**
	 * @param cpedido the cpedido to set
	 */
	public void setCpedido(String cpedido) {
		this.cpedido = cpedido;
	}

	/**
	 * @return the compra
	 */
	public String getCompra() {
		return compra;
	}

	/**
	 * @param compra the compra to set
	 */
	public void setCompra(String compra) {
		this.compra = compra;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the fabrica
	 */
	public String getFabrica() {
		return fabrica;
	}

	/**
	 * @param fabrica the fabrica to set
	 */
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}

	/**
	 * @return the manejo
	 */
	public String getManejo() {
		return manejo;
	}

	/**
	 * @param manejo the manejo to set
	 */
	public void setManejo(String manejo) {
		this.manejo = manejo;
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
	 * @return the fecha
	 */
	public String getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(String fecha) {
		this.fecha = fecha;
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
	 * @return the puntosControl
	 */
	public Integer getPuntosControl() {
		return puntosControl;
	}

	/**
	 * @param puntosControl the puntosControl to set
	 */
	public void setPuntosControl(Integer puntosControl) {
		this.puntosControl = puntosControl;
	}

	/**
	 * @return the puntosFEE
	 */
	public Integer getPuntosFEE() {
		return puntosFEE;
	}

	/**
	 * @param puntosFEE the puntosFEE to set
	 */
	public void setPuntosFEE(Integer puntosFEE) {
		this.puntosFEE = puntosFEE;
	}

	/**
	 * @return the puntosRuta
	 */
	public Integer getPuntosRuta() {
		return puntosRuta;
	}

	/**
	 * @param puntosRuta the puntosRuta to set
	 */
	public void setPuntosRuta(Integer puntosRuta) {
		this.puntosRuta = puntosRuta;
	}

	/**
	 * @return the puntosParciales
	 */
	public Integer getPuntosParciales() {
		return puntosParciales;
	}

	/**
	 * @param puntosParciales the puntosParciales to set
	 */
	public void setPuntosParciales(Integer puntosParciales) {
		this.puntosParciales = puntosParciales;
	}

	/**
	 * @return the puntosManejo
	 */
	public Integer getPuntosManejo() {
		return puntosManejo;
	}

	/**
	 * @param puntosManejo the puntosManejo to set
	 */
	public void setPuntosManejo(Integer puntosManejo) {
		this.puntosManejo = puntosManejo;
	}

	/**
	 * @return the totalPuntos
	 */
	public Integer getTotalPuntos() {
		return totalPuntos;
	}

	/**
	 * @param totalPuntos the totalPuntos to set
	 */
	public void setTotalPuntos(Integer totalPuntos) {
		this.totalPuntos = totalPuntos;
	}

	/**
	 * @return the puntosPAviso
	 */
	public Integer getPuntosPAviso() {
		return puntosPAviso;
	}

	/**
	 * @param puntosPAviso the puntosPAviso to set
	 */
	public void setPuntosPAviso(Integer puntosPAviso) {
		this.puntosPAviso = puntosPAviso;
	}

	/**
	 * @return the precioUnitario
	 */
	public String getPrecioUnitario() {
		return precioUnitario;
	}

	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(String precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	/**
	 * @return the piezas
	 */
	public Integer getPiezas() {
		return piezas;
	}

	/**
	 * @param piezas the piezas to set
	 */
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}

	/**
	 * @return the importe
	 */
	public String getImporte() {
		return importe;
	}

	/**
	 * @param importe the importe to set
	 */
	public void setImporte(String importe) {
		this.importe = importe;
	}

	/**
	 * @return the costo
	 */
	public String getCosto() {
		return costo;
	}

	/**
	 * @param costo the costo to set
	 */
	public void setCosto(String costo) {
		this.costo = costo;
	}

	/**
	 * @return the fechaTramitacion
	 */
	public String getFechaTramitacion() {
		return fechaTramitacion;
	}

	/**
	 * @param fechaTramitacion the fechaTramitacion to set
	 */
	public void setFechaTramitacion(String fechaTramitacion) {
		this.fechaTramitacion = fechaTramitacion;
	}

	/**
	 * @return the fechaCompra
	 */
	public String getFechaCompra() {
		return fechaCompra;
	}

	/**
	 * @param fechaCompra the fechaCompra to set
	 */
	public void setFechaCompra(String fechaCompra) {
		this.fechaCompra = fechaCompra;
	}

	/**
	 * @return the fechaDeclararArribo
	 */
	public String getFechaDeclararArribo() {
		return fechaDeclararArribo;
	}

	/**
	 * @param fechaDeclararArribo the fechaDeclararArribo to set
	 */
	public void setFechaDeclararArribo(String fechaDeclararArribo) {
		this.fechaDeclararArribo = fechaDeclararArribo;
	}

	/**
	 * @return the fechaArribo
	 */
	public String getFechaArribo() {
		return fechaArribo;
	}

	/**
	 * @param fechaArribo the fechaArribo to set
	 */
	public void setFechaArribo(String fechaArribo) {
		this.fechaArribo = fechaArribo;
	}

	public Integer getPuntosEmbalar() {
		return puntosEmbalar;
	}

	public void setPuntosEmbalar(Integer puntosEmbalar) {
		this.puntosEmbalar = puntosEmbalar;
	}

	/**
	 * @return the cantidad
	 */
	public String getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the unidad
	 */
	public String getUnidad() {
		return unidad;
	}

	/**
	 * @param unidad the unidad to set
	 */
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}

	/**
	 * @return the hscode
	 */
	public String getHscode() {
		return hscode;
	}

	/**
	 * @param hscode the hscode to set
	 */
	public void setHscode(String hscode) {
		this.hscode = hscode;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	
	
	
}
