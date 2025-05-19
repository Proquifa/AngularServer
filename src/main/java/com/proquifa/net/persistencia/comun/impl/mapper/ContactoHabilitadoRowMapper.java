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
public class ContactoHabilitadoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Contacto contacto = new Contacto();
		String titulo="",nivelPuesto="",puesto="",depto="",tel1="";
		String correo="",nd="",tel2="",fax="";
		contacto.setIdContacto(rs.getLong("idContacto"));
		contacto.setNombre(rs.getString("Contacto"));
		contacto.setTipo(rs.getString("Tipo"));
		contacto.setEmpresa(rs.getString("Nombre"));
		contacto.setHabilitado(rs.getBoolean("Habilitado"));
		
		contacto.setFk02_Cliente(rs.getString("FK02_Cliente"));
		
		contacto.setFechaUltimaActualizacion(rs.getTimestamp("FUA"));
		contacto.setMantenimiento(rs.getString("Mantenimiento"));
		contacto.setDificultad(rs.getLong("Dificultad"));
		
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
		
		contacto.setEsac(((rs.getString("Vendedor") != null && !rs.getString("Vendedor").equals("") 
				&& !rs.getString("Vendedor").equals("--NINGUNO--") && !rs.getString("Vendedor").equals("Administrador")
				&& !rs.getString("Vendedor").equals("Sin asignacion")) 
				? rs.getString("Vendedor") : "Sin asignación"));
		
		contacto.setEv(((rs.getString("Comprador") != null && !rs.getString("Comprador").equals("") 
				&& !rs.getString("Comprador").equals("--NINGUNO--") && !rs.getString("Comprador").equals("Administrador")
				&& !rs.getString("Comprador").equals("Sin asignacion"))
				? rs.getString("Comprador"): "Sin asignación"));
		
		
		return contacto;
	}
}