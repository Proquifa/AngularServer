package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;

import org.springframework.jdbc.core.RowMapper;

public class PoolVisitasRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		SolicitudVisita SolicitudV=new SolicitudVisita();
		
		SolicitudV.setIdSolicitudVisita(rs.getLong("PK_SolicitudVisita"));
		SolicitudV.setFechaSolicitud(rs.getTimestamp("Fecha"));
		SolicitudV.setFechaDeseadaRealizacion(rs.getDate("FRequerida"));
		SolicitudV.setAsunto(rs.getString("Asunto"));
		SolicitudV.setMotivo(rs.getString("Motivo"));
		SolicitudV.setNumDocumentos(rs.getInt("numDocumentos"));
		SolicitudV.setSolicito(rs.getString("Solicito"));
		SolicitudV.setIdCliente(rs.getLong("FK01_Cliente"));
		SolicitudV.setIdContacto(rs.getLong("FK02_Contacto"));
		SolicitudV.setIdEmpleado(rs.getLong("FK03_Empleado"));
		SolicitudV.setTipoSolicitud(rs.getString("Tipo"));
		
		//-Cliente
		Cliente ClienteV=new Cliente();
		ClienteV.setIdCliente(SolicitudV.getIdCliente());
		ClienteV.setNombre(rs.getString("Nombre"));
		ClienteV.setPais(rs.getString("Pais"));
		ClienteV.setRuta(rs.getString("Ruta"));
		SolicitudV.setNombreCliente(ClienteV.getNombre());
		SolicitudV.setCliente(ClienteV);


		//-Contacto
		Contacto ContactoV=new Contacto();
		ContactoV.setIdContacto(SolicitudV.getIdContacto());
		ContactoV.setNombre(rs.getString("Contacto"));

		ContactoV.setTitulo(rs.getString("Titulo"));
		ContactoV.setPuesto(rs.getString("Puesto"));
		ContactoV.setDepartamento(rs.getString("Depto"));
		ContactoV.setEMail(rs.getString("eMail"));
		ContactoV.setTelefono(rs.getString("Tel1"));
		ContactoV.setCelular(rs.getString("Celular"));
		ContactoV.setExtension1(rs.getString("Extension1"));
//		nivelD=rs.getString("NDecisionContacto");
		SolicitudV.setContacto(ContactoV);

		return SolicitudV;
	}

}

