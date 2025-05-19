/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.comun.Comentario;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author misael.camanos
 *
 */
public class ComentarioClienteRowMapper implements RowMapper{
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Comentario comentario=new Comentario();
		comentario.setIdComentario(rs.getLong("PK_Comentario"));
		comentario.setIdCliente(rs.getLong("FK01_Cliente"));
		comentario.setSeccion(rs.getString("Seccion"));
		comentario.setTema(rs.getString("Tema"));
		comentario.setContenido(rs.getString("Contenido"));
		
		return comentario;
	}

}
