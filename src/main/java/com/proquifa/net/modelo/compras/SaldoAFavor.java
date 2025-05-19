/**
 * 
 */
package com.proquifa.net.modelo.compras;

import java.io.Serializable;
import java.util.Date;

import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Proveedor;

/**
 * @author ymendez
 *
 */
public class SaldoAFavor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6299191843327906305L;
	
	private Integer idSaldo;
	private Date fecha;
	private String ffecha;
	private String folio;
	private String serie;
	private String uuid;
	private Date fechaDocto;
	private String ffechaDocto;
	private String ordenarFecha;
	private Double monto;
	private String moneda;
	private String folioDocto;
	private String comentarios;
	private String estado;
	private boolean habilitado;
	private String tipo;
	
	private String etiqueta;
	private Integer total;
	
	private Proveedor proveedor;
	private Empresa empresa;
	
	
	/**
	 * 
	 */
	public SaldoAFavor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param idSaldo
	 * @param proveedor
	 * @param empresa
	 * @param fecha
	 * @param folio
	 * @param serie
	 * @param uuid
	 * @param fechaDocto
	 * @param monto
	 * @param folioDocto
	 * @param comentarios
	 * @param estado
	 * @param habilitado
	 */
	public SaldoAFavor(SaldoAFavor saldo) {
		super();
		this.idSaldo = saldo.idSaldo;
		this.proveedor = saldo.proveedor;
		this.empresa = saldo.empresa;
		this.fecha = saldo.fecha;
		this.folio = saldo.folio;
		this.serie = saldo.serie;
		this.uuid = saldo.uuid;
		this.fechaDocto = saldo.fechaDocto;
		this.monto = saldo.monto;
		this.folioDocto = saldo.folioDocto;
		this.comentarios = saldo.comentarios;
		this.estado = saldo.estado;
		this.habilitado = saldo.habilitado;
		this.tipo = saldo.tipo;
		if (saldo.empresa != null)
			this.etiqueta = saldo.empresa.getAlias();
	}
	

	/**
	 * @return the idSaldo
	 */
	public Integer getIdSaldo() {
		return idSaldo;
	}

	/**
	 * @param idSaldo the idSaldo to set
	 */
	public void setIdSaldo(Integer idSaldo) {
		this.idSaldo = idSaldo;
	}

	/**
	 * @return the proveedor
	 */
	public Proveedor getProveedor() {
		return proveedor;
	}

	/**
	 * @param proveedor the proveedor to set
	 */
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	/**
	 * @return the empresa
	 */
	public Empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
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
	 * @return the folio
	 */
	public String getFolio() {
		return folio;
	}

	/**
	 * @param folio the folio to set
	 */
	public void setFolio(String folio) {
		this.folio = folio;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the fechaDocto
	 */
	public Date getFechaDocto() {
		return fechaDocto;
	}

	/**
	 * @param fechaDocto the fechaDocto to set
	 */
	public void setFechaDocto(Date fechaDocto) {
		this.fechaDocto = fechaDocto;
	}

	/**
	 * @return the monto
	 */
	public Double getMonto() {
		return monto;
	}

	/**
	 * @param monto the monto to set
	 */
	public void setMonto(Double monto) {
		this.monto = monto;
	}

	/**
	 * @return the folioDocto
	 */
	public String getFolioDocto() {
		return folioDocto;
	}

	/**
	 * @param folioDocto the folioDocto to set
	 */
	public void setFolioDocto(String folioDocto) {
		this.folioDocto = folioDocto;
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
	 * @return the habilitado
	 */
	public boolean isHabilitado() {
		return habilitado;
	}

	/**
	 * @param habilitado the habilitado to set
	 */
	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
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
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the etiqueta
	 */
	public String getEtiqueta() {
		return etiqueta;
	}

	/**
	 * @param etiqueta the etiqueta to set
	 */
	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}

	/**
	 * @return the moneda
	 */
	public String getMoneda() {
		return moneda;
	}

	/**
	 * @param moneda the moneda to set
	 */
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	/**
	 * @return the ffecha
	 */
	public String getFfecha() {
		return ffecha;
	}

	/**
	 * @param ffecha the ffecha to set
	 */
	public void setFfecha(String ffecha) {
		this.ffecha = ffecha;
	}

	/**
	 * @return the ffechaDocto
	 */
	public String getFfechaDocto() {
		return ffechaDocto;
	}

	/**
	 * @param ffechaDocto the ffechaDocto to set
	 */
	public void setFfechaDocto(String ffechaDocto) {
		this.ffechaDocto = ffechaDocto;
	}

	/**
	 * @return the ordenarFecha
	 */
	public String getOrdenarFecha() {
		return ordenarFecha;
	}

	/**
	 * @param ordenarFecha the ordenarFecha to set
	 */
	public void setOrdenarFecha(String ordenarFecha) {
		this.ordenarFecha = ordenarFecha;
	}


}
