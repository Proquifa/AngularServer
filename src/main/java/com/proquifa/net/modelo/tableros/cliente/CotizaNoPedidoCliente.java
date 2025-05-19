/**
 * 
 */
package com.proquifa.net.modelo.tableros.cliente;

/**
 * @author vromero
 *
 */
public class CotizaNoPedidoCliente {
	private String marca;
	private String concepto;
	private Long cantidad;
	private Double monto;
	private String codigo;
	private String cotizacion;
	private String esac;
	/**
	 * @return the marca
	 */
	public String getMarca() {
		return marca;
	}
	/**
	 * @param marca the marca to set
	 */
	public void setMarca(String marca) {
		this.marca = marca;
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
	 * @return the cantidad
	 */
	public Long getCantidad() {
		return cantidad;
	}
	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}
	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param cotizacion the cotizacion to set
	 */
	public void setCotizacion(String cotizacion) {
		this.cotizacion = cotizacion;
	}
	/**
	 * @return the cotizacion
	 */
	public String getCotizacion() {
		return cotizacion;
	}
	/**
	 * @return the esac
	 */
	public String getEsac() {
		return esac;
	}
	/**
	 * @param esac the esac to set
	 */
	public void setEsac(String esac) {
		this.esac = esac;
	}

}
