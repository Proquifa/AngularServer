package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier;

import java.util.List;

public class AddendaDarier {
	private AddendaDarierDocumento documento;
	private List<AddendaDarierConcepto> conceptos;
	private AddendaDarierTotales totales;

	public AddendaDarierDocumento getDocumento() {
		return documento;
	}

	public void setDocumento(AddendaDarierDocumento documento) {
		this.documento = documento;
	}

	public List<AddendaDarierConcepto> getConceptos() {
		return conceptos;
	}

	public void setConceptos(List<AddendaDarierConcepto> conceptos) {
		this.conceptos = conceptos;
	}

	public AddendaDarierTotales getTotales() {
		return totales;
	}

	public void setTotales(AddendaDarierTotales totales) {
		this.totales = totales;
	}

}
