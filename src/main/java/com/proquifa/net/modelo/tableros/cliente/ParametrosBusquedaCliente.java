package com.proquifa.net.modelo.tableros.cliente;

public class ParametrosBusquedaCliente {

	private Long cliente; 
	private Long anio; 
	private String periodo;
	private Integer tipoPeriodo;
	private Long mes;
	private String esac;
	private Long eventas;
	private Long esacMaster;
	private Boolean conEsacMaster;
	private Long corporativo;
	private Long proveedor;
	private int lineal;
	private String tipo;
	private String fabrica;
	private Long colaborador;
	private Boolean todoCotizado;
	private Integer distribuidor; // 1 = Solo distribuidores, 2 = Diferente a distribuidores,  0 or NULL = Todos
	private String ruta;
	private String nivelIngreso;
	private String categoria;
	private Boolean cartera; 
	
	private boolean comparativas;
	
	public boolean isComparativas() {
		return comparativas;
	}
	public void setComparativas(boolean comparativas) {
		this.comparativas = comparativas;
	}
	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}
	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	/**
	 * @return the nivelIngreso
	 */
	public String getNivelIngreso() {
		return nivelIngreso;
	}
	/**
	 * @param nivelIngreso the nivelIngreso to set
	 */
	public void setNivelIngreso(String nivelIngreso) {
		this.nivelIngreso = nivelIngreso;
	}
	/**
	 * @return the cliente
	 */
	public Long getCliente() {
		return cliente;
	}
	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}
	/**
	 * @return the anio
	 */
	public Long getAnio() {
		return anio;
	}
	/**
	 * @param anio the anio to set
	 */
	public void setAnio(Long anio) {
		this.anio = anio;
	}
	/**
	 * @return the periodo
	 */
	public String getPeriodo() {
		return periodo;
	}
	/**
	 * @param periodo the periodo to set
	 */
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	/**
	 * @return the tipoPeriodo
	 */
	public Integer getTipoPeriodo() {
		return tipoPeriodo;
	}
	/**
	 * @param tipoPeriodo the tipoPeriodo to set
	 */
	public void setTipoPeriodo(Integer tipoPeriodo) {
		this.tipoPeriodo = tipoPeriodo;
	}
	/**
	 * @return the mes
	 */
	public Long getMes() {
		return mes;
	}
	/**
	 * @param mes the mes to set
	 */
	public void setMes(Long mes) {
		this.mes = mes;
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
	/**
	 * @return the eventas
	 */
	public Long getEventas() {
		return eventas;
	}
	/**
	 * @param eventas the eventas to set
	 */
	public void setEventas(Long eventas) {
		this.eventas = eventas;
	}
	/**
	 * @return the esacMaster
	 */
	public Long getEsacMaster() {
		return esacMaster;
	}
	/**
	 * @param esacMaster the esacMaster to set
	 */
	public void setEsacMaster(Long esacMaster) {
		this.esacMaster = esacMaster;
	}
	/**
	 * @return the conEsacMaster
	 */
	public Boolean getConEsacMaster() {
		return conEsacMaster;
	}
	/**
	 * @param conEsacMaster the conEsacMaster to set
	 */
	public void setConEsacMaster(Boolean conEsacMaster) {
		this.conEsacMaster = conEsacMaster;
	}
	/**
	 * @return the corporativo
	 */
	public Long getCorporativo() {
		return corporativo;
	}
	/**
	 * @param corporativo the corporativo to set
	 */
	public void setCorporativo(Long corporativo) {
		this.corporativo = corporativo;
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
	 * @return the lineal
	 */
	public int getLineal() {
		return lineal;
	}
	/**
	 * @param lineal the lineal to set
	 */
	public void setLineal(int lineal) {
		this.lineal = lineal;
	}
	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the fabrica
	 */
	public String getFabrica() {
		return fabrica;
	}
	/**
	 * @param fabrica the fabrica to set
	 */
	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}
	/**
	 * @return the colaborador
	 */
	public Long getColaborador() {
		return colaborador;
	}
	/**
	 * @param colaborador the colaborador to set
	 */
	public void setColaborador(Long colaborador) {
		this.colaborador = colaborador;
	}
	/**
	 * @return the todoCotizado
	 */
	public Boolean getTodoCotizado() {
		return todoCotizado;
	}
	/**
	 * @param todoCotizado the todoCotizado to set
	 */
	public void setTodoCotizado(Boolean todoCotizado) {
		this.todoCotizado = todoCotizado;
	}
	/**
	 * @return the distribuidor
	 */
	public Integer getDistribuidor() {
		return distribuidor;
	}
	/**
	 * @param distribuidor the distribuidor to set
	 */
	public void setDistribuidor(Integer distribuidor) {
		this.distribuidor = distribuidor;
	}
	/**
	 * @return the categoria
	 */
	public String getCategoria() {
		return categoria;
	}
	/**
	 * @param categoria the categoria to set
	 */
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	/**
	 * @return the cartera
	 */
	public Boolean getCartera() {
		return cartera;
	}
	/**
	 * @param cartera the cartera to set
	 */
	public void setCartera(Boolean cartera) {
		this.cartera = cartera;
	}
	 
}
