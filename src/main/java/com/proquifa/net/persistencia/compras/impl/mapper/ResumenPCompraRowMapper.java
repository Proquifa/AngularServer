package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.ResumenPCompra;

public class ResumenPCompraRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		ResumenPCompra rpc = new ResumenPCompra();
		rpc.setNum(rs.getString("num"));
		rpc.setCantidad(rs.getString("cantidad"));
		rpc.setCodigo(rs.getString("codigo"));
		rpc.setPrecio(rs.getString("precio"));
		rpc.setImporte(rs.getString("importe"));
        rpc.setCodigoProducto(rs.getString("CodigoProducto"));		

		return rpc;
	}

}
