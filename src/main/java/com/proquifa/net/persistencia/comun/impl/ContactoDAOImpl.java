/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Contacto;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ContactoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ContactoFleteraRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ContactoHabilitadoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ContactoPorClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ContactoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.consultarContactosPorStringRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.obtenerContactosProveedorPorIdRowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class ContactoDAOImpl extends DataBaseDAO implements ContactoDAO {

	Funcion f;
	
	final Logger log = LoggerFactory.getLogger(ContactoDAOImpl.class);
	

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ContactoDAO#actualizar(mx.com.proquifa.proquifanet.modelo.comun.Contacto)
	 */
	public Boolean actualizar(Contacto contacto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Nombre", contacto.getNombre());
			map.put("Titulo", contacto.getTitulo());
			map.put("Puesto", contacto.getPuesto());
			map.put("Departamento", contacto.getDepartamento());
			map.put("Telefono", contacto.getTelefono());
			map.put("TelefonoN", contacto.getTelefonoN());
			map.put("Fax", contacto.getFax());
			map.put("EMail", contacto.getEMail());
			map.put("Direccion", contacto.getDireccion().getIdLugar());
			map.put("NivelDecision", contacto.getNivelDecision());
			map.put("NivelPuesto", contacto.getNivelPuesto());
			map.put("Dificultad", contacto.getDificultad());
			map.put("Mantenimiento", contacto.getMantenimiento());
			map.put("Extension1", contacto.getExtension1());
			map.put("Extension2", contacto.getExtension2());
			map.put("Apellidos", contacto.getApellidos());
			map.put("Celular", contacto.getCelular());
			map.put("FechaNacimiento", contacto.getFechaNacimiento());
			map.put("AntiguedadMes", contacto.getAntiguedadMes());
			map.put("AntiguedadAnio", contacto.getAntiguedadAnio());
			map.put("IdContacto", contacto.getIdContacto());
			log.info("idContacto:"+contacto.getIdContacto());
			if(contacto.getDificultad() == null){
				contacto.setDificultad(0L);
			}
			if(contacto.getMantenimiento() == null){
				contacto.setMantenimiento("");
			}
			
			
//			new Funcion().imprimirDatosClase(contacto);
			if( contacto.getTipo() == null){
				Long valor = 1L;

				Object[] params =  {contacto.getNombre(),contacto.getTitulo(),contacto.getPuesto(),contacto.getDepartamento(),
						contacto.getTelefono(),contacto.getTelefonoN(),contacto.getFax(),contacto.getEMail(),
						contacto.getDireccion().getIdLugar(), contacto.getNivelDecision(), contacto.getNivelPuesto(),valor,contacto.getDificultad(),
						contacto.getMantenimiento(),contacto.getExtension1(),contacto.getExtension2(),
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio(),contacto.getIdContacto()};

				String que="UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?,FK03_Direccion=?,NivelDecision=?,NivelPuesto=?,FUA=GETDATE(),Habilitado=?,Dificultad=?,Mantenimiento=?,Extension1=?,Extension2=?,"
						+ " Apellidos = ?, Celular = ?, FechaNacimiento = ?, AntiguedadMes = ?, AntiguedadAnio = ?"
						+ " WHERE idContacto=?";
				//logger.info(que+" Man: "+contacto.getMantenimiento()+" Dificu: "+contacto.getDificultad());
				super.jdbcTemplate.update(que, map);
				return true;
			}else if (contacto.getTipo().equalsIgnoreCase("proveedores")){				
				Long valor = contacto.getHabilitado() == null ? 0L: contacto.getHabilitado() == true ? 1L:0L;
				Object[] params =  {contacto.getNombre(),contacto.getTitulo(),contacto.getPuesto(),contacto.getDepartamento(),
						contacto.getTelefono(),contacto.getTelefonoN(),contacto.getFax(),contacto.getEMail(),valor, contacto.getExtension1(),contacto.getExtension2(),
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio(), contacto.getIdContacto()};
				long idContacto = contacto.getIdContacto();
				if (contacto.getFirma() != null && contacto.getFirma().length>0){
					String direccionServer = new Funcion().obtenerRutaServidor("contactos","Proveedor");
					File dirreccionServerFile = new File(direccionServer);					
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
					}
					String direccionDocumento = direccionServer+"firma"+ idContacto + ".jpg";					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write(contacto.getFirma());
					out.close();
				}
				super.jdbcTemplate.update("UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?, FUA=GETDATE(),Habilitado=?,Extension1=?,Extension2=?,"
						+ " Apellidos = ?, Celular = ?, FechaNacimiento = ?, AntiguedadMes = ?, AntiguedadAnio = ?"
						+ " WHERE idContacto=?", map);
				super.jdbcTemplate.update("UPDATE Proveedores SET FK04_ContactoNAFTA="+ idContacto +" WHERE Clave= (SELECT FK01_Proveedor FROM Contactos where idContacto="+idContacto +")",map);
				return true;
			}else if (contacto.getTipo().equalsIgnoreCase("AgenteAduanal")){
				Long valor = contacto.getHabilitado() == null ? 0L: contacto.getHabilitado() == true ? 1L:0L;
				Object[] params =  {contacto.getNombre(),contacto.getTitulo(),contacto.getPuesto(),contacto.getDepartamento(),
						contacto.getTelefono(), contacto.getExtension1(),contacto.getTelefonoN(),contacto.getFax(),contacto.getEMail(),valor,
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio(),contacto.getIdContacto()};
				if (contacto.getFoto() != null && contacto.getFoto().length>0){
					String direccionServer = new Funcion().obtenerRutaServidor("contactos","AgenteAduanal");
					File dirreccionServerFile = new File(direccionServer);					
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
					}
					String direccionDocumento = direccionServer+"foto"+ contacto.getIdContacto() + ".jpg";					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write(contacto.getFoto());
					out.close();
				}
				super.jdbcTemplate.update("UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Extension1=?,Tel2=?,Fax=?,eMail=?, FUA=GETDATE(),Habilitado=?,"
						+ " Apellidos = ?, Celular = ?, FechaNacimiento = ?, AntiguedadMes = ?, AntiguedadAnio = ?"
						+ " WHERE idContacto=?", map);
				return true;
			}else if (contacto.getTipo().equalsIgnoreCase("Fletera")){
				
				Object[] params =  {contacto.getNombre(),contacto.getTelefono(),contacto.getExtension1(),
						contacto.getCelular(),contacto.getEMail(),contacto.getIdContacto()};
				if (contacto.getFoto() != null && contacto.getFoto().length>0){
					String direccionServer = new Funcion().obtenerRutaServidor("contactos","Fletera");
					File dirreccionServerFile = new File(direccionServer);					
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
					}
					String direccionDocumento = direccionServer+"foto"+ contacto.getIdContacto() + ".jpg";					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write(contacto.getFoto());
					out.close();
				}
				super.jdbcTemplate.update("UPDATE Contactos SET Contacto = ? ,Tel1 = ?,Extension1 = ?, Celular = ?, eMail = ?, FUA = GETDATE()"
						+ " WHERE idContacto=?", map);
				return true;
			}else{
				Long valor = contacto.getHabilitado() == null ? 0L: contacto.getHabilitado() == true ? 1L:0L;
				Object[] params =  {contacto.getNombre(),contacto.getTitulo(),contacto.getPuesto(),contacto.getDepartamento(),
						contacto.getTelefono(),contacto.getTelefonoN(),contacto.getFax(),contacto.getEMail(),
						contacto.getDireccion().getIdLugar(), contacto.getNivelDecision(), contacto.getNivelPuesto(),valor,contacto.getDificultad(),
						contacto.getMantenimiento(),contacto.getExtension1(),contacto.getExtension2(),
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio(),contacto.getIdContacto()};
				
				String que="UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?,FK03_Direccion=?,NivelDecision=?,NivelPuesto=?,FUA=GETDATE(),Habilitado=?,Dificultad=?,Mantenimiento=?,Extension1=?,Extension2=?,"
						+ " Apellidos = ?, Celular = ?, FechaNacimiento = ?, AntiguedadMes = ?, AntiguedadAnio = ?"
						+ " WHERE idContacto=?";
				
				
				super.jdbcTemplate.update(que, map);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	public Boolean borrar(Long idContacto) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContacto", idContacto);
			String query = "DELETE Contactos WHERE idContacto="+ idContacto;
			super.jdbcTemplate.update(query,map);
			return true;
		}catch(RuntimeException rte){
			return false;
		}
	}
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ContactoDAO#obtenerPorId(java.lang.Long)
	 */
	public Contacto obtenerPorId(Long idContacto) {
		try{		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContacto", idContacto);
			String query = "SELECT " +
					" CASE WHEN CASE WHEN COALESCE(HorarioyLugar.ZonaMensajeria,'')='--NINGUNA--' THEN '' ELSE COALESCE(HorarioyLugar.ZonaMensajeria,'') END <>'' THEN " +
					" COALESCE(HorarioyLugar.ZonaMensajeria,'') " +
					" ELSE 'Sin zona' END AS ZonaDireccion,(CASE WHEN PQL_Usuario IS NOT NULL THEN 1 ELSE 0 END) UsuarioPConnect," +

			" CASE WHEN (COALESCE(HorarioyLugar.Calle,'')+COALESCE(HorarioyLugar.Municipio,'')+CASE WHEN COALESCE(HorarioyLugar.Pais,'')='--NINGUNO--' THEN '' ELSE COALESCE(HorarioyLugar.Pais,'') END +COALESCE(HorarioyLugar.Estado,'')+COALESCE(HorarioyLugar.CP,''))<>'' THEN (" +
			" CASE WHEN COALESCE(HorarioyLugar.Calle,'') <>'' THEN ' · ' ELSE '' END+ COALESCE(HorarioyLugar.Calle,'')+" +
			" CASE WHEN COALESCE(HorarioyLugar.Municipio,'') <>'' THEN ' · ' ELSE '' END+ COALESCE(HorarioyLugar.Municipio,'')+" +
			" CASE WHEN COALESCE(HorarioyLugar.Pais,'') <>'' THEN ' · ' ELSE '' END +  COALESCE(HorarioyLugar.Pais,'')+" +
			" CASE WHEN COALESCE(HorarioyLugar.Estado,'') <>'' THEN ' · ' ELSE '' END+ COALESCE(HorarioyLugar.Estado,'') +	" +
			" CASE WHEN COALESCE(HorarioyLugar.CP,'') <>'' THEN ' · ' ELSE '' END+COALESCE(HorarioyLugar.CP,'') " +
			" )ELSE ' · Sin dirección' END AS Domicilio,Habilitado AS estadoCliente, * FROM contactos" +
			" LEFT JOIN HorarioyLugar ON Contactos.FK03_Direccion=HorarioyLugar.idHorario" +
			" WHERE idContacto = " + idContacto ;
			//log.info(query);
			return (Contacto)super.jdbcTemplate.queryForObject(query,map, new ContactoRowMapper());

		}catch(RuntimeException rte){
			new Funcion().enviarCorreoAvisoExepcion(rte, "\nidContacto: "+idContacto);
			return new Contacto();
		}
	}

	public 	String obtenerContactoXId (Long idContacto){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idContacto", idContacto);
			String query = "SELECT Contacto FROM contactos WHERE idContacto = " + idContacto;
			return (String)super.jdbcTemplate.queryForObject(query,map, String.class);
		}catch (RuntimeException rte) {
			return null;
		}
	}

	public Contacto obtenerPorNombre(String nombre) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nombre", nombre);
			return (Contacto)super.jdbcTemplate.queryForObject("SELECT null AS ZonaDireccion,null AS Domicilio,* FROM Contactos WHERE Contacto = '"+ nombre +"'",map, new ContactoRowMapper());
		} catch (Exception e) {
			return null;
		}		
	}

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.ContactoDAO#registrar(mx.com.proquifa.proquifanet.modelo.comun.Contacto)
	 */
	public Boolean registrar(Contacto contacto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Nombre", contacto.getNombre());
			map.put("Titulo", contacto.getTitulo());
			map.put("NivelPuesto", contacto.getNivelPuesto());
			map.put("Puesto", contacto.getPuesto());
			map.put("Departamento",contacto.getDepartamento());
			map.put("Telefono",contacto.getTelefono());
			map.put("TelefonoN",contacto.getTelefonoN());
			map.put("Fax",contacto.getFax());
			map.put("EMail",contacto.getEMail());
			map.put("Tipo",contacto.getTipo());
			map.put("Empresa",contacto.getEmpresa());
			map.put("Habilitado",contacto.getHabilitado());
			map.put("Proveedor",contacto.getIdProveedor());
			map.put("Extension1",contacto.getExtension1());
			map.put("Extension2",contacto.getExtension2());
			map.put("Apellidos",contacto.getApellidos());
			map.put("Celular",contacto.getCelular());
			map.put("FechaNacimiento",contacto.getFechaNacimiento());
			map.put("AntiguedadMes",contacto.getAntiguedadMes());
			map.put("AntiguedadAnio",contacto.getAntiguedadAnio());
			Long clave = this.obtenerNumeroClaveContacto(contacto);
			if (clave < 0)
				clave = 0L;

			if (contacto.getTipo().equalsIgnoreCase("Proveedores")){

				Object[] params =  {clave+1,contacto.getNombre(),contacto.getTitulo(),contacto.getNivelPuesto(),contacto.getPuesto(),contacto.getDepartamento(),contacto.getTelefono(),	contacto.getTelefonoN(),
						contacto.getFax(),contacto.getEMail(),contacto.getTipo(),contacto.getEmpresa(),	contacto.getHabilitado(), contacto.getIdProveedor(),0,contacto.getExtension1(), contacto.getExtension2(),
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio()};	
				boolean isContactoNafta = contacto.isContactoNAFTA();
				long idProveedor = contacto.getIdProveedor();
				super.jdbcTemplate.update("INSERT INTO Contactos (Clave,Contacto,Titulo,NivelPuesto,Puesto,Depto,Tel1,Tel2,Fax,eMail,Tipo,Empresa,Habilitado,FK01_Proveedor,AgregadoExpo,FUA,Extension1,Extension2,Apellidos,Celular,FechaNacimiento,AntiguedadMes,AntiguedadAnio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE(),?,?,?,?,?,?,?)",map);
				Long idContacto = super.queryForLong("SELECT IDENT_CURRENT ('Contactos')",map);
				if (isContactoNafta){
					super.jdbcTemplate.update("UPDATE Proveedores SET FK04_ContactoNAFTA="+ idContacto +" WHERE clave="+ idProveedor,map);					
				}
				if (contacto.getFirma()!= null && contacto.getFirma().length>0){
					String direccionServer = new Funcion().obtenerRutaServidor("contactos","Proveedor");
					File dirreccionServerFile = new File(direccionServer);					
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
					}
					String direccionDocumento = direccionServer+ "firma"+ idContacto + ".jpg";					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write(contacto.getFirma());
					out.close();
				}
				contacto.setIdContacto(idContacto);
				return true;

			}else if (contacto.getTipo().equalsIgnoreCase("AgenteAduanal")){

				Object[] params =  {clave+1,contacto.getNombre(),contacto.getTitulo(),contacto.getNivelPuesto(),contacto.getPuesto(),contacto.getDepartamento(),contacto.getTelefono(),	contacto.getExtension1(),contacto.getTelefonoN(),
						contacto.getFax(),contacto.getEMail(),contacto.getTipo(),contacto.getEmpresa(),	contacto.getHabilitado(), contacto.getIdAgenteAduanal(),0,
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio()};	

				super.jdbcTemplate.update("INSERT INTO Contactos (Clave,Contacto,Titulo,NivelPuesto,Puesto,Depto,Tel1,Extension1,Tel2,Fax,eMail,Tipo,Empresa,Habilitado,FK04_AgenteAduanal,AgregadoExpo,FUA,Apellidos,Celular,FechaNacimiento,AntiguedadMes,AntiguedadAnio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE(),?,?,?,?,?)",map);
				Long idContacto = super.queryForLong("SELECT IDENT_CURRENT ('Contactos')",map);
				contacto.setIdContacto(idContacto);
				
				if (contacto.getFoto() != null && contacto.getFoto().length>0){
					String direccionServer = new Funcion().obtenerRutaServidor("contactos","AgenteAduanal");
					File dirreccionServerFile = new File(direccionServer);					
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
					}
					String direccionDocumento = direccionServer+ "foto"+ idContacto + ".jpg";					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write(contacto.getFoto());
					out.close();
				}
				return true;

			}else if (contacto.getTipo().equalsIgnoreCase("Fletera")){

				Object[] params =  {contacto.getEmpresa(), contacto.getNombre(),contacto.getTelefono(),contacto.getExtension1(),
						contacto.getCelular(),contacto.getEMail()};	

				super.jdbcTemplate.update("DECLARE @idFletera AS int, @Fletera AS varchar(max) " +
					"SELECT @idFletera= PK_Fletera FROM Fletera WHERE Nombre=? " +
					"SELECT @Fletera = Nombre FROM Fletera WHERE PK_Fletera=@idFletera " +
					"INSERT INTO Contactos (Contacto,Tel1,Extension1,Celular,eMail,Habilitado,FUA, FK06_Fletera, Empresa) VALUES (?,?,?,?,?,1,GETDATE(),@idFletera, @Fletera)",map);
				Long idContacto = super.queryForLong("SELECT IDENT_CURRENT ('Contactos')",map);
				contacto.setIdContacto(idContacto);
				
				if (contacto.getFoto() != null && contacto.getFoto().length>0){
					String direccionServer = new Funcion().obtenerRutaServidor("contactos","Fletera");
					File dirreccionServerFile = new File(direccionServer);					
					if (!dirreccionServerFile.exists()) {
						dirreccionServerFile.mkdirs();
					}
					String direccionDocumento = direccionServer+ "foto"+ idContacto + ".jpg";					
					OutputStream out = new FileOutputStream(direccionDocumento);
					out.write(contacto.getFoto());
					out.close();
				}
				
				return true;

			}else{
				Object[] params =  {clave+1,contacto.getNombre(),contacto.getTitulo(),contacto.getNivelPuesto(),contacto.getPuesto(),contacto.getDepartamento(),contacto.getTelefono(),contacto.getTelefonoN(),
						contacto.getFax(),contacto.getEMail(),contacto.getTipo(),contacto.getEmpresa(),contacto.getHabilitado(),contacto.getIdEmpresa(),contacto.getDireccion().getIdLugar(),contacto.getNivelDecision(),
						contacto.isAgregadoExpo(),contacto.getOrigenRegistro(),contacto.getIdProveedor(),contacto.getDificultad(),contacto.getMantenimiento(),contacto.getExtension1(), contacto.getExtension2(),
						contacto.getApellidos(),contacto.getCelular(),contacto.getFechaNacimiento(),contacto.getAntiguedadMes(),contacto.getAntiguedadAnio()};

				super.jdbcTemplate.update("INSERT INTO Contactos (Clave,Contacto,Titulo,NivelPuesto,Puesto,Depto,Tel1,Tel2,Fax,eMail,Tipo,Empresa,Habilitado,FK02_Cliente,FK03_Direccion,NivelDecision,AgregadoExpo,OrigenRegistro,FK01_Proveedor,FUA,Dificultad,Mantenimiento,Extension1,Extension2,Apellidos,Celular,FechaNacimiento,AntiguedadMes,AntiguedadAnio) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,GETDATE(),?,?,?,?,?,?,?,?,?)",map);
				Long idContacto = super.queryForLong("SELECT IDENT_CURRENT ('Contactos')",map);
				contacto.setIdContacto(idContacto);
				return true;
			}

		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return false;
		}
	}

	public Long obtenerNumeroClaveContacto(Contacto contacto){
		Long clave=0L;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Proveedor", contacto.getIdProveedor());
			map.put("AgenteAduanal", contacto.getIdAgenteAduanal());
			map.put("Empresa", contacto.getIdEmpresa());
			//logger.info("Se entro a obtener la clave");
			if (contacto.getTipo().equalsIgnoreCase("Proveedores")){
				clave = super.queryForLong("SELECT TOP 1 Clave FROM Contactos WHERE FK01_Proveedor="+contacto.getIdProveedor()+" ORDER BY Clave DESC",map);
				//logger.info("SELECT TOP 1 Clave FROM Contactos WHERE FK01_Proveedor="+contacto.getIdProveedor()+" ORDER BY Clave DESC");

			}else if (contacto.getTipo().equalsIgnoreCase("AgenteAduanal")){
				clave = super.queryForLong("SELECT TOP 1 Clave FROM Contactos WHERE FK04_AgenteAduanal="+contacto.getIdAgenteAduanal()+" ORDER BY Clave DESC",map);
				//logger.info("SELECT TOP 1 Clave FROM Contactos WHERE FK04_AgenteAduanal="+contacto.getIdAgenteAduanal()+" ORDER BY Clave DESC");

			}else{
				clave = super.queryForLong("SELECT TOP 1 Clave FROM Contactos WHERE FK02_Cliente="+contacto.getIdEmpresa()+" ORDER BY Clave DESC",map);
				//logger.info("SELECT TOP 1 Clave FROM Contactos WHERE FK02_Cliente="+contacto.getIdEmpresa()+" ORDER BY Clave DESC");
			}
			return clave;
		} catch (Exception e) {
			return -1L;
		}

	}

	@SuppressWarnings("unchecked")
	public List<Contacto> obtener(String origen, String empresa, String nombre,
			String mail) {
		StringBuffer query = new StringBuffer("SELECT 'Sin zona' AS ZonaDireccion,'Sin domicilio' AS Domicilio,contactos.*, NULL estadoCliente FROM Contactos, Clientes WHERE ISNUMERIC(contactos.contacto) <> 1 AND contactos.Habilitado = 1 AND clientes.Habilitado = 1 AND clientes.Nombre = contactos.Empresa ");
		if(!origen.equals("--TODOS--")){
			if(origen.equals("Cliente")){
				query.append("AND contactos.tipo = 'Clientes'");
			}else{
				query.append("AND contactos.tipo = 'Proveedores'");
			}
		}
		if(!empresa.equals("--TODOS--")){
			query.append("AND contactos.empresa = '" + empresa + "' ");
		}
		if(!nombre.isEmpty()){
			query.append("AND contactos.contacto LIKE '%" + nombre + "%' ");
		}
		if(!mail.isEmpty()){
			query.append("AND contactos.email LIKE '%" + mail + "%' ");
		}
		//logger.info(query.toString());
		return super.jdbcTemplate.query(query.toString(), new ContactoRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> obtenerPorDescripcion(String descripcion) {
		String query = "";
		if(descripcion.isEmpty()){
			//query = "SELECT * FROM Contactos WHERE ISNUMERIC (Contacto) <> 1 AND Contacto IS NOT NULL AND Empresa in (select Nombre from Clientes where Habilitado = 1 ) AND Habilitado = 1 ORDER BY Contacto ";
			query = "SELECT 'Sin zona' AS ZonaDireccion,'Sin direccion' AS Domicilio, NULL estadoCliente, Contactos.* FROM Contactos, Clientes WHERE ISNUMERIC(Contactos.Contacto) <> 1 AND contactos.Contacto IS NOT NULL AND contactos.Habilitado = 1 AND CLIENTES.Habilitado = 1 and " +
					"clientes.Nombre = contactos.Empresa Order by contactos.Contacto";
		}else{
			query = "SELECT 'Sin zona' AS ZonaDireccion,'Sin direccion' AS Domicilio,NULL estadoCliente, Contactos.* FROM Contactos, Clientes WHERE ISNUMERIC(Contactos.Contacto) <> 1 AND contactos.Contacto IS NOT NULL AND contactos.Habilitado = 1 AND CLIENTES.Habilitado = 1 and " +
					"clientes.Nombre = contactos.Empresa AND (contactos.Contacto LIKE '%" + descripcion + "%' OR contactos.eMail LIKE '%"+ descripcion + "%') ORDER BY Contacto";
		}
		return super.jdbcTemplate.query(query, new ContactoRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> obtenerContactosProveedorPorId(int proveedor, String tipo, Boolean habilitado) {
		try{
			Long valor = habilitado == true ? 1L:0L;

			String query = "SELECT Contactos.idContacto,Contactos.Titulo,Contactos.Contacto,Contactos.Puesto,Contactos.Depto,Contactos.Tel1,Contactos.Tel2,Contactos.Fax,Contactos.eMail,Contactos.FUA, Contactos.Habilitado, Contactos.Extension1, Contactos.Extension2, "+
					"\n CASE WHEN Pro.FK04_ContactoNAFTA = Contactos.idContacto THEN 1 ELSE 0 END NAFTA "+
					"\n FROM Contactos,"+ tipo +" AS Pro WHERE Contactos.Tipo='Proveedores' AND Contactos.FK01_Proveedor = PRO.Clave AND Contactos.Habilitado="+valor+
					"\n AND Pro.Clave="+ proveedor +" ORDER BY Contacto ASC";
			log.info(query);
			return super.jdbcTemplate.query(query, new obtenerContactosProveedorPorIdRowMapper());
		}catch(RuntimeException e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Contacto obtenerContactoNAFTAporIdProveedor(long idProveedor) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String query = "SELECT *, 1 AS NAFTA FROM Contactos WHERE idContacto=(SELECT FK04_ContactoNAFTA FROM Proveedores WHERE Clave="+ idProveedor +")";
			return (Contacto)super.jdbcTemplate.queryForObject(query,map, new obtenerContactosProveedorPorIdRowMapper());
		}catch(RuntimeException e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public Contacto obtenerContactoNAFTAporIdEmpresa(long idEmpresa) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa", idEmpresa);
			String query = "SELECT *, 1 AS NAFTA FROM Contactos WHERE idContacto=(8413)";
			return (Contacto)super.jdbcTemplate.queryForObject(query,map, new obtenerContactosProveedorPorIdRowMapper());
		}catch(RuntimeException e){
			log.info((e.getMessage()));
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> obtenerContactosAgentePorId(Long Agente, Boolean habilitado) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Agente", Agente);
			map.put("habilitado", habilitado);
			Long valor = habilitado == true ? 1L:0L;

			String query = "SELECT CON.idContacto,CON.Titulo,CON.Contacto,CON.Puesto,CON.Depto,CON.Tel1,CON.Tel2,CON.Fax,CON.eMail,CON.FUA, CON.habilitado, CON.Extension1, CON.Extension2, 0 AS NAFTA "+
					" FROM Contactos AS CON "+
					" WHERE CON.Tipo='AgenteAduanal' AND CON.FK04_AgenteAduanal= "+Agente+" AND CON.Habilitado="+valor+" ORDER BY Contacto ASC";
//			log.info(query);
			return super.jdbcTemplate.query(query,map, new obtenerContactosProveedorPorIdRowMapper());
		}catch(RuntimeException e){
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> consultarContactosPorString(String condicion, String tabla, String consulta){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicion", condicion);
			map.put("tabla", tabla);
			map.put("tabla", consulta);
			String query = 	consulta + condicion + " ORDER BY " + tabla +".Nombre";
			//log.info(query);
			return super.jdbcTemplate.query(query,map, new consultarContactosPorStringRowMapper());
		}catch(RuntimeException e){
			return null;
		}	
	}

	public Boolean deshabilitarContacto(Long idContacto) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicion", idContacto);
			super.jdbcTemplate.update("UPDATE Contactos SET Habilitado=0,FUA=GETDATE() WHERE idContacto="+ idContacto,map);
			return true;
		}catch(RuntimeException rte){
			//logger.info("Error: " + rte.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> findContactosPorIdEmpleado(Integer idEmpleado, List<NivelIngreso> niveles) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			map.put("niveles", niveles);
			String query = crearConsultaObtenerContactos(idEmpleado, "ev", niveles);
			//log.info(query);
			List<Contacto> temp = super.jdbcTemplate.query(query,map, new ContactoPorClienteRowMapper());
			return temp;
		}catch(Exception e){
			//logger.info("Error: "+e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> findContactosHabilitados() {
		try{
			String query = "SELECT CO.*,CL.Vendedor,E.Usuario AS Comprador,CL.Nombre FROM Contactos CO " + 
					"\nLEFT JOIN Clientes CL ON CO.FK02_Cliente = CL.Clave" +
					"\nLEFT JOIN Empleados E ON CL.FK01_EV = E.Clave" +
					"\nWHERE CO.Habilitado = 1 AND CL.Habilitado = 1 AND PQL_Usuario IS NULL";
			List<Contacto> temp = super.jdbcTemplate.query(query, new ContactoHabilitadoRowMapper());
			return temp;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public Long registrar(String nombre, String correo, Long cliente, String empresa) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("nombre", nombre);
			map.put("correo", correo);
			map.put("correo", empresa);
			Object[] params = {nombre,correo,empresa,cliente};

			StringBuilder sbQuery = new StringBuilder("INSERT INTO Contactos(Contacto,eMail,Habilitado,Empresa,FK02_Cliente,Tipo,FUA) VALUES(?,?,1,?,?,'Clientes',GETDATE())");
			//logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return super.queryForLong("SELECT IDENT_CURRENT ('Contactos')",map);
		}catch(Exception e){
			//logger.info(e.getMessage());
			return 0L;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> findObtenerContactos(List<NivelIngreso> niveles) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("niveles", niveles);
			String query = crearConsultaObtenerContactos(0, "", niveles);
			//log.info(query);
			List<Contacto> temp = super.jdbcTemplate.query(query,map, new ContactoPorClienteRowMapper());
			return temp;
		}catch(Exception e){
			//logger.info("Error: "+e.getMessage());
			return null;
		}
	}

	private String crearConsultaObtenerContactos(Integer idEmpleado, String consulta, List<NivelIngreso> niveles){
		f = new Funcion();
		//
		String query =
				"	SELECT TC.T AS tContactos,TD.T AS tDirecciones, null AS tZonas,EM.Clave AS EmpClave, Em.Nombre AS EmpNombre,CL.Clave AS ClienteClave,ESAC.Clave AS EsacClave,ESAC.Usuario AS EsacNombre, " +
						"	CL.Nombre AS ClienteNombre, CL.Industria, CL.Rol,CL.Sector" +
						"	, " + f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NivelIngreso" +
						" 	,CASE WHEN CASE WHEN COALESCE(HYL.ZonaMensajeria,'')='--NINGUNA--' THEN '' ELSE COALESCE(HYL.ZonaMensajeria,'') END <>'' THEN " +
						" 	COALESCE(HYL.ZonaMensajeria,'') " +
						" 	ELSE 'Sin zona' END AS ZonaDireccion," +
						"   CASE WHEN (COALESCE(HYL.Calle,'')+COALESCE(HYL.Municipio,'')+CASE WHEN COALESCE(HYL.Pais,'')='--NINGUNO--' THEN '' ELSE COALESCE(HYL.Pais,'') END +COALESCE(HYL.Estado,'')+COALESCE(HYL.CP,''))<>'' THEN (" +
						"   CASE WHEN COALESCE(HYL.Calle,'') <>'' THEN ' �� ' ELSE '' END+ COALESCE(HYL.Calle,'')+" +
						"   CASE WHEN COALESCE(HYL.Municipio,'') <>'' THEN ' �� ' ELSE '' END+ COALESCE(HYL.Municipio,'')+" +
						"   CASE WHEN COALESCE(HYL.Pais,'') <>'' THEN ' �� ' ELSE '' END +  COALESCE(HYL.Pais,'')+" +
						"   CASE WHEN COALESCE(HYL.Estado,'') <>'' THEN ' �� ' ELSE '' END+ COALESCE(HYL.Estado,'') +	" +
						"   CASE WHEN COALESCE(HYL.CP,'') <>'' THEN ' �� ' ELSE '' END+COALESCE(HYL.CP,'') " +
						"   )ELSE ' �� Sin dirección' END AS Domicilio"+
						"	,CONTA.idContacto AS ContaClave, CONTA.Titulo AS Titulo,CONTA.Contacto AS ContactoNombre, CONTA.NivelPuesto, CONTA.Puesto, CONTA.Depto,CONTA.eMail,CONTA.Tel1,CONTA.Tel2,CONTA.Fax, CONTA.FK02_Cliente AS idEmpresa,CONTA.NivelDecision " +
						"	,CONTA.NivelDecision, CONTA.NivelPuesto, HYL.*" +
						"	FROM Clientes AS CL" +
						"	LEFT JOIN (SELECT * FROM Empleados WHERE FK01_Funcion =7) AS EM ON EM.Clave = CL.FK01_EV" +
						"	LEFT JOIN (SELECT * FROM Empleados) AS ESAC ON ESAC.Usuario= CL.Vendedor" +
						"	LEFT JOIN (SELECT * FROM Contactos WHERE Habilitado=1) AS CONTA ON CONTA.FK02_Cliente = CL.Clave" +
						"	LEFT JOIN (SELECT COUNT(*) AS T," +
						"   CL.Clave FROM Contactos AS C" +
						"				LEFT JOIN (SELECT * FROM Clientes WHERE Habilitado=1)AS CL ON CL.Clave = C.FK02_Cliente" +
						"				WHERE C.Habilitado=1 ";
		if (consulta.toLowerCase().equals("ev")){
			query += "AND C.FK02_Cliente= CL.Clave ";
		}
		query += " GROUP BY CL.Clave) AS TC ON TC.Clave = CL.Clave" +

			//"	LEFT JOIN (SELECT COUNT(DISTINCT C.FK03_Direccion) AS T,CL.FK01_EV FROM Contactos AS C" +
			"	LEFT JOIN (SELECT COUNT(DISTINCT C.FK03_Direccion) AS T,CL.Clave FROM Contactos AS C" +
			"					LEFT JOIN (SELECT * FROM Clientes WHERE Habilitado=1)AS CL ON CL.Clave = C.FK02_Cliente" +
			"					WHERE C.Habilitado=1 ";
		if (consulta.toLowerCase().equals("ev")){
			//query += "AND CL.FK01_EV = '"+idEmpleado+"' "; 
			query += " AND C.FK02_Cliente =CL.Clave "; 
		}
		//query += " GROUP BY CL.FK01_EV) AS TD ON TD.FK01_EV = CL.FK01_EV" +
		query += " GROUP BY CL.Clave) AS TD ON TD.CLave = CL.Clave" +

			"	LEFT JOIN (SELECT * FROM HorarioyLugar) AS HYL ON HYL.idHorario = CONTA.FK03_Direccion" +
			"	LEFT JOIN (SELECT COALESCE(SUM(" +
			" 	CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar" +
			"	WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then CASE WHEN F.TCambio > 0 THEN F.Importe/F.TCambio ELSE F.Importe END END" +
			"	), 0 )as VentasUSD, Cliente FROM Facturas as F" +
			"	LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha" +
			"	WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = CL.Clave" +
			"	LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C" +
			"	LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre" +
			"	LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)" +
			"	AS OPER ON OPER.Clave = CL.Clave" +
			"	WHERE CL.Habilitado = 1 ";
		if (consulta.toLowerCase().equals("ev")){
			if (idEmpleado != 0){
				query += " AND CL.FK01_EV = '"+idEmpleado+"' ";	
			}
		}
		if (consulta.toLowerCase().equals("industria")){
			query += " AND CL.Industria = 'CLINICA HOSPITALARIA' ";
		}
		query += "ORDER BY CL.Nombre,CONTA.Contacto";
		return query;
	}

	@SuppressWarnings("unchecked")
	public List<Contacto> findContactosXidCliente(Long idCliente, int habilitados) {
		try {
			String condicion = "";
			if(habilitados != 2){
				condicion = " AND Contactos.Habilitado = " + habilitados;
			}

			StringBuilder sbQuery = new StringBuilder("SELECT Contactos.FUA," +
					" CASE WHEN CASE WHEN COALESCE(HorarioyLugar.ZonaMensajeria,'')='--NINGUNA--' THEN '' ELSE COALESCE(HorarioyLugar.ZonaMensajeria,'') END <>'' THEN " +
					" COALESCE(HorarioyLugar.ZonaMensajeria,'') " +
					" ELSE 'Sin zona' END AS ZonaDireccion, CLI.Habilitado estadoCliente," +
					" CASE WHEN (COALESCE(HorarioyLugar.Calle,'')+COALESCE(HorarioyLugar.Municipio,'')+CASE WHEN COALESCE(HorarioyLugar.Pais,'')='--NINGUNO--' THEN '' ELSE COALESCE(HorarioyLugar.Pais,'') END +COALESCE(HorarioyLugar.Estado,'')+COALESCE(HorarioyLugar.CP,''))<>'' THEN (" +
					" CASE WHEN COALESCE(HorarioyLugar.Calle,'') <>'' THEN ' · ' ELSE '' END+ COALESCE(HorarioyLugar.Calle,'')+" +
					" CASE WHEN COALESCE(HorarioyLugar.Municipio,'') <>'' THEN ' · ' ELSE '' END+ COALESCE(HorarioyLugar.Municipio,'')+" +
					" CASE WHEN COALESCE(HorarioyLugar.Pais,'') <>'' THEN ' · ' ELSE '' END +  COALESCE(HorarioyLugar.Pais,'')+" +
					" CASE WHEN COALESCE(HorarioyLugar.Estado,'') <>'' THEN ' · ' ELSE '' END+ COALESCE(HorarioyLugar.Estado,'') +	" +
					" CASE WHEN COALESCE(HorarioyLugar.CP,'') <>'' THEN ' · ' ELSE '' END+COALESCE(HorarioyLugar.CP,'') " +
					" )ELSE ' · Sin dirección' END AS Domicilio, *," +
					" (CASE WHEN Contactos.PQL_Usuario IS NOT NULL THEN 1 ELSE 0 END) AS UsuarioPConnect,"+
					" Contactos.Apellidos,Contactos.Celular,Contactos.FechaNacimiento,Contactos.AntiguedadMes,Contactos.AntiguedadAnio" +
					" FROM contactos" +
					" LEFT JOIN HorarioyLugar ON Contactos.FK03_Direccion=HorarioyLugar.idHorario " +
					" LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave = Contactos.FK02_Cliente");
			sbQuery.append(" WHERE FK02_Cliente="+ idCliente + condicion + " ORDER BY Contacto ASC");

			log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new ContactoRowMapper());

		} catch (Exception e) {  
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contacto> findObtenerContactosExpoMed(List<NivelIngreso> niveles) {
		try{
			String query = crearConsultaObtenerContactos(0, "industria", niveles);
			//log.info(query);
			List<Contacto> temp = super.jdbcTemplate.query(query, new ContactoPorClienteRowMapper());
			return temp;
		}catch(Exception e){
			//logger.info("Error: "+e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean actualizarExpo(Contacto contacto) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Nombre", contacto.getNombre());
			map.put("Titulo", contacto.getTitulo());
			map.put("Puesto", contacto.getPuesto());
			map.put("Departamento", contacto.getDepartamento());
			map.put("Telefono", contacto.getTelefono());
			map.put("TelefonoN", contacto.getTelefonoN());
			map.put("Fax", contacto.getFax());
			map.put("EMail", contacto.getEMail());
			map.put("Direccion", contacto.getDireccion().getIdLugar());
			map.put("NivelDecision", contacto.getNivelDecision());
			map.put("NivelPuesto", contacto.getNivelPuesto());
			map.put("OrigenRegistro",contacto.getOrigenRegistro());
			map.put("IdContacto",contacto.getIdContacto());
			if (contacto.getDireccion() != null){
				Object[] params =  {contacto.getNombre(),contacto.getTitulo(),contacto.getPuesto(),contacto.getDepartamento(),
						contacto.getTelefono(),contacto.getTelefonoN(),contacto.getFax(),contacto.getEMail(),
						contacto.getDireccion().getIdLugar(), contacto.getNivelDecision(), contacto.getNivelPuesto(),contacto.getOrigenRegistro(), contacto.getIdContacto()};
				//logger.info("UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?,FK03_Direccion=?,NivelDecision=?,NivelPuesto=?, OrigenRegistro=?  WHERE idContacto=?" + params);
				super.jdbcTemplate.update("UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?,FK03_Direccion=?,NivelDecision=?,NivelPuesto=?,OrigenRegistro=?,FUA=GETDATE()  WHERE idContacto=?", map);
				return true;
			}else{
				Object[] params =  {contacto.getNombre(),contacto.getTitulo(),contacto.getPuesto(),contacto.getDepartamento(),
						contacto.getTelefono(),contacto.getTelefonoN(),contacto.getFax(),contacto.getEMail(),
						contacto.getNivelDecision(), contacto.getNivelPuesto(),contacto.getOrigenRegistro(), contacto.getIdContacto()};
				//logger.info("UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?,NivelDecision=?,NivelPuesto=?,OrigenRegistro?  WHERE idContacto=?" + params);
				super.jdbcTemplate.update("UPDATE Contactos SET Contacto=?,Titulo=?,Puesto=?,Depto=?,Tel1=?,Tel2=?,Fax=?,eMail=?,NivelDecision=?,NivelPuesto=?,OrigenRegistro=?,FUA=GETDATE()  WHERE idContacto=?", map);
				return true;
			}
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return false;
		}
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Contacto> obtenerContactosPorTipo(String tipo,boolean habilitados){
		try {
			String query = "";
			String condicionHabilitados = (habilitados ? " AND Habilitado = 1" : "");
			if (tipo.equalsIgnoreCase("Fletera")) {
				query = "SELECT * FROM Contactos WHERE FK06_Fletera IS NOT NULL" + condicionHabilitados +" ORDER BY Contacto";
				return super.jdbcTemplate.query(query, new ContactoFleteraRowMapper());
			}else if (tipo.equalsIgnoreCase("AgenteAduanal")) {
				query = "SELECT * FROM Contactos WHERE FK04_AgenteAduanal IS NOT NULL" + condicionHabilitados + " ORDER BY Contacto";
				return super.jdbcTemplate.query(query, new ContactoFleteraRowMapper());
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return null;
		}
	}
	
	
}