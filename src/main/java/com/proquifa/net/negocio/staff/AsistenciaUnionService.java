package com.proquifa.net.negocio.staff;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.staff.Asistencia;



public interface AsistenciaUnionService {

	//AsistenciaService: List<Asistencia>getconsultaAsistencia(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador)
	List<Asistencia>getconsultaAsistenciaProquifa(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador) throws ProquifaNetException;
	
	//AsistenciaRyndemService: List<Asistencia>getConsultaAsis(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador)
	List<Asistencia>getConsultaAsistenciaRyndem(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador) throws ProquifaNetException;
	
	List<Asistencia>getAsistenciaUnion(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String localidad, String nombreTrabajador) throws ProquifaNetException, ParseException;
	
}
