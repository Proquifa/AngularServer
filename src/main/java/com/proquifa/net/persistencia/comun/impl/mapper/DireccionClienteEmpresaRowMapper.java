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
public class DireccionClienteEmpresaRowMapper  implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Direccion dir = new Direccion();
		
		dir.setIdCliente(rs.getInt("Clave"));
		dir.setTipo(rs.getString("tipo"));
		dir.setPais(rs.getString("Pais"));
		dir.setEstado(rs.getString("Estado"));
		dir.setCalle(rs.getString("Calle"));
		dir.setMunicipio(rs.getString("Municipio"));
		dir.setCodigoPostal(rs.getString("CP"));
		dir.setRuta(rs.getString("Ruta"));
		dir.setZona(rs.getString("Zona"));
		dir.setMapa(rs.getString("Mapa"));
		
		if (rs.getString("Ciudad")!=null) {
			dir.setCiudad(rs.getString("Ciudad"));
		}
		if (rs.getString("Colonia")!=null) {
			dir.setColonia(rs.getString("Colonia"));
		}

		return dir;
	}

}


