package com.proquifa.net.modelo.despachos;

public class EmbalarPedido {

	
	private Integer idEmbalarPedido;
	private Integer  idPedido;
	private Integer  pedido;
	private Integer idProducto;
	private Integer usuario;
	private String lote;
	private String fechaEmbalado;
	private Integer Tiempo;
	private Integer piezas;
	private String bolsa;
	private String Prioridad;
	private String folioEmpaque;
	private String estado;
	private String folio;
	private String manejo;
	private String bolsaInspeccion;
	private String folioTemporal;
	private String cant;
	private String ubicacion;	
	private String concepto;
	private String folioEtiqueta;
	private String videoPartida;
	private String cliente;
	private String contacto;
	
	private boolean remisionar;
	
	private String comentariosEntrega;
	private String condicionAlmacenaje;
	private String fee;
	private int guiaCliente;
	
	
	/**
	 * 
	 */
	public EmbalarPedido() {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * @param idEmbalarPedido
	 * @param idPedido
	 * @param usuario
	 * @param estado
	 */
	public EmbalarPedido(Integer idPedido, Integer usuario, String estado) {
		super();
		this.idPedido = idPedido;
		this.usuario = usuario;
		this.estado = estado;
	}

	public String getFolioTemporal() {
		return folioTemporal;
	}
	
	public void setFolioTemporal(String folioTemporal) {
		this.folioTemporal = folioTemporal;
	}
	
	public Integer getIdEmbalarPedido() {
		return idEmbalarPedido;
	}
	
	public void setIdEmbalarPedido(Integer idEmbalarPedido) {
		this.idEmbalarPedido = idEmbalarPedido;
	}
	
	public String getBolsaInspeccion() {
		return bolsaInspeccion;
	}
	
	public void setBolsaInspeccion(String bolsaInspeccion) {
		this.bolsaInspeccion = bolsaInspeccion;
	}

	public Integer getIdPedido() {
		return idPedido;
	}
	
	public void setIdPedido(Integer idPedido) {
		this.idPedido = idPedido;
	}
	
	public Integer getIdProducto() {
		return idProducto;
	}
	
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
	public Integer getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}
	
	public String getLote() {
		return lote;
	}
	
	public void setLote(String lote) {
		this.lote = lote;
	}
	
	public String getFechaEmbalado() {
		return fechaEmbalado;
	}
	
	public void setFechaEmbalado(String fechaEmbalado) {
		this.fechaEmbalado = fechaEmbalado;
	}
	
	public Integer getTiempo() {
		return Tiempo;
	}
	
	public void setTiempo(Integer tiempo) {
		Tiempo = tiempo;
	}
	
	public Integer getPiezas() {
		return piezas;
	}
	
	public void setPiezas(Integer piezas) {
		this.piezas = piezas;
	}
	
	public String getBolsa() {
		return bolsa;
	}
	
	public void setBolsa(String bolsa) {
		this.bolsa = bolsa;
	}
	
	public String getPrioridad() {
		return Prioridad;
	}
	
	public void setPrioridad(String prioridad) {
		Prioridad = prioridad;
	}
	
	public String getFolioEmpaque() {
		return folioEmpaque;
	}
	
	public void setFolioEmpaque(String folioEmpaque) {
		this.folioEmpaque = folioEmpaque;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getFolio() {
		return folio;
	}
	
	public void setFolio(String folio) {
		this.folio = folio;
	}
	
	public String getManejo() {
		return manejo;
	}
	
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}
	
	public String getCant() {
		return cant;
	}
	
	public void setCant(String cant) {
		this.cant = cant;
	}
	
	public String getUbicacion() {
		return ubicacion;
	}
	
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}
	
	public String getConcepto() {
		return concepto;
	}
	
	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}
	
	public String getFolioEtiqueta() {
		return folioEtiqueta;
	}
	
	public void setFolioEtiqueta(String folioEtiqueta) {
		this.folioEtiqueta = folioEtiqueta;
	}

	public String getVideoPartida() {
		return videoPartida;
	}

	public void setVideoPartida(String videoPartida) {
		this.videoPartida = videoPartida;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}


	/**
	 * @return the remisionar
	 */
	public boolean isRemisionar() {
		return remisionar;
	}


	/**
	 * @param remisionar the remisionar to set
	 */
	public void setRemisionar(boolean remisionar) {
		this.remisionar = remisionar;
	}


	/**
	 * @return the comentariosEntrega
	 */
	public String getComentariosEntrega() {
		return comentariosEntrega;
	}


	/**
	 * @param comentariosEntrega the comentariosEntrega to set
	 */
	public void setComentariosEntrega(String comentariosEntrega) {
		this.comentariosEntrega = comentariosEntrega;
	}


	/**
	 * @return the condicionAlmacenaje
	 */
	public String getCondicionAlmacenaje() {
		return condicionAlmacenaje;
	}


	/**
	 * @param condicionAlmacenaje the condicionAlmacenaje to set
	 */
	public void setCondicionAlmacenaje(String condicionAlmacenaje) {
		this.condicionAlmacenaje = condicionAlmacenaje;
	}


	public String getFee() {
		return fee;
	}


	public void setFee(String fee) {
		this.fee = fee;
	}


	/**
	 * @return the guiaCliente
	 */
	public int getGuiaCliente() {
		return guiaCliente;
	}


	/**
	 * @param guiaCliente the guiaCliente to set
	 */
	public void setGuiaCliente(int guiaCliente) {
		this.guiaCliente = guiaCliente;
	}


	/**
	 * @return the pedido
	 */
	public Integer getPedido() {
		return pedido;
	}


	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(Integer pedido) {
		this.pedido = pedido;
	}	
	
}
