/**
 * 
 */
package com.proquifa.net.persistencia.ventas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.ventas.PartidaCotizacion;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author ernestogonzalezlozada
 *
 */
public class PartidaCotizacionConsultasMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCotizacion pcotiza = new PartidaCotizacion();
		pcotiza.setCotizacion(rs.getString("Clave"));
		pcotiza.setPartida(rs.getLong("Partida"));
		pcotiza.setIdPartidaCotizacion(rs.getLong("idPCotiza"));
		pcotiza.setClasifOrigen(rs.getString("Clasif"));
		pcotiza.setClasificacion(rs.getString("Clasificacion"));
		pcotiza.setFolio(rs.getLong("Folio"));
		pcotiza.setCantidad(rs.getInt("Cantidad"));
		pcotiza.setPrecio(rs.getFloat("Precio"));
		pcotiza.setEstado(rs.getString("Estado"));
		pcotiza.setConcepto(rs.getString("Descripcion"));
		pcotiza.setImporte(rs.getFloat("Importe"));
		pcotiza.setSituacion(rs.getString("EstadoFinal"));
		pcotiza.setCodigo(rs.getString("Codigo"));
		pcotiza.setFabrica(rs.getString("Fabrica"));
		
		return pcotiza;
	}
}