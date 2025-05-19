package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

import org.springframework.jdbc.core.RowMapper;

public class ResumenVisitaRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		VisitaCliente vc = new VisitaCliente();
		vc.setIdVisitaCliente(rs.getLong("PK_Formulario"));
		vc.setFechaEstimadaVisita(rs.getDate("FechaEstimada"));
		vc.setFechaVisita(rs.getDate("Fecha_Visita"));
		vc.setHoraVisitaInicio(rs.getTime("Hora_Visita"));
		vc.setHoraVisitaFin(rs.getTime("Hora_Visita_Fin"));
		vc.setIdCliente(rs.getLong("FK01_Cliente"));
		vc.setIdContacto(rs.getLong("FK02_Contacto"));
		vc.setIdEmpleado(rs.getLong("FK03_Empleado"));
		vc.setTipo(rs.getString("Tipo"));
		vc.setAsunto(rs.getString("Asunto"));
		vc.setNumDocumentos(rs.getInt("Documentos"));
		vc.setCalificacionEV(rs.getInt("CalificacionEV"));
		vc.setFechaCheckIn(rs.getDate("Fecha_CheckIn"));
		vc.setCredito(rs.getFloat("Creditos"));
		vc.setValor(rs.getFloat("Valor"));
		vc.setReporte(rs.getString("Reporte"));
		vc.setRealizacionVisita(rs.getBoolean("VisitaRealizada"));
		vc.setJustificacionCancelacion(rs.getString("JustificacionCancelacion"));
		vc.setTipoCancelacion(rs.getString("TipoCancelacion"));
		
		vc.setNombreCliente(rs.getString("NombreCliente"));
		vc.setNivelIngreso(rs.getString("Clasificacion_Cliente"));
		vc.setRuta(rs.getString("Ruta"));
		vc.setEstado(rs.getString("Estado"));
		vc.setTipoVisita(rs.getString("Tipo_Visita"));
		vc.setNoHallazgos(rs.getInt("noHallazgos"));
		vc.setNoHallazgosCompletados(rs.getInt("noHallazgosCompletados"));
		
		Empleado emp = new Empleado();
		emp.setUsuario(rs.getString("Usuario"));
		emp.setNombre(rs.getString("nombreUsuario"));
		emp.setIdEmpleado(rs.getLong("FK03_Empleado"));
		emp.setNombreESAC(rs.getString("NombreESAC"));
		emp.setUsuarioESAC(rs.getString("UsuarioESAC"));
		emp.setArea(rs.getString("Area"));
		emp.setPuesto(rs.getString("Puesto"));
		vc.setEmpleado(emp);
		
		Sprint sprint = new Sprint();
		sprint.setIdSprint(rs.getLong("PK_Sprint"));
		sprint.setAnio(rs.getInt("Anio"));
		sprint.setFechaInicio(rs.getDate("FInicio"));
		sprint.setFechaFin(rs.getDate("FFin"));
		sprint.setNumeroSprint(rs.getInt("NumeroSprint"));
		vc.setSprint(sprint);
		
		Contacto contacto = new Contacto();
		contacto.setNombre(rs.getString("Contacto"));
		contacto.setTelefono(rs.getString("Tel1"));
		contacto.setPuesto(rs.getString("Puesto"));
		contacto.setDepartamento(rs.getString("Depto"));
		contacto.setEMail(rs.getString("eMail"));
		contacto.setIdContacto(rs.getLong("FK02_Contacto"));
		vc.setContacto(contacto);
		
		
		Cliente ClienteV=new Cliente();
		ClienteV.setIdCliente(rs.getLong("FK01_Cliente"));
		ClienteV.setNombre(rs.getString("NombreCliente"));
		ClienteV.setNivelIngreso(rs.getString("Clasificacion_Cliente"));
		vc.setCliente(ClienteV);
		
		vc.setIdSprintAntiguo(rs.getLong("FK04_SprintAntiguo"));
		
		return vc;
	}
}
