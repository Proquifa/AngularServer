/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.PartidaCompra;

/**
 * @author amartinez
 *
 */
public class PartidaReporteRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCompra partida = new PartidaCompra();
		partida.setCompra(rs.getString("compra"));
		partida.setCodigo(rs.getString("codigo"));
		partida.setFabrica(rs.getString("fabrica"));
		partida.setTipo(rs.getString("tipo"));
		try {partida.setOrigen(rs.getString("origen"));} catch (Exception e) {}
		return partida;
	}

}
