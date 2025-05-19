package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier;

import java.util.List;

public class AddendaDarierConcepto {
	private String lineaDeOrdenDeCompra;
	private String cantidad;
	private String unidadDeMedida;
	private AddendaDarierSubCantidad subCantidad;
	private String descripcion;
	private String valorUnitario;
	private String importe;
	private String precioLista;
	private String numeroDeLote;
	private String cantidadDeLote;
	private String fechaLote;
	private String fechaExpiracionLote;
	private String codigoReceptor;
	private String codigoBarra;
	private String codigoProveedor;
	private AddendaDarierInfoAduanera infoAduanera;
	private String importeLista;
	private AddendaDarierDescuentoRecargo descuentoORecargo;
	private List<AddendaDarierImpuesto> impuestos;
	private String textosPosicion;
	
	public String getLineaDeOrdenDeCompra() {
		return lineaDeOrdenDeCompra;
	}
	
	public void setLineaDeOrdenDeCompra(String lineaDeOrdenDeCompra) {
		this.lineaDeOrdenDeCompra = lineaDeOrdenDeCompra;
	}
	
	public String getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	
	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}
	
	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getValorUnitario() {
		return valorUnitario;
	}
	
	public void setValorUnitario(String valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	public String getImporte() {
		return importe;
	}
	
	public void setImporte(String importe) {
		this.importe = importe;
	}
	
	public String getPrecioLista() {
		return precioLista;
	}
	
	public void setPrecioLista(String precioLista) {
		this.precioLista = precioLista;
	}
	
	public String getCodigoReceptor() {
		return codigoReceptor;
	}
	
	public void setCodigoReceptor(String codigoReceptor) {
		this.codigoReceptor = codigoReceptor;
	}
	
	public String getCodigoProveedor() {
		return codigoProveedor;
	}
	
	public void setCodigoProveedor(String codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}
	
	public AddendaDarierInfoAduanera getInfoAduanera() {
		return infoAduanera;
	}
	
	public void setInfoAduanera(AddendaDarierInfoAduanera infoAduanera) {
		this.infoAduanera = infoAduanera;
	}
	
	public String getImporteLista() {
		return importeLista;
	}
	
	public void setImporteLista(String importeLista) {
		this.importeLista = importeLista;
	}
	
	public List<AddendaDarierImpuesto> getImpuestos() {
		return impuestos;
	}
	
	public void setImpuestos(List<AddendaDarierImpuesto> impuestos) {
		this.impuestos = impuestos;
	}

	public AddendaDarierSubCantidad getSubCantidad() {
		return subCantidad;
	}

	public void setSubCantidad(AddendaDarierSubCantidad subCantidad) {
		this.subCantidad = subCantidad;
	}

	public String getNumeroDeLote() {
		return numeroDeLote;
	}

	public void setNumeroDeLote(String numeroDeLote) {
		this.numeroDeLote = numeroDeLote;
	}

	public String getCantidadDeLote() {
		return cantidadDeLote;
	}

	public void setCantidadDeLote(String cantidadDeLote) {
		this.cantidadDeLote = cantidadDeLote;
	}

	public String getFechaLote() {
		return fechaLote;
	}

	public void setFechaLote(String fechaLote) {
		this.fechaLote = fechaLote;
	}

	public String getFechaExpiracionLote() {
		return fechaExpiracionLote;
	}

	public void setFechaExpiracionLote(String fechaExpiracionLote) {
		this.fechaExpiracionLote = fechaExpiracionLote;
	}

	public String getCodigoBarra() {
		return codigoBarra;
	}

	public void setCodigoBarra(String codigoBarra) {
		this.codigoBarra = codigoBarra;
	}

	public AddendaDarierDescuentoRecargo getDescuentoORecargo() {
		return descuentoORecargo;
	}

	public void setDescuentoORecargo(AddendaDarierDescuentoRecargo descuentoORecargo) {
		this.descuentoORecargo = descuentoORecargo;
	}

	public String getTextosPosicion() {
		return textosPosicion;
	}

	public void setTextosPosicion(String textosPosicion) {
		this.textosPosicion = textosPosicion;
	}
	
}
