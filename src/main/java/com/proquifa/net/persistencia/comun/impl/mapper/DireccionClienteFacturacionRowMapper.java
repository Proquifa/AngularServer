/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Direccion;

/**
 * @author bryan.magana
 *
 */
public class DireccionClienteFacturacionRowMapper  implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Direccion dir = new Direccion();

		dir.setIdCliente(rs.getInt("Clave"));
		dir.setTipo(rs.getString("tipo"));
		dir.setPais(rs.getString("RSPais"));
		dir.setEstado(rs.getString("RSEstado"));
		dir.setCalle(rs.getString("RSCalle"));
		dir.setMunicipio(rs.getString("RSMunicipio"));
		dir.setCodigoPostal(rs.getString("RSCP"));
		
		if (rs.getString("RSCiudad")!=null) {
			dir.setCiudad(rs.getString("RSCiudad"));
		}
		if (rs.getString("RSColonia")!=null) {
			dir.setColonia(rs.getString("RSColonia"));
		}
			

		return dir;
	}

}


