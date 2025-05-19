package com.proquifa.net.persistencia.compras.comprador.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.totalesInspeccionProducto;

public class TotalesInspeccionRowMapper implements RowMapper {
	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		totalesInspeccionProducto totales = new totalesInspeccionProducto();		
		
		
		totales.setTotal_A(rs.getLong("t_ano"));
		totales.setTotal_M(rs.getLong("t_mes"));
		totales.setTotal_Q(rs.getLong("t_quincena"));
		totales.setTotal_D(rs.getLong("t_dia"));
		
		try {
			totales.setNum_hallazgos(rs.getLong("t_hallazgos"));
			
		} catch (Exception e) {
		}
		
		
		 return totales;
	}

}
