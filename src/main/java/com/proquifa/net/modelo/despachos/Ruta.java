/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.util.Date;

/**
 * @author fmartinez
 *
 */
public class Ruta {
	
	private String idEvento;
	private Long idCliente;
	private String nombreCliente;
	private Long idFactura;
	private String factura;
	private String fpor;
	private Boolean chkDiferente;
	private String mapa;
	private String destino;
	private String pais;
	private String estado;
	private String calle;
	private String delegacion;
	private String cp;
	private String rutaMensajeria;
	private String zonaMensajeria;
	private Date fecha;
	private String prioridad;
	private String comentarios;
	private String idRuta;
	private String idSurtido;
	private String idEntrega;
	private Integer ambiente;
	private Integer refrigeracion;
	private Integer congelacion;
	private Integer sinManejo; 
	private Integer colocoRefrigerados;
	private Integer colocoCongelados;
	private Integer doctoFactura;
	private Integer doctoPedido;
	private Integer doctoCantFactura;
	private Integer doctoCantPedido;
	private Integer doctoOtros;
	private String estadoRuta;
	private Integer OrdenPlan;
	private String entrega;
	private String razonesEntrega;
	private String doctosCierre;
	private String folioDoctos;
	private String folioDoctos2;
	private String folioDoctos3;
	private Date frefacturacion;
	private Boolean entregayRevision;
	private String comentariosAdicionales;
	private String registroDEntrega;
	private String conforme;
	private Date ffin;
	private Long longitud;
	private Long latitud; 
	private Long altitud; 
	private Long idDireccion;
	private String tipoJustificacion;
	private String responsable;
	private Date fer;
	private Date fr;
	private Date fee;
	private String tiempoRealizacion;
	private Integer intentosEntrega;
	private Boolean motivoClientes;
	private Boolean motivoSolicitante;
	private Boolean motivoMensajeros;
	private Boolean motivoND;
	private Integer numeroPartidasRuta;
	private byte docto [];
	private byte docto2 [];
	private byte docto3 [];

	/**
	 * @return the idEvento
	 */
	public String getIdEvento() {
		return idEvento;
	}
	/**
	 * @param idEvento the idEvento to set
	 */
	public void setIdEvento(String idEvento) {
		this.idEvento = idEvento;
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
	 * @return the idFactura
	 */
	public Long getIdFactura() {
		return idFactura;
	}
	/**
	 * @param idFactura the idFactura to set
	 */
	public void setIdFactura(Long idFactura) {
		this.idFactura = idFactura;
	}
	/**
	 * @return the factura
	 */
	public String getFactura() {
		return factura;
	}
	/**
	 * @param factura the factura to set
	 */
	public void setFactura(String factura) {
		this.factura = factura;
	}
	/**
	 * @return the fPor
	 */
	public String getFPor() {
		return fpor;
	}
	/**
	 * @param por the fPor to set
	 */
	public void setFPor(String por) {
		fpor = por;
	}
	/**
	 * @return the chkDiferente
	 */
	public Boolean getChkDiferente() {
		return chkDiferente;
	}
	/**
	 * @param chkDiferente the chkDiferente to set
	 */
	public void setChkDiferente(Boolean chkDiferente) {
		this.chkDiferente = chkDiferente;
	}
	/**
	 * @return the mapa
	 */
	public String getMapa() {
		return mapa;
	}
	/**
	 * @param mapa the mapa to set
	 */
	public void setMapa(String mapa) {
		this.mapa = mapa;
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
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}
	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}
	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}
	/**
	 * @return the rutaMensajeria
	 */
	public String getRutaMensajeria() {
		return rutaMensajeria;
	}
	/**
	 * @param rutaMensajeria the rutaMensajeria to set
	 */
	public void setRutaMensajeria(String rutaMensajeria) {
		this.rutaMensajeria = rutaMensajeria;
	}
	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	/**
	 * @return the zonaMensajeria
	 */
	public String getZonaMensajeria() {
		return zonaMensajeria;
	}
	/**
	 * @param zonaMensajeria the zonaMensajeria to set
	 */
	public void setZonaMensajeria(String zonaMensajeria) {
		this.zonaMensajeria = zonaMensajeria;
	}
	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}
	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	/**
	 * @return the prioridad
	 */
	public String getPrioridad() {
		return prioridad;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
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
	 * @return the idRuta
	 */
	public String getIdRuta() {
		return idRuta;
	}
	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	/**
	 * @return the idSurtido
	 */
	public String getIdSurtido() {
		return idSurtido;
	}
	/**
	 * @param idSurtido the idSurtido to set
	 */
	public void setIdSurtido(String idSurtido) {
		this.idSurtido = idSurtido;
	}
	/**
	 * @return the idEntrega
	 */
	public String getIdEntrega() {
		return idEntrega;
	}
	/**
	 * @param idEntrega the idEntrega to set
	 */
	public void setIdEntrega(String idEntrega) {
		this.idEntrega = idEntrega;
	}
	/**
	 * @return the ambiente
	 */
	public Integer getAmbiente() {
		return ambiente;
	}
	/**
	 * @param ambiente the ambiente to set
	 */
	public void setAmbiente(Integer ambiente) {
		this.ambiente = ambiente;
	}
	/**
	 * @return the refrigeracion
	 */
	public Integer getRefrigeracion() {
		return refrigeracion;
	}
	/**
	 * @param refrigeracion the refrigeracion to set
	 */
	public void setRefrigeracion(Integer refrigeracion) {
		this.refrigeracion = refrigeracion;
	}
	/**
	 * @return the congelacion
	 */
	public Integer getCongelacion() {
		return congelacion;
	}
	/**
	 * @param congelacion the congelacion to set
	 */
	public void setCongelacion(Integer congelacion) {
		this.congelacion = congelacion;
	}
	/**
	 * @return the sinManejo
	 */
	public Integer getSinManejo() {
		return sinManejo;
	}
	/**
	 * @param sinManejo the sinManejo to set
	 */
	public void setSinManejo(Integer sinManejo) {
		this.sinManejo = sinManejo;
	}
	/**
	 * @return the colocoRefrigerados
	 */
	public Integer getColocoRefrigerados() {
		return colocoRefrigerados;
	}
	/**
	 * @param colocoRefrigerados the colocoRefrigerados to set
	 */
	public void setColocoRefrigerados(Integer colocoRefrigerados) {
		this.colocoRefrigerados = colocoRefrigerados;
	}
	/**
	 * @return the colocoCongelados
	 */
	public Integer getColocoCongelados() {
		return colocoCongelados;
	}
	/**
	 * @param colocoCongelados the colocoCongelados to set
	 */
	public void setColocoCongelados(Integer colocoCongelados) {
		this.colocoCongelados = colocoCongelados;
	}
	/**
	 * @return the doctoFactura
	 */
	public Integer getDoctoFactura() {
		return doctoFactura;
	}
	/**
	 * @param doctoFactura the doctoFactura to set
	 */
	public void setDoctoFactura(Integer doctoFactura) {
		this.doctoFactura = doctoFactura;
	}
	/**
	 * @return the doctoPedido
	 */
	public Integer getDoctoPedido() {
		return doctoPedido;
	}
	/**
	 * @param doctoPedido the doctoPedido to set
	 */
	public void setDoctoPedido(Integer doctoPedido) {
		this.doctoPedido = doctoPedido;
	}
	/**
	 * @return the doctoCantFactura
	 */
	public Integer getDoctoCantFactura() {
		return doctoCantFactura;
	}
	/**
	 * @param doctoCantFactura the doctoCantFactura to set
	 */
	public void setDoctoCantFactura(Integer doctoCantFactura) {
		this.doctoCantFactura = doctoCantFactura;
	}
	/**
	 * @return the doctoCantPedido
	 */
	public Integer getDoctoCantPedido() {
		return doctoCantPedido;
	}
	/**
	 * @param doctoCantPedido the doctoCantPedido to set
	 */
	public void setDoctoCantPedido(Integer doctoCantPedido) {
		this.doctoCantPedido = doctoCantPedido;
	}
	/**
	 * @return the doctoOtros
	 */
	public Integer getDoctoOtros() {
		return doctoOtros;
	}
	/**
	 * @param doctoOtros the doctoOtros to set
	 */
	public void setDoctoOtros(Integer doctoOtros) {
		this.doctoOtros = doctoOtros;
	}
	/**
	 * @return the estadoRuta
	 */
	public String getEstadoRuta() {
		return estadoRuta;
	}
	/**
	 * @param estadoRuta the estadoRuta to set
	 */
	public void setEstadoRuta(String estadoRuta) {
		this.estadoRuta = estadoRuta;
	}
	/**
	 * @return the ordenPlan
	 */
	public Integer getOrdenPlan() {
		return OrdenPlan;
	}
	/**
	 * @param ordenPlan the ordenPlan to set
	 */
	public void setOrdenPlan(Integer ordenPlan) {
		OrdenPlan = ordenPlan;
	}
	/**
	 * @return the entrega
	 */
	public String getEntrega() {
		return entrega;
	}
	/**
	 * @param entrega the entrega to set
	 */
	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}
	/**
	 * @return the razonesEntrega
	 */
	public String getRazonesEntrega() {
		return razonesEntrega;
	}
	/**
	 * @param razonesEntrega the razonesEntrega to set
	 */
	public void setRazonesEntrega(String razonesEntrega) {
		this.razonesEntrega = razonesEntrega;
	}
	/**
	 * @return the doctosCierre
	 */
	public String getDoctosCierre() {
		return doctosCierre;
	}
	/**
	 * @param doctosCierre the doctosCierre to set
	 */
	public void setDoctosCierre(String doctosCierre) {
		this.doctosCierre = doctosCierre;
	}
	/**
	 * @return the folioDoctos
	 */
	public String getFolioDoctos() {
		return folioDoctos;
	}
	/**
	 * @param folioDoctos the folioDoctos to set
	 */
	public void setFolioDoctos(String folioDoctos) {
		this.folioDoctos = folioDoctos;
	}
	/**
	 * @return the folioDoctos2
	 */
	public String getFolioDoctos2() {
		return folioDoctos2;
	}
	/**
	 * @param folioDoctos2 the folioDoctos2 to set
	 */
	public void setFolioDoctos2(String folioDoctos2) {
		this.folioDoctos2 = folioDoctos2;
	}
	/**
	 * @return the folioDoctos3
	 */
	public String getFolioDoctos3() {
		return folioDoctos3;
	}
	/**
	 * @param folioDoctos3 the folioDoctos3 to set
	 */
	public void setFolioDoctos3(String folioDoctos3) {
		this.folioDoctos3 = folioDoctos3;
	}
	/**
	 * @return the frefacturacion
	 */
	public Date getFrefacturacion() {
		return frefacturacion;
	}
	/**
	 * @param frefacturacion the frefacturacion to set
	 */
	public void setFrefacturacion(Date frefacturacion) {
		this.frefacturacion = frefacturacion;
	}
	/**
	 * @return the entregayRevision
	 */
	public Boolean getEntregayRevision() {
		return entregayRevision;
	}
	/**
	 * @param entregayRevision the entregayRevision to set
	 */
	public void setEntregayRevision(Boolean entregayRevision) {
		this.entregayRevision = entregayRevision;
	}
	/**
	 * @return the comentariosAdicionales
	 */
	public String getComentariosAdicionales() {
		return comentariosAdicionales;
	}
	/**
	 * @param comentariosAdicionales the comentariosAdicionales to set
	 */
	public void setComentariosAdicionales(String comentariosAdicionales) {
		this.comentariosAdicionales = comentariosAdicionales;
	}
	/**
	 * @return the registroDEntrega
	 */
	public String getRegistroDEntrega() {
		return registroDEntrega;
	}
	/**
	 * @param registroDEntrega the registroDEntrega to set
	 */
	public void setRegistroDEntrega(String registroDEntrega) {
		this.registroDEntrega = registroDEntrega;
	}
	/**
	 * @return the conforme
	 */
	public String getConforme() {
		return conforme;
	}
	/**
	 * @param conforme the conforme to set
	 */
	public void setConforme(String conforme) {
		this.conforme = conforme;
	}
	/**
	 * @return the ffin
	 */
	public Date getFfin() {
		return ffin;
	}
	/**
	 * @param ffin the ffin to set
	 */
	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}
	/**
	 * @return the longitud
	 */
	public Long getLongitud() {
		return longitud;
	}
	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(Long longitud) {
		this.longitud = longitud;
	}
	/**
	 * @return the latitud
	 */
	public Long getLatitud() {
		return latitud;
	}
	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(Long latitud) {
		this.latitud = latitud;
	}
	/**
	 * @return the altitud
	 */
	public Long getAltitud() {
		return altitud;
	}
	/**
	 * @param altitud the altitud to set
	 */
	public void setAltitud(Long altitud) {
		this.altitud = altitud;
	}
	/**
	 * @return the idDireccion
	 */
	public Long getIdDireccion() {
		return idDireccion;
	}
	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
	/**
	 * @return the tipoJustificacion
	 */
	public String getTipoJustificacion() {
		return tipoJustificacion;
	}
	/**
	 * @param tipoJustificacion the tipoJustificacion to set
	 */
	public void setTipoJustificacion(String tipoJustificacion) {
		this.tipoJustificacion = tipoJustificacion;
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
	 * @return the fer
	 */
	public Date getFer() {
		return fer;
	}
	/**
	 * @param fer the fer to set
	 */
	public void setFer(Date fer) {
		this.fer = fer;
	}
	/**
	 * @return the fr
	 */
	public Date getFr() {
		return fr;
	}
	/**
	 * @param fr the fr to set
	 */
	public void setFr(Date fr) {
		this.fr = fr;
	}
	/**
	 * @return the fee
	 */
	public Date getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(Date fee) {
		this.fee = fee;
	}
	/**
	 * @return the tiempoRealizacion
	 */
	public String getTiempoRealizacion() {
		return tiempoRealizacion;
	}
	/**
	 * @param tiempoRealizacion the tiempoRealizacion to set
	 */
	public void setTiempoRealizacion(String tiempoRealizacion) {
		this.tiempoRealizacion = tiempoRealizacion;
	}
	/**
	 * @return the intentosEntrega
	 */
	public Integer getIntentosEntrega() {
		return intentosEntrega;
	}
	/**
	 * @param intentosEntrega the intentosEntrega to set
	 */
	public void setIntentosEntrega(Integer intentosEntrega) {
		this.intentosEntrega = intentosEntrega;
	}
	/**
	 * @return the motivoClientes
	 */
	public Boolean getMotivoClientes() {
		return motivoClientes;
	}
	/**
	 * @param motivoClientes the motivoClientes to set
	 */
	public void setMotivoClientes(Boolean motivoClientes) {
		this.motivoClientes = motivoClientes;
	}
	/**
	 * @return the motivoSolicitante
	 */
	public Boolean getMotivoSolicitante() {
		return motivoSolicitante;
	}
	/**
	 * @param motivoSolicitante the motivoSolicitante to set
	 */
	public void setMotivoSolicitante(Boolean motivoSolicitante) {
		this.motivoSolicitante = motivoSolicitante;
	}
	/**
	 * @return the motivoMensajeros
	 */
	public Boolean getMotivoMensajeros() {
		return motivoMensajeros;
	}
	/**
	 * @param motivoMensajeros the motivoMensajeros to set
	 */
	public void setMotivoMensajeros(Boolean motivoMensajeros) {
		this.motivoMensajeros = motivoMensajeros;
	}
	/**
	 * @return the motivoND
	 */
	public Boolean getMotivoND() {
		return motivoND;
	}
	/**
	 * @param motivoND the motivoND to set
	 */
	public void setMotivoND(Boolean motivoND) {
		this.motivoND = motivoND;
	}
	/**
	 * @return the numeroPartidasRuta
	 */
	public Integer getNumeroPartidasRuta() {
		return numeroPartidasRuta;
	}
	/**
	 * @param numeroPartidasRuta the numeroPartidasRuta to set
	 */
	public void setNumeroPartidasRuta(Integer numeroPartidasRuta) {
		this.numeroPartidasRuta = numeroPartidasRuta;
	}
	/**
	 * @return the docto
	 */
	public byte[] getDocto() {
		return docto;
	}
	/**
	 * @param docto the docto to set
	 */
	public void setDocto(byte[] docto) {
		this.docto = docto;
	}
	/**
	 * @return the docto2
	 */
	public byte[] getDocto2() {
		return docto2;
	}
	/**
	 * @param docto2 the docto2 to set
	 */
	public void setDocto2(byte[] docto2) {
		this.docto2 = docto2;
	}
	/**
	 * @return the docto3
	 */
	public byte[] getDocto3() {
		return docto3;
	}
	/**
	 * @param docto3 the docto3 to set
	 */
	public void setDocto3(byte[] docto3) {
		this.docto3 = docto3;
	}
	
}