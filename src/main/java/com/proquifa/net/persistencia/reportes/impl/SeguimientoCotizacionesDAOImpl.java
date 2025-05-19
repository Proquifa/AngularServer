package com.proquifa.net.persistencia.reportes.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.HistorialPartidaEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.PartidaCotizacionEnSeguimiento;
import com.proquifa.net.modelo.ventas.reportes.seguimientos.SeguimientoCotizacion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.reportes.SeguimientoCotizacionesDAO;
import com.proquifa.net.persistencia.reportes.impl.mapper.HistorialPartidaEnSeguimientoRowMapper;
import com.proquifa.net.persistencia.reportes.impl.mapper.PartidasPorCotizacionEnSeguimientoRowMapper;
import com.proquifa.net.persistencia.reportes.impl.mapper.SeguimientoCotizacionRowMapper;

@Repository
public class SeguimientoCotizacionesDAOImpl extends DataBaseDAO implements SeguimientoCotizacionesDAO{
	
	com.proquifa.net.modelo.comun.util.Funcion f;
	
	final Logger log = LoggerFactory.getLogger(SeguimientoCotizacionesDAOImpl.class);
	
	private String queryFindSeguimientoCotizacion(String Cliente,String Empleado, int confirmacion, List<NivelIngreso> niveles){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Cliente", Cliente);
			map.put("Empleado", Empleado);
			map.put("confirmacion", confirmacion);
			map.put("niveles", niveles);
			
		
		String findcliente ="";
		String findresponsable="";
		String findvendedor="";
		f = new com.proquifa.net.modelo.comun.util.Funcion();
		if(Empleado!=null && !Empleado.equals("")){
			String e = (String) super.jdbcTemplate.queryForObject("SELECT " +
					"(CASE WHEN F.Nombre='Ejecutivo de Servicio a Clientes' THEN ' Vendedor=@' + CAST(e.Usuario AS Varchar(50)) + '@' " +
					"    WHEN F.Nombre='Ejecutivo de Servicio a Clientes Master' THEN ' Vendedor=@' + CAST(e.Usuario AS Varchar(50)) + '@'  " +
					"	   WHEN F.Nombre='Ejecutivo de Ventas' THEN ' FK01_EV=' + CAST(e.Clave AS Varchar(50)) " +
					"      ELSE CAST('Mostrar_Todos_Clientes' AS Varchar(22)) END) AS Usuario " +
					"FROM Funcion AS f,Empleados AS e WHERE f.PK_Funcion=e.FK01_Funcion AND e.Usuario='"+ Empleado +"'",map, String.class);
			if(!e.equals("Mostrar_Todos_Clientes")){
				findresponsable = " AND EXISTS (SELECT * FROM Cotizas WHERE Cliente IN (SELECT Nombre FROM Clientes WHERE "+ e.replace('@','\'') +") AND Pendiente.Docto=Cotizas.Clave) ";
				findvendedor=" AND EXISTS (SELECT * FROM Cotizas WHERE Cliente IN (SELECT Nombre FROM Clientes WHERE "+ e.replace('@','\'') +") AND SegCotiza.Cotiza=Cotizas.Clave) ";
			
			}
		}
		
		if(Cliente!=null && !Cliente.equals("")){
			findcliente=" AND  CL.Nombre='"+Cliente+"' ";
		}
		
		if (confirmacion==0){//NIVEL.VentasUSD
		return "DECLARE @TCEDolar REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPEDolar REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPLDolar REAL = (SELECT TOP(1)LDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPYDolar REAL = (SELECT TOP(1)YDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPDDolar REAL = (SELECT TOP(1)DDolar FROM Monedas ORDER BY Fecha DESC) " +
			" SELECT Pendiente.Tipo,SegCotiza.Cotiza,Cotizas.IMoneda,COALESCE(SegCotiza.Contacto,Cotizas.Contacto) AS Contacto,SegCotiza.Comenta,SegCotiza.FRealizo," +

			" SegCotiza.FUAccion,SegCotiza.Fecha,CASE WHEN SegCotiza.Estatus IS NULL THEN 'Por Realizar' WHEN SegCotiza.Estatus LIKE 'Cotización Reenviada' THEN 'Cotización no recibida' ELSE SegCotiza.Estatus END AS SegEstatus, " +
			" Cotizas.Cliente,cl.Vendedor,(CASE WHEN cl.EV='Administrador' THEN 'No asignado' ELSE cl.EV END) AS EV,Piezas,npart.Partidas,Empleados.Nombre," +
			f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL,NULL AS Fabrica, NULL AS TipoPro, NULL AS CONTROL," +
			" (CASE WHEN Cotizas.IMoneda='Euros' THEN SegCotizasPartidas.PrecioUSD * Monedas.EDolar WHEN Cotizas.IMoneda='Pesos' THEN SegCotizasPartidas.PrecioUSD/Monedas.PDolar ELSE SegCotizasPartidas.PrecioUSD END) AS Monto," +
			" COALESCE(PDolar,@TCEDolar) AS PDolar,COALESCE(EDolar,@TCPEDolar) AS EDolar,COALESCE(LDolar,@TCPLDolar) AS LDolar," +
			" COALESCE(YDolar,@TCPYDolar) AS YDolar,COALESCE(DDolar,@TCPDDolar) AS DDolar,DOCR.Folio" +
			" FROM Pendiente " +
			" LEFT JOIN (SELECT * FROM SegCotiza) AS SegCotiza ON SegCotiza.Cotiza=Pendiente.Docto " +

			" LEFT JOIN (SELECT COUNT(*) AS Partidas,Clave FROM PCotizas WHERE Folio=99 AND Estado='Cotización' GROUP BY Clave) AS npart ON npart.Clave=Pendiente.Docto " +

			" LEFT JOIN Cotizas ON Cotizas.Clave=Pendiente.Docto " +
			" LEFT JOIN (SELECT Clientes.Clave,Clientes.Nombre,Clientes.Vendedor,Empleados.Usuario AS EV FROM Empleados,Clientes WHERE Empleados.Clave=Clientes.FK01_EV) AS cl ON cl.Nombre=Cotizas.Cliente " +
			" LEFT JOIN (SELECT PCotizas.Clave,SUM(Cant) AS Piezas,SUM(PCotizas.Precio * PCotizas.Cant) AS PrecioUSD FROM PCotizaEnSeguimiento " +
			" RIGHT JOIN PCotizas ON PCotizaEnSeguimiento.idPCotiza=PCotizas.idPCotiza WHERE PCotizas.Folio=99 GROUP BY PCotizas.Clave) AS SegCotizasPartidas " +
			" ON SegCotizasPartidas.Clave=Cotizas.Clave " +
			" LEFT JOIN Monedas ON Monedas.Fecha=CONVERT(VARCHAR(8), SegCotiza.Fecha, 112) " +
			" LEFT JOIN Empleados ON Empleados.Usuario=Pendiente.Responsable " +
			" LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar " +
			" WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha " +
			" WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = CL.Clave " +
			" LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C " +
			" LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre " +
			" LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave) " +
			" AS OPER ON OPER.Clave = CL.Clave" +
			" LEFT JOIN (SELECT * FROM DoctosR) AS DOCR ON DOCR.Numero=SegCotiza.Cotiza" +
			" WHERE Pendiente.Tipo='Seguimiento' AND Pendiente.FFin IS NULL " + findcliente + findresponsable +
			" ORDER BY Cliente ASC";
		}
		else{
			return "DECLARE @TCEDolar REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPEDolar REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPLDolar REAL = (SELECT TOP(1)LDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPYDolar REAL = (SELECT TOP(1)YDolar FROM Monedas ORDER BY Fecha DESC) " +
			" DECLARE @TCPDDolar REAL = (SELECT TOP(1)DDolar FROM Monedas ORDER BY Fecha DESC) " +			

			" SELECT 'Confirmación' AS Tipo,SegCotiza.Cotiza,Cotizas.IMoneda,COALESCE(SegCotiza.Contacto,Cotizas.Contacto) AS Contacto,SegCotiza.Comenta,SegCotiza.FRealizo," +
			" SegCotiza.FUAccion,SegCotiza.Fecha,CASE WHEN SegCotiza.Estatus IS NULL THEN 'Por Realizar' WHEN SegCotiza.Estatus LIKE 'Cotización Reenviada' THEN 'Cotización no recibida' ELSE SegCotiza.Estatus END AS SegEstatus," +

			" Cotizas.Cliente,cl.Vendedor,(CASE WHEN cl.EV='Administrador' THEN 'No asignado ' ELSE cl.EV END) AS EV,Piezas,npart.Partidas,Empleados.Nombre," +
			f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL,NULL AS Fabrica, NULL AS TipoPro, NULL AS CONTROL," +
			" (CASE WHEN Cotizas.IMoneda='Euros' THEN SegCotizasPartidas.PrecioUSD * Monedas.EDolar WHEN Cotizas.IMoneda='Pesos' THEN SegCotizasPartidas.PrecioUSD/Monedas.PDolar ELSE SegCotizasPartidas.PrecioUSD END) AS Monto," +
			" COALESCE(PDolar,@TCEDolar) AS PDolar,COALESCE(EDolar,@TCPEDolar) AS EDolar," +
			" COALESCE(LDolar,@TCPLDolar) AS LDolar,COALESCE(YDolar,@TCPYDolar) AS YDolar," +
			" COALESCE(DDolar,@TCPDDolar) AS DDolar,DOCR.Folio " +
			" FROM SegCotiza " +
			" LEFT JOIN (SELECT COUNT(*) AS Partidas,Clave FROM PCotizas WHERE Folio=99 AND Estado='Cotización' GROUP BY Clave) AS npart ON npart.Clave=SegCotiza.Cotiza " +
			" LEFT JOIN Cotizas ON COTIZAS.Clave=SegCotiza.Cotiza " +
			" LEFT JOIN (SELECT Clientes.Clave,Clientes.Nombre,Clientes.Vendedor,Empleados.Usuario AS EV FROM Empleados,Clientes WHERE Empleados.Clave=Clientes.FK01_EV) AS cl ON cl.Nombre=Cotizas.Cliente "+
			" LEFT JOIN (SELECT PCotizas.Clave,SUM(Cant) AS Piezas,SUM(PCotizas.Precio * PCotizas.Cant) AS PrecioUSD FROM PCotizaEnSeguimiento " +
			" RIGHT JOIN PCotizas ON PCotizaEnSeguimiento.idPCotiza=PCotizas.idPCotiza WHERE PCotizas.Folio=99 GROUP BY PCotizas.Clave) AS SegCotizasPartidas ON SegCotizasPartidas.Clave=Cotizas.Clave " +
			" LEFT JOIN Monedas ON Monedas.Fecha=CONVERT(VARCHAR(8), SegCotiza.Fecha, 112) " +
			" LEFT JOIN Empleados ON Empleados.Usuario=SegCotiza.Vendedor " +
			" LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar " +
			" WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha " +
			" WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = CL.Clave " +
			" LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C " +
			" LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre " +
			" LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave) " +
			" AS OPER ON OPER.Clave = CL.Clave" +
			" LEFT JOIN (SELECT * FROM DoctosR) AS DOCR ON DOCR.Numero=SegCotiza.Cotiza" +
			" WHERE FRealizo IS NULL AND Cotizas.Estado<>'Cancelada' AND SegCotiza.Fecha>'20090901' " + findcliente + findvendedor +
			" ORDER BY Cliente ASC";
		}	
	}
		catch(Exception e){
			//logger.info("Error: "+ e.getMessage());
			f.enviarCorreoAvisoExepcion(e, Cliente,Empleado,"Confirmacion:"+confirmacion);
		return "";	
		}
		
	}

	
	@SuppressWarnings("unchecked")
	public List<SeguimientoCotizacion> findSeguimientoCotizacion(
			String Cliente, String Empleado,int confirmacion, List<NivelIngreso> niveles) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Cliente", Cliente);
			map.put("Empleado", Empleado);
			map.put("confirmacion", confirmacion);
			map.put("niveles", niveles);
			String test=queryFindSeguimientoCotizacion(Cliente,Empleado,confirmacion, niveles);
			//System.out.print(test);
			List<SeguimientoCotizacion> lsc = super.jdbcTemplate.query(test, new SeguimientoCotizacionRowMapper());
			for(SeguimientoCotizacion seg:lsc){				
				com.proquifa.net.modelo.comun.util.Funcion fn = new com.proquifa.net.modelo.comun.util.Funcion();
				if(seg.getTipoSeguimiento().equals("Seguimiento")){
					Date fechaSiguiente = (Date) super.jdbcTemplate.queryForObject("DECLARE @Cotiza AS Varchar(20) = '"+ seg.getFolioCotizacion() +"' " +

							"DECLARE @TotalPartidas AS Integer= (SELECT COUNT(*) AS Total FROM PCotizas WHERE Folio=99 AND Estado='Cotización' AND Clave=@Cotiza) " +

							"SELECT TOP 1 (CASE WHEN @TotalPartidas=0 THEN NULL ELSE pcs.FechaSiguiente END) AS FechaSiguiente " +
							"FROM PCotizaEnSeguimiento AS pcs " +
							"LEFT JOIN(SELECT idPCotiza,Clave FROM PCotizas) AS pc ON pc.Clave=@Cotiza " +
							"WHERE pcs.idPCotiza=pc.idPCotiza " +
							"ORDER BY pcs.FechaSiguiente ASC",map, Date.class);
					if(fechaSiguiente!=null){
						seg.setFechaSiguienteContacto(fechaSiguiente);
						Long dt = fn.calcularDiasAtraso(fechaSiguiente,0);
						seg.setDiasAtraso(dt);
						if(dt>0){
							seg.setEnTiempo("FT");
						}else{
							seg.setEnTiempo("ET");
						}
					}else{
						seg.setFechaSiguienteContacto(null);
						seg.setDiasAtraso(null);
						seg.setEnTiempo(null);
					}
				}else{
					seg.setFechaSiguienteContacto(null);
					seg.setDiasAtraso(null);
					seg.setEnTiempo(null);
				}
			}
			return lsc;
		} catch (Exception e) {
			f.enviarCorreoAvisoExepcion(e, Cliente,Empleado,"Confirmacion:"+confirmacion);
			return new ArrayList<SeguimientoCotizacion>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PartidaCotizacionEnSeguimiento> findPartidasPorCotizacionEnSeguimiento(
			String FolioCotizacion) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("FolioCotizacion", FolioCotizacion);
		
			f = new com.proquifa.net.modelo.comun.util.Funcion();
			
			String querypsc = " SELECT pc.Clave,cotizas.IMoneda,pc.idPCotiza,pc.Partida,CASE WHEN Cotizas.IMoneda ='Pesos' THEN (pc.Precio/MON.PDolar) WHEN Cotizas.IMoneda ='Euros' THEN (pc.Precio*MON.EDolar) " +
					  " \n ELSE pc.Precio END AS Precio,pc.Cant, "+ f.sqlDescripcionProductos("PROD","pc") + " AS Concepto,PROD.Tipo,CASE WHEN PROD.Control IS NULL THEN 'Normal' ELSE PROD.Control END AS CONTROL, " +
					  " \n pc.Estado,CASE WHEN Cotizas.IMoneda ='Pesos' THEN (pc.Precio/MON.PDolar)*Cant WHEN Cotizas.IMoneda ='Euros' THEN (pc.Precio*MON.EDolar)*Cant  " +
					  " \n 	ELSE pc.Precio*Cant END AS Monto,nextDate.FechaSiguiente,pc.Fabrica  "+
					  " \n  FROM PCotizas AS pc  " +
					  " \n  LEFT JOIN(SELECT * FROM SegCotiza) AS SEG ON SEG.Cotiza=PC.Clave " +
					  " \n  LEFT JOIN(SELECT Clave,IMoneda,Fecha FROM Cotizas) AS cotizas ON cotizas.Clave=PC.Clave  " +
					  " \n  LEFT JOIN(SELECT idPCotiza,FechaSiguiente FROM PCotizaEnSeguimiento)AS nextDate ON nextDate.idPCotiza=pc.idPCotiza  " +
					  " \n  LEFT JOIN(SELECT * FROM Productos) AS PROD ON PC.Codigo = PROD.Codigo AND PC.Fabrica=PROD.Fabrica " +
					  " \n  LEFT JOIN(SELECT * FROM Monedas) AS MON ON MON.Fecha=CONVERT(VARCHAR(8),SEG.Fecha, 112) " +
					  " \n  WHERE pc.Folio=99 ";
			if(!FolioCotizacion.equals("")) {
				querypsc += "\n AND pc.Clave='"+FolioCotizacion+"'";
			}
			log.info(querypsc);
			List<PartidaCotizacionEnSeguimiento> partidas = super.jdbcTemplate.query(querypsc, map,new PartidasPorCotizacionEnSeguimientoRowMapper());
			for(PartidaCotizacionEnSeguimiento pcs : partidas){
				
				pcs.setHistorial(findHistorialPartidasSeguimiento(pcs.getIdPCotiza()));
				pcs.setSituacion(findSituacionPartidaCotizacion(pcs.getIdPCotiza()));
			}
//logger.info(querypsc);
			return partidas;
		} catch (Exception e) {
			
		//	logger.error("Error: "+ e.getMessage());
			f.enviarCorreoAvisoExepcion(e, FolioCotizacion);
			return new ArrayList<PartidaCotizacionEnSeguimiento>();
		}
	}
	
	public List<HistorialPartidaEnSeguimiento> finHistorialSeguimientoXPartida(
			Long idPCotiza) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCotiza", idPCotiza);
		
		return findHistorialPartidasSeguimiento(idPCotiza);
			}
			catch(Exception e){
			//	logger.info("Error: "+ e.getMessage());
				f.enviarCorreoAvisoExepcion(e, idPCotiza);
				return new ArrayList<HistorialPartidaEnSeguimiento>();
			}
		
			}
	
	@SuppressWarnings("unchecked")
	public List<HistorialPartidaEnSeguimiento> findHistorialPartidasSeguimiento(Long idpcotiza){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idpcotiza", idpcotiza);
		
			String queryhps="";
			List<HistorialPartidaEnSeguimiento> hps = null;
			
			Long pendienteAbierto = super.queryForLong("SELECT COUNT(*) AS ExitePendiente FROM Pendiente WHERE Docto IN(SELECT Clave FROM PCotizas WHERE idPCotiza='"+ idpcotiza +"') AND Tipo='Seguimiento' AND FFin IS NULL",map);
			if(pendienteAbierto!=0){
				queryhps += "SELECT pch.Estado AS Origen,(penSeg.Responsable COLLATE SQL_Latin1_General_CP1_CI_AS) AS Realizo,NULL AS Comentarios,NULL AS Contacto,pch.FechaSiguiente AS FER,penSeg.FFin AS FechaRealizacion," +
						"(pc.Estado COLLATE SQL_Latin1_General_CP1_CI_AS) AS Estado,NULL AS RazonesESAC,NULL AS RazonesMonitor,NULL AS CanceladaDesde,NULL AS Aceptado,NULL AS CotizacionHija " +
						"FROM PCotizaEnSeguimiento AS pch " +
						"LEFT JOIN(SELECT idPCotiza,Clave,Estado FROM PCotizas) AS pc ON pc.idPCotiza="+ idpcotiza +" " +
						"LEFT JOIN(SELECT Docto,FInicio,Responsable,FFin FROM Pendiente WHERE Tipo='Seguimiento') AS penSeg ON penSeg.Docto=pc.Clave " +
						"WHERE pch.idPCotiza=pc.idPCotiza AND (pc.Estado<>'Recotizada' AND pc.Estado<>'Cancelada' AND pc.Estado<>'Pedido')";
				hps = super.jdbcTemplate.query(queryhps,map, new HistorialPartidaEnSeguimientoRowMapper());
			}
			queryhps = "SELECT pch.Origen,pch.Realizo,CAST((CASE WHEN pch.Origen='Tramitar pedido' THEN 'Pedido asignado: ' + CONVERT(varchar(20),pch.Comentarios) ELSE pch.Comentarios END) AS Varchar(200))AS Comentarios," +
					"(CASE WHEN pch.Origen='Confirmacion' THEN seg.Contacto WHEN pch.Origen='En S1' THEN bc.Contacto1 WHEN pch.Origen='En S2' THEN bc.Contacto2 WHEN pch.Origen='En S3' THEN bc.Contacto3 ELSE bc.Contacto3 END) AS Contacto," +
					"(CASE " +
					"	WHEN pch.Origen='Confirmacion' THEN seg.Fecha" +
					"	WHEN pch.Origen='En S1' THEN penS.FInicio" +
					"	WHEN pch.Origen='En S2' THEN pch.FER" +
					"	WHEN pch.Origen='En S3' THEN pch.FER" +
					"	WHEN pch.Origen='En S3R' THEN pch.FER" +
					"	WHEN pch.Origen='Decidir cancelacion' THEN dcan.Fecha" +
					"	WHEN pch.Origen='Tramitar pedido' THEN (SELECT FPedido as Fecha FROM Pedidos WHERE CPedido=CAST(pch.Comentarios AS VARCHAR(100)) COLLATE SQL_Latin1_General_CP1_CI_AS) " +
					"END) AS FER,pch.Fecha AS FechaRealizacion,pch.Estado,pch.Situacion," +
					"(CASE WHEN pch.Origen='Decidir cancelacion' THEN CAST(dcan.RazonesESAC AS Varchar(200)) ELSE NULL END) AS RazonesESAC," +
					"(CASE WHEN pch.Origen='Decidir cancelacion' THEN CAST(dcan.RazonesMonitor AS Varchar(200)) ELSE NULL END) AS RazonesMonitor," +
					"(CASE WHEN pch.Origen='Decidir cancelacion' THEN dcan.CanceladaDesde ELSE NULL END) AS CanceladaDesde," +
					"(CASE WHEN pch.Origen='Decidir cancelacion' THEN dcan.Aceptado ELSE NULL END) AS Aceptado," +
					"(CASE WHEN pch.Estado='Recotizada' THEN ch.Clave ELSE NULL END) AS CotizacionHija " +
					"FROM PCotizaHistorial AS pch " +
					"LEFT JOIN(SELECT * FROM PCotizas) AS pc ON pc.idPCotiza="+ idpcotiza +" " +
					"LEFT JOIN(SELECT * FROM PCotizaEnSeguimiento) AS pcs ON pcs.idPCotiza=pc.idPCotiza " +
					"LEFT JOIN(SELECT Cotiza,Contacto,Fecha FROM SegCotiza) AS seg ON seg.Cotiza=pc.Clave " +
					"LEFT JOIN(SELECT Cotiza,Contacto1,Contacto2,Contacto3,FUAccion FROM Bitacora) AS bc ON bc.Cotiza=pc.Clave " +
					"LEFT JOIN(SELECT * FROM HistorialDecidirCancelacion) AS dcan ON dcan.idPCotiza=pc.idPCotiza AND pch.Estado='Cancelable' AND pch.Fecha=dcan.Fecha " +
					"LEFT JOIN(SELECT Clave,FK01_PCotizaOrigen FROM PCotizas WHERE Folio=99) AS ch ON ch.FK01_PCotizaOrigen=pch.idPCotiza " +
					"LEFT JOIN(SELECT Docto,FInicio FROM Pendiente WHERE Tipo='Seguimiento') AS penS ON penS.Docto=pc.Clave " +
					"WHERE pch.idPCotiza=pc.idPCotiza " +
					"ORDER BY FechaRealizacion DESC";
			List<HistorialPartidaEnSeguimiento> anexo = super.jdbcTemplate.query(queryhps,map,  new HistorialPartidaEnSeguimientoRowMapper());
			
			if(hps!=null){
				for(HistorialPartidaEnSeguimiento result:anexo){
					hps.add(result);
				}
				return hps;
			}else{
				return anexo;
			}
		} 
		catch (Exception e) {
			//logger.error("Error: "+ e.getMessage());
			f.enviarCorreoAvisoExepcion(e,"idpcotiza:"+ idpcotiza);
			return new ArrayList<HistorialPartidaEnSeguimiento>();
		}	
	}

	@SuppressWarnings("unchecked")
	public List<SeguimientoCotizacion> findSeguimientoCotizacionEnPartida(
			int confirmacion, String Cliente, String Empleado,
			String condiciones,Integer esacMaster, List<NivelIngreso> niveles) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("confirmacion", confirmacion);
			map.put("esacMaster", esacMaster);
			map.put("Empleado", Empleado);
			map.put("Cliente", Cliente);
			map.put("condiciones", condiciones);
			map.put("niveles", niveles);
		
			String findcliente ="";
			String query="";
			String estadoParaFSiguiente="";
			List<SeguimientoCotizacion> lsc = null;
			List<SeguimientoCotizacion> temp1 = null;
			String findvendedor="";
			f= new com.proquifa.net.modelo.comun.util.Funcion();
			if(Empleado!=null && !Empleado.equals("")){
				String e = (String) super.jdbcTemplate.queryForObject("SELECT (CASE WHEN F.Nombre='Ejecutivo de Ventas' THEN ' FK03_EVT=' + CAST(e.Clave AS Varchar(50)) " +
						"ELSE CAST('Mostrar_Todos_Clientes' AS Varchar(22)) END) AS Usuario " +
						"FROM Funcion AS f,Empleados AS e " +
						"WHERE f.PK_Funcion=e.FK01_Funcion AND e.Usuario='" + Empleado + "'",map,String.class);
				if(!e.equals("Mostrar_Todos_Clientes")){
					findvendedor=" AND EXISTS (SELECT * FROM Cotizas WHERE Cliente IN (SELECT Nombre FROM Clientes WHERE "+ e +") AND SegCotiza.Cotiza=Cotizas.Clave) ";
				}
			}
			if(Cliente!=null && !Cliente.equals("")){
				findcliente=" AND  CL.Nombre='"+Cliente+"' ";
			}
			
			if (confirmacion==0 || confirmacion ==2){
				 query+= 
				" DECLARE @TCEDolar  REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPEDolar REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPLDolar REAL = (SELECT TOP(1)LDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPYDolar REAL = (SELECT TOP(1)YDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPDDolar REAL = (SELECT TOP(1)DDolar FROM Monedas ORDER BY Fecha DESC) " +
				" SELECT CASE WHEN PCOT.Estado LIKE 'Cancelable' THEN 'Cancelable' ELSE Pendiente.Tipo END AS Tipo," +
				" SegCotiza.Cotiza,Cotizas.IMoneda,COALESCE(SegCotiza.Contacto,Cotizas.Contacto) AS Contacto,SegCotiza.Comenta,SegCotiza.FRealizo," +
				" SegCotiza.FUAccion,SegCotiza.Fecha,SegCotiza.Estatus as SegEstatus,Cotizas.Cliente,cl.Vendedor,(CASE WHEN cl.EV='Administrador' THEN 'No asignado' ELSE cl.EV END) AS EV,"+
				" (CASE WHEN cl.EVT='Administrador' THEN 'No asignado' ELSE cl.EVT END) AS EVT, PCOT.Cant AS Piezas,npart.Partidas,Empleados.Nombre," +
				f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL,JEFE.Usuario AS MASTER,  " +
				" PROD.Fabrica,PROD.Tipo AS TipoPro, CASE WHEN PROD.Control IS NULL THEN 'Normal' ELSE PROD.Control END AS CONTROL, " +
				" Cant*(CASE WHEN Cotizas.IMoneda='Euros' THEN PCOT.Precio * Monedas.EDolar WHEN Cotizas.IMoneda='Pesos' THEN PCOT.Precio/Monedas.PDolar ELSE PCOT.Precio END) AS Monto," +
				" COALESCE(PDolar,@TCEDolar) AS PDolar,COALESCE(EDolar,@TCPEDolar) AS EDolar,COALESCE(LDolar,@TCPLDolar) AS LDolar," +
				" COALESCE(YDolar,@TCPYDolar) AS YDolar,COALESCE(DDolar,@TCPDDolar) AS DDolar,DOCR.Folio " +
				/*Agregado*/
				" ,PCES.FechaSiguiente, DATEDIFF(day, PCES.FechaSiguiente, GETDATE()) AS DiasAtraso, CASE WHEN DATEDIFF(day, PCES.FechaSiguiente, GETDATE())  > 0 THEN 'FT' ELSE 'ET' END AS EnTiempo"+
				/*Agregado*/
				" FROM Pendiente " +
				" LEFT JOIN (SELECT * FROM SegCotiza) AS SegCotiza ON SegCotiza.Cotiza=Pendiente.Docto " +
				" LEFT JOIN (SELECT COUNT(*) AS Partidas,Clave FROM PCotizas WHERE Folio=99 AND Estado='Cotización' GROUP BY Clave) AS npart ON npart.Clave=Pendiente.Docto " +

				" LEFT JOIN (SELECT *  FROM PCotizas WHERE Folio=99 ) AS PCOT ON PCOT.Clave=SegCotiza.Cotiza  " +
				" LEFT JOIN (SELECT * FROM Productos) AS PROD ON PROD.Codigo = PCOT.Codigo AND PROD.Fabrica = PCOT.Fabrica " +
				" LEFT JOIN Cotizas ON Cotizas.Clave=Pendiente.Docto " +
				" LEFT JOIN (SELECT c.Clave,c.Nombre,c.Vendedor,COALESCE(ev.Usuario,'No asignado') AS EV, COALESCE(evt.Usuario,'No asignado') AS EVT FROM Clientes AS c LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS ev ON ev.Clave=c.FK01_EV " +
				" LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS evt ON evt.Clave=c.FK03_EVT  WHERE c.Habilitado=1) AS cl ON cl.Nombre=Cotizas.Cliente " +
				" LEFT JOIN (SELECT PCotizas.Clave,SUM(Cant) AS Piezas,SUM(PCotizas.Precio * PCotizas.Cant) AS PrecioUSD FROM PCotizaEnSeguimiento " +
				" RIGHT JOIN PCotizas ON PCotizaEnSeguimiento.idPCotiza=PCotizas.idPCotiza WHERE PCotizas.Folio=99 " +
				" AND (PCotizas.Estado<>'Cancelada' AND PCotizas.Estado<>'Pedido' AND PCotizas.Estado<>'Recotizada') GROUP BY PCotizas.Clave) AS SegCotizasPartidas " +
				" ON SegCotizasPartidas.Clave=Cotizas.Clave " +
				" LEFT JOIN Monedas ON Monedas.Fecha=CONVERT(VARCHAR(8), SegCotiza.Fecha, 112) " +
				" LEFT JOIN Empleados ON Empleados.Usuario=Pendiente.Responsable " +
				" LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar " +
				" WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha " +
				" WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = CL.Clave " +
				" LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C " +
				" LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre " +
				" LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave) " +
				" AS OPER ON OPER.Clave = CL.Clave" +
				" LEFT JOIN (SELECT * FROM Empleados) AS JE ON JE.Usuario=CL.Vendedor " +
				" LEFT JOIN (SELECT * FROM Jefe_Subordinado) AS JF ON JF.FK02_Subordinado=JE.Clave " +
				" LEFT JOIN (SELECT * FROM Empleados) AS JEFE ON JEFE.Clave=JF.FK01_Jefe" +
				" LEFT JOIN (SELECT * FROM DoctosR) AS DOCR ON DOCR.Numero=SegCotiza.Cotiza" +
				/*Agregado*/
				" LEFT JOIN (SELECT MAX(Folio) Folio, idPCotiza FROM PCotizaEnSeguimiento GROUP BY idPCotiza) AS PCS ON PCS.idPCotiza = PCOT.idPCotiza\n" + 
				" LEFT JOIN PCotizaEnSeguimiento AS PCES ON PCES.Folio = PCS.Folio "+
				/*Agregado*/
				" WHERE Pendiente.Tipo='Seguimiento' AND Pendiente.FFin IS NULL AND (PCOT.Estado='Cotización' OR PCOT.Estado='Cancelable') " + findcliente + findvendedor + condiciones +

			    " ORDER BY Cliente ASC ";
			
				 if(confirmacion==2){
					 log.info(query);
					 lsc = super.jdbcTemplate.query(query,map, new SeguimientoCotizacionRowMapper());
				 }
			}
			 if (confirmacion==1 || confirmacion ==2){
				 if(confirmacion==2){
					 query="";
					 }
				 query+= 
				" DECLARE @TCEDolar  REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPEDolar REAL = (SELECT TOP(1)EDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPLDolar REAL = (SELECT TOP(1)LDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPYDolar REAL = (SELECT TOP(1)YDolar FROM Monedas ORDER BY Fecha DESC) " +
				" DECLARE @TCPDDolar REAL = (SELECT TOP(1)DDolar FROM Monedas ORDER BY Fecha DESC) " +

				" SELECT 'Confirmación' AS Tipo,SegCotiza.Cotiza,Cotizas.IMoneda,COALESCE(SegCotiza.Contacto,Cotizas.Contacto) AS Contacto,SegCotiza.Comenta,SegCotiza.FRealizo," +
				" SegCotiza.FUAccion,SegCotiza.Fecha,SegCotiza.Estatus as SegEstatus,Cotizas.Cliente,cl.Vendedor,(CASE WHEN cl.EV='Administrador' THEN 'No asignado' ELSE cl.EV END) AS EV,"+
				" (CASE WHEN cl.EVT='Administrador' THEN 'No asignado' ELSE cl.EVT END) AS EVT, PCOT.Cant AS Piezas,npart.Partidas,Empleados.Nombre," +
				f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL,JEFE.Usuario AS MASTER," +
				" PROD.Fabrica,PROD.Tipo AS TipoPro, CASE WHEN PROD.Control IS NULL THEN 'Normal' ELSE PROD.Control END AS CONTROL, " +
				" Cant*(CASE WHEN Cotizas.IMoneda='Euros' THEN PCOT.Precio * Monedas.EDolar WHEN Cotizas.IMoneda='Pesos' THEN PCOT.Precio/Monedas.PDolar ELSE PCOT.Precio END) AS Monto," +
				" COALESCE(PDolar,@TCEDolar) AS PDolar,COALESCE(EDolar,@TCPEDolar) AS EDolar," +
				" COALESCE(LDolar,@TCPLDolar) AS LDolar,COALESCE(YDolar,@TCPYDolar) AS YDolar," +
				" COALESCE(DDolar,@TCPDDolar) AS DDolar,DOCR.Folio, null as FechaSiguiente, null as DiasAtraso, null as EnTiempo " +
				" FROM SegCotiza " +

				" LEFT JOIN (SELECT COUNT(*) AS Partidas,Clave FROM PCotizas WHERE Folio=99 AND Estado='Cotización' GROUP BY Clave) AS npart ON npart.Clave=SegCotiza.Cotiza " +

				" LEFT JOIN (SELECT *  FROM PCotizas WHERE Folio=99 ) AS PCOT ON PCOT.Clave=SegCotiza.Cotiza  " +
				" LEFT JOIN (SELECT * FROM Productos) AS PROD ON PROD.Codigo = PCOT.Codigo AND PROD.Fabrica = PCOT.Fabrica " +
				" LEFT JOIN Cotizas ON COTIZAS.Clave=SegCotiza.Cotiza " +
				" LEFT JOIN (SELECT c.Clave,c.Nombre,c.Vendedor,COALESCE(ev.Usuario,'No asignado') AS EV, COALESCE(evt.Usuario,'No asignado') AS EVT FROM Clientes AS c LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS ev ON ev.Clave=c.FK01_EV " +
				" LEFT JOIN(SELECT Clave,Usuario FROM Empleados) AS evt ON evt.Clave=c.FK03_EVT  WHERE c.Habilitado=1 ) AS cl ON cl.Nombre=Cotizas.Cliente "+
				" LEFT JOIN (SELECT PCotizas.Clave,SUM(Cant) AS Piezas,SUM(PCotizas.Precio * PCotizas.Cant) AS PrecioUSD FROM PCotizaEnSeguimiento " +
				" RIGHT JOIN PCotizas ON PCotizaEnSeguimiento.idPCotiza=PCotizas.idPCotiza WHERE PCotizas.Folio=99 " +
				" AND (PCotizas.Estado<>'Cancelada' AND PCotizas.Estado<>'Pedido' AND PCotizas.Estado<>'Recotizada') GROUP BY PCotizas.Clave) AS SegCotizasPartidas ON SegCotizasPartidas.Clave=Cotizas.Clave " +
				" LEFT JOIN Monedas ON Monedas.Fecha=CONVERT(VARCHAR(8), SegCotiza.Fecha, 112) " +
				" LEFT JOIN Empleados ON Empleados.Usuario=SegCotiza.Vendedor " +
				" LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar " +
				" WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha " +
				" WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = CL.Clave " +
				" LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C " +
				" LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre " +
				" LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave) " +
				" AS OPER ON OPER.Clave = CL.Clave" +
				" LEFT JOIN (SELECT * FROM Empleados) AS JE ON JE.Usuario=CL.Vendedor " +
				" LEFT JOIN (SELECT * FROM Jefe_Subordinado) AS JF ON JF.FK02_Subordinado=JE.Clave " +
				" LEFT JOIN (SELECT * FROM Empleados) AS JEFE ON JEFE.Clave=JF.FK01_Jefe" +
				" LEFT JOIN (SELECT * FROM DoctosR) AS DOCR ON DOCR.Numero=SegCotiza.Cotiza" +

				" WHERE FRealizo IS NULL AND Cotizas.Estado<>'Cancelada' AND SegCotiza.Fecha>'20090901' AND (PCOT.Estado='Cotización' OR PCOT.Estado='Cancelable') " + findcliente + findvendedor + condiciones +

				" ORDER BY Cliente ASC";
				 
				 if(confirmacion==2){
					 log.info(query);
					temp1 = super.jdbcTemplate.query(query,map, new SeguimientoCotizacionRowMapper());
					for(SeguimientoCotizacion r:temp1){
						 lsc.add(r);
						 log.info("R: "+r);
					 }
				 }
			}
			 
			 if(confirmacion==0 || confirmacion==1){
//				 logger.info(query);
				 lsc = super.jdbcTemplate.query(query,map, new SeguimientoCotizacionRowMapper());
			 }

			 if(esacMaster==1){

				 estadoParaFSiguiente = "Estado='Cotización' OR Estado='Cancelable'";

			 }else{

				 estadoParaFSiguiente = "Estado='Cotización'";

			 }
			 
//			for(SeguimientoCotizacion seg:lsc){				
//				com.proquifa.net.modelo.comun.util.Funcion fn = new com.proquifa.net.modelo.comun.util.Funcion();
////				logger.info("Folio: " + seg.getFolioCotizacion());
//				if(seg.getTipoSeguimiento().equals("Seguimiento") || seg.getTipoSeguimiento().equals("Cancelable")){
//					try {
//						String q="DECLARE @Cotiza AS Varchar(20) = '"+ seg.getFolioCotizacion() +"' " +
//						"DECLARE @TotalPartidas AS Integer= (SELECT COUNT(*) AS Total FROM PCotizas WHERE Folio=99 AND Clave=@Cotiza) " +
//						"SELECT TOP 1 (CASE WHEN @TotalPartidas=0 THEN NULL ELSE pcs.FechaSiguiente END) AS FechaSiguiente " +
//						"FROM PCotizaEnSeguimiento AS pcs " +
//						"LEFT JOIN(SELECT idPCotiza,Clave FROM PCotizas WHERE Folio=99 AND "+ estadoParaFSiguiente +") AS pc ON pc.Clave=@Cotiza " +
//						"WHERE pcs.idPCotiza=pc.idPCotiza " +
//						"ORDER BY pcs.FechaSiguiente ASC";
//						Date fechaSiguiente = (Date) super.jdbcTemplate.queryForObject(q, map,Date.class);
//						if(fechaSiguiente!=null){
//							seg.setFechaSiguienteContacto(fechaSiguiente);
//							Long dt = fn.calcularDiasAtraso(fechaSiguiente,0);
//							seg.setDiasAtraso(dt);
//							if(dt>0){
//								seg.setEnTiempo("FT");
//							}else{
//								seg.setEnTiempo("ET");
//							}
//						}else{
//							seg.setFechaSiguienteContacto(null);
//							seg.setDiasAtraso(null);
//							seg.setEnTiempo(null);
//						}
//					} catch (Exception e) {
//
//						seg.setFechaSiguienteContacto(null);
//						seg.setDiasAtraso(null);
//						seg.setEnTiempo(null);
//					}
//				}else{
//					seg.setFechaSiguienteContacto(null);
//					seg.setDiasAtraso(null);
//					seg.setEnTiempo(null);
//				}
//			}
			return lsc;
			
		} catch (Exception e) {

			log.info("Error: "+ e.getMessage());

			return new ArrayList<SeguimientoCotizacion>();
		}	
	}

	public String findSituacionPartidaCotizacion (Long idpcotiza){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idpcotiza", idpcotiza);

			return (String)  super.jdbcTemplate.queryForObject("SELECT TOP 1 Situacion FROM PCotizaHistorial WHERE idPCotiza='"+idpcotiza.toString()+"' GROUP BY Fecha,Situacion ORDER BY Fecha DESC",map, String.class);		
		} catch (Exception e) {

//			f.enviarCorreoAvisoExepcion(e, "idpcotiza:"+idpcotiza);
//			logger.info("Error: "+ e.getMessage());

			return "";
		}
	}
}