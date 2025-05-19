package com.proquifa.net.modelo.comun.util.documentoXls;

import java.util.List;

public class HojaXls {

	private String nombre;
	private int id;
	private List< FilaXls> fila;
	private List <ColumnaXls> Encabezado;
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
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the fila
	 */
	public List<FilaXls> getFila() {
		return fila;
	}
	/**
	 * @param fila the fila to set
	 */
	public void setFila(List<FilaXls> fila) {
		this.fila = fila;
	}
	/**
	 * @return the encabezado
	 */
	public List<ColumnaXls> getEncabezado() {
		return Encabezado;
	}
	/**
	 * @param encabezado the encabezado to set
	 */
	public void setEncabezado(List<ColumnaXls> encabezado) {
		Encabezado = encabezado;
	} 
	
}
