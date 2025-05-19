package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Fabricante;

public class FabricantePorIdRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Fabricante fabricante = new Fabricante();
		fabricante.setIdFabricante(rs.getLong("idFabricante"));
		fabricante.setNombre(rs.getString("Nombre"));
        fabricante.setHabilitado(rs.getBoolean("habilitado"));
		fabricante.setPaisCompra(rs.getString("Pais_compra"));
		fabricante.setPaisManufactura(rs.getString("Pais_manufactura"));
		fabricante.setLogoExt(rs.getString("logo_ext"));
		fabricante.setRazonSocial(rs.getString("RazonSocial"));
		fabricante.setTaxID(rs.getString("TaxID"));
		fabricante.setDireccion(rs.getString("Direccion"));
		return fabricante;
		
	}

}
