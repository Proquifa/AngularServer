/**
 * 
 */
package com.proquifa.net.modelo.ventas;

import java.util.Date;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;

/**
 * @author yosimar.mendez
 *
 */
public class DoctoR {

	private Integer folio;
	private int part;
	private Date fecha;
	private String manejo;
	private String origen;
	private Cliente cliente; //en la tabla = Empresa
	private String rpor;
	private String medio;
	private String docto;
	private String numero;
	private String observa;
	private Date fproceso;
	private String ingreso;
	private int forigen;
	private String estado;
	private String folioPadre;
	private boolean hijo;
	private Date fhorigen;
	private Contacto contacto;
	private Integer clienteNuevo;
	private Date fechaps;

	/**
	 * 
	 */
	public DoctoR() {
		super();
	}

	/**
	 * @param folio
	 * @param part
	 * @param fecha
	 * @param manejo
	 * @param origen
	 * @param cliente
	 * @param rpor
	 * @param medio
	 * @param docto
	 * @param numero
	 * @param observa
	 * @param fproceso
	 * @param ingreso
	 * @param forigen
	 * @param estado
	 * @param folioPadre
	 * @param hijo
	 * @param fhorigen
	 * @param contacto
	 */
	public DoctoR(Integer folio, int part, Date fecha, String manejo, String origen, Cliente cliente, String rpor,
			String medio, String docto, String numero, String observa, Date fproceso, String ingreso, int forigen,
			String estado, String folioPadre, boolean hijo, Date fhorigen, Contacto contacto) {
		super();
		this.folio = folio;
		this.part = part;
		this.fecha = fecha;
		this.manejo = manejo;
		this.origen = origen;
		this.cliente = cliente;
		this.rpor = rpor;
		this.medio = medio;
		this.docto = docto;
		this.numero = numero;
		this.observa = observa;
		this.fproceso = fproceso;
		this.ingreso = ingreso;
		this.forigen = forigen;
		this.estado = estado;
		this.folioPadre = folioPadre;
		this.hijo = hijo;
		this.fhorigen = fhorigen;
		this.contacto = contacto;
	}

	/**
	 * @return the folio
	 */
	public Integer getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	/**
	 * @return the part
	 */
	public int getPart() {
		return part;
	}

	/**
	 * @param part the part to set
	 */
	public void setPart(int part) {
		this.part = part;
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
	 * @return the manejo
	 */
	public String getManejo() {
		return manejo;
	}

	/**
	 * @param manejo the manejo to set
	 */
	public void setManejo(String manejo) {
		this.manejo = manejo;
	}

	/**
	 * @return the origen
	 */
	public String getOrigen() {
		return origen;
	}

	/**
	 * @param origen the origen to set
	 */
	public void setOrigen(String origen) {
		this.origen = origen;
	}


	/**
	 * @return the rpor
	 */
	public String getRpor() {
		return rpor;
	}

	/**
	 * @param rpor the rpor to set
	 */
	public void setRpor(String rpor) {
		this.rpor = rpor;
	}

	/**
	 * @return the medio
	 */
	public String getMedio() {
		return medio;
	}

	/**
	 * @param medio the medio to set
	 */
	public void setMedio(String medio) {
		this.medio = medio;
	}

	/**
	 * @return the docto
	 */
	public String getDocto() {
		return docto;
	}

	/**
	 * @param docto the docto to set
	 */
	public void setDocto(String docto) {
		this.docto = docto;
	}

	/**
	 * @return the numero
	 */
	public String getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(String numero) {
		this.numero = numero;
	}

	/**
	 * @return the observa
	 */
	public String getObserva() {
		return observa;
	}

	/**
	 * @param observa the observa to set
	 */
	public void setObserva(String observa) {
		this.observa = observa;
	}

	/**
	 * @return the fproceso
	 */
	public Date getFproceso() {
		return fproceso;
	}

	/**
	 * @param fproceso the fproceso to set
	 */
	public void setFproceso(Date fproceso) {
		this.fproceso = fproceso;
	}

	/**
	 * @return the ingreso
	 */
	public String getIngreso() {
		return ingreso;
	}

	/**
	 * @param ingreso the ingreso to set
	 */
	public void setIngreso(String ingreso) {
		this.ingreso = ingreso;
	}

	/**
	 * @return the forigen
	 */
	public int getForigen() {
		return forigen;
	}

	/**
	 * @param forigen the forigen to set
	 */
	public void setForigen(int forigen) {
		this.forigen = forigen;
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
	 * @return the folioPadre
	 */
	public String getFolioPadre() {
		return folioPadre;
	}

	/**
	 * @param folioPadre the folioPadre to set
	 */
	public void setFolioPadre(String folioPadre) {
		this.folioPadre = folioPadre;
	}

	/**
	 * @return the hijo
	 */
	public boolean isHijo() {
		return hijo;
	}

	/**
	 * @param hijo the hijo to set
	 */
	public void setHijo(boolean hijo) {
		this.hijo = hijo;
	}

	/**
	 * @return the fhorigen
	 */
	public Date getFhorigen() {
		return fhorigen;
	}

	/**
	 * @param fhorigen the fhorigen to set
	 */
	public void setFhorigen(Date fhorigen) {
		this.fhorigen = fhorigen;
	}

	/**
	 * @return the contacto
	 */
	public Contacto getContacto() {
		return contacto;
	}

	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}

	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	/**
	 * @return the clienteNuevo
	 */
	public Integer getClienteNuevo() {
		return clienteNuevo;
	}

	/**
	 * @param clienteNuevo the clienteNuevo to set
	 */
	public void setClienteNuevo(Integer clienteNuevo) {
		this.clienteNuevo = clienteNuevo;
	}

	/**
	 * @return the fechaps
	 */
	public Date getFechaps() {
		return fechaps;
	}

	/**
	 * @param fechaps the fechaps to set
	 */
	public void setFechaps(Date fechaps) {
		this.fechaps = fechaps;
	}


}
