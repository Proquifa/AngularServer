/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.DoctoCotizacion;
import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.NivelIngreso;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.DocumentoRecibidoDAO;
import com.proquifa.net.persistencia.comun.impl.mapper.DocumentoRecibidoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.DocumentoRecibidoSRRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.InfoComplementoCampanaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.InfoCotizacionCampanaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.InfoPartidasCampanaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.TiempoProcesoDoctosRRowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class DocumentoRecibidoDAOImpl extends DataBaseDAO implements
		DocumentoRecibidoDAO {
	Funcion f = new Funcion();
	
	final Logger log = LoggerFactory.getLogger(DocumentoRecibidoDAOImpl.class);
	
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.DocumentoRecibidoDAO#insertarDocumentoRecibido(mx.com.proquifa.proquifanet.modelo.comun.DocumentoRecibido)
	 */
	public Long insertarDocumentoRecibido(DocumentoRecibido doctoRecibido) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("doctoRecibido", doctoRecibido);
Object params [] = {doctoRecibido.getPartida(), new Date(), doctoRecibido.getManejo(),
					doctoRecibido.getOrigen(), doctoRecibido.getEmpresa(), doctoRecibido.getRPor(),
					doctoRecibido.getMedio(), doctoRecibido.getDocto(), doctoRecibido.getNumero(), doctoRecibido.getObservaciones(),
					doctoRecibido.getIngreso(), doctoRecibido.getFOrigen(),doctoRecibido.getFechaOrigen(), 
					doctoRecibido.getIdContacto(),doctoRecibido.getMontoDocumento(),doctoRecibido.getExisteReferencia(),doctoRecibido.getCampana()};
			String query = "INSERT INTO DoctosR (Part,Fecha,Manejo,Origen,Empresa,RPor,Medio,Docto,Numero,Observa,Ingreso,FOrigen,FHOrigen,idContacto,MontoDLS,SinReferencia,CodigoCampana) ";
			query += " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) "; 
			super.jdbcTemplate.update(query, map);
			Long idDoctoR = super.queryForLong("SELECT IDENT_CURRENT ('DoctosR')");
			return idDoctoR;
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			f.enviarCorreoAvisoExepcion(e, doctoRecibido);
			return 0L;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoRecibido> obtenerDocumentoRecibidoPorFolio(String folio,boolean porFolio) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("porFolio", porFolio);
			map.put("folio", folio);
		
			
			String query = "SELECT COALESCE(PND.FFin,Doctosr.FProceso) AS FProceso, doctosr.*,clientes.Nombre, Clientes.Habilitado,Clientes.CPago FROM doctosr,Clientes " +
						"LEFT JOIN Pendiente AS PND ON Pnd.Docto="+ folio +" AND Pnd.Tipo IN ('OTROS a trabajar','Refacturación') WHERE DoctosR.Empresa= Clientes.Clave AND " + 
			(porFolio ? "doctosR.folio = " + folio : "doctosr.Numero like '%" + folio +"%'");
//			log.info("pasa aqui: " + query);
			return jdbcTemplate.query(query,map,  new DocumentoRecibidoRowMapper());
		} catch (Exception e) {
//			logger.info("Error: "+ e.getMessage());
//			new Funcion().enviarCorreoAvisoExepcion(e, "\nFolio: "+folio);
//			return new DocumentoRecibido();
			return null;
		}
	}

	public void actualizarDocumentoRecibido(DocumentoRecibido documentoNuevo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("documentoNuevo", documentoNuevo);
			Object params [] = {documentoNuevo.getOrigen(), documentoNuevo.getEmpresa(), documentoNuevo.getRPor(), documentoNuevo.getMedio(),
						documentoNuevo.getDocto(), documentoNuevo.getNumero(), documentoNuevo.getObservaciones(), documentoNuevo.getMontoDocumento(), 
						documentoNuevo.getExisteReferencia(), documentoNuevo.getFechaOrigen(), documentoNuevo.getIdContacto()};
			String query = "UPDATE DoctosR SET Origen = ?, Empresa = ?, RPor = ?, Medio = ?, ";
	        query += "Docto = ?, Numero = ?, Observa = ?, MontoDLS = ?, SinReferencia = ?, ";
	        query += "FHOrigen = ?, idContacto = ? WHERE Folio = " +  documentoNuevo.getFolio();
			super.jdbcTemplate.update(query, map);
		} catch (Exception e) {
		//	logger.info("Error: "+ e.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(e, documentoNuevo);
		}
	}

	public Long obtenerNumeroDoctoRXFolio(Long folio) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			return super.queryForLong("SELECT COUNT(folio) as noDoctosR FROM doctosr WHERE folio = " + folio);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFolio: ");
			return 0L;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoRecibido> obtenerDocumentosXBA(Date finicio, Date ffin,String restriccion,String abierCerrado1, String abierCerrado2,String abierCerrado3,String abierCerrado4,String abiertoCerrado, List<NivelIngreso> niveles) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("finicio", finicio);
		map.put("ffin", ffin);
		map.put("restriccion", restriccion);
		map.put("abierCerrado1", abierCerrado1);
		map.put("abierCerrado2", abierCerrado2);
		map.put("abierCerrado3", abierCerrado3);
		map.put("abierCerrado4", abierCerrado4);
		map.put("abiertoCerrado", abiertoCerrado);
		map.put("niveles", niveles);
	
		
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");

		String FFINICIO = formatoDeFecha.format(finicio) + " 00:00";
		String FFFIN = formatoDeFecha.format(ffin) + " 23:59";
		f = new Funcion();
		
		String query = 
				" \n Select DoctosR.Folio,DoctosR.Part,DoctosR.Fecha,DoctosR.Manejo,DoctosR.Origen,DoctosR.Empresa, " +
						" \n DoctosR.RPor,DoctosR.Medio,DoctosR.Docto,DoctosR.MontoDLS,DoctosR.SinReferencia,DoctosR.Observa,DoctosR.FProceso,DoctosR.Ingreso,DoctosR.FOrigen,DoctosR.Estado " +
						",DoctosR.FolioPadre,DoctosR.esHijo,DoctosR.FHOrigen,DoctosR.CienteNuevo,DoctosR.idContacto, " +
						" \n CASE Origen WHEN 'Cliente' THEN  Clientes.Nombre WHEN 'Proveedor' THEN  Proveedores.Nombre ELSE Clientes.Nombre END AS Nombre, " +
						" \n COALESCE(CASE Numero WHEN '' THEN  'Ninguna' ELSE Numero END,'Ninguna') AS Numero,Numero AS DoctoCierre,NULL AS FFin, " +
						f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL, CASE WHEN (DATEDIFF(DAY, DoctosR.Fecha,COALESCE( DoctosR.FProceso,GETDATE())) <= ENT.TIEMPO) THEN 'ET' ELSE 'FT' END AS ENTIEMPO," +
						" \n CASE WHEN DoctosR.Estado='Cancelada' THEN 'Cerrado (S/D)' WHEN Numero IS NULL OR Numero ='' THEN 'Abierto' WHEN Numero<>'' THEN 'Cerrado (C/D)' WHEN DoctosR.FProceso IS NULL OR DoctosR.FProceso='' THEN 'Abierto' ELSE 'Cerrado (C/D)' END AS CIERREABIERTO, Clientes.CPago " +
						" \n from DoctosR  " +
						" \n LEFT JOIN Clientes ON Clientes.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN Proveedores ON Proveedores.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar   " +
						" \n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/(CASE WHEN F.TCambio <= 0 THEN 1 else F.Tcambio END) END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha  " + 
						" \n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = Clientes.Clave  " +
						" \n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C   " +
						" \n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre  " + 
						" \n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)   " +
						" \n AS OPER ON OPER.Clave = Clientes.Clave " +
						" \n LEFT JOIN (SELECT CASE  WHEN DATENAME(weekday,DoctosR.Fecha) ='Jueves' THEN 5 " +
						" \n WHEN DATENAME(weekday, DoctosR.Fecha) ='VierneS' THEN 5 ELSE 3 END AS TIEMPO,Folio FROM DoctosR)  " +
						" \n AS ENT ON ENT.Folio=DoctosR.Folio " +
						" \n WHERE Docto = 'Requisición'  AND Fecha >= '" + FFINICIO + "' AND Fecha<= '" + FFFIN + "'  " +restriccion+" " +abierCerrado1+"  " +
						" \n UNION ALL " +
						" \n Select DoctosR.Folio,DoctosR.Part,DoctosR.Fecha,DoctosR.Manejo,DoctosR.Origen,DoctosR.Empresa, " +
						" \n DoctosR.RPor,DoctosR.Medio,DoctosR.Docto,DoctosR.MontoDLS,DoctosR.SinReferencia,DoctosR.Observa,DoctosR.FProceso,DoctosR.Ingreso,DoctosR.FOrigen,DoctosR.Estado " +
						" \n ,DoctosR.FolioPadre,DoctosR.esHijo,DoctosR.FHOrigen,DoctosR.CienteNuevo,DoctosR.idContacto,  " +
						" \n CASE Origen WHEN 'Cliente' THEN  Clientes.Nombre WHEN 'Proveedor' THEN  Proveedores.Nombre  " +
						" \n ELSE Clientes.Nombre END AS Nombre,CAST(COALESCE(DoctosR.Numero,'') AS VARCHAR(50)) AS Numero, CPedido AS DoctoCierre,NULL AS FFin, " +
						f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL, CASE WHEN (DATEDIFF(DAY, DoctosR.Fecha,COALESCE( DoctosR.FProceso,GETDATE())) <= ENT.TIEMPO) THEN 'ET' ELSE 'FT' END AS ENTIEMPO, " +
						" \n CASE WHEN DoctosR.FProceso IS NULL THEN 'Abierto' WHEN DoctosR.Numero  IS NULL THEN 'Cerrado (S/D)' ELSE CASE WHEN Pedidos.CPedido IS NOT NULL THEN 'Cerrado (C/D)' ELSE 'Cerrado (S/D)' END END AS CIERREABIERTO, Clientes.CPago " +
						" \n FROM DoctosR " +
						" \n LEFT JOIN Clientes ON Clientes.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN Proveedores ON Proveedores.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN Pedidos ON DoctosR.Folio=Pedidos.DoctoR " +
						" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar   " +
						" \n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/(CASE WHEN F.TCambio <= 0 THEN 1 else F.Tcambio END) END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha  " + 
						" \n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = Clientes.Clave  " +
						" \n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C   " +
						" \n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre  " + 
						" \n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)   " +
						" \n AS OPER ON OPER.Clave = Clientes.Clave " +
						" \n LEFT JOIN (SELECT CASE  WHEN DATENAME(weekday,DoctosR.Fecha) ='Jueves' THEN 21 " +
						" \n WHEN DATENAME(weekday, DoctosR.Fecha) ='Viernes' THEN 21 ELSE 21 END AS TIEMPO,Folio FROM DoctosR)  " +
						" \n AS ENT ON ENT.Folio=DoctosR.Folio " +
						" \n WHERE Docto='Pedido' and Fecha >= '" + FFINICIO + "' AND Fecha<= '" + FFFIN + "' " +restriccion+" " +abierCerrado2+" " +
						" \n UNION ALL " +
						" \n Select DoctosR.Folio,DoctosR.Part,DoctosR.Fecha,DoctosR.Manejo,DoctosR.Origen,DoctosR.Empresa, " +
						" \n DoctosR.RPor,DoctosR.Medio,DoctosR.Docto,DoctosR.MontoDLS,DoctosR.SinReferencia,DoctosR.Observa,DoctosR.FProceso,DoctosR.Ingreso,DoctosR.FOrigen,DoctosR.Estado " +
						" \n ,DoctosR.FolioPadre,DoctosR.esHijo,DoctosR.FHOrigen,DoctosR.CienteNuevo,DoctosR.idContacto, " +
						" \n CASE Origen WHEN 'Cliente' THEN  Clientes.Nombre WHEN 'Proveedor' THEN  Proveedores.Nombre  " +
						" \n ELSE Clientes.Nombre END AS Nombre,CAST(DoctosR.Numero AS VARCHAR(50)) AS Numero,NULL AS DoctoCierre,NULL AS FFin, " +
						f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL, CASE WHEN (DATEDIFF(DAY, DoctosR.Fecha, COALESCE( DoctosR.FProceso,GETDATE())) <= ENT.TIEMPO) THEN 'ET' ELSE 'FT' END AS ENTIEMPO," +
						" \n CASE WHEN DoctosR.FProceso IS NULL THEN 'Abierto'ELSE 'Cerrado (S/D)' END AS CIERREABIERTO,Clientes.CPago  " +
						" \n from DoctosR " +
						" \n LEFT JOIN Clientes ON Clientes.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN Proveedores ON Proveedores.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN PedidoPago ON PedidoPago.Pago=DoctosR.Folio " +
						" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar   " +
						" \n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/(CASE WHEN F.TCambio <= 0 THEN 1 else F.Tcambio END) END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha  " + 
						" \n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = Clientes.Clave  " +
						" \n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C   " +
						" \n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre  " + 
						" \n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)   " +
						" \n AS OPER ON OPER.Clave = Clientes.Clave " +
						" \n LEFT JOIN (SELECT CASE  WHEN DATENAME(weekday,DoctosR.Fecha) ='Jueves' THEN 5 " +
						" \n WHEN DATENAME(weekday, DoctosR.Fecha) ='VierneS' THEN 5 ELSE 2 END AS TIEMPO,Folio FROM DoctosR)  " +
						" \n AS ENT ON ENT.Folio=DoctosR.Folio " +
						" \n WHERE Docto='Pago' and DoctosR.Fecha >= '" + FFINICIO + "' AND DoctosR.Fecha<= '" + FFFIN + "'  " +restriccion+" " +abierCerrado3+" " +
						" \n UNION ALL " +
						" \n Select DoctosR.Folio,DoctosR.Part,DoctosR.Fecha,DoctosR.Manejo,DoctosR.Origen,DoctosR.Empresa, " +
						" \n DoctosR.RPor,DoctosR.Medio,DoctosR.Docto,DoctosR.MontoDLS,DoctosR.SinReferencia,DoctosR.Observa, COALESCE(DoctosR.FProceso, Pendiente.FFIN) AS FProceso, DoctosR.Ingreso,DoctosR.FOrigen,DoctosR.Estado " +
						" \n ,DoctosR.FolioPadre,DoctosR.esHijo,DoctosR.FHOrigen,DoctosR.CienteNuevo,DoctosR.idContacto, " +
						" \n CASE Origen  " +
						" \n WHEN 'Cliente' THEN  Clientes.Nombre WHEN 'Proveedor' THEN  Proveedores.Nombre  " +
						" \n ELSE Clientes.Nombre END AS Nombre,'Ninguna' as Numero,NULL AS DoctoCierre, PENDIENTE.FFin, " +
						f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL, CASE WHEN (DATEDIFF(DAY, DoctosR.Fecha, COALESCE( DoctosR.FProceso,GETDATE())) <= ENT.TIEMPO) THEN 'ET' ELSE 'FT' END AS ENTIEMPO," +
						" \n CASE WHEN CONVERT(VARCHAR(20), PENDIENTE.FFin, 112) IS NOT NULL THEN 'Cerrado (S/D)' ELSE 'Abierto' END  AS CIERREABIERTO, Clientes.CPago " +
						" \n from DoctosR " +
						" \n LEFT JOIN Clientes ON Clientes.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN Proveedores ON Proveedores.Clave=DoctosR.Empresa " +
						" \n LEFT JOIN (SELECT * FROM Pendiente ) AS PENDIENTE ON PENDIENTE.Docto=DoctosR.Folio  " +
						" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar   " +
						" \n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/(CASE WHEN F.TCambio <= 0 THEN 1 else F.Tcambio END) END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha  " + 
						" \n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = Clientes.Clave  " +
						" \n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C   " +
						" \n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre  " + 
						" \n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)   " +
						" \n AS OPER ON OPER.Clave = Clientes.Clave " +
						" \n LEFT JOIN (SELECT CASE  WHEN DATENAME(weekday,DoctosR.Fecha) ='Jueves' THEN 5 " +
						" \n WHEN DATENAME(weekday, DoctosR.Fecha) ='VierneS' THEN 5 ELSE 2 END AS TIEMPO,Folio FROM DoctosR)  " +
						" \n AS ENT ON ENT.Folio=DoctosR.Folio " +
						" \n WHERE  Fecha >= '" + FFINICIO + "' AND Fecha<= '" + FFFIN + "' and DOCTOSR.Docto='Otros' AND (PENDIENTE.Tipo='OTROS a trabajar' OR Pendiente.Tipo='Refacturación') " +restriccion + abierCerrado4 ;
		if  (abiertoCerrado.equals("Abierto") || abiertoCerrado.equals("--TODOS--") ){
			query += " \n UNION ALL " +
					" \n Select DoctosR.Folio,DoctosR.Part,DoctosR.Fecha,DoctosR.Manejo,DoctosR.Origen,DoctosR.Empresa, " +
					" \n DoctosR.RPor,DoctosR.Medio,DoctosR.Docto,DoctosR.MontoDLS,DoctosR.SinReferencia,DoctosR.Observa,COALESCE( DoctosR.FProceso, PND.FFIN) AS FProceso, DoctosR.Ingreso,DoctosR.FOrigen,DoctosR.Estado " +
					" \n ,DoctosR.FolioPadre,DoctosR.esHijo,DoctosR.FHOrigen,DoctosR.CienteNuevo,DoctosR.idContacto, " +
					" \n CASE Origen WHEN 'Cliente' THEN  Clientes.Nombre WHEN 'Proveedor' THEN  Proveedores.Nombre  " +
					" \n ELSE Clientes.Nombre END AS Nombre,'Ninguna' as Numero,NULL AS DoctoCierre, COALESCE( DoctosR.FProceso, PND.FFIN) AS FFin, " +
					f.sqlLimitesNivelIngreso(niveles, "NIVEL.VentasUSD") + " AS NIVEL, CASE WHEN (DATEDIFF(DAY, DoctosR.Fecha, COALESCE( DoctosR.FProceso,GETDATE())) <= ENT.TIEMPO) THEN 'ET' ELSE 'FT' END AS ENTIEMPO," +
					" \n CASE WHEN Pnd.FFin IS NULL THEN 'Abierto' ELSE 'Cerrado (S/D)' END AS CIERREABIERTO, Clientes.CPago " +
					" \n from DoctosR " +
					" \n LEFT JOIN Clientes ON Clientes.Clave=DoctosR.Empresa " +
					" \n LEFT JOIN Proveedores ON Proveedores.Clave=DoctosR.Empresa " +
					" \n LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Refacturación') AS PND ON PND.Docto=Doctosr.Folio " +
					" \n LEFT JOIN (SELECT COALESCE(SUM(CASE WHEN F.Moneda='Dolares' or F.Moneda='USD' then F.Importe WHEN F.Moneda='Euros' OR F.Moneda='EUR' THEN F.Importe * M.EDolar   " +
					" \n WHEN F.Moneda='Pesos' OR F.Moneda='M.N.' Then F.Importe/(CASE WHEN F.TCambio <= 0 THEN 1 else F.Tcambio END) END), 0 )as VentasUSD, Cliente FROM Facturas as F	 LEFT JOIN (SELECT * FROM Monedas) AS M ON M.Fecha = F.Fecha  " + 
					" \n WHERE YEAR(F.fecha)=DATEPART(year, GETDATE())-1 AND F.Estado NOT LIKE 'Cancela%' GROUP BY F.Cliente )AS NIVEL ON NIVEL.Cliente = Clientes.Clave  " +
					" \n LEFT JOIN (SELECT (COALESCE(CO.COTIZACIONES, 0) + COALESCE(PED.PEDIDOS,0)) As noOperaciones, Clave FROM Clientes AS C   " +
					" \n LEFT JOIN (SELECT COUNT(1) AS COTIZACIONES, Cliente FROM Cotizas WHERE YEAR(Fecha)=(YEAR(GETDATE())-1) GROUP BY Cliente) AS CO ON CO.Cliente = C.Nombre  " + 
					" \n LEFT JOIN (SELECT COUNT(1) AS PEDIDOS, idCliente FROM Pedidos WHERE YEAR(FPedido) = (YEAR(GETDATE())-1) GROUP BY idCliente) AS PED ON PED.idCliente = C.Clave)   " +
					" \n AS OPER ON OPER.Clave = Clientes.Clave " +
					" \n LEFT JOIN (SELECT CASE  WHEN DATENAME(weekday,DoctosR.Fecha) ='Jueves' THEN 5 " +
					" \n WHEN DATENAME(weekday, DoctosR.Fecha) ='VierneS' THEN 5 ELSE 2 END AS TIEMPO,Folio FROM DoctosR)  " +
					" \n AS ENT ON ENT.Folio=DoctosR.Folio " +
					" \n WHERE  Fecha >= '" + FFINICIO + "' AND Fecha<= '" + FFFIN + "' and DOCTOSR.Docto='Otros' AND idContacto IS NULL AND FProceso IS NULL AND Pnd.FFin IS NULL " +restriccion+" ORDER BY Folio";
		}
		else{ 
			query+= " ORDER BY Folio";
		}
		log.info(query);
		try{
			return super.jdbcTemplate.query(query,map, new DocumentoRecibidoSRRowMapper());
		}
		catch(Exception e){
		//	logger.error(e);
			f.enviarCorreoAvisoExepcion(e,"\nFechaInicio: "+finicio,"\nFechaFin: "+ffin,"\nRestriccion: "+restriccion,"\nAbierCerrado1: "+abierCerrado1,"\nAbierCerrado2: "+abierCerrado2,
					"\nAbierCerrado3: "+abierCerrado3,"\nAbierCerrado4: "+abierCerrado4,"\nAbierCerrado: "+abiertoCerrado);
			return new ArrayList<DocumentoRecibido>();
		}
	}

	public DocumentoRecibido obtenerDocumentoRecibidoPorNumero(String numero) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("numero", numero);
			String query = "SELECT *, nombre = '', Clientes.Habilitado FROM DoctosR,clientes WHERE clientes.clave = doctosR.empresa AND Numero = '" + numero + "' ";
			return (DocumentoRecibido)super.jdbcTemplate.queryForObject(query,map, new DocumentoRecibidoRowMapper());
		}catch(RuntimeException runTime){
			//logger.info("Documento no encontrado" + numero);
			new Funcion().enviarCorreoAvisoExepcion(runTime, "\nNumero: "+numero);
			return new DocumentoRecibido();
		}
	}
	
	public DocumentoRecibido obtenerTiempoProcesoDoctosR(Long folio){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			String query =  "SELECT fecha,fhorigen FROM DoctosR WHERE folio = '" + folio + "'";
			return (DocumentoRecibido) super.jdbcTemplate.queryForObject(query,map, new TiempoProcesoDoctosRRowMapper());
		} catch (RuntimeException e) {
			f.enviarCorreoAvisoExepcion(e,"\nFolio: "+folio);
			return new DocumentoRecibido();
		}
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoRecibido> obtenerDocumentosRecibidosPorReferencia(
			String Referencia) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Referencia", Referencia);
			if(Referencia == "" || Referencia == null )
			{
				String query = "SELECT TOP 50 DoctosR.*, clientes.Nombre, Clientes.Habilitado FROM doctosr, clientes WHERE clientes.clave = doctosR.empresa ORDER BY  DoctosR.Folio DESC ";
					List<DocumentoRecibido> query2 = super.jdbcTemplate.query(query, map, new DocumentoRecibidoRowMapper());
				return query2;
			}else{
				try{
					String query = "SELECT doctosr.*,clientes.Nombre, Clientes.Habilitado FROM doctosr, clientes WHERE clientes.clave = doctosR.empresa AND Numero like '%" + Referencia + "%' AND Fecha > '2009-28-08 00:00:00.000'";
					List<DocumentoRecibido> query2 = super.jdbcTemplate.query(query,map, new DocumentoRecibidoRowMapper());
					return query2;
				}catch(RuntimeException runTime){
				//	logger.info("Documento no encontrado" + Referencia);
					return null;
				}
			}
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nReferencia: "+Referencia);
			return new ArrayList<DocumentoRecibido>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<DocumentoRecibido> obtenerDocumentosRecibidosPorFolio(
			String folio) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			if(folio == "" || folio == null ){
				String query = "SELECT TOP 50 DoctosR.*, clientes.Nombre, Clientes.Habilitado FROM doctosr, clientes WHERE clientes.clave = doctosR.empresa ORDER BY  DoctosR.Folio DESC ";				
					List<DocumentoRecibido> query2 = super.jdbcTemplate.query(query,map, new DocumentoRecibidoRowMapper());					
				return query2;
			}else{
				try{
					String query = "SELECT doctosr.*,clientes.Nombre, Clientes.Habilitado FROM doctosr, clientes WHERE clientes.clave = doctosR.empresa AND Folio like '%" + folio + "%' AND Fecha > '20090828'";					
					List<DocumentoRecibido> query2 = super.jdbcTemplate.query(query,map, new DocumentoRecibidoRowMapper());					
					return query2;
				}catch(RuntimeException runTime){
				//	logger.info("Documento no encontrado" + folio);
					return null;
				}
			}
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFolio: "+folio);
			return new ArrayList<DocumentoRecibido>();
		}
	}
	
	public Integer generarPendienteClienteDeshabilitado(String DoctoR, String fecha) {
		String responsable="";
		String tipo="Habilitar Cliente";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DoctoR", DoctoR);
			map.put("fecha", fecha);
			responsable = (String) jdbcTemplate.queryForObject("SELECT TOP 1 Usuario FROM Empleados WHERE FK01_Funcion='3' AND Fase=1 ORDER BY Clave", map, String.class);
			
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(r,"\nDoctoR: "+DoctoR,"\nFecha: "+fecha);
			responsable="CTirado";
		}
		
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DoctoR", DoctoR);
			map.put("fecha", fecha);
			Object[] params2 =  {DoctoR,fecha,responsable,tipo};
			
			super.jdbcTemplate.update(  "INSERT INTO Pendiente(Docto,FInicio,Responsable,Tipo) VALUES( ?,?,?,?) ",map);
			
			return 1;
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(r,"\nDoctoR: "+DoctoR,"\nFecha: "+fecha);
			return -1;
			
		}
	}

	@Override
	public Integer getValidarFolioCampana(String campana, String folio) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("campana", campana);
			map.put("folio", folio);
			Object[] params =  { folio,campana  };
			//respuesta del folio -43 no existe -911 ocupado -1 error y 1 vigente
			String query = " select CASE WHEN RC.INGRESADO IS NULL THEN  43 WHEN RC.INGRESADO = 0 THEN 1 WHEN RC.Ingresado = 1 THEN 911 END VALIDADO from Campana " +
			" \n LEFT JOIN (SELECT * FROM RegistroCampana )AS RC ON RC.FK01_Campana = Campana.PK_Campana AND RC.Codigo = ? " +
			" \n WHERE CAMPANA.Nombre like ? " ;
			//logger.info(query +  " -parametros- "+ folio + "," +  campana  );
			return super.queryForInt(  query,map);

		}catch(RuntimeException r){
		//	logger.error(r.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(r, "\nCampana: "+campana,"\nFolio: "+folio);
			return -1;
			
		}
	}

	@Override
	public long agregarCotizacionCampana(DocumentoRecibido documentoRecibido) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("documentoRecibido", documentoRecibido);
			// Se guarda cotizacion despues de tener el doctoR 
			Object[] params =  { documentoRecibido.getCampana(), documentoRecibido.getEmpresa() , documentoRecibido.getIdContacto() ,documentoRecibido.getIngreso(),documentoRecibido.getPartida(), new Date(), documentoRecibido.getManejo(),
					documentoRecibido.getOrigen(), documentoRecibido.getEmpresa(), documentoRecibido.getRPor(),	documentoRecibido.getMedio(), documentoRecibido.getDocto(), documentoRecibido.getNumero(), documentoRecibido.getObservaciones(),
					documentoRecibido.getIngreso(), documentoRecibido.getFOrigen(),documentoRecibido.getFechaOrigen(),	documentoRecibido.getIdContacto(),documentoRecibido.getMontoDocumento(),documentoRecibido.getExisteReferencia(),documentoRecibido.getCampana()   };
			String parametros =  documentoRecibido.getCampana() + "," +   documentoRecibido.getEmpresa() + "," +  documentoRecibido.getIdContacto() + "," +  documentoRecibido.getIngreso() + "," +  documentoRecibido.getPartida() + "," +  new Date() + "," +  documentoRecibido.getManejo() + "," +  
					documentoRecibido.getOrigen() + "," +   documentoRecibido.getEmpresa() + "," +   documentoRecibido.getRPor() + "," +  documentoRecibido.getMedio() + "," +  documentoRecibido.getDocto() + "," +  documentoRecibido.getNumero() + "," +  documentoRecibido.getObservaciones() + "," +
					documentoRecibido.getIngreso() + "," +   documentoRecibido.getFOrigen() + "," +  documentoRecibido.getFechaOrigen() + "," +  documentoRecibido.getIdContacto() + "," +  documentoRecibido.getMontoDocumento() + "," +  documentoRecibido.getExisteReferencia() + "," +  documentoRecibido.getCampana() ;
			String query = " DECLARE @COTIZA as varchar(12),  @DoctoR as int, @idcliente as int , @idcontacto as int, @cantidadIng as int , @cantidadEsp as int , @partidaEsp as int , @partidaIng as int, @usuario as varchar (20) , @esac as varchar(20) " +
					" \n Declare  @campania as varchar (12), @precio as int,  @moneda as varchar (10),  @idPCotiza as int  , @nombrecontacto as varchar (50), @fechalarga as varchar (15),@idcotiza as int   " + 
					" \n select @campania = ? " + 
					" \n select @idcliente = ? " +
					" \n select @idcontacto = ? " +
					" \n select @usuario = ? " +
					" \n SELECT @precio =  1034 " +
					" \n select @esac =  (SELECT VENDEDOR FROM  Clientes WHERE Clave = @idcliente) " +
					" \n select @fechalarga = ( select substring(REPLACE (CONVERT (varchar, GETDATE() , 21), '-', '') ,1,14)) " +
					" \n select @cantidadIng = ( SELECT cantidad_En FROM RegistroCampana WHERE Codigo = @campania) " +
					" \n select @cantidadEsp = ( SELECT cantidad_Es FROM RegistroCampana WHERE Codigo = @campania) " +
					" \n select @moneda =  'Dolares' " +
					" \n IF @cantidadIng > 0 AND @cantidadEsp>0 BEGIN  " +
					" \n 	select @partidaIng =1 " +
					" \n 	select @partidaEsp = 2 " +
					" \n END  " +
					" \n ELSE IF @cantidadIng = 0 OR @cantidadEsp = 0 BEGIN  " +
					" \n 	select @partidaIng =1 " +
					" \n 	select @partidaEsp = 1 " +
					" \n END  " +
					" \n BEGIN TRAN  " +
					" \n INSERT INTO DoctosR (Part,Fecha,Manejo,Origen,Empresa,RPor,Medio,Docto,Numero,Observa,Ingreso,FOrigen,FHOrigen,idContacto,MontoDLS,SinReferencia,CodigoCampana) " +
					" \n 	VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n select @DoctoR = (SELECT IDENT_CURRENT ('DoctosR')) "+
					" \n IF (select Valor  from Consecutivos where concepto =  'cotizas') = 9999  " +
					" \n 	BEGIN   " +
					" \n 		UPDATE Consecutivos set Valor = 0000 , esBloqueado =  1  where concepto =  'cotizas' IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END " +
					" \n 		SELECT @COTIZA = (select SUBSTRING (replace( (CONVERT (varchar, GETDATE() , 110)),'-',''),1,4) + SUBSTRING (convert(varchar, YEAR(GETDATE ())),3,2) + '-0000' ) " +
					" \n 	END  " +
					" \n ELSE  " +
					" \n 	BEGIN   " +
					" \n 		UPDATE Consecutivos set Valor = (select Valor +1 from Consecutivos where concepto =  'cotizas' ) , esBloqueado =  1  where concepto =  'cotizas' IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 		SELECT @COTIZA = (select SUBSTRING (replace( (CONVERT (varchar, GETDATE() , 110)),'-',''),1,4) + SUBSTRING (convert(varchar, YEAR(GETDATE ())),3,2) + '-' + convert(varchar,(select Valor +1  from Consecutivos where concepto =  'cotizas'))) " +
					" \n 	END  " +
					" \n INSERT INTO Cotizas (Cliente, idContacto , Contacto , Vendedor , Zona, Moneda , CPago , IMoneda , Cotizo , Factura , MEntrada , Parciales ,Clave,Estado,Fecha,HEntrada,FechaClasif,InfoFacturacion, FK01_idCliente,GravaIVA, FK02_doctosR) " +
					" \n 	select Nombre , cont.idContacto, cont.Contacto , Vendedor , Ruta, MonedaFactura, CPago, @Moneda, cont.contacto , 'Golocaer' , 'C', 'NO', @cotiza, 'Cotizada', CONVERT (varchar, GETDATE() , 112) , SUBSTRING( CONVERT ( VARCHAR , GETDATE () ,  108 ),1,5) ,CONVERT (varchar, GETDATE() , 112) + ' ' + SUBSTRING( CONVERT ( VARCHAR , GETDATE () ,  108 ),1,5),'1',@idcliente , '0', @DoctoR  from Clientes  " +
					" \n 	left join (select FK02_Cliente, idContacto , Contacto  from Contactos where Habilitado = 1 ) as cont on cont.FK02_Cliente =  Clientes.clave   " +
					" \n 	where Clave  =  @idcliente and idContacto =  @idcontacto IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END " +
					" \n select @idCotiza = (SELECT IDENT_CURRENT ('Cotizas') ) " +
					" \n INSERT INTO Actividades VALUES (GETDATE() , @esac , 'Realizó cotización' , @COTIZA ,(SELECT NOMBRE FROM Clientes WHERE Clave = @idcliente ) , '' ) IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END " +
					" \n UPDATE DoctosR SET Numero=@COTIZA , FProceso =  GETDATE () WHERE Folio= @DoctoR  IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END " +
					" \n DELETE FROM PCotizas WHERE Clave= @COTIZA IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n if @partidaIng > 0 begin  " +
					" \n 	INSERT INTO PCotizas (Clave,Partida,Cant,Codigo,Precio,Concepto,Estado,IVA,Costo,Fabrica,Nota,Clasif,Destino,HEnvio,MEnvio,Folio,ObservaE, " +
					" \n 		HCancelacion,FGeneracion,IndicePrecio,TEntrega,FS,PrecioI, Recotizar, FK01_PCotizaOrigen ,FK03_idProducto,FK04_fabricante,FK02_Cotiza  )  " +
					" \n 		select @COTIZA,@partidaIng,@cantidadIng,codigo , @precio, concepto , 'Cotización', 0 , costo , fabrica , '', 'A',NULL,NULL,NULL, '0','',NULL,NULL,2,TEntrega ,0,0,0,0,idproducto , fk02_fabricante, @idcotiza from Productos where codigo = '2380001' and fabrica  = 'USP' " +
					" \n 		 IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	INSERT INTO PCotizas (Clave,Partida,Cant,Codigo,Precio,Concepto,Estado,IVA,Costo,Fabrica,Nota,Clasif,Destino,HEnvio,MEnvio,Folio,ObservaE, " +
					" \n 		HCancelacion,FGeneracion,TEntrega,FS,PrecioI, Recotizar, FK01_PCotizaOrigen ,FK03_idProducto,FK04_fabricante,FK02_Cotiza  )  " +
					" \n 		select @COTIZA,@partidaIng,@cantidadIng,codigo , @precio, concepto , 'Cotización', 0 , costo , fabrica , '', 'A',NULL,NULL,NULL,'99','',NULL,GETDATE(),TEntrega ,0,0,0,0,idproducto , fk02_fabricante, @idcotiza from Productos where codigo = '2380001' and fabrica  = 'USP' " +
					" \n 		 IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	INSERT INTO Pendiente(Docto,Partida,FInicio,Responsable,Tipo) VALUES (@COTIZA ,@partidaIng,GETDATE(),( SELECT top 1 usuario FROM Empleados WHERE nivel=29 AND Fase>0),'Por evaluar partida A') IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	select @idPCotiza = (SELECT IDENT_CURRENT ('pcotizas') ) " +
					" \n 	INSERT INTO PCotizaEnSeguimiento (idPCotiza,Estado,Origen,FechaSiguiente) VALUES (@idPCotiza,'En S1','Confirmacion',CONVERT (varchar, GETDATE() , 112) ) IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	INSERT INTO PCotizaHistorial (idPCotiza,Fecha,Realizo,Estado,Comentarios,Origen) VALUES(@idPCotiza,@fechalarga,@esac,'Cotización','Confirmación realizada','Confirmacion') IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n END " +
					" \n IF @partidaEsp > 0 BEGIN  " +
					" \n 	INSERT INTO PCotizas (Clave,Partida,Cant,Codigo,Precio,Concepto,Estado,IVA,Costo,Fabrica,Nota,Clasif,Destino,HEnvio,MEnvio,Folio,ObservaE, " +
					" \n 		HCancelacion,FGeneracion,IndicePrecio,TEntrega,FS,PrecioI, Recotizar, FK01_PCotizaOrigen ,FK03_idProducto,FK04_fabricante,FK02_Cotiza  ) " +
					" \n 		select @COTIZA,@partidaEsp,@cantidadEsp,codigo , @precio, concepto , 'Cotización', 0 , costo , fabrica , '', 'A',null ,  null , null , '0','',NULL,NULL,2,TEntrega ,0,0,0,0,idproducto , fk02_fabricante, @idcotiza from Productos where codigo = '2387001' and fabrica  = 'USP' " +
					" \n 		 IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	INSERT INTO PCotizas (Clave,Partida,Cant,Codigo,Precio,Concepto,Estado,IVA,Costo,Fabrica,Nota,Clasif,Destino,HEnvio,MEnvio,Folio,ObservaE, " +
					" \n 		HCancelacion,FGeneracion,TEntrega,FS,PrecioI, Recotizar, FK01_PCotizaOrigen ,FK03_idProducto,FK04_fabricante,FK02_Cotiza  ) " +
					" \n 		select @COTIZA,@partidaEsp,@cantidadEsp,codigo , @precio, concepto , 'Cotización', 0 , costo , fabrica , '', 'A',NULL,NULL,NULL,'99','',NULL,GETDATE(),TEntrega ,0,0,0,0,idproducto , fk02_fabricante, @idcotiza from Productos where codigo = '2387001' and fabrica  = 'USP'" +
					" \n 		 IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	INSERT INTO Pendiente(Docto,Partida,FInicio,Responsable,Tipo) VALUES (@COTIZA ,@partidaEsp,GETDATE(),( SELECT top 1 usuario FROM Empleados WHERE nivel=29 AND Fase>0),'Por evaluar partida A') IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	select @idPCotiza = (SELECT IDENT_CURRENT ('pcotizas')) " +
					" \n 	INSERT INTO PCotizaEnSeguimiento (idPCotiza,Estado,Origen,FechaSiguiente) VALUES (@idPCotiza,'En S1','Confirmacion',CONVERT (varchar, GETDATE() , 112)) IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n 	INSERT INTO PCotizaHistorial (idPCotiza,Fecha,Realizo,Estado,Comentarios,Origen) VALUES(@idPCotiza,@fechalarga ,@esac,'Cotización','Confirmación realizada','Confirmacion') IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
				 	
					" \n END  " +
					" \n UPDATE Consecutivos SET esBloqueado=0 WHERE Concepto='Cotizas' IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n INSERT INTO SegCotiza (Cotiza,Vendedor,Fecha, Estatus, FRealizo, Recibida , Contacto, Medio  ,FK01_Cotiza ) VALUES (@COTIZA,@esac,GETDATE(),'Confirmada',GETDATE(),1, (select contacto from contactos where idcontacto = @idcontacto), 'Mail',@idcotiza ) IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END " + 
					" \n UPDATE RegistroCampana SET Ingresado =  1 WHERE Codigo = @campania " +
					" \n INSERT INTO Pendiente (Docto,FInicio,Responsable,Tipo) VALUES (@COTIZA,CONVERT (varchar, GETDATE() , 112),@esac ,'Seguimiento') IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +
					" \n INSERT INTO Bitacora (Cotiza,Vendedor,Fecha1,Contacto1) VALUES (@COTIZA,@esac ,CONVERT (varchar, GETDATE() , 112),@nombrecontacto)IF @@ERROR <> 0 BEGIN ROLLBACK TRAN RETURN END  " +

					" \n COMMIT TRAN " ;
			
		//	logger.info(query +  " -parametros- " + parametros);
			
			
			super.jdbcTemplate.update(  query,map);
			
			Long idcotiza = super.queryForLong("SELECT IDENT_CURRENT ('cotizas')");
			return idcotiza;

		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			f.enviarCorreoAvisoExepcion(r, documentoRecibido);
			return -1;
			
		}
	}

	@SuppressWarnings("unchecked")
	public DoctoCotizacion obtenerInfoCotizacionCampana(Long cotiza) {
		try {
			String query = "";

	    query = " \n select PK_Folio, coti.fecha fcreacion , coti.clave , coti.cpago ,emp.usuario Vendedor , FK02_DoctosR as folioDoctoR, IMoneda,Cpago,emp.Nombre as NombreVendedor , factura.* , CONTACTO.*, cli.* from cotizas  as coti " +
	    " \n left join (select nombre, Usuario  from empleados ) as emp on emp.usuario = coti.vendedor " + 
	    " \n left join (SELECT COALESCE(Calle+COALESCE(' - '+Colonia+', ','')+COALESCE(Delegacion+', ','') +Pais+' '+Estado+', CP '+CP, " +
	    " \n   'No existe información en BD sobre la dirección de la empresa.') AS Direccion, coalesce (RazonSocial,'No existe información en BD sobre la dirección de la empresa.') " +
	    " \n    RazonSocial , coalesce(RFC, '') RFC , Alias FROM Empresa)as factura on factura.Alias =coti.factura collate SQL_Latin1_General_CP1_CI_AS " +
	    " \n left join (select COALESCE(titulo+ ' ' ,'')+ COALESCE(contacto,'')+ COALESCE(' / ' + depto,'')NombrePuesto, " +
	    " \n   CASE WHEN (TEL1 IS NOT NULL AND TEL1 <> '')AND(TEL2 IS NOT NULL AND TEL2 <> ''AND TEL2 <> 'ND') THEN COALESCE(tEL1,'')+' / ' +  COALESCE(TEL2,'') " +
	    " \n   WHEN (TEL1 IS NOT NULL AND TEL1 <> '')AND(TEL2 IS NULL OR TEL2 = '' OR TEL2 = 'ND') THEN COALESCE(tEL1,'') " +
	    " \n   WHEN (TEL1 IS NULL OR TEL1 =  '')AND(TEL2 IS NOT NULL AND TEL2 <> ''AND TEL2 <> 'ND') THEN COALESCE(TEL2,'')END AS Ctel " +
	    " \n   ,coalesce ( FAX, 'ND') Cfax,coalesce ( eMail, 'ND')  Cemail, idContacto  from contactos )as contacto on contacto.idcontacto = coti.idContacto " +
	    " \n left join (select Clave as idcliente,Nombre Cnombre ,COALESCE(Rsocial,'') CRSocial, " +
	    " \n COALESCE(RSCalle,'')CCalle, COALESCE(RSDel ,'')CDelegacion,COALESCE(RSCP,'')CCP,COALESCE(RSEstado,'')CEstado from Clientes )as cli on cli.idcliente = coti.FK01_idCliente or cli.Cnombre = coti.Cliente " +
	    " \n where PK_Folio = '" + cotiza + "'";
			
//			logger.info(query);
			List<DoctoCotizacion> list = new ArrayList<DoctoCotizacion>();
			list=  super.jdbcTemplate.query(query, new InfoCotizacionCampanaRowMapper());
	    	return list.get(0);
			
			
		}catch(Exception r){
		//	logger.error(r.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(r, "\nCotiza: "+cotiza);
			return new DoctoCotizacion();
		}
		
	}


	@SuppressWarnings("unchecked")
	public List<PartidaFactura> obtenerInfoPartidasCampana(Long cotiza) {
		try {
			String query = " \n  SELECT pcotizas.Cant , pcotizas.Precio ,idProducto ,pcotizas.partida, pcotizas.idPCotiza, Cantidad,Unidad,Tipo,Productos.Concepto, Productos.Codigo,Productos.Fabrica,Pureza,  " +
					" \n CONVERT ( varchar, Productos.Concepto)+ CONVERT ( varchar,   CASE WHEN Cantidad <> '' And unidad <> '' THEN Productos.Codigo + '(' + Cantidad + ' ' + unidad + ')' " +
					" \n WHEN Cantidad <> '' THEN Productos.Codigo + '(' + Cantidad + ')' END) + coalesce (  " +
					" \n CASE when Tipo = 'Estandares' Or Tipo = 'Reactivos' and Pureza IS  Not null  THEN case when Pureza = 0  THEN ' S/Pureza ' when pureza = 1 then  ' C/Pureza ' end  END , '') AS conceptoFull  " +
					" \n FROM Productos ,  PCotizas, Cotizas WHERE Folio  =  99 and Productos.idProducto = pcotizas.FK03_idProducto and pcotizas.Clave  = cotizas.clave and cotizas.PK_Folio = '" + cotiza + "' order by pcotizas.partida ";

			//logger.info(query);
			return super.jdbcTemplate.query(query, new InfoPartidasCampanaRowMapper());	
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(r, "\nCotiza: "+cotiza);
			return new ArrayList<PartidaFactura>();
		}
		
	}

	@SuppressWarnings("unchecked")
	public List<PartidaFactura> obtenerInfoComplementoCampana(Long cotiza) {
		try {
			String query = " \n  select c.Descripcion concepto, pc.FK03_idProducto idproducto from Cotizas   " +
					" \ninner join PCotizas as pc  on pc.Clave = cotizas.Clave   " +
					" \ninner join Complemento as c on c.Codigo = pc.Codigo and c.Fabrica = pc.Fabrica   " +
					" \nwhere Folio  = 99 and  PK_Folio = '" + cotiza + "'";

			//logger.info(query);
			return super.jdbcTemplate.query(query, new InfoComplementoCampanaRowMapper ());	
		}catch(RuntimeException r){
		//	logger.error(r.getMessage());
			new Funcion().enviarCorreoAvisoExepcion(r, "\nCotiza: "+cotiza);
			return new ArrayList<PartidaFactura>();
		}
	}
	
}