package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Empresa;

import org.springframework.jdbc.core.RowMapper;

public class EmpresasProveedorRowMapper implements RowMapper{

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Empresa e = new Empresa();
		
		e.setIdEmpresa(rs.getInt("PK_Empresa"));
		try {
			e.setIdEmpresaProveedor(rs.getLong("PK_Empresas_Proveedor"));
		} catch (Exception ex) {
		}

		try {
			e.setIdProveedor(rs.getLong("Prov"));
		} catch (Exception ex) {
		}


		e.setAlias(rs.getString("Alias"));

		try {
			e.setRelacionProveedor(rs.getBoolean("Habilitada"));
		} catch (Exception ex) {
		}


		try {
			e.setNumAsigCliente(rs.getString("Asignado"));
		} catch (Exception ex) {
		}



		try {
			e.setFechaUltimaActualizacion(rs.getTimestamp("FUA"));
		} catch (Exception ex) {
		}
		
		return e;
	}

}
