/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;


/**
 * @author ernestogonzalezlozada
 *
 */

public class Proveedor {

	private Long clave;
	private String moneda;
	private String rfc;
	private Long idProveedor;
	private String nombre;
	private String RSCalle;
	private String zona;
	private String CPago;
	private String Pais;
	private String Observa;
	private String calle;
	private String segundaDireccion;
	private String delegacion;
	private String estado;
	private String cp;
	private String numCliente;
	private Boolean habilitado;
	private Boolean cheque;
	private Boolean transferencia;
	private Boolean tarjeta;
	private Boolean deposito;
	private Integer piezasStock;
	private Date fechaActualizacion;
	private String descripcionAmpliada;
	private String razonSocial;
	private String ciudad;
	private Long adeudosConocidos;
	private Long facturas;
	private Float morosidad;
	private Boolean socioComercial;
	private String imagen;
	private byte [] bytes; 
	private Referencia referencia;
	private Date FUAProductos;
	private String monedaCompra;
	private String monedaVenta;
	private Double tipoCambio;
	private Date FUATipoCambio;
	private Boolean cambioEnTipoCambio;
	private Long pagador;
	private Long comprador;
	private Boolean colocarPhs;
	private String objectivoCrecimiento;
	private String objetivoCrecimientoFundamental;
	private Double montoAnualAnterior;
	private Double objetivoTranscurrido;
	private Double objetivoMontoTranscurrido;
	private Long ranking;
	private String rol;
	private Long inspector;
	private int mesInicioFiscal;
	
	private String taxId = "";
	
	private Boolean compraLinea;
	private int clavePais;
	
	
	//Configuracion precio
	
	private int familias;
	private int productos;
	
	private Boolean precioWeb;
	private String usuario;
	private String password;
	private String pagina;
	private Boolean aplicaRecoleccion;
	private String nombrePaisIngles;
;
	
	/**
	 * @return the familias
	 */
	public int getFamilias() {
		return familias;
	}
	/**
	 * @param familias the familias to set
	 */
	public void setFamilias(int familias) {
		this.familias = familias;
	}
	/**
	 * @return the productos
	 */
	public int getProductos() {
		return productos;
	}
	/**
	 * @param productos the productos to set
	 */
	public void setProductos(int productos) {
		this.productos = productos;
	}
	/**
	 * @return the clave
	 */
	public Long getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(Long clave) {
		this.clave = clave;
	}
	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}
	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the rSCalle
	 */
	public String getRSCalle() {
		return RSCalle;
	}
	/**
	 * @param calle the rSCalle to set
	 */
	public void setRSCalle(String calle) {
		RSCalle = calle;
	}
	/**
	 * @return the segundaDireccion
	 */
	public String getSegundaDireccion() {
		return segundaDireccion;
	}
	/**
	 * @param segundaDireccion the segundaDireccion to set
	 */
	public void setSegundaDireccion(String segundaDireccion) {
		this.segundaDireccion = segundaDireccion;
	}
	/**
	 * @return the zona
	 */
	public String getZona() {
		return zona;
	}
	/**
	 * @param zona the zona to set
	 */
	public void setZona(String zona) {
		this.zona = zona;
	}
	/**
	 * @return the cPago
	 */
	public String getCPago() {
		return CPago;
	}
	/**
	 * @param pago the cPago to set
	 */
	public void setCPago(String pago) {
		CPago = pago;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return Pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		Pais = pais;
	}
	/**
	 * @return the observa
	 */
	public String getObserva() {
		return Observa;
	}
	/**
	 * @param observa the observa to set
	 */
	public void setObserva(String observa) {
		Observa = observa;
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
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}
	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	/**
	 * @return the numCliente
	 */
	public String getNumCliente() {
		return numCliente;
	}
	/**
	 * @param numCliente the numCliente to set
	 */
	public void setNumCliente(String numCliente) {
		this.numCliente = numCliente;
	}
	/**
	 * @return the habilitado
	 */
	public Boolean getHabilitado() {
		return habilitado;
	}
	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(Boolean habilitado) {
		this.habilitado = habilitado;
	}
	/**
	 * @return the cheque
	 */
	public Boolean getCheque() {
		return cheque;
	}
	/**
	 * @param cheque the cheque to set
	 */
	public void setCheque(Boolean cheque) {
		this.cheque = cheque;
	}
	/**
	 * @return the transferencia
	 */
	public Boolean getTransferencia() {
		return transferencia;
	}
	/**
	 * @param transferencia the transferencia to set
	 */
	public void setTransferencia(Boolean transferencia) {
		this.transferencia = transferencia;
	}
	/**
	 * @return the tarjeta
	 */
	public Boolean getTarjeta() {
		return tarjeta;
	}
	/**
	 * @param tarjeta the tarjeta to set
	 */
	public void setTarjeta(Boolean tarjeta) {
		this.tarjeta = tarjeta;
	}
	/**
	 * @return the deposito
	 */
	public Boolean getDeposito() {
		return deposito;
	}
	/**
	 * @param deposito the deposito to set
	 */
	public void setDeposito(Boolean deposito) {
		this.deposito = deposito;
	}
	/**
	 * @return the piezasStock
	 */
	public Integer getPiezasStock() {
		return piezasStock;
	}
	/**
	 * @param piezasStock the piezasStock to set
	 */
	public void setPiezasStock(Integer piezasStock) {
		this.piezasStock = piezasStock;
	}
	/**
	 * @return the fechaActualizacion
	 */
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	/**
	 * @param fechaActualizacion the fechaActualizacion to set
	 */
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	/**
	 * @return the descripcionAmpliada
	 */
	public String getDescripcionAmpliada() {
		return descripcionAmpliada;
	}
	/**
	 * @param descripcionAmpliada the descripcionAmpliada to set
	 */
	public void setDescripcionAmpliada(String descripcionAmpliada) {
		this.descripcionAmpliada = descripcionAmpliada;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the adeudosConocidos
	 */
	public Long getAdeudosConocidos() {
		return adeudosConocidos;
	}
	/**
	 * @param adeudosConocidos the adeudosConocidos to set
	 */
	public void setAdeudosConocidos(Long adeudosConocidos) {
		this.adeudosConocidos = adeudosConocidos;
	}
	/**
	 * @return the facturas
	 */
	public Long getFacturas() {
		return facturas;
	}
	/**
	 * @param facturas the facturas to set
	 */
	public void setFacturas(Long facturas) {
		this.facturas = facturas;
	}
	/**
	 * @return the morosidad
	 */
	public Float getMorosidad() {
		return morosidad;
	}
	/**
	 * @param morosidad the morosidad to set
	 */
	public void setMorosidad(Float morosidad) {
		this.morosidad = morosidad;
	}
	/**
	 * @return the socioComercial
	 */
	public Boolean getSocioComercial() {
		return socioComercial;
	}
	/**
	 * @param socioComercial the socioComercial to set
	 */
	public void setSocioComercial(Boolean socioComercial) {
		this.socioComercial = socioComercial;
	}
	/**
	 * @return the imagen
	 */
	public String getImagen() {
		return imagen;
	}
	/**
	 * @param imagen the imagen to set
	 */
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
		/**
	 * @return the referencia
	 */
	public Referencia getReferencia() {
		return referencia;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(Referencia referencia) {
		this.referencia = referencia;
	}
	/**
	 * @return the fUAProductos
	 */
	public Date getFUAProductos() {
		return FUAProductos;
	}
	/**
	 * @param fUAProductos the fUAProductos to set
	 */
	public void setFUAProductos(Date fUAProductos) {
		FUAProductos = fUAProductos;
	}
	public String getMonedaCompra() {
		return monedaCompra;
	}
	public void setMonedaCompra(String monedaCompra) {
		this.monedaCompra = monedaCompra;
	}
	public String getMonedaVenta() {
		return monedaVenta;
	}
	public void setMonedaVenta(String monedaVenta) {
		this.monedaVenta = monedaVenta;
	}
	/**
	 * @return the tipoCambio
	 */
	public Double getTipoCambio() {
		return tipoCambio;
	}
	/**
	 * @param tipoCambio the tipoCambio to set
	 */
	public void setTipoCambio(Double tipoCambio) {
		this.tipoCambio = tipoCambio;
	}
	/**
	 * @return the fUATipoCambio
	 */
	public Date getFUATipoCambio() {
		return FUATipoCambio;
	}
	/**
	 * @param fUATipoCambio the fUATipoCambio to set
	 */
	public void setFUATipoCambio(Date fUATipoCambio) {
		FUATipoCambio = fUATipoCambio;
	}
	/**
	 * @return the bytes
	 */
	public byte [] getBytes() {
		return bytes;
	}
	/**
	 * @param bytes the bytes to set
	 */
	public void setBytes(byte [] bytes) {
		this.bytes = bytes;
	}
	/**
	 * @return the cambioEnTipoCambio
	 */
	public Boolean getCambioEnTipoCambio() {
		return cambioEnTipoCambio;
	}
	/**
	 * @param cambioEnTipoCambio the cambioEnTipoCambio to set
	 */
	public void setCambioEnTipoCambio(Boolean cambioEnTipoCambio) {
		this.cambioEnTipoCambio = cambioEnTipoCambio;
	}
	/**
	 * @return the pagador
	 */
	public Long getPagador() {
		return pagador;
	}
	/**
	 * @param pagador the pagador to set
	 */
	public void setPagador(Long pagador) {
		this.pagador = pagador;
	}
	/**
	 * @return the comprador
	 */
	public Long getComprador() {
		return comprador;
	}
	/**
	 * @param comprador the comprador to set
	 */
	public void setComprador(Long comprador) {
		this.comprador = comprador;
	}
	/**
	 * @return the colocarPhs
	 */
	public Boolean getColocarPhs() {
		return colocarPhs;
	}
	/**
	 * @param colocarPhs the colocarPhs to set
	 */
	public void setColocarPhs(Boolean colocarPhs) {
		this.colocarPhs = colocarPhs;
	}
	/**
	 * @return the objectivoCrecimiento
	 */
	public String getObjectivoCrecimiento() {
		return objectivoCrecimiento;
	}
	/**
	 * @param objectivoCrecimiento the objectivoCrecimiento to set
	 */
	public void setObjectivoCrecimiento(String objectivoCrecimiento) {
		this.objectivoCrecimiento = objectivoCrecimiento;
	}
	/**
	 * @return objectivoCrecimientoFundamental
	 */
	public String getObjetivoCrecimientoFundamental() {
		return objetivoCrecimientoFundamental;
	}
	/**
	 * @param objectivoCrecimientoFundamental
	 */
	public void setObjetivoCrecimientoFundamental(
			String objectivoCrecimientoFundamental) {
		this.objetivoCrecimientoFundamental = objectivoCrecimientoFundamental;
	}
	/**
	 * @return the montoAnualAnterior
	 */
	public Double getMontoAnualAnterior() {
		return montoAnualAnterior;
	}
	/**
	 * @param montoAnualAnterior the montoAnualAnterior to set
	 */
	public void setMontoAnualAnterior(Double montoAnualAnterior) {
		this.montoAnualAnterior = montoAnualAnterior;
	}
	/**
	 * @return the objetivoTranscurrido
	 */
	public Double getObjetivoTranscurrido() {
		return objetivoTranscurrido;
	}
	/**
	 * @param objetivoTranscurrido the objetivoTranscurrido to set
	 */
	public void setObjetivoTranscurrido(Double objetivoTranscurrido) {
		this.objetivoTranscurrido = objetivoTranscurrido;
	}
	/**
	 * @return the objetivoMontoTranscurrido
	 */
	public Double getObjetivoMontoTranscurrido() {
		return objetivoMontoTranscurrido;
	}
	/**
	 * @param objetivoMontoTranscurrido the objetivoMontoTranscurrido to set
	 */
	public void setObjetivoMontoTranscurrido(Double objetivoMontoTranscurrido) {
		this.objetivoMontoTranscurrido = objetivoMontoTranscurrido;
	}
	/**
	 * @return the ranking
	 */
	public Long getRanking() {
		return ranking;
	}
	/**
	 * @param ranking the ranking to set
	 */
	public void setRanking(Long ranking) {
		this.ranking = ranking;
	}
	/**
	 * @return the rol
	 */
	public String getRol() {
		return rol;
	}
	/**
	 * @param rol the rol to set
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}
	public Long getInspector() {
		return inspector;
	}
	public void setInspector(Long inspector) {
		this.inspector = inspector;
	}
	public int getMesInicioFiscal() {
		return mesInicioFiscal;
	}
	public void setMesInicioFiscal(int mesInicioFiscal) {
		this.mesInicioFiscal = mesInicioFiscal;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	/**
	 * @return the precioWeb
	 */
	public Boolean getPrecioWeb() {
		return precioWeb;
	}
	/**
	 * @param precioWeb the precioWeb to set
	 */
	public void setPrecioWeb(Boolean precioWeb) {
		this.precioWeb = precioWeb;
	}
	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}
	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the pagina
	 */
	public String getPagina() {
		return pagina;
	}
	/**
	 * @param pagina the pagina to set
	 */
	public void setPagina(String pagina) {
		this.pagina = pagina;
	}
	/**
	 * @return the compraLinea
	 */
	public Boolean isCompraLinea() {
		return compraLinea;
	}
	/**
	 * @param compraLinea the compraLinea to set
	 */
	public void setCompraLinea(Boolean compraLinea) {
		this.compraLinea = compraLinea;
	}
	public Boolean isAplicaRecoleccion() {
		return aplicaRecoleccion;
	}
	public void setAplicaRecoleccion(Boolean aplicaRecoleccion) {
		this.aplicaRecoleccion = aplicaRecoleccion;
	}
	public int getClavePais() {
		return clavePais;
	}
	public void setClavePais(int clavePais) {
		this.clavePais = clavePais;
	}
	public String getNombrePaisIngles() {
		return nombrePaisIngles;
	}
	public void setNombrePaisIngles(String nombrePaisIngles) {
		this.nombrePaisIngles = nombrePaisIngles;
	}
	/**
	 * @return the rfc
	 */
	public String getRfc() {
		return rfc;
	}
	/**
	 * @param rfc the rfc to set
	 */
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	
	
}