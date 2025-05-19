/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Proveedor;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ProveedorDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ProveedorRowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class ProveedorDAOImpl extends DataBaseDAO implements ProveedorDAO {
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ProveedorDAO#obtenerProveedorPorNombre(java.lang.String)
	 */
	public Long obtenerAdeudosDeProveedor(Long idProveedor, String periodo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idProveedor", idProveedor);
		map.put("periodo", periodo);
		String queryString = "SELECT COUNT(idproveedor) AS adeudos FROM facturaxpagar "
			+ "WHERE foliopg is null AND  idproveedor = '"
			+ idProveedor
			+ "'AND Fecha >= '" + periodo + "'";
		return super.queryForLong(queryString,map);
	}

	public Long obtenerFacturasDeProveedor(Long idProveedor, String periodo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idProveedor", idProveedor);
		map.put("periodo", periodo);
		String queryString = "SELECT COUNT(foliopg) AS adeudos FROM facturaxpagar "
			+ "WHERE foliopg IS NOT NULL AND  idproveedor = '"
			+ idProveedor + "' " + "AND Fecha >= '" + periodo + "'";
		return super.queryForLong(queryString,map);
	}

	public Proveedor obtenerProveedorPorId(Long idProveedor) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String query = "SELECT * FROM Proveedores WHERE clave = '" + idProveedor + "'";
			return (Proveedor)super.jdbcTemplate.queryForObject(query,map, new ProveedorRowMapper());
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nidProveedor: "+idProveedor);
			return new Proveedor();
		}
	}

	public Proveedor obtenerProveedorPorNombre(String nombre) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nombre", nombre);
			String condicion="";
			if(nombre!=null && !nombre.equals("")){condicion = " WHERE Nombre='" + nombre + "'";}
			return (Proveedor)super.jdbcTemplate.queryForObject("SELECT * FROM Proveedores" + condicion,map, new ProveedorRowMapper());
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nNombre: "+nombre);
			return new Proveedor();
		}
	}

	@Override
	public String getPagadorProveedor(Long idProveedor)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String sql = "SELECT EM.Usuario "
					+ " FROM Proveedores P"
					+ " INNER JOIN (SELECT Clave, Usuario FROM Empleados) EM ON EM.Clave = P.Pagador"
					+ " WHERE P.clave = '" + idProveedor + "'";
			return (String) super.jdbcTemplate.queryForObject(sql,map, String.class);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nidProveedor: "+idProveedor);
			return "pmendez";
		}
	}

	@Override
	public String getCompradorProveedor(Long idProveedor)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String sql = "SELECT EM.Usuario "
					+ " FROM Proveedores P"
					+ " INNER JOIN (SELECT Clave, Usuario FROM Empleados) EM ON EM.Clave = P.FK01_Empleado"
					+ " WHERE clave = '" + idProveedor + "'";
			return (String) super.jdbcTemplate.queryForObject(sql,map, String.class);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nidProveedor: "+idProveedor);
			return "lrosas";
		}
	}
	
	
	@Override
	public List<Proveedor> getProveedores(Integer habilitado) throws ProquifaNetException {
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder("SELECT *, Rsocial as razonSocial, Curp RFC FROM Proveedores \n");
			if (habilitado != null)
					sbQuery.append("WHERE Habilitado = :habilitado ORDER BY nombre \n");
			
			parametros.put("habilitado", habilitado);
			
			return jdbcTemplate.query(sbQuery.toString(), parametros, new BeanPropertyRowMapper<>(Proveedor.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}