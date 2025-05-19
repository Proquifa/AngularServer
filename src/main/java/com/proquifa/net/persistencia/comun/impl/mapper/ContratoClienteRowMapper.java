package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Contrato;

public class ContratoClienteRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Contrato con = new Contrato();
		
		con.setIdContrato(rs.getLong("PK_Contrato"));
		con.setIdCliente(rs.getLong("FK01_Cliente"));
		con.setFechaFin(rs.getDate("FechaFin"));
		con.setFechaInicio(rs.getDate("FechaInicio"));
		con.setFuaContrato(rs.getDate("UltimaActualizacion"));
		con.setCondionesPago(rs.getString("Condicion_Pago"));
		con.setFolio(rs.getString("Folio"));
		con.setGenerado(rs.getBoolean("Generado"));
		con.setFinalizado(rs.getBoolean("Finalizado"));
		
		return con;
	}

}
