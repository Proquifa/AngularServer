/**
 * 
 */
package com.proquifa.net.persistencia.ventas.admoncomunicacion.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.admoncomunicacion.Actividad;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.admoncomunicacion.ActividadDAO;

import org.springframework.stereotype.Repository;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class ActividadDAOImpl extends DataBaseDAO implements  ActividadDAO{

	public void insertarActividades(Actividad actividad) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Usuario", actividad.getUsuario());
			map.put("TipoDocumento", actividad.getTipoDocumento());
			map.put("Documento", actividad.getDocumento());
			map.put("Sujeto", actividad.getSujeto());
			map.put("Observaciones",  actividad.getObservaciones());
			Object[] params =  {new Date(), actividad.getUsuario(), actividad.getTipoDocumento(), actividad.getDocumento(), actividad.getSujeto(), actividad.getObservaciones() };
			String query = "INSERT INTO actividades (fecha, usuario, tdocto, docto, sujeto, observa) VALUES " +
					"(?, ?, ?, ?, ?, ?) ";
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, actividad);
		}
	}

}
