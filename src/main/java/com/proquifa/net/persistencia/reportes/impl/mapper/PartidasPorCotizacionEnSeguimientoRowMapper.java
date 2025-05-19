package com.proquifa.net.persistencia.reportes.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;

import org.springframework.jdbc.core.RowMapper;

public class PartidasPorCotizacionEnSeguimientoRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCotizacionEnSeguimiento pcs= new PartidaCotizacionEnSeguimiento();
		pcs.setCantidad(rs.getLong("Cant"));
		pcs.setConcepto(rs.getString("Concepto"));
		pcs.setEstado(rs.getString("Estado"));
		pcs.setFolioCotizacion(rs.getString("Clave"));
		pcs.setIdPCotiza(rs.getLong("idPCotiza"));
		pcs.setMonto(rs.getFloat("Monto"));
		pcs.setPartida(rs.getLong("Partida"));
		pcs.setPrecio(rs.getFloat("Precio"));
		pcs.setMonedaCotiza(rs.getString("IMoneda"));
		pcs.setFechaSiguiente(rs.getTimestamp("FechaSiguiente"));
		pcs.setTipo(rs.getString("Tipo"));
		pcs.setControl(rs.getString("CONTROL"));
		pcs.setMarca(rs.getString("Fabrica"));

		return pcs;
	}
}
