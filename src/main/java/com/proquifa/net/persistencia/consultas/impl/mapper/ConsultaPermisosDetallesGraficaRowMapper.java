package com.proquifa.net.persistencia.consultas.impl.mapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.PermisoImportacion;

public class ConsultaPermisosDetallesGraficaRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PermisoImportacion permiso = new PermisoImportacion();
		
		permiso.setCantidad(rs.getDouble("NoPiezas"));
		permiso.setCosto(rs.getFloat("Monto"));
		permiso.setNoProductos(rs.getInt("NoProductos"));
		
		return permiso;
	}

	
	
	
}
