/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;

/**
 * @author ernestogonzalezlozada
 *
 */
public class DocumentoRecibido {
	private Long folio;
	private Long partida;
	private Date fecha;
	private String manejo;
	private String origen;
	private Long empresa;
	private String rPor;
	private String medio;
	private String docto;
	private String numero;
	private String observaciones;
	private Date fechaProceso;
	private String estado;
	private String folioPadre;
	private Boolean esHijo;
	private Date fechaOrigen;
	private String clienteNuevo;
	private String ingreso;
	private Long fOrigen;
	private String tipo;
	private Long idContacto;
	private String nombreEmpresa;
	private String nombreContacto;
	private String documentoCierre;
	private String cerradoAbierto;
	private String nivelIngreso;
	private String enTiempo;
	private Double montoDocumento;
	private Boolean existeReferencia;
	private String emailContacto;
	private int habilitado;
	private String campana;
	private Long ccotiza;
	private String cPago;
	
	//MAilBOT
	private String asunto;
	private String contenido;
	private String html;
	private String sender;
	private int totalAdj;
	private String nombreArchivos;
	private boolean esOrigenMailBot;
	/**
	 * @return the cPago
	 */
	public String getcPago() {
		return cPago;
	}

	/**
	 * @param cPago the cPago to set
	 */
	public void setcPago(String cPago) {
		this.cPago = cPago;
	}

	/**
	 * @return the folio
	 */
	public Long getFolio() {
		return folio;
	}

	/**
	 * @param folio
	 *            the folio to set
	 */
	public void setFolio(Long folio) {
		this.folio = folio;
	}

	/**
	 * @return the partida
	 */
	public Long getPartida() {
		return partida;
	}

	/**
	 * @param partida
	 *            the partida to set
	 */
	public void setPartida(Long partida) {
		this.partida = partida;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha
	 *            the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the manejo
	 */
	public String getManejo() {
		return manejo;
	}

	/**
	 * @param manejo
	 *            the manejo to set
	 */
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen
	 *            the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}

	/**
	 * @return the empresa
	 */
	public Long getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa
	 *            the empresa to set
	 */
	public void setEmpresa(Long empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the rPor
	 */
	public String getRPor() {
		return rPor;
	}

	/**
	 * @param por
	 *            the rPor to set
	 */
	public void setRPor(String por) {
		rPor = por;
	}

	/**
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}

	/**
	 * @param medio
	 *            the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}

	/**
	 * @return the docto
	 */
	public String getDocto() {
		return docto;
	}

	/**
	 * @param docto
	 *            the docto to set
	 */
	public void setDocto(String docto) {
		this.docto = docto;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero
	 *            the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the observaciones
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones
	 *            the observaciones to set
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the fechaProceso
	 */
	public Date getFechaProceso() {
		return fechaProceso;
	}

	/**
	 * @param fechaProceso
	 *            the fechaProceso to set
	 */
	public void setFechaProceso(Date fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the folioPadre
	 */
	public String getFolioPadre() {
		return folioPadre;
	}

	/**
	 * @param folioPadre
	 *            the folioPadre to set
	 */
	public void setFolioPadre(String folioPadre) {
		this.folioPadre = folioPadre;
	}

	/**
	 * @return the esHijo
	 */
	public Boolean getEsHijo() {
		return esHijo;
	}

	/**
	 * @param esHijo
	 *            the esHijo to set
	 */
	public void setEsHijo(Boolean esHijo) {
		this.esHijo = esHijo;
	}

	/**
	 * @return the fechaOrigen
	 */
	public Date getFechaOrigen() {
		return fechaOrigen;
	}

	/**
	 * @param fechaOrigen
	 *            the fechaOrigen to set
	 */
	public void setFechaOrigen(Date fechaOrigen) {
		this.fechaOrigen = fechaOrigen;
	}

	/**
	 * @return the clienteNuevo
	 */
	public String getClienteNuevo() {
		return clienteNuevo;
	}

	/**
	 * @param clienteNuevo
	 *            the clienteNuevo to set
	 */
	public void setClienteNuevo(String clienteNuevo) {
		this.clienteNuevo = clienteNuevo;
	}

	/**
	 * @return the ingreso
	 */
	public String getIngreso() {
		return ingreso;
	}

	/**
	 * @param ingreso
	 *            the ingreso to set
	 */
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	/**
	 * @return the fOrigen
	 */
	public Long getFOrigen() {
		return fOrigen;
	}

	/**
	 * @param origen
	 *            the fOrigen to set
	 */
	public void setFOrigen(Long origen) {
		fOrigen = origen;
	}

	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 *            the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @param idContacto
	 *            the idContacto to set
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
	 * @param nombreEmpresa
	 *            the nombreEmpresa to set
	 */
	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	/**
	 * @return the nombreEmpresa
	 */
	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	/**
	 * @param nombreContacto
	 *            the nombreContacto to set
	 */
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	/**
	 * @return the nombreContacto
	 */
	public String getNombreContacto() {
		return nombreContacto;
	}

	/**
	 * @param documentoCierre
	 *            the documentoCierre to set
	 */
	public void setDocumentoCierre(String documentoCierre) {
		this.documentoCierre = documentoCierre;
	}

	/**
	 * @return the documentoCierre
	 */
	public String getDocumentoCierre() {
		return documentoCierre;
	}

	/**
	 * @param cerradoAbierto
	 *            the cerradoAbierto to set
	 */
	public void setCerradoAbierto(String cerradoAbierto) {
		this.cerradoAbierto = cerradoAbierto;
	}

	/**
	 * @return the cerradoAbierto
	 */
	public String getCerradoAbierto() {
		return cerradoAbierto;
	}

	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}

	public String getNivelIngreso() {
		return nivelIngreso;
	}

	public void setEnTiempo(String enTiempo) {
		this.enTiempo = enTiempo;
	}

	public String getEnTiempo() {
		return enTiempo;
	}

	/**
	 * @return the montoDocumento
	 */
	public Double getMontoDocumento() {
		return montoDocumento;
	}

	/**
	 * @param montoDocumento
	 *            the montoDocumento to set
	 */
	public void setMontoDocumento(Double montoDocumento) {
		this.montoDocumento = montoDocumento;
	}

	/**
	 * @return the existeReferencia
	 */
	public Boolean getExisteReferencia() {
		return existeReferencia;
	}

	/**
	 * @param existeReferencia
	 *            the existeReferencia to set
	 */
	public void setExisteReferencia(Boolean existeReferencia) {
		this.existeReferencia = existeReferencia;
	}

	/**
	 * @param emailContacto
	 *            the emailContacto to set
	 */
	public void setEmailContacto(String emailContacto) {
		this.emailContacto = emailContacto;
	}

	/**
	 * @return the emailContacto
	 */
	public String getEmailContacto() {
		return emailContacto;
	}

	/**
	 * @return the habilitado
	 */
	public int getHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado
	 *            the habilitado to set
	 */
	public void setHabilitado(int habilitado) {
		this.habilitado = habilitado;
	}
	public String getCampana() {
		return campana;
	}
	public void setCampana(String campana) {
		this.campana = campana;
	}
	public Long getCcotiza() {
		return ccotiza;
	}
	public void setCcotiza(Long ccotiza) {
		this.ccotiza = ccotiza;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public int getTotalAdj() {
		return totalAdj;
	}

	public void setTotalAdj(int totalAdj) {
		this.totalAdj = totalAdj;
	}

	public String getNombreArchivos() {
		return nombreArchivos;
	}

	public void setNombreArchivos(String nombreArchivos) {
		this.nombreArchivos = nombreArchivos;
	}

	public boolean isEsOrigenMailBot() {
		return esOrigenMailBot;
	}

	public void setEsOrigenMailBot(boolean esOrigenMailBot) {
		this.esOrigenMailBot = esOrigenMailBot;
	}
	
}