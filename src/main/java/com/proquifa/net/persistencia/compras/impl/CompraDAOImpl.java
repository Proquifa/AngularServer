/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.compras.Compra;
import com.proquifa.net.modelo.compras.PermisoImportacion;
import com.proquifa.net.modelo.compras.ResumenCompra;
import com.proquifa.net.modelo.compras.ResumenPCompra;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.CompraDAO;
import com.proquifa.net.persistencia.compras.impl.mapper.CompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.ConteoPartidasCompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.MontosRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.ObtenerPermisoImportacionRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.ObtenerPrevioPermisoImportacionRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.ReporteCompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.ResumenCompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.ResumenPCompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.compraTiempoProcesoRowMapper;
import com.proquifa.net.persistencia.consultas.impl.mapper.ConsultaComprasRowMapper;


/**
 * @author amartinez
 *
 */
@Repository
public class CompraDAOImpl extends DataBaseDAO implements CompraDAO{
	Funcion funcion;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CompraDAOImpl.class);

	public Compra obtenerCompraXFolio(String folioCompra){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folioCompra", folioCompra);
			
			String query = "SELECT * FROM compras WHERE clave = '" + folioCompra + "'";	
			return (Compra) super.jdbcTemplate.queryForObject(query,map, new CompraRowMapper());	
		}catch (RuntimeException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Compra> obtenerComprasConfirmadasXIdProveedor(Long idProveedor,
			String periodo) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProveedor", idProveedor);
			map.put("periodo", periodo);
			
			String query = "select clave, empresa, ROUND(sum(Cant * Costo), 2) as montoTotal, Moneda, fecha from Compras, Pendiente, pcompras where fecha >='" + periodo + "' " +
							"and pendiente.Docto = compras.Clave and Pendiente.Tipo = 'OC por confirmar' and ffin is Not null and compras.Provee = " + idProveedor  + " " +
							"and pcompras.Estado <> 'Cancelada' and PCompras.Estado <> 'A destrucción' and pcompras.Compra = compras.Clave group by Clave, Empresa, Moneda, fecha";
			return super.jdbcTemplate.query(query,map, new ReporteCompraRowMapper());
		} catch (RuntimeException e) {
			return null;
		}
	}

	public Compra obtenerMontoDolaresCompra(String ordenCompra,
			String monedaOrigen) {
		String operacion = "";
		String moneda = "";
		String tipoCambio = "";
		if(monedaOrigen.equalsIgnoreCase("euros")){
			operacion = " ROUND(sum(cant * costo) * edolar, 2) ";
			moneda = " GROUP BY EDOLAR";
			tipoCambio = "ROUND(edolar, 2)";
		}else if(monedaOrigen.equalsIgnoreCase("yenes")){
			operacion = " ROUND(sum(cant * costo) * ydolar, 2) ";
			moneda = " GROUP BY YDOLAR";
			tipoCambio = "ROUND (ydolar, 2)";
		}else if(monedaOrigen.equalsIgnoreCase("libras")){
			operacion = " ROUND(sum(cant * costo) * ldolar, 2) ";
			moneda = " GROUP BY LDOLAR";
			tipoCambio = "ROUND(ldolar, 2)";
		}else if(monedaOrigen.equalsIgnoreCase("pesos")){
			operacion = " ROUND(sum(cant * costo) / pdolar, 2) ";
			moneda = " GROUP BY PDOLAR";
			tipoCambio = "ROUND(pdolar, 2) ";
		}else if(monedaOrigen.equalsIgnoreCase("francosuizo")){
			operacion = " ROUND(sum(cant * costo) * FSDolar, 2) ";
			moneda = " GROUP BY FSDolar";
			tipoCambio = "ROUND(FSDolar, 2) ";
		}else{
			operacion = " ROUND(sum(cant * costo), 2) ";
			moneda = "";
			tipoCambio = "0.0";
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordenCompra", ordenCompra); 
		map.put("monedaOrigen", monedaOrigen); 
		String query = "select " + operacion + " as totalDolares, " + tipoCambio  + " as tipoCambio from PCompras, Monedas where Compra = '" + ordenCompra + "' and monedas.Fecha in (select top 1 fecha from Monedas order by Fecha desc) " +
						"and pcompras.Estado <> 'Cancelada' and pcompras.Estado <> 'A destrucción' " + moneda;
		Compra compra = (Compra) super.jdbcTemplate.queryForObject(query,map, new MontosRowMapper());
		return compra;
	}

	@SuppressWarnings("unchecked")
	public List<Compra> obtenerReporteCompra(String periodo, String proveedor,
			String estado, String coloco, String ordenCompra) {
		
		String vWHERE = new String();
		
		if(!periodo.equals("") || !proveedor.equals("") || !estado.equals("") || !coloco.equals("") || !ordenCompra.equals("")){
			vWHERE = " WHERE ";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ordenCompra", ordenCompra); 
		map.put("periodo", periodo); 
		map.put("proveedor", proveedor); 
		map.put("estado", estado); 
		map.put("coloco", coloco); 
	
		String query = 	" SELECT Compras.Clave, Proveedores.Nombre, Proveedores.Clave, Compras.ColocarDesde, Compras.AlmacenUE, " +
						" Compras.AlmacenUSA, Compras.AlmacenMatriz, Compras.Fecha, tblPcompras.cuantos, Compras.FColocacion, tblTP.TotalPiezas, " +
						" Compras.Empresa, OC.FFin AS FConfirmacion FROM Compras INNER JOIN Proveedores ON Compras.Provee=Proveedores.Clave " +
						" LEFT JOIN (SELECT SUM(PCompras.Cant)as TotalPiezas, PCompras.Compra FROM PCompras group by PCompras.Compra) AS tblTP on Compras.Clave=tblTP.Compra " +
						" LEFT JOIN (SELECT COUNT(1) AS cuantos, PCompras.Compra FROM PCompras WHERE PCompras.Estado <> 'Recibido' AND PCompras.Estado <> 'Recibido PHS' " +
						" AND PCompras.Estado <> 'Cancelada' AND PCompras.Estado <> 'Recibido PHS UE' group by PCompras.Compra  ) as tblPcompras ON  Compras.Clave=tblPcompras.Compra " +
						" LEFT JOIN (SELECT Pendiente.FFin, Pendiente.Docto FROM Pendiente WHERE Pendiente.Tipo='OC por confirmar' ) AS OC ON Compras.Clave=OC.Docto " +
						vWHERE + periodo + " " + proveedor + " " + estado + " " + coloco + " " + ordenCompra;
		
		return super.jdbcTemplate.query(query,map, new ConsultaComprasRowMapper());
	}
	
	public Compra obtenerFechaCompra(String cPedido){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido", cPedido); 
		
			String query = "SELECT TOP 1 compras.Fecha, PCompras.idPCompra FROM " + 
							"Compras INNER JOIN PCompras ON PCompras.Compra = compras.Clave AND PCompras.CPedido='" + cPedido + "' ORDER BY compras.Fecha DESC";
			return (Compra) super.jdbcTemplate.queryForObject(query,map, new compraTiempoProcesoRowMapper());
		} catch (RuntimeException e) {
		//	logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	public Date obtenerFechaInspeccionCompra(String cPedido){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido", cPedido); 
			String query = "SELECT MAX(Fecha) AS Fecha FROM inspeccionoc WHERE idpcompra IN (SELECT idpcompra FROM pcompras WHERE cpedido = '"+cPedido+"') AND Fecha is NOT NULL";
			return (Date) super.jdbcTemplate.queryForObject(query,map,Date.class);
		} catch (RuntimeException e) {
			//logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	public Boolean cerrarPendienteDeMonitoreoOC(String folioOC) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folioOC", folioOC); 
			Date fechaFFin = new Date();
			Object[] params =  {fechaFFin,folioOC};
			String query = "SELECT cmp.Clave AS Compra,pcTotal.Numero AS TotalPartidas, pcTotalMexico.Numero AS TotalPartidaMexico,COALESCE(pcCanceladas.Numero,0) AS Canceladas,COALESCE(pcRecibidas.Numero,0) AS Recibidas,"+
						   "COALESCE(pcRecibidasPHS.Numero,0) AS RecibidasPHS "+
						   "FROM Compras AS cmp "+
						   "LEFT JOIN(SELECT COUNT(*) AS Numero,Compra FROM PCompras GROUP BY Compra) AS pcTotal ON pcTotal.Compra=cmp.Clave "+
						   "LEFT JOIN(SELECT COUNT(*) AS Numero,Compra FROM PCompras,Pedidos WHERE Pedidos.CPedido=PCompras.CPedido AND Pedidos.PaisFiscalP = 'MEXICO' GROUP BY Compra) AS pcTotalMexico "+
						   "ON pcTotalMexico.Compra=cmp.Clave "+
						   "LEFT JOIN(SELECT COUNT(*) AS Numero,Compra FROM PCompras WHERE Estado = 'Cancelada' GROUP BY Compra) AS pcCanceladas ON pcCanceladas.Compra=cmp.Clave "+
						   "LEFT JOIN(SELECT COUNT(*) AS Numero,Compra FROM PCompras WHERE FolioInspeccion IS NOT NULL and Estado <> 'Cancelada' GROUP BY Compra) AS pcRecibidas ON pcRecibidas.Compra=CMP.Clave " +
						   "LEFT JOIN(SELECT COUNT(*) AS Numero,pc.Compra FROM PCompras AS pc,PPedidos AS pp,Pedidos AS pd WHERE pc.FolioInspeccionPhs IS NOT NULL AND pc.Estado <> 'Cancelada' AND pc.CPedido=pp.CPedido "+
						   "AND pc.PPedido=pp.Part AND pd.CPedido=pp.CPedido GROUP BY pc.Compra) AS pcRecibidasPHS ON pcRecibidasPHS.Compra=cmp.Clave "+
						   "WHERE cmp.Clave='"+ folioOC +"'";

			Compra partidas = (Compra) super.jdbcTemplate.queryForObject(query, map,new ConteoPartidasCompraRowMapper());
			query="";
			if((partidas.getTotalPiezas()==(partidas.getTotalCanceladas() + partidas.getTotalRecibidasPHS())) && 
					(partidas.getTotalPiezasMexico()==(partidas.getTotalCanceladas() + partidas.getTotalRecibidas())) ){
				query = "UPDATE Pendiente SET FFin=? WHERE Tipo='Monitorear OC' AND FFin IS NULL AND Docto=?";
			}else if(partidas.getTotalPiezas()==(partidas.getTotalCanceladas() + partidas.getTotalRecibidasPHS())){
				query = "UPDATE Pendiente SET FFin=? WHERE Tipo='Monitorear OC' AND Responsable='ComPHS-USA' AND FFin IS NULL AND Docto=?";
			}else if(partidas.getTotalPiezasMexico()==(partidas.getTotalCanceladas() + partidas.getTotalRecibidas())) {
				query = "UPDATE Pendiente SET FFin=? WHERE Tipo='Monitorear OC' AND Responsable<>'ComPHS-USA' AND FFin IS NULL AND Docto=?";
			}
			
			if(!query.equals("")){
				super.jdbcTemplate.update(query,map);
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
		//	logger.error("Error: " + e.getMessage());
			return false;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PermisoImportacion> obtenerReportePermisoImportacion(String condiciones) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("condiciones", condiciones); 
			
		String query = 	" SELECT SOLPER.PK_Solicitud AS SOLICITUD,PROV.Nombre AS Fabrica,PRO.Codigo,SOLPER.FEL, SOLPER.FET, COALESCE(AUTOR1.FInicio,SOM1.FInicio) AS FInicio, " +
						" CASE WHEN ((SOM.FINICIO IS NOT NULL OR AUTOR.FINICIO IS NOT NULL) AND SOLPER.FEL IS NULL ) THEN 'Por Someter' ELSE CASE WHEN  SOLPER.FEL IS NULL  THEN 'A Tramitar' ELSE 'En Autorización'  END END AS ETIQUETA, " +
						" CONVERT(varchar,PRO.Codigo) +' '+ CONVERT(varchar(MAX),PRO.Concepto) +' ('+ CONVERT(varchar,PRO.Cantidad) +' '+ CONVERT(varchar,PRO.Unidad) +') '+ CONVERT(varchar,PRO.Fabrica) AS NOMBRE,  " +
						" SOLPER.Tipo AS TipoPermiso, PRO.Costo AS PU, SOLPER.TipoPermiso AS TipoSolicitud, COALESCE(PEND1.Partida,PEND.Partida,'C-Productos') AS OrigenFolio,  " +
						" CASE WHEN SUBSTRING( COALESCE(PEND1.Partida,PEND.Partida),1 ,1) = 'C' THEN 'Cotizacion' WHEN SUBSTRING( COALESCE(PEND1.Partida,PEND.Partida),1 ,1) = 'P' THEN 'Pedido' ELSE 'C-Productos' END AS Origen, PRO.CAS, PRO.EstadoFisico,  " +
						" LOTPER.Folio AS FolioLote,SOLPER.FSometio,SOLPER.DoctoEntrada,SOLPER.NoEntrada,SOLPER.FolioConjunto,PRO.EstadoPermiso, " +
						" SOLPER.FraccionArancelaria,SOLPER.ClasificacionProducto AS ClasifSolicitud, PRO.Tipo AS TipoProducto, PRO.Subtipo, PRO.Control, PRO.Clasificacion, " +
						" CASE WHEN SUBSTRING( COALESCE(PEND1.Partida,PEND.Partida),1 ,1) = 'C' THEN PCOT.Clave ELSE PPED.CPedido END AS PEDCOT,PRO.Fabrica AS Marca," +
						" CASE WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) > 5 THEN 'FUTURO'   " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) =5 THEN CASE WHEN (DATEPART(DW,GETDATE())=3 OR DATEPART(DW,GETDATE())=4 OR DATEPART(DW,GETDATE())=5) THEN 'POS PASADO' END " + 
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) =4 THEN CASE WHEN (DATEPART(DW,GETDATE())=1) THEN 'FUTURO' ELSE CASE WHEN (DATEPART(DW,GETDATE())=4 OR DATEPART(DW,GETDATE())=5 ) THEN 'PASADO MANANA' END END " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) =3 THEN CASE WHEN (DATEPART(DW,GETDATE())=1 OR DATEPART(DW,GETDATE())=2 ) THEN 'POS PASADO' ELSE CASE WHEN (DATEPART(DW,GETDATE())=5 ) THEN 'MANANA' END END " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) =2 THEN CASE WHEN (DATEPART(DW,GETDATE())=1 OR DATEPART(DW,GETDATE())=2 OR DATEPART(DW,GETDATE())=3 ) THEN 'PASADO MANANA'  END " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) =1 THEN CASE WHEN (DATEPART(DW,GETDATE())=1 OR DATEPART(DW,GETDATE())=2 OR DATEPART(DW,GETDATE())=3 OR DATEPART(DW,GETDATE())=4 )  THEN 'MANANA'  END " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) = 0 THEN 'HOY'   " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) = -1 THEN 'AYER'   " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) = -2 THEN CASE WHEN (DATEPART(DW,GETDATE())  =3 OR DATEPART(DW,GETDATE())=4 OR DATEPART(DW,GETDATE())=5 ) THEN 'ANTIER' END " +  
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) = -3 THEN CASE WHEN DATEPART(DW,GETDATE())  =1  THEN 'AYER' ELSE  CASE WHEN (DATEPART(DW,GETDATE())=4 OR DATEPART(DW,GETDATE())=5 ) THEN 'ANTIER' END  END " +  
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) = -4 THEN CASE WHEN (DATEPART(DW,GETDATE()) =1 OR DATEPART(DW,GETDATE())=2) THEN 'ANTIER' ELSE CASE WHEN DATEPART(DW,GETDATE())  =5  THEN  'PASADO' END  END " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) = -5 THEN CASE WHEN (DATEPART(DW,GETDATE()) =1 OR DATEPART(DW,GETDATE())=2 OR DATEPART(DW,GETDATE())=3 ) THEN 'ANTE ANTIER'  END   " +
						" WHEN DATEDIFF(dd,GETDATE(),COALESCE(SOLPER.FEL, SOLPER.FET)) < -5 THEN 'PASADO'     " +
						" END AS DiasFiltro,NULL Justificacion, " +
						" COALESCE( CASE WHEN SUBSTRING( COALESCE(PEND1.Partida,PEND.Partida),1 ,1) = 'C' THEN PCOT.Cant ELSE PPED.Cant END,1) AS CANT  " +
						" FROM Productos AS PRO " +
						" LEFT JOIN (SELECT * FROM SolicitudPermisoImp) AS SOLPER ON SOLPER.FK01_Producto = PRO.idProducto " +
						" LEFT JOIN (SELECT * FROM LotePermisos) AS LOTPER ON LOTPER.PK_Lote = SOLPER.FK02_LotePermiso " +
						" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A Tramitacion') AS PEND ON PEND.Docto = PRO.idProducto " +
						" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='A Tramitar PermisoImp') AS PEND1 ON PEND1.Docto = PRO.idProducto " +
						" LEFT JOIN (SELECT * FROM Proveedores) AS PROV ON PROV.Clave = PRO.Proveedor " +
						" LEFT JOIN (SELECT * FROM PPedidos) AS PPED ON PPED.idPPedido =  SUBSTRING (COALESCE(PEND1.Partida,PEND.Partida),3,15) " +
						" LEFT JOIN (SELECT * FROM PCotizas) AS PCOT ON  PCOT.idPCotiza = SUBSTRING (COALESCE(PEND1.Partida,PEND.Partida),3,15) " +
						" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Por Someter') AS SOM ON SOM.Docto = LOTPER.PK_Lote  " +
						" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='En autorizacion') AS AUTOR ON AUTOR.Docto = LOTPER.PK_Lote  " +
						" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='Por Someter' AND FFin IS NULL) AS SOM1 ON SOM1.Docto = LOTPER.PK_Lote " +
						" LEFT JOIN (SELECT * FROM Pendiente WHERE Tipo='En autorizacion' AND FFin IS NULL ) AS AUTOR1 ON AUTOR1.Docto = LOTPER.PK_Lote " +
						" WHERE (SOLPER.FEL IS NOT NULL OR SOLPER.FET IS NOT NULL) AND PRO.EstadoPermiso <> 'Autorizado' "+condiciones ; 
		//log.info(query);
		return super.jdbcTemplate.query(query, map,new ObtenerPermisoImportacionRowMapper());
		} catch (RuntimeException e) {
			//logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PermisoImportacion> obtenerHistorialPerImportacion(String idSolicitud) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idSolicitud", idSolicitud); 
			String query = "  SELECT NULL SOLICITUD,NULL FABRICA , NULL CODIGO, HIS.Fecha AS FET, HIS.FELAnterior AS FEL, NULL Marca,CONVERT(DATE, NULL) Finicio , NULL ETIQUETA, " +
							" HIS.Justificacion,NULL NOMBRE , NULL TipoPermiso, NULL PU, NULL TipoSolicitud, NULL OrigenFolio, NULL Origen, NULL CAS,NULL EstadoPermiso, " +
							" NULL EstadoFisico, HIS.Documento_FEL AS FolioLote, NULL FSometio, NULL DoctoEntrada, NULL NoEntrada, NULL FolioConjunto, " +
							" NULL FraccionArancelaria, NULL ClasifSolicitud, NULL TipoProducto, NULL Subtipo, NULL Control, NULL Clasificacion, " +
							" NULL PEDCOT, NULL DiasFiltro, NULL CANT " +
							" FROM HistorialPermisoImportacion AS HIS " +
							" WHERE FK_Solicitud =" + idSolicitud +
							" ORDER BY HIS.Fecha ASC ";
		
		//log.info(query);
		return super.jdbcTemplate.query(query, map, new ObtenerPermisoImportacionRowMapper());
		} catch (RuntimeException e) {
			//logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PermisoImportacion> obtenerPrevioPermisoImportacion() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			
			funcion = new Funcion();
			
			String query =  " \n SELECT PROVE.Nombre, PROD.TIPO, PROD.TipoPermiso, PROD.PermisoImp, PEN.Folio,PEN.Docto,PEN.Partida, " +
							" \n PROD.Codigo, CONVERT(varchar(MAX),PROD.Concepto) +' ('+ CONVERT(varchar,PROD.Cantidad) +' '+ CONVERT(varchar,PROD.Unidad) +') '+ CONVERT(varchar,PROD.Fabrica) AS NOMBRE, " +
							" \n CASE WHEN SUBSTRING(PEN.Docto,1 ,1) = 'C' THEN 'ES' ELSE 'FS' END SISTEMA,COALESCE (PCOTPHA.COTIZA,PCOT.CLAVE)AS ORIGEN, " +
							" \n PROD.Concepto, PROD.Subtipo, PROD.Control, PROD.Clasificacion, PROD.Cantidad, PROD.Unidad, PROD.DepositarioInternacional, PROD.CAS, PROD.EstadoFisico, " +
							" \n PROD.FraccionArancelaria, PROD.TipoPermiso, PROD.ClasificacionProducto, PROD.DatosToxicologicos,PROVE.Pais," +
							  funcion.sqlConversionMonedasADolar("COALESCE(PROD.Moneda,PCOTPHA.Moneda)", "COALESCE(PCOTPHA.Cantidad,PCOT.Cant)", "COALESCE(PCOTPHA.Precio,PCOT.Precio)", "MON", "","",false) + " AS MONTO," +
							" \n COALESCE (PCOTPHA.Cantidad,PCOT.Cant)AS PIEZAS, COALESCE(PROD.EstadoPermiso,'Sin Permiso') AS  Estadopermiso," +
							" \n PROD.idProducto,'C-'+ CONVERT (VARCHAR,COALESCE (PCOT.idPCotiza,PCOT99.idPCotiza,pc1to98.idPCotiza)) AS IDPCOTIZA" +
							" \n FROM Pendiente AS PEN " +
							" \n LEFT JOIN(SELECT * FROM Productos) AS PROD ON PROD.idProducto = PEN.Partida " +
							" \n LEFT JOIN(SELECT * FROM Proveedores) AS PROVE ON PROVE.Clave = PROD.Proveedor " +
							" \n LEFT JOIN(SELECT * FROM PCotizas) AS PCOT ON PCOT.idPCotiza =  CASE WHEN SUBSTRING(PEN.Docto,1 ,1) = 'C' THEN SUBSTRING(PEN.Docto,3 ,15) ELSE 0 END  " +
							" \n LEFT JOIN(SELECT * FROM PCotPharma) AS PCOTPHA ON PCOTPHA.Folio = CASE WHEN SUBSTRING(PEN.Docto,1 ,1) = 'C' THEN 0 ELSE PEN.Docto END" +
							" \n LEFT JOIN(SELECT * FROM PCotizas WHERE Folio = 99 ) AS PCOT99 ON PCOT99.Codigo = PCOTPHA.Codigo AND PCOT99.Fabrica = PCOTPHA.Fabrica AND PCOT99.Clave = PCOTPHA.Cotiza " +
							" \n LEFT JOIN(SELECT Clave,idPCotiza,Codigo,Fabrica FROM PCotizas WHERE Folio<>0 AND Folio<>99) AS pc1to98 ON pc1to98.Clave=PCOTPHA.Cotiza AND pc1to98.Codigo=PCOTPHA.Codigo AND pc1to98.Fabrica=PCOTPHA.Fabrica " +
							" \n LEFT JOIN(SELECT * FROM Monedas) AS MON ON CONVERT(DATE,MON.Fecha,101) =COALESCE( CONVERT(DATE,PCOTPHA.FInicio,101),CONVERT(DATE,PCOT.FGeneracion,101))" +
							" \n WHERE PEN.Tipo='Previo Tramitacion PermisoImp' AND PEN.FFin IS NULL";
		
		//log.info(query);
		return super.jdbcTemplate.query(query,map, new ObtenerPrevioPermisoImportacionRowMapper());
		} catch (RuntimeException e) {
			//logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	public Integer cerrarPendientePrevio(String fecha, String Folio) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha); 
			map.put("Folio", Folio); 
			
			Object [] params = {fecha,Folio};
			
			super.jdbcTemplate.update("UPDATE Pendiente SET FFin = ? WHERE Folio= ?", map);
			return 1;
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			return -1;
			
		}
	}

	public Integer actualizarProducto(PermisoImportacion data) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("TipoProducto", data.getTipoProducto()); 
			map.put("SubTipo", data.getSubTipo()); 
			map.put("ControlProducto", data.getControlProducto()); 
			map.put("ClasificacionSolictud", data.getClasificacionSolictud()); 
			map.put("Concepto", data.getConcepto()); 
			map.put("CantidadString", data.getCantidadString()); 
			map.put("tUnidad", data.getUnidad()); 
			map.put("DepositarioInternacional", data.getDepositarioInternacional()); 
			map.put("Cas", data.getCas()); 
			map.put("EstadoFisico", data.getEstadoFisico()); 
			map.put("FraccionArancelaria", data.getFraccionArancelaria()); 
			map.put("TipoSolicitudPermiso", data.getTipoSolicitudPermiso()); 
			map.put("ClasificacionProducto", data.getClasificacionProducto()); 
			map.put("DatosToxicologicos", data.getDatosToxicologicos()); 
			map.put("ReqPermiso", data.getReqPermiso()); 
			map.put("Partida", data.getPartida()); 
			
			Object[] params1 =  {data.getTipoProducto(),data.getSubTipo(),data.getControlProducto(),data.getClasificacionSolictud(),
					data.getConcepto(),data.getCantidadString(),data.getUnidad(), data.getDepositarioInternacional(),	data.getCas(),
					data.getEstadoFisico(),data.getFraccionArancelaria(),data.getTipoSolicitudPermiso(),data.getClasificacionProducto(),
					data.getDatosToxicologicos(),data.getReqPermiso(),data.getPartida()};
			
			super.jdbcTemplate.update("UPDATE Productos SET Tipo=?,Subtipo=?,Control=?,Clasificacion=?,Concepto=?,Cantidad=?,"+
					"Unidad=?,DepositarioInternacional=?,CAS=?,EstadoFisico=?,FraccionArancelaria=?,TipoPermiso=?,ClasificacionProducto=?," +
					"DatosToxicologicos=?,PermisoImp=? WHERE idProducto=?",map);
			return 1;
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			return -1;
			
		}
	}

	public Integer actualizarPharma(PermisoImportacion data) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Producto", data.getTipoProducto()); 
			map.put("SubTipo", data.getSubTipo()); 
			map.put("ControlProducto", data.getControlProducto()); 
			map.put("ClasificacionSolictud", data.getClasificacionSolictud()); 
			map.put("Concepto", data.getConcepto()); 
			map.put("CantidadString", data.getCantidadString()); 
			map.put("Unidad", data.getUnidad()); 
			map.put("DepositarioInternacional",data.getDepositarioInternacional()); 
			map.put("Cas", data.getCas()); 
			map.put("EstadoFisico", data.getEstadoFisico()); 
			map.put("Docto", data.getDocto()); 
			
			Object[] params = {data.getTipoProducto(),data.getSubTipo(),data.getControlProducto(),data.getClasificacionSolictud(),
								data.getConcepto(),data.getCantidadString(),data.getUnidad(),data.getDepositarioInternacional(),
								data.getCas(), data.getEstadoFisico(),data.getDocto()};
			
			super.jdbcTemplate.update("UPDATE PCotPharma SET Tipo=?,Subtipo=?,Control=?,Clasificacion=?," +
					"Concepto=?,Presentacion=?,Unidades=?,DepositarioInternacional=?,CAS=?,EdoFisico=? WHERE Folio=?",map);
			return 1;
		}catch(RuntimeException r){
		//	logger.error(r.getMessage());
			return -1;
			
		}
	}

	public Integer insertarSolictudPermisoImp(PermisoImportacion data, String fecha) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha); 
			map.put("IdProducto", data.getIdProducto()); 
			map.put("FechaET", data.getFechaET()); 
			map.put("FraccionArancelaria", data.getFraccionArancelaria()); 
			map.put("TipoSolicitudPermiso", data.getTipoSolicitudPermiso()); 
			map.put("DatosToxicologicos", data.getDatosToxicologicos()); 
			map.put("ClasificacionProducto", data.getClasificacionProducto()); 
			map.put("Permiso", "Permiso"); 
			
			Object[] params2 =  {fecha, data.getIdProducto(), data.getFechaET(), data.getFraccionArancelaria(), 
					data.getTipoSolicitudPermiso(), data.getDatosToxicologicos(), data.getClasificacionProducto(),"Permiso"	};
			
			super.jdbcTemplate.update(  "INSERT INTO SolicitudPermisoImp(Fecha,FK01_Producto,FET,FraccionArancelaria," +
										" TipoPermiso,DatosToxicologicos,ClasificacionProducto,Tipo)"+
										" VALUES( ?,?,?,?,?,?,?,?) ",map);
			return 1;
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			return -1;
			
		}
	}

	public Integer crearPendientePartidaStr(PermisoImportacion data, String fecha) {
		String responsable="";
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha); 
			map.put("Partida", data.getPartida()); 
			map.put("Idpcotiza", data.getIdpcotiza()); 
		
			
			responsable = (String) jdbcTemplate.queryForObject("SELECT Usuario FROM Empleados WHERE nivel=22 AND Fase=1",map, String.class); 
			
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
		}
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fecha", fecha); 
			map.put("responsable", responsable); 
			map.put("Partida", data.getPartida()); 
			map.put("Idpcotiza", data.getIdpcotiza()); 
			map.put("A Tramitar PermisoImp", "A Tramitar PermisoImp"); 
			
			
			Object[] params =  { data.getPartida(),data.getIdpcotiza(),fecha,responsable,"A Tramitar PermisoImp"	};
			
			super.jdbcTemplate.update(  (" INSERT INTO Pendiente (Docto,Partida,FInicio,Responsable,Tipo) " +
										" VALUES ( ?,?,?,?,?) "),map);
			return 1;
		}catch(RuntimeException r){
		//	logger.error(r.getMessage());
			return -1;
			
		}
	}

	public Integer actualizarEdoProducto(Long idProducto) {
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idProducto", idProducto); 
			
			Object[] params =  { idProducto};
			
			super.jdbcTemplate.update(  " UPDATE Productos SET EstadoPermiso='A tramitacion' WHERE idProducto= ?",map);
			return 1;
		}catch(RuntimeException r){
			//logger.error(r.getMessage());
			return -1;
			
		}
	}
	
	
	@Override
	public String validarHorarioCliente(String idPCompra) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append(" \n");
			sbQuery.append("SELECT \n");
			sbQuery.append("CASE WHEN H.Diario = 1 THEN '0,6' ELSE '0,6' + \n");
			sbQuery.append("CASE WHEN H.Lunes = 1 THEN '' ELSE ',1' END + \n");
			sbQuery.append("CASE WHEN H.MArtes = 1 THEN '' ELSE ',2' END + \n");
			sbQuery.append("CASE WHEN H.Miercoles = 1 THEN '' ELSE ',3' END + \n");
			sbQuery.append("CASE WHEN H.Jueves = 1 THEN '' ELSE ',4' END + \n");
			sbQuery.append("CASE WHEN H.Viernes = 1 THEN '' ELSE ',5' END END Dias \n");
			sbQuery.append("FROM PCompras PC \n");
			sbQuery.append("INNER JOIN PPedidos PP ON PP.idPPedido = PC.FK03_PPedido \n");
			sbQuery.append("INNER JOIN PEdidos P ON P.CPedido = PP.CPedido  \n");
			sbQuery.append("INNER JOIN Clientes CL ON CL.Clave = P.idCliente \n");
			sbQuery.append("INNER JOIN HorarioyLugar H ON H.idHorario = P.FK01_Horario \n");
			sbQuery.append("WHERE PC.idPCompra = :idPCompra \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idPCompra", idPCompra);
			return jdbcTemplate.queryForObject(sbQuery.toString(), parametros, String.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public List<String> obtenerAsuetos() throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT CAST(Fecha as DATE) Fecha FROM Asueto WHERE YEAR(Fecha) = YEAR(GETDATE()) \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			return jdbcTemplate.queryForList(sbQuery.toString(), parametros, String.class);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String obtenerDiaFinMes(String idCliente) throws ProquifaNetException {
		try {
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT DiasFinMes FROM Clientes WHERE Nombre = :idCliente AND NoFacturar_FinMes = 1 \n");
			
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("idCliente", idCliente);
			return jdbcTemplate.queryForObject(sbQuery.toString(), parametros, String.class);
			
		} catch (Exception e) {
			
		}
		return "-1";
	}
	
	@Override
	public ResumenCompra getResumenCompra(String folioOC) throws ProquifaNetException {
		ResumenCompra rc = new ResumenCompra();
		List<ResumenPCompra> rpc = new ArrayList<ResumenPCompra>();

		Map<String, Object> map = new HashMap<String, Object>();

		try {

			String sql = "select 'OC-'+ C.Clave as orden, "
					+ "\n(CASE WHEN P.RSocial=NULL OR P.RSocial='' OR P.RSocial=' ' THEN 'ND'ELSE P.RSocial END )AS nombreP, P.Clave AS idProveedor, "
					+ "\n(CASE WHEN EP.NumAsignado=NULL OR EP.NumAsignado='' OR EP.NumAsignado=' ' THEN 'ND'ELSE EP.NumAsignado END )AS num_cliente, "
					+ "\n CASE WHEN P.Clave = 1954603426 THEN 'Compras' ELSE "
					+ "\n(CASE WHEN E.Nombre=NULL OR E.Nombre='' OR  E.Nombre=' ' THEN 'ND' ELSE E.Nombre END) END AS nombreR, "
					+ "\nCONVERT(VARCHAR(10), CAST(C.Fecha AS DATE), 103) AS fechaR, "
					+ "\nCOALESCE(CO.Titulo, '')+' '+COALESCE(CO.contacto, '')+' / '+COALESCE(CO.Depto, '') As contacto, "
					+ "\n(CASE WHEN CO.Fax=NULL OR CO.Fax=''OR CO.Fax=' ' THEN 'ND' ELSE CO.Fax END) AS fax, "
					+ "\nCOALESCE(CO.Tel1, '') +'      '+COALESCE(CO.TEL2, '') AS TEL, "
					+ "\n(CASE WHEN C.eMail=NULL OR C.eMail='' OR C.eMail=' ' THEN 'ND' ELSE C.eMail END) AS email, "
					+ "\n(CASE WHEN C.Moneda = 'Dolares' THEN 'USD' "
					+ "\nWHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN 'M.N.' "
					+ "\nWHEN C.Moneda = 'Euros' THEN 'EUR' " + "\nWHEN C.Moneda = 'Libras' THEN 'Libras' "
					+ "\nWHEN C.Moneda = 'DlCan' THEN 'DlCan' " + "\nWHEN C.Moneda = 'Yenes' THEN 'JPY' "
					+ "\nELSE 'M.N.' END) AS moneda, " + "\n(CASE WHEN C.Moneda = 'Dolares' THEN '$' "
					+ "\nWHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN '$' " + "\nWHEN C.Moneda = 'Euros' THEN '€' "
					+ "\nWHEN C.Moneda = 'Libras' THEN '£' " + "\nWHEN C.Moneda = 'DlCan' THEN '$' "
					+ "\nWHEN C.Moneda = 'Yenes' THEN '¥' " + "\nELSE 'M.N.' END) AS simboloMoneda, "
					+ "\n(CASE WHEN P.Pais = 'MEXICO' THEN COALESCE(P.RSCalle, '')+', '+COALESCE(P.RSDel, '')+', '+COALESCE(P.CP, '')+', '+COALESCE(P.Pais, '')+', '+ COALESCE(P.Ciudad, '') ELSE COALESCE(P.RSCalle, '')+', '+COALESCE(P.Ciudad, '')+', '+COALESCE(P.CP, '')+', '+COALESCE(P.Pais, '') END) AS direccion, "
					+ "\nCONVERT(varchar,CAST((Select SUM (Pc.Cant * pc.Costo) FROM PCompras AS PC where PC.Compra= c.Clave) as money), 1) as SUBTOTAL, "
					+ "\nCONVERT(varchar,CAST(CAST((Select SUM (Pc.Cant * pc.Costo) FROM PCompras AS PC where PC.Compra= c.Clave) as money)* CAST(c.IVA as money) as money), 1) as IVA, "
					+ "\nCONVERT(varchar, CAST(CAST((Select SUM (Pc.Cant * pc.Costo) FROM PCompras AS PC where PC.Compra= c.Clave) as decimal(20,2)) +(CAST((Select SUM (Pc.Cant * pc.Costo) FROM PCompras AS PC where PC.Compra= c.Clave) as decimal(20,2))* c.IVA)as money), 1) as TOTAL, "
					+ "\nCOALESCE(Facturar.RazonSocial, '') +'/ '+COALESCE(Facturar.RFC, '') + CHAR(13) + COALESCE(facturar.Calle, '') +', '+ CASE WHEN facturar.Colonia IS NOT NULL AND facturar.Colonia <> '' THEN COALESCE(facturar.Colonia, '')+ ', ' ELSE '' END + CASE WHEN facturar.Delegacion IS NOT NULL AND facturar.Delegacion <>'' THEN COALESCE(facturar.Delegacion, '') + ', ' ELSE '' END + COALESCE(facturar.Estado, '') + ', '+ COALESCE(facturar.Pais, '') +', CP '+ COALESCE(facturar.CP, 'ND') as facturara, "
					+ "\nCOALESCE(facturar.Calle, '') +', '+CASE WHEN facturar.Colonia IS NOT NULL AND facturar.Colonia <> '' THEN COALESCE(facturar.Colonia, '')+ ', ' ELSE '' END + CASE WHEN facturar.Delegacion IS NOT NULL AND facturar.Delegacion <> '' THEN COALESCE(facturar.Delegacion, '') + ', ' ELSE '' END + COALESCE(facturar.Estado, '') + ', '+ COALESCE(facturar.Pais, '') +', CP '+ COALESCE(facturar.CP, 'ND') as Dirfactu, "
					+ "\nCOALESCE(EM.RazonSocial, '') +'/ '+COALESCE(EM.RFC, '') + CHAR(13) + COALESCE(EM.Calle, '') +', '+ CASE WHEN EM.Colonia IS NOT NULL AND EM.Colonia <> '' THEN COALESCE(EM.Colonia, '') + ', ' ELSE '' END + CASE WHEN EM.Delegacion IS NOT NULL AND EM.Delegacion <> '' THEN COALESCE(EM.Delegacion, '') + ', ' ELSE '' END + COALESCE(EM.Estado, '')+ ', '+ COALESCE(EM.Pais, '') +', CP '+ COALESCE(EM.CP, 'ND') + CHAR(13) + 'PH: ' + COALESCE(EM.Telefono, 'ND') AS empresa, "
					+ "\n EM.RazonSocial AS RSocial, C.FacturarA AS AliasFacturarA, CASE WHEN P.Pais = 'MEXICO' THEN 0 ELSE 1 END enIngles "
					+ "\nFROM Compras As C"
					+ "\nleft join proveedores As P On  C.Provee= P.clave "
					+ "\nleft join empleados As E On P.FK01_Empleado= E.clave "
					+ "\nleft join Empresa As Facturar On (CASE WHEN C.FacturarA= 'PhS-USA' THEN 'Pharma' ELSE C.FacturarA END)COLLATE SQL_Latin1_General_CP1_CI_AS = Facturar.Alias "
					+ "\nleft join Empresa As EM On (CASE WHEN C.EntregarEn = 'PhS-USA' THEN 'Pharma' ELSE  C.EntregarEn END) COLLATE SQL_Latin1_General_CP1_CI_AS = EM.Alias "
					+ "\nleft join Empresas_Proveedor AS EP On FK01_Proveedor=C.Provee AND FK02_Empresa=Facturar.PK_Empresa AND Habilitada=1 "
					+ "\nleft join Contactos AS CO On CO.idContacto = C.FK01_Contacto And CO.FK01_Proveedor= C.Provee  "
					+ "\nWhere C.Clave='" + folioOC + "'";

			LOGGER.info(sql);

			rc = (ResumenCompra) super.jdbcTemplate.queryForObject(sql, map, new ResumenCompraRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			String sql = "\n select "
					+ "\n rank() OVER (ORDER BY MIN(PC.Partida)) as num, PP.Codigo as CodigoProducto, "
					+ "\n SUM(Convert(integer,PC.Cant)) AS cantidad, "
					+ "\n PP.Codigo + ' ' + CAST(PP.Concepto AS varchar(max)) AS codigo,"
					+ "\n (CASE WHEN C.Moneda = 'Dolares' THEN '$ '+CONVERT(varchar,CAST(Pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN '$ '+CONVERT(varchar,CAST(Pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Euros' THEN '€ '+CONVERT(varchar,CAST(Pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Libras' THEN '£ '+CONVERT(varchar,CAST(Pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'DlCan' THEN '$ '+CONVERT(varchar,CAST(Pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Yenes' THEN '¥ '+CONVERT(varchar,CAST(Pc.Costo AS money),1) "
					+ "\n ELSE '$'+CONVERT(varchar,CAST(Pc.Costo AS money),1) END) AS precio, "
					+ "\n (CASE WHEN C.Moneda = 'Dolares' THEN '$ '+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Pesos' OR C.Moneda = 'M.N.' THEN '$'+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Euros' THEN '€ '+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Libras' THEN '£ '+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'DlCan' THEN '$ '+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) "
					+ "\n WHEN C.Moneda = 'Yenes' THEN '¥ '+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) "
					+ "\n ELSE '$ '+CONVERT(varchar,CAST(SUM(Pc.Cant) * pc.Costo AS money),1) END) AS Importe "
					+ "\n from Compras As C " + "\n left join pcompras As PC On C.Clave=PC.compra "
					+ "\n INNER JOIN PPedidos AS PP ON PP.idPPedido = PC.Fk03_PPedido"
					+ "\n INNER JOIN Productos AS PO ON PO.idProducto = PC.Fk01_Producto"
					+ "\n left join proveedores As P On  C.Provee= P.clave "
					+ "\n left join empleados As E On P.FK01_Empleado= E.clave " + "\n Where C.Clave = '" + folioOC
					+ "' and PC.idComplemento = 0 "
					+ "\n GROUP By PP.Codigo, CAST(PP.Concepto AS varchar(max)), C.Moneda,Pc.Costo, C.Clave "
					+ "\n Order By MIN(PC.Partida)";

			LOGGER.info(sql);
			
			rpc = super.jdbcTemplate.query(sql, new ResumenPCompraRowMapper());

		} catch (Exception e) {
			e.printStackTrace();
		}

		rc.setResumenPCompra(new ArrayList<ResumenPCompra>());

		for (ResumenPCompra item : rpc) {
			rc.getResumenPCompra().add(item);
		}

		return rc;
	}
}