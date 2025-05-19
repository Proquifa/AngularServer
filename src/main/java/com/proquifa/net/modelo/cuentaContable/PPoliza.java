package com.proquifa.net.modelo.cuentaContable;

public class PPoliza {
	private Integer idPPoliza;
	private Poliza poliza;
	private CuentaContable cuentaContable;
	private CentroCosto centroCosto;
	private String descripcion;
	private double monto;
	private double montoIVA;
	private boolean tipoIVA;// 0% = false, 16% = true;
	private boolean tipo;// Cargo = false, Abono = true;
	
	
	/*Auxiliares RowMapper*/
	private Integer idCuentaContable;
	private Integer idPoliza;
	private Integer idCentroCosto;
	
	public PPoliza(CuentaContable cuentaContable, CentroCosto centroCosto, String descripcion, double monto, double montoIVA, boolean tipoIVA, boolean tipo) {
		super();
		this.cuentaContable = cuentaContable;
		this.centroCosto = centroCosto;
		this.descripcion = descripcion;
		this.monto = monto;
		this.montoIVA = montoIVA;
		this.tipoIVA = tipoIVA;
		this.tipo = tipo;
	}
	
	public PPoliza() { super(); }



	public Integer getIdPPoliza() {
		return idPPoliza;
	}
	public void setIdPPoliza(Integer idPPoliza) {
		this.idPPoliza = idPPoliza;
	}
	public CuentaContable getCuentaContable() {
		return cuentaContable;
	}
	public void setCuentaContable(CuentaContable cuentaContable) {
		this.cuentaContable = cuentaContable;
	}
	public Poliza getPoliza() {
		return poliza;
	}
	public void setPoliza(Poliza poliza) {
		this.poliza = poliza;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public double getMontoIVA() {
		return montoIVA;
	}
	public void setMontoIVA(double montoIVA) {
		this.montoIVA = montoIVA;
	}
	public boolean isTipoIVA() {
		return tipoIVA;
	}
	public void setTipoIVA(boolean tipoIVA) {
		this.tipoIVA = tipoIVA;
	}
	public boolean isTipo() {
		return tipo;
	}
	public void setTipo(boolean tipo) {
		this.tipo = tipo;
	}
	public CentroCosto getCentroCosto() {
		return centroCosto;
	}
	public void setCentroCosto(CentroCosto centroCosto) {
		this.centroCosto = centroCosto;
	}
	public Integer getIdCuentaContable() {
		return idCuentaContable;
	}
	public void setIdCuentaContable(Integer idCuentaContable) {
		this.idCuentaContable = idCuentaContable;
	}
	public Integer getIdPoliza() {
		return idPoliza;
	}
	public void setIdPoliza(Integer idPoliza) {
		this.idPoliza = idPoliza;
	}
	public Integer getIdCentroCosto() {
		return idCentroCosto;
	}
	public void setIdCentroCosto(Integer idCentroCosto) {
		this.idCentroCosto = idCentroCosto;
	}
	
}
