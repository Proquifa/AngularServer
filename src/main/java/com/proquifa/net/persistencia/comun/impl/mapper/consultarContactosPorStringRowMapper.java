/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Contacto;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class consultarContactosPorStringRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Contacto contacto = new Contacto();
			contacto.setNombre(rs.getString("Contacto"));
			contacto.setTitulo(rs.getString("Titulo"));
			contacto.setPuesto(rs.getString("Puesto"));
			contacto.setDepartamento(rs.getString("Depto"));
			contacto.setTelefono(rs.getString("Tel1"));
			contacto.setTelefonoN(rs.getString("Tel2"));
			contacto.setFax(rs.getString("Fax"));
			contacto.setEMail(rs.getString("eMail"));
			contacto.setEmpresa(rs.getString("Empresa"));
			contacto.setNacionalidadEmpresa(rs.getString("Pais"));
			contacto.setIdContacto(rs.getLong("idContacto"));
			contacto.setIdEmpresa(rs.getLong("Clave"));
			contacto.setIndustria(rs.getString("Industria"));
			contacto.setRol(rs.getString("Rol"));
			contacto.setSector(rs.getString("Sector"));
			contacto.setTotalContactos(rs.getInt("NoContactos"));
			contacto.setNivelIngresos(rs.getString("NivelIngreso"));
		return contacto;
	}

}
