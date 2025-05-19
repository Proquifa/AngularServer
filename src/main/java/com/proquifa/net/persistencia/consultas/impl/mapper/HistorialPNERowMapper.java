/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.despachos.HistorialPNE;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class HistorialPNERowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		HistorialPNE historialPNE = new HistorialPNE();
		historialPNE.setFecha(rs.getTimestamp("Fecha"));
		historialPNE.setFolioRuta(rs.getString("FolioRuta"));
		historialPNE.setMensajero(rs.getString("Mensajero"));
		historialPNE.setRazones(rs.getString("Razones"));

		return historialPNE;
	}
}