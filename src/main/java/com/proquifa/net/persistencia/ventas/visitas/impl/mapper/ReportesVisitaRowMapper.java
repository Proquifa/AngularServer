package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

import org.springframework.jdbc.core.RowMapper;

public class ReportesVisitaRowMapper implements RowMapper{

	boolean total;
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		VisitaCliente visitaCliente = new VisitaCliente();
		Contacto contacto;
		Empleado empleado;
		if(this.total){
			visitaCliente.setIdCliente(rs.getLong("FK01_Cliente"));
			visitaCliente.setNombreCliente(rs.getString("Nombre"));
			visitaCliente.setNumSolicitud(rs.getInt("NumSolicitud"));
			visitaCliente.setNumCRM(rs.getInt("NumCRM"));
		}
		else{
			contacto = new Contacto();
			empleado = new Empleado();
			
			visitaCliente.setIdCliente(rs.getLong("FK01_Cliente"));
			visitaCliente.setNombreCliente(rs.getString("Nombre"));
			visitaCliente.setRuta(rs.getString("Ruta"));
			visitaCliente.setNumDocumentos(rs.getInt("Documentos"));
			visitaCliente.setNumSolicitud(rs.getInt("NumSolicitud"));
			visitaCliente.setNumCRM(rs.getInt("NumCRM"));
			visitaCliente.setNivelIngreso(rs.getString("Clasificacion_Cliente"));
			visitaCliente.setFechaEstimadaVisita(rs.getDate("FechaEstimada"));
			visitaCliente.setIdVisitaCliente(rs.getLong("PK_Formulario"));
			visitaCliente.setAsunto(rs.getString("Asunto"));
			visitaCliente.setEstado(rs.getString("Estado"));
			visitaCliente.setIdSprint(rs.getLong("SprintAsignado"));
			 visitaCliente.setTipo(rs.getString("Tipo"));
			
			contacto.setNombre(rs.getString("Contacto"));
			contacto.setApellidos(rs.getString("Apellidos"));
			contacto.setTelefono(rs.getString("Tel1"));
			contacto.setEMail(rs.getString("eMail"));
			contacto.setPuesto(rs.getString("Puesto"));
			contacto.setDepartamento(rs.getString("Depto"));
			
			empleado.setUsuario(rs.getString("Usuario"));
			empleado.setNombre(rs.getString("nombreUsuario"));
			visitaCliente.setEmpleado(empleado);
			
			visitaCliente.setContacto(contacto);
			

			if(rs.getString("Reporte") != null){
				visitaCliente.setReporte(rs.getString("Reporte"));
			}
			if(rs.getDate("Fecha_Visita") != null){
				visitaCliente.setFechaVisita(rs.getDate("Fecha_Visita"));
			}
		}
		
		return visitaCliente;
	}
	
	public ReportesVisitaRowMapper(boolean total) {
		super();
		this.total = total;
	}
}
