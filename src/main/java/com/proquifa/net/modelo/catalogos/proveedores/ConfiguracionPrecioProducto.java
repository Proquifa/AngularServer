/**
 * 
 */
package com.proquifa.net.modelo.catalogos.proveedores;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.comun.Producto;


/**
 * @author fmartinez
 *
 */
public class ConfiguracionPrecioProducto {
	private Long idConfiguracion;// en este momento obtiene lo que es el id configuracion_precio --> BD
	private String nivel;
	private Long idProveedor;
	private CostoFactor costoFactorProducto;
	private TiempoEntrega tentregaProducto;
	private Producto producto;
	private Double porciento;
	private Integer cantProductos;
	private Long idConfiguracionFamilia;
	private Long idConfiguracionCosto;
	private Long idConfiguracionClasificacion;
	private Long idClasificacion;
	private Date fua;
	private Long idConfiguracionPrecioProducto;
	private Boolean restablecer; //para configuracion producto
	private Boolean restablecerCosto; //para configuracion costo
	private Boolean restablecerTemporal; //para configuracion producto temporal
	private Boolean restablecerCostoTemporal; //para configuracion costo temporal
	private Double costoCompra;
	private List<TiempoEntrega> tiempoEntregaRuta;
	private String nivelConfiguracionCliente;
	private Boolean factorClienteConfiguracion;
	private String rutaCliente;
	private Boolean compuestaCostoF;
	private Boolean compuestaFactorU;
	private Boolean compuestaCostoFTemporal;
	private Boolean compuestaFactorUTemporal;
	private boolean precioListaAnterior;
	private ParametrosOfertaCliente parametrosOferta;

	/**
	 * @return the rutaCliente
	 */
	public String getRutaCliente() {
		return rutaCliente;
	}
	/**
	 * @param rutaCliente the rutaCliente to set
	 */
	public void setRutaCliente(String rutaCliente) {
		this.rutaCliente = rutaCliente;
	}
	/**
	 * @return the factorClienteConfiguracion
	 */
	public Boolean getFactorClienteConfiguracion() {
		return factorClienteConfiguracion;
	}
	/**
	 * @param factorClienteConfiguracion the factorClienteConfiguracion to set
	 */
	public void setFactorClienteConfiguracion(Boolean factorClienteConfiguracion) {
		this.factorClienteConfiguracion = factorClienteConfiguracion;
	}
	/**
	 * @return the nivelConfiguracionCliente
	 */
	public String getNivelConfiguracionCliente() {
		return nivelConfiguracionCliente;
	}
	/**
	 * @param nivelConfiguracionCliente the nivelConfiguracionCliente to set
	 */
	public void setNivelConfiguracionCliente(String nivelConfiguracionCliente) {
		this.nivelConfiguracionCliente = nivelConfiguracionCliente;
	}
	/**
	 * @return the tiempoEntregaRuta
	 */
	public List<TiempoEntrega> getTiempoEntregaRuta() {
		return tiempoEntregaRuta;
	}
	/**
	 * @param tiempoEntregaRuta the tiempoEntregaRuta to set
	 */
	public void setTiempoEntregaRuta(List<TiempoEntrega> tiempoEntregaRuta) {
		this.tiempoEntregaRuta = tiempoEntregaRuta;
	}
	/**
	 * @return the idConfiguracionPrecioProducto
	 */
	public Long getIdConfiguracionPrecioProducto() {
		return idConfiguracionPrecioProducto;
	}
	/**
	 * @param idConfiguracionPrecioProducto the idConfiguracionPrecioProducto to set
	 */
	public void setIdConfiguracionPrecioProducto(Long idConfiguracionPrecioProducto) {
		this.idConfiguracionPrecioProducto = idConfiguracionPrecioProducto;
	}
		
	/**
	 * @return the idConfiguracion
	 */
	public Long getIdConfiguracion() {
		return idConfiguracion;
	}
	/**
	 * @param idConfiguracion the idConfiguracion to set
	 */
	public void setIdConfiguracion(Long idConfiguracion) {
		this.idConfiguracion = idConfiguracion;
	}
	/**
	 * @return the nivel
	 */
	public String getNivel() {
		return nivel;
	}
	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(String nivel) {
		this.nivel = nivel;
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
	 * @return the costoFactorProducto
	 */
	public CostoFactor getCostoFactorProducto() {
		return costoFactorProducto;
	}
	/**
	 * @param costoFactorProducto the costoFactorProducto to set
	 */
	public void setCostoFactorProducto(CostoFactor costoFactorProducto) {
		this.costoFactorProducto = costoFactorProducto;
	}
	/**
	 * @return the tentregaProducto
	 */
	public TiempoEntrega getTentregaProducto() {
		return tentregaProducto;
	}
	/**
	 * @param tentregaProducto the tentregaProducto to set
	 */
	public void setTentregaProducto(TiempoEntrega tentregaProducto) {
		this.tentregaProducto = tentregaProducto;
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
	
	public Double getPorciento() {
		return porciento;
	}
	public void setPorciento(Double porciento) {
		this.porciento = porciento;
	}
	/**
	 * @return the idConfiguracionFamilia
	 */
	public Long getIdConfiguracionFamilia() {
		return idConfiguracionFamilia;
	}
	/**
	 * @param idConfiguracionFamilia the idConfiguracionFamilia to set
	 */
	public void setIdConfiguracionFamilia(Long idConfiguracionFamilia) {
		this.idConfiguracionFamilia = idConfiguracionFamilia;
	}
	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}
	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}
	/**
	 * @return the idConfiguracionCosto
	 */
	public Long getIdConfiguracionCosto() {
		return idConfiguracionCosto;
	}
	/**
	 * @param idConfiguracionCosto the idConfiguracionCosto to set
	 */
	public void setIdConfiguracionCosto(Long idConfiguracionCosto) {
		this.idConfiguracionCosto = idConfiguracionCosto;
	}
	/**
	 * @return the restablecer
	 */
	public Boolean getRestablecer() {
		return restablecer;
	}
	/**
	 * @param restablecer the restablecer to set
	 */
	public void setRestablecer(Boolean restablecer) {
		this.restablecer = restablecer;
	}
	/**
	 * @return the restablecerCosto
	 */
	public Boolean getRestablecerCosto() {
		return restablecerCosto;
	}
	/**
	 * @param restablecerCosto the restablecerCosto to set
	 */
	public void setRestablecerCosto(Boolean restablecerCosto) {
		this.restablecerCosto = restablecerCosto;
	}
	/**
	 * @return the costoCompra
	 */
	public Double getCostoCompra() {
		return costoCompra;
	}
	/**
	 * @param costoCompra the costoCompra to set
	 */
	public void setCostoCompra(Double costoCompra) {
		this.costoCompra = costoCompra;
	}
	/**
	 * @return the cantProductos
	 */
	public Integer getCantProductos() {
		return cantProductos;
	}
	/**
	 * @param cantProductos the cantProductos to set
	 */
	public void setCantProductos(Integer cantProductos) {
		this.cantProductos = cantProductos;
	}
	/**
	 * @return the idConfiguracionClasificacion
	 */
	public Long getIdConfiguracionClasificacion() {
		return idConfiguracionClasificacion;
	}
	/**
	 * @param idConfiguracionClasificacion the idConfiguracionClasificacion to set
	 */
	public void setIdConfiguracionClasificacion(
			Long idConfiguracionClasificacion) {
		this.idConfiguracionClasificacion = idConfiguracionClasificacion;
	}
	/**
	 * @return the idClasificacion
	 */
	public Long getIdClasificacion() {
		return idClasificacion;
	}
	/**
	 * @param idClasificacion the idClasificacion to set
	 */
	public void setIdClasificacion(Long idClasificacion) {
		this.idClasificacion = idClasificacion;
	}
	public Boolean getCompuestaCostoF() {
		return compuestaCostoF;
	}
	public void setCompuestaCostoF(Boolean compuestaCostoF) {
		this.compuestaCostoF = compuestaCostoF;
	}
	public Boolean getCompuestaFactorU() {
		return compuestaFactorU;
	}
	public void setCompuestaFactorU(Boolean compuestaFactorU) {
		this.compuestaFactorU = compuestaFactorU;
	}
	public Boolean getRestablecerTemporal() {
		return restablecerTemporal;
	}
	public void setRestablecerTemporal(Boolean restablecerTemporal) {
		this.restablecerTemporal = restablecerTemporal;
	}
	public Boolean getRestablecerCostoTemporal() {
		return restablecerCostoTemporal;
	}
	public void setRestablecerCostoTemporal(Boolean restablecerCostoTemporal) {
		this.restablecerCostoTemporal = restablecerCostoTemporal;
	}
	public Boolean getCompuestaCostoFTemporal() {
		return compuestaCostoFTemporal;
	}
	public void setCompuestaCostoFTemporal(Boolean compuestaCostoFTemporal) {
		this.compuestaCostoFTemporal = compuestaCostoFTemporal;
	}
	public Boolean getCompuestaFactorUTemporal() {
		return compuestaFactorUTemporal;
	}
	public void setCompuestaFactorUTemporal(Boolean compuestaFactorUTemporal) {
		this.compuestaFactorUTemporal = compuestaFactorUTemporal;
	}
	public boolean isPrecioListaAnterior() {
		return precioListaAnterior;
	}
	public void setPrecioListaAnterior(boolean precioListaAnterior) {
		this.precioListaAnterior = precioListaAnterior;
	}
	public ParametrosOfertaCliente getParametrosOferta() {
		return parametrosOferta;
	}
	public void setParametrosOferta(ParametrosOfertaCliente parametrosOferta) {
		this.parametrosOferta = parametrosOferta;
	}
	

}