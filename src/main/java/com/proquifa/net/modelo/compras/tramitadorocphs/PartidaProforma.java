/**
 * 
 */
package com.proquifa.net.modelo.compras.tramitadorocphs;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.ventas.PartidaPedido;

/**
 * @author vromero
 *
 */
public class PartidaProforma extends PartidaPedido {
	
	private Long idPartidaProforma;
	private Long idProforma;
	private Long minCompra;
	private Long maxCompra;
	private Long intervaloMenor;
	private Long intervaloMayor;
	private Long partidasProgramadas;
	private Long partidasRegulares;
	private Long partidasFE;	
	private String folioProforma;
	private String destino;
	private String nombreCliente;
	private String nombreProveedor;
	private String paisProveedor;
	private String monedaProveedor;
	private String tipoCarro;
	private String conceptoProducto;
	private Boolean proformaAbierta;
	private Boolean parciales;
	private Date fechaTramitacion;
	private Date FEE;	
	private Date fechaCierre;
	private Double costoCDesc;
	private String origenProforma;
	private List<NotasProforma> notas;

	/**
	 * @return the idPartidaProforma
	 */
	public Long getIdPartidaProforma() {
		return idPartidaProforma;
	}
	/**
	 * @param idPartidaProforma the idPartidaProforma to set
	 */
	public void setIdPartidaProforma(Long idPartidaProforma) {
		this.idPartidaProforma = idPartidaProforma;
	}
	/**
	 * @return the idProforma
	 */
	public Long getIdProforma() {
		return idProforma;
	}
	/**
	 * @param idProforma the idProforma to set
	 */
	public void setIdProforma(Long idProforma) {
		this.idProforma = idProforma;
	}
	/**
	 * @return the folioProforma
	 */
	public String getFolioProforma() {
		return folioProforma;
	}
	/**
	 * @param folioProforma the folioProforma to set
	 */
	public void setFolioProforma(String folioProforma) {
		this.folioProforma = folioProforma;
	}
	/**
	 * @return the fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}
	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	/**
	 * @return the proformaAbierta
	 */
	public Boolean getProformaAbierta() {
		return proformaAbierta;
	}
	/**
	 * @param proformaAbierta the proformaAbierta to set
	 */
	public void setProformaAbierta(Boolean proformaAbierta) {
		this.proformaAbierta = proformaAbierta;
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
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	/**
	 * @return the paisProveedor
	 */
	public String getPaisProveedor() {
		return paisProveedor;
	}
	/**
	 * @param paisProveedor the paisProveedor to set
	 */
	public void setPaisProveedor(String paisProveedor) {
		this.paisProveedor = paisProveedor;
	}
	/**
	 * @return the monedaProveedor
	 */
	public String getMonedaProveedor() {
		return monedaProveedor;
	}
	/**
	 * @param monedaProveedor the monedaProveedor to set
	 */
	public void setMonedaProveedor(String monedaProveedor) {
		this.monedaProveedor = monedaProveedor;
	}
	/**
	 * @return the minCompra
	 */
	public Long getMinCompra() {
		return minCompra;
	}
	/**
	 * @param minCompra the minCompra to set
	 */
	public void setMinCompra(Long minCompra) {
		this.minCompra = minCompra;
	}
	/**
	 * @return the maxCompra
	 */
	public Long getMaxCompra() {
		return maxCompra;
	}
	/**
	 * @param maxCompra the maxCompra to set
	 */
	public void setMaxCompra(Long maxCompra) {
		this.maxCompra = maxCompra;
	}
	/**
	 * @return the costoCDesc
	 */
	public Double getCostoCDesc() {
		return costoCDesc;
	}
	/**
	 * @param costoCDesc the costoCDesc to set
	 */
	public void setCostoCDesc(Double costoCDesc) {
		this.costoCDesc = costoCDesc;
	}
	/**
	 * @return the fechaTramitacion
	 */
	public Date getFechaTramitacion() {
		return fechaTramitacion;
	}
	/**
	 * @param fechaTramitacion the fechaTramitacion to set
	 */
	public void setFechaTramitacion(Date fechaTramitacion) {
		this.fechaTramitacion = fechaTramitacion;
	}
	/**
	 * @return the fEE
	 */
	public Date getFEE() {
		return FEE;
	}
	/**
	 * @param fee the fEE to set
	 */
	public void setFEE(Date fee) {
		FEE = fee;
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
	 * @return the nombreCliente
	 */
	public String getNombreCliente() {
		return nombreCliente;
	}
	/**
	 * @param nombreCliente the nombreCliente to set
	 */
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	/**
	 * @return the intervaloMenor
	 */
	public Long getIntervaloMenor() {
		return intervaloMenor;
	}
	/**
	 * @param intervaloMenor the intervaloMenor to set
	 */
	public void setIntervaloMenor(Long intervaloMenor) {
		this.intervaloMenor = intervaloMenor;
	}
	/**
	 * @return the intervaloMayor
	 */
	public Long getIntervaloMayor() {
		return intervaloMayor;
	}
	/**
	 * @param intervaloMayor the intervaloMayor to set
	 */
	public void setIntervaloMayor(Long intervaloMayor) {
		this.intervaloMayor = intervaloMayor;
	}
	/**
	 * @return the tipoCarro
	 */
	public String getTipoCarro() {
		return tipoCarro;
	}
	/**
	 * @param tipoCarro the tipoCarro to set
	 */
	public void setTipoCarro(String tipoCarro) {
		this.tipoCarro = tipoCarro;
	}
	/**
	 * @param conceptoProducto the conceptoProducto to set
	 */
	public void setConceptoProducto(String conceptoProducto) {
		this.conceptoProducto = conceptoProducto;
	}
	/**
	 * @return the conceptoProducto
	 */
	public String getConceptoProducto() {
		return conceptoProducto;
	}
	/**
	 * @param partidasProgramadas the partidasProgramadas to set
	 */
	public void setPartidasProgramadas(Long partidasProgramadas) {
		this.partidasProgramadas = partidasProgramadas;
	}
	/**
	 * @return the partidasProgramadas
	 */
	public Long getPartidasProgramadas() {
		return partidasProgramadas;
	}
	/**
	 * @param partidasRegulares the partidasRegulares to set
	 */
	public void setPartidasRegulares(Long partidasRegulares) {
		this.partidasRegulares = partidasRegulares;
	}
	/**
	 * @return the partidasRegulares
	 */
	public Long getPartidasRegulares() {
		return partidasRegulares;
	}
	/**
	 * @param partidasFE the partidasFE to set
	 */
	public void setPartidasFE(Long partidasFE) {
		this.partidasFE = partidasFE;
	}
	/**
	 * @return the partidasFE
	 */
	public Long getPartidasFE() {
		return partidasFE;
	}
	/**
	 * @param origenProforma the origenProforma to set
	 */
	public void setOrigenProforma(String origenProforma) {
		this.origenProforma = origenProforma;
	}
	/**
	 * @return the origenProforma
	 */
	public String getOrigenProforma() {
		return origenProforma;
	}
	/**
	 * @param notas the notas to set
	 */
	public void setNotas(List<NotasProforma> notas) {
		this.notas = notas;
	}
	/**
	 * @return the notas
	 */
	public List<NotasProforma> getNotas() {
		return notas;
	}
	

}
