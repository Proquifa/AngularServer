package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cliente;

public class ClientesVentasPorMesRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		//----------------------------------------------------------------------
		//--Este Rowmaper debe ser unicamente usado por metodo
		//--'getVentasPorMesesCliente' si lo usas para otra consulta
		//--estaras destinado a fracazar
		//-----------------------------------------------------------------------
		
		Cliente cliente = new Cliente();
		cliente.setIdCliente(rs.getLong("Clave"));
		cliente.setNombre(rs.getString("Nombre"));
		cliente.setMonto(rs.getDouble("Monto"));
		cliente.setCantidad(rs.getInt("Mes"));
		cliente.setDificultad(rs.getInt("Anio"));
		cliente.setCorporativo(rs.getLong("Partidas"));
		cliente.setDificultad(rs.getInt("Piezas"));
		cliente.setComentarios(rs.getString("ETIQUETA"));
		
		return cliente;
	}

}
