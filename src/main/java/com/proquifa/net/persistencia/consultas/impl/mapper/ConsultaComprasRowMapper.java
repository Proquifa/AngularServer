/**
 * 
 */
package com.proquifa.net.persistencia.consultas.impl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.proquifa.net.modelo.compras.Compra;

import org.springframework.jdbc.core.RowMapper;

/**
 * @author vromero
 *
 */
public class ConsultaComprasRowMapper implements RowMapper {

	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Compra compra = new Compra();
		//Float x = Double.valueOf( Math.rint((rs.getDouble("MONTOTOTAL")) * 1e2) / 1e2).floatValue();
		
		compra.setClave(rs.getString("OC"));
		compra.setNombreProveedor(rs.getString("PROVEDOR"));
		compra.setFabrica(rs.getString("MARCA"));
		compra.setColocarDesde(rs.getString("COLOCO"));
		compra.setFechaConfirmacion(rs.getTimestamp("FCONFIRMACION"));
		compra.setEstado(rs.getString("ESTADO") );
		compra.setFecha(rs.getTimestamp("FColocacion"));
		compra.setTotalPiezas_partidas(rs.getInt("TotalPiezas"));
		compra.setEmpresa(rs.getString("COMPRO"));
		compra.setComprador(rs.getString("COMPRADOR"));
		compra.setMontoTotalDolares_partidas(Math.rint((rs.getDouble("MONTOTOTAL")) * 1e2) / 1e2);
		compra.setTotalPartidas(rs.getLong("PARTIDAS"));
		compra.setAbierto_cerrado(rs.getString("ABIERTO_CERRADO"));
		compra.setInTime(rs.getString("ET"));
		compra.setTotalPiezas(rs.getInt("PIEZAS"));
		compra.setMontoTotalDolares(rs.getDouble("MONTO"));
	
		
		return compra;
	}

}
