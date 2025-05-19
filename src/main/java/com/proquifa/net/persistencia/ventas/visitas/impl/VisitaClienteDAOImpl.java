package com.proquifa.net.persistencia.ventas.visitas.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Direccion;
import com.proquifa.net.modelo.comun.DocumentoAdjunto;
import com.proquifa.net.modelo.comun.Empleado;
import com.proquifa.net.modelo.comun.Folio;
import com.proquifa.net.modelo.comun.HallazgosAcciones;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.Cotizacion;
import com.proquifa.net.modelo.ventas.PartidaCotizacion;
import com.proquifa.net.modelo.ventas.Sprint;
import com.proquifa.net.modelo.ventas.requisicion.Requisicion;
import com.proquifa.net.modelo.ventas.visitas.AsistenciaDailyScrum;
import com.proquifa.net.modelo.ventas.visitas.DailyScrum;
import com.proquifa.net.modelo.ventas.visitas.ObtenerPrecios;
import com.proquifa.net.modelo.ventas.visitas.ObtenerVisitas;
import com.proquifa.net.modelo.ventas.visitas.ReportarVisita;
import com.proquifa.net.modelo.ventas.visitas.SolicitudVisita;
import com.proquifa.net.modelo.ventas.visitas.VisitaCliente;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.FolioDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.CotizacionReporteVisitaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.PartidaCotizacionReRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.ObtenerAsuntosRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.ObtenerPreciosRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.VisitaClienteDAO;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.AsignarSprintRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.AsistenciaDailyScrumRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.DailyScrumRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.DocumentoSolicitudVisitaRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.DocumentosReporteVisitaRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.HallazgosAccionesRowmapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.ObtenerDatosSeccionHallazgosRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.ObtenerDatosSeccionReporteRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.PlanificarSprintRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.PoolVisitasRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.ReportarVisitaRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.ResumenVisitaRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.SprintRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.VendedorVisitaClienteRowMapper;
import com.proquifa.net.persistencia.ventas.visitas.impl.mapper.VisitaClientePorSprinRowMapper;

@Repository
public class VisitaClienteDAOImpl extends DataBaseDAO implements VisitaClienteDAO {
	@Autowired
	FolioDAO folioDAO;

	private Funcion function;
	
	public VisitaClienteDAOImpl() {
		super();
		function = new Funcion();
	}
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	
	final Logger log = LoggerFactory.getLogger(VisitaClienteDAOImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudVisita> obtenerPoolVisitas(Long usuario)
			throws ProquifaNetException {

		try{
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			String campos ="SV.PK_SolicitudVisita,SV.Fecha,SV.FRequerida,SV.Motivo,SV.Asunto,SV.FK01_Cliente,CL.Nombre,CL.Pais,SV.FK02_Contacto, SV.FK03_Empleado,SV.Tipo,"
					+ "C.Contacto,C.Titulo,C.Puesto,c.Depto,c.eMail,C.Tel1,C.Extension1, Cl.Ruta, C.Celular, EMP.Nombre as Solicito, SV.FK03_Empleado,"
					+ "(SELECT COUNT(*) FROM SolicitudVisitaDocumentacion,SolicitudVisita WHERE PK_SolicitudVisita = FK01_SolicitudVisita AND PK_SolicitudVisita = SV.PK_SolicitudVisita) as NumDocumentos";

			String query= "SELECT "+campos+" FROM SolicitudVisita as SV " +
					"INNER JOIN Clientes as Cl ON Cl.Clave = SV.FK01_Cliente " +
					"LEFT JOIN Contactos as C ON SV.FK02_Contacto = C.idContacto "+
					"INNER JOIN Empleados as EMP ON EMP.Clave = SV.FK03_Empleado " +
					"WHERE SV.EstadoFlujo = 'PoolVisitas' AND CL.FK01_EV = "+usuario;

			//logger.info(query);
			List<SolicitudVisita> solicitudes = super.jdbcTemplate.query(query.toString(),map,  new PoolVisitasRowMapper());

			for (SolicitudVisita solicitudVisita : solicitudes) {
				query= "SELECT PK_SolicitudVisitaDocumentos,FK01_SolicitudVisita,Nombre,Titulo,Descripcion FROM SolicitudVisitaDocumentacion WHERE FK01_SolicitudVisita = "+solicitudVisita.getIdSolicitudVisita();
				solicitudVisita.setDocumentos(super.jdbcTemplate.query(query.toString(), new DocumentoSolicitudVisitaRowMapper(true)));
			}

			return solicitudes;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public Long registrarAgrupacionSolicitudVisitas(
			SolicitudVisita solicitud) throws ProquifaNetException {

		try{	

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("solicitud", solicitud);
			Object[] params = {solicitud.getFechaDeseadaRealizacion(),solicitud.getTipoVisita()};
			Folio folio = folioDAO.obtenerFolioPorConcepto("VisitaCliente", true);			
			String folioVIS = folio.getFolioCompleto();
			String query = "INSERT VisitaCliente (Folio, Telefono,Clasificacion_Cliente,Puesto_Contacto,Area_Contacto,Correo_Electronico,Fecha,FK01_Cliente,FK02_Contacto,FK03_Empleado,Etapa,Tipo,Asunto,Documentos,FechaEstimada,Tipo_Visita) " +
					"VALUES ('"+ folioVIS +"','"+ solicitud.getContacto().getTelefono() +"','"+ solicitud.getCliente().getNivelIngreso() +"','"+ solicitud.getContacto().getPuesto() +"','"+ solicitud.getContacto().getDepartamento() +
					"','"+ solicitud.getContacto().getEMail() +"',GETDATE(),"+ solicitud.getIdCliente() +","+ solicitud.getIdContacto()+","+ solicitud.getIdEmpleado() +",'PlanificarSprint','"+ solicitud.getTipoSolicitud() +
					"', '"+ solicitud.getAsunto() +"', '"+ solicitud.getNumDocumentos() +"' , ? , ?)";

			super.jdbcTemplate.update(query,map);
//			logger.info(query);
			return super.queryForLong("SELECT IDENT_CURRENT ('VisitaCliente')", map);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0L;
		}
	}

	@Override
	public Boolean actualizarVisitaClienteASolicitud(
			List<SolicitudVisita> solicitud, Long idVisitaCliente) throws ProquifaNetException {

		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("solicitud", solicitud);
			map.put("idVisitaCliente", idVisitaCliente);
			String condicion = "";
			for (int i = 0; i < solicitud.size(); i++) {
				if(i+1 == solicitud.size())
					condicion += " PK_SolicitudVisita = "+solicitud.get(i).getIdSolicitudVisita();
				else
					condicion += " PK_SolicitudVisita = "+solicitud.get(i).getIdSolicitudVisita()+" OR";
			}

			String query = "UPDATE SolicitudVisita SET FK04_VisitaCliente = "+ idVisitaCliente +", EstadoFlujo = 'PlanificarSprint' WHERE "+condicion;
			super.jdbcTemplate.update(query, map);
			//logger.info(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> obtenerVisitasClienteAgrupadas(Long usuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			String query= "SELECT SUM(CASE WHEN VC.Tipo = 'solicitud' THEN 1 ELSE 0 END) NumSolicitud, "+
					"SUM(CASE WHEN VC.Tipo = 'crm' THEN 1 ELSE 0 END) NumCRM, VC.FK01_Cliente, C.Nombre " + 
					"FROM VisitaCliente as VC "+ 
					"INNER JOIN Clientes as C ON VC.FK01_Cliente = C.Clave "+
					"WHERE VC.FK03_Empleado = "+ usuario +" AND VC.Etapa = 'PlanificarSprint' GROUP BY VC.FK01_Cliente, C.Nombre ";

			//logger.info(query);
			List<VisitaCliente> solicitudes = super.jdbcTemplate.query(query.toString(), map, new PlanificarSprintRowMapper(true));
			return solicitudes;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> consultarVisitasClientePorUsuario(Long usuario)
			throws ProquifaNetException {

		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			String query= "SELECT VC.FK01_Cliente ,CL.Nombre, VC.Asunto, VC.FechaEstimada, VC.Tipo, CL.Ruta,VC.Clasificacion_Cliente, VC.Documentos, VC.PK_Formulario, VC.FK02_Contacto, CO.Contacto, CO.Apellidos, CO.Tel1, CO.eMail, CO.Puesto, CO.Depto, VC.PK_Formulario , VC.Estado, VC.SprintAsignado,E.Usuario,E.Nombre as nombreUsuario,VC.Reporte,"+
					"SUM(CASE WHEN VC.Tipo = 'solicitud' THEN 1 ELSE 0 END) NumSolicitud, "+ 
					"SUM(CASE WHEN VC.Tipo = 'crm' THEN 1 ELSE 0 END) NumCRM FROM VisitaCliente as VC "+ 
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					"INNER JOIN Contactos as CO ON VC.FK02_Contacto = CO.idContacto "+
					"INNER JOIN Empleados as E ON E.Clave = VC.FK03_Empleado "+
					"WHERE  VC.FK03_Empleado = "+usuario+" AND Etapa = 'PlanificarSprint' "
				+ "GROUP BY CL.Nombre,VC.Asunto,VC.FechaEstimada ,CL.Ruta,VC.Clasificacion_Cliente,VC.Tipo, VC.FK01_Cliente,VC.Documentos,VC.PK_Formulario,VC.FK02_Contacto, CO.Contacto, CO.Apellidos, CO.Tel1, CO.eMail, CO.Puesto, CO.Depto, VC.PK_Formulario,VC.Estado, VC.SprintAsignado,E.Usuario,E.Nombre,VC.Reporte ";

		//	logger.info(query);
			List<VisitaCliente> solicitudes = super.jdbcTemplate.query(query.toString(),map,  new PlanificarSprintRowMapper(false));
			return solicitudes;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	//	@Override
	//	public List<VisitaCliente> consultarSolicitudesVisitaPorVisitaCliente(
	//			Long idVisitaCliente) throws ProquifaNetException {
	//		try{
	//			String query= "";
	//			
	//			logger.info(query);
	//			List<VisitaCliente> solicitudes = super.jdbcTemplate.query(query.toString(), new PlanificarSprintRowMapper(false));
	//			return solicitudes;
	//		}catch(Exception e){
	//			e.printStackTrace();
	//			new Funcion().enviarCorreoAvisoExepcion(e);
	//			return Collections.emptyList();
	//		}
	//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sprint> consultarUltimosSprintsAbiertos()
			throws ProquifaNetException {
		try{
			String query= "SELECT TOP 5 PK_Sprint,Anio,FInicio,FFin,Estado,NumeroSprint FROM Sprint WHERE Estado = 'Abierto'";

		//	logger.info(query);
			List<Sprint> sprint = super.jdbcTemplate.query(query.toString(), new SprintRowMapper());
			return sprint;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public Boolean updateVisitaClienteGuardadasPlanificar(Long usuario,Long idSprint,String condicion)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicion", condicion);
			map.put("usuario", usuario);
			map.put("idSprint", idSprint);
			if(idSprint > 0 && condicion.length() > 0){
				String query = "UPDATE VisitaCliente SET Estado = 'En Planificacion', SprintAsignado = "+ idSprint +" WHERE "+condicion;
				super.jdbcTemplate.update(query, map);
			//	logger.info(query);
			}
			//			PK_Formulario = 0 OR PK_Formulario = 0

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}

	}

	@Override
	public Boolean limpiarVisitasGuardadasPlanificar(Long usuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
	
			map.put("usuario", usuario);
			String queryLimpiar = "UPDATE VisitaCliente SET Estado = NULL, SprintAsignado = NULL WHERE Etapa = 'PlanificarSprint' AND Estado = 'En Planificacion' AND FK03_Empleado = "+usuario;
			super.jdbcTemplate.update(queryLimpiar, map);
			//logger.info(queryLimpiar);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public Boolean updatePlanificarSprintEV(Long usuario, Long idSprint,
			String condicion) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("usuario", usuario);
			map.put("idSprint", idSprint);
			map.put("condicion", condicion);
			if(idSprint > 0 && condicion.length() > 0){
				String query = "UPDATE VisitaCliente SET Estado = 'Planificado', Etapa = 'AsignarSprint' ,SprintAsignado = "+ idSprint +" WHERE "+condicion;
				super.jdbcTemplate.update(query, map);
			//	logger.info(query);
			}

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SolicitudVisita> consultarSolicitudesVisitaPorVisitaCliente(
			Long idVisitaCliente) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idVisitaCliente", idVisitaCliente);
	
			String query= "SELECT SV.PK_SolicitudVisita,SV.Motivo,SV.Asunto,SV.tipo,SV.FK04_VisitaCliente,E.Nombre,SV.FRequerida,SV.FK02_Contacto,SV.FK01_Cliente,SV.FK03_Empleado FROM SolicitudVisita as SV "
					+ "INNER JOIN Empleados as E ON SV.FK03_Empleado = E.Clave "
					+ "WHERE FK04_VisitaCliente = "+idVisitaCliente;
			List<SolicitudVisita> visitas = super.jdbcTemplate.query(query.toString(), map, new DocumentoSolicitudVisitaRowMapper(false));
	//		logger.info(query);

			for (SolicitudVisita solicitudVisita : visitas) {
				query= "SELECT PK_SolicitudVisitaDocumentos,FK01_SolicitudVisita,Nombre,Titulo,Descripcion FROM SolicitudVisitaDocumentacion WHERE FK01_SolicitudVisita = "+solicitudVisita.getIdSolicitudVisita();
				solicitudVisita.setDocumentos(super.jdbcTemplate.query(query.toString(), new DocumentoSolicitudVisitaRowMapper(true)));
			}
		//	logger.info(query);
			return visitas;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public int consultaContadorPendientesPoolVisitas(Long usuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			String query= "SELECT COUNT(*) as Indicador FROM SolicitudVisita as SV INNER JOIN Clientes as Cl ON Cl.Clave = SV.FK01_Cliente  "
					+ "LEFT JOIN Contactos as C ON SV.FK02_Contacto = C.idContacto  "
					+ "INNER JOIN Empleados as EMP ON EMP.Clave = SV.FK03_Empleado WHERE SV.EstadoFlujo = 'PoolVisitas' AND CL.FK01_EV = "+usuario;
			return super.queryForInt(query, map);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@Override
	public int consultaContadorPendientesPlanificarSprint(Long usuario)
			throws ProquifaNetException {
		try{
			String query= "SELECT SUM(NumSolicitud)+SUM(NumCRM) FROM( "
						+ "	SELECT SUM(CASE WHEN VC.Tipo = 'solicitud' THEN 1 ELSE 0 END) NumSolicitud, "
						+ "	SUM(CASE WHEN VC.Tipo = 'crm' THEN 1 ELSE 0 END) NumCRM, VC.FK01_Cliente, C.Nombre "
						+ "	FROM VisitaCliente as VC "
						+ "		INNER JOIN Clientes as C ON VC.FK01_Cliente = C.Clave "
						+ "	WHERE VC.FK03_Empleado = '"+ usuario +"' AND VC.Etapa = 'PlanificarSprint' GROUP BY VC.FK01_Cliente, C.Nombre "
						+ ") as a ";
			log.info(query);
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> consultasVisitasClienteDeCoordinador()
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", null);
			String query= "SELECT EMP.Nombre,EMP.Usuario,VC.Tipo,VC.Clasificacion_Cliente,CL.Ruta,VC.FechaEstimada,VC.Asunto,VC.Documentos,CO.Contacto,CO.Tel1,CO.Puesto,"
					+ "CO.Depto,CO.eMail,VC.PK_Formulario,CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.FK03_Empleado,S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint,VC.Creditos,VC.Valor,VC.Estado,VC.Fecha_CheckIn,VC.Reporte "
					+ " FROM VisitaCliente as VC "
					+ "INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "
					+ "INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "
					+ "INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "
					+ "INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'Abierto') as S ON S.PK_Sprint = VC.SprintAsignado "
					+ "WHERE VC.Estado = 'Planificado' AND EMP.Fase>0";
			
			List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(),map,  new AsignarSprintRowMapper(false));
		//	logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public Boolean asignarVisitasSprintEV(String condicionValor,String condicionCredito, 
			String condicionLlaves,String condicionAsignar)
					throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicionAsignar", condicionAsignar);
			map.put("condicionValor", condicionValor);
			map.put("condicionCredito", condicionCredito);
			map.put("condicionLlaves", condicionLlaves);
			String coma="";
			if(condicionAsignar.length()>0) coma =",";
			String query = "UPDATE VisitaCliente SET Creditos = "+ 
					"CASE "+ 
					condicionCredito+ 
					"END,  "+
					"Valor = CASE "+ 
					condicionValor+
					"END"+coma+" "+
					condicionAsignar+
					"WHERE PK_Formulario in ("+ condicionLlaves +")";
			super.jdbcTemplate.update(query, map);
		//	logger.info(query);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public Boolean limpiarVisitasSinPlanificar(String condicionLlaves) 
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicionLlaves", condicionLlaves);
			String query = "UPDATE VisitaCliente SET Etapa = 'PlanificarSprint', Estado = NULL, Creditos = 0, Valor = 0 "
					+ "WHERE PK_Formulario in ("+condicionLlaves+")";
			super.jdbcTemplate.update(query, map);
	//		logger.info(query);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public int consultaContadorPendientesAsignarSprint()
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicionLlaves", null);
			String query= "SELECT COUNT(Distinct EMP.Nombre) "+
					"FROM VisitaCliente as VC "+ 
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+
					"WHERE VC.Estado = 'Planificado' AND EMP.FK01_Funcion=3 and EMP.Fase>0";
			return super.queryForInt(query, map);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> consultarVisitasAsignadasPorEV(Long usuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usuario", usuario);
			
			log.info("id_USAURIO EN DAO-->"+ usuario);
			String query= "SELECT EMP.Nombre,EMP.Usuario,VC.Tipo,VC.Clasificacion_Cliente,CL.Ruta,VC.FechaEstimada,VC.Asunto,VC.Documentos,CO.Contacto,CO.Tel1,CO.Puesto,VC.Fecha_Visita,VC.Hora_Visita,VC.Hora_Visita_Fin,CO.idContacto,"+
					" \n CO.Depto,CO.eMail,VC.PK_Formulario,CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.FK03_Empleado,S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint,VC.Creditos,VC.Valor,VC.Estado,VC.Fecha_CheckIn,VC.Reporte, "+
					" \n DIR.Pais dPais, DIR.CP dCP, DIR.Estado dEstado, DIR.Municipio dMunicipio, DIR.Ciudad dCiudad, DIR.Colonia dColonia, DIR.Zona dZona, DIR.Ruta dRuta, DIR.Altitud dAltitud, DIR.Latitud dLatitud, DIR.Longitud dLongitud, " +
					" \n DIR.No_Ext dExt, DIR.No_Int dInt, DIR.Region dRegion, DIR.Tipo_Region, Dir.PK_Direccion, DIR.Calle dCalle " +
					" \n FROM VisitaCliente as VC "+ 
					" \n INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					" \n INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+ 
					" \n INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+ 
					" \n INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					" \n LEFT JOIN (SELECT DIR.* FROM Direccion DIR LEFT JOIN Horario H ON H.FK01_Direccion = DIR.PK_Direccion " +
					" \n WHERE H.Tipo = 'Visita') DIR ON DIR.FK01_Cliente = CL.Clave " +
					" \n WHERE VC.Etapa = 'AgendarVisita' AND (VC.Estado = 'Asignado' OR VC.Estado = 'Reportada') AND VC.FK03_Empleado = "+usuario;
			
			List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(),map,  new AsignarSprintRowMapper(true));
		//	logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public Boolean asignarHorarioVisitas(String conidionesLlave,String condicionFecha,String condicionHoraInicio,
			String condicionHoraFin)
					throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condicionLlaves", conidionesLlave);
			map.put("condicionFecha", condicionFecha);
			map.put("condicionHoraInicio", condicionHoraInicio);
			map.put("condicionHoraFin", condicionHoraFin);
			String query = "UPDATE VisitaCliente SET Fecha_Visita = "+
					"CASE "+ condicionFecha +" END, "+
					"Hora_Visita = CASE "+ condicionHoraInicio +" END, "+
					"Hora_Visita_Fin = CASE "+ condicionHoraFin +" END "+
					"WHERE PK_Formulario in ("+ conidionesLlave +")";
			super.jdbcTemplate.update(query, map);
		//	logger.info(query);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public Boolean guardarChecadoVisita(Long idVisita, Long usuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" usuario",  usuario);
			map.put(" idVisita",  idVisita);
			String query =   
					"UPDATE VisitaCliente SET Fecha_CheckIn = GETDATE() WHERE PK_Formulario = "+idVisita+" AND FK03_Empleado = "+usuario;
			super.jdbcTemplate.update(query, map);
			//logger.info(query);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> obtenerVisitasConCheckIn(Long usuario  
			) throws ProquifaNetException {
		try{
			
			String query= "SELECT EMP.Nombre,EMP.Usuario,VC.Tipo,VC.Clasificacion_Cliente,CL.Ruta,VC.FechaEstimada,VC.Asunto,VC.Documentos,CO.Contacto,CO.Tel1,CO.Puesto,VC.Fecha_Visita,VC.Hora_Visita,VC.Hora_Visita_Fin,CO.idContacto, "+
					"CO.Depto,CO.eMail,VC.PK_Formulario,CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.FK03_Empleado,S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint,VC.Creditos,VC.Valor,VC.Estado,VC.Fecha_CheckIn,VC.Reporte "+
					"FROM VisitaCliente as VC "+ 
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+ 
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+ 
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' AND VC.Fecha_CheckIn IS NOT NULL AND VisitaRealizada = 1 AND VC.FK03_Empleado = "+usuario;

		List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(), new AsignarSprintRowMapper(true));
	//		logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> getVisitasAsignadas()
			throws ProquifaNetException {
		try{
			String query= "SELECT EMP.Nombre,EMP.Usuario,VC.Tipo,VC.Clasificacion_Cliente,CL.Ruta,VC.FechaEstimada,VC.Asunto,VC.Documentos,CO.Contacto,CO.Tel1,CO.Puesto,VC.Fecha_Visita,VC.Hora_Visita,VC.Hora_Visita_Fin,CO.idContacto, "+
					"CO.Depto,CO.eMail,VC.PK_Formulario,CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.FK03_Empleado,S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint,VC.Creditos,VC.Valor,VC.Estado,VC.Fecha_CheckIn,VC.Reporte "+
					"FROM VisitaCliente as VC "+ 
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+ 
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+ 
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' ";

			List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(), new AsignarSprintRowMapper(true));
		//	logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public int insertarDailyScrum(DailyScrum daily) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" daily",  daily);
			Object[] params = {daily.getNumeroDaily(),daily.getSprint().getIdSprint()};
			String query= "INSERT INTO DailyScrum (NumeroDaily,FK01_Sprint,FechaDaily) "+
					"VALUES (?,?,GETDATE())";

			super.jdbcTemplate.update(query,map);
		//	logger.info(query);
			return super.queryForInt("SELECT IDENT_CURRENT ('DailyScrum')",map);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@Override
	public Boolean insertarRegistroAsistencia(String queryValues) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" queryValues",  queryValues);
			String query= "INSERT INTO AsistenciaDailyScrum (FK01_Empleado,FK02_DailyScrum,codigoAsistencia) "+
					"VALUES "+queryValues;

			//logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoAdjunto> obtenerDocumentosReportes(Long idVisitaCliente)
			throws ProquifaNetException {
		try{
			String query= "SELECT PK_VisitaCliente,Nombre,Ext_Archivo FROM VisitaClienteDocumentacion "+
					"WHERE FK01_Visita = "+idVisitaCliente; 

			List<DocumentoAdjunto> listaDocumentos = super.jdbcTemplate.query(query.toString(), new DocumentosReporteVisitaRowMapper());
		//	logger.info(query);
			return listaDocumentos;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public int revisarDailyRealizado() throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" queryValues",  null);
			String query= "SELECT COUNT(PK_DailyScrum) FROM DailyScrum AS DS "+
					"INNER JOIN AsistenciaDailyScrum ADS ON DS.PK_DailyScrum = ADS.FK02_DailyScrum "+
					"WHERE DATEDIFF(DAY,CAST(GETDATE() as date) ,CAST(FechaDaily as date)) = 0 AND "+
					"DATEDIFF(MONTH,CAST(GETDATE() as date) ,CAST(FechaDaily as date)) = 0 AND DATEDIFF(YEAR,CAST(GETDATE() as date) ,CAST(FechaDaily as date)) = 0 "+ 
					"AND ADS.Asistencia IS NOT NULL"; 
		//	logger.info(query);
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@Override
	public Boolean insertarDocumentacionVisita(DocumentoAdjunto documento, Long idVisita) throws ProquifaNetException {
		String sQuery = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idVisita",  idVisita);
			map.put(" documento",  documento);
			sQuery = "INSERT INTO VisitaClienteDocumentacion (Nombre,Ext_Archivo,FK01_Visita) VALUES "+
					"('"+ documento.getNombre() +"','"+ documento.getExtension() +"',"+idVisita+")";

		//	logger.info(sQuery);			
			super.jdbcTemplate.update(sQuery, map);
			return true;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			return false;
		}
	}


	@Override
	public Boolean updateEstadoDeVisitaCliente(Long idVisita, String Reporte,Long calificacion)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" calificacion",  calificacion);
			map.put(" Reporte",  Reporte);
			map.put(" idVisita",  idVisita);
			String condicion = "PK_Formulario ="+idVisita;
			String query = "UPDATE VisitaCliente SET Estado = 'Reportada',Reporte ='"+Reporte+"', CalificacionEV = " +calificacion + " WHERE  "+condicion;
			//logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public int consultarNumeroSprintAbierto() throws ProquifaNetException {
		try{
			String query = "SELECT TOP 1 NumeroSprint FROM Sprint WHERE Estado = 'En Curso'";
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@Override
	public int consultarPendientesAgendarVisita(Long usuario) throws ProquifaNetException {
		try{
			String query = "SELECT COUNT(*) "+
					"FROM VisitaCliente as VC "+
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+
					"WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' AND VC.Fecha_Visita IS NULL AND VC.FK03_Empleado = "+usuario;
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@Override
	public int consultarPendientesReportarVisita(Long usuario)
			throws ProquifaNetException {
		try{
			String query = "select COUNT(*) FROM ( "+
					"SELECT VC.PK_Formulario idVisita FROM VisitaCliente VC "+
					"INNER JOIN Clientes CL ON VC.FK01_Cliente = CL.Clave  "+
					"INNER JOIN (SELECT TOP 1 * FROM Sprint WHERE Estado = 'En Curso' ) SP ON SP.PK_Sprint = VC.SprintAsignado "+
					"LEFT JOIN ( SELECT CL.Clave, NIVEL.VentasUSD FROM  Clientes CL "+
					"LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda = 'Dolares' OR F.Moneda ='USD' THEN F.Importe "+
					"WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN F.Importe * M.EDolar "+
					"WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe/CASE WHEN F.TCambio = 0 OR F.TCAMBIO IS NULL THEN 1 ELSE F.TCAMBIO END END ), 0) VentasUSD, Cliente FROM Facturas F "+
					"LEFT JOIN Monedas M ON M.Fecha = F.Fecha "+
					"WHERE YEAR(F.Fecha) = DATEPART(year,GETDATE()) - 1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente) NIVEL ON NIVEL.Cliente = CL.Clave "+
					"WHERE NIVEL.VentasUSD IS NOT NULL ) NIVEL ON NIVEL.Clave = CL.Clave "+
					"LEFT JOIN NivelIngreso NI ON NIVEL.VentasUSD >= NI.MIN AND NIVEL.VentasUSD <= NI.MAX "+
					"LEFT JOIN SolicitudVisita SV ON SV.FK04_VisitaCliente = VC.PK_Formulario "+
					"LEFT JOIN Contactos CON ON CON.idContacto = VC.FK02_Contacto "+
					"WHERE VC.FK03_Empleado = "+usuario+
					"GROUP BY VC.PK_Formulario, CL.Clave, CL.Nombre, CL.Estado, VC.FechaEstimada, VC.Asunto, VC.Tipo_Visita, NI.Nivel, CL.Rol, VC.Notas, "+
					"VC.Estado, VC.Fecha_CheckIn, VC.VisitaRealizada, SP.NumeroSprint, SP.FInicio, SP.FFin, CL.Ruta, CON.Contacto, CON.eMail, CON.Tel1, CON.Extension1, CON.idContacto)AS A ";
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	
	
	@Override
	public int consultarContadorControlarPendientes(Long usuario)
			throws ProquifaNetException {
		try{
			String query =  "select sum(registros) from (select count(*) registros FROM HallazgosAccionesReporteVisita as HARV "+
					" INNER JOIN (SELECT [PK_Formulario],[Folio],[Estrategia],[Comentarios],[Clasificacion_Cliente],[Tipo_Visita],[Fecha],[Fecha_Visita],[Hora_Visita],[Hora_Llegada],[FK01_Cliente],[FK02_Contacto], "+
				    " [FK03_Empleado],[Etapa],[Tipo],[FechaEstimada],[Asunto],[Documentos],[Estado],[SprintAsignado],[Creditos],[Valor],[Hora_Visita_Fin],[Fecha_CheckIn],[Reporte],[VisitaRealizada],[JustificacionCancelacion],[TipoCancelacion], "+
					" [FechaCierre],[FK04_SprintAntiguo],[FK05_CotizasMateriales],[FK06_CotizasPublicaciones] FROM VisitaCliente) VisCli on HARV.FK01_Visita=VisCli.PK_Formulario "+
					" INNER JOIN (SELECT Clave, Nombre Empresa, FK01_EV FROM Clientes) as Cli on VisCli.FK01_Cliente=Cli.Clave "+
					" INNER JOIN (SELECT PK_Sprint, FInicio, FFin, NumeroSprint, CASE WHEN month(FInicio) = month(ffin) then CONVERT(VARCHAR(MAX),day(FInicio)) + ' - ' + CONVERT(VARCHAR(MAX),day(FFin)) + ' / ' + DATENAME(MONTH, finicio) + ' / ' + CONVERT(VARCHAR(MAX),year(FInicio)) else CONVERT(VARCHAR(MAX),day(FInicio)) + ' ' + DATENAME(MONTH, finicio) + ' - ' + CONVERT(VARCHAR(MAX),day(FFin)) + ' ' + DATENAME(MONTH, FFin) + ' / ' + CONVERT(VARCHAR(MAX),year(FInicio)) end fechaFormateada FROM Sprint) AS Sprint on VisCli.SprintAsignado = Sprint.PK_Sprint "+
					" INNER JOIN (SELECT idContacto, Contacto, ISNULL(Tel1,'ND') Tel1, ISNULL(Extension1,'ND') Extension1, ISNULL(eMail,'ND') eMail from Contactos) as C on VisCli.FK02_Contacto=C.idContacto where Cli.FK01_EV= "+usuario+" AND HARV.Tipo LIKE 'acci%' AND HARV.FRealizacion is NULL "+
					" group by Cli.Empresa, VisCli.FechaEstimada) as a";
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	
	@Override
	public int consultarContadorControlarAcciones(Long usuario)
			throws ProquifaNetException {
		try{
			String query =  "select sum(registros) from (select count(*) registros FROM HallazgosAccionesReporteVisita as HARV "+
					" INNER JOIN (SELECT [PK_Formulario],[Folio],[Estrategia],[Comentarios],[Clasificacion_Cliente],[Tipo_Visita],[Fecha],[Fecha_Visita],[Hora_Visita],[Hora_Llegada],[FK01_Cliente],[FK02_Contacto], "+
				    " [FK03_Empleado],[Etapa],[Tipo],[FechaEstimada],[Asunto],[Documentos],[Estado],[SprintAsignado],[Creditos],[Valor],[Hora_Visita_Fin],[Fecha_CheckIn],[Reporte],[VisitaRealizada],[JustificacionCancelacion],[TipoCancelacion], "+
					" [FechaCierre],[FK04_SprintAntiguo],[FK05_CotizasMateriales],[FK06_CotizasPublicaciones] FROM VisitaCliente) VisCli on HARV.FK01_Visita=VisCli.PK_Formulario "+
					" INNER JOIN (SELECT Clave, Nombre Empresa, FK01_EV FROM Clientes) as Cli on VisCli.FK01_Cliente=Cli.Clave "+
					" INNER JOIN (SELECT PK_Sprint, FInicio, FFin, NumeroSprint, CASE WHEN month(FInicio) = month(ffin) then CONVERT(VARCHAR(MAX),day(FInicio)) + ' - ' + CONVERT(VARCHAR(MAX),day(FFin)) + ' / ' + DATENAME(MONTH, finicio) + ' / ' + CONVERT(VARCHAR(MAX),year(FInicio)) else CONVERT(VARCHAR(MAX),day(FInicio)) + ' ' + DATENAME(MONTH, finicio) + ' - ' + CONVERT(VARCHAR(MAX),day(FFin)) + ' ' + DATENAME(MONTH, FFin) + ' / ' + CONVERT(VARCHAR(MAX),year(FInicio)) end fechaFormateada FROM Sprint) AS Sprint on VisCli.SprintAsignado = Sprint.PK_Sprint "+
					" INNER JOIN (SELECT idContacto, Contacto, ISNULL(Tel1,'ND') Tel1, ISNULL(Extension1,'ND') Extension1, ISNULL(eMail,'ND') eMail from Contactos) as C on VisCli.FK02_Contacto=C.idContacto where HARV.FK05_EV_Asignado= "+usuario+" AND HARV.Tipo LIKE 'hallazgo' AND HARV.FRealizacion is NULL AND VisCli.Etapa like 'cierre' AND VisCli.Estado like 'Cerrado'"+
					" group by Cli.Empresa, VisCli.FechaEstimada) as a";
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}




	@Override
	public Boolean insertarHallazgosYAccionesReporteVisita(String descripcion, String Tipo, Long idVisita)
			throws ProquifaNetException {
		String query = "";
		try{

			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" Tipo",  Tipo);
			map.put(" idVisita",  idVisita);
			map.put(" descripcion",  descripcion);
			
			query = "INSERT INTO HallazgosAccionesReporteVisita (Descripcion,Tipo,FK01_Visita) VALUES "+
					"('"+descripcion +"','"+ Tipo +"',"+idVisita+")";
			super.jdbcTemplate.update(query, map);
		//	logger.info(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> obtenerVisitasClienteAgrupadasReportarVisita(Long usuario)
			throws ProquifaNetException {
		try{
			String query= " \nSELECT SUM(CASE WHEN VC.Tipo = 'solicitud' THEN 1 ELSE 0 END) NumSolicitud, SUM(CASE WHEN VC.Tipo = 'crm' THEN 1 ELSE 0 END) NumCRM, VC.FK01_Cliente, C.Nombre FROM VisitaCliente as VC " +
					" \n INNER JOIN Clientes as C ON VC.FK01_Cliente = C.Clave " +
					" \n INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En curso') as S ON S.PK_Sprint = VC.SprintAsignado " + 
					" \n WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' AND VC.Fecha_CheckIn IS NOT NULL AND VC.FK03_Empleado = " + usuario +
					" \n GROUP BY VC.FK01_Cliente, C.Nombre ";

		//	logger.info(query);
			List<VisitaCliente> solicitudes = super.jdbcTemplate.query(query.toString(), new PlanificarSprintRowMapper(true));
			return solicitudes;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean updateContactoSolicitudVisita(SolicitudVisita solicitud)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" solicitud",  solicitud);
			query = "UPDATE SolicitudVisita SET FK02_Contacto = "+solicitud.getContacto().getIdContacto()+" WHERE PK_SolicitudVisita = "+solicitud.getIdSolicitudVisita();
			super.jdbcTemplate.update(query, map);
		//	logger.info(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> obtenerUltimasVisitasDeClientePoridCliente(Long idCliente)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idCliente",  idCliente);
			String query= " \n SELECT TOP 3 PK_Formulario FROM VisitaCliente WHERE FK01_Cliente ="+idCliente+"AND (Estado = 'Reportada' OR Estado = 'Cerrado')  ORDER BY PK_Formulario desc";
			//logger.info(query);
			List<Integer> lista =  super.jdbcTemplate.queryForList(query, map,Integer.class);
			return lista;
			

		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<HallazgosAcciones> obtenerUltimosHallazgoAccionesDeVisitaCliente(Long idCliente , Long idVisita,String tipo)
			throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" tipo",  tipo);
			map.put(" idVisita",  idVisita);
			map.put(" idCliente",  idCliente);
			
			String query= " \n SELECT TOP 3 con1.Tipo  as Tipo, con1.Descripcion " +
					" \n FROM VisitaCliente VC " +
					" \n INNER JOIN HallazgosAccionesReporteVisita AS con1 ON con1.FK01_Visita = VC.PK_Formulario and con1.Tipo = '"+tipo+"'"+
					" \n WHERE VC.FK01_Cliente = " + idCliente+" AND VC.PK_Formulario ="+ idVisita +
					" \n ORDER BY PK_Formulario, con1.PK_HallazgosAcciones DESC ";


			//logger.info(query);
			List<HallazgosAcciones> Info = super.jdbcTemplate.query(query.toString(),map,  new HallazgosAccionesRowmapper());
			return Info;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoAdjunto> obtenerUltimosDocumentosDeVisitaCliente(Long idCliente , Long idVisita)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idCliente",  idCliente);
			map.put(" idVisita",  idVisita);
			String query= " \n SELECT TOP 3 con1.Nombre,con1.Ext_Archivo,con1.PK_VisitaCliente " +
					" \n FROM VisitaCliente VC " +
					" \n INNER JOIN VisitaClienteDocumentacion AS con1 ON con1.FK01_Visita = VC.PK_Formulario" +
					" \n WHERE VC.FK01_Cliente = " + idCliente+ "AND VC.PK_Formulario = "+ idVisita +
					" \n ORDER BY PK_Formulario, con1.PK_VisitaCliente DESC ";
			//logger.info(query);
			List<DocumentoAdjunto> documentos = super.jdbcTemplate.query(query.toString(),map,  new DocumentosReporteVisitaRowMapper());
			return documentos;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<String> obtenerMotivosVisita(Long idCliente , Long idVisita)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idCliente",  idCliente);
			map.put(" idVisita",  idVisita);
			String query= " \n SELECT Motivo FROM SolicitudVisita WHERE FK01_Cliente = "+idCliente+" AND  FK04_VisitaCliente ="+ idVisita;
			//logger.info(query);
			List<String> lista =  super.jdbcTemplate.queryForList(query,map, String.class);
			return lista;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> obtenerVisitasAgrupadasAgendarVisita(Long usuario)
			throws ProquifaNetException {
		try{
			String query= " \n SELECT SUM(CASE WHEN VC.Tipo = 'solicitud' THEN 1 ELSE 0 END) NumSolicitud, SUM(CASE WHEN VC.Tipo = 'crm' THEN 1 ELSE 0 END) NumCRM, VC.FK01_Cliente, C.Nombre FROM VisitaCliente as VC " +  
					" \n  INNER JOIN Clientes as C ON VC.FK01_Cliente = C.Clave  " +
					" \n  INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado " + 
					" \n  WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' AND VC.Fecha_Visita IS NULL  AND VC.FK03_Empleado ="+usuario+
					" \n GROUP BY VC.FK01_Cliente, C.Nombre ";

		//	logger.info(query);
			List<VisitaCliente> solicitudes = super.jdbcTemplate.query(query.toString(), new PlanificarSprintRowMapper(true));
			return solicitudes;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public Sprint getSprintEnCurso() throws ProquifaNetException {
		try{	Map<String, Object> map = new HashMap<String, Object>();
			map.put(" tipo", null);
		
			String query= "SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,Estado,NumeroSprint FROM Sprint WHERE Estado = 'En Curso'";

//			logger.info(query);
			Sprint sprint = (Sprint) super.jdbcTemplate.queryForObject(query.toString(),map,  new SprintRowMapper());
			return sprint;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new Sprint();
		}
	}

	@Override
	public int actualizarRealizacionVisita(VisitaCliente visita)
			throws ProquifaNetException {
		String query = "";
		int realizar = 0;
		
		if(visita.isRealizacionVisita())
			realizar = 1;
		
		Object[] params =  {realizar,visita.getJustificacionCancelacion(),visita.getTipoCancelacion(),visita.getNotas(),visita.getIdVisitaCliente()};
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("realizar", realizar);
			map.put("JustificacionCancelacion", visita.getJustificacionCancelacion());
			map.put("TipoCancelacion", visita.getTipoCancelacion());
			map.put("Notas", visita.getNotas());
			map.put("IdVisitaCliente", visita.getIdVisitaCliente());
			query = "UPDATE VisitaCliente SET VisitaRealizada = :realizar, JustificacionCancelacion = :JustificacionCancelacion, TipoCancelacion = :TipoCancelacion, Notas = :Notas  WHERE PK_Formulario = :IdVisitaCliente";
			super.jdbcTemplate.update(query,map);
		//	logger.info(query);
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}



	@Override
	public boolean reagendarVisita(Long idVisita)
			throws ProquifaNetException {
		String query = "";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idVisita", idVisita);
			query = "UPDATE VisitaCliente SET Hora_Visita = null,Hora_Visita_Fin = null,Fecha_Visita = null where PK_Formulario = " + idVisita;
			super.jdbcTemplate.update(query, map);
			//logger.info(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> obtenerVisitasRealizadas(Long usuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" usuario", usuario);
			String query= "SELECT EMP.Nombre,EMP.Usuario,VC.Tipo,VC.Clasificacion_Cliente,CL.Ruta,VC.FechaEstimada,VC.Asunto,VC.Documentos,CO.Contacto,CO.Tel1,CO.Puesto,VC.Fecha_Visita,VC.Hora_Visita,VC.Hora_Visita_Fin,CO.idContacto, "+
					"CO.Depto,CO.eMail,VC.PK_Formulario,CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.FK03_Empleado,S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint,VC.Creditos,VC.Valor,VC.Estado,VC.Fecha_CheckIn,VC.Reporte "+
					"FROM VisitaCliente as VC "+ 
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+ 
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+ 
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' AND VC.Fecha_CheckIn IS NOT NULL AND VC.VisitaRealizada = 1 AND VC.FK03_Empleado = "+usuario;

			List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(), new AsignarSprintRowMapper(true));
			//logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}



@Override
public Long obtenerPartidasPorSprint(Long idSprint )
		throws ProquifaNetException {
	try{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(" idSprint", idSprint);
		String query= "select COUNT(*) from VisitaCliente where SprintAsignado = "+idSprint + " and  Estado = 'planificado'"; 
		super.jdbcTemplate.update(query, map);
		//logger.info(query);
		return super.queryForLong(query, map);
	}catch(Exception e){
		e.printStackTrace();
		new Funcion().enviarCorreoAvisoExepcion(e);
		return 0L;
	}
}



@Override
public boolean actualizarSprintEnCurso(Long idSprint)
		throws ProquifaNetException {
	try{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(" idSprint", idSprint);
		String query= "update Sprint set Estado ='En Curso' where PK_Sprint =" + idSprint;
		super.jdbcTemplate.update(query, map);
		//logger.info(query);
		return true;
	}catch(Exception e){
		e.printStackTrace();
		new Funcion().enviarCorreoAvisoExepcion(e);
		return false;
	}
}

@Override
public boolean cerrarSprintEnCurso(Long idSprint)
		throws ProquifaNetException {
	try{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(" idSprint", idSprint);
		String query= "update Sprint set Estado ='Cerrado' where Estado = 'En Curso' AND PK_Sprint =" + idSprint;
		super.jdbcTemplate.update(query, map);
//		logger.info(query);
		return true;
	}catch(Exception e){
		e.printStackTrace();
		new Funcion().enviarCorreoAvisoExepcion(e);
		return false;
	}
}



@Override
public Long obteneridDeSprintEnCurso()
		throws ProquifaNetException {
	try{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(" idSprint", null);
		String query= "select Top 1  PK_Sprint from Sprint where Estado ='Abierto'";
       
		super.jdbcTemplate.update(query, map);
		//logger.info(query);
		return super.queryForLong(query, map);
	}catch(Exception e){
		e.printStackTrace();
		new Funcion().enviarCorreoAvisoExepcion(e);
		return 0L;
	}
}


	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> obtenerTodaVisitasParaCierre()
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idSprint", null);

			String query= "SELECT VC.PK_Formulario, VC.FechaEstimada, VC.Fecha_Visita,VC.Hora_Visita,VC.Hora_Visita_Fin,FK01_Cliente,VC.FK02_Contacto,VC.FK03_Empleado,VC.Tipo,VC.Asunto,VC.Documentos,VC.Creditos,VC.Valor,VC.FK04_SprintAntiguo, "+
					"VC.Fecha_CheckIn,VC.Reporte,VisitaRealizada,VC.JustificacionCancelacion,VC.TipoCancelacion, "+
					"EMP.Usuario,EMP.Nombre as nombreUsuario, "+
					"CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.Clasificacion_Cliente,CL.Ruta, "+
					"CO.Contacto,CO.Tel1,CO.Puesto,CO.Depto,CO.eMail, "+
					"S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint, "+
					"(CASE WHEN VC.Fecha_Visita IS NULL OR VC.Fecha_CheckIn IS NULL OR VC.VisitaRealizada IS NULL THEN 'Pendiente' "+
					"WHEN VC.VisitaRealizada = 0 THEN 'No finalizada' "+
					"WHEN VC.VisitaRealizada = 1 AND VC.Reporte IS NULL THEN 'Pendiente' "+
					"WHEN VC.Reporte IS NOT NULL AND VC.VisitaRealizada = 1 THEN 'Finalizada' "+ 
					"END) as Estado,VC.Tipo_Visita ,ESAC.Usuario AS UsuarioESAC, ESAC.Nombre AS NombreESAC, EMP.Area, VC.CalificacionEV, (select COUNT(PK_HallazgosAcciones) from HallazgosAccionesReporteVisita where Tipo like 'Hallazgo' and FK01_Visita = VC.PK_Formulario) noHallazgos, "+  
					"COALESCE((SELECT SUM(HallazgosCompletados) noHallazgosCompletados FROM (select CASE WHEN FERealizacion IS NOT NULL AND Desc_Accion IS NOT NULL AND Desc_Accion != '' AND FK05_EV_Asignado IS NOT NULL OR Descartado = 1 THEN 1 END HallazgosCompletados from HallazgosAccionesReporteVisita where Tipo like 'Hallazgo' and FK01_Visita = VC.PK_Formulario) AS A),0) noHallazgosCompletados FROM VisitaCliente as VC "+  
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+ 
					"LEFT JOIN Empleados AS ESAC on ESAC.Usuario = Vendedor  "+ 
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+				
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Etapa = 'AgendarVisita' AND (VC.Estado = 'Asignado' OR VC.Estado = 'Reportada') AND FechaCierre IS NULL ";

			List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(),map, new ResumenVisitaRowMapper()); 
		//logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public int obtenerNumeroVisitasPendientes() throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idSprint", null);
			String query= "SELECT COUNT(*) FROM VisitaCliente as VC "+
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Fecha_Visita IS NULL OR VC.Fecha_CheckIn IS NULL OR VC.VisitaRealizada IS NULL)";

			int numero = super.queryForInt(query.toString(), map); 
			return numero;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}
	
	@Override
	public Sprint obtenerSprintEnCurso()
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idSprint", null);
			String query= "SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,Estado,NumeroSprint FROM Sprint WHERE Estado = 'En Curso'";

			//logger.info(query);
			Sprint sprint = (Sprint) super.jdbcTemplate.queryForObject(query.toString(), map, new SprintRowMapper());
			return sprint;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new Sprint();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VisitaCliente> getVisitaConCheckIn(Long usuario)
			throws ProquifaNetException {
		try{
			String query= "SELECT EMP.Nombre,EMP.Usuario,VC.Tipo,VC.Clasificacion_Cliente,CL.Ruta,VC.FechaEstimada,VC.Asunto,VC.Documentos,CO.Contacto,CO.Tel1,CO.Puesto,VC.Fecha_Visita,VC.Hora_Visita,VC.Hora_Visita_Fin,CO.idContacto, "+
					"CO.Depto,CO.eMail,VC.PK_Formulario,CL.Nombre as NombreCliente,CL.Clave as idCliente,VC.FK03_Empleado,S.PK_Sprint,S.Anio,S.FInicio,S.FFin,S.NumeroSprint,VC.Creditos,VC.Valor,VC.Estado,VC.Fecha_CheckIn,VC.Reporte "+
					"FROM VisitaCliente as VC "+ 
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+ 
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+ 
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+ 
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Etapa = 'AgendarVisita' AND VC.Estado = 'Asignado' AND VC.Fecha_CheckIn IS NOT NULL AND VisitaRealizada IS NULL AND VC.FK03_Empleado = "+usuario;

			List<VisitaCliente> listaVisita = super.jdbcTemplate.query(query.toString(), new AsignarSprintRowMapper(true));
		//	logger.info(query);
			return listaVisita;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Empleado> getVendedoresConVisitas() throws ProquifaNetException {
		try{
			String query= "SELECT DISTINCT E.Clave,E.Usuario,E.Nombre FROM VisitaCliente as VC "+
					"INNER JOIN Empleados as E ON VC.FK03_Empleado = E.Clave "+
					"INNER JOIN Sprint as S ON VC.SprintAsignado = S.PK_Sprint "+
					"WHERE S.Estado = 'En Curso'";

			List<Empleado> vendedores = super.jdbcTemplate.query(query.toString(), new VendedorVisitaClienteRowMapper());
			//logger.info(query);
			return vendedores;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public DailyScrum getDailylScrumActual() throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idSprint", null);
			String query= "SELECT PK_DailyScrum,NumeroDaily,FK01_Sprint,Observaciones,FechaDaily "
					+ "FROM DailyScrum WHERE FechaDaily = CONVERT(VARCHAR(10),GETDATE(),105)";

			DailyScrum daily = (DailyScrum) super.jdbcTemplate.queryForObject(query.toString(),map, new DailyScrumRowMapper());
			//logger.info(query);
			return daily;
		}catch (EmptyResultDataAccessException e) {
			return new DailyScrum();
        }catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return new DailyScrum();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AsistenciaDailyScrum> getAsistenciaAactual()
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idSprint", null);
			String query= "SELECT ADS.PK_AsistenciaDailyScrum,ADS.FK01_Empleado,ADS.FK02_DailyScrum,ADS.Asistencia,ADS.CodigoAsistencia "
					+ "FROM AsistenciaDailyScrum as ADS "
					+ "INNER JOIN DailyScrum as DS ON ADS.FK02_DailyScrum = DS.PK_DailyScrum "
					+ "WHERE DS.FechaDaily = CONVERT(VARCHAR(10),GETDATE(),105)";

			List<AsistenciaDailyScrum> vendedores = super.jdbcTemplate.query(query.toString(), new AsistenciaDailyScrumRowMapper());
	//		logger.info(query);
			return vendedores;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean actualizarCodigoAsistencia(String codigo,int idAsistencia) throws ProquifaNetException {
		String query = "";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(" idAsistencia", idAsistencia);
		map.put(" codigo", codigo);
		Object[] params =  {codigo,idAsistencia};
		try{
			query = "UPDATE AsistenciaDailyScrum SET CodigoAsistencia = ? WHERE PK_AsistenciaDailyScrum = ?";
			super.jdbcTemplate.update(query,map);
			//logger.info(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public boolean actualizarAsistenciaDailyScrum(int asistencia,Long idEmpleado, int idDailyScrum
			) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idDailyScrum", idDailyScrum);
			map.put(" idEmpleado", idEmpleado);
			map.put(" asistencia", asistencia);
			Object[] params =  {asistencia,idEmpleado,idDailyScrum};
			String query= "UPDATE AsistenciaDailyScrum SET Asistencia = ? WHERE FK01_Empleado = ? AND FK02_DailyScrum = ?";

			//logger.info(query);
			super.jdbcTemplate.update(query,map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public boolean actualizarObservacionesDailyScrum(String observaciones,int idDailyScrum
			) throws ProquifaNetException {
		String query = "";

		Object[] params =  {observaciones,idDailyScrum};
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idDailyScrum", idDailyScrum);
			map.put(" observaciones", observaciones);
			
			query = "UPDATE DailyScrum SET Observaciones = ? WHERE PK_DailyScrum = ?";
			super.jdbcTemplate.update(query,map);
			//logger.info(query);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HallazgosAcciones> obtenerHallazgoAccionesDeVisitaCliente(Long idVisita,String tipo)
			throws ProquifaNetException {
		try{
			String query= "SELECT Tipo,Descripcion FROM HallazgosAccionesReporteVisita WHERE Tipo = '"+tipo+"' AND FK01_Visita = "+idVisita;

			List<HallazgosAcciones> Info = super.jdbcTemplate.query(query.toString(), new HallazgosAccionesRowmapper());
			return Info;
		}catch (EmptyResultDataAccessException e) {
			return Collections.emptyList();
        }catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}

	@Override
	public boolean insertarCierreSprint(Long idSprint, long idEmpleado,
			String observaciones, Integer calificacionEV) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" calificacionEV", calificacionEV);
			map.put(" observaciones", observaciones);
			map.put(" idEmpleado", idEmpleado);
			map.put(" idSprint", idSprint);
			
			Object[] params =  {observaciones,idSprint,idEmpleado,calificacionEV};
			String query= "INSERT INTO CierreSprint (Fecha,Observaciones,Fk01_Sprint,FK02_Empleado, CalificacionEV) "+
					"VALUES (GETDATE(),?,?,?,?)";

			//logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public boolean actualizarFechaCierreVisitaCliente(String idsVisitaCliente,Long idEmpleado)
			throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idEmpleado", idEmpleado);
			map.put(" idsVisitaCliente", idsVisitaCliente);

			
			String query= "UPDATE VisitaCliente SET FechaCierre = GETDATE(), Etapa = 'Cierre',  Estado = 'Cerrado' "
					+ "WHERE PK_Formulario in ("+idsVisitaCliente+")  AND FK03_Empleado = "+idEmpleado;

			//logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public int consultarPendientesJuntaCierre() throws ProquifaNetException {
		try{
			String query = "SELECT COUNT (DISTINCT VC.FK03_Empleado) FROM VisitaCliente as VC "+
					"INNER JOIN Clientes as CL ON VC.FK01_Cliente = CL.Clave "+
					"INNER JOIN Empleados as EMP ON VC.FK03_Empleado = EMP.Clave "+
					"INNER JOIN Contactos AS CO ON VC.FK02_Contacto = CO.idContacto "+
					"INNER JOIN (SELECT TOP 1 PK_Sprint,Anio,FInicio,FFin,NumeroSprint FROM Sprint WHERE Estado= 'En Curso') as S ON S.PK_Sprint = VC.SprintAsignado "+ 
					"WHERE VC.Etapa = 'AgendarVisita' AND (VC.Estado = 'Asignado' OR VC.Estado = 'Reportada') AND FechaCierre IS NULL";
			return super.queryForInt(query);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0;
		}
	}

	@Override
	public boolean actualizarSprintAntiguo(Long idSprint, Long idVisitaCliente)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idVisitaCliente", idVisitaCliente);
			map.put(" idSprint", idSprint);
			String query= "UPDATE VisitaCliente SET FK04_SprintAntiguo = "+idSprint+" WHERE PK_Formulario in ("+idVisitaCliente+")";

		//	logger.info(query);
			super.jdbcTemplate.update(query, map);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return false;
		}
	}

	@Override
	public Long registrarAgrupacionExtratemporal(SolicitudVisita solicitud,Long sprintAsignado)
			throws ProquifaNetException {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" sprintAsignado", sprintAsignado);
			map.put(" solicitud", solicitud);
			Object[] params = {solicitud.getFechaDeseadaRealizacion(),solicitud.getTipoVisita()};
			Folio folio = folioDAO.obtenerFolioPorConcepto("VisitaCliente", true);			
			String folioVIS = folio.getFolioCompleto();
			String query = "INSERT VisitaCliente (Folio, Telefono,Clasificacion_Cliente,Puesto_Contacto,Area_Contacto,Correo_Electronico,Fecha,FK01_Cliente,FK02_Contacto,FK03_Empleado,Etapa,Tipo,Asunto,Documentos,FechaEstimada,Estado,SprintAsignado,Tipo_Visita) " +
					"VALUES ('"+ folioVIS +"','"+ solicitud.getContacto().getTelefono() +"','"+ solicitud.getCliente().getNivelIngreso() +"','"+ solicitud.getContacto().getPuesto() +"','"+ solicitud.getContacto().getDepartamento() +
					"','"+ solicitud.getContacto().getEMail() +"',GETDATE(),"+ solicitud.getIdCliente() +","+ solicitud.getIdContacto()+","+ solicitud.getIdEmpleado() +",'AgendarVisita','"+ solicitud.getTipoSolicitud() +
					"', '"+ solicitud.getAsunto() +"', '"+ solicitud.getNumDocumentos() +"' , ? , 'Asignado', "+sprintAsignado+",?)";

			super.jdbcTemplate.update(query,map);
			//logger.info(query);
			return super.queryForLong("SELECT IDENT_CURRENT ('VisitaCliente')", map);
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0L;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sprint> consultarUltimosSprintsAbiertosSinPlanificar(Long idUsuario)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(" idUsuario", idUsuario);
			String query= " SELECT TOP 5 PK_Sprint,Anio,FInicio,FFin,Estado,NumeroSprint FROM Sprint S " +
                   " \n  WHERE Estado = 'Abierto' AND s.PK_Sprint not in (select SprintAsignado from VisitaCliente where FK03_Empleado = "+idUsuario+" and Estado = 'Planificado')";

		//	logger.info(query);
			List<Sprint> sprint = super.jdbcTemplate.query(query.toString(),map,  new SprintRowMapper());
			return sprint;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Sprint> ConsultaDeSprintSinCerrar()
			throws ProquifaNetException {
		try{
			String query= "Select * from Sprint where Estado <> 'Cerrado'";
			//logger.info(query);
			List<Sprint> sprint = super.jdbcTemplate.query(query.toString(), new SprintRowMapper());
			return sprint;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return Collections.emptyList();
		}
	}
	
	@Override
	public boolean updateLongitudyLatitud(Direccion direccion) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Latitud", direccion.getLatitud());
			map.put("Longitud", direccion.getLongitud());
			map.put("IdDireccion()",direccion.getIdDireccion());
			
			
			Object[] params = {direccion.getLatitud(), direccion.getLongitud(), direccion.getIdDireccion()};
			StringBuilder sbQuery = new StringBuilder("UPDATE Direccion SET Latitud = :Latitud, Longitud = :Longitud WHERE PK_Direccion = :IdDireccion \n");
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List <VisitaCliente> obtenerTodasVisitasPorSprint(Long usuario) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT VC.PK_Formulario idVisita, CL.Clave idCliente, CL.Nombre Cliente, CL.Estado, VC.FechaEstimada, VC.Asunto, VC.Tipo_Visita, \n");
			sbQuery.append("CASE WHEN NI.Nivel IS NULL THEN CL.Rol ELSE NI.Nivel END Nivel, COUNT(SV.PK_SolicitudVisita) NoDocumentos, \n");
			sbQuery.append("CASE WHEN VC.Estado = 'Reportada' THEN 'REPORTADAS' WHEN VC.Fecha_CheckIn IS NOT NULL AND VC.VisitaRealizada = 1 THEN 'FINALIZADAS' \n");
			sbQuery.append("WHEN VC.Fecha_CheckIn IS NOT NULL AND (VC.VisitaRealizada = 0 OR VC.VisitaRealizada IS NULL) THEN 'NO REALIZADAS' ELSE 'PENDIENTES' END EstadoVisita, \n");
			sbQuery.append("SP.NumeroSprint, SP.FInicio, SP.FFin, CL.Ruta, CON.Contacto, CON.eMail, CON.Tel1, CON.Extension1, VC.Notas, CON.idContacto, SV.PK_SolicitudVisita \n");
			sbQuery.append("FROM VisitaCliente VC \n");
			sbQuery.append("INNER JOIN Clientes CL ON VC.FK01_Cliente = CL.Clave \n");
			sbQuery.append("INNER JOIN (SELECT TOP 1 * FROM Sprint WHERE Estado = 'En Curso' ) SP ON SP.PK_Sprint = VC.SprintAsignado \n");
			sbQuery.append("LEFT JOIN ( SELECT CL.Clave, NIVEL.VentasUSD FROM  Clientes CL \n");
			sbQuery.append("	LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda = 'Dolares' OR F.Moneda ='USD' THEN F.Importe \n");
			sbQuery.append("	WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN F.Importe * M.EDolar \n");
			sbQuery.append("	WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe/CASE WHEN F.TCambio = 0 OR F.TCAMBIO IS NULL THEN 1 ELSE F.TCAMBIO END END ), 0) VentasUSD, Cliente FROM Facturas F \n");
			sbQuery.append("	LEFT JOIN Monedas M ON M.Fecha = F.Fecha \n");
			sbQuery.append("	WHERE YEAR(F.Fecha) = DATEPART(year,GETDATE()) - 1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente) NIVEL ON NIVEL.Cliente = CL.Clave \n");
			sbQuery.append("WHERE NIVEL.VentasUSD IS NOT NULL ) NIVEL ON NIVEL.Clave = CL.Clave \n");
			sbQuery.append("LEFT JOIN NivelIngreso NI ON NIVEL.VentasUSD >= NI.MIN AND NIVEL.VentasUSD <= NI.MAX \n");
			sbQuery.append("LEFT JOIN SolicitudVisita SV ON SV.FK04_VisitaCliente = VC.PK_Formulario \n");
			sbQuery.append("LEFT JOIN Contactos CON ON CON.idContacto = VC.FK02_Contacto \n");

			sbQuery.append("WHERE VC.FK03_Empleado = ").append(usuario).append(" \n");
			sbQuery.append("GROUP BY VC.PK_Formulario, CL.Clave, CL.Nombre, CL.Estado, VC.FechaEstimada, VC.Asunto, VC.Tipo_Visita, NI.Nivel, CL.Rol, VC.Notas, \n"); 
			sbQuery.append("VC.Estado, VC.Fecha_CheckIn, VC.VisitaRealizada, SP.NumeroSprint, SP.FInicio, SP.FFin, CL.Ruta, CON.Contacto, CON.eMail, CON.Tel1, CON.Extension1, CON.idContacto, SV.PK_SolicitudVisita \n");
			
//			log.info(sbQuery.toString());
			
			return jdbcTemplate.query(sbQuery.toString(), new VisitaClientePorSprinRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List <VisitaCliente> obtenerDatosSeccionReporte(int visita) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT COALESCE(ROUND(SUM(CAST(Calificacion as FLOAT))/COUNT(CAST(Calificacion as FLOAT)),0),0) Calificacion, COALESCE(ReqRealizados,0) ReqRealizados, COALESCE(TotalReq,0) TotalReq, COALESCE(No_Pendientes,0) No_Pendientes, COALESCE(No_Hallazgos,0) No_Hallazgos, COALESCE(No_Requisiciones,0) No_Requisiciones FROM \n");
			sbQuery.append("(SELECT Calificacion Calificacion, (select COUNT(*) from visitaCliente left join SolicitudVisita on FK04_VisitaCliente = PK_Formulario where PK_Formulario = ").append(visita).append(" and Respuesta is not null) as ReqRealizados, \n");
			sbQuery.append("(select COUNT(*) from visitaCliente left join SolicitudVisita on FK04_VisitaCliente = PK_Formulario where PK_Formulario = ").append(visita).append(") AS TotalReq, No_Pendientes, No_Hallazgos, No_Requisiciones \n");
			sbQuery.append("FROM VisitaCliente LEFT JOIN SolicitudVisita on PK_Formulario = FK04_VisitaCliente  \n");
			sbQuery.append("LEFT JOIN (select FK01_Visita, COUNT(*) No_Pendientes from HallazgosAccionesReporteVisita where Tipo like 'acci%' AND FK01_Visita= ").append(visita).append(" GROUP BY HallazgosAccionesReporteVisita.FK01_Visita) AS Hallazgos on Hallazgos.FK01_Visita = PK_Formulario \n");
			sbQuery.append("LEFT JOIN (select FK01_Visita, COUNT(*) No_Hallazgos from HallazgosAccionesReporteVisita where Tipo like 'HALLAZGO' AND FK01_Visita= ").append(visita).append(" GROUP BY HallazgosAccionesReporteVisita.FK01_Visita) AS Acciones on Acciones.FK01_Visita = PK_Formulario \n");
			sbQuery.append("LEFT JOIN (select PK_Formulario, COUNT(*) No_Requisiciones from doctosr inner join requisicion on doctosr.folio = requisicion.fk01_DoctoR inner join PRequisicion on PRequisicion.idDoctoR = doctosr.folio inner join VisitaCliente on requisicion.FK06_Visita = VisitaCliente.PK_Formulario WHERE PK_Formulario = ").append(visita).append(" GROUP BY VisitaCliente.PK_Formulario) AS Requisiciones on Requisiciones.PK_Formulario = VisitaCliente.PK_Formulario \n");
			sbQuery.append("where VisitaCliente.PK_Formulario = ").append(visita).append(") as a GROUP BY ReqRealizados, TotalReq, No_Pendientes, No_Hallazgos, No_Requisiciones \n");
			
			log.info(sbQuery.toString());
			
			return jdbcTemplate.query(sbQuery.toString(), new ObtenerDatosSeccionReporteRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public VisitaCliente obtenerReporteDeVisita(int visita) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("visita", visita);

			StringBuilder sbQuery = new StringBuilder("SELECT COALESCE(ROUND(SUM(CAST(Calificacion as FLOAT))/COUNT(CAST(Calificacion as FLOAT)),0),0) Calificacion, COALESCE(ReqRealizados,0) ReqRealizados, COALESCE(TotalReq,0) TotalReq, COALESCE(No_Pendientes,0) No_Pendientes, COALESCE(No_Hallazgos,0) No_Hallazgos, COALESCE(No_Requisiciones,0) No_Requisiciones FROM \n");
			sbQuery.append("(SELECT Calificacion Calificacion, (select COUNT(*) from visitaCliente left join SolicitudVisita on FK04_VisitaCliente = PK_Formulario where PK_Formulario = ").append(visita).append(" and Respuesta is not null) as ReqRealizados, \n");
			sbQuery.append("(select COUNT(*) from visitaCliente left join SolicitudVisita on FK04_VisitaCliente = PK_Formulario where PK_Formulario = ").append(visita).append(") AS TotalReq, No_Pendientes, No_Hallazgos, No_Requisiciones \n");
			sbQuery.append("FROM VisitaCliente LEFT JOIN SolicitudVisita on PK_Formulario = FK04_VisitaCliente  \n");
			sbQuery.append("LEFT JOIN (select FK01_Visita, COUNT(*) No_Pendientes from HallazgosAccionesReporteVisita where Tipo like 'acci%' AND FK01_Visita= ").append(visita).append(" GROUP BY HallazgosAccionesReporteVisita.FK01_Visita) AS Hallazgos on Hallazgos.FK01_Visita = PK_Formulario \n");
			sbQuery.append("LEFT JOIN (select FK01_Visita, COUNT(*) No_Hallazgos from HallazgosAccionesReporteVisita where Tipo like 'HALLAZGO' AND FK01_Visita= ").append(visita).append(" GROUP BY HallazgosAccionesReporteVisita.FK01_Visita) AS Acciones on Acciones.FK01_Visita = PK_Formulario \n");
			sbQuery.append("LEFT JOIN (select PK_Formulario, COUNT(*) No_Requisiciones from doctosr inner join requisicion on doctosr.folio = requisicion.fk01_DoctoR inner join PRequisicion on PRequisicion.idDoctoR = doctosr.folio inner join VisitaCliente on requisicion.FK06_Visita = VisitaCliente.PK_Formulario WHERE PK_Formulario = ").append(visita).append(" GROUP BY VisitaCliente.PK_Formulario) AS Requisiciones on Requisiciones.PK_Formulario = VisitaCliente.PK_Formulario \n");
			sbQuery.append("where VisitaCliente.PK_Formulario = ").append(visita).append(") as a GROUP BY ReqRealizados, TotalReq, No_Pendientes, No_Hallazgos, No_Requisiciones \n");
			
			log.info(sbQuery.toString());
			return (VisitaCliente) super.jdbcTemplate.queryForObject(sbQuery.toString(),map,  new ObtenerDatosSeccionReporteRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List <HallazgosAcciones> obtenerDatosSeccionHallazgos(int visita) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("select PK_HallazgosAcciones, Descripcion, FERealizacion, Desc_Accion, FK05_EV_Asignado, Descartado, MotivoDescartado from HallazgosAccionesReporteVisita where Tipo like 'Hallazgo' and FK01_Visita = ").append(visita);
			
			log.info(sbQuery.toString());
			
			return jdbcTemplate.query(sbQuery.toString(), new ObtenerDatosSeccionHallazgosRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ReportarVisita obtenerReportarVisita(Integer idVisita) throws ProquifaNetException {
		try {	Map<String, Object> map = new HashMap<String, Object>();
		map.put("idVisita", idVisita);
			ReportarVisita reporte = new ReportarVisita();
			//Requerimientos
			StringBuilder sbQuery = new StringBuilder("SELECT 'REQUERIMIENTOS' ETAPA, PK_SolicitudVisita, SV.Motivo, SV.Asunto, C.Contacto, C.idContacto, SV.Calificacion, SV.Respuesta, SV.Fecha, E.Nombre, E.Usuario FROM SolicitudVisita SV \n");
			sbQuery.append("INNER JOIN Contactos C ON C.idContacto = SV.FK02_Contacto \n");
			sbQuery.append("INNER JOIN EMPLEADOS E ON E.Clave = SV.FK03_Empleado \n");
			sbQuery.append("WHERE SV.FK04_VisitaCliente = ").append(idVisita).append(" \n");
			
			ReportarVisitaRowMapper reporteRowMapper = new ReportarVisitaRowMapper();
			
			jdbcTemplate.query(sbQuery.toString(), reporteRowMapper );
			if (reporteRowMapper.getLstEtapa() != null && reporteRowMapper.getLstEtapa().containsKey("REQUERIMIENTOS"))
				reporte.setRequerimientos((List<SolicitudVisita>) reporteRowMapper.getLstEtapa().get("REQUERIMIENTOS"));
			
			//Pendientes
			sbQuery = new StringBuilder("SELECT 'PENDIENTES' ETAPA, HAv.PK_HallazgosAcciones, HAV.Descripcion, HAV.FERealizacion, HAV.Observaciones FROM HallazgosAccionesReporteVisita HAV \n");
			sbQuery.append("WHERE HAV.FK01_Visita = ").append(idVisita).append(" AND HAV.Tipo = 'Acción' \n");
			reporteRowMapper = new ReportarVisitaRowMapper();
			jdbcTemplate.query(sbQuery.toString(), reporteRowMapper );
			if (reporteRowMapper.getLstEtapa() != null && reporteRowMapper.getLstEtapa().containsKey("PENDIENTES"))
				reporte.setPendientes((List<HallazgosAcciones>) reporteRowMapper.getLstEtapa().get("PENDIENTES"));
			
			//Hallazgos
			sbQuery = new StringBuilder("SELECT 'HALLAZGOS' ETAPA, HAv.PK_HallazgosAcciones, HAV.Descripcion, HAV.FERealizacion, HAV.Observaciones, HAV.TipoHallazgo, HAV.Competencia, PROV.Nombre, PROv.RSocial, MH.FK02_Marca, HAV.FK01_Visita, HAV.Desc_Accion, HAV.MotivoDescartado  \n");
			sbQuery.append("FROM HallazgosAccionesReporteVisita HAV \n");
			sbQuery.append("LEFT JOIN Marca_Hallazgo MH ON MH.FK01_Hallazgo = HAV.PK_HallazgosAcciones \n");
			sbQuery.append("LEFT JOIN Proveedores PROV ON PROV.Clave = MH.FK02_Marca \n");
			sbQuery.append("WHERE HAV.FK01_Visita = ").append(idVisita).append(" AND HAV.Tipo = 'Hallazgo' \n");
			reporteRowMapper = new ReportarVisitaRowMapper();
			jdbcTemplate.query(sbQuery.toString(), reporteRowMapper );
			
			if (reporteRowMapper.getLstEtapa() != null && reporteRowMapper.getLstEtapa().containsKey("HALLAZGOS")) {
				List<HallazgosAcciones> lstHallazgosAcciones = new ArrayList<HallazgosAcciones>();
				Map<String, Object> mapHallazgo = (Map<String, Object>) reporteRowMapper.getLstEtapa().get("HALLAZGOS");
				for (Entry<String, Object> entryHallazgo : mapHallazgo.entrySet()) {
					HallazgosAcciones hallazgo = (HallazgosAcciones) entryHallazgo.getValue();
					lstHallazgosAcciones.add(hallazgo);
				}
				reporte.setHallazgos(lstHallazgosAcciones);
			}
			
			//Requisicion
			sbQuery = new StringBuilder("SELECT 'REQUISICIONES' ETAPA, PRequisicion.FK01_Requisicion, requisicion.FK02_Cliente, requisicion.FK03_Contacto, requisicion.FK04_EV, requisicion.FK05_ESAC, requisicion.FK06_Visita, PRequisicion.Marca, PRequisicion.Codigo, PRequisicion.Concepto, PRequisicion.Cant, PRequisicion.Cantidad, PRequisicion.Unidad, Requisicion.FK01_DoctoR as idDoctoR, PRequisicion.Precio  \n");
			sbQuery.append("from doctosr inner join requisicion on doctosr.folio = requisicion.fk01_DoctoR \n");
			sbQuery.append("left join PRequisicion on PRequisicion.idDoctoR = doctosr.folio \n");
			sbQuery.append("inner join VisitaCliente on requisicion.FK06_Visita = VisitaCliente.PK_Formulario \n");
			sbQuery.append("where requisicion.FK06_Visita = ").append(idVisita);
		//	logger.info(sbQuery);
			reporteRowMapper = new ReportarVisitaRowMapper();
			jdbcTemplate.query(sbQuery.toString(), reporteRowMapper );
			
			//Requisitar...
			if (reporteRowMapper.getLstEtapa() != null && reporteRowMapper.getLstEtapa().containsKey("REQUISICIONES"))
				reporte.setRequisicion((Requisicion) reporteRowMapper.getLstEtapa().get("REQUISICIONES"));
			
			return reporte;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ObtenerVisitas> obtenerAsuntos(Integer idVisita, Integer idContacto) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("select SolicitudVisita.PK_SolicitudVisita, SolicitudVisita.Asunto, SolicitudVisita.Motivo, Empleados.Nombre, nombreDocs.NombresDocs from SolicitudVisita  \n");
			sbQuery.append("inner join VisitaCliente on PK_Formulario = FK04_VisitaCliente \n");
			sbQuery.append("inner join Empleados on Clave = VisitaCliente.FK03_Empleado \n");
			sbQuery.append("inner join ( \n");
			sbQuery.append("	SELECT PK_Formulario, PK_SolicitudVisita, STUFF((  \n");
			sbQuery.append("    SELECT '|' + Nombre FROM ( SELECT SolicitudVisitaDocumentacion.FK01_SolicitudVisita, SolicitudVisitaDocumentacion.Nombre from VisitaCliente inner join Contactos on  \n");
			sbQuery.append("    VisitaCliente.FK02_Contacto=Contactos.idContacto left join Clientes on Clientes.Clave = FK02_Cliente left join Empleados on FK01_EV = Empleados.Clave \n");
			sbQuery.append("    left join SolicitudVisita on PK_Formulario=FK04_VisitaCliente left join SolicitudVisitaDocumentacion on FK01_SolicitudVisita=PK_SolicitudVisita) PP \n");
			sbQuery.append("	WHERE PP.FK01_SolicitudVisita = C.PK_Formulario FOR XML PATH('') ), 1, 1, '') NombresDocs FROM ( \n");
			sbQuery.append(" 	SELECT PK_Formulario, PK_SolicitudVisita, Fecha_Visita, Contacto, Folio, FK02_Cliente, FechaEstimada, Empleados.Puesto, Area_Contacto, Ruta, VisitaCliente.Asunto, Reporte, Clientes.Nombre, eMail, Tel1, Extension1, Tel2,  \n");
			sbQuery.append("	Extension2 from VisitaCliente  inner join Contactos on VisitaCliente.FK02_Contacto=Contactos.idContacto left join Clientes on Clientes.Clave = FK02_Cliente left join Empleados on FK01_EV = Empleados.Clave \n");
			sbQuery.append(" 	left join SolicitudVisita on PK_Formulario=FK04_VisitaCliente left join SolicitudVisitaDocumentacion on FK01_SolicitudVisita=PK_SolicitudVisita \n");
			sbQuery.append("	where VisitaCliente.FK02_Contacto = ").append(idContacto).append(" \n");
			sbQuery.append(" 	--and FechaEstimada > CONVERT(DATE, ' 2017-08-04') \n");
			sbQuery.append(" 	--and Folio not like ' FolioVisita +'\n");
			sbQuery.append(" 	and PK_Formulario NOT IN (SELECT DISTINCT(COALESCE(FK04_VisitaVinculada,0)) FROM HallazgosAccionesReporteVisita)\n");
			sbQuery.append(") C\n");
			sbQuery.append(") as nombreDocs on nombreDocs.PK_SolicitudVisita = SolicitudVisita.PK_SolicitudVisita\n");
			sbQuery.append("where VisitaCliente.PK_Formulario = ").append(idVisita).append(" \n");
			
		log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new ObtenerAsuntosRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ObtenerPrecios> obtenerPrecios(Integer idProducto, Integer idCliente, Integer idProveedor, String tipo, String subTipo, String control) {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT 'PRODUCTO' Grafica, PC.Clave, Cant, CASE WHEN PC.Estado = 'Pedido' THEN 'Pedido' ELSE 'Cotización' END Estado, MONTH(C.Fecha) Mes, \n");
			sbQuery.append(" \n");
			sbQuery.append("CASE WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio/Mon.PDolar, 0) \n");
			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio*Mon.EDolar, 0) \n");
			sbQuery.append("WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio, 0) \n");
			sbQuery.append("end PrecioDolares, \n");
			sbQuery.append(" \n");
			sbQuery.append("CASE WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio*Mon.PDolar, 0) \n");
			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio*Mon.EDolar*Mon.PDolar, 0) \n");
			sbQuery.append("WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio, 0) \n");
			sbQuery.append("end PrecioPesos, \n");
			sbQuery.append(" \n");
			sbQuery.append("CASE WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio/Mon.EDolar, 0) \n");
			sbQuery.append("WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio/Mon.PDolar/Mon.EDolar, 0) \n");
			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio, 0) \n");
			sbQuery.append("end PrecioEuros \n");
			sbQuery.append("FROM PCotizas PC \n");
			sbQuery.append("LEFT JOIN Cotizas C on PC.Clave = C.Clave \n");
			sbQuery.append("LEFT JOIN Productos Prod on Prod.idProducto = PC.FK03_idProducto \n");
			sbQuery.append("LEFT JOIN Monedas Mon on CAST(Mon.Fecha as date) = CAST(C.Fecha as DATE) \n");
			sbQuery.append(" \n");
			sbQuery.append("WHERE \n");
			sbQuery.append("PC.Folio = 99 AND \n");
			sbQuery.append("C.Fecha BETWEEN DATEADD(MM, -6,GETDATE()) AND GETDATE() \n");
			sbQuery.append("AND Prod.idProducto = ").append(idProducto).append(" \n");
			sbQuery.append("AND C.FK01_idCliente = ").append(idCliente).append(" \n");
			sbQuery.append(" \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'PRODUCTO' Grafica, PC.Clave, '0' Cant, CASE WHEN PC.Estado = 'Pedido' THEN 'Cotización' ELSE 'Pedido' END Estado, MONTH(C.Fecha) Mes, 0 PrecioDolares, 0 PrecioPesos, 0 PrecioEuros  \n");
			sbQuery.append("FROM PCotizas PC \n");
			sbQuery.append("LEFT JOIN Cotizas C on PC.Clave = C.Clave  \n");
			sbQuery.append("LEFT JOIN Productos Prod on Prod.idProducto = PC.FK03_idProducto  \n");
			sbQuery.append("WHERE \n");
			sbQuery.append("PC.Folio = 99 AND \n");
			sbQuery.append("C.Fecha BETWEEN DATEADD(MM, -6,GETDATE()) AND GETDATE()  \n");
			sbQuery.append("AND Prod.idProducto = ").append(idProducto).append(" \n");
			sbQuery.append("AND C.FK01_idCliente = ").append(idCliente).append(" \n");
			sbQuery.append("ORDER BY CLAVE, ESTADO DESC \n");
			
			
//			sbQuery.append("UNION \n");
//			sbQuery.append(" \n");
//			sbQuery.append("SELECT 'FAMILIA' Grafica , PC.Clave, Cant, PC.Estado, MONTH(C.Fecha) Mes,  \n");
//			sbQuery.append("CASE WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio/Mon.PDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio*Mon.EDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio, 0) \n");
//			sbQuery.append("end PrecioDolares, \n");
//			sbQuery.append(" \n");
//			sbQuery.append("CASE WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio*Mon.PDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio*Mon.EDolar*Mon.PDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio, 0) \n");
//			sbQuery.append("end PrecioPesos, \n");
//			sbQuery.append(" \n");
//			sbQuery.append("CASE WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio/Mon.EDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio/Mon.PDolar/Mon.EDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio, 0) \n");
//			sbQuery.append("end PrecioEuros \n");
//			sbQuery.append("FROM PCotizas PC \n");
//			sbQuery.append("LEFT JOIN Cotizas C on PC.Clave = C.Clave \n");
//			sbQuery.append("LEFT JOIN Productos Prod on Prod.idProducto = PC.FK03_idProducto \n");
//			sbQuery.append("LEFT JOIN Monedas Mon on CAST(Mon.Fecha as date) = CAST(C.Fecha as DATE) \n");
//			sbQuery.append(" \n");
//			sbQuery.append("WHERE \n");
//			sbQuery.append("PC.Folio = 99 AND \n");
//			sbQuery.append("C.Fecha BETWEEN DATEADD(MM, -6,GETDATE()) AND GETDATE() \n");
//			
//			if(tipo != null)
//				sbQuery.append("AND Prod.Tipo = '").append(tipo).append("' \n");
//			else
//				sbQuery.append("AND Prod.Tipo = ").append(tipo).append(" \n");
//			
//			if(subTipo != null)
//				sbQuery.append("AND Prod.Subtipo = '").append(subTipo).append("' \n");
//			else
//				sbQuery.append("AND Prod.Subtipo = ").append(subTipo).append(" \n");
//			if(tipo != null)
//				sbQuery.append("AND Prod.Control = '").append(control).append("' \n");
//			else
//				sbQuery.append("AND Prod.Control = ").append(control).append(" \n");
//				
//			sbQuery.append(" \n");
//			sbQuery.append("UNION \n");
//			sbQuery.append(" \n");
//			sbQuery.append("SELECT 'MARCA' Grafica, PC.Clave, Cant, PC.Estado, MONTH(C.Fecha) Mes,  \n");
//			sbQuery.append("CASE WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio/Mon.PDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio*Mon.EDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio, 0) \n");
//			sbQuery.append("end PrecioDolares, \n");
//			sbQuery.append(" \n");
//			sbQuery.append("CASE WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio*Mon.PDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio*Mon.EDolar*Mon.PDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio, 0) \n");
//			sbQuery.append("end PrecioPesos, \n");
//			sbQuery.append(" \n");
//			sbQuery.append("CASE WHEN C.IMoneda = 'Dolares' THEN ROUND(Precio/Mon.EDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Pesos' THEN ROUND(Precio/Mon.PDolar/Mon.EDolar, 0) \n");
//			sbQuery.append("WHEN C.IMoneda = 'Euros' THEN ROUND(Precio, 0) \n");
//			sbQuery.append("end PrecioEuros \n");
//			sbQuery.append("FROM PCotizas PC \n");
//			sbQuery.append("LEFT JOIN Cotizas C on PC.Clave = C.Clave \n");
//			sbQuery.append("LEFT JOIN Productos Prod on Prod.idProducto = PC.FK03_idProducto \n");
//			sbQuery.append("LEFT JOIN Monedas Mon on CAST(Mon.Fecha as date) = CAST(C.Fecha as DATE) \n");
//			sbQuery.append(" \n");
//			sbQuery.append("WHERE \n");
//			sbQuery.append("PC.Folio = 99 AND \n");
//			sbQuery.append("C.Fecha BETWEEN DATEADD(MM, -6,GETDATE()) AND GETDATE() \n");
//			sbQuery.append("AND Prod.Proveedor = ").append(idProveedor).append(" \n");
//			sbQuery.append("AND C.FK01_idCliente = ").append(idCliente).append(" \n");	
			
			log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new ObtenerPreciosRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean updateRequerimientos(List<SolicitudVisita> requerimientos) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("requerimientos", requerimientos);
			StringBuilder sbQuery = new StringBuilder("\n");
			for (SolicitudVisita solicitudVisita : requerimientos) {
				sbQuery.append("UPDATE SolicitudVisita SET Respuesta = ");
				if (solicitudVisita.getRespuesta() != null)
					sbQuery.append("'").append(solicitudVisita.getRespuesta()).append("', ");
				else
					sbQuery.append("NULL, ");
				sbQuery.append("Calificacion = ");
				if (solicitudVisita.getCalificacion() != null)
					sbQuery.append("'").append(solicitudVisita.getCalificacion()).append("' ");
				else
					sbQuery.append("NULL ");
				sbQuery.append(" \n");
				sbQuery.append("WHERE PK_SolicitudVisita = ").append(solicitudVisita.getIdSolicitudVisita()).append(" \n");
				
				if (solicitudVisita.getDocumento() != null && solicitudVisita.getDocumento().getArchivo() != null) {
					String ruta = function.obtenerRutaServidor("requerimientos", "");
					Funcion.subirArchivo(solicitudVisita.getDocumento().getArchivo(), solicitudVisita.getIdSolicitudVisita().toString() + ".pdf", ruta);
				}
			}
			jdbcTemplate.update(sbQuery.toString(), map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		return false;
	}

	@Override
	public boolean updatePendientes(List<HallazgosAcciones> pendientes, Integer idVisita) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idVisita", idVisita);
			map.put("pendientes", pendientes);
			StringBuilder sbQuery = new StringBuilder(" \n");
			String ids = "0,";
			for (HallazgosAcciones hallazgosAcciones : pendientes) {
				if (hallazgosAcciones.getIdHallazgoAccion() != null && 
						hallazgosAcciones.getIdHallazgoAccion() > 0) {
					sbQuery.append("UPDATE HallazgosAccionesReporteVisita SET Tipo = 'Acción', Descripcion = \n");
					if (hallazgosAcciones.getDescripcion() != null)
						sbQuery.append("'").append(hallazgosAcciones.getDescripcion()).append("', ");
					else
						sbQuery.append("NULL, \n");
					sbQuery.append("FERealizacion = \n");
					if (hallazgosAcciones.getFerealizacion() != null)
						sbQuery.append("'").append(formatter.format(hallazgosAcciones.getFerealizacion())).append("' ");
					else
						sbQuery.append("NULL \n");
					sbQuery.append("WHERE PK_HallazgosAcciones = ").append(hallazgosAcciones.getIdHallazgoAccion()).append(" \n");
					ids += hallazgosAcciones.getIdHallazgoAccion() + ",";
				} else {
					StringBuilder sbQueryInsert = new StringBuilder(" \n");
					sbQueryInsert.append("INSERT INTO HallazgosAccionesReporteVisita(Descripcion, Tipo, FK01_Visita, FERealizacion) \n");
					sbQueryInsert.append("VALUES('").append(hallazgosAcciones.getDescripcion()).append("', 'Acción', ").append(hallazgosAcciones.getIdVisita()).append(", '").append(formatter.format(hallazgosAcciones.getFerealizacion())).append("') \n");
					jdbcTemplate.update(sbQueryInsert.toString(), map);
					ids += jdbcTemplate.queryForObject("SELECT IDENT_CURRENT ('HallazgosAccionesReporteVisita')",map, String.class) + ",";
					log.info(sbQueryInsert.toString());
				}
			}
			if (ids.length() > 0)
				ids = ids.substring(0, ids.length() - 1);
			sbQuery.append("DELETE FROM HallazgosAccionesReporteVisita \n");
			sbQuery.append("WHERE FK01_Visita = ").append(idVisita).append(" and tipo like 'Acción' AND PK_HallazgosAcciones NOT IN (").append(ids).append(") \n");
			log.info(sbQuery.toString());
			jdbcTemplate.update(sbQuery.toString(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<HallazgosAcciones> updateHallazgos(List<HallazgosAcciones> hallazgos, Integer idVisita) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idVisita", idVisita);
			map.put("hallazgos", hallazgos);
	
			StringBuilder sbQuery = new StringBuilder("");
			String ids = "0,";
			for (HallazgosAcciones hallazgosAcciones : hallazgos) {
				sbQuery = new StringBuilder("");
				if (hallazgosAcciones.getIdHallazgoAccion() > 0) {
					sbQuery.append("UPDATE HallazgosAccionesReporteVisita SET Descripcion = '").append(hallazgosAcciones.getDescripcion()).append("', ");
					sbQuery.append("TipoHallazgo = '").append(hallazgosAcciones.getTipoHallazgo()).append("' ");
					if (hallazgosAcciones.getCompetencia() != null )					
						sbQuery.append(", Competencia = '").append(hallazgosAcciones.getCompetencia()).append("' \n");
					sbQuery.append("WHERE PK_HallazgosAcciones = ").append(hallazgosAcciones.getIdHallazgoAccion()).append(" \n");
					jdbcTemplate.update(sbQuery.toString(), map);
					if (hallazgosAcciones.getTipoHallazgo().equalsIgnoreCase("Marca")) {
						for (CatalogoItem proveedor : hallazgosAcciones.getMarcas()) {
							sbQuery = new StringBuilder("INSERT INTO Marca_Hallazgo VALUES(").append(hallazgosAcciones.getIdHallazgoAccion()).append(",").append(proveedor.getLlave()).append(")");
							log.info(sbQuery.toString());
							jdbcTemplate.update(sbQuery.toString(), map);
						}
					}
					ids += hallazgosAcciones.getIdHallazgoAccion() + ",";
				} else {
					sbQuery.append("INSERT INTO HallazgosAccionesReporteVisita(Descripcion, Tipo, FK01_Visita, FERealizacion,TipoHallazgo,Competencia) VALUES ( \n");
					sbQuery.append("'").append(hallazgosAcciones.getDescripcion()).append("', 'Hallazgo', ").append(hallazgosAcciones.getIdVisita()).append(", NULL, \n");
					sbQuery.append("'").append(hallazgosAcciones.getTipoHallazgo()).append("',");
					if (hallazgosAcciones.getCompetencia() != null )					
						sbQuery.append("'").append(hallazgosAcciones.getCompetencia()).append("') \n");
					else
						sbQuery.append("NULL) \n");
					log.info(sbQuery.toString());
					jdbcTemplate.update(sbQuery.toString(), map);
					hallazgosAcciones.setIdHallazgoAccion(super.queryForInt("SELECT IDENT_CURRENT ('HallazgosAccionesReporteVisita')", map));

					if (hallazgosAcciones.getTipoHallazgo().equalsIgnoreCase("Marca")) {
						for (CatalogoItem proveedor : hallazgosAcciones.getMarcas()) {
							sbQuery = new StringBuilder("INSERT INTO Marca_Hallazgo VALUES(").append(hallazgosAcciones.getIdHallazgoAccion()).append(",").append(proveedor.getLlave()).append(")");
							log.info(sbQuery.toString());
							jdbcTemplate.update(sbQuery.toString(), map);
						}
					}
					ids += hallazgosAcciones.getIdHallazgoAccion() + ",";
				}
			}
			if (ids.length() > 0)
				ids = ids.substring(0, ids.length() - 1);
			sbQuery = new StringBuilder("");
			sbQuery.append("DELETE FROM HallazgosAccionesReporteVisita \n");
			sbQuery.append("WHERE FK01_Visita = ").append(idVisita).append(" and tipo like 'Hallazgo' AND PK_HallazgosAcciones NOT IN (").append(ids).append(") \n");
			sbQuery.append(" \n");
			jdbcTemplate.update(sbQuery.toString(), map);
			
			log.info(sbQuery.toString());
			return hallazgos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean eliminaHallazgos(Integer idVisita) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idVisita", idVisita);
		
			StringBuilder sbQuery = new StringBuilder("DELETE FROM Marca_Hallazgo WHERE FK01_Hallazgo IN (SELECT PK_HallazgosAcciones FROM HallazgosAccionesReporteVisita WHERE FK01_Visita = ").append(idVisita).append(" AND Tipo = 'Hallazgo') \n");
			jdbcTemplate.update(sbQuery.toString(), map);
//			sbQuery = new StringBuilder("DELETE FROM HallazgosAccionesReporteVisita WHERE FK01_Visita = ").append(idVisita).append(" AND Tipo = 'Hallazgo' \n");
//			jdbcTemplate.update(sbQuery.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminaRequisiciones(Integer idVisita) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idVisita", idVisita);
		
			StringBuilder sbQuery = new StringBuilder("delete from PRequisicion where idDoctoR in (select distinct(idDoctoR) from ( ");
			sbQuery.append("select requisicion.FK02_Cliente, requisicion.FK03_Contacto, requisicion.FK04_EV, requisicion.FK05_ESAC, requisicion.FK06_Visita, PRequisicion.Concepto, PRequisicion.Cant, PRequisicion.idDoctoR, PRequisicion.FK01_Requisicion  ");
			sbQuery.append("from doctosr inner join requisicion on doctosr.folio = requisicion.fk01_DoctoR inner join PRequisicion on PRequisicion.idDoctoR = doctosr.folio  ");
			sbQuery.append("inner join VisitaCliente on requisicion.FK06_Visita = VisitaCliente.PK_Formulario where requisicion.FK06_Visita =  ").append(idVisita).append(" ) as a) \n");
			jdbcTemplate.update(sbQuery.toString(), map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean ReinsertaRequisiciones(Requisicion PRequisiciones) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("PRequisiciones", PRequisiciones);
			if(PRequisiciones != null && PRequisiciones.getPartidaRequisicion() != null){
				for(int i = 0; i < PRequisiciones.getPartidaRequisicion().size(); i++){
					StringBuilder sbQuery = new StringBuilder("INSERT INTO PRequisicion (Concepto, Cant, idDoctoR, FK01_Requisicion) VALUES( '").append(PRequisiciones.getPartidaRequisicion().get(i).getConcepto()).append("', ");
					sbQuery.append(PRequisiciones.getPartidaRequisicion().get(i).getPiezasACotizar()).append(", ").append(PRequisiciones.getIdDoctoR()).append(", ");
					sbQuery.append(PRequisiciones.getIdRequi()).append(") ");
//					log.info(sbQuery);
					jdbcTemplate.update(sbQuery.toString(), map);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}
	
	@Override
	public boolean guardaObservaciones(HallazgosAcciones hallazgoAccion) throws ProquifaNetException {
		try {
			String empleadoStr="";
			String ferealizacionStr="";
			String descAccionStr="";
			String motivoDescartado="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("hallazgoAccion", hallazgoAccion);
			if(hallazgoAccion.getUsuarioAsociado() != 0)
				empleadoStr = "FK05_EV_Asignado = "+String.valueOf(hallazgoAccion.getUsuarioAsociado())+",";
			
			if(hallazgoAccion.getFerealizacion2() != null)
				ferealizacionStr = "FERealizacion = '"+hallazgoAccion.getFerealizacion2()+"',";
			
			if(hallazgoAccion.getDescripcionAccion() != null)
				descAccionStr = "Desc_Accion = '"+String.valueOf(hallazgoAccion.getDescripcionAccion())+"',";

			if(hallazgoAccion.getCheckSeleccionado() == true){
				if(hallazgoAccion.getMotivoDescartado() != null)
					motivoDescartado = "MotivoDescartado = '"+String.valueOf(hallazgoAccion.getMotivoDescartado())+"',";				
			}
			else{
				motivoDescartado = "MotivoDescartado = '"+"',";	
			}
			
			StringBuilder sbQuery = new StringBuilder("UPDATE HallazgosAccionesReporteVisita set "+empleadoStr+" "+ferealizacionStr+" "+descAccionStr+" "+motivoDescartado+" Descartado = '"+hallazgoAccion.getCheckSeleccionado()+"' where PK_HallazgosAcciones="+hallazgoAccion.getIdHallazgoAccion()+" \n");
			log.info(sbQuery.toString());
			jdbcTemplate.update(sbQuery.toString(), map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	

	
	@SuppressWarnings("unchecked")
	@Override
	public List <Cotizacion> cotizacionesLista(Long idVisita, Integer generada) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT * FROM Cotizas where Generada = ").append(generada).append(" and FK03_idVisita = ").append(idVisita);
			
			log.info(sbQuery.toString());
			
			return jdbcTemplate.query(sbQuery.toString(), new CotizacionReporteVisitaRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List <PartidaCotizacion> obtenerListaDePartidasCO(Long idCotizacion) throws ProquifaNetException {
		try {
			
			StringBuilder sbQuery = new StringBuilder(" SELECT PC.*,Moneda.PDolar,Moneda.EDolar,Pro.tipo,pro.subtipo,pro.control,pro.proveedor, CASE WHEN CO.IMoneda = 'Pesos' THEN ROUND(PC.Precio/Mon1.PDolar, 0) WHEN CO.IMoneda = 'Euros' THEN ROUND(PC.Precio*Mon1.EDolar, 0) WHEN CO.IMoneda = 'Dolares' THEN ROUND(PC.Precio, 0) ");
			sbQuery.append(" \n  END PrecioDolares  FROM PCotizas as PC ");
			sbQuery.append(" \n  LEFT JOIN Cotizas AS CO ON CO.PK_Folio = ").append(idCotizacion);
			sbQuery.append(" \n  LEFT JOIN Productos as Pro on pro.idProducto = PC.FK03_idProducto ");
			sbQuery.append(" \n  LEFT JOIN Monedas Mon1 on CAST(Mon1.Fecha as date) = CAST(CO.Fecha as DATE) ");
			sbQuery.append(" \n  LEFT JOIN ( SELECT Mon.* FROM Monedas Mon ");
			sbQuery.append(" \n	 INNER JOIN (SELECT MAX(Fecha) Fecha FROM Monedas WHERE CAST(Fecha as Date) <= CAST(GETDATE() as DATE) ) M ON M.Fecha = Mon.Fecha) Moneda on 1=1 ");
			sbQuery.append("WHERE PC.FK02_Cotiza= ").append(idCotizacion);

						
//			StringBuilder sbQuery = new StringBuilder("SELECT * FROM PCotizas as PC ");
//			sbQuery.append(" \n LEFT JOIN ( SELECT Mon.* FROM Monedas Mon ");
//			sbQuery.append("INNER JOIN (SELECT MAX(Fecha) Fecha FROM Monedas WHERE CAST(Fecha as Date) <= CAST(GETDATE() as DATE) ) M ON M.Fecha = Mon.Fecha) Moneda on 1=1 ");
//			sbQuery.append("WHERE PC.FK02_Cotiza= ").append(idCotizacion);
			
			log.info(sbQuery.toString());
			
			return jdbcTemplate.query(sbQuery.toString(), new PartidaCotizacionReRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	
	@Override
	public Long insertarAutorizacion(String autorizo, String solicitante, String tipo, String razones, String docto)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("docto", docto);
			map.put("razones", razones);
			map.put("tipo", tipo);
			map.put("solicitante", solicitante);
			map.put("autorizo", autorizo);
			this.jdbcTemplate .update("INSERT INTO Autorizacion (Docto,Fecha,Razones,Autorizo,Solicitante,Tipo) VALUES('"+ docto +"',GETDATE(),'" +razones+ "','"+autorizo+"','"+solicitante+"','"+tipo+"')", map);
          
			Long idAutorizacion = super.queryForLong("SELECT IDENT_CURRENT ('Cartera')", map);
			
			return idAutorizacion;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
	         return -1L;
		}
	}
	
	
	
	@Override
	public Long insertarPendienteConfirmacion(String folio, String vendedor, String contacto, Long idCotizacion,String medio)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("medio", medio);
			map.put("idCotizacion", idCotizacion);
			map.put("contacto", contacto);
			map.put("vendedor", vendedor);
			map.put("folio", folio);
			String query= "INSERT INTO SegCotiza (Cotiza,Vendedor,Fecha,Contacto,Comenta,Medio,FUAccion,Estatus,FK01_Cotiza) "+
            "VALUES ('"+ folio +"','"+ vendedor +"',GETDATE(),'" +contacto+ "','','"+medio+"',GETDATE(),'En Realización(email)',"+idCotizacion +")";

			super.jdbcTemplate.update(query, map);
			//logger.info(query);
		
//			this.jdbcTemplate .update("INSERT INTO SegCotiza (Cotiza,Vendedor,Fecha,Contacto,Comenta,Medio,FUAccion,Estatus,FK01_Cotiza) VALUES('"+ folio +"','"+ vendedor +"',GETDATE(),'" +contacto+ "','','"+medio+"',GETDATE(),'En Realización(email)',"+idCotizacion +")");
          
			return 1L;
		} catch (Exception e) {
		//	logger.error(e.getMessage());
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus()
			.setRollbackOnly();
	         return -1L;
		}
	}
	
	
	@Override
	public Long obtenerCalificacion(Long idVisita)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("idVisita", idVisita);
			Long calificacion = 0L;
			String query= "SELECT CalificacionEV FROM VisitaCliente WHERE PK_Formulario = " + idVisita ;
		//	logger.info(query);
     		calificacion = super.queryForLong(query, map);
			return calificacion;
		}catch(Exception e){
			e.printStackTrace();
			new Funcion().enviarCorreoAvisoExepcion(e);
			return 0L;
		}
	}
	
	@Override
	public String obtenerNotasDeVisita(Long idVisita) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("idVisita", idVisita);
			return (String) super.jdbcTemplate.queryForObject("select Notas from VisitaCliente where PK_Formulario = "+ idVisita, map, String.class);
		} catch (Exception e) {
		//	logger.info("Error: "+ e.getMessage());
			return "";
		}	
	}
	
	
	@Override
	public Long obtenerIdContactoDeVisita(Long idVisita) throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("idVisita", idVisita);
			return (Long) super.jdbcTemplate.queryForObject("SELECT FK02_Contacto FROM VisitaCliente WHERE PK_Formulario = "+ idVisita,map, Long.class);
		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return -1L;
		}	
	}
		
}
