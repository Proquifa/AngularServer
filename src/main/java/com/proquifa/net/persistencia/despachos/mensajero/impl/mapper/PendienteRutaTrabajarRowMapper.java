package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.PendientesMensajero;

public class PendienteRutaTrabajarRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		PendientesMensajero pend = new PendientesMensajero();
		pend.setTipoRuta(rs.getString("tipoRuta"));
		pend.setFolio(rs.getString("idDP"));
		pend.setFinicio(rs.getDate("FechaAsignacion"));
		pend.setFfin(rs.getDate("FechaRealizacion"));
		pend.setNombreCliente(rs.getString("Cliente"));
		pend.setFactura(rs.getLong("Factura"));
		pend.setZona(rs.getString("Zona"));
		pend.setPrioridad(rs.getString("Prioridad"));
		pend.setFEE(rs.getDate("FEE"));
		pend.setMonto(rs.getDouble("Monto"));
		
		
		return pend;
	}

}
