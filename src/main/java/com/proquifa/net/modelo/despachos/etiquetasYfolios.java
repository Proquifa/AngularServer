package com.proquifa.net.modelo.despachos;

import java.util.List;

/**
 * @author nbaron
 *
 */

public class etiquetasYfolios {
	
	private List<byte[]> etiquetas; 
	private List<String> folios;
	
	
	public List<byte[]> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(List<byte[]> etiquetas) {
		this.etiquetas = etiquetas;
	}
	public List<String> getFolios() {
		return folios;
	}
	public void setFolios(List<String> folios) {
		this.folios = folios;
	}

}
