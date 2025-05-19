package com.proquifa.net.persistencia.staff.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.staff.Area;
import com.proquifa.net.modelo.staff.Asistencia;
import com.proquifa.net.modelo.staff.CambioTurno;
import com.proquifa.net.modelo.staff.Categoria;
import com.proquifa.net.modelo.staff.Departamento;
import com.proquifa.net.modelo.staff.TipoChecada;
import com.proquifa.net.modelo.staff.TipoIncidencia;
import com.proquifa.net.modelo.staff.Trabajador;
import com.proquifa.net.modelo.staff.Turno;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.staff.AsistenciaDAO;
import com.proquifa.net.persistencia.staff.impl.mapper.AreaRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.AsistenciaGDLRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.AsistenciaRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.AsistenciaRyndemRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.CambioTurnoGDLRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.CambioTurnoRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.CambioTurnoRyndemRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.CategoriaRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.DepartamentoRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.TipoChecadaRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.TipoIncidenciaRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.TrabajadorRowMapper2;
import com.proquifa.net.persistencia.staff.impl.mapper.TrbajadorRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.TurnoGDLRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.TurnoRowMapper;
import com.proquifa.net.persistencia.staff.impl.mapper.TurnoRyndemRowMapper;

/**
 * @author iGarcia
 *
 */
@Repository
public class AsistenciaDAOImpl extends DataBaseDAO implements AsistenciaDAO{
	
	final Logger log = LoggerFactory.getLogger(AsistenciaDAOImpl.class);
	
	Funcion funcion;
	
	@Autowired
	@PersistenceContext(unitName = "Checador")
	private EntityManager em;
	
	@Autowired
	@PersistenceContext(unitName = "ChecadorGDL")
	private EntityManager emGDL;
	
	@SuppressWarnings("unchecked")
	public List<Area> consultarArea(){
		try{
			String query = "SELECT Area, Nombre FROM Checador.dbo.tblArea ORDER BY Nombre";
			return super.jdbcTemplate.query(query, new AreaRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return null;
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Asistencia> consultarAsistenciaProquifa(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		
		try{
			
			String query  = "";
			boolean primero = true;
			do{
				if (fechaInicio.getDay() != 0 && fechaInicio.getDay() != 6) {
					
					if (!primero) {
						query += "\nUNION\n";
					}else{
						primero = false;
					}	
						query += " SELECT t.Trabajador, LTRIM(t.NombreCorto) AS NombreCorto, COALESCE(CONVERT(DATE,(CONVERT(VARCHAR,c.Checada,111))),'"+formatoFecha.format(fechaInicio)+"') Fecha, CONVERT(Varchar(5),c.Checada,108) Hora, c.Checada,	" +
								" (CASE" +
								" 	WHEN c.TipoChecada = 'ET' THEN 'ENTRADA DE TURNO'" +
								" 	WHEN c.TipoChecada = 'EC' THEN 'ENTRADA DE COMER'" +
								" 	WHEN c.TipoChecada = 'SC' THEN 'SALIDA A COMER'" + 		
								" 	WHEN c.TipoChecada = 'ST' THEN 'SALIDA DE TURNO'" + 		
								" 	WHEN c.TipoChecada = 'EI' THEN 'ENTRADA IMPREVISTA'" + 		
								" 	WHEN c.TipoChecada = 'SI' THEN 'SALIDA IMPREVISTA'" + 		
								" 	ELSE RTRIM(c.TipoChecada) " +
								"  END) AS TipoChecada,	a.Nombre Area, DEP.Nombre Departamento, CAT.Nombre Categoria, t.Rotacion, TC.Nombre Incidencia" +  
								"  FROM (SELECT tb.Trabajador, NombreCorto, Area, Depto, Categoria,Rotacion FROM Checador.dbo.tblTrabajador AS tb" +
								" 			INNER JOIN (SELECT min(Fecha) as Fecha, Trabajador FROM Checador.dbo.tblTrabTurno GROUP BY Trabajador HAVING min(Fecha) <= '"+formatoFecha.format(fechaInicio)+"') as tt ON tt.Trabajador = tb.Trabajador " +          
								" 			WHERE Activo =1) AS t " +
								"  LEFT OUTER JOIN (SELECT Trabajador,Checada, TipoChecada FROM Checador.dbo.tblChecada " +
								" 					WHERE Checada >= '"+formatoFecha.format(fechaInicio)+" 00:00'  AND Checada <=  '"+formatoFecha.format(fechaInicio)+" 23:59') AS c ON t.Trabajador = c.Trabajador " +
								"  INNER JOIN (SELECT AREA, Nombre FROM Checador.dbo.tblArea) AS a ON a.Area = t.Area   INNER JOIN (SELECT Depto, Nombre FROM Checador.dbo.tblDepto) DEP ON DEP.Depto = t.Depto " + 
								"  INNER JOIN (SELECT Categoria, Nombre FROM Checador.dbo.tblCategoria) CAT ON CAT.Categoria = t.Categoria  " +
								"  LEFT JOIN (SELECT TRCON.Trabajador, TRCON.Fecha, CON.Concepto, CON.Nombre FROM Checador.dbo.tblTrabConcepto TRCON " + 
								"  INNER JOIN (SELECT Concepto, Nombre FROM Checador.dbo.tblConcepto) CON ON TRCON.Concepto = CON.Concepto ) TC ON TC.Trabajador = t.Trabajador " +
								"  AND TC.Fecha = '" + formatoFecha.format(fechaInicio) + "'" +
								"  LEFT JOIN SAP.dbo.DFestivos df ON df.Fecha =  '"+formatoFecha.format(fechaInicio)+"' WHERE df.Fecha IS NULL";
								
						if (idTrabajador > 0){
								query +=" AND t.Trabajador =" + idTrabajador.toString() + " ";
						}
						
						if (nombreTrabajador != ""){
							query +=" AND LTRIM(t.NombreCorto) ='" + nombreTrabajador + "' ";
						}
						
						
						if (area != ""){
								query += " AND a.Area ='" + area + "' ";	
						}
						
						if (depto != "")
						{
							query += " AND t.Depto='" + depto + "' " ;
						}
						
						if (categoria != "")
						{
								query += " AND CAT.Categoria ='" + categoria + "' ";
						}
						
						if (incidencia != ""){
							if (!incidencia.equals("FALTA") && !incidencia.equals("RETARDO") && !incidencia.equals("FUERA DE TIEMPO") && !incidencia.equals("NINGUNA")){
								query += " AND TC.Concepto ='" + incidencia + "' ";
							}
						}
						
					}
					fechaInicio.setTime((long) fechaInicio.getTime()+(86400000));
					
				} while (fechaInicio.getTime() <= fechaFin.getTime());
		
		   log.info("query: " + query.toString());
		
			List<Asistencia> asis = super.jdbcTemplate.query(query, new AsistenciaRowMapper());
			
			log.info("/************ Resultados CDMX:");
			log.info("",asis.size());
			
		return asis;
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFechaInicio: "+fechaInicio,"\nFechaFin: "+fechaFin,"\nidTrabajador: "+idTrabajador,"\nTipoChecada: "+tipoChecada, "\nArea: "+ area, "\nDepto: "+depto,"\nCategoria: "+ categoria,"\nIncidencia: "+ incidencia,"\nNombreTrabajador: "+nombreTrabajador);
			return new ArrayList<Asistencia>();
		}
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Asistencia>consultarAsistenciaRyndem(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador){
		//SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		try{
			
			em.clear();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoFechaComvertir = new SimpleDateFormat("yyyy-MM-dd");
			
			
			String query  = "";
			boolean primero = true;
			do{
				if (fechaInicio.getDay() != 0 && fechaInicio.getDay() != 6) {
					
					if (!primero) {
						query += "\nUNION\n";
					}else{
						primero = false;
					}	
						query += " SELECT t.Trabajador, LTRIM(t.NombreCorto) AS NombreCorto, CONVERT(Varchar(10),'"+formatoFechaComvertir.format(fechaInicio)+"',111) AS Fecha, CONVERT(Varchar(5),c.Checada,108) Hora, c.Checada," +  
								" (CASE" +
								" 	WHEN c.TipoChecada = 'ET' THEN 'ENTRADA DE TURNO'" +
								" 	WHEN c.TipoChecada = 'EC' THEN 'ENTRADA DE COMER'" +
								" 	WHEN c.TipoChecada = 'SC' THEN 'SALIDA A COMER'" + 		
								" 	WHEN c.TipoChecada = 'ST' THEN 'SALIDA DE TURNO'" + 		
								" 	WHEN c.TipoChecada = 'EI' THEN 'ENTRADA IMPREVISTA'" + 		
								" 	WHEN c.TipoChecada = 'SI' THEN 'SALIDA IMPREVISTA'" + 		
								" 	ELSE RTRIM(c.TipoChecada) " +
								"  END) AS TipoChecada,	a.Nombre Area, DEP.Nombre Departamento, CAT.Nombre Categoria, t.Rotacion, TC.Nombre Incidencia"+
								"  FROM (SELECT tb.Trabajador, NombreCorto, Area, Depto, Categoria,Rotacion FROM Checador.dbo.tblTrabajador AS tb" +
								" 			INNER JOIN (SELECT min(Fecha) as Fecha, Trabajador FROM Checador.dbo.tblTrabTurno GROUP BY Trabajador HAVING min(Fecha) <= '"+formatoFecha.format(fechaInicio)+"') as tt ON tt.Trabajador = tb.Trabajador " +          
								" 			WHERE Activo =1) AS t " +
								"  LEFT OUTER JOIN (SELECT Trabajador,Checada, TipoChecada FROM Checador.dbo.tblChecada " +
								" 					WHERE Checada >= '"+formatoFecha.format(fechaInicio)+" 00:00'  AND Checada <=  '"+formatoFecha.format(fechaInicio)+" 23:59') AS c ON t.Trabajador = c.Trabajador " +
								"  INNER JOIN (SELECT AREA, Nombre FROM Checador.dbo.tblArea) AS a ON a.Area = t.Area   INNER JOIN (SELECT Depto, Nombre FROM Checador.dbo.tblDepto) DEP ON DEP.Depto = t.Depto " + 
								"  INNER JOIN (SELECT Categoria, Nombre FROM Checador.dbo.tblCategoria) CAT ON CAT.Categoria = t.Categoria  " +
								"  LEFT JOIN (SELECT TRCON.Trabajador, TRCON.Fecha, CON.Concepto, CON.Nombre FROM Checador.dbo.tblTrabConcepto TRCON " + 
								"  INNER JOIN (SELECT Concepto, Nombre FROM Checador.dbo.tblConcepto) CON ON TRCON.Concepto = CON.Concepto ) TC ON TC.Trabajador = t.Trabajador " +
								"  AND TC.Fecha = '" + formatoFecha.format(fechaInicio) + "'" +
								"  LEFT JOIN SAP.dbo.DFestivos df ON df.Fecha =  '"+formatoFecha.format(fechaInicio)+"' WHERE df.Fecha IS NULL \n";
								
						if (idTrabajador > 0){
								query +=" AND t.Trabajador =" + idTrabajador.toString() + " ";
						}
						
						if (nombreTrabajador != ""){
							query +=" AND LTRIM(t.NombreCorto) ='" + nombreTrabajador + "' ";
						}
						
						
						if (area != ""){
								query += " AND a.Area ='" + area + "' ";	
						}
						
						if (depto != "")
						{
							query += " AND t.Depto='" + depto + "' " ;
						}
						
						if (categoria != "")
						{
								query += " AND CAT.Categoria ='" + categoria + "' ";
						}
						
						if (incidencia != ""){
							if (!incidencia.equals("FALTA") && !incidencia.equals("RETARDO") && !incidencia.equals("FUERA DE TIEMPO") && !incidencia.equals("NINGUNA")){
								query += " AND TC.Concepto ='" + incidencia + "' ";
							}
						}
						
					}
					fechaInicio.setTime((long) fechaInicio.getTime()+(86400000));
					
				} while (fechaInicio.getTime() <= fechaFin.getTime());
			
			log.info("ryn: " + query);
			StringBuilder sbQuery = new StringBuilder(query);
			Query consulta = em.createNativeQuery(sbQuery.toString());
		
			List<Object[]> x;
			
			try {
				x = consulta.getResultList();
			} catch (Exception e) {
				log.info("Error Ryndem" + e.toString());
				return null;
			}
			
			
		
			List<Asistencia>list = AsistenciaRyndemRowMapper.mapearAistencia(x);
			return list;
		
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFechaInicio: "+ fechaInicio,"\nFechaFin: "+fechaFin,"\nidTrabajador: "+idTrabajador,
					"\nTipoChecada: "+tipoChecada,"\nArea: "+area,"\nDepto: "+depto,"\nCategoria: "+categoria,"\nIncidencia: "+incidencia,
					"\nNombreTrabajador: "+nombreTrabajador);
			return new ArrayList<Asistencia>();
		}
	}
	
	public List<Asistencia>consultarAsistenciaGDL(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador){
		//SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		try{
			
			em.clear();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
			SimpleDateFormat formatoFechaComvertir = new SimpleDateFormat("yyyy-MM-dd");
			
			
			String query  = "";
			boolean primero = true;
			do{
				if (fechaInicio.getDay() != 0 && fechaInicio.getDay() != 6) {
					
					if (!primero) {
						query += "\nUNION\n";
					}else{
						primero = false;
					}	
						query += " SELECT t.Trabajador, LTRIM(t.NombreCorto) AS NombreCorto, CONVERT(Varchar(10),'"+formatoFechaComvertir.format(fechaInicio)+"',111) AS Fecha, CONVERT(Varchar(5),c.Checada,108) Hora, c.Checada," +  
								" (CASE" +
								" 	WHEN c.TipoChecada = 'ET' THEN 'ENTRADA DE TURNO'" +
								" 	WHEN c.TipoChecada = 'EC' THEN 'ENTRADA DE COMER'" +
								" 	WHEN c.TipoChecada = 'SC' THEN 'SALIDA A COMER'" + 		
								" 	WHEN c.TipoChecada = 'ST' THEN 'SALIDA DE TURNO'" + 		
								" 	WHEN c.TipoChecada = 'EI' THEN 'ENTRADA IMPREVISTA'" + 		
								" 	WHEN c.TipoChecada = 'SI' THEN 'SALIDA IMPREVISTA'" + 		
								" 	ELSE RTRIM(c.TipoChecada) " +
								"  END) AS TipoChecada,	a.Nombre Area, DEP.Nombre Departamento, CAT.Nombre Categoria, t.Rotacion, TC.Nombre Incidencia"+
								"  FROM (SELECT tb.Trabajador, NombreCorto, Area, Depto, Categoria,Rotacion FROM ChecadorGDL.dbo.tblTrabajador AS tb" +
								" 			INNER JOIN (SELECT min(Fecha) as Fecha, Trabajador FROM ChecadorGDL.dbo.tblTrabTurno GROUP BY Trabajador HAVING min(Fecha) <= '"+formatoFecha.format(fechaInicio)+"') as tt ON tt.Trabajador = tb.Trabajador " +          
								" 			WHERE Activo =1) AS t " +
								"  LEFT OUTER JOIN (SELECT Trabajador,Checada, TipoChecada FROM ChecadorGDL.dbo.tblChecada " +
								" 					WHERE Checada >= '"+formatoFecha.format(fechaInicio)+" 00:00'  AND Checada <=  '"+formatoFecha.format(fechaInicio)+" 23:59') AS c ON t.Trabajador = c.Trabajador " +
								"  INNER JOIN (SELECT AREA, Nombre FROM ChecadorGDL.dbo.tblArea) AS a ON a.Area = t.Area   INNER JOIN (SELECT Depto, Nombre FROM ChecadorGDL.dbo.tblDepto) DEP ON DEP.Depto = t.Depto " + 
								"  INNER JOIN (SELECT Categoria, Nombre FROM ChecadorGDL.dbo.tblCategoria) CAT ON CAT.Categoria = t.Categoria  " +
								"  LEFT JOIN (SELECT TRCON.Trabajador, TRCON.Fecha, CON.Concepto, CON.Nombre FROM ChecadorGDL.dbo.tblTrabConcepto TRCON " + 
								"  INNER JOIN (SELECT Concepto, Nombre FROM ChecadorGDL.dbo.tblConcepto) CON ON TRCON.Concepto = CON.Concepto ) TC ON TC.Trabajador = t.Trabajador " +
								"  AND TC.Fecha = '" + formatoFecha.format(fechaInicio) + "'" +
								"  LEFT JOIN SAP.dbo.DFestivos df ON df.Fecha =  '"+formatoFecha.format(fechaInicio)+"' WHERE df.Fecha IS NULL \n";
								
						if (idTrabajador > 0){
								query +=" AND t.Trabajador =" + idTrabajador.toString() + " ";
						}
						
						if (nombreTrabajador != ""){
							query +=" AND LTRIM(t.NombreCorto) ='" + nombreTrabajador + "' ";
						}
						
						
						if (area != ""){
								query += " AND a.Area ='" + area + "' ";	
						}
						
						if (depto != "")
						{
							query += " AND t.Depto='" + depto + "' " ;
						}
						
						if (categoria != "")
						{
								query += " AND CAT.Categoria ='" + categoria + "' ";
						}
						
						if (incidencia != ""){
							if (!incidencia.equals("FALTA") && !incidencia.equals("RETARDO") && !incidencia.equals("FUERA DE TIEMPO") && !incidencia.equals("NINGUNA")){
								query += " AND TC.Concepto ='" + incidencia + "' ";
							}
						}
						
					}
					fechaInicio.setTime((long) fechaInicio.getTime()+(86400000));
					
				} while (fechaInicio.getTime() <= fechaFin.getTime());
			
			log.info("query GDL------------------------------------------------------------: " + query);
			
			StringBuilder sbQuery = new StringBuilder(query);
			Query consulta = emGDL.createNativeQuery(sbQuery.toString());
			
		
			List<Object[]> x ;
			
			try {
				x = consulta.getResultList();
			} catch (Exception e) {
				log.info("Error GDL: " + e.toString());
				return null;
			}
			
			
		
			List<Asistencia>list = AsistenciaGDLRowMapper.mapearAistencia(x);
			return list;
		
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFechaInicio: "+ fechaInicio,"\nFechaFin: "+fechaFin,"\nidTrabajador: "+idTrabajador,
					"\nTipoChecada: "+tipoChecada,"\nArea: "+area,"\nDepto: "+depto,"\nCategoria: "+categoria,"\nIncidencia: "+incidencia,
					"\nNombreTrabajador: "+nombreTrabajador);
			return new ArrayList<Asistencia>();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Turno>consultarTurnoProquifa(){
		
		try{
			String query = "SELECT Turno, EntradaHasta, Salida, Comida, ComidaRegreso, ComidaTiempo " +
					" FROM Checador.dbo.tblTurno";
//			log.info(query.toString());
			
			return super.jdbcTemplate.query(query, new TurnoRowMapper());
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new ArrayList<Turno>();
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<CambioTurno> consultarCambioTurnoProquifa(Date fechaInicio, Date fechaFin){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		
		try{
			String query = "SELECT Trabajador, Fecha, Turno FROM Checador.dbo.tblTrabTurno " +
					" WHERE Fecha >= '" + formatoFecha.format(fechaInicio) + "' " +
					" AND Fecha <= '" + formatoFecha.format(fechaFin) + "' " +
					" ORDER BY Fecha";
			
			return super.jdbcTemplate.query(query, new CambioTurnoRowMapper());
		}catch (Exception e) {
			
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFechaInicio: "+ fechaInicio,"\nFechaFin: "+ fechaFin);
			return new ArrayList<CambioTurno>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<CambioTurno>consultarCambioTurnoRyndem(Date fechaInicio, Date fechaFin){
		
		try {
			em.clear();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
			
			String query = "SELECT Trabajador, Fecha, Turno FROM Checador.dbo.tblTrabTurno " +
					" WHERE Fecha >= :pFechaInicio " +
					" AND Fecha <= :pFechaFin " +
					" ORDER BY Fecha";
			
			//log.info(query.toString());
			
			StringBuilder sbQuery = new StringBuilder(query);
			Query consulta = em.createNativeQuery(sbQuery.toString());
			
			consulta.setParameter("pFechaInicio", formatoFecha.format(fechaInicio) + " 00:00");
			consulta.setParameter("pFechaFin", formatoFecha.format(fechaFin) + " 23:59");
			
			List<Object[]> x = consulta.getResultList();
			List<CambioTurno>listCT = CambioTurnoRyndemRowMapper.mapearCambioTurno(x);
			return listCT;
			
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFechaInicion: "+ fechaInicio,"\nFechaFin: "+fechaFin);
			return new ArrayList<CambioTurno>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<CambioTurno>consultarCambioTurnoGDL(Date fechaInicio, Date fechaFin){
		
		try {
			em.clear();
			SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
			
			String query = "SELECT Trabajador, Fecha, Turno FROM ChecadorGDL.dbo.tblTrabTurno " +
					" WHERE Fecha >= :pFechaInicio " +
					" AND Fecha <= :pFechaFin " +
					" ORDER BY Fecha";
			
			//log.info(query.toString());
			
			StringBuilder sbQuery = new StringBuilder(query);
			Query consulta = emGDL.createNativeQuery(sbQuery.toString());
			
			consulta.setParameter("pFechaInicio", formatoFecha.format(fechaInicio) + " 00:00");
			consulta.setParameter("pFechaFin", formatoFecha.format(fechaFin) + " 23:59");
			
			List<Object[]> x = consulta.getResultList();
			List<CambioTurno>listCT = CambioTurnoGDLRowMapper.mapearCambioTurno(x);
			return listCT;
			
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFechaInicion: "+ fechaInicio,"\nFechaFin: "+fechaFin);
			return new ArrayList<CambioTurno>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<Turno>consultarTurnoRyndem(){
		try {
			em.clear();
			String query = "SELECT Turno, EntradaHasta, Salida, Comida, ComidaRegreso, ComidaTiempo " +
					" FROM Checador.dbo.tblTurno";
			//log.info(query.toString());
			
			StringBuilder sbQuery = new StringBuilder(query);
			Query consulta = em.createNativeQuery(sbQuery.toString());
			
			List<Object[]> x = consulta.getResultList();
			List<Turno> lstTurno = TurnoRyndemRowMapper.mapearTurno(x);
			
			return lstTurno;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new ArrayList<Turno>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Turno>consultarTurnoGDL(){
		try {
			em.clear();
			String query = "SELECT Turno, EntradaHasta, Salida, Comida, ComidaRegreso, ComidaTiempo " +
					" FROM ChecadorGDL.dbo.tblTurno";
			//log.info(query.toString());
			
			StringBuilder sbQuery = new StringBuilder(query);
			Query consulta = emGDL.createNativeQuery(sbQuery.toString());
			
			List<Object[]> x = consulta.getResultList();
			List<Turno> lstTurno = TurnoGDLRowMapper.mapearTurno(x);
			
			return lstTurno;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new ArrayList<Turno>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Categoria> consultarCategoria(){
		try{
			String query = "SELECT Categoria, Nombre " +
					" FROM Checador.dbo.tblCategoria " +
					" ORDER BY Nombre";	
			
			//log.info(query.toString());
			return super.jdbcTemplate.query(query, new CategoriaRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Departamento> consultarDepartamento(){
		try{
			String query = "SELECT Depto, Nombre " +
					" FROM Checador.dbo.tblDepto " +
					" ORDER BY Nombre";
//			log.info(query.toString());
			return super.jdbcTemplate.query(query, new DepartamentoRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoChecada> consultarTipoChecada(){
		try{
			String query = "SELECT TipoChecada, Descripcion " +
					" FROM Checador.dbo.tblTipoChecada " +
					" WHERE TipoChecada NOT IN ('NO', 'E', 'S') " +
					" ORDER BY Descripcion";
			
//			log.info(query.toString());
			return super.jdbcTemplate.query(query, new TipoChecadaRowMapper());
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TipoIncidencia> consultarTipoIncidencia(){
		
		try{
			String query = "SELECT Concepto, Nombre FROM Checador.dbo.tblConcepto " + 
					" UNION " + 
					" SELECT 'FALTA' Concepto, 'FALTA' Nombre " + 
					" UNION " +
					" SELECT 'RETARDO' Concepto, 'RETARDO' Nombre " +
					" UNION " +
					" SELECT 'FUERA DE TIEMPO' Concepto, 'FUERA DE TIEMPO' Nombre " +
					" UNION " +
					" SELECT 'NINGUNA' Concepto, 'NINGUNA' Nombre " +
					" ORDER BY 2";
			//log.info(query.toString());
			
			return super.jdbcTemplate.query(query, new TipoIncidenciaRowMapper());
			
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Trabajador>consultarTrabajadorProquifa(){
		try{
			String query ="SELECT Trabajador, LTRIM(NombreCorto) NombreCorto " +
					" FROM Checador.dbo.tblTrabajador " +
					" WHERE Activo=1 " +
					" ORDER BY LTRIM(NombreCorto) ";
			
			//log.info(query.toString());
			return super.jdbcTemplate.query(query, new TrbajadorRowMapper());
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Trabajador>consultarTrabajadorRyndem(){
		try{
			StringBuilder sbQuery = new StringBuilder("SELECT Trabajador, LTRIM(NombreCorto) NombreCorto " +
					" FROM Checador.dbo.tblTrabajador " +
					" WHERE Activo=1 " +
					" ORDER BY LTRIM(NombreCorto)");
			Query query = em.createNativeQuery(sbQuery.toString());
			
			List<Trabajador> list = TrabajadorRowMapper2.mapearTrabajador(query.getResultList());
			return list;
		}catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	
}
