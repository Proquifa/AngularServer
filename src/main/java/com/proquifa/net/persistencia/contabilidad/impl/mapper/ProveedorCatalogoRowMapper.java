package com.proquifa.net.persistencia.contabilidad.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.CatalogoItem;

@SuppressWarnings("rawtypes")
public class ProveedorCatalogoRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		CatalogoItem item = new CatalogoItem();
		item.setLlave(rs.getLong("llave"));
		item.setNombre(rs.getString("nombre"));
		item.setValor(rs.getString("claveCC"));
		String pais = rs.getString("cliPais");
		boolean activo = (pais != null) ? pais.equals("MEXICO") : false;
		item.setActivo( activo );
		return item;
	}

}
