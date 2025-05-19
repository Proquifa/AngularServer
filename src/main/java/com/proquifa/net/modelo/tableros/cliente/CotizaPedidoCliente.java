/**
 * 
 */
package com.proquifa.net.modelo.tableros.cliente;

/**
 * @author vromero
 *
 */
public class CotizaPedidoCliente {
	private String cotizacion;
	private String pedido;
	private Double montoCotizado;
	private Double montoPedido;
	private int piezasCotizadas;
	private int piezasPedidas;
	private int mes;
	private String esac;
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
	 * @return the pedido
	 */
	public String getPedido() {
		return pedido;
	}
	/**
	 * @param pedido the pedido to set
	 */
	public void setPedido(String pedido) {
		this.pedido = pedido;
	}
	/**
	 * @return the montoCotizado
	 */
	public Double getMontoCotizado() {
		return montoCotizado;
	}
	/**
	 * @param montoCotizado the montoCotizado to set
	 */
	public void setMontoCotizado(Double montoCotizado) {
		this.montoCotizado = montoCotizado;
	}
	/**
	 * @return the montoPedido
	 */
	public Double getMontoPedido() {
		return montoPedido;
	}
	/**
	 * @param montoPedido the montoPedido to set
	 */
	public void setMontoPedido(Double montoPedido) {
		this.montoPedido = montoPedido;
	}
	/**
	 * @return the piezasCotizadas
	 */
	public int getPiezasCotizadas() {
		return piezasCotizadas;
	}
	/**
	 * @param piezasCotizadas the piezasCotizadas to set
	 */
	public void setPiezasCotizadas(int piezasCotizadas) {
		this.piezasCotizadas = piezasCotizadas;
	}
	/**
	 * @return the piezasPedidas
	 */
	public int getPiezasPedidas() {
		return piezasPedidas;
	}
	/**
	 * @param piezasPedidas the piezasPedidas to set
	 */
	public void setPiezasPedidas(int piezasPedidas) {
		this.piezasPedidas = piezasPedidas;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(int mes) {
		this.mes = mes;
	}
	/**
	 * @return the mes
	 */
	public int getMes() {
		return mes;
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
