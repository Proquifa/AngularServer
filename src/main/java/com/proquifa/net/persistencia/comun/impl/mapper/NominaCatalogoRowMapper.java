package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.NominaCatalogo;

@SuppressWarnings("rawtypes")
public class NominaCatalogoRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet res, int arg1) throws SQLException {
		NominaCatalogo nominaCatalogo = new NominaCatalogo();
		nominaCatalogo.setActivo(res.getBoolean("Activo"));
		nominaCatalogo.setCodigoSAT(res.getString("CodigoSAT"));
		nominaCatalogo.setDescripcion(res.getString("Descripcion"));
		nominaCatalogo.setIdNominaCatalogo(res.getInt("PK_NominaCatalogo"));
		nominaCatalogo.setTipo(res.getString("Tipo"));
		return nominaCatalogo;
	}

}
