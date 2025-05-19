/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;


import com.proquifa.net.modelo.comun.Incidente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.consultas.comun.ConsultaIncidente;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.IncidenteDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ConsultaIncidenteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.IncidenteRowMapper;


/**
 * @author amartinez
 *
 */
@Repository
public class IncidenteDAOImpl extends DataBaseDAO implements IncidenteDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.IncidenteDAO#actualizarIncidente(mx.com.proquifa.proquifanet.modelo.comun.Incidente)
	 */
	public Long insertarIncidente(Incidente incidente) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("incidente", incidente);
			
			Object[] params =  {incidente.getIdEmpleado(), incidente.getDescripcion(), incidente.getLugar(), incidente.getCuando(), new Date(), 
					incidente.getComentarios(), incidente.getFolio(), incidente.getIncidenteRelacionado()};
			String query = "INSERT INTO incidente (FK01_usuario, descripcion, lugar, cuando, fecha, comentarios, folio, fk02_incidente) VALUES ";
			query+= "(?, ?, ?, ?, ?, ?, ?, ?)";
			super.jdbcTemplate.update(query, map);
			Long idIncidente = super.queryForLong("SELECT IDENT_CURRENT ('incidente')");
			return idIncidente;
		}catch (RuntimeException rte) {
			return -1L;
		}
	}

	public Incidente obtenerIncidenteXId(Long idIncidente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idIncidente", idIncidente);
		return (Incidente)super.jdbcTemplate.queryForObject("SELECT *, 0 as idPendiente  FROM incidente WHERE pk_incidente = " + idIncidente, map, new IncidenteRowMapper());
	}

	public List<Incidente> obtenerIncidentes() {
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Incidente> obtenerIncidentesXUsuario(String tipoPendiente, String usuario) {
		String query = "select PK_Incidente, FK01_Usuario, Descripcion, Lugar, Cuando, Comentarios, incidente.folio, pendiente.FInicio as fecha, incidente.fk02_incidente" +
				", pendiente.Folio as idPendiente, Incidente.FK03_Subproceso from Incidente, Pendiente where incidente.PK_Incidente = pendiente.Docto and " +
				"pendiente.Tipo = '" + tipoPendiente + "' and  pendiente.FFin is null and pendiente.Responsable = '" + usuario  +"'";
		return super.jdbcTemplate.query(query, new IncidenteRowMapper());
	}

	public Incidente obtenerIncidenteXFolio(String folio) {
		try{
			return (Incidente) super.jdbcTemplate.query("SELECT *,0 as idPendiente FROM Incidente WHERE Folio= '"+ folio +"'", new IncidenteRowMapper());
		}catch (RuntimeException e) {
		//	logger.error("Error..." + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConsultaIncidente> obtenerConsultaDeIncidentes(String folio,
			ConsultaIncidente paramConsulta) {
		String query = "";
		String usuario = "";
		String filtrajeParaGestion = "";
		String fechas = "";
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
		
		String conGestion = "SELECT i.PK_Incidente,i.Folio,g.Nombre_Empleado,a.Asociado,r.Tipo,r.Origen,r.Aceptado,r.Decision,i.Abierto,i.Fecha AS Fecha_Incidente,r.PK_Gestion FROM Incidente AS i "+
							"LEFT JOIN(SELECT Incidente.PK_Incidente,Empleados.Nombre AS Nombre_Empleado FROM Incidente,Empleados WHERE Empleados.Clave=Incidente.FK01_Usuario) AS g ON g.PK_Incidente=i.PK_Incidente "+
							"LEFT JOIN(SELECT TOP 1 PK_Incidente AS Asociado,FK02_Incidente FROM Incidente ORDER BY Fecha DESC) AS a ON a.FK02_Incidente=i.PK_Incidente "+
							"LEFT JOIN(SELECT Gestion.FK01_Incidente,Gestion.Tipo,Subproceso.Nombre AS Origen,Gestion.Aceptado,Gestion.Decision,Gestion.PK_Gestion,Subproceso.PK_Subproceso FROM Gestion,Subproceso WHERE Gestion.FK05_Subproceso=Subproceso.PK_Subproceso) AS r ON r.FK01_Incidente=i.PK_Incidente "+ 
							"WHERE ";
		
		String sinGestion = "SELECT i.PK_Incidente,i.Folio,e.Nombre AS Nombre_Empleado,NULL AS Asociado,NULL AS Tipo,NULL AS Origen,NULL AS Aceptado,NULL AS Decision,i.Abierto,i.Fecha AS Fecha_Incidente,NULL AS PK_Gestion "+
							"FROM Incidente AS i,Empleados AS e WHERE e.Clave=i.FK01_Usuario AND PK_Incidente NOT IN (SELECT FK01_Incidente FROM Gestion) "+
							"AND ";

		try {
			if(folio!=null){
				query = conGestion + "i.Folio LIKE '%"+ folio +"%' UNION " + sinGestion + "i.Folio LIKE '%"+ folio +"%' ORDER BY PK_Incidente ASC";			
			}else{
				fechas = "(i.Fecha>='" + formatoDeFecha.format(paramConsulta.getFechaInicio()) + " 00:00' AND i.Fecha<='" + formatoDeFecha.format(paramConsulta.getFechaFin()) + " 23:59') ";				
				if(paramConsulta.getIdEmpleado()!=0){
					usuario = "AND i.FK01_Usuario="+ paramConsulta.getIdEmpleado() +" ";
				}
				
				if(Integer.parseInt(paramConsulta.getOrigen())!=0){
					filtrajeParaGestion = filtrajeParaGestion + " AND r.PK_Subproceso="+ paramConsulta.getOrigen() +" ";
				}
				
				if(!paramConsulta.getTipo().equals("--TODOS--")){
					filtrajeParaGestion = filtrajeParaGestion + "AND r.Tipo='"+ paramConsulta.getTipo() +"' ";
				}				
				
				if(!paramConsulta.getDecision().equals("--TODOS--")){
					if(paramConsulta.getDecision().equals("Aceptado")){
						filtrajeParaGestion = filtrajeParaGestion + "AND r.Aceptado=1 ";
					}else{
						filtrajeParaGestion = filtrajeParaGestion + "AND r.Aceptado=0 ";
					}
				}
				
				if(!paramConsulta.getEstado().equals("--TODOS--")){
					if(paramConsulta.getEstado().equals("Abierto")){
						filtrajeParaGestion = filtrajeParaGestion + "AND (i.Abierto=1 or i.Abierto IS NULL) ";
					}else if (paramConsulta.getEstado().equals("Cerrado")){
						filtrajeParaGestion = filtrajeParaGestion + "AND i.Abierto = 0 ";
					}
				}
				
				if(!filtrajeParaGestion.equals("")){
					query = conGestion + fechas + usuario + filtrajeParaGestion +
							" ORDER BY PK_Incidente ASC";
				}else{
					query = conGestion + fechas + usuario + "UNION " +  
							sinGestion + fechas + usuario + 
							" ORDER BY PK_Incidente ASC";
				}
			}
			return super.jdbcTemplate.query(query, new ConsultaIncidenteRowMapper());
		} catch (RuntimeException e) {
			return null;
		}
	}

	public Long obtenerCantidadDeIncidentes(String tipo, String fecha) {
		//Evalua que quiere encontrar Abierto / Cerrado
		try {
			String condicion = "";
			if(tipo.equals("Abierto")){
				condicion="Abierto = 1 or Abierto IS NULL";
			}else{
				condicion="Abierto = 0";
			}
			
			return super.queryForLong("SELECT COUNT(*) AS Resultado FROM Incidente WHERE " + condicion + fecha);
		} catch (Exception e) {
		//	logger.error("Error...... "  + e.getMessage());
			return -1L;
		}
	}

	public Boolean cerrarIncidenteXidIncidente(Long idIncidente)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idIncidente", idIncidente);
			super.jdbcTemplate.update("UPDATE Incidente SET Abierto = 0 WHERE PK_Incidente = "+ idIncidente, map);
			return true;
		} catch (Exception e) {
			//logger.error("Error....."  + e.getMessage());
			return false;
		}
	}

	public Boolean actualizarSubprocesoIncidente(Long indIncidente,
			Long idSubproceso) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("indIncidente", indIncidente);
			map.put("idSubproceso", idSubproceso);
			super.jdbcTemplate.update("UPDATE Incidente SET FK03_Subproceso="+ idSubproceso +" WHERE PK_Incidente="+ indIncidente, map);
			return true;
		} catch (Exception e) {
		//	logger.error("Error....."  + e.getMessage());
			return false;
		}
	}

	public Long getSubProcesoIncidente(Long idIncidente)
			throws ProquifaNetException {
		try{
			Long idSubproceso = super.queryForLong("SELECT FK03_Subproceso FROM Incidente WHERE PK_Incidente="+ idIncidente);
//			logger.info("IdSubproceso relacionado: "+ idSubproceso);
			return idSubproceso;
		}catch (RuntimeException rte) {
		//	logger.error("Error: "+ rte.getMessage());
			return null;
		}
	}
}

