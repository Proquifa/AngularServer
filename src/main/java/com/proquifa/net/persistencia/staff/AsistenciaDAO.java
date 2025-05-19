package com.proquifa.net.persistencia.staff;

import java.util.Date;
import java.util.List;

import com.proquifa.net.modelo.staff.Area;
import com.proquifa.net.modelo.staff.Asistencia;
import com.proquifa.net.modelo.staff.CambioTurno;
import com.proquifa.net.modelo.staff.Categoria;
import com.proquifa.net.modelo.staff.Departamento;
import com.proquifa.net.modelo.staff.TipoChecada;
import com.proquifa.net.modelo.staff.TipoIncidencia;
import com.proquifa.net.modelo.staff.Trabajador;
import com.proquifa.net.modelo.staff.Turno;



public interface AsistenciaDAO {

	public List<Area> consultarArea();
	
	public List<Asistencia> consultarAsistenciaProquifa(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador);
	
	public List<Asistencia> consultarAsistenciaRyndem(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador);
	
	public List<Asistencia>consultarAsistenciaGDL(Date fechaInicio, Date fechaFin, Long idTrabajador, String tipoChecada, String area, String depto, String categoria, String incidencia, String nombreTrabajador);
	
	public List<CambioTurno> consultarCambioTurnoProquifa(Date fechaInicio, Date fechaFin);
	
	public List<Turno>consultarTurnoProquifa();
	
	public List<CambioTurno>consultarCambioTurnoRyndem(Date fechaInicio, Date fechaFin);
	
	public List<CambioTurno>consultarCambioTurnoGDL(Date fechaInicio, Date fechaFin);
	
	public List<Turno>consultarTurnoRyndem();
	
	public List<Turno>consultarTurnoGDL();
	
	public List<Categoria> consultarCategoria();
	
	public List<Departamento> consultarDepartamento();
	
	public List<TipoChecada> consultarTipoChecada();
	
	public List<TipoIncidencia> consultarTipoIncidencia();
	
	public List<Trabajador>consultarTrabajadorProquifa();
	
	public List<Trabajador>consultarTrabajadorRyndem();

	
}
