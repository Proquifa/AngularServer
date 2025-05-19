package com.proquifa.net.persistencia.comun.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.proquifa.net.modelo.comun.Accion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.AccionDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.AccionRowMapper;


/**
 * @author amartinez
 * 
 */
@Repository
public class AccionDAOImpl extends DataBaseDAO implements AccionDAO {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.com.proquifa.proquifanet.persistencia.comun.AccionDAO#actualizarAccion
	 * (mx.com.proquifa.proquifanet.modelo.comun.Accion)
	 */
	public Boolean actualizarAccion(Accion accion) throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("accion", accion);
			Object[] params = { accion.getIncidente(), accion.getResponsable(),
					new Date(), accion.getFechaRealizacion(), accion.getFechaEstimadaRealizacion(),accion.getTipo(),
					accion.getEficacia(), accion.getDescripcion(),accion.getFolio(), accion.getComentarios(), accion.getHorasInvertidas(),
					accion.getEficaciaVerificacion(), accion.getDescripcionVerificacion(), 
				//	accion.getEficaciaReal(), 
					accion.getRazonesPonderacion(),	accion.getIdAccion() };
			String query = "UPDATE accion SET FK01_incidente = ?, FK02_empleado = ?, fecha = ?, fecha_realizacion = ?, fecha_estimada_realizacion = ?, tipo = ?, eficacia = ?, descripcion = ?, folio = ?, comentarios = ?," 
					+ " horas_invertidas = ?, eficacia_verificacion = ?, descripcion_verificacion = ?, " 
					//+ " eficacia_real = ?, " 
					+ " razones_ponderacion = ? "
					+ "WHERE pk_accion = ?";
			super.jdbcTemplate.update(query, map);
			
			return true;
		} catch (RuntimeException rte) {
			
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.com.proquifa.proquifanet.persistencia.comun.AccionDAO#eliminarAccion
	 * (mx.com.proquifa.proquifanet.modelo.comun.Accion)
	 */
	public Boolean eliminarAccion(Accion accion) throws ProquifaNetException{
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("IdAccion", accion.getIdAccion() );
			Object[] params = { accion.getIdAccion() };
			String query = "DELETE accion WHERE pk_accion =  ?";
			super.jdbcTemplate.update(query, map);
			return true;
		} catch (RuntimeException rte) {
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.com.proquifa.proquifanet.persistencia.comun.AccionDAO#registrarAccion
	 * (mx.com.proquifa.proquifanet.modelo.comun.Accion)
	 */
	public Long registrarAccion(Accion accion) throws ProquifaNetException{
		try {

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Accion", accion );
			Object[] params = { accion.getIncidente(), accion.getResponsable(),
					new Date(), accion.getFechaEstimadaRealizacion(), accion.getTipo(),
					accion.getEficacia(), accion.getDescripcion(),
					accion.getFolio(), accion.getComentarios() };
			String query = "INSERT INTO accion (FK01_incidente, FK02_empleado, fecha, fecha_estimada_realizacion, tipo, eficacia, descripcion, folio, comentarios) VALUES ";
			query += "(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			super.jdbcTemplate.update(query, map);
			Long idAccion = super.queryForLong("SELECT IDENT_CURRENT ('accion')");
			return idAccion;
		} catch (RuntimeException rte) {
			return -1L;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * mx.com.proquifa.proquifanet.persistencia.comun.AccionDAO#obtenerAccionXId
	 * (java.lang.Long)
	 */
	public Accion obtenerAccionXId(Long idAccion) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idAccion", idAccion );
			return (Accion) super.jdbcTemplate.queryForObject(
					"SELECT *, 0 as idPendiente, '' as incidenteF, 0 as programo FROM accion WHERE pk_accion =" + idAccion,map, 
					new AccionRowMapper());
		} catch (RuntimeException rte) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Accion> obtenerAccionesIncidente(Long idIncidente) throws ProquifaNetException{
		List<Accion> acciones = super.jdbcTemplate.query("SELECT *, 0 as idPendiente, '' as incidenteF, 0 as programo FROM accion WHERE fk01_incidente = " + idIncidente , new AccionRowMapper());
		return acciones;

	}

	@SuppressWarnings("unchecked")
	public List<Accion> obtenerAccionXUsuario(String usuario, String tipoPendiente) throws ProquifaNetException{
		String query = "select accion.*, pendiente.Folio as idPendiente, incidente.Folio as incidenteF, gestion.FK02_Empleado as programo from " +
			"accion, Pendiente, Incidente, Gestion where accion.FK01_Incidente = Incidente.PK_Incidente and " +
			"accion.PK_Accion = pendiente.Docto and gestion.FK01_Incidente = incidente.PK_Incidente and pendiente.Tipo = '" + tipoPendiente + "' " +
			"and pendiente.Responsable = '" + usuario + "' and pendiente.ffin is null";
		List<Accion> acciones = super.jdbcTemplate.query(query, new AccionRowMapper());
		return acciones;
	}

	public Boolean borrarAccionesXIdIncidente(Long idIncidente)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idIncidente", idIncidente );
			String query = "DELETE accion WHERE fk01_incidente = " + idIncidente;
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(RuntimeException rte){
			return false;
		}
	}

	public Long numeroPendientesAccionVerificacion(Long idIncidente, String tipo, Boolean fechaCerrada)
			throws ProquifaNetException {
		try{
			String restriccion= "";
			if(fechaCerrada != null){
				if(fechaCerrada == true){
					restriccion = " AND FFin IS NULL";
				}else if(fechaCerrada == false){
					restriccion = " AND FFin IS NOT NULL";
				}
			}
			return super.queryForLong("SELECT COUNT(*) AS Total FROM Pendiente WHERE Docto IN (SELECT PK_Accion FROM Accion WHERE FK01_Incidente = "+ idIncidente +") AND Tipo = '"+ tipo +"'" + restriccion);
		}catch(RuntimeException e){
			return -1L;
		}
	}

	public Long obtenerNumeroPendientesDeAccion(Long idIncidente)
			throws ProquifaNetException {
		return null;
	}

	public Double obtenerEficaciaGlobalXIdIncidente(Long idIncidente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idIncidente", idIncidente );
			return (Double) super.jdbcTemplate.queryForObject("SELECT AVG(Eficacia_real) AS Eficacia FROM Accion WHERE FK01_Incidente = " + idIncidente, map, Double.class);
		} catch (RuntimeException e) {
			return -1.0;
		}
	}
}