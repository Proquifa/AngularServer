package com.proquifa.net.modelo.ventas.admoncomunicacion;


import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MiAutenticador extends Authenticator {
	 
	private PasswordAuthentication passwordAuthentication;
 
	public MiAutenticador(String user, String pwd) {
		this.passwordAuthentication = new PasswordAuthentication(user, pwd);
	}
 
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return passwordAuthentication;
	}
}