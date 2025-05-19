/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;

import com.proquifa.net.modelo.ventas.admoncomunicacion.Correo;





/**
 * @author ernestogonzalezlozada
 *
 */
public class Pendiente {
	private String partida;
	private String tipoPendiente;
	private String docto;
	private Cliente cliente;
	private Integer numeroPartidas;
	private Date fechaInicio;
	private String responsable;
	private Date fechaFin;
	private Correo correo;
	private Long folio;
	private Date fechaAux;
	private Parametro parametros;
	
	/**
	 * 
	 */
	public Pendiente() {
		super();
	}
	
	/**
	 * @param partida
	 * @param tipoPendiente
	 * @param docto
	 * @param cliente
	 * @param numeroPartidas
	 * @param fechaInicio
	 * @param responsable
	 * @param fechaFin
	 * @param correo
	 * @param folio
	 */
	public Pendiente(String partida, String tipoPendiente, String docto,
			Date fechaInicio, String responsable, Date fechaFin) {
		super();
		this.partida = partida;
		this.tipoPendiente = tipoPendiente;
		this.docto = docto;
		this.fechaInicio = fechaInicio;
		this.responsable = responsable;
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the partida
	 */
	public String getPartida() {
		return partida;
	}
	/**
	 * @param partida the partida to set
	 */
	public void setPartida(String partida) {
		this.partida = partida;
	}
	/**
	 * @return the tipoPendiente
	 */
	public String getTipoPendiente() {
		return tipoPendiente;
	}
	/**
	 * @param tipoPendiente the tipoPendiente to set
	 */
	public void setTipoPendiente(String tipoPendiente) {
		this.tipoPendiente = tipoPendiente;
	}
	/**
	 * @return the docto
	 */
	public String getDocto() {
		return docto;
	}
	/**
	 * @param docto the docto to set
	 */
	public void setDocto(String docto) {
		this.docto = docto;
	}
	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the numeroPartidas
	 */
	public Integer getNumeroPartidas() {
		return numeroPartidas;
	}
	/**
	 * @param numeroPartidas the numeroPartidas to set
	 */
	public void setNumeroPartidas(Integer numeroPartidas) {
		this.numeroPartidas = numeroPartidas;
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
	 * @return the responsable
	 */
	public String getResponsable() {
		return responsable;
	}
	/**
	 * @param responsable the responsable to set
	 */
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}
	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	/**
	 * @return the correo
	 */
	public Correo getCorreo() {
		return correo;
	}
	/**
	 * @param correo the correo to set
	 */
	public void setCorreo(Correo correo) {
		this.correo = correo;
	}
	/**
	 * @return the folio
	 */
	public Long getFolio() {
		return folio;
	}
	/**
	 * @param folio the folio to set
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}

	public Date getFechaAux() {
		return fechaAux;
	}

	public void setFechaAux(Date fechaAux) {
		this.fechaAux = fechaAux;
	}
}