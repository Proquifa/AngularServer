package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.enviodocumentos.EnvioDocumentos;

import org.springframework.jdbc.core.RowMapper;

public class ConsultaEnvioDocumentosRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		EnvioDocumentos ed = new EnvioDocumentos();
		
	   ed.setFinicio(rs.getDate("FInicio"));
	   ed.setFfin(rs.getDate("FFin"));
	   ed.setDestino(rs.getInt("cliente"));
	   ed.setOrigen(rs.getString("Origen"));
	   ed.setTipo(rs.getString("tipoCorreo"));
	   ed.setContacto(rs.getString("Contacto"));
	   ed.setFacturaFpor(rs.getString("Facturado"));
	   ed.setProformaFpor(rs.getString("FPor"));
	   ed.setFolioDocumento(rs.getString("FolioDocumento"));
	   ed.setEstado(rs.getString("Nombre"));
	   ed.setClaveProfor(rs.getString("Clave"));
		return ed;
	}

}
