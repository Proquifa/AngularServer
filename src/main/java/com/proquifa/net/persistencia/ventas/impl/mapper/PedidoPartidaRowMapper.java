package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.ventas.PartidaPedido;

public class PedidoPartidaRowMapper  implements RowMapper{
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException{
		PartidaPedido ppedido = new PartidaPedido();
		
		ppedido.setIdPPedido(rs.getLong("idPPedido"));
		ppedido.setCpedido(rs.getString("CPedido"));
		ppedido.setPart(rs.getInt("Part"));
		ppedido.setClave(rs.getLong("Clave"));
		ppedido.setCantidadPedida(rs.getInt("Cant"));
		ppedido.setCodigo(rs.getString("Codigo"));
		ppedido.setPrecio(Math.rint((rs.getDouble("Precio")) * 1e2) / 1e2);
		ppedido.setPrecioUnitarioDLS(Math.rint((rs.getDouble("PrecioUDLS")) * 1e2) / 1e2);
		ppedido.setConcepto(rs.getString("Descripcion"));
		ppedido.setEstado(rs.getString("Estado"));
		ppedido.setCosto(rs.getDouble("Costo"));
		ppedido.setFabrica(rs.getString("Fabrica"));
		ppedido.setProvee(rs.getLong("Provee"));
		ppedido.setFFactura(rs.getDate("FFactura"));
		
		try{
		ppedido.setFactura(rs.getLong("Factura"));
		}catch(Exception e){
		
		}
		
		ppedido.setFRecibido(rs.getDate("FRecibido"));
		ppedido.setFEntrega(rs.getDate("FEntrega"));
		ppedido.setGuia(rs.getString("Guia"));
		ppedido.setFaltan(rs.getInt("Faltan"));
		ppedido.setFPEntrega(rs.getDate("FPEntrega"));
		ppedido.setComenta(rs.getString("Comenta"));
		ppedido.setPedLote(rs.getString("PedLote"));
		ppedido.setStock(rs.getBoolean("Stock"));
		ppedido.setLote(rs.getString("Lote"));
		ppedido.setIdComplemento(rs.getLong("idComplemento"));
		ppedido.setIdEvento(rs.getString("idEvento"));
		ppedido.setReasignada(rs.getBoolean("Reasignada"));
		ppedido.setCotiza(rs.getString("Cotiza"));
		ppedido.setIdProducto(rs.getLong("FK02_Producto"));		
		ppedido.setMoneda(rs.getString("Moneda"));
		ppedido.setTipo(rs.getString("TipoProducto"));
		ppedido.setControl(rs.getString("ControlProducto"));
		ppedido.setManejo(rs.getString("ManejoProducto"));
		ppedido.setTipoFlete(rs.getString("tipoFlete"));
		ppedido.setDiasEnAlmacen(rs.getInt("diasEnAlmacen"));
		ppedido.setDestinos(rs.getString("Destino"));
		ppedido.setFolioFD(rs.getString("FolioFD"));
		ppedido.setDiferencial(rs.getLong("diferencial"));
		ppedido.setFaltaPago(rs.getBoolean("FaltaPago"));
		ppedido.setAnoCaducidad(rs.getString("AnoCaducidad"));
		ppedido.setMesCaducidad(rs.getString("MesCaducidad"));
		
		try {
			ppedido.setFea(rs.getDate("fea"));
		} catch (Exception e) {
		}
		try {
			ppedido.setLineaOrden(Integer.parseInt(rs.getString("LineaDeOrden")));
		} catch (Exception e) {
			// TODO: handle exception			
		}
		try {
			ppedido.setUnidadMedida(rs.getString("UnidadMedida"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return ppedido;
	}
}