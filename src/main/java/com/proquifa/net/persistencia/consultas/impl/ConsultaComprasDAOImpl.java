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

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.consultas.comun.ResumenConsulta;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.consultas.ConsultaComprasDAO;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaComprasRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaComprasTiempoProcesoRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaHistorialPhsMatrizRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ObtenerPatidasCompraParaCEspecificaRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ResumenConsultaRowMapper;

/**
 * @author vromero
 *
 */
@Repository
public class ConsultaComprasDAOImpl extends DataBaseDAO implements ConsultaComprasDAO {
	
	final Logger log = LoggerFactory.getLogger(ConsultaComprasDAOImpl.class);
	
	private Funcion funcion; 
	
	@SuppressWarnings("unchecked")
	public List<Compra> findCompras(String periodo, String proveedor,
			String estado, String ordenCompra, String usuario, String empresaCompra) {
		funcion = new Funcion();
		String vWHERE = new String();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", periodo);
			map.put("proveedor", proveedor);
			map.put("estado", estado);
			map.put("ordenCompra", ordenCompra);
			map.put("usuario", usuario);
			map.put("empresaCompra", empresaCompra);
			
		if(!periodo.equals("") || !proveedor.equals("") || !estado.equals("") || !empresaCompra.equals("") || !ordenCompra.equals("") || !usuario.equals("") ){
			vWHERE = " WHERE ";
		}
		
		String query = 	" SELECT PROV.Nombre AS PROVEDOR,PC.Fabrica AS MARCA, C.Clave AS OC, C.ColocarDesde AS COLOCO, C.FacturarA AS COMPRO, C.Fecha AS FCONFIRMACION, C.FColocacion, " +
						"\n EM.Usuario AS COMPRADOR, COALESCE(PC.TotalPiezas,0) AS TotalPiezas,  COALESCE(" +
							funcion.sqlConversionMonedasADolar("C.Moneda", "1", "PC.MONTOTOTAL", "MON", "","",false) + 
						"\n , 0) AS MONTOTOTAL, CASE WHEN EDO.T = 0 OR C.Estado = 'Cancelada' THEN 'CERRADA' ELSE 'ABIERTA' END AS ABIERTO_CERRADO,  " +
						"\n PC.PARTIDA AS PARTIDAS,CASE WHEN (DATEDIFF(DAY, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio),PP.FPEntrega)>=FEPED.FECHA ) " +
						"\n OR (DATEDIFF(DAY, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio),PP.FPEntrega) IS NULL ) OR (PP.FPEntrega IS NULL) THEN 'ET' ELSE 'FT'  END AS ET," +
						"\n PC.Estado, CANT.MONTO,CANT.PIEZAS " +
						"\n FROM Compras AS C " +
						"\n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
						"\n LEFT JOIN (SELECT Estado,Fabrica,Codigo,PPedido,CPedido,FPharma,FEEntrega,idPCompra,SUM(PC.Cant)as TotalPiezas, SUM(PC.Cant*PC.Costo) AS MONTOTOTAL,COUNT(PC.idPCompra) AS PARTIDA, PC.Compra FROM PCompras AS PC  " +
						"\n GROUP BY Estado,Fabrica,Codigo,PPedido,CPedido,FPharma,PC.Compra,PC.idPCompra,FEEntrega) AS PC ON C.Clave=PC.Compra " +
						"\n LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha) " +
						"\n LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC " +
						"\n			LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END " +
						"\n			ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido " +
						"\n			GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave " +
						"\n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado  " +
						"\n LEFT JOIN (SELECT COUNT(PC.idPCompra) AS PARTIDAS, Compra FROM PCompras AS PC GROUP BY Compra) AS PARTIDAS ON PARTIDAS.Compra = C.Clave " +
						"\n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
						"\n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
						"\n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra  " +
						"\n LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PC.CPedido AND PP.Part = PC.PPedido AND PP.Codigo = PC.Codigo " +
						"\n LEFT JOIN (SELECT CASE WHEN DATENAME(weekday, COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Lunes' THEN 4  WHEN DATENAME(weekday,COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Martes' THEN 4 ELSE 2 END AS FECHA,PP.CPedido,Part,PC.FEEntrega,pc.idPCompra    " + 
						"\n FROM PPedidos AS PP LEFT JOIN (SELECT * FROM PCompras ) AS PC ON PC.CPedido=PP.CPedido AND PC.PPedido = PP.Part" +
						"\n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
						"\n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
						"\n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra   " +
						"\n WHERE FEEntrega IS NOT NULL ) AS FEPED ON FEPED.idPCompra=pc.idPCompra  " +
						"\n LEFT JOIN (SELECT C.Clave, SUM(" + 
							funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) +
						"\n ) AS MONTO, SUM(PC.Cant)AS PIEZAS,C.Monto REAL,C.Moneda,MON.PDolar,MON.LDolar FROM Compras AS C " +
						"\n LEFT JOIN (SELECT * FROM Monedas)AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)	" +
						"\n LEFT JOIN (SELECT * FROM PCompras)AS PC ON PC.Compra=C.Clave" +
						"\n GROUP BY C.Clave, C.Monto,C.Moneda, MON.PDolar,MON.LDolar,MON.EDolar) AS CANT ON CANT.Clave=C.Clave  " +
						vWHERE + periodo + " " + proveedor + " " + estado + " " + empresaCompra + " " + ordenCompra + " " + usuario + " ORDER BY C.Fecha, PC.Compra ";
				//		logger.info(query);
		return super.jdbcTemplate.query(query, map,new ConsultaComprasRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e,"\nPeriodo: "+periodo,"\nProveedor: "+proveedor, "\nEstado: "+estado, "\nOrdenCompra: "+ordenCompra,"\nUsuario: "+ usuario,"\nEmpresaCompra: "+ empresaCompra);
			return new ArrayList<Compra>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<PartidaCompra> obtenerPatidasCompraParaCEspecifica(
			String ordenCompra) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ordenCompra", ordenCompra);
			
			
		String query = 	" SELECT PC.idPCompra, PC.Compra, PC.Partida, PC.Cant AS PIEZAS, PC.Cant * PC.Costo AS MONTO, COALESCE(PRO.Codigo, COM.Codigo) AS Codigo, " +
						" PRO.Cantidad, COM.idComplemento, PRO.Unidad, COALESCE(PRO.Concepto, COM.Descripcion) AS CONCEPTO," +
						" COALESCE(PRO.Fabrica, COM.Fabrica) AS Fabrica,  PRO.Pureza, COALESCE(PRO.Tipo,PRODC.Tipo) AS TIPO," +
						" PP.FPEntrega AS FEE, PC.Costo AS PRECIOUNITARIO, PP.Tipo AS FLETE," +
						" CASE WHEN PP.Stock IS NULL OR PP.Stock = 0 OR ST.cantDisponible=0 THEN COALESCE(CLI.Nombre,'CLIENTE')ELSE 'STOCK' END AS CLIENTE, PC.Estado,"+
						" CASE WHEN PC.Estado LIKE P.ESTADO  OR PC.ESTADO = 'Cancelada' THEN 0 ELSE CASE WHEN(P.ESTADO = 'Recibido' and p.Pais IS Null) THEN 0 " +
						" ELSE CASE WHEN (Pc.Estado <> 'Recibido') THEN 1 ELSE CASE WHEN p.ESTADO IS NULL THEN 0 END END END END AS ABIERTO,"+
						" C.Moneda, COALESCE(P.CPedido,PS.CPedido, '') AS CPEDIDO,COALESCE(pAC.Num,'0') AS AvisoDCambios" +
						" FROM PCompras AS PC" +
						" LEFT JOIN (SELECT COUNT(Folio) AS Num,Docto,Partida FROM Pendiente WHERE Tipo = 'Aviso de cambios' GROUP BY Docto,Partida) AS pAC ON pAC.Docto=pc.Compra AND pAC.Partida=PC.Partida" +
						" LEFT JOIN (SELECT Clave, Moneda FROM Compras) AS C ON C.Clave = PC.Compra " +
						" LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
						" LEFT JOIN (SELECT * FROM Complemento) AS COM ON COM.idComplemento = PC.idComplemento" +
						" LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PC.CPedido AND PP.Part = PC.PPedido AND PP.Codigo = PC.Codigo" +
						" LEFT JOIN (SELECT * FROM Stock) AS ST ON PC.idPCompra = ST.idPCompra"+
						" LEFT JOIN (SELECT * FROM MovimientoStock) AS MOVS ON ST.idStock = MOVS.idStock" +
						" LEFT JOIN (SELECT * FROM PPedidos) AS PS ON MOVS.idPPedido=PS.idPPedido" +
						" LEFT JOIN (SELECT * FROM Pedidos) AS PED ON PED.CPedido = PC.CPedido " +
						" LEFT JOIN (SELECT * FROM Clientes) AS CLI ON CLI.Clave=PED.idCliente "+
						" LEFT JOIN (SELECT Codigo, Fabrica, Tipo FROM Productos)AS PRODC ON PRODC.Codigo = COM.Codigo AND PRODC.Fabrica = COM.Fabrica" +
						" LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
						"			ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido PHS' ELSE 'Recibido' END END AS ESTADO, P.CPedido, P.Pais FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PP.CPedido" +
						" WHERE PC.Compra = '"+ ordenCompra +"'" +
						" ORDER BY PC.Partida" ;
				//logger.info(query);
		return super.jdbcTemplate.query(query,map, new ObtenerPatidasCompraParaCEspecificaRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nOrdenCompra: "+ordenCompra);
			return new ArrayList<PartidaCompra>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<TiempoProceso> obtenerTiempoProcesoPorPartida(Long idPCompra) {
		funcion = new Funcion();//" + funcion.obtenerTiempoTranscurridoSQL("", "", "Dias") + " 
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			
		String query = 	 " \n  declare @idPCompra int = " + idPCompra +
						 " \n SELECT 'TRAMITACIÓN' AS ETAPA, 1 AS NIVEL, 1 AS ID, 3 AS PADRE," + 
						 " \n NULL AS FINICIO, C.Fecha AS FFIN, C.Contacto AS CONTACTO, COALESCE(PEN1.Responsable, PEN2.Responsable) AS RESPONSABLE, PC.Compra AS REFERENCIA," +  
						 " \n PROV.Nombre AS PROVEEDOR, C.Empresa QUIENCOMPRA, '' AS COMENTARIOS, 0  AS TT, " +
						 " \n NULL AS FEA, NULL AS FRA, '' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE, '' AS MANEJO, '' AS IDIOMA, '' AS EDICION, '' AS DOCUMENTACION, " +
						 " \n'' AS ETIQUETAS, '' AS DESPACHABLES,  '' NODESPACHABLES, '' PEDIMENTO,trafico.TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento, '' TotalPiezas , '' PiezasFaltantes, '' PiezasSobrantes, '' FechaProxGestion, '' Respuesta, '' idPAviso, '' AS EntregarEn, '' FRDESPACHO  " +
						 " \n FROM PCompras AS PC " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" + 
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC a colocar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN1 ON PEN1.Docto = C.Clave" + 
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN2 ON PEN2.Docto = C.Clave" + 
						 " \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON PROV.Clave = C.Provee" +
						 " \n LEFT JOIN (SELECT (CASE WHEN AlmacenUSA=1 AND AlmacenMatriz=1 THEN 'Almacén USA, Almacén Matriz' WHEN AlmacenUSA=1 AND AlmacenMatriz=0 THEN 'Almacén USA'" +
						 " \n WHEN AlmacenUSA=0 AND AlmacenMatriz=1 THEN 'Almacén Matriz' WHEN AlmacenUSA=0 AND AlmacenMatriz=0 THEN 'Ninguno' END) AS TRAFICO,Clave FROM Compras) AS trafico ON trafico.Clave=c.Clave" +
						 " \n WHERE C.Fecha IS NOT NULL  AND PC.idPCompra = @idPCompra" +
						 " \n UNION" +
						 " \n SELECT 'COLOCACIÓN' AS ETAPA, 1 AS NIVEL, 2 AS ID, 3 AS PADRE," + 
						 " \n PEN.FInicio AS FINICIO, PEN.FFin AS FFIN, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, '' AS REFERENCIA, '' AS PROVEEDOR," + 
						 " \n '' QUIENCOMPRA, '' AS COMENTARIOS, " + funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "PEN.FFin", "Dias") + " AS TT, NULL AS FEA, NULL AS FRA,  " +
						 " \n '' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE, '' AS MANEJO, '' AS IDIOMA, '' AS EDICION, '' AS DOCUMENTACION, '' AS ETIQUETAS, '' AS DESPACHABLES, " +
						 " \n '' NODESPACHABLES, '' PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '', '' FRDESPACHO  " +
						 " \n FROM PCompras AS PC " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" + 
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC a colocar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave" + 
						 " \n WHERE PEN.FInicio IS NOT NULL AND PC.idPCompra = @idPCompra" +
						 " \n UNION" +
						 " \n SELECT TOP 1 'CONFIRMACIÓN' AS ETAPA, 1 AS NIVEL, 2 AS ID, 3 AS PADRE," +
						 " \n PEN.FInicio AS FINICIO, PEN.FFin AS FFIN, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, '' AS REFERENCIA,  '' AS PROVEEDOR," + 
						 " \n '' QUIENCOMPRA, (C.ComentariosConfirmacion COLLATE SQL_Latin1_General_CP1_CI_AS) AS COMENTARIOS, " + 
						   funcion.obtenerTiempoTranscurridoSQL("PEN.FInicio", "PEN.FFin", "Dias") + " AS TT, NULL AS FEA, NULL AS FRA," +  
						 " \n  '' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE, '' AS MANEJO, '' AS IDIOMA, '' AS EDICION, '' AS DOCUMENTACION, '' AS ETIQUETAS, " +
						 " \n '' AS DESPACHABLES, '' NODESPACHABLES, '' PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '' , '' FRDESPACHO  " +
						 " \n FROM PCompras AS PC " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" + 
						 " \n LEFT JOIN (SELECT DISTINCT Docto, FInicio, FFin, Responsable FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave" + 
						 " \n WHERE PEN.FInicio IS NOT NULL AND PC.idPCompra = @idPCompra " +
						 " \n UNION " +
						 
						  " \n SELECT TOP 1 'TRÁNSITO PHS' AS ETAPA , 1 AS NIVEL, 5 AS ID, 3 AS PADRE ," +
						 " \n CASE WHEN C.AlmacenUSA=1 THEN COALESCE(PEN2.FInicio,PEN.FFin) END AS FINICIO, CASE WHEN C.Fecha >= '20100531' THEN EPC1.FInicio ELSE C.FECHA END AS FFIN, " +
						 " \n '' AS CONTACTO,  COALESCE(PEN.Responsable, 'InsPHS-USA') AS RESPONSABLE, '' AS REFERNCIA, " +
						 " \n '' AS PROVEEDOR, '' QUIENCOMPRA, '' AS COMENTARIOS, " + 
						   funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEN.FFin, PEN2.FInicio, C.FECHA)", "CASE WHEN C.Fecha >= '20100531' THEN EPC1.FInicio ELSE C.FECHA END", "Dias") + "  AS TT, " +
						 " \n PC.FPharma AS FEA, EPC1.FInicio AS FRA, '' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE, '' AS MANEJO, '' AS IDIOMA, '' AS EDICION, '' AS DOCUMENTACION, " +
						 " \n '' AS ETIQUETAS, '' AS DESPACHABLES, '' NODESPACHABLES, '' PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '', '' FRDESPACHO  " +
						 " \n FROM PCompras AS PC" +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave" +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Monitorear OC' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN2 ON PEN2.Docto = C.Clave" +
						 " \n LEFT JOIN (SELECT idPCompra, FInicio FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EPC1 ON EPC1.idPCompra = PC.idPCompra " +
						 " \nLEFT JOIN (SELECT * FROM PCompras ) AS PCO ON PCO.idPCompra = C.PK_Compra " +
						 " \n LEFT JOIN (SELECT * FROM PAviso_Cambios) AS PAVC ON PAVC.FK01_PCompras = PCO.idPCompra " +
						 " \nLEFT JOIN (SELECT * FROM Aviso_Cambios) AS AVC ON AVC.PK_AvisoCambios = PAVC.PK_PAviso_Cambios " +
						 " \n WHERE C.AlmacenUSA = 1 AND PC.idPCompra = @idPCompra AND (CASE WHEN C.Fecha >= '20100531' THEN EPC1.FInicio ELSE C.FECHA END IS NOT NULL OR COALESCE(PEN.FFin, PEN2.FInicio, C.FECHA) IS NOT NULL ) " +
						 " \n UNION" +
						 
						 " \n SELECT 'AVISO CAMBIOS' AS ETAPA, 2 AS NIVEL, 12 AS ID, 5 AS PADRE, AVC.Fecha AS FINICIO, PEN.FFin AS FFIN, CON.Contacto,'' RESPONSABLE , AVC.Folio collate SQL_Latin1_General_CP1_CI_AS AS REFERENCIA, AVC.Origen  AS PROVEEDOR,AVC.GestorOC collate SQL_Latin1_General_CP1_CI_AS AS QUIENCOMPRA,PAVC.Justificacion collate SQL_Latin1_General_CP1_CI_AS AS COMENTARIOS, 0  AS TT,'' FEA,'' FRA,'' CADU_MES,''CADU_ANO, '' AS LOTE,'' MANEJO,'' IDIOMA,'' EDICION, '' DOCUMENTACION, '' ETIQUETAS, '' DESPACHABLES,'' NODESPACHABLES, '' PEDIMENTO,'' TRAFICO, '' GESTORAGENTE,'' FechaEstimadaArribo,'' FechaSalidaAduana, '' PaisProveedor, '' idCompra , PAVC.Tipo collate SQL_Latin1_General_CP1_CI_AS as Tipo,'' Fletera, '' OrdenDespacho,'' Aduana, '' agenteAduanal,'' GuiaEmbarque,'' FechaPlanificar,'' Folio,'' FechaPedimento,'','','', PAVC.FProxGestion AS FechaProxGestion, PAVC.esperaCliente As Respuesta, FK01_PCompras AS idPAviso, '' , '' FRDESPACHO  " + 
						 " \nFROM PCompras AS PC " +
						 " \n LEFT JOIN PAviso_Cambios AS PAVC ON PAVC.FK01_PCompras = PC.idPCompra " +  
						 " \nLEFT JOIN Aviso_Cambios as AVC ON AVC.PK_AvisoCambios = PAVC.FK02_Aviso_Cambios " + 
						 " \n LEFT JOIN contactos AS CON ON CON.idContacto = AVC.FK01_Contacto  " +
						 " \nLEFT JOIN (SELECT * FROM Pendiente where tipo='Gestionar Aviso de Cambios')  as PEN ON PEN.Docto collate Modern_Spanish_CI_AS= AVC.Folio collate Modern_Spanish_CI_AS " +
						 " \n WHERE PC.idPCompra = @idPCompra " +
						 " \n UNION" +
												 
						 " \n SELECT 'AVISO CAMBIOS' AS ETAPA, 2 AS NIVEL, 12 AS ID, 7 AS PADRE, AVC.Fecha AS FINICIO, PEN.FFin AS FFIN, CON.Contacto,'' RESPONSABLE , AVC.Folio collate SQL_Latin1_General_CP1_CI_AS AS REFERENCIA, AVC.Origen  AS PROVEEDOR,AVC.GestorOC collate SQL_Latin1_General_CP1_CI_AS AS QUIENCOMPRA,PAVC.Justificacion collate SQL_Latin1_General_CP1_CI_AS AS COMENTARIOS, 0  AS TT,'' FEA,'' FRA,'' CADU_MES,''CADU_ANO, '' AS LOTE,'' MANEJO,'' IDIOMA,'' EDICION, '' DOCUMENTACION, '' ETIQUETAS, '' DESPACHABLES,'' NODESPACHABLES, '' PEDIMENTO,'' TRAFICO, '' GESTORAGENTE,'' FechaEstimadaArribo,'' FechaSalidaAduana, '' PaisProveedor, '' idCompra , PAVC.Tipo collate SQL_Latin1_General_CP1_CI_AS as Tipo,'' Fletera, '' OrdenDespacho,'' Aduana, '' agenteAduanal,'' GuiaEmbarque,'' FechaPlanificar,'' Folio,'' FechaPedimento,'','','', PAVC.FProxGestion AS FechaProxGestion, PAVC.esperaCliente As Respuesta, FK01_PCompras AS idPAviso, '' , '' FRDESPACHO  " + 
							 " \nFROM PCompras AS PC " +
							 " \n LEFT JOIN PAviso_Cambios AS PAVC ON PAVC.FK01_PCompras = PC.idPCompra " +  
							 " \nLEFT JOIN Aviso_Cambios as AVC ON AVC.PK_AvisoCambios = PAVC.FK02_Aviso_Cambios " + 
							 " \n LEFT JOIN contactos AS CON ON CON.idContacto = AVC.FK01_Contacto  " +
							 " \nLEFT JOIN (SELECT * FROM Pendiente where tipo='Gestionar Aviso de Cambios')  as PEN ON PEN.Docto collate Modern_Spanish_CI_AS= AVC.Folio collate Modern_Spanish_CI_AS " +
							 " \n WHERE PC.idPCompra = @idPCompra " +
    
                        
                         " \n UNION" +
						 " \n SELECT TOP 1 'INSPECCIÓN PHS' AS ETAPA, 2 AS NIVEL, 6 AS ID, 5 AS PADRE," +
						 " \n CASE WHEN C.Fecha >= '20100531' THEN EPC1.FInicio ELSE C.FECHA END AS FINICIO, CASE WHEN C.Fecha >= '20100531' THEN EPC1.FFin ELSE C.FECHA END AS FFIN, " +
						 " \n '' AS CONTACTO, COALESCE(PEN.Responsable, 'InsPHS-USA') AS RESPONSABLE, PL.FolioPacking AS REFERENCIA, '' AS PROVEEDOR," +
						 " \n '' QUIENCOMPRA, '' AS COMENTARIOS, " + 
						 funcion.obtenerTiempoTranscurridoSQL("CASE WHEN C.Fecha >= '20100531' THEN EPC1.FInicio ELSE C.FECHA END", "CASE WHEN C.Fecha >= '20100531' THEN EPC1.FFin ELSE C.FECHA END", "Dias") + "  AS TT, " +
						 " \n EPC1.FFin AS FEA, COALESCE(EPC1.FInicio, C.Fecha) AS FRA, PIEZAS.MesCaducidad AS CADU_MES, PIEZAS.AnoCaducidad AS CADU_ANO, PIEZAS.LoteTxt AS LOTE," +
						 " \n PRO.Manejo AS MANEJO, PIEZAS.Idioma AS IDIOMA, PIEZAS.Edicion AS EDICION, PIEZAS.Documento AS DOCUMENTACION, CAST( PC.idPCompra  AS VARCHAR (20))AS ETIQUETAS, " +
						 " \n DESPA.SD AS DESPACHABLES, NODESPA.ND NODESPACHABLES, INS.Pedimento AS PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '', '' FRDESPACHO  " +
						 " \n FROM PCompras AS PC" +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
						 " \n LEFT JOIN (SELECT idPCompra, FInicio, FFin FROM EstadoPCompra WHERE Tipo='En inspección Phs' ) AS EPC1 ON EPC1.idPCompra = PC.idPCompra" +
						 " \n LEFT JOIN (SELECT DISTINCT IdPCompra, LoteTxt, MesCaducidad, AnoCaducidad, Documento, Idioma, Edicion FROM PiezaPHS) AS PIEZAS ON PIEZAS.IdPCompra = PC.idPCompra" +
						 " \n LEFT JOIN (SELECT idPCompra, Pedimento, Idioma, Edicion FROM InspeccionOCPhs) AS INS ON INS.idPCompra = PC.idPCompra" +
						 " \n LEFT JOIN (SELECT Codigo, Fabrica, Tipo, Manejo FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
						 " \n LEFT JOIN (SELECT COUNT(IdPCompra) AS SD, IdPCompra FROM PiezaPHS WHERE Despachable = 1 GROUP BY IdPCompra) AS DESPA ON DESPA.IdPCompra = PC.idPCompra" +
						 " \n LEFT JOIN (SELECT COUNT(IdPCompra) AS ND, IdPCompra FROM PiezaPHS WHERE Despachable = 0 GROUP BY IdPCompra) AS NODESPA ON NODESPA.IdPCompra = PC.idPCompra" +
						 " \n LEFT JOIN (SELECT * FROM  PackingListFolioInspeccion WHERE FolioPacking LIKE '%PHS') AS PL ON PL.FolioInspeccion = PC.FolioInspeccionPhs" +
						 " \n LEFT JOIN (SELECT Docto, Responsable FROM Pendiente, Empleados WHERE Tipo = 'OC a inspeccionar' AND Responsable = Usuario AND FK01_Funcion = 34)AS PEN ON PEN.Docto = PC.Compra" +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Monitorear OC' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PENMON ON PENMON.Docto = C.Clave  " +
						 " \n WHERE C.AlmacenUSA = 1  AND PC.Estado <> 'En tránsito PhS-USA' AND PC.idPCompra = @idPCompra AND (CASE WHEN C.Fecha >= '20100531' THEN EPC1.FInicio ELSE C.FECHA END IS NOT NULL OR  PC.Estado='En AC') " +
						 " \n UNION " +
						 
						 " \n SELECT 'TRÁNSITO MATRIZ' AS ETAPA, 1 AS NIVEL, 7 AS ID, 3 AS PADRE, " +
						 " \n COALESCE(CASE WHEN C.AlmacenUSA = 0 THEN PEN.FFin ELSE EPC2.FFin END,C.FECHA) AS FINICIO, COALESCE(EPC1.FInicio, INS.Fecha) AS FFIN, " +
						 " \n '' AS CONTACTO, PEN.Responsable AS RESPONSABLE, FEPC1.FolioEntrega AS REFERNCIA, '' AS PROVEEDOR, '' QUIENCOMPRA, '' AS COMENTARIOS, " + 
						   funcion.obtenerTiempoTranscurridoSQL("COALESCE(CASE WHEN C.AlmacenUSA = 0 THEN PEN.FFin ELSE EPC2.FFin END,C.FECHA)", "COALESCE(EPC1.FInicio, INS.Fecha) ", "Dias") + "  AS TT, PC.FProquifa AS FEA," +
						 " \n EPC1.FInicio AS FRA, '' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE, '' AS MANEJO, '' AS IDIOMA, '' AS EDICION, '' AS DOCUMENTACION, '' AS ETIQUETAS," +
						 " \n '' AS DESPACHABLES, '' NODESPACHABLES, ''PEDIMENTO,'' AS TRAFICO,(CASE WHEN gsAg.Trafico IS NULL THEN 'Pendiente' ELSE gsAg.Trafico END) AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '', '' FRDESPACHO   " +
						 " \n FROM PCompras AS PC " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra  " +
						 " \n LEFT JOIN (SELECT * FROM PEDIDOS) AS PED ON PED.CPEDIDO=PC.CPEDIDO" +
						 " \n LEFT JOIN (SELECT idPCompra, FInicio FROM EstadoPCompra WHERE Tipo='En inspección') AS EPC1 ON EPC1.idPCompra = PC.idPCompra  " +
						 " \n LEFT JOIN (SELECT idPCompra, FFin FROM EstadoPCompra WHERE Tipo='En inspección Phs' ) AS EPC2 ON EPC2.idPCompra = PC.idPCompra  " +
						 " \n LEFT JOIN (SELECT Docto, FFin, Responsable FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave  " +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Monitorear OC' ) AS PEN2 ON PEN2.Docto=C.Clave " +
						 " \n LEFT JOIN (SELECT Fecha, idPCompra FROM InspeccionOC) AS INS ON INS.idPCompra = PC.idPCompra  " +
						 " \n LEFT JOIN (SELECT MAX (ID) AS ID,idPCompra FROM FolioEntregaPcompra  GROUP BY idPCompra) AS FEPC ON FEPC.idPCompra = PC.idPCompra " +
					     " \n LEFT JOIN (SELECT ID,FolioEntrega FROM FolioEntregaPcompra ) AS FEPC1 ON FEPC1.Id = FEPC.Id " +
					     " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Evaluar envío') AS PEND ON PEND.Docto=PC.CPEDIDO " +
						 " \n LEFT JOIN (SELECT COALESCE(Trafico,'No disponible') AS Trafico,idPCompra FROM PPedidos,PCompras WHERE PPedidos.CPedido=PCompras.CPedido AND PPedidos.Part=PCompras.PPedido) AS gsAg ON gsAg.idPCompra=pc.idPCompra " +
						 " \n WHERE PC.idPCompra = @idPCompra AND C.AlmacenMatriz = 1 AND CASE WHEN C.AlmacenUSA = 0 THEN PEN.FFin ELSE EPC2.FFin END IS NOT NULL " +
						 " \n AND PEND.folio IS NULL AND PEN2.FInicio IS NOT NULL " +
						 " \n UNION " +
						 " \n SELECT TOP 1 'INSPECCIÓN MATRIZ' AS ETAPA, 2 AS NIVEL, 8 AS ID, 7 AS PADRE," + 
						 " \n COALESCE(EPC1.FInicio, INS.Fecha) AS FINICIO, COALESCE(EPC1.FFin, INS.Fecha) AS FFIN, '' AS CONTACTO, " + 
						 " \n PEN.Responsable AS RESPONSABLE, PL.FolioPacking AS REFERENCIA, '' AS PROVEEDOR, " +
						 " \n '' QUIENCOMPRA, '' AS COMENTARIOS, " + funcion.obtenerTiempoTranscurridoSQL("COALESCE(EPC1.FInicio, C.Fecha)", "COALESCE(EPC1.FFin, C.FECHA)", "Dias") + " AS TT, " +
						 " \n EPC1.FFin AS FEA, COALESCE(EPC1.FInicio, C.Fecha) AS FRA, PIEZAS.MesCaducidad AS CADU_MES, PIEZAS.AnoCaducidad AS CADU_ANO, PIEZAS.LoteTxt AS LOTE," +
						 " \n PRO.Manejo AS MANEJO, PIEZAS.Idioma AS IDIOMA, PIEZAS.Edicion AS EDICION, PIEZAS.Documento AS DOCUMENTACION, " +
						 " \n ('FD-' + PC.CPedido + '-' + CAST(PC.PPedido AS VARCHAR(10)) COLLATE Modern_Spanish_CI_AS)  AS ETIQUETAS," +
						 " \n DESPA.SD AS DESPACHABLES, NODESPA.ND NODESPACHABLES, INS.Pedimento PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '', '' FRDESPACHO  " +
						 " \n FROM PCompras AS PC " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" + 
						 " \n LEFT JOIN (SELECT idPCompra, FInicio, FFin FROM EstadoPCompra WHERE Tipo='En inspección' ) AS EPC1 ON EPC1.idPCompra = PC.idPCompra" + 
						 " \n LEFT JOIN (SELECT DISTINCT IdPCompra, LoteTxt, MesCaducidad, AnoCaducidad, Documento, Idioma, Edicion FROM Pieza) AS PIEZAS ON PIEZAS.IdPCompra = PC.idPCompra" + 
						 " \n LEFT JOIN (SELECT idPCompra, Pedimento, Idioma, Edicion, Fecha FROM InspeccionOC) AS INS ON INS.idPCompra = PC.idPCompra " +
						 " \n LEFT JOIN (SELECT Codigo, Fabrica, Tipo, Manejo FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" + 
						 " \n LEFT JOIN (SELECT COUNT(IdPCompra) AS SD, IdPCompra FROM Pieza WHERE Despachable = 1 GROUP BY IdPCompra) AS DESPA ON DESPA.IdPCompra = PC.idPCompra" + 
						 " \n LEFT JOIN (SELECT COUNT(IdPCompra) AS ND, IdPCompra FROM Pieza WHERE Despachable = 0 GROUP BY IdPCompra) AS NODESPA ON NODESPA.IdPCompra = PC.idPCompra" + 
						 " \n LEFT JOIN (SELECT * FROM  PackingListFolioInspeccion WHERE FolioPacking NOT LIKE '%PHS') AS PL ON PL.FolioInspeccion = PC.FolioInspeccionPhs" +
						 " \n LEFT JOIN (SELECT Docto, Responsable FROM Pendiente, Empleados WHERE Tipo = 'OC a inspeccionar' AND Responsable = Usuario AND FK01_Funcion = 11) AS PEN ON PEN.Docto = PC.Compra" +
						 " \n WHERE C.AlmacenMatriz = 1 AND  (EPC1.FInicio IS NOT NULL OR  PC.Estado='En AC' ) AND PC.idPCompra = @idPCompra " +
						 " \n UNION" +
						 " \n SELECT CASE WHEN (PCH.Fecha >=(CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) OR ((CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) IS NULL)) THEN 'AVISO_MATRIZ' ELSE  'AVISO_PHS' END AS ETAPA, " + 
						 " \n 2 AS NIVEL, " +
						 " \n CASE WHEN (PCH.Fecha >=(CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) OR ((CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) IS NULL)) THEN '11' ELSE '10' END AS ID, " +
						 " \n CASE WHEN (PCH.Fecha >=(CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) OR ((CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) IS NULL)) THEN '7' ELSE '5' END AS PADRE, " +
						 " \n NOTI.Fecha AS FINICIO, COALESCE(PCH2.Fecha,PCH3.Fecha ) AS FFIN, NOTI.Contacto, NOTI.Creador, NOTI.idNotificado,  NOTI.Origen COLLATE Modern_Spanish_CI_AS  AS PROVEEDOR,  " +
						 " \n GES.Gestiono, NOTI.Razon, 0 AS TT, NULL AS FEA, NULL AS FRA, '' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE,  COALESCE(GES.Decision,   " +
						 " \n CASE WHEN COALESCE(PCH2.Fecha,PCH3.Fecha ) = PCH3.Fecha THEN NULL ELSE 'PENDIENTE' END) AS MANEJO, '' AS IDIOMA,  '' AS EDICION,   CAST(PCH.Tipo AS VARCHAR(100)) COLLATE SQL_Latin1_General_CP1_CI_AS AS DOCUMENTACION, " + 
						 " \n PCH.Comentarios AS ETIQUETAS, '' AS DESPACHABLES, '' NODESPACHABLES, GES.idNotRespuesta AS PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento,  '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '' , '' FRDESPACHO   " +
						 " \n FROM PCompras AS PC  " +
						 " \n LEFT JOIN (SELECT DISTINCT FolioNT, idCompra, idPCompra, Tipo, Comentarios,Fecha FROM PCompraHistorial WHERE Tipo <> 'Aviso de cambios, se espera el cliente'  AND Tipo <> 'Aviso de cambios, cancelada') AS PCH ON PCH.idCompra = PC.Compra AND PCH.idPCompra = PC.Partida " +   
						 " \n LEFT JOIN (SELECT DISTINCT FolioNT, Fecha FROM PCompraHistorial WHERE Tipo = 'Aviso de cambios, se espera el cliente' OR Tipo = 'Aviso de cambios, cancelada') AS PCH2 ON PCH2.FolioNT = PCH.FolioNT  " +
						 " \n LEFT JOIN (SELECT DISTINCT FolioNT, Fecha FROM PCompraHistorial WHERE Tipo = 'Cancelada') AS PCH3 ON PCH3.FolioNT = PCH.FolioNT  " +
						 " \n LEFT JOIN (SELECT * FROM GESTIONAC) AS GES ON GES.idNotificado = PCH.FolioNT  " +
						 " \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo LIKE 'En inspección Phs' ) AS EDOPC ON EDOPC.idPCompra = PC.idPCompra " + 
						 " \n LEFT JOIN (SELECT * FROM Notificado WHERE Tipo = 'Aviso de cambios') AS NOTI ON NOTI.idNotificado = PCH.FolioNT  " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave=PC.Compra  " +
						 " \n LEFT JOIN (SELECT Docto, FFin, Responsable FROM Pendiente WHERE Tipo = 'Alistar envío compra internacional' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PENALI ON PENALI.Docto = C.Clave " +   
						 " \n LEFT JOIN (SELECT idPCompra, FFin FROM EstadoPCompra WHERE Tipo='En inspección Phs' ) AS EPC2 ON EPC2.idPCompra = PC.idPCompra    " +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave " +  
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Monitorear OC' AND Responsable IS NOT NULL AND Responsable <> 'null' AND Responsable<>'ComPHS-USA') AS PEN2 ON PEN2.Docto = C.Clave " +   
						 " \n WHERE PC.idPCompra = @idPCompra AND NOTI.Fecha IS NOT NULL  " +
						 " \n UNION " + 
						 
                         " \nSELECT 'IMPORTACIÓN' AS ETAPA, 1 AS NIVEL, 3 AS ID, 3 AS PADRE, " +
                         " \nLA.Fecha AS FINICIO, PEN.FFin AS FFIN, '' AS CONTACTO, PEN.Responsable AS RESPONSABLE,  ODES.NoReferenciaPedimento collate SQL_Latin1_General_CP1_CI_AS AS REFERENCIA, '' AS PROVEEDOR, " +
                         " \n'' QUIENCOMPRA, '' AS COMENTARIOS,  (SELECT DATEDIFF(D,LA.Fecha, COALESCE(PEN.FFin,GETDATE())) - DATEDIFF(week,LA.Fecha,COALESCE(PEN.FFin,GETDATE())) * 2  -  CASE WHEN DATENAME(dw, LA.Fecha) <> 'Saturday' AND DATENAME(dw, COALESCE(PEN.FFin,GETDATE())) = 'Saturday' THEN 1   WHEN DATENAME(dw, LA.Fecha) = 'Saturday' AND DATENAME(dw, COALESCE(PEN.FFin,GETDATE())) <> 'Saturday' THEN -1 ELSE 0 END) AS TT, NULL AS FEA, NULL AS FRA,  " + 
                         " \n'' AS CADU_MES, '' AS CADU_ANO, '' AS LOTE, '' AS MANEJO, '' AS IDIOMA, '' AS EDICION, '' AS DOCUMENTACION, '' AS ETIQUETAS, '' AS DESPACHABLES,  " +
                         " \n'' NODESPACHABLES, ODES.NoPedimento collate SQL_Latin1_General_CP1_CI_AS as PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,ODES.FEA AS FechaEstimadaArribo, ODES.FSA FechaSalidaAduana, PRO.Pais as  PaisProveedor, PC.idPCompra as idCompra, PRODU.Tipo as Tipo , ODES.Fletera as  Fletera, ODES.Folio as OrdenDespach,  ADU.Nombre AS aduana,  AGD.NombreComercial AS agenteAdunal, ODES.GuiaEmbarque, ODES.FUA AS FechaPlanificar, LA.Folio as Folio, " + 
                         " \n ODES.FPedimento as FechaPedimento, INA.TotalPiezas as TotalPiezas , INA.Faltantes as PiezasFaltantes, INA.Sobrantes as PiezasSobrantes, '' FechaProxGestion, '' Respuesta, '' idPAviso, COM.EntregarEn, PENDI.FFin as FRDESPACHO " +
                         " \nFROM PCompras as PC " +
                         " \nLEFT JOIN  PPedidos  PP ON PP.CPedido = PC.CPedido AND  PP.Part = PC.PPedido  " +
                         " \nLEFT JOIN PListaArribo PLA ON PLA.FK02_PCompra = PC.idPCompra " +
                         " \nLEFT JOIN ListaArribo LA ON LA.PK_ListaArribo = PLA.FK01_ListaArribo " +
                         " \nLEFT JOIN OrdenDespacho_ListaArribo ODLA ON ODLA.FK01_OrdenDespacho = LA.FK02_OrdenDespacho and ODLA.FK02_ListaArribo = LA.PK_ListaArribo " +
                         " \nLEFT JOIN OrdenDespacho ODES ON ODES.PK_OrdenDespacho = ODLA.FK01_OrdenDespacho " + 
                         " \nLEFT JOIN Pendiente PENDI ON PENDI.Partida = ODES.Folio and PENDI.Tipo = 'RegistrarDespacho' " +
                         " \nLEFT JOIN InconsistenciaEnArribo INA ON INA.FK01_OrdenDespacho = ODES.PK_OrdenDespacho " +
                         " \nLEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'RegistrarArribo') AS PEN ON PEN.Partida = ODES.Folio " +
                         " \nLEFT JOIN  Proveedores PRO ON PRO.Clave = PP.Provee " +
                         " \nLEFT JOIN  Productos PRODU ON PRODU.idProducto = PP.FK02_Producto " +
                         " \nLEFT JOIN AgenteAduanal AGD ON AGD.PK_AgenteAduanal = ODES.FK01_AgenteAduanal" +
                         " \nLEFT JOIN Aduana ADU ON ADU.PK_Aduana = ODES.FK05_Aduana" +
                         " \nLEFT JOIN Compras COM ON COM.Clave = PC.Compra " +
                         " \nWHERE PC.idPCompra = @idPCompra " +
						 
						 
						 
						 " \n UNION " + 
						 
						 " \n SELECT TOP 1 'RECIBIDO' AS ETAPA, 1 AS NIVEL, 9 AS ID, 3 AS PADRE,COALESCE(PEN1.FInicio, PEN2.FInicio) FINICIO,   " +
						 " \n CASE WHEN PC.FPharma IS NOT NULL THEN COALESCE(EPC1.FInicio, INS.Fecha,EDOPCphs.FInicio) ELSE EDOPCmatriz.FInicio END AS FFIN ,  NULL CONTACTO,     " +
						 " \n NULL RESPONSABLE,CASE WHEN (DATEDIFF(DAY, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio),PP.FPEntrega)>=FEPED.FECHA ) " +
						 " \n OR (DATEDIFF(DAY, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio),PP.FPEntrega) IS NULL ) OR (PP.FPEntrega IS NULL) THEN '1' ELSE '0'  END AS REFERENCIA,NULL PROVEEDOR, NULL QUIENCOMPRA,NULL COMENTARIOS," 
						 + funcion.obtenerTiempoTranscurridoSQL("COALESCE(PEN1.FInicio, PEN2.FInicio) ", "CASE WHEN PC.FPharma IS NOT NULL THEN COALESCE(EPC1.FInicio, INS.Fecha,EDOPCphs.FInicio) ELSE EDOPCmatriz.FInicio END", "Dias") + " AS TT," +
						 " \n COALESCE(PC.FProquifa,PC.Fpharma) FEA,CASE WHEN PC.FPharma IS NOT NULL THEN COALESCE(EPC1.FInicio, INS.Fecha,EDOPCphs.FInicio) ELSE EDOPCmatriz.FInicio END FRA,NULL CADU_MES,NULL CADU_ANO,NULL LOTE,NULL MANEJO,  " +
						 " \n NULL IDIOMA,NULL EDICION,NULL DOCUMENTACION,NULL ETIQUETAS,'' AS DESPACHABLES,NULL NODESPACHABLES, CAST(PCHIS.SUMA AS VARCHAR) PEDIMENTO,'' AS TRAFICO,'' AS GESTORAGENTE,'' FechaEstimadaArribo,''FechaSalidaAduana, '' PaisProveedor, '' idCompra, '' Tipo, '' Fletera, '' OrdenDespacho, '' aduana, '' agenteAdunal, '' GuiaEmbarque, '' FechaPlanificar, '' Folio, '' FechaPedimento, '' , '', '', '' FechaProxGestion, '' Respuesta, '' idPAviso, '', '' FRDESPACHO   " +
						 " \n FROM PCompras AS PC  " +
						 " \n LEFT JOIN (SELECT idPCompra, FInicio FROM EstadoPCompra WHERE Tipo='En inspección') AS EPC1 ON EPC1.idPCompra = PC.idPCompra   " +
						 " \n LEFT JOIN (SELECT Fecha, idPCompra FROM InspeccionOC) AS INS ON INS.idPCompra = PC.idPCompra  " +
						 " \n LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PC.CPedido AND PP.Part = PC.PPedido AND PP.Codigo = PC.Codigo" +
						 " \n LEFT JOIN (SELECT * FROM EstadoPCompra) AS EDOPC ON EDOPC.idPCompra =PC.idPCompra  " +
						 " \n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
						 " \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
						 " \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra  " +
						 " \n LEFT JOIN (SELECT * FROM PPedidos) AS PPED ON PPED.CPedido = PC.CPedido AND PPED.Part = PC.Partida  " +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC a colocar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN1 ON PEN1.Docto = PC.Compra  " +
						 " \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN2 ON PEN2.Docto = PC.Compra  " +
						 " \n LEFT JOIN ( SELECT  COUNT(*) AS SUMA,PCH.idPCompra,PCH.idCompra FROM PCompras AS PC   " +
						 " \n LEFT JOIN (SELECT DISTINCT FolioNT, idCompra, idPCompra, Tipo, Comentarios,PPedido FROM PCompraHistorial WHERE Tipo <> 'Aviso de cambios, se espera el cliente'  " +
						 " \n AND Tipo <> 'Aviso de cambios, cancelada' AND Gestor<>'ComPHS-USA') AS PCH ON PCH.idCompra = PC.Compra AND PCH.idPCompra = PC.Partida " +
						 " \n LEFT JOIN (SELECT * FROM Notificado WHERE Tipo = 'Aviso de cambios') AS NOTI ON NOTI.idNotificado = PCH.FolioNT    " +
						 " \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra GROUP BY PCH.idPCompra,PCH.idCompra ) AS PCHIS ON PCHIS.idCompra=PC.Compra AND PCHIS.idPCompra=PC.Partida   " +
						 " \n LEFT JOIN (SELECT COUNT(IdPCompra) AS SD, IdPCompra FROM Pieza WHERE Despachable = 1 GROUP BY IdPCompra) AS DESPA ON DESPA.IdPCompra = PC.idPCompra    " +
						 " \n LEFT JOIN (SELECT CASE WHEN DATENAME(weekday, COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Lunes' THEN 4  WHEN DATENAME(weekday,COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Martes' THEN 4 ELSE 2 END AS FECHA,PP.CPedido,Part,PC.FEEntrega  " +
						 " \n FROM PPedidos AS PP LEFT JOIN (SELECT * FROM PCompras ) AS PC ON PC.CPedido=PP.CPedido AND PC.PPedido = PP.Part LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.idPCompra =PC.idPCompra " + 
						 " \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra  " +
						 " \n WHERE FPEntrega IS NOT NULL) AS FEPED ON FEPED.CPedido= PC.CPedido AND FEPED.Part = PC.PPedido  " +
						 " \n WHERE PC.Estado LIKE 'Recibido%' AND PC.idPCompra=@idPCompra " +
						 " \n ORDER BY ID";
		//logger.info(query);
		return super.jdbcTemplate.query(query, map,new ConsultaComprasTiempoProcesoRowMapper());
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nidCompra: "+idPCompra);
			return new ArrayList<TiempoProceso>();
		}
	}

	@SuppressWarnings("unchecked")
	public List<ResumenConsulta> findResumenCompras(String periodo,
			String proveedor, String estado, String coloco,
			String ordenCompra, String usuario) {
		funcion = new Funcion();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", periodo);
			map.put("proveedor", proveedor);
			map.put("estado", estado);
			map.put("coloco", coloco);
			map.put("ordenCompra", ordenCompra);
			map.put("usuario", usuario);
			
			String vAnd =" AND ",condiciones="";
			if(!periodo.equals("") || !proveedor.equals("") || !estado.equals("") || !coloco.equals("") || !ordenCompra.equals("")){
				condiciones =vAnd + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario;
			}
			
			String query = 	" SELECT COALESCE(COMPL.Tipo, PRO.Tipo) AS NOMBRE, SUM(COALESCE(PC.CANT,0)) AS TOTAL,  " +
					" SUM(" + funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) + ") AS MONTO," +
					" 'TIPO' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP, COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS " +
					" FROM PCompras AS PC" +
					" LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
					" LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
					" LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
					" LEFT JOIN (SELECT Complemento.idComplemento, Productos.Tipo  FROM Complemento, Productos WHERE Complemento.Codigo = Productos.Codigo AND Complemento.Fabrica = Productos.Fabrica) AS COMPL ON COMPL.idComplemento = PC.idComplemento" +
					" LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)" +
					" LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
					"	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
					"	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
					"	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
					" LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
					" WHERE (PC.Estado <> 'Cancelada' AND PC.Estado <> 'Cancelable') AND PRO.idProducto IS NOT NULL " +condiciones +
					" GROUP BY COALESCE(COMPL.Tipo, PRO.Tipo)" +
					" UNION" +
					" SELECT PC.Fabrica AS NOMBRE, SUM(COALESCE(PC.CANT,0)) AS TOTAL,  " +
					" SUM(" + funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) + ") AS MONTO," +
					" 'FABRICA' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP, COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS" +
					" FROM PCompras AS PC" +
					" LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
					" LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
					" LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
					" LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)" +
					" LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
					"	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
					"	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
					"	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
					" LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
					" WHERE (PC.Estado <> 'Cancelada' AND PC.Estado <> 'Cancelable') AND PRO.idProducto IS NOT NULL " +condiciones+
					" GROUP BY PC.Fabrica" +
					" UNION" +
					" SELECT PC.Estado AS NOMBRE, SUM(COALESCE(PC.CANT,0)) AS TOTAL,  " +
					" SUM(" + funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) + ") AS MONTO," +
					" 'ESTADO' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP, COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS" +
					" FROM PCompras AS PC" +
					" LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
					" LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
					" LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
					" LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)" +
					" LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
					"	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
					"	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
					"	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
					" LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
					" WHERE (PC.Estado <> 'Cancelada' AND PC.Estado <> 'Cancelable') AND PRO.idProducto IS NOT NULL " +condiciones+
					" GROUP BY PC.Estado" +
					" UNION" +
					" SELECT PCH.Tipo AS NOMBRE, COUNT(*) AS TOTAL, "+
					" SUM(" + funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) + " ) AS TOTAL, 'AVISOS DE CAMBIO' AS RESULTADOS, " +
					" COUNT(DISTINCT C.Provee) AS CP, COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS" +
					" FROM PCompras AS PC" +
					" LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
					" LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
					" LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
					" LEFT JOIN (SELECT DISTINCT FolioNT, idCompra, idPCompra, Tipo FROM PCompraHistorial WHERE Tipo <> 'Aviso de cambios, se espera el cliente'" +
					" AND Tipo <> 'Aviso de cambios, cancelada') AS PCH ON PCH.idCompra = PC.Compra AND PCH.idPCompra = PC.Partida" +
					" LEFT JOIN (SELECT DISTINCT FolioNT, Fecha FROM PCompraHistorial WHERE Tipo = 'Aviso de cambios, se espera el cliente' OR Tipo = 'Aviso de cambios, cancelada') AS PCH2 ON PCH2.FolioNT = PCH.FolioNT" +
					" LEFT JOIN (SELECT DISTINCT FolioNT, Fecha FROM PCompraHistorial WHERE Tipo = 'Cancelada') AS PCH3 ON PCH3.FolioNT = PCH.FolioNT" +
					" LEFT JOIN (SELECT * FROM GESTIONAC) AS GES ON GES.idNotificado = PCH.FolioNT" +
					" LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)"+
					" LEFT JOIN (SELECT * FROM Notificado WHERE Tipo = 'Aviso de cambios') AS NOTI ON NOTI.idNotificado = PCH.FolioNT" +
					" LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
					"	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
					"	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
					"	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
					" LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
					" WHERE NOTI.Fecha IS NOT NULL " +condiciones +
					" GROUP BY PCH.Tipo";
			//logger.info(query);
			return super.jdbcTemplate.query(query,map, new ResumenConsultaRowMapper());
		}catch (Exception e) {
		//	logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nPeriodo: "+ periodo,"\nProveedor: "+ proveedor,"\nEstado: "+ estado,"\nColoco: "+ coloco,
					"\nOrdenCompra: "+ ordenCompra, "\nUsuario: "+usuario);
			return new ArrayList<ResumenConsulta>();
		}
	}


	@SuppressWarnings("unchecked")
	public List<ResumenConsulta> findTopProveedores(String periodo,
			String proveedor, String estado, String coloco,
			String ordenCompra, String usuario) {
		funcion = new Funcion();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", periodo);
			map.put("proveedor", proveedor);
			map.put("estado", estado);
			map.put("coloco", coloco);
			map.put("ordenCompra", ordenCompra);
			map.put("usuario", usuario);
			
			String vWHERE = new String();
			
			
			if(!periodo.equals("") || !proveedor.equals("") || !estado.equals("") || !coloco.equals("") || !ordenCompra.equals("")){
				vWHERE = " WHERE ";				
			}
			
			
			String query = 	" \n SELECT TOP 10 PROV.Nombre, SUM(COALESCE(PC.TotalPiezas,0)) AS TOTAL,  " +
							" \n SUM(" + funcion.sqlConversionMonedasADolar("C.Moneda", "1", "PC.MONTOTOTAL", "MON", "","",false) + ") AS MONTO, 'PROVEEDOR' AS RESULTADOS," +
							" \n 1 AS CP, SUM(PC.PARTIDA) AS PARTIDAS,SUM(PARTIDAS.PARTIDAS) AS PARTIDAS, COUNT(PC.FOLIOS) AS FOLIOS " +
							" \n FROM Compras AS C" +
							" \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave" +
							" \n LEFT JOIN (SELECT SUM(PC.Cant) AS TotalPiezas, SUM(PC.Cant*PC.Costo) AS MONTOTOTAL, COUNT(PC.idPCompra) AS PARTIDA, COUNT(*) AS FOLIOS,PC.Compra FROM PCompras AS PC WHERE Estado <> 'Cancelada' " +
							" \n 	AND Estado <> 'Cancelable' GROUP BY PC.Compra) AS PC ON C.Clave=PC.Compra" +
							" \n  LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)" +
							" \n  LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, COUNT(PC.idPCompra)AS P, Compra " +
							" \n 	FROM PCompras AS PC	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END " +
							" \n 	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
							" \n 	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
							" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
							" \n LEFT JOIN (SELECT COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, Compra FROM PCompras AS PC GROUP BY Compra) AS PARTIDAS ON PARTIDAS.Compra = C.Clave" +
							 vWHERE + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario +
							" \n GROUP BY PROV.Nombre " +
							" \n ORDER BY TOTAL DESC";	
			
			//logger.info(query);
			return super.jdbcTemplate.query(query,map, new ResumenConsultaRowMapper());
		}catch (Exception e) {
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nPeriodo: "+ periodo,"\nProveedor: "+ proveedor,"\nEstado: "+ estado,"\nColoco: "+ coloco,
					"\nOrdenCompra: "+ ordenCompra, "\nUsuario: "+usuario);
			return new ArrayList<ResumenConsulta>();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ResumenConsulta> findTopProductos(String periodo,
			String proveedor, String estado, String coloco,
			String ordenCompra, String usuario) {
		funcion = new Funcion();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", periodo);
			map.put("proveedor", proveedor);
			map.put("estado", estado);
			map.put("coloco", coloco);
			map.put("ordenCompra", ordenCompra);
			map.put("usuario", usuario);
			
			String vWHERE = new String();
			
			
			if(!periodo.equals("") || !proveedor.equals("") || !estado.equals("") || !coloco.equals("") || !ordenCompra.equals("")){
				vWHERE = " WHERE ";				
			}
			
			String query = 	" \n SELECT TOP 10 PC.Codigo + ' ' + PC.Fabrica + ' ' + CAST( COALESCE(PROD.Concepto, COMPL.Descripcion) AS VARCHAR(400)) AS NOMBRE, " +
							" \n SUM(COALESCE(PC.CANT,0)) AS TOTAL, SUM(" + funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) + ") AS MONTO, " +
							" \n 'PRODUCTOS' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP, COUNT(DISTINCT PC.idPCompra) AS PARTIDAS," +
							" \n COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS " +
							" \n FROM PCompras AS PC" +
							" \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
							" \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
							" \n LEFT JOIN (SELECT * FROM Productos) AS PROD ON PROD.Codigo = PC.Codigo AND PROD.Fabrica = PC.Fabrica" +
							" \n LEFT JOIN (SELECT * FROM Complemento) AS COMPL ON COMPL.idComplemento = PC.idComplemento" +
							" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)" +
							" \n LEFT JOIN (	SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
							" \n	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
							" \n	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
							" \n	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
							" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
							" \n LEFT JOIN (SELECT COUNT(PC.idPCompra) AS PARTIDAS, Compra FROM PCompras AS PC GROUP BY Compra) AS PARTIDAS ON PARTIDAS.Compra = C.Clave" +
							vWHERE + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario +
							" \n GROUP BY PC.Codigo, PC.Fabrica, CAST(COALESCE(PROD.Concepto, COMPL.Descripcion) AS VARCHAR(400))" +
							" \n	ORDER BY TOTAL DESC";
			 
			//logger.info(query);
			return super.jdbcTemplate.query(query,map, new ResumenConsultaRowMapper());
		}catch (Exception e) {
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nPeriodo: "+ periodo,"\nProveedor: "+ proveedor,"\nEstado: "+ estado,"\nColoco: "+ coloco,
					"\nOrdenCompra: "+ ordenCompra, "\nUsuario: "+usuario);
			return new ArrayList<ResumenConsulta>();
		}
	}


	@SuppressWarnings("unchecked")
	public List<ResumenConsulta> findTotalesComprasPorPeriodo(String periodo,
			String proveedor, String estado, String coloco, String ordenCompra,
			String usuario) {
		funcion = new Funcion();
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", periodo);
			map.put("proveedor", proveedor);
			map.put("periodo", estado);
			map.put("proveedor", coloco);
			map.put("periodo", ordenCompra);
			map.put("proveedor", usuario);
//			Integer.parseInt("sd");
			String query=
				" \n SELECT '' AS NOMBRE, COUNT(DISTINCT C.Clave) AS TOTAL, '' AS MONTO, 'TOTALCOMPRASPASADO' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP, " +
				" \n COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS" +
				" \n FROM PCompras AS PC" +
				" \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
				" \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
				" \n LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
				" \n LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
				" \n	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
				" \n	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
				" \n	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
				" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
				" \n WHERE  PC.Estado <> 'Cancelada' AND PC.Estado <> 'Cancelable' AND  C.Fecha >= " + periodo + " "  + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario +
				" \n UNION " +
				" \n SELECT '', SUM(PC.Cant) AS TOTAL, '' AS NI, 'TOTALPIEZASPASADO' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP," +
				" \n COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS" +
				" \n FROM PCompras AS PC" +
				" \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
				" \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
				" \n LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
				" \n LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
				" \n	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
				" \n	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
				" \n	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
				" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
				" \n WHERE PC.Estado <> 'Cancelada' AND PC.Estado <> 'Cancelable' AND  C.Fecha >= " + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario +
				" \n UNION " +
				" \n SELECT ''AS NOMBRE, '' AS TOTAL, SUM(COALESCE(" + funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) +  ", 0)) AS MONTO, " +
				" \n 'TOTALMONTODLLSPASADO' AS RESULTADOS, COUNT(DISTINCT C.Provee) AS CP," +
				" \n COUNT(DISTINCT PC.idPCompra) AS PARTIDAS, COUNT(DISTINCT C.Clave) AS FOLIOS" +
				" \n FROM PCompras AS PC" +
				" \n LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra" +
				" \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
				" \n LEFT JOIN (SELECT * FROM Productos) AS PRO ON PRO.Codigo = PC.Codigo AND PRO.Fabrica = PC.Fabrica" +
				" \n LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC" +
				" \n	LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END" +
				" \n	ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido" +
				" \n	GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave" +
				" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado " +
				" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)" +
				" \n WHERE  PC.Estado <> 'Cancelada' AND PC.Estado <> 'Cancelable' AND C.Fecha >= "  + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario +
				" \n ORDER BY RESULTADOS, TOTAL DESC ";
			//logger.info(query);
					return super.jdbcTemplate.query(query,map, new ResumenConsultaRowMapper());
			
		}catch(Exception e){
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nPeriodo: "+ periodo,
					"\nProveedor: "+ proveedor, "\nEstado: "+ estado, "\nColoco: "+ coloco, "\nOrdenCompra: "+ ordenCompra, "\nUsuario"+ usuario);
			return new ArrayList<ResumenConsulta>();
		}
	}
	public Integer obtenerEnTiempo(Long idPCompra)	{
		try{	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			
			String query =   
//					" \n SELECT TOP 1  CASE WHEN (DATEDIFF(DAY, PP.FPEntrega, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio,GETDATE())) > 0 ) " +
							" \n SELECT TOP 1  CASE WHEN (DATEDIFF(DAY, PC.FProquifa, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio,GETDATE())) > 0 ) " +
							" \n THEN '0' ELSE '1' END AS TIEMPO  " +
							" \n FROM PCompras AS PC  " +
							" \n LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PC.CPedido AND PP.Part = PC.PPedido AND PP.Codigo = PC.Codigo " +
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra) AS EDOPC ON EDOPC.idPCompra =PC.idPCompra  " +
							" \n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra  " +
							" \n LEFT JOIN (SELECT CASE WHEN COALESCE(PC.FEEntrega,PP.FPEntrega) IS NULL THEN 1000 WHEN DATENAME(weekday, COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Lunes' THEN 4  WHEN DATENAME(weekday, COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Martes' THEN 4 ELSE 2 END AS FECHA,PP.CPedido,Part,FPEntrega FROM PPedidos AS PP " +
							" \n LEFT JOIN (SELECT * FROM PCompras WHERE Estado LIKE 'Recibido%') AS PC ON PC.CPedido=PP.CPedido AND PC.PPedido = PP.Part " +
							" \n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra  " +
							" \n WHERE FPEntrega IS NOT NULL)    AS FEPED ON FEPED.CPedido= PC.CPedido AND FEPED.Part = PC.PPedido   " +
							" \n WHERE PC.Estado LIKE 'Recibido%' AND PC.idPCompra='"+idPCompra+"'";
			//logger.info(query);
			return super.queryForInt(query);
		}catch (Exception e) {
		//	logger.error(e.getMessage());
//			funcion.enviarCorreoAvisoExepcion(e, "\nidPCompra"+idPCompra);
			return 0;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<TiempoProceso> obtenerHistorialPhsMatriz(Long idPCompra){
		try{			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			
			String query =    "  SELECT	CASE WHEN (PCHIS.Fecha >=(CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) OR ((CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) IS NULL)) THEN 'HISTORIAL MATRIZ' " + 
			" ELSE 'HISTORIAL PHS' END AS ETAPA, 2 AS NIVEL,  " +
			" CASE WHEN (PCHIS.Fecha >=(CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) OR ((CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) IS NULL)) THEN '12' ELSE '11' END AS ID, " +
			" CASE WHEN (PCHIS.Fecha >=(CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) OR ((CASE WHEN C.AlmacenUSA = 0 THEN PENALI.FFin ELSE EPC2.FFin END) IS NULL)) THEN '7' ELSE '5' END AS PADRE, " +
			" PCHIS.Fecha, PCHIS.Origen, PCHIS.Gestor,PCHIS.Comentarios, PCHIS.Tipo, PCHIS.Razones, PCHIS.FPhsUSA, PCHIS.FPhSUSAAnterior, PCHIS.FMatriz,    " +
			" PCHIS.FMatrizAnterior, PCHIS.FolioNT AS idNotificado    " +
			" FROM PCompras AS PC         " +
			" LEFT JOIN (SELECT * FROM Compras) AS C ON C.Clave = PC.Compra   " +
			" LEFT JOIN (SELECT *  FROM PCompraHistorial  WHERE Tipo <> 'Aviso de cambios, se espera el cliente'    " +
			" AND Tipo <> 'Aviso de cambios, cancelada' AND Gestor<>'ComPHS-USA') AS PCHIS ON PCHIS.idCompra=PC.Compra AND PCHIS.idPCompra=PC.Partida   " +
			" LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo LIKE 'En inspección Phs' ) AS EDOPC ON EDOPC.idPCompra = PC.idPCompra   " +
			" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'OC por confirmar' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PEN ON PEN.Docto = C.Clave  " +
			" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo = 'Monitorear OC' AND Responsable IS NOT NULL AND Responsable <> 'null' AND Responsable<>'ComPHS-USA') AS PEN2 ON PEN2.Docto = C.Clave  " +
			" LEFT JOIN (SELECT Docto, FFin, Responsable FROM Pendiente WHERE Tipo = 'Alistar envío compra internacional' AND Responsable IS NOT NULL AND Responsable <> 'null') AS PENALI ON PENALI.Docto = C.Clave   " + 
			" LEFT JOIN (SELECT idPCompra, FFin FROM EstadoPCompra WHERE Tipo='En inspección Phs' ) AS EPC2 ON EPC2.idPCompra = PC.idPCompra   " +
			" WHERE PC.idPCompra = '"+idPCompra+"' AND PCHIS.Fecha IS NOT NULL  " ;
			
			//logger.info(query);
			return super.jdbcTemplate.query(query,map, new ConsultaHistorialPhsMatrizRowMapper());
		}catch (Exception e) {
		//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nidCompra: "+idPCompra);
			return new ArrayList<TiempoProceso>();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Compra> obtenerGraficaXCompra(String periodo, String proveedor,
			String estado, String coloco, String ordenCompra, String usuario) throws ProquifaNetException {
		funcion = new Funcion();
		String vWHERE = new String();
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("periodo", periodo);
			map.put("proveedor", proveedor);
			map.put("estado", estado);
			map.put("coloco", coloco);
			map.put("estado", ordenCompra);
			map.put("coloco", usuario);
			
			if(!periodo.equals("") || !proveedor.equals("") || !estado.equals("") || !coloco.equals("") || !ordenCompra.equals("") || !usuario.equals("") ){
				vWHERE = " WHERE ";
			}

			String query = 	" \n SELECT PROV.Nombre AS PROVEDOR,PC.Fabrica AS MARCA, C.Clave AS OC, C.ColocarDesde AS COLOCO, C.Empresa AS COMPRO, C.Fecha AS FCONFIRMACION, C.FColocacion, " +
							" \n EM.Usuario AS COMPRADOR, COALESCE(PC.TotalPiezas,0) AS TotalPiezas,  COALESCE(" + funcion.sqlConversionMonedasADolar("C.Moneda", "1", "PC.MONTOTOTAL", "MON", "","",false) + " , 0) AS MONTOTOTAL, " +
							" \n CASE WHEN EDO.T = 0 OR C.Estado = 'Cancelada' THEN 'CERRADA' ELSE 'ABIERTA' END AS ABIERTO_CERRADO,  " +
							" \n PC.PARTIDA AS PARTIDAS,CASE WHEN (DATEDIFF(DAY, PP.FPEntrega, COALESCE(EDOPCmatriz.FInicio, EDOPCphs.FInicio, GETDATE())) > 0 ) THEN 'FT' ELSE 'ET'  END AS ET," +
							" \n PC.Estado, CANT.MONTO,CANT.PIEZAS " +
							" \n FROM Compras AS C " +
							" \n LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON C.Provee = PROV.Clave " +
							" \n LEFT JOIN (SELECT Estado,Fabrica,Codigo,PPedido,CPedido,FPharma,FEEntrega,idPCompra,SUM(PC.Cant)as TotalPiezas, SUM(PC.Cant*PC.Costo) AS MONTOTOTAL,COUNT(PC.idPCompra) AS PARTIDA, PC.Compra FROM PCompras AS PC  " +
							" \n GROUP BY Estado,Fabrica,Codigo,PPedido,CPedido,FPharma,PC.Compra,PC.idPCompra,FEEntrega) AS PC ON C.Clave=PC.Compra " +
							" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha) " +
							" \n LEFT JOIN (SELECT SUM(CASE WHEN PC.Estado = P.ESTADO OR PC.Estado = 'Cancelada' OR PC.Estado = 'Cancelable' OR PC.Estado = 'Recibido' THEN 0 ELSE 1 END) AS T, Compra FROM PCompras AS PC " +
							" \n			LEFT JOIN (SELECT CASE WHEN P.chkDiferenteDestino = 0 THEN CASE WHEN C.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END " +
							" \n			ELSE CASE WHEN P.Pais = 'MEXICO' THEN 'Recibido' ELSE 'Recibido PHS' END END AS ESTADO, P.CPedido FROM Pedidos AS P, Clientes AS C WHERE P.idCliente = C.Clave) AS P ON P.CPedido = PC.CPedido " +
							" \n			GROUP BY Compra) AS EDO ON EDO.Compra = C.Clave " +
							" \n LEFT JOIN (SELECT Usuario, Clave FROM Empleados) AS EM ON EM.Clave = PROV.FK01_Empleado  " +
							" \n LEFT JOIN (SELECT COUNT(PC.idPCompra) AS PARTIDAS, Compra FROM PCompras AS PC GROUP BY Compra) AS PARTIDAS ON PARTIDAS.Compra = C.Clave " +
							" \n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra  " +
							" \n LEFT JOIN (SELECT * FROM PPedidos) AS PP ON PP.CPedido = PC.CPedido AND PP.Part = PC.PPedido AND PP.Codigo = PC.Codigo " +
							" \n LEFT JOIN (SELECT CASE WHEN DATENAME(weekday, COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Lunes' THEN 4  WHEN DATENAME(weekday,COALESCE(PC.FEEntrega,PP.FPEntrega)) ='Martes' THEN 4 ELSE 2 END AS FECHA," +
							" \n				PP.CPedido,Part,PC.FEEntrega,pc.idPCompra    " + 
							" \n 				FROM PPedidos AS PP LEFT JOIN (SELECT * FROM PCompras ) AS PC ON PC.CPedido=PP.CPedido AND PC.PPedido = PP.Part" +
							" \n LEFT JOIN (SELECT MAX(Folio) as Folio, idPCompra FROM EstadoPCompra WHERE Tipo='En inspección Phs' GROUP BY idPCompra) AS EDOPCphs1 ON EDOPCphs1.idPCompra =PC.idPCompra " +  
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección Phs') AS EDOPCphs ON EDOPCphs.Folio =EDOPCphs1.Folio " +
							" \n LEFT JOIN (SELECT * FROM EstadoPCompra WHERE Tipo='En inspección') AS EDOPCmatriz ON EDOPCmatriz.idPCompra =PC.idPCompra   " +
							" \n WHERE FEEntrega IS NOT NULL ) AS FEPED ON FEPED.idPCompra=pc.idPCompra  " +
							" \n LEFT JOIN (SELECT C.Clave, SUM(" +  funcion.sqlConversionMonedasADolar("C.Moneda", "PC.Cant", "PC.Costo", "MON", "","",false) + " ) AS MONTO, " +
							" \n SUM(PC.Cant)AS PIEZAS,C.Monto REAL,C.Moneda,MON.PDolar,MON.LDolar FROM Compras AS C " +
							" \n LEFT JOIN (SELECT * FROM Monedas)AS MON ON YEAR(MON.Fecha) = YEAR(C.Fecha) AND MONTH(MON.Fecha) = MONTH(C.Fecha) AND DAY(MON.Fecha) = DAY(C.Fecha)	" +
							" \n LEFT JOIN (SELECT * FROM PCompras)AS PC ON PC.Compra=C.Clave" +
							" \n GROUP BY C.Clave, C.Monto,C.Moneda, MON.PDolar,MON.LDolar,MON.EDolar) AS CANT ON CANT.Clave=C.Clave  " +
								vWHERE + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra + " " + usuario + " ORDER BY C.Fecha, PC.Compra ";
			log.info(query);
			return super.jdbcTemplate.query(query,map, new ConsultaComprasRowMapper());
		}catch (Exception e) {
			//logger.error(e.getMessage());
			funcion.enviarCorreoAvisoExepcion(e, "\nFechaInicio: "+ periodo,"\nProveedor: "+ proveedor,"\nEstado: "+ estado,"\nColoco: "+ coloco,
					"\nOrdenCompra: "+ ordenCompra, "\nUsuario: "+usuario);
			return new ArrayList<Compra>();
		}
	}

}
