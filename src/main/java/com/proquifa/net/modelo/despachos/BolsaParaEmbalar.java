package com.proquifa.net.modelo.despachos;

import java.io.Serializable;
import java.util.List;

public class BolsaParaEmbalar implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4262637174500973359L;
	private Long noPiezas;
	private Long noPartidas;
	private String manejo;
	private String serial;
	private String idPack;
	private String modelo;
	private List<PartidaInspeccion> arrayProductos;
	
	//SuperClass
	public BolsaParaEmbalar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getNoPiezas() {
		return noPiezas;
	}

	public void setNoPiezas(Long noPiezas) {
		this.noPiezas = noPiezas;
	}

	public Long getNoPartidas() {
		return noPartidas;
	}

	public void setNoPartidas(Long noPartidas) {
		this.noPartidas = noPartidas;
	}

	public String getManejo() {
		return manejo;
	}

	public void setManejo(String manejo) {
		this.manejo = manejo;
	}

	public String getSerial() {
		return serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getIdPack() {
		return idPack;
	}

	public void setIdPack(String idPack) {
		this.idPack = idPack;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public List<PartidaInspeccion> getArrayProductos() {
		return arrayProductos;
	}

	public void setArrayProductos(List<PartidaInspeccion> arrayProductos) {
		this.arrayProductos = arrayProductos;
	}
	
	

	
	
}
