package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.ConfirmacionEntrega;

public class ConfirmacionEntregaResumenRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfirmacionEntrega c = new ConfirmacionEntrega();
		// TODO Auto-generated method stub

		c.setCantidad(rs.getInt("cantidad"));
		c.setProducto(rs.getString("Fabrica"));
		
		return c;
	}

}
