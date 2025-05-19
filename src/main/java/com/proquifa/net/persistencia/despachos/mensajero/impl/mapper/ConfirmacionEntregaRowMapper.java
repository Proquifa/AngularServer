package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.mensajero.ConfirmacionEntrega;

public class ConfirmacionEntregaRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		ConfirmacionEntrega c = new ConfirmacionEntrega();
		// TODO Auto-generated method stub
		c.setContacto(rs.getString("Titulo") + " " + rs.getString("Contacto") );
		c.setEmail(rs.getString("eMail"));
		c.setCpedido(rs.getString("CPedido"));
		c.setFactura(rs.getString("Factura"));
		c.setCantidad(rs.getInt("Cant"));
		c.setProducto( rs.getString("Codigo") + " " + rs.getString("Concepto"));
		c.setFecha(rs.getDate("fecha"));
		c.setVendedor(rs.getString("Vendedor"));
		c.setEv(rs.getString("Usuario").toLowerCase());
		c.setFechaEsperadaEntrega(rs.getDate("FPEntrega"));
//		c.setPersonaRecibio(rs.getString("Persona_Recibio") + " / " + rs.getString("Puesto_Recibio"));
		c.setPersonaRecibio(rs.getString("Persona_Recibio"));
		c.setRegistroEntrega(rs.getString("RegistroDEntrega"));
		c.setPedidoCliente(rs.getString("Pedido"));
		c.setEstadoEntrega(rs.getString("Entrega"));
		c.setTipoJustificacion(rs.getString("TipoJustificacion"));
		c.setRazonesEntrega(rs.getString("RazonesEntrega"));
		c.setIdFabricante(rs.getInt("FK02_Fabricante"));
		c.setCatalogo(rs.getString("Codigo"));
		c.setLote(rs.getString("Lote"));
		c.setTiempoEntrega(rs.getString("TIEMPO"));
		c.setCliente(rs.getString("Nombre"));
		c.setDiasAtraso(rs.getInt("DIAS"));
		
		return c;
		
	}

}
