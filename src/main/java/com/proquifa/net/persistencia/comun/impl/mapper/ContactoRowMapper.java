/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.Direccion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class ContactoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Contacto contacto = new Contacto();
		String titulo="",nivelPuesto="",puesto="",depto="",tel1="";
		String correo="",nd="",tel2="",fax="";
		contacto.setIdContacto(rs.getLong("idContacto"));

		try {contacto.setNombre(rs.getString("Contacto"));} catch (Exception e) {}
		contacto.setTipo(rs.getString("Tipo"));
		contacto.setEmpresa(rs.getString("Empresa"));
		contacto.setHabilitado(rs.getBoolean("Habilitado"));
		contacto.setFk02_Cliente(rs.getString("FK02_Cliente"));
		contacto.setFechaUltimaActualizacion(rs.getTimestamp("FUA"));
		contacto.setMantenimiento(rs.getString("Mantenimiento"));
		contacto.setDificultad(rs.getLong("Dificultad"));
		contacto.setUsuarioPConnect(rs.getBoolean("UsuarioPConnect"));
		
		titulo=rs.getString("Titulo");
		nivelPuesto=rs.getString("NivelPuesto");
		puesto=rs.getString("Puesto");
		depto=rs.getString("Depto");
		tel1=rs.getString("Tel1");
		tel2=rs.getString("Tel2");
		fax=rs.getString("Fax");
		correo=rs.getString("eMail");
		nd=rs.getString("NivelDecision");

		if(titulo==null || titulo.equals("")){contacto.setTitulo("ND");}else{contacto.setTitulo(titulo);}
		if(nivelPuesto==null || nivelPuesto.equals("")){contacto.setNivelPuesto("ND");}else{contacto.setNivelPuesto(nivelPuesto);}
		if(puesto==null || puesto.equals("")){contacto.setPuesto("ND");}else{contacto.setPuesto(puesto);}
		if(depto==null || depto.equals("")){contacto.setDepartamento("ND");}else{contacto.setDepartamento(depto);}
		if(tel1==null || tel1.equals("")){contacto.setTelefono("ND");}else{contacto.setTelefono(tel1);}
		if(tel2==null || tel2.equals("")){contacto.setTelefonoN("ND");}else{contacto.setTelefonoN(tel2);}
		if(fax==null || fax.equals("")){contacto.setFax("ND");}else{contacto.setFax(fax);}
		if(correo==null || correo.equals("")){contacto.setEMail("ND");}else{contacto.setEMail(correo);}
		if(nd==null || nd.equals("")){contacto.setNivelDecision("ND");}else{contacto.setNivelDecision(nd);}
		if(rs.getString("Extension1") == null || rs.getString("Extension1").equals("")){ contacto.setExtension1("ND"); } else { contacto.setExtension1(rs.getString("Extension1")); }
		if(rs.getString("Extension2") == null || rs.getString("Extension2").equals("")){ contacto.setExtension2("ND"); } else { contacto.setExtension2(rs.getString("Extension2")); }
		
		contacto.setApellidos(rs.getString("Apellidos"));
		contacto.setCelular(rs.getString("Celular"));
		contacto.setAntiguedadMes(rs.getInt("AntiguedadMes"));
		contacto.setAntiguedadAnio(rs.getInt("AntiguedadAnio"));
		contacto.setFechaNacimiento(rs.getDate("FechaNacimiento"));
		
		Direccion  direccion=new Direccion();
		direccion.setZonaMensajeria(rs.getString("ZonaDireccion"));
		if(!rs.getString("Domicilio").isEmpty())
		direccion.setDomicilio(rs.getString("Domicilio").trim().substring(1).trim());
		contacto.setDireccion(direccion);
		contacto.setEstadoCliente(rs.getBoolean("estadoCliente"));
		
		return contacto;
	}
}