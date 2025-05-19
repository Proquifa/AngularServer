package com.proquifa.net.modelo.comun.facturaElectronica.addenda.Darier;

public class AddendaDarierEmisor {
	private String numeroDeProveedor;
	private String registroPublico;
	private String codigoDeConvenioBancario;
	private String eanEmisor;
	private String emailContacto1;
	private String emailContacto2;
	
	public String getNumeroDeProveedor() {
		return numeroDeProveedor;
	}
	
	public void setNumeroDeProveedor(String numeroDeProveedor) {
		this.numeroDeProveedor = numeroDeProveedor;
	}
	
	public String getRegistroPublico() {
		return registroPublico;
	}
	
	public void setRegistroPublico(String registroPublico) {
		this.registroPublico = registroPublico;
	}
	
	public String getEmailContacto1() {
		return emailContacto1;
	}
	
	public void setEmailContacto1(String emailContacto1) {
		this.emailContacto1 = emailContacto1;
	}

	public String getCodigoDeConvenioBancario() {
		return codigoDeConvenioBancario;
	}

	public void setCodigoDeConvenioBancario(String codigoDeConvenioBancario) {
		this.codigoDeConvenioBancario = codigoDeConvenioBancario;
	}

	public String getEanEmisor() {
		return eanEmisor;
	}

	public void setEanEmisor(String eanEmisor) {
		this.eanEmisor = eanEmisor;
	}

	public String getEmailContacto2() {
		return emailContacto2;
	}

	public void setEmailContacto2(String emailContacto2) {
		this.emailContacto2 = emailContacto2;
	}

}
