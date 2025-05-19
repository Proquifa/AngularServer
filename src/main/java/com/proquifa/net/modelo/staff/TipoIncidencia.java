package com.proquifa.net.modelo.staff;

public class TipoIncidencia {

	private String cveIncidencia; //Clave de Incidencia
	private String nomIncidencia; //Nombre de la Incidencia
	
	/**
	 * @return the cveIncidencia
	 */
	public String getCveIncidencia() {
		return cveIncidencia;
	}
	/**
	 * @param cveIncidencia the cveIncidencia to set
	 */
	public void setCveIncidencia(String cveIncidencia) {
		this.cveIncidencia = cveIncidencia;
	}
	/**
	 * @return the nomIncidencia
	 */
	public String getNomIncidencia() {
		return nomIncidencia;
	}
	/**
	 * @param nomIncidencia the nomIncidencia to set
	 */
	public void setNomIncidencia(String nomIncidencia) {
		this.nomIncidencia = nomIncidencia;
	}
}
