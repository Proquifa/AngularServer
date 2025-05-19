/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

/**
 * @author jhidalgo
 *
 */
public class Direccion {
	
	
	
	private Integer idLugar;
	private Integer idCliente; //FK01_Cliente
	private String tipo;
	private String destino;
	
	private String calle;
	private String municipio;
	private String estado;
	private String pais;
	private String codigoPostal;
	private String ruta;
	private String zonaMensajeria;
	private String mapa;
	private String comentarios;
	private Integer idProveedor;
	private Double latitud;
	private Double longitud;
	private Double altitud;
	private Boolean validada;
	private Integer numAsociados;
	private String zona;
	private String domicilio; //CalleNum
	private Boolean borrar; 
	private Long idPais;
	private String ciudad;
	private String colonia;
	private Date fua;
	private Horario horarioEntrega;
	
	private String numInt;
	private String numExt;
	
	private String region;
	private String tipoRegion;

	
	//Direccion Catalogo Cliente
	private Long idDireccion;
	private List<Horario> horarios; // Horario actualmente usado para la Recolecci������n en Cat. Proveedores
	
	private boolean visita;
	
	/**
	 * @return the idPais
	 */
	public Long getIdPais() {
		return idPais;
	}
	/**
	 * @param idPais the idPais to set
	 */
	public void setIdPais(Long idPais) {
		this.idPais = idPais;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
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
	 * @return the idLugar
	 */
	public Integer getIdLugar() {
		return idLugar;
	}
	/**
	 * @param idLugar the idLugar to set
	 */
	public void setIdLugar(Integer idLugar) {
		this.idLugar = idLugar;
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
	 * @return the municipio
	 */
	public String getMunicipio() {
		return municipio;
	}
	/**
	 * @param municipio the municipio to set
	 */
	public void setMunicipio(String municipio) {
		this.municipio = municipio;
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
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the idProveedor
	 */
	public Integer getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
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
	 * @return the validada
	 */
	public Boolean getValidada() {
		return validada;
	}
	/**
	 * @param validada the validada to set
	 */
	public void setValidada(Boolean validada) {
		this.validada = validada;
	}
	/**
	 * @param numAsociados the numAsociados to set
	 */
	public void setNumAsociados(Integer numAsociados) {
		this.numAsociados = numAsociados;
	}
	/**
	 * @return the numAsociados
	 */
	public Integer getNumAsociados() {
		return numAsociados;
	}
	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}
	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}
	/**
	 * @param domicilio the domicilio to set
	 */
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	/**
	 * @return the domicilio
	 */
	public String getDomicilio() {
		return domicilio;
	}
	/**
	 * @return the idDireccion
	 */
	public Long getIdDireccion() {
		return idDireccion;
	}
	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
	public Boolean getBorrar() {
		return borrar;
	}
	public void setBorrar(Boolean borrar) {
		this.borrar = borrar;
	}
	/**
	 * @return the horarios
	 */
	public List<Horario> getHorarios() {
		return horarios;
	}
	/**
	 * @param horarios the horarios to set
	 */
	public void setHorarios(List<Horario> horarios) {
		this.horarios = horarios;
	}
	public boolean isVisita() {
		return visita;
	}
	
	public void setVisita(boolean visita) {
		this.visita = visita;
	}
	public String getNumInt() {
		return numInt;
	}
	public void setNumInt(String numInt) {
		this.numInt = numInt;
	}
	public String getNumExt() {
		return numExt;
	}
	public void setNumExt(String numExt) {
		this.numExt = numExt;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getTipoRegion() {
		return tipoRegion;
	}
	public void setTipoRegion(String tipoRegion) {
		this.tipoRegion = tipoRegion;
	}
	public Horario getHorarioEntrega() {
		return horarioEntrega;
	}
	public void setHorarioEntrega(Horario horarioEntrega) {
		this.horarioEntrega = horarioEntrega;
	}


}


