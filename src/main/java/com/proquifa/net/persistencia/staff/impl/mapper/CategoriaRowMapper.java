package com.proquifa.net.persistencia.staff.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.staff.Categoria;

/**
 * Setea los Campos de la tabla tblCategoria
 * @author Isaac Garcia
 *
 */
public class CategoriaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Categoria categoria = new Categoria();
		
		categoria.setClaveCategoria(rs.getString("Categoria"));
		categoria.setNombreCategoria(rs.getString("Nombre"));
		return categoria;
	}
}
