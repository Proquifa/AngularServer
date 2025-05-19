/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.Subproceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.impl.mapper.SubprocesoRowMapper;
import com.proquifa.net.persistencia.comun.SubprocesoDAO;


import org.springframework.stereotype.Repository;

/**
 * @author amartinez
 *
 */
@Repository
public class SubprocesoDAOImpl extends DataBaseDAO implements SubprocesoDAO {
	
	Funcion funcion; 

	public Boolean actualizarSubProceso(Subproceso nuevo) {
		String query = "";
		try{
			Map<String, Object> map  = new HashMap<String, Object>();
			map.put ("nuevo", nuevo.getIdProceso());
			map.put ("nuevo",nuevo.getNombre());
			map.put ("nuevo", nuevo.getIdEmpleado());
			map.put ("nuevo", nuevo.getIdSubproceso());
			Object [] params = {nuevo.getIdProceso(), nuevo.getNombre(), nuevo.getIdEmpleado(), nuevo.getIdSubproceso()};
			query =  "UPDATE subproceso SET FK01_Proceso = ?, Nombre = ?, FK02_Usuario = ? WHERE PK_Subproceso = ?";
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(RuntimeException rte){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, nuevo,"query: " + query);
			return false;
		}
	}
	
	public Boolean borrarSubProceso(Subproceso subproceso) {
		String query = "";
		try{
			Map<String, Object> map  = new HashMap<String, Object>();
			map.put ("subproceso", subproceso.getIdSubproceso());	
			Object [] params = {subproceso.getIdSubproceso()};
			query = "DELETE subproceso WHERE PK_Subproceso =  ?";
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(RuntimeException rte){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, subproceso, "query: " + query);
			return false;
		}
	}

	public Boolean existeSubProceso(Subproceso target) {
		try {
			Boolean existe = false;
			int registros = super.queryForInt("SELECT COUNT(PK_Subproceso) as noSubProceso FROM subproceso WHERE nombre = '" + target.getNombre() + "'");
			if(registros > 0){
				existe = true;
			}
			return existe;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, target);
			return false;
		}
	}

	public Long insertarSubProceso(Subproceso subproceso) {
		String query = "";
		try{
			Map<String, Object> map =  new HashMap<String, Object>();
			map.put("subproceso", subproceso.getNombre());
			map.put("subproceso", subproceso.getIdProceso());
			map.put("subproceso", subproceso.getIdEmpleado());
			
			query = "INSERT INTO subproceso (FK01_Proceso, Nombre, FK02_Usuario) VALUES ";
			query += "(:subproceso.getIdProceso() "+"," +" :subproceso.getNombre() "+", " +" :subproceso.getIdEmpleado()) ";
			//query += "(" + subproceso.getIdProceso() + ", '" + subproceso.getNombre() + "', " + subproceso.getIdEmpleado() + ") ";
			super.jdbcTemplate.update(query, map);
			Long idSubproceso = super.queryForLong("SELECT IDENT_CURRENT ('subproceso')");
			return idSubproceso;
		}catch(RuntimeException rte){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, subproceso, "query: " + query);
			return -1L;
		}
	}

	@SuppressWarnings("unchecked")
	public Subproceso obtenerSubProcesoXId(Long idSubproceso)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idSubproceso", idSubproceso);
			Object params [] = {idSubproceso};
			return (Subproceso)super.jdbcTemplate.queryForObject("SELECT * FROM subproceso WHERE pk_subproceso = ?", map, new SubprocesoRowMapper());
		}catch(RuntimeException rte){			
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte,"idSubproceso: " + idSubproceso);
			return new Subproceso();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Subproceso> obtenerSubprocesosXIdProceso(Long idProceso)
			throws ProquifaNetException {
		try {
			List<Subproceso> subprocesos = super.jdbcTemplate.query("SELECT * FROM subproceso WHERE fk01_proceso = " + idProceso, new SubprocesoRowMapper());
			return subprocesos;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProceso: " + idProceso);
			return new ArrayList<Subproceso>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Subproceso> obtenerSubprocesos() throws ProquifaNetException {
		try {
			List<Subproceso> subprocesos = super.jdbcTemplate.query("SELECT * FROM subproceso" , new SubprocesoRowMapper());
			return subprocesos;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<Subproceso>();
		}
	}

	public Long obtenerIdSubProceso(String nombreProceso)
			throws ProquifaNetException {
		try{
			Long a= super.queryForLong("SELECT PK_Subproceso FROM Subproceso AS SUB WHERE SUB.Nombre = '" + nombreProceso + "'");
			return a;
		}catch(Exception e){
			//logger.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "nombreProceso: " + nombreProceso);
			return -1L;
		}
		
	}

	public Long getIdSubprocesoXEmpleado(String user)
			throws ProquifaNetException {
		try{
			Long idSubproceso= super.queryForLong("SELECT Funcion.FK01_Subproceso FROM Empleados,Funcion WHERE Empleados.Usuario='"+ user +"' AND Empleados.FK01_Funcion=Funcion.PK_Funcion");
			return idSubproceso;
		}catch(Exception e){
			//logger.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "user: " + user);
			return -1L;
		}
	}
}
