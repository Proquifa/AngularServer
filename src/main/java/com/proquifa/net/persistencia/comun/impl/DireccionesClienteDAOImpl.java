/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.catalogos.impl.mapper.DireccionHorarioDeClienteRowMapper;
import com.proquifa.net.persistencia.comun.DireccionesClienteDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.DireccionClienteEmpresaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.DireccionClienteFacturacionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.DireccionRecoleccionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.DireccionRowMapper;


/**
 * @author vromero
 *
 */
@Repository
public class DireccionesClienteDAOImpl extends DataBaseDAO implements DireccionesClienteDAO {

	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(DireccionesClienteDAOImpl.class);
	
	public String getDatosDeEntrega(Long idCliente, Long idPedido)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
		map.put("idPedido", idPedido);
		map.put("idCliente", idCliente);
			if(idPedido > 0){
				query = "SELECT Calle + ', ' + Delegacion + ' C.P.' + CP + ' ' + Estado + ' ' + Pais AS DOMICILIO FROM Pedidos WHERE idPedido = " + idPedido;
			}else{
				query = "SELECT Calle + ', ' + Municipio + ' C.P.' + CP + ' ' + Estado + ' ' + Pais AS DOMICILIO FROM HorarioyLugar WHERE Tipo= 'Entrega' AND idCliente = " + idCliente;
			}
//			log.info(query);
			return (String)super.jdbcTemplate.queryForObject(query,map,  String.class);
		}catch(RuntimeException e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "idCliente: " + idCliente, "idPedido: " + idPedido);
			return "";
					
		}
	}

	public String getDatosDeFacturacion(Long idCliente)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
		
			map.put("idCliente", idCliente);
			query = "SELECT RSCalle + ', ' + RSDel + ' C.P.' + RSCP + ' ' + RSEstado + ', ' + RSPais AS DOMICILIO  FROM Clientes WHERE Clave = " + idCliente;
//			log.info(query);
			return (String)super.jdbcTemplate.queryForObject(query,map,  String.class);
		}catch(RuntimeException e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "idCliente: " + idCliente);
			return "";
		}
	}

	public Boolean getDiferenteDireccionDeEntrega(Long idPedido)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("idPedido", idPedido);
			query = "SELECT chkDiferenteDestino FROM Pedidos WHERE idPedido = " + idPedido;
//			log.info(query);
			int d = super.queryForInt(query, map);
			if(d == 1){
				return true;
			}else{
				return false;
			}
		}catch(RuntimeException e){
			//logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "idPedido: " + idPedido);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Direccion> findDireccionesCliente(Integer idCliente)
			throws ProquifaNetException {
		String query = "";
		try{
	Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			query=
				"	SELECT HYL.*,CASE WHEN Latitud IS NOT NULL AND Longitud IS NOT NULL AND Altitud IS NOT NULL THEN 1 ELSE 0 END AS Validada,ASO.NumAsociados" +
				"	FROM HorarioyLugar AS HYL" +
				"	LEFT JOIN (SELECT COUNT(*) AS NumAsociados, FK03_Direccion FROM Contactos WHERE FK03_Direccion IS NOT NULL GROUP BY FK03_Direccion) AS ASO ON ASO.FK03_Direccion=HYL.idHorario" +
				"	WHERE HYL.idCliente = "+idCliente+" ";
			
			return super.jdbcTemplate.query(query,map, new DireccionRowMapper());
			
		}catch(RuntimeException e){
			//logger.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "idCliente: " + idCliente);
			return new ArrayList<Direccion>();
		}
	}

	public Integer agregarDireccionCliente(Direccion direccion) throws ProquifaNetException {
		try {		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("direccion", direccion);
			Object[] params =  {direccion.getIdCliente(),direccion.getPais(),direccion.getEstado(),direccion.getCalle(),
					direccion.getMunicipio(),direccion.getCodigoPostal(),direccion.getTipo(), direccion.getComentarios()};
			
			super.jdbcTemplate.update("INSERT INTO HorarioyLugar(idCliente,Pais,Estado,Calle,Municipio,CP,Tipo,Comentarios) VALUES (?,?,?,?,?,?,?,?)", map);
			int idLugar = super.queryForInt("SELECT IDENT_CURRENT ('HorarioyLugar')", map);
			//logger.info("agregarDireccionCliente:" + idLugar);
			return idLugar;
		} catch (RuntimeException e) {
			//logger.error("Error: " + e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, direccion);
			return -1;
		}
	}
	
	public Boolean modificarDireccionCliente(Direccion direccion)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("direccion", direccion);
			Object[] params = {direccion.getPais(),direccion.getEstado(),direccion.getCalle(),direccion.getMunicipio(),direccion.getCodigoPostal(),
					null,null,null,direccion.getTipo(), direccion.getComentarios(), direccion.getIdLugar()};
			
			super.jdbcTemplate.update("UPDATE HorarioyLugar SET Pais=?,Estado=?,Calle=?,Municipio=?,CP=?,Longitud =?,latitud = ?,altitud=?, tipo = ?, comentarios = ? WHERE idHorario= ?", map);
			//logger.info("modificarDireccionCliente:" + direccion.getIdLugar());
			return true;
		}catch(RuntimeException e){
			//logger.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, direccion);
			return false;
		}
	}

	@Override
	public boolean deleteDireccionCliente(Long idLugar)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idLugar", idLugar);
			super.jdbcTemplate.update("DELETE FROM HorarioyLugar WHERE idHorario = " + idLugar , map);
//			log.info("DELETE FROM HorarioyLugar WHERE idHorario = " + idLugar);
			return true;
		} catch (Exception e) {
			//logger.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "idLugar: " + idLugar);
			return false;
		}
	}

	@Override
	public Direccion getDireccionEmpresa(Long idCliente)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			query = "SELECT Clave , Ruta, Mapa, Pais, Estado, Calle, Delegacion as Municipio , CP , Zona, Ciudad, Colonia, 'Empresa' tipo FROM Clientes  WHERE Clave = " + idCliente;
//			log.info(query);
			return (Direccion) super.jdbcTemplate.queryForObject(query,map,  new DireccionClienteEmpresaRowMapper());
		}catch(RuntimeException e){
		//	logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query);
			return new Direccion();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Direccion> getDireccionCliente(Long idCliente) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			String sQuery = "";
			
			sQuery = "SELECT * FROM Direccion WHERE FK01_Cliente = " + idCliente ;
			
			log.info(sQuery);
			return super.jdbcTemplate.query(sQuery, map, new DireccionRecoleccionRowMapper());
//			if(d != null) {
//				return d; 
//			}else{
//				return null;
//			}
			
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	@Override
	public Direccion getDireccionFacturacion(Long idCliente)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			query = "SELECT Clave, RSPais, RSEstado, RSCalle, RSDel as RSMunicipio, RSCP, RSCiudad, RSColonia, 'Facturacion' tipo FROM Clientes  WHERE Clave = " + idCliente;
//			log.info(query);
			return (Direccion) super.jdbcTemplate.queryForObject(query, map, new DireccionClienteFacturacionRowMapper());
		}catch(RuntimeException e){
	//		logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e, "query: " + query, "idCliente: " + idCliente);
			return new Direccion();
		}
	}
	
	@Override
	public Boolean updateDireccionEmpresa(Direccion direccion)
			throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("direccion", direccion);
			if (direccion.getIdCliente() != null) {
				Object[] params = {direccion.getRuta(), direccion.getMapa(), direccion.getPais(),
						direccion.getEstado(),direccion.getCalle(),direccion.getMunicipio(),direccion.getCodigoPostal(),direccion.getZona(),
						direccion.getCiudad(),direccion.getColonia(),direccion.getIdCliente()};

				super.jdbcTemplate.update(" UPDATE CLIENTES SET   Ruta=?, Mapa=?, Pais=?, Estado=?, Calle=?, Delegacion =?, CP =?, Zona =?, Ciudad = ? , Colonia = ? FROM Clientes  WHERE Clave =  ?", map);
				return true;
			}else { 
				//logger.info(" El objeto direccion, no trae el idcliente ");
				return false;				
				
			}
		}catch(RuntimeException e){
			//logger.info(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,direccion);
			return false;
		}
	}
	@Override
	public Boolean updateDireccionFacturacion(Direccion direccion)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("direccion", direccion);
			if (direccion.getIdCliente() != null) {
				Object[] params = {direccion.getPais(), direccion.getEstado(), direccion.getCalle(), direccion.getMunicipio(), direccion.getCodigoPostal(),
						direccion.getCiudad(), direccion.getColonia(), direccion.getIdCliente()};
				
				//logger.info(direccion.getPais()+ direccion.getEstado()+ direccion.getCalle()+ direccion.getMunicipio()+ direccion.getCodigoPostal()+
//						direccion.getCiudad()+direccion.getColonia()+ direccion.getIdCliente());
				
				super.jdbcTemplate.update(" UPDATE CLIENTES SET  RSPais = ?, RSEstado = ?, RSCalle = ?, RSDel = ?, RSCP = ?, RSCiudad = ?, RSColonia = ? FROM Clientes  WHERE Clave =  ?", map);
				return true;
			}else { 
			//	logger.error(" El objeto direccion, no trae el idcliente ");
				return false;				
				
			}
		}catch(Exception e){
	//		logger.error(e.getMessage());
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e,direccion);
			return false;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Direccion> getDireccionesTipoVisitaPorIdCliente(Long idCliente) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			String sQuery = "";
			
			sQuery = "SELECT top 1 * FROM Direccion Dir  "
					+ "   left join (select Tipo,FK01_Direccion from Horario ) as Ho on Ho.FK01_Direccion = Dir.PK_Direccion "
                    + "    WHERE  Dir.FK01_Cliente = " + idCliente;
			
			log.info(sQuery);
			return super.jdbcTemplate.query(sQuery, map, new DireccionRecoleccionRowMapper());
			
		} catch (Exception e) {
		//	logger.info("Error: " + e.getMessage());
			return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Direccion obtenerDireccionPorTipoyidCliente(Long idCliente) throws ProquifaNetException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCliente", idCliente);
			String sQuery = "";
			
			 sQuery = "  SELECT COALESCE(Dir.pais,HyL.pais COLLATE sql_latin1_general_cp1_ci_as)Pais, COALESCE(Dir.CP,HyL.CP COLLATE sql_latin1_general_cp1_ci_as)CP, " +
					 "\n COALESCE(Dir.Estado,HyL.Estado COLLATE sql_latin1_general_cp1_ci_as)Estado," +
					 "\n COALESCE(Dir.Municipio,HyL.Municipio COLLATE sql_latin1_general_cp1_ci_as)Municipio,Dir.Ciudad," +
					 "\n COALESCE(Dir.Calle,HyL.Calle COLLATE sql_latin1_general_cp1_ci_as)Calle,Dir.Colonia,COALESCE(HL.Lunes, HyL.Lunes) Lunes,COALESCE(HL.Martes, HyL.Martes) Martes, " +
					 "\n COALESCE(HL.Miercoles, HyL.Miercoles) Miercoles, COALESCE(HL.Jueves, HyL.Jueves) Jueves, " +
					 "\n COALESCE(HL.Viernes, HyL.Viernes) Viernes, COALESCE(HL.LuDe1 ,HYL.LuDe1 COLLATE sql_latin1_general_cp1_ci_as)  LuDe1, " +
					 "\n COALESCE(HL.LuA1 ,HYL.LuA1 COLLATE sql_latin1_general_cp1_ci_as) LuA1, " +
					 "\n COALESCE(HL.LuDe2 ,HYL.LuDe2 COLLATE sql_latin1_general_cp1_ci_as) LuDe2," +
					 "\n COALESCE(HL.LuA2 ,HYL.LuA2 COLLATE sql_latin1_general_cp1_ci_as) LuA2, " +
					 "\n COALESCE(HL.MaDe1 ,HYL.MaDe1 COLLATE sql_latin1_general_cp1_ci_as) MaDe1," +
					 "\n COALESCE(HL.MaA1 ,HYL.MaA1 COLLATE sql_latin1_general_cp1_ci_as) MaA1," +
					 "\n  COALESCE(HL.MaDe2 ,HYL.MaDe2 COLLATE sql_latin1_general_cp1_ci_as) MaDe2, " +
					 "\n COALESCE(HL.MaA2 ,HYL.MaA2 COLLATE sql_latin1_general_cp1_ci_as) MaA2, " +
					 "\n  COALESCE(HL.MiDe1 ,HYL.MiDe1 COLLATE sql_latin1_general_cp1_ci_as) MiDe1," +
					 "\n  COALESCE(HL.MiA1 ,HYL.MiA1 COLLATE sql_latin1_general_cp1_ci_as) MiA1, " +
					 "\n COALESCE(HL.MiDe2 ,HYL.MiDe2 COLLATE sql_latin1_general_cp1_ci_as) MiDe2, " +
					 "\n COALESCE(HL.MiA2 ,HYL.MiA2 COLLATE sql_latin1_general_cp1_ci_as)MiA2," +
					 "\n COALESCE(HL.JuDe1 ,HYL.JuDe1 COLLATE sql_latin1_general_cp1_ci_as) JuDe1," +
					 "\n COALESCE(HL.JuA1 ,HYL.JuA1 COLLATE sql_latin1_general_cp1_ci_as) JuA1," +
					 "\n COALESCE(HL.JuDe2 ,HYL.JuDe2 COLLATE sql_latin1_general_cp1_ci_as) JuDe2," +
					 "\n COALESCE(HL.JuA2 ,HYL.JuA2 COLLATE sql_latin1_general_cp1_ci_as) JuA2,COALESCE(HL.ViDe1 ,HYL.ViDe1 COLLATE sql_latin1_general_cp1_ci_as) ViDe1,COALESCE(HL.ViA1 ,HYL.ViA1 COLLATE sql_latin1_general_cp1_ci_as) ViA1,COALESCE(HL.ViDe2 ,HYL.ViDe2 COLLATE sql_latin1_general_cp1_ci_as) ViDe2, COALESCE(HL.ViA2 ,HYL.ViA2 COLLATE sql_latin1_general_cp1_ci_as)ViA2 " + 
					 "\n FROM Clientes CLI " +
					 "\n LEFT JOIN ( SELECT  TOP 1 DIR.*, HL.PK_Horario FROM Direccion DIR" +
					 "\n LEFT JOIN Horario HL ON HL.FK01_Direccion = DIR.PK_Direccion" +
					 "\n where DIR.FK01_Cliente = "+idCliente+ " and HL.Tipo = 'Entrega'" +
					 "\n ORDER BY PK_Direccion Desc" +
					 "\n ) DIR ON DIR.FK01_Cliente = CLI.Clave" +
					 "\n LEFT JOIN Horario HL ON HL.PK_Horario = DIR.PK_Horario" +
					 "\n LEFT JOIN (SELECT Top 1 * FROM HorarioyLugar WHERE Tipo = 'Entrega' AND idCliente ="+idCliente+ " ORDER BY idHorario DESC) HyL ON HyL.idCliente = CLI.Clave AND HyL.Tipo = 'Entrega' " +
					 "\n WHERE CLI.Clave = " + idCliente;
			
			 
			      log.info(sQuery);
			
			
//			 sQuery = " SELECT Dir.pais, Dir.CP,Dir.Estado,Dir.Municipio,Dir.Ciudad,Dir.Calle,Dir.Colonia,COALESCE(HL.Lunes, HyL.Lunes) Lunes,COALESCE(HL.Martes, HyL.Martes) Martes, COALESCE(HL.Miercoles, HyL.Miercoles) Miercoles, COALESCE(HL.Jueves, HyL.Jueves) Jueves, COALESCE(HL.Viernes, HyL.Viernes) Viernes, COALESCE(HL.LuDe1 ,HYL.LuDe1 COLLATE sql_latin1_general_cp1_ci_as)  LuDe1, COALESCE(HL.LuA1 ,HYL.LuA1 COLLATE sql_latin1_general_cp1_ci_as) LuA1, " +
//                      "\n COALESCE(HL.LuDe2 ,HYL.LuDe2 COLLATE sql_latin1_general_cp1_ci_as) LuDe2,COALESCE(HL.LuA2 ,HYL.LuA2 COLLATE sql_latin1_general_cp1_ci_as) LuA2, COALESCE(HL.MaDe1 ,HYL.MaDe1 COLLATE sql_latin1_general_cp1_ci_as) MaDe1,COALESCE(HL.MaA1 ,HYL.MaA1 COLLATE sql_latin1_general_cp1_ci_as) MaA1,COALESCE(HL.MaDe2 ,HYL.MaDe2 COLLATE sql_latin1_general_cp1_ci_as) MaDe2, COALESCE(HL.MaA2 ,HYL.MaA2 COLLATE sql_latin1_general_cp1_ci_as) MaA2, " + 
//                      "\n COALESCE(HL.MiDe1 ,HYL.MiDe1 COLLATE sql_latin1_general_cp1_ci_as) MiDe1,COALESCE(HL.MiA1 ,HYL.MiA1 COLLATE sql_latin1_general_cp1_ci_as) MiA1,COALESCE(HL.MiDe2 ,HYL.MiDe2 COLLATE sql_latin1_general_cp1_ci_as) MiDe2, COALESCE(HL.MiA2 ,HYL.MiA2 COLLATE sql_latin1_general_cp1_ci_as)MiA2,COALESCE(HL.JuDe1 ,HYL.JuDe1 COLLATE sql_latin1_general_cp1_ci_as) JuDe1,COALESCE(HL.JuA1 ,HYL.JuA1 COLLATE sql_latin1_general_cp1_ci_as) JuA1,COALESCE(HL.JuDe2 ,HYL.JuDe2 COLLATE sql_latin1_general_cp1_ci_as) JuDe2,COALESCE(HL.JuA2 ,HYL.JuA2 COLLATE sql_latin1_general_cp1_ci_as) JuA2,COALESCE(HL.ViDe1 ,HYL.ViDe1 COLLATE sql_latin1_general_cp1_ci_as) ViDe1,COALESCE(HL.ViA1 ,HYL.ViA1 COLLATE sql_latin1_general_cp1_ci_as) ViA1,COALESCE(HL.ViDe2 ,HYL.ViDe2 COLLATE sql_latin1_general_cp1_ci_as) ViDe2, COALESCE(HL.ViA2 ,HYL.ViA2 COLLATE sql_latin1_general_cp1_ci_as)ViA2 " +
//                      "\n FROM Clientes CLI " +
//                      "\n LEFT JOIN (SELECT TOP 1 * FROM Direccion WHERE FK01_Cliente = "+idCliente+ " ORDER BY PK_Direccion Desc ) DIR ON DIR.FK01_Cliente = CLI.Clave " +
//                      "\n LEFT JOIN Horario HL ON HL.FK01_Direccion = DIR.PK_Direccion AND HL.Tipo = 'Entrega' " +
//                      "\n LEFT JOIN (SELECT Top 1 * FROM HorarioyLugar WHERE Tipo = 'Entrega' AND idCliente =" + idCliente + " ORDER BY idHorario DESC) HyL ON HyL.idCliente = CLI.Clave AND HyL.Tipo = 'Entrega' " +
//                      "\n WHERE CLI.Clave = " + idCliente;
			
			
			
			return (Direccion) super.jdbcTemplate.queryForObject(sQuery,map, new DireccionHorarioDeClienteRowMapper());
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}

}
