package com.proquifa.net.modelo.comun;

import java.util.List;

public class GrupoCarterasDeClientes {
	private long idCartera;
	public long getIdCartera() {
		return idCartera;
	}
	public void setIdCartera(long idCartera) {
		this.idCartera = idCartera;
	}
	private String nombreCartera;
	private String nombreCorporativo;
	private String folio;
	private String esac;
	private String evt;
	private String ev;
	private String cobrador;
	private String elaboro;
	private Integer numeroClientes;
	private Double facturacionActual;
	private Double facturacionAnterior;
	private Double objetivoFundamental;
	private Double objetivoDeseado;
	private Double promedioFacturacion;
	private Double proyeccionVenta;
	private Double debe;
	private Double debemos;
	private String ruta;
	private String industria;
	private String estrella;
	private String triangulo;
	private List<CarteraCliente> clientes;
	
	
	
	public GrupoCarterasDeClientes() {
		super();
		this.numeroClientes = 0;
		this.facturacionActual = 0d;
		this.facturacionAnterior = 0d;
		this.objetivoFundamental = 0d;
		this.objetivoDeseado = 0d;
		this.promedioFacturacion = 0d;
		this.proyeccionVenta = 0d;
		this.debe = 0d;
		this.debemos = 0d;
	}
	public String getNombreCorporativo() {
		return nombreCorporativo;
	}
	public void setNombreCorporativo(String nombreCorporativo) {
		this.nombreCorporativo = nombreCorporativo;
	}
	public String getFolio() {
		return folio;
	}
	public void setFolio(String folio) {
		this.folio = folio;
	}
	public String getEsac() {
		return esac;
	}
	public void setEsac(String esac) {
		this.esac = esac;
	}
	public String getEvt() {
		return evt;
	}
	public void setEvt(String evt) {
		this.evt = evt;
	}
	public String getEv() {
		return ev;
	}
	public void setEv(String ev) {
		this.ev = ev;
	}
	public String getCobrador() {
		return cobrador;
	}
	public void setCobrador(String cobrador) {
		this.cobrador = cobrador;
	}
	public String getElaboro() {
		return elaboro;
	}
	public void setElaboro(String elaboro) {
		this.elaboro = elaboro;
	}
	public Integer getNumeroClientes() {
		return numeroClientes;
	}
	public void setNumeroClientes(Integer numeroClientes) {
		this.numeroClientes = numeroClientes;
	}
	public Double getFacturacionActual() {
		return facturacionActual;
	}
	public void setFacturacionActual(Double facturacionActual) {
		this.facturacionActual = facturacionActual;
	}
	public Double getFacturacionAnterior() {
		return facturacionAnterior;
	}
	public void setFacturacionAnterior(Double facturacionAnterior) {
		this.facturacionAnterior = facturacionAnterior;
	}
	public Double getObjetivoFundamental() {
		return objetivoFundamental;
	}
	public void setObjetivoFundamental(Double objetivoFundamental) {
		this.objetivoFundamental = objetivoFundamental;
	}
	public Double getObjetivoDeseado() {
		return objetivoDeseado;
	}
	public void setObjetivoDeseado(Double objetivoDeseado) {
		this.objetivoDeseado = objetivoDeseado;
	}
	public Double getPromedioFacturacion() {
		return promedioFacturacion;
	}
	public void setPromedioFacturacion(Double promedioFacturacion) {
		this.promedioFacturacion = promedioFacturacion;
	}
	public Double getProyeccionVenta() {
		return proyeccionVenta;
	}
	public void setProyeccionVenta(Double proyeccionVenta) {
		this.proyeccionVenta = proyeccionVenta;
	}
	public Double getDebe() {
		return debe;
	}
	public void setDebe(Double debe) {
		this.debe = debe;
	}
	public Double getDebemos() {
		return debemos;
	}
	public void setDebemos(Double debemos) {
		this.debemos = debemos;
	}
	public String getRuta() {
		return ruta;
	}
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	public String getIndustria() {
		return industria;
	}
	public void setIndustria(String industria) {
		this.industria = industria;
	}
	public String getEstrella() {
		return estrella;
	}
	public void setEstrella(String estrella) {
		this.estrella = estrella;
	}
	public String getTriangulo() {
		return triangulo;
	}
	public void setTriangulo(String triangulo) {
		this.triangulo = triangulo;
	}
	public List<CarteraCliente> getClientes() {
		return clientes;
	}
	public void setClientes(List<CarteraCliente> clientes) {
		this.clientes = clientes;
	}
	public String getNombreCartera() {
		return nombreCartera;
	}
	public void setNombreCartera(String nombreCartera) {
		this.nombreCartera = nombreCartera;
	}
	
}
