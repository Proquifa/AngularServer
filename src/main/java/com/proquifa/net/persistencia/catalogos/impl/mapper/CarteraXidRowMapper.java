/**
 * 
 */
package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Cartera;

/**
 * @author bryan.magana
 *
 */
public class CarteraXidRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Cartera c = new Cartera();
		
		 c.setIdcartera(rs.getLong("PK_Cartera"));
		 c.setArea(rs.getString("Area"));
		 c.setEv(rs.getLong("FK01_EV"));
		 c.setEsac(rs.getLong("FK02_Esac"));
		 c.setCobrador(rs.getLong("FK03_Cobrador"));
		 c.setNombre(rs.getString("nombre"));
		 c.setNombreEsac(rs.getString("Usuario"));
		 return c;
		 
	}

}
