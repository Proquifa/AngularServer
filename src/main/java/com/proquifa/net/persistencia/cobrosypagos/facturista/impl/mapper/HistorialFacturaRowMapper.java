/**
 * 
 */
package com.proquifa.net.persistencia.cobrosypagos.facturista.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.cobrosypagos.facturista.HistorialFactura;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author fmartinez
 *
 */
public class HistorialFacturaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		HistorialFactura hf = new HistorialFactura();
		hf.setComentarios(rs.getString("Comentarios"));
		hf.setFecha(rs.getTimestamp("FechayHora"));
		hf.setUsuario(rs.getString("Usuario"));
		hf.setFactura(rs.getLong("Factura"));
		hf.setFacturadoPor(rs.getString("FPor"));
		hf.setNombreCliente(rs.getString("Cliente"));
		hf.setNombreContacto(rs.getString("Contacto"));
		hf.setPuesto(rs.getString("Puesto"));
		hf.setFEP(rs.getDate("FEP"));

		return hf;
	}
}
