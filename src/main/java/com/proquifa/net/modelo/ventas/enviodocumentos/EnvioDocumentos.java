package com.proquifa.net.modelo.ventas.enviodocumentos;

import java.sql.Date;

/**
 * @author miguelangeldamianlopez
 *
 */

public class EnvioDocumentos {

	private Date finicio;
	private Date ffin;
	private int destino;
	private String origen;
	private String tipo;
	private String contacto;
	private String facturaFpor;
	private String proformaFpor;
	private String claveProfor;
	private String folioDocumento;
	private String adjuntosDocumento;
	private String estado;
	
	public Date getFinicio() {
		return finicio;
	}
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}
	public Date getFfin() {
		return ffin;
	}
	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}
	public int getDestino() {
		return destino;
	}
	public void setDestino(int destino) {
		this.destino = destino;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getFolioDocumento() {
		return folioDocumento;
	}
	public void setFolioDocumento(String folioDocumento) {
		this.folioDocumento = folioDocumento;
	}
	public String getAdjuntosDocumento() {
		return adjuntosDocumento;
	}
	public void setAdjuntosDocumento(String adjuntosDocumento) {
		this.adjuntosDocumento = adjuntosDocumento;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getContacto() {
		return contacto;
	}
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
	public String getFacturaFpor() {
		return facturaFpor;
	}
	public void setFacturaFpor(String facturaFpor) {
		this.facturaFpor = facturaFpor;
	}
	public String getProformaFpor() {
		return proformaFpor;
	}
	public void setProformaFpor(String proformaFpor) {
		this.proformaFpor = proformaFpor;
	}
	public String getClaveProfor() {
		return claveProfor;
	}
	public void setClaveProfor(String claveProfor) {
		this.claveProfor = claveProfor;
	}

	
}
