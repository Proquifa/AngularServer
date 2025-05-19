/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;


import com.proquifa.net.modelo.compras.PartidaCompra;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *	Reporte Compras
 */
public class ObtenerPatidasCompraParaCEspecificaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCompra partidaCompra = new PartidaCompra();

	
		partidaCompra.setEstado(rs.getString("Estado"));		
		partidaCompra.setPartida(rs.getInt("Partida"));

		partidaCompra.setCantidad(rs.getString("Cantidad"));		
		partidaCompra.setTipo(rs.getString("TIPO"));
		partidaCompra.setDestino(rs.getString("CLIENTE"));
		partidaCompra.setFechaEstimadaEntrega(rs.getTimestamp("FEE"));	
		partidaCompra.setCodigo(rs.getString("Codigo"));
		partidaCompra.setIdComplemento(rs.getLong("idComplemento"));
		partidaCompra.setTotalPiezas(rs.getLong("PIEZAS"));
		
		//Float x = Double.valueOf( Math.rint((rs.getDouble("MONTO")) * 1e2) / 1e2).floatValue();
		
		
		partidaCompra.setCodigo(rs.getString("Codigo"));
		partidaCompra.setUnidad(rs.getString("Unidad"));
		partidaCompra.setConcepto(rs.getString("CONCEPTO"));
		partidaCompra.setFabrica(rs.getString("Fabrica"));
		partidaCompra.setPureza(rs.getLong("Pureza"));
		
		partidaCompra.setCosto( Math.rint((rs.getDouble("PRECIOUNITARIO")) * 1e2) / 1e2);
		
		partidaCompra.setMontoTotal(Math.rint((rs.getDouble("MONTO")) * 1e2) / 1e2);
		partidaCompra.setTipoFlete(rs.getString("FLETE"));
		partidaCompra.setAbierto(rs.getBoolean("ABIERTO"));
		partidaCompra.setMoneda(rs.getString("Moneda"));
		partidaCompra.setIdPartidaCompra(rs.getLong("idPCompra"));
		partidaCompra.setCompra(rs.getString("Compra"));
		partidaCompra.setPedido(rs.getString("CPEDIDO"));
		partidaCompra.setACambio(rs.getBoolean("AvisoDCambios"));
		try {
			partidaCompra.setFporCliente(rs.getString("fporCliente"));
		} catch (Exception e) {}
		
		
		
		
		return partidaCompra;
	}
}