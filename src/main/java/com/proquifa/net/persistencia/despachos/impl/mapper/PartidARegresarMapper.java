package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.despachos.PartidaInspeccion;
import org.springframework.jdbc.core.RowMapper;

public class PartidARegresarMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaInspeccion part = new PartidaInspeccion();
		part.setIdPartidaCompra(rs.getLong("idPCompra"));
		part.setIdPPedido(rs.getLong("idPPedido"));
		part.setEdoPrevioPP(rs.getString("EstadoPrevioPPedido"));
		part.setEdoPrevioPC(rs.getString("EstadoPrevioPCompra"));
		part.setInspector(rs.getString("FuncionUsuario"));
		return part;
	}

}
