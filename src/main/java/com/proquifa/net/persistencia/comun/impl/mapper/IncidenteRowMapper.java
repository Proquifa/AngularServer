/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Incidente;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class IncidenteRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Long incidenteRelacionado = 0L;
		Incidente incidente = new Incidente();
		incidente.setIdIncidente(rs.getLong("pk_incidente"));
		incidente.setIdEmpleado(rs.getLong("fk01_usuario"));
		incidente.setDescripcion(rs.getString("descripcion"));
		incidente.setLugar(rs.getString("lugar"));
		incidente.setCuando(rs.getString("cuando"));		
		incidente.setComentarios(rs.getString("comentarios"));
		incidente.setFolio(rs.getString("folio"));
		incidente.setFecha(rs.getTimestamp("fecha"));		
		incidente.setIdPendiente(rs.getLong("idPendiente"));
		incidente.setIdSubprocesoRelacionado(rs.getLong("FK03_Subproceso"));
		incidenteRelacionado = rs.getLong("fk02_incidente");
		if(rs.wasNull()){
			incidente.setIncidenteRelacionado(null);
		}else{			
			incidente.setIncidenteRelacionado(incidenteRelacionado);
		}
		return incidente;
	}

}
