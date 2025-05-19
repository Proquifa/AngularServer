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
public class DocumentoRecibidoSRRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		DocumentoRecibido doctoR = new DocumentoRecibido();
		try{
			
		doctoR.setFolio(rs.getLong("folio"));
		doctoR.setPartida(rs.getLong("part"));
		doctoR.setFecha(rs.getTimestamp("fecha"));
		doctoR.setManejo(rs.getString("manejo"));
		doctoR.setOrigen(rs.getString("origen"));
		if(rs.getString("nombre")==null || rs.getString("nombre").equals("")){
			doctoR.setNombreEmpresa("N/D");
		}else{
			doctoR.setNombreEmpresa(rs.getString("nombre"));
		}
		doctoR.setEmpresa(rs.getLong("empresa"));
		doctoR.setRPor(rs.getString("rpor"));
		doctoR.setMedio(rs.getString("medio"));
		doctoR.setNumero(rs.getString("Numero"));
		doctoR.setFechaProceso(rs.getDate("FProceso"));
		doctoR.setCerradoAbierto(rs.getString("CIERREABIERTO"));
		if ( rs.getString("docto").equals("Pedido") ){
			if(rs.getString("DoctoCierre")!=null){
				doctoR.setDocumentoCierre(rs.getString("DoctoCierre"));
			}else{
				doctoR.setDocumentoCierre("Ninguno");
			}	
		}else{
			doctoR.setDocumentoCierre(doctoR.getNumero());
		}
		
		doctoR.setObservaciones(rs.getString("observa"));
		doctoR.setIngreso(rs.getString("ingreso"));
		doctoR.setFOrigen(rs.getLong("forigen"));
		doctoR.setEstado(rs.getString("estado"));
		doctoR.setFolioPadre(rs.getString("folioPadre"));
		doctoR.setEsHijo(rs.getBoolean("esHijo"));
		doctoR.setFechaOrigen(rs.getTimestamp("fhorigen"));
		doctoR.setIdContacto(rs.getLong("idContacto"));
		doctoR.setTipo(rs.getString("docto"));
		doctoR.setDocto(rs.getString("docto"));
		doctoR.setEnTiempo(rs.getString("ENTIEMPO"));
		doctoR.setNivelIngreso(rs.getString("NIVEL"));
		doctoR.setMontoDocumento(Math.rint((rs.getDouble("MontoDLS")) * 1e2) / 1e2);
		doctoR.setExisteReferencia(rs.getBoolean("SinReferencia"));
		
		doctoR.setcPago(rs.getString("CPago"));
		
		}catch(Exception e){
			System.out.print("e"+e.getMessage());
		}
		return doctoR;
	}

}
