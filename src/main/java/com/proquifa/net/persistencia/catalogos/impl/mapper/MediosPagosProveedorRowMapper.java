package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.catalogos.MedioPago;

import org.springframework.jdbc.core.RowMapper;

public class MediosPagosProveedorRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		MedioPago mp = new MedioPago();
		mp.setIdProveedor(rs.getLong("FK01_Proveedor"));
		mp.setIdMedioPago(rs.getLong("PK_Cuenta"));
		mp.setTipo(rs.getString("Tipo"));
		mp.setBanco(rs.getString("Banco"));
		mp.setBeneficiario(rs.getString("Beneficiario"));
		mp.setNumCuenta(rs.getString("NoCuenta"));
		mp.setSucursal(rs.getString("Sucursal"));
		mp.setClabe(rs.getString("Clabe"));
		
		
		
		return mp;
	}

}
