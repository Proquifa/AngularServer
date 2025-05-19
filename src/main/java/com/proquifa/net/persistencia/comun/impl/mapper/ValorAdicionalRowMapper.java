package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.ValorAdicional;

import org.springframework.jdbc.core.RowMapper;

public class ValorAdicionalRowMapper implements RowMapper{
		/* (non-Javadoc)
		 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
		 */
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			ValorAdicional valorAdicional = new ValorAdicional();
			valorAdicional.setId(rs.getLong("idProveedor"));
			valorAdicional.setNombre(rs.getString("Nombre"));
			valorAdicional.setValor(rs.getString("Valor"));
			if(rs.getString("ValorSecundario") != null && !rs.getString("ValorSecundario").equals("")){
				valorAdicional.setValorSecundario(rs.getString("ValorSecundario"));
			}
			valorAdicional.setTipo(rs.getString("tipo"));
			return valorAdicional;
	}
	
}
