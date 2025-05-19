package com.proquifa.net.persistencia.despachos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.proquifa.net.modelo.despachos.EmbalarPedido;

public class ObtenerFolioRowMapper implements RowMapper<EmbalarPedido> {
	@Override
	public EmbalarPedido mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EmbalarPedido embalar = new EmbalarPedido();					
		 embalar.setFolioEmpaque(rs.getString("FolioEtiqueta"));
		 embalar.setManejo(rs.getString("Manejo"));	
		 embalar.setBolsaInspeccion(rs.getString("BolsaInspeccion"));
		 embalar.setIdPedido(rs.getInt("idPPedido"));
		 embalar.setPiezas(rs.getInt("Cant"));
		 embalar.setIdEmbalarPedido(rs.getInt("PK_EmbalarPedido"));
		 embalar.setFolioTemporal(rs.getString("FolioTemporal"));
		 embalar.setVideoPartida(rs.getString("videoPartida"));
		 embalar.setRemisionar(rs.getBoolean("Remisionar"));
		 embalar.setComentariosEntrega(rs.getString("ComentariosEntrega"));
		 embalar.setCondicionAlmacenaje(rs.getString("CondicionAlmacenaje"));
		try {
			 embalar.setFee(rs.getString("fee"));
			 embalar.setGuiaCliente(rs.getInt("GuiaCliente"));
			 embalar.setPedido(rs.getInt("idPedido"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return embalar;
	}	
}
