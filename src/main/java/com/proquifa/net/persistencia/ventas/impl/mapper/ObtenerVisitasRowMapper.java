package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.ventas.AccionesPendientes;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.Pedido;
import com.proquifa.net.modelo.ventas.visitas.ObtenerVisitas;
import com.proquifa.net.modelo.ventas.visitas.Visita;

public class ObtenerVisitasRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ObtenerVisitas visita = new ObtenerVisitas();
		visita.setPk_Formulario(rs.getInt("PK_Formulario"));
		visita.setFecha_Visita(rs.getDate("Fecha_Visita"));
		visita.setContacto(rs.getString("Contacto"));
		visita.setFolio(rs.getString("Folio"));
		visita.setIdCliente(rs.getInt("FK02_Cliente"));
		visita.setFechaEstimada(rs.getDate("FechaEstimada"));
		visita.setPuesto(rs.getString("Puesto"));
		visita.setAreaContacto(rs.getString("Area_Contacto"));
		visita.setRuta(rs.getString("Ruta"));
		visita.setAsunto(rs.getString("Asunto"));
		visita.setMotivo(rs.getString("Motivo"));
		visita.setNombre(rs.getString("Nombre"));
		visita.seteMail(rs.getString("eMail"));
		visita.setTelefono1(rs.getString("Tel1"));
		visita.setExtension1(rs.getString("Extension1"));
		visita.setTelefono1(rs.getString("Tel2"));
		visita.setExtension1(rs.getString("Extension2"));
		visita.setNombresDocs(rs.getString("NombresDocs"));
		return visita;
	}
}
