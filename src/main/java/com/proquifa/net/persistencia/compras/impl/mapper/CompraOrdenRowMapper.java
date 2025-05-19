
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.PartidaCompra;

public class CompraOrdenRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		PartidaCompra pcompra = new PartidaCompra();
		pcompra.setPartida(rs.getInt("partida"));
		pcompra.setCompra(rs.getString("compra"));
		pcompra.setLote(rs.getString("lote"));
		pcompra.setPedimento(rs.getString("pedimento"));
		pcompra.setIdPartidaCompra(rs.getLong("idPCompra"));
		pcompra.setCodigo(rs.getString("codigo"));
		return pcompra;
	}

}
