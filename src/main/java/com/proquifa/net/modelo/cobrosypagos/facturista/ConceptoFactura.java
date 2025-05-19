package com.proquifa.net.modelo.cobrosypagos.facturista;


public class ConceptoFactura {

	private Integer idConcepto;
	private String descripcion;
	private Integer idEmpresa;
	private Integer cantidad;
	private Float valorUnitario;
	private Float importe;
	private String complementoConcepto;
	private String unidadDeMedida;
	private String codigo;
	private String fabrica;
	private String tipo;
	private String lineaDeOrdenDeCompra;
	private String codigoSKU;
	private String notas;
	private String numeroAduana;
	private String fechaAduana;
	private String aduana;
	private Integer claveUnidad;
	private Integer claveProdServ;
	
	/**
	 * @return the idConcepto
	 */
	public Integer getIdConcepto() {
		return idConcepto;
	}
	/**
	 * @param idConcepto the idConcepto to set
	 */
	public void setIdConcepto(Integer idConcepto) {
		this.idConcepto = idConcepto;
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
	 * @return the idEmpresa
	 */
	public Integer getIdEmpresa() {
		return idEmpresa;
	}
	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Integer idEmpresa) {
		this.idEmpresa = idEmpresa;
	}
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
	 * @return the valorUnitario
	 */
	public Float getValorUnitario() {
		return valorUnitario;
	}
	/**
	 * @param valorUnitario the valorUnitario to set
	 */
	public void setValorUnitario(Float valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	/**
	 * @return the importe
	 */
	public Float getImporte() {
		return importe;
	}
	/**
	 * @param importe the importe to set
	 */
	public void setImporte(Float importe) {
		this.importe = importe;
	}
	/**
	 * @return the complementoConcepto
	 */
	public String getComplementoConcepto() {
		return complementoConcepto;
	}
	/**
	 * @param complementoConcepto the complementoConcepto to set
	 */
	public void setComplementoConcepto(String complementoConcepto) {
		this.complementoConcepto = complementoConcepto;
	}
	/**
	 * @return the unidadDeMedida
	 */
	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}
	/**
	 * @param unidadDeMedida the unidadDeMedida to set
	 */
	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}
	/**
	 * @return the codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo the codigo to set
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	 * @return the lineaDeOrdenDeCompra
	 */
	public String getLineaDeOrdenDeCompra() {
		return lineaDeOrdenDeCompra;
	}
	/**
	 * @param lineaDeOrdenDeCompra the lineaDeOrdenDeCompra to set
	 */
	public void setLineaDeOrdenDeCompra(String lineaDeOrdenDeCompra) {
		this.lineaDeOrdenDeCompra = lineaDeOrdenDeCompra;
	}
	/**
	 * @return the codigoSKU
	 */
	public String getCodigoSKU() {
		return codigoSKU;
	}
	/**
	 * @param codigoSKU the codigoSKU to set
	 */
	public void setCodigoSKU(String codigoSKU) {
		this.codigoSKU = codigoSKU;
	}
	/**
	 * @return the notas
	 */
	public String getNotas() {
		return notas;
	}
	/**
	 * @param notas the notas to set
	 */
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public String getNumeroAduana() {
		return numeroAduana;
	}
	public void setNumeroAduana(String numeroAduana) {
		this.numeroAduana = numeroAduana;
	}
	
	public String getAduana() {
		return aduana;
	}
	public void setAduana(String aduana) {
		this.aduana = aduana;
	}
	public String getFechaAduana() {
		return fechaAduana;
	}
	public void setFechaAduana(String fechaAduana) {
		this.fechaAduana = fechaAduana;
	}

	/**
	 * @return the claveProdServ
	 */
	public Integer getClaveProdServ() {
		return claveProdServ;
	}
	/**
	 * @param claveProdServ the claveProdServ to set
	 */
	public void setClaveProdServ(Integer claveProdServ) {
		this.claveProdServ = claveProdServ;
	}
	/**
	 * @return the claveUnidad
	 */
	public Integer getClaveUnidad() {
		return claveUnidad;
	}
	/**
	 * @param claveUnidad the claveUnidad to set
	 */
	public void setClaveUnidad(Integer claveUnidad) {
		this.claveUnidad = claveUnidad;
	}
}