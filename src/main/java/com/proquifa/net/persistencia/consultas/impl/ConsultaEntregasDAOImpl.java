/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.modelo.despachos.HistorialPNE;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.consultas.ConsultaEntregasDAO;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaEntregasRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaEntregasTiempoProcesoRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaGraficasEntregasRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.HistorialPNERowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ResumenConsultaRowMapper;

/**
 * @author vromero
 *
 */
@Repository
public class ConsultaEntregasDAOImpl extends DataBaseDAO implements
		ConsultaEntregasDAO {
	private Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(ConsultaEntregasDAOImpl.class);	

	@SuppressWarnings("unchecked")
	@Override
	public List<Factura> findEntregas(String condiciones,List<NivelIngreso> niveles) {
		String query = new String();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condiciones", condiciones);
		map.put("niveles", niveles);
		try {
			funcion = new Funcion();// NIVEL.VentasUSDdfg

			query = "SELECT DISTINCT(rdp.idEntrega),"
					+ " \n COALESCE(RutaEjecutar.FInicio,RutaTrabajar.FInicio) AS FRENTREGA, "
					+ " \n COALESCE(rdp.idRuta,'') AS idRuta,rdp.idDP,CLI.Clave AS idCliente,CLI.Nombre,rdp.Ruta,rdp.ZonaMensajeria,(CASE WHEN rdp.FacturaORemision = 0 THEN FAC.idFactura ELSE 0 END) as idFactura,rdp.Factura,rdp.FacturaORemision, "
					+ " \n rdp.FPor,(CASE WHEN rdp.FacturaORemision = 1 Then REM.CPedido ELSE FAC.CPedido END) AS Pedido, "
					+ " \n (CASE WHEN rdp.Ruta = 'GUADALAJARA' Then (SELECT Usuario AS UsuerGDL FROM Empleados WHERE FK01_Funcion = 41) ELSE (CASE WHEN usuario.Responsable IS NOT NULL THEN usuario.Responsable ELSE 'Pendiente' END) END) AS Responsable, "
					+ " \n rdp.Fecha AS FER,COALESCE(rdp.FFin,facturaCancelada.FFin) AS FR,"
					+ " \n restFac.FEE,(CASE WHEN rdp.Entrega='Realizada' AND rdp.FFin IS NOT NULL THEN 'Cerrada' ELSE CASE WHEN facturaCancelada.FFin IS NOT NULL THEN 'Cerrada' ELSE 'Abierta' END END) AS EstadoDP,"
					+ " \n COALESCE(pedidoF.PiezasPedido,pedidoR.PiezasPedido,'0') AS PiezasPedido,COALESCE(pedidoF.MontoPedido,pedidoR.MontoPedido,'0') AS MontoPedido,COALESCE(pedidoF.MonedaPedido,pedidoR.MonedaPedido) AS MonedaPedido,"
					+ " \n COALESCE(restFac.MontoFactura,'0') AS MontoFactura,COALESCE(FAC.Moneda,REM.Moneda) AS MonedaFactura,COALESCE(restFac.PiezasFactura,'0') AS PiezasFactura,numPRDP.Cuantos AS NumeroPartidas,"
					+ " \n (CASE WHEN rdp.Conforme IS NULL THEN CASE WHEN (rdp.EstadoRuta='Cerrada' AND rdp.Entrega='Realizada') OR (rdp.ZonaMensajeria='Almacén') THEN 'NA' ELSE 'Pendiente' END WHEN rdp.Conforme=1 THEN 'SI' WHEN rdp.Conforme=0 THEN 'NO' END) AS Conformidad,"
					+ funcion
							.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD")
					+ " AS NivelIngreso ,(CASE WHEN CAST(COALESCE(rdp.FFin,facturaCancelada.FFin,GETDATE()) AS DATE) > CAST(restFac.FEE AS DATE) THEN 'FT' ELSE 'ET' END) AS Tiempo,"
					+ " \n (CASE WHEN (FAC.Moneda='Pesos' OR FAC.Moneda='M.N.') THEN restFac.MontoFactura/case when coalesce(FAC.TCambio,MON.PDolar) = 0 then MON.PDolar else fac.TCambio end WHEN (FAC.Moneda='Euros' OR FAC.Moneda='EUR') THEN restFac.MontoFactura * MON.EDolar ELSE restFac.MontoFactura END) AS TotalDLS "
					+ " \n FROM RutaDP AS rdp "
					+ " \n LEFT JOIN (SELECT Clave,Nombre FROM Clientes) AS CLI ON CLI.Clave=rdp.idCliente "
					+ " \n INNER JOIN (SELECT idFactura,Factura,FPor,CPedido,Importe,Moneda,Fecha, TCambio, DeSistema,Cliente  FROM Facturas WHERE DeSistema = 1) AS FAC ON FAC.Factura=rdp.Factura AND FAC.FPor=rdp.FPor "
					+ " \n LEFT JOIN(SELECT Factura,FPor,CPedido,Importe,Moneda FROM Remisiones) AS REM ON REM.Factura=rdp.Factura AND REM.FPor=rdp.FPor "
					+ " \n LEFT JOIN(SELECT * FROM Monedas) AS MON ON CAST(MON.Fecha AS DATE)=CAST(FAC.Fecha AS DATE) "
					+ " \n LEFT JOIN (SELECT Responsable,Docto FROM Pendiente WHERE (Tipo like 'Ruta a ejecutar%' OR Tipo='A Ejecutar ruta' or Tipo LIKE 'Ruta a trabajar%')) AS usuario ON usuario.Docto IN (rdp.idEntrega,rdp.idRuta) "
					+ " \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar "
					+ " \n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha "
					+ " \n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente ) AS NIVEL ON NIVEL.Cliente=CLI.Clave "
					+ " \n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C "
					+ " \n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre "
					+ " \n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave) "
					+ " \n AS OPER ON OPER.Clave = CLI.Clave "
					+ " \n LEFT JOIN(SELECT pp.idEvento, p.CPedido,(CASE WHEN p.Moneda='Pesos' THEN 'M.N.' WHEN p.Moneda='Dolares' THEN 'USD' WHEN p.Moneda='Euros' THEN 'EUR' WHEN p.Moneda='Libras' THEN 'LB' END) AS MonedaPedido,PP.MontoPedido,PP.PiezasPedido "
					+ " \n FROM Pedidos AS p LEFT JOIN(SELECT SUM(Cant*Precio) AS MontoPedido,SUM(Cant) AS PiezasPedido,CPedido,idEvento FROM PPedidos WHERE Estado<>'Cancelada' GROUP BY CPedido,idEvento) AS pp ON pp.CPedido=P.CPedido) AS pedidoF ON pedidoF.CPedido=FAC.CPedido  and pedidoF.idEvento = rdp.idDP "
					+ " \n LEFT JOIN(SELECT pp.idEvento, p.CPedido,(CASE WHEN p.Moneda='Pesos' THEN 'M.N.' WHEN p.Moneda='Dolares' THEN 'USD' WHEN p.Moneda='Euros' THEN 'EUR' WHEN p.Moneda='Libras' THEN 'LB' END) AS MonedaPedido,PP.MontoPedido,PP.PiezasPedido "
					+ " \n FROM Pedidos AS p LEFT JOIN(SELECT SUM(Cant*Precio) AS MontoPedido,SUM(Cant) AS PiezasPedido,CPedido,idEvento FROM PPedidos WHERE Estado<>'Cancelada' GROUP BY CPedido,idEvento) AS pp ON pp.CPedido=P.CPedido) AS pedidoR ON pedidoR.CPedido=REM.CPedido  and pedidoR.idEvento = rdp.idDP "
					+ " \n LEFT JOIN(SELECT (prdp.idDP) as iddp ,(CASE WHEN RDP.FacturaORemision = 0 THEN COALESCE(SUM(pfac.Cant * pfac.Importe),SUM(ppF.Cant*ppF.Precio),SUM(ppR.Cant*ppR.Precio))ELSE COALESCE(SUM(PPED.Cant * PPED.Precio),SUM(ppF.Cant*ppF.Precio),SUM(ppR.Cant*ppR.Precio))END) AS MontoFactura,COALESCE(SUM(pfac.Cant),SUM(ppF.Cant),SUM(ppR.Cant)) AS PiezasFactura,COALESCE(MIN(ppF.FEE),MIN(ppR.FEE)) AS FEE "
					+ " \n 		FROM PRutaDP AS prdp   "
					+ " \n 			LEFT JOIN (SELECT FacturaORemision, idDP FROM RutaDP) AS RDP ON RDP.idDP = prdp.idDP  "
					+ " \n 			LEFT JOIN(SELECT * FROM PFacturas where CPedido is not null) AS pfac ON pfac.Factura=prdp.Factura AND pfac.FPor=prdp.FPor AND pfac.Part=prdp.Part  "
					+ " \n 			LEFT JOIN (SELECT idEvento, Cant,Precio, CPedido, part FROM PPedidos)AS PPED on PPED.CPedido = pfac.CPedido and PPED.Part = pfac.PPedido "
					+ " \n 			LEFT JOIN(SELECT * FROM PRemisiones) AS pRem ON pRem.Factura=prdp.Factura AND pRem.FPor=prdp.FPor AND pRem.Part=prdp.Part  "
					+ " \n 			LEFT JOIN(SELECT FPEntrega AS FEE,ppedidos.CPedido,Part,Cant,Precio FROM PPedidos  "
					+ " \n 				left join (select COUNT (1) partNoEnt,CPedido  from PPedidos where Estado in ('Pedido','Compra','No entregado','STOCK','A facturacion')and (idcomplemento = 0 or idcomplemento is null) GROUP BY CPedido )as ppNE on ppNE.CPedido = ppedidos.CPedido "
					+ " \n 				LEFT JOIN (SELECT coalesce (Parciales, 1 ) parciales , CPedido, coalesce (BloqueadoxModficacion, 0)BloqueadoxModficacion  FROM Pedidos ) as p_ppedidos on p_ppedidos.CPedido = ppedidos.CPedido  "
					+ " \n 				where (Parciales = 1 or	(Parciales = 0 and (partNoEnt = 0 or partNoEnt is null ))) and BloqueadoxModficacion = 0  and ppedidos.estado <> 'Cancelada') "
					+ " \n 			 AS ppF ON ppF.CPedido=pfac.CPedido AND ppF.Part=pfac.PPedido  and ppF.FEE is not null  "
					+ " \n 			LEFT JOIN(	SELECT FPEntrega AS FEE,ppedidos.CPedido,Part,Cant,Precio , Parciales , BloqueadoxModficacion , coalesce(partNoEnt,0)partNoEnt ,Estado  FROM PPedidos  "
					+ " \n 				left join (select COUNT (1) partNoEnt,CPedido  from PPedidos where Estado in ('Pedido','Compra','No entregado','STOCK','A facturacion')and (idcomplemento = 0 or idcomplemento is null) GROUP BY CPedido )as ppNE on ppNE.CPedido = ppedidos.CPedido "
					+ " \n 				LEFT JOIN (SELECT coalesce (Parciales, 1 ) parciales , CPedido, coalesce (BloqueadoxModficacion, 0)BloqueadoxModficacion  FROM Pedidos ) as p_ppedidos on p_ppedidos.CPedido = ppedidos.CPedido  "
					+ " \n 				where (Parciales = 1 or	(Parciales = 0 and (partNoEnt = 0 or partNoEnt is null ))) and BloqueadoxModficacion = 0  and ppedidos.estado <> 'Cancelada') "
					+ " \n 			 AS ppR ON ppR.CPedido=pRem.CPedido AND ppR.Part=pRem.PPedido and ppr.FEE is not null  "
					+ " \n 	group by prdp.idDP, RDP.FacturaORemision) AS restFac ON restFac.idDP=rdp.idDP  and FEE is not null "
					+ " \n LEFT JOIN(SELECT COUNT(Folio) AS Cuantos,idDP FROM PRutaDP GROUP BY idDP) AS numPRDP ON numPRDP.idDP=rdp.idDP "
					+ " \n LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo = 'Amparar cancelación') AS facturaCancelada ON facturaCancelada.Docto=rdp.Factura AND facturaCancelada.Partida=rdp.FPor "
					+ " \n LEFT JOIN (SELECT Docto,FInicio,Tipo FROM Pendiente WHERE FFin IS NULL AND Tipo LIKE 'Ruta a trabajar%') AS RutaTrabajar ON   RutaTrabajar.Docto=rdp.idRuta "
					+ " \n LEFT JOIN (SELECT Docto,FInicio,Tipo FROM Pendiente WHERE (Tipo like 'Ruta a ejecutar%' OR Tipo='A Ejecutar ruta')) AS RutaEjecutar ON  RutaEjecutar.Docto IN (rdp.idEntrega,rdp.idRuta)   "
					+ condiciones + " " + "ORDER BY FR ASC";

			// logger.info(query.toString());
			log.info(query);  
			return super.jdbcTemplate.query(query,new ConsultaEntregasRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			//funcion.enviarCorreoAvisoExepcion(e);
			log.info("/*******************************/");
			log.info(e.toString());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findTiempoProceso(String idDP) {
		String query = new String();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDP", idDP);
			funcion = new Funcion();
			query = "DECLARE @idDP AS Varchar(MAX) = '"
					+ idDP
					+ "' "
					+ "SELECT '1' AS Folio,'Tramitación' AS Evento,Pendiente.FInicio AS FechaInicio,COALESCE(Pendiente.FFin,pendCancelacion.FFin) AS FechaFin,Responsable,@idDP + '-D' AS Etiqueta,data.FInicio AS FechaFacturacion,CAST(NULL AS DATE) AS FechaTramitacion,"
					+ "CAST(NULL AS DATE) AS FechaSurtido,CAST(NULL AS DATE) AS FechaEjecucion,CAST(NULL AS DATE) AS FechaCierre,CAST(rdp.ComentariosAdicionales AS Varchar(MAX)) AS ComentariosGestor,NULL AS CajaColectora,"
					+ "NULL AS Documentosresultantes,NULL AS EntregayRevision,NULL AS Refacturacion,NULL AS Ruta,NULL AS Zona,NULL AS Conforme,NULL AS Entrega,'' AS EstadoRuta,NULL AS FPor,"
					+ funcion.obtenerTiempoTranscurridoSQL("Pendiente.FInicio",
							"COALESCE(Pendiente.FFin,pendCancelacion.FFin)",
							"Dias")
					+ " AS TP, '' FolioNE "
					+ "FROM Pendiente "
					+ "LEFT JOIN(SELECT RutaDP.idDP,Pendiente.FInicio FROM RutaDP,Facturas,Pendiente WHERE Pendiente.Docto=Facturas.CPedido AND (Pendiente.Tipo='A Facturar' OR Pendiente.Tipo='Facturar por adelantado') "
					+ "AND Facturas.Factura=RutaDP.Factura AND Facturas.FPor=RutaDP.FPor) AS data ON data.idDP=@idDP "
					+ "LEFT JOIN(SELECT idDP,ComentariosAdicionales,Factura,FPor FROM RutaDP) AS rdp ON rdp.idDP=@idDP "
					+ "LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo='Amparar cancelación') AS pendCancelacion ON pendCancelacion.Docto=rdp.Factura AND pendCancelacion.Partida=rdp.FPor "
					+ "WHERE Tipo='A tramitar ruta' AND Pendiente.Docto=@idDP "
					+ "UNION "
					+ "SELECT '2' AS Folio,'Surtido' AS Evento,Pendiente.FInicio AS FechaInicio,COALESCE(Pendiente.FFin,pendCancelacion.FFin) AS FechaFin,Responsable,"
					+ "(SELECT STUFF((SELECT CAST(',' AS varchar(MAX)) + CASE WHEN Ambiente<>0 THEN idDP+'-1' ELSE NULL END,CAST(',' AS varchar(MAX)) + CASE WHEN Refrigeracion<>0 THEN idDP+'-2' ELSE NULL END,"
					+ "CAST(',' AS varchar(MAX)) + CASE WHEN Congelacion<>0 THEN idDP+'-3' ELSE NULL END,CAST(',' AS varchar(MAX)) + CASE WHEN SinManejo<>0 THEN idDP+'-4' ELSE NULL END "
					+ "FROM RutaDP,Pendiente WHERE Pendiente.Tipo='A tramitar ruta' AND Pendiente.Docto=RutaDP.idDP AND RutaDP.idDP=@idDP "
					+ "FOR XML PATH('')), 1, 1, '')) AS Etiqueta,CAST(NULL AS DATE) AS FechaFacturacion,data.FFin AS FechaTramitacion,CAST(NULL AS DATE) AS FechaSurtido,CAST(NULL AS DATE) AS FechaEjecucion,"
					+ "CAST(NULL AS DATE) AS FechaCierre,NULL AS ComentariosGestor,data.idEntrega AS CajaColectora,NULL AS Documentosresultantes,NULL AS EntregayRevision,NULL AS Refacturacion,NULL AS Ruta,"
					+ "NULL AS Zona,NULL AS Conforme,NULL AS Entrega,'' AS EstadoRuta,NULL AS FPor,"
					+ funcion.obtenerTiempoTranscurridoSQL("Pendiente.FInicio",
							"COALESCE(Pendiente.FFin,pendCancelacion.FFin)",
							"Dias")
					+ " AS TP, '' FolioNE "
					+ "FROM Pendiente "
					+ "LEFT JOIN(SELECT RutaDP.idEntrega,RutaDP.idDP,RutaDP.Factura,RutaDP.FPor,Pendiente.FFin FROM RutaDP,Pendiente WHERE Pendiente.Tipo='A tramitar ruta' AND Pendiente.Docto=RutaDP.idDP) AS data ON data.idDP=@idDP "
					+ "LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo='Amparar cancelación') AS pendCancelacion ON pendCancelacion.Docto=data.Factura AND pendCancelacion.Partida=data.FPor "
					+ "WHERE Tipo='A surtir ruta' AND Pendiente.Docto=@idDP "
					+ "UNION "
					+ "SELECT '3' AS Folio,'Ejecución' AS Evento,(CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN etInicio.FInicio ELSE rlInicio.FInicio END) AS FechaInicio,"
					+ "COALESCE((CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN etFin.FFin ELSE rlFin.FFin END),pendCancelacion.FFin) AS FechaFin,"
					+ "(CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN etInicio.Responsable ELSE rlInicio.Responsable END) AS Responsable,NULL AS Etiqueta,CAST(NULL AS DATE) AS FechaFacturacion,CAST(NULL AS DATE) AS FechaTramitacion,"
					+ "pend.FFin AS FechaSurtido,CAST(NULL AS DATE) AS FechaEjecucion,CAST(NULL AS DATE) AS FechaCierre,NULL AS ComentariosGestor,NULL AS CajaColectora,NULL AS Documentosresultantes,NULL AS EntregayRevision,"
					+ "NULL AS Refacturacion,rdp.Ruta,rdp.ZonaMensajeria AS Zona,(CASE WHEN rdp.Conforme IS NULL THEN CASE WHEN (rdp.EstadoRuta='Cerrada' AND rdp.Entrega='Realizada') OR (rdp.ZonaMensajeria='Almacén') THEN 'NA' ELSE 'Pendiente' END WHEN rdp.Conforme=1 "
					+ "THEN 'SI' WHEN rdp.Conforme=0 THEN 'NO' END) AS Conforme,rdp.Entrega,'' AS EstadoRuta,NULL AS FPor,"
					+ funcion
							.obtenerTiempoTranscurridoSQL(
									"(CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN etInicio.FInicio ELSE rlInicio.FInicio END)",
									"COALESCE((CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN etFin.FFin ELSE rlFin.FFin END),pendCancelacion.FFin)",
									"Dias")
					+ " AS TP, '' FolioNE "
					+ "FROM Pendiente "
					+ "LEFT JOIN(SELECT Docto,FFin FROM Pendiente WHERE Tipo='A surtir ruta') AS pend ON pend.Docto=@idDP "
					+ "LEFT JOIN(SELECT idDP,Ruta,ZonaMensajeria,idEntrega,idRuta,Conforme,Entrega,EstadoRuta,Factura,FPor FROM RutaDP) AS rdp ON rdp.idDP=@idDP "
					+ "LEFT JOIN(SELECT Docto,FInicio,Responsable FROM Pendiente WHERE Tipo='A colectar elementos') AS etInicio ON etInicio.Docto=rdp.idEntrega "
					+ "LEFT JOIN(SELECT Docto,FFin,Responsable FROM Pendiente WHERE Tipo='A Ejecutar ruta') AS etFin ON etFin.Docto=rdp.idEntrega "
					+ "LEFT JOIN(SELECT Docto,Responsable,FInicio FROM Pendiente WHERE Tipo='Ruta a trabajar') AS rlInicio ON rlInicio.Docto=rdp.idRuta "
					+ "LEFT JOIN(SELECT Docto,FFin FROM Pendiente WHERE Tipo='Ruta a ejecutar') AS rlFin ON rlFin.Docto=rdp.idRuta "
					+ "LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo='Amparar cancelación') AS pendCancelacion ON pendCancelacion.Docto=rdp.Factura AND pendCancelacion.Partida=rdp.FPor "
					+ "WHERE Tipo='A surtir ruta' AND Pendiente.Docto=@idDP "
					+ "UNION "
					+ "SELECT '4' AS Folio,'Cierre' AS Evento,Pendiente.FInicio AS FechaInicio,COALESCE((CASE WHEN rdp.Entrega='Realizada' THEN rdp.FFin ELSE CAST(NULL AS DATE) END),pendCancelacion.FFin) AS FechaFin,Pendiente.Responsable,"
					+ "NULL AS Etiqueta,CAST(NULL AS DATE) AS FechaFacturacion,CAST(NULL AS DATE) AS FechaTramitacion,CAST(NULL AS DATE) AS FechaSurtido,CAST(NULL AS DATE) AS FechaEjecucion,CAST(NULL AS DATE) AS FechaCierre,"
					+ "NULL AS ComentariosGestor,NULL AS CajaColectora,(SELECT STUFF((SELECT CAST(',' AS varchar(MAX)) + FolioDoctos,CAST(',' AS varchar(MAX)) + FolioDoctos2,CAST(',' AS varchar(MAX)) + FolioDoctos3 "
					+ "FROM RutaDP WHERE idDP=@idDP FOR XML PATH('')), 1, 1, '')) AS Documentosresultantes,rdp.EntregayRevision,"
					+ "(CASE WHEN rdp.FERefacturacion IS NULL THEN CASE WHEN rdp.EstadoRuta='Cerrada' AND rdp.Entrega='Realizada' THEN 'NO' ELSE 'Pendiente' END ELSE CASE WHEN rdp.FERefacturacion=1 THEN 'SI' ELSE 'NO' END END) AS Refacturacion,"
					+ "NULL AS Ruta,NULL AS Zona,NULL AS Conforme,NULL AS Entrega,'' AS EstadoRuta,NULL AS FPor,"
					+ funcion
							.obtenerTiempoTranscurridoSQL(
									"Pendiente.FInicio",
									"COALESCE((CASE WHEN rdp.Entrega='Realizada' THEN rdp.FFin ELSE CAST(NULL AS DATE) END),pendCancelacion.FFin)",
									"Dias")
					+ " AS TP, rdp.FolioNE "
					+ "FROM Pendiente "
					+ "LEFT JOIN(SELECT idDP,idEntrega,idRuta,idSurtido,EntregayRevision,FERefacturacion,FFin,Entrega,EstadoRuta,Factura,FPor,FolioNE FROM RutaDP) AS rdp ON rdp.idDP=@idDP "
					+ "LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo='Amparar cancelación') AS pendCancelacion ON pendCancelacion.Docto=rdp.Factura AND pendCancelacion.Partida=rdp.FPor "
					+ "WHERE Tipo='A cerrar ruta' AND Pendiente.Docto=(CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN rdp.idEntrega ELSE RDP.idRuta END) "
					+ "UNION "
					+ "SELECT '5' AS Folio,'Entrega' AS Evento,pend.FInicio AS FechaInicio,COALESCE((CASE WHEN rdp.Entrega='Realizada' THEN rdp.FFin ELSE CAST(NULL AS DATE) END),pendCancelacion.FFin) AS FechaFin,NULL AS Responsable,RDP.Factura AS Etiqueta,"
					+ "data.FInicio AS FechaFacturacion,pend.FFin AS FechaTramitacion,pendSurtido.FFin AS FechaSurtido,(CASE WHEN rdp.idRuta IS NULL OR rdp.idRuta='' THEN et.FFin ELSE rlFin.FFin END) AS FechaEjecucion,rdp.FFin AS FechaCierre,"
					+ "NULL AS ComentariosGestor,NULL AS CajaColectora,rdp.FolioDoctos AS Documentosresultantes,NULL AS EntregayRevision,NULL AS Refacturacion,rdp.Ruta,rdp.ZonaMensajeria AS Zona,"
					+ "(CASE WHEN rdp.Conforme IS NULL THEN CASE WHEN (rdp.EstadoRuta='Cerrada' AND rdp.Entrega='Realizada') OR (rdp.ZonaMensajeria='Almacén') THEN 'NA' ELSE 'Pendiente' END WHEN rdp.Conforme=1 THEN 'SI' WHEN rdp.Conforme=0 THEN 'NO' END) AS Conforme,"
					+ "rdp.Entrega,rdp.EstadoRuta,rdp.FPor,"
					+ funcion
							.obtenerTiempoTranscurridoSQL(
									"pend.FInicio",
									"COALESCE((CASE WHEN rdp.Entrega='Realizada' THEN rdp.FFin ELSE CAST(NULL AS DATE) END),pendCancelacion.FFin)",
									"Dias")
					+ " AS TP, rdp.FolioNE "
					+ "FROM RutaDP AS rdp "
					+ "LEFT JOIN(SELECT Docto,FInicio,Responsable,FFin FROM Pendiente WHERE Tipo = 'A tramitar ruta') AS pend ON pend.Docto=rdp.idDP "
					+ "LEFT JOIN(SELECT RutaDP.idDP,Pendiente.FInicio FROM RutaDP,Facturas,Pendiente WHERE Pendiente.Docto=Facturas.CPedido "
					+ "AND (Pendiente.Tipo='A Facturar' OR Pendiente.Tipo='Facturar por adelantado') AND Facturas.Factura=RutaDP.Factura AND Facturas.FPor=RutaDP.FPor) AS data ON data.idDP=rdp.idDP "
					+ "LEFT JOIN(SELECT Docto,FFin FROM Pendiente WHERE Tipo='A surtir ruta') AS pendSurtido ON pendSurtido.Docto=@idDP "
					+ "LEFT JOIN(SELECT Docto,FInicio,FFin,Responsable FROM Pendiente WHERE Tipo='A Ejecutar ruta') AS et ON et.Docto=rdp.idEntrega "
					+ "LEFT JOIN(SELECT Docto,FFin FROM Pendiente WHERE Tipo='Ruta a ejecutar') AS rlFin ON rlFin.Docto=rdp.idRuta "
					+ "LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo='Amparar cancelación') AS pendCancelacion ON pendCancelacion.Docto=rdp.Factura AND pendCancelacion.Partida=rdp.FPor "
					+ "WHERE rdp.idDP=@idDP " + "ORDER BY Folio ASC";
			// log.info(query);
			return super.jdbcTemplate.query(query,map,new ConsultaEntregasTiempoProcesoRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<HistorialPNE> findHistorial(String idDP) {
		String query = new String();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDP", idDP);
			query = "SELECT pend.FFin AS Fecha,AK01_Ruta AS FolioRuta,usuario.Responsable AS Mensajero,evento.RazonesEntrega AS Razones "
					+ "FROM Ruta_Evento AS evento "
					+ "LEFT JOIN(SELECT idDP,RazonesEntrega,DoctosCierre,ComentariosAdicionales FROM RutaDP) AS rdp ON rdp.idDP=(evento.FK01_Evento COLLATE Modern_Spanish_CI_AS) "
					+ "LEFT JOIN(SELECT DISTINCT(Responsable),Docto FROM Pendiente WHERE (Tipo='Ruta a ejecutar' OR Tipo='Ruta a planear' OR Tipo='Ruta a trabajar')) AS usuario "
					+ "ON usuario.Docto=(evento.AK01_Ruta COLLATE Modern_Spanish_CI_AS) "
					+ "LEFT JOIN(SELECT Docto,FFin FROM Pendiente WHERE Tipo='A cerrar ruta') AS pend ON pend.Docto=(evento.AK01_Ruta COLLATE Modern_Spanish_CI_AS) "
					+ "WHERE evento.TipoJustificacion IS NOT NULL and evento.RazonesEntrega IS NOT NULL AND evento.FK01_Evento='"
					+ idDP + "' " + "ORDER BY Fecha DESC";
			// log.info(query);
			// S-lo mandar historial si aparece m-s de 1 vez el folio del evento
			// en la tabla Ruta_Evento, esto debido a que
			// el primer intento no cuenta como historial ya que esos datos se
			// mostrar-n en los generales.
			List<HistorialPNE> resultados = super.jdbcTemplate.query(query,map,
					new HistorialPNERowMapper());

			return resultados;
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ResumenConsulta> findComparativasDPeriodos(String condiciones) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condiciones", condiciones);
		try {
			String query = " \n SELECT COUNT(DISTINCT(rdp.Factura)) AS FOLIOS,SUM((CASE WHEN FAC.Moneda='Pesos' OR FAC.Moneda='M.N.' THEN restFac.MontoFactura/MON.PDolar WHEN FAC.Moneda='EUR' OR FAC.Moneda='Euros' THEN restFac.MontoFactura * MON.EDolar "
					+ " \n ELSE restFac.MontoFactura END)) AS MONTO,SUM(COALESCE(restFac.PiezasFactura,'0')) AS TOTAL,'0' AS PARTIDAS,'USD' AS MonedaFactura,'0' AS CP,'No Aplica' AS Nombre,'Comparativas' AS Resultados "
					+ " \n FROM RutaDP AS rdp "
					+ " \n LEFT JOIN(SELECT idFactura,Factura,FPor,Moneda,Fecha FROM Facturas) AS FAC ON FAC.Factura=rdp.Factura AND FAC.FPor=rdp.FPor "
					+ " \n LEFT JOIN(SELECT Factura,FPor,CPedido,Importe,Moneda FROM Remisiones WHERE Factura=1448 AND FPor='Proquifa') AS REM ON REM.Factura=rdp.Factura AND REM.FPor=rdp.FPor "
					+ " \n LEFT JOIN(SELECT * FROM Monedas) AS MON ON CAST(MON.Fecha AS DATE)=CAST(FAC.Fecha AS DATE) "
					+ " \n LEFT JOIN(SELECT COALESCE(SUM(ppF.Cant*ppF.Precio),SUM(ppR.Cant*ppR.Precio)) AS MontoFactura,COALESCE(SUM(ppF.Cant),SUM(ppR.Cant)) AS PiezasFactura,COALESCE(MIN(ppF.FEE),MIN(ppR.FEE)) AS FEE,prdp.idDP FROM PRutaDP AS prdp "
					+ " \n LEFT JOIN(SELECT Factura,FPor,Part,Cant,Importe,CPedido,PPedido FROM PFacturas) AS pfac ON pfac.Factura=prdp.Factura AND pfac.FPor=prdp.FPor AND pfac.Part=prdp.Part "
					+ " \n LEFT JOIN(SELECT Factura,FPor,Part,Importe,CPedido,PPedido FROM PRemisiones) AS pRem ON pRem.Factura=prdp.Factura AND pRem.FPor=prdp.FPor AND pRem.Part=prdp.Part "
					+ " \n LEFT JOIN(SELECT FPEntrega AS FEE,CPedido,Part,Cant,Precio FROM PPedidos) AS ppF ON ppF.CPedido=pfac.CPedido AND ppF.Part=pfac.PPedido "
					+ "LEFT JOIN(SELECT FPEntrega AS FEE,CPedido,Part,Cant,Precio FROM PPedidos) AS ppR ON ppR.CPedido=pRem.CPedido AND ppR.Part=pRem.PPedido "
					+ "GROUP BY idDP) AS restFac ON restFac.idDP=rdp.idDP "
					+ "LEFT JOIN(SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo = 'Amparar cancelación') AS facturaCancelada ON facturaCancelada.Docto=rdp.Factura AND facturaCancelada.Partida=rdp.FPor "
					+ " \n LEFT JOIN (SELECT Responsable,Docto,FInicio,Tipo FROM Pendiente WHERE FFin IS NULL AND Tipo LIKE 'Ruta a trabajar%') AS usuario ON   usuario.Docto=rdp.idRuta  "
					+ " \n LEFT JOIN (SELECT Responsable,Docto,FInicio,Tipo FROM Pendiente WHERE Tipo like 'Ruta a ejecutar%') AS RutaEjecutar ON  RutaEjecutar.Docto=rdp.idRuta   "
					+ condiciones;

			// "LEFT JOIN (SELECT Responsable,Docto FROM Pendiente WHERE (Tipo='Ruta a ejecutar' OR Tipo='A Ejecutar ruta')) AS usuario ON usuario.Docto IN (rdp.idEntrega,rdp.idRuta) "
			// +
			// log.info(query);
			// logger.info("4--: " + query + "*-");
			return super.jdbcTemplate.query(query,map,new ResumenConsultaRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Factura> findGraficosEntregas(String condiciones) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condiciones", condiciones);
			String query = "";
			query = " SELECT DISTINCT(rdp.idEntrega), ( ";
			query += " \n CASE WHEN (FAC.Moneda='Pesos' OR FAC.Moneda='M.N.') THEN restFac.MontoFactura/case when coalesce(FAC.TCambio,MON.PDolar) = 0 then MON.PDolar else fac.TCambio end WHEN (FAC.Moneda='Euros' OR FAC.Moneda='EUR') THEN restFac.MontoFactura * MON.EDolar ELSE restFac.MontoFactura END) AS TotalDLS, ";
			query += " \n 	COALESCE(restFac.PiezasFactura,'0') AS PiezasFactura, ";
			query += " \n 	numPRDP.Cuantos AS NumeroPartidas, ";
			query += " \n 	cli.Nombre AS Cliente, ";
			query += " \n 	COALESCE(intentos.NumIntentos,'0') AS NumIntentos, ";
			query += " \n 	COALESCE(motivos.IntentoClientes,'0') AS MotivoClientes, ";
			query += " \n 	COALESCE(motivos.IntentoSolicitante,'0') AS MotivoSolicitante, ";
			query += " \n 	COALESCE(motivos.IntentoMensajero,'0') AS MotivosMensajero, ";
			query += " \n 	COALESCE(motivos.IntentoND,'0') AS MotivosND , ";
			query += " \n 	fac.Factura  ";
			query += " \n FROM RutaDP AS rdp  ";
			query += " \n LEFT JOIN(SELECT Clave,Nombre FROM Clientes) AS cli ON cli.Clave=rdp.idCliente   ";
			query += " \n INNER JOIN(SELECT idFactura,Factura,FPor,CPedido,Importe,Moneda,Fecha, TCambio, DeSistema  FROM Facturas WHERE DeSistema = 1) AS FAC ON FAC.Factura=rdp.Factura AND FAC.FPor=rdp.FPor  ";
			query += " \n LEFT JOIN(SELECT * FROM Monedas) AS MON ON CAST(MON.Fecha AS DATE)=CAST(FAC.Fecha AS DATE)  ";
			query += " \n LEFT JOIN(SELECT Factura,FPor,CPedido,Importe,Moneda FROM Remisiones) AS REM ON REM.Factura=rdp.Factura AND REM.FPor=rdp.FPor  ";
			query += " \n LEFT JOIN(SELECT Responsable,Docto FROM Pendiente WHERE (Tipo like 'Ruta a ejecutar%' OR Tipo='A Ejecutar ruta' or Tipo LIKE 'Ruta a trabajar%')) AS usuario ON usuario.Docto IN (rdp.idEntrega,rdp.idRuta)  ";
			query += " \n LEFT JOIN(SELECT COUNT(PK_Ruta_Evento) AS NumIntentos,FK01_Evento FROM Ruta_Evento GROUP BY FK01_Evento) AS intentos ON intentos.FK01_Evento=rdp.idDP  ";
			query += " \n LEFT JOIN(SELECT DISTINCT(rtev.FK01_Evento) AS FolioDP,COALESCE(cli.Num,'0') AS IntentoClientes,COALESCE(sol.Num,'0') AS IntentoSolicitante,COALESCE(mensajero.Num,'0') AS IntentoMensajero,COALESCE(nd.Num,'0') AS IntentoND  ";
			query += " \n 	FROM Ruta_Evento AS rtev  ";
			query += " \n 	LEFT JOIN(SELECT COUNT(*) AS Num,FK01_Evento FROM Ruta_Evento WHERE rtrim(TipoJustificacion)='Cliente' GROUP BY FK01_Evento) AS cli ON cli.FK01_Evento=rtev.FK01_Evento  ";
			query += " \n 	LEFT JOIN(SELECT COUNT(*) AS Num,FK01_Evento FROM Ruta_Evento WHERE rtrim(TipoJustificacion)='Solicitante' GROUP BY FK01_Evento) AS sol ON sol.FK01_Evento=rtev.FK01_Evento  ";
			query += " \n 	LEFT JOIN(SELECT COUNT(*) AS Num,FK01_Evento FROM Ruta_Evento WHERE rtrim(TipoJustificacion)='Mensajero' GROUP BY FK01_Evento) AS mensajero ON mensajero.FK01_Evento=rtev.FK01_Evento  ";
			query += " \n 	LEFT JOIN(SELECT COUNT(*) AS Num,FK01_Evento FROM Ruta_Evento WHERE (TipoJustificacion='' or TipoJustificacion IS NULL) GROUP BY FK01_Evento) AS nd ON nd.FK01_Evento=rtev.FK01_Evento ";
			query += " \n ) AS motivos ON motivos.FolioDP=rdp.idDP  ";
			query += " \n LEFT JOIN(SELECT COALESCE(SUM(pfac.Cant * pfac.Importe),SUM(ppF.Cant * ppF.Precio),SUM(ppR.Cant * ppR.Precio)) AS MontoFactura,COALESCE(SUM(pfac.Cant), SUM(ppF.Cant),SUM(ppR.Cant)) AS PiezasFactura,COALESCE(MIN(ppF.FEE),MIN(ppR.FEE)) AS FEE,prdp.idDP  ";
			query += " \n 	FROM PRutaDP AS prdp  ";
			query += " \n 		LEFT JOIN(SELECT Factura,FPor,Part,Cant,Importe,CPedido,PPedido FROM PFacturas) AS pfac ON pfac.Factura=prdp.Factura AND pfac.FPor=prdp.FPor AND pfac.Part=prdp.Part  ";
			query += " \n 		LEFT JOIN(SELECT Factura,FPor,Part,Importe,CPedido,PPedido FROM PRemisiones) AS pRem ON pRem.Factura=prdp.Factura AND pRem.FPor=prdp.FPor AND pRem.Part=prdp.Part  ";
			query += " \n 		LEFT JOIN(SELECT FPEntrega AS FEE,CPedido,Part,Cant,Precio FROM PPedidos) AS ppF ON ppF.CPedido=pfac.CPedido AND ppF.Part=pfac.PPedido  ";
			query += " \n 		LEFT JOIN(SELECT FPEntrega AS FEE,CPedido,Part,Cant,Precio FROM PPedidos) AS ppR ON ppR.CPedido=pRem.CPedido AND ppR.Part=pRem.PPedido GROUP BY idDP ";
			query += " \n ) AS restFac ON restFac.idDP=rdp.idDP  ";
			query += " \n LEFT JOIN(SELECT COUNT(Folio) AS Cuantos,idDP FROM PRutaDP GROUP BY idDP) AS numPRDP ON numPRDP.idDP=rdp.idDP   ";
			query += " \n LEFT JOIN (SELECT Docto,Partida,FFin FROM Pendiente WHERE Tipo = 'Amparar cancelación') AS facturaCancelada ON facturaCancelada.Docto=rdp.Factura AND facturaCancelada.Partida=rdp.FPor ";
			query += " \n LEFT JOIN (SELECT Docto,FInicio,Tipo,Responsable FROM Pendiente WHERE FFin IS NULL AND Tipo LIKE 'Ruta a trabajar%') AS RutaTrabajar ON   RutaTrabajar.Docto=rdp.idRuta   ";
			query += " \n LEFT JOIN (SELECT Docto,FInicio,Tipo,Responsable FROM Pendiente WHERE (Tipo like 'Ruta a ejecutar%'OR Tipo='A Ejecutar ruta')) AS RutaEjecutar ON  RutaEjecutar.Docto in(rdp.idEntrega,rdp.idRuta)   ";
			query += condiciones;
			// logger.info("Query: --- \n" + query);
			log.info(query);
			return super.jdbcTemplate.query(query,map,new ConsultaGraficasEntregasRowMapper());
		} catch (Exception e) {
			funcion = new Funcion();
			funcion.enviarCorreoAvisoExepcion(e);
			log.info("Error: " + e.getMessage());
			return null;
		}
	}
}