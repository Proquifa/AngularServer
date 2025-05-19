/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
//import mx.com.proquifa.proquifanet.modelo.comun.Modulo;
//import mx.com.proquifa.proquifanet.persistencia.comun.ModuloDAO;
//import mx.com.proquifa.proquifanet.persistencia.comun.impl.mapper.ModuloRowMapper;
//import mx.com.proquifa.proquifanet.persistencia.impl.base.BaseJdbcDAOImpl;

import com.proquifa.net.modelo.comun.Modulo;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ModuloDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ModuloRowMapper;


/**
 * @author
 *
 */
@Repository
public class ModuloDAOImpl extends  DataBaseDAO  implements ModuloDAO {

	@SuppressWarnings("unchecked")
	public List<Modulo> obtenerModulos() {
		String query = "SELECT * FROM Modulo";
		return super.jdbcTemplate.query(query, new ModuloRowMapper());
	}

	
	@SuppressWarnings("unchecked")
	public List<Modulo> obtenerModulosXIdEmpleado(Long idEmpleado) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idEmpleado", idEmpleado);
		//Object params [] = {idEmpleado};
		String query = "SELECT * FROM Modulo WHERE PK_Modulo IN (SELECT FK02_Modulo FROM Empleado_Modulo WHERE FK01_Empleado = :idEmpleado ) ORDER BY Nombre ASC";
		
	
		
		return (List<Modulo>) super.jdbcTemplate.query(query,map, new ModuloRowMapper());
				
	}

	
}
