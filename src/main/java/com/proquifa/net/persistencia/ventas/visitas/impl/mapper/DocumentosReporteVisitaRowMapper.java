package com.proquifa.net.persistencia.ventas.visitas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.DocumentoAdjunto;

import org.springframework.jdbc.core.RowMapper;

public class DocumentosReporteVisitaRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		DocumentoAdjunto doc = new DocumentoAdjunto();
		
		doc.setNombre(rs.getString("Nombre"));
		doc.setExtension(rs.getString("Ext_Archivo"));
		doc.setIdArchivoAdjunto(rs.getInt("PK_VisitaCliente"));
		
		return doc;
	}
}
