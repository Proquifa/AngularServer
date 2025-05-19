/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.proquifa.net.modelo.compras.PartidaCompra;
import com.proquifa.net.modelo.comun.util.Funcion;
import com.proquifa.net.modelo.ventas.PartidaPedido;
import com.proquifa.net.persistencia.DataBaseDAO;
import com.proquifa.net.persistencia.compras.PartidaCompraDAO;
import com.proquifa.net.persistencia.compras.impl.mapper.CompraOrdenRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.PCompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.PartidaCompraHistorialRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.PartidaCompraRowMapper;
import com.proquifa.net.persistencia.compras.impl.mapper.PartidaReporteRowMapper;
import com.proquifa.net.persistencia.ventas.PartidaPedidoDAO;

/**
 * @author sarivera
 *
 */
@Repository
public class PartidaCompraDAOImpl  extends DataBaseDAO  implements PartidaCompraDAO  {
	
	Funcion funcion;
	
	@Autowired
	PartidaPedidoDAO partidaPedidoDAO;
	
	final Logger log = LoggerFactory.getLogger(PartidaCompraDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<PartidaCompra> obtenerPartidasCompraXPeriodo(Long idProveedor,
			String estado, String periodo) {
		String query = "select pcompras.idPCompra, pcompras.estado, pcompras.cant, pcompras.compra, pcompras.cpedido, pcompras.fPharmaUE, pcompras.fpharma, pcompras.fProquifa, pcompras.facturaP, pcompras.codigo, pcompras.fabrica , compras.AlmacenUE, compras.AlmacenUSA, " +
				"compras.AlmacenMatriz, pcompras.idComplemento, clientes.nombre as nombreCliente,pcompras.origen from PCompras, compras, clientes, pedidos where pcompras.Compra = compras.Clave and pcompras.Estado LIKE '%" + estado + "%' AND Fecha >= '" + periodo  + "' AND compras.Provee = " + idProveedor + 
				" AND compras.Estado <> 'Cancelada' AND (pcompras.FPharma IS NOT NULL OR  pcompras.FPharmaUE IS NOT NULL OR pcompras.FProquifa IS NOT NULL) AND Clientes.Clave = pedidos.idCliente AND pcompras.CPedido = pedidos.CPedido " +
				"UNION " +
				"select pcompras.idPCompra, pcompras.estado, pcompras.cant, pcompras.compra, pcompras.cpedido, pcompras.fPharmaUE, pcompras.fpharma, pcompras.fProquifa, pcompras.facturaP, pcompras.codigo, pcompras.fabrica , compras.AlmacenUE, compras.AlmacenUSA, " +
				"compras.AlmacenMatriz, pcompras.idComplemento, '' as NombreCliente,pcompras.origen from PCompras, compras, pedidos where pcompras.Compra = compras.Clave and pcompras.Estado LIKE '%" + estado + "%' AND Fecha >= '" + periodo  + "' AND compras.Provee = " + idProveedor + 
				"AND compras.Estado <> 'Cancelada' AND (pcompras.FPharma IS NOT NULL OR  pcompras.FPharmaUE IS NOT NULL OR pcompras.FProquifa IS NOT NULL) AND pcompras.CPedido = pedidos.CPedido AND pedidos.idCliente is null ";
		return super.jdbcTemplate.query(query, new PCompraRowMapper());
	}

	@SuppressWarnings("unchecked")
	public List<PartidaCompra> obtenerPartidasCompraRechazadas(
			Long idProveedor, String periodo) {
		String query = "select compra, codigo, fabrica, tipo, pcompras.origen from pcompras, Pendiente, Compras where Fecha >= '" + periodo + "' " +
				"AND Tipo like '%Rechazo por%' and Pendiente.Docto = cast(PCompras.idPCompra as varchar(15))  and PCompras.Compra = Compras.Clave and Compras.Provee = " + idProveedor  +" ";
		return super.jdbcTemplate.query(query, new PartidaReporteRowMapper());	
	}
	
	public Integer obtenerCantidadPiezas(String compra, String estado) {
		String query = "";
		if(estado.equalsIgnoreCase("")){
			query = "SELECT SUM(cant) AS totalPiezas FROM PCompras WHERE Compra = '" + compra + "' AND estado <> 'Cancelada' AND estado <> 'A destrucci������������������n'";
		}else{
			query = "SELECT SUM(cant) AS totalPiezas FROM PCompras WHERE Compra = '" + compra + "' AND Estado = '"+ estado + "'";
		}
		return super.queryForInt(query);
	}

	@SuppressWarnings("unchecked")
	public List <PartidaCompra> obtenerOrdendeCompraXCpedidoyPartida(String cPedido, Integer Part, Long idPPedido){
		try{
			String query = "SELECT InspeccionOC.Pedimento, InspeccionOC.Lote, PCompras.idPCompra , PCompras.Codigo ,Compra ,Partida FROM InspeccionOC, PCompras " +
							"WHERE PCompras.CPedido ='" + cPedido + "' AND PCompras.PPedido='" + Part + "' AND PCompras.CPedido=PCompras.CPedido AND InspeccionOC.idPCompra=PCompras.idPCompra " +  
							"UNION " +
							"SELECT InspeccionOC.Pedimento, InspeccionOC.Lote, PCompras.idPCompra , PCompras.Codigo , " +
							"Compra ,Partida FROM MovimientoStock, PPedidos, Stock, PCompras, InspeccionOC " +
							"WHERE PPedidos.CPedido='" + cPedido + "' AND MovimientoStock.idPPedido='" + idPPedido + "' AND ppedidos.part='" + Part + "' AND PPedidos.idPPedido=MovimientoStock.idPPedido	" +
							"AND MovimientoStock.idStock=Stock.idStock AND stock.idPCompra=PCompras.idPCompra AND PCompras.idPCompra=InspeccionOC.idPCompra order by idPCompra desc";
			
			return super.jdbcTemplate.query(query, new CompraOrdenRowMapper());
		}catch (RuntimeException e) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List <PartidaCompra> obtenerFolioNT(String cPedido){
		try{
			String query = "SELECT DISTINCT  ph.fecha AS fechaCierre,ph.FolioNT,pc.Compra,pc.Partida,pc.origen from PCompras as pc, PCompraHistorial as ph " + 
							"where pc.CPedido = '" + cPedido + "'  and ph.idCompra = pc.Compra and ph.idPCompra = pc.Partida and ph.FolioNT is not null ";
			return super.jdbcTemplate.query(query, new PartidaCompraHistorialRowMapper());
		}catch (RuntimeException e) {
			return null;
		}
	}

	public Boolean actualizarPCompra(PartidaCompra pc)  {
		try {
			String query = "";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cant", pc.getCantidadCompra());
			map.put("estado", pc.getEstado());
			map.put("fpharma", pc.getFechaRealArriboUSA());
			map.put("fproquifa", pc.getFechaRealArriboPQF());
			map.put("control", pc.getControl());
			map.put("porCancelar", pc.getPorCancelar());
			map.put("folioNt", pc.getFolioNT());
			map.put("pagada", pc.getPagada());
			map.put("bloqueadaAC", pc.getBloqueadaAC());
			map.put("FPharmaUE", pc.getFechaRealArriboUE());
			map.put("edoCliente", pc.getEdoCliente());
			map.put("feEntrega", pc.getFechaEstimadaEntrega());
			map.put("ac", pc.getACambio());
			map.put("backOrder", pc.getBackOrder());
			map.put("comentarios", pc.getComentarios());
			map.put("folioInspeccion", pc.getFolioInspeccion());
			map.put("folioInspeccionPhs", pc.getFolioInspeccionPHS());
			map.put("folioInspeccionUE", pc.getFolioInspeccionUE());
			map.put("comentariosPhs", pc.getComentariosPHS());
			map.put("anaquelNum", pc.getAnaquelNumero());
			map.put("anaquelLetra", pc.getAnaquelLetra());
			map.put("ubicacion", pc.getUbicacion());
			map.put("porEnterarse", pc.getPorEnterarse());
			map.put("lote", pc.getLote());
			map.put("origen", pc.getOrigen());
			map.put("codigoQr", pc.getCodQrBolsa());
			map.put("idPCompra", pc.getIdPartidaCompra());
			
			
			query = "UPDATE PCompras SET Cant=:cant ,Estado=:estado,FPharma=:fpharma,FProquifa =:fproquifa,Control=:control,PorCancelar=:porCancelar,FolioNT=:folioNt,"+

				"Pagada=:pagada,BloqueadaAC=:bloqueadaAC,FPharmaUE=:FPharmaUE,EdoCliente=:edoCliente,FEEntrega=:feEntrega,AC=:ac,BackOrder=:backOrder,Comentarios=:comentarios,"+
				"FolioInspeccion=:folioInspeccion,FolioInspeccionPhs=:folioInspeccionPhs,FolioInspeccionUE=:folioInspeccionUE,ComentariosPhs=:comentariosPhs,AnaquelNum=:anaquelNum,AnaquelLetra=:anaquelLetra,"+
				"Ubicacion=:ubicacion,PorEnterarse=:porEnterarse, Lote=:lote,origen=:origen, BolsaInspeccion=:codigoQr WHERE idPCompra=:idPCompra";
				
				log.info(query);
				super.jdbcTemplate.update(query,map);
	
			
		
			return true;
		} catch (Exception e) {
			log.info("Error: " + e.getMessage());
			return false;
		}
	}
	

	
	public boolean actualizarCostoPCompra(long idPCompra, double costoNuevo){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);
			map.put("costoNuevo", costoNuevo);
			super.jdbcTemplate.update("UPDATE PCompras SET Costo = :idPCompra  WHERE idPCompra = :costoNuevo ",map);			
			return true;
		}catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public PartidaCompra getPCompraXid(Long idPCompra) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("idPCompra", idPCompra);

			return (PartidaCompra) super.jdbcTemplate.queryForObject("SELECT *, Prod.Control AS ControlProducto FROM PCompras AS PC LEFT JOIN (SELECT * FROM Productos) AS Prod ON Prod.Codigo=pc.Codigo AND Prod.Fabrica=PC.Fabrica WHERE PC.idPCompra= :idPCompra",map, new PartidaCompraRowMapper());
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}		
	}

	public long insertarPCompra(PartidaCompra pcompra) {		
		try{
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compra", pcompra.getCompra());
			map.put("partida", pcompra.getPartida());
			map.put("cpedido", pcompra.getPedido());
			map.put("partidaPedido", pcompra.getPartidaPedido());
			map.put("cant", pcompra.getCantidadCompra());
			map.put("codigo", pcompra.getCodigo());
			map.put("fabrica", pcompra.getFabrica());
			map.put("costo", pcompra.getCosto());
			map.put("estado", pcompra.getEstado());
			map.put("factura", pcompra.getFactura());
			map.put("fpharma", pcompra.getFechaRealArriboUSA());
			map.put("fproquifa", pcompra.getFechaRealArriboPQF());
			map.put("control", pcompra.getControl());
			map.put("folioNT", pcompra.getFolioNT());
			map.put("idComplemento", pcompra.getIdComplemento());
			map.put("bloqueadaAC", pcompra.getBloqueadaAC());
			map.put("edoCliente", pcompra.getEdoCliente());
			map.put("feentrega", pcompra.getFechaEstimadaEntrega());
			map.put("ac", pcompra.getACambio());
			map.put("backOrder", pcompra.getBackOrder());
			map.put("comentarios", pcompra.getComentarios());
			map.put("pedido", 0);
			map.put("cotiza", pcompra.getCotizacion());
			map.put("ubicacion", pcompra.getUbicacion());
			map.put("folioInspeccionPhs", pcompra.getFolioInspeccionPHS());
			map.put("origen", pcompra.getOrigen());
			map.put("lote", pcompra.getLote());
			map.put("fk02_Compra", pcompra.getIdCompra());
			map.put("fk03_PPedido", pcompra.getIdPpedido());
			map.put("fk01_Producto", pcompra.getIdProducto());
			map.put("codigoQR", pcompra.getCodQrBolsa());
			
			log.info ("insertarPCompra.idPPedido" + pcompra.getIdPpedido());
			
			String query = "INSERT INTO PCompras(Compra,Partida,CPedido,PPedido,Cant,Codigo,Fabrica,Costo,Estado,Factura,FPharma,FProquifa,"+
						   "Control,FolioNT,idComplemento,BloqueadaAC,EdoCliente,FEEntrega,AC,BackOrder,Comentarios,Pedido,Cotiza,Ubicacion,FolioInspeccionPhs,origen, Lote, FK02_Compra, FK03_PPedido, FK01_Producto, BolsaInspeccion) VALUES ";
			query+= "(:compra,:partida,:cpedido,:partidaPedido,:cant,:codigo,:fabrica,:costo,"
					+ ":estado,:factura,:fpharma,:fproquifa,:control,:folioNT,:idComplemento,:bloqueadaAC,:edoCliente,:feentrega,:ac,:backOrder,:comentarios,:pedido,:cotiza,:ubicacion,:folioInspeccionPhs,:origen,:lote,:fk02_Compra,:fk03_PPedido, :fk01_Producto, :codigoQR)";
			super.jdbcTemplate.update(query, map);			
			long idPCompra = super.queryForLong("SELECT IDENT_CURRENT ('PCompras')");
			log.info("nueva idPCompra " + idPCompra);
			return idPCompra;
		}catch (Exception rte) {
			rte.printStackTrace();
			return -1L;
		}
	}

	public Integer getMaxPcompra(String compra) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("compra", compra);
			return super.queryForInt("SELECT MAX(Partida)+1 AS Partida FROM PCompras WHERE Compra = :compra",map);
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	public PartidaCompra copiarPCompra(PartidaCompra pcompra) {
		try {
			PartidaCompra nueva = new PartidaCompra();
			nueva.setIdPartidaCompra(pcompra.getIdPartidaCompra());
			nueva.setCompra(pcompra.getCompra());
			nueva.setPartida(pcompra.getPartida());
			nueva.setPedido(pcompra.getPedido());
			nueva.setPartidaPedido(pcompra.getPartidaPedido());
			nueva.setCantidadCompra(pcompra.getCantidadCompra());
			nueva.setCodigo(pcompra.getCodigo());
			nueva.setFabrica(pcompra.getFabrica());
			nueva.setCosto(pcompra.getCosto());
			nueva.setEstado(pcompra.getEstado());
			nueva.setFactura(pcompra.getFactura());
			nueva.setFechaRealArriboUSA(pcompra.getFechaRealArriboUSA());
			nueva.setFechaRealArriboPQF(pcompra.getFechaRealArriboPQF());
			nueva.setControl(pcompra.getControl());
			nueva.setPorCancelar(pcompra.getPorCancelar());
			nueva.setFolioNT(pcompra.getFolioNT());
			nueva.setIdComplemento(pcompra.getIdComplemento());
			nueva.setPagada(pcompra.getPagada());
			nueva.setFechaEstimadaEntrega(pcompra.getFechaEstimadaEntrega());
			nueva.setBackOrder(pcompra.getBackOrder());
			nueva.setComentarios(pcompra.getComentarios());
			nueva.setFolioInspeccion(pcompra.getFolioInspeccion());
			nueva.setFolioInspeccionPHS(pcompra.getFolioInspeccionPHS());
			nueva.setFolioInspeccionUE(pcompra.getFolioInspeccionUE());
			nueva.setCotizacion(pcompra.getCotizacion());
			nueva.setComentariosPHS(pcompra.getComentariosPHS());
			nueva.setAnaquelNumero(pcompra.getAnaquelNumero());
			nueva.setAnaquelLetra(pcompra.getAnaquelLetra());
			nueva.setUbicacion(pcompra.getUbicacion());
			nueva.setPorEnterarse(pcompra.getPorEnterarse());
			nueva.setFechaRealArriboUE(pcompra.getFechaRealArriboUE());
			nueva.setOrigen(pcompra.getOrigen());
			nueva.setLote(pcompra.getLote());
			nueva.setIdCompra(pcompra.getIdCompra());	
			nueva.setIdProducto(pcompra.getIdProducto());
			
			return nueva;
		} catch (Exception e) {
//			logger.error("Error: " + e.getMessage());
			return null;
		}
	}
	
	

	public long dividirPCompra(PartidaCompra pcompra, Long ppedidoRelacionado) {
		try {					
			PartidaPedido pp = this.partidaPedidoDAO.getPPedidoXid(ppedidoRelacionado,true,true);
			Integer pcompraSiguiente = this.getMaxPcompra(pcompra.getCompra());
			pcompra.setIdPpedido(ppedidoRelacionado);
			pcompra.setPartida(pcompraSiguiente);
			
			long nuevoidPPcompra = this.insertarPCompra(pcompra);			
			
			return nuevoidPPcompra;
		} catch (Exception e) {
//			logger.error("Error: "+ e.getMessage());
			return 0;
		}
	}

}