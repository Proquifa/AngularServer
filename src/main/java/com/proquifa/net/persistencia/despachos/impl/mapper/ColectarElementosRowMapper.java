package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.EmbalarPedido;

public class ColectarElementosRowMapper implements RowMapper{
	
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		EmbalarPedido embalarPedido = new EmbalarPedido();
		
		embalarPedido.setManejo(rs.getString("Manejo_Transporte"));
		embalarPedido.setFolioEtiqueta(rs.getString("FolioEtiqueta"));
		embalarPedido.setCant(rs.getInt("Cant")+"");
		embalarPedido.setUbicacion(rs.getString("Ubicacion"));
		embalarPedido.setConcepto(rs.getString("Concepto"));
		embalarPedido.setEstado(rs.getString("Estado"));
		embalarPedido.setUsuario(rs.getInt("FK03_UsuarioEmbalar"));
		embalarPedido.setIdPedido(rs.getInt("idPPedido"));
		embalarPedido.setCliente(rs.getString("Cliente"));
		embalarPedido.setContacto(rs.getString("Contacto"));
		
		return embalarPedido;
	}
	
}
