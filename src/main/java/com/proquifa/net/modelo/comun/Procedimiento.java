/**
 * 
 */
package com.proquifa.net.modelo.comun;

/**
 * @author amartinez
 *
 */
public class Procedimiento {
	private Long idProcedimiento;
	private Long subProceso;
	private String nombre;
	private String referencia;
	private String nombreSubproceso;
	private String nombreProceso;
	/**
	 * @return the idProcedimiento
	 */
	public Long getIdProcedimiento() {
		return idProcedimiento;
	}
	/**
	 * @param idProcedimiento the idProcedimiento to set
	 */
	public void setIdProcedimiento(Long idProcedimiento) {
		this.idProcedimiento = idProcedimiento;
	}
	/**
	 * @return the subProceso
	 */
	public Long getSubProceso() {
		return subProceso;
	}
	/**
	 * @param subProceso the subProceso to set
	 */
	public void setSubProceso(Long subProceso) {
		this.subProceso = subProceso;
	}
	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 * @return the referencia
	 */
	public String getReferencia() {
		return referencia;
	}
	/**
	 * @param referencia the referencia to set
	 */
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	/**
	 * @return the nombreSubproceso
	 */
	public String getNombreSubproceso() {
		return nombreSubproceso;
	}
	/**
	 * @param nombreSubproceso the nombreSubproceso to set
	 */
	public void setNombreSubproceso(String nombreSubproceso) {
		this.nombreSubproceso = nombreSubproceso;
	}
	/**
	 * @return the nombreProceso
	 */
	public String getNombreProceso() {
		return nombreProceso;
	}
	/**
	 * @param nombreProceso the nombreProceso to set
	 */
	public void setNombreProceso(String nombreProceso) {
		this.nombreProceso = nombreProceso;
	}
	
}
