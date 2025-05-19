package com.proquifa.net.persistencia.consultas.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturacionDolarRowMapper;
import com.proquifa.net.persistencia.consultas.ConsultaFacturacionDAO;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaFacturacionResumenFacturaRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaFacturacionResumenRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaFacturacionRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaInspectorCobrosRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaLineaTiempoResunenRowMapper;

@Repository
public class ConsultaFacturacionDAOImpl extends DataBaseDAO implements ConsultaFacturacionDAO {

	Funcion funcion = new Funcion();
	
	final Logger log = LoggerFactory.getLogger(ConsultaFacturacionDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Facturacion> findConsultaFacturacion(String condiciones)
			throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("condiciones", condiciones);
		try{
			String query = 
					"\n SELECT DISTINCT F.Fecha, F.Factura, F.FPor, F.Tipo, F.Medio, F.Importe, F.Moneda, F.Estado, PF.CPedido, F.IVA, F.Cliente, CLI.Nombre, " +
					"\n CASE WHEN RR.CUANTOS IS NULL THEN 'NO' ELSE 'SI' END AS CUANTOS," +
					"\n COALESCE(CASE WHEN F.Moneda = 'Pesos' OR F.Moneda = 'M.N.' THEN F.Importe / CASE WHEN F.TCambio = 0 THEN MON.PDolar ELSE F.TCambio END" +
					"\n  WHEN F.Moneda = 'Euros' OR F.Moneda = 'EUR' THEN F.Importe * MON.EDolar ELSE F.Importe END, F.IMPORTE) AS MONTODLL, coalesce(CLI.Vendedor,'NO ASIGNADO') Vendedor, coalesce(EMP.USUARIO, 'NO ASIGNADO') Cobrador" +
					"\n FROM (SELECT * FROM Facturas WHERE Medio IS NOT NULL) AS F" +
					"\n LEFT JOIN (SELECT Factura, FPor, MAX(CPedido) AS CPEDIDO, MIN(PPedido) AS PPEDIDO " +
					"\n	FROM PFacturas WHERE CPedido IS NOT NULL GROUP BY Factura, FPor) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\n LEFT JOIN (SELECT CPedido, PPedido, Compra FROM PCompras) AS PC ON PC.CPedido = PF.CPedido AND PC.PPedido = PF.PPEDIDO" +
					"\n LEFT JOIN (SELECT Clave, Nombre, Cobrador, Vendedor FROM Clientes) AS CLI ON CLI.Clave = F.Cliente" +
					" \n LEFT JOIN Empleados emp ON emp.Clave = CLI.Cobrador " +
					"\n LEFT JOIN (SELECT FacturaAntigua, COUNT(*) AS CUANTOS FROM RegistroRefacturacion GROUP BY FacturaAntigua) AS RR ON RR.FacturaAntigua = F.Factura" +
					"\n LEFT JOIN (SELECT Fecha, EDolar, PDolar FROM Monedas) AS MON ON MON.Fecha = F.Fecha " + condiciones +
					"\n ORDER by F.Factura desc, F.FPor, CLI.Nombre";
			log.info(query);
			return super.jdbcTemplate.query(query,map, new ConsultaFacturacionRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Facturacion> consultaAvanzadaFacturacion(String factura,
			String cPedido, String uuid, Date finicio, Date ffin, String cliente,
			String estado, String refacturada, String facturo, String tipo,
			String medio, String cPago, String consulta, String condiciones) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("cPedido", cPedido);
		map.put("uuid", uuid);
		map.put("finicio", finicio);
		map.put("ffin", ffin);
		map.put("cliente", cliente);
		map.put("estado", estado);
		map.put("refacturada", refacturada);
		map.put("facturo", facturo);
		map.put("tipo", tipo);
		map.put("medio", medio);
		map.put("cPago", cPago);
		map.put("consulta", consulta);
		try {

			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append(" \n SELECT DISTINCT(f.Factura), f.fecha, f.FPor, COALESCE(f.Tipo,'Normal') AS Tipo, ");
			sbQuery.append(" \n COALESCE(f.Medio,'Fisica') AS Medio, c.MedioPago as MedioPago, f.CPedido, c.Nombre AS Nombre, ");
			sbQuery.append(" \n (Case when f.Moneda = 'M.N.' OR f.Moneda = 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe / m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe / f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) AS Importe, ");
			sbQuery.append(" \n ROUND((Case when f.Moneda = 'M.N.' OR f.Moneda = 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe / m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe / f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) * f.IVA,2) AS totalIVA, ");
			sbQuery.append(" \n ROUND((Case when f.Moneda = 'M.N.' OR f.Moneda = 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe / m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe / f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) + ");
			sbQuery.append(" \n ((Case when f.Moneda = 'M.N.' OR f.Moneda = 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe / m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe / f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) * f.IVA),2) AS Total, ");
			sbQuery.append(" \n (Case when f.Moneda <> 'M.N.' AND f.Moneda <> 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe * m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe * f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) AS ImporteMN, ");
			sbQuery.append(" \n ROUND((Case when f.Moneda <> 'M.N.' AND f.Moneda <> 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe * m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe * f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) * f.IVA,2) AS totalIVAMN, ");
			sbQuery.append(" \n ROUND((Case when f.Moneda <> 'M.N.' AND f.Moneda <> 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe * m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe * f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) + ");
			sbQuery.append(" \n ((Case when f.Moneda <> 'M.N.' AND f.Moneda <> 'Pesos' THEN ( ");
			sbQuery.append(" \n Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN (ROUND((f.Importe * m.PDolar),2))  ");
			sbQuery.append(" \n ELSE (ROUND((f.Importe * f.TCambio),2)) END ) ELSE ROUND(f.Importe,2) END) * f.IVA),2) AS TotalMN, ");
			sbQuery.append(" \n f.Estado, (Case when sr.Factura IS NULL THEN 'NO' ELSE 'SI' END) AS Refacturada, f.CPago, ");
			
			sbQuery.append(" \n CASE WHEN f.Estado='Cobrada' AND CBANCO.NoCuenta IS NOT NULL THEN SUBSTRING((REPLACE(CBANCO.NoCuenta ,'-','')), LEN(REPLACE(CBANCO.NoCuenta ,'-',''))-3,4) ");
			sbQuery.append(" \n WHEN f.Estado='Por Cobrar' THEN 'Pendiente' ELSE 'ND' END CuentaBanco, ");
			
			sbQuery.append(" \n COALESCE(pp.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) as fep, ");
			sbQuery.append(" \n DATEDIFF(DAY, GETDATE(),COALESCE(pp.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END)) AS DRC, ");
			sbQuery.append(" \n (Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN m.PDolar ELSE ROUND(f.TCambio,2) END) as TCambio, len(f.factura) as 'order',");
			sbQuery.append(" \n PF.Cant Part, convert(Date,C5.FPago) as FPago, C.RSocial, C.CURP,F.Moneda, COALESCE(c.Vendedor,'NO ASIGNADO') Vendedor, COALESCE( emp.Usuario,'NO ASIGNADO') Cobrador, ");
			sbQuery.append(" \n FACTCANCELADAS.FechaCancelacion,P1.Pedido AS Referencia,P1.DoctoR, f.uuid ");
			sbQuery.append(" \n FROM (SELECT * FROM Facturas WHERE Medio IS NOT NULL) f ");
			
			sbQuery.append(" \n LEFT JOIN CuentaBanco CBANCO ON f.FK01_idCuentaBanco = CBANCO.idCuenta ");
			   
			sbQuery.append(" \n LEFT JOIN Clientes c ON f.Cliente = c.Clave ");
			sbQuery.append(" \n LEFT JOIN Empleados emp ON emp.Clave = c.Cobrador ");
			sbQuery.append(" \n LEFT JOIN Monedas m on convert(varchar,m.Fecha,105) = CONVERT(varchar,f.fecha,105) ");
			sbQuery.append(" \n LEFT JOIN RegistroRefacturacion rr on f.Factura = rr.FacturaAntigua ");
			sbQuery.append(" \n LEFT JOIN SRefacturacion sr on f.Factura= sr.Factura AND f.FPor = sr.FPorOrigen ");
			sbQuery.append(" \n LEFT JOIN (select Max(pd.FEPago) FEPago, pd.Factura, pd.QuienFactura from PagoPendiente pd ");
			sbQuery.append(" \n  left join Facturas f on f.Factura = pd.Factura and f.FPor = pd.QuienFactura  ");
			sbQuery.append(condicionBusquedaFacturacion(factura, cPedido, null, null, null, null, null, facturo, null, null, null, true, uuid));
			sbQuery.append(" \n group by pd.Factura, pd.QuienFactura ) pp on f.Factura = pp.Factura and pp.QuienFactura = f.FPor ");
			sbQuery.append(" \n LEFT JOIN Pendiente p on p.Docto = f.CPedido ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM Pedidos) AS P1 ON P1.CPedido = F.CPedido ");
			sbQuery.append(" \n LEFT JOIN (SELECT PF.Factura, PF.FPor, SUM(PF.Cant) Cant FROM Facturas F ");
			sbQuery.append(" \n LEFT JOIN PFacturas PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor AND F.CPedido = PF.CPedido  where F.DeSistema = 1 ");
			sbQuery.append(" \n GROUP BY PF.Factura, PF.FPor) PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor ");
			sbQuery.append(" \n LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P1.DoctoR ");
			sbQuery.append(" \n LEFT JOIN (SELECT TOP 1 * FROM PedidoPago) pag ON pag.CPedido = F.CPedido ");
			sbQuery.append(" \n LEFT JOIN Cobro C5 ON (C5.Factura = F.Factura AND C5.FPor = F.FPor) OR C5.idPedidoPago = pag.Folio "); 
			sbQuery.append(" \n LEFT JOIN (Select MAX(FFin) AS FechaCancelacion,Docto,Partida from Pendiente where Tipo='Amparar Cancelación'  group by Docto,Partida) as FACTCANCELADAS ON FACTCANCELADAS.Docto=f.Factura AND FACTCANCELADAS.Partida=F.FPor AND F.Estado='Cancelada' "); 

			sbQuery.append(condicionBusquedaFacturacion(factura, cPedido, finicio, ffin, cliente, estado, refacturada, facturo, tipo, medio, cPago, true, uuid));
			sbQuery.append(condiciones);

			sbQuery.append(" ORDER BY LEN(f.Factura),f.Factura \n");
			log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(),map, new FacturacionDolarRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findLineaTiempoResumen(String factura, String fpor) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			funcion = new Funcion();
			String query = 	
				"\n  SELECT top 1 LEN('4') No, '4' AS ID,'COBRO' AS ETIQUETA, COALESCE(P5.FInicio,PEN.FInicio,PET.FInicio) AS FINICIO, COALESCE(PEN.FFin,PGP.FFin) AS FFIN, " +
				"\n NULL AS CONTACTO, COALESCE(P5.Responsable, PEN.Responsable,PET.Responsable,PGP.Responsable) AS RESPONSABLE, CASE WHEN FAC.CPago LIKE '%100%' OR FAC.CPago LIKE '%50%' THEN '1' ELSE '0' END AS REFERENCIA, " +
				"\n  NULL AS MEDIO,COALESCE(PAGOP.Medio, INCONS.MedioPago, CASE WHEN FAC.CPago = 'PREPAGO 100%' THEN FAC.TPago END, 'Pendiente') COLLATE SQL_Latin1_General_CP1_CI_AS AS COMENTARIOS,NULL AS QUIENCOMPRA,NULL AS LoteTxt, " +
				funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEN.FInicio,PET.FInicio)", "COALESCE(PEN.FFin,PGP.FFin)", "Dias") + "AS TT, NULL AS CONFORME, NULL AS TIPO, NULL AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL FIPortal, NULL FFPortal, NULL AS FolioCompPago " +
				"\n  FROM Facturas AS FAC " +
				"\n LEFT JOIN Pendiente P5 ON P5.Docto = Fac.Factura AND P5.Partida = FAC.FPor AND P5.Tipo = 'Programar cobro' " +
				"\n  LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN ON PEN.Docto = FAC.Factura  AND PEN.Partida = FAC.FPor " +
				"\n  LEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = FAC.Factura AND PAGOP.QuienFactura = FAC.FPor " +
				"\n  LEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = FAC.Factura AND RE.FPor = FAC.FPor " +
				" \n LEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = FAC.CPedido OR P.CPedido = RE.CPedido " +
				"\n  LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR  " +
				"\n  LEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido " +
				"\n  LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=PED.DoctoR " +
				"\n  LEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,Docto FROM Pendiente WHERE Tipo='Pago pendiente' GROUP BY Docto) AS PGP1 ON PGP1.Docto=PED.DoctoR " + 
				"\n  LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Pago pendiente') AS PGP ON PGP.Folio=PGP1.FOLIO " +
				"\n  WHERE (P5.FInicio IS NoT NULL OR PEN.FInicio IS NOT NULL OR PET.FInicio IS NOT NULL) AND FAC.Factura= :factura AND FAC.fpor= :fpor AND FAC.DeSistema = 1" +
				" \n UNION"+
				"\n  SELECT TOP 1 LEN('3'), '3' AS ID,'REVISION' AS ETIQUETA, PEN.FInicio AS FINICIO, RPR.FFin AS FFIN,NULL AS CONTACTO, PEN.Responsable AS RESPONSABLE," +
				"\n  NULL AS REFERENCIA, NULL AS MEDIO,NULL AS COMENTARIOS,NULL AS QUIENCOMPRA,NULL AS LoteTxt," +
				funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "RPR.FFin", "Dias") +" AS TT, NULL AS CONFORME, NULL AS TIPO,NULL AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL, NULL, NULL AS FolioCompPago " +			
				"\n  FROM Pendiente AS PEN" +
				"\n  LEFT JOIN(SELECT * FROM Facturas) AS FAC ON FAC.Factura = PEN.Docto AND FAC.FPor = PEN.Partida " +
				"\n  LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente" +
				"\n  LEFT JOIN Remisiones as Re ON cast(Re.nota as varchar(max))= FAC.Factura " +
				"\n  LEFT JOIN(SELECT * FROM RutaPR WHERE Entrega LIKE 'Realizada') AS RPR ON RPR.Factura = Fac.Factura AND FAC.Cliente = RPR.idCliente " +
				"\n  LEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON (FAC.Factura = RDP.Factura AND RDP.idCliente = FAC.Cliente) OR (RDP.Factura = Re.Factura AND Re.Cliente = RDP.idCliente)" +
				"\n  WHERE  FAC.Factura= :factura AND FAC.fpor= :fpor  AND FAC.DeSistema = 1 AND PEN.Tipo LIKE 'Programar revisión'AND(CLI.CPago LIKE '%dias%' OR CLI.CPago LIKE 'PAGO CONTRA ENTREGA' ) AND RDP.EntregayRevision=0 " +
				"\n  UNION"+
				"\n SELECT TOP 1 LEN('2'), '2' AS ID,CASE WHEN COALESCE(EA.Guia, FACFE.Guia) IS NULL THEN 'ENTREGA' ELSE 'ENTREGA' END  AS ETIQUETA,COALESCE(PENTRA3.FInicio,PENTRA.FInicio, PENTRA2.FInicio, PENENV.FInicio,PENTRA4.FInicio) AS FINICIO, " +
				"\n  COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN,PENTRA4.FFin) AS FFIN,  COALESCE(EA.Contacto, P.CONTACTO) AS CONTACTO, " +
				"\n  COALESCE(PEN.Responsable,PENENV.Responsable, PENEJE.Responsable,PENTRA4.Responsable) AS RESPONSABLE,  COALESCE(GF.Guia,EA.Guia, FACFE.Guia) AS REFERENCIA, " +
				"\n  COALESCE(CE.Mensajeria,EA.Mensajeria) AS Medio,  CAST(RDP.ComentariosAdicionales AS VARCHAR(500)) AS COMENTARIOS,'' AS QUIENCOMPRA,  '' AS LoteTxt," +
				funcion.obtenerTiempoTranscurridoSQL("COALESCE(PENTRA3.FInicio,PENTRA.FInicio, PENTRA2.FInicio, PENENV.FInicio,PENTRA4.FInicio)", "COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN,PENTRA4.FFin)", "Dias") +
				"\n  AS TT, RDP.Conforme AS CONFORME,'' AS TIPO, COALESCE(EA.idGuia, FACFE.Guia, RDP.FolioDoctos) AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL, NULL, NULL AS FolioCompPago " +							 
				"\n  FROM PPedidos AS PP" +
				"\n  LEFT JOIN (SELECT Factura, FPor, CPedido, PPedido, Part FROM PFacturas WHERE Estado IS NULL) AS PF ON PF.PPedido = PP.Part AND PF.CPedido = PP.CPedido" +
				" \n LEFT JOIN (SELECT Factura, FPor, CPedido, PPedido, Part FROM PFacturas WHERE Estado = 'Cancelada') AS PF2	ON PF2.PPedido = PP.Part AND PF2.CPedido = PP.CPedido" +
				"\n  LEFT JOIN (SELECT Factura, FPor, idFactura,CPedido,DeSistema FROM Facturas) AS F ON F.Factura = PF.Factura AND F.FPor = PF.FPor" +
				" \n LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP	ON (PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part) OR (PRDP.Factura = PF2.Factura AND PRDP.FPor = PF2.FPor AND PRDP.Part = PF2.Part)" +
				"\n  LEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP" +
				"\n  LEFT JOIN (SELECT * FROM RutaPR ) AS RPR ON RPR.Factura = PF.Factura AND RPR.FPor = PF.FPor" +
				"\n  LEFT JOIN Remisiones Re ON cast(Re.nota as varchar(max))= PF.Factura" +
				"\n  LEFT JOIN (SELECT * FROM RutaDP) as PRDPREM ON PRDPREM.Factura = Re.Factura AND PRDPREM.FPor = Re.FPor" +
				"\n  LEFT JOIN (SELECT DISTINCT FInicio, Docto FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' AND Partida = '' ) AS PENTRA	ON PENTRA.Docto = PRDP.idDP" +
				" \n LEFT JOIN (SELECT DISTINCT FInicio, Docto FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' AND Partida <> '' ) AS PENTRA3 ON PENTRA3.Docto = PRDP.idDP" +
				" \n LEFT JOIN (SELECT DISTINCT FInicio, Docto FROM Pendiente WHERE Tipo = 'Ruta a planear') AS PENTRA2 ON PENTRA2.Docto = RPR.idRuta" +
				"\n  LEFT JOIN (SELECT DISTINCT FInicio,FFin, Docto, Responsable FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' ) AS PENTRA4 ON PENTRA4.Docto = PRDPREM.idDP" + 
				" \n LEFT JOIN (SELECT DISTINCT FInicio, FFin, Docto, Partida, Responsable FROM Pendiente WHERE Tipo = 'Alistar envío internacional') AS PENENV	ON (PENENV.Docto = F.idFactura OR (PENENV.Docto = F.Factura AND PENENV.Partida = F.FPor)) " +
				" \n LEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA ON PENRUTA.Docto = RDP.idRuta" +
				"  \nLEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA2 ON PENRUTA2.Docto = RPR.idRuta" +
				" \n LEFT JOIN (SELECT DISTINCT FFin, Docto, Responsable FROM Pendiente WHERE Tipo = 'A Ejecutar ruta') AS PENEJE	ON PENEJE.Docto = RDP.idEntrega" +
				" \n LEFT JOIN (SELECT DISTINCT Docto, Responsable FROM Pendiente WHERE Tipo = 'Ruta a trabajar') AS PEN	ON PEN.Docto = RDP.idRuta" +
				"\n  LEFT JOIN (SELECT * FROM FACTURAFOLIOENTREGA) AS FACFE ON (FACFE.Factura = PRDP.Factura AND FACFE.Fpor = PRDP.FPor) OR (FACFE.Factura = F.idFactura)" +
				"\n  LEFT JOIN (SELECT * FROM EnvioAlmacen) AS EA	ON (EA.idEntrega = RDP.idEntrega AND PENENV.FInicio IS NULL) OR (EA.idGuia = FACFE.Guia AND PENENV.FInicio IS NOT NULL)" +
				"\n  LEFT JOIN (SELECT * FROM GuiaFactura ) AS GF	ON GF.DoctoGuia = FACFE.Guia" +
				"\n  LEFT JOIN (SELECT DISTINCT CPedido, Mensajeria FROM CriterioEnvio) AS CE	ON CE.CPedido = PP.CPedido" +
				" \n LEFT JOIN (SELECT CPedido, Contacto FROM Pedidos) AS P ON P.CPedido = PP.CPedido" +
				" \n WHERE COALESCE(PENTRA.FInicio, PENENV.FInicio,Re.Fecha) IS NOT NULL AND F.Factura= :factura AND F.fpor= :fpor AND F.DeSistema = 1" +
				" \n \nUNION"+
				" \n SELECT * FROM (SELECT TOP 1 LEN('1') No, '1' AS ID,'FACTURACION' AS ETIQUETA,PEN.FInicio,COALESCE(PEN.FFIN,PEA.FFIN,FAC.Fecha) FFin, PN.Responsable AS CONTACTO,PEN.Responsable AS RESPONSABLE, FAC.Factura AS REFERENCIA,  FAC.Medio COLLATE Modern_Spanish_CI_AS AS MEDIO," +
				"\n  COALESCE(CAST(PF.Nota AS VARCHAR(400)), CAST(R.Nota AS VARCHAR(400)))  AS COMENTARIOS, FAC.FPor AS QUIENCOMPRA,  R.LOTE AS LoteTxt," +
				funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "COALESCE(PEN.FFIN,PEA.FFIN,FAC.Fecha)", "Dias") + "  AS TT,'' AS  CONFORME,FAC.Tipo AS TIPO, FAC.ComprobantePortal COLLATE Modern_Spanish_CI_AS AS DOCUMENT, CONVERT(DATE,null) FECHACOBRO, NULL AS MONTO, ROUND(fac.TCambio,2) as TCAMBIO, PN.FInicio FIPortal, PN.FFin FFPortal, NULL AS FolioCompPago " +
				" \n FROM Facturas AS FAC	" +
				"\n  LEFT JOIN( SELECT * FROM Pendiente)AS PEN ON FAC.CPedido = PEN.Docto		" +
				"\n  LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido = PEN.Docto AND PF.Fpor=FAC.Fpor AND PF.Factura=FAC.Factura" +
				" \n LEFT JOIN (SELECT * FROM PRemisiones) AS PR ON PR.CPedido = PEN.Docto " +
				"\n  LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=FAC.CPedido  " +
				"\n  LEFT JOIN (SELECT *, 'Fisica' AS Medio, 'Normal' AS TIPO , 'REMISION' AS LOTE FROM Remisiones) AS R ON R.Factura = PR.Factura AND R.FPor = PR.FPor		 " +
				" \n LEFT JOIN Pendiente PN ON PN.Docto = FAC.idFactura AND PN.Tipo = 'SubirFacturaPortal' " +
				" \n WHERE PEN.FInicio IS NOT NULL AND (PEN.Tipo = 'A facturar' OR PEN.Tipo = 'Facturar por adelantado')  AND FAC.DeSistema = 1" +
				" \n AND FAC.Factura = :factura  AND FAC.fpor= :fpor  AND FAC.CPago NOT LIKE 'Prepago%' ORDER BY FAC.Fecha DESC ) FAC "+
				" \nUNION" +
				" \n SELECT LEN('5'), '5' AS ID, 'COOBRADA' AS ETIQUETA, COALESCE(PEA.FInicio,F.Fecha) FInicio , CASE WHEN F.CPago LIKE '%100%' THEN PREP.FFin ELSE PEMON.FFin END FFin,F.FPOR CONTACTO,COALESCE(FECH.Responsable,PREP.Responsable) RESPONSABLE, " +
				"\n  F.Factura AS REFERECIA,convert(varchar,COALESCE(PEN.FFIN,PEA.FFin,F.FECHA),121) MEDIO, CASE WHEN DATEDIFF(DAY,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin), COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) )> 0 THEN 'ET' ELSE 'FT'END AS COMENTARIOS, " +
				"\n  CONVERT(VARCHAR,COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END),120) QUIENCOMPRA,COALESCE(CO.MonedaPagada,COPRE.MonedaPagada) LoteTxt,  " +
				funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEA.FInicio,F.Fecha)", "CASE WHEN F.CPago LIKE '%100%' THEN PREP.FFin ELSE PEMON.FFin END", "Dias") +" AS TT,CASE WHEN F.CPago LIKE '%100%' THEN 5 ELSE 8 END CONFORME,f.CPago TIPO, " +
				"\n  COALESCE(RPC.idPC,COPRE2.FolioPC,COPRE.FolioPC) DOCUMENT,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin)AS FECHACOBRO, COALESCE(CO.MontoPagado,COPRE.MontoPagado) MONTO, '' as TCAMBIO, NULL, NULL, CO.Folio AS FolioCompPago " +
				"\n  FROM Facturas AS F " +
				"\n  LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro')AS FECH ON F.Factura = FECH.Docto AND F.FPor=FECH.Partida		 " +
				" \n LEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON F.Factura = RPC.Factura AND RPC.idCliente = F.Cliente " +
				"\n  LEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = F.Factura AND CO.FPor=F.FPor		 " +
				"\n  LEFT JOIN( SELECT * FROM Pendiente WHERE FInicio IS NOT NULL AND (Tipo = 'A facturar' OR Tipo = 'Facturar por adelantado'))AS PEN ON F.CPedido = PEN.Docto		 " +
				" \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=F.CPedido	 " +
				" \n LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Facturar por adelantado') AS FADE ON FADE.Docto=F.CPedido   " +
				" \n LEFT JOIN( SELECT MAX(Folio)AS Folio,DOCTO FROM Pendiente WHERE Tipo='Cobro a validar' GROUP BY DOCTO) AS PREP1 ON PREP1.Docto=F.CPedido " + 
				" \n LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PREP ON PREP.Folio=PREP1.Folio " +
				" \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro') AS PEMON ON PEMON.Docto=F.Factura AND PEMON.Partida=F.FPor " +
				" \n LEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=F.Factura AND COPRE2.FPor=F.FPor " +
				" \n LEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,CPEDIDO FROM PedidoPago GROUP BY CPedido) AS PEPA1 ON PEPA1.CPedido = F.CPedido " + 
				" \n LEFT JOIN (SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.Folio = PEPA1.FOLIO  " +
				" \n LEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio " +
				" \n LEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +
				" \n LEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = F.Factura AND RE.FPor = F.FPor " +
				" \n LEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = F.CPedido OR P.CPedido = RE.CPedido " +
				" \n LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +
				" \n WHERE  F.Factura = :factura AND F.fpor= :fpor  AND F.DeSistema = 1" +
				" \n UNION" +
				" \n SELECT TOP 1 LEN('6'), '6' AS ID, 'COOBRADA_SC' AS ETIQUETA, COALESCE(FECH.FInicio,PET.FInicio) FInicio ,COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN,PREP.FFin) AS FFIN,F.FPOR CONTACTO,COALESCE(FECH.Responsable,PREP.Responsable) RESPONSABLE, " +
				" \n F.Factura AS REFERECIA,convert(varchar,PEA.FFin,121) MEDIO, CASE WHEN DATEDIFF(DAY,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin), COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END) )> 0 THEN 'ET' ELSE 'FT'END AS COMENTARIOS, " +
				" \n CONVERT(VARCHAR,COALESCE(PAGOP.FEPago, INCONS.FechaPago, CASE WHEN F.CPago = 'PREPAGO 100%' THEN F.Fecha ELSE NULL END),120) QUIENCOMPRA,COALESCE(CO.MonedaPagada,COPRE.MonedaPagada) LoteTxt,  " +
				funcion.obtenerTiempoTranscurridoSQL("COALESCE(FECH.FInicio,PET.FInicio)", "COALESCE(PENRUTA.FFIN,PENRUTA2.FFIN, PENEJE.FFIN, PENENV.FFIN)", "Dias") +" AS TT,CASE WHEN F.CPago LIKE '%100%' THEN 5 ELSE 8 END CONFORME,f.CPago TIPO, " +
				"\n  COALESCE(RPC.idPC,COPRE2.FolioPC,COPRE.FolioPC) DOCUMENT,COALESCE(CONVERT(DATE,COPRE.FPago),FECH.FFin,PREP.FFin)AS FECHACOBRO, COALESCE(CO.MontoPagado,COPRE.MontoPagado) MONTO, '' as TCAMBIO, NULL, NULL, CO.Folio AS FolioCompPago " +
				"\n  FROM Facturas AS F " +
				" \n LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro')AS FECH ON F.Factura = FECH.Docto AND F.FPor=FECH.Partida		 " +
				" \n LEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON F.Factura = RPC.Factura AND RPC.idCliente = F.Cliente " +
				" \n LEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = F.Factura AND CO.FPor=F.FPor		 " +
				" \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar') AS PEA ON PEA.Docto=F.CPedido	 " +
				"\n  LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Facturar por adelantado') AS FADE ON FADE.Docto=F.CPedido   " +
				"\n  LEFT JOIN( SELECT MAX(Folio)AS Folio,DOCTO FROM Pendiente WHERE Tipo='Cobro a validar' GROUP BY DOCTO) AS PREP1 ON PREP1.Docto=F.CPedido " + 
				"\n  LEFT JOIN( SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PREP ON PREP.Folio=PREP1.Folio " +
				"\n  LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Monitorear cobro') AS PEMON ON PEMON.Docto=F.Factura AND PEMON.Partida=F.FPor " +
				" \n LEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=F.Factura AND COPRE2.FPor=F.FPor " +
				"\n  LEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,CPEDIDO FROM PedidoPago GROUP BY CPedido) AS PEPA1 ON PEPA1.CPedido = F.CPedido " + 
				"\n  LEFT JOIN (SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.Folio = PEPA1.FOLIO  " +
				"\n  LEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio " +
				"\n  LEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = F.Factura AND PAGOP.QuienFactura = F.FPor " +
				"\n  LEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = F.Factura AND RE.FPor = F.FPor " +
				" \n LEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = F.CPedido OR P.CPedido = RE.CPedido " +
				" \n LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +
				" \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=P.DoctoR  " +
				" \n LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP	ON (PRDP.Factura = F.Factura AND PRDP.FPor = F.FPor )  " +
				" \n LEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP " +
				"  \nLEFT JOIN (SELECT * FROM RutaPR ) AS RPR ON RPR.Factura = F.Factura AND RPR.FPor = F.FPor " +
				"  \nLEFT JOIN (SELECT DISTINCT FInicio, FFin, Docto, Partida, Responsable FROM Pendiente WHERE Tipo = 'Alistar envío internacional') AS PENENV	ON (PENENV.Docto = F.idFactura OR (PENENV.Docto = F.Factura AND PENENV.Partida = F.FPor))  " +
				" \n LEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA ON PENRUTA.Docto = RDP.idRuta " +
				" \n LEFT JOIN (SELECT DISTINCT FFin, Docto FROM Pendiente WHERE Tipo = 'Ruta a ejecutar') AS PENRUTA2 ON PENRUTA2.Docto = RPR.idRuta " +
				" \n LEFT JOIN (SELECT DISTINCT FFin, Docto, Responsable FROM Pendiente WHERE Tipo = 'A Ejecutar ruta') AS PENEJE	ON PENEJE.Docto = RDP.idEntrega " +
				"\n WHERE  F.Factura = :factura  AND F.fpor= :fpor AND F.DeSistema = 1" +
				" \nUNION" + 
				" \nSELECT top 1 LEN('7'), '7' AS ID,'FACTURA REMISION' AS ETIQUETA, p.FInicio, p.FFin, '' as Contacto, p.Responsable, " +
				" \nf.Factura as REFERENCIA, '' AS MEDIO, '' as COMENTARIOS, coalesce(f.FPor,r.FPor,p.Partida) as QUIENCOMPRA, " +
				" \n'' AS LoteTxt, " + funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p.ffin", "Dias") + " AS TT," +
				" \n'' as CONFORME, f.Tipo, '' AS Document, NULL as FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL, NULL, NULL AS FolioCompPago " +
				" \nFrom Remisiones r" +
				" \nLEFT JOIN Facturas f on f.Factura = cast(r.Nota as varchar)" +
				"\n LEFT JOIN Pendiente p on p.Docto = r.Factura" +
				"\n WHERE p.Tipo = 'Facturar remisión' AND CAST(r.Nota as Varchar) = :factura  AND F.fpor= :fpor  AND F.DeSistema = 1" +
				"\n UNION " +
				"\n SELECT top 1 LEN('8'), '8' AS ID,'REFACTURACION' AS ETIQUETA, p.FInicio, p.FFin ,'' as Contacto, p.Responsable, f.Factura as REFERENCIA, " +  
				" \n'' AS MEDIO, cast(sr.ComentarioFac as varchar) as COMENTARIOS, coalesce(f.FPor,p.Partida) as QUIENCOMPRA, " +
				" \n'' AS LoteTxt, " + funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p.FFin", "Dias") + " AS TT, " +
				" \n'' as CONFORME, f.Tipo, '' AS Document, NULL as FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL, NULL, NULL AS FolioCompPago " +
				" \nFROM SRefacturacion sr " +
				" \nLEFT JOIN Facturas f on f.Factura = sr.Factura AND f.FPor = sr.FPorOrigen " +
				" \nLEFT JOIN Pendiente p on p.Docto = sr.Factura AND p.Partida = sr.FPorOrigen " +
				" \nWHERE f.Factura = :factura  and p.Tipo like '%ReFacturación%' AND F.fpor= :fpor AND F.DeSistema = 1" +
				" \nUNION " +
				" \nSELECT top 1 LEN('9'), '9' AS ID,'CANCELACION' AS ETIQUETA, p.FInicio, p.FFin ,'' as Contacto, p.Responsable, f.Factura as REFERENCIA, " +  
				" \n'' AS MEDIO,'' as COMENTARIOS, coalesce(f.FPor,p.Partida) as QUIENCOMPRA, " +
				" \n'' AS LoteTxt, " + funcion.obtenerTiempoTranscurridoSQL("p.FInicio", "p.FFin", "Dias") + " AS TT, " +
				" \n'' as CONFORME, f.Tipo, '' AS Document, NULL as FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL, NULL, NULL AS FolioCompPago " +
				" \nfrom Pendiente p " +
				" \nLEFT JOIN Facturas f on f.Factura = p.Docto AND f.FPor = p.Partida " +
				" \nwhere p.Tipo like '%Amparar Cancelación%' AND f.Factura = :factura AND F.fpor= :fpor  AND F.DeSistema = 1" +
				"\nUNION " +
				"\nSELECT top 1 LEN('10'), '10' AS ID,'FACTURA' AS ETIQUETA,PEN.FInicio as FINICIO, COALESCE(PEN1.FFin,PGP.FFin) AS FFIN,  " +
				"\nNULL AS CONTACTO,  NULL AS RESPONSABLE, NULL AS REFERENCIA, NULL AS MEDIO, NULL AS COMENTARIOS, NULL AS QUIENCOMPRA, NULL AS LoteTxt, " +
				funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "COALESCE(PEN1.FFin,PGP.FFin)", "Dias") + " AS TT, " +
				"\nNULL AS CONFORME, NULL AS TIPO, NULL AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO, '' as TCAMBIO, NULL, NULL, NULL AS FolioCompPago " +
				"\nFROM Facturas AS FAC " +
				"\nLEFT JOIN( SELECT * FROM Pendiente)AS PEN ON FAC.CPedido = PEN.Docto " +
				"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido = PEN.Docto AND PF.Fpor=FAC.Fpor AND PF.Factura=FAC.Factura " +
				"\nLEFT JOIN (SELECT * FROM PRemisiones) AS PR ON PR.CPedido = PEN.Docto " +
				"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=FAC.CPedido " +
				"\nLEFT JOIN (SELECT *, 'Fisica' AS Medio, 'Normal' AS TIPO , 'REMISION' AS LOTE FROM Remisiones) AS R ON R.Factura = PR.Factura AND R.FPor = PR.FPor " +
				"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN1 ON PEN1.Docto = FAC.Factura  AND PEN1.Partida = FAC.FPor " +
				"\nLEFT JOIN (SELECT * FROM PagoPendiente ) AS PAGOP ON PAGOP.Factura = FAC.Factura AND PAGOP.QuienFactura = FAC.FPor " +
				"\nLEFT JOIN (SELECT * FROM Remisiones) AS RE ON CAST(RE.Nota as varchar(10)) = FAC.Factura AND RE.FPor = FAC.FPor " +
				"\nLEFT JOIN (SELECT * FROM Pedidos) AS P ON P.CPedido = FAC.CPedido OR P.CPedido = RE.CPedido " +
				"\nLEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCONS ON INCONS.Docto = P.DoctoR " +
				"\nLEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido " +
				"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=PED.DoctoR " +
				"\nLEFT JOIN (SELECT MAX(FOLIO) AS FOLIO,Docto FROM Pendiente WHERE Tipo='Pago pendiente' GROUP BY Docto) AS PGP1 ON PGP1.Docto=PED.DoctoR " +
				"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Pago pendiente') AS PGP ON PGP.Folio=PGP1.FOLIO " +
				"\nWHERE PEN.FInicio IS NOT NULL AND (PEN.Tipo = 'A facturar' OR PEN.Tipo = 'Facturar por adelantado')  " +
				"\nAND FAC.Factura = :factura AND FAC.fpor= :fpor AND FAC.DeSistema = 1 " +
				"\nAND (PEN.FInicio IS NOT NULL OR PET.FInicio IS NOT NULL) " +
				"\nAND (PEN1.FFin IS Not NULL OR PGP.FFin IS Not NULL) " +
				"\nORDER BY No, ID ";
			log.info("query2: " + query);
			return super.jdbcTemplate.query(query,map, new ConsultaLineaTiempoResunenRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	public String condicionBusquedaFacturacion(String factura,
			String cPedido, Date finicio, Date ffin, String cliente,
			String estado, String refacturada, String facturo, String tipo,
			String medio, String cPago, Boolean empresas, String uuid){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("cPedido", cPedido);
		map.put("finicio", finicio);
		map.put("ffin", ffin);
		map.put("cliente", cliente);
		map.put("estado", estado);
		map.put("refacturada", refacturada);
		map.put("facturo", facturo);
		map.put("tipo", tipo);
		map.put("medio", medio);
		map.put("cPago", cPago);
		map.put("empresas", empresas);
		map.put("uuid", uuid);
		
//		SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
		
		StringBuilder sbQuery = new StringBuilder("WHERE 1=1 \n");
		
		if(empresas){
			sbQuery.append("AND F.DeSistema = 1 \n");
		}else{
			sbQuery.append("AND f.FPor<>'ProquifaServicios' AND F.FPor<>'Proquifa Servicios' \n");
			sbQuery.append("AND F.DeSistema = 1 \n").append("AND F.Cliente NOT IN (201,347,348,987,1595,2281,2322) \n");
		}

		if (factura != null && !factura.trim().equals("")){
			sbQuery.append("AND f.factura ='").append(factura.trim()).append("' \n");
		}
		if (cPedido != null && !cPedido.trim().equals("")){
			sbQuery.append("AND f.CPedido = '").append(cPedido.trim()).append("' \n");
		}
		if (finicio != null){
			sbQuery.append("AND f.fecha >= '").append(formatoFecha.format(finicio)).append(" 00:00' \n");
		}
		if (ffin != null){
			sbQuery.append("AND f.fecha <= '").append(formatoFecha.format(ffin)).append(" 23:59' \n");
		}
		if (cliente != null && !cliente.trim().toUpperCase().equals("--TODOS--")){
			sbQuery.append("AND c.Clave = '").append(cliente.trim()).append("' \n");
		}
		if (estado != null && !estado.trim().toUpperCase().equals("--TODOS--")){
			sbQuery.append("AND f.Estado = '").append(estado.trim()).append("' \n");
		}
		if (refacturada != null && refacturada.trim().toUpperCase().equals("SI")){
			sbQuery.append("AND rr.FacturaAntigua IS NOT NULL \n");
		}
		if  (refacturada != null && refacturada.trim().toUpperCase().equals("NO")){
			sbQuery.append("AND rr.FacturaAntigua IS NULL \n");
		}
		if (facturo != null && !facturo.trim().toUpperCase().equals("--TODOS--")){
			sbQuery.append("AND f.FPor = '").append(facturo.trim()).append("' \n");
		}
		if (tipo != null && !tipo.trim().toUpperCase().equals("--TODOS--")){
			sbQuery.append("AND COALESCE(f.Tipo,'Normal') = '").append(tipo.trim()).append("' \n");
		}
		if (medio != null && !medio.trim().toUpperCase().equals("--TODOS--")){
			sbQuery.append("AND COALESCE(f.Medio,'Fisica') = '").append(medio.trim()).append("' \n");
		}
		if (cPago != null && !cPago.trim().toUpperCase().equals("--TODOS--")){
			sbQuery.append("AND f.cPago = '").append(cPago.trim()).append("' \n");
		}
		if (uuid != null && !uuid.equals("")){
			sbQuery.append("AND f.UUID LIKE '%").append(uuid.trim()).append("%' \n");
		}
		return sbQuery.toString();
	}

	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroProgramacion(String factura, String fpor){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try{			
			String query = 	" SELECT TOP 1 'COBRO_PROGR' AS ETIQUETA, PROGR.FInicio AS FREVISION, PROGR.FFin AS FPROGRAMACION, PAG.FEPago AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO, " +
				" PROGR.Responsable AS RESPONSABLE, CAST(RPC.Descripcion AS Varchar(60)) AS COMENTARIOS, RUTEJ.Responsable AS MENSAJERO, " +
				" NULL AS REVISION,NULL AS COMENTARIOS_REV, PAG.Moneda AS COBRO, PAG.Monto AS MONTO, PAG.Medio AS DOC_CIERRE, COALESCE(PAG.TipoCambio,FAC.TCambio) AS DOCUMENT, NULL AS DOCUMENT1 " +
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
				" WHERE FAC.Factura= :factura  AND FAC.fpor= :fpor"+
				" AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' )	" +
				" ORDER BY PEN.FFin, RPC.FFin DESC  " ;	
				log.info("query6: " +query);
			return super.jdbcTemplate.query(query,map, new ConsultaInspectorCobrosRowMapper());
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroMonitoreo(String factura, String fpor) {
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			
			StringBuilder sbQuery = new StringBuilder(" \n");
//			StringBuilder sbQuery = new StringBuilder("DECLARE @ListaFolios VARCHAR(MAX) = '' \n");
//			sbQuery.append("SELECT @ListaFolios = @ListaFolios +',' + CONVERT(VARCHAR,NC.AK_Folio) FROM Factura_NotaCredito FN  \n");
//			sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
//			sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FN.FK01_Factura WHERE F.Factura=").append(factura).append(" AND F.fpor='").append(fpor).append("' \n");
//			sbQuery.append("SET @ListaFolios = SUBSTRING(@ListaFolios,2,LEN(@ListaFolios)) \n");
			sbQuery.append(" SELECT DISTINCT 'COBRO_MONITOREO' AS ETIQUETA,PEN.FInicio AS FREVISION, PEN.FFin AS FPROGRAMACION, CONVERT(DATETIME,NULL) AS FENTREGA, CONVERT(DATETIME,COPRE2.FPago) AS FCOBRO, \n");	
			sbQuery.append(" NULL AS RESPONSABLE, CO.MonedaPagada AS COMENTARIOS, NULL AS MENSAJERO, RPC.FFin, \n");
			sbQuery.append(" NULL AS REVISION,NULL AS COMENTARIOS_REV, CO.Moroso AS COBRO, CO.MontoPagado AS MONTO, NULL AS DOC_CIERRE, COALESCE(RPC.idPC,COPRE2.FolioPC,COPRE.FolioPC) AS DOCUMENT, NULL AS DOCUMENT1, FAC.idFactura, CO.Folio AS FolioComplementoPago \n");						 
			sbQuery.append(" FROM Facturas AS FAC \n");
			sbQuery.append(" LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida	\n");								
			sbQuery.append(" LEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON FAC.Factura = RPC.Factura AND RPC.idCliente = FAC.Cliente \n");	
			sbQuery.append(" LEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = FAC.Factura AND CO.FPor=FAC.FPor \n");
			sbQuery.append(" LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente \n");		 
			sbQuery.append(" LEFT JOIN( SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.CPedido=FAC.CPedido \n");	
			sbQuery.append(" LEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio \n");	
			sbQuery.append(" LEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=FAC.Factura AND COPRE2.FPor=FAC.FPor \n");
			sbQuery.append("LEFT JOIN Factura_NotaCredito FN ON FN.FK01_Factura = FAC.idFactura \n");
			sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
			sbQuery.append(" WHERE FAC.Factura='").append(factura).append("' AND FAC.fpor='").append(fpor).append("' \n");
			sbQuery.append(" AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' OR FAC.Cpago='PAGO INMEDIATO') \n");	
			sbQuery.append(" ORDER BY RPC.FFin DESC \n");
//			log.info(sbQuery.toString());
			List<TiempoProceso> list = super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaInspectorCobrosRowMapper());
			
			for (TiempoProceso tiempoProceso : list) {
				sbQuery = new StringBuilder("DECLARE @ListaFolios VARCHAR(MAX) = '' \n");
				sbQuery.append("SELECT @ListaFolios = @ListaFolios +',' + CONVERT(VARCHAR,NC.AK_Folio) FROM Factura_NotaCredito FN  \n");
				sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
				sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FN.FK01_Factura WHERE F.idFactura=").append(tiempoProceso.getFolio()).append(" \n");
				sbQuery.append("SET @ListaFolios = SUBSTRING(@ListaFolios,2,LEN(@ListaFolios)) \n");
				sbQuery.append("SELECT @ListaFolios folio ");
				String folio = (String) super.queryForString(sbQuery.toString());
				tiempoProceso.setFolio(folio);
			}
//			log.info("query7: " + sbQuery);
			return list;
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorCobroSC(String factura, String fpor) throws ProquifaNetException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try{			
			StringBuilder sbQuery = new StringBuilder(" \n");
			
			sbQuery.append(" SELECT 'GENERALES' AS ETIQUETA,PET.FInicio AS FREVISION, FAC.Fecha AS FPROGRAMACION,CONVERT(DATETIME,NULL) AS FENTREGA,PECO.FFin AS FCOBRO, \n");
			sbQuery.append(" NULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, NULL AS COBRO,  \n");
			sbQuery.append(" NULL AS MONTO, NULL AS DOC_CIERRE, NULL AS DOCUMENT, NULL AS DOCUMENT1, '' idFactura \n");
			sbQuery.append(" FROM Facturas AS FAC \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM DoctosR) AS DOC ON DOC.Folio = PED.DoctoR \n");
			sbQuery.append(" LEFT JOIN (SELECT MAX(FInicio) AS FInicio, Docto FROM Pendiente WHERE Tipo='Pedido por tramitar' GROUP BY Docto) AS PET ON PET.Docto=DOC.Folio  \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Cobro a validar') AS PECO ON PECO.Docto = FAC.CPedido \n");
			sbQuery.append(" WHERE FAC.Factura=:factura  AND FAC.fpor= :fpor  \n");
			sbQuery.append(" UNION \n");
			sbQuery.append(" SELECT 'GESTION_PSC' AS ETIQUETA,PET.FInicio AS FREVISION, PET.FFin AS FPROGRAMACION,INCO.FechaPago AS FENTREGA, CONVERT(DATETIME,NULL) AS FCOBRO, \n");
			sbQuery.append(" NULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, NULL AS REVISION,NULL AS COMENTARIOS_REV, INCO.TipoCambio AS COBRO, \n");
			sbQuery.append(" INCO.Monto AS MONTO, INCO.Moneda AS DOC_CIERRE, INCO.MedioPago AS DOCUMENT, NULL AS DOCUMENT1, '' idFactura \n");
			sbQuery.append(" FROM Facturas AS FAC \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='PSC c/problemas') AS PET ON PET.Docto=PED.DoctoR \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCO ON INCO.Docto=PED.DoctoR \n");
			sbQuery.append(" WHERE FAC.Factura= :factura AND FAC.fpor= :fpor \n");
			sbQuery.append(" UNION \n");
			sbQuery.append(" SELECT Distinct 'MONITOREO_CO_SC' AS ETIQUETA,PGP.FInicio AS FREVISION, PGP.FFin AS FPROGRAMACION,COALESCE (CO.Fecha,INCO.FechaPago) AS FENTREGA,CO.FPago AS FCOBRO, \n");
			sbQuery.append(" NULL AS RESPONSABLE, NULL AS COMENTARIOS, NULL AS MENSAJERO, PEDP.Pago AS REVISION,NULL AS COMENTARIOS_REV, CO.TCPagado AS COBRO, \n");
			sbQuery.append(" CO.MontoPagado AS MONTO, CO.FolioPC AS DOC_CIERRE, CO.MonedaPagada AS DOCUMENT, CASE WHEN DATEDIFF(DAY,CO.FPago,CO.Fecha)>0 THEN 'NO' ELSE 'SI' END  AS DOCUMENT1, FAC.idFactura \n");
			sbQuery.append(	" FROM Facturas AS FAC \n");
			sbQuery.append(	" LEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = FAC.CPedido \n");
			sbQuery.append(	" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Pago pendiente') AS PGP ON PGP.Docto=PED.DoctoR \n");
			sbQuery.append(	" LEFT JOIN (SELECT * FROM PedidoPago) AS PEDP ON PEDP.CPedido = FAC.CPedido \n");
			sbQuery.append(	" LEFT JOIN (SELECT * FROM Cobro) AS CO ON CO.idPedidoPago = PEDP.Folio \n");
			sbQuery.append(" LEFT JOIN (SELECT * FROM InconsistenciaDePedido WHERE MedioPago IS NOT NULL) AS INCO ON INCO.Docto=PED.DoctoR \n"); 
			sbQuery.append(" WHERE FAC.Factura=:factura AND FAC.fpor=:fpor \n");

			List<TiempoProceso> list = super.jdbcTemplate.query(sbQuery.toString(), map, new ConsultaInspectorCobrosRowMapper());
			for (TiempoProceso tiempoProceso : list) {
				sbQuery = new StringBuilder("DECLARE @ListaFolios VARCHAR(MAX) = '' \n");
				sbQuery.append("SELECT @ListaFolios = @ListaFolios +',' + CONVERT(VARCHAR,NC.AK_Folio) FROM Factura_NotaCredito FN  \n");
				sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
				sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FN.FK01_Factura WHERE F.idFactura=").append(tiempoProceso.getFolio()).append(" \n");
				sbQuery.append("SET @ListaFolios = SUBSTRING(@ListaFolios,2,LEN(@ListaFolios)) \n");
				sbQuery.append("SELECT @ListaFolios folio ");
				String folio = (String) super.queryForString(sbQuery.toString());
				tiempoProceso.setFolio(folio);
			}
			log.info("query8: " + sbQuery);
			return list;
		}catch(Exception e){
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> findInspectorEntrega(String factura, String fpor){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try{			
			String query = 	"\nSELECT 'TRAMITAR RUTA' AS ETIQUETA,PEN.FInicio AS FInicio, PEN.FFin AS FFin, '' AS CONTACTO, " +
							"\nPEN.Responsable AS RESPONSABLE, CAST(PRDP.idDP AS VARCHAR(14)) + '-D' AS REFERENCIA, '' AS MEDIO,'' QUIENCOMPRA," +
					"\nCAST(RDP.Comentarios AS VARCHAR(500)) AS COMENTARIOS, 0 AS TT, '' AS CONFORME, '' AS LoteTxt,'' AS TIPO," +
					"\n'' AS DOCUMENT,CONVERT(DATE,null)FECHACOBRO, NULL AS MONTO" +
					"\nFROM Facturas F" +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 " +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) PF2 ON PF2.Factura = PRE.Factura AND PF2.FPor = PRE.FPor AND PF2.Part = PRE.Part" +
					"\nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON (PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part) OR (PRDP.Factura = PF2.Factura AND PRDP.FPor = PF2.FPor AND PRDP.Part = PF2.Part) OR (PRDP.Factura = RE.Factura AND PRDP.FPor = RE.FPor)" +
					"\nLEFT JOIN (SELECT * FROM RutaDP) RDP ON RDP.idDP = PRDP.idDP " +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA') AS PEN ON PRDP.idDP = PEN.Docto " +
					"\nWHERE F.fpor = :fpor  AND F.Factura= :factura AND F.DeSistema = 1  AND CASE WHEN RDP.idCliente IS NULL THEN 0 ELSE F.Cliente END = COALESCE(RDP.idCliente, 0)" +
					"\nUNION" +
					"\nSELECT 'SURTIR RUTA' AS ETIQUETA,PEN.FInicio, PEN.FFin, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, CASE WHEN RDP.Ambiente > 0 THEN PRDP.idDP + '-1,' ELSE '' END  +" +
					"\nCASE WHEN RDP.Refrigeracion > 0 THEN PRDP.idDP + '-2,' ELSE '' END + '' + CASE WHEN RDP.Congelacion > 0 THEN PRDP.idDP + '-3,'ELSE '' END + '' + " +
					"\nCAST((CASE WHEN RDP.SinManejo > 0 THEN PRDP.idDP + '-4,' ELSE '' END ) AS VARCHAR (16)) AS REFERENCIA, '' AS MEDIO, '' QUIENCOMPRA, " +
					"\n'' AS COMENTARIOS, 0 AS TT, '' AS CONFORME, '' AS LoteTxt, '' AS TIPO, " +
					"\nRDP.idEntrega AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO " +
					"\nFROM Facturas F" +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 " +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) PF2 ON PF2.Factura = PRE.Factura AND PF2.FPor = PRE.FPor AND PF2.Part = PRE.Part" +
					"\nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON (PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part) OR (PRDP.Factura = PF2.Factura AND PRDP.FPor = PF2.FPor AND PRDP.Part = PF2.Part) OR (PRDP.Factura = RE.Factura AND PRDP.FPor = RE.FPor)" +
					"\nLEFT JOIN (SELECT * FROM RutaDP) RDP ON RDP.idDP = PRDP.idDP " +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A surtir ruta') AS PEN ON PRDP.idDP = PEN.Docto " +
					"\nWHERE F.fpor = :fpor  AND F.Factura=:factura AND F.DeSistema = 1  AND CASE WHEN RDP.idCliente IS NULL THEN 0 ELSE F.Cliente END = COALESCE(RDP.idCliente, 0)" +
					"\nUNION" +
					"\nSELECT 'ASIGNAR MENSAJERO' AS ETIQUETA,PEN.FInicio AS FInicio, PEN3.FFin AS FFin, PEN3.Responsable AS CONTACTO," + 
					"\nPEN.Responsable AS RESPONSABLE, COALESCE(PEN3.Partida, RDP.ZonaMensajeria) AS REFERENCIA, Ruta AS MEDIO ,'' QUIENCOMPRA," +
					"\nCAST(RDP.Comentarios AS VARCHAR(500)) AS COMENTARIOS, 0 AS TT, '' AS CONFORME, '' AS LoteTxt,'' AS TIPO," +
					"\n'' AS DOCUMENT,CONVERT(DATE,null)FECHACOBRO, NULL AS MONTO" +
					"\nFROM Facturas F" +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 " +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF.CPedido" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF.PPedido END = PF.PPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) PF2 ON PF2.Factura = PRE.Factura AND PF2.FPor = PRE.FPor AND PF2.Part = PRE.Part" +
					"\nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON (PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part) OR (PRDP.Factura = PF2.Factura AND PRDP.FPor = PF2.FPor AND PRDP.Part = PF2.Part) OR (PRDP.Factura = RE.Factura AND PRDP.FPor = RE.FPor)" +
					"\nLEFT JOIN (SELECT * FROM RutaDP) RDP ON RDP.idDP = PRDP.idDP " +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA') AS PEN ON PRDP.idDP = PEN.Docto " +
					"\nLEFT JOIN Pendiente PEN3 ON PEN3.Docto = RDP.idRuta AND PEN3.FInicio IS NOT NULL AND PEN3.Tipo = 'Ruta a trabajar'  " +
					"\nWHERE F.fpor = :fpor  AND F.Factura= :factura AND F.DeSistema = 1  AND CASE WHEN RDP.idCliente IS NULL THEN 0 ELSE F.Cliente END = COALESCE(RDP.idCliente, 0)" +
					"\nUNION" +
					"\nSELECT  'EJECUTAR RUTA' AS ETIQUETA,PEN.FInicio, PEN.FFin, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, 'REALIZADA' AS REFERENCIA, '' AS MEDIO, " +
					"\n'' QUIENCOMPRA, '' AS COMENTARIOS, 0 AS TT, '' AS CONFORME, " +
					"\n'' AS LoteTxt,'' AS TIPO,  '' AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO " +
					"\nFROM Pendiente AS PEN " +
					"\nLEFT JOIN (SELECT idEntrega, Factura, FPor, Ruta, idDP, RazonesEntrega  FROM RutaDP) AS RDP ON RDP.idEntrega = PEN.Docto " +
					"\nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = RDP.idDP " +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
					"\nLEFT JOIN (SELECT * FROM Facturas) AS F ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 " +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF2 ON PF2.Factura = F.Factura AND PF2.FPor = F.FPor" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF2.CPedido" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF2.PPedido END = PF2.PPedido" +
					"\nWHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'A Ejecutar ruta' AND PF.fpor = :fpor AND RDP.Factura=:factura" +
					"\nUNION" +
					"\nSELECT 'EJECUTAR RUTA' AS ETIQUETA, PEN.FInicio, PEN.FFin, '' AS CONTACTO, Responsable AS RESPONSABLE, RDP.Entrega  AS REFERENCIA,''AS MEDIO, " +
					"\n'' QUIENCOMPRA, CAST(RDP.RazonesEntrega AS VARCHAR(500)) AS COMENTARIOS, 0 AS TT, '' AS CONFORME, " +
					"\n'' AS LoteTxt,'' AS TIPO,'' AS DOCUMENT, CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO " +
					"\nFROM Pendiente AS PEN " +
					"\nLEFT JOIN (SELECT idRuta, Factura, FPor, Ruta, Entrega, idDP, RazonesEntrega,idCliente  FROM RutaDP) AS RDP ON RDP.idRuta = PEN.Docto " +
					"\nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = RDP.idDP " +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part " +
					"\nLEFT JOIN (SELECT * FROM Facturas) AS F ON PF.Factura = F.Factura AND PF.FPor = F.FPor" +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 " +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) AS PF2 ON PF2.Factura = F.Factura AND PF2.FPor = F.FPor" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.CPedido ELSE F.CPedido END = PF2.CPedido" +
					"\nAND CASE WHEN F.DeRemision = 1 THEN PRE.PPedido ELSE PF2.PPedido END = PF2.PPedido" +
					"\nWHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'Ruta a ejecutar' AND PF.fpor = :fpor AND RDP.Factura= :factura " +
					"\nUNION" +
					"\nSELECT 'CERRAR RUTA' AS ETIQUETA,COALESCE(PEN3.FInicio,PEN2.FInicio,PEN.FInicio) AS FInicio, PEN.FFin, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, FolioDoctos2 AS REFERENCIA, CASE WHEN RDP.EntregayRevision = 1 THEN 'SI' ELSE 'NO' END AS MEDIO, " +
					"\n'' QUIENCOMPRA, CAST(DAY(RDP.FERefacturacion) AS VARCHAR(2)) + CASE WHEN MONTH(RDP.FERefacturacion) > 9 THEN '/' ELSE '/0' END + " +
					"\nCAST(MONTH(RDP.FERefacturacion) AS VARCHAR (2)) + '/' + CAST(YEAR(RDP.FERefacturacion) AS VARCHAR(4))AS COMENTARIOS, 0 AS TT, " +
					"\nCASE WHEN SR.Factura IS NULL THEN 0 ELSE 1 END AS CONFORME, '' AS LoteTxt, '' AS TIPO," +
					"\nFolioDoctos3 AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
					"\nFROM (SELECT * FROM Pendiente WHERE (Tipo = 'A cerrar ruta' OR Tipo = 'A cerrar ruta GDL') AND Docto <> '') AS PEN" +
					"\nLEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idEntrega = PEN.Docto OR RDP.idRuta = PEN.Docto" +
					"\nINNER JOIN (SELECT * FROM PRutaDP WHERE Factura= :factura ) AS PRDP ON PRDP.idDP = RDP.idDP" +
					"\nINNER JOIN (SELECT * FROM PFacturas WHERE fpor = :fpor ) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part" +
					"\nLEFT JOIN (SELECT * FROM SRefacturacion ) SR ON SR.Factura = RDP.Factura" +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA') AS PEN2 ON PRDP.idDP = PEN2.Docto " +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Ruta a ejecutar' OR Tipo = 'A Ejecutar ruta') AS PEN3 ON PRDP.idDP = PEN3.Docto " +
					"\nUNION " +
					"\nSELECT 'CERRAR RUTA' AS ETIQUETA,COALESCE(PEN3.FFin,PEN.FFin,PEN.FInicio) AS FInicio, RDP.FFin, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, FolioDoctos2 AS REFERENCIA, CASE WHEN RDP.EntregayRevision = 1 THEN 'SI' ELSE 'NO' END AS MEDIO, " +
					"\n'' QUIENCOMPRA, CAST(DAY(RDP.FERefacturacion) AS VARCHAR(2)) + CASE WHEN MONTH(RDP.FERefacturacion) > 9 THEN '/' ELSE '/0' END + " +
					"\nCAST(MONTH(RDP.FERefacturacion) AS VARCHAR (2)) + '/' + CAST(YEAR(RDP.FERefacturacion) AS VARCHAR(4))AS COMENTARIOS, 0 AS TT, " +
					"\nCASE WHEN SR.Factura IS NULL THEN 0 ELSE 1 END AS CONFORME, '' AS LoteTxt, '' AS TIPO," +
					"\nFolioDoctos3 AS DOCUMENT,CONVERT(DATE,null)  FECHACOBRO, NULL AS MONTO" +
					"\nFROM (SELECT * FROM Facturas WHERE fpor = :fpor  AND Factura=:factura  AND DeSistema = 1 ) F " +
					"\nLEFT JOIN (SELECT * FROM Remisiones) RE ON CAST(RE.Nota AS VARCHAR(100)) = F.Factura AND F.DeRemision = 1 " +
					"\nLEFT JOIN (SELECT * FROM PRemisiones) PRE ON PRE.Factura = RE.Factura AND PRE.FPor = RE.FPor AND PRE.CPedido = RE.CPedido" +
					"\nLEFT JOIN (SELECT * FROM PFacturas) PF ON PF.Factura = PRE.Factura AND PF.FPor = PRE.FPor AND PF.Part = PRE.Part" +
					"\nLEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON (PRDP.Factura = PF.Factura AND PRDP.FPor = PF.FPor AND PRDP.Part = PF.Part)" +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA') AS PEN ON PRDP.idDP = PEN.Docto" +
					"\nINNER JOIN (SELECT * FROM RutaDP WHERE EstadoRuta = 'Cerrada') RDP ON RDP.idDP = PRDP.idDP" +
					"\nLEFT JOIN (SELECT * FROM SRefacturacion ) SR ON SR.Factura = RDP.Factura" +
					"\nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Ruta a ejecutar' OR Tipo = 'A Ejecutar ruta') AS PEN3 ON PRDP.idDP = PEN3.Docto " +
					"\nORDER BY FINICIO ASC";
			log.info("Query: " + query);
			return super.jdbcTemplate.query(query,map, new ConsultaLineaTiempoResunenRowMapper());
		}catch(Exception e){
			e.printStackTrace();
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Facturacion> obtenerResumenFacturaRemision(String factura,
			String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		StringBuilder sbQuery = new StringBuilder("SELECT 'FacturaRemision' as Etiqueta, r.Fecha as FechaR, r.Factura as Remision, f.Fecha, f.Factura, p.Responsable, \n");
		sbQuery.append("COALESCE(f.Tipo,'Normal') AS Tipo, COALESCE(f.Medio,'Fisica') AS Medio, \n");
		sbQuery.append("(Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN m.PDolar ELSE f.TCambio END) as TCambio, \n");
		sbQuery.append(" '' as Razones, '' as Comentarios, '' as Autorizo, NULL FInicio, NULL FFin \n");
		sbQuery.append("from Remisiones r left join Facturas f on f.Factura = cast(r.Nota as varchar) \n");
		sbQuery.append("LEFT JOIN Pendiente p on p.Docto = r.Factura \n");
		sbQuery.append("LEFT JOIN Monedas m on convert(varchar,m.Fecha,105) = CONVERT(varchar,f.fecha,105)  \n");

		sbQuery.append(this.condicionBusquedaFacturacion(factura, null, null, null, null, null, null, fpor, null, null, null, false, null));
		sbQuery.append(" AND p.Tipo = 'Facturar remisión' \n");
		return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facturacion> obtenerResumenRefacturacion(String factura,
			String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		StringBuilder sbQuery = new StringBuilder("SELECT 'Refacturacion' as Etiqueta, sr.FSR as FechaR, f.Fecha, rr.FacturaNueva Factura, p.Responsable, \n");
		sbQuery.append("(Case when (f.TCambio IS NULL OR f.TCambio = 0 OR f.TCambio = 0.0 ) THEN m.PDolar ELSE f.TCambio END) as TCambio, \n");
		sbQuery.append("sr.Razones, sr.CAutorizacion Comentarios, sr.Autorizo, '' as Remision, '' as Tipo, '' as Medio, p.FInicio, p.FFin \n");
		sbQuery.append("from SRefacturacion sr  \n");
		sbQuery.append("LEFT JOIN RegistroRefacturacion rr ON RR.FacturaAntigua = sr.Factura AND rr.FPorAntiguo = sr.FPorOrigen \n");
		sbQuery.append("LEFT JOIN Facturas f on f.Factura = sr.Factura and f.FPor = sr.FPorOrigen \n");
		sbQuery.append("LEFT JOIN Pendiente p on p.Docto = sr.Factura and p.Partida = sr.FPorOrigen \n");
		sbQuery.append("LEFT JOIN Monedas m on convert(varchar,m.Fecha,105) = CONVERT(varchar,f.fecha,105) \n");
		
		sbQuery.append(this.condicionBusquedaFacturacion(factura, null, null, null, null, null, null, fpor, null, null, null, false, null));
		sbQuery.append(" and p.Tipo like '%ReFacturación%' \n");
		return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facturacion> obtenerResumenCancelacion(String factura,
			String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		StringBuilder sbQuery = new StringBuilder("SELECT 'Cancelacion' as Etiqueta, p.Responsable, f.Factura, p.FFin as Fecha, NULL as FechaR, \n");
		sbQuery.append("'' as Remision, '' as Tipo, '' as Medio, '' as Razones, '' as Comentarios, '' as Autorizo, NULL as TCambio, NULL FInicio, NULL FFin \n");
		sbQuery.append("FROM Pendiente p \n");
		sbQuery.append("LEFT JOIN Facturas f on f.Factura = p.Docto AND f.FPor = p.Partida \n");
		
		sbQuery.append(this.condicionBusquedaFacturacion(factura, null, null, null, null, null, null, fpor, null, null, null, false, null));
		sbQuery.append(" AND p.Tipo like '%Amparar Cancelación%' \n");
		return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TiempoProceso> findInspectorSolicitudRecoleccionCheque(
			String factura, String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try {
			
		StringBuilder sbQuery = new StringBuilder("SELECT ToP 1 'SOLICITUD_RECOLECCION' ETIQUETA, rt.Fecha FechaRequerida, NULL as FechaRecoleccion, p.FInicio FechaSolicitud, \n");
		sbQuery.append("rt.Prioridad, rt.Descripcion as ComentariosAdicionales, rt.Ruta, rt.Zona, rt.idPC as Solicitud, NULL as Cobro, \n");
		sbQuery.append("NULL Comentarios, NULL as doc_cierre, NULL as Document \n");
		sbQuery.append("FROM Facturas f \n");
		sbQuery.append("LEFT JOIN RutaPC rt On rt.Factura = f.Factura AnD rt.FPor = f.FPor \n");
		sbQuery.append("LEfT JOiN Pendiente p On p.Docto = rt.idPC \n");
		sbQuery.append("WHERE p.Tipo = 'A concluir planeacion' \n");
		sbQuery.append("AND f.Factura = '").append(factura).append("' \n");
		sbQuery.append("AND f.Fpor = '").append(fpor).append("' \n");
		return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaInspectorCobrosRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TiempoProceso> findInspectorRecoleccionCheque(
			String factura, String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try {
			
		StringBuilder sbQuery = new StringBuilder("SELECT ToP 1 'RECOLECCION_CHEQUE' ETIQUETA, NULL FechaRequerida, rt.FFin FechaRecoleccion, NULL FechaSolicitud,  \n");
		sbQuery.append("NULL as Prioridad, NULL as ComentariosAdicionales, NULL as Ruta, NULL as Zona, NULL as Solicitud, rt.Entrega as Cobro, \n");
		sbQuery.append("rt.RazonesEntrega Comentarios, DoctosCierre as doc_cierre, rt.FolioDoctos as Document  \n");
		sbQuery.append("FROM Facturas f \n");
		sbQuery.append("LEFT JOIN RutaPC rt On rt.Factura = f.Factura AnD rt.FPor = f.FPor \n");
		sbQuery.append("LEfT JOiN Pendiente p On p.Docto = rt.idPC \n");
		sbQuery.append("WHERE p.Tipo = 'A concluir planeacion' \n");
		sbQuery.append("AND f.Factura = '").append(factura).append("' \n");
		sbQuery.append("AND f.Fpor = '").append(fpor).append("' \n");
		return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaInspectorCobrosRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TiempoProceso> obtenerResumenFactura(String factura, String fpor){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		log.info ("/************ obtenerResumenFactura **********/");
		try {
			StringBuilder sbQuery = new StringBuilder("DECLARE @ListaFolios VARCHAR(MAX) = '' \n");
			sbQuery.append("SELECT @ListaFolios = @ListaFolios +',' + CONVERT(VARCHAR,NC.AK_Folio) FROM Factura_NotaCredito FN  \n");
			sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
			sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FN.FK01_Factura WHERE F.Factura=").append(factura).append(" AND F.FPor = '").append(fpor).append("' \n");
			sbQuery.append("SET @ListaFolios = SUBSTRING(@ListaFolios,2,LEN(@ListaFolios)) \n");			
			sbQuery.append("SELECT * FROM (SELECT TOP 1 '1' AS ID,'Generales' AS ETIQUETA,  FAC.Fecha as FechaFacturacion, FAC.Factura, \n");
			sbQuery.append("pen.Responsable Facturo, FAC.TCambio, FAC.Tipo, COALESCE(FAC.Medio,'Fisica') as Medio, NULL as FechaRevision,  \n");
			sbQuery.append("'' as ComentariosRevision,  '' as Mensajero, '' DocumentoCierre, '' Documento, '' Documento1, NULL AS FechaEntrega, \n");
			sbQuery.append("'' as Conforme , '' as Zona, '' as Ruta, '' As EntregaRevision, '' as Refacturacion, ''as Monto,'' as Moroso, NULL as FechaCobro, '' Moneda, '' FolioNC ,FAC.FPor, NULL AS FolioCompPago \n");
			sbQuery.append("FROM Facturas AS FAC \n");
			sbQuery.append("LEFT JOIN( SELECT * FROM Pendiente)AS PEN ON FAC.CPedido = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.CPedido = PEN.Docto AND PF.Fpor=FAC.Fpor AND PF.Factura=FAC.Factura \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM PRemisiones) AS PR ON PR.CPedido = PEN.Docto \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A facturar' AND FFin IS NOT NULL) AS PEA ON PEA.Docto=FAC.CPedido \n");
			sbQuery.append("LEFT JOIN (SELECT *, 'Fisica' AS Medio, 'Normal' AS TIPO , 'REMISION' AS LOTE FROM Remisiones) AS R ON R.Factura = PR.Factura AND R.FPor = PR.FPor \n");
			sbQuery.append("WHERE PEN.FInicio IS NOT NULL AND (PEN.Tipo = 'A facturar' OR PEN.Tipo = 'Facturar por adelantado')  \n");
			sbQuery.append("AND FAC.Factura ='").append(factura).append("' AND FAC.fpor = '").append(fpor).append("' AND FAC.CPago NOT LIKE 'Prepago%' ORDER BY FAC.Fecha DESC ) FAC \n");
			sbQuery.append("UniON \n");
			sbQuery.append("SELECT '2' AS ID,'Entrega' AS ETIQUETA,  NULL as FechaFacturacion, '' Factura, '' Facturo, '' TCambio, '' Tipo,  \n");
			sbQuery.append("'' as Medio, NULL as FechaRevision, '' as ComentariosRevision, PEN.Mensajero, PEN.DocumentoCierre, \n");
			sbQuery.append("PEN.Documento, PEN.Documento1, PEN.FechaEntrega, PEN.Conforme, PEN.Zona, PEN.Ruta, PEN.EntregaRevision, \n");
			sbQuery.append("PEN.Refacturacion, ''as Monto, '' as Moroso, NULL as FechaCobro, '' Moneda, '' FolioNC, '' FPor, NULL AS FolioCompPago \n");
			sbQuery.append("FROM( SELECT top 1 PEN3.Responsable as Mensajero, RDP.FolioDoctos DocumentoCierre, \n");
			sbQuery.append("RDP.FolioDoctos2 Documento, rdp.FolioDoctos3 Documento1, COALESCE(MAX(PEN2.FFin),MAX(PEN.FFin)) AS FechaEntrega,  \n");
			sbQuery.append("RDP.Conforme, COALESCE(PEN3.Partida, RDP.ZonaMensajeria) as Zona, rdp.Ruta as ruta, RDP.EntregayRevision As EntregaRevision,  \n");
			sbQuery.append("CASE when SR.Factura IS NOT NULL THEN 'SI' ELSE 'NO' END as Refacturacion  \n");
			sbQuery.append("FROM Pendiente AS PEN  \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM PRutaDP) AS PRDP ON PRDP.idDP = PEN.Docto   \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM PFacturas) AS PF ON PF.Factura = PRDP.Factura AND PF.FPor = PRDP.FPor AND PRDP.Part = PF.Part  \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM RutaDP) AS RDP ON RDP.idDP = PRDP.idDP   \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'A TRAMITAR RUTA' AND Partida <> '' ) AS PEN2 ON PEN2.Docto = PEN.Docto \n");
			sbQuery.append("LEFT JOIN Pendiente PEN3 ON PEN3.Docto = RDP.idRuta AND PEN3.FInicio IS NOT NULL AND PEN3.Tipo = 'Ruta a trabajar'  \n");
			sbQuery.append("LEFT JOIN (SELECT Docto, Responsable FROM Pendiente WHERE Tipo LIKE 'A tramitar ruta') AS PENDP ON PENDP.Docto = RDP.idDP \n");
			sbQuery.append("LEFT JOIN Pendiente PEN4 ON (PEN4.Docto = RDP.idRuta OR PEN4.Docto = RDP.idEntrega ) AND PEN4.Tipo = 'A cerrar ruta' AND PEN4.Docto <> '' \n");
			sbQuery.append("LEFT JOIN (SELECT * FROM SRefacturacion ) SR ON SR.Factura = RDP.Factura  \n");
			sbQuery.append("WHERE PEN.FInicio IS NOT NULL AND PEN.Tipo = 'A TRAMITAR RUTA' AND PEN.Partida = '' \n");
			sbQuery.append("AND PF.fpor = '").append(fpor).append("' AND RDP.Factura='").append(factura).append("' \n");
			sbQuery.append("GROUP BY PEN3.Responsable, RDP.FolioDoctos,  RDP.FolioDoctos2,  rdp.FolioDoctos3, RDP.Conforme, \n");
			sbQuery.append("PEN3.Partida,rdp.Ruta, RDP.EntregayRevision, SR.Factura, RDP.ZonaMensajeria \n");
			sbQuery.append("order by FechaEntrega desc ) AS PEN \n");
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT '3' AS ID,'Revision' AS ETIQUETA,  NULL as FechaFacturacion, '' Factura, '' Responsable, '' as TCambio, \n");
			sbQuery.append("'' as Tipo, COALESCE(FAC.Medio,'Fisica') as Medio, RPR.FFin as FechaRevision,  \n");
			sbQuery.append("COALESCE(convert(varchar(MAX),RPR.Descripcion), convert(varchar(MAX),RDP.ComentariosAdicionales)) as ComentariosRevision, \n");
			sbQuery.append("COALESCE(PENrDP.Responsable,PENrPR.Responsable) as Mensajero, convert(varchar(MAX),RPR.DoctosCierre) DocumentoCierre, \n");
			sbQuery.append("RPR.FolioDoctos AS Documento, RPR.FolioDoctos3 AS Documento1, NULL AS FechaEntrega, '' as Conforme, '' as Zona, '' as Ruta, \n");
			sbQuery.append("'' As EntregaRevision, '' as Refacturacion, ''as Monto, '' as Moroso, NULL as FechaCobro, '' Moneda, '' FolioNC, FAC.FPor, NULL AS FolioCompPago \n");
			sbQuery.append("FROM Facturas AS FAC \n");
			sbQuery.append("LEFT JOIN Pendiente AS PEN2 ON FAC.Factura= PEN2.Docto AND FAC.FPor = PEN2.Partida  \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM RutaDP ) AS RDP ON FAC.Factura = RDP.Factura AND RDP.idCliente = FAC.Cliente --AND RDP.EntregayRevision=0 \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM RutaPR ) AS RPR ON FAC.Factura = RPR.Factura AND rpr.FPor = fac.FPor AND RPR.idCliente = FAC.Cliente \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'A tramitar ruta') AS PENiET ON PENiET.Docto = RDP.idDP \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Ruta a ejecutar') AS PENrDP ON PENrDP.Docto= RDP.idRuta   \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Ruta a ejecutar') AS PENrPR ON PENrPR.Docto = RPR.idRuta  \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente  \n");
			sbQuery.append("WHERE PEN2.Tipo LIKE 'Programar revisión' AND(CLI.CPago LIKE '%dias%' OR CLI.CPago LIKE 'PAGO CONTRA ENTREGA' )  \n");
			sbQuery.append("AND RDP.EntregayRevision=0  \n");
			sbQuery.append("AND FAC.Factura='").append(factura).append("' and FAC.fpor='").append(fpor).append("'  \n");
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT '4' AS ID,'Cobro' AS ETIQUETA,  NULL as FechaFacturacion, '' Factura, '' Responsable, '' TCambio,'' Tipo, \n");
			sbQuery.append("'' as Medio, NULL as FechaRevision, '' as ComentariosRevision,  '' as Mensajero, '' DocumentoCierre,  \n");
			sbQuery.append("Mo.Documento Documento, '' Documento1, NULL AS FechaEntrega, '' as Conforme , '' as Zona, '' as Ruta, \n");
			sbQuery.append("'' As EntregaRevision, '' as Refacturacion, mo.MontoPagado as Monto, mo.Moroso, PEN.FFin as FechaCobro, Mo.Moneda, @ListaFolios FolioNC, Mo.FPor, MO.Folio AS FolioCompPago \n");
			sbQuery.append("FROM ( \n");
			sbQuery.append("SELECT top 1 fac.Factura, fac.FPor , co.MontoPagado, COALESCE(RPC.idPC,COPRE2.FolioPC,COPRE.FolioPC) Documento, co.Moroso, FAC.Moneda, CO.Folio \n");
			sbQuery.append("FROM Facturas AS FAC \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN ON FAC.Factura= PEN.Docto AND FAC.FPor = PEN.Partida			 \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM RutaPC  WHERE Entrega LIKE 'Realizada') AS RPC ON FAC.Factura = RPC.Factura AND RPC.idCliente = FAC.Cliente  \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Cobro) AS CO ON CO.Factura = FAC.Factura AND CO.FPor=FAC.FPor  \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Clientes) AS CLI ON CLI.Clave = FAC.Cliente	 \n");
			sbQuery.append("LEFT JOIN( SELECT * FROM PedidoPago) AS PEDPAG ON PEDPAG.CPedido=FAC.CPedido  \n");
			sbQuery.append("LEFT JOIN( SELECT * FROM Cobro) AS COPRE ON COPRE.idPedidoPago=PEDPAG.Folio \n");
			sbQuery.append("LEFT JOIN( SELECT * FROM Cobro) AS COPRE2 ON COPRE2.Factura=FAC.Factura AND COPRE2.FPor=FAC.FPor  \n");
			sbQuery.append("WHERE FAC.Factura='").append(factura).append("' AND FAC.fpor='").append(fpor).append("' \n");
			sbQuery.append("AND (FAC.CPago LIKE '%dias%' OR FAC.CPago LIKE 'PAGO CONTRA ENTREGA' OR FAC.Cpago='PAGO INMEDIATO') \n");
			sbQuery.append("ORDER BY RPC.FFin DESC \n");
			sbQuery.append(") Mo \n");
			sbQuery.append("LEFT JOIN(SELECT * FROM Pendiente WHERE Tipo LIKE 'Monitorear cobro') AS PEN ON PEN.Docto= Mo.Factura AND PEN.Partida = mo.FPor \n");
			sbQuery.append("WHERE Mo.Factura='").append(factura).append("' AND Mo.fpor='").append(fpor).append("' \n");			
			log.info (sbQuery.toString());
			
			return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenFacturaRowMapper());

		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facturacion> obtenerResumenFacturacionXAdelantado(
			String factura, String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try{
			StringBuilder sbQuery = new StringBuilder("SELECT 'FPorAdelantado' as Etiqueta, P1.Finicio as FTramitacionPSC, P.FFin as FPorAdelantado, \n");
			sbQuery.append("PE.Pedido, F.Factura, P.Responsable as Facturo, F.TCambio, F.Tipo, COALESCE(F.Medio, 'Fisica') as Medio, PE.DoctoR, \n");
			sbQuery.append("PN.FInicio FIPortal, PN.FFin FFPortal, PN.Responsable Realizo, F.ComprobantePortal, PR.Clave\n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Pendiente P On P.Docto = F.CPedido \n");
			sbQuery.append("LEFT JOIN Pendiente P1 On P1.Docto = F.CPedido AND P1.Tipo = 'PSC sin FEE' \n");
			sbQuery.append("LEFT JOIN Pedidos PE ON PE.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN Pendiente PN ON PN.Docto = F.idFactura AND PN.Tipo = 'SubirFacturaPortal' \n");
			sbQuery.append("LEFT JOIN Proforma PR ON PR.FK02_Pedido =  PE.idPedido \n");
			sbQuery.append("WHERE P.Tipo = 'Facturar por adelantado' \n");
			sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
			


			////log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TiempoProceso> obtenerLineaTiempoPrepago(String factura,
			String fpor, String profor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try {
			StringBuilder sbQuery = new StringBuilder("SELECT '1' as ID, 'Facturación por Adelantado' as ETIQUETA, P.Finicio, \n");
			sbQuery.append("P.FFin, P.Responsable, \n");
			sbQuery.append(funcion.obtenerTiempoTranscurridoSQL("P.FInicio", "P.FFin", "Dias")).append(" as TT \n");
			sbQuery.append("FROM Facturas f \n");
			sbQuery.append("LefT JoiN Pendiente P ON P.Docto = F.Cpedido \n");
			sbQuery.append("WHERE P.Tipo = 'Facturar por adelantado' \n");
			sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
			sbQuery.append("UNION \n");
			if(profor == null){
				sbQuery.append("SELECT '2' as ID, 'envío de Factura' as ETIQUETA, P.FInicio, P.FFin, P.Responsable, \n");
				sbQuery.append(funcion.obtenerTiempoTranscurridoSQL("P.FInicio", "P.FFin", "Dias")).append(" as TT \n");
				sbQuery.append("FROM Facturas F \n");
				sbQuery.append("LEFT JOIN Pendiente P ON P.Docto = F.Factura AND P.Partida = F.CPedido \n");
				sbQuery.append("WHERE P.Tipo = 'Factura por Enviar' \n");
				sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
				sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
				sbQuery.append("UNION \n");
			}
			sbQuery.append("SELECT '3' as ID, 'Monitoreo de Cobro SC' as Etiqueta, MIN(PN.FInicio) PInicio, MAX(P.FFin) FFin, PN.Responsable, \n");
			sbQuery.append(funcion.obtenerTiempoTranscurridoSQL("MIN(PN.FInicio)", "MAX(P.FFin)", "Dias")).append(" as TT \n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Pedidos PD ON PD.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN Pendiente PN ON PN.Docto = PD.DoctoR \n");
			sbQuery.append("LEFT JOIN Pendiente P ON P.Docto = F.CPedido AND P.Tipo = 'Cobro a validar' \n");
			sbQuery.append("WHERE PN.Tipo = 'Pago pendiente' \n");
			sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
			sbQuery.append("GROUP By PN.Responsable \n");
			sbQuery.append("UNION \n");
			sbQuery.append("SELECT '4' as ID, 'Factura' as Etiqueta, MIN(P1.FInicio), MAX(P1.FFin) FFin, P1.Responsable, \n");
			sbQuery.append(funcion.obtenerTiempoTranscurridoSQL("MIN(P1.FInicio)", "MAX(P1.FFin)", "Dias")).append(" as TT \n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Proforma PRO ON PRO.FK01_Factura = F.idFactura \n");
			sbQuery.append("LEFT JOIN Pendiente P1 ON P1.Docto = F.CPedido \n");
			sbQuery.append("WHERE  P1.Tipo = 'Cobro a validar' \n");
			sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
			sbQuery.append("GROUP By P1.Responsable \n");
			sbQuery.append("HAVING MAX(P1.FFin) IS NOT NULL \n");

			log.info("query3: " + sbQuery);
			return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaLineaTiempoResunenRowMapper());
		} catch (Exception e) {
			log.info(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facturacion> obtenerResumenEnvioFactura(String factura,
			String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try{
			StringBuilder sbQuery = new StringBuilder("SELECT DISTINCT(F.Factura), 'EnvioFactura' as Etiqueta, P1.FFin FechaFacturacion, \n");
			sbQuery.append("P.FFin FechaEnvio, P1.Responsable, P.FFin FechaSAP, PP.FFin FechaProquifaNET, \n");
			sbQuery.append("Convert(varchar(Max),C.CuerpoCorreo) CuerpoCorreo, FA.Contacto \n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Pendiente P ON P.Docto = F.Factura AND P.Partida = F.CPedido \n");
			sbQuery.append("LEFT JOIN Correos C ON C.FolioDocumento = F.Factura AND C.FacturadaPor collate Modern_Spanish_CI_AS = F.FPor \n");
			sbQuery.append("LEFT JOIN Pendiente PP ON PP.Docto = F.Factura AND PP.Tipo = 'Facturas por enviar' AND PP.Partida = C.idCorreo \n");
			sbQuery.append("LEFT JOIN Pendiente P1 ON P1.Docto = F.CPedido AND P1.Tipo = 'Facturar por adelantado' \n");
			sbQuery.append("LEFT JOIN FacturaAdela FA ON FA.CPedido = F.CPedido \n");
			sbQuery.append("WHERE P.Tipo = 'Factura por enviar' \n");
			sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");

			////log.info(sbQuery.toString());
			return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());
		}catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facturacion> obtenerResumenMonitoreoCobro(String factura,
			String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try {
			
			StringBuilder sbQuery = new StringBuilder("SELECT DISTINCT(F.Factura), 'MonitoreoSC' as Etiqueta, P1.FInicio FechaTramitacionPSC, \n");
			sbQuery.append("C5.MontoPagado, C5.MonedaPagada , C5.TCPagado, convert(Date,C5.FPago) FPago, C5.FolioPC DocumentoAmpara, IP2.Monto, IP2.Moneda,   \n");
			sbQuery.append("IP2.MedioPago, Convert(varchar(max), C5.ComentariosValidacion) ComentariosValidacion, IP2.FechaPago FEP, \n");
			sbQuery.append("C5.FFIN FechaValidacionCobro, IP2.FechaAsosiacion FechaAsosiacionPago, F.idFactura FolioNC, PR.Clave \n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Pedidos PD ON PD.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN (SELECT P.Docto,P.Partida, MAX(P.FInicio) FInicio, MAX(P.FFin) FFin \n");
			sbQuery.append("\tFROM Pendiente P WHERE P.Tipo = 'Pago pendiente' Group By P.Docto, P.Partida ) PN ON PN.Docto = PD.DoctoR \n");
			sbQuery.append("LEFT JOIN Pendiente P1 ON P1.Docto = F.CPedido AND P1.Tipo = 'PSC sin FEE' \n");
			sbQuery.append("LEFT JOIN (SELECT IP.idInconsistencia, CASE WHEN MAX(P.FInicio) > MAX(P.FFin) THEN NULL ELSE MAX(P.FFin) END FechaAsosiacion, \n");
			sbQuery.append("\tIP.Monto, IP.Moneda, IP.MedioPago, IP.FechaPago FROM Pendiente P \n");
			sbQuery.append("\tLEFT JOIN InconsistenciaDePedido IP ON IP.idInconsistencia = P.Partida WHERE P.Tipo = 'Pago pendiente' \n");
			sbQuery.append("\tGROUP BY IP.idInconsistencia, IP.FechaPago, IP.Moneda, IP.Monto, IP.MedioPago ) IP2 ON IP2.idInconsistencia = PN.Partida AND IP2.FechaAsosiacion IS NOT NULL \n");
			sbQuery.append("LEFT JOIN (SeleCt CASE WHEN MAX(P.FInicio) > MAX(P.FFin) THEN NULL ELSE MAX(P.FFin) END FFIN, C.idPedidoPago, C.MontoPagado, \n");
			sbQuery.append("\tC.MonedaPagada, C.TCPagado, convert(Date,C.FPago) FPago, Convert(varchar(max), PP.ComentariosValidacion) ComentariosValidacion, C.FolioPC, P.Docto \n");
			sbQuery.append("\tFrom Pendiente P LEFT JOIN PedidoPago PP ON PP.CPedido = P.Docto \n");
			sbQuery.append("\tLEFT JOIN Cobro C ON C.idPedidoPago = PP.Folio AND C.idPedidoPago IS NOT NULL \n");
			sbQuery.append("\tWHERE P.Tipo = 'Cobro a validar' AND C.idPedidoPago IS NOt NULL \n");
			sbQuery.append("\tgROUP by C.idPedidoPago, C.MontoPagado, C.MonedaPagada, C.TCPagado, convert(Date,C.FPago), Convert(varchar(max), PP.ComentariosValidacion), \n");
			sbQuery.append("C.FolioPC, P.Docto) C5 ON C5.Docto = F.CPedido \n");
			sbQuery.append("LEFT JOIN Factura_NotaCredito FN ON FN.FK01_Factura = F.idFactura \n");
			sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
			sbQuery.append("LEFT JOIN Proforma PR ON PR.FK02_Pedido =  PD.idPedido \n"); 
			sbQuery.append("WHERE F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
			
			////log.info(sbQuery.toString());
			List<Facturacion> list = super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());
			
			for (Facturacion facturacion : list) {
				sbQuery = new StringBuilder("DECLARE @ListaFolios VARCHAR(MAX) = '' \n");
				sbQuery.append("SELECT @ListaFolios = @ListaFolios +',' + CONVERT(VARCHAR,NC.AK_Folio) FROM Factura_NotaCredito FN  \n");
				sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
				sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FN.FK01_Factura WHERE F.idFactura=").append(facturacion.getFolioNC()).append(" \n");
				sbQuery.append("SET @ListaFolios = SUBSTRING(@ListaFolios,2,LEN(@ListaFolios)) \n");
				sbQuery.append("SELECT @ListaFolios folio ");
				String folio = (String) super.queryForString(sbQuery.toString());
				facturacion.setFolioNC(folio);
			}
			System.err.println("query4: " + sbQuery);
			return list;
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Facturacion> obtenerResumenFacturaPrepago(String factura,
			String fpor) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("factura", factura);
		map.put("fpor", fpor);
		try {
			
			StringBuilder sbQuery = new StringBuilder("DECLARE @ListaFolios VARCHAR(MAX) = '' \n");
			sbQuery.append("SELECT @ListaFolios = @ListaFolios +',' + CONVERT(VARCHAR,NC.AK_Folio) FROM Factura_NotaCredito FN  \n");
			sbQuery.append("LEFT JOIN NotaCredito NC ON NC.PK_Nota = FN.FK02_NotaCredito \n");
			sbQuery.append("LEFT JOIN Facturas F ON F.idFactura = FN.FK01_Factura WHERE F.Factura=").append(factura).append(" AND F.FPor = '").append(fpor).append("' \n");
			sbQuery.append("SET @ListaFolios = SUBSTRING(@ListaFolios,2,LEN(@ListaFolios)) \n");
			
			sbQuery.append("SELECT 'Factura' AS ETIQUETA, F.Fecha FechaFacturacion, F.Factura Factura, P.Responsable Facturo, \n");
			sbQuery.append("ROUND(F.TCambio,2) as TCambio, F.Tipo Tipo, COALESCE(F.Medio,'Fisica') as Medio, P3.FFin as FechaEnvio, \n");
			sbQuery.append("Convert(varchar(Max),C1.CuerpoCorreo) as CuerpoCorreo,  FA.Contacto as Contacto, C.FolioPC DocumentoAmpara, \n");
			sbQuery.append("PP2.FFin AS FechaProquifaNET,  C.MonedaPagada, convert(Date,C.FPago) FPago, ROUND(C.TCPagado,2) TCPagado,  \n");
			sbQuery.append("Convert(varchar(max),PP.ComentariosValidacion) as ComentariosValidacion, ROUND(C.MontoPagado,2) MontoPagado, F.Fpor,  \n");
			sbQuery.append("@ListaFolios FolioNC, PRO.Clave proforma \n");
			sbQuery.append("FROM Facturas F \n");
			sbQuery.append("LEFT JOIN Pendiente P ON P.Docto = f.CPedido \n");
			sbQuery.append("LEFT JOIN PedidoPago PP ON PP.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN Pedidos PD ON PD.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN Cobro C ON C.idPedidoPago = PP.Folio \n");
			sbQuery.append("LEFT JOIN Pendiente P1 ON P1.Docto = F.CPedido AND P1.Tipo = 'PSC sin FEE' \n");
			sbQuery.append("LEFT JOIN InconsistenciaDePedido IP ON IP.Docto = PP.Pedido AND IP.VieneDePSC = 1 \n");
			sbQuery.append("LEFT JOIN Pendiente PN ON PN.Docto = F.CPedido \n");
			sbQuery.append("LEFT JOIN Pendiente P3 ON P3.Docto = F.Factura AND P3.Partida = F.CPedido \n");
			sbQuery.append("LEFT JOIN Correos C1 ON C1.FolioDocumento = F.Factura AND C1.FacturadaPor collate Modern_Spanish_CI_AS = F.FPor \n");
			sbQuery.append("LEFT JOIN Pendiente PP2 ON PP2.Docto = F.Factura AND PP2.Tipo = 'Facturas por enviar' AND PP2.Partida = C1.idCorreo \n");
			sbQuery.append("LEFT JOIN FacturaAdela FA ON FA.CPedido = F.CPedido \n");
			sbQuery.append("LEFT JOIN Proforma PRO ON PRO.FK01_Factura = F.idFactura \n");
			sbQuery.append("WHERE P.Tipo = 'Cobro a validar' \n");
			sbQuery.append("AND P3.Tipo = 'Factura por enviar' \n");
			sbQuery.append("AND F.Factura = '").append(factura).append("' \n");
			sbQuery.append("AND F.Fpor = '").append(fpor).append("' \n");
			sbQuery.append("AND MonedaPagada IS Not NuLL  \n");
			sbQuery.append("GRouP By F.Factura, P1.FInicio, C.MontoPagado, C.MonedaPagada, C.TCPagado, C.FPago, C.FolioPC, IP.Monto, \n");
			sbQuery.append("IP.Moneda, IP.MedioPAgo, Convert(varchar(max), PP.ComentariosValidacion), IP.FechaPago, F.Fecha, F.Tipo, \n");
			sbQuery.append("F.TCambio, P.Responsable, F.Medio,P3.FFin, Convert(varchar(Max),C1.CuerpoCorreo), fA.Contacto, PP2.FFin, F.Fpor , PRO.Clave\n");
			
			////log.info(sbQuery.toString());
			log.info("query5: " + sbQuery);
			return super.jdbcTemplate.query(sbQuery.toString(),map, new ConsultaFacturacionResumenRowMapper());

		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return null;
	}
	
	
}
