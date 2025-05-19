/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.HistorialMonitoreo;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class HistorialMonitoreoRowMapper implements RowMapper {


	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		HistorialMonitoreo monitoreo = new HistorialMonitoreo();
		//Origen,Fecha,Gestor,Tipo,Comentarios
		
		monitoreo.setOrigenMonitoreo(rs.getString("Origen"));
		monitoreo.setFecha(rs.getDate("Fecha"));
		monitoreo.setGestor(rs.getString("Gestor"));
		monitoreo.setTipo(rs.getString("Tipo"));
		monitoreo.setComentarios(rs.getString("Comentarios"));
		
		return monitoreo;
	}

}
