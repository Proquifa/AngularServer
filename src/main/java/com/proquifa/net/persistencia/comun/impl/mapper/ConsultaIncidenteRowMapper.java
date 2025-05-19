/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.consultas.comun.ConsultaIncidente;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class ConsultaIncidenteRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConsultaIncidente datos = new ConsultaIncidente();
		Long folioRelacionado = 0L;
		Boolean decisionGestion = null;
		Boolean abiertoBD = null;
		datos.setIdIncidente(rs.getLong("PK_Incidente"));
		datos.setFolio(rs.getString("Folio"));
		datos.setNombre_Empleado(rs.getString("Nombre_Empleado"));
		folioRelacionado = rs.getLong("Asociado");
		if(rs.wasNull()){
			datos.setIncidenteRelacionado(null);
		}else{
			datos.setIncidenteRelacionado(folioRelacionado);
		}
		datos.setTipo(rs.getString("Tipo"));
		datos.setOrigen(rs.getString("Origen"));
		datos.setAceptado(rs.getBoolean("Aceptado"));
		decisionGestion = rs.getBoolean("Decision");
		if(rs.wasNull()){
			datos.setDecision("nulo");
		}else{
			if(decisionGestion==true){
				datos.setDecision("1");
			}else{
				datos.setDecision("0");
			}
		}
		abiertoBD = rs.getBoolean("Abierto");
		if(rs.wasNull()){
			datos.setEstado("Abierto");
		}else{
			if(abiertoBD == true){
				datos.setEstado("Abierto");
			}else{
				datos.setEstado("Cerrado");
			}
		}
		datos.setFecha(rs.getTimestamp("Fecha_Incidente"));
		datos.setIdGestionRelacionada(rs.getLong("PK_Gestion"));	
		
		return datos;
	}
}
