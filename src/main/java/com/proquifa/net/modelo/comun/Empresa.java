package com.proquifa.net.modelo.comun;

import java.util.Date;

public class Empresa {
	
	private Integer idEmpresa;
	private String alias;
	private String nomenclaturaEmpresa;
	private String razonSocial;
	private String rfcEmpresa;
	private String usuarioMySuite;
	private String calle;
	private String colonia;
	private String delegacion;
	private String estado;
	private String ciudad;
	private String cp;
	private String pais;
	private String codigoPostalDelPais;

	private Integer foliosAsignados;
	private Integer folioActual;
	private Integer foliosUsadosMes;
	
	private boolean facturaElectronica;
	private boolean facturaMatriz;
	private Double totMontoMes;
	private Integer totCanceladasMes;
	private boolean facturacionHabilitada;
	
	private boolean relacionProveedor;
	private String numAsigCliente;
	private Long idEmpresaProveedor;
	private Long idProveedor;
	private Date fechaUltimaActualizacion;

	private boolean vendedor;
	private boolean comprador;
	
	private boolean exportador;
	private boolean importador;
	private boolean padronImportador;
	private String telefono;
	
	/** CFDI  **/
	private String numCertificado;
	private String llavePrivada;
	private String clave;
	private String serieCFDI;
	
	private String regimenFiscal; 
	
	
	/**
	 * @return the comprador
	 */
	public boolean isComprador() {
		return comprador;
	}
	/**
	 * @param comprador the comprador to set
	 */
	public void setComprador(boolean comprador) {
		this.comprador = comprador;
	}
	/**
	 * @return the vendedor
	 */
	public boolean isVendedor() {
		return vendedor;
	}
	/**
	 * @param vendedor the vendedor to set
	 */
	public void setVendedor(boolean vendedor) {
		this.vendedor = vendedor;
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
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}
	/**
	 * @return the nomenclaturaEmpresa
	 */
	public String getNomenclaturaEmpresa() {
		return nomenclaturaEmpresa;
	}
	/**
	 * @param nomenclaturaEmpresa the nomenclaturaEmpresa to set
	 */
	public void setNomenclaturaEmpresa(String nomenclaturaEmpresa) {
		this.nomenclaturaEmpresa = nomenclaturaEmpresa;
	}
	/**
	 * @return the razonSocial
	 */
	public String getRazonSocial() {
		return razonSocial;
	}
	/**
	 * @param razonSocial the razonSocial to set
	 */
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	/**
	 * @return the rfcEmpresa
	 */
	public String getRfcEmpresa() {
		return rfcEmpresa;
	}
	/**
	 * @param rfcEmpresa the rfcEmpresa to set
	 */
	public void setRfcEmpresa(String rfcEmpresa) {
		this.rfcEmpresa = rfcEmpresa;
	}
	/**
	 * @return the usuarioMySuite
	 */
	public String getUsuarioMySuite() {
		return usuarioMySuite;
	}
	/**
	 * @param usuarioMySuite the usuarioMySuite to set
	 */
	public void setUsuarioMySuite(String usuarioMySuite) {
		this.usuarioMySuite = usuarioMySuite;
	}
	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}
	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}
	/**
	 * @return the colonia
	 */
	public String getColonia() {
		return colonia;
	}
	/**
	 * @param colonia the colonia to set
	 */
	public void setColonia(String colonia) {
		this.colonia = colonia;
	}
	/**
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}
	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
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
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}
	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}
	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}
	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}
	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}
	/**
	 * @return the codigoPostalDelPais
	 */
	public String getCodigoPostalDelPais() {
		return codigoPostalDelPais;
	}
	/**
	 * @param codigoPostalDelPais the codigoPostalDelPais to set
	 */
	public void setCodigoPostalDelPais(String codigoPostalDelPais) {
		this.codigoPostalDelPais = codigoPostalDelPais;
	}
	/**
	 * @return the foliosAsignados
	 */
	public Integer getFoliosAsignados() {
		return foliosAsignados;
	}
	/**
	 * @param foliosAsignados the foliosAsignados to set
	 */
	public void setFoliosAsignados(Integer foliosAsignados) {
		this.foliosAsignados = foliosAsignados;
	}
	/**
	 * @return the folioActual
	 */
	public Integer getFolioActual() {
		return folioActual;
	}
	/**
	 * @param folioActual the folioActual to set
	 */
	public void setFolioActual(Integer folioActual) {
		this.folioActual = folioActual;
	}
	/**
	 * @return the foliosUsadosMes
	 */
	public Integer getFoliosUsadosMes() {
		return foliosUsadosMes;
	}
	/**
	 * @param foliosUsadosMes the foliosUsadosMes to set
	 */
	public void setFoliosUsadosMes(Integer foliosUsadosMes) {
		this.foliosUsadosMes = foliosUsadosMes;
	}
	/**
	 * @param facturaElectronica the facturaElectronica to set
	 */
	public void setFacturaElectronica(boolean facturaElectronica) {
		this.facturaElectronica = facturaElectronica;
	}
	/**
	 * @return the facturaElectronica
	 */
	public boolean isFacturaElectronica() {
		return facturaElectronica;
	}
	/**
	 * @param facturaMatriz the facturaMatriz to set
	 */
	public void setFacturaMatriz(boolean facturaMatriz) {
		this.facturaMatriz = facturaMatriz;
	}
	/**
	 * @return the facturaMatriz
	 */
	public boolean isFacturaMatriz() {
		return facturaMatriz;
	}
	/**
	 * @param totMontoMes the totMontoMes to set
	 */
	public void setTotMontoMes(Double totMontoMes) {
		this.totMontoMes = totMontoMes;
	}
	/**
	 * @return the totMontoMes
	 */
	public Double getTotMontoMes() {
		return totMontoMes;
	}
	/**
	 * @param totCanceladasMes the totCanceladasMes to set
	 */
	public void setTotCanceladasMes(Integer totCanceladasMes) {
		this.totCanceladasMes = totCanceladasMes;
	}
	/**
	 * @return the totCanceladasMes
	 */
	public Integer getTotCanceladasMes() {
		return totCanceladasMes;
	}
	/**
	 * @param facturacionHabilitada the facturacionHabilitada to set
	 */
	public void setFacturacionHabilitada(Boolean facturacionHabilitada) {
		this.facturacionHabilitada = facturacionHabilitada;
	}
	/**
	 * @return the facturacionHabilitada
	 */
	public Boolean getFacturacionHabilitada() {
		return facturacionHabilitada;
	}
	/**
	 * @return the relacionProveedor
	 */
	public Boolean getRelacionProveedor() {
		return relacionProveedor;
	}
	/**
	 * @param relacionProveedor the relacionProveedor to set
	 */
	public void setRelacionProveedor(Boolean relacionProveedor) {
		this.relacionProveedor = relacionProveedor;
	}
	/**
	 * @return the numAsigCliente
	 */
	public String getNumAsigCliente() {
		return numAsigCliente;
	}
	/**
	 * @param numAsigCliente the numAsigCliente to set
	 */
	public void setNumAsigCliente(String numAsigCliente) {
		this.numAsigCliente = numAsigCliente;
	}
	/**
	 * @return the idEmpresaProveedor
	 */
	public Long getIdEmpresaProveedor() {
		return idEmpresaProveedor;
	}
	/**
	 * @param idEmpresaProveedor the idEmpresaProveedor to set
	 */
	public void setIdEmpresaProveedor(Long idEmpresaProveedor) {
		this.idEmpresaProveedor = idEmpresaProveedor;
	}
	/**
	 * @return the idProveedor
	 */
	public Long getIdProveedor() {
		return idProveedor;
	}
	/**
	 * @param idProveedor the idProveedor to set
	 */
	public void setIdProveedor(Long idProveedor) {
		this.idProveedor = idProveedor;
	}
	/**
	 * @return the fechaUltimaActualizacion
	 */
	public Date getFechaUltimaActualizacion() {
		return fechaUltimaActualizacion;
	}
	/**
	 * @param fechaUltimaActualizacion the fechaUltimaActualizacion to set
	 */
	public void setFechaUltimaActualizacion(Date fechaUltimaActualizacion) {
		this.fechaUltimaActualizacion = fechaUltimaActualizacion;
	}
	
	public boolean isExportador() {
		return exportador;
	}
	
	public void setExportador(boolean exportador) {
		this.exportador = exportador;
	}
	public boolean isImportador() {
		return importador;
	}
	
	public void setImportador(boolean importador) {
		this.importador = importador;
	}
	
	public boolean getPadronImportador() {
		return padronImportador;
	}
	
	public void setPadronImportador(boolean padronImportador) {
		this.padronImportador = padronImportador;
	}
	public String getNumCertificado() {
		return numCertificado;
	}
	public void setNumCertificado(String numCertificado) {
		this.numCertificado = numCertificado;
	}
	public String getLlavePrivada() {
		return llavePrivada;
	}
	public void setLlavePrivada(String llavePrivada) {
		this.llavePrivada = llavePrivada;
	}
	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getSerieCFDI() {
		return serieCFDI;
	}
	public void setSerieCFDI(String serieCFDI) {
		this.serieCFDI = serieCFDI;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getRegimenFiscal() {
		return regimenFiscal;
	}
	public void setRegimenFiscal(String regimenFiscal) {
		this.regimenFiscal = regimenFiscal;
	}
	
}