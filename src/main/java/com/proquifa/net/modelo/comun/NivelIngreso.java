package com.proquifa.net.modelo.comun;

import java.util.Date;

public class NivelIngreso {

	private Long idNivelIngreso;
	private Double max;
	private Double min;
	private String nivel;
	private Date fua;
	
	/**
	 * 
	 */
	public NivelIngreso() {
		super();
	}

	/**
	 * @return the idNivelIngreso
	 */
	public Long getIdNivelIngreso() {
		return idNivelIngreso;
	}

	/**
	 * @param idNivelIngreso the idNivelIngreso to set
	 */
	public void setIdNivelIngreso(Long idNivelIngreso) {
		this.idNivelIngreso = idNivelIngreso;
	}

	/**
	 * @return the max
	 */
	public Double getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Double max) {
		this.max = max;
	}

	/**
	 * @return the min
	 */
	public Double getMin() {
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(Double min) {
		this.min = min;
	}

	/**
	 * @return the nivel
	 */
	public String getNivel() {
		return nivel;
	}

	/**
	 * @param nivel the nivel to set
	 */
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}

	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}
	
	
}
