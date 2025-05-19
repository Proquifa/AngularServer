/**
 * 
 */
package com.proquifa.net.persistencia.despachos.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.PartidaInspeccion;

/**
 * @author fmartinez
 *
 */
public class PartidaInspeccionOCRowMapper implements RowMapper{

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaInspeccion PIObject = new PartidaInspeccion();
		PIObject.setIdPartidaCompra(rs.getLong("idPCompra"));
		PIObject.setPedimento(rs.getString("Pedimento"));
		PIObject.setDisponibilidadPedimento(rs.getString("DisponibilidadPedimento"));
		PIObject.setGuia(rs.getString("Guia"));
		PIObject.setMesCaduca(rs.getString("MesCaducidad"));
		PIObject.setAnoCaduca(rs.getString("AnoCaducidad"));
		PIObject.setManejo(rs.getString("Manejo"));
		PIObject.setLote(rs.getString("Lote"));
		PIObject.setDocumento(rs.getString("Documentacion"));
		PIObject.setComentarios(rs.getString("Comentarios"));
		PIObject.setIdioma(rs.getString("Idioma"));
		PIObject.setEdicion(rs.getString("Edicion"));
		PIObject.setFechaInicioInspeccion(rs.getTimestamp("Fecha"));
		PIObject.setInspector(rs.getString("Inspector"));
		
		try {
			PIObject.setRevisoNumPiezas(rs.getBoolean("revisoPieza"));
		} catch (Exception e) {

		}

		try {
			PIObject.setRevisoCaducidad(rs.getBoolean("revisoCaducidad"));
		} catch (Exception e) {

		}
		try {
			PIObject.setRevisoLote(rs.getBoolean("revisoLote"));
		} catch (Exception e) {

		}
		try {
			PIObject.setRevisoDoc(rs.getBoolean("revisoDocumento"));
		} catch (Exception e) {

		}

		try {
			PIObject.setAduana(rs.getString("Aduana"));
		} catch (Exception e) {

		}
		
		try {
			PIObject.setFechaPedimento(rs.getDate("FPedimento"));
		} catch (Exception e) {

		}
		
		
		
		
		
		return PIObject;
	}
}