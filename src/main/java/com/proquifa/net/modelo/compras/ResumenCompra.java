package com.proquifa.net.modelo.compras;

import java.util.List;

public class ResumenCompra {
	private String compra;
	private String nombreP;
	private int idProveedor;
	private String num_cliente;
	private String nombreR;
	private String fechaR;
	private String contacto;
	private String fax;
	private String TEL;
	private String email;
	private String moneda;
	private String simboloMoneda;
	private String direccion;
	private String SUBTOTAL;
	private String IVA;
	private String TOTAL;
	private String facturara;
	private String Dirfactu;
	private String empresa;
	private String rSocialEmpresa;
	private String aliasFacturarA;
	private boolean esIngles;
	private List<ResumenPCompra> resumenPCompra;

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
	 * @return the nombreP
	 */
	public String getNombreP() {
		return nombreP;
	}

	/**
	 * @param nombreP the nombreP to set
	 */
	public void setNombreP(String nombreP) {
		this.nombreP = nombreP;
	}

	/**
	 * @return the num_cliente
	 */
	public String getNum_cliente() {
		return num_cliente;
	}

	/**
	 * @param num_cliente the num_cliente to set
	 */
	public void setNum_cliente(String num_cliente) {
		this.num_cliente = num_cliente;
	}

	/**
	 * @return the nombreR
	 */
	public String getNombreR() {
		return nombreR;
	}

	/**
	 * @param nombreR the nombreR to set
	 */
	public void setNombreR(String nombreR) {
		this.nombreR = nombreR;
	}

	/**
	 * @return the fechaR
	 */
	public String getFechaR() {
		return fechaR;
	}

	/**
	 * @param fechaR the fechaR to set
	 */
	public void setFechaR(String fechaR) {
		this.fechaR = fechaR;
	}

	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the tEL
	 */
	public String getTEL() {
		return TEL;
	}

	/**
	 * @param tEL the tEL to set
	 */
	public void setTEL(String tEL) {
		TEL = tEL;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}

	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	/**
	 * @return the simboloMoneda
	 */
	public String getSimboloMoneda() {
		return simboloMoneda;
	}

	/**
	 * @param simboloMoneda the simboloMoneda to set
	 */
	public void setSimboloMoneda(String simboloMoneda) {
		this.simboloMoneda = simboloMoneda;
	}

	/**
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * @return the sUBTOTAL
	 */
	public String getSUBTOTAL() {
		return SUBTOTAL;
	}

	/**
	 * @param sUBTOTAL the sUBTOTAL to set
	 */
	public void setSUBTOTAL(String sUBTOTAL) {
		SUBTOTAL = sUBTOTAL;
	}

	/**
	 * @return the iVA
	 */
	public String getIVA() {
		return IVA;
	}

	/**
	 * @param iVA the iVA to set
	 */
	public void setIVA(String iVA) {
		IVA = iVA;
	}

	/**
	 * @return the tOTAL
	 */
	public String getTOTAL() {
		return TOTAL;
	}

	/**
	 * @param tOTAL the tOTAL to set
	 */
	public void setTOTAL(String tOTAL) {
		TOTAL = tOTAL;
	}

	/**
	 * @return the facturara
	 */
	public String getFacturara() {
		return facturara;
	}

	/**
	 * @param facturara the facturara to set
	 */
	public void setFacturara(String facturara) {
		this.facturara = facturara;
	}

	/**
	 * @return the dirfactu
	 */
	public String getDirfactu() {
		return Dirfactu;
	}

	/**
	 * @param dirfactu the dirfactu to set
	 */
	public void setDirfactu(String dirfactu) {
		Dirfactu = dirfactu;
	}

	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the resumenPCompra
	 */
	public List<ResumenPCompra> getResumenPCompra() {
		return resumenPCompra;
	}

	/**
	 * @param resumenPCompra the resumenPCompra to set
	 */
	public void setResumenPCompra(List<ResumenPCompra> resumenPCompra) {
		this.resumenPCompra = resumenPCompra;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getrSocialEmpresa() {
		return rSocialEmpresa;
	}

	public void setrSocialEmpresa(String rSocialEmpresa) {
		this.rSocialEmpresa = rSocialEmpresa;
	}

	public String getAliasFacturarA() {
		return aliasFacturarA;
	}

	public void setAliasFacturarA(String aliasFacturarA) {
		this.aliasFacturarA = aliasFacturarA;
	}

	public boolean isEsIngles() {
		return esIngles;
	}

	public void setEsIngles(boolean esIngles) {
		this.esIngles = esIngles;
	}
}
