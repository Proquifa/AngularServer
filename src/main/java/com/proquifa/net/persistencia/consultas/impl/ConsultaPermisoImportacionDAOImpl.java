package com.proquifa.net.persistencia.consultas.impl;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.comun.ResumenPermisoImportacion;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.consultas.ConsultaPermisoImportacionDAO;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaLineaTiempoPermisoImportacionRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaPermisosDetallesGraficaRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaPermisosRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaResumenPermisoImportacionRowMapper;

@Repository
public class ConsultaPermisoImportacionDAOImpl extends DataBaseDAO implements ConsultaPermisoImportacionDAO{

	final Logger log = LoggerFactory.getLogger(ConsultaPermisoImportacionDAOImpl.class);
	
	Funcion f;
	@SuppressWarnings("unchecked")
	@Override
	public List<PermisoImportacion> consultaPermisos(Date finicio,Date ffin, Long proveedor,String catalogo, String tipoPermiso,String tipoProducto,String subTipo,
			String clasificacion,String control,String estadoFisico,String estado) {
		
		try{
			f = new Funcion();
			StringBuilder sbQuery = new StringBuilder("SELECT P.idProducto, PR.Clave ,PN.FInicio as Fecha, PR.Nombre as Proveedor, P.Codigo as Catalogo, \n");
			sbQuery.append(f.sqlDescripcionProductos("P","PC") + "  as Producto, P.Tipo, P.Subtipo, P.TipoPermiso, P.Clasificacion, P.Control, P.EstadoFisico, \n");
			sbQuery.append("(CASE WHEN p.EstadoPermiso = 'Cerrado' OR p.EstadoPermiso = 'Autorizado' THEN 'Cerrado' ELSE 'Abierto' END) AS Estado, \n");
			sbQuery.append("SP.FET, P.CAS, P.FraccionArancelaria as FA, SP.FEL, SP.FLiberacion, P.Fabrica, \n");
			sbQuery.append("CASE WHEN P.Moneda = 'Dolares' THEN p.costo WHEN P.Moneda = 'Pesos' THEN Round((P.Costo * M.PDolar),2) \n"); 
			sbQuery.append("WHEN P.Moneda = 'Euros' THEN Round((P.Costo * M.EDolar),2) WHEN P.Moneda = 'Libras' Then Round((P.Costo * M.LDolar),2) END as Costo, \n");
			sbQuery.append("CASE WHEN PN.Partida IS NULL ThEn 1 ELSe SUM(CASE WHEN PP.Cant IS NOT NULL THEN (PP.Cant) ELSE 0 END ) + \n");
			sbQuery.append("SUM(CASE WHEN PC.Cant IS not NULL THEN (PC.Cant) ElSe 0 EnD) END as NoPiezas, COUNT(P.idProducto) as NoProductos, \n");
			sbQuery.append("CASE WHEN PN.Partida IS NULL THEN 'C-Productos' ELSE (CASE WHEN SUBSTRING(PN.Partida,1,1) = 'P' ");
			sbQuery.append("THEN 'P-' + PP.CPedido ELSE 'C-' + PC.Clave END ) EnD as Origen, P.EstadoPermiso, p.moneda \n");
			sbQuery.append("FROM Productos P \n");
			sbQuery.append("LEFT JOIN Monedas m ON convert(date,m.Fecha) = convert(date,p.Fecha) \n");
			sbQuery.append("LEFT JOIN Proveedores PR ON PR.Clave = P.Proveedor \n");
			sbQuery.append("INNER JOIN Pendiente PN ON PN.Docto = CONVERT(VARCHAR(20), P.idProducto) \n");
			sbQuery.append("LEFT JOIN SolicitudPermisoImp SP ON SP.FK01_Producto = P.idProducto \n");
			sbQuery.append("LEFT JOIN PPedidos PP on PP.idPPedido = SUBSTRiNG(PN.Partida,3,LEN(PN.Partida)) \n");
			sbQuery.append("LEFT JOIN PCotizas PC oN PC.idPCotiza = SUBSTRiNG(PN.Partida,3,LEN(PN.Partida)) \n");
			sbQuery.append("WHERE PN.Tipo = 'A Tramitar PermisoImp' \n");
			
			sbQuery.append(condiciones(finicio, ffin, proveedor, catalogo, tipoPermiso, tipoProducto, subTipo, clasificacion, control, estadoFisico, estado));

			sbQuery.append("GROuP By P.idProducto, PR.Clave, PN.FInicio, PR.Nombre, P.Codigo, Convert(varchar(250),P.Concepto), P.Tipo, \n");
			sbQuery.append("P.SubTipo, P.TipoPermiso, P.Clasificacion, P.Control, P.EstadoPermiso, P.EstadoFisico, SP.FET, P.CAS, \n");
			sbQuery.append("P.FraccionArancelaria, PN.Partida, SP.FEL, SP.FLiberacion, P.Fabrica, P.Costo, PP.CPedido, PC.Clave, \n");
			sbQuery.append("P.Moneda, M.PDolar, M.EDolar, M.LDolar, P.Proveedor, P.Unidad, P.Codigo, P.Cantidad,PC.Codigo \n");
			
			sbQuery.append(" Order by Fecha \n");
			
			log.info(sbQuery.toString());

			return super.jdbcTemplate.query(sbQuery.toString(), new ConsultaPermisosRowMapper());
			
		}catch (Exception e) {
			//f.enviarCorreoAvisoExepcion(e, "finicio"+finicio, "ffin"+ffin,proveedor,catalogo,tipoPermiso,tipoProducto,subTipo,clasificacion,control,estadoFisico,estado);
			return null;
	//////			new ArrayList()<PermisoImportacion>();
		}
	
	}
	
	public String condiciones(Date finicio, Date ffin, Long proveedor,
			String catalogo, String tipoPermiso, String tipoProducto,
			String subTipo, String clasificacion, String control, String estadoFisico, String estado){
		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		
		StringBuilder sbQuery = new StringBuilder();
		try{

		if (finicio != null && ffin != null){
			sbQuery.append("AND CONVERT(DATE,PN.FInicio) BETWEEN ").append("CONVERT(DATE,'").append(formatoFecha.format(finicio)).append("',105) ");
			sbQuery.append("AND CONVERT(DATE,'").append(formatoFecha.format(ffin)).append("',105) \n");
		}
		if (proveedor != null && proveedor > 0){
			sbQuery.append("AND PR.Clave = ").append(proveedor).append(" \n");
		}
		if (catalogo != null && !catalogo.trim().equals("") && !catalogo.trim().equals("--TODOS--")){
			sbQuery.append("AND P.Codigo LIKE '%").append(catalogo).append("%' \n");
		}
		if (tipoPermiso != null && !tipoPermiso.trim().equals("") && !tipoPermiso.trim().equals("--TODOS--")){
			sbQuery.append("AND P.TipoPermiso LIKE '").append(tipoPermiso).append("' \n");
		}
		if (tipoProducto != null && !tipoProducto.trim().equals("") && !tipoProducto.trim().equals("--TODOS--")){
			sbQuery.append("AND P.Tipo LIKE '").append(tipoProducto).append("' \n");
		}
		if (subTipo != null && !subTipo.trim().equals("") && !subTipo.trim().equals("--TODOS--")){
			sbQuery.append("AND P.SubTipo LIKE '").append(subTipo).append("' \n");
		}
		if (clasificacion != null && !clasificacion.trim().equals("") && !clasificacion.trim().equals("--TODOS--")){
			sbQuery.append("AND P.Clasificacion LIKE '").append(clasificacion).append("' \n");
		}
		if (control != null && !control.trim().equals("") && !control.trim().equals("--TODOS--")){
			sbQuery.append("AND P.Control LIKE '").append(control).append("' \n");
		}
		if (estadoFisico != null && !estadoFisico.trim().equals("") && !estadoFisico.trim().equals("--TODOS--")){
			sbQuery.append("AND P.EstadoFisico LIKE '").append(estadoFisico).append("' \n");
		}
		if (estado != null && !estado.trim().equals("") && !estado.trim().equals("--TODOS--")){
			sbQuery.append("AND (CASE WHEN p.EstadoPermiso = 'Cerrado' OR p.EstadoPermiso = 'Autorizado' THEN 'Cerrado' ELSE 'Abierto' END) ");
			sbQuery.append("LIKE '").append(estado).append("' \n");
		}
		
		return sbQuery.toString();
		}
		catch(Exception e){
			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e, "finicio:"+finicio,"ffin:"+ffin,"Proveedor:"+proveedor,catalogo,tipoPermiso,tipoProducto,subTipo,clasificacion,control,estadoFisico,estado);
			return "";
		}
		
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TiempoProceso> obtenerLineaTiempo(Long idProducto) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			
			Funcion funcion = new Funcion();
			StringBuilder sbQuery = new StringBuilder("--PrevioTramitacion-- \n");

			sbQuery.append("SELECT top 1 '1' AS ID, 'Previo Tramitación' as Etiqueta, p.FInicio, p.FFin, p.Responsable, \n");

			sbQuery.append("(CASE WHEN p.FFin IS NULL THEN 0 ELSE ").append(funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p.FFin", "Dias"));
			sbQuery.append(" END) AS TT \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = p.Partida \n");
			sbQuery.append("WHERE p.Tipo LIKE 'Previo Tramitacion PermisoImp' \n");
			sbQuery.append("AND p.Partida LIKE '").append(idProducto).append("' \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("--A Tramitar Permiso-- \n");
			sbQuery.append("SELECT top 1 '2' AS ID, 'A Tramitar Permiso' as Etiqueta, p.FInicio, p.FFin, p.Responsable, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NULL THEN 0 ELSE ").append(funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p.FFin", "Dias"));
			sbQuery.append(" END) AS TT \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = p.Docto \n");
			sbQuery.append("WHERE p.Tipo LIKE 'A Tramitar PermisoImp' \n");
			sbQuery.append("AND p.Docto LIKE '").append(idProducto).append("' \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("--En autorizacion-- \n");

			sbQuery.append("SELECT top 1 '3' AS ID, 'En autorización' as Etiqueta, p.FInicio, p1.FFin, p.Responsable, \n");

			sbQuery.append("(CASE WHEN p1.FFin IS NULL THEN 0 ELSE ").append(funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p1.FFin", "Dias"));
			sbQuery.append(" END) AS TT \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("INNER JOIN SolicitudPermisoImp sp ON sp.FK02_LotePermiso = p.Docto \n");
			sbQuery.append("LEFt JOiN (SeLecT * FROM Pendiente WHERE Tipo = 'En autorizacion') p1 ON p1.Docto = p.Docto \n");
			sbQuery.append("WHERE p.Tipo LIKE 'Por Someter' \n");
			sbQuery.append("AND sp.FK01_Producto LIKE '").append(idProducto).append("' \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("--Monitorear solicitud-- \n");
			sbQuery.append("SELECT top 1 '4' AS ID, 'Monitorear' as Etiqueta, p.FInicio, p.FFin, p.Responsable, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NULL THEN 0 ELSE ").append(funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p.FFin", "Dias"));
			sbQuery.append(" END) AS TT \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("INNER JOIN SolicitudPermisoImp sp ON sp.PK_Solicitud = p.Docto \n");
			sbQuery.append("INNER JOIN Productos pr ON pr.idProducto = sp.FK01_Producto \n");
			sbQuery.append("WHERE p.Tipo LIKE 'Monitorear solicitud permiso' \n");
			sbQuery.append("AND sp.FK01_Producto LIKE '").append(idProducto).append("' \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("--Permiso Importacion-- \n");

			sbQuery.append("SELECT top 1 '5' AS ID, 'Permiso Importación' as Etiqueta, p1.FInicio, p.FFin, p.Responsable, \n");

			sbQuery.append("(CASE WHEN p.FFin IS NULL THEN 0 ELSE ").append(funcion.obtenerTiempoTranscurridoSQL("p1.FInicio", "p.FFin", "Dias"));
			sbQuery.append(" END) AS TT \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("INNER JOIN SolicitudPermisoImp sp ON sp.PK_Solicitud = p.Docto \n");
			sbQuery.append("INNER JOIN Productos pr On pr.idProducto = sp.FK01_Producto \n");
			sbQuery.append("INNER JOIN Pendiente p1 On p1.Docto = pr.idProducto AND p1.Tipo = 'A Tramitar PermisoImp' \n");
			sbQuery.append("WHERE p.Tipo LIkE 'Monitorear Solicitud Permiso' \n");
			sbQuery.append("AND p.FFin iS NoT Null \n");
			sbQuery.append("AND sp.Fk01_Producto LIkE '").append(idProducto).append("' \n");
			sbQuery.append("ORDER BY ID \n");
			
			
			////log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new ConsultaLineaTiempoPermisoImportacionRowMapper());
 		} catch (Exception e) {				
			System.out.print(e.getMessage());
			f.enviarCorreoAvisoExepcion(e, idProducto);	
			return new ArrayList<TiempoProceso>();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenPermisoImportacion> resumenPrevioTramitacion(Long idProducto) {
		try {
			
			StringBuilder sbQuery = new StringBuilder("SELECT 'Previo' as Etiqueta, pr.Fecha as FechaIngreso, \n");
			sbQuery.append("p.FFin as FechaTramitacion, pr.TipoPermiso, pr.ClasificacionProducto, (CASE WHEN p.FFin IS NOT NULL THEN (CASE WHEN ( \n");
			sbQuery.append("SELECT Docto From Pendiente WHERE Tipo LIKE 'A Tramitar PermisoImp' AND Docto = pr.idProducto) IS NOT NULL \n");
			sbQuery.append("THEN 'Si' ELSE 'No' END) ELSE '' END) as Tramitar, sp.FET, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END) AS Estado \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = p.Partida \n");
			sbQuery.append("LEFT JOIN SolicitudPermisoImp sp ON sp.FK01_Producto = pr.idProducto \n");
			sbQuery.append("WHERE p.Tipo LIKE 'Previo Tramitacion PermisoImp' \n");
			sbQuery.append("AND p.Partida LIKE '").append(idProducto).append("' \n");
			
			return super.jdbcTemplate.query(sbQuery.toString(), new ConsultaResumenPermisoImportacionRowMapper());
			
		} catch (Exception e) {			
			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e,idProducto);
			return new ArrayList<ResumenPermisoImportacion>();
		}

	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenPermisoImportacion> resumenTramitarPermiso(Long idProducto) {
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT 'A Tramitar' as Etiqueta, p.FInicio as FechaTramitacion, \n");
			sbQuery.append("p.FFin as FechaTramitar, lp.Folio as Lote, sp.TipoPermiso, sp.FolioConjunto as Solicitud, '' as Codigo, \n");
			sbQuery.append("'' as Concepto, NULL as FET, '' as FolioDocto, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END) AS Estado \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = p.Docto \n");
			sbQuery.append("LEfT JOIN SolicitudPermisoImp sp ON sp.FK01_Producto = pr.idProducto \n");
			sbQuery.append("LEFT JOIN LotePermisos lp ON lp.PK_Lote = sp.FK02_LotePermiso \n");
			sbQuery.append("WHERE p.Tipo LIKE 'A Tramitar PermisoImp' \n");
			sbQuery.append("AND p.Docto LIKE '").append(idProducto).append("' \n");
			sbQuery.append("AND p.FInicio = sp.Fecha \n");
			sbQuery.append("UNION ALL \n");
			sbQuery.append("SELECT 'Solicitud' as Etiqueta, NULL, NULL, '', '', sp.FolioConjunto as Solicitud, \n");
			sbQuery.append("pr.Codigo, pr.Concepto, sp.FET, sp.FolioDocto, '' \n");
			sbQuery.append("FROM SolicitudPermisoImp sp \n");
			sbQuery.append("LEFt JOIN Productos pr ON pr.idProducto = sp.FK01_Producto \n");
			sbQuery.append("WHERE sp.FolioConjunto IN ( SELECT sp.FolioConjunto FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = p.Docto \n");
			sbQuery.append("LEFT JOIN SolicitudPermisoImp sp ON sp.FK01_Producto = pr.idProducto \n");
			sbQuery.append("LEFT JOIN LotePermisos lp ON lp.PK_Lote = sp.FK02_LotePermiso \n");
			sbQuery.append("WHERE p.Tipo LIKE 'A Tramitar PermisoImp' \n");
			sbQuery.append("AND p.Docto Like '").append(idProducto).append("' ) \n");
			
			return super.jdbcTemplate.query(sbQuery.toString(), new ConsultaResumenPermisoImportacionRowMapper());
		} catch (Exception e) {

			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e, idProducto);
			return new ArrayList<ResumenPermisoImportacion>();
		}
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenPermisoImportacion> resumenEnAutorizacion(Long idProducto) {
		
		try {
			
			StringBuilder sbQuery = new StringBuilder("SELECT 'En Autorizacion' as Etiqueta, p.FInicio as FechaTramitar, \n");
			sbQuery.append("p1.FFin as PorSometer, sp.TipoPermiso, lp.Folio as Lote, sp.FolioConjunto as Solicitud, \n");
			sbQuery.append("sp.FSometio as FechaSometio, sp.FEL, sp.NoEntrada, sp.DoctoEntrada, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END) AS Estado \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'En Autorizacion' ) p1 ON p1.Docto = p.Docto \n");
			sbQuery.append("LEFT JOIN LotePermisos lp ON lp.PK_Lote = p.Docto \n");
			sbQuery.append("LEFT JOIN SolicitudPermisoImp sp ON sp.FK02_LotePermiso = lp.PK_Lote \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = sp.FK01_Producto \n");
			sbQuery.append("WHERE p.Tipo = 'Por Someter' \n");
			sbQuery.append("AND pr.idProducto = '").append(idProducto).append("' \n");

			//log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(), new ConsultaResumenPermisoImportacionRowMapper());
		} catch (Exception e) {

			log.info(e.getMessage());

			f.enviarCorreoAvisoExepcion(e, idProducto);
			return new ArrayList<ResumenPermisoImportacion>();
		}
		
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenPermisoImportacion> resumenMonitorearResumen(Long idProducto) {
		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto);
			StringBuilder sbQuery = new StringBuilder("SELECT 'Monitorear' as Etiqueta, p.FInicio as FechaAutorizacion, sp.FEL, sp.FLiberacion, \n");
			sbQuery.append("lp.Folio as Lote, sp.FolioConjunto as Solicitud, pe.FCaducidad as Vigencia, pe.Numero, pe.Documento, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END) AS Estado \n");
			sbQuery.append("FROM Pendiente p \n");
			sbQuery.append("LEFT JOIN SolicitudPermisoImp sp ON sp.PK_Solicitud = p.Docto \n");
			sbQuery.append("LEFT JOIN LotePermisos lp ON lp.PK_Lote = p.Partida \n");
			sbQuery.append("LEFT JOIN Productos pr ON pr.idProducto = sp.FK01_Producto \n");
			sbQuery.append("LEFT JOIN Permiso pe ON pe.FK01_Producto = pr.idProducto \n");
			sbQuery.append("WHERE p.Tipo LIKE 'Monitorear solicitud permiso' \n");
			sbQuery.append("AND sp.FK01_Producto LIKE '").append(idProducto).append("' \n");
			return super.jdbcTemplate.query(sbQuery.toString(), map,new ConsultaResumenPermisoImportacionRowMapper());
		
		} catch (Exception e) {

			log.info(e.getMessage());

			f.enviarCorreoAvisoExepcion(e, idProducto);
			return new ArrayList<ResumenPermisoImportacion>();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenPermisoImportacion> resumenPermiso(Long idProducto) {
		
		try {
			f = new Funcion();
			StringBuilder sbQuery = new StringBuilder("SELECt 'Permiso' AS Etiqueta, pr.Fecha As FechaIngreso, pp.FInicio as FechaTramitacion, \n");
			sbQuery.append("sp.FET, sp.TipoPermiso, pr.Clasificacion as ClasificacionProducto,"+ f.sqlDescripcionProductos("pr","pr")+" Concepto, pp.FFin as FechaTramitar, lp.Folio as Lote, \n");
			sbQuery.append("sp.FolioConjunto as Solicitud, sp.FSometio as FechaSometio, sp.FEL, sp.NoEntrada, sp.DoctoEntrada, p1.FFin as FechaAutorizacion, \n");
			sbQuery.append("sp.FLiberacion, pm.FCaducidad as Vigencia, pm.Numero, pm.Documento, \n");
			sbQuery.append("(CASE WHEN p.FFin IS NOT NULL THEN 'Cerrado' ELSE 'Abierto' END) AS Estado \n");
			sbQuery.append("from Pendiente p \n");
			sbQuery.append("INNER JOIN SolicitudPermisoImp sp ON sp.PK_Solicitud = p.Docto \n");
			sbQuery.append("INNER JOIN Productos pr ON pr.idProducto = sp.FK01_Producto \n");
			sbQuery.append("LEFT JOIN LotePermisos lp ON lp.PK_Lote = sp.FK02_LotePermiso \n");
			sbQuery.append("LEFT JOIN Permiso pm ON pm.FK01_Producto = pr.idProducto \n");
			sbQuery.append("LEFT JOIN Pendiente pp ON pp.Docto = pr.idProducto \n");
			sbQuery.append("LEFT JOIN Pendiente p1 ON p1.Docto = lp.PK_Lote \n");
			sbQuery.append("WHERE p.Tipo LIKE 'Monitorear solicitud permiso' \n");
			sbQuery.append("and p.FFin is not null \n");
			sbQuery.append("AND pp.Tipo LIKE 'A Tramitar PErmisoImp' \n");
			sbQuery.append("AND p1.Tipo LIKE 'En Autorizacion' \n");
			sbQuery.append("AND sp.FK01_Producto LIKE '").append(idProducto).append("' \n");
			
			return super.jdbcTemplate.query(sbQuery.toString(), new ConsultaResumenPermisoImportacionRowMapper());
			
		} catch (Exception e) {

			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e, idProducto);
			return new ArrayList<ResumenPermisoImportacion>();
		}
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public PermisoImportacion obtenerDetallesGrafica(Date finicio, Date ffin, Long proveedor,
			String catalogo, String tipoPermiso, String tipoProducto,
			String subTipo, String clasificacion, String control, String estadoFisico, String estado) {
/**/		
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", finicio);
			map.put("proveedor", ffin);
			map.put("estado", proveedor);
			map.put("ordenCompra", catalogo);
			map.put("usuario", tipoPermiso);
			map.put("empresaCompra", tipoProducto);
			map.put("ordenCompra", subTipo);
			map.put("usuario", clasificacion);
			map.put("empresaCompra", control);
			map.put("usuario", estadoFisico);
			map.put("empresaCompra", estado);
			
			
			StringBuilder sbQuery = new StringBuilder("SELECT COUNT(Distinct(P.idProducto)) as 'NoProductos', \n");
			sbQuery.append("ROUND(SUM(P.Costo),2) as Monto, Sum(CASE WHEN PN.Partida IS NULL Then 1 ELse 0 end) + \n");
			sbQuery.append("SUM(Case When PP.Cant IS NoT NULL Then (PP.Cant) ElSe 0 EnD ) + \n");
			sbQuery.append("SUM(Case When PC.Cant Is NoT NULL Then (PC.Cant) ELSe 0 EnD ) as NoPiezas \n");
			sbQuery.append("FROM Productos P \n");
			sbQuery.append("LEFT JOIN Pendiente PN ON PN.Docto = CONVERT(varchar(50),P.idProducto) \n");
			sbQuery.append("LEFt JOIN Proveedores PR ON PR.Clave = P.Proveedor \n");
			sbQuery.append("LEFT JOIN SolicitudPermisoImp SP ON SP.FK01_Producto = P.idProducto \n");
			sbQuery.append("LEFt JOIN PPedidos PP ON PP.idPPedido = SubString(PN.Partida,3,LEN(PN.Partida)) \n");
			sbQuery.append("LEfT JOIN PCotizas PC ON PC.idPCotiza = SubString(PN.Partida,3,LEN(PN.Partida)) \n");
			sbQuery.append("WHERE PN.Tipo LIkE 'A Tramitar PermisoImp' \n");
			sbQuery.append(condiciones(finicio, ffin, proveedor, catalogo, tipoPermiso, tipoProducto, subTipo, clasificacion, control, estadoFisico, estado));

			return (PermisoImportacion) super.jdbcTemplate.queryForObject(sbQuery.toString(), map, new ConsultaPermisosDetallesGraficaRowMapper());

		} catch (Exception e) {
			log.info(e.getMessage());
			f.enviarCorreoAvisoExepcion(e, "finicio:"+finicio, "ffin:"+ffin,proveedor,catalogo,tipoPermiso,tipoProducto,subTipo,clasificacion,control,estadoFisico,estado);
			return new PermisoImportacion();
		}
		
	
	}

	
	
	
}
