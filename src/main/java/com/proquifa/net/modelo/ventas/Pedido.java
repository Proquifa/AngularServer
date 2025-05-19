/**
 * 
 */
package com.proquifa.net.modelo.ventas;

import java.util.Date;

import com.proquifa.net.modelo.comun.Cliente;


/**
 * @author ernestogonzalezlozada
 *
 */
public class Pedido {
	private Long idPedido;
	private Long idCliente;
	private String cpedido;
	private Long idContacto;
	private String pedido;
	private Date fecha;
	private Date fechaIngreso;
	private Long doctoR;
	private Boolean cambioDireccion;
	private String mapa;
	private String pais;
	private String estado;
	private String calle;
	private String delegacion;
	private String codigoPostal;
	private String ruta;
	private String zonaMensajeria;
	private Boolean recibeDiario;
	private Boolean parciales;
	private Boolean cambioDestino;
	private String destino;
	private String nombreFiscal;
	private String rfc;
	private String facturaFiscal;
	private String paisFiscal;
	private String estadoFiscal;
	private String direccionFiscal;
	private String delegacionFiscal;
	private String cpFiscal;
	private String cotizacion;
	private String condicionesPago;
	private String moneda;
	private String monedaFactura;
	private Double iva;
	private String contacto;
	private Double montoDolares;
	private Long folio;
	private Date fechaDoctoR;
	private Date fechaOrigen;
	private String tramito;
	private String referencia;
	private Long totalPiezas;
	private Double montoTotal;
	private Long totalPartidas;
	private String tipo;
	private String marca;
	private String enTiempo;
	private String flete;
	
	private Cliente cliente;
	
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
	}
	/**
	 * @return the enTiempo
	 */
	public String getEnTiempo() {
		return enTiempo;
	}
	/**
	 * @param enTiempo the enTiempo to set
	 */
	public void setEnTiempo(String enTiempo) {
		this.enTiempo = enTiempo;
	}
	public Double getMontoDolares() {
		return montoDolares;
	}
	public void setMontoDolares(Double montoDolares) {
		this.montoDolares = montoDolares;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	private Long cantidad;
	private String nombreCliente;
	/**
	 * @return the idPedido
	 */
	public Long getIdPedido() {
		return idPedido;
	}
	/**
	 * @param idPedido the idPedido to set
	 */
	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
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
	 * @return the fechaIngreso
	 */
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	/**
	 * @param fechaIngreso the fechaIngreso to set
	 */
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	/**
	 * @return the doctoR
	 */
	public Long getDoctoR() {
		return doctoR;
	}
	/**
	 * @param doctoR the doctoR to set
	 */
	public void setDoctoR(Long doctoR) {
		this.doctoR = doctoR;
	}
	/**
	 * @return the cambioDireccion
	 */
	public Boolean getCambioDireccion() {
		return cambioDireccion;
	}
	/**
	 * @param cambioDireccion the cambioDireccion to set
	 */
	public void setCambioDireccion(Boolean cambioDireccion) {
		this.cambioDireccion = cambioDireccion;
	}
	/**
	 * @return the mapa
	 */
	public String getMapa() {
		return mapa;
	}
	/**
	 * @param mapa the mapa to set
	 */
	public void setMapa(String mapa) {
		this.mapa = mapa;
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
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	/**
	 * @return the codigoPostal
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}
	/**
	 * @param codigoPostal the codigoPostal to set
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
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
	 * @return the zonaMensajeria
	 */
	public String getZonaMensajeria() {
		return zonaMensajeria;
	}
	/**
	 * @param zonaMensajeria the zonaMensajeria to set
	 */
	public void setZonaMensajeria(String zonaMensajeria) {
		this.zonaMensajeria = zonaMensajeria;
	}
	/**
	 * @return the recibeDiario
	 */
	public Boolean getRecibeDiario() {
		return recibeDiario;
	}
	/**
	 * @param recibeDiario the recibeDiario to set
	 */
	public void setRecibeDiario(Boolean recibeDiario) {
		this.recibeDiario = recibeDiario;
	}
	/**
	 * @return the parciales
	 */
	public Boolean getParciales() {
		return parciales;
	}
	/**
	 * @param parciales the parciales to set
	 */
	public void setParciales(Boolean parciales) {
		this.parciales = parciales;
	}
	/**
	 * @return the cambioDestino
	 */
	public Boolean getCambioDestino() {
		return cambioDestino;
	}
	/**
	 * @param cambioDestino the cambioDestino to set
	 */
	public void setCambioDestino(Boolean cambioDestino) {
		this.cambioDestino = cambioDestino;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the nombreFiscal
	 */
	public String getNombreFiscal() {
		return nombreFiscal;
	}
	/**
	 * @param nombreFiscal the nombreFiscal to set
	 */
	public void setNombreFiscal(String nombreFiscal) {
		this.nombreFiscal = nombreFiscal;
	}
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	/**
	 * @return the facturaFiscal
	 */
	public String getFacturaFiscal() {
		return facturaFiscal;
	}
	/**
	 * @param facturaFiscal the facturaFiscal to set
	 */
	public void setFacturaFiscal(String facturaFiscal) {
		this.facturaFiscal = facturaFiscal;
	}
	/**
	 * @return the paisFiscal
	 */
	public String getPaisFiscal() {
		return paisFiscal;
	}
	/**
	 * @param paisFiscal the paisFiscal to set
	 */
	public void setPaisFiscal(String paisFiscal) {
		this.paisFiscal = paisFiscal;
	}
	/**
	 * @return the estadoFiscal
	 */
	public String getEstadoFiscal() {
		return estadoFiscal;
	}
	/**
	 * @param estadoFiscal the estadoFiscal to set
	 */
	public void setEstadoFiscal(String estadoFiscal) {
		this.estadoFiscal = estadoFiscal;
	}
	/**
	 * @return the direccionFiscal
	 */
	public String getDireccionFiscal() {
		return direccionFiscal;
	}
	/**
	 * @param direccionFiscal the direccionFiscal to set
	 */
	public void setDireccionFiscal(String direccionFiscal) {
		this.direccionFiscal = direccionFiscal;
	}
	/**
	 * @return the delegacionFiscal
	 */
	public String getDelegacionFiscal() {
		return delegacionFiscal;
	}
	/**
	 * @param delegacionFiscal the delegacionFiscal to set
	 */
	public void setDelegacionFiscal(String delegacionFiscal) {
		this.delegacionFiscal = delegacionFiscal;
	}
	/**
	 * @return the cpFiscal
	 */
	public String getCpFiscal() {
		return cpFiscal;
	}
	/**
	 * @param cpFiscal the cpFiscal to set
	 */
	public void setCpFiscal(String cpFiscal) {
		this.cpFiscal = cpFiscal;
	}
	/**
	 * @return the cotizacion
	 */
	public String getCotizacion() {
		return cotizacion;
	}
	/**
	 * @param cotizacion the cotizacion to set
	 */
	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}
	/**
	 * @param condicionesPago the condicionesPago to set
	 */
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}
	/**
	 * @return the condicionesPago
	 */
	public String getCondicionesPago() {
		return condicionesPago;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	/**
	 * @param monedaFactura the monedaFactura to set
	 */
	public void setMonedaFactura(String monedaFactura) {
		this.monedaFactura = monedaFactura;
	}
	/**
	 * @return the monedaFactura
	 */
	public String getMonedaFactura() {
		return monedaFactura;
	}
	/**
	 * @param iva the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
	}
	/**
	 * @return the iva
	 */
	public Double getIva() {
		return iva;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	public void setFolio(Long folio) {
		this.folio = folio;
	}
	public Long getFolio() {
		return folio;
	}
	public void setFechaDoctoR(Date fechaDoctoR) {
		this.fechaDoctoR = fechaDoctoR;
	}
	public Date getFechaDoctoR() {
		return fechaDoctoR;
	}
	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}
	public Date getFechaOrigen() {
		return fechaOrigen;
	}
	public void setTramito(String tramito) {
		this.tramito = tramito;
	}
	public String getTramito() {
		return tramito;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Long totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	/**
	 * @return the totalPiezas
	 */
	public Long getTotalPiezas() {
		return totalPiezas;
	}
	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
	}
	/**
	 * @return the montoTotal
	 */
	public Double getMontoTotal() {
		return montoTotal;
	}
	/**
	 * @param totalPartidas the totalPartidas to set
	 */
	public void setTotalPartidas(Long totalPartidas) {
		this.totalPartidas = totalPartidas;
	}
	/**
	 * @return the totalPartidas
	 */
	public Long getTotalPartidas() {
		return totalPartidas;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setFlete(String flete) {
		this.flete = flete;
	}
	public String getFlete() {
		return flete;
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
	
	
}
