/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

/**
 * @author vromero
 *
 */
public class Horario {
	private Boolean diario;
	private String diaDe1;
	private String diaA1;
	private String diaDe2;
	private String diaA2;
	
	private Boolean lunes;
	private String luDe1;
	private String luA1;
	private String luDe2;
	private String luA2;
	
	private Boolean martes;
	private String maDe1;
	private String maA1;
	private String maDe2;
	private String maA2;
	
	private Boolean miercoles;
	private String miDe1;
	private String miA1;
	private String miDe2;
	private String miA2;
	
	private Boolean jueves;
	private String juDe1;
	private String juA1;
	private String juDe2;
	private String juA2;
	
	private Boolean viernes;
	private String viDe1;
	private String viA1;
	private String viDe2;
	private String viA2;
	
	private String tipo;
	private Direccion direccion;
	
	private Long idCliente;
	private Long idHorario;
	private Long idDireccion;
	
	private List<Integer> plunes;
	private List<Integer> pmartes;
	private List<Integer> pmiercoles;
	private List<Integer> pjueves;
	private List<Integer> pviernes;
	
	
	private String comentarios;
	
	private Boolean borrar;
	private Date fua;

	
	/**
	 * @return the plunes
	 */
	public List<Integer> getPlunes() {
		return plunes;
	}
	/**
	 * @param plunes the plunes to set
	 */
	public void setPlunes(List<Integer> plunes) {
		this.plunes = plunes;
	}
	/**
	 * @return the pmartes
	 */
	public List<Integer> getPmartes() {
		return pmartes;
	}
	/**
	 * @param pmartes the pmartes to set
	 */
	public void setPmartes(List<Integer> pmartes) {
		this.pmartes = pmartes;
	}
	/**
	 * @return the pmiercoles
	 */
	public List<Integer> getPmiercoles() {
		return pmiercoles;
	}
	/**
	 * @param pmiercoles the pmiercoles to set
	 */
	public void setPmiercoles(List<Integer> pmiercoles) {
		this.pmiercoles = pmiercoles;
	}
	/**
	 * @return the pjueves
	 */
	public List<Integer> getPjueves() {
		return pjueves;
	}
	/**
	 * @param pjueves the pjueves to set
	 */
	public void setPjueves(List<Integer> pjueves) {
		this.pjueves = pjueves;
	}
	/**
	 * @return the pviernes
	 */
	public List<Integer> getPviernes() {
		return pviernes;
	}
	/**
	 * @param pviernes the pviernes to set
	 */
	public void setPviernes(List<Integer> pviernes) {
		this.pviernes = pviernes;
	}
	public Horario() {
		direccion = new Direccion();
	}
	/**
	 * @return the diario
	 */
	public Boolean getDiario() {
		return diario;
	}
	/**
	 * @param diario the diario to set
	 */
	public void setDiario(Boolean diario) {
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
	public Boolean getLunes() {
		return lunes;
	}
	/**
	 * @param lunes the lunes to set
	 */
	public void setLunes(Boolean lunes) {
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
	public Boolean getMartes() {
		return martes;
	}
	/**
	 * @param martes the martes to set
	 */
	public void setMartes(Boolean martes) {
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
	public Boolean getMiercoles() {
		return miercoles;
	}
	/**
	 * @param miercoles the miercoles to set
	 */
	public void setMiercoles(Boolean miercoles) {
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
	public Boolean getJueves() {
		return jueves;
	}
	/**
	 * @param jueves the jueves to set
	 */
	public void setJueves(Boolean jueves) {
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
	public Boolean getViernes() {
		return viernes;
	}
	/**
	 * @param viernes the viernes to set
	 */
	public void setViernes(Boolean viernes) {
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
	 * @return the tipo
	 */
	public String getTipo() {
		return tipo;
	}
	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	/**
	 * @return the idCliente
	 */
	public Long getIdCliente() {
		return idCliente;
	}
	/**
	 * @param idCliente the idCliente to set
	 */
	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}
	/**
	 * @return the idHorario
	 */
	public Long getIdHorario() {
		return idHorario;
	}
	/**
	 * @param idHorario the idHorario to set
	 */
	public void setIdHorario(Long idHorario) {
		this.idHorario = idHorario;
	}
	/**
	 * @return the direccion
	 */
	public Direccion getDireccion() {
		return direccion;
	}
	/**
	 * @param direccion the direccion to set
	 */
	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}
	/**
	 * @return the idDireccion
	 */
	public Long getIdDireccion() {
		return idDireccion;
	}
	/**
	 * @param idDireccion the idDireccion to set
	 */
	public void setIdDireccion(Long idDireccion) {
		this.idDireccion = idDireccion;
	}
	public Boolean getBorrar() {
		return borrar;
	}
	public void setBorrar(Boolean borrar) {
		this.borrar = borrar;
	}
	/**
	 * @return the fua
	 */
	public Date getFua() {
		return fua;
	}
	/**
	 * @param fua the fua to set
	 */
	public void setFua(Date fua) {
		this.fua = fua;
	}
	
	 public String getComentarios() {
			return comentarios;
		}
	 
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	
}
