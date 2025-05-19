package com.proquifa.net.modelo.comun.util.documentoXls;

import java.util.List;

public class FilaXls {
	private int id; 
	private List <ColumnaXls> columna;
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
	 * @return the columna
	 */
	public List<ColumnaXls> getColumna() {
		return columna;
	}
	/**
	 * @param columna the columna to set
	 */
	public void setColumna(List<ColumnaXls> columna) {
		this.columna = columna;
	} 
}
