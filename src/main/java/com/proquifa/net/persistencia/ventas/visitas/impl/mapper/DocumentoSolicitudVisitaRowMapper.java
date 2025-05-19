package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;

import org.springframework.jdbc.core.RowMapper;

public class DocumentoSolicitudVisitaRowMapper implements RowMapper{

	boolean documentos;
	public DocumentoSolicitudVisitaRowMapper(boolean documentos) {
		super();
		this.documentos = documentos;
	}
	
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		if(documentos){
			DocumentoSolicitudVisita documento = new DocumentoSolicitudVisita();
			documento.setDescripcion(rs.getString("Descripcion"));
			documento.setTitulo(rs.getString("Titulo"));
			documento.setNombre(rs.getString("Nombre"));
			documento.setIdDocumentoSolicitudVisita(rs.getInt("PK_SolicitudVisitaDocumentos"));
			documento.setIdSolicitudVisita(rs.getInt("FK01_SolicitudVisita"));
			return documento;
		}else{
			SolicitudVisita solicitud = new SolicitudVisita();
			solicitud.setIdSolicitudVisita(rs.getLong("PK_SolicitudVisita"));
			solicitud.setMotivo(rs.getString("Motivo"));
			solicitud.setAsunto(rs.getString("Asunto"));
			solicitud.setSolicito(rs.getString("Nombre"));
			solicitud.setTipoSolicitud(rs.getString("tipo"));
			solicitud.setFechaSolicitud(rs.getDate("FRequerida"));
			solicitud.setIdContacto(rs.getLong("FK02_Contacto"));
			solicitud.setIdCliente(rs.getLong("FK01_Cliente"));
			solicitud.setIdEmpleado(rs.getLong("FK03_Empleado"));
			
			return solicitud;
		}
	}

}
