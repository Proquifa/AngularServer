package com.proquifa.net.persistencia.catalogos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.tableros.cliente.EntregaCatClientes;

public class DatosEntregaClienteRowMapper implements RowMapper {
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		EntregaCatClientes entrega = new EntregaCatClientes();
		
		 entrega.setIdEntregaCliente(rs.getLong("PK_FORMULARIO"));
		 entrega.setComentarios(rs.getString("Comentarios"));
		 entrega.setNum_copiasFactura(rs.getInt("NumCopias_Factura"));
		 entrega.setNum_copiasPedidos(rs.getInt("NumCopias_Pedidos"));
		 entrega.setCertificado(rs.getBoolean("Certificados"));
		 entrega.setCopiaFactura(rs.getBoolean("Copia_Factura"));
		 entrega.setCopiaPedido(rs.getBoolean("Copia_Pedido"));
		 entrega.setFacturaOriginal(rs.getBoolean("Factura_Original"));
		 entrega.setHojaSeguridad(rs.getBoolean("Hojas_Seguridad"));
		 entrega.setIdCliente(rs.getLong("FK01_Cliente"));
		 entrega.setPedidoOriginal(rs.getBoolean("Pedido_Original"));
	 
		 return entrega;
		 
	}

}
