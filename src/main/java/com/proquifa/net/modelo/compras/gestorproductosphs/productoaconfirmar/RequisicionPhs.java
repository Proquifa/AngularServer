/**
 * 
 */
package com.proquifa.net.modelo.compras.gestorproductosphs.productoaconfirmar;

import java.util.Date;
import java.util.List;

import com.google.zxing.common.BitArray;

/**
 * @author vromero
 *
 */
public class RequisicionPhs {
	private int idRequisicion;
	private String folio;
	private String medio;
	private int idProveedor;
	private String nombreProveedor;
	private Date fechaInicio;
	private Date fechaCierre;
	private List<PartidaConfirmacion> partidas;
	private int totalPartidas;
	private int totalET;
	private int totalFT;
	private String dia;
	private BitArray archivoAmparo;
	private String folioAmparo;
	private Boolean guardado;
	private int diasDeAtraso;
	/**
	 * @param idRequisicion the idRequisicion to set
	 */
	public void setIdRequisicion(int idRequisicion) {
		this.idRequisicion = idRequisicion;
	}
	/**
	 * @return the idRequisicion
	 */
	public int getIdRequisicion() {
		return idRequisicion;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param medio the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}
	/**
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the idProveedor
	 */
	public int getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	/**
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaCierre the fechaCierre to set
	 */
	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	/**
	 * @return the fechaCierre
	 */
	public Date getFechaCierre() {
		return fechaCierre;
	}
	/**
	 * @param partidas the partidas to set
	 */
	public void setPartidas(List<PartidaConfirmacion> partidas) {
		this.partidas = partidas;
	}
	/**
	 * @return the partidas
	 */
	public List<PartidaConfirmacion> getPartidas() {
		return partidas;
	}
	/**
	 * @param totalPartidas the totalPartidas to set
	 */
	public void setTotalPartidas(int totalPartidas) {
		this.totalPartidas = totalPartidas;
	}
	/**
	 * @return the totalPartidas
	 */
	public int getTotalPartidas() {
		return totalPartidas;
	}
	/**
	 * @param totalET the totalET to set
	 */
	public void setTotalET(int totalET) {
		this.totalET = totalET;
	}
	/**
	 * @return the totalET
	 */
	public int getTotalET() {
		return totalET;
	}
	/**
	 * @param totalFT the totalFT to set
	 */
	public void setTotalFT(int totalFT) {
		this.totalFT = totalFT;
	}
	/**
	 * @return the totalFT
	 */
	public int getTotalFT() {
		return totalFT;
	}
	/**
	 * @param dia the dia to set
	 */
	public void setDia(String dia) {
		this.dia = dia;
	}
	/**
	 * @return the dia
	 */
	public String getDia() {
		return dia;
	}
	/**
	 * @param folioAmparo the folioAmparo to set
	 */
	public void setFolioAmparo(String folioAmparo) {
		this.folioAmparo = folioAmparo;
	}
	/**
	 * @return the folioAmparo
	 */
	public String getFolioAmparo() {
		return folioAmparo;
	}
	/**
	 * @param guardado the guardado to set
	 */
	public void setGuardado(Boolean guardado) {
		this.guardado = guardado;
	}
	/**
	 * @return the guardado
	 */
	public Boolean getGuardado() {
		return guardado;
	}
	/**
	 * @param archivoAmparo the archivoAmparo to set
	 */
	public void setArchivoAmparo(BitArray archivoAmparo) {
		this.archivoAmparo = archivoAmparo;
	}
	/**
	 * @return the archivoAmparo
	 */
	public BitArray getArchivoAmparo() {
		return archivoAmparo;
	}
	/**
	 * @param diasDeAtraso the diasDeAtraso to set
	 */
	public void setDiasDeAtraso(int diasDeAtraso) {
		this.diasDeAtraso = diasDeAtraso;
	}
	/**
	 * @return the diasDeAtraso
	 */
	public int getDiasDeAtraso() {
		return diasDeAtraso;
	}
	
}
