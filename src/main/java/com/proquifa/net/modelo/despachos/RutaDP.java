/**
 * 
 */
package com.proquifa.net.modelo.despachos;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ymendez
 *
 */
public class RutaDP implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5622385083558862502L;
	private String idRuta;
	private String idSurtido;
	private String idCliente;
	private String factura;
	private String fpor;
	private String chkDiferente;
	private String mapa;
	private String destino;
	private String pais;
	private String estado;
	private String calle;
	private String delegacion;
	private String cp;
	private String ruta;
	private String zonaMensajeria;
	private String diario;
	private String diaDe1;
	private String diaA1;
	private String diaDe2;
	private String diaA2;
	private String lunes;
	private String luDe1;
	private String luDe2;
	private String luA1;
	private String luA2;
	private String martes;
	private String maDe1;
	private String maDe2;
	private String maA1;
	private String maA2;
	private String miercoles;
	private String miDe1;
	private String miDe2;
	private String miA1;
	private String miA2;
	private String jueves;
	private String juDe1;
	private String juDe2;
	private String juA1;
	private String juA2;
	private String viernes;
	private String viDe1;
	private String viDe2;
	private String viA1;
	private String viA2;
	private Date fecha;
	private String comentarios;
	private String comentariosSurtir;
	private String facturaRevision;
	private String idPackingList;
	private String estadoRuta;
	private String idHorario;
	
	private String folioRemision;
	private String facturadoRemision;
	private Integer idRemision;
	
	/**
	 * 
	 */
	public RutaDP() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the idRuta
	 */
	public String getIdRuta() {
		return idRuta;
	}

	/**
	 * @param idRuta the idRuta to set
	 */
	public void setIdRuta(String idRuta) {
		this.idRuta = idRuta;
	}

	/**
	 * @return the idSurtido
	 */
	public String getIdSurtido() {
		return idSurtido;
	}

	/**
	 * @param idSurtido the idSurtido to set
	 */
	public void setIdSurtido(String idSurtido) {
		this.idSurtido = idSurtido;
	}

	/**
	 * @return the idCliente
	 */
	public String getIdCliente() {
		return idCliente;
	}

	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	/**
	 * @return the factura
	 */
	public String getFactura() {
		return factura;
	}

	/**
	 * @param factura the factura to set
	 */
	public void setFactura(String factura) {
		this.factura = factura;
	}

	/**
	 * @return the fpor
	 */
	public String getFpor() {
		return fpor;
	}

	/**
	 * @param fpor the fpor to set
	 */
	public void setFpor(String fpor) {
		this.fpor = fpor;
	}

	/**
	 * @return the mapa
	 */
	public String getMapa() {
		return mapa;
	}

	/**
	 * @param mapa the mapa to set
	 */
	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	/**
	 * @return the destino
	 */
	public String getDestino() {
		return destino;
	}

	/**
	 * @param destino the destino to set
	 */
	public void setDestino(String destino) {
		this.destino = destino;
	}

	/**
	 * @return the pais
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * @param pais the pais to set
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the calle
	 */
	public String getCalle() {
		return calle;
	}

	/**
	 * @param calle the calle to set
	 */
	public void setCalle(String calle) {
		this.calle = calle;
	}

	/**
	 * @return the delegacion
	 */
	public String getDelegacion() {
		return delegacion;
	}

	/**
	 * @param delegacion the delegacion to set
	 */
	public void setDelegacion(String delegacion) {
		this.delegacion = delegacion;
	}

	/**
	 * @return the cp
	 */
	public String getCp() {
		return cp;
	}

	/**
	 * @param cp the cp to set
	 */
	public void setCp(String cp) {
		this.cp = cp;
	}

	/**
	 * @return the ruta
	 */
	public String getRuta() {
		return ruta;
	}

	/**
	 * @param ruta the ruta to set
	 */
	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	/**
	 * @return the zonaMensajeria
	 */
	public String getZonaMensajeria() {
		return zonaMensajeria;
	}

	/**
	 * @param zonaMensajeria the zonaMensajeria to set
	 */
	public void setZonaMensajeria(String zonaMensajeria) {
		this.zonaMensajeria = zonaMensajeria;
	}

	/**
	 * @return the diario
	 */
	public String getDiario() {
		return diario;
	}

	/**
	 * @param diario the diario to set
	 */
	public void setDiario(String diario) {
		this.diario = diario;
	}

	/**
	 * @return the diaDe1
	 */
	public String getDiaDe1() {
		return diaDe1;
	}

	/**
	 * @param diaDe1 the diaDe1 to set
	 */
	public void setDiaDe1(String diaDe1) {
		this.diaDe1 = diaDe1;
	}

	/**
	 * @return the diaA1
	 */
	public String getDiaA1() {
		return diaA1;
	}

	/**
	 * @param diaA1 the diaA1 to set
	 */
	public void setDiaA1(String diaA1) {
		this.diaA1 = diaA1;
	}

	/**
	 * @return the diaDe2
	 */
	public String getDiaDe2() {
		return diaDe2;
	}

	/**
	 * @param diaDe2 the diaDe2 to set
	 */
	public void setDiaDe2(String diaDe2) {
		this.diaDe2 = diaDe2;
	}

	/**
	 * @return the diaA2
	 */
	public String getDiaA2() {
		return diaA2;
	}

	/**
	 * @param diaA2 the diaA2 to set
	 */
	public void setDiaA2(String diaA2) {
		this.diaA2 = diaA2;
	}

	/**
	 * @return the lunes
	 */
	public String getLunes() {
		return lunes;
	}

	/**
	 * @param lunes the lunes to set
	 */
	public void setLunes(String lunes) {
		this.lunes = lunes;
	}

	/**
	 * @return the luDe1
	 */
	public String getLuDe1() {
		return luDe1;
	}

	/**
	 * @param luDe1 the luDe1 to set
	 */
	public void setLuDe1(String luDe1) {
		this.luDe1 = luDe1;
	}

	/**
	 * @return the luDe2
	 */
	public String getLuDe2() {
		return luDe2;
	}

	/**
	 * @param luDe2 the luDe2 to set
	 */
	public void setLuDe2(String luDe2) {
		this.luDe2 = luDe2;
	}

	/**
	 * @return the luA1
	 */
	public String getLuA1() {
		return luA1;
	}

	/**
	 * @param luA1 the luA1 to set
	 */
	public void setLuA1(String luA1) {
		this.luA1 = luA1;
	}

	/**
	 * @return the luA2
	 */
	public String getLuA2() {
		return luA2;
	}

	/**
	 * @param luA2 the luA2 to set
	 */
	public void setLuA2(String luA2) {
		this.luA2 = luA2;
	}

	/**
	 * @return the martes
	 */
	public String getMartes() {
		return martes;
	}

	/**
	 * @param martes the martes to set
	 */
	public void setMartes(String martes) {
		this.martes = martes;
	}

	/**
	 * @return the maDe1
	 */
	public String getMaDe1() {
		return maDe1;
	}

	/**
	 * @param maDe1 the maDe1 to set
	 */
	public void setMaDe1(String maDe1) {
		this.maDe1 = maDe1;
	}

	/**
	 * @return the maDe2
	 */
	public String getMaDe2() {
		return maDe2;
	}

	/**
	 * @param maDe2 the maDe2 to set
	 */
	public void setMaDe2(String maDe2) {
		this.maDe2 = maDe2;
	}

	/**
	 * @return the maA1
	 */
	public String getMaA1() {
		return maA1;
	}

	/**
	 * @param maA1 the maA1 to set
	 */
	public void setMaA1(String maA1) {
		this.maA1 = maA1;
	}

	/**
	 * @return the maA2
	 */
	public String getMaA2() {
		return maA2;
	}

	/**
	 * @param maA2 the maA2 to set
	 */
	public void setMaA2(String maA2) {
		this.maA2 = maA2;
	}

	/**
	 * @return the miercoles
	 */
	public String getMiercoles() {
		return miercoles;
	}

	/**
	 * @param miercoles the miercoles to set
	 */
	public void setMiercoles(String miercoles) {
		this.miercoles = miercoles;
	}

	/**
	 * @return the miDe1
	 */
	public String getMiDe1() {
		return miDe1;
	}

	/**
	 * @param miDe1 the miDe1 to set
	 */
	public void setMiDe1(String miDe1) {
		this.miDe1 = miDe1;
	}

	/**
	 * @return the miDe2
	 */
	public String getMiDe2() {
		return miDe2;
	}

	/**
	 * @param miDe2 the miDe2 to set
	 */
	public void setMiDe2(String miDe2) {
		this.miDe2 = miDe2;
	}

	/**
	 * @return the miA1
	 */
	public String getMiA1() {
		return miA1;
	}

	/**
	 * @param miA1 the miA1 to set
	 */
	public void setMiA1(String miA1) {
		this.miA1 = miA1;
	}

	/**
	 * @return the miA2
	 */
	public String getMiA2() {
		return miA2;
	}

	/**
	 * @param miA2 the miA2 to set
	 */
	public void setMiA2(String miA2) {
		this.miA2 = miA2;
	}

	/**
	 * @return the jueves
	 */
	public String getJueves() {
		return jueves;
	}

	/**
	 * @param jueves the jueves to set
	 */
	public void setJueves(String jueves) {
		this.jueves = jueves;
	}

	/**
	 * @return the juDe1
	 */
	public String getJuDe1() {
		return juDe1;
	}

	/**
	 * @param juDe1 the juDe1 to set
	 */
	public void setJuDe1(String juDe1) {
		this.juDe1 = juDe1;
	}

	/**
	 * @return the juDe2
	 */
	public String getJuDe2() {
		return juDe2;
	}

	/**
	 * @param juDe2 the juDe2 to set
	 */
	public void setJuDe2(String juDe2) {
		this.juDe2 = juDe2;
	}

	/**
	 * @return the juA1
	 */
	public String getJuA1() {
		return juA1;
	}

	/**
	 * @param juA1 the juA1 to set
	 */
	public void setJuA1(String juA1) {
		this.juA1 = juA1;
	}

	/**
	 * @return the juA2
	 */
	public String getJuA2() {
		return juA2;
	}

	/**
	 * @param juA2 the juA2 to set
	 */
	public void setJuA2(String juA2) {
		this.juA2 = juA2;
	}

	/**
	 * @return the viernes
	 */
	public String getViernes() {
		return viernes;
	}

	/**
	 * @param viernes the viernes to set
	 */
	public void setViernes(String viernes) {
		this.viernes = viernes;
	}

	/**
	 * @return the viDe1
	 */
	public String getViDe1() {
		return viDe1;
	}

	/**
	 * @param viDe1 the viDe1 to set
	 */
	public void setViDe1(String viDe1) {
		this.viDe1 = viDe1;
	}

	/**
	 * @return the viDe2
	 */
	public String getViDe2() {
		return viDe2;
	}

	/**
	 * @param viDe2 the viDe2 to set
	 */
	public void setViDe2(String viDe2) {
		this.viDe2 = viDe2;
	}

	/**
	 * @return the viA1
	 */
	public String getViA1() {
		return viA1;
	}

	/**
	 * @param viA1 the viA1 to set
	 */
	public void setViA1(String viA1) {
		this.viA1 = viA1;
	}

	/**
	 * @return the viA2
	 */
	public String getViA2() {
		return viA2;
	}

	/**
	 * @param viA2 the viA2 to set
	 */
	public void setViA2(String viA2) {
		this.viA2 = viA2;
	}

	/**
	 * @return the fecha
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * @return the comentarios
	 */
	public String getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the comentariosSurtir
	 */
	public String getComentariosSurtir() {
		return comentariosSurtir;
	}

	/**
	 * @param comentariosSurtir the comentariosSurtir to set
	 */
	public void setComentariosSurtir(String comentariosSurtir) {
		this.comentariosSurtir = comentariosSurtir;
	}

	/**
	 * @return the facturaRevision
	 */
	public String getFacturaRevision() {
		return facturaRevision;
	}

	/**
	 * @param facturaRevision the facturaRevision to set
	 */
	public void setFacturaRevision(String facturaRevision) {
		this.facturaRevision = facturaRevision;
	}

	/**
	 * @return the idPackingList
	 */
	public String getIdPackingList() {
		return idPackingList;
	}

	/**
	 * @param idPackingList the idPackingList to set
	 */
	public void setIdPackingList(String idPackingList) {
		this.idPackingList = idPackingList;
	}

	/**
	 * @return the estadoRuta
	 */
	public String getEstadoRuta() {
		return estadoRuta;
	}

	/**
	 * @param estadoRuta the estadoRuta to set
	 */
	public void setEstadoRuta(String estadoRuta) {
		this.estadoRuta = estadoRuta;
	}

	/**
	 * @return the idHorario
	 */
	public String getIdHorario() {
		return idHorario;
	}

	/**
	 * @param idHorario the idHorario to set
	 */
	public void setIdHorario(String idHorario) {
		this.idHorario = idHorario;
	}

	/**
	 * @return the chkDiferente
	 */
	public String getChkDiferente() {
		return chkDiferente;
	}

	/**
	 * @param chkDiferente the chkDiferente to set
	 */
	public void setChkDiferente(String chkDiferente) {
		this.chkDiferente = chkDiferente;
	}

	/**
	 * @return the folioRemision
	 */
	public String getFolioRemision() {
		return folioRemision;
	}

	/**
	 * @param folioRemision the folioRemision to set
	 */
	public void setFolioRemision(String folioRemision) {
		this.folioRemision = folioRemision;
	}

	/**
	 * @return the facturadoRemision
	 */
	public String getFacturadoRemision() {
		return facturadoRemision;
	}

	/**
	 * @param facturadoRemision the facturadoRemision to set
	 */
	public void setFacturadoRemision(String facturadoRemision) {
		this.facturadoRemision = facturadoRemision;
	}

	/**
	 * @return the idRemision
	 */
	public Integer getIdRemision() {
		return idRemision;
	}

	/**
	 * @param idRemision the idRemision to set
	 */
	public void setIdRemision(Integer idRemision) {
		this.idRemision = idRemision;
	}
	
	
	
	
}
