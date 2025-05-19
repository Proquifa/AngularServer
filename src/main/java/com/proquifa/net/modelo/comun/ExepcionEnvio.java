package com.proquifa.net.modelo.comun;

import java.util.List;

public class ExepcionEnvio {
	
	String servicio;
	String metodo;
	String mensaje;
	List<Object> parametros;

	
	public String getServicio() {
		return servicio;
	}
	public void setServicio(String servicio) {
		this.servicio = servicio;
	}
	public String getMetodo() {
		return metodo;
	}
	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public List<Object> getParametros() {
		return parametros;
	}
	public void setParametros(List<Object> parametros) {
		this.parametros = parametros;
	}

}
