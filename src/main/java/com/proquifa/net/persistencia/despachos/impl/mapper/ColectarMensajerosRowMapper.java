package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.ColectarMensajero;

public class ColectarMensajerosRowMapper  implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ColectarMensajero colectar = new  ColectarMensajero();
		colectar.setTotalPL(rs.getInt("numPKL"));
		colectar.setTotalPR(rs.getInt("numPR"));
		colectar.setTotalES(rs.getInt("numES"));
		colectar.setTotalPC(rs.getInt("numPC"));
		colectar.setTotalRM(rs.getInt("numRM"));
		colectar.setCalle(rs.getString("calle"));
		colectar.setDelegacion(rs.getString("Delegacion"));
		colectar.setCp(rs.getString("CP"));
		return colectar;
	}
	
}
