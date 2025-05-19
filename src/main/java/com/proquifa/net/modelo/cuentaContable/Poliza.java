package com.proquifa.net.modelo.cuentaContable;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Proveedor;

public class Poliza {
	private Integer idPoliza;
	private Integer tipo; // Ingreso = 1, Egreso = 2, Diario = 3
	private String folio;
	private String descripcion;
	private Date fecha;
	private Empresa empresa;
	private Cliente cliente;
	private Proveedor proveedor;
	private double monto;
	private double iva;
	private double total;
	private boolean aplicada;
	private boolean activa;
	private List<PPoliza> lstPPoliza;
	private String referencia;
	
	/*Auxiliares RowMapper*/
	private Integer idEmpresa;
	private Integer idCliente;
	private Integer idProveedor;
	public Integer getIdPoliza() {
		return idPoliza;
	}
	public void setIdPoliza(Integer idPoliza) {
		this.idPoliza = idPoliza;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public double getIva() {
		return iva;
	}
	public void setIva(double iva) {
		this.iva = iva;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public boolean isAplicada() {
		return aplicada;
	}
	public void setAplicada(boolean aplicada) {
		this.aplicada = aplicada;
	}
	public boolean isActiva() {
		return activa;
	}
	public void setActiva(boolean activa) {
		this.activa = activa;
	}
	public List<PPoliza> getLstPPoliza() {
		return lstPPoliza;
	}
	public void setLstPPoliza(List<PPoliza> lstPPoliza) {
		this.lstPPoliza = lstPPoliza;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
}
