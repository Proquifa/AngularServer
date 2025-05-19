package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Sanofi;

import java.util.List;

public class AddendaSanofi {
	private AddendaSanofiHeader header;
	private List<AddendaSanofiDetails> lstDetails;
	
	public AddendaSanofiHeader getHeader() {
		return header;
	}
	
	public void setHeader(AddendaSanofiHeader header) {
		this.header = header;
	}
	
	public List<AddendaSanofiDetails> getLstDetails() {
		return lstDetails;
	}
	
	public void setLstDetails(List<AddendaSanofiDetails> lstDetails) {
		this.lstDetails = lstDetails;
	}
	
}
