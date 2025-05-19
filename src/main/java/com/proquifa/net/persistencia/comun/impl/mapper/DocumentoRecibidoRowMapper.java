/**
 * 
 */
package com.proquifa.net.persistencia.comun.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.comun.DocumentoRecibido;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class DocumentoRecibidoRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		DocumentoRecibido doctoR = new DocumentoRecibido();
		doctoR.setFolio(rs.getLong("folio"));
		doctoR.setPartida(rs.getLong("part"));
		doctoR.setFecha(rs.getTimestamp("fecha"));
		doctoR.setManejo(rs.getString("manejo"));
		doctoR.setOrigen(rs.getString("origen"));
		doctoR.setEmpresa(rs.getLong("empresa"));
		doctoR.setRPor(rs.getString("rpor"));
		doctoR.setMedio(rs.getString("medio"));
		doctoR.setDocto(rs.getString("docto"));
		doctoR.setHabilitado(rs.getInt("Habilitado"));
		
		if(rs.getString("numero")==null || rs.getString("numero").equals("")){
			doctoR.setNumero("Ninguna");
		}else{
			doctoR.setNumero(rs.getString("numero"));
		}
		doctoR.setObservaciones(rs.getString("observa"));
		doctoR.setFechaProceso(rs.getDate("fproceso"));
		doctoR.setIngreso(rs.getString("ingreso"));
		doctoR.setFOrigen(rs.getLong("forigen"));
		doctoR.setEstado(rs.getString("estado"));
		doctoR.setFolioPadre(rs.getString("folioPadre"));
		doctoR.setEsHijo(rs.getBoolean("esHijo"));
		doctoR.setFechaOrigen(rs.getTimestamp("fhorigen"));
		doctoR.setIdContacto(rs.getLong("idContacto"));
		doctoR.setNombreEmpresa(rs.getString("nombre"));
		doctoR.setTipo(rs.getString("docto"));
		doctoR.setMontoDocumento(Math.rint((rs.getDouble("MontoDLS")) * 1e2) / 1e2);
		doctoR.setExisteReferencia(rs.getBoolean("SinReferencia"));
		try {doctoR.setcPago(rs.getString("CPago"));} catch (Exception e) {}
		
		
		return doctoR;
	}
}