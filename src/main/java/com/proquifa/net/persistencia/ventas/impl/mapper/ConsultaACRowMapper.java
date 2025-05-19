package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import org.springframework.jdbc.core.RowMapper;

public class ConsultaACRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCotizacion partidaCotizacion = new PartidaCotizacion();
		partidaCotizacion.setIdPartidaCotizacion(rs.getLong("idPCotiza"));
		partidaCotizacion.setCotizacion(rs.getString("Clave"));
		partidaCotizacion.setCodigo(rs.getString("Codigo"));
		partidaCotizacion.setFabrica(rs.getString("Fabrica"));
		partidaCotizacion.setCantidad(rs.getInt("Cant"));
		partidaCotizacion.setPrecio(rs.getFloat("Precio"));
		partidaCotizacion.setPartida(rs.getLong("Partida"));
		partidaCotizacion.setConcepto(rs.getString("Concepto"));
		partidaCotizacion.setTotal(rs.getFloat("Total"));
		partidaCotizacion.setEstado(rs.getString("Estado"));
		
		return partidaCotizacion;
	}
}