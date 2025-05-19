package com.proquifa.net.modelo.despachos.mensajero;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Documento;
import com.proquifa.net.modelo.comun.Horario;


public class PendientesMensajero implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -611332351474299477L;

	@Override
	public String toString() {
		return "PendientesMensajero [folio=" + folio + ", docto=" + docto + ", comentarios=" + comentarios
				+ ", contactos=" + contactos + ", destino=" + destino + ", empresa=" + empresa + ", evento=" + evento
				+ ", prioridad=" + prioridad + ", ruta=" + ruta + ", tipo=" + tipo + ", direccion=" + direccion
				+ ", diferenteDireccion=" + diferenteDireccion + ", mapa=" + mapa + ", horario=" + horario
				+ ", documentos=" + documentos + ", idCliente=" + idCliente + ", idProveedor=" + idProveedor
				+ ", folioEvento=" + folioEvento + ", folioProducto=" + folioProducto + ", folioDocumento="
				+ folioDocumento + ", latitud=" + latitud + ", longitud=" + longitud + ", altitud=" + altitud
				+ ", idHorario=" + idHorario + ", finicio=" + finicio + ", ffin=" + ffin + ", zona=" + zona
				+ ", idEntrega=" + idEntrega + ", nombre=" + nombre + ", clave=" + clave + ", ciudad=" + ciudad
				+ ", colonia=" + colonia + ", idhorario=" + idhorario + ", realizado=" + realizado + ", justificacion="
				+ justificacion + ", tipoJustificacion=" + tipoJustificacion + ", estadoEntrega=" + estadoEntrega
				+ ", estadoPendiente=" + estadoPendiente + ", personaRecibio=" + personaRecibio
				+ ", puestoPersonaRecibio=" + puestoPersonaRecibio + ", tipoRuta=" + tipoRuta + ", ordenPlan="
				+ ordenPlan + ", stDocto=" + stDocto + ", partida=" + partida + ", nombreCliente=" + nombreCliente
				+ ", factura=" + factura + ", FEE=" + FEE + ", monto=" + monto + "]";
	}
	private String folio;
	private Long docto;
	private String comentarios;
	private List<Contacto> contactos;
	private String destino;
	private String empresa;
	private String evento;
	private String prioridad;
	private String ruta;
	private String tipo;
	private String direccion;
	private Boolean diferenteDireccion;
	private String mapa;
	private Horario horario;
	private List<Documento> documentos;
	private Long idCliente;
	private Long idProveedor;
	private String folioEvento;
	private String folioProducto;
	private String folioDocumento;
	private Double latitud;
	private Double longitud;
	private Double altitud;
	private Long idHorario;
	private Date finicio;
	private Date ffin;
	private String zona;
	private String idEntrega;
	private String nombre;
	private Integer clave;
	private String ciudad;
	private String colonia;
	private String idhorario;
    private String  foliosPL;
    private String foliosPPL;
    private String manejo;
    private Integer orden;
    private Boolean conforme;
    private String razonesEntrega;
    private String idRuta;
	
	/*
	 * para pendientes en cierre
	 */
	
	private Boolean realizado;
	private String justificacion;
	private String tipoJustificacion;
	private String estadoEntrega;
	private String estadoPendiente;
	private String personaRecibio;
	private String puestoPersonaRecibio;
	
	private String tipoRuta;
	private int ordenPlan;
	private String stDocto;
	private String partida;
	private String nombreCliente;
	private long factura;
	private Date FEE;
	private double monto;
	
	private String realizadoTxt;
	
	private Boolean aceptaEyR;
	private Boolean entregaRevision;
	
	private Boolean facturaORemision;
	
	private Boolean actualizar;



	public String getFoliosPPL() {
		return foliosPPL;
	}
	public void setFoliosPPL(String foliosPPL) {
		this.foliosPPL = foliosPPL;
	}
	public Integer getOrden() {
		return orden;
	}
	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	public String getManejo() {
		return manejo;
	}
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}
	public String getFoliosPL() {
		return foliosPL;
	}
	public void setFoliosPL(String foliosPL) {
		this.foliosPL = foliosPL;
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
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}
	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param evento the evento to set
	 */
	public void setEvento(String evento) {
		this.evento = evento;
	}
	/**
	 * @return the evento
	 */
	public String getEvento() {
		return evento;
	}
	/**
	 * @param prioridad the prioridad to set
	 */
	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}
	/**
	 * @return the prioridad
	 */
	public String getPrioridad() {
		return prioridad;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
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
	 * @return the direccion
	 */
	public String getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the diferenteDireccion
	 */
	public Boolean getDiferenteDireccion() {
		return diferenteDireccion;
	}
	/**
	 * @param diferenteDireccion the diferenteDireccion to set
	 */
	public void setDiferenteDireccion(Boolean diferenteDireccion) {
		this.diferenteDireccion = diferenteDireccion;
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
	 * @param documentos the documentos to set
	 */
	public void setDocumentos(List<Documento> documentos) {
		this.documentos = documentos;
	}
	/**
	 * @return the documentos
	 */
	public List<Documento> getDocumentos() {
		return documentos;
	}
	/**
	 * @param horario the horario to set
	 */
	public void setHorario(Horario horario) {
		this.horario = horario;
	}
	/**
	 * @return the horario
	 */
	public Horario getHorario() {
		return horario;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param folioEvento the folioEvento to set
	 */
	public void setFolioEvento(String folioEvento) {
		this.folioEvento = folioEvento;
	}
	/**
	 * @return the folioEvento
	 */
	public String getFolioEvento() {
		return folioEvento;
	}
	/**
	 * @param ordenPlan the ordenPlan to set
	 */
	public void setOrdenPlan(int ordenPlan) {
		this.ordenPlan = ordenPlan;
	}
	/**
	 * @return the ordenPlan
	 */
	public int getOrdenPlan() {
		return ordenPlan;
	}
	/**
	 * @param folioDocumento the folioDocumento to set
	 */
	public void setFolioDocumento(String folioDocumento) {
		this.folioDocumento = folioDocumento;
	}
	/**
	 * @return the folioDocumento
	 */
	public String getFolioDocumento() {
		return folioDocumento;
	}
	/**
	 * @param folioProducto the folioProducto to set
	 */
	public void setFolioProducto(String folioProducto) {
		this.folioProducto = folioProducto;
	}
	/**
	 * @return the folioProducto
	 */
	public String getFolioProducto() {
		return folioProducto;
	}
	/**
	 * @param contactos the contactos to set
	 */
	public void setContactos(List<Contacto> contactos) {
		this.contactos = contactos;
	}
	/**
	 * @return the contactos
	 */
	public List<Contacto> getContactos() {
		return contactos;
	}
	/**
	 * @param latitud the latitud to set
	 */
	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}
	/**
	 * @return the latitud
	 */
	public Double getLatitud() {
		return latitud;
	}
	/**
	 * @param longitud the longitud to set
	 */
	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}
	/**
	 * @return the longitud
	 */
	public Double getLongitud() {
		return longitud;
	}
	/**
	 * @param realizado the realizado to set
	 */
	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}
	/**
	 * @return the realizado
	 */
	public Boolean getRealizado() {
		return realizado;
	}
	/**
	 * @param justificacion the justificacion to set
	 */
	public void setJustificacion(String justificacion) {
		this.justificacion = justificacion;
	}
	/**
	 * @return the justificacion
	 */
	public String getJustificacion() {
		return justificacion;
	}
	/**
	 * @param tipoJustificacion the tipoJustificacion to set
	 */
	public void setTipoJustificacion(String tipoJustificacion) {
		this.tipoJustificacion = tipoJustificacion;
	}
	/**
	 * @return the tipoJustificacion
	 */
	public String getTipoJustificacion() {
		return tipoJustificacion;
	}
	/**
	 * @param estadoEntrega the estadoEntrega to set
	 */
	public void setEstadoEntrega(String estadoEntrega) {
		this.estadoEntrega = estadoEntrega;
	}
	/**
	 * @return the estadoEntrega
	 */
	public String getEstadoEntrega() {
		return estadoEntrega;
	}
	/**
	 * @param altitud the altitud to set
	 */
	public void setAltitud(Double altitud) {
		this.altitud = altitud;
	}
	/**
	 * @return the altitud
	 */
	public Double getAltitud() {
		return altitud;
	}
	/**
	 * @param estadoPendiente the estadoPendiente to set
	 */
	public void setEstadoPendiente(String estadoPendiente) {
		this.estadoPendiente = estadoPendiente;
	}
	/**
	 * @return the estadoPendiente
	 */
	public String getEstadoPendiente() {
		return estadoPendiente;
	}
	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}
	public Long getIdHorario() {
		return idHorario;
	}
	/**
	 * @return the personaRecibio
	 */
	public String getPersonaRecibio() {
		return personaRecibio;
	}
	/**
	 * @param personaRecibio the personaRecibio to set
	 */
	public void setPersonaRecibio(String personaRecibio) {
		this.personaRecibio = personaRecibio;
	}
	/**
	 * @return the puestoPersonaRecibio
	 */
	public String getPuestoPersonaRecibio() {
		return puestoPersonaRecibio;
	}
	/**
	 * @param puestoPersonaRecibio the puestoPersonaRecibio to set
	 */
	public void setPuestoPersonaRecibio(String puestoPersonaRecibio) {
		this.puestoPersonaRecibio = puestoPersonaRecibio;
	}
	public Date getFinicio() {
		return finicio;
	}
	public void setFinicio(Date finicio) {
		this.finicio = finicio;
	}
	public Date getFfin() {
		return ffin;
	}
	public void setFfin(Date ffin) {
		this.ffin = ffin;
	}
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getIdEntrega() {
		return idEntrega;
	}
	public void setIdEntrega(String idEntrega) {
		this.idEntrega = idEntrega;
	}
	public Long getDocto() {
		return docto;
	}
	public void setDocto(Long docto) {
		this.docto = docto;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Integer getClave() {
		return clave;
	}
	public void setClave(Integer clave) {
		this.clave = clave;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getColonia() {
		return colonia;
	}
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	public String getIdhorario() {
		return idhorario;
	}
	public void setIdhorario(String idhorario) {
		this.idhorario = idhorario;
	}
	public String getTipoRuta() {
		return tipoRuta;
	}
	public void setTipoRuta(String tipoRuta) {
		this.tipoRuta = tipoRuta;
	}
	public String getStDocto() {
		return stDocto;
	}
	public void setStDocto(String stDocto) {
		this.stDocto = stDocto;
	}
	public String getPartida() {
		return partida;
	}
	public void setPartida(String partida) {
		this.partida = partida;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	public long getFactura() {
		return factura;
	}
	public void setFactura(long factura) {
		this.factura = factura;
	}
	public Date getFEE() {
		return FEE;
	}
	public void setFEE(Date fEE) {
		FEE = fEE;
	}
	public double getMonto() {
		return monto;
	}
	public void setMonto(double monto) {
		this.monto = monto;
	}
	public String getRealizadoTxt() {
		return realizadoTxt;
	}
	public void setRealizadoTxt(String realizadoTxt) {
		this.realizadoTxt = realizadoTxt;
	}
	public Boolean getConforme() {
		return conforme;
	}
	public void setConforme(Boolean conforme) {
		this.conforme = conforme;
	}
	public String getRazonesEntrega() {
		return razonesEntrega;
	}
	public void setRazonesEntrega(String razonesEntrega) {
		this.razonesEntrega = razonesEntrega;
	}
	public String getIdRuta() {
		return idRuta;
	}
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}
	public Boolean getAceptaEyR() {
		return aceptaEyR;
	}
	public void setAceptaEyR(Boolean aceptaEyR) {
		this.aceptaEyR = aceptaEyR;
	}
	public Boolean getEntregaRevision() {
		return entregaRevision;
	}
	public void setEntregaRevision(Boolean entregaRevision) {
		this.entregaRevision = entregaRevision;
	}
	/**
	 * @return the facturaORemision
	 */
	public Boolean getFacturaORemision() {
		return facturaORemision;
	}
	/**
	 * @param facturaORemision the facturaORemision to set
	 */
	public void setFacturaORemision(Boolean facturaORemision) {
		this.facturaORemision = facturaORemision;
	}
	/**
	 * @return the actualizar
	 */
	public Boolean getActualizar() {
		return actualizar;
	}
	/**
	 * @param actualizar the actualizar to set
	 */
	public void setActualizar(Boolean actualizar) {
		this.actualizar = actualizar;
	}
	
}
