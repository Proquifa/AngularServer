/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.ModificacionDocumentoRecibido;
import com.proquifa.net.persistencia.comun.ModificacionDocumentoDAO;

import org.springframework.stereotype.Repository;

import com.proquifa.net.persistencia.DataBaseDAO;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class ModificacionDocumentoDAOImpl extends DataBaseDAO implements ModificacionDocumentoDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ModificacionDocumentoDAO#insertarModificacionDocumento(mx.com.proquifa.proquifanet.modelo.comun.ModificacionDocumentoRecibido)
	 */
	public void insertarModificacionDocumento(
			ModificacionDocumentoRecibido modificacion) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IdDocumento", modificacion.getIdDocumento());
			map.put("Origen", modificacion.getOrigen());
			map.put("Empresa",modificacion.getEmpresa());
			map.put("Recibio",modificacion.getRecibio());
			map.put("Medio",modificacion.getMedio());
			map.put("Numero",modificacion.getNumero());
			map.put("Observacion",modificacion.getObservacion());
			map.put("FechaOrigen",modificacion.getFechaOrigen());
			map.put("Realizo", modificacion.getRealizo());
			map.put("MontoDocto",modificacion.getMontoDocto());
			map.put("Referencia",modificacion.getExisteReferencia());
			Object params [] = {modificacion.getIdDocumento(), new Date(), modificacion.getOrigen(), modificacion.getEmpresa(),
					modificacion.getRecibio(), modificacion.getMedio(), modificacion.getTipo(), modificacion.getNumero(),
					modificacion.getObservacion(), modificacion.getFechaOrigen(), modificacion.getRealizo(), modificacion.getMontoDocto(),
					modificacion.getExisteReferencia()};
			 String query = "INSERT INTO ModificacionDoctoR (idDocto,Fecha,Origen,Empresa,RPor,Medio,TDocto,Numero,Observacion,FHOrigen,Realizo,MontoDLS,SinReferencia) VALUES" +
			 				" (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			 super.jdbcTemplate.update(query,map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, modificacion);
		}
	}
}