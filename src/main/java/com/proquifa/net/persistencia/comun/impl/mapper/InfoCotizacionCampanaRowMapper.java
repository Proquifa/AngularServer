/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.DoctoCotizacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author bryan.magana
 *
 */
public class InfoCotizacionCampanaRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 * 
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		DoctoCotizacion cotiza = new DoctoCotizacion();
		 cotiza.setNombreCliente(rs.getString("crsocial"));
		 cotiza.setDireccionCliente(rs.getString("direccion"));
		 cotiza.setNombreContacto(rs.getString("NombrePuesto"));
		 cotiza.setFaxContacto(rs.getString("cfax"));
		 cotiza.setMailContacto(rs.getString("cemail"));
		 cotiza.setTelContacto(rs.getString("ctel"));
		 cotiza.setNombreEsac(rs.getString("nombrevendedor"));
		 cotiza.setCpago(rs.getString("cpago"));
		 cotiza.setMoneda(rs.getString("imoneda"));
		 cotiza.setFcreacion(rs.getDate("Fcreacion"));
		 cotiza.setCcotiza(rs.getString("clave"));
		 cotiza.setFolioDorctoR(rs.getLong("folioDoctoR")); 
		 cotiza.setUsuarioEsac(rs.getString("vendedor"));
		return cotiza ;
	}

}
