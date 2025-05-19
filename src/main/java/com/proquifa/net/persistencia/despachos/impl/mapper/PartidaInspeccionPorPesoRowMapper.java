package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.proquifa.net.modelo.despachos.InspeccionPorFolio;



public class PartidaInspeccionPorPesoRowMapper implements RowMapper{

	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		InspeccionPorFolio part = new InspeccionPorFolio();
		part.setPuntos(rs.getInt("sumaPuntos"));
		part.setCompra(rs.getString("compra"));
		part.setPedimento(rs.getString("NoPedimento"));
		return part;
	}

}