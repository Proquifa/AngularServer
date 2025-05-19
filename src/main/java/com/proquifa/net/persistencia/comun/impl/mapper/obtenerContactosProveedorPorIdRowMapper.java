/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.util.Funcion;

/**
 * @author vromero
 *
 */
public class obtenerContactosProveedorPorIdRowMapper implements RowMapper {
	
	final Logger log = LoggerFactory.getLogger(obtenerContactosProveedorPorIdRowMapper.class);

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Contacto contacto = new Contacto();
		contacto.setIdContacto(rs.getLong("idContacto"));
		contacto.setNombre(rs.getString("Contacto"));
		contacto.setTitulo(rs.getString("Titulo"));
		contacto.setPuesto(rs.getString("Puesto"));
		contacto.setDepartamento(rs.getString("Depto"));
		contacto.setTelefono(rs.getString("Tel1"));
		contacto.setTelefonoN(rs.getString("Tel2"));
		contacto.setFax(rs.getString("Fax"));
		contacto.setEMail(rs.getString("eMail"));
		if(rs.getTimestamp("FUA") == null){
		}else{
			contacto.setFechaUltimaActualizacion(rs.getTimestamp("FUA"));
		}
		contacto.setHabilitado(rs.getBoolean("habilitado"));
		if(rs.getString("Extension1") == null || rs.getString("Extension1").equals("")){ contacto.setExtension1("ND"); } else { contacto.setExtension1(rs.getString("Extension1")); }
		if(rs.getString("Extension2") == null || rs.getString("Extension2").equals("")){ contacto.setExtension2("ND"); } else { contacto.setExtension2(rs.getString("Extension2")); }
		
		try{
			contacto.setContactoNAFTA(rs.getBoolean("NAFTA"));
			String direccionFirma = new Funcion().obtenerRutaServidor("contactos","Proveedor")+"firma" + contacto.getIdContacto()+ ".jpg";
			File dirreccionServerFile = new File(direccionFirma);
			
			if (dirreccionServerFile.exists()) {
				contacto.setRutaFirma("Proveedor/firma"+contacto.getIdContacto()+ ".jpg");
			}
		}catch(Exception e){	
			log.info(e.getMessage());
			return null;
		}
		return contacto;
	}
}