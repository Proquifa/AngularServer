/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.Parametro;
import com.proquifa.net.modelo.comun.excepciones.ProquifaNetException;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.ventas.PartidaPedidoDAO;
import com.proquifa.net.persistencia.ventas.impl.mapper.PedidoPartidaRowMapper;

/**
 * @author fmartinez
 *
 */
@Repository
public class PartidaPedidoDAOImpl extends DataBaseDAO implements PartidaPedidoDAO {

	final Logger log = LoggerFactory.getLogger(PartidaPedidoDAOImpl.class);
	
	Funcion f;
	/* (non-Javadoc)
	 * @see mx.com.proquifa.proquifanet.persistencia.ventas.PCotizaHistorialDAO#obtenerPCotizaHistorial(mx.com.proquifa.proquifanet.modelo.ventas.PartidaCotizacion)
	 */
	public Boolean actualizarPPedido(PartidaPedido pp) {
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cpedido", pp.getCpedido());
			map.put("part", pp.getPart());
			map.put("cantidadPedida", pp.getCantidadPedida());
			map.put("guia", pp.getGuia());
			map.put("faltan", pp.getFaltan());
			map.put("fpEntrega",pp.getFPEntrega());
			map.put("comenta",pp.getComenta());
			map.put("pedLote",pp.getPedLote());
			map.put("faltaPago",pp.getFaltaPago());
			map.put("stock",pp.getStock());
			map.put("tipoFlete",pp.getTipoFlete());
			map.put("trafico",pp.getTrafico());
			map.put("idEvento",pp.getIdEvento());
			map.put("mesCaducidad",pp.getMesCaducidad());
			map.put("anoCaducidad",pp.getAnoCaducidad());
			map.put("lote",pp.getLote());
			map.put("edicion",pp.getEdicion());
			map.put("reasignada",pp.getReasignada());
			map.put("bloqueadaAC",pp.getBloqueadaAC());
			map.put("ac",pp.getAc());
			map.put("estado",pp.getEstado());
			map.put("idPedido",pp.getIdPPedido());
			
			super.jdbcTemplate.update("UPDATE PPedidos SET CPedido= :cpedido, Part= :part, Cant= :cantidadPedida, Guia= :guia, Faltan= :cantidadPedida, FPEntrega= :fpEntrega, Comenta= :comenta, PedLote= :pedLote, FaltaPago= :faltaPago, "+
					"Stock= :stock, Tipo= :tipoFlete, Trafico= :trafico, idEvento= :idEvento, MesCaducidad= :mesCaducidad, AnoCaducidad= :anoCaducidad, Lote= :lote, Edicion= :edicion, Reasignada= :reasignada, BloqueadaAC= :bloqueadaAC, AC=ac, Estado= :estado WHERE idPPedido= :idPedido",map);
			return true;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return false;
		}
	}

	public Boolean insertRegistroHistorialPartidaPedidos(Long idPPedido,
			String tipo, String concepto, Date fecha) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPedido", idPPedido);
			map.put("fecha", fecha);
			map.put("tipo", tipo);
			map.put("concepto", concepto);
			
			String query = "INSERT INTO PartidaPedidoHistorial (FK01_PPedido, Fecha, Tipo, Concepto) VALUES ";
			query += "(:idPedido, :fecha, :tipo, :concepto) ";
			
			super.jdbcTemplate.update(query, map);
		}catch(Exception e){

		}

		return true;
	}

	public String getOrigen(Long idPPedido) {
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPPedido", idPPedido);
			String query = "SELECT TOP 1 Concepto FROM PartidaPedidoHistorial WHERE (Tipo='Cambio Proforma' OR Tipo = 'Regresa a Carro') AND FK01_PPedido = :idPPedido  order by PK_PartidaPedidoHistorial DESC" ;
			int hay = super.queryForInt("SELECT COUNT(*) AS TOTAL FROM PartidaPedidoHistorial WHERE (Tipo='Cambio Proforma' OR Tipo = 'Regresa a Carro') AND FK01_PPedido = :idPPedido",map);
			if(hay == 0){
				return null;
			}else{
				Object o = super.jdbcTemplate.queryForObject(query,map, String.class);
				return (String)o;
			}
		}catch(RuntimeException e){
//			logger.error(e.getMessage());
			return null;

		}
	}

	@SuppressWarnings("unchecked")
	public PartidaPedido getPPedidoXid(Long idPPedido, Boolean tipoFlete, Boolean compras) {
		try {
			f = new Funcion();
			Map<String, Object> map = new HashMap<String, Object>();
			
			String query = generarConsultaPPedido("",idPPedido,tipoFlete, compras);
			return (PartidaPedido) super.jdbcTemplate.queryForObject(query, map, new PedidoPartidaRowMapper());
		} catch (Exception e) {

			return null;
		}
	}
	@SuppressWarnings("unchecked")
	public PartidaPedido getPPedidoXCPedidoIdPPedido(String cpedido, Long idPPedido, Boolean tipoFlete) {
		try {
			f = new Funcion();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cpedido", cpedido);
			map.put("idPPedido", idPPedido);
			map.put("tipoFlete", tipoFlete);
			String query = generarConsultaPPedido(cpedido,idPPedido,tipoFlete, false);

			return (PartidaPedido) super.jdbcTemplate.queryForObject(query, map, new PedidoPartidaRowMapper());
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public List<PartidaPedido> findPartidasXCPedido(String cpedido, Boolean tipoFlete) throws ProquifaNetException {
		try {
			//String query = generarConsultaPPedido(cpedido,0L,tipoFlete);

			f = new Funcion();
			String query = "SELECT pp.*,"+ f.sqlDescripcionProductos("prod","pp")+" as Descripcion, CASE WHEN P.Moneda = 'M.N.' THEN PP.Precio / MON.PDolar WHEN P.Moneda = 'EUR' THEN PP.Precio * MON.EDolar ELSE PP.Precio END AS PrecioUDLS,p.Moneda,(CASE WHEN pp.idComplemento=0 THEN prod.Tipo ELSE sup.Tipo END) TipoProducto," +
					" \n (CASE WHEN pp.idComplemento=0 THEN prod.Control ELSE sup.Control END) ControlProducto,(CASE WHEN pp.idComplemento=0 THEN prod.Manejo ELSE sup.Manejo END) ManejoProducto," +
					" \n COALESCE(pp.Tipo, 'Regular') AS tipoFlete," +
					" \n (SELECT DATEDIFF(D,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE()),GETDATE()) - DATEDIFF(week,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE()),GETDATE()) * 2  - CASE WHEN DATENAME(dw,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE())) <> 'Saturday' " +
					" \n AND DATENAME(dw,GETDATE()) = 'Saturday' THEN 1 WHEN DATENAME(dw,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE())) = 'Saturday' AND DATENAME(dw,GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) AS diasEnAlmacen," +
					" \n CAST(CASE WHEN pp.Stock = 1 THEN 'Stock' ELSE 'Cliente' END AS VARCHAR(10)) As Destino," +
					" \n (CASE WHEN pp.FRecibido IS NULL THEN 'FD-' + pp.CPedido + '-' + CAST(pp.Part AS Varchar(10)) ELSE folioFD.Folio END) AS FolioFD, " +
					" \n (SELECT DATEDIFF(DAY, COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin),GETDATE()) -  DATEDIFF(WEEK,COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin),GETDATE()) * 2 - CASE WHEN DATENAME(DW, COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin)) <> 'Saturday' AND DATENAME(DW, GETDATE()) = 'Saturday'  THEN 1 WHEN DATENAME(DW, COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin)) = 'Saturday' AND DATENAME(DW, GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) AS diferencial " +
					" \n , COALESCE(PC.FProquifa,EDOPCSTOCK.FProquifa,folioFD.FProquifa, EDOPC.FProquifa) fea " +
					" \n FROM PPedidos AS pp " +
					" \n LEFT JOIN(SELECT (CASE WHEN Moneda='Pesos' THEN 'M.N.' WHEN Moneda='Dolares' THEN 'USD' WHEN Moneda='Euros' THEN 'EUR' WHEN Moneda='Libras' THEN 'LB' END) AS Moneda,CPedido,FPedido FROM Pedidos) AS p ON p.CPedido=pp.CPedido " +
					" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON CONVERT(varchar(10),mon.Fecha,112)=CONVERT(varchar(10),p.FPedido,112) " +
					" \n LEFT JOIN(SELECT * FROM Productos) AS prod ON prod.idProducto=pp.FK02_Producto " +
					" \n LEFT JOIN(SELECT Complemento.idComplemento,Productos.Tipo,Productos.Control,Productos.Manejo FROM Productos,Complemento WHERE Productos.Codigo=Complemento.Codigo AND Productos.Fabrica=Complemento.Fabrica) AS sup ON sup.idComplemento=pp.idComplemento " +
					" \n LEFT JOIN(SELECT ('FD-' + PCompras.CPedido + '-' + CAST(PCompras.PPedido AS Varchar(10))) AS Folio,PPedidos.CPedido AS CPedidoOrigen,PPedidos.Part AS PartOrigen,PCompras.CPedido AS CPedidoDestino,PCompras.PPedido AS PartDestino, PCompras.FProquifa " +
					" \n FROM PCompras,Stock,MovimientoStock,PPedidos WHERE PCompras.idPCompra=Stock.idPCompra AND Stock.idStock=MovimientoStock.idStock AND MovimientoStock.idPPedido=PPedidos.idPPedido) AS folioFD ON folioFD.CPedidoOrigen=pp.CPedido AND folioFD.PartOrigen=pp.Part " +
					" \n LEFT JOIN(SELECT EstadoPCompra.FInicio,EstadoPCompra.FFin,PCompras.CPedido,PCompras.PPedido, PCompras.FProquifa FROM EstadoPCompra,PCompras WHERE EstadoPCompra.Tipo='En inspección' AND EstadoPCompra.idPCompra=PCompras.idPCompra) AS EDOPC ON EDOPC.CPedido=pp.CPedido AND EDOPC.PPedido=pp.Part " +
					" \n LEFT JOIN(SELECT EstadoPCompra.FInicio,EstadoPCompra.FFin,PCompras.CPedido,PCompras.PPedido, PCompras.FProquifa FROM EstadoPCompra,PCompras WHERE EstadoPCompra.Tipo='En inspección' AND EstadoPCompra.idPCompra=PCompras.idPCompra) AS EDOPCSTOCK ON EDOPCSTOCK.CPedido=folioFD.CPedidoDestino " +
					" \n AND EDOPCSTOCK.PPedido=folioFD.PartDestino " +
					" \n LEFT JOIN PCompras PC ON PC.CPedido = PP.CPedido AND PC.PPedido = PP.Part " +
					" \n WHERE (PC.Estado<>'Cancelada' OR (PC.ESTADO IS NULL AND (PP.Estado='Despachable' OR PP.Estado='A destrucción' OR PP.Estado='No entregado' OR PP.Estado='En asignacion' " +
					" \n 			OR PP.Estado='A programacion' OR PP.Estado='A facturacion' OR PP.Estado='En entrega' OR PP.Estado='STOCK' OR PP.Estado='En inspección' OR PP.Estado='Pedido' OR PP.Estado ='A gestion pago'))) AND " +
					" \n pp.CPedido='"+ cpedido +"'";			
 
			if(!tipoFlete){
				query+=" AND ( COALESCE(prod.Tipo,sup.Tipo)<>'Fletes' OR COALESCE(prod.Tipo,sup.Tipo) IS NULL ) ";
			}

//			logger.info(query);
			return super.jdbcTemplate.query(query, new PedidoPartidaRowMapper());
		} catch (Exception e) {
//			logger.error("Error: "+ e.getMessage());
			return null;
		}
	}

	public Integer getMaxPPedido(String cpedido) {
		try {
			return super.queryForInt("SELECT MAX(Part)+1 AS Partida FROM PPedidos WHERE CPedido = '"+ cpedido +"'");
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}

	public long insertarPPedido(PartidaPedido ppedido) {
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("idPPedido", ppedido.getIdPPedido());
			map.put("cpedido", ppedido.getCpedido());
			map.put("part", ppedido.getPart());
			map.put("clave", 0);
			map.put("cant", ppedido.getCantidadPedida());
			map.put("codigo", ppedido.getCodigo());
			map.put("precio", ppedido.getPrecio());
			map.put("concepto", ppedido.getConcepto());
			map.put("estado", ppedido.getEstado());
			map.put("costo", ppedido.getCosto());
			map.put("fabrica", ppedido.getFabrica());
			map.put("provee", ppedido.getProvee());
			map.put("factura", ppedido.getFactura());
			map.put("faltan", ppedido.getFaltan());
			map.put("fpEntrega", ppedido.getFPEntrega());
			map.put("stock", ppedido.getStock());
			map.put("tipo", ppedido.getTipoFlete());
			map.put("idComplemento", ppedido.getIdComplemento());
			map.put("trafico", ppedido.getTrafico());
			map.put("mesCaducidad", ppedido.getMesCaducidad());
			map.put("anoCaducidad", ppedido.getAnoCaducidad());
			map.put("lote", ppedido.getLote());
			map.put("edicion", ppedido.getEdicion());
			map.put("reasignada", ppedido.getReasignada());
			map.put("bloqueadaAC", ppedido.getBloqueadaAC());
			map.put("ac", ppedido.getAc());
			map.put("cotiza", ppedido.getCotiza());
			map.put("ffactura", ppedido.getFFactura());
			map.put("fk02_Producto", ppedido.getIdProducto());
			map.put("lineaOrden", ppedido.getLineaOrden());
			map.put("unidadMedida", ppedido.getUnidadMedida());
		
			String query = "INSERT INTO PPedidos (CPedido,Part,Clave,Cant,Codigo,Precio,Concepto,Estado,Costo,Fabrica," +
					"Provee,Factura,Faltan,FPEntrega,Stock,Tipo,idComplemento,Trafico,MesCaducidad,AnoCaducidad," +
					"Lote,Edicion,Reasignada,BloqueadaAC,AC,Cotiza,FFactura,FK02_Producto,LineaDeOrden,UnidadMedida) VALUES";
			
			query+= "(:cpedido,:part,:clave,:cant,:codigo,:precio,:concepto,:estado,:costo,:fabrica,:provee,:factura,:faltan,:fpEntrega,:stock,:tipo,:idComplemento,:trafico,:mesCaducidad,:anoCaducidad,:lote,:edicion,:reasignada,:bloqueadaAC,:ac,:cotiza,:ffactura,:fk02_Producto,:lineaOrden,:unidadMedida)";
			int r = super.jdbcTemplate.update(query, map);
			
			if(r > 0){
				log.info(query);
				long idPPedido = super.queryForLong("SELECT IDENT_CURRENT ('PPedidos')");
				log.info("***idppedido: "+ idPPedido);
				return idPPedido;
			}else{
				return -1;
			}
		}catch (Exception e) {
//			logger.error(e.getMessage());
			log.info(e.getMessage());
			return -1L;
		}
	}

	public PartidaPedido copiarPPedido(PartidaPedido ppedido)
			throws ProquifaNetException {
		try {
			PartidaPedido nueva = new PartidaPedido();
			nueva.setIdPPedido(ppedido.getIdPPedido());
			nueva.setCpedido(ppedido.getCpedido());
			nueva.setPart(ppedido.getPart());
			nueva.setClave(ppedido.getClave());
			nueva.setCantidadPedida(ppedido.getCantidadPedida());
			nueva.setCodigo(ppedido.getCodigo());
			nueva.setPrecio(ppedido.getPrecio());
			nueva.setConcepto(ppedido.getConcepto());
			nueva.setEstado(ppedido.getEstado());
			nueva.setCosto(ppedido.getCosto());
			nueva.setFabrica(ppedido.getFabrica());
			nueva.setProvee(ppedido.getProvee());
			nueva.setFFactura(ppedido.getFFactura());
			nueva.setFactura(ppedido.getFactura());
			nueva.setFRecibido(ppedido.getFRecibido());			
			nueva.setFEntrega(ppedido.getFEntrega());
			nueva.setGuia(ppedido.getGuia());
			nueva.setFaltan(ppedido.getFaltan());
			nueva.setFPEntrega(ppedido.getFPEntrega());
			nueva.setComenta(ppedido.getComenta());
			nueva.setPedLote(ppedido.getPedLote());
			nueva.setStock(ppedido.getStock());
			nueva.setTipoFlete(ppedido.getTipoFlete());
			nueva.setLote(ppedido.getLote());
			nueva.setIdComplemento(ppedido.getIdComplemento());
			nueva.setIdEvento(ppedido.getIdEvento());
			nueva.setReasignada(ppedido.getReasignada());
			nueva.setCotiza(ppedido.getCotiza());
			nueva.setIdProducto(ppedido.getIdProducto());
			try {
				nueva.setUnidadMedida(ppedido.getUnidadMedida());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				nueva.setLineaOrden(ppedido.getLineaOrden());
			} catch (Exception e) {
				// TODO: handle exception
			}

			return nueva;
		} catch (Exception e) {
			return null;
		}
	}

	public long dividirPPedido(PartidaPedido ppedido)
			throws ProquifaNetException {
		try {
			PartidaPedido pp = copiarPPedido(ppedido);
			
			Integer ppedidoSiguiente = this.getMaxPPedido(ppedido.getCpedido());
			pp.setPart(ppedidoSiguiente);
			pp.setFaltan(ppedido.getCantidadPedida());

			return this.insertarPPedido(pp);
		} catch (Exception e) {
//			logger.error("Error: "+ e.getMessage());
			return 0;
		}
	}

	private String generarConsultaPPedido(String cpedido, Long iddPPedido, Boolean fletes, Boolean compras){
		f = new Funcion();
		String consulta = "SELECT top 1  pp.*,"+ f.sqlDescripcionProductos("prod","pp")+" as Descripcion, CASE WHEN P.Moneda = 'M.N.' THEN PP.Precio / MON.PDolar WHEN P.Moneda = 'EUR' THEN PP.Precio * MON.EDolar ELSE PP.Precio END AS PrecioUDLS,p.Moneda,(CASE WHEN pp.idComplemento=0 THEN prod.Tipo ELSE sup.Tipo END) TipoProducto," +
				" \n (CASE WHEN pp.idComplemento=0 THEN prod.Control ELSE sup.Control END) ControlProducto,(CASE WHEN pp.idComplemento=0 THEN prod.Manejo ELSE sup.Manejo END) ManejoProducto," +
				" \n COALESCE(pp.Tipo, 'Regular') AS tipoFlete," +
				" \n (SELECT DATEDIFF(D,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE()),GETDATE()) - DATEDIFF(week,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE()),GETDATE()) * 2  - CASE WHEN DATENAME(dw,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE())) <> 'Saturday' " +
				" \n AND DATENAME(dw,GETDATE()) = 'Saturday' THEN 1 WHEN DATENAME(dw,COALESCE(EDOPC.FInicio,EDOPCSTOCK.FInicio,GETDATE())) = 'Saturday' AND DATENAME(dw,GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) AS diasEnAlmacen," +
				" \n CAST(CASE WHEN pp.Stock = 1 THEN 'Stock' ELSE 'Cliente' END AS VARCHAR(10)) As Destino," +
				" \n (CASE WHEN pp.FRecibido IS NULL THEN 'FD-' + pp.CPedido + '-' + CAST(pp.Part AS Varchar(10)) ELSE folioFD.Folio END) AS FolioFD, " +
				" \n (SELECT DATEDIFF(DAY, COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin),GETDATE()) -  DATEDIFF(WEEK,COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin),GETDATE()) * 2 - CASE WHEN DATENAME(DW, COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin)) <> 'Saturday' AND DATENAME(DW, GETDATE()) = 'Saturday'  THEN 1 WHEN DATENAME(DW, COALESCE(EDOPC.FFin,EDOPCSTOCK.FFin)) = 'Saturday' AND DATENAME(DW, GETDATE()) <> 'Saturday' THEN -1 ELSE 0 END) AS diferencial " +
				" \n , COALESCE(PC.FProquifa,EDOPCSTOCK.FProquifa,folioFD.FProquifa, EDOPC.FProquifa) fea " +
				" \n FROM PPedidos AS pp " +
				" \n LEFT JOIN(SELECT (CASE WHEN Moneda='Pesos' THEN 'M.N.' WHEN Moneda='Dolares' THEN 'USD' WHEN Moneda='Euros' THEN 'EUR' WHEN Moneda='Libras' THEN 'LB' END) AS Moneda,CPedido,FPedido FROM Pedidos) AS p ON p.CPedido=pp.CPedido " +
				" \n LEFT JOIN (SELECT * FROM Monedas) AS MON ON CONVERT(varchar(10),mon.Fecha,112)=CONVERT(varchar(10),p.FPedido,112) " +
				" \n LEFT JOIN(SELECT * FROM Productos) AS prod ON prod.idProducto=pp.FK02_Producto " +
				" \n LEFT JOIN(SELECT Complemento.idComplemento,Productos.Tipo,Productos.Control,Productos.Manejo FROM Productos,Complemento WHERE Productos.Codigo=Complemento.Codigo AND Productos.Fabrica=Complemento.Fabrica) AS sup ON sup.idComplemento=pp.idComplemento " +
				" \n LEFT JOIN(SELECT ('FD-' + PCompras.CPedido + '-' + CAST(PCompras.PPedido AS Varchar(10))) AS Folio,PPedidos.CPedido AS CPedidoOrigen,PPedidos.Part AS PartOrigen,PCompras.CPedido AS CPedidoDestino,PCompras.PPedido AS PartDestino, PCompras.FProquifa " +
				" \n FROM PCompras,Stock,MovimientoStock,PPedidos WHERE PCompras.idPCompra=Stock.idPCompra AND Stock.idStock=MovimientoStock.idStock AND MovimientoStock.idPPedido=PPedidos.idPPedido) AS folioFD ON folioFD.CPedidoOrigen=pp.CPedido AND folioFD.PartOrigen=pp.Part " +
				" \n LEFT JOIN(SELECT EstadoPCompra.FInicio,EstadoPCompra.FFin,PCompras.CPedido,PCompras.PPedido, PCompras.FProquifa FROM EstadoPCompra,PCompras WHERE EstadoPCompra.Tipo='En inspección' AND EstadoPCompra.idPCompra=PCompras.idPCompra) AS EDOPC ON EDOPC.CPedido=pp.CPedido AND EDOPC.PPedido=pp.Part " +
				" \n LEFT JOIN(SELECT EstadoPCompra.FInicio,EstadoPCompra.FFin,PCompras.CPedido,PCompras.PPedido, PCompras.FProquifa FROM EstadoPCompra,PCompras WHERE EstadoPCompra.Tipo='En inspección' AND EstadoPCompra.idPCompra=PCompras.idPCompra) AS EDOPCSTOCK ON EDOPCSTOCK.CPedido=folioFD.CPedidoDestino " +
				" \n AND EDOPCSTOCK.PPedido=folioFD.PartDestino " +
				" \n LEFT JOIN PCompras PC ON PC.CPedido = PP.CPedido AND PC.PPedido = PP.Part AND PC.ESTADO<>'Cancelada' " +
				" \n WHERE ";
		
		if(compras){
			consulta += " (PC.Estado<>'Cancelada' OR (PC.ESTADO IS NULL AND PP.ESTADO = 'STOCK'))  AND ";
		}
		
		if(!cpedido.equals("") && iddPPedido==0){
			consulta+="pp.CPedido='"+ cpedido +"'";
		}else if(cpedido.equals("") && iddPPedido!=0){
			consulta+="pp.idPPedido="+ iddPPedido +
					" \n ORDER BY fea desc ";
		}else{
			consulta+="pp.CPedido='"+ cpedido +"' AND pp.Part="+ iddPPedido +
					" \n ORDER BY fea desc ";
		}

		if(!fletes){
			consulta+=" AND ( COALESCE(prod.Tipo,sup.Tipo)<>'Fletes' OR COALESCE(prod.Tipo,sup.Tipo) IS NULL ) " +
					" \n ORDER BY fea desc ";
		}
//		logger.info(consulta);
		return consulta;
		
	}
	
	@Override
	public boolean updateEstadoxEmbalarPedido(Parametro parametro) throws ProquifaNetException {
		
		try {
			Map<String, Object> parametros = new HashMap<String, Object>();
			StringBuilder sbQuery = new StringBuilder(" \n");
			if(parametro.getEstado().equalsIgnoreCase("Embalado")) {
				String ids = "";
				if(parametro.getLista().size() > 0 && parametro.getLista() != null) {				
					for(int i = 0; i < parametro.getLista().size(); i++) {
						ids += parametro.getLista().get(i).toString()+ ',';
						if(i == parametro.getLista().size() -1 ) {
							ids +=  "0";
						}					
					}
				}
				parametros.put("estado", parametro.getEstado());
				sbQuery.append("UPDATE PP SET PP.Estado = :estado FROM PPedidos PP \n");
				sbQuery.append("INNER JOIN EmbalarPEdido PE ON PE.Fk01_PPEdido = PP.idPPEdido \n");
				sbQuery.append("WHERE PP.idPPEdido IN (" + ids + ") AND PE.Estado = :estado \n");
				super.jdbcTemplate.update(sbQuery.toString(), parametros);
			}else {
				
				sbQuery.append(" \n");
				sbQuery.append("UPDATE PP SET PP.Estado = :estado FROM PPedidos PP \n");
				sbQuery.append("INNER JOIN EmbalarPedido EP ON EP.FK01_PPEdido = PP.idPPedido \n");
				sbQuery.append("LEFT JOIN PPackingList PL ON PL.FK02_EmbalarPedido = EP.PK_EmbalarPedido \n");
				sbQuery.append("WHERE EP.FK03_UsuarioEmbalar = :idUsuario AND EP.Estado <> 'Embalado' \n");
				
				if (parametro.getIdPackingList() != null && parametro.getIdPackingList() > 0) {
					sbQuery.append("AND PL.FK01_PackingList = :idPackingList \n");
				}
				
				
				parametros.put("estado", parametro.getEstado());
				parametros.put("idUsuario", parametro.getIdUsuarioLogueado());
				parametros.put("idPackingList", parametro.getIdPackingList());
				
				jdbcTemplate.update(sbQuery.toString(), parametros);
				return true;
			}
		
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ProquifaNetException();
		}
		
	}
	
	@Override
	public boolean verificarFactura(PartidaPedido ppedido, Long idPCompra, Integer stock) throws ProquifaNetException {
		try {
			Map <String,Object> map = new HashMap<String, Object> ();
			map.put("idPCompra", idPCompra);
			map.put("Cpedido", ppedido.getCpedido());
			map.put("part", ppedido.getPart());
			String estado = estadoPFactura(idPCompra);
			if(stock != 1) {
				if(!estado.equalsIgnoreCase("")) {
					StringBuilder sbQueryPF = new StringBuilder(" \n");
					sbQueryPF.append("DECLARE @MaxPFactura AS INT \n");
					sbQueryPF.append("SELECT @MaxPFactura =  MAX(Part) FROM PFacturas WHERE CPEDIDO = (SELECT CPedido FROM PCompras where idPCompra = :idPCompra) \n");
					sbQueryPF.append("INSERT INTO PFacturas (Factura,Cant, Fabrica, Codigo, Concepto, Importe, Part, FPor, Ppedido, Cpedido, iva, FK01_FActura) \n");
					sbQueryPF.append("SELECT Factura, 1, Fabrica, Codigo, Concepto, Importe, @MaxPFactura +");sbQueryPF.append(1); sbQueryPF.append(", FPor,  :part, CPedido, iva, FK01_FActura \n");
					sbQueryPF.append("FROM PFacturas WHERE CPEDIDO = (SELECT CPEDIDO FROM PCompras WHERE IdPCompra = :idPCompra )  AND PPEDIDO = (SELECT part FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra  = :idPCompra)) \n");
					jdbcTemplate.update(sbQueryPF.toString(), map);
				}
			}
			Long facturaXpagar = null;
			StringBuilder sbEstado = new StringBuilder(" \n");
			sbEstado.append("SELECT idPFacturaXPagar FROM PFACturaxPagar WHERE idPcompra = :idPCompra \n");
			try {
				facturaXpagar = jdbcTemplate.queryForObject(sbEstado.toString(), map, Long.class);
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
				facturaXpagar = null;
			}
			
			if(facturaXpagar != null) {
				StringBuilder sbQueryPFactura = new StringBuilder(" \n");
				sbQueryPFactura.append("DECLARE @MaxCompra AS INT \n");
				sbQueryPFactura.append("SELECT @MaxCompra =  MAX(Partida) FROM PCompras WHERE Compra = (SELECT Compra FROM PCompras where idPCompra = :idPCompra) \n");
				sbQueryPFactura.append("INSERT INTO PFACTURAxPagar (idFxPagar, idPCompra, FPor) \n");
				sbQueryPFactura.append("SELECT idFxPagar, (SELECT idPCompra FROM PCOMPRAS WHERE Compra = (SELECT Compra  FROM PCompras WHERE idPCompra = :idPCompra)  AND Partida = @MaxCompra ").append("), FPor \n");
				sbQueryPFactura.append("FROM PFACturaxPagar WHERE idPCompra = :idPCompra \n");
				jdbcTemplate.update(sbQueryPFactura.toString(), map);
				
			}
		
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}
	public String estadoPFactura(Long idPCompra) throws ProquifaNetException{
		String estado = "";
		try {
			Map <String,Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			StringBuilder sbQuery = new StringBuilder(" \n");
			sbQuery.append("SELECT F.Estado FROM Facturas F \n");
			sbQuery.append("LEFT JOIN PFACTURAS  PF ON F.idFACtura = PF.FK01_Factura \n");
			sbQuery.append("WHERE PF.CPEDIDO = (SELECT CPEDIDO FROM PCompras WHERE IdPCompra = :idPCompra )  AND PF.PPEDIDO = ");
			sbQuery.append("(SELECT part FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra  = :idPCompra)) \n");
			try {
				 return estado = jdbcTemplate.queryForObject(sbQuery.toString(), map, String.class);
			} catch (Exception e) {
				// TODO: handle exception
				return estado = "";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return estado;
		}
	}
	
	@Override
	public boolean actualizarPFactura(Long idPCompra, Integer cantidad) throws ProquifaNetException{
		try {
			String stadoPF = estadoPFactura(idPCompra);
			
			if(!stadoPF.equalsIgnoreCase("")) {
				Map <String,Object> map = new HashMap<String, Object>();
				map.put("cant", cantidad);
				map.put("idPCompra", idPCompra);
				StringBuilder sbQueryPF = new StringBuilder(" \n");
				sbQueryPF.append("UPDATE PFacturas \n");
				sbQueryPF.append("SET Cant = :cant WHERE CPEDIDO = (SELECT CPEDIDO FROM PCompras WHERE IdPCompra = :idPCompra )  AND PPEDIDO = (SELECT part FROM PPEDIDOS WHERE idPPedido = (SELECT FK03_PPedido FROM PCompras where idPCompra  = :idPCompra)) \n");
				jdbcTemplate.update(sbQueryPF.toString(), map);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
			// TODO: handle exception
		}
	}
	
}