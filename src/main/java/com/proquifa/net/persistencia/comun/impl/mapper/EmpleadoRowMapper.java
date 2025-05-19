/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Empleado;

/**
 * @author ernestogonzalezlozada
 *
 */
public class EmpleadoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		String nivel_Empleado="";
		String subproceso_Empleado="";
		Long idSubproceso=null;
		
		Empleado empleado = new Empleado();
		empleado.setClave(rs.getLong("clave"));
		empleado.setUsuario(rs.getString("usuario"));
		empleado.setPassword(rs.getString("pass"));
		empleado.setNivel(rs.getLong("nivel"));
		empleado.setNombre(rs.getString("nombre"));
		empleado.setDepartamento(rs.getString("departamento"));
		empleado.setFase(rs.getLong("fase"));
		empleado.setPuesto(rs.getString("puesto"));
		empleado.setFechaIngreso(rs.getDate("fingreso"));
		empleado.setFechaContrato(rs.getDate("fcontrato"));
		empleado.setFechaNacimiento(rs.getDate("fnacimiento"));
		empleado.setTelefono(rs.getString("teldom"));
		empleado.setTelefono2(rs.getString("telCel"));
		empleado.setEmpresa(rs.getString("empresa"));
		empleado.setIdFuncion(rs.getLong("FK01_Funcion"));
		empleado.setIdEmpleado(rs.getLong("clave"));
		try {nivel_Empleado = rs.getString("Nivel_General");} catch (Exception e) {}
		
		if(rs.wasNull()){
			empleado.setNivelGeneral(null);
		}else{			
			empleado.setNivelGeneral(nivel_Empleado);
		}
		try {subproceso_Empleado = rs.getString("Subproceso");} catch (Exception e) {}
		
		if(rs.wasNull()){
			empleado.setSubproceso(null);
		}else{
			empleado.setSubproceso(subproceso_Empleado);
		}
		try {idSubproceso=rs.getLong("idSubproceso");} catch (Exception e) {}
		
		if(rs.wasNull()){
			empleado.setIdSubproceso(null);
		}else{
			empleado.setIdSubproceso(idSubproceso);
		}
		try {empleado.setNombreFuncion(rs.getString("NombreFuncion"));} catch (Exception e) {}
		
		
		return empleado;
	}
}