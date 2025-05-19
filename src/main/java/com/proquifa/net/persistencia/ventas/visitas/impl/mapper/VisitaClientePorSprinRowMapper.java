/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

/**
 * @author yosimar.mendez
 *
 */
public class VisitaClientePorSprinRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		VisitaCliente visita = new VisitaCliente();
		visita.setIdVisitaCliente(rs.getLong("idVisita"));
		visita.setFechaEstimadaVisita(rs.getDate("FechaEstimada"));
		visita.setAsunto(rs.getString("Asunto"));
		visita.setTipoVisita(rs.getString("Tipo_Visita"));
		visita.setNumDocumentos(rs.getInt("NoDocumentos"));
		visita.setEstado(rs.getString("EstadoVisita"));
		visita.setNotas(rs.getString("Notas"));
		
		Funcion function = new Funcion();
		SolicitudVisita solicitud = new SolicitudVisita();
		solicitud.setIdSolicitudVisita(rs.getLong("PK_SolicitudVisita"));
		try {
			String ruta = function.obtenerRutaServidor("requerimientos", "");
			if (new File(ruta + solicitud.getIdSolicitudVisita().toString() + ".pdf").exists()) {
				solicitud.setDocumento(new DocumentoSolicitudVisita("requerimientos.pdf"));
			}
		} catch (ProquifaNetException e) {
			e.printStackTrace();
		}
		visita.setSolicitud(solicitud);
		
		Sprint sprint = new Sprint();
		sprint.setNumeroSprint(rs.getInt("NumeroSprint"));
		sprint.setFechaFin(rs.getDate("FFin"));
		sprint.setFechaInicio(rs.getDate("FInicio"));
		visita.setSprint(sprint);
		
		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("idCliente"));
		cliente.setNombre(rs.getString("Cliente"));
		cliente.setEstado(rs.getString("Estado"));
		cliente.setNivelIngreso(rs.getString("Nivel"));
		cliente.setRuta(rs.getString("ruta"));
		visita.setCliente(cliente);
		
		Contacto contacto = new Contacto();
		contacto.setIdContacto(rs.getLong("idContacto"));
		contacto.setNombre(rs.getString("Contacto"));
		contacto.setTelefono(rs.getString("Tel1"));
		contacto.setExtension1(rs.getString("Extension1"));
		contacto.setEMail(rs.getString("eMail"));
		contacto.setIdContacto(rs.getLong("idContacto"));
		visita.setContacto(contacto);
		
		return visita;
	}

}
