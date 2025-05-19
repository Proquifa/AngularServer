/**
 * 
 */
package com.proquifa.net.persistencia.ventas.requisicion.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.requisicion.Requisicion;


import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class RequisicionRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Requisicion requi = new Requisicion();

		
		//para Empleado
		requi.setIdEmpleadoEv(rs.getInt("EmpClave"));
		requi.setNombreEmpleadoEv(rs.getString("EmpNombre"));
		

		
//		requi.setDireccionCompletaContacto(rs.getString("Calle") +" "+ rs.getString("Municipio")+" "+rs.getString("Estado")+" "+rs.getString("Pais")
//				+" "+rs.getString("CP"));
		

//		requi.setFolioRequi(rs.getLong("Folio"));
//		requi.setRegistroRequi(rs.getString("Ingreso"));
//		requi.setFechaOrigen(rs.getTimestamp("FHOrigen"));
//		requi.setFechaRegistro(rs.getTimestamp("Fecha"));
//		requi.setDiasDEspera(rs.getInt("TT"));
		
		return requi;
	}
}