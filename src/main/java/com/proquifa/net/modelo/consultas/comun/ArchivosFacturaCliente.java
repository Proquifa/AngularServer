package com.proquifa.net.modelo.consultas.comun;

public class ArchivosFacturaCliente {
	private String nombreCliente;
	private String[] rutasArchivos;
	private String[] nombresArchivos;
	
	public String getNombreCliente() {
		return nombreCliente;
	}
	
	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}
	
	public String[] getRutasArchivos() {
		return rutasArchivos;
	}
	
	public void setRutasArchivos(String[] rutasArchivos) {
		this.rutasArchivos = rutasArchivos;
	}
	
	public String[] getNombresArchivos() {
		return nombresArchivos;
	}
	
	public void setNombresArchivos(String[] nombresArchivos) {
		this.nombresArchivos = nombresArchivos;
	}
}
