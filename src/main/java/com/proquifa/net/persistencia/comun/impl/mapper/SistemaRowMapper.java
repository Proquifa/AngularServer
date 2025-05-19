package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.util.Sistema;

public class SistemaRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Sistema sitema = new Sistema();
		sitema.setIdSistema(rs.getInt("PK_Sistema"));
		sitema.setNombre(rs.getString("Nombre"));
		sitema.setVersion(rs.getString("Version"));
		sitema.setComentario(rs.getString("Comentario"));
		sitema.setUrl(rs.getString("URL"));
		
		return sitema;
	}
	
}
