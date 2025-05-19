package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.ubicacionPorManejo;

public class ubicacionPorExistenciaRowMapper  implements RowMapper{

		public Object mapRow (ResultSet rs, int arg1) throws SQLException{
			
			ubicacionPorManejo dato = new ubicacionPorManejo();
			dato.setIdUbicacion(rs.getString("idUbicacion"));
			dato.setExistencias(rs.getInt("Existencia"));
			dato.setTotal(rs.getInt("Total"));
			return dato;
		}
}

