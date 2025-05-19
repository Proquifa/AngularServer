/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.comun.TiempoProceso;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.modelo.ventas.Pedido;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.PedidoDAO;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoConsultaRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoGuiaRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoHistorialNotificadoRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoReporteRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoRowMapper;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoTiempoProcesoRowMapper;
//import com.proquifa.net.persistencia.ventas.impl.PDFConfirmacionPedido;
//import com.proquifa.net.modelo.ventas.PDFConfirmacionPedido;


/**
 * @author ernestogonzalezlozada
 *
 */
@Repository
public class PedidoDAOImpl extends DataBaseDAO implements PedidoDAO {

	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.comun.PedidoDAO#obtenerPedidoXDocumentoRecibido(java.lang.String)
	 */
	final Logger log = LoggerFactory.getLogger(PedidoDAOImpl.class);
	public Pedido obtenerPedidoXDocumentoRecibido(Long documentoRecibido) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("documentoRecibido",documentoRecibido);
			return (Pedido)super.jdbcTemplate.queryForObject("SELECT * FROM Pedidos WHERE DoctoR = '" + documentoRecibido + "'",map, new PedidoRowMapper());			
		}catch(RuntimeException rte){
//			new Funcion().enviarCorreoAvisoExepcion(rte, "\nDocumentoRecibido: "+dcumentoRecibido);
//			return new Pedido();
			return null;
		}
	}
	
	public void actualizarPedido(Pedido nuevoPedido) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("DoctoR",nuevoPedido.getDoctoR());
			Object params [] = {nuevoPedido.getPedido()};
			String query = "UPDATE Pedidos SET pedido = ? WHERE doctor = " + nuevoPedido.getDoctoR() + " ";
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, nuevoPedido);
		}
	}

	public Integer obtenerNumeroPedidosXDoctoR(Long folioDocumento) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("folioDocumento",folioDocumento);
			return super.queryForInt("select COUNT(idpedido) as noPedidos FROM Pedidos WHERE DoctoR = '" + folioDocumento + "'");
		}catch(Exception e){
			new Funcion().enviarCorreoAvisoExepcion(e, "\nFolioDocumento: "+folioDocumento);
			return 0;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Pedido> obtenerPedidoXFolio(String cPedido) {
		String query = "SELECT * FROM pedidos WHERE cpedido LIKE '%" + cPedido + "%'";
		return super.jdbcTemplate.query(query,new PedidoRowMapper ());
	}
	
	public Pedido obtenerPedidoXCPedido(String cPedido) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cPedido",cPedido);
		String query = "SELECT * FROM pedidos WHERE cpedido LIKE '%" + cPedido + "%'";
		
		return (Pedido) super.jdbcTemplate.queryForObject(query,map,new PedidoRowMapper ());
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public Pedido obtenerPedidoXIdPedido(Integer idPedido) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("idPedido",idPedido);
		String query = "SELECT * FROM pedidos WHERE idPedido = " + idPedido;
		
		return (Pedido) super.jdbcTemplate.queryForObject(query,map,new PedidoRowMapper ());
	}
	
	@SuppressWarnings("unchecked")
	public List<Pedido> obtenerPedidosXCpedido(String cPedido, String tipo, Date finicio, Date ffin, String Cliente, String tramito, String Estado, String referencia){
		String query="";
		SimpleDateFormat formatoDeFecha = new SimpleDateFormat("yyyyMMdd");
		if(tipo.equals("rapida")){

			query = "SELECT (case when cuantos.cuantos IS null then 'Cerrado' else 'Abierto' end) as cuantos, Pedidos.DoctoR,Pedidos.FPedido,Pedidos.idCliente, Pedidos.idContacto, Pedidos.CPedido, Pedidos.Pedido, ";  
			query += "DoctosR.Folio,DoctosR.Fecha,DoctosR.FHOrigen,DoctosR.RPor as Tramito,Contactos.Contacto,Clientes.Nombre "; 
			query += "FROM Pedidos inner join Contactos on Contactos.idContacto=Pedidos.idContacto ";
			query += "left join Clientes on Clientes.Clave=Pedidos.idCliente ";
			query += "left join DoctosR on DoctosR.Folio=Pedidos.Doctor AND Pedidos.DoctoR=DoctosR.Folio AND Pedidos.idCliente=DoctosR.Empresa "; 
			query += "left join ( ";
			query += "SELECT COUNT(*) AS cuantos, CPedido FROM PPedidos WHERE ";   
			query += "Estado<>'Entregado '  AND Estado<>'A destrucción' AND Estado<>'Cancelada' AND Estado<>'Enviado' "; 
			query += "AND CPedido IN (SELECT Pedidos.CPedido ";
			query += "FROM pedidos,DoctosR,contactos,clientes WHERE Pedidos.doctor "; 
			query += "IN (SELECT DISTINCT docto FROM pendiente WHERE Tipo='Pedido por Tramitar' AND FFin IS NOT NULL)  "; 
			query += "AND DoctosR.Folio=Pedidos.Doctor AND Pedidos.DoctoR=DoctosR.Folio AND Pedidos.idCliente=DoctosR.Empresa ";
			query += "and contactos.idContacto=Pedidos.idContacto and Clientes.Clave=Pedidos.idCliente ) ";
			query += "group by CPedido)as cuantos on cuantos.CPedido=Pedidos.CPedido ";
			query += "WHERE Pedidos.doctor "; 
			query += "IN (SELECT DISTINCT docto FROM pendiente WHERE Tipo='Pedido por Tramitar' AND FFin IS NOT NULL)  "; 
			query += "AND Pedidos.CPedido LIKE '%" + cPedido +"%' ";

		}else{
			query = "SELECT (case when cuantos.cuantos IS null then 'Cerrado' else 'Abierto' end) as cuantos, Pedidos.DoctoR,Pedidos.FPedido,Pedidos.idCliente, Pedidos.idContacto, Pedidos.CPedido, Pedidos.Pedido, ";  
			query += "DoctosR.Folio,DoctosR.Fecha,DoctosR.FHOrigen,DoctosR.RPor as Tramito,Contactos.Contacto,Clientes.Nombre "; 
			query += "FROM Pedidos inner join Contactos on Contactos.idContacto=Pedidos.idContacto ";
			query += "left join Clientes on Clientes.Clave=Pedidos.idCliente ";
			query += "left join DoctosR on DoctosR.Folio=Pedidos.Doctor AND Pedidos.DoctoR=DoctosR.Folio AND Pedidos.idCliente=DoctosR.Empresa "; 
			query += "left join ( ";
			query += "SELECT COUNT(*) AS cuantos, CPedido FROM PPedidos WHERE ";   
			query += "Estado<>'Entregado '  AND Estado<>'A destrucción' AND Estado<>'Cancelada' AND Estado<>'Enviado' "; 
			query += "AND CPedido IN (SELECT Pedidos.CPedido ";
			query += "FROM pedidos,DoctosR,contactos,clientes WHERE Pedidos.doctor "; 
			query += "IN (SELECT DISTINCT docto FROM pendiente WHERE Tipo='Pedido por Tramitar' AND FFin IS NOT NULL) AND FPedido>='" + formatoDeFecha.format(finicio) + "' AND FPedido<='" + formatoDeFecha.format(ffin) + "'  "; 
			query += "AND DoctosR.Folio=Pedidos.Doctor AND Pedidos.DoctoR=DoctosR.Folio AND Pedidos.idCliente=DoctosR.Empresa ";
			query += "and contactos.idContacto=Pedidos.idContacto and Clientes.Clave=Pedidos.idCliente ) ";
			query += "group by CPedido)as cuantos on cuantos.CPedido=Pedidos.CPedido ";
			query += "WHERE Pedidos.doctor "; 
			query += "IN (SELECT DISTINCT docto FROM pendiente WHERE Tipo='Pedido por Tramitar' AND FFin IS NOT NULL) AND FPedido>='" + formatoDeFecha.format(finicio) + "' AND FPedido<='" + formatoDeFecha.format(ffin) + "'  "; 
//			query += "AND Pedidos.CPedido LIKE '%" + cPedido +"%' ";
			if(!Cliente.equals("--TODOS--")){
				query += " AND Clientes.Nombre='" + Cliente + "' ";
			}
			if(!tramito.equals("--TODOS--")){
				query += " AND RPor ='" + tramito + "'";
			}
			if(!referencia.equals("")){
				query += " AND Pedidos.Pedido like '%" + referencia + "%'";
			}
		}
		return super.jdbcTemplate.query(query,new PedidoConsultaRowMapper());
	}
	
	public 	Long obtenerEstadoXCpedido(String cPedido){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cPedido",cPedido);
		String query="SELECT COUNT(1) AS cuantos FROM PPedidos WHERE CPedido='" + cPedido + "' AND Estado<>'Entregado ' "+ 
				     " AND Estado<>'A destrucción' AND Estado<>'Cancelada' AND Estado<>'Enviado'";
		Long estado = super.queryForLong(query);
		return estado;
		//return (Long)super.jdbcTemplate.queryForObject(query,Long.class);
	}
	
	public 	PartidaPedido obtenerGuiaPedido(Long Factura, String fPor, String  idEvento){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Factura",Factura);
			map.put("fPor",fPor);
			map.put("idEvento",idEvento);
			String query = "SELECT TOP 1 RutaDP.idEntrega, RutaDP.idRuta,RutaDP.FFin as rutaFFin,RutaDP.Ruta,RutaDP.Conforme, RutaDP.FolioDoctos,EnvioAlmacen.Guia, EnvioAlmacen.idGuia, Pendiente.FFin FROM RutaDP "+ 
						   "LEFT JOIN EnvioAlmacen on RutaDP.idEntrega=EnvioAlmacen.idEntrega LEFT JOIN Pendiente " + 
						   "ON EnvioAlmacen.idEntrega=Pendiente.Docto WHERE RutaDP.Factura='" + Factura + "' AND RutaDP.FPor='" + fPor + "' AND RutaDP.idDP='" + idEvento + "' ";   
							// "UNION " +
							// "SELECT RutaDP.idEntrega, RutaDP.idRuta ,EnvioAlmacen.Guia, 'NA' AS idGuia, 0 AS FFin FROM RutaDP, EnvioAlmacen  WHERE RutaDP.Factura='" + Factura + "' " +
							// "AND RutaDP.FPor='" + fPor + "' AND RutaDP.idDP='" + idEvento + "' AND EnvioAlmacen.idEntrega=RutaDP.idEntrega ";
			return (PartidaPedido)super.jdbcTemplate.queryForObject(query,map, new PedidoGuiaRowMapper());
		} catch (RuntimeException e) {
			return null;
		}
	}
	
	public Date obtenerFechaEstimadaRealizacion(String cPedido, Long Factura, String fPor, Integer Part){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido",cPedido);
			map.put("Factura",Factura);
			map.put("fPor",fPor);
			map.put("Part",Part);
			String query =   "SELECT CASE DATEPART(WEEKDAY,FInicio) " +
			 "WHEN 2 THEN DATEADD(d,1,FInicio) " +
			 "WHEN 3 THEN DATEADD(d,1,FInicio) " +
			 "WHEN 4 THEN DATEADD(d,1,FInicio) " +
			 "WHEN 5 THEN DATEADD(d,1,FInicio) ELSE DATEADD(d,3,FInicio) END FFin " + 
			 "FROM Pendiente, PRutaDP, RutaDP, PFacturas where Pendiente.Docto=RutaDP.idRuta AND RutaDP.idDP=PRutaDP.idDP AND PRutaDP.Factura='" + Factura + "' AND PRutaDP.FPor='" + fPor + "' " + 
			 "AND PRutaDP.Part=PFacturas.Part AND PFacturas.CPedido='" + cPedido + "' AND PFacturas.PPedido='" + Part + "' AND PFacturas.Factura='" + Factura + "' AND Pendiente.Tipo='Ruta a trabajar' " +
			 "UNION " +
			 "SELECT FInicio  FROM Pendiente,RutaDP,PRutaDP,PFacturas WHERE Pendiente.Docto=RutaDP.idEntrega AND RutaDP.idDP=PRutaDP.idDP AND PRutaDP.Factura='" + Factura + "' AND PRutaDP.FPor='" + fPor + "' " +
			 "AND PRutaDP.Part=PFacturas.Part AND PFacturas.CPedido='" + cPedido + "' AND PFacturas.PPedido='" + Part + "' AND PFacturas.Factura='" + Factura + "' AND Pendiente.Tipo='A Ejecutar ruta' " +
			 "UNION " +
			 "SELECT FFin FROM Pendiente WHERE Docto='" + Factura + "' AND Partida='" + fPor + "' AND Tipo='Factura a surtir' ";
			
			return (Date)super.jdbcTemplate.queryForObject(query,map,Date.class);
		} catch (RuntimeException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PartidaPedido> obtenerHistorialNotificado(String folioNT, String compra, Integer part){
		try{
			String query = "SELECT Notificado.FEnvio,Pendiente.Responsable, GestionAC.Decision, GestionAC.idNotRespuesta FROM Notificado, Pendiente, GestionAC " +
							"WHERE Notificado.idNotificado='" + folioNT + "' and Pendiente.Docto='" + compra + "' AND Pendiente.Partida='" + part + "' " + 
							"AND Notificado.idNotificado=GestionAC.idNotificado AND Pendiente.Responsable=GestionAC.Gestiono ";
			return super.jdbcTemplate.query(query, new PedidoHistorialNotificadoRowMapper());
		}catch (RuntimeException e) {
			return null;
		}
	}
	
	public Long obtenerTiempoProceso(Date finicio, Date ffin){
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("finicio",finicio);
			map.put("ffin",ffin);
			String query = "SELECT " +
							"DATEDIFF(MI,'" + finicio + "','" + ffin + "') "+
							"- DATEDIFF(week,'" + finicio + "','" + ffin + "') * 2 * 24 * 60 "+
							"- CASE WHEN DATENAME(dw, '" + finicio + "') <> 'Saturday' "+
							"AND DATENAME(dw, '" + ffin + "') = 'Saturday' THEN 24 * 60 "+ 
							"WHEN DATENAME(dw, '" + finicio + "') = 'Saturday' " + 
							"AND DATENAME(dw, '" + ffin + "') <> 'Saturday' THEN -24 * 60 ELSE 0 END AS tiempoProceso  ";
			return (Long) super.jdbcTemplate.queryForObject(query,map, Long.class);
		} catch (RuntimeException e) {
			return null;
		}
	}
	
	public TiempoProceso obtenerDoctoPedido(String cPedido){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cPedido",cPedido);
			String query ="SELECT TOP 1 PPedidos.FRecibido as finiciopedido,pedidos.doctor,pedidos.fpedido FROM ppedidos,pedidos WHERE  ppedidos.cpedido = pedidos.cpedido AND ppedidos.cpedido = '" + cPedido + "' ";
			return (TiempoProceso)super.jdbcTemplate.queryForObject(query,map, new PedidoTiempoProcesoRowMapper());
		}catch (RuntimeException e) {
			return null;
		}
	}

	public Double obtenerMontoTotalPedidos(Long idCliente,Long idProveedor, String periodo) {	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cPedido",idCliente);
		map.put(" idProveedor", idProveedor);
		map.put(" periodo", periodo);
		String query = "SELECT SUM(TOTALDOLARES) from (SELECT ROUND(sum(cant * Precio), 2) as totalDolares, 0.0 as tipoCambio from ppedidos,pedidos where ppedidos.provee = "+ idProveedor +  " and pedidos.idCliente = " + idCliente + " and " +
				"ppedidos.cpedido = pedidos.cpedido and pedidos.idCliente is not Null and pedidos.fpedido >= '" +  periodo + "' and ppedidos.estado <> 'Cancelada' " +
				"and ppedidos.estado <> 'A destrucción' and pedidos.Moneda = 'Dolares' " +
				"UNION " +
				"select ROUND(sum(cant * Precio)/PDolar, 2) as totalDolares, 0.0 as tipoCambio from ppedidos,pedidos, Monedas where ppedidos.provee = " + idProveedor + " and pedidos.idCliente = " + idCliente + " and " +
				"ppedidos.cpedido = pedidos.cpedido and pedidos.idCliente is not Null and pedidos.fpedido >= '" + periodo + "' and ppedidos.estado <> 'Cancelada' " +
				"and ppedidos.estado <> 'A destrucción' and pedidos.Moneda = 'Pesos' " +
				"and Monedas.fecha in (select top 1 fecha from Monedas order by Fecha desc) group by PDolar " +
				"UNION " +
				"select ROUND (sum(cant * Precio) * EDolar, 2) as totalDolares, 0.0 tipoCambio from ppedidos,pedidos, Monedas where ppedidos.provee = " + idProveedor + "  and pedidos.idCliente = " + idCliente + " and " +
				"ppedidos.cpedido = pedidos.cpedido and pedidos.idCliente is not Null and pedidos.fpedido >= '" + periodo + "' and ppedidos.estado <> 'Cancelada' " +
				"and ppedidos.estado <> 'A destrucción' and pedidos.Moneda = 'Euros' " +
				"and Monedas.fecha in (select top 1 fecha from Monedas order by Fecha desc) group by  EDolar) as sumaTotalEnDolares"; 
		return (Double)super.jdbcTemplate.queryForObject(query,map, Double.class);
	}
	@SuppressWarnings("unchecked")
	public List<Pedido> obtenerCantidadXProveedor(Long idProveedor, String periodo, Boolean topTen){
		String query = "";
		if(topTen){
			query = "select TOP 10 idcliente,sum(cant) as cantidad, clientes.nombre from ppedidos,pedidos, clientes where ppedidos.provee = " + idProveedor + " and " +
			"ppedidos.cpedido = pedidos.cpedido and pedidos.idCliente is not Null and pedidos.fpedido >= '"+ periodo + "' and ppedidos.estado <> 'Cancelada' and ppedidos.estado <> 'A destrucción' and clientes.clave = pedidos.idCliente " +
			"group by pedidos.idcliente, clientes.nombre order by cantidad desc ";
		}else{
			query = "select idcliente,sum(cant) as cantidad, clientes.nombre from ppedidos,pedidos, clientes where ppedidos.provee = " + idProveedor + " and " +
			"ppedidos.cpedido = pedidos.cpedido and pedidos.idCliente is not Null and pedidos.fpedido >= '"+ periodo + "' and ppedidos.estado <> 'Cancelada' and ppedidos.estado <> 'A destrucción' and clientes.clave = pedidos.idCliente " +
			"group by pedidos.idcliente, clientes.nombre order by cantidad desc ";
		}
		return super.jdbcTemplate.query(query, new PedidoReporteRowMapper());
	}

	public List<PDFConfirmacionPedido> obtenerDatosPdfConfirmarPedido(String cpedido) {

		try {
			String sql="";
			sql =	"SELECT CASE WHEN PPedidos.Estado='Cancelada' THEN 'Cancelada' ELSE COALESCE(Productos.Disponibilidad, ProdPadre.Disponibilidad) END AS Tipo," +
					" Cl.nombre as cliente," +
					" ppedidos.Codigo + ' ' + CAST(ppedidos.Concepto AS VARCHAR(1000)) AS Concepto, contactos.Titulo + ' '+ contactos.contacto as contacto, contactos.Puesto, CASE WHEN contactos.Depto <> '' THEN contactos.Depto ELSE 'N/D' END AS Departamento," +
					" ppedidos.cpedido, CAST(PPedidos.FPEntrega AS DATE) FEE, PPedidos.cant Pza," +
					" CASE WHEN PPedidos.Estado='Cancelada' THEN 0 ELSE PPedidos.Cant * PPedidos.Precio END AS PT, CASE WHEN PPedidos.Estado='Cancelada' THEN 0 ELSE PPedidos.Precio END AS PU," +
					" Cl.vendedor as 'usuario', P.CPago as condicionPago, CASE WHEN P.Moneda='Dolares' THEN 'USD' WHEN P.Moneda='Euros' THEN 'EUR' WHEN P.Moneda='Pesos' THEN 'M.N.' END AS Moneda, P.IVA, P.RFCFiscalP," +
					" P.DireccionFiscalP +' '+ P.DelegacionFiscalP +' '+ P.EstadoFiscalP +', '+ P.PaisFiscalP +', C.P.'+ P.CPFiscalP as direccionFiscal, P.Parciales, contactos.Contacto as contactoArriba," +
					" LTRIM(P.Titulo +' '+ P.Contacto +', '+ P.Puesto+' / '+ P.Departamento) as contactoEntrega, P.Calle +' '+ P.Delegacion +' '+ P.Estado +', '+ P.Pais + ', C.P.'+ P.CP as direccionEntrega," +
					" P.Pedido as Referencia, P.NombreFiscalP as razonSocial, Productos.Fabrica, Empleados.Nombre AS Tramito, DoctosR.FHOrigen as Recepcion, DoctosR.FProceso as Tramitacion," +
					" P.FacturFiscalP as condicion from Pedidos AS P" +
					" LEFT JOIN Clientes AS Cl ON Cl.Clave=P.idCliente " +
					" LEFT JOIN ppedidos ON ppedidos.Cpedido = P.CPedido" +
					" LEFT JOIN contactos ON contactos.idContacto = P.idContacto" +
					" LEFT JOIN productos ON productos.Codigo = ppedidos.Codigo AND productos.Fabrica = PPedidos.Fabrica " +
			" LEFT JOIN Complemento AS comp ON ppedidos.idcomplemento = comp.idComplemento\n" +
			" LEFT JOIN Productos AS ProdPadre ON ProdPadre.codigo = comp.codigo AND ProdPadre.Fabrica = comp.Fabrica\n" +
			" LEFT JOIN Empleados ON Empleados.Usuario = Cl.Vendedor  \n" +
			" INNER JOIN DoctosR ON DoctosR.Folio = P.DoctoR \n" +
			" WHERE P.CPedido='"+cpedido+"'";
			log.info(sql);
			return super.jdbcTemplate.query(sql, new PDFConfirmacionPedidoRowMapper());
		}catch (Exception e){
			log.info("Error: "+e.getMessage());
			//System.err.println("Ocurrió un error inesperado: " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

}
