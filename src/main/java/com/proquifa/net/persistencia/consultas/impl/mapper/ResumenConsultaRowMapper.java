package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;

import org.springframework.jdbc.core.RowMapper;

public class ResumenConsultaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		//Lo esta utilizando consulta compras y consulta pedidos
		ResumenConsulta r = new ResumenConsulta();
		
		r.setEtiqueta(rs.getString("RESULTADOS"));
		r.setConceptoResultado(rs.getString("Nombre"));
		r.setTotal(Math.rint((rs.getDouble("MONTO")) * 1e2) / 1e2);
		r.setPiezasTotal(rs.getLong("TOTAL"));		
		r.setPartidas(rs.getLong("PARTIDAS"));
		r.setClientes_proveedores(rs.getLong("CP"));
		r.setTotalFolios(rs.getLong("FOLIOS"));
		return r;
	}
}