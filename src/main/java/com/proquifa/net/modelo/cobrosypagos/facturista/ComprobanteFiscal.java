/**
 * 
 */
package com.proquifa.net.modelo.cobrosypagos.facturista;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.util.Funcion;


/**
 * @author fmartinez
 *
 */
public class ComprobanteFiscal {
	
	private String version = "4.0";
	private Boolean guardarArchivo;
	private Empresa emisor;
	private Cliente receptor;
	private List<ConceptoFactura> conceptosList;
	private Factura totales;	
	private String tipoDeComprobante;
	private Boolean pruebaCFDI;
	private String cadenaOriginal;
	private Folio folio;
	private String sello;
	private String certificado;
	private Date fechaFactura;
	
	private int usoCFDI;
	private int metPago;
	private int idFactura;
	
	
	/**
	 * @return the guardarArchivo
	 */
	public Boolean getGuardarArchivo() {
		return guardarArchivo;
	}
	/**
	 * @param guardarArchivo the guardarArchivo to set
	 */
	public void setGuardarArchivo(Boolean guardarArchivo) {
		if(guardarArchivo==null){
			this.guardarArchivo = false;
		}else{
			this.guardarArchivo = guardarArchivo;
		}
	}
	/**
	 * @return the emisor
	 */
	public Empresa getEmisor() {
		return emisor;
	}
	/**
	 * @param emisor the emisor to set
	 */
	public void setEmisor(Empresa emisor) {
		this.emisor = emisor;
	}
	/**
	 * @return the receptor
	 */
	public Cliente getReceptor() {
		return receptor;
	}
	/**
	 * @param receptor the receptor to set
	 */
	public void setReceptor(Cliente receptor) {
		this.receptor = receptor;
	}
	/**
	 * @return the conceptosList
	 */
	public List<ConceptoFactura> getConceptosList() {
		return conceptosList;
	}
	/**
	 * @param conceptosList the conceptosList to set
	 */
	public void setConceptosList(List<ConceptoFactura> conceptosList) {
		this.conceptosList = conceptosList;
	}
	/**
	 * @return the totales
	 */
	public Factura getTotales() {
		return totales;
	}
	/**
	 * @param totales the totales to set
	 */
	public void setTotales(Factura totales) {
		this.totales = totales;
	}
	/**
	 * @return the tipoDeComprobante
	 */
	public String getTipoDeComprobante() {
		return tipoDeComprobante;
	}
	/**
	 * @param tipoDeComprobante the tipoDeComprobante to set
	 */
	public void setTipoDeComprobante(String tipoDeComprobante) {
		this.tipoDeComprobante = tipoDeComprobante;
	}
	/**
	 * @return the pruebaCFDI
	 */
	public Boolean getPruebaCFDI() {
		return pruebaCFDI;
	}
	/**
	 * @param pruebaCFDI the pruebaCFDI to set
	 */
	public void setPruebaCFDI(Boolean pruebaCFDI) {
		this.pruebaCFDI = pruebaCFDI;
	}
	public String getCadenaOriginal() {
		return cadenaOriginal;
	}
	public void setCadenaOriginal(String cadenaOriginal) {
		this.cadenaOriginal = cadenaOriginal;
	}
	public Folio getFolio() {
		return folio;
	}
	public void setFolio(Folio folio) {
		this.folio = folio;
	}
	public String getVersion() {
		if(Funcion.FACTURACION_4){
			version = "4.0";
		}
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSello() {
		return sello;
	}
	public void setSello(String sello) {
		this.sello = sello;
	}
	public String getCertificado() {
		return certificado;
	}
	public void setCertificado(String certificado) {
		this.certificado = certificado;
	}
	public Date getFechaFactura() {
		return fechaFactura;
	}
	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
	}
	/**
	 * @return the usoCFDI
	 */
	public int getUsoCFDI() {
		return usoCFDI;
	}
	/**
	 * @param usoCFDI the usoCFDI to set
	 */
	public void setUsoCFDI(int usoCFDI) {
		this.usoCFDI = usoCFDI;
	}
	/**
	 * @return the metPago
	 */
	public int getMetPago() {
		return metPago;
	}
	/**
	 * @param metPago the metPago to set
	 */
	public void setMetPago(int metPago) {
		this.metPago = metPago;
	}
	public int getIdFactura() {
		return idFactura;
	}
	public void setIdFactura(int idFactura) {
		this.idFactura = idFactura;
	}
	
}
