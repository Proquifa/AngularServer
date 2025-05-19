package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.proveedores.Licencia;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author orosales
 *
 */

public class LicenciaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {

		Licencia l = new Licencia();
		
		l.setIdProveedor(rs.getLong("FK01_Proveedor"));
		l.setNCTC(rs.getDouble("NCTC"));
		l.setCIP(rs.getDouble("CIP"));
		l.setATCC(rs.getDouble("ATCC"));
		l.setIMI(rs.getDouble("IMI"));
		l.setNCPF(rs.getDouble("NCPF"));
		l.setNBRC(rs.getDouble("NBRC"));
		l.setNCIMB(rs.getDouble("NCIMB"));
		l.setNCYC(rs.getDouble("NCYC"));
		
		return l;

	}
}
