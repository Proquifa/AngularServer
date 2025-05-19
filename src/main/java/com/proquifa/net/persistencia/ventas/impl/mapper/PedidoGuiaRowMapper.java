package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.PartidaPedido;

import org.springframework.jdbc.core.RowMapper;

public class PedidoGuiaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		PartidaPedido ppedido = new PartidaPedido();
		//ppedido.setFRecibido(rs.getDate("FFIN"));
		ppedido.setFRealizacion(rs.getDate("FFIN"));
		ppedido.setGuia(rs.getString("Guia"));
		ppedido.setIdGuia(rs.getString("idGuia"));
		ppedido.setIdEntrega(rs.getString("idEntrega"));
		ppedido.setIdRuta(rs.getString("idRuta"));
		ppedido.setRuta(rs.getString("Ruta"));
		ppedido.setRutaFFin(rs.getDate("rutaFFin"));
		ppedido.setConforme(rs.getString("Conforme"));
		ppedido.setDc(rs.getString("FolioDoctos"));
		return ppedido;
	}
}
