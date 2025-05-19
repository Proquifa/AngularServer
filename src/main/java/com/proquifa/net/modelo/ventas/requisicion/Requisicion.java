/**
 * 
 */
package com.proquifa.net.modelo.ventas.requisicion;

import java.util.Date;
import java.util.List;

/**
 * @author fmartinez
 *
 */
public class Requisicion {

	//para el empleado EV
	private Integer idEmpleadoEv;
	private Integer idVisita;
	private String nombreEmpleadoEv;
	
	//Para el empleado ESAC
	private Integer idEmpleadoEsac;
	private String nombreEsac;
	
	//Para el cliente
	private Long idCliente;
	private String nombreCliente;
	
	//para el contacto
	private Long idContacto;
	private String nombreContacto;
	//private String direccionCompletaContacto;
	
	//para el doctoR
	private Integer idDoctoR;
	
	//para la requisición
	private Integer idRequi;
	private String comentarios;
	private Double importe;
	private Date fecha;
	private String folio;
//	private String registroRequi;
//	private Date fechaOrigen;
//	private Date fechaRegistro;
//	private String folioArchivo[];
//	private Integer diasDEspera;
	
	private List<PRequisicion> partidaRequisicion;

	/**
	 * @return the idEmpleadoEv
	 */
	public Integer getIdEmpleadoEv() {
		return idEmpleadoEv;
	}
	/**
	 * @param idEmpleadoEv the idEmpleadoEv to set
	 */
	public void setIdEmpleadoEv(Integer idEmpleadoEv) {
		this.idEmpleadoEv = idEmpleadoEv;
	}
	/**
	 * @return the nombreEmpleadoEv
	 */
	public String getNombreEmpleadoEv() {
		return nombreEmpleadoEv;
	}
	/**
	 * @param nombreEmpleadoEv the nombreEmpleadoEv to set
	 */
	public void setNombreEmpleadoEv(String nombreEmpleadoEv) {
		this.nombreEmpleadoEv = nombreEmpleadoEv;
	}
	/**
	 * @return the idContacto
	 */
	public Long getIdContacto() {
		return idContacto;
	}
	/**
	 * @param idContacto the idContacto to set
	 */
	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
	 * @return the nombrecontacto
	 */
	public String getNombreContacto() {
		return nombreContacto;
	}
	/**
	 * @param nombrecontacto the nombrecontacto to set
	 */
	public void setNombreContacto(String nombrecontacto) {
		this.nombreContacto = nombrecontacto;
	}
//	/**
//	 * @return the registroRequi
//	 */
//	public String getRegistroRequi() {
//		return registroRequi;
//	}
//	/**
//	 * @param registroRequi the registroRequi to set
//	 */
//	public void setRegistroRequi(String registroRequi) {
//		this.registroRequi = registroRequi;
//	}
//	/**
//	 * @return the fechaOrigen
//	 */
//	public Date getFechaOrigen() {
//		return fechaOrigen;
//	}
//	/**
//	 * @param fechaOrigen the fechaOrigen to set
//	 */
//	public void setFechaOrigen(Date fechaOrigen) {
//		this.fechaOrigen = fechaOrigen;
//	}
//	/**
//	 * @return the fechaRegistro
//	 */
//	public Date getFechaRegistro() {
//		return fechaRegistro;
//	}
//	/**
//	 * @param fechaRegistro the fechaRegistro to set
//	 */
//	public void setFechaRegistro(Date fechaRegistro) {
//		this.fechaRegistro = fechaRegistro;
//	}
//	/**
//	 * @return the folioArchivo
//	 */
//	public String[] getFolioArchivo() {
//		return folioArchivo;
//	}
//	/**
//	 * @param folioArchivo the folioArchivo to set
//	 */
//	public void setFolioArchivo(String[] folioArchivo) {
//		this.folioArchivo = folioArchivo;
//	}
//	/**
//	 * @return the diasDEspera
//	 */
//	public Integer getDiasDEspera() {
//		return diasDEspera;
//	}
//	/**
//	 * @param diasDEspera the diasDEspera to set
//	 */
//	public void setDiasDEspera(Integer diasDEspera) {
//		this.diasDEspera = diasDEspera;
//	}
	/**
	 * @return the idEmpleadoEsac
	 */
	public Integer getIdEmpleadoEsac() {
		return idEmpleadoEsac;
	}
	/**
	 * @param idEmpleadoEsac the idEmpleadoEsac to set
	 */
	public void setIdEmpleadoEsac(Integer idEmpleadoEsac) {
		this.idEmpleadoEsac = idEmpleadoEsac;
	}
	/**
	 * @return the nombreEsac
	 */
	public String getNombreEsac() {
		return nombreEsac;
	}
	/**
	 * @param nombreEsac the nombreEsac to set
	 */
	public void setNombreEsac(String nombreEsac) {
		this.nombreEsac = nombreEsac;
	}
	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}
	/**
	 * @param idDoctoR the idDoctoR to set
	 */
	public void setIdDoctoR(Integer idDoctoR) {
		this.idDoctoR = idDoctoR;
	}
	/**
	 * @return the idDoctoR
	 */
	public Integer getIdDoctoR() {
		return idDoctoR;
	}
	/**
	 * @param idRequi the idRequi to set
	 */
	public void setIdRequi(Integer idRequi) {
		this.idRequi = idRequi;
	}
	/**
	 * @return the idRequi
	 */
	public Integer getIdRequi() {
		return idRequi;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	/**
	 * @return the importe
	 */
	public Double getImporte() {
		return importe;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
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
	 * @return the partidaRequisicion
	 */
	public List<PRequisicion> getPartidaRequisicion() {
		return partidaRequisicion;
	}
	/**
	 * @param partidaRequisicion the partidaRequisicion to set
	 */
	public void setPartidaRequisicion(List<PRequisicion> partidaRequisicion) {
		this.partidaRequisicion = partidaRequisicion;
	}
	public Integer getIdVisita() {
		return idVisita;
	}
	public void setIdVisita(Integer idVisita) {
		this.idVisita = idVisita;
	}
}