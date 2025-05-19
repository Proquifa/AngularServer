package com.proquifa.net.modelo.compras;

import java.util.List;
import com.proquifa.net.modelo.comun.Archivo;

public class Pieza {

	private Long idPieza;
	private Long idPCompra;
	private Boolean despachable;
	private String codigo;
	private String fabrica;
	private Boolean revisoCatalogo;
	private Boolean revisoDescripcion;
	private Boolean revisoPresentacion;
	private Boolean revisoFisicamenteC;
	private Boolean revisoCaducidad;
	private Boolean revisoLote;
	private Boolean revisoDocumentacion;
	private Boolean revisoEdicion;
	private Boolean revisoIdioma;
	private String loteTxt;
	private List<Archivo> listArchivos;
	private Boolean VerificoPieza;
	private String edoPieza;
	private String comentarioRechazo;
	private String mesCaduca;
	private String anoCaduca;
	private String instrucciones;
	private Boolean AStock;
	
	//SuperClase
	public Pieza() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdPieza() {
		return idPieza;
	}

	public void setIdPieza(Long idPieza) {
		this.idPieza = idPieza;
	}

	public Long getIdPCompra() {
		return idPCompra;
	}

	public void setIdPCompra(Long idPCompra) {
		this.idPCompra = idPCompra;
	}

	public Boolean getDespachable() {
		return despachable;
	}

	public void setDespachable(Boolean despachable) {
		this.despachable = despachable;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getFabrica() {
		return fabrica;
	}

	public void setFabrica(String fabrica) {
		this.fabrica = fabrica;
	}

	public Boolean getRevisoCatalogo() {
		return revisoCatalogo;
	}

	public void setRevisoCatalogo(Boolean revisoCatalogo) {
		this.revisoCatalogo = revisoCatalogo;
	}

	public Boolean getRevisoDescripcion() {
		return revisoDescripcion;
	}

	public void setRevisoDescripcion(Boolean revisoDescripcion) {
		this.revisoDescripcion = revisoDescripcion;
	}

	public Boolean getRevisoPresentacion() {
		return revisoPresentacion;
	}

	public void setRevisoPresentacion(Boolean revisoPresentacion) {
		this.revisoPresentacion = revisoPresentacion;
	}

	public Boolean getRevisoFisicamenteC() {
		return revisoFisicamenteC;
	}

	public void setRevisoFisicamenteC(Boolean revisoFisicamenteC) {
		this.revisoFisicamenteC = revisoFisicamenteC;
	}

	public Boolean getRevisoCaducidad() {
		return revisoCaducidad;
	}

	public void setRevisoCaducidad(Boolean revisoCaducidad) {
		this.revisoCaducidad = revisoCaducidad;
	}

	public Boolean getRevisoLote() {
		return revisoLote;
	}

	public void setRevisoLote(Boolean revisoLote) {
		this.revisoLote = revisoLote;
	}

	public Boolean getRevisoDocumentacion() {
		return revisoDocumentacion;
	}

	public void setRevisoDocumentacion(Boolean revisoDocumentacion) {
		this.revisoDocumentacion = revisoDocumentacion;
	}

	public Boolean getRevisoEdicion() {
		return revisoEdicion;
	}

	public void setRevisoEdicion(Boolean revisoEdicion) {
		this.revisoEdicion = revisoEdicion;
	}

	public Boolean getRevisoIdioma() {
		return revisoIdioma;
	}

	public void setRevisoIdioma(Boolean revisoIdioma) {
		this.revisoIdioma = revisoIdioma;
	}

	public String getLoteTxt() {
		return loteTxt;
	}

	public void setLoteTxt(String loteTxt) {
		this.loteTxt = loteTxt;
	}

	public List<Archivo> getListArchivos() {
		return listArchivos;
	}

	public void setListArchivos(List<Archivo> listArchivos) {
		this.listArchivos = listArchivos;
	}

	public Boolean getVerificoPieza() {
		return VerificoPieza;
	}

	public void setVerificoPieza(Boolean verificoPieza) {
		VerificoPieza = verificoPieza;
	}

	public String getEdoPieza() {
		return edoPieza;
	}

	public void setEdoPieza(String edoPieza) {
		this.edoPieza = edoPieza;
	}

	public String getComentarioRechazo() {
		return comentarioRechazo;
	}

	public void setComentarioRechazo(String comentarioRechazo) {
		this.comentarioRechazo = comentarioRechazo;
	}

	public String getMesCaduca() {
		return mesCaduca;
	}

	public void setMesCaduca(String mesCaduca) {
		this.mesCaduca = mesCaduca;
	}

	public String getAnoCaduca() {
		return anoCaduca;
	}

	public void setAnoCaduca(String anoCaduca) {
		this.anoCaduca = anoCaduca;
	}

	public String getInstrucciones() {
		return instrucciones;
	}

	public void setInstrucciones(String instrucciones) {
		this.instrucciones = instrucciones;
	}

	public Boolean getAStock() {
		return AStock;
	}

	public void setAStock(Boolean aStock) {
		AStock = aStock;
	}
	
	
	
	
}
