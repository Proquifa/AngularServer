/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.FuncionDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.EmpleadoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.FuncionRowMapper;



/**
 * @author amartinez
 *
 */
@Repository
public class FuncionDAOImpl extends DataBaseDAO implements FuncionDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.FuncionDAO#actualizarFuncion(mx.com.proquifa.proquifanet.modelo.comun.Funcion)
	 */
	public Boolean actualizarFuncion(Funcion nueva) {
		try{	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("subProceso", nueva.getSubproceso());
			map.put("idFuncion", nueva.getIdFuncion());
			
			super.jdbcTemplate.update("UPDATE funcion SET fk01_subproceso = :subProceso WHERE pk_funcion = :idFuncion", map);
			return true;
		}catch(RuntimeException rte){
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.FuncionDAO#obtenerFuncionPorId(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public Funcion obtenerFuncionPorId(Long idFuncion) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFuncion", idFuncion);
			return (Funcion)super.jdbcTemplate.queryForObject("SELECT * FROM funcion WHERE pk_funcion = :idFuncion",map, new FuncionRowMapper()) ;
		}catch(RuntimeException e){
//			new com.proquifa.proquifanet.comun.util.Funcion().enviarCorreoAvisoExepcion(e,"\nidFuncion: "+idFuncion);
			return new Funcion();
		}
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.FuncionDAO#obtenerFunciones()
	 */
	@SuppressWarnings("unchecked")
	public List<Funcion> obtenerFunciones() {
		return super.jdbcTemplate.query("SELECT * FROM funcion", new FuncionRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Funcion> obtenerFuncionesSubproceso(Long idSubproceso) {
		try {
			return super.jdbcTemplate.query("SELECT * FROM funcion WHERE fk01_subproceso = " + idSubproceso, new FuncionRowMapper());
		} catch (RuntimeException e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Empleado> obtenerEmpleadosXIdFuncion(Long idFuncion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFuncion", idFuncion);
			return super.jdbcTemplate.query("SELECT E.*,NULL AS Nivel_General,NULL AS Subproceso,NULL AS idSubproceso,NULL AS NombreFuncion FROM Empleados as E WHERE fk01_funcion = :idFuncion",map, new EmpleadoRowMapper());
		} catch (RuntimeException e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Funcion> obtenerFuncionesXNivel(String nivel) {
		String query = "";
		if(nivel == null || nivel.equals("--TODOS--")){
			query = "SELECT * FROM funcion";
		}else if(nivel.equals("")){
			query = "SELECT * FROM funcion WHERE nivel = '" + nivel +"'";
		}
		return super.jdbcTemplate.query(query, new FuncionRowMapper());
	}

	public String getEmpleadoXIdFuncion(Long idFuncion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFuncion", idFuncion);
			return (String) super.jdbcTemplate.queryForObject("SELECT TOP 1 Usuario FROM Funcion AS f,Empleados AS e WHERE e.Fase>0 AND e.FK01_Funcion=f.PK_Funcion AND f.PK_Funcion= :idFuncion",map, String.class);
		} catch (RuntimeException e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}

	public String getEmpleadoXIdFuncionHabilitado(Long idFuncion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFuncion", idFuncion);
			return (String) super.jdbcTemplate.queryForObject("SELECT Usuario FROM Funcion AS f,Empleados AS e WHERE e.FK01_Funcion=f.PK_Funcion AND e.Fase>0 AND f.PK_Funcion= :idFuncion",map, String.class);
		} catch (RuntimeException e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	public Long getidFuncionXNombre(String nombreFuncion) {
		try {
			return super.queryForLong("SELECT PK_Funcion FROM Funcion WHERE Nombre='"+ nombreFuncion +"'");
		} catch (RuntimeException e) {
			//logger.info("Error: " + e.getMessage());
			return -1L;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Funcion getFuncionPorIdEmpleado(Long idEmpleado) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			String sql = "	SELECT FUN.*, EMP.Usuario FROM Empleados AS EMP " +
					"	RIGHT JOIN(SELECT * FROM Funcion) AS FUN ON FUN.PK_Funcion = EMP.FK01_Funcion " +
					"	WHERE EMP.Clave= :idEmpleado";
			
			return (Funcion) this.jdbcTemplate.queryForObject(sql,map, new FuncionRowMapper());
		}catch(Exception e){
			//logger.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public String getEsacJRXCargaClientes() {
		try {
			//consulta encargada de validar quien es el esac junior habilitado y en linea que tenga la menor cantidad de clientes habilitados con registro de inscripcion del ano en curso o pendientes de insertar clientes abiertos donde los clientes no ayan sido actualizados, en caso de no coincidir con la busqueda se asigna al esac que tenga menos clientes sin validar si esta en linea o no. 

			String query =  " \n   select top 1 Usuario  from (  " +
			" \n		select  e.clave , e.usuario , e.logueado,(  count (c.clave)+ coalesce(pend.noPendientes,0)) CAsignados, rol.Descripcion, 1 Importancia  from empleados  as e   " +
			" \n		left join (select *from clientes where year (FechaRegistro) = YEAR (GETDATE())  ) as c on c.vendedor  =  e.usuario   " +
			" \n		left join (select Descripcion , PK_Rol , FK02_Empleado from rol as r, rol_empleado as re  where re.FK01_Rol = r.PK_Rol and r.Descripcion in ('Esac_Junior','Esac_Senior','Esac_Master')) as rol on rol.FK02_Empleado = e.Clave  " +
			" \n		LEFT JOIN (	SELECT distinct Responsable , COUNT (1) noPendientes FROM Pendiente  as pend " +
			" \n			inner join (select *  from DoctosR )as dr on dr.Folio = pend.docto " +
			" \n			inner join (select * from Clientes where FUActual is null and Vendedor is null ) as cli on cli.Clave =  dr.Empresa  " +
			" \n		where pend.Tipo  = 'Agregar cliente' and FFin is null group by Responsable  ) as pend on pend.Responsable =  e.Usuario  " +
			" \n		where nivel in  (8, 42) and fase > 0 and logueado >  0 and rol.PK_Rol is not null   " +
			" \n		group by e.clave , e.usuario , e.logueado, rol.Descripcion,  pend.noPendientes " +
			" \n		union all  " +
			" \n		select  e.clave , e.usuario , e.logueado,(count (c.clave)+ coalesce(pend.noPendientes,0)) CAsignados, rol.Descripcion, 0  importancia from empleados  as e  " +
			" \n		left join (select *from clientes where year (FechaRegistro) = YEAR (GETDATE()) ) as c on c.vendedor  =  e.usuario   " +
			" \n		left join (select Descripcion , PK_Rol , FK02_Empleado from rol as r, rol_empleado as re  where re.FK01_Rol = r.PK_Rol and r.Descripcion = 'Esac_Junior') as rol on rol.FK02_Empleado = e.Clave  " +
			" \n		LEFT JOIN (SELECT distinct Responsable , COUNT (1) noPendientes FROM Pendiente  as pend " +
			" \n			inner join (select *  from DoctosR )as dr on dr.Folio = pend.docto " +
			" \n			inner join (select * from Clientes where FUActual is null and Vendedor is null ) as cli on cli.Clave =  dr.Empresa  " +
			" \n		where pend.Tipo  = 'Agregar cliente' and FFin is null group by Responsable   ) as pend on pend.Responsable =  e.Usuario   " +
			" \n		where nivel in  (8, 42) and fase > 0 AND Descripcion IS NULL  " +
			" \n		group by e.clave , e.usuario , e.logueado, rol.Descripcion , pend.noPendientes " +
			" \n	) as d order by importancia DESC , CAsignados asc  ";
			
			//logger.info(query);
			return (String) super.jdbcTemplate.queryForObject(query, new HashMap(), String.class);

		} catch (RuntimeException e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}
}