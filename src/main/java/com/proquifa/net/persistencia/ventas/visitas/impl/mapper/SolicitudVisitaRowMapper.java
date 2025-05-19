/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author jmcamanos
 *
 */
public class SolicitudVisitaRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		//SolicitudVisita
		SolicitudVisita SolicitudV=new SolicitudVisita();	
		SolicitudV.setIdSolicitudVisita(rs.getLong("PK_SolicitudVisita"));
		SolicitudV.setUrgencia(rs.getString("Urgencia"));
		SolicitudV.setFechaDeseadaRealizacion(rs.getDate("FechaDeseadaRealizacion"));
		SolicitudV.setTiempoRealizacion(rs.getString("TiempoRealizacion"));
		SolicitudV.setFechaSolicitud(rs.getTimestamp("Fecha"));
		SolicitudV.setFolio(rs.getString("Folio"));
		SolicitudV.setJustificacion(rs.getString("Justificacion"));
		SolicitudV.setAsunto(rs.getString("Asunto"));
		SolicitudV.setArgumento(rs.getString("Argumento"));
		SolicitudV.setDiasAtraso(rs.getString("DiasAtraso"));
		
		SolicitudV.setExisteReferencia(rs.getBoolean("existeReferencia"));
		//-Cliente
		Cliente ClienteV=new Cliente();
		ClienteV.setIdCliente(rs.getLong("FK01_Cliente"));
		ClienteV.setNombre(rs.getString("NombreCliente"));
		ClienteV.setPais(rs.getString("PaisCliente"));
		ClienteV.setRuta(rs.getString("RutaCliente"));
		ClienteV.setIndustria(rs.getString("IndustriaCliente"));
		ClienteV.setRol(rs.getString("RolCliente"));
		ClienteV.setSector(rs.getString("SectorCliente"));
		ClienteV.setNivelIngreso(rs.getString("NivelIngresoCliente"));
		SolicitudV.setCliente(ClienteV);

		//-Contacto
		Contacto ContactoV=new Contacto();
		String titulo="",puesto="",depto="",correo="",nivelD="";
		ContactoV.setIdContacto(rs.getLong("FK02_Contacto"));
		ContactoV.setNombre(rs.getString("NombreContacto"));
		
		titulo=rs.getString("TituloContacto");
		puesto=rs.getString("PuestoContacto");
		depto=rs.getString("DepartamentoContacto");
		correo=rs.getString("eMailContacto");
		nivelD=rs.getString("NDecisionContacto");

		if(titulo==null || titulo.equals("")){ContactoV.setTitulo("ND");}else{ContactoV.setTitulo(titulo);}
		if(puesto==null || puesto.equals("")){ContactoV.setPuesto("ND");}else{ContactoV.setPuesto(puesto);}
		if(depto==null || depto.equals("")){ContactoV.setDepartamento("ND");}else{ContactoV.setDepartamento(depto);}
		if(correo==null || correo.equals("")){ContactoV.setEMail("ND");}else{ContactoV.setEMail(correo);}
		if(nivelD==null || nivelD.equals("")){ContactoV.setNivelDecision("ND");}else{ContactoV.setNivelDecision(nivelD);}

			//--Direccion
			Direccion DireccionV=new Direccion();
			DireccionV.setCalle(rs.getString("CalleDireccion"));
			DireccionV.setMunicipio(rs.getString("MunicipioDireccion"));
			DireccionV.setPais(rs.getString("PaisDireccion"));
			DireccionV.setEstado(rs.getString("EstadoDireccion"));
			DireccionV.setCodigoPostal(rs.getString("CPDireccion"));
			DireccionV.setZonaMensajeria(rs.getString("ZonaDireccion"));
			ContactoV.setDireccion(DireccionV);
		SolicitudV.setContacto(ContactoV);
		//-Empleado
		Empleado empleadoV=new Empleado();
		empleadoV.setIdEmpleado(rs.getLong("FK03_Empleado"));
		empleadoV.setNombre(rs.getString("NombreEmpleado"));
		empleadoV.setDepartamento(rs.getString("DeptoEmpleado"));
		SolicitudV.setEmpleado(empleadoV);
		
		//-Visita
		SolicitudV.setFolioVisita(rs.getString("FolioVisita"));
		SolicitudV.setFolioVisitaOrigen(rs.getString("FolioVisitaOrigen"));
		SolicitudV.setFechaEstimadaRealizacionVisita(rs.getTimestamp("FechaEstRealizacionVisita"));
		SolicitudV.setFechaUltimaActualizacionVisita(rs.getTimestamp("FechaUltimaActualizacionVisita"));
		return SolicitudV;
	}
}