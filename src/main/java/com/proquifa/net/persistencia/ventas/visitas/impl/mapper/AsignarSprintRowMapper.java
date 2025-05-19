package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

import org.springframework.jdbc.core.RowMapper;

public class AsignarSprintRowMapper implements RowMapper{

	Boolean fechaVisita = false;
	public AsignarSprintRowMapper(Boolean fechaVisita) {
		super();
		this.fechaVisita = fechaVisita;
	}
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		VisitaCliente vc = new VisitaCliente();
		vc.setIdVisitaCliente(rs.getLong("PK_Formulario"));
		vc.setTipo(rs.getString("Tipo"));
		vc.setNivelIngreso(rs.getString("Clasificacion_Cliente"));
		vc.setRuta(rs.getString("Ruta"));
		vc.setFechaEstimadaVisita(rs.getDate("FechaEstimada"));
		vc.setFechaE(rs.getDate("FechaEstimada"));
		vc.setAsunto(rs.getString("Asunto"));
		vc.setNumDocumentos(rs.getInt("Documentos"));
		vc.setNombreCliente(rs.getString("NombreCliente"));
		vc.setIdCliente(rs.getInt("idCliente"));
		vc.setIdEmpleado(rs.getLong("FK03_Empleado"));
		vc.setCredito(rs.getFloat("Creditos"));
		vc.setEstado(rs.getString("Estado"));
		vc.setValor(rs.getFloat("Valor"));
		vc.setFechaCheckIn(rs.getDate("Fecha_CheckIn"));
		vc.setReporte(rs.getString("Reporte"));
		
		Contacto contacto = new Contacto();
		contacto.setNombre(rs.getString("Contacto"));
		contacto.setTelefono(rs.getString("Tel1"));
		contacto.setPuesto(rs.getString("Puesto"));
		contacto.setDepartamento(rs.getString("Depto"));
		contacto.setEMail(rs.getString("eMail"));
		
		
		Empleado empleado = new Empleado();
		empleado.setUsuario(rs.getString("Usuario"));
		empleado.setNombre(rs.getString("Nombre"));
		empleado.setIdEmpleado(rs.getLong("FK03_Empleado"));
		vc.setEmpleado(empleado);
		
		Sprint sprint = new Sprint();
		sprint.setIdSprint(rs.getLong("PK_Sprint"));
		sprint.setAnio(rs.getInt("Anio"));
		sprint.setFechaInicio(rs.getDate("FInicio"));
		sprint.setFechaFin(rs.getDate("FFin"));
		sprint.setNumeroSprint(rs.getInt("NumeroSprint"));
		vc.setSprint(sprint);
		if(fechaVisita){
			vc.setFechaEstimadaVisita(rs.getDate("Fecha_Visita"));
			vc.setFechaVisita(rs.getDate("Fecha_Visita"));
			vc.setHoraVisitaInicio(rs.getTime("Hora_Visita"));
			vc.setHoraVisitaFin(rs.getTime("Hora_Visita_Fin"));
			vc.setIdContacto(rs.getLong("idContacto"));
			contacto.setIdContacto(rs.getLong("idContacto"));
		}
		vc.setContacto(contacto);
		
		//Direccion del cliente de la tabla DIRECCION
		Direccion dir = new Direccion();
		try {
			dir.setIdDireccion(rs.getLong("PK_Direccion"));
			dir.setLatitud(rs.getDouble("dLatitud"));
			dir.setLongitud(rs.getDouble("dLongitud"));
			dir.setTipo(rs.getString("tipo"));
			dir.setPais(rs.getString("dPais"));
			dir.setEstado(rs.getString("dEstado"));
			dir.setCalle(rs.getString("dCalle"));
			dir.setMunicipio(rs.getString("dMunicipio"));
			dir.setCodigoPostal(rs.getString("dCP"));
			dir.setRuta(rs.getString("dRuta"));
			dir.setZona(rs.getString("dZona"));
			dir.setCiudad(rs.getString("dCiudad"));
			dir.setColonia(rs.getString("dColonia"));
			dir.setNumExt(rs.getString("dExt"));
			dir.setNumInt(rs.getString("dInt"));
			dir.setRegion(rs.getString("dRegion"));
			dir.setTipoRegion(rs.getString("Tipo_Region"));
			dir.setAltitud(rs.getDouble("dAltitud"));
			dir.setIdCliente(rs.getInt("idCliente"));
		} catch (Exception e) {
		}
		
		Cliente cliente = new Cliente();
		cliente.setDireccion(dir);
		vc.setCliente(cliente);
		
		return vc;
	}

}
