/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.NominaCatalogo;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.CatalogoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoEmpleadoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoEmpleadoTipoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoEmpresaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoItemRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoPaisRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoProveedorRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoRutaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoTipoProductoMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoUnidadesRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.InspectorRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.NominaCatalogoRowMapper;


/**
 * @author ernestogonzalezlozada
 *
 */
@Repository 
public class CatalogoDAOImpl extends DataBaseDAO implements CatalogoDAO {

	final Logger log = LoggerFactory.getLogger(CatalogoDAOImpl.class);
	
	Funcion funcion;
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.CatalogoDAO#obtenerUnidades()
	 */
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerUnidades(String valorAdicional) 
			throws ProquifaNetException{
		String query = "";
		if(valorAdicional.equals("--TODOS--")){
			query = "SELECT * FROM unidades UNION SELECT '--TODOS--',0 ORDER BY unidades";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT * FROM unidades UNION SELECT '--NINGUNO--',0 ORDER BY unidades";
		}else if(valorAdicional.equals("")){
			query = "SELECT * FROM unidades ORDER BY unidades";
		}else if(valorAdicional.equals("No Disponible")){
			query = "SELECT * FROM unidades UNION SELECT 'No Disponible',0 FROM Unidades ORDER BY PK_unidad";
		}
		List<CatalogoItem> resp = super.jdbcTemplate.query(query,new CatalogoUnidadesRowMapper());
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerClientes(String valorAdicional) 
			throws ProquifaNetException {
		String query = "";
		Map<String, Object> map = new HashMap<String, Object>();
		List<CatalogoItem> resp = null;
		if(valorAdicional == null || valorAdicional.equals("")){
			query = "SELECT clave, nombre FROM clientes WHERE habilitado = 1 ORDER BY nombre";
		}else if(valorAdicional.equals("--TODOS--")){
			query = "SELECT clave, nombre FROM clientes WHERE habilitado = 1  UNION SELECT 0, '--TODOS--' ORDER BY nombre";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT clave, nombre FROM clientes WHERE habilitado = 1  UNION SELECT 0, '--NINGUNO--' ORDER BY nombre";
		}else{
			try {
				Integer.parseInt(valorAdicional);
				query = " SELECT clave, nombre FROM clientes WHERE habilitado = 1 AND FK01_EV='"+valorAdicional+"' ORDER BY nombre";
			} catch (NumberFormatException nfe){
				query = "DECLARE @usuario AS Varchar(50)='"+valorAdicional+"' " +
						"SELECT Clave,Nombre FROM Clientes WHERE Habilitado=1 AND Vendedor=@usuario " ;
				String tipoDUsuario = (String) super.jdbcTemplate.queryForObject("SELECT Funcion.Identificador FROM Empleados,Funcion WHERE Funcion.PK_Funcion=Empleados.FK01_Funcion AND Empleados.Usuario='"+valorAdicional+"'",
						map, String.class);
				if(tipoDUsuario.equals("ESAC Master")){
					query += "UNION " +
							"SELECT c.Clave,c.Nombre FROM Clientes AS c " +
							"LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS usuario ON usuario.Usuario=@usuario " +
							"LEFT JOIN(SELECT Jefe_Subordinado.FK01_Jefe,Empleados.Usuario FROM Jefe_Subordinado,Empleados WHERE Empleados.Clave=Jefe_Subordinado.FK02_Subordinado) AS sub ON sub.FK01_Jefe=usuario.Clave " +
							"WHERE c.Habilitado = 1 AND c.Vendedor=sub.Usuario ";
				}
				query += "ORDER BY Nombre ASC";
			}		
		}
		//logger.info(query);
		if(query.equals("")){
			resp = new ArrayList<CatalogoItem>();
		}else{
			resp =  super.jdbcTemplate.query(
					query, new CatalogoItemRowMapper()); 
		}
		return resp;

	}
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerFabricantes(String valorAdicional) 
			throws ProquifaNetException {
		String query = "";
		List<CatalogoItem> resp = null;
		if(valorAdicional == null || valorAdicional.equals("")){
			query = "SELECT idFabricante as clave, nombre FROM fabricantes WHERE habilitado = 1 ORDER BY nombre";
		}else if(valorAdicional.equals("--TODOS--")){
			query = "SELECT idFabricante as clave, nombre FROM fabricantes WHERE habilitado = 1 UNION SELECT 0,'--TODOS--' ORDER BY nombre";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT idFabricante as clave, nombre FROM fabricantes WHERE habilitado = 1 UNION SELECT 0,'--NINGUNO--' ORDER BY nombre";
		}else if(valorAdicional.toLowerCase().equals("reporteconfirmacion")){
			query = "	SELECT DISTINCT PCPHA.Fabrica AS nombre,'1' AS clave FROM PCotPharma AS PCPHA " +
					"	WHERE PCPHA.Estado = 'En Pharma' OR PCPHA.Estado ='En Realización Pharma' OR PCPHA.Estado ='En Pharma rechazada'" +
					"	UNION SELECT '--TODOS--' AS Fabrica,'0' AS clave ORDER BY nombre";
		}
		if(query.equals("")){
			resp = new ArrayList<CatalogoItem>();
		}else{
			resp =  super.jdbcTemplate.query(
					query, new CatalogoItemRowMapper()); 
		}
		return resp;
	}

	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerTipoProductos(String valorAdicional) 
			throws ProquifaNetException {
		try{
			String query = "";
			if(valorAdicional.equals("--TODOS--")){
				query = " SELECT 0 PK_Folio,'--TODOS--' Valor, 0 posicion UNION SELECT  PK_Folio, Valor, 1 posicion  FROM ValorCombo WHERE Concepto = 'TipoProducto'  ORDER BY posicion,Valor";
			}else if(valorAdicional.equals("--NINGUNO--")){
				query = " SELECT 0 PK_Folio,'--NINGUNO--' Valor, 0 posicion UNION SELECT  PK_Folio, Valor, 1 posicion  FROM ValorCombo WHERE Concepto = 'TipoProducto' ORDER BY posicion,Valor";
			}else if(valorAdicional.equals("")){
				query = "SELECT  PK_Folio, Valor  FROM ValorCombo WHERE Concepto = 'TipoProducto' ORDER BY Valor";
			}
			List<CatalogoItem> resp = super.jdbcTemplate.query(query, new CatalogoTipoProductoMapper());
			return resp;
		}catch (Exception e) {
			return null;// TODO: handle exception
		}
	}
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerProveedores(String valorAdicional) 
			throws ProquifaNetException{
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "";
			if(valorAdicional.equals("--TODOS--")){
				query = "SELECT clave, nombre FROM Proveedores WHERE habilitado = 1 UNION SELECT 0, '--TODOS--' ORDER BY nombre";
			}else if(valorAdicional.equals("--NINGUNO--")){
				query = "SELECT clave, nombre FROM Proveedores WHERE habilitado = 1 UNION SELECT 0, '--NINGUNO--' ORDER BY nombre";
			}else if(!valorAdicional.equals("")) {
				try {
					Integer.parseInt(valorAdicional);
					//logger.info("-----------");
					String funcion = (String) super.jdbcTemplate.queryForObject("SELECT Funcion.Nombre FROM Empleados,Funcion WHERE Empleados.FK01_Funcion=Funcion.PK_Funcion AND Empleados.Clave="+ valorAdicional, map, String.class);
					if(funcion.equals("Comprador")){
						query = "SELECT Clave,Nombre FROM Proveedores WHERE Habilitado=1 AND FK01_Empleado= "+valorAdicional+" ORDER BY Nombre";
					}else{
						query = "SELECT Clave,Nombre FROM Proveedores WHERE Habilitado=1 ORDER BY Nombre";
					}
				} catch (NumberFormatException nfe){
					log.info("Error: " + nfe.getMessage());
				}
			}
			if(valorAdicional.equals("")){
				query = "SELECT clave, nombre FROM Proveedores WHERE habilitado = 1 ORDER BY nombre";
			}
			//logger.info(query);
			List<CatalogoItem> resp = super.jdbcTemplate.query(query, new CatalogoProveedorRowMapper());
		return resp;
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerProveedoresPorTipo(String valorAdicional, String tipo) 
		throws ProquifaNetException {
		String query = "";
		String tabla = "";
		
		if(tipo.equals("Temporal")){
			tabla = "ProveedorTemp";
		}else if(tipo.equals("Regular") || tipo.equals("")){
			tabla = "Proveedores";
		}

		if(valorAdicional.equals("--TODOS--")){
			query = "SELECT clave, nombre FROM " + tabla + " UNION SELECT 0, '--TODOS--' ORDER BY nombre";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT clave, nombre FROM " + tabla + " UNION SELECT 0, '--NINGUNO--' ORDER BY nombre";
		}else if(valorAdicional.equals("")){
			query = "SELECT clave, nombre FROM " + tabla + " ORDER BY nombre";
		}else if(valorAdicional.toLowerCase().equals("reporteconfirmacion")){
			String condicion;
			
			if(tipo.equals("Temporal"))
				condicion = "T";
			else
				condicion = "R";
			
			query = 
				"	SELECT PRO.Clave,CASE WHEN PRO.Nombre IS NOT NULL THEN PRO.Nombre END AS 'Nombre' FROM PCotPharma AS PCPHA" +
				"	RIGHT JOIN(SELECT Clave,Nombre FROM "+tabla+") AS PRO ON PRO.Clave = PCPHA.Proveedor AND PCPHA.TProvee = '"+condicion+"'" +
				"	WHERE PCPHA.Estado = 'En Pharma' OR PCPHA.Estado ='En Realización Pharma' OR PCPHA.Estado ='En Pharma rechazada'" +
				"	UNION SELECT 0,'--TODOS--' AS 'nombre' ORDER BY nombre";
		}else if(valorAdicional.toLowerCase().equals("estados")){
			
			String condicion="";
			if (tipo.equalsIgnoreCase("todo")){
				condicion="";
			}
			if (tipo.equalsIgnoreCase("hab")){
				condicion=" WHERE habilitado = 1 ";
			}
			if (tipo.equalsIgnoreCase("des")){
				condicion=" WHERE habilitado <> 1 ";
			}
			
			query = " SELECT Clave,Nombre FROM Proveedores " + condicion +
					" ORDER BY Nombre ";
		}
		//log.info(query);
		List<CatalogoItem> resp = super.jdbcTemplate.query(
				query, new CatalogoProveedorRowMapper());
		
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerEmpleados(String valorAdicional) 
			throws ProquifaNetException {
		String query = "";
		if(valorAdicional.equals("--TODOS--")){
			query = "SELECT clave, usuario FROM Empleados WHERE Fase<>'0' UNION SELECT 0,'--TODOS--' ORDER BY Usuario";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT clave, usuario FROM Empleados WHERE Fase<>'0' UNION SELECT 0,'--NINGUNO--' ORDER BY Usuario";
		}else if(valorAdicional.equals("")){
			query = "SELECT clave, usuario FROM Empleados WHERE Fase<>'0' ORDER BY Usuario";
		}
		List<CatalogoItem> resp = super.jdbcTemplate
		.query(
				query,
				new CatalogoEmpleadoRowMapper());
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerEmpleadosPorTipo(String tipo, String valorAdicional) 
			throws ProquifaNetException {
		String complemento  = "";
		String query = "";
		if(valorAdicional.equals("--TODOS--") || valorAdicional.equals("--NINGUNO--")){
			complemento = "UNION SELECT 0,'" + valorAdicional + "'";
		}
		if(tipo.equals("esac")){
			query = "SELECT clave, usuario FROM Empleados WHERE (Nivel = 8 or nivel = 26 or nivel = 32 or nivel = 27 or nivel = 41) and fase > 0 " + complemento + " ORDER BY Usuario";
		}else if(tipo.equals("ev")){
			query = "SELECT e.clave, e.usuario  FROM Empleados e INNER JOIN Funcion F ON F.PK_Funcion = e.FK01_Funcion WHERE e.Fase > 0 AND (F.Identificador = 'EV'  ) ORDER BY e.Usuario";
		}else{
			query = "SELECT clave, usuario FROM Empleados WHERE Fase<>'0' "+ complemento + " ORDER BY Usuario";
		}
		//log.info(query);
		List<CatalogoItem> resp = super.jdbcTemplate.query(	query,new CatalogoEmpleadoRowMapper());
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public CatalogoItem obtenerVendedorPorEmpresa(String idCliente) 
			throws ProquifaNetException {
		int habilitado= 0;
		String query ="";
		Map<String, Object> map = new HashMap<String, Object>();
		if (idCliente != null){
			query = "SELECT Habilitado FROM Clientes WHERE Clave = '" + idCliente  + "'";
			habilitado = super.queryForInt(query);
			}
		
		if (habilitado == 1){
			query = "SELECT clave, usuario FROM empleados WHERE usuario IN (SELECT vendedor FROM clientes WHERE Clave = '" + idCliente  + "')";
			//log.info(query);
			return (CatalogoItem) super.jdbcTemplate.queryForObject(query, map, new CatalogoEmpleadoRowMapper());
		}else {
			CatalogoItem dato = new CatalogoItem();
			return dato;
		}
		 
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerProductosXIdProveedor(Long idProveedor, String valorAdicional)
			throws ProquifaNetException {
		String query = "";
		List<CatalogoItem> resp = null;
		if( valorAdicional == null || valorAdicional.equals("")){
			query = "SELECT idproducto AS clave, codigo AS nombre FROM productos WHERE proveedor = " + idProveedor  +  " ORDER BY clave";
		}else if(valorAdicional.equals("--TODOS--")){
			query = "SELECT idproducto AS clave, codigo AS nombre FROM productos WHERE proveedor = " + idProveedor  + " UNION SELECT 0, '--TODOS--' ORDER BY clave";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT idproducto AS clave, codigo AS nombre FROM productos WHERE proveedor = " + idProveedor  + " UNION SELECT 0, '--NINGUNO--' ORDER BY clave";
		
		}
		if(query.equals("")){
			resp = new ArrayList<CatalogoItem>();
		}else{
			resp =  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 
		}
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerProcedimientos(String valorAdicional)
			throws ProquifaNetException {
		String query = "";
		List<CatalogoItem> resp = null;
		if( valorAdicional == null || valorAdicional.equals("")){
			query = "SELECT pk_procedimiento AS clave, nombre FROM procedimiento ORDER BY nombre";
		}else{
			query = "SELECT pk_procedimiento AS clave, nombre FROM procedimiento UNION SELECT 0, '" + valorAdicional + "' ORDER BY clave";
		}
		if(query.equals("")){
			resp = new ArrayList<CatalogoItem>();
		}else{
			resp =  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 
		}
		return resp;
	}
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerSubProcesos(String valorAdicional)
			throws ProquifaNetException {
		String query = "";
		List<CatalogoItem> resp = null;
		if( valorAdicional == null || valorAdicional.equals("")){
			query = "SELECT pk_subproceso AS clave, nombre FROM subproceso ORDER BY nombre";
		}else{
			query = "SELECT pk_subproceso AS clave, nombre FROM subproceso UNION SELECT 0, '" + valorAdicional + "' ORDER BY clave";
		}
		if(query.equals("")){
			resp = new ArrayList<CatalogoItem>();
		}else{
			resp =  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 
		}
		return resp;
	}
	public List<CatalogoItem> obtenerEmpleadosXNivel(String nivel,
			String valorAdicional) throws ProquifaNetException {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerEmpleadosPorTipoTablero(String tipo,
			String valorAdicional) throws ProquifaNetException {
		String complemento  = "";
		String query = "";
		if(valorAdicional.equals("--TODOS--") || valorAdicional.equals("--NINGUNO--")){
			complemento = "UNION SELECT 0,'" + valorAdicional + "', '','--NINGUNO--'";
		}
		if(tipo.equals("esac")){
			query = "SELECT clave, usuario, 'esac' AS Tipo, Nombre FROM Empleados WHERE (Nivel = 8 or nivel = 26 or nivel = 32 or nivel = 27) and fase > 0 " + complemento + " ORDER BY Usuario";
		}else if(tipo.equals("ev")){
			query = "SELECT clave, usuario, 'ev' AS Tipo, Nombre FROM Empleados WHERE (Nivel = 3 ) and fase > 0 " + complemento + " UNION SELECT clave, usuario , 'ev' as tipo, Nombre FROM Empleados where Fk01_funcion in (3, 35) and fase > 0  ORDER BY Usuario";
		}else if(tipo.equals("evt")){
			query = "SELECT clave, usuario, 'evt' AS Tipo, Nombre FROM Empleados WHERE (Nivel = 47 ) and fase > 0 " + complemento + " ORDER BY Usuario"; 	
		}else if(tipo.equals("esac-eve")){
			query = "SELECT e.Clave,e.Usuario, " +
					"(CASE WHEN e.FK01_Funcion=es.PK_Funcion THEN 'esac' " +
					"	  WHEN e.FK01_Funcion=ev.PK_Funcion THEN 'ev' " +
					"	  ELSE 'esac_master' END) AS Tipo, Nombre " +
					"FROM Empleados AS e " +
					"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS es ON es.Identificador='ESAC' " +
					"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS ev ON ev.Identificador='EV' " +
					"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS em ON em.Identificador='ESAC Master' " +
					"WHERE Fase > 0 AND (e.FK01_Funcion=es.PK_Funcion OR e.FK01_Funcion=ev.PK_Funcion OR e.FK01_Funcion=em.PK_Funcion) " + complemento + " ORDER BY Usuario";
		}else if(tipo.equals("esac-esac_master")){
			query = "SELECT e.Clave,e.Usuario, " +
			"(CASE WHEN e.FK01_Funcion=es.PK_Funcion THEN 'esac' " +
			"	  ELSE 'esac_master' END) AS Tipo, Nombre  " +
			"FROM Empleados AS e " +
			"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS es ON es.Identificador='ESAC' " +
			"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS em ON em.Identificador='ESAC Master' " +
			"WHERE Fase > 0 AND (e.FK01_Funcion=es.PK_Funcion OR e.FK01_Funcion=em.PK_Funcion) " + complemento + " ORDER BY Usuario";
		}
		else if(tipo.equals("comprador")){
			query = "SELECT clave, usuario, 'comprador' AS Tipo, Nombre FROM Empleados WHERE Fase > 0 AND Nivel = 12 "+ complemento + " ORDER BY Usuario";
		}else if(tipo.equals("esac_master")){
			query = "DECLARE @emp AS Varchar(70)='"+ valorAdicional +"' " +
					"SELECT  e.Clave,e.Usuario,(" +
					"CASE WHEN f.Identificador='ESAC Master' THEN 'esac_master' " +
					"     WHEN f.Identificador='ESAC' THEN 'esac'" +
					"     ELSE 'ev' END) AS Tipo, e.Nombre " +
					"FROM Funcion AS f " +
					"LEFT JOIN(SELECT * FROM Empleados) AS e ON e.Usuario=@emp " +
					"WHERE f.PK_Funcion=e.FK01_Funcion " +
					"UNION " +
					"SELECT subordinado.Clave,subordinado.Usuario,'esac' AS Tipo, e.Nombre " +
					"FROM Jefe_Subordinado AS js " +
					"LEFT JOIN(SELECT Clave,Usuario,Nombre FROM Empleados) AS e ON e.Usuario=@emp " +
					"LEFT JOIN(SELECT Clave,Usuario,Nombre FROM Empleados) AS subordinado ON subordinado.Clave=js.FK02_Subordinado " +
					"WHERE js.FK01_Jefe=e.Clave ORDER BY Usuario ASC";
		}else if(tipo.equals("esac_master-esac-eve")){
			query = "SELECT e.Clave,e.Usuario, " +
					"(CASE WHEN e.FK01_Funcion=es.PK_Funcion THEN 'esac' " +
					"	  WHEN e.FK01_Funcion=ev.PK_Funcion THEN 'ev' " +
					"	  ELSE 'esac_master' END) AS Tipo, Nombre " +
					"FROM Empleados AS e " +
					"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS es ON es.Identificador='ESAC' " +
					"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS em ON em.Identificador='ESAC Master' " +
					"LEFT JOIN(SELECT PK_Funcion,Identificador FROM Funcion) AS ev ON ev.Identificador='EV' " +
					"WHERE Fase > 0 AND (e.FK01_Funcion=es.PK_Funcion OR e.FK01_Funcion=em.PK_Funcion OR e.FK01_Funcion=ev.PK_Funcion) ORDER BY Usuario";
		}else if(tipo.equals("RT") || tipo.equals("rt")){
			query = "SELECT Clave,Usuario,Funcion.Nombre AS Tipo, Empleados.Nombre FROM Funcion,Empleados WHERE Empleados.FK01_Funcion=Funcion.PK_Funcion " +
					"AND Empleados.Fase>0 AND Funcion.Identificador='"+ tipo + "' " +
					"UNION " +
					"SELECT e.Clave,e.Usuario,f.Nombre AS Tipo, e.Nombre FROM Funcion AS f,Empleados AS e WHERE " +
					"f.PK_Funcion=e.FK01_Funcion AND f.Nombre='Almacenista de Salidas' " +
					complemento +
					" ORDER BY Usuario";
		}else{
			query = "SELECT clave, usuario, '' AS Tipo, Nombre FROM Empleados WHERE Fase<>'0' "+ complemento + " ORDER BY Usuario";
		}
		List<CatalogoItem> resp = super.jdbcTemplate.query(query,new CatalogoEmpleadoTipoRowMapper());
		return resp;
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> findPais(String valorAdicional) {
		try{
			String query = "";
			if(valorAdicional == null || valorAdicional.equals("")){
				query = "SELECT PK_Folio, Valor,Tipo FROM valorcombo WHERE concepto = 'Pais' ORDER BY Valor ASC ";
			}else if(valorAdicional.equals("--TODOS--")){
				query = "SELECT 0 PK_Folio, '--TODOS--' Valor, '1' Tipo, '1' orden UNION SELECT PK_Folio, Valor,Tipo, '2' orden FROM valorcombo WHERE concepto = 'Pais'  ORDER BY orden, Valor";
			}else if(valorAdicional.equals("--NINGUNO--")){
				query = "SELECT 0 PK_Folio, '--NINGUNO--' Valor,'1' Tipo, '1' orden  UNION SELECT PK_Folio, Valor,Tipo, '2' orden FROM valorcombo WHERE concepto = 'Pais' ORDER BY orden , Valor";
			}
			
			return super.jdbcTemplate.query(query,new CatalogoPaisRowMapper());
		}catch(RuntimeException e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<CatalogoItem> findRutas(String valorAdicional) {
		try {
			return super.jdbcTemplate.query(valorAdicional +
					"SELECT PK_Folio,Valor,'Ruta' AS Tipo , '1' AS Cont FROM ValorCombo WHERE Concepto = 'Ruta' " +
					"ORDER BY cont,Valor ASC", new CatalogoRutaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerClientesEstado(String valorAdicional, String tipo) 
			throws ProquifaNetException {
		String query = "";
		String condicion="";
		String condicion2="";
		String condicion3="";
		Map<String, Object> map = new HashMap<String, Object>();
		if (tipo.equalsIgnoreCase("todo")){
			condicion="";
			condicion2="";
			condicion3=" ";
		}
		if (tipo.equalsIgnoreCase("hab")){
			condicion=" WHERE habilitado = 1 ";
			condicion2=" AND habilitado = 1 ";
			condicion3=" AND c.Habilitado = 1";
		}
		if (tipo.equalsIgnoreCase("des")){
			condicion=" WHERE habilitado <> 1 ";
			condicion2=" AND habilitado <> 1 ";
			condicion3=" AND c.Habilitado <> 1";
		}
		List<CatalogoItem> resp = null;
		
//		query = "SELECT clave, nombre FROM clientes WHERE habilitado <> 1 ORDER BY nombre";
		if(valorAdicional == null || valorAdicional.equals("")){
			query = "SELECT clave, nombre FROM clientes "+condicion+" ORDER BY nombre";
		}else if(valorAdicional.equals("--TODOS--")){
			query = "SELECT clave, nombre FROM clientes "+condicion+"  UNION SELECT 0, '--TODOS--' ORDER BY nombre";
		}else if(valorAdicional.equals("--NINGUNO--")){
			query = "SELECT clave, nombre FROM clientes "+condicion+"  UNION SELECT 0, '--NINGUNO--' ORDER BY nombre";
		}else{
			try {
				Integer.parseInt(valorAdicional);
				query = " SELECT clave, nombre FROM clientes WHERE FK01_EV='"+valorAdicional+"'  "+condicion2+" ORDER BY nombre";
			} catch (NumberFormatException nfe){
				query = "DECLARE @usuario AS Varchar(50)='"+valorAdicional+"' " +
						"SELECT Clave,Nombre FROM Clientes WHERE Vendedor=@usuario "+condicion2+" " ;
				String tipoDUsuario = (String) super.jdbcTemplate.queryForObject("SELECT Funcion.Identificador FROM Empleados,Funcion WHERE Funcion.PK_Funcion=Empleados.FK01_Funcion AND Empleados.Usuario='"+valorAdicional+"'",
						map, String.class);
				if(tipoDUsuario.equals("ESAC Master")){
					query += "UNION " +
							"SELECT c.Clave,c.Nombre FROM Clientes AS c " +
							"LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS usuario ON usuario.Usuario=@usuario " +
							"LEFT JOIN(SELECT Jefe_Subordinado.FK01_Jefe,Empleados.Usuario FROM Jefe_Subordinado,Empleados WHERE Empleados.Clave=Jefe_Subordinado.FK02_Subordinado) AS sub ON sub.FK01_Jefe=usuario.Clave " +
							"WHERE c.Vendedor=sub.Usuario "+condicion3;
				}
				query += "ORDER BY Nombre ASC";
			}		
		}
		
//		logger.info(query);
		if(query.equals("")){
			resp = new ArrayList<CatalogoItem>();
		}else{
			resp =  super.jdbcTemplate.query(
					query, new CatalogoItemRowMapper()); 
		}
		return resp;

	}
	
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerCatalogoPorProveedor(String condiciones) throws ProquifaNetException {
		String query="";
		List<CatalogoItem> resp = null;
		try {
			query=  " SELECT PRO.idProducto AS CLAVE, PRO.Codigo AS NOMBRE " +
					" FROM Productos AS PRO " +
					" WHERE  Vigente = 1 " +condiciones +
					" ORDER BY PRO.Codigo ";
			
		//log.info(query);
		resp =  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 
		
		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}
		
		return resp;

	}

	@SuppressWarnings("unchecked")
	public List<CatalogoItem> obtenerCatalogoTiempoEntrega(String valorAdicional) throws ProquifaNetException {
		String query="";
		List<CatalogoItem> resp = null;
		try {
			if(valorAdicional.equals("--NINGUNO--")){
				query = " SELECT VC.PK_FOLIO AS clave, VC.Valor AS nombre,  " +
						" CASE WHEN VC.Valor LIKE '%días%'  THEN 2 ELSE CASE WHEN VC.Valor LIKE '%SEMANA%' THEN 3 ELSE " +
						" CASE WHEN VC.Valor LIKE '%MES%' THEN 4 ELSE 1 END END END AS t, Tipo" +
						" FROM ValorCombo AS VC WHERE concepto='TiempoEntrega' " + 
						" UNION SELECT 0, '--NINGUNO--', 0,'00' ORDER BY t, Tipo ";
			}else{
				query = " SELECT VC.PK_FOLIO AS clave, VC.Valor AS nombre,  " +
						" CASE WHEN VC.Valor LIKE '%días%'  THEN 2 ELSE CASE WHEN VC.Valor LIKE '%SEMANA%' THEN 3 ELSE " +
						" CASE WHEN VC.Valor LIKE '%MES%' THEN 4 ELSE 1 END END END AS t, Tipo" +
						" FROM ValorCombo AS VC WHERE concepto='TiempoEntrega'  ORDER BY t, Tipo ";
			}
			
			//log.info(query);
			resp =  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 

		} catch (Exception e) {
			log.info("Error: "+ e.getMessage());
			return null;
		}

		return resp;

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getClientesPorIdUsuarioRol(String condiciones)
			throws ProquifaNetException {
		try{
			String query = "SELECT clave, nombre FROM clientes WHERE Habilitado = 1 " + condiciones + " ORDER BY nombre";

			return  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 
		}catch (Exception e) {
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getProveedoresPorIdUsuarioRol(String condiciones)
			throws ProquifaNetException {
		try{
			String query = "SELECT clave, nombre FROM Proveedores WHERE Habilitado = 1 " + condiciones + " ORDER BY nombre";

			return  super.jdbcTemplate.query(query, new CatalogoItemRowMapper()); 
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getCondicionesDePagoProveedor()
			throws ProquifaNetException {
		try{
			String sql = "SELECT PK_Folio clave, Valor nombre,Tipo FROM valorcombo WHERE concepto = 'CPago' and Tipo = 'Proveedor' ORDER BY Valor ASC ";
			return super.jdbcTemplate.query(sql, new CatalogoItemRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getBancosClientes(String condiciones) throws ProquifaNetException {
		try{
			String sql = "SELECT DISTINCT Banco Nombre, 0 clave FROM CuentaBanco "+ condiciones +" ORDER BY Banco ";
			
			
			return super.jdbcTemplate.query(sql, new CatalogoItemRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getCuentasBancoClientes(String banco, String fpor) throws ProquifaNetException {
		try{
			String sql = "";
			if(fpor.equals("")){
			sql = "SELECT idCuenta clave, NoCuenta +  ' '+Moneda nombre FROM CuentaBanco WHERE Banco like '%" + banco + "%' ORDER BY NoCuenta ";
			}else{
			sql = "SELECT idCuenta clave, NoCuenta +  ' '+Moneda nombre FROM CuentaBanco WHERE Beneficiario COLLATE Modern_Spanish_CI_AS = (SELECT Prefijo FROM Empresa WHERE Alias = '"+ fpor +"') AND Banco like '%" + banco + "%' ORDER BY Banco, NoCuenta ";
			}
			//logger.info(sql);
			return super.jdbcTemplate.query(sql, new CatalogoItemRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getBancos() throws ProquifaNetException {
		try{
			String sql = "SELECT Banco nombre, PK_Banco clave FROM Bancos ORDER BY Banco ASC ";
			
			return super.jdbcTemplate.query(sql, new CatalogoItemRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getCuentas(String banco) throws ProquifaNetException {
		try{
			String sql = "select NoCuenta as nombre, idCuenta as clave From CuentaBanco where Banco like '%" + banco + "%' ORDER BY Banco ASC ";
			
			return super.jdbcTemplate.query(sql, new CatalogoItemRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getCorporativos(String usuario) throws ProquifaNetException {
		try {
			String aux;
			if(usuario.isEmpty()){
				aux="";
			}else{
				aux="AND Vendedor='"+usuario+"'";
			}
			//StringBuilder sbQuery = new StringBuilder("SELECT Nombre, PK_Corporativo clave FROM Corporativo");
			StringBuilder sbQuery = new StringBuilder(" \n SELECT DISTINCT(PK_Corporativo) AS clave,CO.Nombre ");
			sbQuery.append(" \n FROM Clientes C ");
			sbQuery.append(" \n LEFT JOIN Corporativo CO ON CO.PK_Corporativo=C.FK02_Corporativo ");
			sbQuery.append(" \n WHERE PK_Corporativo IS NOT NULL ").append(aux).append(" order by co.Nombre");
			//log.info(sbQuery);
			return super.jdbcTemplate.query(sbQuery.toString(), new CatalogoItemRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerEmpresas()
			throws ProquifaNetException {
		String query = "";
		query = "SELECT PK_Empresa AS clave, Alias AS empresa FROM Empresa  ORDER BY empresa";
		List<CatalogoItem> empr = super.jdbcTemplate.query(query, new CatalogoEmpresaRowMapper());
		return empr;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerIndustrias() throws ProquifaNetException {
		try {			
			StringBuilder sbQuery = new StringBuilder("SELECT PK_Folio as clave,Valor as nombre ");
			sbQuery.append("\n FROM ValorCombo ");
			sbQuery.append("\n WHERE Concepto='Industrial' ");
			//log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new CatalogoItemRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CatalogoItem>obtenerEmpresaCompra(Boolean bCompra, String valorAdicional) throws ProquifaNetException {
		try {
			List<CatalogoItem> lstCat =  new ArrayList<CatalogoItem>();
			List<CatalogoItem> lstAux =  new ArrayList<CatalogoItem>();
			
			if (valorAdicional != null ){
				if (!valorAdicional.equals("")){
					CatalogoItem catalogoItm =  new CatalogoItem();
					catalogoItm.setLlave(0L);
					catalogoItm.setValor(valorAdicional);
					lstCat.add(catalogoItm);
				}
			}
			
			String sQuery = "";
			int iCompra=0;
			
			if (bCompra) {
				iCompra = 1;
			}
			else if (!bCompra){
				iCompra=0;
			}
			
			sQuery = "SELECT PK_Empresa AS clave, Alias AS Empresa FROM Empresa WHERE Compra = " + iCompra + " ORDER BY Alias";
			//logger.info(sQuery);
			
			lstAux = super.jdbcTemplate.query(sQuery, new CatalogoEmpresaRowMapper());
			lstCat.addAll(lstAux);
			return lstCat;
			
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> listClientesDistribuidores(String condiciones)
			throws ProquifaNetException {
		try{
			String query = 
							" \n SELECT C.Clave, C.Nombre empresa " +
							" \n FROM Clientes C " +
							" \n LEFT JOIN (SELECT * FROM Empleados) AS E ON E.Usuario = C.Vendedor " +
							" \n LEFT JOIN (SELECT * FROM Jefe_Subordinado JS " +
							" \n	LEFT JOIN (SELECT Clave, FK01_Funcion FROM Empleados WHERE FK01_Funcion <> 37) E2 ON E2.Clave = JS.FK02_Subordinado WHERE E2.Clave IS NOT NULL ) AS JS ON JS.FK02_Subordinado = E.Clave " +
							" \n WHERE " + condiciones + " ORDER BY C.Nombre";
			//logger.info(query);
			return  super.jdbcTemplate.query(query, new CatalogoEmpresaRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerNivelIngreso() throws ProquifaNetException {
		try{
			String query = 
							" \n SELECT PK_NivelIngreso clave,Nivel nombre, case when nivel  = 'aaplus' then 0 else PK_NivelIngreso end orden  FROM nivelingreso ORDER BY orden ";
			//logger.info(query);
			return  super.jdbcTemplate.query(query, new CatalogoItemRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getAgentesAduanales(String condiciones, String condicionesDos)
			throws ProquifaNetException {
		try{
			String query = condiciones +
							" \n SELECT PK_AgenteAduanal clave, NombreComercial nombre, 1 posicion FROM AgenteAduanal " + condicionesDos;
			//logger.info(query);
			
			return  super.jdbcTemplate.query(query, new CatalogoItemRowMapper());
			
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;// TODO: handle exception
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> getInspectores(String condiciones) throws ProquifaNetException {
		try{
			String query = condiciones +
							" \n SELECT Clave idUsuario,Usuario, 1 posicion FROM Empleados WHERE nivel=31 AND Fase>0 AND FK01_Funcion=11 ORDER BY posicion,Usuario ";
			////logger.info(query);
			
			return  super.jdbcTemplate.query(query, new InspectorRowMapper());
			
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerFamiliasPorProveedor(int idProveedor) {
		try {
			String query = "SELECT RANK() OVER (ORDER BY (MAX(vct.Valor) + ' · ' + coalesce(MAX(vcst.Valor),'') + ' · ' + coalesce(MAX(vcc.Valor),''))) AS clave" +
					"  ,(MAX(vct.Valor) + ' · ' + coalesce(MAX(vcst.Valor),'') + ' · ' + coalesce(MAX(vcc.Valor),'')) AS nombre" +
					"  FROM Configuracion_Precio cp " +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'TipoProducto') vct ON vct.PK_Folio = cp.Tipo" +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'SubTipoProducto') vcst ON vcst.PK_Folio = cp.Subtipo" +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'Control') vcc ON vcc.PK_Folio = cp.Control";
			if(idProveedor > 0){
				query += " WHERE cp.FK01_Proveedor = " + idProveedor;
			}
			query += " GROUP BY cp.Tipo,Subtipo, Control ORDER BY clave";
			
			return super.jdbcTemplate.query(query, new CatalogoItemRowMapper());
			
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CatalogoItem>();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerFamiliasPorTipo(int tipo) {
		try {
			String query = "SELECT RANK() OVER (ORDER BY (MAX(vct.Valor) + ' · ' + coalesce(MAX(vcst.Valor),'') + ' · ' + coalesce(MAX(vcc.Valor),''))) AS clave" +
					"  ,(MAX(vct.Valor) + ' · ' + coalesce(MAX(vcst.Valor),'') + ' · ' + coalesce(MAX(vcc.Valor),'')) AS nombre" +
					"  FROM Configuracion_Precio cp " +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'TipoProducto') vct ON vct.PK_Folio = cp.Tipo" +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'SubTipoProducto') vcst ON vcst.PK_Folio = cp.Subtipo" +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'Control') vcc ON vcc.PK_Folio = cp.Control";
			if(tipo > 0){
				query += " WHERE cp.Tipo = " + tipo ;
			}
			query += " GROUP BY cp.Tipo,Subtipo, Control ORDER BY clave";
			
			return super.jdbcTemplate.query(query, new CatalogoItemRowMapper());
			
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CatalogoItem>();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogoItem> obtenerFamiliasPorAgenteAduanal(int idAgenteAduanal) {
		try {
			String query = "SELECT RANK() OVER (ORDER BY (MAX(vct.Valor) + ' · ' + coalesce(MAX(vcst.Valor),'') + ' · ' + coalesce(MAX(vcc.Valor),''))) AS clave" +
					"  ,(MAX(vct.Valor) + ' · ' + coalesce(MAX(vcst.Valor),'') + ' · ' + coalesce(MAX(vcc.Valor),'')) AS nombre" +
					"  FROM Configuracion_Precio cp" +
					"  LEFT JOIN  AA_ConfiguracionPrecio aa ON aa.FK02_ConfiguracionPrecio = cp.PK_Configuracion_Precio " +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'TipoProducto') vct ON vct.PK_Folio = cp.Tipo" +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'SubTipoProducto') vcst ON vcst.PK_Folio = cp.Subtipo" +
					"  LEFT JOIN (SELECT PK_Folio,Valor FROM ValorCombo WHERE Concepto = 'Control') vcc ON vcc.PK_Folio = cp.Control";
			if(idAgenteAduanal > 0){
				query += " WHERE aa.FK01_AgenteAduanal = " + idAgenteAduanal ;
			}
			query += " GROUP BY cp.Tipo,Subtipo, Control ORDER BY clave";
			
			return super.jdbcTemplate.query(query, new CatalogoItemRowMapper());
			
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CatalogoItem>();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<CatalogoItem> getOrigenProductos() {
		try {
			return super.jdbcTemplate.query("SELECT PK_Pais AS clave,Espanol AS Nombre FROM Pais ORDER BY Espanol", new CatalogoItemRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new ArrayList<CatalogoItem>();
		}
	}
	
	@Override
	public NominaCatalogo getNominaCatalogoID(Integer idNominaCatalogo) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idNominaCatalogo", idNominaCatalogo);
			List<NominaCatalogo> lst = new ArrayList<NominaCatalogo>();
			String query = "SELECT * FROM NominaCatalogo WHERE PK_NominaCatalogo = :idNominaCatalogo";
			lst = super.jdbcTemplate.query(query, map, new NominaCatalogoRowMapper());
			if (lst.size() > 0) {
				return lst.get(0);
			} else {
				return new NominaCatalogo();
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new NominaCatalogo();
		}
	}
	
	@Override
	public List<CatalogoItem> getEmpresasContabilidad() throws ProquifaNetException {
		try {
			LocalDate fecha = LocalDate.now();
			String query =
					"SELECT " + 
					"	PK_Empresa AS clave, " + 
					"	Alias AS empresa, " + 
					"	RFC AS rfc, " + 
					"	RazonSocial AS razon, " + 
					"	Activo AS activo, " + 
					"	Prefijo AS prefijo, " + 
					"	(SELECT COUNT(*) FROM Poliza AS pol WHERE empresa.PK_Empresa = pol.FK01_Empresa AND pol.Tipo = 1 AND pol.Activa = 1 AND YEAR(pol.Fecha) = :anio) AS pIngreso, " + 
					"	(SELECT COUNT(*) FROM Poliza AS pol WHERE empresa.PK_Empresa = pol.FK01_Empresa AND pol.Tipo = 1 AND pol.Activa = 2 AND YEAR(pol.Fecha) = :anio) AS pEgreso, " + 
					"	(SELECT COUNT(*) FROM Poliza AS pol WHERE empresa.PK_Empresa = pol.FK01_Empresa AND pol.Tipo = 1 AND pol.Activa = 3 AND YEAR(pol.Fecha) = :anio) AS pDiario " +
					"FROM " + 
					"	Empresa " + 
					"WHERE " + 
					"	empresa.Pais LIKE '%mex%' " + 
					"ORDER BY " + 
					"	empresa";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("anio", fecha.getYear());
			List<CatalogoItem> empr = super.jdbcTemplate.query(query, map, new CatalogoEmpresaRowMapper());
			return empr;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}
	
	@Override
	public List<CatalogoItem> getPaisesCodPost() throws ProquifaNetException {
		try{
			String sql = "SELECT Pais AS nombre, CodigoPais AS clave FROM CodigoPostal GROUP BY Pais, CodigoPais";
			return super.jdbcTemplate.query(sql, new CatalogoItemRowMapper());
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<CatalogoItem>();
		}
	}
	
	@Override
	public List<NominaCatalogo> getNominaCatalogoTipo(String tipo) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo", tipo);
			List<NominaCatalogo> lst = new ArrayList<NominaCatalogo>();
			String query = "SELECT * FROM NominaCatalogo WHERE Tipo = :tipo";
			lst = super.jdbcTemplate.query(query, map, new NominaCatalogoRowMapper());
			return lst;
		}catch(Exception e) {
			e.printStackTrace();
			return new ArrayList<NominaCatalogo>();
		}
	}
	
}