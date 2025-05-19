package com.proquifa.net.modelo.comun.facturaElectronica;

public class CfdiRelacionado {
	
	private Long idCfdiRelacionado;
	private String tipoRelacion;
	private String uuid;
	private Long idFacturaElectronica;
	
	/**
	 * @return the idCfdiRelacionado
	 */
	public Long getIdCfdiRelacionado() {
		return idCfdiRelacionado;
	}
	
	/**
	 * @param idCfdiRelacionado the idCfdiRelacionado to set
	 */
	public void setIdCfdiRelacionado(Long idCfdiRelacionado) {
		this.idCfdiRelacionado = idCfdiRelacionado;
	}
	
	/**
	 * @return the tipoRelacion
	 */
	public String getTipoRelacion() {
		return tipoRelacion;
	}
	
	/**
	 * @param tipoRelacion the tipoRelacion to set
	 */
	public void setTipoRelacion(String tipoRelacion) {
		this.tipoRelacion = tipoRelacion;
	}
	
	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}
	
	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	/**
	 * @return the idFacturaElectronica
	 */
	public Long getIdFacturaElectronica() {
		return idFacturaElectronica;
	}
	
	/**
	 * @param idFacturaElectronica the idFacturaElectronica to set
	 */
	public void setIdFacturaElectronica(Long idFacturaElectronica) {
		this.idFacturaElectronica = idFacturaElectronica;
	}

	
}
