package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.catalogos.clientes.ParametrosOfertaCliente;
import com.proquifa.net.modelo.catalogos.proveedores.ConfiguracionPrecioProducto;

public class Contrato {
	
	
	private Date fechaInicio;
	private Date fechaFin;
	private byte[] documento;
	private List<Proveedor> marcas;
	private Long idCliente;
	private Long idContrato;
    private Date fuaContrato;
    private boolean generado;
    private List<ParametrosOfertaCliente> configuracionesContrato;
    private String condionesPago;
    private String folio;
    private boolean finalizado;
    private List<ConfiguracionPrecioProducto> listaDeIdsNivel;
	
	public Date getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public Date getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	public byte[] getDocumento() {
		return documento;
	}
	public void setDocumento(byte[] documento) {
		this.documento = documento;
	}
	public List<Proveedor> getMarcas() {
		return marcas;
	}
	public void setMarcas(List<Proveedor> marcas) {
		this.marcas = marcas;
	}
	public Long getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	public Long getIdContrato() {
		return idContrato;
	}
	public void setIdContrato(Long idContrato) {
		this.idContrato = idContrato;
	}
	public Date getFuaContrato() {
		return fuaContrato;
	}
	public void setFuaContrato(Date fuaContrato) {
		this.fuaContrato = fuaContrato;
	}
	public boolean isGenerado() {
		return generado;
	}
	public void setGenerado(boolean generado) {
		this.generado = generado;
	}
//	public List<ConfiguracionPrecioProducto> getConfiguracionesContrato() {
//		return configuracionesContrato;
//	}
//	public void setConfiguracionesContrato(List<ConfiguracionPrecioProducto> configuracionesContrato) {
//		this.configuracionesContrato = configuracionesContrato;
//	}
	public String getCondionesPago() {
		return condionesPago;
	}
	public void setCondionesPago(String condionesPago) {
		this.condionesPago = condionesPago;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public boolean isFinalizado() {
		return finalizado;
	}
	public void setFinalizado(boolean finalizado) {
		this.finalizado = finalizado;
	}
	public List<ConfiguracionPrecioProducto> getListaDeIdsNivel() {
		return listaDeIdsNivel;
	}
	public void setListaDeIdsNivel(List<ConfiguracionPrecioProducto> listaDeIdsNivel) {
		this.listaDeIdsNivel = listaDeIdsNivel;
	}
	public List<ParametrosOfertaCliente> getConfiguracionesContrato() {
		return configuracionesContrato;
	}
	public void setConfiguracionesContrato(List<ParametrosOfertaCliente> configuracionesContrato) {
		this.configuracionesContrato = configuracionesContrato;
	}
	

}
