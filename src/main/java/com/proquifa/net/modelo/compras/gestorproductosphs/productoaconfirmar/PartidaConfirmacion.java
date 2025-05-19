/**
 * 
 */
package com.proquifa.net.modelo.compras.gestorproductosphs.productoaconfirmar;

import java.util.Date;
import java.util.List;

/**
 * @author vromero
 *
 */
public class PartidaConfirmacion {
	private String nombreProveedor;
	private String paisProveedor;
	private String tipoProveedor;
	private int diferencialDias; 
	private String solicitoESAC;
	private Date fechaInicio;
	private String tipoProducto;
	private int noPiezas;
	private String descripcionProducto;
	private String folioCotizacion;
	private int numFila;
	private int idProveedor;
	private Boolean conRequisicion;
	private String comentariosESAC;
	private Boolean estatus;
	private int idPCotPharma;
	private Date fechaFin;
	private String tipoMoneda;
	private Double costoWeb;
	private Double costoBaseConfirmado;
	private List<CostoAdicionalPartidaConfirmacion> costosAdicionales;
	private String situacionDisponibilidad;
	private String tiempoEntrega;
	private String loteDisponible;
	private String caducidadDisponible;
	private Boolean conCertificado;
	private String comentariosDisponibilidad;
	private String almacenamiento;
	private String transporte;
	private String comentariosManejo;
	private String comentariosAdicionales;
	private int numRechazos;
	private String justificacionRechazo;
	private String comentariosRechazo;
	private List<RechazoPartida> historialRechazos;
	private Boolean enviar;
	private String edicionDisponible;
	private Boolean ultimaEdicion;
	private String codigo;
	private Boolean esGuardada;
	private Date fechaEnEspera;
	
	
	
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
	 * @param paisProveedor the paisProveedor to set
	 */
	public void setPaisProveedor(String paisProveedor) {
		this.paisProveedor = paisProveedor;
	}
	/**
	 * @return the paisProveedor
	 */
	public String getPaisProveedor() {
		return paisProveedor;
	}
	/**
	 * @param tipoProveedor the tipoProveedor to set
	 */
	public void setTipoProveedor(String tipoProveedor) {
		this.tipoProveedor = tipoProveedor;
	}
	/**
	 * @return the tipoProveedor
	 */
	public String getTipoProveedor() {
		return tipoProveedor;
	}
	/**
	 * @param diferencialDias the diferencialDias to set
	 */
	public void setDiferencialDias(int diferencialDias) {
		this.diferencialDias = diferencialDias;
	}
	/**
	 * @return the diferencialDias
	 */
	public int getDiferencialDias() {
		return diferencialDias;
	}
	/**
	 * @param solicitoESAC the solicitoESAC to set
	 */
	public void setSolicitoESAC(String solicitoESAC) {
		this.solicitoESAC = solicitoESAC;
	}
	/**
	 * @return the solicitoESAC
	 */
	public String getSolicitoESAC() {
		return solicitoESAC;
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
	 * @param tipoProducto the tipoProducto to set
	 */
	public void setTipoProducto(String tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	/**
	 * @return the tipoProducto
	 */
	public String getTipoProducto() {
		return tipoProducto;
	}
	/**
	 * @param noPiezas the noPiezas to set
	 */
	public void setNoPiezas(int noPiezas) {
		this.noPiezas = noPiezas;
	}
	/**
	 * @return the noPiezas
	 */
	public int getNoPiezas() {
		return noPiezas;
	}
	/**
	 * @param descripcionProducto the descripcionProducto to set
	 */
	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}
	/**
	 * @return the descripcionProducto
	 */
	public String getDescripcionProducto() {
		return descripcionProducto;
	}
	/**
	 * @param folioCotizacionRevisar the folioCotizacionRevisar to set
	 */
	public void setFolioCotizacion(String folioCotizacion) {
		this.folioCotizacion = folioCotizacion;
	}
	/**
	 * @return the folioCotizacionRevisar
	 */
	public String getFolioCotizacion() {
		return folioCotizacion;
	}
	
	/**
	 * @param numFila the numFila to set
	 */
	public void setNumFila(int numFila) {
		this.numFila = numFila;
	}
	/**
	 * @return the numFila
	 */
	public int getNumFila() {
		return numFila;
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
	 * @param conRequisicion the conRequisicion to set
	 */
	public void setConRequisicion(Boolean conRequisicion) {
		this.conRequisicion = conRequisicion;
	}
	/**
	 * @return the conRequisicion
	 */
	public Boolean getConRequisicion() {
		return conRequisicion;
	}
	/**
	 * @param comentariosESAC the comentariosESAC to set
	 */
	public void setComentariosESAC(String comentariosESAC) {
		this.comentariosESAC = comentariosESAC;
	}
	/**
	 * @return the comentariosESAC
	 */
	public String getComentariosESAC() {
		return comentariosESAC;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the estatus
	 */
	public Boolean getEstatus() {
		return estatus;
	}
	/**
	 * @param idPCotPharma the idPCotPharma to set
	 */
	public void setIdPCotPharma(int idPCotPharma) {
		this.idPCotPharma = idPCotPharma;
	}
	/**
	 * @return the idPCotPharma
	 */
	public int getIdPCotPharma() {
		return idPCotPharma;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param tipoMoneda the tipoMoneda to set
	 */
	public void setTipoMoneda(String tipoMoneda) {
		this.tipoMoneda = tipoMoneda;
	}
	/**
	 * @return the tipoMoneda
	 */
	public String getTipoMoneda() {
		return tipoMoneda;
	}
	/**
	 * @param costoWeb the costoWeb to set
	 */
	public void setCostoWeb(Double costoWeb) {
		this.costoWeb = costoWeb;
	}
	/**
	 * @return the costoWeb
	 */
	public Double getCostoWeb() {
		return costoWeb;
	}
	/**
	 * @param costoBaseConfirmado the costoBaseConfirmado to set
	 */
	public void setCostoBaseConfirmado(Double costoBaseConfirmado) {
		this.costoBaseConfirmado = costoBaseConfirmado;
	}
	/**
	 * @return the costoBaseConfirmado
	 */
	public Double getCostoBaseConfirmado() {
		return costoBaseConfirmado;
	}
	/**
	 * @param costosAdicionales the costosAdicionales to set
	 */
	public void setCostosAdicionales(List<CostoAdicionalPartidaConfirmacion> costosAdicionales) {
		this.costosAdicionales = costosAdicionales;
	}
	/**
	 * @return the costosAdicionales
	 */
	public List<CostoAdicionalPartidaConfirmacion> getCostosAdicionales() {
		return costosAdicionales;
	}
	/**
	 * @param situacionDisponibilidad the situacionDisponibilidad to set
	 */
	public void setSituacionDisponibilidad(String situacionDisponibilidad) {
		this.situacionDisponibilidad = situacionDisponibilidad;
	}
	/**
	 * @return the situacionDisponibilidad
	 */
	public String getSituacionDisponibilidad() {
		return situacionDisponibilidad;
	}
	/**
	 * @param tiempoEntrega the tiempoEntrega to set
	 */
	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}
	/**
	 * @return the tiempoEntrega
	 */
	public String getTiempoEntrega() {
		return tiempoEntrega;
	}
	/**
	 * @param loteDisponible the loteDisponible to set
	 */
	public void setLoteDisponible(String loteDisponible) {
		this.loteDisponible = loteDisponible;
	}
	/**
	 * @return the loteDisponible
	 */
	public String getLoteDisponible() {
		return loteDisponible;
	}
	/**
	 * @param caducidadDisponible the caducidadDisponible to set
	 */
	public void setCaducidadDisponible(String caducidadDisponible) {
		this.caducidadDisponible = caducidadDisponible;
	}
	/**
	 * @return the caducidadDisponible
	 */
	public String getCaducidadDisponible() {
		return caducidadDisponible;
	}
	/**
	 * @param conCertificado the conCertificado to set
	 */
	public void setConCertificado(Boolean conCertificado) {
		this.conCertificado = conCertificado;
	}
	/**
	 * @return the conCertificado
	 */
	public Boolean getConCertificado() {
		return conCertificado;
	}
	/**
	 * @param comentariosDisponibilidad the comentariosDisponibilidad to set
	 */
	public void setComentariosDisponibilidad(String comentariosDisponibilidad) {
		this.comentariosDisponibilidad = comentariosDisponibilidad;
	}
	/**
	 * @return the comentariosDisponibilidad
	 */
	public String getComentariosDisponibilidad() {
		return comentariosDisponibilidad;
	}
	/**
	 * @param almacenamiento the almacenamiento to set
	 */
	public void setAlmacenamiento(String almacenamiento) {
		this.almacenamiento = almacenamiento;
	}
	/**
	 * @return the almacenamiento
	 */
	public String getAlmacenamiento() {
		return almacenamiento;
	}
	/**
	 * @param transporte the transporte to set
	 */
	public void setTransporte(String transporte) {
		this.transporte = transporte;
	}
	/**
	 * @return the transporte
	 */
	public String getTransporte() {
		return transporte;
	}
	/**
	 * @param comentariosManejo the comentariosManejo to set
	 */
	public void setComentariosManejo(String comentariosManejo) {
		this.comentariosManejo = comentariosManejo;
	}
	/**
	 * @return the comentariosManejo
	 */
	public String getComentariosManejo() {
		return comentariosManejo;
	}
	/**
	 * @param comentariosAdicionales the comentariosAdicionales to set
	 */
	public void setComentariosAdicionales(String comentariosAdicionales) {
		this.comentariosAdicionales = comentariosAdicionales;
	}
	/**
	 * @return the comentariosAdicionales
	 */
	public String getComentariosAdicionales() {
		return comentariosAdicionales;
	}
	/**
	 * @param numRechazos the numRechazos to set
	 */
	public void setNumRechazos(int numRechazos) {
		this.numRechazos = numRechazos;
	}
	/**
	 * @return the numRechazos
	 */
	public int getNumRechazos() {
		return numRechazos;
	}
	/**
	 * @param justificacionRechazo the justificacionRechazo to set
	 */
	public void setJustificacionRechazo(String justificacionRechazo) {
		this.justificacionRechazo = justificacionRechazo;
	}
	/**
	 * @return the justificacionRechazo
	 */
	public String getJustificacionRechazo() {
		return justificacionRechazo;
	}
	/**
	 * @param comentariosRechazo the comentariosRechazo to set
	 */
	public void setComentariosRechazo(String comentariosRechazo) {
		this.comentariosRechazo = comentariosRechazo;
	}
	/**
	 * @return the comentariosRechazo
	 */
	public String getComentariosRechazo() {
		return comentariosRechazo;
	}
	/**
	 * @param historialRechazos the historialRechazos to set
	 */
	public void setHistorialRechazos(List<RechazoPartida> historialRechazos) {
		this.historialRechazos = historialRechazos;
	}
	/**
	 * @return the historialRechazos
	 */
	public List<RechazoPartida> getHistorialRechazos() {
		return historialRechazos;
	}
	/**
	 * @param enviar the enviar to set
	 */
	public void setEnviar(Boolean enviar) {
		this.enviar = enviar;
	}
	/**
	 * @return the enviar
	 */
	public Boolean getEnviar() {
		return enviar;
	}
	/**
	 * @return the edicionDisponible
	 */
	public String getEdicionDisponible() {
		return edicionDisponible;
	}
	/**
	 * @param edicionDisponible the edicionDisponible to set
	 */
	public void setEdicionDisponible(String edicionDisponible) {
		this.edicionDisponible = edicionDisponible;
	}
	/**
	 * @return the ultimaEdicion
	 */
	public Boolean getUltimaEdicion() {
		return ultimaEdicion;
	}
	/**
	 * @param ultimaEdicion the ultimaEdicion to set
	 */
	public void setUltimaEdicion(Boolean ultimaEdicion) {
		this.ultimaEdicion = ultimaEdicion;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param esGuardada the esGuardada to set
	 */
	public void setEsGuardada(Boolean esGuardada) {
		this.esGuardada = esGuardada;
	}
	/**
	 * @return the esGuardada
	 */
	public Boolean getEsGuardada() {
		return esGuardada;
	}
	/**
	 * @param fechaEnEspera the fechaEnEspera to set
	 */
	public void setFechaEnEspera(Date fechaEnEspera) {
		this.fechaEnEspera = fechaEnEspera;
	}
	/**
	 * @return the fechaEnEspera
	 */
	public Date getFechaEnEspera() {
		return fechaEnEspera;
	}
	

}


