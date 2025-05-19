/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

/**
 * @author jonathan.monroy
 *
 */
public class ObtenerDatosSeccionReporteRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		VisitaCliente visita = new VisitaCliente();
	
		SolicitudVisita solicitud = new SolicitudVisita();
		solicitud.setCalificacion(rs.getInt("Calificacion"));
		solicitud.setReqRealizados(rs.getInt("ReqRealizados"));
		solicitud.setReqTotales(rs.getInt("TotalReq"));
		solicitud.setNoPendientes(rs.getInt("No_Pendientes"));
		solicitud.setNoHallazgos(rs.getInt("No_Hallazgos"));
		solicitud.setNoRequisiciones(rs.getInt("No_Requisiciones"));
		visita.setSolicitudesVisita(new ArrayList<SolicitudVisita>());
		
		visita.getSolicitudesVisita().add(solicitud);
		
		return visita;
	}

}
