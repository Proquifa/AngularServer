package com.proquifa.net.persistencia.cobrosypagos.facturista.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.catalogos.cobrosypagos.facturista.ComplementoPago;
import com.proquifa.net.modelo.cobrosypagos.facturista.ComprobanteFiscal;
import com.proquifa.net.modelo.cobrosypagos.facturista.ConceptoFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Factura;
import com.proquifa.net.modelo.cobrosypagos.facturista.Facturacion;
import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;
import com.proquifa.net.modelo.cobrosypagos.facturista.PartidaFactura;
import com.proquifa.net.modelo.comun.CatalogoItem;
import com.proquifa.net.modelo.comun.Cliente;
import com.proquifa.net.modelo.comun.Empresa;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.FacturacionDAO;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.ComplementoPDFRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.ConceptofacturaRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.DocumentoFiscalTemporalRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturaElectronicaRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturaEmitidasRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturaPQNetRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturaPendientePDFRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturaRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.FacturacionRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.HistorialFacturaRowMapper;
import com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper.infoEmpresaFacturaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.CatalogoItemRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ClienteRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ComplementoPagoRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.FormaPagoFacturaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.ObtieneInfoCFDIxidFacturaRowMapper;
import com.proquifa.net.persistencia.comun.impl.mapper.UsoCfdiFacturaRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PartidaFacturaRowMapper;

/**
 * @author sarivera
 *
 */

@Repository
public class FacturacionDAOImpl extends DataBaseDAO implements FacturacionDAO {

	Funcion funcion;
	
	final Logger log = LoggerFactory.getLogger(FacturacionDAOImpl.class);

	private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyyMMdd");
	@SuppressWarnings("unchecked")
	public List<Facturacion> consultaFacturacion (String factura, String busqueda, Date finicio, Date ffin, String cliente, String medio, String facturo, String estado, String tipo, String refacturada){
		String query = "";
		Map<String, Object> map = new HashMap<String, Object>();

		if(busqueda.equals("rapida")){
			map.put("factura", factura);
			query = " SELECT (CASE WHEN RegistroRefacturacion.FacturaAntigua IS NULL THEN 'NO' ELSE 'SI' END)AS Refacturada, ROUND(cambio.PDolar,2) AS PDolar, ROUND (Facturas.Importe*Facturas.IVA,2) AS totalIVA, ";
			query += " ROUND (Facturas.Importe + (Facturas.Importe*Facturas.IVA),2) AS Total ,Facturas.Fecha,Facturas.Factura,Facturas.CPedido,Facturas.FPor,Facturas.Tipo,Facturas.Medio,ROUND (Facturas.Importe,2) AS importe, ";
			query += " Facturas.Moneda,ROUND (Facturas.IVA,2) as IVA, Facturas.Estado, Clientes.Nombre,RegistroRefacturacion.FacturaAntigua, RegistroRefacturacion.FacturaNueva, SRefacturacion.Razones, ";
			query += " SRefacturacion.Autorizo , (CASE WHEN SRefacturacion.Razones = 'A solicitud del cliente' THEN SRefacturacion.CAutorizacion ELSE SRefacturacion.Comentarios END)AS razonPop ";
			query += " FROM Facturas LEFT JOIN Clientes ON Facturas.Cliente=Clientes.Clave ";
			query += " LEFT JOIN RegistroRefacturacion ON Facturas.Factura=RegistroRefacturacion.FacturaAntigua "; 
			query += " LEFT JOIN SRefacturacion ON Facturas.FPor=SRefacturacion.FPorOrigen AND Facturas.Factura=SRefacturacion.Factura "; 
			query += " LEFT JOIN (SELECT TOP 1 PDolar FROM Monedas ORDER BY Fecha DESC)AS cambio on cambio.PDolar=cambio.PDolar ";
			query += " WHERE Facturas.Factura like '%:factura%' ";
		}else{
			query = "SELECT (CASE WHEN RegistroRefacturacion.FacturaAntigua IS NULL THEN 'NO' ELSE 'SI' END)AS Refacturada, ROUND(cambio.PDolar,2) AS PDolar, ROUND (Facturas.Importe*Facturas.IVA,2) AS totalIVA, ";
			query +=" ROUND (Facturas.Importe + (Facturas.Importe*Facturas.IVA),2) AS Total ,Facturas.Fecha,Facturas.Factura,Facturas.CPedido,Facturas.FPor,COALESCE(Facturas.Tipo,'Normal')AS Tipo, ";
			query +=" COALESCE(Facturas.Medio,'Fisica')AS Medio,ROUND (Facturas.Importe,2) AS importe,Facturas.Moneda,ROUND (Facturas.IVA,2) as IVA, Facturas.Estado, Clientes.Nombre,RegistroRefacturacion.FacturaAntigua, "; 
			query +=" RegistroRefacturacion.FacturaNueva, SRefacturacion.Razones,SRefacturacion.Autorizo , ";
			query +=" (CASE WHEN SRefacturacion.Razones = 'A solicitud del cliente' THEN SRefacturacion.CAutorizacion ELSE SRefacturacion.Comentarios END)AS razonPop ";
			query +=" FROM Facturas LEFT JOIN Clientes ON Facturas.Cliente=Clientes.Clave ";
			query +=" LEFT JOIN RegistroRefacturacion ON Facturas.Factura=RegistroRefacturacion.FacturaAntigua "; 
			query +=" LEFT JOIN SRefacturacion ON Facturas.FPor=SRefacturacion.FPorOrigen AND Facturas.Factura=SRefacturacion.Factura "; 
			query +=" LEFT JOIN (SELECT TOP 1 PDolar FROM Monedas ORDER BY Fecha DESC)AS cambio on cambio.PDolar=cambio.PDolar "; 
			query +=" WHERE Facturas.Fecha >='" + formatoFecha.format(finicio) + " 00:00' AND Facturas.Fecha <='" + formatoFecha.format(ffin) + " 23:59' ";

			if(!cliente.equals("--TODOS--")){
				map.put("cliente", cliente);
				query += " AND Clientes.Clave = :cliente";
			}
			if(!medio.equals("--TODOS--")){
				map.put("medio", medio);
				query += " AND (Facturas.Medio = :medio OR Facturas.Medio IS NULL) ";
			}
			if(!facturo.equals("--TODOS--")){
				map.put("facturo", facturo);
				query += " AND Facturas.FPor = :facturo ";
			}
			if(!estado.equals("--TODOS--")){
				map.put("estado", estado);
				query += " AND Facturas.Estado = :estado "; 
			}
			if(!tipo.equals("--TODOS--")){
				map.put("tipo", tipo);
				query += " AND (Facturas.Tipo = :tipo or Facturas.Tipo IS NULL) ";
			}
			if(refacturada.equals("SI")){
				query += " AND RegistroRefacturacion.FacturaAntigua IS NOT NULL";    
			}
			if(refacturada.equals("NO")){
				query += "AND RegistroRefacturacion.FacturaAntigua IS NULL";
			}		
		}
		//logger.info(query);
		return super.jdbcTemplate.query(query, map, new FacturacionRowMapper()); 
	}

	@SuppressWarnings("unchecked")
	public List<Factura> obtenerFacturaPedido(Integer part, String cPedido){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("part", part);
			map.put("cPedido", cPedido);

			String query = "SELECT facturas.Factura,facturas.FPor FROM Facturas,PFacturas WHERE facturas.Factura=PFacturas.Factura and PFacturas.CPedido= :cPedido "+ 
					"AND pfacturas.PPedido= :part AND Facturas.CPedido= :cPedido AND Facturas.Estado<>'Cancelada' UNION " +
					"SELECT Remisiones.Factura, Remisiones.FPor FROM Remisiones, PRemisiones WHERE Remisiones.Factura=PRemisiones.Factura AND PRemisiones.CPedido= :cPedido " + 
					"AND PRemisiones.PPedido= :part AND Remisiones.CPedido= :cPedido AND Remisiones.Estado<>'Cancelada'";
			return super.jdbcTemplate.query(query, map, new PartidaFacturaRowMapper());
		}catch (RuntimeException e) {
			return null;
		}
	}

	public Date obtenerFechaFactura(String cPedido){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido", cPedido);

			String query = "select MAX(facturas.fecha) AS Fecha from facturas,pfacturas where  facturas.fpor = pfacturas.fpor and facturas.factura = pfacturas.factura AND pfacturas.cpedido = :cPedido";
			return (Date) super.jdbcTemplate.queryForObject(query, map, Date.class);
		} catch (RuntimeException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Factura> findFacturasXFolio(Long factura, String empresaFactura) {
		try {
			String fpor="";
			Map<String, Object> map = new HashMap<String, Object>();

			if(empresaFactura!=null && !empresaFactura.equals("")){
				map.put("empresaFactura", empresaFactura);
				fpor="AND f.FPor= :empresaFactura ";
			}

			map.put("factura", factura);
			String query= construirConsultaFactura("WHERE f.Estado<>'Cancelada' AND f.Fecha>='20090828' AND f.Factura= :factura "+ fpor);		
			return super.jdbcTemplate.query(query, map, new FacturaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Factura> findFacturas(Factura factura, Date finicio, Date ffin) {
		try {

			funcion = new Funcion();
			Map<String, Object> map = new HashMap<String, Object>();

			String query="",condiciones="";
			if(factura.getIdCliente()!=null && !factura.getIdCliente().equals("") && factura.getIdCliente()>0){
				map.put("idCliente", factura.getIdCliente());
				condiciones += "AND f.Cliente= :idCliente ";
			}
			if(factura.getEstado()!=null){
				map.put("estado", factura.getEstado());
				condiciones += "AND f.Estado= :estado ";
			}
			if(factura.getFacturadoPor()!=null){
				map.put("facturadoPor", factura.getFacturadoPor());
				condiciones += "AND f.FPor= :facturadoPor ";	
			}
			if(finicio!=null && ffin!=null){
				condiciones += "AND f.Fecha>='"+ funcion.convertirFechaAString(finicio) +"' AND f.Fecha<='"+ funcion.convertirFechaAString(ffin) +"' ";
			}else{
				condiciones += "AND f.Fecha>='20090828' ";
			}

			query= construirConsultaFactura("WHERE f.Estado<>'Cancelada' AND f.Fecha>='20090828' "+ condiciones);		

			return super.jdbcTemplate.query(query, map, new FacturaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Factura> obtenerFacturasPorCobrar(Long idCliente) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idCliente", idCliente);
		String query = construirConsultaFactura("WHERE f.Cliente= :idCliente AND f.Estado='Por cobrar' AND c.Clave=f.Cliente AND f.Factura IN (SELECT Factura FROM PagoPendiente)");	
		return super.jdbcTemplate.query(query, map, new FacturaRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<HistorialFactura> findHistorialXFactura(Long idFactura) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idFactura", idFactura);
		try {
			return super.jdbcTemplate.query("SELECT hf.* FROM HistorialFactura AS hf,Facturas AS f WHERE hf.Factura=f.Factura AND hf.FPor=f.FPor AND f.idFactura="+ ":idFactura" +" ORDER BY hf.FechayHora Desc", new HistorialFacturaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	private String construirConsultaFactura(String parametros){
		String consultaFacturas="SELECT DISTINCT(f.idFactura),f.Factura,f.Fecha,f.CPago,f.Cliente,f.Moneda,f.Importe,f.IVA,f.Estado,f.FPor,f.Pedido,f.TCambio,f.CPedido,f.ImprimirTC,f.FolioEntrega,f.DeRemision,f.OrdenCompra,f.Tipo,f.Serie,f.Medio,pd.DoctoR,c.Nombre,"+
				"(CASE WHEN f.CPago='PREPAGO 100%' THEN CAST(pedidoPago.Pago AS VARCHAR(20)) ELSE CASE WHEN rdp.FFin IS NOT NULL THEN rdp.FolioDoctos2 ELSE CASE WHEN rpr.FFin IS NOT NULL THEN rpr.FolioDoctos ELSE 'NA' END END END)AS ContraRecibo,"+
				"(CASE WHEN rdp.FFin IS NOT NULL THEN rdp.FFin ELSE COALESCE(rpr.FFin, NULL) END) AS FRevision,"+
				"(COALESCE(pagoPendiente.Medio,ip.Medio,'Pendiente'))AS MedioPago,"+
				"(f.Importe*(1+f.IVA))AS Monto,"+
				"(COALESCE(pagoPendiente.Moneda,ip.Moneda,'Pendiente')) AS MonedaPago,"+
				"(COALESCE(pagoPendiente.FEP,ip.FEP,NULL))AS FEP,"+
				"(CASE WHEN f.Estado='Cobrada' THEN COALESCE(cb.Fecha,cbf.Fecha,NULL) ELSE NULL END) AS FechaPago,"+
				"(CASE WHEN f.Estado='Cobrada' THEN COALESCE(cb.FolioPC,cbf.FolioPC,NULL) ELSE NULL END) AS FolioPC,"+
				"(CASE WHEN f.Estado='Cobrada' THEN CASE WHEN f.CPago='PREPAGO 100%'THEN cb.MontoPagado ELSE COALESCE(cbf.MontoPagado,NULL) END ELSE NULL END) AS MontoPagado "+
				"FROM Facturas AS f "+
				"LEFT JOIN(SELECT Clave,Nombre FROM Clientes) AS c ON c.Clave=f.Cliente "+
				"LEFT JOIN(SELECT pp.Folio,pp.Pago,pp.CPedido FROM Pedidos AS p, PedidoPago AS pp WHERE p.CPedido=pp.CPedido AND pp.Pedido=p.DoctoR) AS pedidoPago ON pedidoPago.CPedido=f.CPedido "+
				"LEFT JOIN(SELECT RutaDP.Factura,RutaDP.FPor,RutaDP.FolioDoctos2,Pendiente.FFin FROM RutaDP,Pendiente WHERE RutaDP.EstadoRuta='Cerrada' AND RutaDP.Entrega='Realizada' AND RutaDP.EntregayRevision=1 AND Pendiente.Docto=RutaDP.idRuta AND Pendiente.Tipo='A cerrar ruta') AS rdp ON rdp.Factura=f.Factura AND rdp.FPor=f.FPor "+
				"LEFT JOIN(SELECT RutaPR.Factura,RutaPR.FPor,RutaPR.FolioDoctos,Pendiente.FFin FROM RutaPR,Pendiente WHERE RutaPR.EstadoRuta='Cerrada' AND RutaPR.Entrega='Realizada' AND Pendiente.Docto=RutaPR.idRuta AND Pendiente.Tipo='A cerrar ruta') AS rpr ON rpr.Factura=f.Factura AND rpr.FPor=f.FPor "+
				"LEFT JOIN(SELECT Factura,QuienFactura,Monto,Moneda,Medio,FEPago AS FEP FROM PagoPendiente) AS pagoPendiente ON pagoPendiente.Factura=f.Factura AND pagoPendiente.QuienFactura=f.FPor "+
				"LEFT JOIN(SELECT pedidos.CPedido,i.Docto,i.Monto,i.Moneda,i.MedioPago AS Medio,i.FechaPago AS FEP FROM InconsistenciaDePedido AS i,Pedidos AS pedidos WHERE pedidos.DoctoR=i.Docto) AS ip ON ip.CPedido=f.CPedido "+
				"LEFT JOIN(SELECT idPedidoPago,Fecha,MontoPagado,MonedaPagada,FolioPC FROM Cobro) AS cb ON cb.idPedidoPago=pedidoPago.Folio "+
				"LEFT JOIN(SELECT idPedidoPago,Fecha,MontoPagado,MonedaPagada,FolioPC,Factura,FPor FROM Cobro) AS cbf ON cbf.Factura=f.Factura AND cbf.FPor=f.FPor "+
				"LEFT JOIN(SELECT DoctoR,CPedido FROM Pedidos) AS pd ON pd.CPedido=f.CPedido "+
				parametros + 
				"ORDER BY f.Fecha DESC";
		return consultaFacturas;
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> consultaFolios() {
		try {
			String query= 
					" \n SELECT EM.*,EM.Alias,(CON.Valor-ASI.FolioInicio)+1 AS FActual,(ASI.FolioFinal-ASI.FolioInicio)+1 AS FoliosAsignados" +
							" \n 			,CASE WHEN FU.Usados IS NOT NULL" +
							" \n 						THEN					" +
							" \n 	  FU.Usados" +
							" \n 							ELSE 0" +
							" \n 					END AS FUsadosMes" +
							" \n 					,MONTO.TOTALMONTODLL,FCAN.TOTCANCELADAS" +
							" \n 					,CASE WHEN EM.FacturacionElectronica = 1 AND EM.FacturacionMatriz=1 AND EM.PK_Empresa = ASI.FK01_Empresa AND CF.FK01_Empresa=EM.PK_Empresa THEN 1 ELSE 0 END AS FacHab" +
							" \n 					FROM Empresa EM" +
							" \n 					LEFT JOIN (SELECT CASE WHEN F.FPor = 'ProquifaServicios'" +
							" \n 												THEN 'Proquifa Servicios'" +
							" \n 										   WHEN F.FPor = 'ProquifaElSalvador'" +
							" \n 												THEN 'Proquifa El Salvador'" +
							" \n 											ELSE" +
							" \n 												F.FPor" +
							" \n 									  END AS Fpor" +
							" \n 					,SUM(CASE WHEN F.Moneda='USD' OR F.Moneda = 'Dolares'" +
							" \n 								THEN" +
							" \n 									RES.TOTPORFACTURA" +
							" \n 								 WHEN F.Moneda= 'EUR' OR F.Moneda = 'Euros'" +
							" \n 									THEN" +
							" \n 										RES.TOTPORFACTURA* MO.EDolar" +
							" \n 									ELSE" +
							" \n 										RES.TOTPORFACTURA / case when F.TCambio = 0 or F.TCambio is null then 1 else F.TCambio end" +
							" \n 						END) AS TOTALMONTODLL" +
							" \n 					FROM Facturas AS F" +
							" \n 					LEFT JOIN (SELECT SUM(IMPORTE) AS TOTPORFACTURA,Factura,Estado,Fpor FROM PFacturas GROUP BY FPor,Factura,Estado) AS RES ON RES.FPor = F.FPor AND F.Factura = RES.Factura AND (RES.Estado is null OR RES.Estado<>'Cancelada')" +
							" \n 					LEFT JOIN (SELECT * FROM Monedas) AS MO ON CAST(MO.Fecha AS DATE)=CAST(F.Fecha AS DATE)" +
							" \n 					WHERE (F.Estado<>'Cancelada' OR F.Estado IS NULL) AND YEAR(F.Fecha)=YEAR(GETDATE()) AND MONTH(F.Fecha)=MONTH(GETDATE()) GROUP BY F.FPor) AS MONTO ON MONTO.FPor = (EM.Alias COLLATE Modern_Spanish_CI_AS)" +
							" \n 					LEFT JOIN (SELECT * FROM AsignacionFolio WHERE Asignado=1 AND Tipo = 'Factura') AS ASI ON ASI.FK01_Empresa = EM.PK_Empresa" +
							" \n 					LEFT JOIN(SELECT PK_Empresa,CASE WHEN Alias='Proquifa Servicios'" +
							" \n 			  										 THEN 'FProquifaServicios'" +
							" \n 													 WHEN Alias = 'Proquifa El Salvador'" +
							" \n 													 THEN 'FProquifaElSalvador'" +
							" \n 													 ELSE" +
							" \n 														'F'+ Alias" +
							" \n 												END COLLATE Modern_Spanish_CI_AS AS PF FROM EMPRESA ) AS CPCONSE ON CPCONSE.PK_Empresa = EM.PK_Empresa" +
							" \n 					LEFT JOIN (SELECT * FROM Consecutivos)AS CON ON CON.Concepto= CPCONSE.PF" +
							" \n 					LEFT JOIN (SELECT CASE WHEN FPor = 'ProquifaServicios'" +
							" \n 												THEN 'Proquifa Servicios'" +
							" \n 										   WHEN FPor = 'ProquifaElSalvador'" +
							" \n 												THEN 'Proquifa El Salvador'" +
							" \n 											ELSE" +
							" \n 												FPor" +
							" \n 									  END AS Fpor,COUNT(*) AS Usados FROM Facturas WHERE YEAR(Fecha)=YEAR(GETDATE()) AND MONTH(Fecha)=MONTH(GETDATE()) GROUP BY FPor) AS FU ON FU.FPor = (EM.Alias COLLATE Modern_Spanish_CI_AS)" +
							" \n 					LEFT JOIN (SELECT CASE WHEN FPor = 'ProquifaServicios'" +
							" \n 												THEN 'Proquifa Servicios'" +
							" \n 										   WHEN FPor = 'ProquifaElSalvador'" +
							" \n 												THEN 'Proquifa El Salvador'" +
							" \n 											ELSE" +
							" \n 												FPor" +
							" \n 									  END AS Fpor,COUNT (*) AS TOTCANCELADAS FROM Facturas AS FA WHERE FA.Estado='Cancelada' AND YEAR(Fecha)=YEAR(GETDATE()) AND MONTH(Fecha)=MONTH(GETDATE()) GROUP BY FPor) AS FCAN ON FCAN.FPor = (EM.Alias COLLATE Modern_Spanish_CI_AS)" +
							" \n 					LEFT JOIN (SELECT FK01_Empresa FROM ConceptoFactura GROUP BY FK01_Empresa) AS CF ON CF.FK01_Empresa = EM.PK_Empresa" +
							" \n 					ORDER BY FacHab";
			return super.jdbcTemplate.query(query, new infoEmpresaFacturaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Empresa> getInfoEmpresa() {
		funcion = new Funcion();
		try {
			String query=
					"	SELECT *,NULL AS FActual,NULL AS FUsadosMes,NULL AS FoliosAsignados FROM Empresa" +
							"	LEFT JOIN (SELECT " +
							"	F.FPor," +
							" SUM(" + funcion.sqlConversionMonedasADolar("F.Moneda", "1", "F.Importe", "MO", "F.TCambio","",false) + ") AS TOTALMONTODLL," +
							"	NULL AS FacHab" +
							"	FROM Facturas AS F" +
							"	LEFT JOIN (SELECT * FROM Monedas) AS MO ON YEAR(MO.Fecha)=2011 AND MONTH(MO.Fecha)<=MONTH(F.Fecha) AND DAY(MO.Fecha)=DAY(F.Fecha)" +
							"	WHERE F.Estado<>'Cancelada' AND MONTH(F.Fecha)<= MONTH(GETDATE()) AND YEAR(F.Fecha)=2011 GROUP BY F.FPor) AS MONTO ON MONTO.FPor =(Empresa.Alias COLLATE Modern_Spanish_CI_AS)" +
							"	LEFT JOIN (SELECT FPor,COUNT (*) AS TOTCANCELADAS FROM Facturas AS FA WHERE FA.Estado='Cancelada' AND MONTH(FA.Fecha)<=MONTH(GETDATE()) AND YEAR(FA.Fecha)=2011 GROUP BY FPor) AS FCANCELADA ON FCANCELADA.FPor = (Empresa.Alias COLLATE Modern_Spanish_CI_AS)" +
							"	WHERE FacturacionElectronica = 1 AND FacturacionMatriz = 1";
			//			//logger.info(query);
			return super.jdbcTemplate.query(query, new infoEmpresaFacturaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<ConceptoFactura> obtenerConceptosFactura(Integer idEmpresa) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idEmpresa",idEmpresa);
			return super.jdbcTemplate.query("SELECT * FROM conceptoFactura WHERE FK01_Empresa = "+ ":idEmpresa", new ConceptofacturaRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Cliente> obtenerClientesHabilitados() {
		try {
			return super.jdbcTemplate.query("SELECT *,NOM_EV='' FROM Clientes where Habilitado = '1'", new ClienteRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	public Long insetarFactura(Factura fac) {
		try{
			Date fecha = new Date();			
			Double iva = 0.00;
			if(fac.getIva()!=0.00){
				iva = 0.16;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Double tipoDCambio=fac.getTipoCambio();
			if(fac.getMoneda().equals("EUR")){
				tipoDCambio = (Double) super.jdbcTemplate.queryForObject("SELECT ('"+ fac.getTipoCambio() +"'/PDolar) AS TC FROM Monedas WHERE Fecha='"+ formatoFecha.format(fecha) + "'",map, Double.class);
			}else if(fac.getMoneda().equals("MXN")){
				fac.setMoneda("M.N.");
				tipoDCambio = (Double) super.jdbcTemplate.queryForObject("SELECT PDolar FROM Monedas WHERE Fecha='"+ formatoFecha.format(fecha) + "'",map, Double.class);
			}

			map.put("Factura", fac.getNumeroFactura());
			map.put("Fecha",fecha);
			map.put("CPago", fac.getCondicionesPago());
			map.put("Cliente", fac.getIdCliente());
			map.put("Moneda", fac.getMoneda());
			map.put("Importe", fac.getImporte());
			map.put("IVA", iva);
			map.put("Estado", fac.getEstado());
			map.put("FPor", fac.getFacturadoPor());
			map.put("TCambio", tipoDCambio);
			map.put("Tipo", fac.getTipo());
			map.put("Serie", fac.getSerie());
			map.put("Medio", fac.getMedio());
			map.put("DeSistema", 0);

			String query = "INSERT INTO Facturas (Factura,Fecha,CPago,Cliente,Moneda,Importe,IVA,Estado,FPor,TCambio,Tipo,Serie,Medio,DeSistema) VALUES";
			query+= "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			super.jdbcTemplate.update(query, map);
			Long idFactura = super.queryForLong("SELECT IDENT_CURRENT ('Facturas')");

			//Actualiza el consecutivo de la factura
			super.jdbcTemplate.update("UPDATE Consecutivos SET Valor=Valor+1 WHERE Concepto='F" + fac.getFacturadoPor() +"'",map);
			return idFactura;

		}catch (RuntimeException rte) {
			//logger.info("Error: "+ rte.getMessage());
			return -1L;
		}
	}

	public Float getTipoCambio(String tipo){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tipo", tipo);
			String query = "SELECT TOP 1 CAST(PDolar AS VARCHAR(MAX))+','+CAST(EDolar AS VARCHAR(MAX))+','+CAST(LDolar AS VARCHAR(MAX))+','+CAST(YDolar AS VARCHAR(MAX))+','+" +
					"										   CAST(DDolar AS VARCHAR(MAX)) FROM Monedas WHERE Fecha <= CAST(GETDATE() AS DATE) ORDER BY Fecha DESC";
			//			"										   CAST(DDolar AS VARCHAR(MAX))+','+CAST(CHFDolar AS VARCHAR(MAX)) FROM Monedas WHERE Fecha <= CAST(GETDATE() AS DATE) ORDER BY Fecha DESC";

			//logger.info(query);

			String tipocambioStr= (String) super.jdbcTemplate.queryForObject(query, map,String.class);
			Float tipocambio = null;
			String[] monedas = tipocambioStr.split(",");
			//monedas = tipocambio.split(",");
			//[0]=PDolar
			//[1]=EDolar
			//[2]=LDolar
			//[3]=YDolar
			//[4]=DDolar
			//[5]=CHF



			if(tipo.equals("dolar")){
				tipocambio= Float.valueOf(monedas[0]);
			}else if(tipo.equals("euro")){
				tipocambio= Float.valueOf(monedas[0])*Float.valueOf(monedas[1]);
			}else if(tipo.equals("libra")){
				tipocambio= Float.valueOf(monedas[0])*Float.valueOf(monedas[2]);
			}else if(tipo.equals("yen")){
				tipocambio= Float.valueOf(monedas[0])*Float.valueOf(monedas[3]);
			}else if(tipo.equals("DlCan")){
				tipocambio= Float.valueOf(monedas[0])*Float.valueOf(monedas[4]);
			}else if(tipo.equals("CHF")){
				tipocambio= Float.valueOf(monedas[0])*Float.valueOf(monedas[5]);
			}



			return tipocambio;
		}catch(Exception e){
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Factura> findFacturasEmitidasPQNet() {
		try {
			String query="SELECT f.Factura,c.CURP AS RFC_Cliente,c.Nombre AS NombreCliente,f.Fecha,f.Importe, " +
					"f.Estado,e.RazonSocial,e.Alias,e.RFC,e.PK_Empresa AS idEmpresa,f.Serie " +
					"FROM Facturas AS f " +
					"LEFT JOIN(SELECT * FROM Empresa) AS e ON REPLACE((e.Alias COLLATE Modern_Spanish_CI_AS),' ','')=f.FPor " +
					"LEFT JOIN(SELECT * FROM Clientes) AS c ON c.Clave=f.Cliente " +
					"WHERE f.DeSistema=0 AND f.Estado='Por Cobrar'";
			//			//logger.info(query);
			return super.jdbcTemplate.query(query, new FacturaPQNetRowMapper());
		} catch (Exception e) {
			//logger.info("Error: " + e.getMessage());
			return null;
		}
	}

	public Boolean actualizarFactura(Factura factura) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Estado", factura.getEstado());
			map.put("Fecha", factura.getFecha());
			map.put("CPago", factura.getCondicionesPago());
			map.put("Moneda", factura.getMoneda());
			map.put("IVA", factura.getIva());
			map.put("Importe", factura.getImporte());
			map.put("TCambio", factura.getTipoCambio());
			map.put("Tipo", factura.getTipo());
			map.put("MPago", factura.getMonedaPago());
			map.put("FPago", factura.getFPago());
			map.put("idFactura", factura.getIdFactura());
			String query = "UPDATE Facturas SET Estado=?,Fecha=?,CPago=?,Moneda=?,IVA=?,";
			query += "Importe=?,TCambio=?,Tipo=?,MPago=?,FPago=? WHERE idFactura=?";

			super.jdbcTemplate.update(query, map);
			return true;

		} catch (Exception e) {
			//logger.info("Error: "+ e.getMessage());
			return false;
		}
	}

	public Factura getFactura(String factura, String fpor) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("fpor", fpor);
			String query ="SELECT idFactura,Factura,Fecha,CPago,Cliente,Moneda,Importe,IVA,Estado,FPor,Pedido,TCambio,CPedido,ImprimirTC," +
					"FolioEntrega,DeRemision,OrdenCompra,Tipo,Serie,Medio,FRevision,FPago AS FechaPago,CRecibo AS ContraRecibo," +
					"MPago AS MonedaPago,TPago AS MedioPago,NULL AS DoctoR,NULL AS Nombre,NULL AS MontoPagado,NULL AS Monto,CONVERT(DATE,NULL) AS FEP," +
					"NULL AS FolioPC FROM Facturas WHERE Factura=:factura AND FPor= :fpor";

			//logger.info("Info"+query);

			return (Factura) super.jdbcTemplate.queryForObject(query, map, new FacturaRowMapper());

		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Factura> findFacturasEmitidas(String condiciones,boolean dentroSistema)
			throws ProquifaNetException {
		try {
			//Consulta
			Map<String, Object> map = new HashMap<String, Object>();
			String query="";

			query += "\n SELECT f.Factura,c.CURP AS RFC_Cliente,c.Nombre AS NombreCliente,f.Fecha,f.Importe, ";
			query += "f.Estado,e.RazonSocial,e.Alias,e.RFC,e.PK_Empresa AS idEmpresa,f.Serie, C.Clave, F.Importe,";
			query += "\n CASE WHEN f.Estado='Cobrada' AND CBANCO.NoCuenta IS NOT NULL THEN SUBSTRING((REPLACE(CBANCO.NoCuenta ,'-','')), LEN(REPLACE(CBANCO.NoCuenta ,'-',''))-3,4) ";
			query += "\n WHEN f.Estado='Por Cobrar' THEN 'Pendiente' ELSE 'ND' END CuentaBanco, ";
			query += "\n CAST (CASE	WHEN F.Moneda = 'M.N.' OR F.Moneda = 'Pesos' OR F.Moneda = 'MXN' THEN COALESCE(f.Importe, PF.TI)";
			query += "\n WHEN F.Moneda = 'EUR' THEN COALESCE(f.Importe, PF.TI) * COALESCE(MON.EDolar,1) * F.TCambio";
			query += "\n WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN COALESCE(f.Importe, PF.TI) * F.TCambio END AS DECIMAL(9,2))";
			query += "\n IMPORTE_MN, ";
			query += "\n CAST((F.IVA * CASE	WHEN F.Moneda = 'M.N.' OR F.Moneda = 'Pesos' OR F.Moneda = 'MXN' THEN COALESCE(f.Importe, PF.TI)";
			query += "\n	WHEN F.Moneda = 'EUR' THEN COALESCE(f.Importe, PF.TI) * COALESCE(MON.EDolar,1) * F.TCambio";
			query += "\n	WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN COALESCE(f.Importe, PF.TI) * F.TCambio END)AS DECIMAL(9,2)) IVA_MN ,";
			query += "\n CAST (CASE	WHEN F.Moneda = 'M.N.' OR F.Moneda = 'Pesos' OR F.Moneda = 'MXN' THEN COALESCE(f.Importe, PF.TI) / CASE WHEN F.TCambio = 0 THEN 1 ELSE F.TCambio END";
			query += "\n	WHEN F.Moneda = 'EUR' THEN COALESCE(f.Importe, PF.TI) * COALESCE(MON.EDolar,1) ";
			query += "\n	WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN COALESCE(f.Importe, PF.TI) END AS DECIMAL(9,2))";
			query += "\n IMPORTE_USD, ";
			query += "\n CAST((F.IVA * CASE	WHEN F.Moneda = 'M.N.' OR F.Moneda = 'Pesos' OR F.Moneda = 'MXN' THEN COALESCE(f.Importe, PF.TI) / CASE WHEN F.TCambio = 0 THEN 1 ELSE F.TCambio END";
			query += "\n	WHEN F.Moneda = 'EUR' THEN COALESCE(f.Importe, PF.TI) * COALESCE(MON.EDolar,1) ";
			query += "\n	WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN COALESCE(f.Importe, PF.TI)  END)AS DECIMAL(9,2)) IVA_USD , f.TCambio, F.Moneda, MON.EDolar, F.IVA,";
			query += "\n COALESCE(c.Vendedor, 'NO ASIGNADO') Vendedor, COALESCE(emp.Usuario,'NO ASIGNADO') Cobrador " + (!dentroSistema ? ",CO.FolioPC" : "")+", F.UUID";
			query += "\n FROM Facturas AS f ";
			query += (!dentroSistema ? "\n LEFT JOIN (SELECT Factura,FPor,MAX(FolioPC) AS FolioPC FROM Cobro WHERE FolioPC IS NOT NULL GROUP BY Factura,FPor) AS CO ON F.Factura = CO.Factura AND F.FPor = CO.FPor" : "");
			query += "\n LEFT JOIN CuentaBanco CBANCO ON f.FK01_idCuentaBanco = CBANCO.idCuenta ";
			query += "\n LEFT JOIN (SELECT * FROM Empresa) AS e ON REPLACE((e.Alias COLLATE Modern_Spanish_CI_AS),' ','')=f.FPor ";
			query += "\n LEFT JOIN (SELECT * FROM Clientes) AS c ON c.Clave=f.Cliente ";
			query += "\n LEFT JOIN (SELECT SUM(Importe * Cant) AS TI, Factura, FPor FROM PFacturas GROUP BY Factura, FPor) AS PF ON PF.Factura = F.Factura AND PF.FPor = F.FPor";
			query += "\n LEFT JOIN (SELECT * FROM Monedas) AS MON ON YEAR(MON.Fecha) = YEAR(F.Fecha) AND MONTH(MON.Fecha) = MONTH(F.Fecha) AND DAY(MON.Fecha) = DAY(F.Fecha)";
			query += "\n LEFT JOIN (SELECT * FROM Empleados) emp on emp.Clave =  c.Cobrador";
			query += condiciones;

			return super.jdbcTemplate.query(query, map, new FacturaEmitidasRowMapper());
		} catch (Exception e) {
			log.info(e.toString());
			return null;
		}
	}

	@Override
	public Double getTotalFacturadoClienteEmpresa(String condiciones)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condiciones", condiciones);
			String query = 	" \n	SELECT 	coalesce(SUM((CASE WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN PF.Importe WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN M.EDolar * PF.Importe ELSE PF.Importe / F.TCambio END * PF.cant)),0) TOTALDOLARES " +
					" \n	FROM PFacturas PF " +
					" \n	LEFT JOIN (SELECT * FROM Facturas) F ON F.FPor = PF.FPor AND F.Factura = PF.Factura" +
					" \n	LEFT JOIN (SELECT * FROM Monedas) M ON M.Fecha = F.Fecha" +
					" \n	WHERE PF.Estado IS NULL AND F.Estado <> 'Cancelada' AND F.Estado <> 'Por cancelar' AND F.Estado <> 'A refacturaci��n' :condiciones" ;
			//			//logger.info(query);
			return (Double) super.jdbcTemplate.queryForObject(query,map, Double.class);

		}catch(Exception e){
			return null;
		}

	}

	public String getUuid(int folio, String fPor) {
		String query = "";
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folio", folio);
			map.put("fPor", fPor);
			query = "SELECT UUID FROM Facturas WHERE Factura = :folio AND FPor =  :fPor ";
			return (String) super.jdbcTemplate.queryForObject(query,map, String.class);
		} catch (Exception e) {
			log.info(query);
			//			logger.error("Error: " + e.getMessage());
			//			funcion = new Funcion();
			//			funcion.enviarCorreoAvisoExepcion(e, "query: " + query,
			//					"idcliente: " + idcliente);
			return null;
		}
	}

	@Override
	public Double getMontoFactura(Long factura, String empresa)
			throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("factura", factura);
			map.put("empresa", empresa);
			String query = 	" \n	SELECT COALESCE(SUM((CASE WHEN F.Moneda = 'Dolares' OR F.Moneda = 'USD' THEN PF.Importe WHEN F.Moneda = 'EUR' OR F.Moneda = 'Euros' THEN M.EDolar * PF.Importe ELSE PF.Importe / F.TCambio END * PF.cant)) ,0) T" +
					" \n	FROM PFacturas PF" +
					" \n	LEFT JOIN (SELECT * FROM Facturas) F ON F.FPor = PF.FPor AND F.Factura = PF.Factura" +
					" \n	LEFT JOIN (SELECT * FROM Monedas) M ON M.Fecha = F.Fecha" +
					" \n	WHERE PF.Estado IS NULL AND PF.Factura = :factura  AND PF.FPor = :empresa" ;

			return (Double) super.jdbcTemplate.queryForObject(query,map ,Double.class);

		}catch(Exception e){
			return null;
		}
	}
	@Override
	public Long insertDocumentoFiscalTemporal(Factura fact) {

		try{

			Date fecha = new Date();			
			Double iva = 0.00;
			if(fact.getIva()!=0.00){
				iva = 0.16;
			}
			Map<String, Object> map = new HashMap<String, Object>();

			Double tipoDCambio =fact.getTipoCambio();
			if (fact.getMoneda() !=null){
				if(fact.getMoneda().equals("EUR")){
					tipoDCambio = (Double) super.jdbcTemplate.queryForObject("SELECT ('"+ fact.getTipoCambio() +"'/PDolar) AS TC FROM Monedas WHERE Fecha='"+ formatoFecha.format(fecha) + "'",map, Double.class);
				}else if(fact.getMoneda().equals("MXN")){
					fact.setMoneda("M.N.");
					tipoDCambio = (Double) super.jdbcTemplate.queryForObject("SELECT PDolar FROM Monedas WHERE Fecha='"+ formatoFecha.format(fecha) + "'",map, Double.class);
				}
			}


			map.put("Factura", null);
			map.put("Fecha", fecha);
			map.put("CPago",fact.getCondicionesPago());
			map.put("Cliente", fact.getIdCliente());
			map.put("Moneda", fact.getMoneda());
			map.put("Importe",fact.getImporte());
			map.put("IVA",iva );
			map.put("Estado",fact.getEstado());
			map.put("FPor",fact.getFacturadoPor());
			map.put("TCambio", tipoDCambio);
			map.put("Tipo",fact.getTipo() );
			map.put("Serie",fact.getSerie() );
			map.put("Medio", fact.getMedio());
			map.put("DeSistema", 0);
			map.put("TipoDocto", "Factura");
			//			String parametros =  "(" + null + "," + fecha+ "," + fact.getCondicionesPago() + "," + fact.getIdCliente() + "," + fact.getMoneda() + "," + fact.getImporte() + "," + iva + "," ;
			//			parametros += fact.getEstado() + "," + fact.getFacturadoPor() + "," + tipoDCambio + "," + fact.getTipo() + "," + fact.getSerie() + "," + fact.getMedio() + "," + 0 + "," + "Factura)";
			String query = "INSERT INTO DocumentoFiscalTemporal (Factura,Fecha,CPago,Cliente,Moneda,Importe,IVA,Estado,FPor,TCambio,Tipo,Serie,Medio,DeSistema, TipoDocto) VALUES";
			//query+= "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//logger.info(query + " - " + parametros);
			super.jdbcTemplate.update(query, map);
			Long idFactura = super.queryForLong("SELECT IDENT_CURRENT ('DocumentoFiscalTemporal')");
			return idFactura;
		}catch (RuntimeException rte) {
			return -1L;
		}
	}

	@Override
	public Boolean updateDocumentoFiscalTemporal(Factura fact) {
		try{
			Date fecha = new Date();
			Double iva = 0.00;
			if(fact.getIva()!=0.00){
				iva = 0.16;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			Double tipoDCambio=fact.getTipoCambio();
			if (fact.getMoneda() !=null){
				if(fact.getMoneda().equals("EUR")){
					tipoDCambio = (Double) super.jdbcTemplate.queryForObject("SELECT ('"+ fact.getTipoCambio() +"'/PDolar) AS TC FROM Monedas WHERE Fecha='"+ formatoFecha.format(fecha) + "'",map, Double.class);
				}else if(fact.getMoneda().equals("MXN")){
					fact.setMoneda("M.N.");
					tipoDCambio = (Double) super.jdbcTemplate.queryForObject("SELECT PDolar FROM Monedas WHERE Fecha='"+ formatoFecha.format(fecha) + "'",map, Double.class);
				}else{
					fact.setMoneda(null);
				}
			}	

			map.put("Factura",null);
			map.put("Fecha",fecha);
			map.put("CPago",fact.getCondicionesPago());
			map.put("Cliente",fact.getIdCliente());
			map.put("Moneda",fact.getMoneda());
			map.put("Importe",fact.getImporte());
			map.put("IVA",iva);
			map.put("Estado",fact.getEstado());
			map.put("FPor",fact.getFacturadoPor());
			map.put("TCambio",tipoDCambio);
			map.put("Tipo",fact.getTipo());
			map.put("Serie",fact.getSerie());
			map.put("Medio",fact.getMedio());
			map.put("DeSistema",0);
			map.put("TipoDocto","Factura");
			map.put("PK_DocumentoFiscalTemporal",fact.getIdDocumentoFiscal());

			//			String parametros = "(null,"+fecha + "," + fact.getCondicionesPago() + "," + fact.getIdCliente() + "," + fact.getMoneda() + "," + fact.getImporte() + "," + iva + ",";
			//			parametros += fact.getEstado() + "," + fact.getFacturadoPor() + "," + tipoDCambio + "," + fact.getTipo() + "," + fact.getSerie() + "," + fact.getMedio() + ",0," + "Factura" + "," + fact.getIdDocumentoFiscal()+ ")";
			String query = "UPDATE  DocumentoFiscalTemporal set Factura = ?,Fecha= ?,CPago= ?,Cliente= ?,Moneda= ?,Importe= ?,IVA= ?,Estado= ?,FPor=?,TCambio= ?,Tipo= ?,Serie= ?,Medio= ?,DeSistema= ?, TipoDocto =? WHERE PK_DocumentoFiscalTemporal = ? ";
			//logger.info(query + " - " + parametros );
			//query+= "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			super.jdbcTemplate.update(query, map);
			return true ;

		}catch (RuntimeException rte) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Factura> findDocumentosFiscalTemporal(
			String parametro) {
		try{
			String query = 	" select * from documentoFiscalTemporal " + parametro ;
			return super.jdbcTemplate.query(query, new DocumentoFiscalTemporalRowMapper());

		}catch(Exception e){
			return null;
		}
	}


	@Override
	public Boolean deleteDocumentoFiscalTemporal(Long idDocumentoFiscal) 
			throws ProquifaNetException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDocumentoFiscal", idDocumentoFiscal);
			StringBuilder sbQuery = new StringBuilder("");
			sbQuery.append("Declare @PK_DocumentoFiscalTemporal as int select @PK_DocumentoFiscalTemporal  =  :idDocumentoFiscal" );
			sbQuery.append(" \n DELETE FROM PDocumentoFiscalTemporal WHERE FK01_DocumentoFiscalTemporal  = @PK_DocumentoFiscalTemporal \n");
			sbQuery.append(" \n DELETE FROM DocumentoFiscalTemporal WHERE PK_DocumentoFiscalTemporal =  @PK_DocumentoFiscalTemporal \n");

			super.jdbcTemplate.update(sbQuery.toString(),map);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Factura obtenerDocumentoFiscalTemporalxidDocto(Long idDoctoFiscalTemporal) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idDoctoFiscalTemporal", idDoctoFiscalTemporal);
			String query = 	" SELECT * FROM documentoFiscalTemporal WHERE PK_DocumentoFiscalTemporal = :idDoctoFiscalTemporal";
			return (Factura) super.jdbcTemplate.queryForObject(query,map, new DocumentoFiscalTemporalRowMapper());

		}catch(Exception e){
			return null;
		}
	}

	public Factura obtenerComprobanteDePago(Long idFactura){
		try{	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFactura", idFactura);
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append(" \n");
			sbQuery.append("select C.clave AS idCliente, C.NumCtaPago, F.Cliente, F.idFactura, F.Factura, F.FPago, F.Moneda, F.MPago, F.Serie, F.UUID, \n"); 
			sbQuery.append("CASE \n");
			sbQuery.append("WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Efectivo' THEN '01' \n");
			sbQuery.append("WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Cheque' THEN '02' \n");
			sbQuery.append("WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Transferencia' THEN '03' \n");
			sbQuery.append("WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='Tarjeta' THEN '04' \n");
			sbQuery.append("WHEN C.NumCtaPago IS NOT NULL AND (C.MedioPago='NO IDENTIFICADO' OR C.MedioPago='NO APLICA' OR C.MedioPago='DEPÓSITO BANCARIO' OR C.MedioPago='NA') THEN '05' \n");
			sbQuery.append("WHEN C.NumCtaPago IS NOT NULL AND C.MedioPago='OTROS' THEN '99' \n");
			sbQuery.append("ELSE '99' \n");
			sbQuery.append("END AS MedioDePago \n");
			sbQuery.append("from Clientes AS C \n");
			sbQuery.append("left Join Facturas AS F ON C.clave=F.Cliente \n");
			sbQuery.append("where  F.idFactura = :idFactura");

			return (Factura )super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new ComplementoPagoRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@SuppressWarnings("unchecked")
	public List<ComprobanteFiscal> obtenerInfoCFDIxidFactura(Long idFactura){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFactura", idFactura);
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append(" \n");
			sbQuery.append(" SELECT  F.idFactura, F.Factura, E.RFC AS RFCEmisor, E.RazonSocial AS RazonSocialEmisor, E.CP AS LugarExpedicion, \n "); 
			sbQuery.append(" C.NumCtaPago, C.Pais AS PaisReceptor, C.CURP AS RFCReceptor, C.RSocial AS RazonSocialReceptor, PF.Cant AS Cantidad, \n ");
			sbQuery.append(" PF.Clave AS ClaveProdServ, PF.Codigo, PF.Concepto AS Descripcion, PF.Importe AS ImportePF, PF.iva, \n ");
			sbQuery.append(" F.Moneda, F.TCambio, F.Importe AS ImporteF \n ");   
			sbQuery.append(" FROM Facturas AS F \n ");
			sbQuery.append(" LEFT JOIN Empresa AS E ON E.Alias = F.FPor CollATE Modern_Spanish_CI_AS \n ");
			sbQuery.append(" LEFT JOIN Clientes AS C ON C.Clave = F.Cliente \n ");
			sbQuery.append(" LEFT JOIN PFacturas AS PF ON PF.Factura = F.Factura and PF.FPor = F.Fpor \n ");
			sbQuery.append("WHERE F.idFactura = \n :idFactura");

			return (List<ComprobanteFiscal>) super.jdbcTemplate.query(sbQuery.toString(), new ObtieneInfoCFDIxidFacturaRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public CatalogoItem obtenerUsoCFDI (int idCFDI){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idCFDI", idCFDI);
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append(" select PK_Folio as clave, Tipo as nombre from ValorCombo where Concepto = 'UsoCFDI' and Pk_Folio = :idCFDI");

			return (CatalogoItem) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new CatalogoItemRowMapper ());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CatalogoItem obtenerMetodoPago(int metPago)  {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("metPago", metPago);
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append(" select PK_Folio as clave, Tipo as nombre from ValorCombo where Concepto = 'MetodoPago' and Pk_Folio = :metPago ");
			return (CatalogoItem) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new CatalogoItemRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public CatalogoItem obtenerClaveUnidad(int claveUnidad) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("claveUnidad", claveUnidad);
			StringBuilder sbQuery = new StringBuilder("select PK_Folio as clave, ClaveUnidad as Nombre from c_ClaveUnidad where PK_Folio = :claveUnidad ");
			return (CatalogoItem) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new CatalogoItemRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public CatalogoItem obtenerClaveProdServ(int claveProdServ) throws ProquifaNetException {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("claveProdServ", claveProdServ);
			StringBuilder sbQuery = new StringBuilder("select PK_Folio as clave, ClaveProdServ as nombre from c_ClaveProdServ where PK_Folio = :claveProdServ");
			return (CatalogoItem) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new CatalogoItemRowMapper());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public Boolean actualizarPDFGenerado(Factura factura) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idFactura",factura.getIdFactura());
			String query = "UPDATE facturas SET PDFGenerado = 1 WHERE idFactura= :idFactura";  

			super.jdbcTemplate.update(query,map);
			return true;
		} catch (Exception e) {
			return false;
		}		
	}


	@SuppressWarnings("unchecked")
	public List<Factura> findFacturaPendiente(String idFactura) {
		try {
			String query="SELECT TOP 1 idFactura, Factura, Fpor FROM facturas WHERE (fPor < > 'Pharma' or fPor < > 'Ryndem') and medio = 'Electronica' and PDFGenerado = 0 ";
			if(!idFactura.equals("")) {
				query+=" and idFactura = "+idFactura+"";
			}
			query+=" ORDER BY idFactura DESC";
			
			return super.jdbcTemplate.query(query, new FacturaPendientePDFRowMapper());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public CatalogoItem obtenerUsoCFDI (String usoCfdi){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("usoCfdi", usoCfdi);
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("SELECT valor FROM valorCombo WHERE concepto = 'UsoCFDI' and tipo = :usoCfdi");

			return (CatalogoItem) super.jdbcTemplate.queryForObject(sbQuery.toString(), map,new UsoCfdiFacturaRowMapper ());
		}catch (Exception e){
			//e.printStackTrace();
		}
		return null;
	}

	public CatalogoItem obtenerFormaPago (String formaPago){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("formaPago", formaPago);
			StringBuilder sbQuery = new StringBuilder();
			sbQuery.append("SELECT valor FROM valorCombo WHERE concepto = 'formaPago' AND tipo = :formaPago");

			return (CatalogoItem) super.jdbcTemplate.queryForObject(sbQuery.toString(),map, new FormaPagoFacturaRowMapper());
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Factura obtenerDatosCliente(String factura) throws ProquifaNetException{
		try {
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT FE.Version AS medio, FE.Serie, FE.LugarExpedicion  contraRecibo, FE.UsoCFDI estado, \n");
			sbQuery.append("FE.Folio folioEntrega, COALESCE(FE.RRFC, C.Curp COLLATE Modern_Spanish_CI_AS) rfc_Cliente, Emisor.RazonSocial razonSocialFPor, Emisor.RFC rfc_FPor, \n");
			sbQuery.append("CONCAT(Emisor.RegimenFiscal, ' - General de Ley Personas Morales') cpedido, COALESCE(FE.RRazonSocial, C.RSocial COLLATE Modern_Spanish_CI_AS) cobrador, FE.SatUUID uuid, FE.Total medioPago, FE.SatSelloCFD  condicionesPago, \n");
			sbQuery.append("FE.SatNoCertificadoSAT folioPC, FE.NoCertificado esac, FE.SatSelloSAT idioma,  FE.SatSelloSAT tipo, FE.CadenaOriginal observaciones, \n");
			sbQuery.append("FE.Subtotal importe, FE.Total nivelIngresocliente, FE.Moneda, FE.TotalTexto montoConLetra, FE.Fecha entregarEn, FE.SatFechaTimbrado medioEnvio, \n");
			sbQuery.append("CASE WHEN FE.TipoComprobante = 'P' THEN 'P-Pago' \n");
			sbQuery.append("WHEN FE.TipoComprobante = 'E' THEN 'E-Egreso' \n");
			sbQuery.append("ELSE FE.TipoComprobante END puntoEntrega, Emisor.Alias facturarA, VC.Valor tipoPago, FE.CondicionesPago ordenCompra, FE.MetodoPago metodoPago, \n");
			sbQuery.append("COALESCE(VCTipoR.Valor,CFDI.TipoRelacion)tipoRelacion, CFDI.UUID relacionUUID, FE.TipoCambio tipoCambioF, FE.ImpuestosTotalTraslados totalTraslados, \n");
			sbQuery.append("FE.ImpuestosClave tipoImpuesto, FE.ImpuestosTasaOCuota tasa, FE.ImpuestosTipoFactor tipoFactor,C.CP ,C.RegimenFiscal FROM \n");
			sbQuery.append("FACTURAElectronica FE \n");
			sbQuery.append("INNER JOIN EMPRESA Emisor on Emisor.PK_Empresa = FE.EmpresaEmisor \n");
			sbQuery.append("INNER JOIN CLIENTES C ON C.Clave = Fe.Cliente \n");
			sbQuery.append("LEFT JOIN CFDiRelacionado CFDI ON CFDI.FK01_FACturaElectronica = FE.PK_Factura \n");
			sbQuery.append("LEFT JOIN valorCombo VC ON VC.Tipo = FE.FormaPago AND VC.CONCEPTO = 'FormaPago' \n");
			sbQuery.append("LEFT JOIN valorCombo VCTipoR ON VCTipoR.Tipo = CFDI.TipoRelacion AND VCTipoR.Concepto = 'TipoRelacion' \n");
			sbQuery.append("WHERE PK_Factura = :PKFactura \n");
			
			Map <String, Object> param = new HashMap<String, Object>();
			param.put("PKFactura", factura);
			return jdbcTemplate.queryForObject(sbQuery.toString(), param, new BeanPropertyRowMapper<>(Factura.class));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Map<String,Object> obtenerListasComplemento(String factura) throws ProquifaNetException{
		try {
			Map<String, Object> listas = new HashMap<String, Object>();
//			Productos
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT ClaveProdServ clave, Cantidad, ClaveUnidad , Descripcion, CAST(ValorUnitario AS FLOAt) ValorUnitario, CAST(Importe AS FLOAT) importe, \n");
			sbQuery.append("impBase, Unidad, ImpImpuesto, ImpTipoFactor, ImpTasaOcuota, ImpImporte, NoIdentificacion, comentario \n");
			sbQuery.append("FROM PFacturaElectronica \n");
			sbQuery.append("WHERE FK01_FActuraElectronica = :idFacturaElectronica \n");	
//			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idFacturaElectronica", factura);
			List<ComplementoPago> lstProductos = jdbcTemplate.query(sbQuery.toString(),param, new ComplementoPDFRowMapper());
			listas.put("lstProd", lstProductos);
			return listas;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<ComplementoPago> pagosComplemento(String idFactura) throws ProquifaNetException{
		try {
			Funcion funcion = new Funcion();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT C.FechaPago fechaP, C.FormaDePagoP formaP, C.MonedaP moneda, CAST(C.Monto AS FLOAT) monto, \n");
			sbQuery.append("D.IdDocumento, D.Serie, D.NumParcialidad, D.ImpPagado, D.MonedaDR, COALESCE(C.TipoCambioP, '0') tipoCambio, C.rfcEmisorCtaOrd, \n");
			sbQuery.append("C.NomBancoOrdExt, D.ImpSaldoAnt, D.ImpSaldoInsoluto, D.Folio, D.MetodoDePagoDR,  \n");
			sbQuery.append("C.RfcEmisorCtaOrd, C.CtaOrdenante, C.NomBancoOrdExt, Emisor.RFC, COALESCE(D.TipoCambioDR, '0') tipoCambioDR, C.NumOperacion,F.Subtotal, case when F.ImpuestosTasaOCuota='0.000000' Then 0 else 1 end as Aplica_impuestos  \n");
			sbQuery.append("FROM ComplementoPago  C \n");
			sbQuery.append("INNER JOIN CP_DocRelacionados D ON D.FK03_Deposito = C.PK_ComplementoPago \n");
			sbQuery.append("INNER JOIN FacturaElectronica FE ON FE.PK_Factura = C.FK01_FacturaElectronica \n");
			sbQuery.append("INNER JOIN FacturaElectronica F ON F.Folio = D.Folio \n");
			sbQuery.append("INNER JOIN EMPRESA Emisor ON Emisor.PK_Empresa = FE.EmpresaEmisor \n");
			sbQuery.append("WHERE C.FK01_FacturaElectronica = :idFacElectronica \n");

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("idFacElectronica", idFactura);
			Map<String, ComplementoPago> mapReturn = new HashMap<String, ComplementoPago>();
			List<ComplementoPago> list = new ArrayList<ComplementoPago>();
			System.out.println(sbQuery+" "+idFactura);

			jdbcTemplate.query(sbQuery.toString(), param, new RowMapper<Object>() {
				int cont=0;
				double total=0.0;
				@Override

				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {

					try {

						Double importePagado = (double) rs.getFloat("ImpPagado");
						String importeP = funcion.formatoMoneda(importePagado);
						Double totMonto = (double) rs.getFloat("monto");
						String monto = funcion.formatoMoneda(totMonto);
						String formaP = rs.getString("formaP");
						ComplementoPago datos = new ComplementoPago();
						ComplementoPago dato = new ComplementoPago();
						String saldoAnt = funcion.formatoMoneda(Double.parseDouble(rs.getString("ImpSaldoAnt")));
						datos.setVersion("2.0");
						datos.setFechaP(rs.getString("fechaP"));
						datos.setFormaP(rs.getString("formaP"));
						datos.setMoneda(rs.getString("moneda"));
						datos.setNumOperacion(rs.getString("NumOperacion"));
						datos.setMontoDoc(monto);
						datos.setIdDocumento(rs.getString("IdDocumento"));
						datos.setMonedaDR(rs.getString("MonedaDR"));
						datos.setSerie(rs.getString("Serie"));
						datos.setImporteA(saldoAnt);
						datos.setNumParcialidad(rs.getString("NumParcialidad"));
						datos.setImporteIns(rs.getString("ImpSaldoInsoluto"));
						datos.setFolio(rs.getString("Folio"));
						datos.setMetodoP(rs.getString("MetodoDePagoDR"));
						datos.setImporte(importeP);
						datos.setRfcCuenta(rs.getString("RfcEmisorCtaOrd"));
						datos.setNomBanco(rs.getString("NomBancoOrdExt"));
						datos.setNumCuenta(rs.getString("CtaOrdenante"));
						datos.setRfcEmisor(rs.getString("RFC"));
						datos.setTipoCambio(rs.getString("tipoCambio"));


						if(rs.getString("moneda").equalsIgnoreCase(rs.getString("MonedaDR"))) {
							datos.setTipoCambioDR("0");
							dato.setTipoCambioDR("0");
						} else {
							datos.setTipoCambioDR(rs.getString("tipoCambioDR"));
							dato.setTipoCambioDR(rs.getString("tipoCambioDR"));
						}
//						
						String saldoInsoluto = funcion.formatoMoneda(Double.parseDouble(rs.getString("ImpSaldoInsoluto")));
						dato.setFechaP(rs.getString("fechaP"));
						dato.setFormaP(rs.getString("formaP"));
						dato.setMoneda(rs.getString("moneda"));
						dato.setNumOperacion(rs.getString("NumOperacion"));
						dato.setMontoDoc(monto);
						dato.setVersion("2.0");
						dato.setIdDocumento(rs.getString("IdDocumento"));
						dato.setMonedaDR(rs.getString("MonedaDR"));
						dato.setSerie(rs.getString("Serie"));
						dato.setImporteA(saldoAnt);
						dato.setNumParcialidad(rs.getString("NumParcialidad"));
						dato.setImporteIns(saldoInsoluto);
						dato.setFolio(rs.getString("Folio"));
						dato.setMetodoP(rs.getString("MetodoDePagoDR"));
						dato.setImporte(importeP);
						dato.setRfcCuenta(rs.getString("RfcEmisorCtaOrd"));
						dato.setNomBanco(rs.getString("NomBancoOrdExt"));
						dato.setNumCuenta(rs.getString("CtaOrdenante"));
						////////nuevos nodos del complemento
						if(rs.getBoolean("Aplica_impuestos")) {

							////////////////////////totales///////////////////////
							double conversion=0.0;
							if (rs.getString("moneda").equalsIgnoreCase("USD")){
								conversion=(totMonto*rs.getDouble("tipoCambio"));
							}else{
								conversion=totMonto;
							}
							String baseIVA16 = funcion.formatoMoneda(conversion / 1.16);
							datos.setBaseIVA16(baseIVA16);
							String impIVA16 = funcion.formatoMoneda((double)rs.getFloat("monto") * 0.16);
							datos.setImpIVA16(impIVA16);

							dato.setBaseIVA16(baseIVA16);
							dato.setImpIVA16(impIVA16);
							////////////////////////////////Traslados DR //////////////////////
							datos.setImpDR("002");
							datos.setTasaCuotaDR("0.160000");
							datos.setTipoFactorDR("Tasa");
							String baseDR= funcion.formatoMoneda(Double.parseDouble(rs.getString("Subtotal")));
							datos.setBaseDR(baseDR);
							String importeDR=funcion.formatoMoneda(Double.parseDouble(rs.getString("Subtotal"))*0.160000);
							datos.setImporteDR(importeDR);

							dato.setImpDR("002");
							dato.setTasaCuotaDR("0.160000");
							dato.setTipoFactorDR("Tasa");
							dato.setBaseDR(baseDR);
							dato.setImporteDR(importeDR);
							/////////////////////////////traslado P/////////////////////////////

							Double resultadoParcial=Double.parseDouble(rs.getString("Subtotal")) / Double.parseDouble(rs.getString("tipoCambioDR"));
							total += resultadoParcial;


							datos.setBaseP(funcion.formatoMoneda(total));
							datos.setImpuestoP("002");
							datos.setTipoFacP("Tasa");
							datos.setTasaCuotaP("0.160000");
							datos.setImporteP(funcion.formatoMoneda(total*0.160000));

							dato.setBaseP(funcion.formatoMoneda(total));
							dato.setImpuestoP("002");
							dato.setTipoFacP("Tasa");
							dato.setTasaCuotaP("0.160000");
							dato.setImporteP(funcion.formatoMoneda(total*0.160000));
						}
						if(mapReturn.get(formaP) != null) {
							mapReturn.get(formaP).getListaPagos().add(datos);
							if(rs.getBoolean("Aplica_impuestos")) {
								mapReturn.get(formaP).setBaseP(funcion.formatoMoneda(total));
								mapReturn.get(formaP).setImporteP(funcion.formatoMoneda(total * 0.160000));
							}
						} else {
							List<ComplementoPago> lista = new ArrayList<ComplementoPago>();
							mapReturn.put(formaP, datos);
							datos.setListaPagos(lista);
							lista.add(dato);
							list.add(datos);

							
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					return null;
				}
			});

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Factura obtenerFacturaElectronica(Integer idFacturaElectronica) throws ProquifaNetException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT FE.Folio, FE.CondicionesPago, FE.Cliente, FE.Moneda, FE.EmpresaEmisor, \n");
			sbQuery.append("FE.SubTotal Total, FE.ImpuestosTasaOCuota, 'Por Cobrar' Estado, EM.Alias, FE.TipoCambio, \n");
			sbQuery.append("'Normal' Tipo, FE.Serie, 'Electronica' Medio, FE.SAtUUID \n");
			sbQuery.append("FROM FacturaElectronica FE  \n");
			sbQuery.append("INNER JOIN Empresa EM ON EM.PK_Empresa = FE.EmpresaEmisor \n");
			sbQuery.append("WHERE PK_Factura = ").append(idFacturaElectronica);
			
			return (Factura) jdbcTemplate.queryForObject(sbQuery.toString(), param, new FacturaElectronicaRowMapper());
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}
	
	@Override
	public Integer insertarFactura(Factura factura) throws ProquifaNetException {
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("folio", factura.getNumeroFactura());
			param.put("condiciones", factura.getCondicionesPago());
			param.put("idCliente", factura.getIdCliente());
			param.put("moneda", factura.getMoneda());
			param.put("importe", factura.getImporte());
			param.put("iva", factura.getIva());
			param.put("estado", factura.getEstado());
			param.put("fpor", factura.getFacturadoPor());
			param.put("tc", factura.getTipoCambio());
			param.put("tipo", factura.getTipo());
			param.put("serie", factura.getSerie());
			param.put("medio", factura.getMedio());
			param.put("uuid", factura.getUuid());
			
			Object[] params =  {factura.getNumeroFactura(),factura.getCondicionesPago(),factura.getIdCliente(),factura.getMoneda(),factura.getImporte(),factura.getIva(),
					factura.getEstado(),factura.getFacturadoPor(),factura.getTipoCambio(),factura.getTipo(),factura.getSerie(),factura.getMedio(),0, factura.getUuid()};
			StringBuilder sbQuery = new StringBuilder("INSERT INTO Facturas (Factura,Fecha,CPago,Cliente,Moneda,Importe,IVA,Estado,FPor,TCambio,Tipo,Serie,Medio,DeSistema, UUID, PDFGenerado) VALUES \n");
			sbQuery.append("(:folio,GETDATE(),:condiciones,:idCliente,:moneda,:importe,:iva,:estado,:fpor,:tc,:tipo,:serie,:medio,0,:uuid,0) \n");
			
			super.jdbcTemplate.update(sbQuery.toString(), param);
			Long idFactura = super.jdbcTemplate.queryForObject("SELECT IDENT_CURRENT ('Facturas')", param, Long.class);
			
			for (PartidaFactura pfactura : factura.getPartidas()) {
				Map<String, Object> paramPartida = new HashMap<String, Object>();
				paramPartida.put("partida", pfactura.getPartidaFactura());
				paramPartida.put("factura", pfactura.getFactura());
				paramPartida.put("fpor", pfactura.getFpor());
				paramPartida.put("cpedido", pfactura.getCpedido());
				paramPartida.put("ppedido", pfactura.getPpedido());
				paramPartida.put("importe", pfactura.getImporte());
				paramPartida.put("cantidad", pfactura.getCantidad());
				paramPartida.put("fabrica", pfactura.getFabrica());
				paramPartida.put("concepto", pfactura.getCodigo());
				paramPartida.put("conceptoPartida", pfactura.getConceptoPartida());
				paramPartida.put("cotiza", pfactura.getCotiza());
				paramPartida.put("nota", pfactura.getNota());
				paramPartida.put("idFactura", idFactura);
				
				
				sbQuery = new StringBuilder("INSERT INTO PFacturas (Part,Factura,FPor,CPedido,PPedido,Importe,Cant,Fabrica,Codigo,Concepto,Cotiza,Nota, FK01_Factura) VALUES ");
				Object[] paramsPartida =  {pfactura.getPartidaFactura(),pfactura.getFactura(),pfactura.getFpor(),pfactura.getCpedido(),
						pfactura.getPpedido(),pfactura.getImporte(),pfactura.getCantidad(),pfactura.getFabrica(),pfactura.getCodigo(),
						pfactura.getConceptoPartida(),pfactura.getCotiza(),pfactura.getNota(), idFactura};
				sbQuery.append("(:partida,:factura,:fpor,:cpedido,:ppedido,:importe,:cantidad,:fabrica,:concepto,:conceptoPartida,:cotiza,:nota,:idFactura)");
				
				super.jdbcTemplate.update(sbQuery.toString(), paramPartida);
			}
			
			sbQuery = new StringBuilder("INSERT INTO Factura_FElectronica (FK01_Factura, FK02_FacturaElectronica) VALUES (");
			sbQuery.append(idFactura).append(",").append(factura.getIdFacturaElectronica()).append(")");
			
			Map<String, Object> dummy = new HashMap<String, Object>();
			super.jdbcTemplate.update(sbQuery.toString(), dummy);
			
			return idFactura.intValue();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
	}

}
