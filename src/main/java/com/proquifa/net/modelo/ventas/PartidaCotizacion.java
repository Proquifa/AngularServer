package com.proquifa.net.modelo.ventas;

import java.util.Date;

import com.proquifa.net.modelo.comun.Producto;

public class PartidaCotizacion {
	
	private Long idPartidaCotizacion;
	private String cotizacion;
	private Long partida;
	private Integer cantidad;
	private String codigo;
	private Float precio;
	private String concepto;
	private String estado;
	private Double iva;
	private Float costo;
	private String fabrica;
	private String nota;
	private String clasificacion;
	private Date horaEnvio;
	private String medioEnvio;
	private Long folio;
	private String observaE;
	private Date hCancelacion;
	private Date fechaGeneracion;
	private Long indicePrecio;
	private String presentacion;
	private String unidades;
	private String tiempoEntrega;
	private Boolean fueraSistema;
	private Float precioInvestigacion;
	private String notaCancelacion;
	private String situacion;
	private String cargosEnviosAdicionales;
	private String hieloSeco;
	private String comentariosAdicionales;
	private String rechazoESAC;
	private String rechazoPharma;
	private String enSTOCK;
	private String ultimaEdicion;
	private String costosAdicionales;

	//Extras
	private Producto producto;
	
	private String tipo;
	private String descripcion;
	private Boolean variasMarcas;
	private String destino;
	private String clasifOrigen;
	private String clasifFinal;
	private String tiempoProceo;
	private Boolean conforme;
	private Float importe;
	private String comentariosRequisicion;
	private Float total;
	private String estadoPedido;
	private String cliente;
	private String cotizo;
	private String TotalProceso;
	private Long idProducto;
	private Float precioDolarProducto;
	
	/***/
	private Integer idCotizacion;
	private Integer idPCotizaOrigen;
	private int num;
	//Formato String para JasperReports
	private String importeFormato;
	private String precioFormato;
	private Long idAutorizacion;
	/**
	 * 
	 */
	public PartidaCotizacion() {
		super();
	}
	
	/**
	 * 
	 */
	public PartidaCotizacion(Cotizacion cotiza, Producto producto) {
		super();
		this.cotizacion = cotiza.getFolioCotizacion();
		this.idCotizacion = cotiza.getIdCotizacion().intValue();
		this.producto = producto;
		this.clasificacion = producto.getIdProducto() > 0 ? "A" : "C";
		this.folio = 0L;
		this.estado = "Cotizaci��n";
		this.nota = "";
		this.indicePrecio = 0L;
		this.tiempoEntrega = "";
		this.precioInvestigacion = 0F;
		this.iva = 0D;
		this.idPCotizaOrigen = 0;
		this.descripcion = producto.getDescripcion();
	}

	/**
	 * @return the idPartidaCotizacion
	 */
	public Long getIdPartidaCotizacion() {
		return idPartidaCotizacion;
	}
	/**
	 * @param idPartidaCotizacion the idPartidaCotizacion to set
	 */
	public void setIdPartidaCotizacion(Long idPartidaCotizacion) {
		this.idPartidaCotizacion = idPartidaCotizacion;
	}
	/**
	 * @return the cotizacion
	 */
	public String getCotizacion() {
		return cotizacion;
	}
	/**
	 * @param cotizacion the cotizacion to set
	 */
	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}
	/**
	 * @return the partida
	 */
	public Long getPartida() {
		return partida;
	}
	/**
	 * @param partida the partida to set
	 */
	public void setPartida(Long partida) {
		this.partida = partida;
	}
	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the precio
	 */
	public Float getPrecio() {
		return precio;
	}
	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	/**
	 * @return the concepto
	 */
	public String getConcepto() {
		return concepto;
	}
	/**
	 * @param concepto the concepto to set
	 */
	public void setConcepto(String concepto) {
		this.concepto = concepto;
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
	 * @return the iva
	 */
	public Double getIva() {
		return iva;
	}
	/**
	 * @param iva the iva to set
	 */
	public void setIva(Double iva) {
		this.iva = iva;
	}
	/**
	 * @return the costo
	 */
	public Float getCosto() {
		return costo;
	}
	/**
	 * @param costo the costo to set
	 */
	public void setCosto(Float costo) {
		this.costo = costo;
	}
	/**
	 * @return the fabrica
	 */
	public String getFabrica() {
		return fabrica;
	}
	/**
	 * @param fabrica the fabrica to set
	 */
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}
	/**
	 * @return the nota
	 */
	public String getNota() {
		return nota;
	}
	/**
	 * @param nota the nota to set
	 */
	public void setNota(String nota) {
		this.nota = nota;
	}
	/**
	 * @return the clasificacion
	 */
	public String getClasificacion() {
		return clasificacion;
	}
	/**
	 * @param clasificacion the clasificacion to set
	 */
	public void setClasificacion(String clasificacion) {
		this.clasificacion = clasificacion;
	}
	/**
	 * @return the horaEnvio
	 */
	public Date getHoraEnvio() {
		return horaEnvio;
	}
	/**
	 * @param horaEnvio the horaEnvio to set
	 */
	public void setHoraEnvio(Date horaEnvio) {
		this.horaEnvio = horaEnvio;
	}
	/**
	 * @return the medioEnvio
	 */
	public String getMedioEnvio() {
		return medioEnvio;
	}
	/**
	 * @param medioEnvio the medioEnvio to set
	 */
	public void setMedioEnvio(String medioEnvio) {
		this.medioEnvio = medioEnvio;
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
	/**
	 * @return the observaE
	 */
	public String getObservaE() {
		return observaE;
	}
	/**
	 * @param observaE the observaE to set
	 */
	public void setObservaE(String observaE) {
		this.observaE = observaE;
	}
	/**
	 * @return the hCancelacion
	 */
	public Date getHCancelacion() {
		return hCancelacion;
	}
	/**
	 * @param cancelacion the hCancelacion to set
	 */
	public void setHCancelacion(Date cancelacion) {
		hCancelacion = cancelacion;
	}
	/**
	 * @return the fechaGeneracion
	 */
	public Date getFechaGeneracion() {
		return fechaGeneracion;
	}
	/**
	 * @param fechaGeneracion the fechaGeneracion to set
	 */
	public void setFechaGeneracion(Date fechaGeneracion) {
		this.fechaGeneracion = fechaGeneracion;
	}
	/**
	 * @return the indicePrecio
	 */
	public Long getIndicePrecio() {
		return indicePrecio;
	}
	/**
	 * @param indicePrecio the indicePrecio to set
	 */
	public void setIndicePrecio(Long indicePrecio) {
		this.indicePrecio = indicePrecio;
	}
	/**
	 * @return the presentacion
	 */
	public String getPresentacion() {
		return presentacion;
	}
	/**
	 * @param presentacion the presentacion to set
	 */
	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}
	/**
	 * @return the unidades
	 */
	public String getUnidades() {
		return unidades;
	}
	/**
	 * @param unidades the unidades to set
	 */
	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}
	/**
	 * @return the tiempoEntrega
	 */
	public String getTiempoEntrega() {
		return tiempoEntrega;
	}
	/**
	 * @param tiempoEntrega the tiempoEntrega to set
	 */
	public void setTiempoEntrega(String tiempoEntrega) {
		this.tiempoEntrega = tiempoEntrega;
	}
	/**
	 * @return the fueraSistema
	 */
	public Boolean getFueraSistema() {
		return fueraSistema;
	}
	/**
	 * @param fueraSistema the fueraSistema to set
	 */
	public void setFueraSistema(Boolean fueraSistema) {
		this.fueraSistema = fueraSistema;
	}
	/**
	 * @return the precioInvestigacion
	 */
	public Float getPrecioInvestigacion() {
		return precioInvestigacion;
	}
	/**
	 * @param precioInvestigacion the precioInvestigacion to set
	 */
	public void setPrecioInvestigacion(Float precioInvestigacion) {
		this.precioInvestigacion = precioInvestigacion;
	}
	/**
	 * @return the notaCancelacion
	 */
	public String getNotaCancelacion() {
		return notaCancelacion;
	}
	/**
	 * @param notaCancelacion the notaCancelacion to set
	 */
	public void setNotaCancelacion(String notaCancelacion) {
		this.notaCancelacion = notaCancelacion;
	}
	/**
	 * @return the situacion
	 */
	public String getSituacion() {
		return situacion;
	}
	/**
	 * @param situacion the situacion to set
	 */
	public void setSituacion(String situacion) {
		this.situacion = situacion;
	}
	/**
	 * @return the cargosEnviosAdicionales
	 */
	public String getCargosEnviosAdicionales() {
		return cargosEnviosAdicionales;
	}
	/**
	 * @param cargosEnviosAdicionales the cargosEnviosAdicionales to set
	 */
	public void setCargosEnviosAdicionales(String cargosEnviosAdicionales) {
		this.cargosEnviosAdicionales = cargosEnviosAdicionales;
	}
	/**
	 * @return the hieloSeco
	 */
	public String getHieloSeco() {
		return hieloSeco;
	}
	/**
	 * @param hieloSeco the hieloSeco to set
	 */
	public void setHieloSeco(String hieloSeco) {
		this.hieloSeco = hieloSeco;
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
	 * @return the rechazoESAC
	 */
	public String getRechazoESAC() {
		return rechazoESAC;
	}
	/**
	 * @param rechazoESAC the rechazoESAC to set
	 */
	public void setRechazoESAC(String rechazoESAC) {
		this.rechazoESAC = rechazoESAC;
	}
	/**
	 * @return the rechazoPharma
	 */
	public String getRechazoPharma() {
		return rechazoPharma;
	}
	/**
	 * @param rechazoPharma the rechazoPharma to set
	 */
	public void setRechazoPharma(String rechazoPharma) {
		this.rechazoPharma = rechazoPharma;
	}
	/**
	 * @return the enSTOCK
	 */
	public String getEnSTOCK() {
		return enSTOCK;
	}
	/**
	 * @param enSTOCK the enSTOCK to set
	 */
	public void setEnSTOCK(String enSTOCK) {
		this.enSTOCK = enSTOCK;
	}
	/**
	 * @return the ultimaEdicion
	 */
	public String getUltimaEdicion() {
		return ultimaEdicion;
	}
	/**
	 * @param ultimaEdicion the ultimaEdicion to set
	 */
	public void setUltimaEdicion(String ultimaEdicion) {
		this.ultimaEdicion = ultimaEdicion;
	}
	/**
	 * @return the costosAdicionales
	 */
	public String getCostosAdicionales() {
		return costosAdicionales;
	}
	/**
	 * @param costosAdicionales the costosAdicionales to set
	 */
	public void setCostosAdicionales(String costosAdicionales) {
		this.costosAdicionales = costosAdicionales;
	}
	/**
	 * @return the producto
	 */
	public Producto getProducto() {
		return producto;
	}
	/**
	 * @param producto the producto to set
	 */
	public void setProducto(Producto producto) {
		this.producto = producto;
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
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the variasMarcas
	 */
	public Boolean getVariasMarcas() {
		return variasMarcas;
	}
	/**
	 * @param variasMarcas the variasMarcas to set
	 */
	public void setVariasMarcas(Boolean variasMarcas) {
		this.variasMarcas = variasMarcas;
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
	 * @return the clasifOrigen
	 */
	public String getClasifOrigen() {
		return clasifOrigen;
	}
	/**
	 * @param clasifOrigen the clasifOrigen to set
	 */
	public void setClasifOrigen(String clasifOrigen) {
		this.clasifOrigen = clasifOrigen;
	}
	/**
	 * @return the clasifFinal
	 */
	public String getClasifFinal() {
		return clasifFinal;
	}
	/**
	 * @param clasifFinal the clasifFinal to set
	 */
	public void setClasifFinal(String clasifFinal) {
		this.clasifFinal = clasifFinal;
	}
	/**
	 * @return the tiempoProceo
	 */
	public String getTiempoProceo() {
		return tiempoProceo;
	}
	/**
	 * @param tiempoProceo the tiempoProceo to set
	 */
	public void setTiempoProceo(String tiempoProceo) {
		this.tiempoProceo = tiempoProceo;
	}
	/**
	 * @return the conforme
	 */
	public Boolean getConforme() {
		return conforme;
	}
	/**
	 * @param conforme the conforme to set
	 */
	public void setConforme(Boolean conforme) {
		this.conforme = conforme;
	}
	/**
	 * @return the importe
	 */
	public Float getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Float importe) {
		this.importe = importe;
	}
	/**
	 * @return the comentariosRequisicion
	 */
	public String getComentariosRequisicion() {
		return comentariosRequisicion;
	}
	/**
	 * @param comentariosRequisicion the comentariosRequisicion to set
	 */
	public void setComentariosRequisicion(String comentariosRequisicion) {
		this.comentariosRequisicion = comentariosRequisicion;
	}
	/**
	 * @return the total
	 */
	public Float getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Float total) {
		this.total = total;
	}
	/**
	 * @return the estadoPedido
	 */
	public String getEstadoPedido() {
		return estadoPedido;
	}
	/**
	 * @param estadoPedido the estadoPedido to set
	 */
	public void setEstadoPedido(String estadoPedido) {
		this.estadoPedido = estadoPedido;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCotizo(String cotizo) {
		this.cotizo = cotizo;
	}
	public String getCotizo() {
		return cotizo;
	}
	public void setTotalProceso(String totalProceso) {
		TotalProceso = totalProceso;
	}
	public String getTotalProceso() {
		return TotalProceso;
	}

	/**
	 * @return the idCotizacion
	 */
	public Integer getIdCotizacion() {
		return idCotizacion;
	}

	/**
	 * @param idCotizacion the idCotizacion to set
	 */
	public void setIdCotizacion(Integer idCotizacion) {
		this.idCotizacion = idCotizacion;
	}

	/**
	 * @return the idPCotizaOrigen
	 */
	public Integer getIdPCotizaOrigen() {
		return idPCotizaOrigen;
	}

	/**
	 * @param idPCotizaOrigen the idPCotizaOrigen to set
	 */
	public void setIdPCotizaOrigen(Integer idPCotizaOrigen) {
		this.idPCotizaOrigen = idPCotizaOrigen;
	}

	/**
	 * @return the num
	 */
	public int getNum() {
		return num;
	}

	/**
	 * @param num the num to set
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * @return the importeFormato
	 */
	public String getImporteFormato() {
		return importeFormato;
	}

	/**
	 * @param importeFormato the importeFormato to set
	 */
	public void setImporteFormato(String importeFormato) {
		this.importeFormato = importeFormato;
	}

	/**
	 * @return the precioFormato
	 */
	public String getPrecioFormato() {
		return precioFormato;
	}

	/**
	 * @param precioFormato the precioFormato to set
	 */
	public void setPrecioFormato(String precioFormato) {
		this.precioFormato = precioFormato;
	}

	public Long getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}

	public Float getPrecioDolarProducto() {
		return precioDolarProducto;
	}

	public void setPrecioDolarProducto(Float precioDolarProducto) {
		this.precioDolarProducto = precioDolarProducto;
	}

	public Long getIdAutorizacion() {
		return idAutorizacion;
	}

	public void setIdAutorizacion(Long idAutorizacion) {
		this.idAutorizacion = idAutorizacion;
	}
}