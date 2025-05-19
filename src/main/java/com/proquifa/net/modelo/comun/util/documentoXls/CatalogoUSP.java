/**
 * 
 */
package com.proquifa.net.modelo.comun.util.documentoXls;

import java.util.Date;


/**
 * @author bryan.magana
 *
 */
public class CatalogoUSP {
    private long pk_Producto_USP;
    private String conceptoUSP;
    private String casUSP;
    private double precioUSP;
    private String lote_AntUSP;
    private String lote_ActUSP;
    private String fabrica;
    private int proveedor;
    private String moneda;
    private Date fecha;
    
    private String estado;

    private long idProducto ;
    private boolean vigenteProd;
    private String codigoProd;
    private String conceptoProd;
    private String casProd;
    private Date caducaProd;
    private double costoProd;
    
    
    private String unidad;
    private String cantidad;
    
	/**
	 * @return the pk_Producto_USP
	 */
	public long getPk_Producto_USP() {
		return pk_Producto_USP;
	}
	/**
	 * @param pk_Producto_USP the pk_Producto_USP to set
	 */
	public void setPk_Producto_USP(long pk_Producto_USP) {
		this.pk_Producto_USP = pk_Producto_USP;
	}
	/**
	 * @return the conceptoUSP
	 */
	public String getConceptoUSP() {
		return conceptoUSP;
	}
	/**
	 * @param conceptoUSP the conceptoUSP to set
	 */
	public void setConceptoUSP(String conceptoUSP) {
		this.conceptoUSP = conceptoUSP;
	}
	/**
	 * @return the casUSP
	 */
	public String getCasUSP() {
		return casUSP;
	}
	/**
	 * @param casUSP the casUSP to set
	 */
	public void setCasUSP(String casUSP) {
		this.casUSP = casUSP;
	}
	
	/**
	 * @return the idProducto
	 */
	public long getIdProducto() {
		return idProducto;
	}
	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(long idProducto) {
		this.idProducto = idProducto;
	}
	/**
	 * @return the vigenteProd
	 */
	public boolean isVigenteProd() {
		return vigenteProd;
	}
	/**
	 * @param vigenteProd the vigenteProd to set
	 */
	public void setVigenteProd(boolean vigenteProd) {
		this.vigenteProd = vigenteProd;
	}
	/**
	 * @return the codigoProd
	 */
	public String getCodigoProd() {
		return codigoProd;
	}
	/**
	 * @param codigoProd the codigoProd to set
	 */
	public void setCodigoProd(String codigoProd) {
		this.codigoProd = codigoProd;
	}
	/**
	 * @return the conceptoProd
	 */
	public String getConceptoProd() {
		return conceptoProd;
	}
	/**
	 * @param conceptoProd the conceptoProd to set
	 */
	public void setConceptoProd(String conceptoProd) {
		this.conceptoProd = conceptoProd;
	}
	/**
	 * @return the casProd
	 */
	public String getCasProd() {
		return casProd;
	}
	/**
	 * @param casProd the casProd to set
	 */
	public void setCasProd(String casProd) {
		this.casProd = casProd;
	}
	/**
	 * @return the caducaProd
	 */
	public Date getCaducaProd() {
		return caducaProd;
	}
	/**
	 * @param caducaProd the caducaProd to set
	 */
	public void setCaducaProd(Date caducaProd) {
		this.caducaProd = caducaProd;
	}
	
	/**
	 * @return the lote_AntUSP
	 */
	public String getLote_AntUSP() {
		return lote_AntUSP;
	}
	/**
	 * @param lote_AntUSP the lote_AntUSP to set
	 */
	public void setLote_AntUSP(String lote_AntUSP) {
		this.lote_AntUSP = lote_AntUSP;
	}
	/**
	 * @return the lote_ActUSP
	 */
	public String getLote_ActUSP() {
		return lote_ActUSP;
	}
	/**
	 * @param lote_ActUSP the lote_ActUSP to set
	 */
	public void setLote_ActUSP(String lote_ActUSP) {
		this.lote_ActUSP = lote_ActUSP;
	}
	public String getFabrica() {
		return fabrica;
	}
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}
	public int getProveedor() {
		return proveedor;
	}
	public void setProveedor(int proveedor) {
		this.proveedor = proveedor;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
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
	 * @return the precioUSP
	 */
	public double getPrecioUSP() {
		return precioUSP;
	}
	/**
	 * @param precioUSP the precioUSP to set
	 */
	public void setPrecioUSP(double precioUSP) {
		this.precioUSP = precioUSP;
	}
	/**
	 * @return the costoProd
	 */
	public double getCostoProd() {
		return costoProd;
	}
	/**
	 * @param costoProd the costoProd to set
	 */
	public void setCostoProd(double costoProd) {
		this.costoProd = costoProd;
	}
	public String getUnidad() {
		return unidad;
	}
	public void setUnidad(String unidad) {
		this.unidad = unidad;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
    
}
