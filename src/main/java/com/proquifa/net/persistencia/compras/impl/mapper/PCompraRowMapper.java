/**
 * 
 */
package com.proquifa.net.persistencia.compras.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;



import org.springframework.jdbc.core.RowMapper;

import com.proquifa.net.modelo.compras.PartidaCompra;

/**
 * @author amartinez
 *
 */
public class PCompraRowMapper implements RowMapper {

	/* (non-Javadoc)
	 * @see org.springframework.jdbc.core.RowMapper#mapRow(java.sql.ResultSet, int)
	 */
	//Utilizado En reportes
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		PartidaCompra partidaCompra = new PartidaCompra();
		partidaCompra.setIdPartidaCompra(rs.getLong("idPCompra"));
		partidaCompra.setEstado(rs.getString("estado"));
		partidaCompra.setCompra(rs.getString("compra"));
		partidaCompra.setPedido(rs.getString("cpedido"));
		partidaCompra.setCodigo(rs.getString("codigo"));
		partidaCompra.setFabrica(rs.getString("fabrica"));
		partidaCompra.setFechaRealArriboUE(rs.getDate("fpharmaue"));
		partidaCompra.setFechaRealArriboUSA(rs.getDate("fpharma"));
		partidaCompra.setFechaRealArriboPQF(rs.getDate("fproquifa"));
		partidaCompra.setAlmacenUE(rs.getBoolean("almacenUE"));
		partidaCompra.setAlmacenUSA(rs.getBoolean("almacenUSA"));
		partidaCompra.setAlmacenMatriz(rs.getBoolean("almacenMatriz"));
		partidaCompra.setFacturaProveedor(rs.getString("FacturaP"));
		partidaCompra.setIdComplemento(rs.getLong("idComplemento"));
		partidaCompra.setNombreCliente(rs.getString("nombreCliente"));
		partidaCompra.setCantidadCompra(rs.getInt("cant"));
		try {partidaCompra.setOrigen(rs.getString("origen"));} catch (Exception e) {}
		return partidaCompra;
	}

}
