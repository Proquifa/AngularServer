package com.proquifa.net.persistencia.despachos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;

public class TotalPiezasInspeccionadas_totalTiempo implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		totalesInspeccionProducto partT = new totalesInspeccionProducto();
		
		partT.setTotal_piezas(rs.getLong("t_piezas"));
		partT.setTotalTiempo_Ensegundos(rs.getLong("t_tiempo"));
		
		
		
		
		
		return partT;
		
	}
	
	
	

}
