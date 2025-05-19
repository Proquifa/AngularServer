package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

import com.proquifa.net.modelo.catalogos.proveedores.ClasificacionConfiguracionPrecio;

import org.springframework.jdbc.core.RowMapper;

public class ConfigPrecioClasificacionRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ClasificacionConfiguracionPrecio cla = new ClasificacionConfiguracionPrecio();

		cla.setIdClasificacion(rs.getLong("CLASIFICACION"));
		cla.setIdConfigFamilia(rs.getLong("FK01_ConfigFamilia"));
		cla.setIdConfigClasificacion(rs.getLong("CONFIG"));
		cla.setConcepto(rs.getString("Concepto"));
		cla.setTotalProductos(rs.getLong("TPRODUCTOS"));
		cla.setRestablecerCliente(rs.getBoolean("RES_CLIE"));
		cla.setEliminar(false);
		
		try{
			if(rs.getString("TipoFamilia") != null)
				cla.setTipo(rs.getString("TipoFamilia"));
		}
		catch(Exception e){
			
			
		}
		
		try{
			if(rs.getString("Subtipo") != null)
				cla.setSubtipo(rs.getString("Subtipo"));
		}
		catch(Exception e){
			
			
		}
		
		try{
			if(rs.getString("Control") != null)
				cla.setControl(rs.getString("Control"));
		}
		catch(Exception e){
			
			
		}
		
		
		return cla;
	}

}
