/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.ibm.icu.util.Calendar;
import com.proquifa.net.modelo.comun.DocumentoRecibido;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.Reporte;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.comun.ReportesDAO;

/**
 * @author ymendez
 *
 */
@Repository
public class ReportesDAOImpl extends DataBaseDAO implements ReportesDAO {
	
	final Logger log = LoggerFactory.getLogger(ReportesDAOImpl.class);

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.comun.ReportesDAO#getScoringInspeccionar()
	 */
	@Override
	public List<Reporte> getScoringInspeccionar() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT PC.cpedido, PC.compra, PC.codigo, PC.Descripcion, PC.fabrica, PC.manejo, PC.Nombre AS Cliente, CONVERT(VARCHAR(MAX),pc.FPEntrega,105) Fecha, \n"); 
			sbQuery.append("pc.ruta, PC.zonaMensajeria Zona, PC.diasRestantes Dias, PC.PuntosPAviso, pc.PuntosFEE, pc.PuntosRuta, pc.PuntosRParciales puntosParciales, 0 puntosManejo,  \n");
			sbQuery.append("pc.puntos totalPuntos, CASE WHEN PC.Urgencia > 1 THEN 'Urgencia' ELSE pc.prioridad END prioridad, pc.puntosEmbalar AS PuntosEmbalar \n");
			sbQuery.append("FROM partidadeCompraenInspeccion as PC  WHERE estado='En inspección'  \n");
			sbQuery.append("ORDER BY prioridad, puntos DESC \n");
			sbQuery.append(" \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Reporte.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		

	/* (non-Javadoc)
	 * @see com.proquifa.net.persistencia.comun.ReportesDAO#getScoringDespachos()
	 */
	@Override
	public List<Reporte> getScoringDespachos() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT PC.cpedido, PC.compra, PC.codigo, PC.concepto Descripcion, PC.fabrica, PC.manejo, PC.cliente, CONVERT(VARCHAR(MAX),pc.FPEntrega,105) Fecha, \n"); 
			sbQuery.append("pc.ruta, PC.zonaMensajeria Zona, PC.diasRestantes Dias, pc.PuntosPAviso, pc.PuntosFEE, pc.PuntosRuta, 0 PuntosParciales, pc.puntos totalPuntos, CASE WHEN PC.Urgencia > 1 THEN 'Urgencia' ELSE pc.prioridad END prioridad \n");
			sbQuery.append("FROM partidaCompraPorEmbalar as PC  \n");
			sbQuery.append("ORDER BY prioridad, puntos DESC\n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Reporte.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reporte> getSeguimientos() throws ProquifaNetException {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");  
		    Date fechaActual = new Date();  
		    
			StringBuilder sbQuery = new StringBuilder("\n");
			sbQuery.append("SELECT Cliente, Cotizacion AS cpedido, Monto AS Costo, Producto AS Descripcion, Fabrica, EVT AS Origen, FechaSiguiente AS FechaArribo FROM ufn_obtenerSeguimientos('"+ formatter.format(fechaActual) +"') \n");
			log.info(sbQuery.toString());
			Map<String, Object> map = new HashMap<String, Object>();
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Reporte.class));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<Reporte> getOrdenDespacho(String ordenDespacho) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT PC.idPCompra, Cl.Nombre Cliente, COALESCE(Prod.Codigo, ProdCod.Codigo) AS Codigo, COALESCE(Prod.Concepto, ProdCod.Concepto) AS Descripcion, COALESCE(Prod.Fabrica, ProdCod.Fabrica) AS Fabrica, PC.Cpedido, convert(varchar(max), PP.FPEntrega, 105) Fecha, \n");
			sbQuery.append("CASE WHEN P.Moneda='Pesos' THEN  \n");
			sbQuery.append("CASE WHEN P.TCAMBIO IS NOT NULL AND P.TCAMBIO>0 THEN \n"); 
			sbQuery.append("PP.Precio/P.TCambio \n");
			sbQuery.append("ELSE  \n");
			sbQuery.append("PP.Precio/Mon.PDolar \n");
			sbQuery.append("END   \n");
			sbQuery.append("ELSE  \n");
			sbQuery.append("PP.Precio \n");
			sbQuery.append("END AS precioUnitario, PP.Cant piezas, \n");
			sbQuery.append("CASE WHEN P.Moneda='Pesos' THEN \n"); 
			sbQuery.append("CASE WHEN P.TCAMBIO IS NOT NULL AND P.TCAMBIO>0 THEN \n"); 
			sbQuery.append("(PP.Precio/P.TCambio)*PP.Cant \n");
			sbQuery.append("ELSE  \n");
			sbQuery.append("(PP.Precio/Mon.PDolar)*PP.Cant \n");
			sbQuery.append("END   \n");
			sbQuery.append("ELSE  \n");
			sbQuery.append("PP.Precio*PP.Cant \n");
			sbQuery.append("END AS Importe, \n");
			sbQuery.append("CASE WHEN C.Moneda='Euros' THEN PC.Costo * MonCompra.EDolar \n");
			sbQuery.append("WHEN C.Moneda='Libras' THEN PC.Costo * MonCompra.LDolar \n");
			sbQuery.append("ELSE PC.Costo \n");
			sbQuery.append("END Costo, convert(varchar(10),Pnd.FTramitacion, 103) +' ' + convert(VARCHAR(8), Pnd.FTramitacion, 14) fechaTramitacion, \n"); 
			sbQuery.append("convert(varchar(10),C.Fecha, 103) +' ' + convert(VARCHAR(8), C.Fecha, 14) FechaCompra,  \n");
			sbQuery.append("convert(varchar(10),LA.Fecha, 103) +' ' + convert(VARCHAR(8), LA.Fecha, 14) FechaDeclararArribo, \n");
			sbQuery.append("convert(varchar(10),Ins.Fecha, 103) +' ' + convert(VARCHAR(8), Ins.Fecha, 14) FechaArribo \n");
			sbQuery.append("from OrdenDespacho AS OD \n");
			sbQuery.append("LEFT JOIN ListaArribo AS LA ON LA.FK02_OrdenDespacho=OD.pk_OrdenDespacho \n");
			sbQuery.append("LEFT JOIN PListaArribo AS PLA ON PLA.FK01_ListaArribo=LA.PK_ListaArribo \n");
			sbQuery.append("LEFT JOIN PCompras AS PC ON PC.idpcompra=PLA.FK02_PCompra \n");
			sbQuery.append("LEFT JOIN Compras AS C ON C.clave = PC.compra \n");
			sbQuery.append("LEFT JOIN PPedidos AS PP ON PP.idppedido=PC.FK03_PPedido \n");
			sbQuery.append("LEFT JOIN Pedidos AS P ON P.cpedido=PP.Cpedido \n");
			sbQuery.append("left join (Select max(FFin) as FTramitacion, Docto FROM Pendiente WHERE Tipo='Pedido por tramitar' GROUP BY Docto) AS Pnd on Pnd.Docto=P.DoctoR \n");
			sbQuery.append("LEFT JOIN Clientes AS Cl ON Cl.clave = P.idCliente \n");
			sbQuery.append("LEFT JOIN Monedas AS Mon ON Mon.Fecha = P.Fpedido \n");
			sbQuery.append("LEFT JOIN Monedas AS MonCompra ON CAST(MonCompra.Fecha AS DATE) = CAST(C.Fecha AS DATE) \n");
			sbQuery.append("LEFT JOIN Productos AS Prod ON Prod.idProducto=PC.FK01_Producto \n");
			sbQuery.append("LEFT JOIN Productos AS ProdCod ON ProdCod.Codigo=PP.Codigo AND ProdCod.Fabrica=Pp.Fabrica \n");
			sbQuery.append("left join InspeccionOC as Ins ON Ins.idPCompra = PC.idPCompra \n");
			sbQuery.append("WHERE OD.Folio = :ordenDespacho \n");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ordenDespacho", ordenDespacho);
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Reporte.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<Reporte> getConsultaCompra(String compra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			//Se creo funcion en SQL			
			sbQuery.append("SELECT * FROM ufn_OCCompraVenta (:compra)");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compra", compra);
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(Reporte.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String obtenerDate(Parametro parametro) {
		try {
			Calendar calendar = Calendar.getInstance();
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT TOP 1 YEAR(").append(parametro.getFecha()).append(") FROM ").append(parametro.getTabla());
			sbQuery.append(" WHERE ").append(parametro.getCampo()).append(" = '").append(parametro.getFolio()).append("' \n");
			if (parametro.getZona() != null) {
				sbQuery.append(" AND ").append(parametro.getZona()).append(" = '").append(parametro.getFacturo()).append("' \n");
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			String year = jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class) + "/";
			year = year.replace(String.valueOf(calendar.get(Calendar.YEAR)) + "/", "");
			return year;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	@Override
	public List<DocumentoRecibido> getMailInfoDoctor(long folioDoctor) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT DR.Fecha, DR.Fecha AS ingreso, COALESCE(DR.Asunto, '') AS Asunto, COALESCE( DR.Contenido, '') AS Contenido, COALESCE(DR.HTML, '') AS HTML, COALESCE(DR.sender, '') AS sender, \n"); 
			sbQuery.append("COALESCE(Adj.totalAdj, 0) AS totalAdj, Cl.Nombre AS nombreEmpresa, CASE WHEN DR.Asunto IS NULL THEN CAST(0 AS BIT) ELSE CAST(1 AS BIT) END AS esOrigenMailBot,\n");
			sbQuery.append("COALESCE(STUFF((SELECT '|' + CD.NombreArchivo \n");
			sbQuery.append("FROM core_documentoadj CD \n");
			sbQuery.append("WHERE CD.doctoCorreo_id = DR.Folio FOR XML PATH ('')), 1, 1, '' ), '') AS NombreArchivos\n");
			sbQuery.append("FROM Doctosr AS DR\n");
			sbQuery.append("LEFT JOIN(SELECT COUNT(1) AS totalAdj, doctoCorreo_id FROM core_documentoadj GROUP BY doctoCorreo_id) AS Adj ON adj.doctoCorreo_id = DR.Folio\n");
			sbQuery.append("INNER JOIN Clientes AS Cl ON Cl.Clave = DR.Empresa\n");					
			sbQuery.append("WHERE DR.Folio = :folioDoctor \n");
			
			log.info(sbQuery.toString());
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folioDoctor", folioDoctor);
			return jdbcTemplate.query(sbQuery.toString(), map, new BeanPropertyRowMapper<>(DocumentoRecibido.class));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
