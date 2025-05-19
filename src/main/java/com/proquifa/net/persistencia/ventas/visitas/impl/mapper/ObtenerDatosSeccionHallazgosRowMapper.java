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
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;

/**
 * @author jonathan.monroy
 *
 */
public class ObtenerDatosSeccionHallazgosRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		HallazgosAcciones hallazgos = new HallazgosAcciones();
		
		hallazgos.setIdHallazgoAccion(rs.getInt("PK_HallazgosAcciones"));
		hallazgos.setDescripcion(rs.getString("Descripcion"));
		hallazgos.setFerealizacion(rs.getDate("FERealizacion"));
		hallazgos.setDescripcionAccion(rs.getString("Desc_Accion"));
		hallazgos.setUsuarioAsociado(rs.getInt("FK05_EV_Asignado"));
		hallazgos.setCheckSeleccionado(rs.getBoolean("Descartado"));
		hallazgos.setMotivoDescartado(rs.getString("MotivoDescartado"));
		
		return hallazgos;
	}

}
