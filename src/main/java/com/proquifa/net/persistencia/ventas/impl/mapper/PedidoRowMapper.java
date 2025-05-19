/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.Pedido;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class PedidoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Pedido pedido = new Pedido();
		pedido.setIdPedido(rs.getLong("idPedido"));
		pedido.setIdCliente(rs.getLong("idCliente"));
		pedido.setCpedido(rs.getString("cpedido"));
		pedido.setIdContacto(rs.getLong("idContacto"));
		pedido.setPedido(rs.getString("pedido"));
		pedido.setFecha(rs.getDate("fpedido"));
		pedido.setFechaIngreso(rs.getDate("fingreso"));
		pedido.setDoctoR(rs.getLong("doctor"));
		pedido.setParciales(rs.getBoolean("Parciales"));
		pedido.setCondicionesPago(rs.getString("cpago"));
		pedido.setMoneda(rs.getString("moneda"));
		pedido.setMonedaFactura(rs.getString("monedaFactura"));
		pedido.setIva(rs.getDouble("iva"));
		pedido.setRuta(rs.getString("ruta"));
		pedido.setZonaMensajeria(rs.getString("zonamensajeria"));
		pedido.setPais(rs.getString("pais"));
		pedido.setMapa(rs.getString("mapa"));
		pedido.setCalle(rs.getString("calle"));
		pedido.setDelegacion(rs.getString("delegacion"));
		pedido.setEstado(rs.getString("estado"));
		pedido.setCodigoPostal(rs.getString("cp"));
		pedido.setNombreFiscal(rs.getString("nombreFiscalP"));
		pedido.setRfc(rs.getString("rfcFiscalP"));
		pedido.setFacturaFiscal(rs.getString("facturfiscalP"));
		pedido.setPaisFiscal(rs.getString("paisFiscalP"));
		pedido.setEstadoFiscal(rs.getString("estadoFiscalP"));
		pedido.setDireccionFiscal(rs.getString("direccionFiscalP"));
		pedido.setDelegacionFiscal(rs.getString("delegacionFiscalP"));
		pedido.setCpFiscal(rs.getString("cpfiscalp"));
		pedido.setCotizacion(rs.getString("cotiza"));
		return pedido;
	}

}
