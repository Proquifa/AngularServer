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
 * @author ernestogonzalezlozada
 *
 */
public class ContactoFleteraRowMapper implements RowMapper {

	final Logger log = LoggerFactory.getLogger(ContactoFleteraRowMapper.class);
	
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Contacto contacto = new Contacto();
		contacto.setHabilitado(rs.getBoolean("habilitado"));
		contacto.setIdContacto(rs.getLong("idContacto"));
		contacto.setNombre(rs.getString("Contacto"));
		contacto.setTelefono(rs.getString("Tel1"));
		contacto.setExtension1(rs.getString("Extension1"));
		contacto.setCelular(rs.getString("Celular"));
		contacto.setEMail(rs.getString("eMail"));
		contacto.setIdFletera(rs.getInt("FK06_Fletera"));
		contacto.setIdAgenteAduanal(rs.getLong("FK04_AgenteAduanal"));
		
		try{
			String tipo="";
			if (contacto.getIdFletera() != 0){
				tipo ="Fletera";
			}else if (contacto.getIdAgenteAduanal() != 0){
				tipo="AgenteAduanal";
			}			
			String direccionFoto = new Funcion().obtenerRutaServidor("contactos",tipo)+"foto" + contacto.getIdContacto()+ ".jpg";			
			File dirreccionServerFile = new File(direccionFoto);
			
			if (dirreccionServerFile.exists()) {
				contacto.setRutaFoto(tipo+"/foto"+contacto.getIdContacto()+ ".jpg");
				log.info("rutaFoto: "+tipo+"/foto"+contacto.getIdContacto()+ ".jpg");
			}
		}catch(Exception e){	
			log.info(e.getMessage());
			return null;
		}
		return contacto;
	}
}