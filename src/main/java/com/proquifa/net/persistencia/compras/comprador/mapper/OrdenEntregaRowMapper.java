package com.proquifa.net.persistencia.compras.comprador.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.proquifa.net.modelo.despachos.OrdenEntrega;
import org.springframework.jdbc.core.RowMapper;

public class OrdenEntregaRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		OrdenEntrega orden = new OrdenEntrega();
		orden.setIdOrdenEntrega(rs.getLong("PK_OrdenEntrega"));
		orden.setFolio(rs.getString("Folio"));
		orden.setFolio_packingList(rs.getString("Packing_List"));
		orden.setIdCliente(rs.getLong("FK01_Cliente"));
		orden.setZona(rs.getString("Zona"));
		orden.setRuta(rs.getString("Ruta"));
		return orden;
	}

}
