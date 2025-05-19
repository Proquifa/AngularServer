/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.tableros.cliente.ResumenVentasESAC;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author bryan.magana
 *
 */

public class ObjetivoCrecimientoporCarteraRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ResumenVentasESAC resumen = new ResumenVentasESAC ();
		resumen.setNombreCartera(rs.getString("Nombre"));
		resumen.setIdcartera(rs.getLong("idCartera"));
		resumen.setMontoVentaAnterior(rs.getDouble("MontoVentaAnt"));
		resumen.setObjetivoFundamental(rs.getDouble("objFundamental"));
		resumen.setObjetivoDeseado(rs.getDouble("objDeseado"));
		resumen.setNivelIngreso(rs.getString("NivelIngreso"));

		return resumen;
	}

}