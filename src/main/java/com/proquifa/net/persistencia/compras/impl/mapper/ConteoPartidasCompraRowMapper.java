/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.Compra;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class ConteoPartidasCompraRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Compra datosCompra = new Compra();
		datosCompra.setTotalPiezas(rs.getInt("TotalPartidas"));
		datosCompra.setTotalPiezasMexico(rs.getInt("TotalPartidaMexico"));
		datosCompra.setTotalCanceladas(rs.getInt("Canceladas"));
		datosCompra.setTotalRecibidas(rs.getInt("Recibidas"));
		datosCompra.setTotalRecibidasPHS(rs.getInt("RecibidasPHS"));

		return datosCompra;
	}
}