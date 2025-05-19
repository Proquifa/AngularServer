/**
 * 
 */
package com.proquifa.net.modelo.ventas.admoncomunicacion;

import java.util.Date;

/**
 * @author ernestogonzalezlozada
 *
 */
public class Correo {
	private Long idCorreo;
	private Date fechaInicio;
	private String origen;
	private Long destino;
	private String medio;
	private String FolioDocumento;
	private String correo;
	private String ccorreo;
	private String cocorreo;
	private String cuerpoCorreo;
	private String comentariosAdicionales;
	private String asunto;
	private String archivoAdjunto;
	private String tipo;
	private String nombreDestino;
	private String facturadaPor;
	private Long idContacto;
	private Long idPendiente;
	private Long idEmpleado;
	private String paisDestino;
	private String nombreOrigen;
	private Integer conFormato = 0;
	private boolean sinPiePagina = false;
	private String remitente;
	
	private String idEmpleadoString;
	
	/**
	 * @return the idCorreo
	 */
	public Long getIdCorreo() {
		return idCorreo;
	}
	/**
	 * @param idCorreo the idCorreo to set
	 */
	public void setIdCorreo(Long idCorreo) {
		this.idCorreo = idCorreo;
	}
	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}
	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	/**
	 * @return the destino
	 */
	public Long getDestino() {
		return destino;
	}
	/**
	 * @param destino the destino to set
	 */
	public void setDestino(Long destino) {
		this.destino = destino;
	}
	/**
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}
	/**
	 * @param medio the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}
	/**
	 * @return the FolioDocumento
	 */
	public String getFolioDocumento() {
		return FolioDocumento;
	}
	/**
	 * @param FolioDocumento the FolioDocumento to set
	 */
	public void setFolioDocumento(String _FolioDocumento) {
		FolioDocumento = _FolioDocumento;
	}
	/**
	 * @return the correo
	 */
	public String getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	/**
	 * @return the ccorreo
	 */
	public String getCcorreo() {
		return ccorreo;
	}
	/**
	 * @param ccorreo the ccorreo to set
	 */
	public void setCcorreo(String ccorreo) {
		this.ccorreo = ccorreo;
	}
	/**
	 * @return the cuerpoCorreo
	 */
	public String getCuerpoCorreo() {
		return cuerpoCorreo;
	}
	/**
	 * @param cuerpoCorreo the cuerpoCorreo to set
	 */
	public void setCuerpoCorreo(String cuerpoCorreo) {
		this.cuerpoCorreo = cuerpoCorreo;
	}
	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
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
	 * @return the asunto
	 */
	public String getAsunto() {
		return asunto;
	}
	/**
	 * @param asunto the asunto to set
	 */
	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	/**
	 * @return the archivoAdjunto
	 */
	public String getArchivoAdjunto() {
		return archivoAdjunto;
	}
	/**
	 * @param archivoAdjunto the archivoAdjunto to set
	 */
	public void setArchivoAdjunto(String archivoAdjunto) {
		this.archivoAdjunto = archivoAdjunto;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @param nombreDestino the nombreDestino to set
	 */
	public void setNombreDestino(String nombreDestino) {
		this.nombreDestino = nombreDestino;
	}
	/**
	 * @return the nombreDestino
	 */
	public String getNombreDestino() {
		return nombreDestino;
	}
	/**
	 * @param facturadaPor the facturadaPor to set
	 */
	public void setFacturadaPor(String facturadaPor) {
		this.facturadaPor = facturadaPor;
	}
	/**
	 * @return the facturadaPor
	 */
	public String getFacturadaPor() {
		return facturadaPor;
	}
	/**
	 * @param idContacto the idContacto to set
	 */
	public void setIdContacto(Long idContacto) {
		this.idContacto = idContacto;
	}
	/**
	 * @return the idContacto
	 */
	public Long getIdContacto() {
		return idContacto;
	}
	/**
	 * @param idPendiente the idPendiente to set
	 */
	public void setIdPendiente(Long idPendiente) {
		this.idPendiente = idPendiente;
	}
	/**
	 * @return the idPendiente
	 */
	public Long getIdPendiente() {
		return idPendiente;
	}
	/**
	 * @param idEmpleado the idEmpleado to set
	 */
	public void setIdEmpleado(Long idEmpleado) {
		this.idEmpleado = idEmpleado;
	}
	/**
	 * @return the idEmpleado
	 */
	public Long getIdEmpleado() {
		return idEmpleado;
	}
	public void setCocorreo(String cocorreo) {
		this.cocorreo = cocorreo;
	}
	public String getCocorreo() {
		return cocorreo;
	}
	/**
	 * @return paisDestino
	 */
	
  	public void setpaisDestino (String pais){
		this.paisDestino = pais;
	}
  	public String getpaisDestino(){
  		return paisDestino;
  	}
	/**
	 * @return nombreOrigen
	 */
	
  	public void setnombreOrigen (String nombreOrigen){
		this.nombreOrigen = nombreOrigen;
	}
  	public String getnombreOrigen(){
  		return nombreOrigen;
  	}
	public String getIdEmpleadoString() {
		return idEmpleadoString;
	}
	public void setIdEmpleadoString(String idEmpleadoString) {
		this.idEmpleadoString = idEmpleadoString;
	}
	/**
	 * @return the conFormato
	 */
	public Integer getConFormato() {
		return conFormato;
	}
	/**
	 * @param conFormato the conFormato to set
	 */
	public void setConFormato(Integer conFormato) {
		this.conFormato = conFormato;
	}
	public boolean isSinPiePagina() {
		return sinPiePagina;
	}
	public void setSinPiePagina(boolean sinPiePagina) {
		this.sinPiePagina = sinPiePagina;
	}
	/**
	 * @return the remitente
	 */
	public String getRemitente() {
		return remitente;
	}
	/**
	 * @param remitente the remitente to set
	 */
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	@Override
	public String toString() {
		return "Correo [idCorreo=" + idCorreo + ", fechaInicio=" + fechaInicio + ", origen=" + origen + ", destino="
				+ destino + ", medio=" + medio + ", FolioDocumento=" + FolioDocumento + ", correo=" + correo
				+ ", ccorreo=" + ccorreo + ", cocorreo=" + cocorreo + ", cuerpoCorreo=" + cuerpoCorreo
				+ ", comentariosAdicionales=" + comentariosAdicionales + ", asunto=" + asunto + ", archivoAdjunto="
				+ archivoAdjunto + ", tipo=" + tipo + ", nombreDestino=" + nombreDestino + ", facturadaPor="
				+ facturadaPor + ", idContacto=" + idContacto + ", idPendiente=" + idPendiente + ", idEmpleado="
				+ idEmpleado + ", paisDestino=" + paisDestino + ", nombreOrigen=" + nombreOrigen + ", conFormato="
				+ conFormato + ", sinPiePagina=" + sinPiePagina + ", remitente=" + remitente + ", idEmpleadoString="
				+ idEmpleadoString + "]";
	}
	
	
}
