/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author fmartinez
 *
 */
public class TablaPDF {
	
	private Integer cantidad;
	private String descripcion;
	private String comentarios;
	private Long precioUnitario;
	private Double pUnitario;
	private Long importe;
	private Double importeD;
	private String catalogo;
	private String estatus;
	private String fee;
	private Long total;
	
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
	 * @return the precioUnitario
	 */
	public Long getPrecioUnitario() {
		return precioUnitario;
	}
	/**
	 * @param precioUnitario the precioUnitario to set
	 */
	public void setPrecioUnitario(Long precioUnitario) {
		this.precioUnitario = precioUnitario;
	}
	/**
	 * @return the importe
	 */
	public Long getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Long importe) {
		this.importe = importe;
	}
	/**
	 * @return the catalogo
	 */
	public String getCatalogo() {
		return catalogo;
	}
	/**
	 * @param catalogo the catalogo to set
	 */
	public void setCatalogo(String catalogo) {
		this.catalogo = catalogo;
	}
	/**
	 * @return the estatus
	 */
	public String getEstatus() {
		return estatus;
	}
	/**
	 * @param estatus the estatus to set
	 */
	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}
	/**
	 * @return the fee
	 */
	public String getFee() {
		return fee;
	}
	/**
	 * @param fee the fee to set
	 */
	public void setFee(String fee) {
		this.fee = fee;
	}
	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}
	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}
	/**
	 * @param pUnitario the pUnitario to set
	 */
	public void setPUnitario(Double pUnitario) {
		this.pUnitario = pUnitario;
	}
	/**
	 * @return the pUnitario
	 */
	public Double getPUnitario() {
		return pUnitario;
	}
	/**
	 * @param importeD the importeD to set
	 */
	public void setImporteD(Double importeD) {
		this.importeD = importeD;
	}
	/**
	 * @return the importeD
	 */
	public Double getImporteD() {
		return importeD;
	}
}