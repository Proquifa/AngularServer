/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Accion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class AccionRowMapper implements RowMapper  {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Accion accion = new Accion();
		accion.setIdAccion(rs.getLong("pk_accion"));
		accion.setIncidente(rs.getLong("fk01_incidente"));
		accion.setResponsable(rs.getLong("fk02_Empleado"));
		accion.setFecha(rs.getTimestamp("fecha"));
		accion.setFechaEstimadaRealizacion(rs.getTimestamp("fecha_estimada_realizacion"));
		accion.setTipo(rs.getString("tipo"));
		accion.setEficacia(rs.getDouble("eficacia"));
		accion.setFolio(rs.getString("folio"));
		accion.setDescripcion(rs.getString("descripcion"));
		accion.setFechaRealizacion(rs.getTimestamp("fecha_realizacion"));
		accion.setComentarios(rs.getString("comentarios"));
		accion.setHorasInvertidas(rs.getInt("horas_invertidas"));
		accion.setEficaciaVerificacion(rs.getDouble("eficacia_verificacion"));
		accion.setDescripcionVerificacion(rs.getString("descripcion_verificacion"));
		accion.setEficaciaReal(rs.getDouble("eficacia_real"));
		accion.setRazonesPonderacion(rs.getString("razones_ponderacion"));
		accion.setIdPendiente(rs.getLong("idPendiente"));
		accion.setIncidenteFolio(rs.getString("incidenteF"));
		accion.setProgramo(rs.getLong("programo"));
		return accion;
	}
}