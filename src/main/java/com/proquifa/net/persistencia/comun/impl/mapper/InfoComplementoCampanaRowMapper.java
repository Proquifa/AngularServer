/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;

/**
 * @author bryan.magana
 *
 */
public class InfoComplementoCampanaRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaFactura partida = new PartidaFactura();
		partida.setConceptoPartida(rs.getString("concepto"));
		partida.setIdPFactura(rs.getLong("idproducto")); // se guarda el idproducto en el idfactura para reutilizar variables existentes 
		return partida;
	}
}
