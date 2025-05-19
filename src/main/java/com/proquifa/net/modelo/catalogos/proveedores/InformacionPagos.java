package com.proquifa.net.modelo.catalogos.proveedores;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.MedioPago;

public class InformacionPagos {
	
	private Long idProveedor;
	private String condicionesPago;
	private Double lineaCredito;
	private Double limiteCredito;
	private String comentarios;
	private List<MedioPago> medios;
	private Date fua;
	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the condicionesPago
	 */
	public String getCondicionesPago() {
		return condicionesPago;
	}
	/**
	 * @param condicionesPago the condicionesPago to set
	 */
	public void setCondicionesPago(String condicionesPago) {
		this.condicionesPago = condicionesPago;
	}
	/**
	 * @return the lineaCredito
	 */
	public Double getLineaCredito() {
		return lineaCredito;
	}
	/**
	 * @param lineaCredito the lineaCredito to set
	 */
	public void setLineaCredito(Double lineaCredito) {
		this.lineaCredito = lineaCredito;
	}
	/**
	 * @return the limiteCredito
	 */
	public Double getLimiteCredito() {
		return limiteCredito;
	}
	/**
	 * @param limiteCredito the limiteCredito to set
	 */
	public void setLimiteCredito(Double limiteCredito) {
		this.limiteCredito = limiteCredito;
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
	 * @return the medios
	 */
	public List<MedioPago> getMedios() {
		return medios;
	}
	/**
	 * @param medios the medios to set
	 */
	public void setMedios(List<MedioPago> medios) {
		this.medios = medios;
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

}
