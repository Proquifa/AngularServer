/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author bryan.magana
 *
 */
public class InfoPartidasCampanaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaFactura partida = new PartidaFactura();
		//pcotizas.Cant , pcotizas.Costo ,idProducto , pcotizas.idPCotiza, pcotiza.part, Cantidad,Unidad,Tipo,Productos.Concepto, Productos.Codigo,Productos.Fabrica,Pureza,conceptoFull
		partida.setCantidad(rs.getInt("Cant"));
		partida.setConceptoPartida(rs.getString("conceptoFull"));
		partida.setImporte(rs.getDouble("Precio"));
		partida.setPartidaFactura(rs.getInt("partida"));
		partida.setIdPFactura(rs.getLong("idproducto")); // se guarda el idproducto en el idfactura para reutilizar variables existentes 
		return partida;
	}

}
