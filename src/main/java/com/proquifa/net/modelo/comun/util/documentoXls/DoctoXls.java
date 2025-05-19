/**
 * 
 */
package com.proquifa.net.modelo.comun.util.documentoXls;

import java.util.List;

/**
 * @author bryan.magana
 *
 */
public class DoctoXls {
 private String nombre;
 private String ruta;
 private List < HojaXls> hoja;
/**
 * @return the nombre
 */
public String getNombre() {
	return nombre;
}
/**
 * @param nombre the nombre to set
 */
public void setNombre(String nombre) {
	this.nombre = nombre;
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
 * @return the hoja
 */
public List<HojaXls> getHoja() {
	return hoja;
}
/**
 * @param hoja the hoja to set
 */
public void setHoja(List<HojaXls> hoja) {
	this.hoja = hoja;
}

}
