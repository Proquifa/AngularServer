/**
 * 
 */
package com.proquifa.net.persistencia.despachos.mensajero.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.comun.Documento;

/**
 * @author vromero
 *
 */
public class DocumentosDeRutaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Documento documento = new Documento();
		documento.setFolio(rs.getString("idDP"));
		if( documento.getFolio().lastIndexOf("ET-") < 0 ){
			documento.setTipo("Documento");
		}else{
			documento.setTipo("Producto");
		}
		
		documento.setEvento(rs.getString("Evento"));
		documento.setDestino(rs.getString("Destino"));
		return documento;
	}

}
