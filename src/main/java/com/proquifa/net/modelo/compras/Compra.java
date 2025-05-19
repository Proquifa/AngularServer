/**
 * 
 */
package com.proquifa.net.modelo.compras;
import java.util.Date;

/**
 * @author sarivera
 *
 */
public class Compra {
	private String clave;
	private Date fecha;   	//fecha de colocacion
	private Long proveedor;
	private String estado;
	private Boolean almacenUE;
	private Boolean almacenUSA;
	private Boolean almacenMatriz;
	private String primerAlmacen;
	private String tiempoRespuesta;
	private String empresa; //Compro
	private Double montoTotal;
	private String moneda;
	private Double montoTotalDolares;
	private Double tipoCambio;
	private String nombreProveedor;
	private String colocarDesde;
	private Date fechaConfirmacion;
	private Integer totalPiezas;
	private Integer totalPiezasMexico;
	private Integer totalCanceladas;
	private Integer totalRecibidas;
	private Integer totalRecibidasPHS;
	private Long idPCompra;
	private String comprador;
	private Long totalPartidas;
	private String fabrica;
	private String abierto_cerrado;
	private String inTime;
	private Double montoTotalDolares_partidas;
	private Integer totalPiezas_partidas;
	private Boolean envio;
	private Double iva;
	private String medioPago;
	private String tipoPendiente;
	private String folioRMT;
	private String responsable;
	private String paisProveedor;
	private Long idEmpresa;
	private boolean ingles;
	private boolean publicacion;
	
	/**
	 * @return the clave
	 */
	public String getClave() {
		return clave;
	}
	/**
	 * @param clave the clave to set
	 */
	public void setClave(String clave) {
		this.clave = clave;
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
	 * @return the proveedor
	 */
	public Long getProveedor() {
		return proveedor;
	}
	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Long proveedor) {
		this.proveedor = proveedor;
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
	 * @return the almacenUE
	 */
	public Boolean getAlmacenUE() {
		return almacenUE;
	}
	/**
	 * @param almacenUE the almacenUE to set
	 */
	public void setAlmacenUE(Boolean almacenUE) {
		this.almacenUE = almacenUE;
	}
	/**
	 * @return the almacenUSA
	 */
	public Boolean getAlmacenUSA() {
		return almacenUSA;
	}
	/**
	 * @param almacenUSA the almacenUSA to set
	 */
	public void setAlmacenUSA(Boolean almacenUSA) {
		this.almacenUSA = almacenUSA;
	}
	/**
	 * @return the almacenMatriz
	 */
	public Boolean getAlmacenMatriz() {
		return almacenMatriz;
	}
	/**
	 * @param almacenMatriz the almacenMatriz to set
	 */
	public void setAlmacenMatriz(Boolean almacenMatriz) {
		this.almacenMatriz = almacenMatriz;
	}
	/**
	 * @return the primerAlmacen
	 */
	public String getPrimerAlmacen() {
		return primerAlmacen;
	}
	/**
	 * @param primerAlmacen the primerAlmacen to set
	 */
	public void setPrimerAlmacen(String primerAlmacen) {
		this.primerAlmacen = primerAlmacen;
	}
	/**
	 * @return the tiempoRespuesta
	 */
	public String getTiempoRespuesta() {
		return tiempoRespuesta;
	}
	/**
	 * @param tiempoRespuesta the tiempoRespuesta to set
	 */
	public void setTiempoRespuesta(String tiempoRespuesta) {
		this.tiempoRespuesta = tiempoRespuesta;
	}
	/**
	 * @return the empresa
	 */
	public String getEmpresa() {
		return empresa;
	}
	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}
	/**
	 * @return the montoTotal
	 */
	public Double getMontoTotal() {
		return montoTotal;
	}
	/**
	 * @param montoTotal the montoTotal to set
	 */
	public void setMontoTotal(Double montoTotal) {
		this.montoTotal = montoTotal;
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
	 * @return the montoTotalDolares
	 */
	public Double getMontoTotalDolares() {
		return montoTotalDolares;
	}
	/**
	 * @param montoTotalDolares the montoTotalDolares to set
	 */
	public void setMontoTotalDolares(Double montoTotalDolares) {
		this.montoTotalDolares = montoTotalDolares;
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
	 * @return the nombreProveedor
	 */
	public String getNombreProveedor() {
		return nombreProveedor;
	}
	/**
	 * @param nombreProveedor the nombreProveedor to set
	 */
	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}
	/**
	 * @return the colocarDesde
	 */
	public String getColocarDesde() {
		return colocarDesde;
	}
	/**
	 * @param colocarDesde the colocarDesde to set
	 */
	public void setColocarDesde(String colocarDesde) {
		this.colocarDesde = colocarDesde;
	}
	/**
	 * @return the fechaConfirmacion
	 */
	public Date getFechaConfirmacion() {
		return fechaConfirmacion;
	}
	/**
	 * @param fechaConfirmacion the fechaConfirmacion to set
	 */
	public void setFechaConfirmacion(Date fechaConfirmacion) {
		this.fechaConfirmacion = fechaConfirmacion;
	}
	/**
	 * @return the totalPiezas
	 */
	public Integer getTotalPiezas() {
		return totalPiezas;
	}
	/**
	 * @param totalPiezas the totalPiezas to set
	 */
	public void setTotalPiezas(Integer totalPiezas) {
		this.totalPiezas = totalPiezas;
	}
	/**
	 * @return the totalPiezasMexico
	 */
	public Integer getTotalPiezasMexico() {
		return totalPiezasMexico;
	}
	/**
	 * @param totalPiezasMexico the totalPiezasMexico to set
	 */
	public void setTotalPiezasMexico(Integer totalPiezasMexico) {
		this.totalPiezasMexico = totalPiezasMexico;
	}
	/**
	 * @return the totalCanceladas
	 */
	public Integer getTotalCanceladas() {
		return totalCanceladas;
	}
	/**
	 * @param totalCanceladas the totalCanceladas to set
	 */
	public void setTotalCanceladas(Integer totalCanceladas) {
		this.totalCanceladas = totalCanceladas;
	}
	/**
	 * @return the totalRecibidas
	 */
	public Integer getTotalRecibidas() {
		return totalRecibidas;
	}
	/**
	 * @param totalRecibidas the totalRecibidas to set
	 */
	public void setTotalRecibidas(Integer totalRecibidas) {
		this.totalRecibidas = totalRecibidas;
	}
	/**
	 * @return the totalRecibidasPHS
	 */
	public Integer getTotalRecibidasPHS() {
		return totalRecibidasPHS;
	}
	/**
	 * @param totalRecibidasPHS the totalRecibidasPHS to set
	 */
	public void setTotalRecibidasPHS(Integer totalRecibidasPHS) {
		this.totalRecibidasPHS = totalRecibidasPHS;
	}
	/**
	 * @return the idPCompra
	 */
	public Long getIdPCompra() {
		return idPCompra;
	}
	/**
	 * @param idPCompra the idPCompra to set
	 */
	public void setIdPCompra(Long idPCompra) {
		this.idPCompra = idPCompra;
	}
	/**
	 * @param comprador the comprador to set
	 */
	public void setComprador(String comprador) {
		this.comprador = comprador;
	}
	/**
	 * @return the comprador
	 */
	public String getComprador() {
		return comprador;
	}
	/**
	 * @param totalPartidas the totalPartidas to set
	 */
	public void setTotalPartidas(Long totalPartidas) {
		this.totalPartidas = totalPartidas;
	}
	/**
	 * @return the totalPartidas
	 */
	public Long getTotalPartidas() {
		return totalPartidas;
	}
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}
	public String getFabrica() {
		return fabrica;
	}
	public void setAbierto_cerrado(String abierto_cerrado) {
		this.abierto_cerrado = abierto_cerrado;
	}
	public String getAbierto_cerrado() {
		return abierto_cerrado;
	}
	public void setInTime(String inTime) {
		this.inTime = inTime;
	}
	public String getInTime() {
		return inTime;
	}
	public void setMontoTotalDolares_partidas(Double montoTotalDolares_partidas) {
		this.montoTotalDolares_partidas = montoTotalDolares_partidas;
	}
	public Double getMontoTotalDolares_partidas() {
		return montoTotalDolares_partidas;
	}
	public void setTotalPiezas_partidas(Integer totalPiezas_partidas) {
		this.totalPiezas_partidas = totalPiezas_partidas;
	}
	public Integer getTotalPiezas_partidas() {
		return totalPiezas_partidas;
	}
	public Boolean getEnvio() {
		return envio;
	}
	public void setEnvio(Boolean envio) {
		this.envio = envio;
	}
	public Double getIva() {
		return iva;
	}
	public void setIva(Double iva) {
		this.iva = iva;
	}
	public String getMedioPago() {
		return medioPago;
	}
	public void setMedioPago(String medioPago) {
		this.medioPago = medioPago;
	}
	public String getTipoPendiente() {
		return tipoPendiente;
	}
	public void setTipoPendiente(String tipoPendiente) {
		this.tipoPendiente = tipoPendiente;
	}
	public String getFolioRMT() {
		return folioRMT;
	}
	public void setFolioRMT(String folioRMT) {
		this.folioRMT = folioRMT;
	}
	public String getResponsable() {
		return responsable;
	}
	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}
	public String getPaisProveedor() {
		return paisProveedor;
	}
	public void setPaisProveedor(String paisProveedor) {
		this.paisProveedor = paisProveedor;
	}
	public Long getIdEmpresa() {
		return idEmpresa;
	}
	public void setIdEmpresa(Long idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
	public boolean isIngles() {
		return ingles;
	}
	public void setIngles(boolean ingles) {
		this.ingles = ingles;
	}
	public boolean isPublicacion() {
		return publicacion;
	}
	public void setPublicacion(boolean publicacion) {
		this.publicacion = publicacion;
	}
}