/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Pendiente;


/**
 * @author fmartinez
 *
 */
public class PendienteRowMapper implements RowMapper {
		
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Pendiente pendienteEncontrado = new Pendiente();
		pendienteEncontrado.setFolio(rs.getLong("folio"));
		pendienteEncontrado.setDocto(rs.getString("Docto"));
		pendienteEncontrado.setPartida(rs.getString("Partida"));
		pendienteEncontrado.setFechaInicio(rs.getTimestamp("FInicio"));
		pendienteEncontrado.setFechaAux(rs.getDate("FInicio"));
		pendienteEncontrado.setFechaFin(rs.getTimestamp("FFin"));
		pendienteEncontrado.setResponsable(rs.getString("Responsable"));
		pendienteEncontrado.setTipoPendiente(rs.getString("Tipo"));
		return pendienteEncontrado;
	}
}