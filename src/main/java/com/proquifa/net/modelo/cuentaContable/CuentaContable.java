package com.proquifa.net.modelo.cuentaContable;

import java.util.List;

import com.proquifa.net.modelo.comun.Empresa;

public class CuentaContable {
	
	private int idCuentaContable;
	private int nivel;
	private int nivel1;
	private int nivel2;
	private String nivel3;
	private String descripcion;
	private ContableCaracteristica tipo;
	private ContableCaracteristica detalle;
	private Integer tipoInt;
	private Integer detalleInt;
	private boolean activo;
	private Empresa empresa;
	private Integer idEmpresa;
	private boolean editable;
	private boolean eliminable;
	private Integer cantPolizas;
	private double totalCargo;
	private double totalAbono;
	private double monto;
	private List<PPoliza> lstPP;
	private Long idCliente;
	private Integer idBanco;
	private Integer idProveedor;
	private double saldoInicial;
	private String origen;
	private String naturaleza;
	
	public int getIdCuentaContable() {
		return idCuentaContable;
	}
	public void setIdCuentaContable(int idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}
	public int getNivel() {
		return nivel;
	}
	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	public int getNivel1() {
		return nivel1;
	}
	public void setNivel1(int nivel1) {
		this.nivel1 = nivel1;
	}
	public int getNivel2() {
		return nivel2;
	}
	public void setNivel2(int nivel2) {
		this.nivel2 = nivel2;
	}
	public String getNivel3() {
		return nivel3;
	}
	public void setNivel3(String nivel3) {
		this.nivel3 = nivel3;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ContableCaracteristica getTipo() {
		return tipo;
	}
	public void setTipo(ContableCaracteristica tipo) {
		this.tipo = tipo;
	}
	public ContableCaracteristica getDetalle() {
		return detalle;
	}
	public void setDetalle(ContableCaracteristica detalle) {
		this.detalle = detalle;
	}
	public Integer getTipoInt() {
		return tipoInt;
	}
	public void setTipoInt(Integer tipoInt) {
		this.tipoInt = tipoInt;
	}
	public Integer getDetalleInt() {
		return detalleInt;
	}
	public void setDetalleInt(Integer detalleInt) {
		this.detalleInt = detalleInt;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	public Empresa getEmpresa() {
		return empresa;
	}
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public boolean isEliminable() {
		return eliminable;
	}
	public void setEliminable(boolean eliminable) {
		this.eliminable = eliminable;
	}
	public Integer getCantPolizas() {
		return cantPolizas;
	}
	public void setCantPolizas(Integer cantPolizas) {
		this.cantPolizas = cantPolizas;
	}
	public double getTotalCargo() {
		return totalCargo;
	}
	public void setTotalCargo(double totalCargo) {
		this.totalCargo = totalCargo;
	}
	public double getTotalAbono() {
		return totalAbono;
	}
	public void setTotalAbono(double totalAbono) {
		this.totalAbono = totalAbono;
	}
	public List<PPoliza> getLstPP() {
		return lstPP;
	}
	public void setLstPP(List<PPoliza> lstPP) {
		this.lstPP = lstPP;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Integer getIdBanco() {
		return idBanco;
	}
	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}
	public Integer getIdProveedor() {
		return idProveedor;
	}
	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}
	public double getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	/**
	 * @return the naturaleza
	 */
	public String getNaturaleza() {
		return naturaleza;
	}
	/**
	 * @param naturaleza the naturaleza to set
	 */
	public void setNaturaleza(String naturaleza) {
		this.naturaleza = naturaleza;
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
	/**
	 * @return the monto
	 */
	public double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(double monto) {
		this.monto = monto;
	}
	
}
