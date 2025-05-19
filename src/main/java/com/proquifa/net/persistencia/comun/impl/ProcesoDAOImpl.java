/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.Proceso;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ProcesoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ProcesoRowMapper;


/**
 * @author amartinez
 *
 */
@Repository
public class ProcesoDAOImpl extends DataBaseDAO implements ProcesoDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ProcesoDAO#obtenerProcesos()
	 */
	
	Funcion funcion;
	
	@SuppressWarnings("unchecked")
	public List<Proceso> obtenerProcesos() {
		try {
			List<Proceso> procesos = super.jdbcTemplate.query("SELECT * FROM proceso",new ProcesoRowMapper());
			return procesos;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Proceso>();
		}
	}

	public Boolean actualizarProceso(Proceso proceso) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("proceso", proceso.getIdProceso());
			map.put("proceso", proceso.getIdProceso());
			Object params [] = {proceso.getIdEmpleado(), proceso.getIdProceso()};
			super.jdbcTemplate.update("UPDATE proceso SET fk01_usuario = ? WHERE pk_proceso = ?", map);
			return true;
		}catch(RuntimeException rte){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte,proceso);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public Proceso obtenerProcesoXId(Long idProceso) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProceso", idProceso);
			return (Proceso)super.jdbcTemplate.queryForObject("SELECT * FROM proceso WHERE pk_proceso =  :idProceso", map, new ProcesoRowMapper());
		}catch(RuntimeException rte){			
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte,"idProceso: " + idProceso);
			return new Proceso();
		}
	}

	public String getResponsableDeProceso(Long idEmpleado) throws ProquifaNetException {
		String query  = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			query = "SELECT Responsable.Usuario FROM Empleados AS e "+
						   "LEFT JOIN(SELECT PK_Funcion,FK01_Subproceso FROM Funcion) AS fun ON fun.PK_Funcion=e.FK01_Funcion "+
						   "LEFT JOIN(SELECT PK_Subproceso,FK01_Proceso FROM Subproceso) AS subp ON subp.PK_Subproceso=fun.FK01_Subproceso "+
						   "LEFT JOIN(SELECT PK_Proceso,FK01_Usuario FROM Proceso) AS pro ON pro.PK_Proceso=subp.FK01_Proceso "+
						   "LEFT JOIN(SELECT Usuario,Clave FROM Empleados) AS Responsable ON Responsable.Clave=pro.FK01_Usuario "+
						   "WHERE e.Clave= :idEmpleado";
			
			return (String) super.jdbcTemplate.queryForObject(query, map, String.class);
		} catch (Exception e) {
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"query: " + query,"idEmpleado: " + idEmpleado);
			return "";
		}
	}
}
