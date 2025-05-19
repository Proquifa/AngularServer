/**
 * 
 */
package com.proquifa.net.persistencia.despachos.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.despachos.PackingListJasper;

/**
 * @author admin
 *
 */
public class PackingListJasperRowMapper implements RowMapper<PackingListJasper> {

	@Override
	public PackingListJasper mapRow(ResultSet rs, int rowNum) throws SQLException {
		PackingListJasper pack = new PackingListJasper();
		
		pack.setAmbiente(rs.getInt("Ambiente"));
		pack.setCalle(rs.getString("Calle"));
		pack.setCant(rs.getInt("Cant"));
		pack.setCliente(rs.getString("Cliente"));
		pack.setCodigo(rs.getString("Codigo"));
		pack.setDescripcion(rs.getString("Concepto"));
		pack.setCongelacion(rs.getInt("Congelacion"));
		pack.setContacto(rs.getString("Contacto"));
		try {
			if (pack.getContacto() != null) {
				String contacto = pack.getContacto().replace("--", "");
				pack.setContacto(contacto);
			}
		} catch (Exception e) {
		}
		
		pack.setDestino(rs.getString("Destino"));
		pack.setFolioEmpaque(rs.getString("FolioEmpaque"));
		pack.setFolio(rs.getString("PackingList"));
//		pack.setIdPackingList(rs.getInt("PK_PackingList"));
		pack.setPedido(rs.getString("Pedido"));
		pack.setTitulo(rs.getString("Titulo"));
		pack.setRefrigeracion(rs.getInt("Refrigeracion"));
		pack.setFactura(rs.getString("Factura"));
		
		return pack;
	}
}




