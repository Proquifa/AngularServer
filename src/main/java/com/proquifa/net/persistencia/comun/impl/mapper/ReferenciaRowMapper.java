/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.Referencia;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author amartinez
 *
 */
public class ReferenciaRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		Referencia referencia = new Referencia();
		Long idUsuario = null;
		String usuario="";
		referencia.setIdReferencia(rs.getLong("pk_referencia"));
		referencia.setIdIncidente(rs.getLong("fk01_incidente"));
		referencia.setExtensionArchivo(rs.getString("extension_archivo"));
		referencia.setFolio(rs.getString("folio"));
		referencia.setOrigen(rs.getString("origen"));
		if(rs.getString("Justificacion")==null){
			referencia.setJustificacion("N/D");	
		}else{
			referencia.setJustificacion(rs.getString("Justificacion"));
		}
		
		
		idUsuario = rs.getLong("FK01_Usuario");
		
		if(rs.wasNull()){
			referencia.setIdUsuario(null);
		}else{
			referencia.setIdUsuario(idUsuario);
		}
		usuario = rs.getString("Usuario");
		if(rs.wasNull()){
			referencia.setNombreResonsable("NA");
		}else{
			referencia.setNombreResonsable(usuario);
		}
		
		return referencia;
	}
}