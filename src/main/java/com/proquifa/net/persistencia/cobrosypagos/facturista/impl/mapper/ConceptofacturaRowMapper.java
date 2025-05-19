package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;


/**
 * @author Jhidalgo
 *
 */

public class ConceptofacturaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet res, int arg1) throws SQLException {
		
		ConceptoFactura conceptos = new ConceptoFactura();
		
		conceptos.setIdConcepto(res.getInt("PK_Concepto"));
		conceptos.setDescripcion(res.getString("Descripcion"));
		conceptos.setClaveUnidad(res.getInt("FK02_ClaveUnidad"));
		conceptos.setClaveProdServ(res.getInt("FK03_ClaveProdServ"));

		return conceptos;
	}

}
