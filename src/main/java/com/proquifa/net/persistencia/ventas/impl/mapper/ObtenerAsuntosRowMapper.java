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

public class ObtenerAsuntosRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ObtenerVisitas visita = new ObtenerVisitas();
		visita.setPk_Formulario(rs.getInt("PK_SolicitudVisita"));
		visita.setAsunto(rs.getString("Asunto"));
		visita.setMotivo(rs.getString("Motivo"));
		visita.setNombre(rs.getString("Nombre"));
		visita.setNombresDocs(rs.getString("NombresDocs"));
		return visita;
	}
}
