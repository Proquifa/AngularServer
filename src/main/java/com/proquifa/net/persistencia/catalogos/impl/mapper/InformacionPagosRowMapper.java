package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.InformacionPagos;

import org.springframework.jdbc.core.RowMapper;

public class InformacionPagosRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		InformacionPagos ip = new InformacionPagos();
		  
		ip.setComentarios(rs.getString("ObservaPago"));
		ip.setCondicionesPago(rs.getString("CPago"));
		ip.setIdProveedor(rs.getLong("Clave"));
		ip.setLimiteCredito(rs.getDouble("LimiteCredito"));
		ip.setLineaCredito(rs.getDouble("LineaCredito"));
		ip.setFua(rs.getTimestamp("FUA_Pagos"));
		
		return ip;
	}

}
