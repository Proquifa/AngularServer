/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.Cobros;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.HistorialFacturaRowMapper;
import com.proquifa.net.persistencia.consultas.ConsultaCobrosDAO;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaCobrosRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaInspectorCobrosRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaLineaTiempoResunenRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ResumenConsultaRowMapper;

/**
 * @author vromero
 *
 */
@Repository
public class ConsultaCobrosDAOImpl extends DataBaseDAO implements ConsultaCobrosDAO {

	final Logger log = LoggerFactory.getLogger(ConsultaCobrosDAOImpl.class);
	
	Funcion f ;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cobros> findCobros(String periodo, String restricciones, List<NivelIngreso> niveles)
			throws ProquifaNetException {
		try{
			f = new Funcion(); //NIVEL.VentasUSD
			
			String query = 
							"\n SELECT DISTINCT p.DoctoR,F.FPor, F.Cliente AS IDCLIENTE, CLI.Nombre, COALESCE(P.CPedido, 'N/A') CPedido, F.Factura, F.CPago, COALESCE(F.Fecha,PEA.FFIN) AS FECHAFACTURACION, F.Medio,F.OrdenCompra, prdp.Entrega, " +
							"\n COALESCE(CAST(PEPA.Pago AS VARCHAR(50)), CASE WHEN RUTA1.RUTA = 'LOCAL' THEN RUTA1.FolioDoctos2 ELSE NULL END,   " +
							"\n CASE WHEN RUTA2.RUTA = 'LOCAL' THEN RUTA2.FolioDoctos ELSE NULL END, 'N/A') AS CONTRARECIBO,  " +
							"\n COALESCE(RUTA1.FFin, RUTA2.FFin) AS FECHAREVISION,  " +
							"\n COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) AS FEP,  " +
							"\n DATEDIFF(DAY, GETDATE(),COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) AS DRC,PFAC.PIEZAS,PFAC.PARTIDAS,  " +
							"\n COALESCE(PAGOP.Medio,CLI.MedioPago , INCONS.MedioPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.TPago END, 'Pendiente') AS MEDIOPAGO,  " +
							"\n COALESCE(CASE WHEN F.CPago = 'PREPAGO 100%' AND INCONS.Monto<> null AND INCONS.Monto<> 0 THEN INCONS.Monto * (1 + F.IVA) ELSE NULL END ,F.Importe * (1 + F.IVA),PAGOP.Monto* (1 + F.IVA)) AS MONTOESPERADO,    " +
							f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + "  AS NIVEL,    " +
							"\n COALESCE(CASE WHEN F.CPago = 'PREPAGO 100%' AND INCONS.Monto<> null AND INCONS.Monto<> 0 THEN INCONS.Moneda  ELSE NULL END ,F.Moneda ,PAGOP.Moneda ) AS MONEDAESPERADO,  " +
							"\n COBROREAL.MontoPagado AS MONTOREAL, COBROREAL.MonedaPagada AS MONEDAREAL, COBROREAL.FolioPC AS FOLIOPC, F.Estado,  " +
							//"\n CASE WHEN COBRO.FPago IS NOT NULL THEN COBRO.FPago " + 
							//"\n WHEN COBRO2.FP IS NOT NULL THEN COBRO2.FP " + 
							//"\n WHEN FECH.FFin IS NOT NULL THEN CONVERT(DATE,FECH.FFin) ELSE  CONVERT(DATE,PREP.FFin) END AS FECHAREAL, " +
							"\n COBROREAL.FPago AS FECHAREAL, " +
							"\n CASE WHEN F.CPago = 'PREPAGO 100%'AND INCONS.Monto<> null AND INCONS.Monto<> 0 THEN ( CASE  WHEN INCONS.Moneda = 'M.N.' THEN INCONS.Monto / INCONS.TipoCambio ELSE INCONS.Monto END) ELSE			  " + 
							"\n (CASE WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN F.Importe* MON.EDolar WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe /  MON.PDolar ELSE F.Importe END) END AS MONTODLLS, COALESCE(P.Pedido, 'OC-' + F.OrdenCompra)  AS REFERENCIA, " +
							"\n CASE WHEN DATEDIFF(DAY, CASE WHEN COBROREAL.FPAGO IS NOT NULL THEN COBROREAL.FPAGO" +
							"\n WHEN COBRO2.FP IS NOT NULL THEN COBRO2.FP WHEN FECH.FFIN IS NOT NULL THEN CONVERT(DATE,FECH.FFin) " +
							"\n WHEN PREP.FFin IS NOT NULL THEN CONVERT(DATE,PREP.FFin) ELSE CONVERT(DATE,GETDATE()) END, COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) > 0 THEN 'NO' ELSE 'SI' END AS MOROSO, F.Fecha " +
							"\n ,(CASE WHEN EMPL.Usuario IS NULL THEN 'NO ASIGNADO' ELSE EMPL.Usuario END) AS userCobrador, CLI.Cobrador AS claveCobrador, coalesce(CLI.Vendedor,'NO ASIGNADO') Vendedor, F.TCambio, coalesce (replace(replace(NoCuenta,'-',''),' ', ''),'ND') NoCuenta, coalesce (cb.Banco , 'ND') Banco, " +
							 
							"\n  CASE WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros') and MON.EDolar IS Not null THEN F.Importe * MON.EDolar WHEN (F.Moneda = 'Pesos' OR F.Moneda = 'M.N.') and f.TCambio <> 0  THEN F.Importe /  f.TCambio	ELSE F.Importe END AS SUBTOTALDLLS, " +
							"\n  CASE WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros') and MON.EDolar IS Not null THEN  F.Importe * f.IVA * MON.EDolar	WHEN (F.Moneda = 'Pesos' OR F.Moneda = 'M.N.') and f.TCambio <> 0  THEN (F.Importe* f.IVA) /  f.TCambio	ELSE F.Importe * f.IVA END AS IVADLLS, " +
							"\n  CASE WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros') and MON.EDolar IS Not null THEN (F.Importe+(F.Importe * f.IVA))* MON.EDolar	WHEN (F.Moneda = 'Pesos' OR F.Moneda = 'M.N.') and f.TCambio <> 0  THEN (F.Importe+ (F.Importe * f.IVA)) /  f.TCambio ELSE F.Importe + (F.Importe * f.IVA)  END AS TOTALDLLS, " +
							"\n  CASE WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros') and MON.EDolar IS Not null THEN F.Importe* MON.EDolar * MON.PDolar	WHEN (F.Moneda = 'Pesos' OR F.Moneda = 'M.N.') and f.TCambio <> 0  THEN F.Importe	ELSE F.Importe * f.TCambio  END AS SUBTOTALPESOS, " +
							"\n  CASE WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros') and MON.EDolar IS Not null THEN  F.Importe * f.IVA* MON.EDolar * MON.PDolar	WHEN (F.Moneda = 'Pesos' OR F.Moneda = 'M.N.') and f.TCambio <> 0  THEN (F.Importe* f.IVA)	ELSE (F.Importe * f.IVA)* f.TCambio END AS IVAPESOS, " +
							"\n  CASE WHEN (F.Moneda = 'EUR' OR F.Moneda = 'Euros') and MON.EDolar IS Not null THEN (F.Importe+(F.Importe * f.IVA))* MON.EDolar * MON.PDolar  WHEN (F.Moneda = 'Pesos' OR F.Moneda = 'M.N.') and f.TCambio <> 0  THEN (F.Importe+ (F.Importe * f.IVA))	ELSE (F.Importe + (F.Importe * f.IVA))* f.TCambio  END AS TOTALPESOS,  prdp.FFin " +
							"\n ,PRDP.FPEntrega, F.UUID" +
							"\n FROM Facturas AS F " +
							"\n LEFT JOIN (select idCuenta , Banco, NoCuenta  from CuentaBanco  ) as CB on CB.idcuenta =  F.FK01_idCuentaBanco " +
							"\n LEFT JOIN (SELECT Clave, Nombre,MedioPago, Cobrador, Vendedor FROM Clientes) AS CLI ON CLI.Clave = F.Cliente " +
							"\n LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro')AS FECH ON F.Factura = FECH.Docto AND F.FPor=FECH.Partida	" +
							"\n LEFT JOIN( SELECT MAX(Folio)AS Folio,DOCTO FROM Pendiente WHERE Tipo='Cobro a validar' GROUP BY DOCTO) AS PREP1 ON PREP1.Docto=F.CPedido " + 
							"\n LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PREP ON PREP.Folio=PREP1.Folio " +
							
							"\n LEFT JOIN (SELECT * FROM PagoPendiente WHERE YEAR(Fecha)>2009) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +
							"\n LEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = F.CPedido " +
							"\n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=F.CPedido " +
							"\n LEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,CPEDIDO FROM PedidoPago GROUP BY CPedido) AS PEPA1 ON PEPA1.CPedido = F.CPedido " +
							"\n LEFT JOIN (SELECT * FROM PedidoPago) AS PEPA ON PEPA.Folio = PEPA1.FOLIO  " +
							"\n  LEFT JOIN (select Factura, FPor, MontoPagado, MonedaPagada, FolioPC, FPago, idPedidoPago from Cobro) AS CO ON (CO.Factura = F.Factura AND CO.FPor = F.FPor AND F.Importe = CO.MontoPagado) or (CO.idPedidoPago=PEPA.folio) " +
							"\n LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +
							"\n LEFT JOIN (SELECT  MAX(DP.FolioDoctos2) FolioDoctos2, DP.Factura, DP.FPor, DP.Ruta, MAX(P.FFin) FFin, DP.idCliente, DP.Entrega  FROM RutaDP DP " +
							"\n	INNER JOIN (SELECT MAX(P.FFin) FFin, P.Docto, P.Tipo FROM Pendiente P WHERE P.Tipo = 'A cerrar ruta' GROUP BY P.Docto, P.Tipo) P ON P.Docto = DP.idRuta AND P.Tipo = 'A cerrar ruta' " +
							"\n	WHERE DP.EstadoRuta = 'Cerrada'	AND DP.Entrega = 'Realizada' AND DP.EntregayRevision = 1  GROUP BY DP.Factura, DP.FPor, DP.Ruta,DP.idCliente,DP.Entrega) AS RUTA1 ON RUTA1.Factura = F.Factura AND RUTA1.FPor = F.FPor AND F.Cliente = RUTA1.idCliente " +
							"\n LEFT JOIN (SELECT RutaPR.Factura, RutaPR.FPor, Pendiente.FFin, RutaPR.FolioDoctos, RutaPR.Ruta FROM RutaPR,Pendiente WHERE RutaPR.EstadoRuta='Cerrada' AND RutaPR.Entrega='Realizada' " +
							"\n	AND Pendiente.Docto=RutaPR.idRuta AND Pendiente.Tipo='A cerrar ruta') AS RUTA2 ON RUTA2.Factura = F.Factura AND RUTA2.FPor = F.FPor " +
							"\n LEFT JOIN (SELECT CO.Fecha, CO.MontoPagado, CO.MonedaPagada, CO.FolioPC, F.Factura, F.FPor, CO.FPago AS FP,CO.TCPagado AS TCAMBIO FROM Cobro AS CO, PedidoPago AS PPA, Pedidos AS P, Facturas AS F " +
							"\n	WHERE CO.idPedidoPago = PPA.Folio AND PPA.Pedido = P.DoctoR AND P.CPedido = F.CPedido) AS COBRO2 ON COBRO2.Factura = F.Factura AND COBRO2.FPor = F.FPor " +
							"\n LEFT JOIN (SELECT * FROM Monedas) AS MON ON MON.Fecha = F.Fecha " +
							"\n LEFT JOIN (SELECT COUNT(*) AS PARTIDAS,SUM(Cant)AS PIEZAS, Factura,FPor FROM PFacturas GROUP BY Factura,FPor)AS PFAC ON PFAC.Factura=F.Factura AND PFAC.FPor=F.FPor  " +
							"\n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar    " +
							"\n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/F.TCambio END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha    " +
							"\n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = F.Cliente    " +
							"\n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C    " +
							"\n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre    " +
							"\n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)  " +  
							"\n AS OPER ON OPER.Clave = F.Cliente  " +
							"\n LEFT JOIN (SELECT Clave, Usuario FROM Empleados) AS EMPL ON EMPL.Clave = CLI.Cobrador " +
							"\n LEFT JOIN Cobro AS COBROREAL ON COBROREAL.Factura = F.Factura AND COBROREAL.FPor = F.FPor OR COBROREAL.idPedidoPago = PEPA.Folio " +				
							"\n LEFT JOIN ( SELECT  F.Factura,F.FPor, CO.FPago AS FP FROM Cobro AS CO, PedidoPago AS PPA, Pedidos AS P, Facturas AS F " +
							"\n WHERE F.CPedido=P.CPedido AND CO.idPedidoPago = PPA.Folio AND PPA.Pedido = P.DoctoR ) AS COBROREAL2 ON COBROREAL2.Factura = F.Factura AND COBROREAL2.FPor = F.FPor " +
							"\n LEFT JOIN (SELECT  MAX(RDP.FFin) FFin, rdp.Factura, rdp.FPor, MIN(Pped.FPEntrega) FPEntrega, RDP.Entrega   FROM PRutaDP as PRDP " +
							"\n left join (select * from RutaDP) as RDP on RDP.Factura = PRDP.Factura AND RDP.FPor = PRDP.FPor AND PRDP.idDP = PRDP.idDP " +
							"\n left join (select * from PFacturas) as Pfac on Pfac.Factura = PRDP.Factura AND PFac.FPor = PRDP.FPor " +
							"\n left join (select * from PPedidos) as Pped on Pped.CPedido = Pfac.CPedido AND Pped.Part = Pfac.PPedido " +
							"\n where rdp.FFin is not null group by rdp.Factura , rdp.fpor, RDP.Entrega " +
							"\n ) as prdp on prdp.Factura = F.Factura AND prdp.FPor = F.FPor  " +
							"\n WHERE F.DeSistema=1 AND F.FPor <>'ProquifaServicios' " + periodo + " " + restricciones + " AND F.Medio IS NOT NULL ORDER BY F.Fecha DESC";
			log.info(query);  
			return super.jdbcTemplate.query(query, new ConsultaCobrosRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nPeriodo: "+ periodo,"\nRestricciones: "+ restricciones);
			return new ArrayList<Cobros>();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HistorialFactura> findHistorial(String restricciones)
			throws ProquifaNetException {
		try{
			String query = 	" SELECT * FROM HistorialFactura " 
							+ restricciones +
							" ORDER BY FechayHora Desc";
			////logger.info(query);
			return super.jdbcTemplate.query(query, new HistorialFacturaRowMapper());
		}catch(Exception e){
//			logger.error(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nRestricciones: "+restricciones);
			return new ArrayList<HistorialFactura>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findLineaTiempoResumen(String factura, String fpor) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			Funcion funcion = new Funcion();
			String query = 	
							"  \nSELECT '4' AS ID,'COBRO' AS ETIQUETA, COALESCE(PEN.FInicio,PET.FInicio) AS FINICIO, COALESCE(PEN.FFin,PGP.FFin) AS FFIN, NULL AS CONTACTO, COALESCE(PEN.Responsable,PET.Responsable,PGP.Responsable) AS RESPONSABLE, CASE WHEN FAC.CPago LIKE '%100%' THEN '1' ELSE '0' END AS REFERENCIA, " +
							"  \nNULL AS MEDIO,COALESCE(PAGOP.Medio, INCONS.MedioPago, CASE WHEN FAC.CPago = 'PREPAGO 100%' THEN FAC.TPago END, 'Pendiente') COLLATE SQL_Latin1_General_CP1_CI_AS AS COMENTARIOS,NULL AS QUIENCOMPRA,NULL AS LoteTxt, " +
							   funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEN.FInicio,PET.FInicio)", "COALESCE(PEN.FFin,PGP.FFin)", "Dias") + "AS TT, NULL AS CONFORME, NULL AS TIPO, NULL AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO,FAC.Estado, NULL AS FCancelacion, '' AS FolioCompPago " +
							"  \nFROM Facturas AS FAC " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN ON PEN.Docto = FAC.Factura  AND PEN.Partida = FAC.FPor " +
						    "  \nLEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = FAC.Factura AND PAGOP.QuienFactura = FAC.FPor " +
						    "  \nLEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = FAC.Factura AND RE.FPor = FAC.FPor " +
						    "  \nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = FAC.CPedido OR P.CPedido = RE.CPedido " +
						    "  \nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR  " +
							"  \nLEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=PED.DoctoR " +
							"  \nLEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,Docto FROM Pendiente WHERE Tipo='Pago pendiente' GROUP BY Docto) AS PGP1 ON PGP1.Docto=PED.DoctoR " + 
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Pago pendiente') AS PGP ON PGP.Folio=PGP1.FOLIO " +
							"  \nWHERE (PEN.FInicio IS NOT NULL OR PET.FInicio IS NOT NULL) AND FAC.Factura= :factura AND FAC.fpor= :fpor AND FAC.DeSistema = 1 " +
							"  \nUNION"+
							"  \nSELECT TOP 1 '3' AS ID,'REVISION' AS ETIQUETA, PEN.FInicio AS FINICIO, RPR.FFin AS FFIN,NULL AS CONTACTO, PEN.Responsable AS RESPONSABLE," +
							"  \nNULL AS REFERENCIA, NULL AS MEDIO,NULL AS COMENTARIOS,NULL AS QUIENCOMPRA,NULL AS LoteTxt," +
							   funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "RPR.FFin", "Dias") +" AS TT, NULL AS CONFORME, NULL AS TIPO,NULL AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO,FAC.Estado, NULL AS FCancelacion, '' AS FolioCompPago" +			
							"  \nFROM Pendiente AS PEN" +
							"  \nLEFT JOIN(SELECT * FROM Facturas) AS FAC ON FAC.Factura = PEN.Docto" +
							"  \nLEFT JOIN(SELECT * FROM RutaPR WHERE Entrega LIKE 'Realizada') AS RPR ON RPR.Factura = Fac.Factura AND RPR.FPor = FAC.FPor " +
							"  \nLEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON FAC.Factura = RDP.Factura AND RDP.FPor = FAC.FPor " +
							"  \nLEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente" +
							"  \nWHERE  FAC.Factura= :factura AND FAC.fpor= :fpor AND RPR.idPR IS NOT NULL AND PEN.Tipo LIKE 'Programar revisión' AND (CLI.CPago LIKE '%dias%' OR CLI.CPago LIKE 'PAGO CONTRA ENTREGA' ) AND RDP.EntregayRevision=0 " +
							"  \nUNION"+
							"  \nSELECT TOP 1 '2' AS ID,CASE WHEN COALESCE(EA.Guia, FACFE.Guia) IS NULL THEN 'ENTREGA' ELSE 'ENVIO' END  AS ETIQUETA,COALESCE(PENTRA3.FInicio,PENTRA.FInicio, PENTRA2.FInicio, PENENV.FInicio) AS FINICIO, " +
							"  \nCOALESCE(RDP.FFIN, PENEJE.FFIN, PENENV.FFIN) AS FFIN,  COALESCE(EA.Contacto, P.CONTACTO) AS CONTACTO,  " +
							"  \nCOALESCE(PEN.Responsable,PENENV.Responsable, PENEJE.Responsable) AS RESPONSABLE,  COALESCE(GF.Guia,EA.Guia, FACFE.Guia) AS REFERENCIA, " +
							"  \nCOALESCE(CE.Mensajeria,EA.Mensajeria) AS Medio,  CAST(RDP.ComentariosAdicionales AS VARCHAR(500)) AS COMENTARIOS,'' AS QUIENCOMPRA,  '' AS LoteTxt," +
							   funcion.obtenerTiempoTranscurridoSQL("COALESCE(PENTRA3.FInicio,PENTRA.FInicio, PENTRA2.FInicio, PENENV.FInicio)", "COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN)", "Dias") +
							"  \nAS TT, RDP.Conforme AS CONFORME,'' AS TIPO, COALESCE(EA.idGuia, FACFE.Guia, RDP.FolioDoctos) AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO,F.Estado, NULL AS FCancelacion, '' AS FolioCompPago " +							 
							"  \nFROM PPedidos AS PP" +
							"  \nLEFT JOIN (SELECT Factura, FPor, CPedido, PPedido, Part FROM PFacturas WHERE Estado IS NULL) AS PF ON PF.PPedido = PP.Part AND PF.CPedido = PP.CPedido" +
							"  \nLEFT JOIN (SELECT Factura, FPor, CPedido, PPedido, Part FROM PFacturas WHERE Estado = 'Cancelada') AS PF2	ON PF2.PPedido = PP.Part AND PF2.CPedido = PP.CPedido" +
							"  \nLEFT JOIN (SELECT Factura, FPor, idFactura,CPedido, Estado FROM Facturas) AS F ON F.Factura = PF.Factura AND F.FPor = PF.FPor" +
							"  \nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP	ON (PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part) OR (PRDP.Factura = PF2.Factura AND PRDP.FPor = PF2.FPor AND PRDP.Part = PF2.Part)" +
							"  \nLEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP" +
							"  \nLEFT JOIN (SELECT * FROM RutaPR ) AS RPR ON RPR.Factura = PF.Factura AND RPR.FPor = PF.FPor" +
							"  \nLEFT JOIN (SELECT DISTINCT FInicio, Docto FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' AND Partida = '' ) AS PENTRA	ON PENTRA.Docto = PRDP.idDP" +
							"  \nLEFT JOIN (SELECT DISTINCT FInicio, Docto FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' AND Partida <> '' ) AS PENTRA3 ON PENTRA3.Docto = PRDP.idDP" +
							"  \nLEFT JOIN (SELECT DISTINCT FInicio, Docto FROM Pendiente WHERE Tipo = 'Ruta a planear') AS PENTRA2 ON PENTRA2.Docto = RPR.idRuta" +
							"  \nLEFT JOIN (SELECT DISTINCT FInicio, FFin, Docto, Partida, Responsable FROM Pendiente WHERE Tipo = 'Alistar envío internacional') AS PENENV	ON (PENENV.Docto = F.idFactura OR (PENENV.Docto = F.Factura AND PENENV.Partida = F.FPor)) " +
							"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA ON PENRUTA.Docto = RDP.idRuta" +
							"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA2 ON PENRUTA2.Docto = RPR.idRuta" +
							"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto, Responsable FROM Pendiente WHERE Tipo = 'A Ejecutar ruta') AS PENEJE	ON PENEJE.Docto = RDP.idEntrega" +
							"  \nLEFT JOIN (SELECT DISTINCT Docto, Responsable FROM Pendiente WHERE Tipo = 'Ruta a trabajar') AS PEN	ON PEN.Docto = RDP.idRuta" +
							"  \nLEFT JOIN (SELECT * FROM FACTURAFOLIOENTREGA) AS FACFE ON (FACFE.Factura = PRDP.Factura AND FACFE.Fpor = PRDP.FPor) OR (FACFE.Factura = F.idFactura)" +
							"  \nLEFT JOIN (SELECT * FROM EnvioAlmacen) AS EA	ON (EA.idEntrega = RDP.idEntrega AND PENENV.FInicio IS NULL) OR (EA.idGuia = FACFE.Guia AND PENENV.FInicio IS NOT NULL)" +
							"  \nLEFT JOIN (SELECT * FROM GuiaFactura ) AS GF	ON GF.DoctoGuia = FACFE.Guia" +
							"  \nLEFT JOIN (SELECT DISTINCT CPedido, Mensajeria FROM CriterioEnvio) AS CE	ON CE.CPedido = PP.CPedido" +
							"  \nLEFT JOIN (SELECT CPedido, Contacto FROM Pedidos) AS P ON P.CPedido = PP.CPedido" +
							"  \nWHERE COALESCE(PENTRA.FInicio, PENENV.FInicio) IS NOT NULL AND F.Factura= :factura AND F.fpor=:fpor " +
							"  \nUNION"+
							"  \nSELECT TOP 1 '1' AS ID,'FACTURACION' AS ETIQUETA,PEN.FInicio,COALESCE(PEN.FFIN,PEA.FFIN,FAC.Fecha) FFin,'' AS CONTACTO,PEN.Responsable AS RESPONSABLE, FAC.Factura AS REFERENCIA,  FAC.Medio COLLATE Modern_Spanish_CI_AS AS MEDIO," +
							"  \nCOALESCE(CAST(PF.Nota AS VARCHAR(400)), CAST(R.Nota AS VARCHAR(400)))  AS COMENTARIOS, FAC.FPor AS QUIENCOMPRA,  R.LOTE AS LoteTxt," +
							   funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "PEN.FFin", "Dias") + "  AS TT,'' AS  CONFORME,FAC.Tipo AS TIPO, '' AS DOCUMENT, CONVERT(DATE,null) FECHACOBRO, NULL AS MONTO,FAC.Estado, NULL AS FCancelacion, '' AS FolioCompPago " +
							"  \nFROM Facturas AS FAC	" +
							"  \nLEFT JOIN( SELECT * FROM Pendiente)AS PEN ON FAC.CPedido = PEN.Docto		" +
							"  \nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido = PEN.Docto AND PF.Fpor=FAC.Fpor AND PF.Factura=FAC.Factura" +
							"  \nLEFT JOIN (SELECT * FROM PRemisiones) AS PR ON PR.CPedido = PEN.Docto " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=FAC.CPedido  " +
							"  \nLEFT JOIN (SELECT *, 'Fisica' AS Medio, 'Normal' AS TIPO , 'REMISION' AS LOTE FROM Remisiones) AS R ON R.Factura = PR.Factura AND R.FPor = PR.FPor		 " +
							"  \nWHERE PEN.FInicio IS NOT NULL AND (PEN.Tipo = 'A facturar' OR PEN.Tipo = 'Facturar por adelantado')  AND FAC.Factura =:factura AND FAC.fpor=:fpor"+
							"  \nUNION" +
							"  \nSELECT TOP 1 '5' AS ID,'INTERFACTURACION' AS ETIQUETA,PEN.FInicio,COALESCE(PEN.FFIN,PEA.FFIN,FAC.Fecha) FFin,'' AS CONTACTO,PEN.Responsable AS RESPONSABLE, FAC.Factura AS REFERENCIA,  FAC.Medio COLLATE Modern_Spanish_CI_AS AS MEDIO, "+  
							"  \nCOALESCE(CAST(PF.Nota AS VARCHAR(400)), CAST(FAC.CPago AS VARCHAR(400)))  AS COMENTARIOS, FAC.FPor AS QUIENCOMPRA,  FAC.Moneda AS LoteTxt,  "+
							funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "PEN.FFin", "Dias") + "  AS TT,'' AS  CONFORME,FAC.Tipo AS TIPO, '' AS DOCUMENT, CONVERT(DATE,null) FECHACOBRO, NULL AS MONTO,FAC.Estado, NULL AS FCancelacion, '' AS FolioCompPago "+   
							"  \nFROM Facturas as FAC " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente)AS PEN ON FAC.OrdenCompra = PEN.Docto " +		  
							"  \nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido = PEN.Docto AND PF.Fpor=FAC.Fpor AND PF.Factura=FAC.Factura " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=FAC.CPedido " +    
							"  \nWHERE FAC.Factura =:factura AND FAC.fpor=:fpor AND PEN.Tipo LIKE '%A facturar compra internacional%'  " +
							"  \nUNION " +
							"  \nSELECT TOP 1 '5' AS ID,'REFACTURACION' AS ETIQUETA,PEN1.FInicio,REGREF.FechaInicio as FFin,REGREF.FporNuevo AS CONTACTO,CASE WHEN REGREF.Folio IS NULL THEN NULL ELSE PEN1.Responsable END AS RESPONSABLE, REGREF.FacturaNueva AS REFERENCIA,  FAC.Medio COLLATE Modern_Spanish_CI_AS AS MEDIO, " +
							"  \nCAST(SREF.CAutorizacion AS VARCHAR(400))  AS COMENTARIOS, FAC.FPor AS QUIENCOMPRA,  SREF.Razones AS LoteTxt, " +
							   funcion.obtenerTiempoTranscurridoSQL("PEN1.FInicio", "REGREF.FechaInicio ", "Dias") +"  AS TT, '' AS  CONFORME, FAC.Tipo AS TIPO, SREF.Autorizo AS DOCUMENT, CONVERT(DATE,null) FECHACOBRO, NULL AS MONTO,FAC.Estado, NULL AS FCancelacion, '' AS FolioCompPago " + 
							"  \nFROM Facturas AS FAC	 " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente)AS PEN1 ON PEN1.Docto = FAC.Factura AND PEN1.Partida = FAC.FPor " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente)AS PEN ON FAC.CPedido = PEN.Docto		 " +
							"  \nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido = PEN.Docto AND PF.Fpor=FAC.Fpor AND PF.Factura=FAC.Factura " +
							"  \nLEFT JOIN (SELECT * FROM PRemisiones) AS PR ON PR.CPedido = PEN.Docto  " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=FAC.CPedido " +  
							"  \nLEFT JOIN (SELECT *, 'Fisica' AS Medio, 'Normal' AS TIPO , 'REMISION' AS LOTE FROM Remisiones) AS R ON R.Factura = PR.Factura AND R.FPor = PR.FPor " +		 
							"  \nLEFT JOIN (SELECT * FROM SRefacturacion) AS SREF ON SREF.Factura = FAC.Factura AND SREF.FPorOrigen = FAC.FPor " +
							"  \nLEFT JOIN (SELECT * FROM RegistroRefacturacion) AS REGREF ON REGREF.FacturaAntigua = FAC.Factura AND REGREF.FPorAntiguo = FAC.FPor" +
							"  \nWHERE PEN1.FInicio IS NOT NULL AND (PEN.Tipo = 'A facturar' OR PEN.Tipo = 'Facturar por adelantado') AND (PEN1.Tipo = 'A refacturación' OR PEN1.Tipo = 'A refacturaciónXAdela') " +
							"  \nAND FAC.Factura =:factura  AND FAC.fpor= :fpor " +
							"  \nUNION  " +
							"  \nSELECT '6' AS ID, 'COOBRADA' AS ETIQUETA,  COALESCE(PEA.FInicio,F.Fecha) FInicio , CASE WHEN F.CPago LIKE '%100%' THEN COALESCE (PREP.FFin,REFC.FFIN) ELSE COALESCE(PEMON.FFin,REFC.FFIN) END FFin,F.FPOR CONTACTO,COALESCE(FECH.Responsable,PREP.Responsable) RESPONSABLE, " +
							"  \nF.Factura AS REFERECIA,convert(varchar,COALESCE(PEN.FFIN,PEA.FFin,F.FECHA),121) MEDIO, CASE WHEN DATEDIFF(DAY,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin), COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) )> 0 THEN 'ET' ELSE 'FT'END AS COMENTARIOS,  " +
							"  \nCONVERT(VARCHAR,COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END),120) QUIENCOMPRA,COALESCE(CO.MonedaPagada,COPRE.MonedaPagada) LoteTxt,  " +
							   funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEA.FInicio,F.Fecha)", "CASE WHEN F.CPago LIKE '%100%' THEN COALESCE(PREP.FFin,CANC.FFin) ELSE COALESCE(PEMON.FFin,CANC.FFin) END", "Dias") +" AS TT,CASE WHEN F.CPago LIKE '%100%' THEN 5 ELSE 8 END CONFORME,f.CPago TIPO, " +
							"  \nCOALESCE(COPRE2.FolioPC,COPRE.FolioPC) DOCUMENT,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin)AS FECHACOBRO, COALESCE(CO.MontoPagado,COPRE.MontoPagado) MONTO, F.Estado, CANC.FFin AS FCancelacion, CO.Folio AS FolioCompPago " +
							"  \nFROM Facturas AS F " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro')AS FECH ON F.Factura = FECH.Docto AND F.FPor=FECH.Partida		 " +
							"  \nLEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON F.Factura = RPC.Factura AND RPC.idCliente = F.Cliente " +
							"  \nLEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = F.Factura AND CO.FPor=F.FPor		 " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE FInicio IS NOT NULL AND (Tipo = 'A facturar' OR Tipo = 'Facturar por adelantado'))AS PEN ON F.CPedido = PEN.Docto		 " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=F.CPedido	 " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Facturar por adelantado') AS FADE ON FADE.Docto=F.CPedido   " +
							"  \nLEFT JOIN( SELECT MAX(Folio)AS Folio,DOCTO FROM Pendiente WHERE Tipo='Cobro a validar' GROUP BY DOCTO) AS PREP1 ON PREP1.Docto=F.CPedido " + 
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PREP ON PREP.Folio=PREP1.Folio " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro') AS PEMON ON PEMON.Docto=F.Factura AND PEMON.Partida=F.FPor " +
							"  \nLEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=F.Factura AND COPRE2.FPor=F.FPor " +
							"  \nLEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,CPEDIDO FROM PedidoPago GROUP BY CPedido) AS PEPA1 ON PEPA1.CPedido = F.CPedido " + 
							"  \nLEFT JOIN (SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.Folio = PEPA1.FOLIO  " +
							"  \nLEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio " +
							"  \nLEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +
							"  \nLEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = F.Factura AND RE.FPor = F.FPor " +
							"  \nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = F.CPedido OR P.CPedido = RE.CPedido " +
							"  \nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Amparar cancelación') AS CANC ON CANC.Docto = F.Factura AND CANC.Partida = F.Fpor " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A refacturación' OR Tipo = 'A refacturaciónXAdela') AS REFC ON REFC.Docto = F.Factura AND REFC.Partida = F.FPor " +
							"  \nWHERE  F.Factura =:factura  AND F.fpor=:fpor AND F.DeSistema = 1 " +
							"  \nUNION" +
							"  \nSELECT '6' AS ID, 'COBRADA INTER' AS ETIQUETA,  COALESCE(PEA.FInicio,F.Fecha) FInicio , CO.Fecha FFin,F.FPOR CONTACTO,COALESCE(FECH.Responsable,PREP.Responsable) RESPONSABLE, " +   
							"  \nF.Factura AS REFERECIA,convert(varchar,COALESCE(PEN.FFIN,PEA.FFin,F.FECHA),121) MEDIO, CASE WHEN DATEDIFF(DAY,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin), COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) )> 0 THEN 'ET' ELSE 'FT'END AS COMENTARIOS, " +    
							"  \nCONVERT(VARCHAR,COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END),120) QUIENCOMPRA,COALESCE(CO.MonedaPagada,COPRE.MonedaPagada) LoteTxt, " +
								funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEA.FInicio,F.Fecha)", "CASE WHEN F.CPago LIKE '%100%' THEN COALESCE(PREP.FFin,CANC.FFin) ELSE COALESCE(PEMON.FFin,CANC.FFin) END", "Dias") +" AS TT,CASE WHEN F.CPago LIKE '%100%' THEN 5 ELSE 8 END CONFORME,f.CPago TIPO, " +   
							"  \nCOALESCE(COPRE2.FolioPC,COPRE.FolioPC) DOCUMENT,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin)AS FECHACOBRO, COALESCE(CO.MontoPagado,COPRE.MontoPagado) MONTO, F.Estado, CANC.FFin AS FCancelacion, '' AS FolioCompPago " +
							"  \nFROM Facturas AS F " +   
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro')AS FECH ON F.Factura = FECH.Docto AND F.FPor=FECH.Partida " +		   
							"  \nLEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON F.Factura = RPC.Factura AND RPC.idCliente = F.Cliente " +   
							"  \nLEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = F.Factura AND CO.FPor=F.FPor " +		   
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE FInicio IS NOT NULL AND (Tipo = 'A facturar' OR Tipo = 'Facturar por adelantado'))AS PEN ON F.CPedido = PEN.Docto " +		   
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=F.CPedido " +	   
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Facturar por adelantado') AS FADE ON FADE.Docto=F.CPedido " +     
							"  \nLEFT JOIN( SELECT MAX(Folio)AS Folio,DOCTO FROM Pendiente WHERE Tipo='Cobro a validar' GROUP BY DOCTO) AS PREP1 ON PREP1.Docto=F.CPedido " +   
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PREP ON PREP.Folio=PREP1.Folio " +   
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro') AS PEMON ON PEMON.Docto=F.Factura AND PEMON.Partida=F.FPor " +   
							"  \nLEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=F.Factura AND COPRE2.FPor=F.FPor " +   
							"  \nLEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,CPEDIDO FROM PedidoPago GROUP BY CPedido) AS PEPA1 ON PEPA1.CPedido = F.CPedido " +   
							"  \nLEFT JOIN (SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.Folio = PEPA1.FOLIO " +    
							"  \nLEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio " +   
							"  \nLEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +   
							"  \nLEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = F.Factura AND RE.FPor = F.FPor " +   
							"  \nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = F.CPedido OR P.CPedido = RE.CPedido " +   
							"  \nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +   
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Amparar cancelación') AS CANC ON CANC.Docto = F.Factura AND CANC.Partida = F.Fpor " +   
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A refacturación' OR Tipo = 'A refacturaciónXAdela') AS REFC ON REFC.Docto = F.Factura AND REFC.Partida = F.FPor " +   
							"  \nWHERE  F.Factura =:factura AND F.fpor=:fpor AND F.DeSistema = 1 AND F.OrdenCompra IS NOT NULL" +
							"  \nUNION" +
							"  \nSELECT TOP 1 '7' AS ID, 'COOBRADA_SC' AS ETIQUETA, COALESCE(FECH.FInicio,PET.FInicio) FInicio ,COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN,PREP.FFin,REFC.FFIN) AS FFIN,F.FPOR CONTACTO,COALESCE(FECH.Responsable,PREP.Responsable) RESPONSABLE, " +
							"  \nF.Factura AS REFERECIA,convert(varchar,PEA.FFin,121) MEDIO, CASE WHEN DATEDIFF(DAY,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin), COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) )> 0 THEN 'ET' ELSE 'FT'END AS COMENTARIOS, " +
							"  \nCONVERT(VARCHAR,COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END),120) QUIENCOMPRA,COALESCE(CO.MonedaPagada,COPRE.MonedaPagada) LoteTxt,  " +
							   funcion.obtenerTiempoTranscurridoSQL("COALESCE(FECH.FInicio,PET.FInicio)", "COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN,CANC.FFin)", "Dias") +" AS TT,CASE WHEN F.CPago LIKE '%100%' THEN 5 ELSE 8 END CONFORME,f.CPago TIPO, " +
							"  \nCOALESCE(COPRE2.FolioPC,COPRE.FolioPC) DOCUMENT,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin)AS FECHACOBRO, COALESCE(CO.MontoPagado,COPRE.MontoPagado) MONTO,F.Estado, CANC.FFin AS FCancelacion, '' AS FolioCompPago " +
							"  \nFROM Facturas AS F " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro')AS FECH ON F.Factura = FECH.Docto AND F.FPor=FECH.Partida		 " +
							"  \nLEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON F.Factura = RPC.Factura AND RPC.idCliente = F.Cliente " +
							"  \nLEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = F.Factura AND CO.FPor=F.FPor		 " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar') AS PEA ON PEA.Docto=F.CPedido	 " +
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Facturar por adelantado') AS FADE ON FADE.Docto=F.CPedido   " +
							"  \nLEFT JOIN( SELECT MAX(Folio)AS Folio,DOCTO FROM Pendiente WHERE Tipo='Cobro a validar' GROUP BY DOCTO) AS PREP1 ON PREP1.Docto=F.CPedido " + 
							"  \nLEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PREP ON PREP.Folio=PREP1.Folio " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro') AS PEMON ON PEMON.Docto=F.Factura AND PEMON.Partida=F.FPor " +
							"  \nLEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=F.Factura AND COPRE2.FPor=F.FPor " +
							"  \nLEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,CPEDIDO FROM PedidoPago GROUP BY CPedido) AS PEPA1 ON PEPA1.CPedido = F.CPedido " + 
							"  \nLEFT JOIN (SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.Folio = PEPA1.FOLIO  " +
							"  \nLEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio " +
							"  \nLEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +
							"  \nLEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = F.Factura AND RE.FPor = F.FPor " +
							"  \nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = F.CPedido OR P.CPedido = RE.CPedido " +
							"  \nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=P.DoctoR  " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Amparar cancelación') AS CANC ON CANC.Docto = F.Factura AND CANC.Partida = F.Fpor   " +
							"  \nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP	ON (PRDP.Factura = F.Factura AND PRDP.FPor = F.FPor )  " +
							"  \nLEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP " +
							"  \nLEFT JOIN (SELECT * FROM RutaPR ) AS RPR ON RPR.Factura = F.Factura AND RPR.FPor = F.FPor " +
							"  \nLEFT JOIN (SELECT DISTINCT FInicio, FFin, Docto, Partida, Responsable FROM Pendiente WHERE Tipo = 'Alistar envío internacional') AS PENENV	ON (PENENV.Docto = F.idFactura OR (PENENV.Docto = F.Factura AND PENENV.Partida = F.FPor))  " +
							"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA ON PENRUTA.Docto = RDP.idRuta " +
							"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA2 ON PENRUTA2.Docto = RPR.idRuta " +
							"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto, Responsable FROM Pendiente WHERE Tipo = 'A Ejecutar ruta') AS PENEJE	ON PENEJE.Docto = RDP.idEntrega " +
							"  \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A refacturación' OR Tipo = 'A refacturaciónXAdela') AS REFC ON REFC.Docto = F.Factura AND REFC.Partida = F.FPor " +
							"  \nWHERE  F.Factura =:factura AND F.fpor=:fpor AND F.DeSistema = 1 " +
							"  \nORDER BY ID ASC";
//			logger.info(query);
			return super.jdbcTemplate.query(query, map,new ConsultaLineaTiempoResunenRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+ factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorEntrega(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	"  SELECT 'TRAMITAR RUTA' AS ETIQUETA,COALESCE(PEN2.FInicio, PEN.FInicio) AS FInicio, COALESCE(PEN2.FFin,PEN.FFin) AS FFin, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, CAST(PRDP.idDP AS VARCHAR(14)) + '-D' AS REFERENCIA, '' AS MEDIO,  '' QUIENCOMPRA," + 
							"  CAST(RDP.Comentarios AS VARCHAR(500)) AS COMENTARIOS, 0 AS TT, '' AS CONFORME, '' AS LoteTxt,'' AS TIPO, " +
							"  '' AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO " +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = PEN.Docto" +
							" LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
							" LEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP" +
							" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' AND Partida <> '' ) AS PEN2 ON PEN2.Docto = PEN.Docto" +
							" WHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'A TRAMITAR RUTA' AND PEN.Partida = '' AND PF.fpor = :fpor  AND RDP.Factura=:factura " +
							" UNION" +
							" SELECT 'SURTIR RUTA' AS ETIQUETA,PEN.FInicio, PEN.FFin, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, CASE WHEN RDP.Ambiente > 0 THEN PRDP.idDP + '-1,' ELSE '' END  +" +
							" CASE WHEN RDP.Refrigeracion > 0 THEN PRDP.idDP + '-2,' ELSE '' END + '' + CASE WHEN RDP.Congelacion > 0 THEN PRDP.idDP + '-3,'ELSE '' END + '' +" +
							" CAST((CASE WHEN RDP.SinManejo > 0 THEN PRDP.idDP + '-4,' ELSE '' END ) AS VARCHAR (16)) AS REFERENCIA, '' AS MEDIO, '' QUIENCOMPRA," +
							" '' AS COMENTARIOS, 0 AS TT, '' AS CONFORME, '' AS LoteTxt, '' AS TIPO," +
							" RDP.idEntrega AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = PEN.Docto" +
							" LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
							" LEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP" +
							" WHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'A surtir ruta' AND PF.fpor = :fpor  AND RDP.Factura=:factura " +
							" UNION" +
							" SELECT 'ASIGNAR MENSAJERO' AS ETIQUETA, PEN.FInicio, PEN.FFin, PENDP.Responsable AS CONTACTO, PEN.Responsable AS RESPONSABLE, PEN.Partida AS REFERENCIA, Ruta AS MEDIO," +
							" '' QUIENCOMPRA, '' AS COMENTARIOS, 0 AS TT, '' AS CONFORME," +
							" '' AS LoteTxt,'' AS TIPO, '' AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN (SELECT idRuta, Factura, FPor, Ruta, idDP FROM RutaDP) AS RDP ON RDP.idRuta = PEN.Docto" +
							" LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = RDP.idDP " +
							" LEFT JOIN (SELECT Factura, FPor, CPedido, PPedido FROM PFacturas) AS PF ON PF.Factura = RDP.Factura AND PF.FPor = RDP.FPor" +
							" LEFT JOIN (SELECT Docto, Responsable FROM Pendiente WHERE Tipo LIKE 'A tramitar ruta') AS PENDP ON PENDP.Docto = RDP.idDP" +
							" WHERE PEN.FInicio IS NOT NULL AND Tipo = 'Ruta a trabajar' AND PRDP.Part = PF.PPedido AND PF.fpor = :fpor AND RDP.Factura=:factura" +
							" UNION" +
							" SELECT  'EJECUTAR RUTA' AS ETIQUETA,PEN.FInicio, PEN.FFin, '' AS CONTACTO, Responsable AS RESPONSABLE, 'REALIZADA' AS REFERENCIA, '' AS MEDIO," +
							" '' QUIENCOMPRA, '' AS COMENTARIOS, 0 AS TT, '' AS CONFORME," +
							" '' AS LoteTxt,'' AS TIPO, '' AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN (SELECT idEntrega, Factura, FPor, Ruta, idDP, RazonesEntrega FROM RutaDP) AS RDP ON RDP.idEntrega = PEN.Docto" +
							" LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = RDP.idDP" +
							" LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
							" WHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'A Ejecutar ruta' AND PF.fpor = :fpor AND RDP.Factura=:factura" +
							" UNION" +
							" SELECT 'EJECUTAR RUTA' AS ETIQUETA, PEN.FInicio, PEN.FFin, '' AS CONTACTO, Responsable AS RESPONSABLE, RDP.Entrega  AS REFERENCIA,''AS MEDIO," +
							" '' QUIENCOMPRA, CAST(RDP.RazonesEntrega AS VARCHAR(500)) AS COMENTARIOS, 0 AS TT, '' AS CONFORME," +
							" '' AS LoteTxt,'' AS TIPO,'' AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN (SELECT idRuta, Factura, FPor, Ruta, Entrega, idDP, RazonesEntrega FROM RutaDP) AS RDP ON RDP.idRuta = PEN.Docto" +
							" LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = RDP.idDP" +
							" LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
							" WHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'Ruta a ejecutar' AND PF.fpor = :fpor AND RDP.Factura= :factura " +
							" UNION" +
							" SELECT 'CERRAR RUTA' AS ETIQUETA,PEN.FInicio, PEN.FFin, '' AS CONTACTO, Responsable AS RESPONSABLE, FolioDoctos2 AS REFERENCIA, CASE WHEN RDP.EntregayRevision = 1 THEN 'SI' ELSE 'NO' END AS MEDIO, " +
							" '' QUIENCOMPRA, CAST(DAY(RDP.FERefacturacion) AS VARCHAR(2)) + CASE WHEN MONTH(RDP.FERefacturacion) > 9 THEN '/' ELSE '/0' END + " +
							" CAST(MONTH(RDP.FERefacturacion) AS VARCHAR (2)) + '/' + CAST(YEAR(RDP.FERefacturacion) AS VARCHAR(4))AS COMENTARIOS, 0 AS TT, " +
							" CASE WHEN SR.Factura IS NULL THEN 0 ELSE 1 END AS CONFORME, '' AS LoteTxt, '' AS TIPO," + 
							" FolioDoctos3 AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idEntrega = PEN.Docto OR RDP.idRuta = PEN.Docto" +
							" LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = RDP.idDP" +
							" LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
							" LEFT JOIN (SELECT * FROM SRefacturacion ) SR ON SR.Factura = RDP.Factura" +
							" WHERE PEN.Tipo = 'A cerrar ruta' AND PEN.Docto <> '' AND PF.fpor = :fpor AND RDP.Factura=:factura" +
							" ORDER BY FINICIO ASC";
			////logger.info(query);
			log.info(query);
			return super.jdbcTemplate.query(query,map, new ConsultaLineaTiempoResunenRowMapper());
			
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+ factura,"\nFpor: " + fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	

	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorRevision(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" SELECT TOP 1 'REVISION' AS ETIQUETA, COALESCE(PENrDP.FFin,PENrPR.FFIN) AS FENTREGA, PEN.FFin AS FPROGRAMACION, RPR.FFin AS FREVISION, RPR.FFin AS FCOBRO," +
							" PEN.Responsable AS RESPONSABLE, COALESCE(CAST(RPR.Descripcion AS VARCHAR(400)),CAST(RDP.ComentariosAdicionales AS VARCHAR(400))) AS COMENTARIOS," +
							" COALESCE(PENrDP.Responsable,PENrPR.Responsable) AS MENSAJERO, CASE WHEN (PEN.FFin IS NULL OR RPR.FFin IS NULL) THEN 'No Realizada' WHEN (PEN.FFin IS NOT NULL AND RPR.FFin IS NOT NULL) THEN 'Realizada' END AS REVISION, " +
							" CAST(RPR.RazonesEntrega AS VARCHAR(400)) AS COMENTARIOS_REV, NULL AS COBRO, NULL AS MONTO," +
							" RPR.DoctosCierre AS DOC_CIERRE, RPR.FolioDoctos AS DOCUMENT, RPR.FolioDoctos3 AS DOCUMENT1							 " +
							" FROM Pendiente AS PEN" +
							" LEFT JOIN(SELECT * FROM Facturas) AS FAC ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida" +
							" LEFT JOIN Remisiones as Re ON cast(Re.nota as varchar(max))= FAC.Factura" +
							" LEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON (FAC.Factura = RDP.Factura AND RDP.idCliente = FAC.Cliente) OR (RDP.Factura = Re.Factura AND Re.Cliente = RDP.idCliente)" +
							" LEFT JOIN(SELECT * FROM RutaPR ) AS RPR ON FAC.Factura = RPR.Factura AND RPR.idCliente = FAC.Cliente" +
					        " LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'A tramitar ruta') AS PENiET ON PENiET.Docto = RDP.idDP" +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Ruta a ejecutar') AS PENrDP ON PENrDP.Docto= RDP.idRuta " +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Ruta a ejecutar') AS PENrPR ON PENrPR.Docto = RPR.idRuta" +
							" LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente			" +				 
							" WHERE FAC.Factura= :factura and FAC.fpor=:fpor  AND FAC.DeSistema = 1 " +
							" AND  PEN.Tipo LIKE 'Programar revisión' AND(CLI.CPago LIKE '%dias%' OR CLI.CPago LIKE 'PAGO CONTRA ENTREGA' ) AND RDP.EntregayRevision=0" +
							" ORDER BY PEN.FFin, RPR.FFin DESC   " ;
			////logger.info(query);
			return super.jdbcTemplate.query(query,map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+ factura,"\nFpor: "+ fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroGral(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	"  SELECT TOP 1 'COBRO_GRAL' AS ETIQUETA, COALESCE(RPR.FFin,RDP.FFIN) AS FREVISION,PROGR.FFin AS FPROGRAMACION, CONVERT(DATETIME,NULL) AS FENTREGA, MONIT.FFin AS FCOBRO," +
							" NULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, NULL AS COBRO, NULL AS MONTO," +
							" NULL AS DOC_CIERRE,NULL AS DOCUMENT, NULL AS DOCUMENT1" +						 
							" FROM Facturas AS FAC	" +	
							" LEFT JOIN(SELECT * FROM Pendiente) AS PEN ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida" +											
							" LEFT JOIN(SELECT * FROM RutaPC ) AS RPC ON FAC.Factura = RPC.Factura AND RPC.idCliente = FAC.Cliente	" +
							" LEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON FAC.Factura = RDP.Factura AND RDP.idCliente = FAC.Cliente	" +
							" LEFT JOIN(SELECT * FROM RutaPR ) AS RPR ON FAC.Factura = RPR.Factura AND RPR.idCliente = FAC.Cliente		" +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS MONIT ON MONIT.Docto= FAC.Factura " +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Programar cobro') AS PROGR ON PROGR.Docto = FAC.Factura " +
							" LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente" +					 
							" WHERE FAC.Factura=:factura  AND FAC.fpor=:fpor AND FAC.DeSistema = 1 " +
							" AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' OR FAC.CPAGO='PAGO INMEDIATO')" +
							" ORDER BY PEN.FFin, RPR.FFin DESC " ;
			log.info(query);
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroProgramacion(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" SELECT TOP 1 'COBRO_PROGR' AS ETIQUETA, PROGR.FInicio AS FREVISION, PROGR.FFin AS FPROGRAMACION, PAG.FEPago AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO, " +
							" PROGR.Responsable AS RESPONSABLE, CAST(RPC.Descripcion AS Varchar(60)) AS COMENTARIOS, RUTEJ.Responsable AS MENSAJERO, " +
							" NULL AS REVISION,NULL AS COMENTARIOS_REV, PAG.Moneda AS COBRO, PAG.Monto AS MONTO, PAG.Medio AS DOC_CIERRE, COALESCE(FAC.TCambio,PAG.TipoCambio) AS DOCUMENT, NULL AS DOCUMENT1 " +
							" FROM Facturas AS FAC " +
							" LEFT JOIN(SELECT * FROM Pendiente) AS PEN ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida	" +
							" LEFT JOIN(SELECT * FROM RutaPC ) AS RPC ON FAC.Factura = RPC.Factura AND RPC.idCliente = FAC.Cliente" +
							" LEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON FAC.Factura = RDP.Factura AND RDP.idCliente = FAC.Cliente" +
							" LEFT JOIN(SELECT * FROM RutaPR ) AS RPR ON FAC.Factura = RPR.Factura AND RPR.idCliente = FAC.Cliente" +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS MONIT ON MONIT.Docto= FAC.Factura " +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Programar cobro') AS PROGR ON PROGR.Docto = FAC.Factura " +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Ruta a ejecutar') AS RUTEJ ON RUTEJ.Docto = RPC.idRuta" +
							" LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente" +
							" LEFT JOIN(SELECT * FROM PagoPendiente) AS PAG ON PAG.Factura=FAC.Factura AND PAG.QuienFactura=FAC.FPor " +
							" WHERE FAC.Factura=:factura AND FAC.fpor=:fpor  AND FAC.DeSistema = 1 " +
							" AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' OR FAC.CPago='PAGO INMEDIATO')	" +
							" ORDER BY PEN.FFin, RPC.FFin DESC  " ;	
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroEjecucion(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" SELECT TOP 1 'COBRO_EJECU' AS ETIQUETA,RTRAB.FInicio AS FREVISION, RPC.FFin AS FPROGRAMACION, CONVERT(DATETIME,NULL) AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO," +
							" CASE  WHEN MONIT.FFin IS NULL THEN 'No Realizada' ELSE 'Realizada' END AS RESPONSABLE, CAST(RPC.Descripcion AS Varchar(60)) AS COMENTARIOS, RTRAB.Responsable AS MENSAJERO, " +
							" NULL AS REVISION,NULL AS COMENTARIOS_REV, NULL AS COBRO, NULL AS MONTO, CAST(RPC.DoctosCierre AS VARCHAR(20)) AS DOC_CIERRE, RPC.FolioDoctos AS DOCUMENT, NULL AS DOCUMENT1		" +					 
							" FROM Facturas AS FAC" +
							" LEFT JOIN(SELECT * FROM Pendiente) AS PEN ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida					" +			
							" LEFT JOIN(SELECT * FROM RutaPC ) AS RPC ON FAC.Factura = RPC.Factura AND RPC.idCliente = FAC.Cliente	" +
							" LEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON FAC.Factura = RDP.Factura AND RDP.idCliente = FAC.Cliente	" +
							" LEFT JOIN(SELECT * FROM RutaPR ) AS RPR ON FAC.Factura = RPR.Factura AND RPR.idCliente = FAC.Cliente		" +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Ruta a trabajar') AS RTRAB ON RTRAB.Docto= RPC.idRuta " +
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS MONIT ON MONIT.Docto= FAC.Factura " +
							" LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente		" +					 
							" WHERE FAC.Factura=:factura  AND FAC.fpor=:fpor AND FAC.DeSistema = 1 " +
							" AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' OR FAC.CPago='PAGO INMEDIATO')" +
							" ORDER BY RPC.FFin DESC";
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroMonitoreo(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" SELECT  'COBRO_MONITOREO' AS ETIQUETA, FAC.idFactura, PEN.FInicio AS FREVISION, PEN.FFin AS FPROGRAMACION, CONVERT(DATETIME,NULL) AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO," +	
							" NULL AS RESPONSABLE, CO.MonedaPagada AS COMENTARIOS, NULL AS MENSAJERO," +	
							" NULL AS REVISION,NULL AS COMENTARIOS_REV, CO.Moroso AS COBRO, CO.MontoPagado AS MONTO, NULL AS DOC_CIERRE, COALESCE(COPRE2.FolioPC,COPRE.FolioPC) AS DOCUMENT, NULL AS DOCUMENT1, CO.Folio AS FolioComplementoPago " +						 
							" FROM Facturas AS FAC" +	
							" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida	" +								
							" LEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON FAC.Factura = RPC.Factura AND RPC.idCliente = FAC.Cliente " +	
							" LEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = FAC.Factura AND CO.FPor=FAC.FPor " +	
							" LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente	" +					 
							" LEFT JOIN( SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.CPedido=FAC.CPedido 	" +	
							" LEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio 	" +	
							" LEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=FAC.Factura AND COPRE2.FPor=FAC.FPor 	 " +
							" WHERE FAC.Factura=:factura AND FAC.fpor=:fpor  AND FAC.DeSistema = 1 " +
							" AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' OR FAC.CPago='PAGO INMEDIATO')" +	
							" ORDER BY RPC.FFin DESC";
//			log.info (query);
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroHistorial(String factura, String fpor) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" SELECT 'COBRO_HISTORIAL' AS ETIQUETA,HIST.FechayHora AS FREVISION, HIST.FEP AS FPROGRAMACION, CONVERT(DATETIME,NULL) AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO," +
							" HIST.Usuario AS RESPONSABLE, HIST.Comentarios AS COMENTARIOS, HIST.Contacto AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, NULL AS COBRO, " +
							" NULL AS MONTO, NULL AS DOC_CIERRE, NULL AS DOCUMENT, NULL AS DOCUMENT1	" +
							" FROM historialFactura AS HIST" +
							" LEFT JOIN(SELECT Factura,CPedido,FPor, DeSistema FROM Facturas) AS FAC ON FAC.Factura = HIST.Factura AND FAC.FPor=HIST.FPor" +
							" WHERE FAC.Factura=:factura AND FAC.fpor=:fpor  AND FAC.DeSistema = 1 " +
							" ORDER BY FREVISION DESC";
			////logger.info(query);
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroSolicitud(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" SELECT TOP 1 'SOLICITUD' ETIQUETA,PEN.FInicio AS FREVISION,RPC.Fecha AS FPROGRAMACION,CONVERT(DATETIME,NULL) AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO, " +
							" HIS.Contacto RESPONSABLE,HIS.Comentarios COMENTARIOS,NULL AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, NULL AS COBRO,  " +
							" NULL AS MONTO, NULL AS DOC_CIERRE, NULL AS DOCUMENT, NULL AS DOCUMENT1	 " +
							" FROM Facturas AS FAC " +
							" LEFT JOIN (SELECT * FROM RutaPC) AS RPC ON RPC.Factura=FAC.Factura AND RPC.FPor=FAC.FPor " +
							" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A concluir planeacion') AS PEN ON PEN.Docto=RPC.idPC " +
							" LEFT JOIN (SELECT * FROM historialFactura) AS HIS ON HIS.Factura=FAC.Factura AND HIS.FPor=FAC.FPor " +
							" WHERE FAC.Factura=:factura AND FAC.fpor=:fpor AND FAC.DeSistema = 1 " +
							" ORDER BY FechayHora DESC ";
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroSC(String factura, String fpor) throws ProquifaNetException {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query = 	" \nSELECT 'GENERALES' AS ETIQUETA,PET.FInicio AS FREVISION, FAC.Fecha AS FPROGRAMACION,CONVERT(DATETIME,NULL) AS FENTREGA,PECO.FFin AS FCOBRO, " +
							" \nNULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, NULL AS COBRO,  " +
							" \nNULL AS MONTO, NULL AS DOC_CIERRE, NULL AS DOCUMENT, NULL AS DOCUMENT1	 " +
							" \nFROM Facturas AS FAC " +
							" \nLEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido " +
							" \nLEFT JOIN (SELECT * FROM DoctosR) AS DOC ON DOC.Folio = PED.DoctoR " +
							" \nLEFT JOIN (SELECT MAX(FInicio) AS FInicio, Docto FROM Pendiente WHERE Tipo='Pedido por tramitar' GROUP BY Docto) AS PET ON PET.Docto=DOC.Folio  " +
							" \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PECO ON PECO.Docto = FAC.CPedido " +
							" \nWHERE FAC.Factura=:factura AND FAC.fpor=:fpor  AND FAC.DeSistema = 1 " +
							" \nUNION " +
							" \nSELECT 'GESTION_PSC' AS ETIQUETA,PET.FInicio AS FREVISION, PET.FFin AS FPROGRAMACION,INCO.FechaPago AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO, " +
							" \nNULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, INCO.TipoCambio AS COBRO,  " +
							" \nINCO.Monto AS MONTO, INCO.Moneda AS DOC_CIERRE, INCO.MedioPago AS DOCUMENT, NULL AS DOCUMENT1 " +
							" \nFROM Facturas AS FAC " +
							" \nLEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido " +
							" \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=PED.DoctoR " +
							" \nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCO ON INCO.Docto=PED.DoctoR " +
							" \nWHERE FAC.Factura=:factura  AND FAC.fpor=:fpor  AND FAC.DeSistema = 1 " + 
							" \nUNION " +
							" \nSELECT 'MONITOREO_CO_SC' AS ETIQUETA,PGP.FInicio AS FREVISION, PGP.FFin AS FPROGRAMACION,COALESCE (CO.Fecha,INCO.FechaPago) AS FENTREGA,CO.FPago AS FCOBRO, " +
							" \nNULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, PEDP.Pago AS REVISION,NULL AS COMENTARIOS_REV, CO.TCPagado AS COBRO,  " +
							" \nCO.MontoPagado AS MONTO, CO.FolioPC AS DOC_CIERRE, CO.MonedaPagada AS DOCUMENT, CASE WHEN DATEDIFF(DAY,CO.FPago,CO.Fecha)>0 THEN 'NO' ELSE 'SI' END  AS DOCUMENT1 " +
							" \nFROM Facturas AS FAC " +
							" \nLEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido " +
							" \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Pago pendiente') AS PGP ON PGP.Docto=PED.DoctoR " +
							" \nLEFT JOIN (SELECT * FROM PedidoPago) AS PEDP ON PEDP.CPedido = FAC.CPedido " +
							" \nLEFT JOIN (SELECT * FROM Cobro) AS CO ON CO.idPedidoPago = PEDP.Folio " +
							" \nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCO ON INCO.Docto=PED.DoctoR " + 
							" \nWHERE FAC.Factura=:factura AND FAC.fpor=:fpor AND FAC.DeSistema = 1 ";
			
			//logger.info(query);
			return super.jdbcTemplate.query(query, map, new ConsultaInspectorCobrosRowMapper());		
		}catch(Exception e){
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e,"\nFactura: "+factura,"\nFpor: "+fpor);
			return new ArrayList<TiempoProceso>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<ResumenConsulta> findComparativasDPeriodos(String condiciones)
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			String query = "SELECT COUNT( DISTINCT(F.Factura)) AS FOLIOS,SUM(PFAC.PIEZAS) AS TOTAL,'0' AS PARTIDAS,'0' AS CP,'No Aplica' AS Nombre,'Comparativas' AS Resultados," +
					"SUM(CASE WHEN F.CPago = 'PREPAGO 100%'AND INCONS.Monto <> NULL AND INCONS.Monto<>0 THEN (CASE  WHEN INCONS.Moneda='M.N.' THEN INCONS.Monto/INCONS.TipoCambio ELSE INCONS.Monto END) ELSE " +
					"(CASE WHEN F.Moneda='EUR' OR F.Moneda='Euros' THEN F.Importe*MON.EDolar WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' THEN F.Importe/MON.PDolar ELSE F.Importe END) END) AS MONTO " +
					"FROM Facturas AS F " +
					"LEFT JOIN (SELECT * FROM Monedas) AS MON ON CAST(MON.Fecha AS DATE)=CAST(F.Fecha AS DATE) " +
					"LEFT JOIN (SELECT inc.Monto,inc.Moneda,inc.TipoCambio,p.CPedido,inc.FechaPago FROM Pedidos AS p,InconsistenciaDePedido AS inc " +
					"WHERE inc.MedioPago IS NOT NULL AND inc.Docto=p.DoctoR) AS INCONS ON INCONS.CPedido = F.CPedido " +
					"LEFT JOIN (SELECT SUM(Cant)AS PIEZAS,Factura,FPor FROM PFacturas GROUP BY Factura,FPor)AS PFAC ON PFAC.Factura=F.Factura AND PFAC.FPor=F.FPor " +
					"LEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +
					"WHERE F.FPor <>'ProquifaServicios' " +
					condiciones;
			return super.jdbcTemplate.query(query, map, new ResumenConsultaRowMapper());	
		} catch (Exception e) {
			log.info(e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e, "\nCondiciones: "+condiciones);
			return new ArrayList<ResumenConsulta>();
		}
	}
}