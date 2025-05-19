/**
 * 
 */
package com.proquifa.net.modelo.comun;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;

/**
 * @author bryan.magana
 *
 */
public class DoctoCotizacion {
 private  String nombreCliente;
 private String direccionCliente;
 private String nombreContacto;
 private String FaxContacto;
 private String mailContacto;
 private String telContacto;
 private String nombreEsac;
 private String cpago;
 private Date vigencia;
 private String moneda;
 private Double subtotal;
 private Double ivaTotal;
 private Double total;
 private Date fcreacion;
 private String ccotiza; 
 private String usuarioEsac;
 private long folioDorctoR;
 private List<PartidaFactura> partidas;
/**
 * @return the nombreCliente
 */
public String getNombreCliente() {
	return nombreCliente;
}
/**
 * @param nombreCliente the nombreCliente to set
 */
public void setNombreCliente(String nombreCliente) {
	this.nombreCliente = nombreCliente;
}
/**
 * @return the direccionCliente
 */
public String getDireccionCliente() {
	return direccionCliente;
}
/**
 * @param direccionCliente the direccionCliente to set
 */
public void setDireccionCliente(String direccionCliente) {
	this.direccionCliente = direccionCliente;
}
/**
 * @return the nombreContacto
 */
public String getNombreContacto() {
	return nombreContacto;
}
/**
 * @param nombreContacto the nombreContacto to set
 */
public void setNombreContacto(String nombreContacto) {
	this.nombreContacto = nombreContacto;
}
/**
 * @return the faxContacto
 */
public String getFaxContacto() {
	return FaxContacto;
}
/**
 * @param faxContacto the faxContacto to set
 */
public void setFaxContacto(String faxContacto) {
	FaxContacto = faxContacto;
}
/**
 * @return the mailContacto
 */
public String getMailContacto() {
	return mailContacto;
}
/**
 * @param mailContacto the mailContacto to set
 */
public void setMailContacto(String mailContacto) {
	this.mailContacto = mailContacto;
}
/**
 * @return the telContacto
 */
public String getTelContacto() {
	return telContacto;
}
/**
 * @param telContacto the telContacto to set
 */
public void setTelContacto(String telContacto) {
	this.telContacto = telContacto;
}
/**
 * @return the nombreEsac
 */
public String getNombreEsac() {
	return nombreEsac;
}
/**
 * @param nombreEsac the nombreEsac to set
 */
public void setNombreEsac(String nombreEsac) {
	this.nombreEsac = nombreEsac;
}
/**
 * @return the cpago
 */
public String getCpago() {
	return cpago;
}
/**
 * @param cpago the cpago to set
 */
public void setCpago(String cpago) {
	this.cpago = cpago;
}
/**
 * @return the vigencia
 */
public Date getVigencia() {
	return vigencia;
}
/**
 * @param vigencia the vigencia to set
 */
public void setVigencia(Date vigencia) {
	this.vigencia = vigencia;
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
 * @return the subtotal
 */
public Double getSubtotal() {
	return subtotal;
}
/**
 * @param subtotal the subtotal to set
 */
public void setSubtotal(Double subtotal) {
	this.subtotal = subtotal;
}
/**
 * @return the ivaTotal
 */
public Double getIvaTotal() {
	return ivaTotal;
}
/**
 * @param ivaTotal the ivaTotal to set
 */
public void setIvaTotal(Double ivaTotal) {
	this.ivaTotal = ivaTotal;
}
/**
 * @return the total
 */
public Double getTotal() {
	return total;
}
/**
 * @param total the total to set
 */
public void setTotal(Double total) {
	this.total = total;
}
/**
 * @return the partidas
 */
public List<PartidaFactura> getPartidas() {
	return partidas;
}
/**
 * @param partidas the partidas to set
 */
public void setPartidas(List<PartidaFactura> partidas) {
	this.partidas = partidas;
}
/**
 * @return the fcreacion
 */
public Date getFcreacion() {
	return fcreacion;
}
/**
 * @param fcreacion the fcreacion to set
 */
public void setFcreacion(Date fcreacion) {
	this.fcreacion = fcreacion;
}
public String getCcotiza() {
	return ccotiza;
}
public void setCcotiza(String ccotiza) {
	this.ccotiza = ccotiza;
}
/**
* @return the usuarioEsac
*/
public String getUsuarioEsac() {
	return usuarioEsac;
}
/**
* @param usuarioEsac the usuarioEsac to set
*/
public void setUsuarioEsac(String usuarioEsac) {
	this.usuarioEsac = usuarioEsac;
}
/**
* @return the folioDorctoR
*/
public long getFolioDorctoR() {
	return folioDorctoR;
}
/**
* @param folioDorctoR the folioDorctoR to set
*/
public void setFolioDorctoR(long folioDorctoR) {
	this.folioDorctoR = folioDorctoR;
}
 
}
