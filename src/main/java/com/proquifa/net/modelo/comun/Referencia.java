/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author amartinez
 *
 */
public class Referencia {
	private Long idReferencia;
	private Long idIncidente;
	private Long idAccion;
	private Long idSolicitudVisita;
	private Long idTareaTI;
	private Long idServicioTI;
	private Long idUsuario;
	private String folio;
	private String origen;
	private byte [] bytes;
	private String tipo;
	private String extensionArchivo;
	private Boolean borrar;	
	private String nombreResonsable;
	private String justificacion;
	private String nombre;
	
	public Long getIdServicioTI() {
		return idServicioTI;
	}
	public void setIdServicioTI(Long idServicioTI) {
		this.idServicioTI = idServicioTI;
	}
	public Long getIdTareaTI() {
		return idTareaTI;
	}
	public void setIdTareaTI(Long idTareaTI) {
		this.idTareaTI = idTareaTI;
	}
	/**
	 * @return the idReferencia
	 */
	public Long getIdReferencia() {
		return idReferencia;
	}
	/**
	 * @param idReferencia the idReferencia to set
	 */
	public void setIdReferencia(Long idReferencia) {
		this.idReferencia = idReferencia;
	}
	/**
	 * @return the idIncidente
	 */
	public Long getIdIncidente() {
		return idIncidente;
	}
	/**
	 * @param idIncidente the idIncidente to set
	 */
	public void setIdIncidente(Long idIncidente) {
		this.idIncidente = idIncidente;
	}
	/**
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
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
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte [] bytes) {
		this.bytes = bytes;
	}
	/**
	 * @return the bytes
	 */
	public byte [] getBytes() {
		return bytes;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param extensionArchivo the extensionArchivo to set
	 */
	public void setExtensionArchivo(String extensionArchivo) {
		this.extensionArchivo = extensionArchivo;
	}
	/**
	 * @return the extensionArchivo
	 */
	public String getExtensionArchivo() {
		return extensionArchivo;
	}
	/**
	 * @param borrar the borrar to set
	 */
	public void setBorrar(Boolean borrar) {
		this.borrar = borrar;
	}
	/**
	 * @return the borrar
	 */
	public Boolean getBorrar() {
		return borrar;
	}
	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return the nombreResonsable
	 */
	public String getNombreResonsable() {
		return nombreResonsable;
	}
	/**
	 * @param nombreResonsable the nombreResonsable to set
	 */
	public void setNombreResonsable(String nombreResonsable) {
		this.nombreResonsable = nombreResonsable;
	}
	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	/**
	 * @param the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}


	/**
	 * @param idSolicitudVisita the idSolicitudVisita to set
	 */
	public void setIdSolicitudVisita(Long idSolicitudVisita) {
		this.idSolicitudVisita = idSolicitudVisita;
	}
	/**
	 * @return the idSolicitudVisita
	 */
	public Long getIdSolicitudVisita() {
		return idSolicitudVisita;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @return the idAccion
	 */
	public Long getIdAccion() {
		return idAccion;
	}
	/**
	 * @param idAccion the idAccion to set
	 */
	public void setIdAccion(Long idAccion) {
		this.idAccion = idAccion;
	}

}