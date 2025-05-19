/**
 * 
 */
package com.proquifa.net.modelo.ventas.visitas;

public class ObtenerPrecios {

	private String grafica;
	private String clave;
	private int cant;
	private String estado;
	private int mes;
	private Double precioDolares;
	private Double precioPesos;
	private Double precioEuros;
	
	
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public int getCant() {
		return cant;
	}
	public void setCant(int cant) {
		this.cant = cant;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public int getMes() {
		return mes;
	}
	public void setMes(int mes) {
		this.mes = mes;
	}
	public String getGrafica() {
		return grafica;
	}
	public void setGrafica(String grafica) {
		this.grafica = grafica;
	}
	public Double getPrecioDolares() {
		return precioDolares;
	}
	public void setPrecioDolares(Double precioDolares) {
		this.precioDolares = precioDolares;
	}
	public Double getPrecioPesos() {
		return precioPesos;
	}
	public void setPrecioPesos(Double precioPesos) {
		this.precioPesos = precioPesos;
	}
	public Double getPrecioEuros() {
		return precioEuros;
	}
	public void setPrecioEuros(Double precioEuros) {
		this.precioEuros = precioEuros;
	}	
	
}
