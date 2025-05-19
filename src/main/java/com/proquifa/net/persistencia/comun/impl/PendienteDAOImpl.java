/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.proquifa.net.modelo.comun.ActividadPendiente;
import com.proquifa.net.modelo.comun.Pendiente;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.PendienteDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.ActividadPendienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.PendienteNotificacionRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.PendienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.PendienteTiempoProcesoRowMapper;
import com.proquifa.net.persistencia.despachos.impl.EmbalarDAOImpl;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ernestogonzalezlozada
 *
 */


@Repository
public class PendienteDAOImpl extends DataBaseDAO implements PendienteDAO {
	Funcion f = new Funcion();
	
	final Logger log = LoggerFactory.getLogger(PendienteDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.PendienteDAO#obtenerPendientesPorUsuario(java.lang.String, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Pendiente> obtenerPendientesPorUsuario(String usuario,
			String tipoPendiente) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			map.put("tipoPendiente", tipoPendiente);
		
			List<Pendiente> resp = super.jdbcTemplate
					.query(
							"SELECT DISTINCT Cotizas.Cliente as Cliente,Cotizas.idCliente as idCliente,COUNT(Pendiente.Partida) AS NoPartidas FROM Pendiente,Cotizas " +
									"WHERE Tipo='Buscar producto F/S' AND Responsable='" + usuario + "' AND FFin IS NULL " +
									"AND Cotizas.Clave=Pendiente.Docto GROUP BY Cotizas.Cliente, Cotizas.idCliente ORDER BY Cliente ",map,
									new PendienteRowMapper());
			return resp;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nUsuario: "+usuario,"\nTipoPendiente: "+ tipoPendiente);
			return new ArrayList<Pendiente>();
		}
	}

	public long guardarPendiente(Pendiente pendiente) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pendiente", pendiente);
			
			Date fechaInicio = new Date();
			Object[] params =  {pendiente.getDocto(), fechaInicio, pendiente.getResponsable(), pendiente.getTipoPendiente(),pendiente.getPartida()};
			String query = "INSERT INTO pendiente (docto,finicio,responsable,tipo,partida) VALUES ";
			query += "(?, ?, ?, ?,?) ";
			super.jdbcTemplate.update(query, map);
			//logger.info("Docto: " + pendiente.getDocto());
			return super.queryForLong("SELECT IDENT_CURRENT ('pendiente')");
		}catch (RuntimeException e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e, pendiente);
			return 0L;
		}
	}
	
	@Override
	public long guardarPendiente_angular(Pendiente pendiente) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pendiente", pendiente);			
			Date fechaInicio = new Date();
			Map<String, Object> map2 = new HashMap <String, Object>();
			map2.put("docto", pendiente.getDocto());
			map2.put("fecha", fechaInicio);
			map2.put("responsable", pendiente.getResponsable());
			map2.put("tipoPendiente", pendiente.getTipoPendiente());
			map2.put("partida", pendiente.getPartida());
			String query = "INSERT INTO pendiente (docto,finicio,responsable,tipo,partida) VALUES ";
			query += "(:docto, :fecha, :responsable, :tipoPendiente, :partida) ";
 			super.jdbcTemplate.update(query, map2);
			log.info("Docto: " + pendiente.getDocto());
			return super.queryForLong("SELECT IDENT_CURRENT ('pendiente')");
		}catch (RuntimeException e) {
			e.printStackTrace();
			//cambiar
//			new Funcion().enviarCorreoAvisoExepcion(e, pendiente);
			return 0L;
		}
	}
	
	@Override
	public void borrarAsignarMensajero(String evento) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("evento", evento);
			String query = "DELETE FROM AsignarMensajero WHERE Evento = :evento ";
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int validarPendiente(Pendiente pendiente) {
		try{
			String query = "SELECT * FROM Pendiente WHERE Docto = :docto AND Partida = :partida AND Responsable = :responsable AND Tipo = :tipoPendiente";
			Map<String, Object> map = new HashMap <String, Object>();
			map.put("docto", pendiente.getDocto());
			map.put("responsable", pendiente.getResponsable());
			map.put("tipoPendiente", pendiente.getTipoPendiente());
			map.put("partida", pendiente.getPartida());
			
			List<Pendiente> resp = super.jdbcTemplate.query(query,map,new PendienteRowMapper());
			if(resp != null) {
				return resp.size();
			}else {
				return 0;
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public int validarPendienteAsignar(Pendiente pendiente) {
		try{
			String query = "SELECT * FROM Pendiente WHERE Docto = :docto AND Responsable = :responsable AND Tipo = :tipoPendiente AND FFin IS NULL";
			Map<String, Object> map = new HashMap <String, Object>();
			map.put("docto", pendiente.getDocto());
			map.put("responsable", pendiente.getResponsable());
			map.put("tipoPendiente", pendiente.getTipoPendiente());
			
			List<Pendiente> resp = super.jdbcTemplate.query(query,map,new PendienteRowMapper());
			if(resp != null) {
				return resp.size();
			}else {
				return 0;
			}
		}catch (RuntimeException e) {
			e.printStackTrace();
			return 0;
		}		
	}
	
	@Override
	public Boolean cerrarPendiente_angular(String docto,String partida,String tipo) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			map.put("partida",partida);
			map.put("docto",docto);
			StringBuilder sbQuery = new StringBuilder("UPDATE Pendiente SET FFin = GETDATE() ");
			sbQuery.append("WHERE Docto = :docto AND Tipo = :tipo AND FFin IS NULL ");
			if (partida != null)
				sbQuery.append("AND Partida = :partida");
			
			super.jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	public Boolean actualizarPendiente(Pendiente pendiente) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fechaFin", new Date());
			map.put("Folio", pendiente.getFolio());
			
			super.jdbcTemplate.update("UPDATE pendiente SET ffin = :fechaFin WHERE folio = :Folio", map);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}
	
	@Override
	public Boolean actualizarPendienteEnvioAlmacen(Pendiente pendiente, String tipo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fechaFin", new Date());
			map.put("Folio", pendiente.getFolio());
			
			super.jdbcTemplate.update("UPDATE pendiente SET ffin = :fechaFin WHERE folio = :Folio", map);
			
			Map<String, Object> map2 = new HashMap<String, Object>();
			map2.put("tipo", tipo);
			map2.put("Folio", pendiente.getFolio());
							
			StringBuilder query = new StringBuilder();
			query.append("UPDATE PPedidos SET Estado = :tipo WHERE idPPedido in ( \n");
			query.append("select PP.idPPedido FROM Pendiente as P \n");
			query.append("LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n");
			query.append("LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n");
			query.append("LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido \n");
			query.append("LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido \n");
			query.append("WHERE p.tipo = 'Por Enviar' AND p.Folio = :Folio \n");
			query.append("UNION \n");
			query.append("select ppCompleto.idPPedido FROM Pendiente as P \n");
			query.append("LEFT JOIN PackingList pl on pl.Folio COLLATE DATABASE_DEFAULT = p.Docto COLLATE DATABASE_DEFAULT \n");
			query.append("LEFT JOIN PPackingList ppl ON ppl.FK01_PackingList = pl.PK_PackingList \n");
			query.append("LEFT JOIN EmbalarPedido ep ON ep.PK_EmbalarPedido = ppl.FK02_EmbalarPedido \n");
			query.append("LEFT JOIN Ppedidos pp ON pp.idPPedido = ep.FK01_PPedido \n");
			query.append("LEFT JOIN Ppedidos ppCompleto ON ppCompleto.cpedido = pp.Cpedido \n");
			query.append("WHERE p.tipo = 'Por Enviar' AND p.Folio = :Folio \n");
			query.append("AND ppCompleto.Fabrica='Fletes') \n");
			log.info(query.toString());
			super.jdbcTemplate.update(query.toString(), map2);
			return true;
		} catch (RuntimeException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pendiente> obtenerPendientesPorEnviar(String tipo, String usuario) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo", tipo);
			map.put("usuario", usuario);
			String queryPendientes = "SELECT Correos.*, Clientes.nombre, pendiente.folio, pendiente.docto , pendiente.tipo FROM Correos,Pendiente, Clientes WHERE Pendiente.Tipo = '" + tipo + "' AND Pendiente.ffin IS NULL " +
					"AND Pendiente.Responsable = '" +  usuario + "' AND Pendiente.partida = Correos.idCorreo AND Correos.destino = Clientes.clave"; 
			List<Pendiente> resp = super.jdbcTemplate
					.query(
							queryPendientes,map,
							new PendienteNotificacionRowMapper());
			return resp;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nTipo: "+tipo);
			return new ArrayList<Pendiente>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pendiente> findPendientesPorEnviarTodos(String usuario) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			String queryPendientesTodos = "SELECT Correos.*, Clientes.nombre, pendiente.folio, pendiente.docto, pendiente.tipo, Clientes.Pais , Empleados.Nombre  FROM Correos,Pendiente, Clientes, Empleados WHERE Pendiente.ffin IS NULL " +
					"AND Pendiente.Responsable = '" +  usuario + "' AND Pendiente.partida = Correos.idCorreo AND Correos.destino = Clientes.clave AND Empleados.Usuario = Correos.Origen ORDER BY Correos.FechaInicio DESC";
			List<Pendiente> resp = super.jdbcTemplate
					.query(
							queryPendientesTodos,map, 
							new PendienteNotificacionRowMapper());
			return resp;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nUsuario: "+usuario);
			return  new ArrayList<Pendiente>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pendiente> obtenerPendientesPorEnviarFolio(String folioDocumento, String tipoPendiente, String usuario) {
		String query = "";
		Format formatter;
		formatter = new SimpleDateFormat("yyyyMMdd");
		Date fechaInicio = new Date();
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			map.put("tipoPendiente", tipoPendiente);
			map.put("folioDocumento", folioDocumento);
			
			if (folioDocumento.equals(" ")){
				query = "SELECT Correos.*, Clientes.Nombre, pendiente.folio, pendiente.docto, pendiente.tipo  FROM Correos,Pendiente, Clientes WHERE pendiente.tipo = '" + tipoPendiente  + "' AND Pendiente.FInicio BETWEEN '" + formatter.format(fechaInicio) +" 00:00' AND '" + formatter.format(fechaInicio)  + " 23:59' AND Correos.idCorreo = Pendiente.Partida " +
						"AND Pendiente.FFin IS NULL AND pendiente.responsable = '" + usuario +  "' AND correos.destino = clientes.clave  ORDER BY Correos.FechaInicio ASC"; 
			}else{
				query = "SELECT Correos.*,Clientes.Nombre, pendiente.folio, pendiente.docto, pendiente.tipo  FROM Correos,Pendiente, Clientes WHERE pendiente.tipo = '" + tipoPendiente  + "' AND Correos.idCorreo = Pendiente.Partida AND pendiente.docto LIKE '%" + folioDocumento + "%' " + 
						"AND Pendiente.FFin IS NULL AND correos.destino = clientes.clave  ORDER BY Pendiente.Finicio ASC"; 
			}
			List<Pendiente> resp = super.jdbcTemplate.query(query,map,  new PendienteNotificacionRowMapper());
			return resp;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFolioDocumento: "+folioDocumento, 
					"\nTipoPendiente: "+tipoPendiente,"\nUsuario: "+usuario);
			return new ArrayList<Pendiente>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pendiente> obtenerPendientesPorEnviar(String medio, String origen, String destino,
			String tipoPendiente, String usuario, String tiempo) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			map.put("tiempo", tiempo);
			map.put("tipoPendiente", tipoPendiente);
			map.put("destino", destino);
			map.put("origen", origen);
			map.put("medio", medio);
			
			Object[] params =  {tipoPendiente, usuario};
			StringBuffer query = new StringBuffer("SELECT Correos.*, Clientes.nombre, pendiente.folio, pendiente.docto, pendiente.tipo FROM Correos,Pendiente, Clientes WHERE pendiente.ffin IS NULL AND pendiente.tipo = ? AND pendiente.responsable = ? AND pendiente.partida = correos.idCorreo AND Correos.destino = Clientes.clave ");
			if(!tiempo.equals("--TODOS--") && !tiempo.equals(" ")){
				if(tiempo.toLowerCase().equals("en tiempo")){
					query.append("AND DATEDIFF(day, finicio, GETDATE()) <= 0 ");
				}else{
					query.append("AND DATEDIFF(day, finicio, GETDATE()) > 0 ");
				}
			}
			if(!medio.equals("--TODOS--") && !medio.equals(" ")){
				query.append("AND Correos.Medio = '" + medio + "' ");
			}
			if(!origen.equals("--TODOS--") && !origen.equals(" ")){
				query.append("AND correos.origen = '" + origen + "' ");
			}
			if(!destino.equals("--TODOS--") && !destino.equals(" ")){
				query.append("AND correos.destino = '" + destino  + "' ");
			}
			query.append("ORDER BY Pendiente.FInicio ASC ");
			List<Pendiente> resp = super.jdbcTemplate.query(query.toString(), map, new PendienteNotificacionRowMapper());
			return resp;
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nMedio: "+medio,"\nOrigen: "+origen,"\nDestino: "+destino,
					"\nTipoPendiente: "+tipoPendiente,"\nUsuario: "+usuario,"\nTiempo: "+tiempo);
			return new ArrayList<Pendiente>();
		}
	}

	public Long obtenerNumeroPendientes(String usuario, String tipo) {
		Object [] params = {usuario, tipo};
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			map.put("tipo", tipo);
			
			String query = "select COUNT(folio) as NumeroPendientes from Pendiente where FFin is null and responsable = ? and tipo = ?"; 
			return super.queryForLong(query, map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e,"\nUsuario: "+usuario,"\nTipo: "+tipo);
			return 0L;
		}
	}

	public void borrarPendiente(Pendiente pendiente) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Docto", pendiente.getDocto());
			map.put("TipoPendiente", pendiente.getTipoPendiente());

			Object [] params = {pendiente.getDocto(), pendiente.getTipoPendiente()};
			String query = "DELETE FROM pendiente WHERE docto = ? AND tipo = ? AND FFin IS NULL ";
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, pendiente);
		}
	}

	public Boolean actualizarResponsablePendiente(Pendiente pendiente) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Responsable", pendiente.getResponsable());
			map.put("Docto", pendiente.getDocto());
			String query =  "UPDATE pendiente SET responsable = '" + pendiente.getResponsable() + "' WHERE docto = '" + pendiente.getDocto() + "' ";
			super.jdbcTemplate.update(query,map);
			return true;
		}catch(RuntimeException rte){
			//logger.info(rte.getMessage());
			return false;
		}
	}
	public Boolean actualizarResponsablePendienteXId(Long idPendiente, String responsable){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPendiente",idPendiente);
			map.put("responsable", responsable);
			String query =  "UPDATE pendiente SET responsable = '" + responsable + "' WHERE folio = " + idPendiente + " ";
			super.jdbcTemplate.update(query,map);
			return true;
		}catch(RuntimeException rte){
			//logger.info(rte.getMessage());
			return false;
		}
	}

	public Pendiente obtenerPendienteXTipoDoctoResponsable(String docto, String responsable, String tipo) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("docto",docto);
			map.put("responsable",responsable);
			map.put("tipo",tipo);
			String query =  "SELECT * FROM Pendiente WHERE Docto = '" + docto + "' AND (Tipo = 'OTROS a trabajar' OR Tipo='Refacturación') ";			
			Pendiente pendiente = (Pendiente) super.jdbcTemplate.queryForObject(query,map,new PendienteRowMapper());
			return pendiente;
		}catch(RuntimeException rte){
			//			new Funcion().enviarCorreoAvisoExepcion(rte,"\nDocto: "+docto,"\nResponsable: "+responsable,"\nTipo: "+tipo);
			//			return new Pendiente();
			return null;
		}
	}

	public Long obtenerNumeroPendientes(String docto, String usuario,
			String tipo, Boolean abierto) {
		try{
			String query = "SELECT COUNT(Folio) AS NumeroPendientes FROM Pendiente WHERE Docto = '" + docto + "'";
			if(usuario != null){
				if(!usuario.isEmpty()){
					query += " AND Responsable = '" + usuario + "'";
				}
			}
			if(tipo != null){
				if(!tipo.isEmpty()){
					query += " AND Tipo = '" + tipo  + "'";
				}
			}
			if(abierto != null){
				if(abierto){
					query += " AND FFin IS NULL";
				}else{
					query += " AND FFin IS NOT NULL";
				}
			}
			Long numeroPendientes = super.queryForLong(query); 
			return numeroPendientes;
		}catch(RuntimeException e){
			return -1L;
		}

	}

	public Pendiente obtenerPendienteXTipoDocto(String docto, String tipo) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("docto",docto);
			map.put("tipo",tipo);
			String query =  "SELECT * FROM Pendiente WHERE Docto = '" + docto + "' AND Tipo = '" + tipo + "'";
			Pendiente pendiente = (Pendiente) super.jdbcTemplate.queryForObject(query,map, new PendienteRowMapper());
			return pendiente;
		}catch(RuntimeException rte){

			//			f.enviarCorreoAvisoExepcion(rte, docto,tipo);
			//			logger.info(rte.getMessage());
			//			return new Pendiente();
			return null;
		}
	}

	public Pendiente obtenerPendienteXFolio(Long folio) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio",folio);
			return (Pendiente) super.jdbcTemplate.queryForObject("SELECT * FROM Pendiente WHERE Folio = " + folio,map, new PendienteRowMapper());
		} catch (Exception e) {
			//			//logger.info("Error........" + e.getMessage());
			return null;
		}
	}

	public Date obtenerFechaRealizacion(String idRuta, String idEntrega, Long Factura, String fPor, String ruta, String cPedido){
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idRuta",idRuta);
			map.put("idEntrega",idEntrega);
			map.put("Factura",Factura);
			map.put("fPor",fPor);
			map.put("ruta",ruta);
			map.put("cPedido",cPedido);
			if(idRuta.equals("")){
				idRuta = "0";
			}
			if(ruta.equals("Local")){
				query = "SELECT TOP 1 Pendiente.FFin FROM Pendiente " +
						" INNER JOIN (SELECT FFIN  FROM Pendiente WHERE Docto='" + idEntrega + "' AND Tipo='A Ejecutar ruta' AND FFIN IS NOT NULL " + 
						") as c1 on Pendiente.FFin = c1.FFin " +
						"UNION " + 
						"SELECT FFin  FROM Pendiente WHERE Docto='" + idRuta + "' AND Tipo='A cerrar ruta' " +
						"UNION " +
						"SELECT FFIN  FROM Pendiente WHERE Docto='" + Factura + "' AND Partida='" + fPor + "' AND Tipo='Alistar env��o internacional'  ";
			}else{
				query = "SELECT top 1 Pendiente.FFIN,EnvioAlmacen.Guia, EnvioAlmacen.idGuia, EnvioAlmacen.Mensajeria, RutaDP.idEntrega, PFacturas.PPedido, PFacturas.CPedido " +
						"FROM Pendiente,EnvioAlmacen,RutaDP,PRutaDP,PFacturas WHERE PFacturas.CPedido='" + cPedido + "' AND PFacturas.Part=PRutaDP.Part AND PFacturas.Factura=PRutaDP.Factura " +
						"AND PRutaDP.idDP=RutaDP.idDP AND RutaDP.idEntrega=EnvioAlmacen.idEntrega AND Pendiente.FFin IS NOT NULL AND EnvioAlmacen.idEntrega=Pendiente.Docto";
			}

			return (Date)super.jdbcTemplate.queryForObject(query, map,Date.class);
		}catch (RuntimeException e) {
			return null;
		}
	}

	public Pendiente obtenerFechaPendienteFacturacion(String cPedido){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido",cPedido);
			String query = "SELECT TOP 1 pendiente.FInicio, pendiente.FFin FROM pendiente,pfacturas,rutadp,ppedidos WHERE  pendiente.tipo = 'a tramitar ruta' " + 
					"AND rutadp.idDP = pendiente.docto AND pfacturas.fpor = rutadp.fpor AND pfacturas.factura = rutadp.factura AND " +  
					"pfacturas.ppedido = ppedidos.part AND pfacturas.cpedido = ppedidos.cpedido AND ppedidos.cpedido = '" + cPedido + "' ORDER BY FInicio DESC";			
			return (Pendiente) super.jdbcTemplate.queryForObject(query, map, new PendienteTiempoProcesoRowMapper());
		} catch (RuntimeException e) {
			return null;
		}
	}

	public Pendiente obtenerFechaPendiente(Long idPCompra){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra",idPCompra);
			String query = "SELECT TOP 1 pendiente.FInicio, Pendiente.FFin FROM pendiente,pfacturas,rutadp,pcompras WHERE  pendiente.tipo = 'a tramitar ruta' "+ 
					"AND rutadp.idDP = pendiente.docto AND pfacturas.fpor = rutadp.fpor AND pfacturas.factura = rutadp.factura AND "+
					"pfacturas.ppedido = pcompras.ppedido AND pfacturas.idpcompra = pfacturas.idpcompra AND "+
					"pcompras.idpcompra = '" + idPCompra + "' AND FInicio IS NOT NULL ORDER BY FInicio DESC";
			return (Pendiente) super.jdbcTemplate.queryForObject(query,map, new PendienteTiempoProcesoRowMapper());

		} catch (RuntimeException e) {
			return null;
		}
	}

	public 	Date obtenerFechaPendienteSurtir(String cPedido){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido",cPedido);
			String query = "SELECT MAX(ffin) AS ffin FROM pendiente WHERE tipo = 'A surtir ruta' and docto in( SELECT iddp FROM rutadp WHERE factura " +
					"IN ( SELECT pfacturas.factura FROM pfacturas,ppedidos WHERE ppedidos.cpedido = pfacturas.cpedido AND ppedidos.part = pfacturas.ppedido "+ 
					"AND ppedidos.cpedido = '" + cPedido + "') AND fpor "+
					"IN (SELECT pfacturas.fpor FROM pfacturas,ppedidos WHERE ppedidos.cpedido = pfacturas.cpedido AND ppedidos.part = pfacturas.ppedido "+ 
					"AND ppedidos.cpedido = '" + cPedido+ "'))";
			return (Date) super.jdbcTemplate.queryForObject(query,map, Date.class);

		} catch (RuntimeException e) {
			return null;
		}
	}

	public 	Date obtenerFechaPendienteEntrega(String cPedido){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido",cPedido);
			String query = "SELECT COALESCE( " +
					"(select MAX(ffin) as FFin from pendiente where tipo = 'A cerrar ruta' and docto " + 
					"in ( select idEntrega from rutadp where factura " +
					"in (select pfacturas.factura from pfacturas,ppedidos where ppedidos.cpedido = pfacturas.cpedido and ppedidos.part = pfacturas.ppedido " + 
					"and ppedidos.cpedido = '" + cPedido + "') and fpor " +
					"in (select pfacturas.fpor from pfacturas,ppedidos where ppedidos.cpedido = pfacturas.cpedido and ppedidos.part = pfacturas.ppedido " + 
					"and ppedidos.cpedido = '" + cPedido + "'))) " + 
					", " +
					"(select MAX(ffin) as FFin from pendiente where tipo = 'A cerrar ruta' and docto " +
					"in ( select idRuta from rutadp where factura in ( select pfacturas.factura from pfacturas,ppedidos where ppedidos.cpedido = pfacturas.cpedido " + 
					"and ppedidos.part = pfacturas.ppedido and ppedidos.cpedido = '" + cPedido + "' ) and fpor " +
					"in (select pfacturas.fpor from pfacturas,ppedidos where ppedidos.cpedido = pfacturas.cpedido and ppedidos.part = pfacturas.ppedido " + 
					"and ppedidos.cpedido = '" + cPedido + "' ))) " + 
					") as FFin " ;
			return (Date)super.jdbcTemplate.queryForObject(query,map, Date.class);
		} catch (RuntimeException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ActividadPendiente> findActividadesXusuario(String Usuario) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Usuario",Usuario);
			return super.jdbcTemplate.query("SELECT Rol,Actividad AS Nombre,NULL AS Total FROM ActividadesPorFuncion AS AxF,Empleados AS e WHERE AxF.FK01_Funcion=e.FK01_Funcion AND e.Usuario='"+ Usuario +"'",map, new ActividadPendienteRowMapper());
		} catch (Exception e) {
			//logger.info("Error |findActividadesXusuario |DAO: "+ e.getMessage());
			return null;
		}
	}

	public Long finNoPendientesProductoAConfirmar(String tipo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			String query="";
			if(tipo.equals("Proveedores con PAC")){
				query="SELECT COUNT(*) AS TOTAL FROM PCotPharma AS PC WHERE (TProvee='R' OR TProvee='T') AND  (PC.Estado='En Pharma' OR PC.Estado='En Realizaci��n Pharma')";
			}else if(tipo.equals("Proveedores con PAC rechazados")){
				query="SELECT COUNT(*) AS TOTAL FROM PCotPharma AS PC WHERE  (TProvee='R' OR TProvee='T') AND  PC.Estado='En Pharma rechazada'";
			}
			Long numeroPendientes = super.queryForLong(query,map); 
			return numeroPendientes;
		} catch (Exception e) {
			//logger.info("ERROR | finNoPendientesProductoAConfirmar | DAO: " + e.getMessage());
			return -1L;
		}		
	}

	public Long finNoPendientesProformas(String tipo, Long idUsuario) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			map.put("idUsuario",idUsuario);
			String query="";
			if(tipo.equals("Por Colocar")){
				query="SELECT COUNT(*) AS TOTAL FROM Proforma, Proveedores " +
						"WHERE FechaEnvio IS NULL AND FK01_Empleado = " + idUsuario + " AND Proveedores.Clave = Proforma.FK01_Proveedor";
			}

			Long numeroPendientes = super.queryForLong(query, map); 
			return numeroPendientes;
		} catch (Exception e) {
			//logger.info("ERROR | finNoPendientesProformas | DAO: " + e.getMessage());
			return -1L;
		}
	}


	public Boolean validarPendienteAbierto(String docto, String responsable,
			String tipo) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tipo",tipo);
		map.put("docto",docto);
		map.put("responsable",responsable);
		Boolean existe = false;
		int registros = super.queryForInt("SELECT COUNT(*) AS Num FROM Pendiente WHERE Docto = '"+ docto +"' AND Tipo = '"+ tipo +"' AND Responsable = '"+ responsable +"' AND FFin IS NULL", map);
		if(registros > 0){
			existe = true;
		}else{
			existe = false;
		}
		return existe;
	}


	public Boolean cerrarPendiente(String docto,String partida,String tipo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			map.put("partida",partida);
			map.put("docto",docto);
			Date fechaCierre = new Date();
			Object [] params = {fechaCierre,docto,partida,tipo};
			super.jdbcTemplate.update("UPDATE Pendiente SET FFin=? WHERE Docto=? AND Partida=? AND Tipo=? AND FFin IS NULL", map);
			return true;
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return false;
		}
	}

	@Transactional
	public Boolean cerrarPendiente(String docto,String tipo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			map.put("docto",docto);
			
			String query = "UPDATE Pendiente SET FFin= GETDATE() WHERE Docto= '"+ docto +"' AND Tipo= '"+tipo+"' AND FFin IS NULL ";
			//logger.info(query);
			super.jdbcTemplate.update(query,map);
			return true;
		} catch (Exception e) {
			//logger.error("Error: "+ e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean reabrirPendiente(String docto, String tipo, String partida) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo",tipo);
			map.put("docto",docto);
			map.put("partida",partida);
			String query = "UPDATE Pendiente SET FFin= NULL , Partida = '"+ partida +"' WHERE Docto= '"+ docto +"' AND Tipo= '"+tipo+"' ";
			super.jdbcTemplate.update(query, map);
			return true;
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return false;
		}
	}
	
	
	public Boolean cerrarPendienteGestionarAvisoCambios(String docto,String partida ) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
		
			map.put("docto",docto);
			map.put("partida",partida);
			
			String query = "UPDATE Pendiente SET FFin= GETDATE() WHERE Partida= '"+partida+"' AND FFin IS NULL AND Tipo='AC por realizar'";
		//	logger.info(query);
			super.jdbcTemplate.update(query,map);
			return true;
		} catch (Exception e) {
		//	logger.error("Error: "+ e.getMessage());
			return false;
		}
	}
	

	
	@Override
	public Boolean insertaEnHistorialAvisosCam(long folio, String idPCompra,Date fee ) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("folio",folio);
			map.put("idPCompra",idPCompra);
			map.put("fee",fee);
			
			String query = "INSERT INTO HistorialPendientes(FolioPendiente,FEE,idPCompra) VALUES ('"+folio+"','"+fee+"','"+idPCompra+"')";
			//logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		} catch (Exception e) {
		//	logger.error("Error: "+ e.getMessage());
			return false;
		}
	}
	
	@Override
	public Boolean actualizarFfinProductosBO(Long idProducto, Boolean tipo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("UPDATE ProductoBO SET Ffin = \n");
			if(tipo) {
				sbQuery.append("GETDATE() WHERE FK01_Producto = :idProducto \n");
			} else if (!tipo) {
				sbQuery.append("NULL, FInicio = GETDATE() WHERE FK01_Producto = :idProducto \n");
			}
			map.put("idProducto", idProducto);
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	@Override
	public String mensajeroGDL(String idRuta) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idRuta", idRuta);
			String query = "SELECT Responsable FROM Pendiente WHERE Docto = :idRuta";
			return  (String)super.jdbcTemplate.queryForObject(query,map, String.class);
		} catch (Exception e) {
			return "";
		}
	}
	
}
