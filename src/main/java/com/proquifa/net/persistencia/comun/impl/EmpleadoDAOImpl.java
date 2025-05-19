package com.proquifa.net.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Modificacion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.EmpleadoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.EmpleadoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.EmpleadoRowMapperAdmin;

@Repository
public class EmpleadoDAOImpl extends DataBaseDAO implements EmpleadoDAO {
	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(EmpleadoDAOImpl.class);

	@Override
	public Boolean validaUsuario(Empleado user) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", user.getUsuario());
			map.put("password", user.getPassword());
			String pass = super.jdbcTemplate.queryForObject("SELECT Pass FROM Empleados WHERE Usuario= :usuario", map, String.class);
			return pass.equals(user.getPassword()); 
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}


	@SuppressWarnings({ "unchecked" })
	@Override
	public Empleado obtenerGerenteXIdEmpleado(Long idEmpleado) {
		try{	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			String query = 	" SELECT E.*,NULL AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion " +
					" FROM Empleados as E,Funcion AS F,Subproceso AS Sub WHERE "+
					" FK01_Funcion IN (SELECT PK_Funcion FROM Funcion WHERE FK01_Subproceso IN (SELECT FK01_Subproceso FROM Funcion WHERE PK_Funcion IN (SELECT FK01_Funcion "+
					" FROM Empleados where Clave = :idEmpleado )) AND Nivel = 'Gerente') AND E.FK01_Funcion = F.PK_Funcion AND F.FK01_Subproceso = Sub.PK_Subproceso AND E.Fase > 0";




			return (Empleado) super.jdbcTemplate.queryForObject(query, map ,new EmpleadoRowMapper()); 
		}catch(RuntimeException e){		
			//e.printStackTrace();
			//			funcion = new Funcion();
			//			funcion.enviarCorreoAvisoExepcion(e, "idEmpleado: " + idEmpleado);
			return null;
		}
	}

	//@Override
	@SuppressWarnings("unchecked")
	public Empleado obtenerEmpleadoPorUsuario(String usuario) {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			query = 	" SELECT E.*,F.Nivel AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion,E.Administrador" +
					" FROM Empleados as E" +
					" LEFT JOIN (SELECT * FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
					" LEFT JOIN (SELECT * FROM Subproceso) AS SUB ON SUB.PK_Subproceso = F.FK01_Subproceso" +
					" WHERE usuario = :usuario ";

			return (Empleado) super.jdbcTemplate.queryForObject(query, map, new EmpleadoRowMapperAdmin());  

		}catch(RuntimeException runTime){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(runTime, "query: " + query,"usuario: " + usuario);
			return new Empleado();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Empleado obtenerResponsableDeSubProceso(Long idSubproceso){  
		try{
			String query =  " SELECT E.*,F.Nivel AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion  " +
					" FROM Subproceso AS SUB, Empleados AS E, Funcion AS F" +
					" WHERE SUB.FK02_Usuario = E.Clave AND E.FK01_Funcion = F.PK_Funcion AND SUB.PK_Subproceso = :idSubproceso" ;

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idSubproceso", idSubproceso);
			Long idVisita = super.queryForLong("SELECT IDENT_CURRENT ('VisitaCliente')");
			log.info("idVisita" + idVisita);
			return (Empleado) super.jdbcTemplate.queryForObject(query, map, new EmpleadoRowMapper());
		}catch(RuntimeException rte){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, "idSubproceso: " + idSubproceso);
			rte.printStackTrace();
			return new Empleado();
		}
	}



	@Override
	public List<String> getRolesEmpleado(String usuario) {
		String sql = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usuario",usuario);
		try {
			try
			{
				Integer d = Integer.parseInt(usuario);

				map.put("d",d);


				sql = 	"\n select Descripcion " +
						"\n from Rol_Empleado re" +
						"\n left join (select * from Rol) r on r.PK_Rol = re.FK01_Rol" +
						"\n where re.FK02_Empleado = :d";



			}
			catch(NumberFormatException nfe)  {
				sql = 	"\n select Descripcion " +
						"\n from Rol_Empleado re" +
						"\n left join (select * from Rol) r on r.PK_Rol = re.FK01_Rol" +
						"\n left join (select * from Empleados) e on e.Clave = re.FK02_Empleado" +
						"\n where e.Usuario = :usuario";
			}
			System.out.println(sql); //NO HAY REGISTRO DEL USUARIO 1102
			log.info(sql);

			return super.jdbcTemplate.queryForList(sql, map, String.class);

			//logger.info("rol del usuario" + sql );

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"usuario: " + usuario);
			return new ArrayList<String>();
		}


	}


	@SuppressWarnings("unchecked")
	public List<Empleado> obtenerEmpleadosHabilitados() {
		String query = "";
		try{
			query = 	" SELECT E.*,F.Nivel AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion " +
					" FROM Empleados as E" +
					" LEFT JOIN (SELECT * FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
					" LEFT JOIN (SELECT * FROM Subproceso) AS SUB ON SUB.PK_Subproceso = F.FK01_Subproceso" +
					" WHERE E.Fase > 0" +
					" ORDER BY Usuario";
			List<Empleado> resp = super.jdbcTemplate.query(query,new EmpleadoRowMapper());
			return resp;
		}catch(Exception e){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query);
			return new ArrayList<Empleado>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Empleado> obtenerEmpleadosPorTipo(String tipo) {
		String query = "";
		try {

			if(tipo.equals("esac")){
				query = " SELECT E.*,NULL AS Nivel_General,NULL AS Subproceso,NULL AS idSubproceso,F.Nombre AS NombreFuncion " +
						" FROM Empleados as E, Funcion as F WHERE e.FK01_Funcion=f.PK_Funcion and (e.Nivel = 8 or e.Nivel = 26 or e.Nivel = 32 or e.Nivel = 27) and e.Fase > 0 ORDER BY Usuario";
			}else if(tipo.toLowerCase().equals("contabilidad")){
				query = " SELECT E.*,NULL AS Nivel_General,NULL AS Subproceso,NULL AS idSubproceso,F.Nombre AS NombreFuncion " +
						" FROM Empleados as E, Funcion as F WHERE e.FK01_Funcion=f.PK_Funcion and e.FK01_Funcion = 40 and e.Fase > 0 ORDER BY Usuario";
			}else if(tipo.toLowerCase().equals("comprador")){
				query = " SELECT E.*,NULL AS Nivel_General,NULL AS Subproceso,NULL AS idSubproceso,F.Nombre AS NombreFuncion " +
						" FROM Empleados as E, Funcion as F WHERE e.FK01_Funcion=f.PK_Funcion and e.FK01_Funcion = 10 and e.Fase > 0 ORDER BY Usuario";
			}else if(tipo.toLowerCase().equals("esac_master")){
				query = " SELECT E.*,NULL AS Nivel_General,NULL AS Subproceso,NULL AS idSubproceso,F.Nombre AS NombreFuncion " +
						" FROM Empleados as E, Funcion as F WHERE e.FK01_Funcion=f.PK_Funcion and e.FK01_Funcion = 37 and e.Fase > 0 ORDER BY Usuario";
			}else{
				query = " SELECT E.*,NULL AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion" +
						" FROM Empleados as E" +
						" LEFT JOIN (SELECT * FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
						" LEFT JOIN (SELECT * FROM Subproceso) AS SUB ON SUB.PK_Subproceso = F.FK01_Subproceso" +
						" WHERE Fase > 0 ORDER BY Usuario";
			}

			return (List<Empleado>) super.jdbcTemplate.query(query,new EmpleadoRowMapper());

		} catch (Exception e) {
			log.info(e.getMessage());
			log.info(query);
			//			funcion = new Funcion();
			//			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "tipo: " + tipo);
			return new ArrayList<Empleado>();
		}
	}



	@SuppressWarnings("unchecked")
	public Empleado obtenerEmpleadoPorId(Long idEmpleado) {
		String query = "";
		try{
			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			query = 	" SELECT E.*,F.Nivel AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion" +
					" FROM Empleados AS E" +
					" LEFT JOIN (SELECT * FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
					" LEFT JOIN (SELECT * FROM Subproceso) AS SUB ON SUB.PK_Subproceso = F.FK01_Subproceso" +
					" WHERE E.Clave = :idEmpleado";



			Empleado empleado = (Empleado) super.jdbcTemplate.queryForObject(query,map, new EmpleadoRowMapper());

			return empleado;
		}catch(RuntimeException runTime){
			runTime.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(runTime, "query: " + query, "idEmpleado: " + idEmpleado);
			return new Empleado();
		}
	}


	@Override
	public Boolean actualizarEmpleado(Long idEmpleado, Long idFuncion) {
		try{
			Map<String, Object>  map = new HashMap<String, Object>();
			map.put("idEmpleado",idEmpleado);
			map.put("idFuncion", idFuncion);
			super.jdbcTemplate.update("UPDATE empleados SET fk01_funcion = :idFuncion WHERE clave = :idEmpleado", map);
			return true;
		}catch(RuntimeException runTime){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(runTime, "idEmpleado: " + idEmpleado, "idFuncion: " + idFuncion);
			return false;
		}
	}


	@Override
	@SuppressWarnings("unchecked")
	public List<Empleado> obtenerEmpleadosPorNivel(String nivel, Long idEmpleado) {

		try {
			String query = "";
			String usuario = " " ;

			Map<String, Object>  map = new HashMap<String, Object>();

			if(nivel == null || nivel.equals("--TODOS--")){
				query = " SELECT E.*,F.Nivel AS Nivel_General,SUB.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion " +
						" FROM Empleados AS E" +
						" LEFT JOIN (SELECT * FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
						" LEFT JOIN (SELECT * FROM Subproceso) AS SUB ON SUB.PK_Subproceso = F.FK01_Subproceso" +
						" WHERE FK01_Funcion IS NOT NULL ORDER BY Usuario";
			}else{
				if(idEmpleado != 0){
					usuario = " AND Clave = " + idEmpleado + " " ;
				}
				query = " SELECT E.*,F.Nivel AS Nivel_General,SUB.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion " +
						" FROM Empleados AS E,Funcion AS F,Subproceso AS Sub " +
						" WHERE FK01_Funcion IN (SELECT PK_Funcion FROM Funcion WHERE Nivel = '" + nivel + "') AND Fase <> '0' "+
						" AND E.FK01_Funcion = F.PK_Funcion AND F.FK01_Subproceso = SUB.PK_Subproceso"+ usuario +"ORDER BY Usuario";
			}

			map.put("nivel",nivel);
			map.put("idEmpleado", idEmpleado);

			return (List<Empleado>) super.jdbcTemplate.queryForObject(query,map, new EmpleadoRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idEmpleado: " + idEmpleado, "nivel: " + nivel);
			return new ArrayList<Empleado>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Empleado obtenerGerenteDeSubProceso(Long idSubproceso) {
		try{
			Map <String, Object > map = new HashMap<String, Object>();
			map.put("idSubproceso",idSubproceso);
			String query = ""; 
			query = " SELECT E.*,NULL AS Nivel_General,Sub.Nombre AS Subproceso,Sub.PK_Subproceso AS idSubproceso,F.Nombre AS NombreFuncion " +
					" FROM Empleados as E,Funcion AS F,Subproceso AS Sub WHERE fk01_funcion in (SELECT pk_funcion from Funcion where FK01_Subproceso = "+ idSubproceso +" and Nivel = 'Gerente') "+
					" AND E.FK01_Funcion = F.PK_Funcion AND F.FK01_Subproceso = Sub.PK_Subproceso";
			return (Empleado) super.jdbcTemplate.queryForObject(query,map, new EmpleadoRowMapper());
		}catch(RuntimeException rte){
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(rte, "idSubproceso: " + idSubproceso);
			return new Empleado();
		}
	}



	public Boolean validarPassUser(Empleado user) {
		Map <String,Object> map = new HashMap<String, Object>();
		map.put("user",user);
		log.info("Entre en query de verificacion");
		String passEmpleadoEncontrado = (String) super.jdbcTemplate.queryForObject("SELECT Pass FROM Empleados WHERE Usuario='"+ user.getUsuario() +"'", map,String.class);
		log.info("SELECT Pass FROM Empleados WHERE Usuario='"+ user.getUsuario()+"'");
		if (passEmpleadoEncontrado.equals(user.getPassword())){
			return true;
		}else{
			return false;
		}
	}






	public String getGerenteOResponsableSubProceso(Long idSubproceso) {
		try {
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("idSubproceso", idSubproceso);
			String query = "SELECT COALESCE(Gerente.Usuario,ResponsableSub.Usuario) AS Gerente "+
					"FROM Subproceso AS sub "+
					"LEFT JOIN(SELECT * FROM Funcion) AS f ON f.FK01_Subproceso=sub.PK_Subproceso AND f.Nivel='Gerente' "+
					"LEFT JOIN(SELECT * FROM Empleados WHERE Empleados.Fase>0) AS Gerente ON Gerente.FK01_Funcion=f.PK_Funcion "+
					"LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS ResponsableSub ON ResponsableSub.Clave=sub.FK02_Usuario "+
					"WHERE sub.PK_Subproceso=:idSubproceso";
			return (String) super.jdbcTemplate.queryForObject(query,map, String.class);
		} catch (Exception e) {
			//logger.info("No existe alg-n gerente o responsable del subproceso");
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idSubproceso: " + idSubproceso);
			return "";
		}
	}


	@SuppressWarnings("unchecked")
	public List<String> finEquipoESAC(Long idEsacMaster) {
		try {
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idEsacMaster", idEsacMaster);
			String query = "DECLARE @idUsusario AS Varchar(50)=:idEsacMaster" +
					"SELECT Usuario FROM Empleados WHERE Clave=@idUsusario UNION " +
					"SELECT Usuario FROM Empleados,Jefe_Subordinado WHERE Empleados.Clave=Jefe_Subordinado.FK02_Subordinado " +
					"AND Jefe_Subordinado.FK01_Jefe=@idUsusario " +
					"ORDER BY Usuario ASC";
			return super.jdbcTemplate.queryForList(query,map,String.class);			
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idEsacMaster: " + idEsacMaster);
			return new ArrayList<String>();
		}
	}


	public String getNivelEmpleado(Long idEmpleado) {
		try {
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			return (String) super.jdbcTemplate.queryForObject("SELECT Funcion.Nivel FROM Empleados,Funcion WHERE Funcion.PK_Funcion=Empleados.FK01_Funcion AND Empleados.Clave= :idEmpleado", map, String.class);
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idEmpleado: " + idEmpleado);
			return "";
		}
	}


	@SuppressWarnings("unchecked")
	public Empleado getEsacParaNuevoCliente() {
		try {
			Map <String ,Object> map = new HashMap<String, Object>();

			return (Empleado) super.jdbcTemplate.queryForObject("SELECT TOP 1 *,'' AS Nivel_General,'' AS Subproceso,0 AS idSubproceso,'' AS NombreFuncion FROM Empleados WHERE FK01_Funcion= 5 AND Fase>0 ORDER BY NEWID() ", map, new EmpleadoRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			return new Empleado();
		}
	}


	public String getFuncionEmpleado(Long idEmpleado) {
		try {
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idEmpleado", idEmpleado);
			return (String) super.jdbcTemplate.queryForObject("SELECT Funcion.Nombre AS Funcion FROM Empleados,Funcion WHERE Funcion.PK_Funcion=Empleados.FK01_Funcion AND Empleados.Clave= :idEmpleado", map, String.class);
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idEmpleado: " + idEmpleado);
			return "";
		}	
	}

	public Boolean guardarModificacion(Modificacion nuevaModificacion) {
		try{
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("nuevaModificacion", new Date ());
			map.put("nuevaModificacion", nuevaModificacion.getVentana());
			map.put("nuevaModificacion", nuevaModificacion.getModificaciones());
			map.put("nuevaModificacion", nuevaModificacion.getIdEmpleado() );

			//	Object params [] = { new Date (), nuevaModificacion.getVentana(),nuevaModificacion.getModificaciones(),nuevaModificacion.getIdEmpleado()  };
			super.jdbcTemplate.update(" INSERT INTO  Modificacion (fecha, VentanaOrigen, modificacion, fk01_Empleado ) VALUES (?,?,?,?) ", map);
			//logger.info("inserta modificacion: INSERT INTO  Modificacion (fecha, VentanaOrigen, modificacion, fk01_Empleado ) VALUES (" +  new Date () + ","  +nuevaModificacion.getVentana() + ","  +nuevaModificacion.getModificaciones() + "," +nuevaModificacion.getIdEmpleado() );
			return true;
		}catch(RuntimeException e){
			//logger.info("Error: "+ e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"nuevaModificacion: " + nuevaModificacion);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Empleado> getsubordinadosEsacMaster(Long idEsac) {
		try { 	
			String query = "\n select fs.*,'' AS Nivel_General,'' AS Subproceso,0 AS idSubproceso,'' AS NombreFuncion  from Empleados   " +
					"\n left join (select empleados.* , Jefe_Subordinado.FK01_Jefe as jefe   from Jefe_Subordinado, Empleados where empleados.Clave = Jefe_Subordinado.FK02_Subordinado  and empleados.Fase > 0 ) as fs on fs.Jefe = Empleados.Clave   " +
					"\n where Empleados.Nivel  =  41 and  empleados.Clave = " + idEsac.toString() +
					"\n union all select *, Clave as jefe ,'' AS Nivel_General,'' AS Subproceso,0 AS idSubproceso,'' AS NombreFuncion  from Empleados where Nivel =  41 and Fase > 0 and Clave =  " + idEsac.toString(); 
			//logger.info("obtener subordinados: "+ query);	 	
			return super.jdbcTemplate.query(query, new EmpleadoRowMapper());
		}catch(Exception e){
			//logger.error("Error: "+ e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idEsac: " + idEsac);
			return new ArrayList<Empleado>();
		}
	}

	@Override
	public String getCompradorporProveedor(Long idProveedor) {
		try {

			Map <String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			String query = "\n select top 1 empl.usuario from Proveedores as prov, empleados as empl where empl.clave = prov.FK01_Empleado and empl.Fase > 0  and prov.Clave = :idProveedor ";
			//			logger.info( query);	 
			return (String) super.jdbcTemplate.queryForObject(query,map, String.class);
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"idProveedor: " + idProveedor);
			return "";
		}	
	}


	@SuppressWarnings("unchecked")
	@Override
	public Empleado getEmpleadoPorFuncion(long funcionId) throws ProquifaNetException{
		Map <String ,Object> map = new HashMap<String, Object>();
		map.put ("funcionId",funcionId);

		return (Empleado) super.jdbcTemplate.queryForObject("select E.*,F.Nombre AS NombreFuncion from Empleados AS E" +
				"\nINNER JOIN (SELECT Nombre,PK_Funcion FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
				"\nwhere FK01_Funcion = :funcionId and Fase > 0", map, new EmpleadoRowMapper());
	}


	@SuppressWarnings("unchecked")
	@Override
	public Empleado getEmpleadoPorId(long id) throws ProquifaNetException{
		Map <String, Object> map = new HashMap<String, Object>();
		map.put ("id", id);

		return (Empleado) super.jdbcTemplate.queryForObject("SELECT E.*,F.Nombre AS NombreFuncion from Empleados AS E" +
				"\nINNER JOIN (SELECT Nombre,PK_Funcion FROM Funcion) AS F ON F.PK_Funcion = E.FK01_Funcion" +
				"\nWHERE E.Clave = :id AND Fase > 0", map, new EmpleadoRowMapper());
	}

	@Override
	public int getIdEmpleadoPorUsuario(String usuario) throws ProquifaNetException{
		try {
			Map <String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			return  super.queryForInt("SELECT Clave FROM Empleados WHERE Usuario = :usuario ",map);
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public Empleado getEmpleadoPorRol(String rol) throws ProquifaNetException {
		try {
			Map <String, Object>  map = new HashMap<String, Object>();
			map.put("rol", rol);
			return (Empleado) super.jdbcTemplate.queryForObject("SELECT * FROM Empleados E " +
					"INNER JOIN Rol_Empleado RE ON RE.FK02_Empleado = E.Clave " +
					"INNER JOIN Rol R ON R.PK_Rol = RE.FK01_Rol " +
					"WHERE R.Descripcion = :rol", map,new EmpleadoRowMapper()); 
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<String> getContraseniasPorFuncion(String funcion) throws ProquifaNetException{
		try {
			Map <String, Object>  map = new HashMap<String, Object>();
			map.put("funcion", funcion);
			String query = "SELECT (Usuario + ','  + Pass) FROM Empleados E " +
					"LEFT JOIN Funcion F ON E.FK01_Funcion = F.PK_Funcion " +
					"WHERE F.Nombre =:funcion";

			return super.jdbcTemplate.queryForList(query,map,String.class);

		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRolesEmpleadoInt(String usuario) {
		String sql = "";
		Map <String,Object> map = new HashMap<String, Object>();
		try {

			try  
			{  
				Integer d = Integer.parseInt(usuario); 
				map.put("d",d);
				map.put("usuario",usuario);

				sql = 	"\n select r.PK_Rol " +
						"\n from Rol_Empleado re" +
						"\n left join (select * from Rol) r on r.PK_Rol = re.FK01_Rol" +
						"\n where re.FK02_Empleado =:d ";
			}  
			catch(NumberFormatException nfe)  {  
				sql = 	"\n select r.PK_Rol " +
						"\n from Rol_Empleado re" +
						"\n left join (select * from Rol) r on r.PK_Rol = re.FK01_Rol" +
						"\n left join (select * from Empleados) e on e.Clave = re.FK02_Empleado" +
						"\n where e.Usuario =:usuario";    
			}  
			//				logger.info("rol del usuario" + sql );	 	
			return super.jdbcTemplate.queryForList(sql,map,String.class);	

		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,"usuario: " + usuario);
			return new ArrayList<String>();
		}


	}



	@Override 
	public String getContraseniaAdministrador() throws ProquifaNetException{
		Map <String, Object> map = new HashMap<String, Object>();
		try {
			String query = "SELECT TOP 1 ('Administrador,' + Pass) FROM Empleados WHERE Usuario = 'Administrador'";
			return (String) super.jdbcTemplate.queryForObject(query,map, String.class);
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> getContraseniasTipoAutorizacionValidarCertificadosNoDisponibles() throws ProquifaNetException{
		Map <String, Object> map = new HashMap<String, Object>();
		try{
			String query="SELECT (Usuario + ','  + Pass) FROM Empleados E LEFT JOIN Funcion F ON E.FK01_Funcion = F.PK_Funcion "
					+"\nleft join(select * from Rol_Empleado) as RE ON E.Clave=RE.FK02_Empleado"
					+"\nWHERE F.Nombre ='Gerente de Compras' or  RE.FK01_Rol=15";
			return super.jdbcTemplate.queryForList(query, map, String.class);
		}catch(Exception ex){
			ex.printStackTrace();
			return new ArrayList();
		}
	}

	@Override
	public Boolean updateContraseniaUsuario(Long clave,String pass) throws ProquifaNetException {
		Map <String, Object> map = new HashMap<String, Object>();
		map.put("clave", clave);
		map.put("pass", pass);
		try {
			String query = "UPDATE Empleados SET Pass = :pass WHERE Clave = :clave";
			super.jdbcTemplate.update(query, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Empleado> obtenerMensajeros() {
		try {
			String query = "";
			Map<String, Object>  map = new HashMap<String, Object>();
			
			query = " select * from empleados where nivel = 10 and fase > 0 ";

			return super.jdbcTemplate.query(query,new EmpleadoRowMapper());
			
		} catch (Exception e) {
			log.info("Llenar combo de mensajeros");
			e.printStackTrace();
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "Llenar combo de mensajeros");
			return new ArrayList<Empleado>();
		}
	}
}
