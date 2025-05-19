package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.CatalogoItem;

public class UsoCfdiFacturaRowMapper implements RowMapper {

	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		CatalogoItem catalogoItem = new CatalogoItem();
		catalogoItem.setValor(rs.getString("valor").toString());
		return catalogoItem;
	}
}
