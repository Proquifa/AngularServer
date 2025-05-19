/**
 * 
 */
package com.proquifa.net.persistencia.ventas.visitas.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.Referencia;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.visitas.DocumentoSolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.visitas.SolicitudVisitaDAO;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.IdsSolicitudVisitaRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.ReferenciaSolicitudRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.SolicitudVisitaRowMapper;

/**
 * @author ymendez
 *
 */
@Repository
public class SolicitudVisitaDAOImpl extends DataBaseDAO implements SolicitudVisitaDAO {

	private Funcion util=null;
	
	final Logger log = LoggerFactory.getLogger(SolicitudVisitaDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<SolicitudVisita> findSolicitudesVisita(String parametros, List<NivelIngreso> niveles) {
		util=new Funcion();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parametros", parametros);
			map.put("niveles", niveles);
			StringBuilder query = new StringBuilder("SELECT ")
					.append(" SolicitudVisita.*,")
					.append(" CASE")	
					.append(" WHEN (").append(util.obtenerTiempoTranscurridoSQL("SolicitudVisita.FechaDeseadaRealizacion", "SYSDATETIME()","Horas")) 
					.append(")<=72 ")
					.append(" THEN 'ET' ")
					.append(" ELSE 'FT' ")
					.append(" END TiempoRealizacion,")
					.append(" CASE")
					.append(" WHEN (SYSDATETIME()>SolicitudVisita.FechaDeseadaRealizacion) THEN ").append(util.obtenerTiempoTranscurridoSQL("SolicitudVisita.FechaDeseadaRealizacion","SYSDATETIME()", "Dias"))
					.append(" ELSE '0'")
					.append(" END DiasAtraso,")
					.append(" Clientes.Nombre AS NombreCliente, Clientes.Pais AS PaisCliente, Clientes.Ruta AS RutaCliente, Clientes.Industria AS IndustriaCliente, Clientes.Rol AS RolCliente, Clientes.Sector AS SectorCliente,")
					.append(  util.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NivelIngresoCliente,")
					.append(" Contactos.Titulo AS TituloContacto,Contactos.eMail AS eMailContacto, Contactos.Contacto AS NombreContacto, Contactos.Puesto AS PuestoContacto, Contactos.Depto AS DepartamentoContacto, Contactos.NivelDecision AS NDecisionContacto,")
					.append(" HorarioyLugar.Calle AS CalleDireccion, HorarioyLugar.Municipio AS MunicipioDireccion, HorarioyLugar.Pais AS PaisDireccion, HorarioyLugar.Estado AS EstadoDireccion, HorarioyLugar.CP AS CPDireccion, HorarioyLugar.ZonaMensajeria AS ZonaDireccion,")
					.append(" Empleados.Nombre AS NombreEmpleado, Empleados.Departamento AS DeptoEmpleado,")
					.append(" CASE ")
					.append(" WHEN  (SolicitudVisita.FK04_Visita IS NULL) THEN 'ND'")
					.append(" ELSE Visita.Folio")
					.append(" END FolioVisita," )
					.append(" CASE ")
					.append(" WHEN  (SolicitudVisita.FK05_Visita_Origen IS NULL) THEN 'ND'")
					.append(" ELSE VisitaOrigen.Folio")
					.append(" END FolioVisitaOrigen, Visita.FechaEstimadaRealizacion AS FechaEstRealizacionVisita, Visita.FechaUltimaActualizacion AS FechaUltimaActualizacionVisita,")
					.append(" CASE")
					.append(" WHEN (Select COUNT(1) from Referencia where FK03_SolicitudVisita=SolicitudVisita.PK_SolicitudVisita)>0 THEN 1")
					.append(" ELSE 0")
					.append(" END existeReferencia")
					.append(" FROM SolicitudVisita")
					.append(" LEFT JOIN Visita  ON SolicitudVisita.FK04_Visita=Visita.PK_Visita")
					.append(" LEFT JOIN Visita AS VisitaOrigen ON SolicitudVisita.FK05_Visita_Origen=VisitaOrigen.PK_Visita")
					.append(" LEFT JOIN Clientes ON SolicitudVisita.FK01_Cliente=Clientes.Clave")
					.append(" LEFT JOIN Empleados ON SolicitudVisita.FK03_Empleado=Empleados.Clave")
					.append(" LEFT JOIN Contactos ON SolicitudVisita.FK02_Contacto=Contactos.idContacto")
					.append(" LEFT JOIN HorarioyLugar ON Contactos.FK03_Direccion=HorarioyLugar.idHorario")
					.append(" LEFT JOIN (")
					.append(" SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar    ")
					.append(" WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F  LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha    ")
					.append(" WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )")
					.append(" AS NIVEL ON NIVEL.Cliente = Clientes.Clave")
					.append(" LEFT JOIN (")
					.append(" SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C    ")
					.append(" LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente")
					.append(" ) AS CO ON CO.Cliente = C.Nombre    ")
					.append(" LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) ")
					.append(" AS PED ON PED.idCliente = C.Clave)    ")
					.append(" AS OPER ON OPER.Clave = Clientes.Clave")
					.append(" INNER JOIN (Select Docto,Partida from Pendiente where FFin IS NULL) AS PENDIENTE ON ")
					.append(" (PENDIENTE.Docto=CONVERT(VARCHAR(40),SolicitudVisita.PK_SolicitudVisita) AND PENDIENTE.Partida=SolicitudVisita.Folio COLLATE Modern_Spanish_CI_AS)")
					.append(parametros)
					.append(" ORDER BY SolicitudVisita.Fecha DESC");
			List<SolicitudVisita> solicitudes = super.jdbcTemplate.query(query.toString(), new SolicitudVisitaRowMapper());
			return solicitudes;
		} catch (DataAccessException e) {
			//logger.info("Error en la consulta"+e.getMessage());
			return Collections.emptyList();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Referencia> findReferenciaSolicitud(Long idSolicitudVisita) {
		try {
			return super.jdbcTemplate.query("SELECT PK_Referencia,Nombre FROM Referencia WHERE FK03_SolicitudVisita="+idSolicitudVisita, new ReferenciaSolicitudRowMapper());
		} catch (DataAccessException e) {
			//logger.info("Error en la consulta"+e.getMessage());
			return Collections.emptyList();
		}
	}
	
	@Override
	public Long insertarSolicitudVisita(SolicitudVisita solicitudVisita, Folio folio) {
		
		//logger.info("Entrando a insertar Solicitud de Visita");
		//logger.info("IDCliente: "+solicitudVisita.getCliente().getIdCliente());
		//logger.info("IDContacto: "+solicitudVisita.getContacto().getIdContacto());
		//logger.info("fechaDeseadaRealizacion: "+solicitudVisita.getFechaDeseadaRealizacion());
		//logger.info("Urgencia: "+solicitudVisita.getUrgencia());
		//logger.info("Justificacion: "+solicitudVisita.getJustificacion());
		//logger.info("IdEmpleado: "+solicitudVisita.getEmpleado().getIdEmpleado());
		//logger.info("FolioCompleto: "+folio.getFolioCompleto());
		
		
		
//		Object[] params = {new Date(), solicitudVisita.getCliente().getIdCliente(), solicitudVisita.getContacto().getIdContacto(),
//			solicitudVisita.getFechaDeseadaRealizacion(), solicitudVisita.getUrgencia(), solicitudVisita.getJustificacion(),
//			solicitudVisita.getEmpleado().getIdEmpleado(), folio.getFolioCompleto()};
		
		StringBuilder sbQuery = new StringBuilder("INSERT INTO SolicitudVisita(Fecha,FK01_Cliente,FK02_Contacto,");
		sbQuery.append("FechaDeseadaRealizacion,Urgencia,Justificacion,FK03_Empleado,Folio) VALUES(?,?,?,?,?,?,?,?)");

		//logger.info(super.jdbcTemplate.update(sbQuery.toString(), params));
		//logger.info(super.jdbcTemplate.queryForLong("SELECT IDENT_CURRENT ('SolicitudVisita')"));
		return super.queryForLong("SELECT IDENT_CURRENT ('SolicitudVisita')");
	}

	
	public Boolean descartarSolicitudVisita(Long idSolicitudVisita,
			String justificacion) {
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("justificacion", justificacion);
			map.put("idSolicitudVisita", idSolicitudVisita);
			
			Object[] params = { justificacion, idSolicitudVisita};
			StringBuilder sbQuery = new StringBuilder("UPDATE SolicitudVisita SET JustificacionDescartada = ? ");
			sbQuery.append("WHERE PK_SolicitudVisita = ?");
			
			//logger.info(sbQuery.toString());
			super.jdbcTemplate.update(sbQuery.toString(),map);
		}catch(Exception e){
			//logger.info(e.getMessage());
			return false;
		}
		return true;
	}


	public Boolean agruparSolicitudes(List<Integer> solicitudesVisitas,
			Long idVisita) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("idVisita", idVisita);
			map.put("solicitudesVisitas", solicitudesVisitas);
			
			
			for (Integer idSolicitud: solicitudesVisitas){
				Object[] params = {idVisita, idSolicitud};
				StringBuilder sbQuery = new StringBuilder("UPDATE SolicitudVisita SET FK04_Visita= ? WHERE SolicitudVisita.PK_SolicitudVisita= ?");
				//logger.info("UPDATE SolicitudVisita SET FK04_Visita="+idVisita+" WHERE SolicitudVisita.PK_SolicitudVisita="+idSolicitud);
				super.jdbcTemplate.update(sbQuery.toString(), map);
			}
		}catch (Exception e) {
			//logger.info(e.getMessage());
			return false;
		}

		return true;
	}


	public String buscarFolioxId(Long idSolicitudVisita) { 
		Object[] param = {idSolicitudVisita};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idSolicitudVisita", idSolicitudVisita);
		
		StringBuilder sbQuery = new StringBuilder("select Folio from SolicitudVisita where PK_SolicitudVisita = ?");
		return (String) super.jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
	}


	@SuppressWarnings("unchecked")
	public List<Long> obtenerSolicitudesxVisita(Long idVisita) {
		Object[] param = {idVisita};
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("idVisita", idVisita);
	
		
		StringBuilder sbQuery = new StringBuilder("SELECT FK02_SolicitudVisita FROM Relacion_Solicitud_Visita WHERE FK01_Visita = ? ");
		return super.jdbcTemplate.query(sbQuery.toString(), map, new IdsSolicitudVisitaRowMapper());
	}


	public Boolean actualiarSolicitudVisita(SolicitudVisita solicitudVisita) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("solicitudVisita", solicitudVisita);
		
		
		Object[] params = {solicitudVisita.getAsunto(), solicitudVisita.getArgumento(), solicitudVisita.getIdSolicitudVisita() };
		
		StringBuilder sbQuery = new StringBuilder("UPDATE SolicitudVisita set Asunto=?, Argumento=? WHERE PK_SolicitudVisita=?");
		super.jdbcTemplate.update(sbQuery.toString(), map);
		return true;
	}
	
	public int insertSolicitudVisita(SolicitudVisita solicitudVisita,String tipo) throws ProquifaNetException {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("solicitudVisita", solicitudVisita);
			map.put("tipo", tipo);
			
			
			
			Funcion funcion = new Funcion();
			String query = "";
			if(tipo.compareTo("solicitud")==0){
				Object[] params = {funcion.obtenerFechaConFormato("yyyyMMdd", solicitudVisita.getFechaSolicitud()), solicitudVisita.getMotivo(),solicitudVisita.getIdCliente(),
						solicitudVisita.getIdContacto(),solicitudVisita.getIdEmpleado(),solicitudVisita.getAsunto(),tipo};
				
				query = "INSERT INTO SolicitudVisita (Fecha,FRequerida,Motivo,FK01_Cliente,FK02_Contacto,FK03_Empleado,Asunto,EstadoFlujo,Tipo) "
						+ "VALUES (GETDATE(),?,?,?,?,?,"
						+ " ?, 'PoolVisitas',? )";
				
				super.jdbcTemplate.update(query,map);
			}else if(tipo.compareTo("crm")==0){
				Object[] params = {funcion.obtenerFechaConFormato("yyyyMMdd", solicitudVisita.getFechaSolicitud()), solicitudVisita.getMotivo(),solicitudVisita.getIdCliente(),
						solicitudVisita.getIdEmpleado(),solicitudVisita.getAsunto(),tipo};
				
				query = "INSERT INTO SolicitudVisita (Fecha,FRequerida,Motivo,FK01_Cliente,FK03_Empleado,Asunto,EstadoFlujo,Tipo) "
						+ "VALUES (GETDATE(),?,?,?,?,"
						+ " ?, 'PoolVisitas',? )";
				
				super.jdbcTemplate.update(query,map);
			}
			
			log.info(query);
			return super.queryForInt("SELECT IDENT_CURRENT ('SolicitudVisita')");
			
		} catch (Exception e) {
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}
	
	public Integer insertDocumentoSolicitudVisita(DocumentoSolicitudVisita documento,int idSolicitud) throws ProquifaNetException{
		try {
	Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("documento", documento);
			map.put("idSolicitud", idSolicitud);
			Object[] params = {idSolicitud,documento.getNombre(),documento.getTitulo(),documento.getDescripcion()};
			StringBuilder sbQuery = new StringBuilder("INSERT INTO SolicitudVisitaDocumentacion (FK01_SolicitudVisita,Nombre,Titulo,Descripcion) VALUES "
					+ "( ? , ? , ? , ? )");
			super.jdbcTemplate.update(sbQuery.toString(),map);
			return super.queryForInt("SELECT IDENT_CURRENT ('SolicitudVisitaDocumentacion')");
		} catch (Exception e) {
			return 0;
		}
	}


}