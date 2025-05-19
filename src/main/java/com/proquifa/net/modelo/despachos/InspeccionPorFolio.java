package com.proquifa.net.modelo.despachos;

public class InspeccionPorFolio {
	private String compra;
	private String Pedimento;
	private int puntos;

	public String getCompra() {
		return compra;
	}

	public void setCompra(String compra) {
		this.compra = compra;
	}

	public String getPedimento() {
		return Pedimento;
	}

	public void setPedimento(String pedimento) {
		Pedimento = pedimento;
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	// SuperClase
	public InspeccionPorFolio() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}
