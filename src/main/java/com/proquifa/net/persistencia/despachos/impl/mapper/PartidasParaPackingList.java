package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import org.springframework.jdbc.core.RowMapper;

public class PartidasParaPackingList implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		PartidaInspeccion part = new PartidaInspeccion();
		part.setCodigo(rs.getString("Codigo"));
		part.setDescripcion(rs.getString("Concepto"));
		part.setFolioPaquete(rs.getString("Folio_bolsa"));
		part.setFactura(rs.getString("Factura"));
		part.setCpedido(rs.getString("CPedido"));
		part.setManejo(rs.getString("Manejo"));
		part.setIdPPedido(rs.getLong("idPPedido"));
		part.setIdOrdenEntrega(rs.getLong("idOrdenEntrega"));
		part.setCant(rs.getInt("Cant"));
		part.setPedido(rs.getString("Pedido"));
		return part;
	}

}
