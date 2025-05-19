package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecio;

public class Campana {
	
	private Long id_Camapana;
	private Date fechaFin;
	private Date fechaInicio;
	private String nombre;
	private String tipo;
	private Double comision;
	private String objetivo;
	private Long idProvee;
	private List<ClasificacionConfiguracionPrecio> clasificaciones;
	private List<ConfiguracionPrecio> familias;
	private List<Producto> productos;
	private String tipo_Comision;
	private Date fuaCampana;
	
	public Long getId_Camapana() {
		return id_Camapana;
	}
	public void setId_Camapana(Long id_Camapana) {
		this.id_Camapana = id_Camapana;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Double getComision() {
		return comision;
	}
	public void setComision(Double comision) {
		this.comision = comision;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public Long getIdProvee() {
		return idProvee;
	}
	public void setIdProvee(Long idProvee) {
		this.idProvee = idProvee;
	}
	public List<ClasificacionConfiguracionPrecio> getClasificaciones() {
		return clasificaciones;
	}
	public void setClasificaciones(List<ClasificacionConfiguracionPrecio> clasificaciones) {
		this.clasificaciones = clasificaciones;
	}
	public List<ConfiguracionPrecio> getFamilias() {
		return familias;
	}
	public void setFamilias(List<ConfiguracionPrecio> familias) {
		this.familias = familias;
	}
	public List<Producto> getProductos() {
		return productos;
	}
	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
	public String getTipo_Comision() {
		return tipo_Comision;
	}
	public void setTipo_Comision(String tipo_Comision) {
		this.tipo_Comision = tipo_Comision;
	}
	public Date getFuaCampana() {
		return fuaCampana;
	}
	public void setFuaCampana(Date fuaCampana) {
		this.fuaCampana = fuaCampana;
	}

	

}
